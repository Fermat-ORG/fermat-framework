package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.FanActorNetworkServicePluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.ActorAlreadyRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantRegisterActorException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Manuel Perez on 30/06/2016
 */
public class FanActorNetworkServiceManager implements FanManager {

    /**
     * Represents the plugin database DAO
     */
    private final FanActorNetworkServiceDao fanActorNetworkServiceDao;

    /**
     * Represents the plugin root
     */
    private final FanActorNetworkServicePluginRoot pluginRoot;

    /**
     * Represents the Executor
     */
    ExecutorService executorService;

    /**
     * Default constructor with parameters.
     * @param fanActorNetworkServiceDao
     * @param pluginRoot
     */
    public FanActorNetworkServiceManager(
            FanActorNetworkServiceDao fanActorNetworkServiceDao,
            FanActorNetworkServicePluginRoot pluginRoot) {
        this.fanActorNetworkServiceDao = fanActorNetworkServiceDao;
        this.pluginRoot = pluginRoot;
        this.executorService = Executors.newFixedThreadPool(3);
    }

    /**
     *
     * @param fanExposingData  fan exposing information.
     *
     * @throws CantExposeIdentityException
     */
    @Override
    public void exposeIdentity(FanExposingData fanExposingData) throws CantExposeIdentityException {
        try {
            pluginRoot.registerActor(
                    fanExposingData.getPublicKey(),
                    fanExposingData.getAlias(),
                    fanExposingData.getAlias(),
                    fanExposingData.getExtraData(),
                    fanExposingData.getLocation(),
                    Actors.ART_FAN,
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
     *
     * @param fanExposingData
     * @throws CantExposeIdentityException
     */
    @Override
    public void updateIdentity(FanExposingData fanExposingData) throws CantExposeIdentityException {
        try {
            pluginRoot.updateRegisteredActor(
                    fanExposingData.getPublicKey(),
                    fanExposingData.getAlias(),
                    fanExposingData.getAlias(),
                    fanExposingData.getLocation(),
                    fanExposingData.getExtraData(),
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

    @Override
    public void exposeIdentities(Collection<FanExposingData> fanExposingDataList)
            throws CantExposeIdentitiesException {
        try {
            for (final FanExposingData fanExposingData : fanExposingDataList)
                this.exposeIdentity(fanExposingData);
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
    public ActorSearch<FanExposingData> getSearch() {
        return new FanActorNetworkServiceSearch(
                pluginRoot
        );
    }

    @Override
    public void requestConnection(FanConnectionInformation fanConnectionInformation)
            throws CantRequestConnectionException {
        try {

            final ProtocolState state  = ProtocolState.PROCESSING_SEND;
            final RequestType type = RequestType.SENT;
            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST;

            fanActorNetworkServiceDao.createConnectionRequest(
                    fanConnectionInformation,
                    state,
                    type,
                    action,
                    0
            );

            Actors actorType = getActorType(fanConnectionInformation.getSenderActorType());
            sendMessage(
                    buildJsonRequestMessage(fanConnectionInformation),
                    fanConnectionInformation.getSenderPublicKey(),
                    actorType,
                    fanConnectionInformation.getDestinationPublicKey(),
                    Actors.ART_FAN
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
    public void disconnect(UUID requestId)
            throws CantDisconnectException,
            ConnectionRequestNotFoundException {
        try {

            final ProtocolState state = ProtocolState.PROCESSING_SEND;

            fanActorNetworkServiceDao.disconnectConnection(
                    requestId,
                    state
            );

            FanConnectionRequest cbcr = fanActorNetworkServiceDao.getConnectionRequest(requestId);
            Actors actorType = getActorType(cbcr.getSenderActorType());
            if (cbcr.getRequestType() == RequestType.RECEIVED) {
                sendMessage(
                        buildJsonInformationMessage(cbcr),
                        cbcr.getDestinationPublicKey(),
                        Actors.ART_FAN,
                        cbcr.getSenderPublicKey(),
                        actorType
                );
            } else {
                sendMessage(
                        buildJsonInformationMessage(cbcr),
                        cbcr.getSenderPublicKey(),
                        actorType,
                        cbcr.getDestinationPublicKey(),
                        Actors.ART_FAN
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

            fanActorNetworkServiceDao.denyConnection(
                    requestId,
                    protocolState
            );

            FanConnectionRequest cbcr = fanActorNetworkServiceDao.getConnectionRequest(requestId);
            Actors actorType = getActorType(cbcr.getSenderActorType());
            sendMessage(
                    buildJsonInformationMessage(cbcr),
                    cbcr.getDestinationPublicKey(),
                    Actors.ART_FAN,
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

            fanActorNetworkServiceDao.cancelConnection(
                    requestId,
                    protocolState
            );

            FanConnectionRequest connectionRequest = fanActorNetworkServiceDao.getConnectionRequest(requestId);

            Actors actorType = getActorType(connectionRequest.getSenderActorType());
            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getDestinationPublicKey(),
                    Actors.ART_FAN,
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

            fanActorNetworkServiceDao.acceptConnection(
                    requestId,
                    protocolState
            );

            FanConnectionRequest connectionRequest = fanActorNetworkServiceDao.getConnectionRequest(requestId);
            Actors actorType = getActorType(connectionRequest.getSenderActorType());
            sendMessage(
                    buildJsonInformationMessage(connectionRequest),
                    connectionRequest.getDestinationPublicKey(),
                    Actors.ART_FAN,
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
    public List<FanConnectionRequest> listPendingConnectionNews(PlatformComponentType actorType)
            throws CantListPendingConnectionRequestsException {
        try {

            return fanActorNetworkServiceDao.listPendingConnectionNews(actorType);

        } catch (final CantListPendingConnectionRequestsException e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<FanConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {
        try {

            return fanActorNetworkServiceDao.listPendingConnectionUpdates();

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

            fanActorNetworkServiceDao.confirmActorConnectionRequest(requestId);

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
    public List<FanConnectionRequest> listAllRequest() throws CantListPendingConnectionRequestsException {
        return fanActorNetworkServiceDao.listAllRequest();
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

    private String buildJsonInformationMessage(final FanConnectionRequest aer) {

        return new InformationMessage(
                aer.getRequestId(),
                aer.getRequestAction()
        ).toJson();
    }

    private String buildJsonRequestMessage(final FanConnectionInformation aer) {

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