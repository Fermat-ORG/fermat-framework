package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.EventRecord;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantGetPendingEventListException;
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
    public ChatMiddlewareDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem,
                                     UUID pluginId,
                                     Database database) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId;
        this.database             = database;

    }

    public List<Contact> getContacts(DatabaseTableFilter filter) throws CantGetContactException, DatabaseOperationException
    {
        //if filter is null all records
        Database database = null;
        try {
            database = openDatabase();
            List<Contact> contacts = new ArrayList<>();
            // I will add the contact information from the database
            for (DatabaseTableRecord record : getContactData(filter)) {
                final Contact contact = getContactTransaction(record);

                contacts.add(contact);
            }

            database.closeDatabase();

            return contacts;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Contact from the database with filter: " + filter.toString(), null);
        }
    }

    public Contact getContactByContactId(UUID contactId) throws CantGetContactException, DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Contact> contacts = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(contactId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.CONTACTS_FIRST_KEY_COLUMN);
            // I will add the contact information from the database
            for (DatabaseTableRecord record : getContactData(filter)) {
                final Contact contact = getContactTransaction(record);

                contacts.add(contact);
            }

            database.closeDatabase();

            return contacts.get(0);
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Contact from the database with filter: " + contactId.toString(), null);
        }
    }

    public Contact newEmptyInstanceContact() throws CantNewEmptyContactException
    {
        ContactImpl contact = new ContactImpl();
        contact.setContactId(UUID.randomUUID());
        return contact;
    }

    public void saveContact(Contact contact) throws CantSaveContactException, DatabaseOperationException {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);
            DatabaseTableRecord record = getContactRecord(contact);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(contact.getContactId().toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.CONTACTS_FIRST_KEY_COLUMN);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }

            //I execute the transaction and persist the database side of the Contact.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Contact Transaction in the database.", null);
        }
    }

    public void deleteContact(Contact contact) throws CantDeleteContactException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CONTACTS_TABLE_NAME);
            DatabaseTableRecord record = getContactRecord(contact);

            table.deleteRecord(record);

            //I execute the transaction and persist the database side of the Contact.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to delete the Contact Transaction in the database.", null);
        }
    }

    /**
     * This method returns all the chats recorded in database.
     * @return
     * @throws DatabaseOperationException
     * @throws CantGetChatException
     */
    public List<Chat> getChatList() throws
            DatabaseOperationException,
            CantGetChatException {
        /**
         * I'll pass null as argument to the next method to get all the chat list.
         */
        return getChats(null);
    }

    public List<Chat> getChats(DatabaseTableFilter filter) throws CantGetChatException, DatabaseOperationException
    {
        //if filter is null all records
        Database database = null;
        try {
            database = openDatabase();
            List<Chat> chats = new ArrayList<>();
            // I will add the contact information from the database
            for (DatabaseTableRecord record : getChatData(filter)) {
                final Chat chat = getChatTransaction(record);

                chats.add(chat);
            }

            database.closeDatabase();

            return chats;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Contact from the database with filter: " + filter.toString(), null);
        }
    }

    public Chat getChatByChatId(UUID chatId) throws CantGetChatException, DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Chat> chats = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.CHATS_FIRST_KEY_COLUMN);
            // I will add the contact information from the database
            for (DatabaseTableRecord record : getChatData(filter)) {
                final Chat chat = getChatTransaction(record);

                chats.add(chat);
            }

            database.closeDatabase();

            return chats.get(0);
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Contact from the database with filter: " + chatId.toString(), null);
        }
    }
    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException
    {
        ChatImpl chat = new ChatImpl();
        chat.setChatId(UUID.randomUUID());
        return chat;
    }

    public void saveChat(Chat chat) throws CantSaveChatException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableRecord record = getChatRecord(chat);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chat.getChatId().toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.CHATS_FIRST_KEY_COLUMN);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Chat Transaction in the database.", null);
        }
    }

    public void deleteChat(Chat chat) throws CantDeleteChatException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableRecord record = getChatRecord(chat);

            table.deleteRecord(record);

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to delete the Chat Transaction in the database.", null);
        }
    }

    /**
     * This method returns a message created list.
     * The messages in CREATED status are saved in database, but, are not sent through the Network
     * Service.
     * @return
     * @throws DatabaseOperationException
     * @throws CantGetMessageException
     */
    public List<Message> getCreatedMesages() throws
            DatabaseOperationException,
            CantGetMessageException {
        DatabaseTable databaseTable=getDatabaseTable(
                ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
        DatabaseTableFilter databaseTableFilter=databaseTable.getEmptyTableFilter();
        databaseTableFilter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME);
        databaseTableFilter.setType(DatabaseFilterType.EQUAL);
        databaseTableFilter.setValue(MessageStatus.CREATED.getCode());
        return getMessages(databaseTableFilter);
    }

    /**
     * This method returns the full messages list.
     * @return
     * @throws DatabaseOperationException
     * @throws CantGetMessageException
     */
    public List<Message> getMessages() throws
            DatabaseOperationException,
            CantGetMessageException {
        /**
         * I'll pass null as argument to the next method to get all the chat list.
         */
        return getMessages(null);
    }

    public List<Message> getMessages(DatabaseTableFilter filter) throws CantGetMessageException, DatabaseOperationException
    {
        //if filter is null all records
        Database database = null;
        try {
            database = openDatabase();
            List<Message> messages = new ArrayList<>();
            // I will add the message information from the database
            for (DatabaseTableRecord record : getMessageData(filter)) {
                final Message message = getMessageTransaction(record);

                messages.add(message);
            }

            database.closeDatabase();

            return messages;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get message from the database with filter: " + filter.toString(), null);
        }
    }

    public Message getMessageByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Message> messages = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME);
            // I will add the message information from the database
            for (DatabaseTableRecord record : getMessageData(filter)) {
                final Message message = getMessageTransaction(record);

                messages.add(message);
            }

            database.closeDatabase();

            return messages.get(0);
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Message from the database with filter: " + chatId.toString(), null);
        }
    }

    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException, DatabaseOperationException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<Message> messages = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(messageId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_FIRST_KEY_COLUMN);
            // I will add the message information from the database
            for (DatabaseTableRecord record : getMessageData(filter)) {
                final Message message = getMessageTransaction(record);

                messages.add(message);
            }

            database.closeDatabase();

            return messages.get(0);
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Message from the database with filter: " + messageId.toString(), null);
        }
    }

    public Message newEmptyInstanceMessage() throws CantNewEmptyMessageException
    {
        MessageImpl message = new MessageImpl();
        message.setMessageId(UUID.randomUUID());
        return message;
    }

    public void saveMessage(Message message) throws CantSaveMessageException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableRecord record = getMessageRecord(message);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(message.getMessageId().toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_FIRST_KEY_COLUMN);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, record);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the message Transaction in the database.", null);
        }
    }

    public void deleteMessage(Message message) throws CantDeleteMessageException, DatabaseOperationException
    {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableRecord record = getMessageRecord(message);

            table.deleteRecord(record);

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to delete the message Transaction in the database.", null);
        }
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

    private Chat getChatTransaction(final DatabaseTableRecord chatTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        ChatImpl chat = new ChatImpl();

        chat.setChatId(chatTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME));
        chat.setObjectId(chatTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_OBJECT_COLUMN_NAME));
        chat.setChatName(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CHAT_NAME_COLUMN_NAME));
        chat.setDate(Date.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME)));
        chat.setLastMessageDate(Date.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME)));
        chat.setRemoteActorPublicKey(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME));
        chat.setRemoteActorType(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_TYPE_COLUMN_NAME));
        chat.setLocalActorPublicKey(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME));
        chat.setLocalActorType(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_TYPE_COLUMN_NAME));
        chat.setStatus(ChatStatus.getByCode(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME)));

        return chat;
    }

    private Message getMessageTransaction(final DatabaseTableRecord messageTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException
    {
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
        ContactImpl contact = new ContactImpl();

        contact.setContactId(contactTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CONTACTS_ID_CONTACT_COLUMN_NAME));
        contact.setAlias(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_ALIAS_COLUMN_NAME));
        contact.setRemoteName(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_NAME_COLUMN_NAME));
        contact.setRemoteActorType(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_TYPE_COLUMN_NAME));
        contact.setRemoteActorPublicKey(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME));
        contact.setCreationDate(Date.valueOf(contactTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CONTACTS_CREATION_DATE_COLUMN_NAME)));

        return contact;
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

    /**
     * This method returns the event database table
     * @return
     */
    private DatabaseTable getDatabaseEventsTable() {
        return database.getTable(
                ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * This method saves a new event in database
     * @param eventType
     * @param eventSource
     * @throws CantSaveEventException
     */
    public void saveNewEvent(
            String eventType,
            String eventSource,
            UUID chatId) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            //Logger LOG = Logger.getGlobal();
            //LOG.info("Distribution DAO:\nUUID:" + eventRecordID + "\n" + unixTime);
            eventRecord.setUUIDValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            eventRecord.setUUIDValue(ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_CHAT_ID_COLUMN_NAME, chatId);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Open Contract database");
        } catch(Exception exception){
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    public List<EventRecord> getPendingEventList() throws
            CantGetPendingEventListException,
            UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            List<EventRecord> eventRecords=new ArrayList<>();
            EventRecord eventRecord;
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return eventRecords;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                String eventTypeString=databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_EVENT_COLUMN_NAME);
                if(eventTypeString==null){
                    throw new UnexpectedResultReturnedFromDatabaseException("The event type is null");
                }
                eventRecord=new EventRecord(EventType.valueOf(eventTypeString));
                String eventId=databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME);
                eventRecord.setEventId(eventId);
                String eventSource =databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_SOURCE_COLUMN_NAME);
                if(eventSource==null){
                    throw new UnexpectedResultReturnedFromDatabaseException("The event source is null");
                }
                eventRecord.setEventSource(EventSource.valueOf(eventSource));
                String eventStatus=databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME);
                if(eventStatus==null){
                    throw new UnexpectedResultReturnedFromDatabaseException("The event status is null");
                }
                eventRecord.setEventStatus(EventStatus.getByCode(eventStatus));
                long timestamp=databaseTableRecord.getLongValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);
                eventRecord.setTimestamp(timestamp);
                UUID chatId=databaseTableRecord.getUUIDValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_CHAT_ID_COLUMN_NAME);
                eventRecord.setChatId(chatId);
                eventRecords.add(eventRecord);
            }
            return eventRecords;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetPendingEventListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        } catch (InvalidParameterException e) {
            throw new CantGetPendingEventListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Invalid parameter in EventStatus enum");
        }
    }


    /**
     * This method returns a pending events list from database
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetPendingEventListException
     */
    public List<String> getPendingEvents() throws
            UnexpectedResultReturnedFromDatabaseException,
            CantGetPendingEventListException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            List<String> eventTypeList=new ArrayList<>();
            String eventId;
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if(records.isEmpty()){
                //There is no records in database, I'll return an empty list.
                return eventTypeList;
            }
            for(DatabaseTableRecord databaseTableRecord : records){
                eventId=databaseTableRecord.getStringValue(
                        ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetPendingEventListException(e,
                    "Getting events in EventStatus.PENDING",
                    "Cannot load the table into memory");
        }
    }

    /**
     * This method updates the event status
     * @param eventId
     * @param eventStatus
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateEventStatus(
            String eventId,
            EventStatus eventStatus) throws UnexpectedResultReturnedFromDatabaseException,
            CantUpdateRecordException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventStatus.getCode());
            databaseTable.updateRecord(record);
        }  catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }
    }

    /**
     * This method updates an event record by eventId
     * @param eventRecord
     */
    public void updateEventRecord(EventRecord eventRecord)
            throws UnexpectedResultReturnedFromDatabaseException {
        try{
            DatabaseTable databaseTable=getDatabaseEventsTable();
            String eventId=eventRecord.getEventId();
            databaseTable.addStringFilter(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventId,
                    DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            if(records.isEmpty()){
                throw new UnexpectedResultReturnedFromDatabaseException(
                        "This record "+eventRecord+" does not exists in database.");
            }
            DatabaseTableRecord record=records.get(0);
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_ID_COLUMN_NAME,
                    eventRecord.getEventId());
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_EVENT_COLUMN_NAME,
                    eventRecord.getEventType().getCode());
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_SOURCE_COLUMN_NAME,
                    eventRecord.getEventSource().getCode());
            record.setStringValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    eventRecord.getEventStatus().getCode());
            record.setLongValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME,
                    eventRecord.getTimestamp());
            record.setUUIDValue(
                    ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_CHAT_ID_COLUMN_NAME,
                    eventRecord.getChatId());
        } catch (CantLoadTableToMemoryException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(
                    exception,
                    "Updating parameter "+ChatMiddlewareDatabaseConstants.EVENTS_RECORDED_STATUS_COLUMN_NAME,"");
        }
    }

}