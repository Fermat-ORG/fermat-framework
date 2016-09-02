package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
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

    //Documentar
    List<Chat> listVisibleChats() throws CantGetChatException;

    Boolean existAnyVisibleChat() throws CantGetChatException;

    Chat getChatByChatId(UUID chatId) throws CantGetChatException;

    void saveChat(Chat chat) throws CantSaveChatException;

    void deleteChat(UUID chatId) throws CantDeleteChatException;

    List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException;

    Message getLastMessageByChatId(UUID chatId) throws CantGetMessageException;

    long getUnreadCountMessageByChatId(UUID chatId) throws CantGetMessageException;

    Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException;

    void saveMessage(Message message) throws CantSaveMessageException;

    void markAsRead(UUID messageId) throws CantSaveMessageException;

    void sendReadMessageNotification(UUID messageId, UUID chatId) throws SendStatusUpdateMessageNotificationException;

    void sendWritingStatus(UUID chatId) throws SendWritingStatusMessageNotificationException;

    Timestamp getLastMessageReceivedDate(UUID chatId) throws CantGetChatException;

    /**
     * This method sends the message through the Chat Network Service
     *
     * @param createdMessage
     * @throws CantSendChatMessageException
     */
    void sendMessage(Message createdMessage) throws CantSendChatMessageException;

}
