package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.developer_utils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import org.fermat.fermat_dap_api.layer.dap_transaction.asset_redemption.exceptions.CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetBuyerDeveloperDatabaseFactory {

    //VARIABLE DECLARATION
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    //CONSTRUCTORS

    public AssetBuyerDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantSetObjectException {
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
    }

    public AssetBuyerDeveloperDatabaseFactory() {
    }

    //PUBLIC METHODS


    public void initializeDatabase() throws CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            pluginDatabaseSystem.openDatabase(pluginId, org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_DATABASE);

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
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseFactory assetAppropriationDatabaseFactory = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseFactory(pluginDatabaseSystem, pluginId);

            try {
                  /*
                   * We create the new database
                   */
                assetAppropriationDatabaseFactory.createDatabase();
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

        eventsRecorderColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

        List<String> assetBuyerColumns = new ArrayList<>();
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_ENTRY_ID_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NETWORK_TYPE_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_PUBLICKEY_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_CRYPTO_ADDRESS_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_CRYPTO_ADDRESS_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_CRYPTO_CURRENCY_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_REFERENCE_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_TRANSACTION_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_VALUE_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_TRANSACTION_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_VALUE_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_ENTRY_ID_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_OUTGOING_ID_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_TX_HASH_COLUMN_NAME);
        assetBuyerColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable transactionMetadataTable = developerObjectFactory.getNewDeveloperDatabaseTable(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_TABLE_NAME, assetBuyerColumns);
        tables.add(transactionMetadataTable);

        List<String> assetNegotiationColumns = new ArrayList<>();
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_ID_COLUMN_NAME);
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_OBJECT_XML_COLUMN_NAME);
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_SELLER_PUBLICKEY_COLUMN_NAME);
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_ASSET_PUBLICKEY_COLUMN_NAME);
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_BTC_WALLET_PK_COLUMN_NAME);
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME);
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_NETWORK_TYPE_COLUMN_NAME);
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FOR_PROCESS_COLUMN_NAME);
        assetNegotiationColumns.add(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable assetNegotiationTable = developerObjectFactory.getNewDeveloperDatabaseTable(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_TABLE_NAME, assetNegotiationColumns);
        tables.add(assetNegotiationTable);

        return tables;
    }

    public static List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory, UUID pluginId) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDatabaseConstants.ASSET_BUYER_DATABASE, pluginId.toString()));
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
