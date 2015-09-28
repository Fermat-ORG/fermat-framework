package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedCryptoStatusException;
import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;

import org.bitcoinj.core.AbstractWalletEventListener;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;

import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 *
 */
public class VaultEventListeners extends AbstractWalletEventListener implements DealsWithErrors, DealsWithEvents, DealsWithLogger {


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
    final String fullPath = this.getClass().getCanonicalName();

    /**
     * VaultEventListeners member variables
     */
    Database database;
    CryptoVaultDatabaseActions dbActions;

    /**
     * Constructor
     * @param database reference
     */
    public VaultEventListeners(Database database, ErrorManager errorManager, EventManager eventManager, LogManager logManager) {
        this.database = database;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.logManager = logManager;

        dbActions = new CryptoVaultDatabaseActions(this.database, errorManager, eventManager);
    }

    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoVault information: Ney money received!!! New balance: " + newBalance.getValue(), null, null);
        /**
         * I save this transaction in the database
         */
        try {
            dbActions.setVault(wallet);
            dbActions.saveIncomingTransaction(UUID.randomUUID(), tx.getHashAsString());
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        try {
            logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Money sent.", "Prev Balance: " + prevBalance.getValue() + " New Balance:" + newBalance.getValue(), "Transaction: " + tx.toString());
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(fullPath), "Transaction confidence change detected!", "Transaction confidence changed. Transaction: " + tx, "Transaction confidence changed. Transaction: " + tx);

        TransactionConfidenceCalculator transactionConfidenceCalculator = new TransactionConfidenceCalculator(tx, wallet);
        CryptoStatus cryptoStatus;
        try {
            cryptoStatus = transactionConfidenceCalculator.getCryptoStatus();
        } catch (CantCalculateTransactionConfidenceException e) {
            cryptoStatus = CryptoStatus.ON_CRYPTO_NETWORK;
        }
        try {
            switch (cryptoStatus){
                case ON_CRYPTO_NETWORK:
                    raiseTransactionEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK);
                    break;
                case REVERSED_ON_CRYPTO_NETWORK:
                    insertNewTransactionAndRaiseEvent(tx.getHashAsString(), cryptoStatus, EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK);
                    break;
                case ON_BLOCKCHAIN:
                    insertNewTransactionAndRaiseEvent(tx.getHashAsString(), cryptoStatus, EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN);
                    break;
                case REVERSED_ON_BLOCKCHAIN:
                    insertNewTransactionAndRaiseEvent(tx.getHashAsString(), cryptoStatus, EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN);
                    break;
                case IRREVERSIBLE:
                    raiseTransactionEvent(EventType.INCOMING_CRYPTO_IRREVERSIBLE);
                    break;
                default:
                    throw new UnexpectedCryptoStatusException(UnexpectedCryptoStatusException.DEFAULT_MESSAGE, null, "Unexpected Crypto Status detected.", "");
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    /**
     * Create the transaction in the DB and raise the event
     *
     * @param hash of the transaction
     * @param cryptoStatus which we want to save
     * @param eventType type of event i want to raise
     * @throws CantExecuteQueryException if something goes wrong when i'm trying to insert the record.
     */
    private void insertNewTransactionAndRaiseEvent(String hash, CryptoStatus cryptoStatus, EventType eventType) throws CantExecuteQueryException {
        dbActions.insertNewTransactionWithNewConfidence(hash, cryptoStatus);
        raiseTransactionEvent(eventType);
    }

    private void raiseTransactionEvent(EventType eventType) {
        FermatEvent transactionEvent = eventManager.getNewEvent(eventType);
        transactionEvent.setSource(EventSource.CRYPTO_VAULT);
        eventManager.raiseEvent(transactionEvent);
    }


    /**
     * DealsWithEvents interface implmentation
     * @param eventManager reference
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithErrors interface implementation
     * @param errorManager reference
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithLogger interface implementation
     * @param logManager reference
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }
}
