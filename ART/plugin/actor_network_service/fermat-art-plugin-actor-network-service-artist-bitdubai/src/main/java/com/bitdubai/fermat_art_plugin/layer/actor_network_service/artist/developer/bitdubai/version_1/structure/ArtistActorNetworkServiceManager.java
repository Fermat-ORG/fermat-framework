package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
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
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.ArtistActorNetworkServicePluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceManager</code>
 * is the manager of the plug-in crypto broker actor network service of the cbp platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class ArtistActorNetworkServiceManager implements ArtistManager {

    private final CommunicationsClientConnection            communicationsClientConnection    ;
    private final ArtistActorNetworkServiceDao artistActorNetworkServiceDao;
    private final ArtistActorNetworkServicePluginRoot pluginRoot                        ;
    private final ErrorManager                              errorManager                      ;
    private final PluginVersionReference                    pluginVersionReference            ;

    /**
     * Executor
     */
    ExecutorService executorService;

    private PlatformComponentProfile platformComponentProfile;

    public ArtistActorNetworkServiceManager(final CommunicationsClientConnection communicationsClientConnection,
                                            final ArtistActorNetworkServiceDao artistActorNetworkServiceDao,
                                            final ArtistActorNetworkServicePluginRoot pluginRoot,
                                            final ErrorManager errorManager,
                                            final PluginVersionReference pluginVersionReference) {

        this.communicationsClientConnection     = communicationsClientConnection    ;
        this.artistActorNetworkServiceDao       = artistActorNetworkServiceDao      ;
        this.pluginRoot                         = pluginRoot                        ;
        this.errorManager                       = errorManager                      ;
        this.pluginVersionReference             = pluginVersionReference            ;
        this.executorService                    = Executors.newFixedThreadPool(3)   ;
    }

    private ConcurrentHashMap<String, ArtistExposingData> artistsToExpose;

    @Override
    public final void exposeIdentity(final ArtistExposingData artist) throws CantExposeIdentityException {

        try {

            if (!isRegistered()) {

                addArtistsToExpose(artist);

            } else {

                final String imageString = Base64.encodeToString(artist.getExtraData().getBytes(), Base64.DEFAULT);

                final PlatformComponentProfile actorPlatformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        artist.getPublicKey(),
                        (artist.getAlias()),
                        (artist.getAlias().toLowerCase()),
                        NetworkServiceType.ARTIST_ACTOR,
                        PlatformComponentType.ART_ARTIST,
                        imageString
                );

                communicationsClientConnection.registerComponentForCommunication(platformComponentProfile.getNetworkServiceType(), actorPlatformComponentProfile);

                if (artistsToExpose != null && artistsToExpose.containsKey(artist.getPublicKey()))
                    artistsToExpose.remove(artist.getPublicKey());
            }

        } catch (final CantRegisterComponentException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Problem trying to register an identity component.");

        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void updateIdentity(ArtistExposingData actor) throws CantExposeIdentityException {
        try {
            if (isRegistered()) {

                final String imageString = Base64.encodeToString(actor.getExtraData().getBytes(), Base64.DEFAULT);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        actor.getPublicKey(),
                        (actor.getAlias()),
                        (actor.getAlias().toLowerCase() + "_" + this.platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.ARTIST_ACTOR,
                        PlatformComponentType.ART_ARTIST,
                        imageString);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.updateRegisterActorProfile(platformComponentProfile.getNetworkServiceType(), platformComponentProfile);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                },"ART ACTOR NS ARTIST UPDATE IDENTITY");

                thread.start();
            }
        }catch (Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    private void addArtistsToExpose(final ArtistExposingData artistExposingData) {

        if (artistsToExpose == null)
            artistsToExpose = new ConcurrentHashMap<>();

        artistsToExpose.putIfAbsent(artistExposingData.getPublicKey(), artistExposingData);
    }

    @Override
    public final void exposeIdentities(final Collection<ArtistExposingData> artistExposingDataList) throws CantExposeIdentitiesException {

        try {

            for (final ArtistExposingData artistExposingData : artistExposingDataList)
                this.exposeIdentity(artistExposingData);

        } catch (final CantExposeIdentityException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Problem trying to expose an identity.");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Unhandled Exception.");
        }
    }

    private boolean isRegistered() {
        return platformComponentProfile != null;
    }

    public final void setPlatformComponentProfile(final PlatformComponentProfile platformComponentProfile) {

        this.platformComponentProfile = platformComponentProfile;

        if (platformComponentProfile != null && artistsToExpose != null && !artistsToExpose.isEmpty()) {

            try {

                this.exposeIdentities(artistsToExpose.values());

            } catch (final CantExposeIdentitiesException e){

                errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public final ActorSearch<ArtistExposingData> getSearch() {
        return new ArtistActorNetworkServiceSearch(communicationsClientConnection, errorManager, pluginVersionReference);
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : REQUEST.
     * - Type          : SENT.
     */
    @Override
    public final void requestConnection(final ArtistConnectionInformation artistConnectionInformation) throws CantRequestConnectionException {

        try {

            final ProtocolState state  = ProtocolState          .PROCESSING_SEND;
            final RequestType type   = RequestType            .SENT           ;
            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST        ;

            artistActorNetworkServiceDao.createConnectionRequest(
                    artistConnectionInformation,
                    state,
                    type,
                    action
            );

            sendMessage(
                    buildJsonRequestMessage(artistConnectionInformation),
                    artistConnectionInformation.getSenderPublicKey(),
                    artistConnectionInformation.getSenderActorType(),
                    artistConnectionInformation.getDestinationPublicKey(),
                    artistConnectionInformation.getDestinationActorType()
            );

        } catch (final CantRequestConnectionException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : DISCONNECT.
     */
    @Override
    public final void disconnect(final UUID requestId) throws CantDisconnectException,
            ConnectionRequestNotFoundException {

        try {

            final ProtocolState state = ProtocolState.PROCESSING_SEND;

            artistActorNetworkServiceDao.disconnectConnection(
                    requestId,
                    state
            );

            ArtistConnectionRequest connectionRequest = artistActorNetworkServiceDao.getConnectionRequest(requestId);

            if (connectionRequest.getRequestType() == RequestType.RECEIVED) {
                sendMessage(
                        buildJsonInformationMessage(connectionRequest),
                        connectionRequest.getDestinationPublicKey(),
                        connectionRequest.getDestinationActorType(),
                        connectionRequest.getSenderPublicKey(),
                        connectionRequest.getSenderActorType()
                );
            } else {
                sendMessage(
                        buildJsonInformationMessage(connectionRequest),
                        connectionRequest.getSenderPublicKey(),
                        connectionRequest.getSenderActorType(),
                        connectionRequest.getDestinationPublicKey(),
                        connectionRequest.getDestinationActorType()
                );
            }

        } catch (final CantDisconnectException | ConnectionRequestNotFoundException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDisconnectException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : DENY.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public final void denyConnection(final UUID requestId) throws CantDenyConnectionRequestException,
            ConnectionRequestNotFoundException {

        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            artistActorNetworkServiceDao.denyConnection(
                    requestId,
                    protocolState
            );

            ArtistConnectionRequest connectionRequest = artistActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getDestinationPublicKey(),
                    connectionRequest.getDestinationActorType(),
                    connectionRequest.getSenderPublicKey(),
                    connectionRequest.getSenderActorType()
            );

        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : CANCEL.
     * - Protocol State: PROCESSING_SEND.
     *
     * We must to validate if the record is in PENDING_REMOTE_ACTION.
     */
    @Override
    public final void cancelConnection(final UUID requestId) throws CantCancelConnectionRequestException,
            ConnectionRequestNotFoundException  {
        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            artistActorNetworkServiceDao.cancelConnection(
                    requestId,
                    protocolState
            );

            ArtistConnectionRequest connectionRequest = artistActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getSenderPublicKey(),
                    connectionRequest.getSenderActorType(),
                    connectionRequest.getDestinationPublicKey(),
                    connectionRequest.getDestinationActorType()
            );

        } catch (final CantCancelConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCancelConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public final void acceptConnection(final UUID requestId) throws CantAcceptConnectionRequestException,
            ConnectionRequestNotFoundException  {

        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            artistActorNetworkServiceDao.acceptConnection(
                    requestId,
                    protocolState
            );

            ArtistConnectionRequest connectionRequest = artistActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getDestinationPublicKey(),
                    connectionRequest.getDestinationActorType(),
                    connectionRequest.getSenderPublicKey(),
                    connectionRequest.getSenderActorType()
            );

        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return all the request news with a pending local action.
     * State : PENDING_LOCAL_ACTION.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    @Override
    public final List<ArtistConnectionRequest> listPendingConnectionNews(PlatformComponentType actorType) throws CantListPendingConnectionRequestsException {

        try {

            return artistActorNetworkServiceDao.listPendingConnectionNews(actorType);

        } catch (final CantListPendingConnectionRequestsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * This method returns all completed request connections.
     * @return
     * @throws CantListPendingConnectionRequestsException
     */
    @Override
    public final List<ArtistConnectionRequest> listCompletedConnections() throws
            CantListPendingConnectionRequestsException{
        try{
            return artistActorNetworkServiceDao.listCompletedConnections();
        } catch (final CantListPendingConnectionRequestsException e){
            errorManager.reportUnexpectedPluginException(
                    this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw e;
        } catch (final Exception e){
            errorManager.reportUnexpectedPluginException(
                    this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return all the request updates with a pending local action.
     * State : PENDING_LOCAL_ACTION.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    @Override
    public final List<ArtistConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        try {

            return artistActorNetworkServiceDao.listPendingConnectionUpdates();

        } catch (final CantListPendingConnectionRequestsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void confirm(final UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException {

        try {

            artistActorNetworkServiceDao.confirmActorConnectionRequest(requestId);

        } catch (final ConnectionRequestNotFoundException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantConfirmConnectionRequestException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, "", "Error in DAO, trying to confirm the request.");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * This method request the ArtArtistExtraData managed by the Artist.
     * @param requesterPublicKey
     * @param requesterActorType
     * @param artistPublicKey
     * @return
     * @throws CantRequestExternalPlatformInformationException
     */
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
            sendMessage(
                    informationRequest.toJson(),
                    informationRequest.getRequesterPublicKey(),
                    informationRequest.getRequesterActorType(),
                    informationRequest.getArtistPublicKey(),
                    PlatformComponentType.ART_ARTIST
            );
            return informationRequest;
        } catch (final CantRequestExternalPlatformInformationException e){
            errorManager.reportUnexpectedPluginException(
                    this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw e;
        } catch (final Exception e){
            errorManager.reportUnexpectedPluginException(
                    this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantRequestExternalPlatformInformationException(
                    e,
                    null,
                    "Unhandled Exception.");
        }
    }

    /**
     * This method returns the pending information request list.
     * @param requestType SENT or RECEIVED
     * @return
     * @throws CantListPendingInformationRequestsException
     */
    @Override
    public List<ArtArtistExtraData<ArtistExternalPlatformInformation>> listPendingInformationRequests(
            RequestType requestType) throws CantListPendingInformationRequestsException {
        try {

            return artistActorNetworkServiceDao.listPendingInformationRequests(
                    ProtocolState.PENDING_LOCAL_ACTION,
                    requestType);
        } catch (final CantListPendingInformationRequestsException e){
            errorManager.reportUnexpectedPluginException(
                    this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw e;
        } catch (final Exception e){
            errorManager.reportUnexpectedPluginException(
                    this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantListPendingInformationRequestsException(
                    e,
                    null,
                    "Unhandled Exception.");
        }
    }

    private void sendMessage(final String jsonMessage      ,
                             final String identityPublicKey,
                             final PlatformComponentType identityType     ,
                             final String actorPublicKey   ,
                             final PlatformComponentType actorType        ) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    pluginRoot.sendNewMessage(
                            pluginRoot.getProfileSenderToRequestConnection(
                                    identityPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    identityType
                            ),
                            pluginRoot.getProfileDestinationToRequestConnection(
                                    actorPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    actorType
                            ),
                            jsonMessage
                    );
                } catch (CantSendMessageException e) {
                    errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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

        return new RequestMessage(
                aer.getConnectionId(),
                aer.getSenderPublicKey(),
                aer.getSenderActorType(),
                aer.getSenderAlias(),
                aer.getSenderImage(),
                aer.getDestinationPublicKey(),
                aer.getDestinationActorType(),
                ConnectionRequestAction.REQUEST,
                aer.getSendingTime()
        ).toJson();
    }

}
