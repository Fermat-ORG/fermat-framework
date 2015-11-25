package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantPerformTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions.MissingCashMoneyDestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils.CashTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionParameters;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.Date;
import java.util.logging.Logger;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCashMoneyDestockMonitorAgent implements Agent{
    //TODO: Documentar y manejo de excepciones. Inicializar los manager del Hold Bank y Wallet CBP para que seteados en el constructor
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final StockTransactionCashMoneyDestockManager stockTransactionCashMoneyDestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CashHoldTransactionManager cashHoldTransactionManager;

    public StockTransactionsCashMoneyDestockMonitorAgent(ErrorManager errorManager,
                                                         StockTransactionCashMoneyDestockManager stockTransactionCashMoneyDestockManager,
                                                         CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                         CashHoldTransactionManager cashHoldTransactionManager) {

        this.errorManager                            = errorManager;
        this.stockTransactionCashMoneyDestockManager = stockTransactionCashMoneyDestockManager;
        this.cryptoBrokerWalletManager               = cryptoBrokerWalletManager;
        this.cashHoldTransactionManager              = cashHoldTransactionManager;
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
                    errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_DESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }
    }

    private void doTheMainTask(){
        try {
            // I define the filter to null for all
            DatabaseTableFilter filter = null;
            for(CashMoneyTransaction cashMoneyTransaction : stockTransactionCashMoneyDestockManager.getCashMoneyTransactionList(filter))
            {
                switch(cashMoneyTransaction.getTransactionStatus()) {
                    case INIT_TRANSACTION:
                        //Llamar al metodo de la interfaz public del manager de Bank Hold
                        //Luego cambiar el status al registro de la transaccion leido
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        stockTransactionCashMoneyDestockManager.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        break;
                    case IN_WALLET:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de la wallet si lo consigue entonces le cambia el status de COMPLETED
                        try {
                            WalletTransactionWrapper walletTransactionRecord = new WalletTransactionWrapper(cashMoneyTransaction.getTransactionId(),
                                    null, //Fecha revisar
                                    BalanceType.AVAILABLE,
                                    TransactionType.CREDIT,
                                    CurrencyType.BANK_MONEY,
                                    cashMoneyTransaction.getCbpWalletPublicKey(),
                                    cashMoneyTransaction.getActorPublicKey(),
                                    cashMoneyTransaction.getAmount(),
                                    new Date().getTime() / 1000,
                                    cashMoneyTransaction.getConcept());

                            cryptoBrokerWalletManager.getCryptoBrokerWallet(cashMoneyTransaction.getCbpWalletPublicKey()).performTransaction(walletTransactionRecord);

                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                            stockTransactionCashMoneyDestockManager.saveCashMoneyDestockTransactionData(cashMoneyTransaction);

                        } catch (CantPerformTransactionException e) {
                            e.printStackTrace();
                        } catch (CryptoBrokerWalletNotFoundException e) {
                            e.printStackTrace();
                        }
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        stockTransactionCashMoneyDestockManager.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
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
                                cashMoneyTransaction.getCashReference(),
                                cashMoneyTransaction.getMemo());
                                //"pluginId");
                        cashHoldTransactionManager.createCashHoldTransaction(cashTransactionParametersWrapper);
                        CashTransactionStatus castTransactionStatus =  cashHoldTransactionManager.getCashHoldTransactionStatus(cashMoneyTransaction.getTransactionId());
                        if (castTransactionStatus.CONFIRMED.getCode() == castTransactionStatus.getCode())
                        {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                            stockTransactionCashMoneyDestockManager.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        }
                        if (castTransactionStatus.REJECTED.getCode() == castTransactionStatus.getCode())
                        {
                            //Debito en la Wallet CBP
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                            stockTransactionCashMoneyDestockManager.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
                        }
                        break;
                }
            }
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingCashMoneyDestockDataException e) {
            e.printStackTrace();
        } catch (CantCreateHoldTransactionException e) {
            e.printStackTrace();
        } catch (CantGetHoldTransactionException e) {
            e.printStackTrace();
        }
    }
}
