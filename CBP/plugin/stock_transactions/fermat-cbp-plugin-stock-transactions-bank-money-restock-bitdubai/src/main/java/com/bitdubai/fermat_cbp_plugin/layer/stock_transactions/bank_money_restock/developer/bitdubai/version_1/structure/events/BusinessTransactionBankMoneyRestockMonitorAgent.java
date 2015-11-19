package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantMakeHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantPerformTransactionException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.MissingBankMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.utils.BankTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.logging.Logger;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class BusinessTransactionBankMoneyRestockMonitorAgent  implements Agent{
    //TODO: Documentar y manejo de excepciones
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final HoldManager holdManager;

    public BusinessTransactionBankMoneyRestockMonitorAgent(ErrorManager                            errorManager,
                                                           StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager,
                                                           CryptoBrokerWalletManager               cryptoBrokerWalletManager,
                                                           HoldManager                             holdManager) {

        this.errorManager                            = errorManager;
        this.stockTransactionBankMoneyRestockManager = stockTransactionBankMoneyRestockManager;
        this.cryptoBrokerWalletManager               = cryptoBrokerWalletManager;
        this.holdManager                             = holdManager;
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
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CBP_STOCK_TRANSACTIONS_BANK_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }
        }
    }

    private void doTheMainTask(){
        try {
            // I define the filter to null for all
            DatabaseTableFilter filter = null;
            for(BankMoneyTransaction bankMoneyTransaction : stockTransactionBankMoneyRestockManager.getBankMoneyTransactionList(filter))
            {
                switch(bankMoneyTransaction.getTransactionStatus()) {
                    case INIT_TRANSACTION:
                        //Llamar al metodo de la interfaz public del manager de Bank Hold
                        BankTransactionParametersWrapper bankTransactionParametersWrapper = new BankTransactionParametersWrapper(
                                bankMoneyTransaction.getTransactionId(),
                                bankMoneyTransaction.getFiatCurrency(),
                                bankMoneyTransaction.getBnkWalletPublicKey(),
                                bankMoneyTransaction.getActorPublicKey(),
                                bankMoneyTransaction.getBankAccount(),
                                bankMoneyTransaction.getAmount(),
                                bankMoneyTransaction.getMemo(),
                                "pluginId");
                        holdManager.hold(bankTransactionParametersWrapper);
                        //Luego cambiar el status al registro de la transaccion leido
                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_HOLD);
                        stockTransactionBankMoneyRestockManager.saveBankMoneyRestockTransactionData(bankMoneyTransaction);
                        break;
                    case IN_HOLD:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de Bank Hold y si lo consigue entonces le cambia el status de IN_WALLET y hace el credito
                        BankTransactionStatus bankTransactionStatus =  holdManager.getUnholdTransactionsStatus(bankMoneyTransaction.getTransactionId());
                        if (BankTransactionStatus.CONFIRMED.getCode() == bankTransactionStatus.getCode())
                        {

                           try {
                                WalletTransactionWrapper walletTransactionRecord = new WalletTransactionWrapper(bankMoneyTransaction.getTransactionId(),
                                                                                                                null,
                                                                                                                BalanceType.AVAILABLE,
                                                                                                                TransactionType.CREDIT,
                                                                                                                CurrencyType.BANK_MONEY,
                                                                                                                bankMoneyTransaction.getCbpWalletPublicKey(),
                                                                                                                bankMoneyTransaction.getActorPublicKey(),
                                                                                                                bankMoneyTransaction.getAmount(),
                                                                                                                0,
                                                                                                                bankMoneyTransaction.getConcept());

                               cryptoBrokerWalletManager.getCryptoBrokerWallet(bankMoneyTransaction.getCbpWalletPublicKey()).performTransaction(walletTransactionRecord);

                               bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                               stockTransactionBankMoneyRestockManager.saveBankMoneyRestockTransactionData(bankMoneyTransaction);

                           } catch (CantPerformTransactionException e) {
                                e.printStackTrace();
                            } catch (CryptoBrokerWalletNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                        break;
                    case IN_WALLET:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de la wallet si lo consigue entonces le cambia el status de COMPLETED
                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        stockTransactionBankMoneyRestockManager.saveBankMoneyRestockTransactionData(bankMoneyTransaction);
                        break;
                }
            }
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingBankMoneyRestockDataException e) {
            e.printStackTrace();
        } catch (CantMakeHoldTransactionException e) {
            e.printStackTrace();
        } catch (CantGetHoldTransactionException e) {
            e.printStackTrace();
        }
    }
}
