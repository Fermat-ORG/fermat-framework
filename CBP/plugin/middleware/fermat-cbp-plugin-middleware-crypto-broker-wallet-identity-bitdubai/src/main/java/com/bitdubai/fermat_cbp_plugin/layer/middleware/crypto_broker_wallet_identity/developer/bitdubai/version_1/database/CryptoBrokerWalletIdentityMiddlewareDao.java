package com.bitdubai.fermat_cbp_plugin.layer.middleware.crypto_broker_wallet_identity.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.middleware.crypto_broker_wallet_identity.exceptions.CantCreateCryptoBrokerWalletIdentityException;
import com.bitdubai.fermat_cbp_api.layer.middleware.crypto_broker_wallet_identity.exceptions.CantDeleteCryptoBrokerWalletIdentityException;
import com.bitdubai.fermat_cbp_api.layer.middleware.crypto_broker_wallet_identity.exceptions.CantUpdateCryptoBrokerWalletIdentityException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.crypto_broker_wallet_identity.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerWalletIdentityMiddlewareDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.crypto_broker_wallet_identity.developer.bitdubai.version_1.structure.CryptoBrokerWalletIdentityMiddleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by angel on 28/9/15.
 */
public class CryptoBrokerWalletIdentityMiddlewareDao {

    private Database             database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CryptoBrokerWalletIdentityMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCryptoBrokerWalletIdentityMiddlewareDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCryptoBrokerWalletIdentityMiddlewareDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCryptoBrokerWalletIdentityMiddlewareDaoException(CantInitializeCryptoBrokerWalletIdentityMiddlewareDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void createCryptoBrokerWalletIdentity(CryptoBrokerIdentity identity, UUID Wallet) throws CantCreateCryptoBrokerWalletIdentityException{
        try {
            DatabaseTable walletIdentityTable = this.database.getTable(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = walletIdentityTable.getEmptyRecord();
            loadRecordAsNew(recordToInsert, identity, Wallet);
            walletIdentityTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantCreateCryptoBrokerWalletIdentityException("An exception happened",e,"","");
        }
    }

    public void deleteCryptoBrokerWalletIdentity(CryptoBrokerIdentity identity, UUID Wallet) throws CantDeleteCryptoBrokerWalletIdentityException{
        try {
            DatabaseTable walletIdentityTable = this.database.getTable(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = walletIdentityTable.getEmptyRecord();
            loadRecordToDelete(recordToDelete, identity, Wallet);
            walletIdentityTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCryptoBrokerWalletIdentityException("An exception happened",e,"","");
        }
    }

    public void updateIdentityToWalletCryptoBrokerWalletIdentity(CryptoBrokerIdentity currentIdentity, UUID Wallet, CryptoBrokerIdentity newIdentity) throws CantUpdateCryptoBrokerWalletIdentityException {
        try {
            DatabaseTable walletIdentityTable = this.database.getTable(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
            walletIdentityTable.setStringFilter(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_ACTOR_PUBLIC_KEY_COLUMN_NAME, currentIdentity.getPublicKey(), DatabaseFilterType.EQUAL);

            DatabaseTableRecord recordToUpdate   = walletIdentityTable.getEmptyRecord();
            loadRecordToUpdate(recordToUpdate, newIdentity, Wallet);
            walletIdentityTable.deleteRecord(recordToUpdate);

        } catch (CantDeleteRecordException e) {
            throw new CantUpdateCryptoBrokerWalletIdentityException("An exception happened",e,"","");
        }
    }

    public void updateWalletToIdentityCryptoBrokerWalletIdentity(CryptoBrokerIdentity identity, UUID currentWallet, UUID newWallet) throws CantUpdateCryptoBrokerWalletIdentityException{
        try {
            DatabaseTable walletIdentityTable = this.database.getTable(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
            walletIdentityTable.setUUIDFilter(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_WALLET_ID_COLUMN_NAME, currentWallet, DatabaseFilterType.EQUAL);

            DatabaseTableRecord recordToUpdate   = walletIdentityTable.getEmptyRecord();
            loadRecordToUpdate(recordToUpdate, identity, newWallet);
            walletIdentityTable.deleteRecord(recordToUpdate);

        } catch (CantDeleteRecordException e) {
            throw new CantUpdateCryptoBrokerWalletIdentityException("An exception happened",e,"","");
        }
    }

    public List<CryptoBrokerWalletIdentityMiddleware> getAllCryptoBrokerWalletIdentity() throws CantLoadTableToMemoryException {
        Map<String, CryptoBrokerWalletIdentityMiddleware> relations = new HashMap<String, CryptoBrokerWalletIdentityMiddleware>();
        String publickey = null;
        UUID wallet = null;

        DatabaseTable identityTable = this.database.getTable(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        for (DatabaseTableRecord record : records) {
            publickey = record.getStringValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_ACTOR_PUBLIC_KEY_COLUMN_NAME);
            wallet = record.getUUIDValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_WALLET_ID_COLUMN_NAME);

            if(relations.containsKey(publickey)) {
                relations.get(publickey).addWallet(wallet);
            }else{
                relations.put(publickey, new CryptoBrokerWalletIdentityMiddleware(publickey, wallet));
            }
        }

        List<CryptoBrokerWalletIdentityMiddleware> wallets = new ArrayList<>();

        Iterator it = relations.keySet().iterator();
        while(it.hasNext()){
            String key = (String) it.next();
            wallets.add(relations.get(key));
        }

        return wallets;
    }

    public List<String> getCryptoBrokerWalletIdentityAssociatedWithAWallet(UUID Wallet) throws CantLoadTableToMemoryException {
        List<String> publicKeys = new ArrayList<>();

        DatabaseTable identityTable = this.database.getTable(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
        identityTable.setUUIDFilter(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_WALLET_ID_COLUMN_NAME, Wallet, DatabaseFilterType.EQUAL);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        for (DatabaseTableRecord record : records) {
            publicKeys.add(record.getStringValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_ACTOR_PUBLIC_KEY_COLUMN_NAME));
        }
        return publicKeys;
    }

    public List<UUID> getAllWalletAssociatedWithAIdentity(CryptoBrokerIdentity identity) throws CantLoadTableToMemoryException {
        List<UUID> wallets = new ArrayList<>();
        DatabaseTable identityTable = this.database.getTable(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
        identityTable.setStringFilter(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_ACTOR_PUBLIC_KEY_COLUMN_NAME, identity.getPublicKey(), DatabaseFilterType.EQUAL);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        for (DatabaseTableRecord record : records) {
            wallets.add(record.getUUIDValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_WALLET_ID_COLUMN_NAME));
        }

        return wallets;
    }

    /*
        Methods Private
     */

    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord, CryptoBrokerIdentity identity, UUID Wallet) {
        databaseTableRecord.setStringValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_ACTOR_PUBLIC_KEY_COLUMN_NAME, identity.getPublicKey());
        databaseTableRecord.setUUIDValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_WALLET_ID_COLUMN_NAME, Wallet);
    }

    private void loadRecordToDelete(DatabaseTableRecord databaseTableRecord, CryptoBrokerIdentity identity, UUID Wallet) {
        databaseTableRecord.setStringValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_ACTOR_PUBLIC_KEY_COLUMN_NAME, identity.getPublicKey());
        databaseTableRecord.setUUIDValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_WALLET_ID_COLUMN_NAME, Wallet);
    }

    private void loadRecordToUpdate(DatabaseTableRecord databaseTableRecord, CryptoBrokerIdentity identity, UUID Wallet) {
        databaseTableRecord.setStringValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_ACTOR_PUBLIC_KEY_COLUMN_NAME, identity.getPublicKey());
        databaseTableRecord.setUUIDValue(CryptoBrokerWalletIdentityMiddlewareDatabaseConstants.WALLET_IDENTITY_WALLET_ID_COLUMN_NAME, Wallet);
    }

}
