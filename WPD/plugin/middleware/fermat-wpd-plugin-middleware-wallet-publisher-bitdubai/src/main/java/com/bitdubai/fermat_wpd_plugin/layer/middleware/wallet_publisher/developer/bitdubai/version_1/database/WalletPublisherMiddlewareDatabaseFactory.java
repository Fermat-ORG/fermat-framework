/*
 * @#WalletPublisherMiddlewareDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.Wallet PublisherMiddlewareDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublisherMiddlewareDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public WalletPublisherMiddlewareDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create INFORMATION PUBLISHED COMPONENTS table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_TABLE_NAME);

            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_WFP_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_WFP_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_DESCRIPTIONS_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_ICON_IMG_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_MAIN_SCREEN_SHOT_IMG_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_VIDEO_URL_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_STATUS_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLICATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_SIGNATURE_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);

            table.addIndex(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create COMPONENT VERSIONS DETAILS table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);

            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_SCREEN_SIZE_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_WALLET_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_WALLET_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_PLATFORM_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_PLATFORM_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_OBSERVATIONS_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_CATALOG_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_COMPONENT_ID_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addIndex(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create SCREENS SHOTS COMPONENTS table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_TABLE_NAME);

            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FILE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_COMPONENT_ID_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addIndex(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}