package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoIrreversibleEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoOnBlockchainEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoOnCryptoNetworkEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReversedOnBlockchainEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReversedOnCryptoNetworkEvent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;

import org.bitcoinj.core.AbstractWalletEventListener;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;

import java.util.UUID;

/**
 * Created by rodrigo on 11/06/15.
 */
public class VaultEventListeners extends AbstractWalletEventListener implements DealsWithErrors, DealsWithEvents, DealsWithLogger {

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
    public VaultEventListeners(Database database, ErrorManager errorManager, EventManager eventManager, LogManager logManager){
        this.database = database;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.logManager = logManager;

        dbActions = new CryptoVaultDatabaseActions(this.database, errorManager, eventManager);
    }

    //TODO Franklin, aquifalta la gestion de excepciones genericas
    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoVault information: Ney money received!!! New balance: " + newBalance.getValue(), null, null);
        /**
         * I save this transaction in the database
         */
        try {
            dbActions.setVault(wallet);
            UUID txId = UUID.randomUUID();
            dbActions.saveIncomingTransaction(txId.toString(), tx.getHashAsString());

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

            dbActions.updateCryptoTransactionStatus(txId.toString(), tx.getHashAsString(), cryptoStatus);
        } catch (CantExecuteQueryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    //TODO Franklin, aqui falta la gestion de excepciones genericas, usa una invocacion al errorManager
    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "Money sent.", "Prev Balance: " + prevBalance.getValue() + " New Balance:" + newBalance.getValue(), "Transaction: " + tx.toString());
    }

    //TODO Franklin, aqui falta la gestion de excepciones genericas
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
            /**
             * now I raise the event
             */
            if (cryptoStatus == CryptoStatus.ON_CRYPTO_NETWORK) {
                /**
                 * I create the transaction in the DB and raise the event
                 */
                PlatformEvent eventON_CRYPTO_NETWORK = new IncomingCryptoOnCryptoNetworkEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK);
                eventON_CRYPTO_NETWORK.setSource(EventSource.CRYPTO_VAULT);
                eventManager.raiseEvent(eventON_CRYPTO_NETWORK);
            }

            if (cryptoStatus == CryptoStatus.ON_BLOCKCHAIN) {
                /**
                 * I create the transaction in the DB and raise the event
                 */
                dbActions.insertNewTransactionWithNewConfidence(tx.getHashAsString(), cryptoStatus);
                PlatformEvent eventINCOMING_CRYPTO_ON_BLOCKCHAIN = new IncomingCryptoOnBlockchainEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN);
                eventINCOMING_CRYPTO_ON_BLOCKCHAIN.setSource(EventSource.CRYPTO_VAULT);
                eventManager.raiseEvent(eventINCOMING_CRYPTO_ON_BLOCKCHAIN);
            }

            if (cryptoStatus == CryptoStatus.IRREVERSIBLE)
            /**
             * I don't create a new transaction for this CryptoStatus
             */ {
                PlatformEvent eventINCOMING_CRYPTO_IRREVERSIBLE = new IncomingCryptoIrreversibleEvent(EventType.INCOMING_CRYPTO_IRREVERSIBLE);
                eventINCOMING_CRYPTO_IRREVERSIBLE.setSource(EventSource.CRYPTO_VAULT);
                eventManager.raiseEvent(eventINCOMING_CRYPTO_IRREVERSIBLE);
                }

            if (cryptoStatus == CryptoStatus.REVERSED_ON_CRYPTO_NETWORK)
            /**
             * I create the transaction in the DB and raise the event
             */{
                dbActions.insertNewTransactionWithNewConfidence(tx.getHashAsString(), cryptoStatus);
                PlatformEvent eventREVERSED_ON_CRYPTO_NETWORK = new IncomingCryptoReversedOnCryptoNetworkEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK);
                eventREVERSED_ON_CRYPTO_NETWORK.setSource(EventSource.CRYPTO_VAULT);
                eventManager.raiseEvent(eventREVERSED_ON_CRYPTO_NETWORK);
                }


            if (cryptoStatus == CryptoStatus.REVERSED_ON_BLOCKCHAIN)
            /**
             * I create the transaction in the DB and raise the event
             */{
                dbActions.insertNewTransactionWithNewConfidence(tx.getHashAsString(), cryptoStatus);

                PlatformEvent eventREVERSED_ON_BLOCKCHAIN = new IncomingCryptoReversedOnBlockchainEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN);
                eventREVERSED_ON_BLOCKCHAIN.setSource(EventSource.CRYPTO_VAULT);
                eventManager.raiseEvent(eventREVERSED_ON_BLOCKCHAIN);
                }



        } catch (CantExecuteQueryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }
}
