package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;

import org.bitcoinj.core.AbstractWalletEventListener;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.Wallet;

/**
 * Created by rodrigo on 11/06/15.
 */
class VaultEventListeners extends AbstractWalletEventListener implements DealsWithEvents{

    /**
     * DealsWithEvents interface member variables
     */
    EventManager eventManager;
    EventListener eventListener;
    PlatformEvent platformEvent;

    /**
     * VaultEventListeners member variables
     */
    Database database;
    CryptoVaultDatabaseActions dbActions;


    VaultEventListeners (Database database){
        this.database = database;
        dbActions = new CryptoVaultDatabaseActions(this.database);
    }

    /**
     * DealsWithEvents interface implmentation
     * @param eventManager
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        /**
         * I save this transaction in the database
         */
            dbActions.saveIncomingTransaction(tx.getHashAsString());

        /**
         * once saved, I notify the platform event
         */
        //todo add platforn event of coins received event
    }

    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {

    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        TransactionConfidence txConfidence = tx.getConfidence();


    }
}
