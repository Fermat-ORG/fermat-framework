package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraActorTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>OutgoingIntraActorTransactionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 21/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class OutgoingIntraActorTransactionDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public OutgoingIntraActorTransactionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
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
            database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DATABASE_NAME);

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
            OutgoingIntraActorTransactionDatabaseFactory outgoingIntraActorTransactionDatabaseFactory = new OutgoingIntraActorTransactionDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = outgoingIntraActorTransactionDatabaseFactory.createDatabase(pluginId, OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeOutgoingIntraActorTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Outgoing Intra User", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Outgoing Intra User columns.
         */
        List<String> outgoingIntraUserColumns = new ArrayList<String>();

        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_ID_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_REQUEST_ID_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_HASH_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ADDRESS_FROM_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ADDRESS_TO_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_CURRENCY_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_AMOUNT_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_OP_RETURN_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TRANSACTION_STATUS_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_DESCRIPTION_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TIMESTAMP_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_CRYPTO_STATUS_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_FROM_TYPE_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_ACTOR_TO_TYPE_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_WALLET_REFERENCE_TYPE_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_SAME_DEVICE_COLUMN_NAME);
        outgoingIntraUserColumns.add(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_RUNNING_NETWORK_TYPE);

        /**
         * Table Outgoing Intra User addition.
         */
        DeveloperDatabaseTable outgoingIntraUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(OutgoingIntraActorTransactionDatabaseConstants.OUTGOING_INTRA_ACTOR_TABLE_NAME, outgoingIntraUserColumns);
        tables.add(outgoingIntraUserTable);



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
