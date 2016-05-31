package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 22/03/16.
 */
public class TransferIntraWalletUsersDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {


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
    public TransferIntraWalletUsersDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        try {
            initializeDatabase();
        } catch (CantInitializeOutgoingIntraActorTransactionDatabaseException e) {
            e.printStackTrace();
        }
    }



    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeOutgoingIntraActorTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeOutgoingIntraActorTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeOutgoingIntraActorTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            TransferIntraWalletUsersDatabaseFactory outgoingDraftTransactionDatabaseFactory = new TransferIntraWalletUsersDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = outgoingDraftTransactionDatabaseFactory.createDatabase(pluginId, TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeOutgoingIntraActorTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public static List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory, UUID pluginId) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DATABASE_NAME, pluginId.toString()));
        return databases;
    }


    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Outgoing Intra User columns.
         */
        List<String> outgoingIntraUserColumns = new ArrayList<String>();

        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_ID_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_HASH_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_CRYPTO_AMOUNT_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TRANSACTION_STATUS_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_DESCRIPTION_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TIMESTAMP_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_ACTOR_TYPE_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_REFERENCE_TYPE_SENDING_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_REFERENCE_TYPE_RECEIVING_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_PUBLIC_KEY_SENDING_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_WALLET_PUBLIC_KEY_RECEIVING_COLUMN_NAME);
        outgoingIntraUserColumns.add(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_RUNNING_NETWORK_TYPE);

        /**
         * Table Outgoing Intra User addition.
         */
        DeveloperDatabaseTable outgoingIntraUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(TransferIntraWalletUsersDatabaseConstants.TRANSFER_INTRA_WALLET_USERS_TABLE_NAME, outgoingIntraUserColumns);
        tables.add(outgoingIntraUserTable);


        return tables;
    }


    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, Database database, DeveloperDatabaseTable developerDatabaseTable) {
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
            for (DatabaseTableRecord row : records) {
                List<String> developerRow = new ArrayList<String>();
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
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        } catch (Exception e) {
            return returnedRecords;
        }
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
