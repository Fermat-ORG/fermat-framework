package org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.developer_utils;

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

import org.fermat.fermat_dap_api.layer.dap_transaction.asset_redemption.exceptions.CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException;
import org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.database.AssetRedeemPointRedemptionDatabaseConstants;
import org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.database.AssetRedeemPointRedemptionDatabaseFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class AssetRedeemPointRedemptionDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
    //VARIABLE DECLARATION
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private Database database;

    //CONSTRUCTORS
    public AssetRedeemPointRedemptionDeveloperDatabaseFactory(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public AssetRedeemPointRedemptionDeveloperDatabaseFactory() {
    }

    //PUBLIC METHODS
    public void initializeDatabase() throws CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = pluginDatabaseSystem.openDatabase(pluginId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_DATABASE);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetRedeemPointRedemptionDatabaseFactory redeemPointRedemptionDatabaseFactory = new AssetRedeemPointRedemptionDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = redeemPointRedemptionDatabaseFactory.createDatabase(pluginId);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Events Recorder table
         * */
        List<String> eventsRecorderColumns = new ArrayList<String>();

        eventsRecorderColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

        /**
         * Metadata table.
         */
        List<String> metadataColumns = new ArrayList<String>();
        metadataColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_ID_COLUMN_NAME);
        metadataColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_STATUS_COLUMN_NAME);
        metadataColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_CRYPTO_STATUS_COLUMN_NAME);
        metadataColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_SENDER_KEY_COLUMN_NAME);
        metadataColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_RECEIVER_KEY_COLUMN_NAME);
        metadataColumns.add(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable metadataTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_NAME, metadataColumns);
        tables.add(metadataTable);


        return tables;
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_DATABASE, pluginId.toString()));
        return databases;
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
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        } finally {
            database.closeDatabase();
        }
        return returnedRecords;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS
    //INNER CLASSES
}
