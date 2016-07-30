package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.StockTransactionsCryptoMoneyDestockPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyDestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.utils.CryptoUnholdTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.interfaces.CryptoUnholdTransactionManager;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCryptoMoneyDestockMonitorAgent2 extends AbstractAgent {
    //TODO: Documentar y manejo de excepciones.
    //TODO: Manejo de Eventos

    private Thread agentThread;

    private final StockTransactionsCryptoMoneyDestockPluginRoot pluginRoot;
    private final StockTransactionCryptoMoneyDestockManager stockTransactionCryptoMoneyDestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CryptoUnholdTransactionManager cryptoUnholdTransactionManager;
    private final StockTransactionCryptoMoneyDestockFactory stockTransactionCryptoMoneyDestockFactory;
    private UUID pluginId;
    private Broadcaster broadcaster;

    public final int SLEEP_TIME = 1500;

    public StockTransactionsCryptoMoneyDestockMonitorAgent2(long sleepTime,
                                                            TimeUnit timeUnit,
                                                            long initDelayTime,
                                                            StockTransactionsCryptoMoneyDestockPluginRoot pluginRoot,
                                                            StockTransactionCryptoMoneyDestockManager stockTransactionCryptoMoneyDestockManager,
                                                            CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                            CryptoUnholdTransactionManager cryptoUnholdTransactionManager,
                                                            PluginDatabaseSystem pluginDatabaseSystem,
                                                            UUID pluginId,
                                                            Broadcaster broadcaster) {
        super(sleepTime, timeUnit, initDelayTime);
        this.pluginRoot = pluginRoot;
        this.stockTransactionCryptoMoneyDestockManager = stockTransactionCryptoMoneyDestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cryptoUnholdTransactionManager = cryptoUnholdTransactionManager;
        this.stockTransactionCryptoMoneyDestockFactory = new StockTransactionCryptoMoneyDestockFactory(pluginDatabaseSystem, pluginId);
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
                new Exception("StockTransactionsCryptoMoneyDestockMonitorAgent Error"));
    }

    private void doTheMainTask() {
        try {

            //Get unprocessed transactions (All with status != COMPLETED)
            for (CryptoMoneyTransaction cryptoMoneyTransaction : stockTransactionCryptoMoneyDestockFactory.getCryptoMoneyTransactionList(null)) {

                //Get broker wallet's balance
                cryptoMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cryptoMoneyTransaction.getCbpWalletPublicKey());
                final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                switch (cryptoMoneyTransaction.getTransactionStatus()) {

                    case INIT_TRANSACTION:
                        //Debit the broker wallet
                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(UUID.randomUUID(), cryptoMoneyTransaction.getCryptoCurrency(), BalanceType.BOOK, TransactionType.DEBIT, MoneyType.CRYPTO, cryptoMoneyTransaction.getCbpWalletPublicKey(), cryptoMoneyTransaction.getActorPublicKey(), cryptoMoneyTransaction.getAmount(), new Date().getTime() / 1000, cryptoMoneyTransaction.getConcept(), cryptoMoneyTransaction.getPriceReference(), cryptoMoneyTransaction.getOriginTransaction(), cryptoMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(UUID.randomUUID(), cryptoMoneyTransaction.getCryptoCurrency(), BalanceType.AVAILABLE, TransactionType.DEBIT, MoneyType.CRYPTO, cryptoMoneyTransaction.getCbpWalletPublicKey(), cryptoMoneyTransaction.getActorPublicKey(), cryptoMoneyTransaction.getAmount(), new Date().getTime() / 1000, cryptoMoneyTransaction.getConcept(), cryptoMoneyTransaction.getPriceReference(), cryptoMoneyTransaction.getOriginTransaction(), cryptoMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.debit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.debit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);

                        //Set status to IN_WALLET
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        break;

                    case IN_WALLET:

                        //Try to unhold the funds in the crypto wallet
                        CryptoUnholdTransactionParametersWrapper cryptoTransactionParametersWrapper = new CryptoUnholdTransactionParametersWrapper(cryptoMoneyTransaction.getTransactionId(), cryptoMoneyTransaction.getCryptoCurrency(), cryptoMoneyTransaction.getCryWalletPublicKey(), cryptoMoneyTransaction.getActorPublicKey(), cryptoMoneyTransaction.getAmount(), cryptoMoneyTransaction.getMemo(), pluginId.toString(), cryptoMoneyTransaction.getBlockchainNetworkType(), cryptoMoneyTransaction.getFee(), cryptoMoneyTransaction.getFeeOrigin());
                        cryptoUnholdTransactionManager.createCryptoUnholdTransaction(cryptoTransactionParametersWrapper);

                        //Set status to IN_UNHOLD
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_UNHOLD);
                        break;

                    case IN_UNHOLD:
                        //Get the status of the hold transaction from the crypto wallet
                        CryptoTransactionStatus cryptoTransactionStatus = cryptoUnholdTransactionManager.getCryptoUnholdTransactionStatus(cryptoMoneyTransaction.getTransactionId());

                        //If unhold was CONFIRMED, set status to COMPLETED
                        if (cryptoTransactionStatus.CONFIRMED.equals(cryptoTransactionStatus)) {

                            //Send broadcast to Stock Management Fragment
                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW);
                            cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        }

                        //If unhold was REJECTED, set status to REJECTED
                        if (cryptoTransactionStatus.REJECTED.equals(cryptoTransactionStatus)) {
                            cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                        }
                        break;

                    case REJECTED:

                        //If unhold was REJECTED, undo deposit on broker wallet
                        WalletTransactionWrapper walletTransactionRecordAvailable2 = new WalletTransactionWrapper(UUID.randomUUID(), cryptoMoneyTransaction.getCryptoCurrency(), BalanceType.AVAILABLE, TransactionType.DEBIT, MoneyType.CRYPTO, cryptoMoneyTransaction.getCbpWalletPublicKey(), cryptoMoneyTransaction.getActorPublicKey(), cryptoMoneyTransaction.getAmount(), new Date().getTime() / 1000, cryptoMoneyTransaction.getConcept(), cryptoMoneyTransaction.getPriceReference(), cryptoMoneyTransaction.getOriginTransaction(), cryptoMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordBook2 = new WalletTransactionWrapper(UUID.randomUUID(), cryptoMoneyTransaction.getCryptoCurrency(), BalanceType.BOOK, TransactionType.DEBIT, MoneyType.CRYPTO, cryptoMoneyTransaction.getCbpWalletPublicKey(), cryptoMoneyTransaction.getActorPublicKey(), cryptoMoneyTransaction.getAmount(), new Date().getTime() / 1000, cryptoMoneyTransaction.getConcept(), cryptoMoneyTransaction.getPriceReference(), cryptoMoneyTransaction.getOriginTransaction(), cryptoMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.debit(walletTransactionRecordBook2, BalanceType.BOOK);
                        stockBalance.debit(walletTransactionRecordAvailable2, BalanceType.AVAILABLE);


                        //Send error broadcast to Stock Management Fragment
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW_ERROR);

                        //Set status to COMPLETED
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        break;
                }

                //Save the current bankMoneyTransaction status
                stockTransactionCryptoMoneyDestockFactory.saveCryptoMoneyDestockTransactionData(cryptoMoneyTransaction);
            }
        } catch (FermatException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }
}
