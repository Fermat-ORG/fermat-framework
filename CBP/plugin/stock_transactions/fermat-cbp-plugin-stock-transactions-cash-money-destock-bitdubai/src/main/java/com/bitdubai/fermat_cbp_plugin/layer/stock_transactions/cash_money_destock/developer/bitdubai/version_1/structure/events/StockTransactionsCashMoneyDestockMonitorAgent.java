package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions.MissingCashMoneyDestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils.CashTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantCreateUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCashMoneyDestockMonitorAgent extends FermatAgent {
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final StockTransactionCashMoneyDestockManager stockTransactionCashMoneyDestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CashUnholdTransactionManager cashUnholdTransactionManager;
    private final StockTransactionCashMoneyDestockFactory stockTransactionCashMoneyDestockFactory;
    private UUID pluginId;
    public final int SLEEP_TIME = 5000;
    int iterationNumber = 0;
    boolean threadWorking;

    public StockTransactionsCashMoneyDestockMonitorAgent(ErrorManager errorManager,
                                                         StockTransactionCashMoneyDestockManager stockTransactionCashMoneyDestockManager,
                                                         CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                         CashUnholdTransactionManager cashUnholdTransactionManager,
                                                         PluginDatabaseSystem pluginDatabaseSystem,
                                                         UUID pluginId) {

        this.errorManager = errorManager;
        this.stockTransactionCashMoneyDestockManager = stockTransactionCashMoneyDestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cashUnholdTransactionManager = cashUnholdTransactionManager;
        stockTransactionCashMoneyDestockFactory = new StockTransactionCashMoneyDestockFactory(pluginDatabaseSystem, pluginId);
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
        LOG.info("Cash Money Destock Transaction monitor agent starting");

        //final MonitorAgent monitorAgent = new MonitorAgent(errorManager);

        //this.agentThread = new Thread(monitorAgent);
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
//
//                    doTheMainTask();
//                } catch (InterruptedException e) {
//                    errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                } catch (Exception e) {
//                    errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                }
//
//            }
//        }
//    }
    private void doTheMainTask() {
        try {
            // I define the filter to null for all
            DatabaseTableFilter filter = null;
            for (CashMoneyTransaction cashMoneyTransaction : stockTransactionCashMoneyDestockFactory.getCashMoneyTransactionList(filter)) {
                switch (cashMoneyTransaction.getTransactionStatus()) {
                    case INIT_TRANSACTION:
                        //Llamar al metodo de la interfaz public del manager de Bank Hold
                        //Luego cambiar el status al registro de la transaccion leido
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        break;
                    case IN_WALLET:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de la wallet si lo consigue entonces le cambia el status de COMPLETED
                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(
                                cashMoneyTransaction.getTransactionId(),
                                cashMoneyTransaction.getFiatCurrency(),
                                BalanceType.BOOK,
                                TransactionType.DEBIT,
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
                                cashMoneyTransaction.getTransactionId(),
                                cashMoneyTransaction.getFiatCurrency(),
                                BalanceType.AVAILABLE,
                                TransactionType.DEBIT,
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

                        //TODO:Solo para testear
                        cashMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest");
                        cryptoBrokerWalletManager.loadCryptoBrokerWallet(cashMoneyTransaction.getCbpWalletPublicKey()).getStockBalance().debit(walletTransactionRecordBook, BalanceType.BOOK);
                        cryptoBrokerWalletManager.loadCryptoBrokerWallet(cashMoneyTransaction.getCbpWalletPublicKey()).getStockBalance().debit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);

                        break;
                    case IN_UNHOLD:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de Bank Hold y si lo consigue entonces le cambia el status de IN_WALLET y hace el credito
                        CashTransactionParametersWrapper cashTransactionParametersWrapper = new CashTransactionParametersWrapper(

                                cashMoneyTransaction.getTransactionId(),
                                cashMoneyTransaction.getFiatCurrency(),
                                cashMoneyTransaction.getCashWalletPublicKey(),
                                cashMoneyTransaction.getActorPublicKey(),
                                cashMoneyTransaction.getAmount(),
                                //cashMoneyTransaction.getCashReference(),
                                cashMoneyTransaction.getMemo(),
                                pluginId.toString());

                        if (!cashUnholdTransactionManager.isTransactionRegistered(cashMoneyTransaction.getTransactionId()))
                            cashUnholdTransactionManager.createCashUnholdTransaction(cashTransactionParametersWrapper);

                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_EJECUTION);
                        stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        break;
                    case IN_EJECUTION:
                        CashTransactionStatus castTransactionStatus = cashUnholdTransactionManager.getCashUnholdTransactionStatus(cashMoneyTransaction.getTransactionId());
                        if (Objects.equals(castTransactionStatus.CONFIRMED.getCode(), castTransactionStatus.getCode())) {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                            stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        }
                        if (Objects.equals(castTransactionStatus.REJECTED.getCode(), castTransactionStatus.getCode())) {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                            stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        }
                        break;
                }
            }
        } catch (CryptoBrokerWalletNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetStockCryptoBrokerWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantAddDebitCryptoBrokerWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingCashMoneyDestockDataException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCreateUnholdTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetUnholdTransactionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
