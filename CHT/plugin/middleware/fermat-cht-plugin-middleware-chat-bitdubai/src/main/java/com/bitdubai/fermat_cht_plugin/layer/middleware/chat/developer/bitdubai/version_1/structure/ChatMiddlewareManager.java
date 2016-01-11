package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cht_api.all_definition.util.ObjectChecker;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;

import java.util.List;
import java.util.UUID;

/**
 * This class is the implementation of ChatManager.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/01/16.
 */
public class ChatMiddlewareManager implements ChatManager {

    /**
     * Represents the plugin Database Dao.
     */
    private ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao;

    public ChatMiddlewareManager(
            ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao) {
        this.chatMiddlewareDatabaseDao = chatMiddlewareDatabaseDao;
    }

    /**
     * This method returns a full list of chat recorded in database.
     * @return
     * @throws CantGetChatException
     */
    @Override
    public List<Chat> getChats() throws CantGetChatException {
        try{
            return this.chatMiddlewareDatabaseDao.getChatList();
        } catch (DatabaseOperationException exception){
            throw new CantGetChatException(
                    exception,
                    "Getting the chat list from database",
                    "An unexpected error happened in a database operation");
        }
    }

    /**
     * This method returns a chat by UUID
     * @param chatId
     * @return
     * @throws CantGetChatException
     */
    @Override
    public Chat getChatByChatId(UUID chatId) throws CantGetChatException {
        try{
            ObjectChecker.checkArgument(chatId, "The chat Id argument is null");
            return this.chatMiddlewareDatabaseDao.getChatByChatId(chatId);
        } catch (ObjectNotSetException e) {
            throw new CantGetChatException(
                    e,
                    "Getting a chat by UUID",
                    "The chat Id probably is null");
        } catch (DatabaseOperationException e) {
            throw new CantGetChatException(
                    e,
                    "Getting a chat by UUID",
                    "An unexpected error happened in a database operation");
        }
    }

    /**
     * This method returns a new empty instance chat.
     * @return
     * @throws CantNewEmptyChatException
     */
    @Override
    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException {
        return this.chatMiddlewareDatabaseDao.newEmptyInstanceChat();
    }

    /**
     * This method saves a chat, properly formed, in database.
     * @param chat
     * @throws CantSaveChatException
     */
    @Override
    public void saveChat(Chat chat) throws CantSaveChatException {
        try{
            ObjectChecker.checkArgument(chat, "The chat argument is null");
            this.chatMiddlewareDatabaseDao.saveChat(chat);
        } catch (ObjectNotSetException e) {
            throw new CantSaveChatException(
                    e,
                    "Saving a chat in database",
                    "The chat probably is null");
        } catch (DatabaseOperationException e) {
            throw new CantSaveChatException(
                    e,
                    "Saving a chat in database",
                    "An unexpected error happened in a database operation");
        }
    }

    /**
     * This method deletes a chat from database.
     * @param chat
     * @throws CantDeleteChatException
     */
    @Override
    public void deleteChat(Chat chat) throws CantDeleteChatException {
        try{
            ObjectChecker.checkArgument(chat, "The chat argument is null");
            this.chatMiddlewareDatabaseDao.deleteChat(chat);
        } catch (ObjectNotSetException e) {
            throw new CantDeleteChatException(
                    e,
                    "Deleting a chat from database",
                    "The chat probably is null");
        } catch (DatabaseOperationException e) {
            throw new CantDeleteChatException(
                    e,
                    "Deleting a chat from database",
                    "An unexpected error happened in a database operation");
        }
    }

    /**
     * This method returns a full message list.
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public List<Message> getMessages() throws CantGetMessageException {
        try {
            return this.chatMiddlewareDatabaseDao.getMessages();
        } catch (DatabaseOperationException e) {
            throw new CantGetMessageException(
                    e,
                    "Getting the full messages list",
                    "An unexpected error happened in a database operation");
        }
    }

    /**
     * This method returns a message by chat Id.
     * @param chatId
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public Message getMessageByChatId(UUID chatId) throws CantGetMessageException {
        try{
            ObjectChecker.checkArgument(chatId, "The chat id argument is null");
            return this.chatMiddlewareDatabaseDao.getMessageByChatId(chatId);
        } catch (ObjectNotSetException e) {
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "The chat id is probably null");
        } catch (DatabaseOperationException e) {
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "An unexpected error happened in a database operation");
        }
    }

    /**
     * This method return a message by message id.
     * @param messageId
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException {
        try{
            ObjectChecker.checkArgument(messageId, "The message Id is null");
            return this.chatMiddlewareDatabaseDao.getMessageByMessageId(messageId);
        } catch (ObjectNotSetException e) {
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "The chat id is probably null");
        } catch (DatabaseOperationException e) {
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "An unexpected error happened in a database operation");
        }
    }

    /**
     * This method return a new empty instance message.
     * @return
     * @throws CantNewEmptyMessageException
     */
    @Override
    public Message newEmptyInstanceMessage() throws CantNewEmptyMessageException {
        return this.chatMiddlewareDatabaseDao.newEmptyInstanceMessage();
    }

    /**
     * This method saves a message in database.
     * @param message
     * @throws CantSaveMessageException
     */
    @Override
    public void saveMessage(Message message) throws CantSaveMessageException {
        try{
            ObjectChecker.checkArgument(message, "The message argument is null");
            this.chatMiddlewareDatabaseDao.saveMessage(message);
        } catch (ObjectNotSetException e) {
            throw new CantSaveMessageException(
                    e,
                    "Saving a message in database",
                    "The message is probably null");
        } catch (DatabaseOperationException e) {
            throw new CantSaveMessageException(
                    e,
                    "Saving a message in database",
                    "An unexpected error happened in a database operation");
        }
    }

    /**
     * This method deletes a message from database.
     * @param message
     * @throws CantDeleteMessageException
     */
    @Override
    public void deleteMessage(Message message) throws CantDeleteMessageException {
        try{
            ObjectChecker.checkArgument(message, "The message argument is null");
            this.chatMiddlewareDatabaseDao.deleteMessage(message);
        } catch (ObjectNotSetException e) {
            throw new CantDeleteMessageException(
                    e,
                    "Deleting a message from database",
                    "The message is probably null");
        } catch (DatabaseOperationException e) {
            throw new CantDeleteMessageException(
                    e,
                    "Deleting a message from database",
                    "An unexpected error happened in a database operation");
        }
    }
}
