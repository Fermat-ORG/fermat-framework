package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReceivedEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReceptionConfirmedEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.IncomingCryptoReversedEvent;
import com.bitdubai.fermat_cry_api.layer.definition.DepthInBlocksThreshold;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;

import org.bitcoinj.core.AbstractWalletEventListener;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.Wallet;

/**
 * Created by rodrigo on 11/06/15.
 */
class VaultEventListeners extends AbstractWalletEventListener implements DealsWithErrors, DealsWithEvents{

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
        System.out.println("CryptoVault information: Ney money received!!! Incoming transaction with " + tx.getValueSentToMe(wallet).getValue() + ". New balance: " + newBalance.getValue());
        /**
         * I save this transaction in the database
         */
        try {
            dbActions.setVault(wallet);
            dbActions.saveIncomingTransaction(tx.getHashAsString());
        } catch (CantExecuteQueryException e) {
            //todo better handle this
            e.printStackTrace();
        }

    }

    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
//todo completar
    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        TransactionConfidenceCalculator transactionConfidenceCalculator = new TransactionConfidenceCalculator(tx, wallet);
        CryptoStatus cryptoStatus;
        try {
            cryptoStatus = transactionConfidenceCalculator.getCryptoStatus();
        } catch (CantCalculateTransactionConfidenceException e) {
            cryptoStatus = CryptoStatus.IDENTIFIED;
        }

        try {
            /**
             * I update the record with the new cryptoStatus
             */
            dbActions.updateCryptoTransactionStatus(tx.getHashAsString(), cryptoStatus);

            /**
             * now I raise the event
             */
            if (cryptoStatus == CryptoStatus.RECEIVED) {
                eventManager.raiseEvent(new IncomingCryptoReceivedEvent(EventType.INCOMING_CRYPTO_RECEIVED));
            }

            if (cryptoStatus == CryptoStatus.CONFIRMED)
                eventManager.raiseEvent(new IncomingCryptoReceptionConfirmedEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED));

            if (cryptoStatus == CryptoStatus.REVERSED)
                eventManager.raiseEvent(new IncomingCryptoReversedEvent(EventType.INCOMING_CRYPTO_REVERSED));



        } catch (CantExecuteQueryException e) {
            System.err.println("CryptoVault Error: new cryptoStatus " + cryptoStatus.getCode() + " could not be saved for transaction " + tx.getHash().toString());
        }

    }
}
