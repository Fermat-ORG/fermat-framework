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
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantUpdateActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorIdentity;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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

    public ActorConnectionManager(final ChatManager chatNetworkService,
                                  final ChatActorConnectionDao dao,
                                  final ChatActorConnectionPluginRoot chatActorConnectionPluginRoot) {

        this.chatNetworkService = chatNetworkService;
        this.dao = dao;
        this.chatActorConnectionPluginRoot = chatActorConnectionPluginRoot;
    }

    @Override
    public ChatActorConnectionSearch getSearch(ActorIdentity actorIdentitySearching) {
        return new ActorConnectionSearch(actorIdentitySearching, dao);
    }

    @Override
    public void requestConnection(ActorIdentityInformation actorSending, ActorIdentityInformation actorReceiving) throws CantRequestActorConnectionException, UnsupportedActorTypeException, ConnectionAlreadyRequestedException {

        try {

            /**
             * If the actor type of the receiving actor is different of CHAT i can't send the request.
             */
            if (actorReceiving.getActorType() != Actors.CHAT)
                throw new UnsupportedActorTypeException("actorSending: " + actorSending + " - actorReceiving: " + actorReceiving, "Unsupported actor type exception.");

            final long currentTime = System.currentTimeMillis();

            /**
             * Here I generate the needed information to register the new actor connection record.
             */

            final ChatLinkedActorIdentity linkedIdentity = new ChatLinkedActorIdentity(
                    actorSending.getPublicKey(),
                    actorSending.getActorType()
            );

            try {

                ChatActorConnection oldActorConnection = dao.getActorConnection(linkedIdentity, actorReceiving.getPublicKey());

                ConnectionState connectionState = oldActorConnection.getConnectionState();

                if (connectionState == ConnectionState.PENDING_LOCALLY_ACCEPTANCE) {
                    // if it is expecting a local acceptance it will mark it as connected and send the acceptance
                    // todo we should raise an event in case the acceptance cannot be send (connection problem | counter-part not connected)
                    // todo validate if the request is sent, otherwise we should raise an event and set the status to "CANNOT_SEND_ACCEPTANCE" in android we should show "send acceptance again"

                    dao.changeConnectionState(oldActorConnection.getConnectionId(), ConnectionState.CONNECTED);
                    chatNetworkService.acceptConnection(oldActorConnection.getConnectionId());

                } else {

                    // if i already have the connection created but i want to resend the connection i will put it in pending remotely acceptance and send the request again
                    // todo validate if the request is sent, otherwise we should raise an event and set the status to "CANNOT_SEND_REQUEST" in android we should show "send request again"

                    dao.changeConnectionState(oldActorConnection.getConnectionId(), ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

                    final ChatConnectionInformation connectionInformation = new ChatConnectionInformation(
                            oldActorConnection.getConnectionId(),
                            actorSending.getPublicKey(),
                            actorSending.getActorType(),
                            actorSending.getAlias(),
                            actorSending.getImage(),
                            actorReceiving.getPublicKey(),
                            currentTime
                    );

                    chatNetworkService.requestConnection(connectionInformation);
                }
            } catch (ActorConnectionNotFoundException actorConnectionNotFound) {

                // if there is not a previous connection i will create it
                // todo validate if the request is sent, otherwise we should raise an event and set the status to "CANNOT_SEND_REQUEST" in android we should show "send request again"

                UUID newConnectionId = UUID.randomUUID();

                final ChatActorConnection actorConnection = new ChatActorConnection(
                        newConnectionId,
                        linkedIdentity,
                        actorReceiving.getPublicKey(),
                        actorReceiving.getAlias(),
                        actorReceiving.getImage(),
                        ConnectionState.PENDING_REMOTELY_ACCEPTANCE,
                        currentTime,
                        currentTime,
                        actorReceiving.getStatus()
                );

                dao.registerActorConnection(actorConnection);

                final ChatConnectionInformation connectionInformation = new ChatConnectionInformation(
                        newConnectionId,
                        actorSending.getPublicKey(),
                        actorSending.getActorType(),
                        actorSending.getAlias(),
                        actorSending.getImage(),
                        actorReceiving.getPublicKey(),
                        currentTime
                );

                chatNetworkService.requestConnection(connectionInformation);
            }

        } catch (final UnsupportedActorTypeException unsupportedActorTypeException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, unsupportedActorTypeException);
            throw unsupportedActorTypeException;

        } catch (final ActorConnectionAlreadyExistsException actorConnectionAlreadyExistsException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, actorConnectionAlreadyExistsException);
            throw new ConnectionAlreadyRequestedException(actorConnectionAlreadyExistsException, "actorSending: " + actorSending + " - actorReceiving: " + actorReceiving, "The connection was already requested or exists.");
        } catch (final CantRegisterActorConnectionException cantRegisterActorConnectionException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantRegisterActorConnectionException);
            throw new CantRequestActorConnectionException(cantRegisterActorConnectionException, "actorSending: " + actorSending + " - actorReceiving: " + actorReceiving, "Problem registering the actor connection in DAO.");
        } catch (final CantRequestConnectionException cantRequestConnectionException) {

            // TODO if there is an error in the actor network service we should delete the generated actor connection record.+++

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantRequestConnectionException);
            throw new CantRequestActorConnectionException(cantRequestConnectionException, "actorSending: " + actorSending + " - actorReceiving: " + actorReceiving, "Error trying to request the connection through the network service.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantRequestActorConnectionException(exception, "actorSending: " + actorSending + " - actorReceiving: " + actorReceiving, "Unhandled error.");
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
                    throw new UnexpectedConnectionStateException("connectionId: " + connectionId + " - currentConnectionState: " + currentConnectionState, "Unexpected contact state for denying.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantDisconnectFromActorException(cantGetConnectionStateException, "connectionId: " + connectionId, "Error trying to get the connection state.");
        } catch (final CantDisconnectException | ConnectionRequestNotFoundException networkServiceException) {

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
                    throw new UnexpectedConnectionStateException("connectionId: " + connectionId + " - currentConnectionState: " + currentConnectionState, "Unexpected contact state for denying.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantDenyActorConnectionRequestException(cantGetConnectionStateException, "connectionId: " + connectionId, "Error trying to get the connection state.");
        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException networkServiceException) {

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
                            "connectionId: " + connectionId + " - currentConnectionState: " + currentConnectionState,
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
            throw new CantCancelActorConnectionRequestException(cantGetConnectionStateException, "connectionId: " + connectionId, "Error trying to get the connection state.");
        } catch (final CantCancelConnectionRequestException | ConnectionRequestNotFoundException networkServiceException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, networkServiceException);
            throw new CantCancelActorConnectionRequestException(networkServiceException, "connectionId: " + connectionId, "Error trying to cancel the connection through the network service.");
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantChangeActorConnectionStateException);
            throw new CantCancelActorConnectionRequestException(cantChangeActorConnectionStateException, "connectionId: " + connectionId, "Error trying to change the actor connection state.");
        } catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantCancelActorConnectionRequestException(exception, "connectionId: " + connectionId, "Unhandled error.");
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
                    throw new UnexpectedConnectionStateException("connectionId: " + connectionId + " - currentConnectionState: " + currentConnectionState, "Unexpected contact state for cancelling.");
            }

        } catch (final ActorConnectionNotFoundException | UnexpectedConnectionStateException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    innerException
            );
            throw innerException;
        } catch (final CantGetConnectionStateException cantGetConnectionStateException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantGetConnectionStateException);
            throw new CantAcceptActorConnectionRequestException(cantGetConnectionStateException, "connectionId: " + connectionId, "Error trying to get the connection state.");
        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException networkServiceException) {

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

    @Override
    public void updateAlias(UUID connectionId, String alias) throws CantUpdateActorConnectionException, ActorConnectionNotFoundException {

        try {

            dao.changeAlias(connectionId, alias);

        } catch (final CantUpdateActorConnectionException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        }  catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantUpdateActorConnectionException(exception, "connectionId: " + connectionId, "Unhandled error.");
        }
    }


    @Override
    public void updateImage(UUID connectionId, byte[] image) throws CantUpdateActorConnectionException, ActorConnectionNotFoundException {

        try {

            dao.changeImage(connectionId, image);

        } catch (final CantUpdateActorConnectionException innerException) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, innerException);
            throw innerException;
        }  catch (final Exception exception) {

            chatActorConnectionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantUpdateActorConnectionException(exception, "connectionId: " + connectionId, "Unhandled error.");
        }
    }
}

