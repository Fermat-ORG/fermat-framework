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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_Customer.developer.bitdubai.version_1.database.CryptoCustomerActorDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 16/01/16.
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
         * Table Crypto Customer Actor Relationship columns.
         */
        List<String> cryptoCustomerActorRelationshipColumns = new ArrayList<String>();

        cryptoCustomerActorRelationshipColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME);
        cryptoCustomerActorRelationshipColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_RELATIONSHIP_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        cryptoCustomerActorRelationshipColumns.add(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_RELATIONSHIP_WALLET_COLUMN_NAME);
        /**
         * Table Crypto Customer Actor Relationship addition.
         */
        DeveloperDatabaseTable cryptoCustomerActorRelationshipTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoCustomerActorDatabaseConstants.CRYPTO_CUSTOMER_ACTOR_RELATIONSHIP_TABLE_NAME, cryptoCustomerActorRelationshipColumns);
        tables.add(cryptoCustomerActorRelationshipTable);

        /**
         * Table Actor Extra Data columns.
         */
        List<String> actorExtraDataColumns = new ArrayList<String>();

        actorExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.ACTOR_EXTRA_DATA_ID_COLUMN_NAME);
        actorExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.ACTOR_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME);
        actorExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.ACTOR_EXTRA_DATA_ALIAS_COLUMN_NAME);
        actorExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.ACTOR_EXTRA_DATA_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        /**
         * Table Actor Extra Data addition.
         */
        DeveloperDatabaseTable actorExtraDataTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoCustomerActorDatabaseConstants.ACTOR_EXTRA_DATA_TABLE_NAME, actorExtraDataColumns);
        tables.add(actorExtraDataTable);

        /**
         * Table Quote Extra Data columns.
         */
        List<String> quoteExtraDataColumns = new ArrayList<String>();

        quoteExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.QUOTE_EXTRA_DATA_QUOTE_ID_COLUMN_NAME);
        quoteExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.QUOTE_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME);
        quoteExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.QUOTE_EXTRA_DATA_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        quoteExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.QUOTE_EXTRA_DATA_MERCHANDISE_COLUMN_NAME);
        quoteExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME);
        quoteExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.QUOTE_EXTRA_DATA_PRICE_COLUMN_NAME);
        quoteExtraDataColumns.add(CryptoCustomerActorDatabaseConstants.QUOTE_EXTRA_DATA_SUPPORTED_PLATFORMS_COLUMN_NAME);
        /**
         * Table Quote Extra Data addition.
         */
        DeveloperDatabaseTable quoteExtraDataTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoCustomerActorDatabaseConstants.QUOTE_EXTRA_DATA_TABLE_NAME, quoteExtraDataColumns);
        tables.add(quoteExtraDataTable);


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
        for (DatabaseTableRecord row : records) {
            List<String> developerRow = new ArrayList<String>();
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