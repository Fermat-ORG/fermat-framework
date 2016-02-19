package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 18/12/15.
 */
public class ChatNetworkServiceDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public ChatNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
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
            throw new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            ChatNetworkServiceDatabaseFactory chatNetworkServiceDatabaseFactory = new ChatNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = chatNetworkServiceDatabaseFactory.createDatabase(pluginId, ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeChatNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
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
         * Table Chat columns.
         */
        List<String> incomingChatColumns = new ArrayList<String>();

        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDCHAT_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDOBJECTO_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORTYPE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_REMOTEACTORTYPE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_CHATNAME_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_CHATSTATUS_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_MESSAGE_STATUS_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DATE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_IDMENSAJE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_MESSAGE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROTOCOL_STATE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_SENTDATE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME);
        /**
         * Table Outgoing Chat addition.
         */
        DeveloperDatabaseTable incomingChatTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_CHAT_TABLE_NOTIFICATION_TABLE_NAME, incomingChatColumns);
        tables.add(incomingChatTable);

        List<String> outgoingChatColumns = new ArrayList<String>();

        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_TRANSACTION_ID_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_TRANSACTION_HASH_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_IDCHAT_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_IDOBJECTO_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_LOCALACTORTYPE_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_LOCALACTORPUBKEY_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_REMOTEACTORTYPE_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_CHATNAME_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_CHATSTATUS_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_MESSAGE_STATUS_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_DATE_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_IDMENSAJE_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_MESSAGE_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_PROTOCOL_STATE_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_SENTDATE_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_READ_MARK_COLUMN_NAME);
        outgoingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_CHAT_SENT_COUNT_COLUMN_NAME);
        /**
         * Table Outgoing Chat addition.
         */
        DeveloperDatabaseTable outgoingChtTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_TABLE_NOTIFICATION_TABLE_NAME, outgoingChatColumns);
        tables.add(outgoingChtTable);


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

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}