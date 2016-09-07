package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
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
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.ChatActorCommunitySubAppModulePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 * Edited by Miguel Rincon on 18/04/2016
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 15/06/16.
 */
public class ChatActorCommunityManager
        extends ModuleManagerImpl<ChatActorCommunitySettings>
        implements ChatActorCommunitySubAppModuleManager, Serializable {

    private final ChatIdentityManager chatIdentityManager;
    private final ChatActorConnectionManager chatActorConnectionManager;
    private final ChatManager chatActorNetworkServiceManager;
    private Broadcaster broadcaster;

    private String subAppPublicKey;
    private final ChatActorCommunitySubAppModulePluginRoot chatActorCommunitySubAppModulePluginRoot;
    private final GeolocationManager geolocationManager;
    private final LocationManager locationManager;

    public ChatActorCommunityManager(ChatIdentityManager chatIdentityManager,
                                     ChatActorConnectionManager chatActorConnectionManager,
                                     ChatManager chatActorNetworkServiceManager,
                                     Broadcaster broadcaster,
                                     ChatActorCommunitySubAppModulePluginRoot chatActorCommunitySubAppModulePluginRoot,
                                     PluginFileSystem pluginFileSystem, UUID pluginId,
                                     GeolocationManager geolocationManager,
                                     LocationManager locationManager) {
        super(pluginFileSystem, pluginId);
        this.chatIdentityManager = chatIdentityManager;
        this.chatActorConnectionManager = chatActorConnectionManager;
        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;
        this.broadcaster = broadcaster;
        this.chatActorCommunitySubAppModulePluginRoot = chatActorCommunitySubAppModulePluginRoot;
        this.geolocationManager = geolocationManager;
        this.locationManager = locationManager;
    }

    public void listWorldChatActor(String publicKey, Actors actorType, DeviceLocation deviceLocation, double distance, String alias, int max, int offset, String requesterPublicKey) throws CantListChatActorException, CantGetChtActorSearchResult, CantListActorConnectionsException {
        getChatActorSearch().getResult(publicKey, deviceLocation, distance, alias, offset, max, requesterPublicKey);
    }

    @Override
    public ChatActorCommunitySearch getChatActorSearch() {
        return new ChatActorCommunitySubAppModuleSearch(chatActorNetworkServiceManager) {
        };
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
        List<ChatActorCommunityInformation> chatActorCommunityInformationList = new ArrayList<>();
        //ChatExposingData chatExposingData = null;
        try {
            if (selectedIdentity != null) {

                final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(selectedIdentity);

                search.addConnectionState(ConnectionState.CONNECTED);

                final List<ChatActorConnection> actorConnections = search.getResult(max, offset);

                for (ChatActorConnection cac : actorConnections) {
                    chatActorCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(cac,null));
                }

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
            if (publicKey != null && actorType != null) {
                final ChatLinkedActorIdentity linkedChatActor = new ChatLinkedActorIdentity(
                        publicKey,
                        actorType
                );

                final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActor);

                search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);

                final List<ChatActorConnection> actorConnections = search.getResult(max, offset);

                chatActorCommunityInformationList = new ArrayList<>();

                for (ChatActorConnection cac : actorConnections)
                    chatActorCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(cac, null));

            }
        } catch (CantListActorConnectionsException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

        return chatActorCommunityInformationList;
    }

    @Override
    public List<ChatActorCommunityInformation> listChatActorPendingRemoteAction(ChatActorCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListChatActorException {
        List<ChatActorCommunityInformation> chatActorCommunityInformationList = null;
        try {
            if (selectedIdentity != null) {

                final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(selectedIdentity);

                search.addConnectionState(ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

                final List<ChatActorConnection> actorConnections = search.getResult(max, offset);

                chatActorCommunityInformationList = new ArrayList<>();

                for (ChatActorConnection cac : actorConnections)
                    chatActorCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(cac, null));
            }
        } catch (CantListActorConnectionsException e) {
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
                        record.getLocation(),
                        ProfileStatus.ONLINE)));


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

        try {
            ChatActorCommunitySelectableIdentity selectedIdentity = getSelectedActorIdentity();
            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(selectedIdentity);
            return search.getConnectionState(publicKey);

        } catch (final CantGetActorConnectionException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantValidateActorConnectionStateException(e, "", "Error trying to list actor connections.");
        } catch (Exception e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }

        return ConnectionState.DISCONNECTED_REMOTELY;
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
        } catch (Exception e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            appSettings = null;
        }

        if (appSettings == null) {
            appSettings = new ChatActorCommunitySettings();
        }
        List<ChatIdentity> IdentitiesInDevice = new ArrayList<>();
        try {
            IdentitiesInDevice = chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();
            //TODO:Revisar como asignar estos valores deben ser seteados al entrar a la comunidad setear los settings necesario
            if (IdentitiesInDevice != null && IdentitiesInDevice.size() > 0) {
                appSettings.setLastSelectedIdentityPublicKey(IdentitiesInDevice.get(0).getPublicKey());
                appSettings.setLastSelectedActorType(IdentitiesInDevice.get(0).getActorType());
            }
        } catch (CantListChatIdentityException e) {
            chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
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
        } catch (Exception e) {
            appSettings = null;
        }


        //If appSettings exist
        if (appSettings != null) {
            appSettings.setLastSelectedActorType(Actors.CHAT);

            try {
                this.persistSettings(this.subAppPublicKey, appSettings);
            } catch (CantPersistSettingsException e) {
                chatActorCommunitySubAppModulePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.subAppPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        int[] notifications = new int[4];
        try {
            if (getSelectedActorIdentity() != null)
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

    public void handleActorListReceivedEvent(List<ChatExposingData> actorProfileList) throws FermatException {

        List<ChatActorCommunityInformation> worldActorList = new ArrayList<>();

        final ChatActorCommunitySelectableIdentity selectableIdentity = getSelectedActorIdentity();

        final ChatLinkedActorIdentity linkedChatActorIdentity = new ChatLinkedActorIdentity(selectableIdentity.getPublicKey(), selectableIdentity.getActorType());
        final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(linkedChatActorIdentity);

        ConnectionState connectionState;
        UUID connectionID;
        String country, city, state;

        for (ChatExposingData worldActor : actorProfileList) {

            System.out.println(" chat actor community manager processiong -> "+worldActor.getAlias());

            country = "--";
            city = "--";
            state = "--";
            final Location location = worldActor.getLocation();
            try {
                if(location!=null) {
                    //if(location.getLatitude() != null && location.getAltitude() != null) {
                    final Address address = geolocationManager.getAddressByCoordinate(location.getLatitude(), location.getLongitude());
                    country = address.getCountry();
                    city = address.getCity().equals("null") ? address.getCounty() : address.getCity();
                    state = address.getState().equals("null") ? address.getCounty() : address.getState();
                    //}
                }
            } catch (CantCreateAddressException ignore) {
            }
            try {

                ChatActorConnection connectedActor = search.findByPublicKey(worldActor.getPublicKey());

                connectionState = connectedActor.getConnectionState();
                connectionID = connectedActor.getConnectionId();

                if (!worldActor.getAlias().equals(connectedActor.getAlias())) {
                    chatActorConnectionManager.updateAlias(connectionID, worldActor.getAlias());
                    //todo see if necessary change more fields
                }

                if (!Arrays.equals(worldActor.getImage(), connectedActor.getImage()))
                    chatActorConnectionManager.updateImage(connectionID, worldActor.getImage() != null ? worldActor.getImage() : new byte[0]);

            } catch (ActorConnectionNotFoundException ex) {
                connectionState = null;
                connectionID = null;
            } catch (Exception ex) {
                ex.printStackTrace();
                connectionState = null;
                connectionID = null;
            }

            worldActorList.add(
                    new ChatActorCommunitySubAppModuleInformationImpl(
                            worldActor.getPublicKey(),
                            worldActor.getAlias(),
                            worldActor.getImage(),
                            connectionState,
                            connectionID,
                            worldActor.getStatus(),
                            country,
                            state,
                            city,
                            location,
                            worldActor.getStatusConnected()
                    )
            );

        }

        FermatBundle bundle = new FermatBundle();

        bundle.put(SOURCE_PLUGIN, Plugins.CHAT_COMMUNITY_SUP_APP_MODULE.getCode());
        bundle.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_COMMUNITY.getCode());
        bundle.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_COMM_ACTOR_RECEIVED);
        bundle.put(ChatBroadcasterConstants.CHAT_COMM_ACTOR_LIST, worldActorList);

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_COMMUNITY.getCode(), bundle);
    }
}
