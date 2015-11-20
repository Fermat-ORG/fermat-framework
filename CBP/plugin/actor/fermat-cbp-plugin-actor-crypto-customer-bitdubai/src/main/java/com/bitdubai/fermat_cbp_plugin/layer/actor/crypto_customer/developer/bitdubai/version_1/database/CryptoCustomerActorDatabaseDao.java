package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.CustomerBrokerNegotiation;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantClosePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantDeleteCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdateCustomerIdentiyWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdatePurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantUpdateStatusPurchaseContractException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerIdentityWalletRelationshipImpl;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantRegisterCryptoCustomerIdentityWalletRelationshipException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 15.11.15.
 */
public class CryptoCustomerActorDatabaseDao {
    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public CryptoCustomerActorDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
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
    /*public CryptoCustomerActor createRegisterCryptoCustomerActor(ActorIdentity identity) throws CantRegisterCryptoCustomerActorException{
        try {
            if (actorExists(identity.getPublicKey()))
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException ("Cant create new Customer Identity Wallet Relationship, It exists.","Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, Relationship exists.");

            DatabaseTable table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME, walletPublicKey);
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_IDENTITY_COLUMN_NAME, identityPublicKey);
            table.insertRecord(record);

            return new CryptoCustomerIdentityWalletRelationshipImpl(walletPublicKey, identityPublicKey);
        } catch (CantInsertRecordException e){
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException (e.getMessage(), e, "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException (e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, unknown failure.");
        }
    }*/

    public CryptoCustomerActor getRegisterCryptoCustomer(ActorIdentity identity) throws CantRegisterCryptoCustomerActorException {
        return null;
    }

    //### RELATIONSHIP ###
    //CREATE RELATIONSHIP
    public CustomerIdentityWalletRelationship createRegisterCustomerIdentityWalletRelationship(String walletPublicKey, String identityPublicKey) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        try {
            if (relationshipExists(walletPublicKey, identityPublicKey))
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("Cant create new Customer Identity Wallet Relationship, It exists.", "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, Relationship exists.");

            DatabaseTable table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME, walletPublicKey);
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_IDENTITY_COLUMN_NAME, identityPublicKey);
            table.insertRecord(record);

            return new CryptoCustomerIdentityWalletRelationshipImpl(walletPublicKey, identityPublicKey);
        } catch (CantInsertRecordException e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), e, "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant create new Customer Identity Wallet Relationship, unknown failure.");
        }
    }

    //UPDATE RELATIONSHIP
    public CustomerIdentityWalletRelationship updateRegisterCustomerIdentityWalletRelationship(UUID relationshipId, String walletPublicKey, String identityPublicKey) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        try {
            if (!relationshipExists(relationshipId))
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("Cant Update Customer Identity Wallet Relationship, not exists.", "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant Update Customer Identity Wallet Relationship, not exists");

            DatabaseTable table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME, walletPublicKey);
            record.setStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_IDENTITY_COLUMN_NAME, identityPublicKey);
            table.setUUIDFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId, DatabaseFilterType.EQUAL);
            table.updateRecord(record);

            return new CryptoCustomerIdentityWalletRelationshipImpl(walletPublicKey, identityPublicKey);
        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), e, "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant update Customer Identity Wallet Relationship, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor, Customer Identity Wallet Relationship", "Cant update Customer Identity Wallet Relationship, unknown failure.");
        }
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
    public Collection<CustomerIdentityWalletRelationship> getAllRegisterCustomerIdentityWalletRelationships() throws CantGetCustomerIdentiyWalletRelationshipException {
        return null;
    }

    //GET RELATIONSHIP ID
    public CustomerIdentityWalletRelationship getAllRegisterCustomerIdentityWalletRelationships(UUID relationshipId) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
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
                throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException("The number of records with a primary key is different thatn one ", null, "The id is: " + relationshipId.toString(), "");

//            return getCustomerIdentityWalletRelationshipFromRecord(record);
            return null;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(em.getMessage(), em, "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException(e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "unknown failure.");
        }
    }

    //GET RELATIONSHIP IDENTITY
    public CustomerIdentityWalletRelationship getAllRegisterCustomerIdentityWalletRelationshipsByIdentity(CryptoCustomerIdentity identity) throws CantGetCustomerIdentiyWalletRelationshipException {
        return null;
    }

    //GET RELATIONSHIP WALLET
    public CustomerIdentityWalletRelationship getAllRegisterCustomerIdentityWalletRelationshipsByWallet(UUID Wallet) throws CantGetCustomerIdentiyWalletRelationshipException {
        return null;
    }

    //OTHERS
    private boolean relationshipExists(String walletPublicKey, String identityPublicKey) throws CantRegisterCryptoCustomerIdentityWalletRelationshipException {
        DatabaseTable table;
        try {
            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship exists", "Crypto Customer Actor", "");
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
        DatabaseTable table;
        try {
            table = this.database.getTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship exists", "Crypto Customer Actor", "");
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

    private CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipFromRecord(DatabaseTableRecord record){

        String walletPublicKey = record.getStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME);
        String identityPublicKey = record.getStringValue(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME);

        return new CryptoCustomerIdentityWalletRelationshipImpl(walletPublicKey, identityPublicKey);
    }
    /*private actorExists(){
        DatabaseTable table;
        try {
            table = this.database.getTable (CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if relationship exists", "Crypto Customer Actor", "");
            }
            table.setUUIDFilter(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME, relationshipId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords ().size () > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException (em.getMessage(), em, "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "Cant load " + CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantRegisterCryptoCustomerIdentityWalletRelationshipException (e.getMessage(), FermatException.wrapException(e), "Crypto Customer Actor Identity Wallet Relationship It Already Exists", "unknown failure.");
        }
    }*/
}
