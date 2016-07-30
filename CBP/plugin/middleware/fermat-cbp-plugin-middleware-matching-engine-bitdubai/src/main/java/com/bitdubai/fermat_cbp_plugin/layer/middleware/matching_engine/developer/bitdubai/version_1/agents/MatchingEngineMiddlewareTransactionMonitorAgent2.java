package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.agents;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetTransactionCryptoBrokerWalletMatchingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantMarkAsSeenException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.MatchingEngineMiddlewarePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantCreateInputTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantGetInputTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListWalletsException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.utils.MatchingEngineMiddlewareCurrencyPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * The agent <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.agents.MatchingEngineMiddlewareTransactionMonitorAgent</code>
 * has the responsibility of monitor all the new transactions in the wallets registered in the plug-in and get them as Input Transactions.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareTransactionMonitorAgent2 extends AbstractAgent {

    private static final int SLEEP = 8000;

    private Thread agentThread;

    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final MatchingEngineMiddlewarePluginRoot pluginRoot;
    private final MatchingEngineMiddlewareDao dao;

    public MatchingEngineMiddlewareTransactionMonitorAgent2(long sleepTime,
                                                            TimeUnit timeUnit,
                                                            long initDelayTime,
                                                            final CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                            final MatchingEngineMiddlewarePluginRoot pluginRoot,
                                                            final MatchingEngineMiddlewareDao dao) {
        super(sleepTime, timeUnit, initDelayTime);
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.pluginRoot = pluginRoot;
        this.dao = dao;

    }

    @Override
    protected Runnable agentJob() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doTheMainTask();
            }
        };
        return runnable;
    }

    @Override
    protected void onErrorOccur() {
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception("MatchingEngineMiddlewareTransactionMonitorAgent Error"));
    }

    private void doTheMainTask() {

        List<WalletReference> walletReferenceList;

        try {

            walletReferenceList = dao.listWallets();

        } catch (CantListWalletsException cantListWalletsException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantListWalletsException);
            return;
        }

        for (WalletReference walletReference : walletReferenceList) {

            loadWalletInputTransactions(walletReference);
        }

    }

    private void loadWalletInputTransactions(final WalletReference walletReference) {

        CryptoBrokerWallet cryptoBrokerWallet;

        try {

            cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(walletReference.getPublicKey());

        } catch (CryptoBrokerWalletNotFoundException cryptoBrokerWalletNotFoundException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cryptoBrokerWalletNotFoundException);
            return;
        }

        List<CurrencyMatching> currencyMatchingList;

        try {

            currencyMatchingList = cryptoBrokerWallet.getCryptoBrokerTransactionCurrencyInputs();

        } catch (CantGetTransactionCryptoBrokerWalletMatchingException cantGetTransactionCryptoBrokerWalletMatchingException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetTransactionCryptoBrokerWalletMatchingException);
            return;
        }

        // TODO test purposes
        //currencyMatchingList = testDataCurrencyMatching();

        Map<MatchingEngineMiddlewareCurrencyPair, UUID> linkedEarningPairs = new HashMap<>();

        try {

            List<EarningsPair> earningPairs = dao.listEarningPairs(walletReference);

            for (EarningsPair earningsPair : earningPairs)
                linkedEarningPairs.put(
                        new MatchingEngineMiddlewareCurrencyPair(
                                earningsPair.getEarningCurrency(),
                                earningsPair.getLinkedCurrency()
                        ),
                        earningsPair.getId()
                );

        } catch (CantListEarningsPairsException cantListEarningsPairsException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantListEarningsPairsException);
            return;
        }

        MatchingEngineMiddlewareCurrencyPair currencyPair;

        List<String> transactionsToMarkAsSeen = new ArrayList<>();

        for (CurrencyMatching currencyMatching : currencyMatchingList) {

            try {

                currencyPair = new MatchingEngineMiddlewareCurrencyPair(
                        currencyMatching.getCurrencyGiving(),
                        currencyMatching.getCurrencyReceiving()
                );

                UUID earningPairId = linkedEarningPairs.get(currencyPair);

                if (earningPairId == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("currencyMatching: ").append(currencyMatching).append(" - ").append("There's no earnings pair set for this currency matching.");
                    System.out.println(stringBuilder.toString());
                    transactionsToMarkAsSeen.add(currencyMatching.getOriginTransactionId());
                    //I'll remove this report from this agent, is very annoying and doesn't have any porpoise
                    //pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new CantCreateInputTransactionException("currencyMatching: " + currencyMatching, "There's no earnings pair set for this currency matching."));

                } else {

                    if (!dao.existsInputTransaction(currencyMatching.getOriginTransactionId()))
                        dao.createInputTransaction(
                                currencyMatching,
                                earningPairId
                        );

                    transactionsToMarkAsSeen.add(currencyMatching.getOriginTransactionId());
                }
            } catch (CantCreateInputTransactionException | CantGetInputTransactionException daoException) {

                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, daoException);
                return;
            }
        }

        try {

            cryptoBrokerWallet.markAsSeen(transactionsToMarkAsSeen);

        } catch (CantMarkAsSeenException cantMarkAsSeenException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantMarkAsSeenException);
        }

    }
/*
    private List<CurrencyMatching> testDataCurrencyMatching() {

        List<CurrencyMatching> matchingList = new ArrayList<>();

        matchingList.add(
                new CurrencyMatchingImp(
                        "ORIGIN1",
                        FiatCurrency.VENEZUELAN_BOLIVAR,
                        CryptoCurrency.BITCOIN,
                        1000,
                        1
                )
        );

        matchingList.add(
                new CurrencyMatchingImp(
                        "ORIGIN2",
                        CryptoCurrency.BITCOIN,
                        FiatCurrency.VENEZUELAN_BOLIVAR,
                        0.9f,
                        1000
                        )
        );

        matchingList.add(
                new CurrencyMatchingImp(
                        "ORIGIN3",
                        CryptoCurrency.BITCOIN,
                        FiatCurrency.VENEZUELAN_BOLIVAR,
                        1.5f,
                        1650
                )
        );

        matchingList.add(
                new CurrencyMatchingImp(
                        "ORIGIN4",
                        FiatCurrency.VENEZUELAN_BOLIVAR,
                        CryptoCurrency.BITCOIN,
                        1500,
                        1.5f
                )
        );
        matchingList.add(
                new CurrencyMatchingImp(
                        "ORIGIN5",
                        FiatCurrency.VENEZUELAN_BOLIVAR,
                        CryptoCurrency.BITCOIN,
                        1500,
                        1.5f
                )
        );

        return matchingList;
    }*/

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }

}
