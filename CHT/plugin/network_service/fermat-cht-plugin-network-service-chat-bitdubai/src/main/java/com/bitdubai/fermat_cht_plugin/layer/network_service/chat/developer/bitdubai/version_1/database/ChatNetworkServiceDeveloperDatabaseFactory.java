package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 18/12/15.
 */
public class ChatNetworkServiceDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    private ErrorManager errorManager;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public ChatNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeChatNetworkServiceDatabaseException
     */
    public void initializeDatabase() throws CantInitializeChatNetworkServiceDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());
            reportUnexpectedError(cantInitializeChatNetworkServiceDatabaseException);
            throw cantInitializeChatNetworkServiceDatabaseException;

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            ChatNetworkServiceDatabaseFactory chatNetworkServiceDatabaseFactory = new ChatNetworkServiceDatabaseFactory(pluginDatabaseSystem, errorManager);

            try {
                  /*
                   * We create the new database
                   */
                database = chatNetworkServiceDatabaseFactory.createDatabase(pluginId, ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
                reportUnexpectedError(cantInitializeChatNetworkServiceDatabaseException);
                throw cantInitializeChatNetworkServiceDatabaseException;
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();


        /**
         * incomingChatColumns Chat columns.
         */

        List<String> messageMetadata = new ArrayList<>();
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_ID_COLUMN_NAME);
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_TRANSACTION_TYPE_COLUMN_NAME);
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_LOCALACTORPUBKEY_COLUMN_NAME);
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_REMOTEACTORPUBKEY_COLUMN_NAME);
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_IDMESSAGE_COLUMN_NAME);
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_MESSAGE_COLUMN_NAME);
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_MESSAGE_STATUS_COLUMN_NAME);
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_DATE_COLUMN_NAME);
        messageMetadata.add(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_SENTDATE_COLUMN_NAME);

        DeveloperDatabaseTable messageMetadataTable = developerObjectFactory.getNewDeveloperDatabaseTable(ChatNetworkServiceDataBaseConstants.MESSAGE_METADATA_TRANSACTION_RECORD_TABLE, messageMetadata);
        tables.add(messageMetadataTable);

        List<String> event = new ArrayList<>();
        event.add(ChatNetworkServiceDataBaseConstants.PACKAGE_ID_RECORD_COLUMN_NAME);
        event.add(ChatNetworkServiceDataBaseConstants.SENDER_PUBLIC_KEY_COLUMN_NAME);
        event.add(ChatNetworkServiceDataBaseConstants.DESTINATION_PUBLICK_KEY_COLUMN_NAME);

        DeveloperDatabaseTable eventTable = developerObjectFactory.getNewDeveloperDatabaseTable(ChatNetworkServiceDataBaseConstants.P2P_CLIENT_EVENT_RECORD_TABLE, event);
        tables.add(eventTable);
        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given incomingChatColumns
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        /**
         * I load the passed incomingChatColumns name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row : records) {
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the incomingChatColumns list
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
             * return the list of DeveloperRecords for the passed incomingChatColumns.
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