package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.agents;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListInputTransactionsException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListWalletsException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

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

        // bring unmatched transactions for each pair
        List<InputTransaction> inputTransactionList;

        try {

            inputTransactionList = dao.listUnmatchedInputTransactions(earningsPair.getId());

        } catch (CantListInputTransactionsException cantListInputTransactionsException) {

            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantListInputTransactionsException);
            return;
        }

        // compare the transactions and generate the neccesary earning transactions

        // have in count:
        // there can be a lot of transactions in a side and a lot of transactions in the other side
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }

}
