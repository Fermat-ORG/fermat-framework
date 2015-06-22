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
import com.bitdubai.fermat_cry_api.layer.definition.DepthInBlocksThreshold;

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
        /**
         * I save this transaction in the database
         */
            dbActions.saveIncomingTransaction(tx.getHashAsString());
    }

    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
//todo completar
    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        TransactionConfidence txConfidence = tx.getConfidence();
        /**
         * Confidence type building is that it is in the main blockchain and increasing
         */
        if (txConfidence.getConfidenceType() == TransactionConfidence.ConfidenceType.BUILDING){
            if (DepthInBlocksThreshold.DEPTH == txConfidence.getDepthInBlocks()){
                /**
                 * The transaction height in the blockchain has reached our threshold.
                 */

                dbActions.updateCryptoTransactionStatus(tx.getHashAsString(), CryptoStatus.RECEIVED);
            }

            if (1 == txConfidence.getDepthInBlocks()){
                /**
                 * The transactions has one block already.
                 */

                dbActions.updateCryptoTransactionStatus(tx.getHashAsString(), CryptoStatus.CONFIRMED);
            }
        }


    }
}
