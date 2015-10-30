package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.developerUtils;

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
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.database.RedeemPointActorDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.database.RedeemPointActorDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantInitializeRedeemPointActorDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 17/09/15.
 */
public class RedeemPointActorDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public RedeemPointActorDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeRedeemPointActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeRedeemPointActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeRedeemPointActorDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException exception) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            RedeemPointActorDatabaseFactory redeemPointActorDatabaseFactory = new RedeemPointActorDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = redeemPointActorDatabaseFactory.createDatabase(pluginId, RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeRedeemPointActorDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(RedeemPointActorDatabaseConstants.REDEEM_POINT_DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Redeem Point database columns.
         */
        List<String> redeemPointActorColumns = new ArrayList<String>();

        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_NAME_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONTACT_INFORMATION_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_HOURS_OF_OPERATION_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_COUNTRY_NAME_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_POSTAL_CODE_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_PROVINCE_NAME_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_CITY_NAME_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_STREET_NAME_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_HOUSE_NUMBER_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_ADDRESS_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_CURRENCY_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LATITUDE_COLUMN_NAME);
        redeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LONGITUDE_COLUMN_NAME);

        /*
         * Redeem Point database addition.
         */
        DeveloperDatabaseTable redeemPointActorTable = developerObjectFactory.getNewDeveloperDatabaseTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME, redeemPointActorColumns);
        tables.add(redeemPointActorTable);

        // REGISTERED REDEEM POINT DATABASE

        List<String> registeredRedeemPointActorColumns = new ArrayList<String>();

        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_NAME_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CONNECTION_STATE_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_REGISTRATION_DATE_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_MODIFIED_DATE_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CONTACT_INFORMATION_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_HOURS_OF_OPERATION_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_COUNTRY_NAME_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_POSTAL_CODE_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_PROVINCE_NAME_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_CITY_NAME_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_STREET_NAME_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_HOUSE_NUMBER_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME);
        registeredRedeemPointActorColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME);

        /*
         * Registered Redeem Point database addition.
         */
        DeveloperDatabaseTable registeredRedeemPointActorTable = developerObjectFactory.getNewDeveloperDatabaseTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_TABLE_NAME, registeredRedeemPointActorColumns);
        tables.add(registeredRedeemPointActorTable);

        /**
         * Redeem Point Relation Issuer database columns.
         */
//        List<String> redeemPointRelationAssetIssuerColumns = new ArrayList<String>();
//
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_PUBLIC_KEY_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_NAME_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_STATUS_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_ASSETS_COUNT_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_REGISTRATION_DATE_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_NAME_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_STATUS_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME);
//        redeemPointRelationAssetIssuerColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME);
        /**
         * Redeem Point Relation Issuer database addition.
         */
//        DeveloperDatabaseTable redeemPointRelationAssetIssuerTable = developerObjectFactory.getNewDeveloperDatabaseTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ISSUER_TABLE_NAME, redeemPointRelationAssetIssuerColumns);
//        tables.add(redeemPointRelationAssetIssuerTable);

        /**
         * Redeem Point Relation Aseet User database columns.
         */
//        List<String> redeemPointRelationAssetUserColumns = new ArrayList<String>();
//
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_RELATION_ISSUER_NAME_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ISSUER_IDENTITY_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_USER_NAME_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_USER_SEX_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_USER_UBICACION_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_USER_AGE_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_DESCRIPTION_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_NAME_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_ID_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_HASH_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_STATUS_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_RESOURCES_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_AMOUNT_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_CURRENCY_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_ASSET_EXPIRATION_DATE_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_REDEEMPTION_TIMESTAMP_COLUMN_NAME);
//        redeemPointRelationAssetUserColumns.add(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_REDEEMPTION_DATE_COLUMN_NAME);
        /**
         * Redeem Point Relation Aseet User database addition.
         */
//        DeveloperDatabaseTable redeemPointRelationAssetUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(RedeemPointActorDatabaseConstants.REDEEM_POINT_RELATION_ASSET_USER_TABLE_NAME, redeemPointRelationAssetUserColumns);
//        tables.add(redeemPointRelationAssetUserTable);

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
