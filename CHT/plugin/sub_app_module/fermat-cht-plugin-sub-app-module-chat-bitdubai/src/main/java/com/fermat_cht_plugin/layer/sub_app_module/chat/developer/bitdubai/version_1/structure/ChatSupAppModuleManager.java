package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
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
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatConnectionAlreadyRequestesException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatTypeNotSupportedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySearch;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;
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
public class ChatSupAppModuleManager
        extends ModuleManagerImpl<ChatPreferenceSettings>
        implements ChatManager, Serializable {
//public class ChatSupAppModuleManager ModuleManagerImpl<ChatPreferenceSettings> implements ChatManager, Serializable {

    private final MiddlewareChatManager middlewareChatManager;
    private final ChatIdentityManager chatIdentityManager;
    //private SettingsManager<ChatPreferenceSettings> settingsManager;
    //private SettingsManager<ChatActorCommunitySettings>    settingsManagerCommunity                       ;
    private final ChatActorConnectionManager chatActorConnectionManager;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;
    private ChatSupAppModulePluginRoot chatSupAppModulePluginRoot;
    private final com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager chatActorNetworkServiceManager;
    private String subAppPublicKey;
    private final GeolocationManager geolocationManager;
    private final LocationManager locationManager;

    public ChatSupAppModuleManager(MiddlewareChatManager middlewareChatManager,
                                   ChatIdentityManager chatIdentityManager,
                                   PluginFileSystem pluginFileSystem,
                                   ChatActorConnectionManager chatActorConnectionManager,
                                   UUID pluginId,
                                   ChatSupAppModulePluginRoot chatSupAppModulePluginRoot,
                                   com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager chatActorNetworkServiceManager,
                                   GeolocationManager geolocationManager,
                                   LocationManager locationManager) {
        super(pluginFileSystem, pluginId);
        this.middlewareChatManager = middlewareChatManager;
        this.chatIdentityManager = chatIdentityManager;
        this.pluginFileSystem = pluginFileSystem;
        this.chatActorConnectionManager = chatActorConnectionManager;
        this.pluginId = pluginId;
        this.chatSupAppModulePluginRoot = chatSupAppModulePluginRoot;
        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;
        this.geolocationManager = geolocationManager;
        this.locationManager = locationManager;
    }

    @Override
    public List<Chat> getChats() throws CantGetChatException {
        return middlewareChatManager.getChats();
    }

    @Override
    public Chat getChatByChatId(UUID chatId) throws CantGetChatException {
        return middlewareChatManager.getChatByChatId(chatId);
    }

    @Override
    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException {
        return middlewareChatManager.newEmptyInstanceChat();
    }

    @Override
    public void saveChat(Chat chat) throws CantSaveChatException {
        middlewareChatManager.saveChat(chat);
    }

    @Override
    public void deleteChat(Chat chat) throws CantDeleteChatException {
        middlewareChatManager.deleteChat(chat);
    }

    @Override
    public void deleteChats() throws CantDeleteChatException {
        middlewareChatManager.deleteChats();
    }

    @Override
    public void deleteMessagesByChatId(UUID chatId) throws CantDeleteMessageException {
        middlewareChatManager.deleteMessagesByChatId(chatId);
    }

    @Override
    public List<Message> getMessages() throws CantGetMessageException {
        return middlewareChatManager.getMessages();
    }

    @Override
    public List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getMessagesByChatId(chatId);
    }

    @Override
    public Message getMessageByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getMessageByChatId(chatId);
    }

    @Override
    public int getCountMessageByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getCountMessageByChatId(chatId);
    }

    @Override
    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException {
        return middlewareChatManager.getMessageByMessageId(messageId);
    }

    @Override
    public Message newEmptyInstanceMessage() throws CantNewEmptyMessageException {
        return middlewareChatManager.newEmptyInstanceMessage();
    }

    @Override
    public void saveMessage(Message message) throws CantSaveMessageException {
        System.out.println("*** 12345 case 2:send msg in Module layer" + new Timestamp(System.currentTimeMillis()));
        middlewareChatManager.saveMessage(message);
    }

    @Override
    public void deleteMessage(Message message) throws CantDeleteMessageException {
        middlewareChatManager.deleteMessage(message);
    }

    @Override
    public Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException {
        return middlewareChatManager.getChatByRemotePublicKey(publicKey);
    }

    @Override
    public void sendReadMessageNotification(Message message) throws SendStatusUpdateMessageNotificationException {
        middlewareChatManager.sendReadMessageNotification(message);
    }


    /**
     * This method returns the Network Service public key
     *
     * @return
     * @throws CantGetNetworkServicePublicKeyException
     */
    @Override
    public String getNetworkServicePublicKey() throws CantGetNetworkServicePublicKeyException {
        return middlewareChatManager.getNetworkServicePublicKey();
    }


    @Override
    public boolean isIdentityDevice() throws CantListChatIdentityException {
        if (chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser().isEmpty())
            return false;
        else return true;
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
    public boolean checkWritingStatus(UUID chatId) throws CantGetWritingStatus {
        return middlewareChatManager.checkWritingStatus(chatId);
    }

    @Override
    public boolean checkOnlineStatus(String contactPublicKey) throws CantGetOnlineStatus {
        return middlewareChatManager.checkOnlineStatus(contactPublicKey);
    }

    @Override
    public String checkLastConnection(String contactPublicKey) throws CantGetOnlineStatus {
        return middlewareChatManager.checkLastConnection(contactPublicKey);
    }

    @Override
    public void activeOnlineStatus(String contactPublicKey) throws CantGetOnlineStatus {
        middlewareChatManager.activeOnlineStatus(contactPublicKey);
    }

    @Override
    public void saveGroupMember(GroupMember groupMember) throws CantSaveGroupMemberException {
        middlewareChatManager.saveGroupMember(groupMember);
    }

    @Override
    public void deleteGroupMember(GroupMember groupMember) throws CantDeleteGroupMemberException {
        middlewareChatManager.deleteGroupMember(groupMember);
    }


    @Override
    public List<GroupMember> getGroupMembersByGroupId(UUID groupId) throws CantListGroupMemberException {
        return getGroupMembersByGroupId(groupId);
    }

    @Override
    public void clearChatMessageByChatId(UUID chatId) throws CantDeleteMessageException, CantGetMessageException {
        List<Message> messages = middlewareChatManager.getMessagesByChatId(chatId);

        for (Message message : messages) {
            middlewareChatManager.deleteMessage(message);
        }
    }

    @Override
    public void updateActorConnection(ChatActorConnection chatActorConnection) {
        try {
            List<ChatActorCommunityInformation> chatActorCommunityInformationList = listWorldChatActor(chatActorConnection.getPublicKey(),
                    Actors.CHAT, null, 0, "", 1, 0);
            ChatActorCommunityInformation actorChat = chatActorCommunityInformationList.get(0);
            ChatActorConnection chatConnection = new ChatActorConnection(actorChat.getConnectionId(),null,
                    chatActorConnection.getPublicKey(), actorChat.getAlias(), actorChat.getImage(),
                    chatActorConnection.getConnectionState(), 0, 0, actorChat.getStatus());
            middlewareChatManager.updateActorConnection(chatConnection);
        }catch (CantGetChtActorSearchResult | CantListActorConnectionsException e){
            chatSupAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }catch (Exception e){
            chatSupAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    @Override
    public ChatActorCommunitySelectableIdentity newInstanceChatActorCommunitySelectableIdentity(ChatIdentity chatIdentity) {
        return new ChatActorCommunitySelectableIdentityImpl(chatIdentity.getPublicKey(), chatIdentity.getActorType(), chatIdentity.getAlias(), chatIdentity.getImage());
    }

    /**
     * Through the method <code>getSettingsManager</code> we can get a settings manager for the specified
     * settings class parametrized.
     *
     * @return a new instance of the settings manager for the specified fermat settings object.
     */

    /**
     * Through the method <code>getSelectedActorIdentity</code> we can get the selected actor identity.
     *
     * @return an instance of the selected actor identity.
     * @throws CantGetSelectedActorIdentityException if something goes wrong.
     * @throws ActorIdentityNotSelectedException     if there's no actor identity selected.
     */

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

    //COMMUNITY

    @Override
    public List<ChatActorCommunityInformation> listWorldChatActor(String publicKey, Actors actorType, DeviceLocation deviceLocation, double distance, String alias, int max, int offset) throws com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException, CantGetChtActorSearchResult, CantListActorConnectionsException {
//        List<ChatActorCommunityInformation> worldActorList = null;
//        List<ChatActorConnection> actorConnections = null;
//
//        worldActorList = getResult();
//
//        try {
//            if (selectableIdentity != null) {
//                final ChatLinkedActorIdentity linkedChatActorIdentity = new ChatLinkedActorIdentity(selectableIdentity.getPublicKey(), selectableIdentity.getActorType());
//                final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActorIdentity);
//
//                actorConnections = search.getResult(Integer.MAX_VALUE, 0);
//            }//else linkedChatActorIdentity=null;
//        } catch (CantListActorConnectionsException exception) {
//            exception.printStackTrace();
//        }
//
//        ChatActorCommunityInformation worldActor;

        List<ChatActorCommunityInformation> worldActorList = null;
        List<ChatActorConnection> actorConnections = null;
        ConnectionState connectionState;
        UUID connectionID;
        try {
            worldActorList = getChatActorSearch().getResult(publicKey, deviceLocation, distance, alias, offset, max);
        } catch (CantGetChtActorSearchResult exception) {
            chatSupAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        try {
            if (publicKey != null && actorType != null) {
                final ChatLinkedActorIdentity linkedChatActorIdentity = new ChatLinkedActorIdentity(publicKey, actorType);
                final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActorIdentity);

                actorConnections = search.getResult(1000, 0);
                //  actorConnections = search.getResult(Integer.MAX_VALUE, 0);
            }//else linkedChatActorIdentity=null;
        } catch (CantListActorConnectionsException exception) {
            chatSupAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        ChatActorCommunityInformation worldActor;
        if (actorConnections != null && worldActorList != null) {
            if (actorConnections.size() > 0 && worldActorList.size() > 0) {
                for (int i = 0; i < worldActorList.size(); i++) {

                    worldActor = worldActorList.get(i);
                    String country = "", city = "", state = "";
                    connectionID = null;
                    connectionState = null;
                    final Location location = worldActor.getLocation();
                    try {
                        if(location!=null) {
                            if(location.getLatitude() != null && location.getAltitude() != null) {
                                final Address address = geolocationManager.getAddressByCoordinate(location.getLatitude(), location.getLongitude());
                                country = address.getCountry();
                                city = address.getCity().equals("null") ? address.getCounty() : address.getCity();
                                state = address.getState().equals("null") ? address.getCounty() : address.getState();
                            }
                        }
                    } catch (CantCreateAddressException ignore) {
                    }
                    if (actorConnections != null && actorConnections.size() > 0) {
                        for (ChatActorConnection connectedActor : actorConnections) {
                            if (worldActor.getPublicKey().equals(connectedActor.getPublicKey())) {
                                connectionState = connectedActor.getConnectionState();
                                connectionID = connectedActor.getConnectionId();
                            }
                        }
                    }
                    worldActor.setCity(city);
                    worldActor.setCountry(country);
                    worldActor.setState(state);
                    for (ChatActorConnection connectedActor : actorConnections) {
                        if (worldActor.getPublicKey().equals(connectedActor.getPublicKey()))
                            worldActorList.set(i, new ChatActorCommunitySubAppModuleInformationImpl(
                                    worldActor.getPublicKey(), worldActor.getAlias(),
                                    worldActor.getImage(), connectionState,
                                    connectionID, worldActor.getStatus(),
                                    country, state,
                                    city, null, worldActor.getProfileStatus()));
                    }
                }
            }
        }

        return worldActorList;
    }

    @Override
    public ChatActorCommunitySearch getChatActorSearch() {
        return new ChatActorCommunitySubAppModuleSearch(chatActorNetworkServiceManager) {
        };
    }


    public List<ChatActorCommunityInformation> getResult() {
        try {
            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            final List<ChatExposingData> chatActorConnections = chatActorSearch.getResult();

            final List<ChatActorCommunityInformation> chatActorLocalCommunityInformationList = new ArrayList<>();

            for (ChatExposingData ced : chatActorConnections)
                chatActorLocalCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(ced));

            return chatActorLocalCommunityInformationList;
        } catch (CantListChatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void requestConnectionToChatActor(final com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity selectedIdentity,
                                             final com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation chatActorToContact) throws CantRequestActorConnectionException, ActorChatTypeNotSupportedException, ActorChatConnectionAlreadyRequestesException {
        try {

            final ActorIdentityInformation actorSending = new ActorIdentityInformation(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType(),
                    selectedIdentity.getAlias(),
                    selectedIdentity.getImage(),
                    ""
            );

            final ActorIdentityInformation actorReceiving = new ActorIdentityInformation(
                    chatActorToContact.getPublicKey(),
                    Actors.CHAT,
                    chatActorToContact.getAlias(),
                    chatActorToContact.getImage(),
                    chatActorToContact.getStatus()
            );

            chatActorConnectionManager.requestConnection(
                    actorSending,
                    actorReceiving
            );
        } catch (ConnectionAlreadyRequestedException e) {
            e.printStackTrace();
        } catch (com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException e) {
            e.printStackTrace();
        } catch (UnsupportedActorTypeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ChatActorCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        //Try to get appSettings
        ChatActorCommunitySettings appSettings = null;
        //TODO: Revisar este caso
//        try {
//            appSettings = this.getSettingsManagerCommmunity().loadAndGetSettings(this.subAppPublicKey);//SubAppsPublicKeys.CHT_COMMUNITY.getCode() //this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
//        } catch (Exception e) {
//            try {
//                appSettings = new ChatActorCommunitySettings();
//                this.getSettingsManagerCommmunity().persistSettings(this.subAppPublicKey, appSettings);
//            } catch (CantPersistSettingsException e1) {
//                //chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
//                //e1.printStackTrace();
//            }
//            //chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
//            return null;
//        }

        List<ChatIdentity> IdentitiesInDevice = new ArrayList<>();
        try {
            IdentitiesInDevice = chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();
            //TODO:Revisar como asignar estos valores deben ser seteados al entrar a la comunidad setear los settings necesario
            if (IdentitiesInDevice != null && IdentitiesInDevice.size() > 0) {
                appSettings.setLastSelectedIdentityPublicKey(IdentitiesInDevice.get(0).getPublicKey());
                appSettings.setLastSelectedActorType(IdentitiesInDevice.get(0).getActorType());
            }
        } catch (CantListChatIdentityException e) {
            e.printStackTrace();
            /*Do nothing*/
        }


        //If appSettings exists, get its selectedActorIdentityPublicKey property
        if (appSettings != null) {
            String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();
            Actors lastSelectedActorType = appSettings.getLastSelectedActorType();

            if (lastSelectedIdentityPublicKey != null && lastSelectedActorType != null) {

                ChatActorCommunitySelectableIdentityImpl selectedIdentity = null;

                if (lastSelectedActorType == Actors.CHAT) {
                    for (ChatIdentity i : IdentitiesInDevice) {
                        if (i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new ChatActorCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.CHAT, i.getAlias(), i.getImage());
                    }
                }
//                if(selectedIdentity == null)
//                    throw new ActorIdentityNotSelectedException("", null, "", "");

                return selectedIdentity;
            }
//            else
//                throw new ActorIdentityNotSelectedException("", null, "", "");
        }

        return null;
    }

//    public SettingsManager<ChatActorCommunitySettings> getSettingsManagerCommmunity() {
//        if (this.settingsManagerCommunity != null)
//            return this.settingsManagerCommunity;
//
//        this.settingsManagerCommunity = new SettingsManager<>(
//                pluginFileSystem,
//                pluginId
//        );
//
//        return this.settingsManagerCommunity;
//    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.subAppPublicKey = publicKey;
    }
}
