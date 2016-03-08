package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.agents;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionType;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantGetInputTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListInputTransactionsException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListWalletsException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareInputTransaction;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The agent <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.agents.MatchingEngineMiddlewareEarningsTransactionGeneratorAgent</code>
 * has the responsibility of monitor all the input transactions in the wallets registered in the plug-in and generate the proper Earning Transactions if needed.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/03/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareEarningsTransactionGeneratorAgent extends FermatAgent {

    private static final int SLEEP = 8000;

    private Thread agentThread;

    private final ErrorManager                errorManager             ;
    private final MatchingEngineMiddlewareDao dao                      ;
    private final PluginVersionReference      pluginVersionReference   ;

    public MatchingEngineMiddlewareEarningsTransactionGeneratorAgent(final ErrorManager                errorManager             ,
                                                                     final MatchingEngineMiddlewareDao dao                      ,
                                                                     final PluginVersionReference      pluginVersionReference   ) {

        this.errorManager              = errorManager             ;
        this.dao                       = dao                      ;
        this.pluginVersionReference    = pluginVersionReference   ;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        });
    }

    @Override
    public void start() {
        this.agentThread.start();
        this.status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {

        if(isRunning())
            this.agentThread.interrupt();

        this.status = AgentStatus.STOPPED;
    }

    public void process() {

        while (isRunning()) {

            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException interruptedException) {
                cleanResources();
                return;
            }

            doTheMainTask();

            if (agentThread.isInterrupted()) {
                cleanResources();
                return;
            }
        }
    }

    private void doTheMainTask() {

        List<WalletReference> walletReferenceList;

        try {

            walletReferenceList = dao.listWallets();

        } catch (CantListWalletsException cantListWalletsException) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantListWalletsException);
            return;
        }

        for (WalletReference walletReference : walletReferenceList) {

            processEarningsPairs(walletReference);
        }

    }

    private void processEarningsPairs(final WalletReference walletReference) {

        // bring the pairs registered for the wallet
        List<EarningsPair> earningPairs;

        try {

            earningPairs = dao.listEarningPairs(walletReference);

        } catch (CantListEarningsPairsException cantListEarningsPairsException) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantListEarningsPairsException);
            return;
        }

        // process inputs for each pair
        for (EarningsPair earningsPair : earningPairs)
            processInputTransactions(earningsPair);

    }

    private void processInputTransactions(final EarningsPair earningsPair) {

        try {

            InputTransaction unmatchedSellInputTransaction = dao.getNextUnmatchedSellInputTransaction(earningsPair);

            InputTransaction sellTransactionToMatch = unmatchedSellInputTransaction;

            while (unmatchedSellInputTransaction != null) {

                DatabaseTransaction databaseTransaction = dao.getNewDatabaseTransaction();

                float amountToMatch = unmatchedSellInputTransaction.getAmountGiving();
                float amountMatched = 0;

                List<InputTransaction> unmatchedBuyInputTransactions = dao.listUnmatchedBuyInputTransactions(earningsPair);

                // if there's not any unmatched buy transactions I cannot match.
                if (unmatchedBuyInputTransactions.isEmpty())
                    break;

                List<InputTransaction> matchedBuyTransactions = new ArrayList<>();

                int i = 0;
                while (i < unmatchedBuyInputTransactions.size()) {

                    InputTransaction buyTransaction = unmatchedBuyInputTransactions.get(i);

                    // amount matched with the current buy transaction
                    float amountMatchedTemp = amountMatched + buyTransaction.getAmountReceiving();

                    // if there are equals, i will generate the earning transaction and mark both sell and buy transaction as matched.
                    if (amountToMatch == amountMatchedTemp) {

                        amountMatched = amountMatchedTemp;
                        dao.markInputTransactionAsMatched(databaseTransaction, buyTransaction.getId());
                        matchedBuyTransactions.add(buyTransaction);
                        break;

                    }

                    // if the amount to match is lesser than the amount matched, i've to cut the buy transaction in parts
                    // the first part to match the needed amount, and the second part as unmatched amount
                    // i will mark the original buy transaction as matched as well.
                    else if (amountToMatch < amountMatchedTemp) {

                        float partialToMatchAmountGiving    = amountToMatch - amountMatched;
                        float partialToMatchAmountReceiving = buyTransaction.getAmountGiving() / partialToMatchAmountGiving * buyTransaction.getAmountReceiving();

                        float partialRestingAmountGiving    = buyTransaction.getAmountGiving()    - partialToMatchAmountGiving   ;
                        float partialRestingAmountReceiving = buyTransaction.getAmountReceiving() - partialToMatchAmountReceiving;

                        InputTransaction partialToMatch = dao.createPartialInputTransaction(
                                databaseTransaction                    ,
                                buyTransaction.getOriginTransactionId(),
                                buyTransaction.getCurrencyGiving()     ,
                                partialToMatchAmountGiving             ,
                                buyTransaction.getCurrencyReceiving()  ,
                                partialToMatchAmountReceiving          ,
                                earningsPair.getId()                   ,
                                InputTransactionState.MATCHED
                        );

                        dao.createPartialInputTransaction(
                                databaseTransaction,
                                buyTransaction.getOriginTransactionId(),
                                buyTransaction.getCurrencyGiving(),
                                partialRestingAmountGiving,
                                buyTransaction.getCurrencyReceiving(),
                                partialRestingAmountReceiving,
                                earningsPair.getId(),
                                InputTransactionState.UNMATCHED
                        );

                        dao.markInputTransactionAsMatched(databaseTransaction, buyTransaction.getId());
                        matchedBuyTransactions.add(partialToMatch);
                        amountMatched = amountToMatch;
                    }

                    // if the amount to match is bigger than the amount matched i've to keep looking buy transactions until i can.
                    else {

                        amountMatched = amountMatchedTemp;
                        dao.markInputTransactionAsMatched(databaseTransaction, buyTransaction.getId());
                        matchedBuyTransactions.add(buyTransaction);
                    }

                    i++;
                }

                // if the amount to match is greater than the amount matched, i've to cut the sell transaction in parts
                // the first part to match the needed amount, and the second part as unmatched amount
                // i will mark the original sell transaction as matched as well.
                if (amountToMatch > amountMatched) {

                    float partialToMatchAmountGiving    = amountToMatch - amountMatched;
                    float partialToMatchAmountReceiving = unmatchedSellInputTransaction.getAmountGiving() / partialToMatchAmountGiving * unmatchedSellInputTransaction.getAmountReceiving();

                    float partialRestingAmountGiving    = unmatchedSellInputTransaction.getAmountGiving()    - partialToMatchAmountGiving   ;
                    float partialRestingAmountReceiving = unmatchedSellInputTransaction.getAmountReceiving() - partialToMatchAmountReceiving;

                    InputTransaction partialToMatch = dao.createPartialInputTransaction(
                            databaseTransaction                    ,
                            unmatchedSellInputTransaction.getOriginTransactionId(),
                            unmatchedSellInputTransaction.getCurrencyGiving()     ,
                            partialToMatchAmountGiving             ,
                            unmatchedSellInputTransaction.getCurrencyReceiving()  ,
                            partialToMatchAmountReceiving          ,
                            earningsPair.getId()                   ,
                            InputTransactionState.MATCHED
                    );

                    dao.createPartialInputTransaction(
                            databaseTransaction,
                            unmatchedSellInputTransaction.getOriginTransactionId(),
                            unmatchedSellInputTransaction.getCurrencyGiving(),
                            partialRestingAmountGiving,
                            unmatchedSellInputTransaction.getCurrencyReceiving(),
                            partialRestingAmountReceiving,
                            earningsPair.getId(),
                            InputTransactionState.UNMATCHED
                    );

                    dao.markInputTransactionAsMatched(databaseTransaction, unmatchedSellInputTransaction.getId());
                    matchedBuyTransactions.add(partialToMatch);
                    amountToMatch = amountMatched;
                    sellTransactionToMatch = partialToMatch;
                }

                // generate the earning transaction with:
                // sellTransactionToMatch as the sell transaction to match...
                // matchedBuyTransactions as the buy transactions related to it...
                // then execute the database transaction

                unmatchedSellInputTransaction = dao.getNextUnmatchedSellInputTransaction(earningsPair);
            }

        } catch (CantGetInputTransactionException | CantListInputTransactionsException e) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }

}
