package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.StockTransactionsCashMoneyDestockPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils.CashTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionManager;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCashMoneyDestockMonitorAgent2 extends AbstractAgent {

    private final StockTransactionsCashMoneyDestockPluginRoot pluginRoot;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CashUnholdTransactionManager cashUnholdTransactionManager;
    private final StockTransactionCashMoneyDestockFactory stockTransactionCashMoneyDestockFactory;
    private UUID pluginId;
    private Broadcaster broadcaster;

    public StockTransactionsCashMoneyDestockMonitorAgent2(long sleepTime,
                                                          TimeUnit timeUnit,
                                                          long initDelayTime,
                                                          StockTransactionsCashMoneyDestockPluginRoot pluginRoot,
                                                          CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                          CashUnholdTransactionManager cashUnholdTransactionManager,
                                                          PluginDatabaseSystem pluginDatabaseSystem,
                                                          UUID pluginId,
                                                          Broadcaster broadcaster) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cashUnholdTransactionManager = cashUnholdTransactionManager;
        stockTransactionCashMoneyDestockFactory = new StockTransactionCashMoneyDestockFactory(pluginDatabaseSystem, pluginId);
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
                new Exception("StockTransactionsCashMoneyDestockMonitorAgent Error"));
    }


    private void doTheMainTask() {
        try {

            //Get unprocessed transactions (All with status != COMPLETED)
            for (CashMoneyTransaction cashMoneyTransaction : stockTransactionCashMoneyDestockFactory.getCashMoneyTransactionList(null)) {

                //Get broker wallet's balance
                cashMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cashMoneyTransaction.getCbpWalletPublicKey());
                final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                switch (cashMoneyTransaction.getTransactionStatus()) {

                    case INIT_TRANSACTION:
                        //Debit the broker wallet
                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(UUID.randomUUID(), cashMoneyTransaction.getFiatCurrency(), BalanceType.BOOK, TransactionType.DEBIT, MoneyType.CASH_DELIVERY, cashMoneyTransaction.getCbpWalletPublicKey(), cashMoneyTransaction.getActorPublicKey(), cashMoneyTransaction.getAmount(), new Date().getTime() / 1000, cashMoneyTransaction.getConcept(), cashMoneyTransaction.getPriceReference(), cashMoneyTransaction.getOriginTransaction(), cashMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(UUID.randomUUID(), cashMoneyTransaction.getFiatCurrency(), BalanceType.AVAILABLE, TransactionType.DEBIT, MoneyType.CASH_DELIVERY, cashMoneyTransaction.getCbpWalletPublicKey(), cashMoneyTransaction.getActorPublicKey(), cashMoneyTransaction.getAmount(), new Date().getTime() / 1000, cashMoneyTransaction.getConcept(), cashMoneyTransaction.getPriceReference(), cashMoneyTransaction.getOriginTransaction(), cashMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.debit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.debit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);

                        //Set status to IN_WALLET
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        break;

                    case IN_WALLET:
                        //Try to unhold the funds in the cash wallet
                        CashTransactionParametersWrapper cashTransactionParametersWrapper = new CashTransactionParametersWrapper(cashMoneyTransaction.getTransactionId(), cashMoneyTransaction.getFiatCurrency(), cashMoneyTransaction.getCashWalletPublicKey(), cashMoneyTransaction.getActorPublicKey(), cashMoneyTransaction.getAmount(), cashMoneyTransaction.getMemo(), pluginId.toString());
                        cashUnholdTransactionManager.createCashUnholdTransaction(cashTransactionParametersWrapper);

                        //Set status to IN_UNHOLD
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        break;

                    case IN_UNHOLD:
                        //Get the status of the hold transaction from the cash wallet
                        CashTransactionStatus castTransactionStatus = cashUnholdTransactionManager.getCashUnholdTransactionStatus(cashMoneyTransaction.getTransactionId());

                        //If unhold was CONFIRMED, set status to COMPLETED
                        if (CashTransactionStatus.CONFIRMED.equals(castTransactionStatus)) {

                            //Send broadcast to Stock Management Fragment
                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW);
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        }

                        //If unhold was REJECTED, set status to REJECTED
                        else if (CashTransactionStatus.REJECTED.equals(castTransactionStatus))
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);

                        break;

                    case REJECTED:
                        //If unhold was REJECTED, undo deposit on broker wallet
                        WalletTransactionWrapper walletTransactionRecordBook2 = new WalletTransactionWrapper(UUID.randomUUID(), cashMoneyTransaction.getFiatCurrency(), BalanceType.BOOK, TransactionType.CREDIT, MoneyType.CASH_DELIVERY, cashMoneyTransaction.getCbpWalletPublicKey(), cashMoneyTransaction.getActorPublicKey(), cashMoneyTransaction.getAmount(), new Date().getTime() / 1000, cashMoneyTransaction.getConcept(), cashMoneyTransaction.getPriceReference(), cashMoneyTransaction.getOriginTransaction(), cashMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordAvailable2 = new WalletTransactionWrapper(UUID.randomUUID(), cashMoneyTransaction.getFiatCurrency(), BalanceType.AVAILABLE, TransactionType.CREDIT, MoneyType.CASH_DELIVERY, cashMoneyTransaction.getCbpWalletPublicKey(), cashMoneyTransaction.getActorPublicKey(), cashMoneyTransaction.getAmount(), new Date().getTime() / 1000, cashMoneyTransaction.getConcept(), cashMoneyTransaction.getPriceReference(), cashMoneyTransaction.getOriginTransaction(), cashMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.credit(walletTransactionRecordBook2, BalanceType.BOOK);
                        stockBalance.credit(walletTransactionRecordAvailable2, BalanceType.AVAILABLE);

                        //Send error broadcast to Stock Management Fragment
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW_ERROR);

                        //Set status to COMPLETED
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        break;

                }

                //Save the current bankMoneyTransaction status
                stockTransactionCashMoneyDestockFactory.saveCashMoneyDestockTransactionData(cashMoneyTransaction);
            }
        } catch (FermatException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

}
