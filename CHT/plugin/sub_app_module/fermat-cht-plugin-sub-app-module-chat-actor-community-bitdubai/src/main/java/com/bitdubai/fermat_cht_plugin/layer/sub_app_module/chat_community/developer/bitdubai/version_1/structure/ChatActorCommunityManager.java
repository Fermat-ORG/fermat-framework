package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;

import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;

import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatActorWaitingException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatConnectionAlreadyRequestesException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatTypeNotSupportedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantAcceptChatRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatIdentitiesToSelectException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantValidateActorConnectionStateException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorCancellingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorConnectionDenialFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorDisconnectingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySearch;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 * Edited by Miguel Rincon on 18/04/2016
 */
public class ChatActorCommunityManager implements ChatActorCommunitySubAppModuleManager, Serializable {

    private final ChatIdentityManager                   chatIdentityManager;
    private ChatActorCommunityInformation               chatActorCommunityManager             ;
    private final ChatActorConnectionManager            chatActorConnectionManager            ;
    private final ChatManager                           chatActorNetworkServiceManager        ;
    private String                                      subAppPublicKey                       ;
    private final ErrorManager                          errorManager                          ;
    private final PluginFileSystem                      pluginFileSystem                      ;
    private final UUID                                  pluginId                              ;
    private final PluginVersionReference                pluginVersionReference                ;
    private SettingsManager<ChatActorCommunitySettings> settingsManager                       ;
    private ChatActorCommunitySubAppModuleManager chatActorCommunitySubAppModuleManager;

    public ChatActorCommunityManager(ChatIdentityManager chatIdentityManager, ChatActorConnectionManager chatActorConnectionManager, ChatManager chatActorNetworkServiceManager, ErrorManager errorManager, PluginFileSystem pluginFileSystem, UUID pluginId, PluginVersionReference pluginVersionReference) {
        this.chatIdentityManager= chatIdentityManager;
        this.chatActorConnectionManager=chatActorConnectionManager;
        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;
        this.chatActorCommunityManager = chatActorCommunityManager;
        this.errorManager = errorManager;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.pluginVersionReference= pluginVersionReference;
    }

    @Override
    public List<ChatActorCommunityInformation> listWorldChatActor(ChatActorCommunitySelectableIdentity selectableIdentity, int max, int offset) throws CantListChatActorException, CantGetChtActorSearchResult, CantListActorConnectionsException {
        List<ChatActorCommunityInformation> worldActorList = null;
        List<ChatActorConnection> actorConnections = null;

        try{
            worldActorList = getChatActorSearch().getResult();
        } catch (CantGetChtActorSearchResult exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        try{
            final ChatLinkedActorIdentity linkedChatActorIdentity = new ChatLinkedActorIdentity(selectableIdentity.getPublicKey(), selectableIdentity.getActorType());
            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActorIdentity);

            actorConnections = search.getResult(Integer.MAX_VALUE, 0);
        } catch (CantListActorConnectionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,exception);
        }

        ChatActorCommunityInformation worldActor;
        for(int i = 0; i < worldActorList.size(); i++)
        {
            worldActor = worldActorList.get(i);
            for(ChatActorConnection connectedActor : actorConnections)
            {
                if(worldActor.getPublicKey().equals(connectedActor.getPublicKey()))
                    worldActorList.set(i, new ChatActorCommunitySubAppModuleInformationImpl(worldActor.getPublicKey(), worldActor.getAlias(), worldActor.getImage(), connectedActor.getConnectionState(), connectedActor.getConnectionId()));
            }
        }

