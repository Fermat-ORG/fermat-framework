package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionRequest;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.ArtCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.utils.ArtistCommunityInformationImpl;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanListException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanSearchResult;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListFansException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantLoginFanException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.FanCancellingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.FanDisconnectingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySearch;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.LinkedFanIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.settings.FanCommunitySettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.utils.FanCommunityInformationImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public class FanCommunityManager
        extends ModuleManagerImpl<FanCommunitySettings>
        implements FanCommunityModuleManager,Serializable {
    private final ArtistIdentityManager                artistIdentityManager                    ;
    private final ArtistActorConnectionManager         artistActorConnectionManager             ;
    private final ArtistManager                        artistActorNetworkServiceManager         ;
    private final FanActorConnectionManager            fanActorConnectionManager                ;
    private final FanManager                           fanActorNetworkServiceManager            ;
    private final FanaticIdentityManager               fanaticIdentityManager                   ;
    private final ErrorManager                         errorManager                             ;
    private final PluginVersionReference               pluginVersionReference                   ;

    private SettingsManager<FanCommunitySettings> settingsManager                               ;

    private       String                              subAppPublicKey                           ;

    private boolean isDialog = true;

    public FanCommunityManager(
            final ArtistIdentityManager artistIdentityManager,
            final FanActorConnectionManager fanActorConnectionManager,
            final FanManager fanActorNetworkServiceManager,
            final FanaticIdentityManager fanaticIdentityManager,
            final ErrorManager errorManager,
            final PluginFileSystem pluginFileSystem,
            final UUID pluginId,
            final PluginVersionReference pluginVersionReference,
            final ArtistActorConnectionManager artistActorConnectionManager,
            final ArtistManager artistActorNetworkServiceManager) {
        super(pluginFileSystem, pluginId);
        this.artistIdentityManager                    = artistIdentityManager                    ;
        this.fanActorConnectionManager                = fanActorConnectionManager                ;
        this.fanActorNetworkServiceManager            = fanActorNetworkServiceManager            ;
        this.fanaticIdentityManager                   = fanaticIdentityManager                   ;
        this.errorManager                             = errorManager                             ;
        this.pluginVersionReference                   = pluginVersionReference                   ;
        this.artistActorConnectionManager             = artistActorConnectionManager             ;
        this.artistActorNetworkServiceManager         = artistActorNetworkServiceManager         ;
    }

    @Override
    public List<FanCommunityInformation> listWorldFan(FanCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListFansException {
        List<FanCommunityInformation> worldFanaticList;
        List<FanActorConnection> actorConnections;

        try{
            worldFanaticList = getFanaticSearch().getResult();
        } catch (CantGetFanSearchResult e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListFansException(e, "", "Error in listWorldFan trying to list world Fanatics");
        }


        try {

            final FanLinkedActorIdentity linkedActorIdentity = new FanLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final FanActorConnectionSearch search = fanActorConnectionManager.getSearch(linkedActorIdentity);
            search.addConnectionState(ConnectionState.CONNECTED);

            actorConnections = search.getResult(Integer.MAX_VALUE, 0);

        } catch (final CantListActorConnectionsException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListFansException(e, "", "Error trying to list actor connections.");
        }


        FanCommunityInformation worldFanatic;
        for(int i = 0; i < worldFanaticList.size(); i++)
        {
            worldFanatic = worldFanaticList.get(i);
            for(FanActorConnection connectedFan : actorConnections)
            {
                if(worldFanatic.getPublicKey().equals(connectedFan.getPublicKey()))
                    worldFanaticList.set(i, new com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.utils.FanCommunityInformationImpl(worldFanatic.getPublicKey(), worldFanatic.getAlias(), worldFanatic.getImage(), connectedFan.getConnectionState(), connectedFan.getConnectionId()));
            }
        }

        return worldFanaticList;    }

    @Override
    public List<FanCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {
        try {

            final List<FanCommunitySelectableIdentity> selectableIdentities = new ArrayList<>();

            final List<Artist> artistIdentities = artistIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final Artist ai : artistIdentities)
                selectableIdentities.add(new FanCommunitySelectableIdentityImpl(ai));

            final List<Fanatic> fanaticsIdentities = fanaticIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final Fanatic ai : fanaticsIdentities)
                selectableIdentities.add(new FanCommunitySelectableIdentityImpl(ai));

            return selectableIdentities;

        } catch (final CantListArtistIdentitiesException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Error in DAO trying to list identities.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Unhandled Exception.");
        }    }

    @Override
    public void setSelectedActorIdentity(FanCommunitySelectableIdentity identity) {
        FanCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }

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
    public List<LinkedFanIdentity> listFansPendingLocalAction(FanCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantGetFanListException {
        try {

            final FanLinkedActorIdentity linkedActorIdentity = new FanLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final FanActorConnectionSearch search = fanActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);

            final List<FanActorConnection> actorConnections = search.getResult(max, offset);

            final List<LinkedFanIdentity> linkedFanaticIdentityList = new ArrayList<>();

            for (FanActorConnection fac : actorConnections)
                linkedFanaticIdentityList.add(new LinkedFanIdentityImpl(fac));

            return linkedFanaticIdentityList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetFanListException("", e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetFanListException("", e, "", "Unhandled Exception.");
        }    }

    @Override
    public List<LinkedFanIdentity> listFansPendingRemoteAction(FanCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantGetFanListException {
        try {

            final FanLinkedActorIdentity linkedActorIdentity = new FanLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final FanActorConnectionSearch search = fanActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

            final List<FanActorConnection> actorConnections = search.getResult(max, offset);

            final List<LinkedFanIdentity> linkedFanaticIdentityList = new ArrayList<>();

            for (FanActorConnection fac : actorConnections)
                linkedFanaticIdentityList.add(new LinkedFanIdentityImpl(fac));

            return linkedFanaticIdentityList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetFanListException("", e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetFanListException("", e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<ArtCommunityInformation> listAllConnectedFans(
            FanCommunitySelectableIdentity selectedIdentity,
            int max,
            int offset) throws CantGetFanListException {
        try {

            final List<ArtCommunityInformation> allActorConnectedList = new ArrayList<>();
            final List<String> actorConnectedPublicKeyList = new ArrayList<>();
            String publicKey;
            PlatformComponentType platformComponentType;
            //Fan connected search.
            final FanLinkedActorIdentity linkedActorIdentity = new FanLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );
            FanCommunityInformationImpl fanCommunityInformation;
            final List<FanConnectionRequest> fanConnectionRequestList =
                    fanActorNetworkServiceManager.listAllRequest();
            final FanActorConnectionSearch search = fanActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            final List<FanActorConnection> actorConnections = search.getResult(max, offset);

            //final List<FanCommunityInformation> fanaticCommunityInformationList = new ArrayList<>();

            for (FanActorConnection fac : actorConnections){
                //fanaticCommunityInformationList.add(new FanCommunityInformationImpl(fac));
                publicKey=fac.getPublicKey();
                if(!actorConnectedPublicKeyList.contains(publicKey)){
                    actorConnectedPublicKeyList.add(publicKey);
                    platformComponentType = getActorTypeFromRequest(
                            fanConnectionRequestList,
                            publicKey);
                    fanCommunityInformation = new FanCommunityInformationImpl(fac);
                    switch (platformComponentType){
                        case ART_FAN:
                            fanCommunityInformation.setActorType(Actors.ART_FAN);
                            break;
                        case ART_ARTIST:
                            fanCommunityInformation.setActorType(Actors.ART_ARTIST);
                            break;
                    }
                    allActorConnectedList.add(fanCommunityInformation);
                }
            }

            //Artist connected search
            ArtistCommunityInformationImpl artistCommunityInformation;
            final List<ArtistConnectionRequest> artistConnectionRequestList =
                    artistActorNetworkServiceManager.listAllRequest();
            final ArtistLinkedActorIdentity artistLinkedActorIdentity = new ArtistLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );
            final ArtistActorConnectionSearch artistActorConnectionSearch =
                    artistActorConnectionManager.getSearch(artistLinkedActorIdentity);
            artistActorConnectionSearch.addConnectionState(ConnectionState.CONNECTED);
            final List<ArtistActorConnection> artistActorConnectionList =
                    artistActorConnectionSearch.getResult(
                            max,
                            offset);
            for (ArtistActorConnection aac : artistActorConnectionList){
                publicKey=aac.getPublicKey();
                if(!actorConnectedPublicKeyList.contains(publicKey)) {
                    actorConnectedPublicKeyList.add(publicKey);
                    platformComponentType = getActorTypeFromRequestFromArtist(
                            artistConnectionRequestList,
                            publicKey);
                    artistCommunityInformation = new ArtistCommunityInformationImpl(aac);
                    switch (platformComponentType) {
                        case ART_FAN:
                            artistCommunityInformation.setActorType(Actors.ART_FAN);
                            break;
                        case ART_ARTIST:
                            artistCommunityInformation.setActorType(Actors.ART_ARTIST);
                            break;
                    }
                    allActorConnectedList.add(artistCommunityInformation);
                }
            }

            return allActorConnectedList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetFanListException("", e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetFanListException("", e, "", "Unhandled Exception.");
        }    }

    @Override
    public void acceptFan(UUID connectionId) throws CantAcceptRequestException {
        try {
            System.out.println("************* im accepting in module the request: "+connectionId);
            this.fanActorConnectionManager.acceptConnection(connectionId);
        } catch (CantAcceptActorConnectionRequestException | ActorConnectionNotFoundException | UnexpectedConnectionStateException e)
        {
            throw new CantAcceptRequestException("", e, "", "");
        }
    }

    @Override
    public void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException {
        try {
            this.fanActorConnectionManager.denyConnection(connectionId);
        } catch (CantDenyActorConnectionRequestException | ActorConnectionNotFoundException | UnexpectedConnectionStateException e)
        {
            throw new CantDenyActorConnectionRequestException("", e, "", "");
        }
    }

    @Override
    public List<FanCommunityInformation> getSuggestionsToContact(int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public FanCommunitySearch getFanaticSearch() {
        return new FanCommunitySearchImpl(fanActorNetworkServiceManager);
    }

    @Override
    public void requestConnectionToFan(
            FanCommunitySelectableIdentity selectedIdentity,
            FanCommunityInformation artistToContact) throws
            CantRequestConnectionException,
            ActorConnectionAlreadyRequestedException,
            ActorTypeNotSupportedException{
        try {

            final ActorIdentityInformation actorSending = new ActorIdentityInformation(
                    selectedIdentity.getPublicKey()   ,
                    selectedIdentity.getActorType()   ,
                    selectedIdentity.getAlias()       ,
                    selectedIdentity.getImage()
            );

            final ActorIdentityInformation actorReceiving = new ActorIdentityInformation(
                    artistToContact.getPublicKey()   ,
                    Actors.ART_FAN                ,
                    artistToContact.getAlias()       ,
                    artistToContact.getImage()
            );

           fanActorConnectionManager.requestConnection(
                    actorSending,
                    actorReceiving
            );

        } catch (ConnectionAlreadyRequestedException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Error trying to request the actor connection.");
        } catch (CantRequestActorConnectionException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorTypeNotSupportedException(e, "", "Actor type is not supported.");
        } catch (UnsupportedActorTypeException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void disconnectFan(UUID requestId) throws FanDisconnectingFailedException {
        try {
            fanActorConnectionManager.disconnect(requestId);

        } catch (final CantDisconnectFromActorException | UnexpectedConnectionStateException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new FanDisconnectingFailedException("", e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new FanDisconnectingFailedException("", e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new FanDisconnectingFailedException("", e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void cancelFan(String fanToCancelPublicKey) throws FanCancellingFailedException {

    }

    @Override
    public List<FanCommunityInformation> getAllFanatics(int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public List<FanCommunityInformation> getFansWaitingYourAcceptance(int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public List<FanCommunityInformation> getFansWaitingTheirAcceptance(int max, int offset) throws CantGetFanListException {
        return null;
    }

    @Override
    public void login(String fanPublicKey) throws CantLoginFanException {

    }

    /**
     * This method checks if an actor connection exists.
     * @param linkedIdentityPublicKey
     * @param linkedIdentityActorType
     * @param actorPublicKey
     * @return
     * @throws CantGetActorConnectionException
     */
    @Override
    public List<FanActorConnection> getRequestActorConnections(
            String linkedIdentityPublicKey,
            Actors linkedIdentityActorType,
            String actorPublicKey) throws CantGetActorConnectionException {
        return fanActorConnectionManager.getRequestActorConnections(
                linkedIdentityPublicKey,
                linkedIdentityActorType,
                actorPublicKey);
    }

    /*@Override
    public SettingsManager<FanCommunitySettings> getSettingsManager() {

        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;    }*/

    @Override
    public FanCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        //Try to get appSettings
        FanCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ return null; }


        //Get all Fanatics identities on local device
        List<Fanatic> fanaticsIdentitiesInDevice = new ArrayList<>();
        try{
            fanaticsIdentitiesInDevice = fanaticIdentityManager.listIdentitiesFromCurrentDeviceUser();
        } catch(CantListFanIdentitiesException e) { /*Do nothing*/ }


        //Get all Artists identities on local device
        List<Artist> artistIdentitiesInDevice = new ArrayList<>();
        try{
            artistIdentitiesInDevice = artistIdentityManager.listIdentitiesFromCurrentDeviceUser();
        } catch(CantListArtistIdentitiesException e) { /*Do nothing*/ }

        //No registered users in device
        if(fanaticsIdentitiesInDevice.size() + artistIdentitiesInDevice.size() == 0 && isDialog){
            isDialog = false;
            throw new CantGetSelectedActorIdentityException("", null, "", "");
        }


        //If appSettings exists, get its selectedActorIdentityPublicKey property
        if(appSettings != null)
        {
            String lastSelectedIdentityPublicKey = appSettings.getLastSelectedIdentityPublicKey();
            Actors lastSelectedActorType = appSettings.getLastSelectedActorType();

            if (lastSelectedIdentityPublicKey != null && lastSelectedActorType != null) {

                FanCommunitySelectableIdentityImpl selectedIdentity = null;

                if(lastSelectedActorType == Actors.ART_ARTIST)
                {
                    for(Artist i : artistIdentitiesInDevice) {
                        if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new FanCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.ART_ARTIST, i.getAlias(), i.getProfileImage());
                    }
                }
                else if( lastSelectedActorType == Actors.ART_FAN)
                {
                    for(Fanatic i : fanaticsIdentitiesInDevice) {
                        if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new FanCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.ART_FAN, i.getAlias(), i.getProfileImage());
                    }
                }


                if(selectedIdentity == null)
                    throw new ActorIdentityNotSelectedException("", null, "", "");

                return selectedIdentity;
            }
            else if(isDialog){
                isDialog = false;
                throw new ActorIdentityNotSelectedException("", null, "", "");
            }
        }

        isDialog = true;
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    public void setAppPublicKey(String publicKey) {
        this.subAppPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    /**
     * This method returns the actor type from a request list.
     * @param fanConnectionRequestList
     * @param actorPublicKey
     * @return
     */
    private PlatformComponentType getActorTypeFromRequest(
            List<FanConnectionRequest> fanConnectionRequestList,
            String actorPublicKey){
        for(FanConnectionRequest fanConnectionRequest : fanConnectionRequestList){
            if(fanConnectionRequest.getSenderPublicKey().equals(actorPublicKey)){
                return fanConnectionRequest.getSenderActorType();
            }
            if(fanConnectionRequest.getDestinationPublicKey().equals(actorPublicKey)){
                return fanConnectionRequest.getDestinationActorType();
            }
        }
        //For now, I'll return an ART_FAN
        return PlatformComponentType.ART_FAN;
    }

    /**
     * This method returns the actor type from a request list.
     * @param fanConnectionRequestList
     * @param actorPublicKey
     * @return
     */
    private PlatformComponentType getActorTypeFromRequestFromArtist(
            List<ArtistConnectionRequest> fanConnectionRequestList,
            String actorPublicKey){
        for(ArtistConnectionRequest artistConnectionRequest : fanConnectionRequestList){
            if(artistConnectionRequest.getSenderPublicKey().equals(actorPublicKey)){
                return artistConnectionRequest.getSenderActorType();
            }
            if(artistConnectionRequest.getDestinationPublicKey().equals(actorPublicKey)){
                return artistConnectionRequest.getDestinationActorType();
            }
        }
        //For now, I'll return an ART_FAN
        return PlatformComponentType.ART_ARTIST;
    }
}
