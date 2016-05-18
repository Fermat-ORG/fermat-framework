package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyRestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.utils.BankTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class BusinessTransactionBankMoneyRestockMonitorAgent extends FermatAgent {
    //TODO: Documentar y manejo de excepciones
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final ErrorManager errorManager;
    private final StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final HoldManager holdManager;
    StockTransactionBankMoneyRestockFactory stockTransactionBankMoneyRestockFactory;
    private UUID pluginId;

    //private final ErrorManager errorManager;
    public final int SLEEP_TIME = 5000;
    int iterationNumber = 0;
    boolean threadWorking;

    /**
     * Constructor for BusinessTransactionBankMoneyRestockMonitorAgent
     *
     * @param errorManager
     * @param stockTransactionBankMoneyRestockManager
     * @param cryptoBrokerWalletManager
     * @param holdManager
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public BusinessTransactionBankMoneyRestockMonitorAgent(ErrorManager errorManager,
                                                           StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager,
                                                           CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                           HoldManager holdManager,
                                                           PluginDatabaseSystem pluginDatabaseSystem,
                                                           UUID pluginId) {

        this.errorManager = errorManager;
        this.stockTransactionBankMoneyRestockManager = stockTransactionBankMoneyRestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.holdManager = holdManager;
        this.stockTransactionBankMoneyRestockFactory = new StockTransactionBankMoneyRestockFactory(pluginDatabaseSystem, pluginId);
        this.pluginId = pluginId;

        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        }, this.getClass().getSimpleName());
    }

    /**
     * Starts the agent
     *
     * @throws CantStartAgentException
     */
    @Override
    public void start() throws CantStartAgentException {
        Logger LOG = Logger.getGlobal();
        LOG.info("Bank Money Restock Transaction monitor agent starting");

        //final MonitorAgent monitorAgent = new MonitorAgent(errorManager);

        //this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
        this.status = AgentStatus.STARTED;
    }

    /**
     * Stops the agent
     */
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
//                    errorManager.reportUnexpectedPluginException(Plugins.BANK_MONEY_RESTOCK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                }
//
//            }
//        }
//    }
    private void doTheMainTask() {
        try {
            // I define the filter to null for all
            DatabaseTableFilter filter = null;
            for (BankMoneyTransaction bankMoneyTransaction : stockTransactionBankMoneyRestockFactory.getBankMoneyTransactionList(filter)) {
                switch (bankMoneyTransaction.getTransactionStatus()) {
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
                                pluginId.toString());

                        if (!holdManager.isTransactionRegistered(bankMoneyTransaction.getTransactionId()))
                            holdManager.hold(bankTransactionParametersWrapper);

                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_EJECUTION);
                        stockTransactionBankMoneyRestockFactory.saveBankMoneyRestockTransactionData(bankMoneyTransaction);

                        break;
                    case IN_EJECUTION:
                        //Luego cambiar el status al registro de la transaccion leido
                        if (Objects.equals(holdManager.getHoldTransactionsStatus(bankMoneyTransaction.getTransactionId()).getCode(), BankTransactionStatus.CONFIRMED.getCode())) {
                            bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_HOLD);
                            stockTransactionBankMoneyRestockFactory.saveBankMoneyRestockTransactionData(bankMoneyTransaction);
                        }
                        if (Objects.equals(holdManager.getHoldTransactionsStatus(bankMoneyTransaction.getTransactionId()).getCode(), BankTransactionStatus.REJECTED.getCode())) {
                            bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                            stockTransactionBankMoneyRestockFactory.saveBankMoneyRestockTransactionData(bankMoneyTransaction);
                        }

                        break;
                    case IN_HOLD:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de Bank Hold y si lo consigue entonces le cambia el status de IN_WALLET y hace el credito
                        BankTransactionStatus bankTransactionStatus = holdManager.getHoldTransactionsStatus(bankMoneyTransaction.getTransactionId());
                        if (Objects.equals(BankTransactionStatus.CONFIRMED.getCode(), bankTransactionStatus.getCode())) {

                            WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(
                                    UUID.randomUUID(),
                                    bankMoneyTransaction.getFiatCurrency(),
                                    BalanceType.BOOK,
                                    TransactionType.CREDIT,
                                    MoneyType.BANK,
                                    bankMoneyTransaction.getCbpWalletPublicKey(),
                                    bankMoneyTransaction.getActorPublicKey(),
                                    bankMoneyTransaction.getAmount(),
                                    new Date().getTime() / 1000,
                                    bankMoneyTransaction.getConcept(),
                                    bankMoneyTransaction.getPriceReference(),
                                    bankMoneyTransaction.getOriginTransaction(),
                                    bankMoneyTransaction.getOriginTransactionId(),
                                    false);

                            WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(
                                    UUID.randomUUID(),
                                    bankMoneyTransaction.getFiatCurrency(),
                                    BalanceType.AVAILABLE,
                                    TransactionType.CREDIT,
                                    MoneyType.BANK,
                                    bankMoneyTransaction.getCbpWalletPublicKey(),
                                    bankMoneyTransaction.getActorPublicKey(),
                                    bankMoneyTransaction.getAmount(),
                                    new Date().getTime() / 1000,
                                    bankMoneyTransaction.getConcept(),
                                    bankMoneyTransaction.getPriceReference(),
                                    bankMoneyTransaction.getOriginTransaction(),
                                    bankMoneyTransaction.getOriginTransactionId(),
                                    false);

                            bankMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                            final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(bankMoneyTransaction.getCbpWalletPublicKey());
                            final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                            stockBalance.credit(walletTransactionRecordBook, BalanceType.BOOK);
                            stockBalance.credit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);
                            bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                            stockTransactionBankMoneyRestockFactory.saveBankMoneyRestockTransactionData(bankMoneyTransaction);

                        }

                        break;
                    case IN_WALLET:
                        //Llamar al metodo de la interfaz public del manager de la wallet CBP
                        //Luego cambiar el status al registro de la transaccion leido
                        //Buscar el regsitro de la transaccion en manager de la wallet si lo consigue entonces le cambia el status de COMPLETED
                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        stockTransactionBankMoneyRestockFactory.saveBankMoneyRestockTransactionData(bankMoneyTransaction);
                        break;
                }
            }
        } catch (FermatException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BANK_MONEY_RESTOCK,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
