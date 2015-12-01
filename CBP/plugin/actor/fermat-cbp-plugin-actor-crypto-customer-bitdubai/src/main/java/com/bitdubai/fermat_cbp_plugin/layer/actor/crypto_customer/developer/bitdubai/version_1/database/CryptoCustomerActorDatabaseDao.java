package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerIdentityWalletRelationshipRecord;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantGetCryptoCustomerActorProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantUpdateConnectionRegisterCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorImpl;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoCryptoCustomerIdentityWalletRelationshipRecordImpl;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerIdentityWalletRelationshipException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 15.11.15.
 */
public class CryptoCustomerActorDatabaseDao {
    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    public CryptoCustomerActorDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    Database database;

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCryptoCustomerActorDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CryptoCustomerActorDatabaseFactory databaseFactory = new CryptoCustomerActorDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCryptoCustomerActorDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCryptoCustomerActorDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCryptoCustomerActorDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCryptoCustomerActorDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    //### ACTOR ###
    //CREATE ACTOR
    public CryptoCustomerActor createRegisterCryptoCustomerActor(String actorLoggedInPublicKey, String actorPublicKey, String actorPrivateKey, String actorName, byte[] actorPhoto, ConnectionState connectionState) throws CantRegisterCryptoCustomerActorException{
        try {
            UUID actorId = UUID.randomUUID();
            Date time = new Date();
            long timestamp = time.getTime();

            if (actorExists(actorPublicKey)){
                this.updateRegisterCryptoCustomerActor(actorLoggedInPublicKey, actorPublicKey, connectionState,timestamp);
            }else{

                DatabaseTable table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setUUIDValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_ACTOR_ID_COLUMN_NAME, actorId);
                record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_ACTOR_COLUMN_NAME, actorPublicKey);
                record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_IDENTITY_COLUMN_NAME, actorLoggedInPublicKey);
                record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_NAME_ACTOR_COLUMN_NAME, actorName);
                record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TIMESTAMP_COLUMN_NAME, timestamp);

                table.insertRecord(record);

                persistPrivateKey(actorPrivateKey, actorPublicKey);

                if(actorPhoto!=null) persistNewCryptoCustomerActorProfileImage(actorPublicKey, actorPhoto);
            }

        } catch (CantInsertRecordException e){
            throw new CantRegisterCryptoCustomerActorException (e.getMessage(), e, "Crypto Customer Actor, Crypto Customer Actor", "Cant create new Crypto Customer Actor, insert database problems.");
        } catch (CantUpdateConnectionRegisterCryptoCustomerActorException e){
            throw new CantRegisterCryptoCustomerActorException (e.getMessage(), e, "Crypto Customer Actor, Crypto Customer Actor", "Cant create new Crypto Customer Actor, insert database problems.");
        } catch (CantPersistPrivateKeyException e){
            throw new CantRegisterCryptoCustomerActorException (e.getMessage(), e, "Crypto Customer Actor, Crypto Customer Actor", "Cant create new Crypto Customer Actor, insert database problems.");
        } catch (CantPersistProfileImageException e){
            throw new CantRegisterCryptoCustomerActorException (e.getMessage(), e, "Crypto Customer Actor, Crypto Customer Actor", "Cant create new Crypto Customer Actor, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerActorException (e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor, Crypto Customer Actor", "Cant create new Crypto Customer Actor, unknown failure.");
        }

        return new CryptoCustomerActorImpl(actorPublicKey, actorPrivateKey, actorName);
    }

    //GET ACTOR.
    public CryptoCustomerActor getRegisterCryptoCustomerActor(String actorLoggedInPublicKey, String actorPublicKey) throws CantRegisterCryptoCustomerActorException {
        CryptoCustomerActor getActor = null;

        try {
            DatabaseTable table;
            List<DatabaseTableRecord> record;
            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if Actor table exists", "Crypto Customer Actor", "");
            }
            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_ACTOR_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_IDENTITY_COLUMN_NAME, actorLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCryptoCustomerActorException("The number of records is 0 ", null, "actorPublicKey: " + actorPublicKey.toString() + "", "");

            for (DatabaseTableRecord records : record) {
                byte[] image;
                try {
                    image = getIntraUserProfileImagePrivateKey(records.getStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_ACTOR_COLUMN_NAME));
                } catch(FileNotFoundException e) {
                    image = new  byte[0];
                }
                String actoyPublicKey   = records.getStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_ACTOR_COLUMN_NAME);
                String actorName        = records.getStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_NAME_ACTOR_COLUMN_NAME);
                getActor = new CryptoCustomerActorImpl(actoyPublicKey,"",actorName, image);
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerActorException(em.getMessage(), em, "Crypto Customer Actor It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerActorException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor It Already Exists", "unknown failure.");
        }

        return getActor;
    }

    //### RELATIONSHIP ###
    //CREATE RELATIONSHIP
    public CryptoCustomerIdentityWalletRelationshipRecord createRegisterCustomerIdentityWalletRelationship(String walletPublicKey, String identityPublicKey) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        UUID relationshipId = UUID.randomUUID();
        try {
            if (relationshipExists(walletPublicKey, identityPublicKey))
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("Cant create new Customer Identity Wallet Relationship, It exists.", "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, Relationship exists.");


            DatabaseTable table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setUUIDValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId);
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME, walletPublicKey);
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_IDENTITY_COLUMN_NAME, identityPublicKey);
            table.insertRecord(record);

        } catch (CantInsertRecordException e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), e, "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, unknown failure.");
        }

        return new CryptoCryptoCustomerIdentityWalletRelationshipRecordImpl(relationshipId, walletPublicKey, identityPublicKey);
    }

    //UPDATE RELATIONSHIP
    public CryptoCustomerIdentityWalletRelationshipRecord updateRegisterCustomerIdentityWalletRelationship(UUID relationshipId, String walletPublicKey, String identityPublicKey) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        try {
            if (!relationshipExists(relationshipId))
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("Cant Update Customer Identity Wallet Relationship, not exists.", "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant Update Customer Identity Wallet Relationship, not exists");

            DatabaseTable table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            table.setUUIDFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME, walletPublicKey);
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_IDENTITY_COLUMN_NAME, identityPublicKey);
            table.updateRecord(record);

        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), e, "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant update Customer Identity Wallet Relationship, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant update Customer Identity Wallet Relationship, unknown failure.");
        }

        return new CryptoCryptoCustomerIdentityWalletRelationshipRecordImpl(relationshipId, walletPublicKey, identityPublicKey);
    }

    //DELETE RELATIONSHIP
    public void deleteRegisterCustomerIdentityWalletRelationship(UUID relationshipId) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        try {
            if (!relationshipExists(relationshipId))
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("Cant Delete Customer Identity Wallet Relationship, not exists.", "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant Delete Customer Identity Wallet Relationship, not exists");

            DatabaseTable table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setUUIDValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId);
            table.deleteRecord(record);

        } catch (CantDeleteRecordException e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), e, "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant delete Customer Identity Wallet Relationship, delete database problems.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant delete Customer Identity Wallet Relationship, unknown failure.");
        }
    }

    //GET RELATIONSHIP COLLECTION
    public Collection<CryptoCustomerIdentityWalletRelationshipRecord> getAllRegisterCustomerIdentityWalletRelationships() throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        Collection<CryptoCustomerIdentityWalletRelationshipRecord> getCollections = new ArrayList<CryptoCustomerIdentityWalletRelationshipRecord>();

        try {
            DatabaseTable table;
            List<DatabaseTableRecord> record;

            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship exists", "Crypto Customer Actor", "");
            }
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("The number of records with a primary key is different thatn one ", null, "The ");
            for (DatabaseTableRecord records : record) {
                getCollections.add(getCustomerIdentityWalletRelationshipFromRecord(records));
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(em.getMessage(), em, "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "unknown failure.");
        }

        return getCollections;
    }

    //GET RELATIONSHIP ID
    public CryptoCustomerIdentityWalletRelationshipRecord getAllRegisterCustomerIdentityWalletRelationships(UUID relationshipId) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        CryptoCustomerIdentityWalletRelationshipRecord getRelationship = null;

        try {
            DatabaseTable table;
            List<DatabaseTableRecord> record;
            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship exists", "Crypto Customer Actor", "");
            }
            table.setUUIDFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("The number of records is 0 ", null, "The id is: " + relationshipId.toString(), "");

            for (DatabaseTableRecord records : record) {
                getRelationship = getCustomerIdentityWalletRelationshipFromRecord(records);
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(em.getMessage(), em, "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "unknown failure.");
        }

        return getRelationship;
    }

    //GET RELATIONSHIP IDENTITY
    public CryptoCustomerIdentityWalletRelationshipRecord getAllRegisterCustomerIdentityWalletRelationshipsByIdentity(String identityPublicKey) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        CryptoCustomerIdentityWalletRelationshipRecord getRelationship = null;

        try {
            DatabaseTable table;
            List<DatabaseTableRecord> record;
            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship exists", "Crypto Customer Actor", "");
            }
            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_IDENTITY_COLUMN_NAME, identityPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("The number of records 0 ", null, "The identityPublickey is: " + identityPublicKey.toString(), "");

            for (DatabaseTableRecord records : record) {
                getRelationship = getCustomerIdentityWalletRelationshipFromRecord(records);
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(em.getMessage(), em, "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "unknown failure.");
        }

        return getRelationship;
    }

    //GET RELATIONSHIP WALLET
    public CryptoCustomerIdentityWalletRelationshipRecord getAllRegisterCustomerIdentityWalletRelationshipsByWallet(String walletPublicKey) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        CryptoCustomerIdentityWalletRelationshipRecord getRelationship = null;

        try {
            DatabaseTable table;
            List<DatabaseTableRecord> record;
            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship exists", "Crypto Customer Actor", "");
            }
            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("The number of records 0 ", null, "The walletPublicKey is: " + walletPublicKey.toString(), "");

            for (DatabaseTableRecord records : record) {
                getRelationship = getCustomerIdentityWalletRelationshipFromRecord(records);
            }

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(em.getMessage(), em, "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "unknown failure.");
        }

        return getRelationship;
    }

    //OTHERS
    private void updateRegisterCryptoCustomerActor(String actorLoggedInPublicKey, String actorPublicKey, ConnectionState connectionState, long timestamp) throws CantUpdateConnectionRegisterCryptoCustomerActorException{
        try {
            DatabaseTable table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant get crypto customer actor, table not found.", "Crypto Customer Actor", "");

            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_ACTOR_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_IDENTITY_COLUMN_NAME, actorLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TIMESTAMP_COLUMN_NAME, timestamp);
                table.updateRecord(record);
            }
        } catch (CantUpdateRecordException e){
            throw new CantUpdateConnectionRegisterCryptoCustomerActorException (e.getMessage(), e, "Crypto Customer Actor, Crypto Customer Actor", "Cant update Crypto Customer Actor, update database problems.");
        } catch (Exception e) {
            throw new CantUpdateConnectionRegisterCryptoCustomerActorException (e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor, Crypto Customer Actor", "Cant update Crypto Customer Actor, unknown failure.");
        }
    }

    private boolean relationshipExists(String walletPublicKey, String identityPublicKey) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        try {
            DatabaseTable table;
            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship table exists", "Crypto Customer Actor", "");
            }
            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME, identityPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(em.getMessage(), em, "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "unknown failure.");
        }
    }

    private boolean relationshipExists(UUID relationshipId) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        try {
            DatabaseTable table;
            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship tablet exists", "Crypto Customer Actor", "");
            }
            table.setUUIDFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(em.getMessage(), em, "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "unknown failure.");
        }
    }

    private boolean actorExists(String actorPublicKey) throws CantRegisterCryptoCustomerActorException{
        try {
            DatabaseTable table;
            table = this.database.getTable (CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if actor table exists", "Crypto Customer Actor", "");
            }
            table.setStringFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_ACTOR_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords ().size () > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerActorException (em.getMessage(), em, "Crypto Customer Actor It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerActorException (e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor It Already Exists", "unknown failure.");
        }
    }

    private void persistPrivateKey(String privateKey, String publicKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(
                    pluginId,
                    DeviceDirectory.LOCAL_USERS.getName() + "/" + CryptoCustomerActorPluginRoot.ACTOR_CRYPTO_CUSTOMER_PRIVATE_KEYS_DIRECTORY_NAME,
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(privateKey);
            file.persistToMedia();
        } catch (CantPersistFileException | CantCreateFileException e) {
            throw new CantPersistPrivateKeyException(CantPersistPrivateKeyException.DEFAULT_MESSAGE, e, "Error creating or persisting file.", null);
        } catch (Exception e) {
            throw new CantPersistPrivateKeyException(CantPersistPrivateKeyException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private void  persistNewCryptoCustomerActorProfileImage(String publicKey,byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                DeviceDirectory.LOCAL_USERS.getName(),
                CryptoCustomerActorPluginRoot.ACTOR_CRYPTO_CUSTOMER_PROFILE_IMAGE_DIRECTORY_NAME + "_" + publicKey,
                FilePrivacy.PRIVATE,
                FileLifeSpan.PERMANENT
            );
            file.setContent(profileImage);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw  new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ",FermatException.wrapException(e),"", "");
        }
    }

    private byte[] getIntraUserProfileImagePrivateKey(final String publicKey) throws CantGetCryptoCustomerActorProfileImageException,FileNotFoundException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                DeviceDirectory.LOCAL_USERS.getName(),
                CryptoCustomerActorPluginRoot.ACTOR_CRYPTO_CUSTOMER_PROFILE_IMAGE_DIRECTORY_NAME + "_" + publicKey,
                FilePrivacy.PRIVATE,
                FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            profileImage = file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantGetCryptoCustomerActorProfileImageException("CAN'T GET PROFILE IMAGE ", e, "Error persist file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new FileNotFoundException(e, "", null);
        } catch (Exception e) {
            throw new CantGetCryptoCustomerActorProfileImageException("CAN'T GET PROFILE IMAGE ",FermatException.wrapException(e),"", "");
        }
        return profileImage;
    }

    private CryptoCustomerIdentityWalletRelationshipRecord getCustomerIdentityWalletRelationshipFromRecord(DatabaseTableRecord record){

        UUID relationshipId = record.getUUIDValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME);
        String walletPublicKey = record.getStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME);
        String identityPublicKey = record.getStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME);

        return new CryptoCryptoCustomerIdentityWalletRelationshipRecordImpl(relationshipId, walletPublicKey, identityPublicKey);
    }
}
