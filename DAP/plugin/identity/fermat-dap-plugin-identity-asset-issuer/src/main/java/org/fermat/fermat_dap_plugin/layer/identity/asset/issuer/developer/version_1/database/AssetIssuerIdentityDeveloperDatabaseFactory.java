package org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.database;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class AssetIssuerIdentityDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public AssetIssuerIdentityDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException
     */
    public void initializeDatabase() throws org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_DB_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetIssuerIdentityDatabaseFactory assetIssuerIdentityDatabaseFactory = new AssetIssuerIdentityDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetIssuerIdentityDatabaseFactory.createDatabase(pluginId);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions.CantInitializeAssetIssuerIdentityDatabaseException(e.getMessage());

        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_DB_NAME, this.pluginId.toString()));
        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Asset Issuer columns.
         */
        List<String> AssetIssuerColumns = new ArrayList<String>();

        AssetIssuerColumns.add(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        AssetIssuerColumns.add(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_ALIAS_COLUMN_NAME);
        AssetIssuerColumns.add(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME);

        AssetIssuerColumns.add(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_COUNTRY_KEY_COLUMN);
        AssetIssuerColumns.add(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_CITY_KEY_COLUMN);
        AssetIssuerColumns.add(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_STATE_KEY_COLUMN);
        AssetIssuerColumns.add(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_ACCURACY_KEY_COLUMN);
        AssetIssuerColumns.add(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_FREQUENCY_KEY_COLUMN);
        /**
         * Table Asset Issuer addition.
         */
        DeveloperDatabaseTable assetIssuerTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_TABLE_NAME, AssetIssuerColumns);
        tables.add(assetIssuerTable);


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
