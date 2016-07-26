package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.BusinessTransactionBankMoneyRestockPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyRestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.utils.BankTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;

import java.util.Date;
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

    private final BusinessTransactionBankMoneyRestockPluginRoot pluginRoot;
    private final StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final HoldManager holdManager;
    StockTransactionBankMoneyRestockFactory stockTransactionBankMoneyRestockFactory;
    private UUID pluginId;
    private Broadcaster broadcaster;

    //private final ErrorManager errorManager;
    public final int SLEEP_TIME = 1500;
    int iterationNumber = 0;
    boolean threadWorking;

    /**
     * Constructor for BusinessTransactionBankMoneyRestockMonitorAgent
     *
     * @param pluginRoot
     * @param stockTransactionBankMoneyRestockManager
     * @param cryptoBrokerWalletManager
     * @param holdManager
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public BusinessTransactionBankMoneyRestockMonitorAgent(BusinessTransactionBankMoneyRestockPluginRoot pluginRoot,
                                                           StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager,
                                                           CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                           HoldManager holdManager,
                                                           PluginDatabaseSystem pluginDatabaseSystem,
                                                           UUID pluginId,
                                                           Broadcaster broadcaster) {

        this.pluginRoot = pluginRoot;
        this.stockTransactionBankMoneyRestockManager = stockTransactionBankMoneyRestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.holdManager = holdManager;
        this.stockTransactionBankMoneyRestockFactory = new StockTransactionBankMoneyRestockFactory(pluginDatabaseSystem, pluginId);
        this.pluginId = pluginId;
        this.broadcaster = broadcaster;

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


    private void doTheMainTask() {
        try {

            //Get unprocessed transactions (All with status != COMPLETED)
            for (BankMoneyTransaction bankMoneyTransaction : stockTransactionBankMoneyRestockFactory.getBankMoneyTransactionList(null)) {
                switch (bankMoneyTransaction.getTransactionStatus()) {

                    case INIT_TRANSACTION:
                        //Try to hold the funds in the bank wallet
                        BankTransactionParametersWrapper bankTransactionParametersWrapper = new BankTransactionParametersWrapper(bankMoneyTransaction.getTransactionId(), bankMoneyTransaction.getFiatCurrency(), bankMoneyTransaction.getBnkWalletPublicKey(), bankMoneyTransaction.getActorPublicKey(), bankMoneyTransaction.getBankAccount(), bankMoneyTransaction.getAmount(), bankMoneyTransaction.getMemo(), pluginId.toString());
                        holdManager.hold(bankTransactionParametersWrapper);

                        //Set status to IN_HOLD
                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_HOLD);
                        break;

                    case IN_HOLD:
                        //Get the status of the hold transaction from the bank
                        BankTransactionStatus holdTransactionStatus = holdManager.getHoldTransactionsStatus(bankMoneyTransaction.getTransactionId());

                        //If hold was CONFIRMED, set status to IN_WALLET
                        if (BankTransactionStatus.CONFIRMED.equals(holdTransactionStatus))
                            bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);

                            //If REJECTED, set status to REJECTED
                        else if (BankTransactionStatus.REJECTED.equals(holdTransactionStatus)) {
                            //Send error broadcast to Stock Management Fragment
                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW_ERROR);
                            bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                        }
                        break;

                    case IN_WALLET:
                        //Hold was CONFIRMED, do restock broker wallet
                        bankMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                        final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(bankMoneyTransaction.getCbpWalletPublicKey());
                        final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(UUID.randomUUID(), bankMoneyTransaction.getFiatCurrency(), BalanceType.BOOK, TransactionType.CREDIT, MoneyType.BANK, bankMoneyTransaction.getCbpWalletPublicKey(), bankMoneyTransaction.getActorPublicKey(), bankMoneyTransaction.getAmount(), new Date().getTime() / 1000, bankMoneyTransaction.getConcept(), bankMoneyTransaction.getPriceReference(), bankMoneyTransaction.getOriginTransaction(), bankMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(UUID.randomUUID(), bankMoneyTransaction.getFiatCurrency(), BalanceType.AVAILABLE, TransactionType.CREDIT, MoneyType.BANK, bankMoneyTransaction.getCbpWalletPublicKey(), bankMoneyTransaction.getActorPublicKey(), bankMoneyTransaction.getAmount(), new Date().getTime() / 1000, bankMoneyTransaction.getConcept(), bankMoneyTransaction.getPriceReference(), bankMoneyTransaction.getOriginTransaction(), bankMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.credit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.credit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);

                        //Send broadcast to Stock Management Fragment
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW);

                        //Set status to COMPLETED
                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        break;

                }

                //Save the current bankMoneyTransaction status
                stockTransactionBankMoneyRestockFactory.saveBankMoneyRestockTransactionData(bankMoneyTransaction);
            }
        } catch (FermatException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
