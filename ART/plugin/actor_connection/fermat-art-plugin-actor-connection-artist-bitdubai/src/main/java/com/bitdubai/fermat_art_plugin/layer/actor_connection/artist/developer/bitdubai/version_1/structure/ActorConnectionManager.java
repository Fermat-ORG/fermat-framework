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
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database.ArtistActorConnectionDao;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/03/16.
 */
public class ActorConnectionManager implements ArtistActorConnectionManager {

    /**
     * Represents the Actor Network service manager.
     */
    private final ArtistManager artistActorNetworkServiceManager;
    /**
     * Represents the plugin database dao.
     */
    private final ArtistActorConnectionDao artistActorConnectionDao;
    /**
     * Represents the Error Manager
     */
    private final ErrorManager errorManager;
    /**
     * Represents the plugin version reference.
     */
    private final PluginVersionReference pluginVersionReference;

    public ActorConnectionManager(
            final ArtistManager artistActorNetworkServiceManager,
            final ArtistActorConnectionDao artistActorConnectionDao,
            final ErrorManager errorManager,
            final PluginVersionReference pluginVersionReference) {
        this.artistActorNetworkServiceManager = artistActorNetworkServiceManager;
        this.artistActorConnectionDao  = artistActorConnectionDao;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
    }

    /**
     * This method returns an ArtistActorConnectionSearch.
     * @param actorIdentitySearching
     * @return
     */
    @Override
    public ArtistActorConnectionSearch getSearch(ArtistLinkedActorIdentity actorIdentitySearching) {
        return new ActorConnectionSearch(
                actorIdentitySearching,
                artistActorConnectionDao);
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
            ActorIdentityInformation actorSending,
            ActorIdentityInformation actorReceiving) throws
            CantRequestActorConnectionException,
            UnsupportedActorTypeException,
            ConnectionAlreadyRequestedException {
        try {

            /**
             * If the actor type of the receiving actor is different of ART_ARTIST I can't send the request.
             */
            if (actorReceiving.getActorType() != Actors.ART_ARTIST)
                throw new UnsupportedActorTypeException(
                        "actorSending: "+actorSending + " - actorReceiving: "+actorReceiving, "Unsupported actor type exception.");
            /**
             * Here I generate the needed information to register the new actor connection record.
             */
            final UUID newConnectionId = UUID.randomUUID();
            final ArtistLinkedActorIdentity linkedIdentity = new ArtistLinkedActorIdentity(
                    actorSending.getPublicKey(),
                    actorSending.getActorType()
            );

            final ConnectionState connectionState = ConnectionState.PENDING_REMOTELY_ACCEPTANCE;
            final long currentTime = System.currentTimeMillis();

            final ArtistActorConnection actorConnection = new ArtistActorConnection(
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
            artistActorConnectionDao.registerConnection(actorConnection);
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

            final ArtistConnectionInformation connectionInformation = new ArtistConnectionInformation(
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
            artistActorNetworkServiceManager.requestConnection(connectionInformation);
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
     * This method contains all the logic to make a disconnect process.
     * @param connectionId   id of the actor connection to be disconnected.
     *
     * @throws CantDisconnectFromActorException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    @Override
    public void disconnect(UUID connectionId) throws
            CantDisconnectFromActorException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {
        try {
            ConnectionState currentConnectionState = artistActorConnectionDao.getConnectionState(
                    connectionId);
            switch (currentConnectionState) {
                case DISCONNECTED_LOCALLY:
                    // no action needed
                    break;
                case CONNECTED:
                    // disconnect from an actor through network service and after that mark as DISCONNECTED LOCALLY
                    // TODO HAVE IN COUNT THAT YOU HAVE TO DISCONNECT FROM AN SPECIFIC TYPE OF ACTOR,.
                    artistActorNetworkServiceManager.disconnect(connectionId);
                    artistActorConnectionDao.changeConnectionState(
                            connectionId,
                            ConnectionState.DISCONNECTED_LOCALLY
                    );
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
        } catch (final CantDisconnectException | ConnectionRequestNotFoundException networkServiceException ) {
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
     * This method contains all the logic to deny a connection.
     * @param connectionId   id of the actor connection to be denied.
     *
     * @throws CantDenyActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    @Override
    public void denyConnection(UUID connectionId) throws
            CantDenyActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {
        try {
            ConnectionState currentConnectionState = artistActorConnectionDao.getConnectionState(
                    connectionId);
            switch (currentConnectionState) {
                case DENIED_LOCALLY:
                    // no action needed
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    // deny connection through network service and after that mark as DENIED LOCALLY
                    artistActorNetworkServiceManager.denyConnection(connectionId);
                    artistActorConnectionDao.changeConnectionState(
                            connectionId,
                            ConnectionState.DENIED_LOCALLY
                    );
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
        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException networkServiceException ) {
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
     * This method contains all the logic to cancel a connection
     * @param connectionId   id of the actor connection request to be canceled.
     *
     * @throws CantCancelActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    @Override
    public void cancelConnection(UUID connectionId) throws
            CantCancelActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {
        try {
            ConnectionState currentConnectionState = artistActorConnectionDao.getConnectionState(
                    connectionId);
            switch (currentConnectionState) {
                case CANCELLED_LOCALLY:
                    // no action needed
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    // cancel connection through network service and after that mark as CANCELLED LOCALLY
                    artistActorNetworkServiceManager.cancelConnection(connectionId);
                    artistActorConnectionDao.changeConnectionState(
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
        } catch (final CantCancelConnectionRequestException | ConnectionRequestNotFoundException networkServiceException ) {
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
     * This method contains all the logic to accept a connection
     * @param connectionId   id of the actor connection to be accepted.
     *
     * @throws CantAcceptActorConnectionRequestException
     * @throws ActorConnectionNotFoundException
     * @throws UnexpectedConnectionStateException
     */
    @Override
    public void acceptConnection(UUID connectionId) throws
            CantAcceptActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {
        try {
            ConnectionState currentConnectionState = artistActorConnectionDao.getConnectionState(
                    connectionId);
            switch (currentConnectionState) {
                case CONNECTED:
                    // no action needed
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    // cancel connection through network service and after that mark as CANCELLED LOCALLY
                    artistActorNetworkServiceManager.acceptConnection(connectionId);
                    artistActorConnectionDao.changeConnectionState(
                            connectionId,
                            ConnectionState.CONNECTED
                    );
                    break;
                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for cancelling.");
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
            throw new CantAcceptActorConnectionRequestException(
                    cantGetConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to get the connection state.");
        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException networkServiceException ) {
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
                    "connectionId: "+connectionId, "Unhandled error.");
        }
    }
}
