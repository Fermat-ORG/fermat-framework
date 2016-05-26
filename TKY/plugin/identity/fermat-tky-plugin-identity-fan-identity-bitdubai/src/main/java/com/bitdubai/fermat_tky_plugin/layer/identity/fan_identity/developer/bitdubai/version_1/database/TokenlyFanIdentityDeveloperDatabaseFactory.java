package com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.exceptions.CantInitializeTokenlyFanIdentityDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by GAbriel Araujo 10/03/16.
 */
public class TokenlyFanIdentityDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public TokenlyFanIdentityDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeTokenlyFanIdentityDatabaseException
     */
    public void initializeDatabase() throws CantInitializeTokenlyFanIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_DB_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeTokenlyFanIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            TokenlyFanIdentityDatabaseFactory assetIssuerIdentityDatabaseFactory = new TokenlyFanIdentityDatabaseFactory(pluginDatabaseSystem);

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
                throw new CantInitializeTokenlyFanIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new CantInitializeTokenlyFanIdentityDatabaseException(e.getMessage());

        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_DB_NAME, this.pluginId.toString()));
        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Asset User columns.
         */
        List<String> ArtistUserColumns = new ArrayList<String>();

        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_ID_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_EXTERNAL_ID_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_SECRET_KEY_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_PASSWORD_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_USER_NAME_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_ACCESS_TOKEN_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_EMAIL_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME);
        ArtistUserColumns.add(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_ARTIST_CONNECTED_LIST_COLUMN_NAME);

        /**
         * Table Asset User addition.
         */
        DeveloperDatabaseTable assetUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(TokenlyFanIdentityDatabaseConstants.TOKENLY_FAN_IDENTITY_TABLE_NAME, ArtistUserColumns);
        tables.add(assetUserTable);



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
}
