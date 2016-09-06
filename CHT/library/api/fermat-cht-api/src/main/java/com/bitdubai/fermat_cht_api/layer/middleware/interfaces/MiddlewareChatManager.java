package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendWritingStatusMessageNotificationException;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by miguel payarez (miguel_payarez@hotmail.com) on 29/12/15.
 * Update by Manuel Perez on 08/01/2016 (fix naming conventions)
 */
public interface MiddlewareChatManager extends FermatManager {

    /**
     * List all the chat instances with status "VISIBLE" || see ChatStatus enum
     *
     * @return a list of chat instances
     *
     * @throws CantGetChatException if something goes wrong.
     */
    List<Chat> listVisibleChats() throws CantGetChatException;

    /**
     * Check in database if there are available visible chats.
     *
     * @return a boolean value
     *
     * @throws CantGetChatException if something goes wrong.
     */
    Boolean existAnyVisibleChat() throws CantGetChatException;

    /**
     * Bring a chat instance searching it by its id.
     *
     * @param chatId UUID representing the id of the searched chat
     *
     * @return an instance of chat
     *
     * @throws CantGetChatException if something goes wrong.
     */
    Chat getChatByChatId(UUID chatId) throws CantGetChatException;

    /**
     * Create or update a chat instance // todo change to not to update through the same method
     *
     * @param chat an instance of the chat to create or update
     *
     * @throws CantSaveChatException
     */
    void saveChat(Chat chat) throws CantSaveChatException;

    /**
     * Change the ChatStatus of a chat to the given
     * see ChatStatus enum
     *
     * @param chatId       id of the chat
     * @param chatStatus   status to change
     *
     * @throws CantSaveChatException if something goes wrong.
     */
    void markChatAs(UUID chatId, ChatStatus chatStatus) throws CantSaveChatException;

    /**
     * Delete all messages and change the chat status to INVISIBLE if isDeleteChat value is false if not delete the record
     * see ChatStatus enum
     *
     * @param chatId id of the chat
     *
     * @throws CantDeleteChatException if something goes wrong.
     */
    void deleteChat(UUID chatId, boolean isDeleteChat) throws CantDeleteChatException;

    /**
     * Delete all messages of the chats and set all the chat status to INVISIBLE
     * see ChatStatus enum
     *
     * @throws CantDeleteChatException if something goes wrong.
     */
    void deleteAllChats() throws CantDeleteChatException;

    /**
     * List all the messages related to a given chat id.
     *
     * @param chatId id of the chat
     *
     * @return a list of Message instances
     *
     * @throws CantGetMessageException if something goes wrong.
     */
    List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException;

    /**
     * Bring the last message in a chat conversation (SENT or RECEIVED)
     * Return null if there isn't one.
     *
     * @param chatId id of the chat
     *
     * @return a Message instance.
     *
     * @throws CantGetMessageException if something goes wrong.
     */
    Message getLastMessageByChatId(UUID chatId) throws CantGetMessageException;

    /**
     * Check how many unread messages are in a chat.
     *
     * @param chatId id of the chat
     *
     * @return a long instance
     *
     * @throws CantGetMessageException if something goes wrong.
     */
    long getUnreadCountMessageByChatId(UUID chatId) throws CantGetMessageException;

    /**
     * Bring a chat instance searching it by the remote actor public key
     * Return a null value if there isn't one.
     *
     * @param publicKey of the remote actor.
     *
     * @return a Chat instance
     *
     * @throws CantGetChatException if something goes wrong.
     */
    Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException;

    /**
     * Create a message instance, if there is one with the same id no action is made
     *
     * @param message a Message instance
     *
     * @throws CantSaveMessageException if something goes wrong.
     */
    void saveMessage(Message message) throws CantSaveMessageException;

    /**
     * Change the MessageStatus of a message to READ
     * See MessageStatus enum
     *
     * @param messageId id of the message
     *
     * @throws CantSaveMessageException if something goes wrong.
     */
    void markAsRead(UUID messageId) throws CantSaveMessageException;

    /**
     * Send a read message notification to the counter-part.
     * use @chatId to get the public keys of the actors involved
     *
     * @param messageId  id of the message
     * @param chatId     id of the chat
     *
     * @throws SendStatusUpdateMessageNotificationException if something goes wrong.
     */
    void sendReadMessageNotification(UUID messageId, UUID chatId) throws SendStatusUpdateMessageNotificationException;

    /**
     * Send a writing status notification to the counter-part.
     * use @chatId to get the public keys of the actors involved
     *
     * @param chatId id of the chat
     *
     * @throws SendWritingStatusMessageNotificationException if something goes wrong.
     */
    void sendWritingStatus(UUID chatId) throws SendWritingStatusMessageNotificationException;

    /**
     * Bring the date of the last message received in a specific chat
     *
     * @param chatId id of the chat
     *
     * @return a Timestamp instance
     *
     * @throws CantGetChatException if something goes wrong.
     */
    Timestamp getLastMessageReceivedDate(UUID chatId) throws CantGetChatException;

    /**
     * This method sends the message through the Chat Network Service
     *
     * @param createdMessage an instance of the message
     *
     * @throws CantSendChatMessageException if something goes wrong.
     */
    void sendMessage(Message createdMessage) throws CantSendChatMessageException;

}
