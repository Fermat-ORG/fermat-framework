package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.developerUtils;

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

import org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 17/09/15.
 */
public class AssetIssuerActorDeveloperDatabaseFactory  implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public AssetIssuerActorDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeAssetIssuerActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeAssetIssuerActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetIssuerActorDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException exception) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseFactory assetIssuerActorDatabaseFactory = new org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.database.AssetIssuerActorDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetIssuerActorDatabaseFactory.createDatabase(pluginId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetIssuerActorDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Asset Issuer database columns.
         */
        List<String> assetIssuerActorColumns = new ArrayList<String>();

        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TYPE_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME);
        assetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME);

        /*
         * Redeem Point database addition.
         */
        DeveloperDatabaseTable assetIssuerActorTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME, assetIssuerActorColumns);
        tables.add(assetIssuerActorTable);


        List<String> registeredAssetIssuerActorColumns = new ArrayList<String>();

        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TYPE_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME);
        registeredAssetIssuerActorColumns.add(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME);

        /*
         * Redeem Point database addition.
         */
        DeveloperDatabaseTable registeredAssetIssuerActorTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME, registeredAssetIssuerActorColumns);
        tables.add(registeredAssetIssuerActorTable);

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

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
