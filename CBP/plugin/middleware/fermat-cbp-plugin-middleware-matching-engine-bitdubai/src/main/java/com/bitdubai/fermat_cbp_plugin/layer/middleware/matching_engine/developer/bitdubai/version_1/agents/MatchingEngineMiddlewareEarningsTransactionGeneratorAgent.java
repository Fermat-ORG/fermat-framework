package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.agents;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListInputTransactionsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.MatchingEngineMiddlewarePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantGetInputTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantListWalletsException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningTransaction;

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

    private final MatchingEngineMiddlewarePluginRoot pluginRoot;
    private final MatchingEngineMiddlewareDao dao;

    public MatchingEngineMiddlewareEarningsTransactionGeneratorAgent(final MatchingEngineMiddlewarePluginRoot pluginRoot,
                                                                     final MatchingEngineMiddlewareDao dao) {

        this.pluginRoot = pluginRoot;
        this.dao = dao;

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

        if (isRunning())
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

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantListWalletsException);
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

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantListEarningsPairsException);
            return;
        }

        // process inputs for each pair
        for (EarningsPair earningsPair : earningPairs)
            processInputTransactions(earningsPair);

    }

    private void processInputTransactions(final EarningsPair earningsPair) {

        try {

            InputTransaction unmatchedSellInputTransaction = dao.getNextUnmatchedSellInputTransaction(earningsPair);

            InputTransaction sellTransactionToMatch;

            while (unmatchedSellInputTransaction != null) {

                sellTransactionToMatch = unmatchedSellInputTransaction;

                System.out.println(new StringBuilder().append(" *************** processing sell transaction: ").append(sellTransactionToMatch.getOriginTransactionId()).toString());

                DatabaseTransaction databaseTransaction = dao.getNewDatabaseTransaction();

                float amountToMatch = sellTransactionToMatch.getAmountGiving();
                float amountMatched = 0;

                List<InputTransaction> unmatchedBuyInputTransactions = dao.listUnmatchedBuyInputTransactions(earningsPair);

                // if there's not any unmatched buy transactions I cannot match.
                if (unmatchedBuyInputTransactions.isEmpty())
                    break;

                for (InputTransaction inputTransaction : unmatchedBuyInputTransactions)
                    System.out.println(new StringBuilder().append("    ************ unmatched buy transactions: ").append(inputTransaction.getOriginTransactionId()).toString());

                UUID earningTransactionId = UUID.randomUUID();

                List<InputTransaction> matchedBuyTransactions = new ArrayList<>();

                int i = 0;
                while (i < unmatchedBuyInputTransactions.size() && amountMatched != amountToMatch) {

                    InputTransaction buyTransaction = unmatchedBuyInputTransactions.get(i);

                    // amount matched with the current buy transaction
                    float amountMatchedTemp = amountMatched + buyTransaction.getAmountReceiving();

                    System.out.println(new StringBuilder().append("       ********* amountToMatch    : ").append(amountToMatch).toString());
                    System.out.println(new StringBuilder().append("       ********* amountMatched    : ").append(amountMatched).toString());
                    System.out.println(new StringBuilder().append("       ********* amountMatchedTemp: ").append(amountMatchedTemp).toString());

                    // if there are equals, i will generate the earning transaction and mark both sell and buy transaction as matched.
                    if (amountToMatch == amountMatchedTemp) {

                        System.out.println("         ******* amount is equal: the while will be finished");

                        amountMatched = amountMatchedTemp;
                        dao.markInputTransactionAsMatched(databaseTransaction, buyTransaction.getId(), earningTransactionId);
                        matchedBuyTransactions.add(buyTransaction);

                    }

                    // if the amount to match is lesser than the amount matched, i've to cut the buy transaction in parts
                    // the first part to match the needed amount, and the second part as unmatched amount
                    // i will mark the original buy transaction as matched as well.
                    else if (amountToMatch < amountMatchedTemp) {

                        System.out.println("         ******* the amount to match is lesser than the amount matched temp (with the new buy transaction)");
                        System.out.println("         ******* generated new input transactions: one matched, one unmatched");

                        float partialToMatchAmountReceiving = amountToMatch - amountMatched;
                        float partialToMatchAmountGiving = partialToMatchAmountReceiving / buyTransaction.getAmountReceiving() * buyTransaction.getAmountGiving();

                        float partialRestingAmountGiving = buyTransaction.getAmountGiving() - partialToMatchAmountGiving;
                        float partialRestingAmountReceiving = buyTransaction.getAmountReceiving() - partialToMatchAmountReceiving;

                        InputTransaction partialToMatch = dao.createPartialMatchedInputTransaction(
                                databaseTransaction,
                                buyTransaction.getOriginTransactionId(),
                                buyTransaction.getCurrencyGiving(),
                                partialToMatchAmountGiving,
                                buyTransaction.getCurrencyReceiving(),
                                partialToMatchAmountReceiving,
                                earningsPair.getId(),
                                earningTransactionId
                        );

                        System.out.println(new StringBuilder().append("            **** partialToMatch: ").append(partialToMatch).toString());

                        InputTransaction partialRest = dao.createPartialUnmatchedInputTransaction(
                                databaseTransaction,
                                buyTransaction.getOriginTransactionId(),
                                buyTransaction.getCurrencyGiving(),
                                partialRestingAmountGiving,
                                buyTransaction.getCurrencyReceiving(),
                                partialRestingAmountReceiving,
                                earningsPair.getId()
                        );

                        System.out.println(new StringBuilder().append("            **** partialRest: ").append(partialRest).toString());

                        dao.markInputTransactionAsSplit(databaseTransaction, buyTransaction.getId());
                        matchedBuyTransactions.add(partialToMatch);
                        amountMatched = amountToMatch;

                        System.out.println("         ******* amount is equal: the while will be finished");
                    }

                    // if the amount to match is bigger than the amount matched i've to keep looking buy transactions until i can.
                    else {
                        System.out.println("         ******* the amount to match is bigger than the amount to match with the current buy transaction: i will look for more buy transactions to match");

                        amountMatched = amountMatchedTemp;
                        dao.markInputTransactionAsMatched(databaseTransaction, buyTransaction.getId(), earningTransactionId);
                        matchedBuyTransactions.add(buyTransaction);
                    }

                    i++;
                }

                // if the amount to match is greater than the amount matched, i've to cut the sell transaction in parts
                // the first part to match the needed amount, and the second part as unmatched amount
                // i will mark the original sell transaction as matched as well.
                if (amountToMatch > amountMatched) {

                    System.out.println("       ********* woow we cannot reach the amount to match with the buy transactions we will have to cut the sell transaction    : ");
                    System.out.println(new StringBuilder().append("           ***** amountToMatch: ").append(amountToMatch).toString());
                    System.out.println(new StringBuilder().append("           ***** amountMatched: ").append(amountMatched).toString());

                    System.out.println("         ******* generated new sell input transactions: one matched, one unmatched");

                    float partialToRestAmountGiving = amountToMatch - amountMatched;
                    float partialToRestAmountReceiving = partialToRestAmountGiving / sellTransactionToMatch.getAmountGiving() * sellTransactionToMatch.getAmountReceiving();

                    float partialToMatchAmountGiving = sellTransactionToMatch.getAmountGiving() - partialToRestAmountGiving;
                    float partialToMatchAmountReceiving = sellTransactionToMatch.getAmountReceiving() - partialToRestAmountReceiving;

                    InputTransaction partialRest = dao.createPartialUnmatchedInputTransaction(
                            databaseTransaction,
                            sellTransactionToMatch.getOriginTransactionId(),
                            sellTransactionToMatch.getCurrencyGiving(),
                            partialToRestAmountGiving,
                            sellTransactionToMatch.getCurrencyReceiving(),
                            partialToRestAmountReceiving,
                            earningsPair.getId()
                    );

                    System.out.println(new StringBuilder().append("           ***** partialRest: ").append(partialRest).toString());

                    InputTransaction partialToMatch = dao.createPartialMatchedInputTransaction(
                            databaseTransaction,
                            sellTransactionToMatch.getOriginTransactionId(),
                            sellTransactionToMatch.getCurrencyGiving(),
                            partialToMatchAmountGiving,
                            sellTransactionToMatch.getCurrencyReceiving(),
                            partialToMatchAmountReceiving,
                            earningsPair.getId(),
                            earningTransactionId
                    );

                    System.out.println(new StringBuilder().append("           ***** partialToMatch: ").append(partialToMatch).toString());

                    dao.markInputTransactionAsSplit(databaseTransaction, sellTransactionToMatch.getId());
                    sellTransactionToMatch = partialToMatch;

                } else {
                    dao.markInputTransactionAsMatched(databaseTransaction, sellTransactionToMatch.getId(), earningTransactionId);
                }

                System.out.println("       ********* Now We'll create the new EarningTransaction: ");
                System.out.println(new StringBuilder().append("          ****** sellTransactionToMatch: ").append(sellTransactionToMatch).toString());

                for (InputTransaction buyTransactionToMatch : matchedBuyTransactions)
                    System.out.println(new StringBuilder().append("          ****** buyTransactionToMatch : ").append(buyTransactionToMatch).toString());

                System.out.println("                *****");

                float sellAmountReceiving = sellTransactionToMatch.getAmountReceiving();
                float buyAmountGiving = 0;

                for (InputTransaction inputTransaction : matchedBuyTransactions)
                    buyAmountGiving += inputTransaction.getAmountGiving();

                float earningAmount = sellAmountReceiving - buyAmountGiving;

                System.out.println(new StringBuilder().append("          ****** sellAmountReceiving: ").append(sellAmountReceiving).toString());
                System.out.println(new StringBuilder().append("          ****** buyAmountGiving    : ").append(buyAmountGiving).toString());

                EarningTransaction newEarningTransaction = new MatchingEngineMiddlewareEarningTransaction(
                        earningTransactionId,
                        earningsPair.getEarningCurrency(),
                        earningAmount,
                        EarningTransactionState.CALCULATED,
                        System.currentTimeMillis(),
                        dao);

                System.out.println("          ****** : ******");
                System.out.println(new StringBuilder().append("          ****** newEarningTransaction: ").append(newEarningTransaction).toString());
                System.out.println("          ****** : ******");


                dao.createEarningTransaction(databaseTransaction, newEarningTransaction, earningsPair.getId());

                try {
                    dao.executeTransaction(databaseTransaction);
                } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, databaseTransactionFailedException);
                }

                unmatchedSellInputTransaction = dao.getNextUnmatchedSellInputTransaction(earningsPair);
            }

        } catch (CantGetInputTransactionException | CantListInputTransactionsException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }

}
