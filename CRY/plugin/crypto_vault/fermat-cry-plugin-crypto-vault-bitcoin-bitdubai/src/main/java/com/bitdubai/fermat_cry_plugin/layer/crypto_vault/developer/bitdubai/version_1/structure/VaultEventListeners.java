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


    VaultEventListeners (Database database){
        this.database = database;
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
        DatabaseTable incomingTxTable;
        incomingTxTable = database.getTable(CryptoVaultDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
        DatabaseTableRecord incomingTxRecord =  incomingTxTable.getEmptyRecord();
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, tx.getHash().toString());
        incomingTxRecord.setStringValue(CryptoVaultDatabaseConstants.INCOMING_CRYPTO_TABLE_NOTIFICATION_STS_COLUMN_NAME, "new");
        try {
            incomingTxTable.insertRecord(incomingTxRecord);
        } catch (CantInsertRecord cantInsertRecord) {
            //todo see how I will handle this
            cantInsertRecord.printStackTrace();
        }


    }
}
