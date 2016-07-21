package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionAlreadyExistsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantChangeActorConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRegisterActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
import com.bitdubai.fermat_cht_api.layer.actor_connection.enums.ActorConnectionNotificationType;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionRequest;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.ChatActorConnectionPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.database.ChatActorConnectionDao;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.exceptions.CantHandleNewsEventException;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.exceptions.CantHandleUpdateEventException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

import java.util.List;
import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class ActorConnectionEventActions {

    private final ChatManager chatNetworkService;
    private final ChatActorConnectionDao dao;
    private ChatActorConnectionPluginRoot chatActorConnectionPluginRoot;
    private final EventManager eventManager;
    private final Broadcaster broadcaster;
    private final PluginVersionReference pluginVersionReference;

    public ActorConnectionEventActions(final ChatManager cryptoBrokerNetworkService,
                                       final ChatActorConnectionDao dao,
                                       final ChatActorConnectionPluginRoot chatActorConnectionPluginRoot,
                                       final EventManager eventManager,
                                       final Broadcaster broadcaster,
                                       final PluginVersionReference pluginVersionReference) {

        this.chatNetworkService = cryptoBrokerNetworkService;
        this.dao = dao;
        this.chatActorConnectionPluginRoot = chatActorConnectionPluginRoot;
        this.eventManager = eventManager;
        this.broadcaster = broadcaster;
        this.pluginVersionReference = pluginVersionReference;
    }

    public void handleNewsEvent() throws CantHandleNewsEventException {

        try {

            final List<ChatConnectionRequest> list = chatNetworkService.listPendingConnectionNews(Actors.CHAT);


            for (final ChatConnectionRequest request : list)
                this.handleRequestConnection(request);

        } catch (CantListPendingConnectionRequestsException |
                CantRequestActorConnectionException |
                UnsupportedActorTypeException |
                ConnectionAlreadyRequestedException e) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

            throw new CantHandleNewsEventException(e, "", "Error handling Crypto Broker Connection Request News Event.");
        }

    }

    public void handleUpdateEvent() throws CantHandleUpdateEventException {

        try {

            final List<ChatConnectionRequest> list = chatNetworkService.listPendingConnectionUpdates();

            for (final ChatConnectionRequest request : list) {

                switch (request.getRequestAction()) {

                    case ACCEPT:
                        this.handleAcceptConnection(request.getRequestId());//TODO check this, exception when connectionID is not found

                        FermatBundle fermatBundle = new FermatBundle();
                        fermatBundle.put(SOURCE_PLUGIN, Plugins.CHAT_ACTOR_CONNECTION.getCode());
                        fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(SubAppsPublicKeys.CHT_COMMUNITY.getCode()));
                        fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, SubAppsPublicKeys.CHT_COMMUNITY.getCode());
                        fermatBundle.put(NOTIFICATION_ID, ChatBroadcasterConstants.CHAT_COMMUNITY_CONNECTION_ACCEPTED_NOTIFICATION);
                        fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD.getCode());

                        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

//                        FermatBundle fermatBundle = new FermatBundle();
//                        fermatBundle.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_COMMUNITY.getCode());
//                        fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, ActorConnectionNotificationType.ACTOR_CONNECTED.getCode());

//                        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.CHT_COMMUNITY.getCode(), fermatBundle);
//                        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.CHT_COMMUNITY.getCode(), ActorConnectionNotificationType.ACTOR_CONNECTED.getCode());

                        break;
                   /* case CANCEL:
                        this.handleCancelConnection(request.getRequestId());
                        break;*/
                    case DENY:
                        this.handleDenyConnection(request.getRequestId());
                        break;
                    case DISCONNECT:
//                        if (request.getRequestType() == RequestType.SENT)
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
            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

            throw new CantHandleUpdateEventException(e, "", "Error handling Crypto Addresses News Event.");
        }
    }

    public void handleRequestConnection(final ChatConnectionRequest request) throws CantRequestActorConnectionException,
            UnsupportedActorTypeException,
            ConnectionAlreadyRequestedException {

        try {

            final ChatLinkedActorIdentity linkedIdentity = new ChatLinkedActorIdentity(
                    request.getDestinationPublicKey(),
                    Actors.CHAT
            );

            ChatActorConnection oldActorConnection = dao.chatActorConnectionExists(linkedIdentity, request.getSenderPublicKey());
            ConnectionState connectionState = null;
            if(oldActorConnection!=null)
                connectionState = oldActorConnection.getConnectionState();
//
            if(connectionState != null && connectionState.equals(ConnectionState.CONNECTED))
                return;
//            else
                connectionState = ConnectionState.PENDING_LOCALLY_ACCEPTANCE;

            final ChatActorConnection actorConnection = new ChatActorConnection(
                    request.getRequestId(),
                    linkedIdentity,
                    request.getSenderPublicKey(),
                    request.getSenderAlias(),
                    request.getSenderImage(),
                    connectionState,
                    request.getSentTime(),
                    request.getSentTime(),
                    ""
            );

            switch (request.getSenderActorType()) {
                case CHAT:
                    dao.registerChatActorConnection(actorConnection,oldActorConnection);

                    FermatBundle fermatBundle = new FermatBundle();
                    fermatBundle.put(SOURCE_PLUGIN, Plugins.CHAT_ACTOR_CONNECTION.getCode());
                    fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(SubAppsPublicKeys.CHT_COMMUNITY.getCode()));
                    fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, SubAppsPublicKeys.CHT_COMMUNITY.getCode());
                    fermatBundle.put(NOTIFICATION_ID, ChatBroadcasterConstants.CHAT_COMMUNITY_REQUEST_CONNECTION_NOTIFICATION);
                    fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD.getCode());

                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

