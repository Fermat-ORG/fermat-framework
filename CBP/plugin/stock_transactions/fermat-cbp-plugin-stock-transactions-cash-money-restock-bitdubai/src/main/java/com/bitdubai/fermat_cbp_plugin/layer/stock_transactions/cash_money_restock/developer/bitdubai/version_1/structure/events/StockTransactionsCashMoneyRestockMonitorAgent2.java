package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.StockTransactionsCashMoneyRestockPluginRoot;
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

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCashMoneyRestockMonitorAgent2 extends AbstractAgent {
    private Thread agentThread;

    private final StockTransactionsCashMoneyRestockPluginRoot pluginRoot;
    private final StockTransactionCashMoneyRestockManager stockTransactionCashMoneyRestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CashHoldTransactionManager cashHoldTransactionManager;
    private final StockTransactionCashMoneyRestockFactory stockTransactionCashMoneyRestockFactory;
    private final UUID pluginId;
    private Broadcaster broadcaster;

    public StockTransactionsCashMoneyRestockMonitorAgent2(long sleepTime,
                                                          TimeUnit timeUnit,
                                                          long initDelayTime,
                                                          StockTransactionsCashMoneyRestockPluginRoot pluginRoot,
                                                          StockTransactionCashMoneyRestockManager stockTransactionCashMoneyRestockManager,
                                                          CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                          CashHoldTransactionManager cashHoldTransactionManager,
                                                          PluginDatabaseSystem pluginDatabaseSystem,
                                                          UUID pluginId,
                                                          Broadcaster broadcaster) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
        this.stockTransactionCashMoneyRestockManager = stockTransactionCashMoneyRestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cashHoldTransactionManager = cashHoldTransactionManager;
        this.stockTransactionCashMoneyRestockFactory = new StockTransactionCashMoneyRestockFactory(pluginDatabaseSystem, pluginId);
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
                new Exception("StockTransactionsCashMoneyRestockMonitorAgent Error"));
    }

    private void doTheMainTask() {
        try {

            //Get unprocessed transactions (All with status != COMPLETED)
            for (CashMoneyTransaction cashMoneyTransaction : stockTransactionCashMoneyRestockFactory.getCashMoneyTransactionList(null)) {

                switch (cashMoneyTransaction.getTransactionStatus()) {

                    case INIT_TRANSACTION:
                        //Try to hold the funds in the bank wallet
                        CashTransactionParametersWrapper cashTransactionParametersWrapper = new CashTransactionParametersWrapper(cashMoneyTransaction.getTransactionId(), cashMoneyTransaction.getFiatCurrency(), cashMoneyTransaction.getCashWalletPublicKey(), cashMoneyTransaction.getActorPublicKey(), cashMoneyTransaction.getAmount(), cashMoneyTransaction.getMemo(), pluginId.toString());
                        cashHoldTransactionManager.createCashHoldTransaction(cashTransactionParametersWrapper);

                        //Set status to IN_HOLD
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_HOLD);
                        break;

                    case IN_HOLD:
                        //Get the status of the hold transaction from the cash wallet
                        CashTransactionStatus holdTransactionStatus = cashHoldTransactionManager.getCashHoldTransactionStatus(cashMoneyTransaction.getTransactionId());


                        //If hold was CONFIRMED, set status to IN_WALLET
                        if (CashTransactionStatus.CONFIRMED.equals(holdTransactionStatus)) {
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        }

                        //If REJECTED, set status to REJECTED
                        else if (CashTransactionStatus.REJECTED.equals(holdTransactionStatus)) {
                            //Send error broadcast to Stock Management Fragment
                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW_ERROR);
                            cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                        }
                        break;

                    case IN_WALLET:
                        //Hold was CONFIRMED, do restock broker wallet
                        cashMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                        final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cashMoneyTransaction.getCbpWalletPublicKey());
                        final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(UUID.randomUUID(), cashMoneyTransaction.getFiatCurrency(), BalanceType.BOOK, TransactionType.CREDIT, MoneyType.CASH_DELIVERY, cashMoneyTransaction.getCbpWalletPublicKey(), cashMoneyTransaction.getActorPublicKey(), cashMoneyTransaction.getAmount(), new Date().getTime() / 1000, cashMoneyTransaction.getConcept(), cashMoneyTransaction.getPriceReference(), cashMoneyTransaction.getOriginTransaction(), cashMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(UUID.randomUUID(), cashMoneyTransaction.getFiatCurrency(), BalanceType.AVAILABLE, TransactionType.CREDIT, MoneyType.CASH_DELIVERY, cashMoneyTransaction.getCbpWalletPublicKey(), cashMoneyTransaction.getActorPublicKey(), cashMoneyTransaction.getAmount(), new Date().getTime() / 1000, cashMoneyTransaction.getConcept(), cashMoneyTransaction.getPriceReference(), cashMoneyTransaction.getOriginTransaction(), cashMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.credit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.credit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);

                        //Set status to IN_EXECUTION
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_EXECUTION);
                        break;

                    case IN_EXECUTION:
                        //Send broadcast to Stock Management Fragment
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW);

                        //Set status to COMPLETED
                        cashMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        break;
                }

                //Save the current cashMoneyTransaction status
                stockTransactionCashMoneyRestockFactory.saveCashMoneyRestockTransactionData(cashMoneyTransaction);

            }
        } catch (CryptoBrokerWalletNotFoundException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetStockCryptoBrokerWalletException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantAddCreditCryptoBrokerWalletException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseOperationException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingCashMoneyRestockDataException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetHoldTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCreateHoldTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

}
