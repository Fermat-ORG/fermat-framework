package com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHandleNewsEventException;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.enums.ArtistActorConnectionNotificationType;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database.ArtistActorConnectionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/03/16.
 */
public class ActorConnectionEventsActions {

    private final ArtistManager actorArtistNetworkServiceManager;
    private final ArtistActorConnectionDao dao;
    private final ErrorManager errorManager;
    private final EventManager eventManager;
    private final Broadcaster broadcaster;
    private final PluginVersionReference pluginVersionReference    ;

    /**
     * Constructor with parameters.
     * @param actorArtistNetworkServiceManager
     * @param dao
     * @param errorManager
     * @param eventManager
     * @param broadcaster
     * @param pluginVersionReference
     */
    public ActorConnectionEventsActions(
            final ArtistManager actorArtistNetworkServiceManager,
            final ArtistActorConnectionDao dao,
            final ErrorManager errorManager,
            final EventManager eventManager,
            final Broadcaster broadcaster,
            final PluginVersionReference pluginVersionReference) {
        this.actorArtistNetworkServiceManager = actorArtistNetworkServiceManager;
        this.dao = dao;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.broadcaster = broadcaster;
        this.pluginVersionReference = pluginVersionReference;
    }

    /**
     * This method contains the logic to handle the news event.
     * @throws CantHandleNewsEventException
     */
    public void handleNewsEvent() throws CantHandleNewsEventException {
        try {
            //Here we got all the Artist request
            List<ArtistConnectionRequest> list = actorArtistNetworkServiceManager.
                    listPendingConnectionNews(PlatformComponentType.ART_ARTIST);

            for (final ArtistConnectionRequest request : list)
                this.handleRequestConnection(request);

            //Now the fans request
            list = actorArtistNetworkServiceManager.
                    listPendingConnectionNews(PlatformComponentType.ART_FAN);

            for (final ArtistConnectionRequest request : list)
                this.handleRequestConnection(request);

        } catch(CantListPendingConnectionRequestsException |
                CantRequestActorConnectionException |
                UnsupportedActorTypeException |
                ConnectionAlreadyRequestedException e) {

            throw new CantHandleNewsEventException(
                    e,
                    "Handling news event",
                    "Error handling Artist Connection Request News Event.");
        }

    }

    /**
     * This method contains the logic to handle the Artist Update event.
     * @throws CantHandleNewsEventException
     */
    public void handleArtistUpdateEvent() throws CantHandleNewsEventException {
        try {
            final List<ArtistConnectionRequest> list = actorArtistNetworkServiceManager.
                    listPendingConnectionUpdates();

            for (final ArtistConnectionRequest request : list) {

                switch (request.getRequestAction()) {
                    //TODO: I'll use ART_ARTIST_IDENTITY until the Art community is ready
                    case ACCEPT:
                        this.handleAcceptConnection(request.getRequestId());
                        broadcaster.publish(
                                BroadcasterType.NOTIFICATION_SERVICE,
                                SubAppsPublicKeys.ART_ARTIST_IDENTITY.getCode(),
                                ArtistActorConnectionNotificationType.ACTOR_CONNECTED.getCode());
                        break;
                    case DENY:
                        this.handleDenyConnection(request.getRequestId());
                        break;
                    case DISCONNECT:
                        //if (request.getRequestType() == RequestType.SENT)
                            this.handleDisconnect(request.getRequestId());
                        break;
                    case CANCEL:
                        if(request.getRequestType() == RequestType.RECEIVED)
                            this.handleCancelConnection(request.getRequestId());
                        break;
                }
            }
        } catch(CantListPendingConnectionRequestsException |
                ActorConnectionNotFoundException |
                UnexpectedConnectionStateException |
                CantAcceptActorConnectionRequestException |
                CantDenyActorConnectionRequestException |
                CantDisconnectFromActorException |
                CantCancelActorConnectionRequestException e) {

            throw new CantHandleNewsEventException(
                    e,
                    "Handling Artist Update event",
                    "Error handling Crypto Addresses News Event.");
        }
    }

