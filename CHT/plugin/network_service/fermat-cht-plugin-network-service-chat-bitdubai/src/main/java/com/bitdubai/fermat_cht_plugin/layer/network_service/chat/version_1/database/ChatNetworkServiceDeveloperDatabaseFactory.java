package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.version_1.database;


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
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cht_plugin.layer.network_service.chat.version_1.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Gabriel Araujo - (gabe_512@hotmail.com) on 30/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
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
            ChatNetworkServiceDatabaseFactory chatNetworkServiceDatabaseFactory = new ChatNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = chatNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
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
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Chat", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table incoming messages columns.
         */
        List<String> incomingmessagesColumns = new ArrayList<String>();

        incomingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME);
        incomingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME);
        incomingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME);
        incomingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME);
        incomingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME);
        incomingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
        incomingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
        incomingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME);
        /**
         * Table incoming messages addition.
         */
        DeveloperDatabaseTable incomingmessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(ChatNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME, incomingmessagesColumns);
        tables.add(incomingmessagesTable);

        /**
         * Table outgoing messages columns.
         */
        List<String> outgoingmessagesColumns = new ArrayList<String>();

        outgoingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME);
        outgoingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME);
        outgoingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME);
        outgoingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME);
        outgoingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TYPE_COLUMN_NAME);
        outgoingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
        outgoingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
        outgoingmessagesColumns.add(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME);
        /**
         * Table outgoing messages addition.
         */
        DeveloperDatabaseTable outgoingmessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(ChatNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME, outgoingmessagesColumns);
        tables.add(outgoingmessagesTable);

        /**
         * Table chat_metadata_transaction columns.
         */
        List<String> chat_metadata_transactionColumns = new ArrayList<String>();

        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_CHAT_ID_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_SENDER_ID_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_SENDER_TYPE_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_RECEIVER_ID_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_RECEIVER_TYPE_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_META_DATA_XML_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_TYPE_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_DISTRIBUTION_STATUS_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_TIMESTAMP_COLUMN_NAME);
        chat_metadata_transactionColumns.add(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_PROCESSED_COLUMN_NAME);
        /**
         * Table chat_metadata_transaction addition.
         */
        DeveloperDatabaseTable chat_metadata_transactionTable = developerObjectFactory.getNewDeveloperDatabaseTable(ChatNetworkServiceDatabaseConstants.CHAT_METADATA_TRANSACTION_TABLE_NAME, chat_metadata_transactionColumns);
        tables.add(chat_metadata_transactionTable);



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
