package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by miguel payarez miguel_payarez@hotmail.com on 06/01/16.
 * Modified by Franklin Marcano 09-01-2016
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
 */
public class ChatMiddlewareDatabaseDao {

    private Database database;
    private ChatMiddlewarePluginRoot chatMiddlewarePluginRoot;

    /**
     * Constructor
     */
    public ChatMiddlewareDatabaseDao(Database database,
                                     ChatMiddlewarePluginRoot chatMiddlewarePluginRoot) {
        this.database = database;
        this.chatMiddlewarePluginRoot = chatMiddlewarePluginRoot;
    }

    /**
     * This method returns all the chats recorded in database.
     *
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

    public List<Chat> getChats(DatabaseTableFilter filter) throws CantGetChatException, DatabaseOperationException {

        try {
            List<Chat> chats = new ArrayList<>();

            for (DatabaseTableRecord record : getChatData(filter))
                chats.add(getChatTransaction(record));

            return chats;

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Contact from the database with filter: " + filter.toString(),
                    null);
        }
    }


    public Chat getChatByChatId(UUID chatId) throws
            CantGetChatException,
            DatabaseOperationException {
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

            if (chats.isEmpty()) {
                return null;
            }

            return chats.get(0);
        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "error trying to get Chat from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public Chat getChatByRemotePublicKey(String publicKey) throws
            CantGetChatException,
            DatabaseOperationException {
        Database database = null;
        try {
            database = openDatabase();
            List<Chat> chats = new ArrayList<>();
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(publicKey);
            filter.setColumn(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME);
            // I will add the contact information from the database
            for (DatabaseTableRecord record : getChatData(filter)) {
                final Chat chat = getChatTransaction(record);

                chats.add(chat);
            }

            database.closeDatabase();

            if (chats.isEmpty()) {
                return null;
            }

            return chats.get(0);
        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "error trying to get Chat from the database with filter: " + publicKey,
                    null);
        }
    }

    public UUID getChatIdByRemotePublicKey(String publicKey) throws CantGetChatException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

            table.addStringFilter(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records.isEmpty())
                return null;
            else
                return records.get(0).getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME);

        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "error trying to get Chat from the database with filter: " + publicKey,
                    null
            );
        }
    }

    public void saveChat(Chat chat) throws CantSaveChatException, DatabaseOperationException {
        try {
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

            if (chat.getMessagesAsociated() != null) {
                for (Message message : chat.getMessagesAsociated()) {
                    record = getMessageRecord(message);
                    filter.setType(DatabaseFilterType.EQUAL);
                    filter.setValue(message.getMessageId().toString());
                    filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_FIRST_KEY_COLUMN);

                    if (isNewRecord(table, filter))
                        transaction.addRecordToInsert(table, record);
                    else {
                        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                        transaction.addRecordToUpdate(table, record);
                    }
                }
            }

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);
            database.closeDatabase();

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantSaveChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the Chat Transaction in the database.",
                    null);
        }
    }

    public void deleteChat(Chat chat) throws CantDeleteChatException, DatabaseOperationException {
        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
            chat.setStatus(ChatStatus.INVISSIBLE);
            DatabaseTableRecord record = getChatRecord(chat);
            deleteMessagesByChatId(record.getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME));

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

            database.executeTransaction(transaction);
            database.closeDatabase();

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantDeleteChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }


    public void deleteChats() throws CantDeleteChatException, DatabaseOperationException {

        try {

            List<Chat> chats = getChatList();
            for (Chat chat : chats) {
                deleteChat(chat);
            }

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantDeleteChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }

    public void deleteMessagesByChatId(UUID chatId) throws CantDeleteMessageException, DatabaseOperationException {
        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, chatId, DatabaseFilterType.EQUAL);

            table.deleteRecord();

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));

            throw new CantDeleteMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }

    public void purifyMessage(Message message) {
//        String text = message.getMessage();
//        char a = 39;
//        char b = 182;
//        text = text.replace(a,b);
//        message.setMessage(text);
        message.setMessage(message.getMessage().replace("'", "@alt+39#"));
    }

    public void despurifyMessage(Message message) {
//        String text = message.getMessage();
//        char a = 39;
//        char b = 182;
//        text = text.replace(b,a);
//        message.setMessage(text);
        //code to decode apostrophe
        message.setMessage(message.getMessage().replace("@alt+39#", "'"));
    }

    public List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException {

        try {


            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, chatId, DatabaseFilterType.EQUAL);

            table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records == null || records.isEmpty()) {
                return null;
            }

            List<Message> messages = new ArrayList<>();

            for (DatabaseTableRecord record : records) {
                final Message message = getMessageTransaction(record);
                despurifyMessage(message);
                messages.add(message);
            }

            database.closeDatabase();

            if (messages.isEmpty()) {
                return null;
            }
            return messages;
        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new CantGetMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public Message getFirstMessageByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, chatId, DatabaseFilterType.EQUAL);

            table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records == null || records.isEmpty())
                return null;

            final Message message = getMessageTransaction(records.get(0));

            despurifyMessage(message);

            return message;
        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public long getUnreadCountMessageByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter      (ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, chatId               , DatabaseFilterType.EQUAL);
            table.addFermatEnumFilter(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME , MessageStatus.CREATED, DatabaseFilterType.EQUAL);

            return table.getCount();

        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Count Message from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public UUID getChatIdByMessageId(UUID messageId) throws CantGetMessageException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME, messageId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records.size() > 0)
                return records.get(0).getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME);
            else
                return null;
        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + messageId.toString(),
                    null);
        }
    }

    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME, messageId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records.size() > 0) {
                Message message = getMessageTransaction(records.get(0));
                despurifyMessage(message);
                return message;
            } else
                return null;
        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + messageId.toString(),
                    null);
        }
    }

    public MessageStatus getMessageStatus(final UUID messageId) throws CantGetMessageException, DatabaseOperationException {

        try {
            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME, messageId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records.size() > 0)
                return MessageStatus.getByCode(records.get(0).getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME));
            else
                return null;

        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + messageId.toString(),
                    null);
        }
    }

    public void updateMessageStatus(UUID messageId, MessageStatus status) throws CantSaveMessageException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME, messageId, DatabaseFilterType.EQUAL);

            DatabaseTableRecord record = table.getEmptyRecord();

            record.setFermatEnum(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME, status);

            table.updateRecord(record);

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the message Transaction in the database.",
                    null);
        }
    }

    public void saveMessage(Message message) throws CantSaveMessageException, DatabaseOperationException {
        try {
            System.out.println("*** 12345 case 4:send msg in Dao layer" + new Timestamp(System.currentTimeMillis()));

            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            DatabaseTableRecord record;
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(message.getMessageId().toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_FIRST_KEY_COLUMN);
            purifyMessage(message);
            if (isNewRecord(table, filter)) {
                message.setCount(getLastMessageCount() + 1);

                record = getMessageRecord(message);
                transaction.addRecordToInsert(table, record);
            } else {
                message.setCount(getMessageByMessageId(message.getMessageId()).getCount());
                record = getMessageRecord(message);
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, record);
            }

            //I execute the transaction and persist the database side of the chat.
            database.executeTransaction(transaction);

        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the message Transaction in the database.",
                    null);
        }
    }

    private synchronized Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {

        return database;
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        return table.getCount() == 0;
    }

    private DatabaseTableRecord getChatRecord(Chat chat) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, chat.getChatId());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_OBJECT_COLUMN_NAME, chat.getObjectId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CHAT_NAME_COLUMN_NAME, chat.getChatName());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME, chat.getLocalActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, chat.getRemoteActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME, chat.getStatus().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME, chat.getDate().toString());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME, chat.getLastMessageDate().toString());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CONTACT_ASSOCIATED_LIST, chat.getContactListString());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_TYPE_CHAT, chat.getTypeChat().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_SCHEDULED_DELIVERY, String.valueOf(chat.getScheduledDelivery()));
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_IS_WRITING, String.valueOf(chat.isWriting()));
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_IS_ONLINE, String.valueOf(chat.isOnline()));

        return record;
    }

    private DatabaseTableRecord getMessageRecord(Message message) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME, message.getMessageId());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, message.getChatId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TEXT_MESSAGE_COLUMN_NAME, message.getMessage());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME, message.getStatus().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME, message.getType().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME, message.getMessageDate().toString());
        record.setUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_CONTACT_ID, message.getContactId());
        record.setLongValue(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT, message.getCount());

        return record;
    }

    private List<DatabaseTableRecord> getChatData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.addFilterOrder(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

        table.loadToMemory();

        return table.getRecords();
    }

    private Chat getChatTransaction(final DatabaseTableRecord chatTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        ChatImpl chat = new ChatImpl();

        chat.setChatId(chatTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME));
        chat.setObjectId(chatTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_OBJECT_COLUMN_NAME));
        chat.setChatName(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CHAT_NAME_COLUMN_NAME));
        chat.setDate(Timestamp.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME)));
        chat.setLastMessageDate(Timestamp.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME)));
        chat.setRemoteActorPublicKey(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME));
        chat.setLocalActorPublicKey(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME));
        chat.setStatus(ChatStatus.getByCode(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME)));
        chat.setTypeChat(TypeChat.getByCode(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_TYPE_CHAT)));
        chat.setScheduledDelivery(Boolean.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_SCHEDULED_DELIVERY)));
        chat.setIsWriting(Boolean.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_IS_WRITING)));
        chat.setIsOnline(Boolean.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_IS_ONLINE)));

        return chat;
    }

    private Message getMessageTransaction(final DatabaseTableRecord messageTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        MessageImpl message = new MessageImpl();

        message.setChatId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME));
        message.setMessageId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME));
        message.setMessage(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TEXT_MESSAGE_COLUMN_NAME));
        message.setMessageDate(Timestamp.valueOf(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_MESSAGE_DATE_COLUMN_NAME)));
        message.setStatus(MessageStatus.getByCode(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME)));
        message.setType(TypeMessage.getByCode(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME)));
        message.setContactId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_CONTACT_ID));
        message.setCount(messageTransactionRecord.getLongValue(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT));

        return message;
    }

    public long getLastMessageCount() {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT, DatabaseFilterOrder.DESCENDING);

            table.setFilterTop("1");

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records.isEmpty())
                return 0;
            else
                return records.get(0).getLongValue(ChatMiddlewareDatabaseConstants.MESSAGE_COUNT);

        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return 0;
    }
}