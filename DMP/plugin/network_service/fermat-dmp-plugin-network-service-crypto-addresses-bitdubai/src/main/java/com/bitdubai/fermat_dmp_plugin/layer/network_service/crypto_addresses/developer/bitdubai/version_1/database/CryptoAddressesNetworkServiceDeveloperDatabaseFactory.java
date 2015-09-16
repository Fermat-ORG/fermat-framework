package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoAddressesNetworkServiceDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CryptoAddressesNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCryptoAddressesNetworkServiceDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCryptoAddressesNetworkServiceDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCryptoAddressesNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CryptoAddressesNetworkServiceDatabaseFactory cryptoAddressesNetworkServiceDatabaseFactory = new CryptoAddressesNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = cryptoAddressesNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCryptoAddressesNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Crypto Addresses", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Contact Request columns.
         */
        List<String> contactRequestColumns = new ArrayList<>();

        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_REQUEST_ID_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_WALLET_PUBLIC_KEY_TO_SEND_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_REFERENCE_WALLET_TO_SEND_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_CRYPTO_ADDRESS_TO_SEND_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_REQUESTER_INTRA_USER_PUBLIC_KEY_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_REQUESTER_INTRA_USER_NAME_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_REQUESTER_INTRA_USER_PROFILE_IMAGE_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_WALLET_PUBLIC_KEY_ACCEPTING_REQUEST_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_REFERENCE_WALLET_ACCEPTING_REQUEST_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_CRYPTO_ADDRESS_RECEIVED_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_INTRA_USER_PUBLIC_KEY_ACCEPTING_REQUEST_COLUMN_NAME);
        contactRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_STATE_COLUMN_NAME);
        /**
         * Table Contact Request addition.
         */
        DeveloperDatabaseTable contactRequestTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoAddressesNetworkServiceDatabaseConstants.CONTACT_REQUEST_TABLE_NAME, contactRequestColumns);
        tables.add(contactRequestTable);



        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();


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
        List<String> developerRow = new ArrayList<>();
        for (DatabaseTableRecord row : records) {
            /**
             * for each row in the table list
             */
            for (DatabaseRecord field : row.getValues()) {
                /**
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue());
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