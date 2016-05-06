package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.MissingCashMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyRestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.utils.CashTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCashMoneyRestockMonitorAgent extends FermatAgent {
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final StockTransactionCashMoneyRestockManager stockTransactionCashMoneyRestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CashHoldTransactionManager cashHoldTransactionManager;
    private final StockTransactionCashMoneyRestockFactory stockTransactionCashMoneyRestockFactory;
    private final UUID pluginId;
    //private  MonitorAgent monitorAgent;
    public final int SLEEP_TIME = 5000;
    int iterationNumber = 0;
    boolean threadWorking;

    public StockTransactionsCashMoneyRestockMonitorAgent(ErrorManager errorManager,
                                                         StockTransactionCashMoneyRestockManager stockTransactionCashMoneyRestockManager,
                                                         CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                         CashHoldTransactionManager cashHoldTransactionManager,
                                                         PluginDatabaseSystem pluginDatabaseSystem,
                                                         UUID pluginId) {

        this.errorManager = errorManager;
        this.stockTransactionCashMoneyRestockManager = stockTransactionCashMoneyRestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cashHoldTransactionManager = cashHoldTransactionManager;
        this.stockTransactionCashMoneyRestockFactory = new StockTransactionCashMoneyRestockFactory(pluginDatabaseSystem, pluginId);
        this.pluginId = pluginId;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        }, this.getClass().getSimpleName());
    }

    @Override
    public void start() throws CantStartAgentException {
        Logger LOG = Logger.getGlobal();
        LOG.info("Cash Money Restock Transaction monitor agent starting");

//        monitorAgent = new MonitorAgent(errorManager);
//
//        this.agentThread = new Thread(monitorAgent);
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
                Thread.sleep(SLEEP_TIME);
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

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
//    private final class MonitorAgent implements Runnable {
//
//        private final ErrorManager errorManager;
//        public final int SLEEP_TIME = 5000;
//        int iterationNumber = 0;
//        boolean threadWorking;
//
//        public MonitorAgent(final ErrorManager errorManager) {
//
//            this.errorManager = errorManager;
//        }
//
//        @Override
//        public void run() {
//            threadWorking = true;
//            while (threadWorking) {
//                /**
//                 * Increase the iteration counter
//                 */
//                iterationNumber++;
//                try {
//                    Thread.sleep(SLEEP_TIME);
//
//                    /**
//                     * now I will check if there are pending transactions to raise the event
//                     */
//                    doTheMainTask();
//
//                } catch (InterruptedException e) {
//                    errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                } catch (Exception e) {
//                    errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                }
//
//            }
//        }
//    }
    private void doTheMainTask() {
        try {
            // I define the filter to null for all
            DatabaseTableFilter filter = null;
            for (CashMoneyTransaction cashMoneyTransaction : stockTransactionCashMoneyRestockFactory.getCashMoneyTransactionList(filter)) {
                switch (cashMoneyTransaction.getTransactionStatus()) {
                    case INIT_TRANSACTION:
                        //Llamar al metodo de la interfaz public del manager de Bank Hold
                        //Luego cambiar el status al registro de la transaccion leido
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de Cash Hold y si lo consigue entonces le cambia el status de IN_WALLET y hace el credito
                        CashTransactionParametersWrapper cashTransactionParametersWrapper = new CashTransactionParametersWrapper(

                                cashMoneyTransaction.getTransactionId(),
                                cashMoneyTransaction.getFiatCurrency(),
                                cashMoneyTransaction.getCashWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                //cashMoneyTransaction.getCashReference(),
                                cashMoneyTransaction.getMemo(),
                                pluginId.toString());

                        if (!cashHoldTransactionManager.isTransactionRegistered(cashMoneyTransaction.getTransactionId()))
                            cashHoldTransactionManager.createCashHoldTransaction(cashTransactionParametersWrapper);

                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_EJECUTION);
                        stockTransactionCashMoneyRestockFactory.saveCashMoneyRestockTransactionData(cashMoneyTransaction);
                        break;
                    case IN_EJECUTION:
                        CashTransactionStatus castTransactionStatus = cashHoldTransactionManager.getCashHoldTransactionStatus(cashMoneyTransaction.getTransactionId());

                        if (CashTransactionStatus.CONFIRMED == castTransactionStatus) {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_HOLD);
                            stockTransactionCashMoneyRestockFactory.saveCashMoneyRestockTransactionData(cashMoneyTransaction);
                        }
                        if (CashTransactionStatus.REJECTED == castTransactionStatus) {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                            stockTransactionCashMoneyRestockFactory.saveCashMoneyRestockTransactionData(cashMoneyTransaction);
                        }
                        break;
                    case IN_HOLD:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de la wallet si lo consigue entonces le cambia el status de COMPLETED
                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(
                                UUID.randomUUID(),
                                cashMoneyTransaction.getFiatCurrency(),
                                BalanceType.BOOK,
                                TransactionType.CREDIT,
                                MoneyType.CASH_DELIVERY,
                                cashMoneyTransaction.getCbpWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                new Date().getTime() / 1000,
                                cashMoneyTransaction.getConcept(),
                                cashMoneyTransaction.getPriceReference(),
                                cashMoneyTransaction.getOriginTransaction(),
                                cashMoneyTransaction.getOriginTransactionId(),
                                false);

                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(
                                UUID.randomUUID(),
                                cashMoneyTransaction.getFiatCurrency(),
                                BalanceType.AVAILABLE,
                                TransactionType.CREDIT,
                                MoneyType.CASH_DELIVERY,
                                cashMoneyTransaction.getCbpWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                new Date().getTime() / 1000,
                                cashMoneyTransaction.getConcept(),
                                cashMoneyTransaction.getPriceReference(),
                                cashMoneyTransaction.getOriginTransaction(),
                                cashMoneyTransaction.getOriginTransactionId(),
                                false);

                        cashMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                        final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cashMoneyTransaction.getCbpWalletPublicKey());
                        final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                        stockBalance.credit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.credit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        stockTransactionCashMoneyRestockFactory.saveCashMoneyRestockTransactionData(cashMoneyTransaction);

                        break;
                    case IN_WALLET:
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        stockTransactionCashMoneyRestockFactory.saveCashMoneyRestockTransactionData(cashMoneyTransaction);
                        break;
                }
            }
        } catch (CryptoBrokerWalletNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetStockCryptoBrokerWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantAddCreditCryptoBrokerWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingCashMoneyRestockDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetHoldTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCreateHoldTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
