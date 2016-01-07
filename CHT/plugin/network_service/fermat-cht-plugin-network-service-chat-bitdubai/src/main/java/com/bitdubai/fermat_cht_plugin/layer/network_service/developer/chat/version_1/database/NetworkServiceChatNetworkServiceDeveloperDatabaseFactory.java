
package com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.database;

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
import com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cht_plugin.layer.network_service.network_service_chat.developer.bitdubai.version_1.database.NetworkServiceChatNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Gabriel Araujo - (gabe_512@hotmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class NetworkServiceChatNetworkServiceDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public NetworkServiceChatNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
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
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

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
            NetworkServiceChatNetworkServiceDatabaseFactory networkServiceChatNetworkServiceDatabaseFactory = new NetworkServiceChatNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = networkServiceChatNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
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
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Network Service Chat", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Outgoing Chat columns.
         */
        List<String> outgoingChatColumns = new ArrayList<String>();

        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_IDCHAT_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_IDOBJECTO_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_LOCALACTORTYPE_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_LOCALACTORPUBKEY_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_REMOTEACTORTYPE_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_CHATNAME_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_CHATSTATUS_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_DATE_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_IDMENSAJE_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_COLUMN_NAME);
        outgoingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME);
        /**
         * Table Outgoing Chat addition.
         */
        DeveloperDatabaseTable outgoingChatTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_TABLE_NAME, outgoingChatColumns);
        tables.add(outgoingChatTable);

        /**
         * Table Incoming Chat columns.
         */
        List<String> incomingChatColumns = new ArrayList<String>();

        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_IDCHAT_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_IDOBJECTO_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_LOCALACTORTYPE_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_LOCALACTORPUBKEY_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_REMOTEACTORTYPE_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_CHATNAME_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_CHATSTATUS_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_DATE_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_IDMENSAJE_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_COLUMN_NAME);
        incomingChatColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME);
        /**
         * Table Incoming Chat addition.
         */
        DeveloperDatabaseTable incomingChatTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_TABLE_NAME, incomingChatColumns);
        tables.add(incomingChatTable);

        /**
         * Table Incoming Chat Message columns.
         */
        List<String> incomingChatMessageColumns = new ArrayList<String>();

        incomingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_IDMENSAJE_COLUMN_NAME);
        incomingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_IDCHAT_COLUMN_NAME);
        incomingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_MESSAGE_COLUMN_NAME);
        incomingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_DATE_COLUMN_NAME);
        incomingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_DISTRIBUTIONSTATUS_COLUMN_NAME);
        /**
         * Table Incoming Chat Message addition.
         */
        DeveloperDatabaseTable incomingChatMessageTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_TABLE_NAME, incomingChatMessageColumns);
        tables.add(incomingChatMessageTable);

        /**
         * Table Outgoing Chat Message columns.
         */
        List<String> outgoingChatMessageColumns = new ArrayList<String>();

        outgoingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_IDMENSAJE_COLUMN_NAME);
        outgoingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_IDCHAT_COLUMN_NAME);
        outgoingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_MESSAGE_COLUMN_NAME);
        outgoingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_DATE_COLUMN_NAME);
        outgoingChatMessageColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_DISTRIBUTIONSTATUS_COLUMN_NAME);
        /**
         * Table Outgoing Chat Message addition.
         */
        DeveloperDatabaseTable outgoingChatMessageTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_TABLE_NAME, outgoingChatMessageColumns);
        tables.add(outgoingChatMessageTable);

        /**
         * Table Incoming Chat Message Notification Status columns.
         */
        List<String> incomingChatMessageNotificationStatusColumns = new ArrayList<String>();

        incomingChatMessageNotificationStatusColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDMENSAJE_COLUMN_NAME);
        incomingChatMessageNotificationStatusColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDCHAT_COLUMN_NAME);
        incomingChatMessageNotificationStatusColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_DATE_COLUMN_NAME);
        incomingChatMessageNotificationStatusColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_DISTRIBUTIONSTATUS_COLUMN_NAME);
        /**
         * Table Incoming Chat Message Notification Status addition.
         */
        DeveloperDatabaseTable incomingChatMessageNotificationStatusTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_TABLE_NAME, incomingChatMessageNotificationStatusColumns);
        tables.add(incomingChatMessageNotificationStatusTable);

        /**
         * Table Outgoing Chat Message Notification Status columns.
         */
        List<String> outgoingChatMessageNotificationStatusColumns = new ArrayList<String>();

        outgoingChatMessageNotificationStatusColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDMENSAJE_COLUMN_NAME);
        outgoingChatMessageNotificationStatusColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDCHAT_COLUMN_NAME);
        outgoingChatMessageNotificationStatusColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_DATE_COLUMN_NAME);
        outgoingChatMessageNotificationStatusColumns.add(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_DISTRIBUTIONSTATUS_COLUMN_NAME);
        /**
         * Table Outgoing Chat Message Notification Status addition.
         */
        DeveloperDatabaseTable outgoingChatMessageNotificationStatusTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_TABLE_NAME, outgoingChatMessageNotificationStatusColumns);
        tables.add(outgoingChatMessageNotificationStatusTable);



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