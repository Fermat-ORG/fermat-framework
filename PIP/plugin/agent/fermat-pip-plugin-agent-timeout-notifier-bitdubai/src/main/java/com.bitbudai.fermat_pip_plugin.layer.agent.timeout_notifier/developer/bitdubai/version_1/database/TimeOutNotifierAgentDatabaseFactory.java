package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * The Class  <code>com.bitdubai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOut NotifierAgentDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Acosta Rodrigo - (acosta_rodrigo@hotmail.com) on 28/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TimeOutNotifierAgentDatabaseFactory {

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
    public TimeOutNotifierAgentDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId the owner id
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, TimeOutNotifierAgentDatabaseConstants.DATABASE_NAME);
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
             * Create agents table.
             */
            table = databaseFactory.newTableFactory(ownerId, TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);

            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_START_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_END_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_DURATION_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_READ_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.AGENTS_LAST_UPDATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(TimeOutNotifierAgentDatabaseConstants.AGENTS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create agent_owner table.
             */
            table = databaseFactory.newTableFactory(ownerId, TimeOutNotifierAgentDatabaseConstants.OWNER_TABLE_NAME);

            table.addColumn(TimeOutNotifierAgentDatabaseConstants.OWNER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.OWNER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.OWNER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(TimeOutNotifierAgentDatabaseConstants.OWNER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create event_monitor table.
             */
            table = databaseFactory.newTableFactory(ownerId, TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_TABLE_NAME);

            table.addColumn(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AGENT_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AMOUNT_RAISE_COLUMN_NAME, DatabaseDataType.INTEGER, 0, Boolean.FALSE);
            table.addColumn(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_LAST_UPDATED_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_FIRST_KEY_COLUMN);

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
}
