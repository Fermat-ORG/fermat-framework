package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMesssageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatMiddlewareDaoException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by miguel payarez miguel_payarez@hotmail.com on 06/01/16.
 * Modified by Franklin Marcano 09-01-2016
 */
public class ChatMiddlewareDatabaseDao {

    //TODO: Documentar
    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /**
     * Constructor
     */
    public ChatMiddlewareDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId;

    }

    public List<Contact> getContacts() throws CantGetContactException
    {
        return null;
    }

    public Contact getContactByContactId(UUID contactId) throws CantGetContactException
    {
        return null;
    }

    public Contact newEmptyInstanceContact() throws CantNewEmptyContactException
    {
        return null;
    }

    public void saveContact(Contact contact) throws CantSaveContactException
    {

    }

    public void deleteContact(Contact contact) throws CantDeleteContactException
    {

    }

    public List<Chat> getChats() throws CantGetChatException
    {
        return null;
    }

    public Chat getChatByChatId(UUID chatId) throws CantGetChatException
    {
        return null;
    }
    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException
    {
        return null;
    }

    public void saveChat(Chat chat) throws CantSaveChatException
    {

    }

    public void deleteChat(Chat chat) throws CantDeleteChatException
    {

    }

    public List<Message> getMessages() throws CantGetMessageException
    {
        return null;
    }

    public Message getMessageByChatId(UUID chatId) throws CantGetMessageException
    {
        return null;
    }

    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException
    {
        return null;
    }

    public Chat newEmptyInstanceMessage() throws CantNewEmptyMessageException
    {
        return null;
    }

    public void saveMessage(Message message) throws CantSaveMessageException
    {

    }

    public void deleteMessage(Message message) throws CantDeleteMesssageException
    {

    }


    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, ChatMiddlewareDatabaseConstants.DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            ChatMiddlewareDatabaseFactory chatMiddlewareDatabaseFactory = new ChatMiddlewareDatabaseFactory(pluginDatabaseSystem);
            database = chatMiddlewareDatabaseFactory.createDatabase(this.pluginId, ChatMiddlewareDatabaseConstants.DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        if (table.getRecords().isEmpty())
            return true;
        else
            return false;
    }

    private DatabaseTableRecord getContactRecord(Contact contact) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CONTACTS_ID_CONTACT_COLUMN_NAME, contact.getContactId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_ALIAS_COLUMN_NAME, contact.getAlias());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_NAME_COLUMN_NAME, contact.getRemoteName());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_TYPE_COLUMN_NAME, contact.getRemoteActorType());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, contact.getRemoteActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CREATION_DATE_COLUMN_NAME, contact.getCreationDate().toString());

        return record;
    }

    private DatabaseTableRecord getChatRecord(Chat chat) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, chat.getChatId());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_OBJECT_COLUMN_NAME, chat.getObjectId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CHAT_NAME_COLUMN_NAME, chat.getChatName());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_TYPE_COLUMN_NAME, chat.getLocalActorType());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME, chat.getLocalActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_TYPE_COLUMN_NAME, chat.getRemoteActorType());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, chat.getRemoteActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME, chat.getStatus().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME, chat.getDate().toString());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME, chat.getLastMessageDate().toString());

        return record;
    }

    private DatabaseTableRecord getMessageRecord(Message message) throws DatabaseOperationException
    {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME, message.getMessageId());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, message.getChatId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TEXT_MESSAGE_COLUMN_NAME, message.getMessage());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME, message.getStatus().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME, message.getType().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME, message.getMessageDate().toString());

        return record;
    }

    private List<DatabaseTableRecord> getChatData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getMessageData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private List<DatabaseTableRecord> getContactData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException
    {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private Chat getChatTransaction(final DatabaseTableRecord chatTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException
    {
        ChatImpl chat = new ChatImpl();
        //TODO:Implementar
        return chat;
    }

    private Message getMessageTransaction(final DatabaseTableRecord messageTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        MessageImpl message = new MessageImpl();

        message.setChatId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME));
        message.setMessageId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME));
        message.setMessage(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TEXT_MESSAGE_COLUMN_NAME));
        message.setMessageDate(Date.valueOf(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME)));
        message.setStatus(MessageStatus.getByCode(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME)));
        message.setType(TypeMessage.getByCode(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME)));

        return message;
    }

    private Contact getContactTransaction(final DatabaseTableRecord contactTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException
    {
        //TODO:Implementar
        ContactImpl contact = new ContactImpl();
        return contact;
    }



    //Eliminar
    //TODO: Added CHTException, CantAddContact exception does not exists
    public void addContact(String idContact,String remoteName,String remoteActorPubKey,String remoteActorType,
                           String alias,
                           String creationDate
    ) throws CHTException {
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
            throw new CHTException(CHTException.DEFAULT_MESSAGE, e, "Cant Add Contact Exception", "Cant Insert Record Exception");
        }
    }

    public List<String> getContactDetail(String idChat) throws CantLoadTableToMemoryException {
        List<String> field = new ArrayList<>();
        DatabaseTable chatDatabaseTable =this.database.getTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
        chatDatabaseTable.addStringFilter(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, idChat, DatabaseFilterType.EQUAL);
        chatDatabaseTable.loadToMemory();
        List<DatabaseTableRecord> records = chatDatabaseTable.getRecords();
        chatDatabaseTable.clearAllFilters();

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
            databaseTable.addStringFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME,
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