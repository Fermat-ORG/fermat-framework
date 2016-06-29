package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database;

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
 * The Class  <code>com.bitdubai.fermat_pip_plugin.layer.network_service.system_monitor.developer.bitdubai.version_1.database.System MonitorNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Miguel Celedon - (miguelceledon@outlook.com) on 14/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SystemMonitorNetworkServiceDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public SystemMonitorNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create System Data table.
             */
            table = databaseFactory.newTableFactory(ownerId, SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_TABLE_NAME);

            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_SYSTEM_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_NODE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_HARDWARE_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_OS_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);

            table.addIndex(SystemMonitorNetworkServiceDatabaseConstants.SYSTEM_DATA_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Connections table.
             */
            table = databaseFactory.newTableFactory(ownerId, SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_TABLE_NAME);

            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_CONNID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_PEERID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_PEER_IPV4_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_PEER_IPV6_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_NETWORK_SERVICE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);

            table.addIndex(SystemMonitorNetworkServiceDatabaseConstants.CONNECTIONS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Services table.
             */
            table = databaseFactory.newTableFactory(ownerId, SystemMonitorNetworkServiceDatabaseConstants.SERVICES_TABLE_NAME);

            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_SUBTYPE_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);

            table.addIndex(SystemMonitorNetworkServiceDatabaseConstants.SERVICES_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }


            table = databaseFactory.newTableFactory(ownerId, SystemMonitorNetworkServiceDatabaseConstants.PLATFORM_COMPONENTS_TABLE_NAME);

            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_ID_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.TRUE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_NAME_COLUMN_NAME, DatabaseDataType.STRING, 40, Boolean.FALSE);
            table.addColumn(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);

            table.addIndex(SystemMonitorNetworkServiceDatabaseConstants.COMPONENT_FIRST_KEY_COLUMN);

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
