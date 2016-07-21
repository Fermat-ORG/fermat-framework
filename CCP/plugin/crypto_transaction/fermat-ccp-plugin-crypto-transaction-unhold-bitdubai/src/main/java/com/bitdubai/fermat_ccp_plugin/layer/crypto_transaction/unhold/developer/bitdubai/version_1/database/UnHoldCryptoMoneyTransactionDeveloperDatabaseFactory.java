package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdCryptoMoneyTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Franklin Marcano on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeUnholdCryptoMoneyTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeUnholdCryptoMoneyTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeUnholdCryptoMoneyTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            UnHoldCryptoMoneyTransactionDatabaseFactory unHoldCryptoMoneyTransactionDatabaseFactory = new UnHoldCryptoMoneyTransactionDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = unHoldCryptoMoneyTransactionDatabaseFactory.createDatabase(pluginId, UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeUnholdCryptoMoneyTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_DATABASE_NAME, UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_DATABASE_NAME));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Hold columns.
         */
        List<String> holdColumns = new ArrayList<String>();

        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_AMOUNT_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_CURRENCY_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_MEMO_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_FEE_COLUMN_NAME);
        holdColumns.add(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_FEE_ORIGIN_COLUMN_NAME);
        /**
         * Table Hold addition.
         */
        DeveloperDatabaseTable holdTable = developerObjectFactory.getNewDeveloperDatabaseTable(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME, holdColumns);
        tables.add(holdTable);



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