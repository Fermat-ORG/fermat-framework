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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
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
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.Cities;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.ultils.CitiesImpl;
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.ChatActorCommunitySubAppModulePluginRoot;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateGeoRectangleException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCountryDependenciesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Country;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeoRectangle;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 * Edited by Miguel Rincon on 18/04/2016
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 15/06/16.
 */
public class ChatActorCommunityManager extends ModuleManagerImpl<ChatActorCommunitySettings> implements ChatActorCommunitySubAppModuleManager, Serializable {

    private final ChatIdentityManager                      chatIdentityManager;
    private final ChatActorConnectionManager               chatActorConnectionManager            ;
    private final ChatManager                              chatActorNetworkServiceManager        ;
    private String                                         subAppPublicKey                       ;
    private final ChatActorCommunitySubAppModulePluginRoot chatActorCommunitySubAppModulePluginRoot;
    private final PluginFileSystem                         pluginFileSystem                      ;
    private final UUID                                     pluginId                              ;
    private final PluginVersionReference                   pluginVersionReference                ;
    private final GeolocationManager                       geolocationManager                    ;
    private final LocationManager                          locationManager                       ;

    public ChatActorCommunityManager(ChatIdentityManager chatIdentityManager,
                                     ChatActorConnectionManager chatActorConnectionManager,
                                     ChatManager chatActorNetworkServiceManager,
                                     ChatActorCommunitySubAppModulePluginRoot chatActorCommunitySubAppModulePluginRoot,
                                     PluginFileSystem pluginFileSystem, UUID pluginId,
                                     PluginVersionReference pluginVersionReference,
                                     GeolocationManager geolocationManager,
                                     LocationManager locationManager) {
        super(pluginFileSystem, pluginId);
        this.chatIdentityManager= chatIdentityManager;
        this.chatActorConnectionManager=chatActorConnectionManager;
        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;
        this.chatActorCommunitySubAppModulePluginRoot = chatActorCommunitySubAppModulePluginRoot;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.pluginVersionReference= pluginVersionReference;
        this.geolocationManager = geolocationManager;
        this.locationManager = locationManager;
    }

