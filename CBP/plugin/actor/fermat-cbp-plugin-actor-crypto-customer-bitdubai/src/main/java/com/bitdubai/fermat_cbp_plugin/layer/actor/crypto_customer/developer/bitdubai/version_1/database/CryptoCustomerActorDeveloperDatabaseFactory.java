package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 21/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoCustomerActorDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public CryptoCustomerActorDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCryptoCustomerActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCryptoCustomerActorDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCryptoCustomerActorDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CryptoCustomerActorDatabaseFactory cryptoCustomerActorDatabaseFactory = new CryptoCustomerActorDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = cryptoCustomerActorDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCryptoCustomerActorDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Crypto Customer", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Crypto Customer Actor columns.
         */
        List<String> cryptoCustomerActorColumns = new ArrayList<String>();

        cryptoCustomerActorColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_ACTOR_ID_COLUMN_NAME);
        cryptoCustomerActorColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_ACTOR_COLUMN_NAME);
        cryptoCustomerActorColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_IDENTITY_COLUMN_NAME);
        cryptoCustomerActorColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_NAME_ACTOR_COLUMN_NAME);
        cryptoCustomerActorColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_CONNECTION_STATE_COLUMN_NAME);
        cryptoCustomerActorColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TIMESTAMP_COLUMN_NAME);
        /**
         * Table Crypto Customer Actor addition.
         */
        DeveloperDatabaseTable cryptoCustomerActorTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_TABLE_NAME, cryptoCustomerActorColumns);
        tables.add(cryptoCustomerActorTable);

        /**
         * Table Crypto Customer Identity Wallet Relationship columns.
         */
        List<String> cryptoCustomerIdentityWalletRelationshipColumns = new ArrayList<String>();

        cryptoCustomerIdentityWalletRelationshipColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME);
        cryptoCustomerIdentityWalletRelationshipColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_IDENTITY_COLUMN_NAME);
        cryptoCustomerIdentityWalletRelationshipColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME);
        cryptoCustomerIdentityWalletRelationshipColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TIMESTAMP_COLUMN_NAME);
        /**
         * Table Crypto Customer Identity Wallet Relationship addition.
         */
        DeveloperDatabaseTable cryptoCustomerIdentityWalletRelationshipTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME, cryptoCustomerIdentityWalletRelationshipColumns);
        tables.add(cryptoCustomerIdentityWalletRelationshipTable);



        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();


        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        List<String> developerRow = new ArrayList<String>();
        for (DatabaseTableRecord row : records) {
            /**
             * for each row in the table list
             */
            for (DatabaseRecord field : row.getValues()) {
                /**
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue().toString());
            }
            /**
             * I create the Developer Database record
             */
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }


        /**
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}