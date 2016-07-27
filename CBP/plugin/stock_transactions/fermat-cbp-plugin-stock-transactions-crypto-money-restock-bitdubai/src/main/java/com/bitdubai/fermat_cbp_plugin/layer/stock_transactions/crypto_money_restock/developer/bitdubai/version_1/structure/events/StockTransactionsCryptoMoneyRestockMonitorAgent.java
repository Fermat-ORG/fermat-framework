package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.StockTransactionsCryptoMoneyRestockPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyRestockFactory;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.utils.CryptoHoldTransactionParametersWrapper;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.utils.WalletTransactionWrapper;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionManager;

import java.util.Date;
import java.util.UUID;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockMonitorAgent</code>
 * contains the logic for handling agent transactional
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCryptoMoneyRestockMonitorAgent extends FermatAgent {

    private Thread agentThread;

    private final StockTransactionsCryptoMoneyRestockPluginRoot pluginRoot;
    private final StockTransactionCryptoMoneyRestockManager stockTransactionCryptoMoneyRestockManager;
    private final CryptoBrokerWalletManager cryptoBrokerWalletManager;
    private final CryptoHoldTransactionManager cryptoHoldTransactionManager;
    private final StockTransactionCryptoMoneyRestockFactory stockTransactionCryptoMoneyRestockFactory;
    private UUID pluginId;
    private Broadcaster broadcaster;

    public final int SLEEP_TIME = 1500;

    public StockTransactionsCryptoMoneyRestockMonitorAgent(StockTransactionsCryptoMoneyRestockPluginRoot pluginRoot,
                                                           StockTransactionCryptoMoneyRestockManager stockTransactionCryptoMoneyRestockManager,
                                                           CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                           CryptoHoldTransactionManager cryptoHoldTransactionManager,
                                                           PluginDatabaseSystem pluginDatabaseSystem,
                                                           UUID pluginId,
                                                           Broadcaster broadcaster) {

        this.pluginRoot = pluginRoot;
        this.stockTransactionCryptoMoneyRestockManager = stockTransactionCryptoMoneyRestockManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.cryptoHoldTransactionManager = cryptoHoldTransactionManager;
        this.stockTransactionCryptoMoneyRestockFactory = new StockTransactionCryptoMoneyRestockFactory(pluginDatabaseSystem, pluginId);
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

    @Override
    public void start() throws CantStartAgentException {
        this.agentThread.start();
        super.start();
    }

    @Override
    public void stop() throws CantStopAgentException {
        this.agentThread.interrupt();
        super.stop();
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
            for (CryptoMoneyTransaction cryptoMoneyTransaction : stockTransactionCryptoMoneyRestockFactory.getCryptoMoneyTransactionList(null)) {

                switch (cryptoMoneyTransaction.getTransactionStatus()) {

                    case INIT_TRANSACTION:

                        //Try to hold the funds in the crypto wallet
                        CryptoHoldTransactionParametersWrapper cryptoTransactionParametersWrapper = new CryptoHoldTransactionParametersWrapper(cryptoMoneyTransaction.getTransactionId(), cryptoMoneyTransaction.getCryptoCurrency(), cryptoMoneyTransaction.getCryWalletPublicKey(), cryptoMoneyTransaction.getActorPublicKey(), cryptoMoneyTransaction.getAmount(), cryptoMoneyTransaction.getMemo(), pluginId.toString(), cryptoMoneyTransaction.getBlockchainNetworkType(), cryptoMoneyTransaction.getFee(), cryptoMoneyTransaction.getFeeOrigin());
                        cryptoHoldTransactionManager.createCryptoHoldTransaction(cryptoTransactionParametersWrapper);

                        //Set status to IN_HOLD
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_HOLD);
                        break;

                    case IN_HOLD:
                        //Get the status of the hold transaction from the cash wallet
                        CryptoTransactionStatus holdTransactionStatus = cryptoHoldTransactionManager.getCryptoHoldTransactionStatus(cryptoMoneyTransaction.getTransactionId());

                        if (CryptoTransactionStatus.CONFIRMED.equals(holdTransactionStatus)) {
                            cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_WALLET);
                        }

                        //If REJECTED, set status to REJECTED
                        else if (CryptoTransactionStatus.REJECTED.equals(holdTransactionStatus)) {
                            //Send error broadcast to Stock Management Fragment
                            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW_ERROR);
                            cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.REJECTED);
                        }
                        break;

                    case IN_WALLET:
                        //Hold was CONFIRMED, do restock broker wallet
                        cryptoMoneyTransaction.setCbpWalletPublicKey("walletPublicKeyTest"); //TODO:Solo para testear
                        final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cryptoMoneyTransaction.getCbpWalletPublicKey());
                        final StockBalance stockBalance = cryptoBrokerWallet.getStockBalance();

                        WalletTransactionWrapper walletTransactionRecordBook = new WalletTransactionWrapper(UUID.randomUUID(), cryptoMoneyTransaction.getCryptoCurrency(), BalanceType.BOOK, TransactionType.CREDIT, MoneyType.CRYPTO, cryptoMoneyTransaction.getCbpWalletPublicKey(), cryptoMoneyTransaction.getActorPublicKey(), cryptoMoneyTransaction.getAmount(), new Date().getTime() / 1000, cryptoMoneyTransaction.getConcept(), cryptoMoneyTransaction.getPriceReference(), cryptoMoneyTransaction.getOriginTransaction(), cryptoMoneyTransaction.getOriginTransactionId(), false);
                        WalletTransactionWrapper walletTransactionRecordAvailable = new WalletTransactionWrapper(UUID.randomUUID(), cryptoMoneyTransaction.getCryptoCurrency(), BalanceType.AVAILABLE, TransactionType.CREDIT, MoneyType.CRYPTO, cryptoMoneyTransaction.getCbpWalletPublicKey(), cryptoMoneyTransaction.getActorPublicKey(), cryptoMoneyTransaction.getAmount(), new Date().getTime() / 1000, cryptoMoneyTransaction.getConcept(), cryptoMoneyTransaction.getPriceReference(), cryptoMoneyTransaction.getOriginTransaction(), cryptoMoneyTransaction.getOriginTransactionId(), false);
                        stockBalance.credit(walletTransactionRecordBook, BalanceType.BOOK);
                        stockBalance.credit(walletTransactionRecordAvailable, BalanceType.AVAILABLE);

                        //Set status to IN_EXECUTION
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.IN_EXECUTION);
                        break;

                    case IN_EXECUTION:
                        //Send broadcast to Stock Management Fragment
                        broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DESTOCK_OR_RESTOCK_UPDATE_VIEW);

                        //Set status to COMPLETED
                        cryptoMoneyTransaction.setTransactionStatus(TransactionStatusRestockDestock.COMPLETED);
                        break;
                }

                //Save the current cashMoneyTransaction status
                stockTransactionCryptoMoneyRestockFactory.saveCryptoMoneyRestockTransactionData(cryptoMoneyTransaction);

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
        } catch (MissingCryptoMoneyRestockDataException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantCreateHoldTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetHoldTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
