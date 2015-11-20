package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.events;

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
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.MissingCashMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.utils.CashTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantCreateUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.logging.Logger;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCashMoneyRestockMonitorAgent implements Agent{
    //TODO: Documentar y manejo de excepciones. Inicializar los manager del Hold Bank y Wallet CBP para que seteados en el constructor
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final StockTransactionCashMoneyRestockManager stockTransactionCashMoneyRestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CashUnholdTransactionManager cashUnHoldTransactionManager;

    public StockTransactionsCashMoneyRestockMonitorAgent(ErrorManager errorManager,
                                                         StockTransactionCashMoneyRestockManager stockTransactionCashMoneyRestockManager,
                                                         CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                         CashUnholdTransactionManager cashUnHoldTransactionManager) {

        this.errorManager                            = errorManager;
        this.stockTransactionCashMoneyRestockManager = stockTransactionCashMoneyRestockManager;
        this.cryptoBrokerWalletManager               = cryptoBrokerWalletManager;
        this.cashUnHoldTransactionManager              = cashUnHoldTransactionManager;
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
                    errorManager.reportUnexpectedPluginException(Plugins.CASH_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }
    }

    private void doTheMainTask(){
        try {
            // I define the filter to null for all
            DatabaseTableFilter filter = null;
            for(CashMoneyTransaction cashMoneyTransaction : stockTransactionCashMoneyRestockManager.getCashMoneyTransactionList(filter))
            {
                switch(cashMoneyTransaction.getTransactionStatus()) {
                    case INIT_TRANSACTION:
                        //Llamar al metodo de la interfaz public del manager de Bank Hold
                        //Luego cambiar el status al registro de la transaccion leido
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_HOLD);
                        stockTransactionCashMoneyRestockManager.saveCashMoneyRestockTransactionData(cashMoneyTransaction);
                        break;
                    case IN_WALLET:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de la wallet si lo consigue entonces le cambia el status de COMPLETED
                        try {
                            WalletTransactionWrapper walletTransactionRecord = new WalletTransactionWrapper(cashMoneyTransaction.getTransactionId(),
                                    null, //FermatEnum revisar
                                    BalanceType.AVAILABLE,
                                    TransactionType.CREDIT,
                                    CurrencyType.BANK_MONEY,
                                    cashMoneyTransaction.getCbpWalletPublicKey(),
                                    cashMoneyTransaction.getActorPublicKey(),
                                    cashMoneyTransaction.getAmount(),
                                    0, //Fecha revisar
                                    cashMoneyTransaction.getConcept());

                            cryptoBrokerWalletManager.getCryptoBrokerWallet(cashMoneyTransaction.getCbpWalletPublicKey()).performTransaction(walletTransactionRecord);

                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                            stockTransactionCashMoneyRestockManager.saveCashMoneyRestockTransactionData(cashMoneyTransaction);

                        } catch (CantPerformTransactionException e) {
                            e.printStackTrace();
                        } catch (CryptoBrokerWalletNotFoundException e) {
                            e.printStackTrace();
                        }
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        stockTransactionCashMoneyRestockManager.saveCashMoneyRestockTransactionData(cashMoneyTransaction);
                        break;
                    case IN_HOLD:
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
                        cashUnHoldTransactionManager.createCashUnholdTransaction(cashTransactionParametersWrapper);
                        CashTransactionStatus castTransactionStatus =  cashUnHoldTransactionManager.getCashUnholdTransactionStatus(cashMoneyTransaction.getTransactionId());
                        if (castTransactionStatus.COMPLETED.getCode() == castTransactionStatus.getCode())
                        {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                            stockTransactionCashMoneyRestockManager.saveCashMoneyRestockTransactionData(cashMoneyTransaction);
                        }
                        break;
                }
            }
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingCashMoneyRestockDataException e) {
            e.printStackTrace();;
        } catch (CantGetUnholdTransactionException e) {
            e.printStackTrace();
        } catch (CantCreateUnholdTransactionException e) {
            e.printStackTrace();
        }
    }
}
