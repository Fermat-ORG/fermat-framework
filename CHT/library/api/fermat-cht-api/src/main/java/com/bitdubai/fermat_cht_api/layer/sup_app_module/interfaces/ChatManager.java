package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetNetworkServicePublicKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetOnlineStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetWritingStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendWritingStatusMessageNotificationException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatConnectionAlreadyRequestesException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatTypeNotSupportedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySearch;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;

import java.io.Serializable;
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
    List<Chat> getChats() throws CantGetChatException;

    Chat getChatByChatId(UUID chatId) throws CantGetChatException;

    Chat newEmptyInstanceChat() throws CantNewEmptyChatException;

    void saveChat(Chat chat) throws CantSaveChatException;

    void deleteChat(Chat chat) throws CantDeleteChatException;

    void deleteChats() throws CantDeleteChatException;

    void deleteMessagesByChatId(UUID chatId) throws CantDeleteMessageException;

    List<Message> getMessages() throws CantGetMessageException;

    List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException;

    Message getMessageByChatId(UUID chatId) throws CantGetMessageException;

    int getCountMessageByChatId(UUID chatId) throws CantGetMessageException;

    Message getMessageByMessageId(UUID messageId) throws CantGetMessageException;

    Message newEmptyInstanceMessage() throws CantNewEmptyMessageException;

    void saveMessage(Message message) throws CantSaveMessageException;

    void deleteMessage(Message message) throws CantDeleteMessageException;

    Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException;

    void sendReadMessageNotification(Message message) throws SendStatusUpdateMessageNotificationException;

    String getNetworkServicePublicKey() throws CantGetNetworkServicePublicKeyException;

    boolean isIdentityDevice() throws  CantListChatIdentityException;

    List<ChatIdentity> getIdentityChatUsersFromCurrentDeviceUser() throws CantListChatIdentityException;

    ChatActorCommunitySearch getChatActorSearch();

    List<ChatActorCommunityInformation> listAllConnectedChatActor(final ChatActorCommunitySelectableIdentity selectedIdentity,
                                                                  final int                                     max             ,
                                                                  final int                                     offset          ) throws CantListChatActorException;

    /**
     * This method sends the message through the Chat Network Service
     * @param createdMessage
     * @throws CantSendChatMessageException
     */
    void sendMessage(Message createdMessage) throws CantSendChatMessageException;

    /**
     * This method sends the message through the Chat Network Service for view writingStatus
     * @param chatId
     * @throws CantSendChatMessageException
     */
    void sendWritingStatus(UUID chatId) throws SendWritingStatusMessageNotificationException;

    boolean checkWritingStatus(UUID chatId) throws CantGetWritingStatus;

    /**
     * This method check through the Chat Network Service for view onlineStatus
     * @param contactPublicKey,
     * @throws CantSendChatMessageException
     */
    boolean checkOnlineStatus(String contactPublicKey) throws CantGetOnlineStatus;

    String checkLastConnection(String contactPublicKey) throws CantGetOnlineStatus;

    void activeOnlineStatus(String contactPublicKey) throws CantGetOnlineStatus;

    void saveGroupMember(GroupMember groupMember) throws CantSaveGroupMemberException;

    void deleteGroupMember(GroupMember groupMember) throws CantDeleteGroupMemberException;

    List<GroupMember> getGroupMembersByGroupId(UUID groupId)throws CantListGroupMemberException;

    void clearChatMessageByChatId(UUID chatId) throws CantDeleteMessageException, CantGetMessageException;

    ChatActorCommunitySelectableIdentity newInstanceChatActorCommunitySelectableIdentity(ChatIdentity chatIdentity);

    /**
     * Through the method <code>getSettingsManager</code> we can get a settings manager for the specified
     * settings class parametrized.
     *
     * @return a new instance of the settings manager for the specified fermat settings object.
     */

    @Override
    SettingsManager<ChatPreferenceSettings> getSettingsManager();

    List<ChatActorCommunityInformation> listWorldChatActor(ChatActorCommunitySelectableIdentity selectableIdentity, int max, int offset) throws CantListChatActorException, CantGetChtActorSearchResult, CantListActorConnectionsException;
    void requestConnectionToChatActor(final com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity selectedIdentity,
                                 final com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation chatActorToContact) throws CantRequestActorConnectionException, ActorChatTypeNotSupportedException, ActorChatConnectionAlreadyRequestesException;
    public ChatActorCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException;

}
