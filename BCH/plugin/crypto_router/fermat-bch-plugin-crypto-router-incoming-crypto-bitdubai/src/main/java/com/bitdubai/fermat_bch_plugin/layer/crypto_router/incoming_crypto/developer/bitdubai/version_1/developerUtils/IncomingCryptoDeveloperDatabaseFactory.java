package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.developerUtils;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoDataBaseConstants;
import com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetDataBaseTool;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eze on 2015.06.29..
 */
public class IncomingCryptoDeveloperDatabaseFactory {
    String pluginId;
    String databaseNAme;

    ErrorManager errorManager;

    public IncomingCryptoDeveloperDatabaseFactory (ErrorManager errorManager, String pluginId, String databaseNAme){
        this.pluginId = pluginId;
        this.databaseNAme = databaseNAme;
        this.errorManager = errorManager;
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * We have one database for the whole plugin, so we will return its name.
         *
         */
        /**
         * Modified by Franklin Marcano, 03/08/2015
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        try {
            databases.add(developerObjectFactory.getNewDeveloperDatabase(this.databaseNAme, this.pluginId));
        }catch(Exception exception){
            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "getDatabaseList:" + developerObjectFactory, "Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /*
         * First we add the IncomingCryptoRegistry table
         */
        /**
         * Modified by Franklin Marcano, 03/08/2015
         */

        List<String> incomingCryptoRegistryColumns = new ArrayList<>();
        try {


            incomingCryptoRegistryColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName);
            incomingCryptoRegistryColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_TRANSACTION_COLUMN.columnName);
            incomingCryptoRegistryColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName);
            incomingCryptoRegistryColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName);
            incomingCryptoRegistryColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN.columnName);
            incomingCryptoRegistryColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName);
            incomingCryptoRegistryColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName);
            incomingCryptoRegistryColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TIMESTAMP_COLUMN.columnName);

            /**
             * incomingCryptoRegistry table
             */
            DeveloperDatabaseTable incomingCryptoRegistryTable = developerObjectFactory.getNewDeveloperDatabaseTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME, incomingCryptoRegistryColumns);
            tables.add(incomingCryptoRegistryTable);

            /*
             * EventsRecorded Registry table columns
             */
            List<String> eventsRecordedColumns = new ArrayList<>();
            eventsRecordedColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName);
            eventsRecordedColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_EVENT_COLUMN.columnName);
            eventsRecordedColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_SOURCE_COLUMN.columnName);
            eventsRecordedColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName);
            eventsRecordedColumns.add(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName);

            /*
             * EventsRecorded Registry table
             */
            DeveloperDatabaseTable eventsRecordedTable = developerObjectFactory.getNewDeveloperDatabaseTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME, eventsRecordedColumns);
            tables.add(eventsRecordedTable);
        }catch(Exception exception){
            FermatException e = new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "getDatabaseTableList",  "Check the cause");
           this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

        return tables;
    }


    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory,  Database database, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        /**
         * Modified by Franklin Marcano, 03/08/2015
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
}