    /**
     * This method contains the logic to manage the request connection
     * @param request
     * @throws CantRequestActorConnectionException
     * @throws UnsupportedActorTypeException
     * @throws ConnectionAlreadyRequestedException
     */
    public void handleRequestConnection(
            final ArtistConnectionRequest request) throws
            CantRequestActorConnectionException ,
            UnsupportedActorTypeException       ,
            ConnectionAlreadyRequestedException {

        try {

            final ArtistLinkedActorIdentity linkedIdentity = new ArtistLinkedActorIdentity(
                    request.getDestinationPublicKey(),
                    Actors.ART_ARTIST
            );
            final ConnectionState connectionState = ConnectionState.PENDING_LOCALLY_ACCEPTANCE;

            final ArtistActorConnection actorConnection = new ArtistActorConnection(
                    request.getRequestId()       ,
                    linkedIdentity               ,
                    request.getSenderPublicKey() ,
                    request.getSenderAlias()     ,
                    request.getSenderImage()     ,
                    connectionState              ,
                    request.getSentTime()        ,
                    request.getSentTime()
            );

            switch(request.getSenderActorType()) {
                case ART_ARTIST:
                    dao.registerConnection(actorConnection);
                    actorArtistNetworkServiceManager.confirm(request.getRequestId());
                    break;
                case ART_FAN:
                    dao.registerActorConnection(actorConnection);
                    actorArtistNetworkServiceManager.confirm(request.getRequestId());
                    break;
                default:
                    throw new UnsupportedActorTypeException(
                            "request: "+request, "Unsupported actor type exception.");
            }
            broadcaster.publish(
                    BroadcasterType.NOTIFICATION_SERVICE,
                    SubAppsPublicKeys.ART_ARTIST_COMMUNITY.getCode(),
                    ArtistActorConnectionNotificationType.CONNECTION_REQUEST_RECEIVED.getCode());


        } catch (final UnsupportedActorTypeException unsupportedActorTypeException) {

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    unsupportedActorTypeException);
            throw unsupportedActorTypeException;

        } catch (final ActorConnectionAlreadyExistsException actorConnectionAlreadyExistsException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    actorConnectionAlreadyExistsException);
            throw new ConnectionAlreadyRequestedException(
                    actorConnectionAlreadyExistsException,
                    "request: "+request,
                    "The connection was already requested or exists.");
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
     * This method contains all the logic to handle disconnections.
     * @param connectionId
     * @throws CantDisconnectFromActorException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    public void handleDisconnect(
            final UUID connectionId) throws
            CantDisconnectFromActorException   ,
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
                    actorArtistNetworkServiceManager.confirm(connectionId);
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for denying.");
            }
        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {
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
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException);
            throw new CantDisconnectFromActorException(
                    networkServiceException, "connectionId: "+connectionId,
                    "Error trying to disconnect from an actor through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantChangeActorConnectionStateException);
            throw new CantDisconnectFromActorException(
                    cantChangeActorConnectionStateException, "connectionId: "+connectionId,
                    "Error trying to change the actor connection state.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException
                    (pluginVersionReference,
                            UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                            exception);
            throw new CantDisconnectFromActorException(
                    exception,
                    "connectionId: "+connectionId,
                    "Unhandled error.");
        }
    }

    /**
     * This method contains all the logic to deny connections.
     * @param connectionId
     * @throws CantDenyActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    public void handleDenyConnection(
            final UUID connectionId) throws
            CantDenyActorConnectionRequestException,
            ActorConnectionNotFoundException       ,
            UnexpectedConnectionStateException     {

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
                    actorArtistNetworkServiceManager.confirm(connectionId);
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for denying.");
            }
        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

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
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException);
            throw new CantDenyActorConnectionRequestException(
                    networkServiceException,
                    "connectionId: "+connectionId,
                    "Error trying to deny the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException ) {
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
     * This method contains all the logic to handle cancel connections.
     * @param connectionId
     * @throws CantCancelActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    public void handleCancelConnection(final UUID connectionId) throws
            CantCancelActorConnectionRequestException,
            ActorConnectionNotFoundException         ,
            UnexpectedConnectionStateException       {
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
                    actorArtistNetworkServiceManager.confirm(connectionId);
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for cancelling."
                    );
            }
        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {
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
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException);
            throw new CantCancelActorConnectionRequestException(
                    networkServiceException,
                    "connectionId: "+connectionId,
                    "Error trying to cancel the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException ) {
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
                    "connectionId: "+connectionId, "" +
                    "Unhandled error.");
        }
    }

    public void handleAcceptConnection(final UUID connectionId) throws
            CantAcceptActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException       {
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
                    actorArtistNetworkServiceManager.confirm(connectionId);
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for cancelling.");
            }
            //Raise new connection Event.
            FermatEvent eventToRaise = eventManager.getNewEvent(
                    EventType.ARTIST_ACTOR_CONNECTION_NEW_CONNECTION_EVENT);
            eventToRaise.setSource(EventSource.ARTIST_ACTOR_CONNECTION);
            eventManager.raiseEvent(eventToRaise);
        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {
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
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException ) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException);
            throw new CantAcceptActorConnectionRequestException(
                    networkServiceException,
                    "connectionId: "+connectionId,
                    "Error trying to accept the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException ) {
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
