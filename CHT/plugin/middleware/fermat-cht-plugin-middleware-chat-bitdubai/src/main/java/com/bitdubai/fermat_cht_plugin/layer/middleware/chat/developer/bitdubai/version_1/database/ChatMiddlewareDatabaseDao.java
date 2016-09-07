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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
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
    public List<Chat> listVisibleChats() throws DatabaseOperationException, CantGetChatException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

            table.addFermatEnumFilter(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME, ChatStatus.VISIBLE, DatabaseFilterType.EQUAL);

            table.addFilterOrder(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();
            List<Chat> chats = new ArrayList<>();

            for (DatabaseTableRecord record : records)
                chats.add(getChatTransaction(record));

            return chats;

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    null,
                    null);
        }
    }

    public Boolean existAnyVisibleChat() throws DatabaseOperationException, CantGetChatException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

            table.addFermatEnumFilter(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME, ChatStatus.VISIBLE, DatabaseFilterType.EQUAL);

            return table.getCount() > 0;

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    null,
                    null);
        }
    }

    public Chat getChatByChatId(UUID chatId) throws CantGetChatException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, chatId, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records.size() > 0)
                return getChatTransaction(records.get(0));
            else
                return null;

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "error trying to get Chat from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public Timestamp getLastMessageReceivedDateByChatId(UUID chatId) throws CantGetChatException, DatabaseOperationException {

        // if there is no chat there's no last message received
        if (chatId == null)
            return null;

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, chatId, DatabaseFilterType.EQUAL);
            table.addFermatEnumFilter(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME, TypeMessage.INCOMING, DatabaseFilterType.EQUAL);

            table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            table.setFilterTop(String.valueOf(1));

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records.size() > 0)
                return Timestamp.valueOf(records.get(0).getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_DATE_COLUMN_NAME));
            else
                return null;

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "error trying to get Chat from the database with filter: chatId=" + chatId,
                    null);
        }
    }

    public Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

            table.addStringFilter(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records.size() > 0)
                return getChatTransaction(records.get(0));
            else
                return null;

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
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

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, chat.getChatId(), DatabaseFilterType.EQUAL);

            if (table.getCount() > 0)
                table.updateRecord(getChatRecord(chat));
            else
                table.insertRecord(getChatRecord(chat));

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantSaveChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the Chat Transaction in the database.",
                    null);
        }
    }

    public void deleteChat(UUID chatId, boolean isDeleteChat) throws CantDeleteChatException, DatabaseOperationException {

        try {

            deleteMessagesByChatId(chatId);

            if(isDeleteChat)
                deleteChat(chatId);
        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantDeleteChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }

    public void deleteAllChats() throws CantDeleteChatException, DatabaseOperationException {

        try {

            // delete all messages
            DatabaseTable messageTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            messageTable.deleteRecord();

            // change the status of the chat to invisible
            DatabaseTable chatTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

            DatabaseTableRecord record = chatTable.getEmptyRecord();

            record.setFermatEnum(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME, ChatStatus.INVISIBLE);

            chatTable.updateRecord(record);
        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantDeleteChatException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }

    private void deleteChat(UUID chatId) throws CantDeleteRecordException {
        DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
        DatabaseTableFilter filter = table.getEmptyTableFilter();
        filter.setType(DatabaseFilterType.EQUAL);
        filter.setValue(chatId.toString());
        filter.setColumn(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME);
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.deleteRecord();

    }

    public void deleteMessagesByChatId(UUID chatId) throws CantDeleteMessageException, DatabaseOperationException {
        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(chatId.toString());
            filter.setColumn(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME);
            List<DatabaseTableRecord> records = getMessageData(filter,table);
            if (records != null && !records.isEmpty()) {
                for (DatabaseTableRecord record : records) {
                    if (record.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME).equals(chatId))
                        table.deleteRecord(record);
                }
            }

        } catch (Exception e) {

            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));

            throw new CantDeleteMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to delete the Chat Transaction in the database.",
                    null);
        }
    }

    private List<DatabaseTableRecord> getMessageData(DatabaseTableFilter filter, DatabaseTable table) throws CantLoadTableToMemoryException {

        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        List<DatabaseTableRecord> records = table.getRecords();

        if (records == null || records.isEmpty())
            return null;
        return records;
    }

    public List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, chatId, DatabaseFilterType.EQUAL);

            table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            List<Message> messages = new ArrayList<>();

            for (DatabaseTableRecord record : records)
                messages.add(getMessageTransaction(record));

            return messages;
        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetMessageException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "error trying to get Message from the database with filter: " + chatId.toString(),
                    null);
        }
    }

    public Message getLastMessageByChatId(UUID chatId) throws CantGetMessageException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME, chatId, DatabaseFilterType.EQUAL);

            table.addFilterOrder(ChatMiddlewareDatabaseConstants.MESSAGE_DATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            table.setFilterTop(String.valueOf(1));

            table.loadToMemory();

            List<DatabaseTableRecord> records = table.getRecords();

            if (records == null || records.isEmpty())
                return null;

            return getMessageTransaction(records.get(0));

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
            table.addFermatEnumFilter(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME , MessageStatus.READ   , DatabaseFilterType.NOT_EQUALS);
            table.addFermatEnumFilter(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME   , TypeMessage.OUTGOING , DatabaseFilterType.NOT_EQUALS);

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
            table.addFermatEnumFilter(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME, MessageStatus.READ, DatabaseFilterType.NOT_EQUALS);

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

    public void updateChatStatus(UUID chatId, ChatStatus status) throws CantSaveMessageException, DatabaseOperationException {

        try {

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, chatId, DatabaseFilterType.EQUAL);

            DatabaseTableRecord record = table.getEmptyRecord();

            record.setFermatEnum(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME, status);

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

            DatabaseTable table = getDatabaseTable(ChatMiddlewareDatabaseConstants.MESSAGE_TABLE_NAME);

            table.addUUIDFilter(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME, message.getMessageId(), DatabaseFilterType.EQUAL);

            if (table.getCount() == 0)
                table.insertRecord(getMessageRecord(message));

        } catch (Exception e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new DatabaseOperationException(
                    DatabaseOperationException.DEFAULT_MESSAGE,
                    FermatException.wrapException(e),
                    "Error trying to save the message Transaction in the database.",
                    null);
        }
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    private DatabaseTableRecord getChatRecord(Chat chat) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME, chat.getChatId());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME, chat.getLocalActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME, chat.getRemoteActorPublicKey());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME, chat.getStatus().getCode());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME, chat.getCreationDate().toString());
        record.setStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME, chat.getLastMessageDate().toString());

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
        record.setStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_DATE_COLUMN_NAME, message.getMessageDate().toString());

        return record;
    }

    private Chat getChatTransaction(final DatabaseTableRecord chatTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {
        ChatImpl chat = new ChatImpl();

        chat.setChatId(chatTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.CHATS_ID_CHAT_COLUMN_NAME));
        chat.setCreationDate(Timestamp.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_CREATION_DATE_COLUMN_NAME)));
        chat.setLastMessageDate(Timestamp.valueOf(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LAST_MESSAGE_DATE_COLUMN_NAME)));
        chat.setRemoteActorPublicKey(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_REMOTE_ACTOR_PUB_KEY_COLUMN_NAME));
        chat.setLocalActorPublicKey(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_LOCAL_ACTOR_PUB_KEY_COLUMN_NAME));
        chat.setStatus(ChatStatus.getByCode(chatTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.CHATS_STATUS_COLUMN_NAME)));

        return chat;
    }

    private Message getMessageTransaction(final DatabaseTableRecord messageTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        MessageImpl message = new MessageImpl();

        message.setChatId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_CHAT_COLUMN_NAME));
        message.setMessageId(messageTransactionRecord.getUUIDValue(ChatMiddlewareDatabaseConstants.MESSAGE_ID_MESSAGE_COLUMN_NAME));
        message.setMessage(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TEXT_MESSAGE_COLUMN_NAME));
        message.setMessageDate(Timestamp.valueOf(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_DATE_COLUMN_NAME)));
        message.setStatus(MessageStatus.getByCode(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_STATUS_COLUMN_NAME)));
        message.setType(TypeMessage.getByCode(messageTransactionRecord.getStringValue(ChatMiddlewareDatabaseConstants.MESSAGE_TYPE_COLUMN_NAME)));

        return message;
    }
}