package com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionAlreadyExistsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantChangeActorConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRegisterActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHandleNewsEventException;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.enums.FanActorConnectionNotificationType;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.database.FanActorConnectionDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/04/16.
 */
public class ActorConnectionEventActions {

    private final ArtistManager artistNetworkService;
    private final FanManager fanNetworkService;
    private final FanActorConnectionDao dao;
    private final ErrorManager errorManager;
    private final Broadcaster broadcaster;
    private final PluginVersionReference pluginVersionReference;

    /**
     * Default constructor with parameters.
     * @param artistNetworkService
     * @param dao
     * @param errorManager
     * @param broadcaster
     * @param pluginVersionReference
     */
    public ActorConnectionEventActions(
            final ArtistManager artistNetworkService,
            final FanManager fanNetworkService,
            final FanActorConnectionDao dao,
            final ErrorManager errorManager,
            final Broadcaster broadcaster,
            final PluginVersionReference  pluginVersionReference) {
        this.artistNetworkService = artistNetworkService;
        this.fanNetworkService = fanNetworkService;
        this.dao = dao;
        this.errorManager = errorManager;
        this.broadcaster = broadcaster;
        this.pluginVersionReference = pluginVersionReference;
    }

    /**
     * This method handle with artist news event
     * @throws CantHandleNewsEventException
     */
    public void handleArtistNewsEvent(EventSource eventSource) throws CantHandleNewsEventException {
        try {
            List<ArtistConnectionRequest> artistConnectionRequests;
            List<FanConnectionRequest> fanConnectionRequests;
            switch (eventSource){
                case ARTIST_ACTOR_CONNECTION:
                    artistConnectionRequests = artistNetworkService.listPendingConnectionNews(
                            PlatformComponentType.ART_FAN);
                    for (final ArtistConnectionRequest request : artistConnectionRequests)
                        this.handleArtistRequestConnection(request, Actors.ART_ARTIST);
                    artistConnectionRequests = artistNetworkService.listPendingConnectionNews(
                            PlatformComponentType.ART_ARTIST);
                    for (final ArtistConnectionRequest request : artistConnectionRequests)
                        this.handleArtistRequestConnection(request, Actors.ART_ARTIST);
                    break;
                case ACTOR_NETWORK_SERVICE_FAN:
                    fanConnectionRequests = fanNetworkService.listPendingConnectionNews(
                            PlatformComponentType.ART_FAN);
                    for (final FanConnectionRequest request : fanConnectionRequests)
                        this.handleFanRequestConnection(request, Actors.ART_FAN);
                    fanConnectionRequests = fanNetworkService.listPendingConnectionNews(
                            PlatformComponentType.ART_ARTIST);
                    for (final FanConnectionRequest request : fanConnectionRequests)
                        this.handleFanRequestConnection(request, Actors.ART_FAN);
                    break;
                case ACTOR_NETWORK_SERVICE_ARTIST:
                    //Nothing to do here
                    break;
                default:
                    throw new CantHandleNewsEventException(
                            "Unexpected event source when processing News Event in Fan Actor " +
                                    "Network Service");
            }

        } catch(CantListPendingConnectionRequestsException |
                CantRequestActorConnectionException |
                UnsupportedActorTypeException |
                ConnectionAlreadyRequestedException e) {
            throw new CantHandleNewsEventException(
                    e,
                    "",
                    "Error handling Artist Connection Request News Event.");
        }

    }

