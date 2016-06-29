package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions.CantInitializeTimeOutNotifierAgentDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Acosta Rodrigo - (acosta_rodrigo@hotmail.com) on 28/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TimeOutNotifierAgentDeveloperDatabaseFactory {
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
    public TimeOutNotifierAgentDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeTimeOutNotifierAgentDatabaseException
     */
    public void initializeDatabase() throws CantInitializeTimeOutNotifierAgentDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, TimeOutNotifierAgentDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeTimeOutNotifierAgentDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            TimeOutNotifierAgentDatabaseFactory timeOutNotifierAgentDatabaseFactory = new TimeOutNotifierAgentDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = timeOutNotifierAgentDatabaseFactory.createDatabase(pluginId);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeTimeOutNotifierAgentDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(TimeOutNotifierAgentDatabaseConstants.DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table agents columns.
         */
        List<String> agentsColumns = new ArrayList<String>();

        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_NAME_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_DESCRIPTION_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_START_TIME_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_END_TIME_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_DURATION_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_READ_COLUMN_NAME);
        agentsColumns.add(TimeOutNotifierAgentDatabaseConstants.AGENTS_LAST_UPDATE_COLUMN_NAME);
        /**
         * Table agents addition.
         */
        DeveloperDatabaseTable agentsTable = developerObjectFactory.getNewDeveloperDatabaseTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME, agentsColumns);
        tables.add(agentsTable);

        /**
         * Table agent_owner columns.
         */
        List<String> agent_ownerColumns = new ArrayList<String>();

        agent_ownerColumns.add(TimeOutNotifierAgentDatabaseConstants.OWNER_PUBLICKEY_COLUMN_NAME);
        agent_ownerColumns.add(TimeOutNotifierAgentDatabaseConstants.OWNER_NAME_COLUMN_NAME);
        agent_ownerColumns.add(TimeOutNotifierAgentDatabaseConstants.OWNER_TYPE_COLUMN_NAME);
        /**
         * Table agent_owner addition.
         */
        DeveloperDatabaseTable agent_ownerTable = developerObjectFactory.getNewDeveloperDatabaseTable(TimeOutNotifierAgentDatabaseConstants.OWNER_TABLE_NAME, agent_ownerColumns);
        tables.add(agent_ownerTable);

        /**
         * Table event_monitor columns.
         */
        List<String> event_monitorColumns = new ArrayList<String>();

        event_monitorColumns.add(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AGENT_ID_COLUMN_NAME);
        event_monitorColumns.add(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AMOUNT_RAISE_COLUMN_NAME);
        event_monitorColumns.add(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_LAST_UPDATED_COLUMN_NAME);
        /**
         * Table event_monitor addition.
         */
        DeveloperDatabaseTable event_monitorTable = developerObjectFactory.getNewDeveloperDatabaseTable(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_TABLE_NAME, event_monitorColumns);
        tables.add(event_monitorTable);


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
            for (DatabaseTableRecord row : records) {
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()) {
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
        } catch (Exception e) {
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }
}