    @Override
    public List<ChatActorCommunityInformation> listWorldChatActor(String publicKey, Actors actorType, DeviceLocation deviceLocation, double distance, String alias, int max, int offset) throws CantListChatActorException, CantGetChtActorSearchResult, CantListActorConnectionsException {
        List<ChatActorCommunityInformation> worldActorList = null;
        List<ChatActorConnection> actorConnections = null;

        try{
            worldActorList = getChatActorSearch().getResult(publicKey, deviceLocation, distance, alias, offset, max);
        } catch (CantGetChtActorSearchResult exception) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        try{
            if(publicKey!=null && actorType!= null) {
                final ChatLinkedActorIdentity linkedChatActorIdentity = new ChatLinkedActorIdentity(publicKey, actorType);
                final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActorIdentity);

                actorConnections = search.getResult(1000, 0);
//                actorConnections = search.getResult(Integer.MAX_VALUE, 0);
            }//else linkedChatActorIdentity=null;
        } catch (CantListActorConnectionsException exception) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        ChatActorCommunityInformation worldActor;
        if(actorConnections != null && worldActorList != null
                && actorConnections.size() > 0 && worldActorList.size() > 0) {
            for (int i = 0; i < worldActorList.size(); i++) {
                worldActor = worldActorList.get(i);
                for (ChatActorConnection connectedActor : actorConnections) {
                    if (worldActor.getPublicKey().equals(connectedActor.getPublicKey()))
                        worldActorList.set(i,
                                new ChatActorCommunitySubAppModuleInformationImpl(
                                        worldActor.getPublicKey(), worldActor.getAlias(),
                                        worldActor.getImage(), connectedActor.getConnectionState(),
                                        connectedActor.getConnectionId(), worldActor.getStatus(),
                                        connectedActor.getCountry(),connectedActor.getState(),
                                        connectedActor.getCity(), null));
                }
            }
        }
        return worldActorList;
    }

    @Override
    public List<ChatActorCommunitySelectableIdentity> listSelectableIdentities()
            throws CantListChatIdentitiesToSelectException, CantListChatIdentityException {

        List<ChatActorCommunitySelectableIdentity> selectableIdentities = null;
        try {
            selectableIdentities = new ArrayList<>();
            final List<ChatIdentity> chatActorIdentity = chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();
            for (final ChatIdentity chi : chatActorIdentity)
                selectableIdentities.add(new ChatActorCommunitySelectableIdentityImpl(chi));

        } catch (CantListChatIdentityException exception) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }
        return selectableIdentities;
    }

    @Override
    public void setSelectedActorIdentity(ChatActorCommunitySelectableIdentity identity) throws CantPersistSettingsException, CantGetSettingsException, SettingsNotFoundException {

        ChatActorCommunitySettings appSettings = null;
        try {
            appSettings = this.loadAndGetSettings(this.subAppPublicKey);
        }catch (CantGetSettingsException | SettingsNotFoundException e){
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            appSettings = null;
        }

        //If appSettings exist, save identity
        if(appSettings != null){
            if(identity.getPublicKey() != null)
                appSettings.setLastSelectedIdentityPublicKey(identity.getPublicKey());
            if(identity.getActorType() != null)
                appSettings.setLastSelectedActorType(identity.getActorType());
            try {
                this.persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnsupportedActorTypeException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    public void acceptChatActor(UUID requestId) throws CantAcceptChatRequestException, ActorConnectionRequestNotFoundException {
        try {
            chatActorConnectionManager.acceptConnection(requestId);
        } catch (CantAcceptActorConnectionRequestException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (ActorConnectionNotFoundException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnexpectedConnectionStateException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    @Override
    public void denyChatConnection(UUID requestId) throws ChatActorConnectionDenialFailedException,
            ActorConnectionRequestNotFoundException {

        try {

            chatActorConnectionManager.denyConnection(requestId);

        } catch (CantDenyActorConnectionRequestException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (ActorConnectionNotFoundException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnexpectedConnectionStateException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

    }

    @Override
    public void disconnectChatActor(UUID requestId) throws ChatActorDisconnectingFailedException,
            ActorConnectionRequestNotFoundException, ConnectionRequestNotFoundException, CantDisconnectFromActorException, UnexpectedConnectionStateException, ActorConnectionNotFoundException {

        try {

            chatActorConnectionManager.disconnect(requestId);

        } catch (CantDisconnectFromActorException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (ActorConnectionNotFoundException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnexpectedConnectionStateException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

    }

    @Override
    public void cancelChatActor(UUID requestId) throws ChatActorCancellingFailedException,
            ActorConnectionRequestNotFoundException, ConnectionRequestNotFoundException {

        try {

            chatActorConnectionManager.cancelConnection(requestId);

        } catch (CantCancelActorConnectionRequestException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (ActorConnectionNotFoundException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        } catch (UnexpectedConnectionStateException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    @Override
    public List<ChatActorCommunityInformation> listAllConnectedChatActor(ChatActorCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListChatActorException {
        List<ChatActorCommunityInformation> chatActorCommunityInformationList = null;
        try{
            if(selectedIdentity!=null) {
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
            }
        } catch (CantListActorConnectionsException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return chatActorCommunityInformationList;

    }


    @Override
    public List<ChatActorCommunityInformation> listChatActorPendingLocalAction(String publicKey, Actors actorType, int max, int offset) throws CantListChatActorException {

        List<ChatActorCommunityInformation> chatActorCommunityInformationList = null;
        try {
            if (publicKey!= null && actorType != null) {
                final ChatLinkedActorIdentity linkedChatActor = new ChatLinkedActorIdentity(
                        publicKey,
                        actorType
                );

                final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActor);

                search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);

                final List<ChatActorConnection> actorConnections = search.getResult(max, offset);

                chatActorCommunityInformationList = new ArrayList<>();

                for (ChatActorConnection cac : actorConnections)
                    chatActorCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(cac));

            }
        }
        catch(CantListActorConnectionsException e){
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

        return chatActorCommunityInformationList;
    }

    @Override
    public List<ChatActorCommunityInformation> listChatActorPendingRemoteAction(ChatActorCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListChatActorException {
        List<ChatActorCommunityInformation> chatActorCommunityInformationList = null;
        try {
            if (selectedIdentity != null) {
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
            }
        } catch(CantListActorConnectionsException e){
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

        return chatActorCommunityInformationList;
    }

    public List<ChatActorCommunityInformation> getChatActorWaitingYourAcceptanceCount(final String PublicKey, int max, int offset) throws CantGetChatActorWaitingException {
        List<ChatActorCommunityInformation> actorList = null;
        try {
            List<ChatActorCommunityInformation> chatActorList;
            actorList = new ArrayList<>();

            chatActorList = this.getChatActorWaitingYourAcceptanceCount(PublicKey, max, offset);

            for (ChatActorCommunityInformation record : chatActorList)
                actorList.add((new ChatActorCommunitySubAppModuleInformationImpl(
                        record.getPublicKey(),
                        record.getAlias(),
                        record.getImage(),
                        record.getConnectionState(),
                        record.getConnectionId(),
                        record.getStatus(),
                        record.getCountry(),
                        record.getState(),
                        record.getCity(),
                        record.getLocation())));


        } catch (CantGetChatActorWaitingException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantValidateActorConnectionStateException(e, "", "Error trying to list actor connections.");
        } catch (Exception e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

        return ConnectionState.DISCONNECTED_REMOTELY;
    }

    @Override
    public HashMap<String, Country> getCountryList() throws CantConnectWithExternalAPIException, CantCreateBackupFileException, CantCreateCountriesListException {
        return geolocationManager.getCountryList();
    }

    @Override
    public List<CountryDependency> getCountryDependencies(String countryCode) throws CantGetCountryDependenciesListException, CantConnectWithExternalAPIException, CantCreateBackupFileException {
        return geolocationManager.getCountryDependencies(countryCode);
    }

    @Override
    public List<City> getCitiesByCountryCode(String countryCode) throws CantGetCitiesListException {
        return geolocationManager.getCitiesByCountryCode(countryCode);
    }

    @Override
    public List<City> getCitiesByCountryCodeAndDependencyName(String countryName, String dependencyName) throws CantGetCitiesListException, CantCreateCountriesListException {
        return geolocationManager.getCitiesByCountryCodeAndDependencyName(countryName, dependencyName);
    }

    @Override
    public GeoRectangle getGeoRectangleByLocation(String location) throws CantCreateGeoRectangleException {
        return geolocationManager.getGeoRectangleByLocation(location);
    }

    @Override
    public Address getAddressByCoordinate(double latitude, double longitude) throws CantCreateAddressException {
        return geolocationManager.getAddressByCoordinate(latitude, longitude);
    }

    @Override
    public GeoRectangle getRandomGeoLocation() throws CantCreateGeoRectangleException {
        return geolocationManager.getRandomGeoLocation();
    }

    @Override
    public Location getLocation() throws CantGetDeviceLocationException {
        return locationManager.getLocation();
    }

    @Override
    public List<ExtendedCity> getExtendedCitiesByFilter(String filter) throws CantGetCitiesListException {
        return geolocationManager.getExtendedCitiesByFilter(filter);
    }

    @Override
    public ChatActorCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        //Try to get appSettings
        ChatActorCommunitySettings appSettings = null;
        try {
            appSettings = this.loadAndGetSettings(SubAppsPublicKeys.CHT_COMMUNITY.getCode());
        }catch (Exception e){
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            appSettings = null;
        }

        if(appSettings==null){
            appSettings = new ChatActorCommunitySettings();
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
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
            /*Do nothing*/
        }

        //If appSettings exists, get its selectedActorIdentityPublicKey property
        if(appSettings != null) {
            String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();
            Actors lastSelectedActorType = appSettings.getLastSelectedActorType();

            if (lastSelectedIdentityPublicKey != null && lastSelectedActorType != null) {

                ChatActorCommunitySelectableIdentityImpl selectedIdentity = null;

                if(lastSelectedActorType == Actors.CHAT)
                {
                    for(ChatIdentity i : IdentitiesInDevice) {
                        if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new ChatActorCommunitySelectableIdentityImpl(
                                    i.getPublicKey(), Actors.CHAT, i.getAlias(), i.getImage(),
                                    i.getConnectionState(), i.getCountry(), i.getState(),
                                    i.getCity(), i.getConnectionState(), i.getAccuracy(), i.getFrecuency());
                    }
                }

                return selectedIdentity;
            }
        }

        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        //TODO: Revisar este metodo que hace aca
        chatIdentityManager.createNewIdentityChat(name, profile_img, "country", "state", "city", "available", 0, null);

        //Try to get appSettings
        ChatActorCommunitySettings appSettings = null;
        try {
            appSettings = this.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }


        //If appSettings exist
        if(appSettings != null){
            appSettings.setLastSelectedActorType(Actors.CHAT);

            try {
                this.persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
                notifications[2] = this.getChatActorWaitingYourAcceptanceCount(getSelectedActorIdentity().getPublicKey(), 99, 0).size();
            else
                notifications[2] = 0;
        } catch (CantGetSelectedActorIdentityException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (ActorIdentityNotSelectedException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (CantGetChatActorWaitingException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return notifications;
    }

}
