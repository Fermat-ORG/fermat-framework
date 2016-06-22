package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.developer_utils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantInitializeAssetIssuingTransactionDatabaseException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.transaction.asset_issuing.developer.bitdubai.version_1.database.AssetIssuingTransactionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class AssetIssuingTransactionDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public AssetIssuingTransactionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeAssetIssuingTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeAssetIssuingTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetIssuingTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetIssuingDatabaseFactory assetIssuingDatabaseFactory = new AssetIssuingDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetIssuingDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetIssuingTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE, this.pluginId.toString()));
        return databases;
    }


    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Digital Asset Transaction columns.
         */
        List<String> issuingColumns = new ArrayList<String>();

        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_TO_GENERATE_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_COMPLETED_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_PROCESSED_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_NETWORK_TYPE_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_BTC_WALLET_PUBLIC_KEY_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUER_WALLET_PUBLIC_KEY_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_CREATION_DATE_COLUMN_NAME);
        issuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_PROCESSING_COLUMN_NAME);
        /**
         * Table Digital Asset Transaction addition.
         */
        DeveloperDatabaseTable digitalAssetTransactionTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME, issuingColumns);
        tables.add(digitalAssetTransactionTable);

        /**
         * Events Recorder table
         * */
        List<String> eventsRecorderColumns = new ArrayList<String>();

        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

        /**
         * Asset Issuing table
         */
        List<String> metadataColumns = new ArrayList<>();

        metadataColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ID_COLUMN_NAME);
        metadataColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_OUTGOING_ID_COLUMN_NAME);
        metadataColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_GENESIS_TRANSACTION_COLUMN_NAME);
        metadataColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ISSUING_STATUS_COLUMN_NAME);
        metadataColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_CREATION_TIME_COLUMN_NAME);

        DeveloperDatabaseTable assetIssuingTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_TABLE, metadataColumns);
        tables.add(assetIssuingTable);

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
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e) {
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }
}
