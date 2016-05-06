package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ArtistCancellingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ArtistConnectionDenialFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ArtistDisconnectingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantGetArtistSearchResult;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantValidateConnectionStateException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySearch;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.settings.ArtistCommunitySettings;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public class ArtistCommunityManager implements ArtistCommunitySubAppModuleManager,Serializable {

    private final ArtistIdentityManager                         artistIdentityManager                 ;
    private final ArtistActorConnectionManager                  artistActorConnectionManager          ;
    private final ArtistManager                                 artistActorNetworkServiceManager      ;
    private final FanaticIdentityManager                        fanaticIdentityManager                ;
    private final ErrorManager errorManager                          ;
    private final PluginFileSystem                              pluginFileSystem                      ;
    private final UUID                                          pluginId                              ;
    private final PluginVersionReference                        pluginVersionReference                ;

    private       String                                        subAppPublicKey                       ;
    private       SettingsManager<ArtistCommunitySettings>      settingsManager                       ;

    private boolean isDialog = true;


    public ArtistCommunityManager(final ArtistIdentityManager           artistIdentityManager                 ,
                                  final ArtistActorConnectionManager    artistActorConnectionManager          ,
                                  final ArtistManager                   artistActorNetworkServiceManager      ,
                                  final FanaticIdentityManager          fanaticIdentityManager                ,
                                  final ErrorManager                    errorManager                          ,
                                  final PluginFileSystem                pluginFileSystem                      ,
                                  final UUID                            pluginId                              ,
                                  final PluginVersionReference          pluginVersionReference                ) {

        this.artistIdentityManager                  = artistIdentityManager                 ;
        this.artistActorConnectionManager           = artistActorConnectionManager          ;
        this.artistActorNetworkServiceManager       = artistActorNetworkServiceManager      ;
        this.fanaticIdentityManager                 = fanaticIdentityManager                ;
        this.errorManager                           = errorManager                          ;
        this.pluginFileSystem                       = pluginFileSystem                      ;
        this.pluginId                               = pluginId                              ;
        this.pluginVersionReference                 = pluginVersionReference                ;
    }

    @Override
    public List<ArtistCommunityInformation> listWorldArtists(ArtistCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListArtistsException {
        List<ArtistCommunityInformation> worldArtistList;
        List<ArtistActorConnection> actorConnections;

        try{
            worldArtistList = getArtistSearch().getResult();
        } catch (CantGetArtistSearchResult e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Error in listWorldArtists trying to list world Artists");
        }


        try {

            final ArtistLinkedActorIdentity linkedActorIdentity = new ArtistLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final ArtistActorConnectionSearch search = artistActorConnectionManager.getSearch(linkedActorIdentity);
            //search.addConnectionState(ConnectionState.CONNECTED);
            //search.addConnectionState(ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

            actorConnections = search.getResult(Integer.MAX_VALUE, 0);

        } catch (final CantListActorConnectionsException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Error trying to list actor connections.");
        }

        ArtistCommunityInformation worldArtist;
        for(int i = 0; i < worldArtistList.size(); i++)
        {
            worldArtist = worldArtistList.get(i);
            for(ArtistActorConnection connectedArtist : actorConnections)
            {
                if(worldArtist.getPublicKey().equals(connectedArtist.getPublicKey()))
                    worldArtistList.set(i, new ArtistCommunityInformationImpl(worldArtist.getPublicKey(), worldArtist.getAlias(), worldArtist.getImage(), connectedArtist.getConnectionState(), connectedArtist.getConnectionId()));
            }
        }
        return worldArtistList;
    }

    /**
     * We are listing here all Artists and all Fanatics identities found in the device.
     */
    @Override
    public List<ArtistCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {
        try {

            final List<ArtistCommunitySelectableIdentity> selectableIdentities = new ArrayList<>();

            final List<Artist> artistsIdentities = artistIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final Artist ai : artistsIdentities)
                selectableIdentities.add(new ArtistCommunitySelectableIdentityImpl(ai));

            final List<Fanatic> fanaticsIdentities = fanaticIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final Fanatic fi : fanaticsIdentities)
                selectableIdentities.add(new ArtistCommunitySelectableIdentityImpl(fi));

            return selectableIdentities;

        } catch (final CantListArtistIdentitiesException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Error in DAO trying to list identities.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListIdentitiesToSelectException(e, "", "Unhandled Exception.");
        }    }

    @Override
    public void setSelectedActorIdentity(ArtistCommunitySelectableIdentity identity) {
        //Try to get appSettings
        ArtistCommunitySettings appSettings = null;
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
    public ArtistCommunitySearch getArtistSearch() {
        return new ArtistCommunitySearchImpl(artistActorNetworkServiceManager);

    }

    @Override
    public ArtistCommunitySearch searchConnectedArtist(ArtistCommunitySelectableIdentity selectedIdentity) {
        return null;
    }

    @Override
    public void requestConnectionToArtist(ArtistCommunitySelectableIdentity selectedIdentity, ArtistCommunityInformation artistToContact) throws CantRequestConnectionException, ActorConnectionAlreadyRequestedException, ActorTypeNotSupportedException {
        try {

            final ActorIdentityInformation actorSending = new ActorIdentityInformation(
                    selectedIdentity.getPublicKey()   ,
                    selectedIdentity.getActorType()   ,
                    selectedIdentity.getAlias()       ,
                    selectedIdentity.getImage()
            );

            final ActorIdentityInformation actorReceiving = new ActorIdentityInformation(
                    artistToContact.getPublicKey()   ,
                    Actors.ART_ARTIST                ,
                    artistToContact.getAlias()       ,
                    artistToContact.getImage()
            );

            artistActorConnectionManager.requestConnection(
                    actorSending,
                    actorReceiving
            );

        } catch (final CantRequestActorConnectionException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Error trying to request the actor connection.");
        } catch (final UnsupportedActorTypeException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorTypeNotSupportedException(e, "", "Actor type is not supported.");
        } catch (final ConnectionAlreadyRequestedException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ActorConnectionAlreadyRequestedException(e, "", "Connection already requested.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void acceptArtist(UUID requestId) throws CantAcceptRequestException, ConnectionRequestNotFoundException {
        try {

            artistActorConnectionManager.acceptConnection(requestId);

        } catch (final CantAcceptActorConnectionRequestException |
                UnexpectedConnectionStateException        e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptRequestException(e, "", "Error trying to accept the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection already requested.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptRequestException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void denyConnection(UUID requestId) throws ArtistConnectionDenialFailedException, ConnectionRequestNotFoundException {
        try {

            artistActorConnectionManager.denyConnection(requestId);

        } catch (final CantDenyActorConnectionRequestException |
                UnexpectedConnectionStateException      e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ArtistConnectionDenialFailedException(e, "", "Error trying to deny the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ArtistConnectionDenialFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void disconnectArtist(UUID requestId) throws ArtistDisconnectingFailedException, ConnectionRequestNotFoundException {
        try {

            artistActorConnectionManager.disconnect(requestId);

        } catch (final CantDisconnectFromActorException   |
                UnexpectedConnectionStateException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ArtistDisconnectingFailedException(e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ArtistDisconnectingFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public void cancelArtist(UUID requestId) throws ArtistCancellingFailedException, ConnectionRequestNotFoundException {
        try {

            artistActorConnectionManager.cancelConnection(requestId);

        } catch (final CantCancelActorConnectionRequestException |
                UnexpectedConnectionStateException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ArtistCancellingFailedException(e, "", "Error trying to disconnect the actor connection.");
        } catch (final ActorConnectionNotFoundException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ConnectionRequestNotFoundException(e, "", "Connection request not found.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ArtistCancellingFailedException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<ArtistCommunityInformation> listAllConnectedArtists(ArtistCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListArtistsException {
        try {

            final ArtistLinkedActorIdentity linkedActorIdentity = new ArtistLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final ArtistActorConnectionSearch search = artistActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            final List<ArtistActorConnection> actorConnections = search.getResult(max, offset);

            final List<ArtistCommunityInformation> artistCommunityInformationList = new ArrayList<>();

            for (ArtistActorConnection aac : actorConnections)
                artistCommunityInformationList.add(new ArtistCommunityInformationImpl(aac));

            return artistCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled Exception.");
        }    }

    @Override
    public List<ArtistCommunityInformation> listArtistsPendingLocalAction(ArtistCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListArtistsException {
        try {

            final ArtistLinkedActorIdentity linkedActorIdentity = new ArtistLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final ArtistActorConnectionSearch search = artistActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_LOCALLY_ACCEPTANCE);

            final List<ArtistActorConnection> actorConnections = search.getResult(max, offset);

            final List<ArtistCommunityInformation> artistCommunityInformationList = new ArrayList<>();

            for (ArtistActorConnection aac : actorConnections)
                artistCommunityInformationList.add(new ArtistCommunityInformationImpl(aac));

            return artistCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled Exception.");
        }    }

    @Override
    public List<ArtistCommunityInformation> listArtistsPendingRemoteAction(ArtistCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListArtistsException {
        try {

            final ArtistLinkedActorIdentity linkedActorIdentity = new ArtistLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final ArtistActorConnectionSearch search = artistActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

            final List<ArtistActorConnection> actorConnections = search.getResult(max, offset);

            final List<ArtistCommunityInformation> artistCommunityInformationList = new ArrayList<>();

            for (ArtistActorConnection aac : actorConnections)
                artistCommunityInformationList.add(new ArtistCommunityInformationImpl(aac));

            return artistCommunityInformationList;

        } catch (final CantListActorConnectionsException e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Error trying to list actor connections.");
        } catch (final Exception e) {

            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListArtistsException(e, "", "Unhandled Exception.");
        }    }

    @Override
    public int getArtistsWaitingYourAcceptanceCount() {
        return 0;
    }

    @Override
    public ConnectionState getActorConnectionState(String publicKey) throws CantValidateConnectionStateException {
        try {
            ArtistCommunitySelectableIdentity selectedIdentity  =  getSelectedActorIdentity();
            final ArtistLinkedActorIdentity linkedActorIdentity = new ArtistLinkedActorIdentity(selectedIdentity.getPublicKey(), selectedIdentity.getActorType());
            final ArtistActorConnectionSearch search = artistActorConnectionManager.getSearch(linkedActorIdentity);
            final List<ArtistActorConnection> actorConnections = search.getResult(Integer.MAX_VALUE, 0);

            for(ArtistActorConnection connection : actorConnections){
                if(publicKey.equals(connection.getPublicKey()))
                    return connection.getConnectionState();
            }

        } catch (final CantListActorConnectionsException e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantValidateConnectionStateException(e, "", "Error trying to list actor connections.");
        } catch (Exception e) {}

        return ConnectionState.DISCONNECTED_LOCALLY;    }
    
    public SettingsManager<ArtistCommunitySettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ArtistCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        //Try to get appSettings
        ArtistCommunitySettings appSettings = null;
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

                ArtistCommunitySelectableIdentityImpl selectedIdentity = null;

                if(lastSelectedActorType == Actors.ART_ARTIST)
                {
                    for(Artist i : artistIdentitiesInDevice) {
                        if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new ArtistCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.ART_ARTIST, i.getAlias(), i.getProfileImage());
                    }
                }
                else if( lastSelectedActorType == Actors.ART_FAN)
                {
                    for(Fanatic i : fanaticsIdentitiesInDevice) {
                        if(i.getPublicKey().equals(lastSelectedIdentityPublicKey))
                            selectedIdentity = new ArtistCommunitySelectableIdentityImpl(i.getPublicKey(), Actors.ART_FAN, i.getAlias(), i.getProfileImage());
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
        return null;    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.subAppPublicKey = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
