package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantConfirmException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionRequest;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorConnectionPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorConnectionDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.exceptions.CantHandleNewsEventException;

import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;


/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.structure.ActorConnectionEventActions</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 14/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorConnectionEventActions {


    private final CryptoBrokerManager cryptoBrokerNetworkService;
    private final CryptoBrokerActorConnectionDao dao;
    private final CryptoBrokerActorConnectionPluginRoot pluginRoot;
    private final EventManager eventManager;
    private final Broadcaster broadcaster;

    public ActorConnectionEventActions(final CryptoBrokerManager cryptoBrokerNetworkService,
                                       final CryptoBrokerActorConnectionDao dao,
                                       final CryptoBrokerActorConnectionPluginRoot pluginRoot,
                                       final EventManager eventManager,
                                       final Broadcaster broadcaster) {

        this.cryptoBrokerNetworkService = cryptoBrokerNetworkService;
        this.dao = dao;
        this.pluginRoot = pluginRoot;
        this.eventManager = eventManager;
        this.broadcaster = broadcaster;
    }

    public void handleNewsEvent() throws CantHandleNewsEventException {

        try {

            final List<CryptoBrokerConnectionRequest> list = cryptoBrokerNetworkService.listPendingConnectionNews(Actors.CBP_CRYPTO_BROKER);


            for (final CryptoBrokerConnectionRequest request : list)
                this.handleRequestConnection(request);

        } catch (CantListPendingConnectionRequestsException |
                CantRequestActorConnectionException |
                UnsupportedActorTypeException |
                ConnectionAlreadyRequestedException e) {

            throw new CantHandleNewsEventException(e, "", "Error handling Crypto Broker Connection Request News Event.");
        }

    }

    public void handleCryptoBrokerUpdateEvent() throws CantHandleNewsEventException {

        try {

            final List<CryptoBrokerConnectionRequest> list = cryptoBrokerNetworkService.listPendingConnectionUpdates();

            for (final CryptoBrokerConnectionRequest request : list) {

                switch (request.getRequestAction()) {

                    case ACCEPT:
                        this.handleAcceptConnection(request.getRequestId());

                        FermatBundle fermatBundle = new FermatBundle();
                        fermatBundle.put(SOURCE_PLUGIN, Plugins.CRYPTO_BROKER.getCode());
                        fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode()));
                        fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode());
                        fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CBC_ACTOR_CONNECTED);
                        fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST.getCode());

                        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

                        break;
                   /* case CANCEL:
                        this.handleCancelConnection(request.getRequestId());
                        break;*/
                    case DENY:
                        this.handleDenyConnection(request.getRequestId());
                        break;
                    case DISCONNECT:
                        if (request.getRequestType() == RequestType.SENT)
                            this.handleDisconnect(request.getRequestId());

                        break;

                }

            }

        } catch (CantListPendingConnectionRequestsException |
                ActorConnectionNotFoundException |
                UnexpectedConnectionStateException |
                CantAcceptActorConnectionRequestException /* |
                CantCancelActorConnectionRequestException */ |
                CantDenyActorConnectionRequestException |
                CantDisconnectFromActorException e) {

            throw new CantHandleNewsEventException(e, "", "Error handling Crypto Addresses News Event.");
        }

    }


    public void handleRequestConnection(final CryptoBrokerConnectionRequest request) throws CantRequestActorConnectionException,
            UnsupportedActorTypeException,
            ConnectionAlreadyRequestedException {

        try {

            final CryptoBrokerLinkedActorIdentity linkedIdentity = new CryptoBrokerLinkedActorIdentity(
                    request.getDestinationPublicKey(),
                    Actors.CBP_CRYPTO_BROKER
            );
            final ConnectionState connectionState = ConnectionState.PENDING_LOCALLY_ACCEPTANCE;

            final CryptoBrokerActorConnection actorConnection = new CryptoBrokerActorConnection(
                    request.getRequestId(),
                    linkedIdentity,
                    request.getSenderPublicKey(),
                    request.getSenderAlias(),
                    request.getSenderImage(),
                    connectionState,
                    request.getSentTime(),
                    request.getSentTime(),
                    null
            );

            switch (request.getSenderActorType()) {
                case CBP_CRYPTO_BROKER:

                    dao.registerActorConnection(actorConnection);

                    cryptoBrokerNetworkService.confirm(request.getRequestId());
                    break;
                case CBP_CRYPTO_CUSTOMER:

                    dao.registerActorConnection(actorConnection);

                    cryptoBrokerNetworkService.confirm(request.getRequestId());
                    break;
                default:
                    throw new UnsupportedActorTypeException(new StringBuilder().append("request: ").append(request).toString(), "Unsupported actor type exception.");
            }

            FermatBundle fermatBundle = new FermatBundle();
            fermatBundle.put(SOURCE_PLUGIN, Plugins.CRYPTO_BROKER.getCode());
            fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode()));
            fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode());
            fermatBundle.put(NOTIFICATION_ID, CBPBroadcasterConstants.CBC_CONNECTION_REQUEST_RECEIVED);
            fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST.getCode());

            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);


        } catch (final UnsupportedActorTypeException unsupportedActorTypeException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, unsupportedActorTypeException);
            throw unsupportedActorTypeException;

        } catch (final ActorConnectionAlreadyExistsException actorConnectionAlreadyExistsException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, actorConnectionAlreadyExistsException);
            throw new ConnectionAlreadyRequestedException(actorConnectionAlreadyExistsException, new StringBuilder().append("request: ").append(request).toString(), "The connection was already requested or exists.");
        } catch (final CantRegisterActorConnectionException cantRegisterActorConnectionException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantRegisterActorConnectionException);
            throw new CantRequestActorConnectionException(cantRegisterActorConnectionException, new StringBuilder().append("request: ").append(request).toString(), "Problem registering the actor connection in DAO.");
        } catch (final CantConfirmException cantConfirmException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantConfirmException);
            throw new CantRequestActorConnectionException(cantConfirmException, new StringBuilder().append("request: ").append(request).toString(), "Error trying to confirm the connection request through the network service.");
        } catch (final Exception exception) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantRequestActorConnectionException(exception, new StringBuilder().append("request: ").append(request).toString(), "Unhandled error.");
        }

    }

    public void handleDisconnect(final UUID connectionId) throws CantDisconnectFromActorException,
            ActorConnectionNotFoundException,
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

                    cryptoBrokerNetworkService.confirm(connectionId);

                    break;

                default:
                    throw new UnexpectedConnectionStateException(new StringBuilder().append("connectionId: ").append(connectionId).append(" - currentConnectionState: ").append(currentConnectionState).toString(), "Unexpected contact state for denying.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantDisconnectFromActorException(cantGetConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantDisconnectFromActorException(networkServiceException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to disconnect from an actor through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantDisconnectFromActorException(cantChangeActorConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantDisconnectFromActorException(exception, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Unhandled error.");
        }
    }

    public void handleDenyConnection(final UUID connectionId) throws CantDenyActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {

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

                    cryptoBrokerNetworkService.confirm(connectionId);

                    break;

                default:
                    throw new UnexpectedConnectionStateException(new StringBuilder().append("connectionId: ").append(connectionId).append(" - currentConnectionState: ").append(currentConnectionState).toString(), "Unexpected contact state for denying.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantDenyActorConnectionRequestException(cantGetConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantDenyActorConnectionRequestException(networkServiceException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to deny the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantDenyActorConnectionRequestException(cantChangeActorConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantDenyActorConnectionRequestException(exception, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Unhandled error.");
        }

    }

    public void handleCancelConnection(final UUID connectionId) throws CantCancelActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {

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

                    cryptoBrokerNetworkService.confirm(connectionId);

                    break;

                default:
                    throw new UnexpectedConnectionStateException(
                            new StringBuilder().append("connectionId: ").append(connectionId).append(" - currentConnectionState: ").append(currentConnectionState).toString(),
                            "Unexpected contact state for cancelling."
                    );
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException
            );
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantCancelActorConnectionRequestException(cantGetConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantCancelActorConnectionRequestException(networkServiceException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to cancel the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantCancelActorConnectionRequestException(cantChangeActorConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantCancelActorConnectionRequestException(exception, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Unhandled error.");
        }
    }

    public void handleAcceptConnection(final UUID connectionId) throws CantAcceptActorConnectionRequestException,
            ActorConnectionNotFoundException,
            UnexpectedConnectionStateException {

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

                    cryptoBrokerNetworkService.confirm(connectionId);

                    break;

                default:
                    throw new UnexpectedConnectionStateException(new StringBuilder().append("connectionId: ").append(connectionId).append(" - currentConnectionState: ").append(currentConnectionState).toString(), "Unexpected contact state for cancelling.");
            }

            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_ACTOR_CONNECTION_NEW_CONNECTION);
            eventToRaise.setSource(EventSource.CRYPTO_BROKER_ACTOR_CONNECTION);
            eventManager.raiseEvent(eventToRaise);

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException
            );
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(cantGetConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantAcceptActorConnectionRequestException(networkServiceException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to accept the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(cantChangeActorConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantAcceptActorConnectionRequestException(exception, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Unhandled error.");
        }
    }
}