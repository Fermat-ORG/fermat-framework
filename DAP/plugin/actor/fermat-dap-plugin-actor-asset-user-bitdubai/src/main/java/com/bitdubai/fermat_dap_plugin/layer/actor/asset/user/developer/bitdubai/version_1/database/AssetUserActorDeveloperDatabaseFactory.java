package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 17/09/15.
 */
public class AssetUserActorDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public AssetUserActorDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }


    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeAssetUserActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeAssetUserActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetUserActorDatabaseConstants.ASSET_USER_DATABASE_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetUserActorDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException exception) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetUserActorDatabaseFactory assetUserActorDatabaseFactory = new AssetUserActorDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetUserActorDatabaseFactory.createDatabase(pluginId, AssetUserActorDatabaseConstants.ASSET_USER_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetUserActorDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(AssetUserActorDatabaseConstants.ASSET_USER_DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Asset User database columns.
         */
        List<String> assetUserActorColumns = new ArrayList<String>();

        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_USER_NAME_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_USER_SEX_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_USER_UBICACION_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_USER_PHOTO_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_USER_AGE_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_USER_REGISTRATION_DATE_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_USER_MODIFIED_DATE_COLUMN_NAME);
        /*
         * Asset User database addition.
         */
        DeveloperDatabaseTable assetUserActorTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME, assetUserActorColumns);
        tables.add(assetUserActorTable);

        /**
         * Asset User Relation Asset Issuer database columns.
         */
        List<String> assetUserRelationAssetIssuerColumns = new ArrayList<String>();

        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_PUBLIC_KEY_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_NAME_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_IDENTITY_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_NAME_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_DESCRIPTION_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_ID_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_HASH_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_STATUS_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_RESOURCES_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_AMOUNT_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_CURRENCY_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_EXPIRATION_DATE_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_REDEEMPTION_TIMESTAMP_COLUMN_NAME);
        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_REDEEMPTION_DATE_COLUMN_NAME);
        /**
         * Asset User Relation Asset Issuer database addition.
         */
        DeveloperDatabaseTable assetUserRelationAssetIssuerTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ASSET_ISSUER_TABLE_NAME, assetUserRelationAssetIssuerColumns);
        tables.add(assetUserRelationAssetIssuerTable);

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
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        List<String> developerRow = new ArrayList<String>();
        for (DatabaseTableRecord row : records) {
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
        return returnedRecords;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
