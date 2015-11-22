/*
 * @#CommunicationsCloudServerP2PDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.database;

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
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.p2p.communications_cloud_server.developer.bitdubai.version_1.database.Communications Cloud ServerP2PDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationsCloudServerP2PDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public CommunicationsCloudServerP2PDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
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
             * Create known servers catalog table.
             */
            table = databaseFactory.newTableFactory(ownerId, CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_TABLE_NAME);

            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_IP_COLUMN_NAME, DatabaseDataType.STRING, 19, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_DEFAULT_PORT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_WEB_SERVICE_PORT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LATITUDE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LONGITUDE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_OFFLINE_COUNTER_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(CommunicationsCloudServerP2PDatabaseConstants.KNOWN_SERVERS_CATALOG_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create platform component profile registered history table.
             */
            table = databaseFactory.newTableFactory(ownerId, CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_TABLE_NAME);

            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_COMPONENT_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_EXTRA_DATA_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(CommunicationsCloudServerP2PDatabaseConstants.PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_FIRST_KEY_COLUMN);

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
