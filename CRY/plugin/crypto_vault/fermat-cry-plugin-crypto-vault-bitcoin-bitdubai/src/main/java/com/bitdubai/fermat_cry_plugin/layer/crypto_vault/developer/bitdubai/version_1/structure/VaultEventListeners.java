package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoIrreversibleEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoOnBlockchainEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoOnCryptoNetworkEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReceivedEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReceptionConfirmedEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReversedEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReversedOnBlockchainEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReversedOnCryptoNetworkEvent;
import com.bitdubai.fermat_cry_api.layer.definition.DepthInBlocksThreshold;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;

import org.bitcoinj.core.AbstractWalletEventListener;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerEventListener;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.Wallet;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 11/06/15.
 */
class VaultEventListeners extends AbstractWalletEventListener implements DealsWithErrors, DealsWithEvents, DealsWithLogger {

    /**
     * VaultEventListeners member variables
     */
    Database database;
    CryptoVaultDatabaseActions dbActions;

    /**
     * DealsWithErrors interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithEvents interface member variables
     */
    EventManager eventManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    LogLevel logLevel;
    final String fullPath = this.getClass().getCanonicalName().toString();

    /**
     * DealsWithEvents interface implmentation
     * @param eventManager
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithErrors interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithLogger interface implementation
     * @param logManager
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * Constructor
     * @param database
     */
    VaultEventListeners (Database database, ErrorManager errorManager, EventManager eventManager){
        this.database = database;
        this.errorManager = errorManager;
        this.eventManager = eventManager;

        dbActions = new CryptoVaultDatabaseActions(this.database, errorManager, eventManager);
    }


    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoVault information: Ney money received!!! Incoming transaction with " + tx.getValueSentToMe(wallet).getValue() + ". New balance: " + newBalance.getValue(), null, null);
        /**
         * I save this transaction in the database
         */
        try {
            dbActions.setVault(wallet);
            dbActions.saveIncomingTransaction(tx.getHashAsString());

            /**
             * once I save it I will check the confidence level of the transaction.
             */
            TransactionConfidenceCalculator transactionConfidenceCalculator = new TransactionConfidenceCalculator(tx, wallet);
            CryptoStatus cryptoStatus;

            try {
                cryptoStatus = transactionConfidenceCalculator.getCryptoStatus();
            } catch (CantCalculateTransactionConfidenceException e) {
                cryptoStatus = CryptoStatus.ON_CRYPTO_NETWORK;
            }

            dbActions.updateCryptoTransactionStatus(tx.getHashAsString(), cryptoStatus);
        } catch (CantExecuteQueryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Money sent.", "Prev Balance: " + prevBalance.getValue() + " New Balance:" + newBalance.getValue(), "Transaction: " + tx.toString());
    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(fullPath), "Transaction confidence change detected!", "Transaction confidence changed. Transaction: " + tx , "Transaction confidence changed. Transaction: " + tx);

        TransactionConfidenceCalculator transactionConfidenceCalculator = new TransactionConfidenceCalculator(tx, wallet);
        CryptoStatus cryptoStatus;
        try {
            cryptoStatus = transactionConfidenceCalculator.getCryptoStatus();
        } catch (CantCalculateTransactionConfidenceException e) {
            cryptoStatus = CryptoStatus.ON_CRYPTO_NETWORK;
        }

        try {
            /**
             * I will insert a new transaction with the same tx_hash to let other plugins to get it.
             */
            //dbActions.updateCryptoTransactionStatus(tx.getHashAsString(), cryptoStatus);
            dbActions.insertNewTransactionWithNewConfidence(tx.getHashAsString(), cryptoStatus);

            /**
             * now I raise the event
             */
            if (cryptoStatus == CryptoStatus.ON_CRYPTO_NETWORK) {
                eventManager.raiseEvent(new IncomingCryptoOnCryptoNetworkEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK));
            }

            if (cryptoStatus == CryptoStatus.ON_BLOCKCHAIN) {
                eventManager.raiseEvent(new IncomingCryptoOnBlockchainEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN));
            }

            if (cryptoStatus == CryptoStatus.IRREVERSIBLE)
                eventManager.raiseEvent(new IncomingCryptoIrreversibleEvent(EventType.INCOMING_CRYPTO_IRREVERSIBLE));


            if (cryptoStatus == CryptoStatus.REVERSED_ON_CRYPTO_NETWORK)
                eventManager.raiseEvent(new IncomingCryptoReversedOnCryptoNetworkEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK));

            if (cryptoStatus == CryptoStatus.REVERSED_ON_BLOCKCHAIN)
                eventManager.raiseEvent(new IncomingCryptoReversedOnBlockchainEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN));


        } catch (CantExecuteQueryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }
}
