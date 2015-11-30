/*
* @#NetworkClientP2PDatabaseFactory.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.database;

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
 *  The Class  <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.database.NetworkClientP2PDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientP2PDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public NetworkClientP2PDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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

            /**
             * Create Node Connection history table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_TABLE_NAME);

            table.addColumn(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IP_COLUMN_NAME, DatabaseDataType.STRING, 19, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_DEFAULT_PORT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            table.addIndex(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_FIRST_KEY_COLUMN);

            /**
             * Create Client Connection history table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_TABLE_NAME);

            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_NAME_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_COMPONENT_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_EXTRA_DATA_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            table.addIndex(NetworkClientP2PDatabaseConstants.CLIENT_CONNECTION_HISTORY_FIRST_KEY_COLUMN);

            try {
                //Create the table
                ((DatabaseFactory) database).createTable(ownerId, table);
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