    /**
     * This method handles with request connections
     * @param request
     * @param actorType
     * @throws CantRequestActorConnectionException
     * @throws UnsupportedActorTypeException
     * @throws ConnectionAlreadyRequestedException
     */
    public void handleArtistRequestConnection(
            final ArtistConnectionRequest request,
            Actors actorType) throws
            CantRequestActorConnectionException,
            UnsupportedActorTypeException       ,
            ConnectionAlreadyRequestedException {
        try {
            final FanLinkedActorIdentity linkedIdentity = new FanLinkedActorIdentity(
                    request.getDestinationPublicKey(),
                    actorType
            );
            final ConnectionState connectionState = ConnectionState.PENDING_LOCALLY_ACCEPTANCE;
            final FanActorConnection actorConnection = new FanActorConnection(
                    request.getRequestId(),
                    linkedIdentity,
                    request.getSenderPublicKey(),
                    request.getSenderAlias(),
                    request.getSenderImage(),
                    connectionState,
                    request.getSentTime(),
                    request.getSentTime()
            );
            dao.registerActorConnection(actorConnection);
            broadcaster.publish(
                    BroadcasterType.NOTIFICATION_SERVICE,
                    SubAppsPublicKeys.ART_FAN_COMMUNITY.getCode(),
                    FanActorConnectionNotificationType.CONNECTION_REQUEST_RECEIVED.getCode());
            artistNetworkService.confirm(request.getRequestId());
        } catch (final ActorConnectionAlreadyExistsException actorConnectionAlreadyExistsException){
            try {
                artistNetworkService.confirm(request.getRequestId());
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(
                        pluginVersionReference,
                        UnexpectedPluginExceptionSeverity.
                                DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        actorConnectionAlreadyExistsException);
                throw new ConnectionAlreadyRequestedException(
                        actorConnectionAlreadyExistsException,
                        "request: "+request,
                        "The connection was already requested or exists.");
            }
        } catch (final CantRegisterActorConnectionException cantRegisterActorConnectionException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantRegisterActorConnectionException);
            throw new CantRequestActorConnectionException(
                    cantRegisterActorConnectionException,
                    "request: "+request,
                    "Problem registering the actor connection in DAO.");
        } catch (final CantConfirmException cantConfirmException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantConfirmException);
            throw new CantRequestActorConnectionException(
                    cantConfirmException,
                    "request: "+request,
                    "Error trying to confirm the connection request through the network service.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantRequestActorConnectionException(
                    exception,
                    "request: "+request,
                    "Unhandled error.");
        }
    }

    /**
     * This method handles with request connections
     * @param request
     * @param actorType
     * @throws CantRequestActorConnectionException
     * @throws UnsupportedActorTypeException
     * @throws ConnectionAlreadyRequestedException
     */
    public void handleFanRequestConnection(
            final FanConnectionRequest request,
            Actors actorType) throws
            CantRequestActorConnectionException,
            UnsupportedActorTypeException       ,
            ConnectionAlreadyRequestedException {
        try {
            final FanLinkedActorIdentity linkedIdentity = new FanLinkedActorIdentity(
                    request.getDestinationPublicKey(),
                    actorType
            );
            final ConnectionState connectionState = ConnectionState.PENDING_LOCALLY_ACCEPTANCE;
            final FanActorConnection actorConnection = new FanActorConnection(
                    request.getRequestId(),
                    linkedIdentity,
                    request.getSenderPublicKey(),
                    request.getSenderAlias(),
                    request.getSenderImage(),
                    connectionState,
                    request.getSentTime(),
                    request.getSentTime()
            );
            dao.registerActorConnection(actorConnection);
            broadcaster.publish(
                    BroadcasterType.NOTIFICATION_SERVICE,
                    SubAppsPublicKeys.ART_FAN_COMMUNITY.getCode(),
                    FanActorConnectionNotificationType.CONNECTION_REQUEST_RECEIVED.getCode());
            fanNetworkService.confirm(request.getRequestId());
        } catch (final ActorConnectionAlreadyExistsException actorConnectionAlreadyExistsException){
            try {
                fanNetworkService.confirm(request.getRequestId());
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(
                        pluginVersionReference,
                        UnexpectedPluginExceptionSeverity.
                                DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        actorConnectionAlreadyExistsException);
                throw new ConnectionAlreadyRequestedException(
                        actorConnectionAlreadyExistsException,
                        "request: "+request,
                        "The connection was already requested or exists.");
            }
        } catch (final CantRegisterActorConnectionException cantRegisterActorConnectionException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantRegisterActorConnectionException);
            throw new CantRequestActorConnectionException(
                    cantRegisterActorConnectionException,
                    "request: "+request,
                    "Problem registering the actor connection in DAO.");
        } catch (final CantConfirmException cantConfirmException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantConfirmException);
            throw new CantRequestActorConnectionException(
                    cantConfirmException,
                    "request: "+request,
                    "Error trying to confirm the connection request through the network service.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantRequestActorConnectionException(
                    exception,
                    "request: "+request,
                    "Unhandled error.");
        }
    }

    public void handleUpdateEvent(EventSource eventSource) throws CantHandleNewsEventException {
        switch (eventSource){
            case ACTOR_NETWORK_SERVICE_ARTIST:
                handleArtistUpdateEvent();
                break;
            case ACTOR_NETWORK_SERVICE_FAN:
                handleFanUpdateEvent();
                break;
            default:
                throw new CantHandleNewsEventException(
                        "Unexpected Event source from Update event in Fan Actor Network service.");
        }
    }

    /**
     * This method handles with artist news event exception
     * @throws CantHandleNewsEventException
     */
    public void handleArtistUpdateEvent() throws CantHandleNewsEventException {
        try {
            final List<ArtistConnectionRequest> list = artistNetworkService.
                    listPendingConnectionUpdates();
            for (final ArtistConnectionRequest request : list) {
                if (request.getRequestType() == RequestType.RECEIVED  /*&&
                        request.getSenderActorType() == PlatformComponentType.ART_FAN*/) {
                    switch (request.getRequestAction()) {
                        case ACCEPT:
                            this.handleAcceptConnection(request.getRequestId(),EventSource.ACTOR_NETWORK_SERVICE_ARTIST);
                            break;
                        case DISCONNECT:
                            this.handleDisconnect(request.getRequestId(), Actors.ART_ARTIST);
                            break;
                        case CANCEL:
                            this.handleCancelConnection(request.getRequestId());
                            break;
                        case DENY:
                            this.handleDenyConnection(request.getRequestId());
                            break;
                    }
                }
            }
        } catch(CantListPendingConnectionRequestsException |
                ActorConnectionNotFoundException |
                UnexpectedConnectionStateException |
                CantDisconnectFromActorException |
                CantAcceptActorConnectionRequestException |
                CantDenyActorConnectionRequestException |
                CantCancelActorConnectionRequestException e) {
            throw new CantHandleNewsEventException(
                    e,
                    "",
                    "Error handling Artist News Event.");
        }
    }

    /**
     * This method handles with artist news event exception
     * @throws CantHandleNewsEventException
     */
    public void handleFanUpdateEvent() throws
            CantHandleNewsEventException {
        try {
            final List<FanConnectionRequest> list = fanNetworkService.
                    listPendingConnectionUpdates();
            for (final FanConnectionRequest request : list) {
                /*if (request.getRequestType() == RequestType.SENT  &&
                        request.getSenderActorType() == PlatformComponentType.ART_FAN) {*/
                    switch (request.getRequestAction()) {
                        case ACCEPT:
                            this.handleAcceptConnection(request.getRequestId(),EventSource.ACTOR_NETWORK_SERVICE_FAN);
                            break;
                        case DISCONNECT:
                            this.handleDisconnect(request.getRequestId(), Actors.ART_FAN);
                            break;
                    }
                //}
            }
        } catch(CantListPendingConnectionRequestsException |
                ActorConnectionNotFoundException |
                UnexpectedConnectionStateException |
                CantDisconnectFromActorException |
                CantAcceptActorConnectionRequestException e) {
            throw new CantHandleNewsEventException(
                    e,
                    "",
                    "Error handling Artist News Event.");
        }
    }

    /**
     * This method handles with disconnect event
     * @param connectionId
     * @throws CantDisconnectFromActorException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    public void handleDisconnect(final UUID connectionId, Actors actorSource) throws
            CantDisconnectFromActorException ,
            ActorConnectionNotFoundException   ,
            UnexpectedConnectionStateException {
        try {
            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);
            switch (currentConnectionState) {
                case DISCONNECTED_REMOTELY:
                    // no action needed
                    break;
                case CONNECTED:
                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.DISCONNECTED_REMOTELY
                    );
                    switch (actorSource){
                        case ART_ARTIST:
                            artistNetworkService.confirm(connectionId);
                            break;
                        case ART_FAN:
                            fanNetworkService.confirm(connectionId);
                            break;
                    }
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for denying.");
            }
        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException
                innerException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantGetConnectionStateException);
            throw new CantDisconnectFromActorException(
                    cantGetConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException
                networkServiceException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException);
            throw new CantDisconnectFromActorException(
                    networkServiceException,
                    "connectionId: "+connectionId,
                    "Error trying to disconnect from an actor through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantChangeActorConnectionStateException);
            throw new CantDisconnectFromActorException(
                    cantChangeActorConnectionStateException,
                    "connectionId: "+connectionId, "Error trying to change the actor connection state.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantDisconnectFromActorException(exception, "connectionId: "+connectionId, "Unhandled error.");
        }
    }

    /**
     * This method handles with deny connections.
     * @param connectionId
     * @throws CantDenyActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    public void handleDenyConnection(final UUID connectionId) throws
            CantDenyActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException{
        try {
            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);
            switch (currentConnectionState) {
                case DENIED_REMOTELY:
                    // no action needed
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.DENIED_REMOTELY
                    );
                    artistNetworkService.confirm(connectionId);
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for denying.");
            }
        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException
                innerException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantGetConnectionStateException);
            throw new CantDenyActorConnectionRequestException(
                    cantGetConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException
                networkServiceException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException);
            throw new CantDenyActorConnectionRequestException(
                    networkServiceException,
                    "connectionId: "+connectionId,
                    "Error trying to deny the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException
                cantChangeActorConnectionStateException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantChangeActorConnectionStateException);
            throw new CantDenyActorConnectionRequestException(
                    cantChangeActorConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to change the actor connection state.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantDenyActorConnectionRequestException(
                    exception,
                    "connectionId: "+connectionId,
                    "Unhandled error.");
        }
    }

    /**
     * This method handles with cancel connections.
     * @param connectionId
     * @throws CantCancelActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    public void handleCancelConnection(final UUID connectionId) throws
            CantCancelActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException{
        try {
            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);
            switch (currentConnectionState) {
                case CANCELLED_REMOTELY:
                    // no action needed
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.CANCELLED_REMOTELY
                    );
                    artistNetworkService.confirm(connectionId);
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for cancelling."
                    );
            }
        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException
                innerException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException
            );
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantGetConnectionStateException);
            throw new CantCancelActorConnectionRequestException(
                    cantGetConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException
                networkServiceException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException);
            throw new CantCancelActorConnectionRequestException(
                    networkServiceException,
                    "connectionId: "+connectionId,
                    "Error trying to cancel the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException
                cantChangeActorConnectionStateException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantChangeActorConnectionStateException);
            throw new CantCancelActorConnectionRequestException(
                    cantChangeActorConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to change the actor connection state.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantCancelActorConnectionRequestException(
                    exception,
                    "connectionId: "+connectionId,
                    "Unhandled error.");
        }
    }

    /**
     * This method handles with connection acceptance.
     * @param connectionId
     * @throws CantAcceptActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    public void handleAcceptConnection(final UUID connectionId, final EventSource eventSource) throws
            CantAcceptActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException{
        try {
            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);
            switch (currentConnectionState) {
                case CONNECTED:
                    // no action needed
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.CONNECTED
                    );
                    if (eventSource == EventSource.ACTOR_NETWORK_SERVICE_ARTIST)
                        artistNetworkService.confirm(connectionId);
                    else
                        fanNetworkService.confirm(connectionId);
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for cancelling.");
            }
        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException
                innerException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException
            );
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantGetConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(
                    cantGetConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException
                networkServiceException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException);
            throw new CantAcceptActorConnectionRequestException(
                    networkServiceException,
                    "connectionId: "+connectionId,
                    "Error trying to accept the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException
                cantChangeActorConnectionStateException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantChangeActorConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(
                    cantChangeActorConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to change the actor connection state.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantAcceptActorConnectionRequestException(
                    exception,
                    "connectionId: "+connectionId,
                    "Unhandled error.");
        }
    }
}
