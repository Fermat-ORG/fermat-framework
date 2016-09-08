package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetOnlineStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetWritingStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendWritingStatusMessageNotificationException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/01/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
 */
public interface ChatManager extends ModuleManager, Serializable, ModuleSettingsImpl<ChatPreferenceSettings> {
    //public interface ChatManager extends ModuleManager, Serializable, ModuleSettingsImpl<ChatPreferenceSettings> {
    //TODO: Implementar los metodos que necesiten manejar el module
    //Documentar
    List<Chat> listVisibleChats() throws CantGetChatException;

    Boolean existAnyVisibleChat() throws CantGetChatException;

    Chat getChatByChatId(UUID chatId) throws CantGetChatException;

    void saveChat(Chat chat) throws CantSaveChatException;

    void markChatAs(UUID chatId, ChatStatus chatStatus) throws CantSaveChatException;

    void deleteChat(UUID chatId, boolean isDeleteChat) throws CantDeleteChatException;

    void deleteAllChats() throws CantDeleteChatException;

    List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException;

    Message getLastMessageByChatId(UUID chatId) throws CantGetMessageException;

    long getUnreadCountMessageByChatId(UUID chatId) throws CantGetMessageException;

    void saveMessage(Message message) throws CantSaveMessageException;

    void markAsRead(UUID messageId) throws CantSaveMessageException;

    Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException;

    void sendReadMessageNotification(UUID messageId, UUID chatId) throws SendStatusUpdateMessageNotificationException;

    List<ChatIdentity> getIdentityChatUsersFromCurrentDeviceUser() throws CantListChatIdentityException;

    List<ChatActorCommunityInformation> listAllConnectedChatActor(final ChatActorCommunitySelectableIdentity selectedIdentity,
                                                                  final int max,
                                                                  final int offset) throws CantListChatActorException;

    ChatActorCommunityInformation getChatActorConnection(String localPublicKey, String remotePublicKey) throws CantGetActorConnectionException, ActorConnectionNotFoundException;

    /**
     * This method sends the message through the Chat Network Service
     *
     * @param createdMessage
     * @throws CantSendChatMessageException
     */
    void sendMessage(Message createdMessage) throws CantSendChatMessageException;

    /**
     * This method sends the message through the Chat Network Service for view writingStatus
     *
     * @param chatId
     * @throws CantSendChatMessageException
     */
    void sendWritingStatus(UUID chatId) throws SendWritingStatusMessageNotificationException;

    Timestamp getLastMessageReceivedDate(UUID chatId) throws CantGetChatException;

    ChatActorCommunitySelectableIdentity newInstanceChatActorCommunitySelectableIdentity(ChatIdentity chatIdentity);

}
