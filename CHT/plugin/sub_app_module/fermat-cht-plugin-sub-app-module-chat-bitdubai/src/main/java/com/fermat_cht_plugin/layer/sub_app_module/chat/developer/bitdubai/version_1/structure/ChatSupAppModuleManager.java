package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendWritingStatusMessageNotificationException;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.ChatSupAppModulePluginRoot;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/01/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
 */
public class ChatSupAppModuleManager extends ModuleManagerImpl<ChatPreferenceSettings>
        implements ChatManager, Serializable {

    private final MiddlewareChatManager middlewareChatManager;
    private final ChatIdentityManager chatIdentityManager;
    private final ChatActorConnectionManager chatActorConnectionManager;
    private ChatSupAppModulePluginRoot chatSupAppModulePluginRoot;

    public ChatSupAppModuleManager(MiddlewareChatManager middlewareChatManager,
                                   ChatIdentityManager chatIdentityManager,
                                   PluginFileSystem pluginFileSystem,
                                   ChatActorConnectionManager chatActorConnectionManager,
                                   UUID pluginId,
                                   ChatSupAppModulePluginRoot chatSupAppModulePluginRoot) {
        super(pluginFileSystem, pluginId);
        this.middlewareChatManager = middlewareChatManager;
        this.chatIdentityManager = chatIdentityManager;
        this.chatActorConnectionManager = chatActorConnectionManager;
        this.chatSupAppModulePluginRoot = chatSupAppModulePluginRoot;
    }

    @Override
    public List<Chat> listVisibleChats() throws CantGetChatException {
        return middlewareChatManager.listVisibleChats();
    }

    @Override
    public Boolean existAnyVisibleChat() throws CantGetChatException {
        return middlewareChatManager.existAnyVisibleChat();
    }

    @Override
    public Chat getChatByChatId(UUID chatId) throws CantGetChatException {
        return middlewareChatManager.getChatByChatId(chatId);
    }

    @Override
    public void saveChat(Chat chat) throws CantSaveChatException {
        middlewareChatManager.saveChat(chat);
    }

    @Override
    public void markChatAs(UUID chatId, ChatStatus chatStatus) throws CantSaveChatException {

        middlewareChatManager.markChatAs(chatId, chatStatus);
    }

    public void deleteChat(UUID chatId, boolean isDeleteChat) throws CantDeleteChatException {
        middlewareChatManager.deleteChat(chatId,isDeleteChat);
    }

    @Override
    public void deleteAllChats() throws CantDeleteChatException {
        middlewareChatManager.deleteAllChats();
    }

    @Override
    public List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getMessagesByChatId(chatId);
    }

    @Override
    public Message getLastMessageByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getLastMessageByChatId(chatId);
    }

    @Override
    public long getUnreadCountMessageByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getUnreadCountMessageByChatId(chatId);
    }

    @Override
    public void saveMessage(Message message) throws CantSaveMessageException {
        System.out.println("*** 12345 case 2:send msg in Module layer" + new Timestamp(System.currentTimeMillis()));
        middlewareChatManager.saveMessage(message);
    }

    @Override
    public void markAsRead(UUID messageId) throws CantSaveMessageException {

        middlewareChatManager.markAsRead(messageId);
    }

    @Override
    public Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException {
        return middlewareChatManager.getChatByRemotePublicKey(publicKey);
    }

    @Override
    public void sendReadMessageNotification(UUID messageId, UUID chatId) throws SendStatusUpdateMessageNotificationException {
        middlewareChatManager.sendReadMessageNotification(messageId, chatId);
    }

    @Override
    public List<ChatIdentity> getIdentityChatUsersFromCurrentDeviceUser() throws CantListChatIdentityException {
        return chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();
    }

    @Override
    public List<ChatActorCommunityInformation> listAllConnectedChatActor(ChatActorCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListChatActorException {
        List<ChatActorCommunityInformation> chatActorCommunityInformationList = null;
        try {
            final ChatLinkedActorIdentity linkedChatActor = new ChatLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActor);

            search.addConnectionState(ConnectionState.CONNECTED);

            final List<ChatActorConnection> actorConnections = search.getResult(max, offset);

            chatActorCommunityInformationList = new ArrayList<>();

            for (ChatActorConnection cac : actorConnections)
                chatActorCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(cac));

        } catch (CantListActorConnectionsException e) {
            chatSupAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return chatActorCommunityInformationList;
    }

    @Override
    public ChatActorCommunityInformation getChatActorConnection(String localPublicKey, String remotePublicKey) throws CantGetActorConnectionException, ActorConnectionNotFoundException {

        final ChatLinkedActorIdentity linkedChatActor = new ChatLinkedActorIdentity(localPublicKey, Actors.CHAT);

        final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActor);

        return new ChatActorCommunitySubAppModuleInformationImpl(search.findByPublicKey(remotePublicKey));
    }

    /**
     * This method sends the message through the Chat Network Service
     *
     * @param createdMessage
     * @throws CantSendChatMessageException
     */
    @Override
    public void sendMessage(Message createdMessage) throws CantSendChatMessageException {
        middlewareChatManager.sendMessage(createdMessage);
    }

    @Override
    public void sendWritingStatus(UUID chatId) throws SendWritingStatusMessageNotificationException {
        middlewareChatManager.sendWritingStatus(chatId);
    }

    @Override
    public Timestamp getLastMessageReceivedDate(UUID chatId) throws CantGetChatException {
        return middlewareChatManager.getLastMessageReceivedDate(chatId);
    }

    @Override
    public ChatActorCommunitySelectableIdentity newInstanceChatActorCommunitySelectableIdentity(ChatIdentity chatIdentity) {
        return new ChatActorCommunitySelectableIdentityImpl(chatIdentity.getPublicKey(), chatIdentity.getActorType(), chatIdentity.getAlias(), chatIdentity.getImage());
    }

    /**
     * Create identity
     *
     * @param name
     * @param phrase
     * @param profile_img
     */
    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        chatIdentityManager.createNewIdentityChat(name, profile_img, null, null, null, "available", 0, null);
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    @Override
    public ChatActorCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }


    @Override
    public void setAppPublicKey(String publicKey) {

    }
}
