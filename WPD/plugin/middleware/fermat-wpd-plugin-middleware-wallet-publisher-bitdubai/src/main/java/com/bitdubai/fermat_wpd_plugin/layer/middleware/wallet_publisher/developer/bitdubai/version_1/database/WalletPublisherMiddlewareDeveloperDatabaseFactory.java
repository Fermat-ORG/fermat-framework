/*
 * @#WalletPublisherMiddlewareDeveloperDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInitializeWalletPublisherMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletPublisherMiddlewareDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public WalletPublisherMiddlewareDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeWalletPublisherMiddlewareDatabaseException
     */
    public void initializeDatabase() throws CantInitializeWalletPublisherMiddlewareDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeWalletPublisherMiddlewareDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletPublisherMiddlewareDatabaseFactory walletPublisherMiddlewareDatabaseFactory = new WalletPublisherMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = walletPublisherMiddlewareDatabaseFactory.createDatabase(pluginId, WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletPublisherMiddlewareDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table INFORMATION PUBLISHED COMPONENTS columns.
         */
        List<String> InformationPublishedComponentsColumns = new ArrayList<String>();

        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_ID_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_WFP_ID_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_WFP_NAME_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_DESCRIPTIONS_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_ICON_IMG_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_MAIN_SCREEN_SHOT_IMG_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_VIDEO_URL_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_STATUS_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_STATUS_TIMESTAMP_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLICATION_TIMESTAMP_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        InformationPublishedComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_SIGNATURE_COLUMN_NAME);
        /**
         * Table INFORMATION PUBLISHED COMPONENTS addition.
         */
        DeveloperDatabaseTable iNFORMATIONPUBLISHEDCOMPONENTSTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_TABLE_NAME, InformationPublishedComponentsColumns);
        tables.add(iNFORMATIONPUBLISHEDCOMPONENTSTable);

        /**
         * Table COMPONENT VERSIONS DETAILS columns.
         */
        List<String> componentVersionsDetailsColumns = new ArrayList<String>();

        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_SCREEN_SIZE_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_TIMESTAMP_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_WALLET_VERSION_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_WALLET_VERSION_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_PLATFORM_VERSION_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_PLATFORM_VERSION_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_OBSERVATIONS_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_CATALOG_ID_COLUMN_NAME);
        componentVersionsDetailsColumns.add(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_COMPONENT_ID_COLUMN_NAME);
        /**
         * Table COMPONENT VERSIONS DETAILS addition.
         */
        DeveloperDatabaseTable cOMPONENTVERSIONSDETAILSTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME, componentVersionsDetailsColumns);
        tables.add(cOMPONENTVERSIONSDETAILSTable);

        /**
         * Table SCREENS SHOTS COMPONENTS columns.
         */
        List<String> screensShotsComponentsColumns = new ArrayList<String>();

        screensShotsComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FILE_ID_COLUMN_NAME);
        screensShotsComponentsColumns.add(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_COMPONENT_ID_COLUMN_NAME);
        /**
         * Table SCREENS SHOTS COMPONENTS addition.
         */
        DeveloperDatabaseTable sCREENSSHOTSCOMPONENTSTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_TABLE_NAME, screensShotsComponentsColumns);
        tables.add(sCREENSSHOTSCOMPONENTSTable);



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