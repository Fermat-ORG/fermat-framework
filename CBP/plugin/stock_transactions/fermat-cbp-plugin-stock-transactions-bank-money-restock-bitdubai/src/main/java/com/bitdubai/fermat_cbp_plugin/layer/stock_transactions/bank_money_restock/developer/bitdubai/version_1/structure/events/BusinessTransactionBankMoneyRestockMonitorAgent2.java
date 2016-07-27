package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import java.util.concurrent.TimeUnit;


/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class BusinessTransactionBankMoneyRestockMonitorAgent2 extends AbstractAgent {
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
    public BusinessTransactionBankMoneyRestockMonitorAgent2(long sleepTime,
                                                            TimeUnit timeUnit,
                                                            long initDelayTime,
                                                            BusinessTransactionBankMoneyRestockPluginRoot pluginRoot,
                                                            StockTransactionBankMoneyRestockManager stockTransactionBankMoneyRestockManager,
                                                            CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                            HoldManager holdManager,
                                                            PluginDatabaseSystem pluginDatabaseSystem,
                                                            UUID pluginId,
                                                            Broadcaster broadcaster) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
        this.stockTransactionBankMoneyRestockManager = stockTransactionBankMoneyRestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.holdManager = holdManager;
        this.stockTransactionBankMoneyRestockFactory = new StockTransactionBankMoneyRestockFactory(pluginDatabaseSystem, pluginId);
        this.pluginId = pluginId;
        this.broadcaster = broadcaster;
    }

    @Override
    protected Runnable agentJob() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doTheMainTask();
            }
        };
        return runnable;
    }

    @Override
    protected void onErrorOccur() {
        pluginRoot.reportError(
                UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                new Exception("BusinessTransactionBankMoneyDestockMonitorAgent2 Error"));
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

}
