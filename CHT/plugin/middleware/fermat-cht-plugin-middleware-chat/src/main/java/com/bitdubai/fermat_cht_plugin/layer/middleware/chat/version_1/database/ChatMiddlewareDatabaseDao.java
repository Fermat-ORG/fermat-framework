package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.exceptions.CantAddContact;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.exceptions.CantInitializeChatMiddlewareDaoException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.structure.chtmiddlewaremanager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by miguel payarez miguel_payarez@hotmail.com on 06/01/16.
 */
public class ChatMiddlewareDatabaseDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;


    //Constructor

    public ChatMiddlewareDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    public void initialize(UUID pluginId) throws CantInitializeChatMiddlewareDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId,pluginId.toString());
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeChatMiddlewareDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeChatMiddlewareDaoException(CantInitializeChatMiddlewareDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void addContact(String idContact,String remoteName,String remoteActorPubKey,String remoteActorType,
                           String alias,
                           String creationDate
    ) throws CantAddContact {
        try {
            DatabaseTable table = this.database.getTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_ID_CONTACT_COLUMN_NAME,idContact);
            record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_NAME_COLUMN_NAME,remoteName);
            record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_TYPE_COLUMN_NAME,remoteActorType);
            record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME,remoteActorPubKey);
            record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_ALIAS_COLUMN_NAME,alias);
            record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CREATION_DATE_COLUMN_NAME,creationDate);
            table.insertRecord(record);
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddContact(CantAddContact.DEFAULT_MESSAGE, e, "Cant Add Contact Exception", "Cant Insert Record Exception");
        }
    }

    public List<String> getContactDetail(String idChat) throws CantLoadTableToMemoryException {
        List<String> field = new ArrayList<>();
        DatabaseTable chattable =this.database.getTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
        chattable.addStringFilter(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, idChat, DatabaseFilterType.EQUAL);
        chattable.loadToMemory();
        List<DatabaseTableRecord> records = chattable.getRecords();
        chattable.clearAllFilters();

        for (DatabaseTableRecord record : records) {
            field.add(record.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CHAT_NAME_COLUMN_NAME));
            field.add(record.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME));
            field.add(record.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME));
            field.add(record.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME));
            field.add(record.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_TYPE_COLUMN_NAME));
            field.add(record.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME));
            field.add(record.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_TYPE_COLUMN_NAME));
            field.add(record.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME));


        }

        return field;
    }





    private void updateReadMessage(String idChat, String idMessage) throws
            UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {

        try {
            DatabaseTable databaseTable = this.database.getTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME,
                    idChat,
                    DatabaseFilterType.EQUAL);
            databaseTable.addStringFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MENSAJE_COLUMN_NAME,
                    idMessage,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME, "true");
            databaseTable.updateRecord(record);
        } catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Updating parameter " + true, "");
        }
    }

    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws
            UnexpectedResultReturnedFromDatabaseException {
        /**
         * Represents the maximum number of records in <code>records</code>
         * I'm gonna set this number in 1 for now, because I want to check the records object has
         * one only result.
         */
        int VALID_RESULTS_NUMBER=1;
        int recordsSize;
        if(records.isEmpty()){
            return;
        }
        recordsSize=records.size();
        if(recordsSize>VALID_RESULTS_NUMBER){
            throw new UnexpectedResultReturnedFromDatabaseException("I excepted "+VALID_RESULTS_NUMBER+", but I got "+recordsSize);
        }
    }




}