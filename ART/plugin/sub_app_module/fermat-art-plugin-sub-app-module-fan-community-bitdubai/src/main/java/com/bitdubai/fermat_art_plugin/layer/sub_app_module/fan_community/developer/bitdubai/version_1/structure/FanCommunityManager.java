package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanListException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanSearchResult;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListFansException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantLoginFanException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantStartRequestException;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public class FanCommunityManager implements FanCommunityModuleManager,Serializable {
    private final ArtistIdentityManager                artistIdentityManager                    ;
    private final FanActorConnectionManager            fanActorConnectionManager                ;
    private final FanManager                           fanActorNetworkServiceManager            ;
    private final FanaticIdentityManager               fanaticIdentityManager                   ;
    private final ErrorManager                         errorManager                             ;
    private final PluginFileSystem                     pluginFileSystem                         ;
    private final UUID                                 pluginId                                 ;
    private final PluginVersionReference               pluginVersionReference                   ;

    private SettingsManager<FanCommunitySettings> settingsManager;

    private       String                              subAppPublicKey                           ;

    public FanCommunityManager(final ArtistIdentityManager artistIdentityManager,
                                          final FanActorConnectionManager fanActorConnectionManager,
                                          final FanManager fanActorNetworkServiceManager,
                                          final FanaticIdentityManager fanaticIdentityManager,
                                          final ErrorManager errorManager,
                                          final PluginFileSystem pluginFileSystem,
                                          final UUID pluginId,
                                          final PluginVersionReference pluginVersionReference) {

        this.artistIdentityManager                    = artistIdentityManager                    ;
        this.fanActorConnectionManager                = fanActorConnectionManager                ;
        this.fanActorNetworkServiceManager            = fanActorNetworkServiceManager            ;
        this.fanaticIdentityManager                   = fanaticIdentityManager                   ;
        this.errorManager                             = errorManager                             ;
        this.pluginFileSystem                         = pluginFileSystem                         ;
        this.pluginId                                 = pluginId                                 ;
        this.pluginVersionReference                   = pluginVersionReference                   ;
    }

    @Override
    public List<FanCommunityInformation> listWorldFan(FanCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantListFansException {
        List<FanCommunityInformation> worldfanaticList;
        List<FanActorConnection> actorConnections;

        try{
            worldfanaticList = getFanaticSearch().getResult();
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
        for(int i = 0; i < worldfanaticList.size(); i++)
        {
            worldFanatic = worldfanaticList.get(i);
            for(FanActorConnection connectedFan : actorConnections)
            {
                if(worldFanatic.getPublicKey().equals(connectedFan.getPublicKey()))
                    worldfanaticList.set(i, new FanCommunityInformationImpl(worldFanatic.getPublicKey(), worldFanatic.getAlias(), worldFanatic.getImage(), connectedFan.getConnectionState(), connectedFan.getConnectionId()));
            }
        }

        return worldfanaticList;    }

    @Override
    public List<FanCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException {
        try {

            final List<FanCommunitySelectableIdentity> selectableIdentities = new ArrayList<>();

            final List<Artist> artistIdentities = artistIdentityManager.listIdentitiesFromCurrentDeviceUser();

            for (final Artist ai : artistIdentities)
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

        if(appSettings != null){
            if(identity.getPublicKey() != null)
                appSettings.setLastSelectedIdentityPublicKey(identity.getPublicKey());
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
    public List<FanCommunityInformation> listAllConnectedFans(FanCommunitySelectableIdentity selectedIdentity, int max, int offset) throws CantGetFanListException {
        try {

            final FanLinkedActorIdentity linkedActorIdentity = new FanLinkedActorIdentity(
                    selectedIdentity.getPublicKey(),
                    selectedIdentity.getActorType()
            );

            final FanActorConnectionSearch search = fanActorConnectionManager.getSearch(linkedActorIdentity);

            search.addConnectionState(ConnectionState.CONNECTED);

            final List<FanActorConnection> actorConnections = search.getResult(max, offset);

            final List<FanCommunityInformation> fanaticCommunityInformationList = new ArrayList<>();

            for (FanActorConnection fac : actorConnections)
                fanaticCommunityInformationList.add(new FanCommunityInformationImpl(fac));

            return fanaticCommunityInformationList;

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
    public void askFanForAcceptance(String fanToAddName, String fanToAddPublicKey, byte[] profileImage) throws CantStartRequestException {

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

    @Override
    public SettingsManager<FanCommunitySettings> getSettingsManager() {

        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;    }

    @Override
    public FanCommunitySelectableIdentity getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void createFanaticIdentity(String name, String phrase, byte[] profile_img, UUID externalIdentityID) throws Exception {
        String createdPublicKey = null;

        try{
            final Artist createdIdentity = artistIdentityManager.createArtistIdentity(name, profile_img, externalIdentityID);
            createdPublicKey = createdIdentity.getPublicKey();

            new Thread() {
                @Override
                public void run() {
                    try {
                        artistIdentityManager.publishIdentity(createdIdentity.getPublicKey());
                    } catch(Exception e) {
                        errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                    }
                }
            }.start();
        }catch(Exception e) {
            this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return;
        }


        //Try to get appSettings
        FanCommunitySettings appSettings = null;
        try {
            appSettings = this.settingsManager.loadAndGetSettings(this.subAppPublicKey);
        }catch (Exception e){ appSettings = null; }


        //If appSettings exist
        if(appSettings != null){
            appSettings.setLastSelectedIdentityPublicKey(createdPublicKey);
            try {
                this.settingsManager.persistSettings(this.subAppPublicKey, appSettings);
            }catch (CantPersistSettingsException e){
                this.errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
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
