package com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionAlreadyExistsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantChangeActorConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRegisterActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.events.ArtistConnectionRequestAcceptedEvent;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.database.FanActorConnectionDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/04/16.
 */
public class ActorConnectionManager implements FanActorConnectionManager {

    private final ArtistManager artistNetworkService;
    private final FanActorConnectionDao dao;
    private final FanManager fanNetworkService;
    private final ErrorManager errorManager;
    private final EventManager eventManager;
    private final PluginVersionReference pluginVersionReference;

    /**
     * Default constructor with parameters.
     * @param artistNetworkService
     * @param dao
     * @param errorManager
     * @param pluginVersionReference
     */
    public ActorConnectionManager(final ArtistManager artistNetworkService,
                                  final FanActorConnectionDao dao,
                                  final ErrorManager errorManager,
                                  final PluginVersionReference pluginVersionReference,
                                  final FanManager fanNetworkService,
                                  final EventManager eventManager) {
        this.artistNetworkService = artistNetworkService;
        this.dao = dao;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
        this.fanNetworkService = fanNetworkService;
        this.eventManager = eventManager;
    }

    /**
     * This method returns a FanActorConnectionSearch
     * @param actorIdentitySearching
     * @return
     */
    @Override
    public FanActorConnectionSearch getSearch(FanLinkedActorIdentity actorIdentitySearching) {
        return new ActorConnectionSearch(actorIdentitySearching, dao);
    }

