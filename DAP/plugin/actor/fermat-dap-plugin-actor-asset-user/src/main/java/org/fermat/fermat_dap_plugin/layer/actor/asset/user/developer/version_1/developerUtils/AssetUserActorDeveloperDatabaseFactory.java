package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.developerUtils;

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

import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.database.AssetUserActorDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.database.AssetUserActorDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;

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
         * Asset User Actor database columns.
         */
        List<String> assetUserActorColumns = new ArrayList<String>();

        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_NAME_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_AGE_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_GENDER_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LATITUDE_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LONGITUDE_COLUMN_NAME);
        //assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME);
        //assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTRATION_DATE_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_LAST_CONNECTION_DATE_COLUMN_NAME);
        assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_ACTOR_TYPE_COLUMN_NAME);
        //assetUserActorColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_NETWORK_TYPE_COLUMN_NAME);
        /*
         * Asset User Actor database addition.
         */
        DeveloperDatabaseTable assetUserActorTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME, assetUserActorColumns);
        tables.add(assetUserActorTable);

        /**
         * Asset User Actor Registered Table database columns.
         */
        List<String> assetUserRegisteredColumns = new ArrayList<String>();

        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME);
        //assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME);
        //assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME);
        assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_ACTOR_TYPE_COLUMN_NAME);
        //assetUserRegisteredColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NETWORK_TYPE_COLUMN_NAME);

        /**
         * Asset User Actor Registered database addition.
         */
        DeveloperDatabaseTable assetUserRegisteredTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME, assetUserRegisteredColumns);
        tables.add(assetUserRegisteredTable);


        /**
         * Asset User Group Table database columns.
         */
        List<String> assetUserGroupColumns = new ArrayList<String>();

        assetUserGroupColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_ID_COLUMN_NAME);
        assetUserGroupColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_NAME_COLUMN_NAME);


        /**
         * Asset User Group database addition.
         */
        DeveloperDatabaseTable assetUserGroupTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME, assetUserGroupColumns);
        tables.add(assetUserGroupTable);

        /**
         * Asset User Group Member Table database columns.
         */
        List<String> assetUserGroupMemberColumns = new ArrayList<String>();

        assetUserGroupMemberColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_GROUP_ID_COLUMN_NAME);
        assetUserGroupMemberColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME);


        /**
         * Asset User Group Member database addition.
         */
        DeveloperDatabaseTable assetUserGroupMemberTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_TABLE_NAME, assetUserGroupMemberColumns);
        tables.add(assetUserGroupMemberTable);
        /**
         * Asset User Relation Asset Issuer database columns.
         */
//        List<String> assetUserRelationAssetIssuerColumns = new ArrayList<String>();
//
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_PUBLIC_KEY_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_NAME_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_IDENTITY_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_NAME_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_DESCRIPTION_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_ID_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_HASH_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_STATUS_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_RESOURCES_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_AMOUNT_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_CURRENCY_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_EXPIRATION_DATE_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_REDEEMPTION_TIMESTAMP_COLUMN_NAME);
//        assetUserRelationAssetIssuerColumns.add(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_REDEEMPTION_DATE_COLUMN_NAME);
        /**
         * Asset User Relation Asset Issuer database addition.
         */
//        DeveloperDatabaseTable assetUserRelationAssetIssuerTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ASSET_ISSUER_TABLE_NAME, assetUserRelationAssetIssuerColumns);
//        tables.add(assetUserRelationAssetIssuerTable);

        /**
         * Crypto Address database columns.
         */
        List<String> cryptoAddressColums = new ArrayList<String>();

        cryptoAddressColums.add(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_PUBLIC_KEY_COLUMN_NAME);
        cryptoAddressColums.add(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CRYPTO_ADDRESS_COLUMN_NAME);
        cryptoAddressColums.add(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CRYPTO_CURRENCY_COLUMN_NAME);
        cryptoAddressColums.add(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_NETWORK_TYPE_COLUMN_NAME);


        /**
         * Crypto Address  database addition.
         */
        DeveloperDatabaseTable assetUserRelationAssetIssuerTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_TABLE_NAME, cryptoAddressColums);
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
