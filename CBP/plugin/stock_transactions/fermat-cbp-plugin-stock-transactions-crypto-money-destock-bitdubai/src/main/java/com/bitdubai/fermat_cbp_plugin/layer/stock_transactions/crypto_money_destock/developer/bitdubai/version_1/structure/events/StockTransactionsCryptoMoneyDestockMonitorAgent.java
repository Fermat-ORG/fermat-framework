package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyDestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.utils.CryptoTransactionParametersWrapper;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.logging.Logger;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCryptoMoneyDestockMonitorAgent implements Agent{
    //TODO: Documentar y manejo de excepciones.
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final StockTransactionCryptoMoneyDestockManager stockTransactionCryptoMoneyDestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CryptoHoldTransactionManager cryptoHoldTransactionManager;

    public StockTransactionsCryptoMoneyDestockMonitorAgent(ErrorManager errorManager,
                                                           StockTransactionCryptoMoneyDestockManager stockTransactionCryptoMoneyDestockManager,
                                                           CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                           CryptoHoldTransactionManager cryptoHoldTransactionManager)
                                                           {

        this.errorManager                            = errorManager;
        this.stockTransactionCryptoMoneyDestockManager = stockTransactionCryptoMoneyDestockManager;
        this.cryptoBrokerWalletManager               = cryptoBrokerWalletManager;
        this.cryptoHoldTransactionManager            = cryptoHoldTransactionManager;
    }
    @Override
    public void start() throws CantStartAgentException {
        Logger LOG = Logger.getGlobal();
        LOG.info("Bank Money Restock Transaction monitor agent starting");

        final MonitorAgent monitorAgent = new MonitorAgent(errorManager);

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private final class MonitorAgent implements Runnable {

        private final ErrorManager errorManager;
        public final int SLEEP_TIME = 5000;
        int iterationNumber = 0;
        boolean threadWorking;

        public MonitorAgent(final ErrorManager errorManager) {

            this.errorManager = errorManager;
        }

        @Override
        public void run() {
            threadWorking = true;
            while (threadWorking) {
                /**
                 * Increase the iteration counter
                 */
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {
                    doTheMainTask();
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }
    }

    private void doTheMainTask(){
        try {
            // I define the filter to null for all
            DatabaseTableFilter filter = null;
            for(CryptoMoneyTransaction cryptoMoneyTransaction : stockTransactionCryptoMoneyDestockManager.getCryptoMoneyTransactionList(filter)) {
                switch (cryptoMoneyTransaction.getTransactionStatus()) {
                    case INIT_TRANSACTION:
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        stockTransactionCryptoMoneyDestockManager.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                        break;
                    case IN_WALLET:
//                        try {
//                            WalletTransactionWrapper walletTransactionRecord = new WalletTransactionWrapper(cryptoMoneyTransaction.getTransactionId(),
//                                    null,
//                                    BalanceType.AVAILABLE,
//                                    TransactionType.CREDIT,
//                                    CurrencyType.BANK_MONEY,
//                                    cryptoMoneyTransaction.getCbpWalletPublicKey(),
//                                    cryptoMoneyTransaction.getActorPublicKey(),
//                                    cryptoMoneyTransaction.getAmount(),
//                                    new Date().getTime() / 1000,
//                                    cryptoMoneyTransaction.getConcept());
//
//                            cryptoBrokerWalletManager.getCryptoBrokerWallet(cryptoMoneyTransaction.getCbpWalletPublicKey()).performTransaction(walletTransactionRecord);
//
//                        } catch (CantPerformTransactionException e) {
//                            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);;
//                        } catch (CryptoBrokerWalletNotFoundException e) {
//                            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);;
//                        }
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        stockTransactionCryptoMoneyDestockManager.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                        break;
                    case IN_UNHOLD:
                        CryptoTransactionParametersWrapper cryptoTransactionParametersWrapper = new CryptoTransactionParametersWrapper(

                                cryptoMoneyTransaction.getTransactionId(),
                                cryptoMoneyTransaction.getCryptoCurrency(),
                                cryptoMoneyTransaction.getCryWalletPublicKey(),
                                cryptoMoneyTransaction.getActorPublicKey(),
                                cryptoMoneyTransaction.getAmount(),
                                cryptoMoneyTransaction.getMemo(),
                                "pluginId");
                        cryptoHoldTransactionManager.createCryptoHoldTransaction(cryptoTransactionParametersWrapper);
                        CryptoTransactionStatus cryptoTransactionStatus = cryptoHoldTransactionManager.getCryptoHoldTransactionStatus(cryptoMoneyTransaction.getTransactionId());
                        if (CryptoTransactionStatus.CONFIRMED.getCode() == cryptoTransactionStatus.getCode()) {
                            cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                            stockTransactionCryptoMoneyDestockManager.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                        }
                        if (CryptoTransactionStatus.REJECTED.getCode() == cryptoTransactionStatus.getCode()) {
                            cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                            stockTransactionCryptoMoneyDestockManager.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
                        }
                        break;
                }
            }
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);;
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);;
        } catch (MissingCryptoMoneyDestockDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);;
        } catch (CantCreateHoldTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);;
        } catch (CantGetHoldTransactionException e) {
        errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            ;
        }
    }
}
