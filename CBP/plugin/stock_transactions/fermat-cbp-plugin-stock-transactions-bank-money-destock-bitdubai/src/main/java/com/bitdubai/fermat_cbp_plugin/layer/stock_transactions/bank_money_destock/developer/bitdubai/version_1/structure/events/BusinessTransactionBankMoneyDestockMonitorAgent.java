package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces.UnholdManager;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.BusinessTransactionBankMoneyDestockPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyDestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.utils.BankTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class BusinessTransactionBankMoneyDestockMonitorAgent extends FermatAgent {
    //TODO: Documentar y manejo de excepciones.

    private Thread agentThread;

    private final BusinessTransactionBankMoneyDestockPluginRoot pluginRoot;
    private final StockTransactionBankMoneyDestockManager stockTransactionBankMoneyDestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final UnholdManager unHoldManager;
    StockTransactionBankMoneyDestockFactory stockTransactionBankMoneyDestockFactory;
    private UUID pluginId;
    private Broadcaster broadcaster;

    public final int SLEEP_TIME = 1500;
    int iterationNumber = 0;
    boolean threadWorking;


    /**
     * Constructor for BusinessTransactionBankMoneyDestockMonitorAgent
     *
     * @param pluginRoot
     * @param stockTransactionBankMoneyDestockManager
     * @param cryptoBrokerWalletManager
     * @param unHoldManager
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public BusinessTransactionBankMoneyDestockMonitorAgent(BusinessTransactionBankMoneyDestockPluginRoot pluginRoot,
                                                           StockTransactionBankMoneyDestockManager stockTransactionBankMoneyDestockManager,
                                                           CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                           UnholdManager unHoldManager,
                                                           PluginDatabaseSystem pluginDatabaseSystem,
                                                           UUID pluginId,
                                                           Broadcaster broadcaster) {

        this.pluginRoot = pluginRoot;
        this.stockTransactionBankMoneyDestockManager = stockTransactionBankMoneyDestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.unHoldManager = unHoldManager;
        this.stockTransactionBankMoneyDestockFactory = new StockTransactionBankMoneyDestockFactory(pluginDatabaseSystem, pluginId);
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
        LOG.info("Bank Money Destock Transaction monitor agent starting");

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

    private void doTheMainTask() {
        try {

            //Get unprocessed transactions (All with status != COMPLETED)
            final List<BankMoneyTransaction> bankMoneyTransactionList = stockTransactionBankMoneyDestockFactory.getBankMoneyTransactionList(null);

            for (BankMoneyTransaction bankMoneyTransaction : bankMoneyTransactionList) {

                //Get broker wallet's balance
                bankMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest");  //TODO:Solo para testear
                final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(bankMoneyTransaction.getCbpWalletPublicKey());
                final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                switch (bankMoneyTransaction.getTransactionStatus()) {

                    case INIT_TRANSACTION:
                        //Debit the broker wallet
                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(UUID.randomUUID(), bankMoneyTransaction.getFiatCurrency(), BalanceType.BOOK, TransactionType.DEBIT, MoneyType.BANK, bankMoneyTransaction.getCbpWalletPublicKey(), bankMoneyTransaction.getActorPublicKey(), bankMoneyTransaction.getAmount(), new Date().getTime() / 1000, bankMoneyTransaction.getConcept(), bankMoneyTransaction.getPriceReference(), bankMoneyTransaction.getOriginTransaction(), bankMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(UUID.randomUUID(), bankMoneyTransaction.getFiatCurrency(), BalanceType.AVAILABLE, TransactionType.DEBIT, MoneyType.BANK, bankMoneyTransaction.getCbpWalletPublicKey(), bankMoneyTransaction.getActorPublicKey(), bankMoneyTransaction.getAmount(), new Date().getTime() / 1000, bankMoneyTransaction.getConcept(), bankMoneyTransaction.getPriceReference(), bankMoneyTransaction.getOriginTransaction(), bankMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.debit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.debit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);

                        //Set status to IN_WALLET
                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        break;

                    case IN_WALLET:
                        //Try to unhold the funds in the bank wallet
                        BankTransactionParametersWrapper bankTransactionParametersWrapper = new BankTransactionParametersWrapper(bankMoneyTransaction.getTransactionId(), bankMoneyTransaction.getFiatCurrency(), bankMoneyTransaction.getBnkWalletPublicKey(), bankMoneyTransaction.getActorPublicKey(), bankMoneyTransaction.getBankAccount(), bankMoneyTransaction.getAmount(), bankMoneyTransaction.getMemo(), pluginId.toString());
                        unHoldManager.unHold(bankTransactionParametersWrapper);

                        //Set status to IN_UNHOLD
                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        break;

                    case IN_UNHOLD:

                        //Get the status of the hold transaction from the bank
                        BankTransactionStatus unholdTransactionStatus = unHoldManager.getUnholdTransactionsStatus(bankMoneyTransaction.getTransactionId());

                        //If unhold was CONFIRMED, set status to COMPLETED
                        if (BankTransactionStatus.CONFIRMED.equals(unholdTransactionStatus)) {

                            //Send broadcast to Stock Management Fragment
                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW);
                            bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        }

                        //If unhold was REJECTED, set status to REJECTED
                        else if (BankTransactionStatus.REJECTED.equals(unholdTransactionStatus))
                            bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);

                        break;

                    case REJECTED:
                        //If unhold was REJECTED, undo deposit on broker wallet
                        WalletTransactionWrapper walletTransactionRecordBook2 = new WalletTransactionWrapper(UUID.randomUUID(), bankMoneyTransaction.getFiatCurrency(), BalanceType.BOOK, TransactionType.CREDIT, MoneyType.BANK, bankMoneyTransaction.getCbpWalletPublicKey(), bankMoneyTransaction.getActorPublicKey(), bankMoneyTransaction.getAmount(), new Date().getTime() / 1000, bankMoneyTransaction.getConcept(), bankMoneyTransaction.getPriceReference(), bankMoneyTransaction.getOriginTransaction(), bankMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordAvailable2 = new WalletTransactionWrapper(UUID.randomUUID(), bankMoneyTransaction.getFiatCurrency(), BalanceType.AVAILABLE, TransactionType.CREDIT, MoneyType.BANK, bankMoneyTransaction.getCbpWalletPublicKey(), bankMoneyTransaction.getActorPublicKey(), bankMoneyTransaction.getAmount(), new Date().getTime() / 1000, bankMoneyTransaction.getConcept(), bankMoneyTransaction.getPriceReference(), bankMoneyTransaction.getOriginTransaction(), bankMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.credit(walletTransactionRecordBook2, BalanceType.BOOK);
                        stockBalance.credit(walletTransactionRecordAvailable2, BalanceType.AVAILABLE);

                        //Send error broadcast to Stock Management Fragment
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW_ERROR);

                        //Set status to COMPLETED
                        bankMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        break;

                }

                //Save the current bankMoneyTransaction status
                stockTransactionBankMoneyDestockFactory.saveBankMoneyDestockTransactionData(bankMoneyTransaction);
            }
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