    /**
     * This method makes a request connection
     * @param actorSending    the actor which is trying to connect.
     * @param actorReceiving  the actor which we're trying to connect with.
     *
     * @throws CantRequestActorConnectionException
     * @throws UnsupportedActorTypeException
     * @throws ConnectionAlreadyRequestedException
     */
    @Override
    public void requestConnection(
            final ActorIdentityInformation actorSending,
            final ActorIdentityInformation actorReceiving) throws
            CantRequestActorConnectionException,
            UnsupportedActorTypeException,
            ConnectionAlreadyRequestedException {
        try{
            // TODO I'm gonna implement the fan connection, an artist can be make a connection with a fan
            /**
             * If the actor type of the receiving actor is different of ART_ARTIST I can't send the request.
             */
            if (actorReceiving.getActorType() != Actors.ART_FAN)
                throw new UnsupportedActorTypeException(
                        "actorSending: "+actorSending + " - actorReceiving: "+actorReceiving, "Unsupported actor type exception.");
            /**
             * Here I generate the needed information to register the new actor connection record.
             */
            final UUID newConnectionId = UUID.randomUUID();
            final FanLinkedActorIdentity linkedIdentity = new FanLinkedActorIdentity(
                    actorSending.getPublicKey(),
                    actorSending.getActorType()
            );

            final ConnectionState connectionState = ConnectionState.PENDING_REMOTELY_ACCEPTANCE;
            final long currentTime = System.currentTimeMillis();

            final FanActorConnection actorConnection = new FanActorConnection(
                    newConnectionId,
                    linkedIdentity,
                    actorReceiving.getPublicKey(),
                    actorReceiving.getAlias(),
                    actorReceiving.getImage(),
                    connectionState,
                    currentTime,
                    currentTime
            );
            /**
             * I register the actor connection.
             */
            dao.registerActorConnection(actorConnection);
            PlatformComponentType platformComponentType = PlatformComponentType.ART_ARTIST;
            switch (actorSending.getActorType()){
                case ART_ARTIST:
                    platformComponentType = PlatformComponentType.ART_ARTIST;
                    break;
                case ART_FAN:
                    platformComponentType = PlatformComponentType.ART_FAN;
                    break;
            }
            PlatformComponentType platformComponentTypeReceiving = PlatformComponentType.ART_ARTIST;
            switch (actorReceiving.getActorType()){
                case ART_ARTIST:
                    platformComponentTypeReceiving = PlatformComponentType.ART_ARTIST;
                    break;
                case ART_FAN:
                    platformComponentTypeReceiving = PlatformComponentType.ART_FAN;
                    break;
            }

            final FanConnectionInformation connectionInformation = new FanConnectionInformation(
                    newConnectionId,
                    actorSending.getPublicKey(),
                    platformComponentType,
                    actorSending.getAlias(),
                    actorSending.getImage(),
                    actorReceiving.getPublicKey(),
                    platformComponentTypeReceiving,
                    currentTime
            );

            /**
             * I'll send the request through the network service.
             */
            fanNetworkService.requestConnection(connectionInformation);

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
                    "actorSending: "+actorSending + " - actorReceiving: "+actorReceiving,
                    "The connection was already requested or exists.");
        } catch (final CantRegisterActorConnectionException cantRegisterActorConnectionException) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantRegisterActorConnectionException);
            throw new CantRequestActorConnectionException(
                    cantRegisterActorConnectionException,
                    "actorSending: "+actorSending + " - actorReceiving: "+actorReceiving,
                    "Problem registering the actor connection in DAO.");
        } catch (final CantRequestConnectionException cantRequestConnectionException) {
            // TODO if there is an error in the actor network service we should delete the generated actor connection record.+++
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantRequestConnectionException);
            throw new CantRequestActorConnectionException(
                    cantRequestConnectionException,
                    "actorSending: "+actorSending + " - actorReceiving: "+actorReceiving,
                    "Error trying to request the connection through the network service.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantRequestActorConnectionException(
                    exception,
                    "actorSending: "+actorSending + " - actorReceiving: "+actorReceiving,
                    "Unhandled error.");
        }

    }

    /**
     * This method handle a disconnection
     * @param connectionId   id of the actor connection to be disconnected.
     *
     * @throws CantDisconnectFromActorException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    @Override
    public void disconnect(final UUID connectionId) throws
            CantDisconnectFromActorException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {
        try {
            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);
            switch (currentConnectionState) {
                case DISCONNECTED_LOCALLY:
                    // no action needed
                    break;
                case CONNECTED:
                    // disconnect from an actor through network service and after that mark as DISCONNECTED LOCALLY
                    // TODO HAVE IN COUNT THAT YOU HAVE TO DISCONNECT FROM AN SPECIFIC TYPE OF ACTOR,.
                    fanNetworkService.disconnect(connectionId);
                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.DISCONNECTED_LOCALLY
                    );
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
            throw new CantDisconnectFromActorException(cantGetConnectionStateException, "connectionId: "+connectionId,
                    "Error trying to get the connection state.");
        } catch (final CantDisconnectException | ConnectionRequestNotFoundException
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
                    "connectionId: "+connectionId,
                    "Error trying to change the actor connection state.");
        } catch (final Exception exception) {
            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantDisconnectFromActorException(
                    exception,
                    "connectionId: "+connectionId,
                    "Unhandled error.");
        }
    }

    /**
     * This method denies a connection.
     * @param connectionId   id of the actor connection to be denied.
     *
     * @throws CantDenyActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    @Override
    public void denyConnection(final UUID connectionId)
            throws CantDenyActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {
        try {
            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);
            switch (currentConnectionState) {
                case DENIED_LOCALLY:
                    // no action needed
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    // deny connection through network service and after that mark as DENIED LOCALLY
                    artistNetworkService.denyConnection(connectionId);
                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.DENIED_LOCALLY
                    );
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
        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException
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
     * This method cancels a connection.
     * @param connectionId   id of the actor connection request to be canceled.
     *
     * @throws CantCancelActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    @Override
    public void cancelConnection(final UUID connectionId) throws
            CantCancelActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {
        try {
            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);
            switch (currentConnectionState) {
                case CANCELLED_LOCALLY:
                    // no action needed
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    // cancel connection through network service and after that mark as CANCELLED LOCALLY
                    artistNetworkService.cancelConnection(connectionId);
                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.CANCELLED_LOCALLY
                    );
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
        } catch (final CantCancelConnectionRequestException | ConnectionRequestNotFoundException
                networkServiceException ) {
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
                    "connectionId: "+connectionId,
                    "Unhandled error.");
        }
    }


    /**
     * This method accepts a connection.
     * @param connectionId   id of the actor connection to be accepted.
     *
     * @throws CantAcceptActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    @Override
    public void acceptConnection(
            final UUID connectionId) throws
            CantAcceptActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException{
        try {
            System.out.println("************* im accepting in actor connection the request: "+connectionId);
            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);
            System.out.println("************* im accepting in actor connection the request: -> current connection state: "+currentConnectionState);
            switch (currentConnectionState) {
                case CONNECTED:
                    // no action needed
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    Actors linkedActorType = dao.getLinkedIdentityActorType(connectionId);
                    switch (linkedActorType) {
                        case ART_ARTIST:
                            artistNetworkService.acceptConnection(connectionId);
                            dao.changeConnectionState(
                                    connectionId,
                                    ConnectionState.CONNECTED
                            );
                            //We gonna raise a event indicating the request accepting.
                            //raiseConnectionRequestEvent(artistAcceptedPublicKey);
                            break;
                        case ART_FAN:
                            fanNetworkService.acceptConnection(connectionId);
                            dao.changeConnectionState(
                                    connectionId,
                                    ConnectionState.CONNECTED
                            );
                            String artistAcceptedPublicKey="";
                            List<FanActorConnection> fanActorConnectionList =
                                    dao.listActorConnections(dao.getActorConnectionsTable());
                            for(FanActorConnection fanActorConnection : fanActorConnectionList){
                                if(fanActorConnection.getConnectionId().equals(connectionId)){
                                    artistAcceptedPublicKey = fanActorConnection.getPublicKey();
                                    break;
                                }
                            }
                            //We gonna raise a event indicating the request accepting.
                            raiseConnectionRequestEvent(artistAcceptedPublicKey);
                    }
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
        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException
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

    /**
     * This method raise an event to indicate that a connection is accepted.
     * @param artistAcceptedPublicKey
     */
    private void raiseConnectionRequestEvent(final String artistAcceptedPublicKey){
        ArtistConnectionRequestAcceptedEvent eventToRaise =
                (ArtistConnectionRequestAcceptedEvent) eventManager.getNewEvent(
                EventType.ARTIST_CONNECTION_REQUEST_ACCEPTED_EVENT);
        eventToRaise.setSource(EventSource.ARTIST_ACTOR_CONNECTION);
        eventToRaise.setArtistAcceptedPublicKey(artistAcceptedPublicKey);
        eventManager.raiseEvent(eventToRaise);
    }

    /**
     * This method checks if an actor connection exists.
     * @param linkedIdentityPublicKey
     * @param linkedIdentityActorType
     * @param actorPublicKey
     * @return
     * @throws CantGetActorConnectionException
     */
    public List<FanActorConnection> getRequestActorConnections(
            String linkedIdentityPublicKey,
            Actors linkedIdentityActorType,
            String actorPublicKey) throws CantGetActorConnectionException {
        return dao.getRequestActorConnections(
                linkedIdentityPublicKey,
                linkedIdentityActorType,
                actorPublicKey);
    }
}

