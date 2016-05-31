package com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_tky_plugin.layer.song_wallet.tokenly.developer.bitdubai.version_1.exceptions.CantInitializeTokenlySongWalletDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/03/16.
 */
public class TokenlySongWalletDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public TokenlySongWalletDeveloperDatabaseFactory(
            PluginDatabaseSystem pluginDatabaseSystem,
            UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeTokenlySongWalletDatabaseException
     */
    public void initializeDatabase() throws CantInitializeTokenlySongWalletDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, TokenlySongWalletDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeTokenlySongWalletDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            TokenlySongWalletDatabaseFactory tokenlySongWalletDatabaseFactory = new TokenlySongWalletDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = tokenlySongWalletDatabaseFactory.createDatabase(pluginId, TokenlySongWalletDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeTokenlySongWalletDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Tokenly", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Song columns.
         */
        List<String> songColumns = new ArrayList<>();

        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_SONG_ID_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_TOKENLY_ID_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_NAME_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_TOKENS_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_PERFORMERS_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_COMPOSERS_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_RELEASE_DATE_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_LYRICS_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_CREDITS_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_COPYRIGHT_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_OWNERSHIP_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_USAGE_RIGHTS_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_USAGE_PROHIBITIONS_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_DEVICE_PATH_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_SONG_STATUS_COLUMN_NAME);
        songColumns.add(TokenlySongWalletDatabaseConstants.SONG_TOKENLY_USERNAME_COLUMN_NAME);
        /**
         * Table Song addition.
         */
        DeveloperDatabaseTable songTable = developerObjectFactory.getNewDeveloperDatabaseTable(
                TokenlySongWalletDatabaseConstants.SONG_TABLE_NAME, songColumns);
        tables.add(songTable);

        /**
         * Table Synchronize columns.
         */
        List<String> synchronizeColumns = new ArrayList<>();

        synchronizeColumns.add(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_SYNC_ID_COLUMN_NAME);
        synchronizeColumns.add(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_DEVICE_SONGS_COLUMN_NAME);
        synchronizeColumns.add(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_SONGS_COLUMN_NAME);
        synchronizeColumns.add(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TOKENLY_USERNAME_COLUMN_NAME);
        synchronizeColumns.add(TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TIMESTAMP);
        /**
         * Table Synchronize addition.
         */
        DeveloperDatabaseTable synchronizeTable = developerObjectFactory.getNewDeveloperDatabaseTable(
                TokenlySongWalletDatabaseConstants.SYNCHRONIZE_TABLE_NAME, synchronizeColumns);
        tables.add(synchronizeTable);

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

//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
}