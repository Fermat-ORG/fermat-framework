package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 15/09/15.
 */
public class AssetFactoryMiddlewareDeveloperFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public AssetFactoryMiddlewareDeveloperFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    public void initializeDatabase() throws org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.CantInitializeAssetFactoryMiddlewareDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetFactoryMiddlewareDatabaseConstant.DATABASE_NAME);


        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.CantInitializeAssetFactoryMiddlewareDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetFactoryMiddlewareDatabaseFactory assetFactoryMiddlewareDatabaseFactory = new AssetFactoryMiddlewareDatabaseFactory(this.pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetFactoryMiddlewareDatabaseFactory.createDatabase(pluginId, AssetFactoryMiddlewareDatabaseConstant.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.CantInitializeAssetFactoryMiddlewareDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(AssetFactoryMiddlewareDatabaseConstant.DATABASE_NAME, AssetFactoryMiddlewareDatabaseConstant.DATABASE_NAME));

        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Project columns.
         */
        List<String> assetFactoryColumns = new ArrayList<>();

        /**
         * Table Asset Factory addition.
         */
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ID_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NAME_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_QUANTITY_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TOTAL_QUANTITY_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_AMOUNT_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_FEE_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_DESCRIPTION_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_ALIAS_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_SIGNATURE_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CREATION_TIME_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_LAST_UPDATE_TIME_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_BEHAVIOR_COLUMN);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_REDEEMABLE);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_TRANSFERABLE);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_EXCHANGEABLE);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_EXPIRATION_DATE);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_WALLET_PUBLIC_KEY);
        assetFactoryColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NETWORK_TYPE);
        DeveloperDatabaseTable assetFactoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TABLE_NAME, assetFactoryColumns);
        tables.add(assetFactoryTable);

        /**
         * Table Project columns.
         */
        List<String> assetFactoryResourceColumns = new ArrayList<>();
        /**
         * Table Asset Factory Resource addition.
         */
        assetFactoryResourceColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ID_COLUMN);
        assetFactoryResourceColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ASSET_PUBLIC_KEY_COLUMN);
        assetFactoryResourceColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_FILE_NAME_COLUMN);
        assetFactoryResourceColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_PATH_COLUMN);
        assetFactoryResourceColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_RESOURCE_DENSITY_COLUMN);
        assetFactoryResourceColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_RESOURCE_TYPE_COLUMN);
        assetFactoryResourceColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_NAME_COLUMN);
        DeveloperDatabaseTable assetResourceTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_TABLE_NAME, assetFactoryResourceColumns);
        tables.add(assetResourceTable);

        /**
         * Table Project columns.
         */
        List<String> assetFactoryContractColumns = new ArrayList<>();
        /**
         * Table Asset Factory Contract properties addition.
         */
        assetFactoryContractColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ID_COLUMN);
        assetFactoryContractColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ASSET_PUBLIC_KEY_COLUMN);
        assetFactoryContractColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_NAME_COLUMN);
        assetFactoryContractColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_VALUE_COLUMN);
        DeveloperDatabaseTable assetContractTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_TABLE_NAME, assetFactoryContractColumns);
        tables.add(assetContractTable);

        /**
         * Table Project columns.
         */
        List<String> assetFactoryIdentityColumns = new ArrayList<>();
        /**
         * Table Asset Factory Identity Issuer properties addition.
         */
        assetFactoryIdentityColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ID_COLUMN);
        assetFactoryIdentityColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_PUBLIC_KEY_COLUMN);
        assetFactoryIdentityColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_NAME_COLUMN);
        assetFactoryIdentityColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_ASSET_PUBLIC_KEY_COLUMN);
        assetFactoryIdentityColumns.add(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_SIGNATURE_COLUMN);
        DeveloperDatabaseTable assetIdentityIssuerTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME, assetFactoryIdentityColumns);
        tables.add(assetIdentityIssuerTable);

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
}