        return worldActorList;
    }

    @Override
    public List<ChatActorCommunitySelectableIdentity> listSelectableIdentities() throws CantListChatIdentitiesToSelectException, CantListChatIdentityException {

        List<ChatActorCommunitySelectableIdentity> selectableIdentities = null;
        try {
            selectableIdentities = new ArrayList<>();

            final List<ChatIdentity> chatActorIdentity = chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();

            for (final ChatIdentity chi : chatActorIdentity)
                selectableIdentities.add(new ChatActorCommunitySelectableIdentityImpl(chi));


        } catch (CantListChatIdentityException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,exception);
        }

        return selectableIdentities;

    }

    @Override
    public void setSelectedActorIdentity(ChatActorCommunitySelectableIdentity identity) throws CantPersistSettingsException, CantGetSettingsException, SettingsNotFoundException {

        ChatActorCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (CantGetSettingsException | SettingsNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            appSettings = null;
        }

        //If appSettings exist, save identity
        if(appSettings != null){
            if(identity.getPublicKey() != null)
                appSettings.setLastSelectedIdentityPublicKey(identity.getPublicKey());
            if(identity.getActorType() != null)
                appSettings.setLastSelectedActorType(identity.getActorType());
            try {
                this.settingsManager.persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public ChatActorCommunitySearch getChatActorSearch() {
        return new ChatActorCommunitySubAppModuleSearch(chatActorNetworkServiceManager) {
        };
    }


    @Override
    public ChatActorCommunitySearch searchConnectedChatActor(ChatActorCommunitySelectableIdentity selectedIdentity) {
        return null;
    }

    @Override
    public void requestConnectionToChatActor(final ChatActorCommunitySelectableIdentity selectedIdentity,
                                             final ChatActorCommunityInformation chatActorToContact) throws CantRequestActorConnectionException, ActorChatTypeNotSupportedException, ActorChatConnectionAlreadyRequestesException {
        try {

            final ActorIdentityInformation actorSending = new ActorIdentityInformation(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType(),
                    selectedIdentity.getAlias(),
                    selectedIdentity.getImage()
            );

            final ActorIdentityInformation actorReceiving = new ActorIdentityInformation(
                    chatActorToContact.getPublicKey(),
                    Actors.CHAT,
                    chatActorToContact.getAlias(),
                    chatActorToContact.getImage()
            );

            chatActorConnectionManager.requestConnection(
                    actorSending,
                    actorReceiving
            );
        } catch (ConnectionAlreadyRequestedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,e);
        } catch (com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnsupportedActorTypeException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    public void acceptChatActor(UUID requestId) throws CantAcceptChatRequestException, ActorConnectionRequestNotFoundException {
        try {
            chatActorConnectionManager.acceptConnection(requestId);
        } catch (CantAcceptActorConnectionRequestException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (ActorConnectionNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnexpectedConnectionStateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    @Override
    public void denyChatConnection(UUID requestId) throws ChatActorConnectionDenialFailedException,
            ActorConnectionRequestNotFoundException {

        try {

            chatActorConnectionManager.denyConnection(requestId);

        } catch (CantDenyActorConnectionRequestException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (ActorConnectionNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnexpectedConnectionStateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

    }

    @Override
    public void disconnectChatActor(UUID requestId) throws ChatActorDisconnectingFailedException,
            ActorConnectionRequestNotFoundException, ConnectionRequestNotFoundException, CantDisconnectFromActorException, UnexpectedConnectionStateException, ActorConnectionNotFoundException {

        try {

            chatActorConnectionManager.disconnect(requestId);

        } catch (CantDisconnectFromActorException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (ActorConnectionNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnexpectedConnectionStateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

    }

    @Override
    public void cancelChatActor(UUID requestId) throws ChatActorCancellingFailedException,
            ActorConnectionRequestNotFoundException, ConnectionRequestNotFoundException {

        try {

            chatActorConnectionManager.cancelConnection(requestId);

        } catch (CantCancelActorConnectionRequestException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (ActorConnectionNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnexpectedConnectionStateException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    @Override
    public List<ChatActorCommunityInformation> listAllConnectedChatActor(ChatActorCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListChatActorException {
        List<ChatActorCommunityInformation> chatActorCommunityInformationList = null;
        try{
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
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return chatActorCommunityInformationList;

    }


    @Override
    public List<ChatActorCommunityInformation> listChatActorPendingLocalAction(ChatActorCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListChatActorException {

        List<ChatActorCommunityInformation> chatActorCommunityInformationList = null;
        try {
            final ChatLinkedActorIdentity linkedChatActor = new ChatLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActor);

            search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);

            final List<ChatActorConnection> actorConnections = search.getResult(max, offset);

            chatActorCommunityInformationList = new ArrayList<>();

            for (ChatActorConnection cac : actorConnections)
                chatActorCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(cac));
        }

        catch(CantListActorConnectionsException e){
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

        return chatActorCommunityInformationList;
    }







    @Override
    public List<ChatActorCommunityInformation> listChatActorPendingRemoteAction(ChatActorCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListChatActorException {
        List<ChatActorCommunityInformation> chatActorCommunityInformationList = null;
        try {
            final ChatLinkedActorIdentity linkedChatActor = new ChatLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActor);

            search.addConnectionState(ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

            final List<ChatActorConnection> actorConnections = search.getResult(max, offset);

            chatActorCommunityInformationList = new ArrayList<>();

            for (ChatActorConnection cac : actorConnections)
                chatActorCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(cac));
        } catch(CantListActorConnectionsException e){
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }


        return chatActorCommunityInformationList;
    }

    public List<ChatActorCommunityInformation> getChatActorWaitingYourAcceptanceCount(final String PublicKey, int max, int offset) throws CantGetChatActorWaitingException {
        List<ChatActorCommunityInformation> actorList = null;
        try {
            List<ChatActorCommunityInformation> chatActorList;
            actorList = new ArrayList<>();

            chatActorList = this.chatActorCommunitySubAppModuleManager.getChatActorWaitingYourAcceptanceCount(PublicKey, max, offset);



            for (ChatActorCommunityInformation record : chatActorList)
                actorList.add((new ChatActorCommunitySubAppModuleInformationImpl(
                        record.getPublicKey(),
                        record.getAlias(),
                        record.getImage(),
                        record.getConnectionState(),
                        record.getConnectionId())));


        } catch (CantGetChatActorWaitingException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_COMMUNITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            try {
                throw new CantGetChatActorWaitingException("CAN'T GET CHAT ACTOR WAITING THEIR ACCEPTANCE", e, "", "Error on CHAT ACTOR MANAGER");
            } catch (CantGetChatActorWaitingException e1) {
                e1.printStackTrace();
            }
        }
        return actorList;
    }



    @Override
    public ConnectionState getActorConnectionState(String publicKey) throws CantValidateActorConnectionStateException {

        try{
            ChatActorCommunitySelectableIdentity selectedIdentity = getSelectedActorIdentity();
            final ChatLinkedActorIdentity linkedChatActor = new ChatLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActor);
            final List<ChatActorConnection> actorConnections = search.getResult(Integer.MAX_VALUE,0);

            for (ChatActorConnection connection : actorConnections){
                if(publicKey.equals(connection.getPublicKey()))
                    return connection.getConnectionState();
            }

        } catch (final CantListActorConnectionsException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantValidateActorConnectionStateException(e, "", "Error trying to list actor connections.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

        return ConnectionState.DISCONNECTED_REMOTELY;
    }

    @Override
    public SettingsManager<ChatActorCommunitySettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ChatActorCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        //Try to get appSettings
        ChatActorCommunitySettings appSettings = null;
        try {
            appSettings = this.getSettingsManager().loadAndGetSettings(SubAppsPublicKeys.CHT_COMMUNITY.getCode()); //this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){
            //errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            appSettings = new ChatActorCommunitySettings();
            try {
                this.getSettingsManager().persistSettings(SubAppsPublicKeys.CHT_COMMUNITY.getCode(), appSettings);
            } catch (CantPersistSettingsException e1) {
                //e1.printStackTrace();
            }
            return null;
        }

        List<ChatIdentity> IdentitiesInDevice = new ArrayList<>();
        try{
            IdentitiesInDevice = chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();
            //TODO:Revisar como asignar estos valores deben ser seteados al entrar a la comunidad setear los settings necesario
            if(IdentitiesInDevice != null && IdentitiesInDevice.size() > 0) {
                appSettings.setLastSelectedIdentityPublicKey(IdentitiesInDevice.get(0).getPublicKey());
                appSettings.setLastSelectedActorType(IdentitiesInDevice.get(0).getActorType());
            }
        } catch(CantListChatIdentityException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            /*Do nothing*/
        }


        //If appSettings exists, get its selectedActorIdentityPublicKey property
        if(appSettings != null)
        {
            String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();
            Actors lastSelectedActorType = appSettings.getLastSelectedActorType();

            if (lastSelectedIdentityPublicKey != null && lastSelectedActorType != null) {

                ChatActorCommunitySelectableIdentityImpl selectedIdentity = null;

                if(lastSelectedActorType == Actors.CHAT)
                {
                    for(ChatIdentity i : IdentitiesInDevice) {
                        if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new ChatActorCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.CHAT, i.getAlias(), i.getImage());
                    }
                }
                if(selectedIdentity == null)
                    //throw new ActorIdentityNotSelectedException("", null, "", "");

                return selectedIdentity;
            }
            else{}
                //throw new ActorIdentityNotSelectedException("", null, "", "");
        }

        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        chatIdentityManager.createNewIdentityChat(name, profile_img, "country", "state", "city", "available");


        //Try to get appSettings
        ChatActorCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }


        //If appSettings exist
        if(appSettings != null){
            appSettings.setLastSelectedActorType(Actors.CHAT);

            try {
                this.settingsManager.persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }


    @Override
    public void setAppPublicKey(String publicKey) { this.subAppPublicKey= publicKey;}



    @Override
    public int[] getMenuNotifications() {
        int[] notifications = new int[4];
        try {
            if(getSelectedActorIdentity() != null)
                notifications[2] = chatActorCommunitySubAppModuleManager.getChatActorWaitingYourAcceptanceCount(getSelectedActorIdentity().getPublicKey(),99,0).size();
            else
                notifications[2] = 0;
        } catch (CantGetSelectedActorIdentityException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (ActorIdentityNotSelectedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetChatActorWaitingException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return notifications;
    }
}
