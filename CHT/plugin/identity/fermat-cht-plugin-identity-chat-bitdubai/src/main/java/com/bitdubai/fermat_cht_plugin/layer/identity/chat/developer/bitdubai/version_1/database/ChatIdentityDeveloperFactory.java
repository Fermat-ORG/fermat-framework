package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatIdentityDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>ChatIdentityDeveloperFactory.java
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Franklin Marcano - (franklinmarcano970@gmail.com) on 30-03-2016.
 * Edited by Miguel Rincon on 19/04/2016
 *
 * @version 1.0
 * @since Java JDK 1.7
 */public class ChatIdentityDeveloperFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    private ErrorManager errorManager;

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
    public ChatIdentityDeveloperFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    public void initializeDatabase() throws CantInitializeChatIdentityDatabaseException
    {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, ChatIdentityDatabaseConstants.CHAT_DATABASE_NAME);
            database.closeDatabase();

        }catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(cantOpenDatabaseException));
             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeChatIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        }catch (DatabaseNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(e));
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            ChatIdentityDatabaseFactory chatIdentityDatabaseFactory = new ChatIdentityDatabaseFactory(this.pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = chatIdentityDatabaseFactory.createDatabase(pluginId, ChatIdentityDatabaseConstants.CHAT_DATABASE_NAME);
                database.closeDatabase();
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(cantCreateDatabaseException));
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeChatIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(ChatIdentityDatabaseConstants.CHAT_DATABASE_NAME, ChatIdentityDatabaseConstants.CHAT_DATABASE_NAME));

        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Project columns.
         */
        List<String> projectColumns = new ArrayList<String>();

        /**
         * Table Chat Identity addition.
         */
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_PUBLIC_KEY_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_ALIAS_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_EXPOSURE_LEVEL_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_COUNTRY_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_STATE_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_CITY_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_CONNECTION_STATE_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_ACCURACY_COLUMN_NAME);
        projectColumns.add(ChatIdentityDatabaseConstants.CHAT_FRECUENCY_COLUMN_NAME);

        DeveloperDatabaseTable chatIdentityTable = developerObjectFactory.getNewDeveloperDatabaseTable(ChatIdentityDatabaseConstants.CHAT_TABLE_NAME, projectColumns);
        tables.add(chatIdentityTable);

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
