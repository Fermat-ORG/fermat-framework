package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletContactsMiddlewareDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public WalletContactsMiddlewareDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeWalletContactsMiddlewareDatabaseException
     */
    public void initializeDatabase() throws CantInitializeWalletContactsMiddlewareDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeWalletContactsMiddlewareDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletContactsMiddlewareDatabaseFactory walletContactsMiddlewareDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = walletContactsMiddlewareDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletContactsMiddlewareDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Wallet Contacts", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Wallet Contacts columns.
         */
        List<String> walletContactsColumns = new ArrayList<String>();

        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME);
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_TYPE_COLUMN_NAME);
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME);
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME);
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME);
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        /**
         * Table Wallet Contacts addition.
         */
        DeveloperDatabaseTable walletContactsTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME, walletContactsColumns);
        tables.add(walletContactsTable);

        /**
         * Table Wallet Contact Addresses columns.
         */
        List<String> walletContactAddressesColumns = new ArrayList<String>();

        walletContactAddressesColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME);
        walletContactAddressesColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME);
        walletContactAddressesColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME);
        /**
         * Table Wallet Contact Addresses addition.
         */
        DeveloperDatabaseTable walletContactAddressesTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TABLE_NAME, walletContactAddressesColumns);
        tables.add(walletContactAddressesTable);



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