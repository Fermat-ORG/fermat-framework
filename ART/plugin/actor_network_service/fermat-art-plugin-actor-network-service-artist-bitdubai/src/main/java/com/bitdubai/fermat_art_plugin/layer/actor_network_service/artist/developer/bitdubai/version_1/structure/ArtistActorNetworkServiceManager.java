package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingInformationRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestExternalPlatformInformationException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtArtistExtraData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.ArtistActorNetworkServicePluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ArtistActorNetworkServiceManager implements ArtistManager {

    /**
     * Represents the plugin database DAO
     */
    private final ArtistActorNetworkServiceDao artistActorNetworkServiceDao;

    /**
     * Represents the plugin root
     */
    private final ArtistActorNetworkServicePluginRoot pluginRoot;

    /**
     * Represents the Executor
     */
    ExecutorService executorService;

    /**
     * Default constructor with parameters.
     * @param artistActorNetworkServiceDao
     * @param pluginRoot
     */
    public ArtistActorNetworkServiceManager(
            ArtistActorNetworkServiceDao artistActorNetworkServiceDao,
            ArtistActorNetworkServicePluginRoot pluginRoot) {
        this.artistActorNetworkServiceDao = artistActorNetworkServiceDao;
        this.pluginRoot = pluginRoot;
        this.executorService = Executors.newFixedThreadPool(3);
    }

    /**
     * This method exposes an identity.
     * @param artistExposingData  artist exposing information.
     *
     * @throws CantExposeIdentityException
     */
    @Override
    public void exposeIdentity(ArtistExposingData artistExposingData) throws CantExposeIdentityException {
        try {
            pluginRoot.registerActor(
                    artistExposingData.getPublicKey(),
                    artistExposingData.getAlias(),
                    artistExposingData.getAlias(),
                    artistExposingData.getExtraData(),
                    artistExposingData.getLocation(),
                    Actors.ART_ARTIST,
                    null,
                    0,0
            );
        } catch (ActorAlreadyRegisteredException | CantRegisterActorException e) {
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantExposeIdentityException(
                    e,
                    "Registering a new actor",
                    "Problem trying to register an identity component.");
        } catch (final Exception e){
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantExposeIdentityException(
                    e,
                    "Registering a new actor",
                    "Unhandled Exception.");
        }
    }

    /**
     * This method updates an identity.
     * @param artistExposingData
     * @throws CantExposeIdentityException
     */
    @Override
    public void updateIdentity(ArtistExposingData artistExposingData) throws CantExposeIdentityException {
        try {
            pluginRoot.updateRegisteredActor(
                    artistExposingData.getPublicKey(),
                    artistExposingData.getAlias(),
                    artistExposingData.getAlias(),
                    artistExposingData.getLocation(),
                    artistExposingData.getExtraData(),
                    null
            );
        }catch (Exception e){
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantExposeIdentityException(
                    e,
                    "Registering a new actor",
                    "Unhandled Exception.");
        }
    }

    /**
     * This method exposes an identities list.
     * @param artistExposingDataList  list of artist exposing information.
     *
     * @throws CantExposeIdentitiesException
     */
    @Override
    public void exposeIdentities(Collection<ArtistExposingData> artistExposingDataList)
            throws CantExposeIdentitiesException {
        try {
            for (final ArtistExposingData artistExposingData : artistExposingDataList)
                this.exposeIdentity(artistExposingData);
        } catch (final CantExposeIdentityException e){
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantExposeIdentitiesException(
                    e,
                    "Exposing identities",
                    "Problem trying to expose an identity.");
        } catch (final Exception e){
            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantExposeIdentitiesException(
                    e,
                    "Exposing identities",
                    "Unhandled Exception.");
        }
    }

    @Override
    public ActorSearch<ArtistExposingData> getSearch() {
        return new ArtistActorNetworkServiceSearch(
                pluginRoot
        );
    }

    @Override
    public void requestConnection(ArtistConnectionInformation artistConnectionInformation)
            throws CantRequestConnectionException {
        try {

            final ProtocolState state  = ProtocolState.PROCESSING_SEND;
            final RequestType type = RequestType.SENT;
            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST;

            artistActorNetworkServiceDao.createConnectionRequest(
                    artistConnectionInformation,
                    state,
                    type,
                    action,
                    0
            );

            Actors actorType = getActorType(artistConnectionInformation.getSenderActorType());
            sendMessage(
                    buildJsonRequestMessage(artistConnectionInformation),
                    artistConnectionInformation.getSenderPublicKey(),
                    actorType,
                    artistConnectionInformation.getDestinationPublicKey(),
                    Actors.ART_ARTIST
            );

        } catch (final CantRequestConnectionException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void disconnect(UUID requestId) throws CantDisconnectException, ConnectionRequestNotFoundException {
        try {

            final ProtocolState state = ProtocolState.PROCESSING_SEND;

            artistActorNetworkServiceDao.disconnectConnection(
                    requestId,
                    state
            );

            ArtistConnectionRequest cbcr = artistActorNetworkServiceDao.getConnectionRequest(requestId);
            Actors actorType = getActorType(cbcr.getSenderActorType());
            if (cbcr.getRequestType() == RequestType.RECEIVED) {
                sendMessage(
                        buildJsonInformationMessage(cbcr),
                        cbcr.getDestinationPublicKey(),
                        Actors.ART_ARTIST,
                        cbcr.getSenderPublicKey(),
                        actorType
                );
            } else {
                sendMessage(
                        buildJsonInformationMessage(cbcr),
                        cbcr.getSenderPublicKey(),
                        actorType,
                        cbcr.getDestinationPublicKey(),
                        Actors.ART_ARTIST
                );
            }

        } catch (final CantDisconnectException | ConnectionRequestNotFoundException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDisconnectException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void denyConnection(UUID requestId) throws CantDenyConnectionRequestException, ConnectionRequestNotFoundException {
        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            artistActorNetworkServiceDao.denyConnection(
                    requestId,
                    protocolState
            );

            ArtistConnectionRequest cbcr = artistActorNetworkServiceDao.getConnectionRequest(requestId);
            Actors actorType = getActorType(cbcr.getSenderActorType());
            sendMessage(
                    buildJsonInformationMessage(cbcr),
                    cbcr.getDestinationPublicKey(),
                    Actors.ART_ARTIST,
                    cbcr.getSenderPublicKey(),
                    actorType
            );

        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void cancelConnection(UUID requestId) throws CantCancelConnectionRequestException, ConnectionRequestNotFoundException {
        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            artistActorNetworkServiceDao.cancelConnection(
                    requestId,
                    protocolState
            );

            ArtistConnectionRequest connectionRequest = artistActorNetworkServiceDao.getConnectionRequest(requestId);

            Actors actorType = getActorType(connectionRequest.getSenderActorType());
            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getDestinationPublicKey(),
                    Actors.ART_ARTIST,
                    connectionRequest.getSenderPublicKey(),
                    actorType
            );

        } catch (final CantCancelConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void acceptConnection(UUID requestId) throws CantAcceptConnectionRequestException, ConnectionRequestNotFoundException {
        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            artistActorNetworkServiceDao.acceptConnection(
                    requestId,
                    protocolState
            );

            ArtistConnectionRequest connectionRequest = artistActorNetworkServiceDao.getConnectionRequest(requestId);
            Actors actorType = getActorType(connectionRequest.getSenderActorType());
            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getDestinationPublicKey(),
                    Actors.ART_ARTIST,
                    connectionRequest.getSenderPublicKey(),
                    actorType
            );

        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ArtistConnectionRequest> listPendingConnectionNews(PlatformComponentType actorType) throws CantListPendingConnectionRequestsException {
        try {

            return artistActorNetworkServiceDao.listPendingConnectionNews(actorType);

        } catch (final CantListPendingConnectionRequestsException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ArtistConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {
        try {

            return artistActorNetworkServiceDao.listPendingConnectionUpdates();

        } catch (final CantListPendingConnectionRequestsException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void confirm(UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException {
        try {

            artistActorNetworkServiceDao.confirmActorConnectionRequest(requestId);

        } catch (final ConnectionRequestNotFoundException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantConfirmConnectionRequestException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, "", "Error in DAO, trying to confirm the request.");
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public ArtArtistExtraData<ArtistExternalPlatformInformation> requestExternalPlatformInformation(
            UUID requestId,
            String requesterPublicKey,
            PlatformComponentType requesterActorType,
            String artistPublicKey) throws CantRequestExternalPlatformInformationException {
        try {

            //final UUID newId = UUID.randomUUID();

            final ProtocolState state  = ProtocolState.PROCESSING_SEND;
            final RequestType type = RequestType.SENT;

            ArtistActorNetworkServiceExternalPlatformInformationRequest informationRequest =
                    artistActorNetworkServiceDao.createExternalPlatformInformationRequest(
                            requestId,
                            requesterPublicKey,
                            requesterActorType,
                            artistPublicKey,
                            state,
                            type
                    );
            Actors actorType = getActorType(informationRequest.getRequesterActorType());
            sendMessage(
                    informationRequest.toJson(),
                    informationRequest.getRequesterPublicKey(),
                    actorType,
                    informationRequest.getArtistPublicKey(),
                    Actors.ART_ARTIST
            );
            return informationRequest;
        }  catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestExternalPlatformInformationException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ArtArtistExtraData<ArtistExternalPlatformInformation>> listPendingInformationRequests(
            RequestType requestType) throws CantListPendingInformationRequestsException {
        try {

            return artistActorNetworkServiceDao.listPendingInformationRequests(
                    ProtocolState.PENDING_LOCAL_ACTION,
                    requestType);
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingInformationRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ArtistConnectionRequest> listCompletedConnections() throws CantListPendingConnectionRequestsException {
        try{
            return artistActorNetworkServiceDao.listCompletedConnections();
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<ArtistConnectionRequest> listAllRequest() throws CantListPendingConnectionRequestsException {
        return artistActorNetworkServiceDao.listAllRequest();
    }

    private Actors getActorType(PlatformComponentType platformComponentType){
        Actors actorType;
        switch (platformComponentType){
            case ART_ARTIST:
                actorType = Actors.ART_ARTIST;
                break;
            case ART_FAN:
                actorType = Actors.ART_FAN;
                break;
            default:
                actorType = Actors.ART_ARTIST;
                break;
        }
        return actorType;
    }

    private void sendMessage(final String jsonMessage      ,
                             final String identityPublicKey,
                             final Actors identityType     ,
                             final String actorPublicKey   ,
                             final Actors actorType        ) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    ActorProfile sender = new ActorProfile();
                    sender.setActorType(identityType.getCode());
                    sender.setIdentityPublicKey(identityPublicKey);

                    ActorProfile receiver = new ActorProfile();
                    receiver.setActorType(actorType.getCode());
                    receiver.setIdentityPublicKey(actorPublicKey);

                    pluginRoot.sendNewMessage(
                            sender,
                            receiver,
                            jsonMessage
                    );
                } catch (CantSendMessageException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }

    private String buildJsonInformationMessage(final ArtistConnectionRequest aer) {

        return new InformationMessage(
                aer.getRequestId(),
                aer.getRequestAction()
        ).toJson();
    }

    private String buildJsonRequestMessage(final ArtistConnectionInformation aer) {

        Actors actorType = getActorType(aer.getSenderActorType());
        return new RequestMessage(
                aer.getConnectionId(),
                aer.getSenderPublicKey(),
                actorType,
                aer.getSenderAlias(),
                aer.getSenderImage(),
                aer.getDestinationPublicKey(),
                ConnectionRequestAction.REQUEST,
                aer.getSendingTime()
        ).toJson();
    }
}