//                    FermatBundle fermatBundle = new FermatBundle();
//                    fermatBundle.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_COMMUNITY.getCode());
//                    fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, ActorConnectionNotificationType.CONNECTION_REQUEST_RECEIVED.getCode());
//
//                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.CHT_COMMUNITY.getCode(), fermatBundle);

//
//            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.CHT_COMMUNITY.getCode(), ActorConnectionNotificationType.CONNECTION_REQUEST_RECEIVED.getCode());


                    chatNetworkService.confirm(request.getRequestId());
                    break;
                default:
                    throw new UnsupportedActorTypeException("request: " + request, "Unsupported actor type exception.");
            }

        } catch (final UnsupportedActorTypeException unsupportedActorTypeException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, unsupportedActorTypeException);
            throw unsupportedActorTypeException;

        } catch (final ActorConnectionAlreadyExistsException actorConnectionAlreadyExistsException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, actorConnectionAlreadyExistsException);
            throw new ConnectionAlreadyRequestedException(actorConnectionAlreadyExistsException, "request: " + request, "The connection was already requested or exists.");
        } catch (final CantRegisterActorConnectionException cantRegisterActorConnectionException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantRegisterActorConnectionException);
            throw new CantRequestActorConnectionException(cantRegisterActorConnectionException, "request: " + request, "Problem registering the actor connection in DAO.");
        } catch (final CantConfirmException cantConfirmException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantConfirmException);
            throw new CantRequestActorConnectionException(cantConfirmException, "request: " + request, "Error trying to confirm the connection request through the network service.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantRequestActorConnectionException(exception, "request: " + request, "Unhandled error.");
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

                    chatNetworkService.confirm(connectionId);

                    break;

                default:
                    throw new UnexpectedConnectionStateException("connectionId: " + connectionId + " - currentConnectionState: " + currentConnectionState, "Unexpected contact state for denying.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantDisconnectFromActorException(cantGetConnectionStateException, "connectionId: " + connectionId, "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantDisconnectFromActorException(networkServiceException, "connectionId: " + connectionId, "Error trying to disconnect from an actor through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantDisconnectFromActorException(cantChangeActorConnectionStateException, "connectionId: " + connectionId, "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantDisconnectFromActorException(exception, "connectionId: " + connectionId, "Unhandled error.");
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

                    chatNetworkService.confirm(connectionId);

                    break;

                default:
                    throw new UnexpectedConnectionStateException("connectionId: " + connectionId + " - currentConnectionState: " + currentConnectionState, "Unexpected contact state for denying.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantDenyActorConnectionRequestException(cantGetConnectionStateException, "connectionId: " + connectionId, "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantDenyActorConnectionRequestException(networkServiceException, "connectionId: " + connectionId, "Error trying to deny the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantDenyActorConnectionRequestException(cantChangeActorConnectionStateException, "connectionId: " + connectionId, "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantDenyActorConnectionRequestException(exception, "connectionId: " + connectionId, "Unhandled error.");
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

                    chatNetworkService.confirm(connectionId);

                    break;

                default:
                    throw new UnexpectedConnectionStateException("connectionId: " + connectionId + " - currentConnectionState: " + currentConnectionState, "Unexpected contact state for cancelling.");
            }

            //TODO: REALIZAR EVENTO PARA AGREGAR CONTACTO
//            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CHAT_ACTOR_CONNECTION_REQUEST_NEW);
//            eventToRaise.setSource(EventSource.CHAT_ACTOR_CONNECTION);
//            eventManager.raiseEvent(eventToRaise);

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException
            );
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(cantGetConnectionStateException, "connectionId: " + connectionId, "Error trying to get the connection state.");
        } catch (final CantConfirmException | ConnectionRequestNotFoundException networkServiceException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantAcceptActorConnectionRequestException(networkServiceException, "connectionId: " + connectionId, "Error trying to accept the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(cantChangeActorConnectionStateException, "connectionId: " + connectionId, "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantAcceptActorConnectionRequestException(exception, "connectionId: " + connectionId, "Unhandled error.");
        }
    }
}
