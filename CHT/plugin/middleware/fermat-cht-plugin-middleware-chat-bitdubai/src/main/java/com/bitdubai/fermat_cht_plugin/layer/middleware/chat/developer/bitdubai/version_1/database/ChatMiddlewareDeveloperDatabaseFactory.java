package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database;


import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.database.ChatMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Miguel Payarez - (miguel_payarez@hotmail.com) on 05/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ChatMiddlewareDeveloperDatabaseFactory {

    private final Database database;

    /**
     * Constructor
     *
     * @param database
     */
    public ChatMiddlewareDeveloperDatabaseFactory(Database database) {
        this.database = database;
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Chat", "Chat"));
        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();
        /**
         * Table Chats columns.
         */
        List<String> chatsColumns = new ArrayList<String>();

        chatsColumns.add(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME);
        chatsColumns.add(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME);
        chatsColumns.add(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME);
        chatsColumns.add(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME);
        chatsColumns.add(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME);
        chatsColumns.add(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME);

        /**
         * Table Chats addition.
         */
        DeveloperDatabaseTable chatsTable = developerObjectFactory.getNewDeveloperDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME, chatsColumns);
        tables.add(chatsTable);

        /**
         * Table Message columns.
         */
        List<String> messageColumns = new ArrayList<String>();

        messageColumns.add(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME);
        messageColumns.add(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME);
        messageColumns.add(ChatMiddlewareDatabaseConstants.MESSAGE_TEXT_MESSAGE_COLUMN_NAME);
        messageColumns.add(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME);
        messageColumns.add(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME);
        messageColumns.add(ChatMiddlewareDatabaseConstants.MESSAGE_DATE_COLUMN_NAME);
        /**
         * Table Message addition.
         */
        DeveloperDatabaseTable messageTable = developerObjectFactory.getNewDeveloperDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME, messageColumns);
        tables.add(messageTable);

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
            return returnedRecords;
        } catch (Exception e) {
            return returnedRecords;
        }
        return returnedRecords;
    }

}