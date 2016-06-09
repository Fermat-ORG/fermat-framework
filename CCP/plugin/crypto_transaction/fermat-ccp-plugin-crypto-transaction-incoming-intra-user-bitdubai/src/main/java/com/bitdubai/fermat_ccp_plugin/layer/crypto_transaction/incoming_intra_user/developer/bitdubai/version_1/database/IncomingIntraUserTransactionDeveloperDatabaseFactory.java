package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>IncomingIntraUserTransactionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IncomingIntraUserTransactionDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public IncomingIntraUserTransactionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeIncomingIntraUserTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeIncomingIntraUserTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeIncomingIntraUserTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            IncomingIntraUserTransactionDatabaseFactory incomingIntraUserTransactionDatabaseFactory = new IncomingIntraUserTransactionDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = incomingIntraUserTransactionDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeIncomingIntraUserTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            } catch (Exception e2) {
                throw new CantInitializeIncomingIntraUserTransactionDatabaseException(e2.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Incoming Intra User", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Incoming Intra User Registry columns.
         */
        List<String> incomingIntraUserRegistryColumns = new ArrayList<String>();

        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_HASH_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ADDRESS_TO_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_CURRENCY_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_AMOUNT_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ADDRESS_FROM_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_STATUS_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ACTION_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_PROTOCOL_STATUS_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_STATUS_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TIMESTAMP_COLUMN_NAME);
        incomingIntraUserRegistryColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_NETWORK_TYPE);
        /**
         * Table Incoming Intra User Registry addition.
         */
        DeveloperDatabaseTable incomingIntraUserRegistryTable = developerObjectFactory.getNewDeveloperDatabaseTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME, incomingIntraUserRegistryColumns);
        tables.add(incomingIntraUserRegistryTable);

        /**
         * Table Incoming Intra User Events Recorded columns.
         */
        List<String> incomingIntraUserEventsRecordedColumns = new ArrayList<String>();

        incomingIntraUserEventsRecordedColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_ID_COLUMN_NAME);
        incomingIntraUserEventsRecordedColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        incomingIntraUserEventsRecordedColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        incomingIntraUserEventsRecordedColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        incomingIntraUserEventsRecordedColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);
        /**
         * Table Incoming Intra User Events Recorded addition.
         */
        DeveloperDatabaseTable incomingIntraUserEventsRecordedTable = developerObjectFactory.getNewDeveloperDatabaseTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TABLE_NAME, incomingIntraUserEventsRecordedColumns);
        tables.add(incomingIntraUserEventsRecordedTable);

        /**
         * Table Incoming Intra User Crypto Metadata columns.
         */
        List<String> incomingIntraUserCryptoMetadataColumns = new ArrayList<String>();

        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ID_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_REQUEST_FLAG_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_REQUEST_ID_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_SENDER_PUBLIC_KEY_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ACTION_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PROTOCOL_STATUS_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TRANSACTION_STATUS_COLUMN_NAME);
        incomingIntraUserCryptoMetadataColumns.add(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TIMESTAMP_COLUMN_NAME);

        /**
         * Table Incoming Intra User Crypto Metadata addition.
         */
        DeveloperDatabaseTable incomingIntraUserCryptoMetadataTable = developerObjectFactory.getNewDeveloperDatabaseTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME, incomingIntraUserCryptoMetadataColumns);
        tables.add(incomingIntraUserCryptoMetadataTable);



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
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row: records){
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()){
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
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e){
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }

//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
}
