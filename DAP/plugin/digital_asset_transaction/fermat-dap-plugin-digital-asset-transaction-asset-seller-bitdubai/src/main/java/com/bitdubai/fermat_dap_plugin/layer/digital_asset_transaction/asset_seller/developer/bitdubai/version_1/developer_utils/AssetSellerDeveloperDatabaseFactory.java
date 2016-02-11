package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.developer_utils;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_redemption.exceptions.CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.database.AssetSellerDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.database.AssetSellerDatabaseFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetSellerDeveloperDatabaseFactory {

    //VARIABLE DECLARATION
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    //CONSTRUCTORS

    public AssetSellerDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantSetObjectException {
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
    }

    public AssetSellerDeveloperDatabaseFactory() {
    }

    //PUBLIC METHODS


    public void initializeDatabase() throws CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            pluginDatabaseSystem.openDatabase(pluginId, AssetSellerDatabaseConstants.ASSET_SELLER_DATABASE);

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
            AssetSellerDatabaseFactory assetAppropriationDatabaseFactory = new AssetSellerDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                assetAppropriationDatabaseFactory.createDatabase(pluginId);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Events Recorder table
         * */
        List<String> eventsRecorderColumns = new ArrayList<>();

        eventsRecorderColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

        List<String> assetSellerColumns = new ArrayList<>();


        assetSellerColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_ENTRY_ID_COLUMN_NAME);
        assetSellerColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_GENESIS_TRANSACTION_COLUMN_NAME);
        assetSellerColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_NETWORK_TYPE_COLUMN_NAME);
        assetSellerColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_BUYER_PUBLICKEY_COLUMN_NAME);
        assetSellerColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_SELL_STATUS_COLUMN_NAME);
        assetSellerColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_REFERENCE_COLUMN_NAME);
        assetSellerColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_TRANSACTION_COLUMN_NAME);
        assetSellerColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable transactionMetadataTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetSellerDatabaseConstants.ASSET_SELLER_TABLE_NAME, assetSellerColumns);
        tables.add(transactionMetadataTable);

        List<String> assetNegotiationColumns = new ArrayList<>();


        assetNegotiationColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_ID_COLUMN_NAME);
        assetNegotiationColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_OBJECT_XML_COLUMN_NAME);
        assetNegotiationColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_STATUS_COLUMN_NAME);
        assetNegotiationColumns.add(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_TIMESTAMP_COLUMN_NAME);
        DeveloperDatabaseTable assetNegotiationTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_TABLE_NAME, assetNegotiationColumns);
        tables.add(assetNegotiationTable);

        return tables;
    }

    public static List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory, UUID pluginId) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(AssetSellerDatabaseConstants.ASSET_SELLER_DATABASE, pluginId.toString()));
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
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        }
        return returnedRecords;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
