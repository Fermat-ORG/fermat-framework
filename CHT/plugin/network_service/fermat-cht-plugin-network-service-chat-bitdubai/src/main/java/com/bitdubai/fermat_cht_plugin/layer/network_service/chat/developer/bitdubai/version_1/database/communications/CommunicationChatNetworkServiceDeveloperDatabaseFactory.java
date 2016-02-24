
package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cht_plugin.layer.network_service.network_service_chat.developer.bitdubai.version_1.database.CommunicationChatNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Gabriel Araujo - (gabe_512@hotmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CommunicationChatNetworkServiceDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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

    public CommunicationChatNetworkServiceDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ErrorManager errorManager) {
      this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
    }

    public void reportUnexpectedException(FermatException e){
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
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationChatNetworkServiceDatabaseConstants.DATA_BASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());
            reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
            throw cantInitializeChatNetworkServiceDatabaseException;
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */

            CommunicationChatNetworkServiceDatabaseFactory communicationChatNetworkServiceDatabaseFactory = new CommunicationChatNetworkServiceDatabaseFactory(pluginDatabaseSystem,errorManager);
            try {
                  /*
                   * We create the new database
                   */
                database = communicationChatNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                CantInitializeChatNetworkServiceDatabaseException cantInitializeChatNetworkServiceDatabaseException = new CantInitializeChatNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
                reportUnexpectedException(cantInitializeChatNetworkServiceDatabaseException);
                throw cantInitializeChatNetworkServiceDatabaseException;
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
         * Table incoming messages columns.
         */
        List<String> incomingmessagesColumns = new ArrayList<String>();

        incomingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME);
        incomingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME);
        incomingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME);
        incomingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME);
        incomingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME);
        incomingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
        incomingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
        incomingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME);
        /**
         * Table incoming messages addition.
         */
        DeveloperDatabaseTable incomingmessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME, incomingmessagesColumns);
        tables.add(incomingmessagesTable);

        /**
         * Table outgoing messages columns.
         */
        List<String> outgoingmessagesColumns = new ArrayList<String>();

        outgoingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME);
        outgoingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME);
        outgoingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME);
        outgoingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME);
        outgoingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TYPE_COLUMN_NAME);
        outgoingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
        outgoingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
        outgoingmessagesColumns.add(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME);
        /**
         * Table outgoing messages addition.
         */
        DeveloperDatabaseTable outgoingmessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME, outgoingmessagesColumns);
        tables.add(outgoingmessagesTable);

        /**
         * Table Chat columns.
         */
        List<String> incomingChatColumns = new ArrayList<String>();

        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_ID_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TRANSACTION_HASH_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDCHAT_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDOBJECTO_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORTYPE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_LOCALACTORPUBKEY_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORTYPE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_REMOTEACTORPUBKEY_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_CHATNAME_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_CHATSTATUS_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_STATUS_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_DATE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_IDMENSAJE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_MESSAGE_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME);
        incomingChatColumns.add(CommunicationChatNetworkServiceDatabaseConstants.CHAT_PROCCES_STATUS_COLUMN_NAME);
        /**
         * Table Outgoing Chat addition.
         */
        DeveloperDatabaseTable incomingChatTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationChatNetworkServiceDatabaseConstants.CHAT_TABLE_NAME, incomingChatColumns);
        tables.add(incomingChatTable);


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