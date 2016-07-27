package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionInformation;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.ChatActorConnectionPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.database.ChatActorConnectionDao;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class ActorConnectionManager implements ChatActorConnectionManager {

    private final ChatManager chatNetworkService;
    private final ChatActorConnectionDao dao;
    private ChatActorConnectionPluginRoot chatActorConnectionPluginRoot;
    private final PluginVersionReference pluginVersionReference;

    public ActorConnectionManager(final ChatManager chatNetworkService,
                                  final ChatActorConnectionDao dao,
                                  final ChatActorConnectionPluginRoot chatActorConnectionPluginRoot,
                                  final PluginVersionReference pluginVersionReference) {

        this.chatNetworkService = chatNetworkService;
        this.dao = dao;
        this.chatActorConnectionPluginRoot = chatActorConnectionPluginRoot;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public ChatActorConnectionSearch getSearch(ChatLinkedActorIdentity actorIdentitySearching) {
        return new ActorConnectionSearch(actorIdentitySearching, dao);
    }

    @Override
    public void requestConnection(ActorIdentityInformation actorSending, ActorIdentityInformation actorReceiving) throws CantRequestActorConnectionException, UnsupportedActorTypeException, ConnectionAlreadyRequestedException {

        try {

            /**
             * If the actor type of the receiving actor is different of CHAT i can't send the request.
             */
            if (actorReceiving.getActorType() != Actors.CHAT)
                throw new UnsupportedActorTypeException(new StringBuilder().append("actorSending: ").append(actorSending).append(" - actorReceiving: ").append(actorReceiving).toString(), "Unsupported actor type exception.");

            /**
             * Here I generate the needed information to register the new actor connection record.
             */
            UUID newConnectionId = UUID.randomUUID();
            final ChatLinkedActorIdentity linkedIdentity = new ChatLinkedActorIdentity(
                    actorSending.getPublicKey(),
                    actorSending.getActorType()
            );

//            ActorConnection actorConnectionCache = dao.chatActorConnectionExists(linkedIdentity,actorReceiving.getPublicKey());
            ConnectionState connectionState = null;
//            if(actorConnectionCache!=null)
//            connectionState = actorConnectionCache.getStatus();
//
//            if(connectionState != null && connectionState.equals(ConnectionState.PENDING_LOCALLY_ACCEPTANCE))
//                connectionState = ConnectionState.CONNECTED;
//            else

            ChatActorConnection oldActorConnection = dao.chatActorConnectionExists(linkedIdentity, actorReceiving.getPublicKey());
            if (oldActorConnection != null) {
                //if (!oldActorConnection.getStatus().getCode().equals(ConnectionState.CONNECTED.getCode()))
                connectionState = oldActorConnection.getConnectionState();//ConnectionState.CONNECTED;

                //TODO: Vilchez esto debemos de analizar todo lo que estaba no funcionaba, pero de esta forma quedo estable
                if (connectionState.getCode().equals(ConnectionState.DISCONNECTED_LOCALLY.getCode()))
                    connectionState = ConnectionState.PENDING_REMOTELY_ACCEPTANCE;
                if (connectionState.getCode().equals(ConnectionState.DISCONNECTED_REMOTELY.getCode()))
                    connectionState = ConnectionState.PENDING_REMOTELY_ACCEPTANCE;
                if (connectionState.getCode().equals(ConnectionState.DENIED_LOCALLY.getCode()))
                    connectionState = ConnectionState.PENDING_REMOTELY_ACCEPTANCE;
                if (connectionState.getCode().equals(ConnectionState.DENIED_REMOTELY.getCode()))
                    connectionState = ConnectionState.PENDING_REMOTELY_ACCEPTANCE;
                //TODO: Esto lo coloque porque cada vez que llegaba aca se creaba un nuevo ID y entonces cuando tratabas desconectar explotaba porque no lo conseguia
                newConnectionId = oldActorConnection.getConnectionId();
                //else
                //    connectionState = ConnectionState.PENDING_REMOTELY_ACCEPTANCE;

            } else connectionState = ConnectionState.PENDING_REMOTELY_ACCEPTANCE;
            final long currentTime = System.currentTimeMillis();

            final ChatActorConnection actorConnection = new ChatActorConnection(
                    newConnectionId,
                    linkedIdentity,
                    actorReceiving.getPublicKey(),
                    actorReceiving.getAlias(),
                    actorReceiving.getImage(),
                    connectionState,
                    currentTime,
                    currentTime,
                    actorReceiving.getStatus()
            );

            /**
             * I register the actor connection.
             */
            if (!dao.registerChatActorConnection(actorConnection, oldActorConnection)) return;

            final ChatConnectionInformation connectionInformation = new ChatConnectionInformation(
                    newConnectionId,
                    actorSending.getPublicKey(),
                    actorSending.getActorType(),
                    actorSending.getAlias(),
                    actorSending.getImage(),
                    actorReceiving.getPublicKey(),
                    currentTime
            );

            /**
             * i send the request through the network service.
             */
            chatNetworkService.requestConnection(connectionInformation);

        } catch (final UnsupportedActorTypeException unsupportedActorTypeException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, unsupportedActorTypeException);
            throw unsupportedActorTypeException;

        } catch (final ActorConnectionAlreadyExistsException actorConnectionAlreadyExistsException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, actorConnectionAlreadyExistsException);
            throw new ConnectionAlreadyRequestedException(actorConnectionAlreadyExistsException, new StringBuilder().append("actorSending: ").append(actorSending).append(" - actorReceiving: ").append(actorReceiving).toString(), "The connection was already requested or exists.");
        } catch (final CantRegisterActorConnectionException cantRegisterActorConnectionException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantRegisterActorConnectionException);
            throw new CantRequestActorConnectionException(cantRegisterActorConnectionException, new StringBuilder().append("actorSending: ").append(actorSending).append(" - actorReceiving: ").append(actorReceiving).toString(), "Problem registering the actor connection in DAO.");
        } catch (final CantRequestConnectionException cantRequestConnectionException) {

            // TODO if there is an error in the actor network service we should delete the generated actor connection record.+++

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantRequestConnectionException);
            throw new CantRequestActorConnectionException(cantRequestConnectionException, new StringBuilder().append("actorSending: ").append(actorSending).append(" - actorReceiving: ").append(actorReceiving).toString(), "Error trying to request the connection through the network service.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantRequestActorConnectionException(exception, new StringBuilder().append("actorSending: ").append(actorSending).append(" - actorReceiving: ").append(actorReceiving).toString(), "Unhandled error.");
        }
    }

    @Override
    public void disconnect(UUID connectionId) throws CantDisconnectFromActorException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {

        try {

            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);

            switch (currentConnectionState) {

                case DISCONNECTED_LOCALLY:
                    // no action needed
                    break;

                case CONNECTED:

                    // disconnect from an actor through network service and after that mark as DISCONNECTED LOCALLY
                    // TODO HAVE IN COUNT THAT YOU HAVE TO DISCONNECT FROM AN SPECIFIC TYPE OF ACTOR,.
                    chatNetworkService.disconnect(connectionId);

                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.DISCONNECTED_LOCALLY
                    );

                    break;

                default:
                    throw new UnexpectedConnectionStateException(new StringBuilder().append("connectionId: ").append(connectionId).append(" - currentConnectionState: ").append(currentConnectionState).toString(), "Unexpected contact state for denying.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantDisconnectFromActorException(cantGetConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to get the connection state.");
        } catch (final CantDisconnectException | ConnectionRequestNotFoundException networkServiceException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantDisconnectFromActorException(networkServiceException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to disconnect from an actor through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantDisconnectFromActorException(cantChangeActorConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantDisconnectFromActorException(exception, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Unhandled error.");
        }
    }

    @Override
    public void denyConnection(UUID connectionId) throws CantDenyActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {

        try {

            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);

            switch (currentConnectionState) {

                case DENIED_LOCALLY:
                    // no action needed
                    break;

                case PENDING_LOCALLY_ACCEPTANCE:

                    // deny connection through network service and after that mark as DENIED LOCALLY
                    chatNetworkService.denyConnection(connectionId);

                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.DENIED_LOCALLY
                    );

                    break;

                default:
                    throw new UnexpectedConnectionStateException(new StringBuilder().append("connectionId: ").append(connectionId).append(" - currentConnectionState: ").append(currentConnectionState).toString(), "Unexpected contact state for denying.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantDenyActorConnectionRequestException(cantGetConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to get the connection state.");
        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException networkServiceException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantDenyActorConnectionRequestException(networkServiceException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to deny the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantDenyActorConnectionRequestException(cantChangeActorConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantDenyActorConnectionRequestException(exception, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Unhandled error.");
        }

    }

    @Override
    public void cancelConnection(UUID connectionId) throws CantCancelActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {

        try {

            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);

            switch (currentConnectionState) {

                case CANCELLED_LOCALLY:
                    // no action needed
                    break;

                case PENDING_REMOTELY_ACCEPTANCE:

                    // cancel connection through network service and after that mark as CANCELLED LOCALLY
                    chatNetworkService.cancelConnection(connectionId);

                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.CANCELLED_LOCALLY
                    );

                    break;

                default:
                    throw new UnexpectedConnectionStateException(
                            new StringBuilder().append("connectionId: ").append(connectionId).append(" - currentConnectionState: ").append(currentConnectionState).toString(),
                            "Unexpected contact state for cancelling."
                    );
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException
            );
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantCancelActorConnectionRequestException(cantGetConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to get the connection state.");
        } catch (final CantCancelConnectionRequestException | ConnectionRequestNotFoundException networkServiceException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantCancelActorConnectionRequestException(networkServiceException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to cancel the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantCancelActorConnectionRequestException(cantChangeActorConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantCancelActorConnectionRequestException(exception, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Unhandled error.");
        }
    }

    @Override
    public void acceptConnection(UUID connectionId) throws CantAcceptActorConnectionRequestException, ActorConnectionNotFoundException, UnexpectedConnectionStateException {

        try {

            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);

            switch (currentConnectionState) {

                case CONNECTED:
                    // no action needed
                    break;

                case PENDING_LOCALLY_ACCEPTANCE:

                    // cancel connection through network service and after that mark as CANCELLED LOCALLY
                    chatNetworkService.acceptConnection(connectionId);

                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.CONNECTED
                    );
                    break;

                case DISCONNECTED_LOCALLY:

                    chatNetworkService.acceptConnection(connectionId);

                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.CONNECTED
                    );
                    break;

                case DISCONNECTED_REMOTELY:

                    chatNetworkService.acceptConnection(connectionId);

                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.CONNECTED
                    );
                    break;

                default:
                    throw new UnexpectedConnectionStateException(new StringBuilder().append("connectionId: ").append(connectionId).append(" - currentConnectionState: ").append(currentConnectionState).toString(), "Unexpected contact state for cancelling.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException
            );
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(cantGetConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to get the connection state.");
        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException networkServiceException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantAcceptActorConnectionRequestException(networkServiceException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to accept the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(cantChangeActorConnectionStateException, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantAcceptActorConnectionRequestException(exception, new StringBuilder().append("connectionId: ").append(connectionId).toString(), "Unhandled error.");
        }
    }
}

