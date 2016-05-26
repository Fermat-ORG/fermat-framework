package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.CantInitializeWalletFactoryMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 17/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletFactoryMiddlewareDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public WalletFactoryMiddlewareDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeWalletFactoryMiddlewareDatabaseException
     */
    public void initializeDatabase() throws CantInitializeWalletFactoryMiddlewareDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, WalletFactoryMiddlewareDatabaseConstants.DATABASE_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeWalletFactoryMiddlewareDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletFactoryMiddlewareDatabaseFactory walletFactoryMiddlewareDatabaseFactory = new WalletFactoryMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = walletFactoryMiddlewareDatabaseFactory.createDatabase(pluginId, WalletFactoryMiddlewareDatabaseConstants.DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletFactoryMiddlewareDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(WalletFactoryMiddlewareDatabaseConstants.DATABASE_NAME, WalletFactoryMiddlewareDatabaseConstants.DATABASE_NAME));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Project columns.
         */
        List<String> projectColumns = new ArrayList<String>();

        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLICKEY_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETTYPE_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLETCATEGORY_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_FACTORYPROJECTTYPE_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_CREATION_TIMESTAMP_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_MODIFICATION_TIMESTAMP_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SIZE_COLUMN_NAME);
        /**
         * Table Project addition.
         */
        DeveloperDatabaseTable projectTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME, projectColumns);
        tables.add(projectTable);

        /**
         * Table skin columns.
         */
        List<String> skinColumns = new ArrayList<String>();

        skinColumns.add(WalletFactoryMiddlewareDatabaseConstants.SKIN_PROJECT_PUBLICKEY_COLUMN_NAME);
        skinColumns.add(WalletFactoryMiddlewareDatabaseConstants.SKIN_SKIN_ID_COLUMN_NAME);
        skinColumns.add(WalletFactoryMiddlewareDatabaseConstants.SKIN_DEFAULT_COLUMN_NAME);
        /**
         * Table skin addition.
         */
        DeveloperDatabaseTable skinTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.SKIN_TABLE_NAME, skinColumns);
        tables.add(skinTable);

        /**
         * Table language columns.
         */
        List<String> languageColumns = new ArrayList<String>();

        languageColumns.add(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_PROJECT_PUBLICKEY_COLUMN_NAME);
        languageColumns.add(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_LANGUAGE_ID_COLUMN_NAME);
        languageColumns.add(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_DEFAULT_COLUMN_NAME);
        /**
         * Table language addition.
         */
        DeveloperDatabaseTable languageTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.LANGUAGE_TABLE_NAME, languageColumns);
        tables.add(languageTable);

        /**
         * Table navigation_structure columns.
         */
        List<String> navigation_structureColumns = new ArrayList<String>();

        navigation_structureColumns.add(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PROJECT_PUBLICKEY_COLUMN_NAME);
        navigation_structureColumns.add(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_PUBLICKEY_COLUMN_NAME);
        /**
         * Table navigation_structure addition.
         */
        DeveloperDatabaseTable navigation_structureTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.NAVIGATION_STRUCTURE_TABLE_NAME, navigation_structureColumns);
        tables.add(navigation_structureTable);



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