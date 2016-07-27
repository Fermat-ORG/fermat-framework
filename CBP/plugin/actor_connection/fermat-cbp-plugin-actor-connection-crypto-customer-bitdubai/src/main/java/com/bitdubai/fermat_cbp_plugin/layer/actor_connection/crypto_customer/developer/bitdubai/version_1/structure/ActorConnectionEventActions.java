package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantConfirmException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionRequest;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantListCryptoCustomersException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorConnectionPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorConnectionDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.exceptions.CantHandleNewsEventException;

import java.util.List;
import java.util.UUID;


/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure.ActorConnectionEventActions</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class ActorConnectionEventActions {

    private final CryptoBrokerManager cryptoBrokerNetworkService;
    private final CryptoCustomerActorConnectionDao dao;
    private final CryptoCustomerActorConnectionPluginRoot pluginRoot;
    private final Broadcaster broadcaster;
    private final PluginVersionReference pluginVersionReference;
    private final CryptoCustomerManager cryptoCustomerManager;

    public ActorConnectionEventActions(final CryptoBrokerManager cryptoBrokerNetworkService,
                                       final CryptoCustomerActorConnectionDao dao,
                                       final CryptoCustomerActorConnectionPluginRoot pluginRoot,
                                       final Broadcaster broadcaster,
                                       final PluginVersionReference pluginVersionReference,
                                       final CryptoCustomerManager cryptoCustomerManager) {

        this.cryptoBrokerNetworkService = cryptoBrokerNetworkService;
        this.dao = dao;
        this.pluginRoot = pluginRoot;
        this.broadcaster = broadcaster;
        this.pluginVersionReference = pluginVersionReference;
        this.cryptoCustomerManager = cryptoCustomerManager;
    }

    public void handleCryptoBrokerNewsEvent() throws CantHandleNewsEventException {

        try {

            final List<CryptoBrokerConnectionRequest> list = cryptoBrokerNetworkService.listPendingConnectionNews(Actors.CBP_CRYPTO_CUSTOMER);
            //I need to know the location of the actors, so I'll need the actors list
            CryptoCustomerSearch cryptoCustomerSearch = cryptoCustomerManager.getSearch();
            String actorAlias;
            Location location;
            List<CryptoCustomerExposingData> cryptoCustomerExposingDataList;
            for (final CryptoBrokerConnectionRequest request : list) {
                actorAlias = request.getSenderAlias();
                location = null;
                try {
                    cryptoCustomerExposingDataList = cryptoCustomerSearch.getResultAlias(actorAlias, 10, 0);
                    for (CryptoCustomerExposingData cryptoCustomerExposingData : cryptoCustomerExposingDataList) {
                        if (cryptoCustomerExposingData.getAlias().equals(actorAlias)) {
                            location = cryptoCustomerExposingData.getLocation();
                            break;
                        }
                    }
                } catch (CantListCryptoCustomersException e) {
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    location = null;
                }
                this.handleRequestConnection(request, Actors.CBP_CRYPTO_BROKER, location);
            }


        } catch (CantListPendingConnectionRequestsException |
                CantRequestActorConnectionException |
                UnsupportedActorTypeException |
                ConnectionAlreadyRequestedException e) {

            throw new CantHandleNewsEventException(e, "", "Error handling Crypto Broker Connection Request News Event.");
        }

    }

    public void handleRequestConnection(final CryptoBrokerConnectionRequest request, Actors actorType, Location location) throws CantRequestActorConnectionException,
            UnsupportedActorTypeException,
            ConnectionAlreadyRequestedException {

        try {

            final CryptoCustomerLinkedActorIdentity linkedIdentity = new CryptoCustomerLinkedActorIdentity(
                    request.getDestinationPublicKey(),
                    actorType
            );
            final ConnectionState connectionState = ConnectionState.PENDING_LOCALLY_ACCEPTANCE;
            final CryptoCustomerActorConnection actorConnection = new CryptoCustomerActorConnection(
                    request.getRequestId(),
                    linkedIdentity,
                    request.getSenderPublicKey(),
                    request.getSenderAlias(),
                    request.getSenderImage(),
                    connectionState,
                    request.getSentTime(),
                    request.getSentTime(),
                    location
            );

            dao.registerActorConnection(actorConnection);
            dao.persistLocation(actorConnection);

            FermatBundle fermatBundle = new FermatBundle();
            fermatBundle.put(NotificationBundleConstants.SOURCE_PLUGIN, Plugins.CRYPTO_CUSTOMER.getCode());
            fermatBundle.put(NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM, new Owner(SubAppsPublicKeys.CBP_CUSTOMER_COMMUNITY.getCode()));
            fermatBundle.put(NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY, SubAppsPublicKeys.CBP_CUSTOMER_COMMUNITY.getCode());
            fermatBundle.put(NotificationBundleConstants.NOTIFICATION_ID, CBPBroadcasterConstants.CCC_CONNECTION_REQUEST_RECEIVED);
            fermatBundle.put(NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE, Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode());

            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

            cryptoBrokerNetworkService.confirm(request.getRequestId());

        } catch (final ActorConnectionAlreadyExistsException actorConnectionAlreadyExistsException) {

            try {
                cryptoBrokerNetworkService.confirm(request.getRequestId());
            } catch (Exception e) {

                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, actorConnectionAlreadyExistsException);
                throw new ConnectionAlreadyRequestedException(actorConnectionAlreadyExistsException, new StringBuilder().append("request: ").append(request).toString(), "The connection was already requested or exists.");
            }

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

    public void handleCryptoBrokerUpdateEvent() throws CantHandleNewsEventException {

        try {

            final List<CryptoBrokerConnectionRequest> list = cryptoBrokerNetworkService.listPendingConnectionUpdates();


            for (final CryptoBrokerConnectionRequest request : list) {

                if (request.getRequestType() == RequestType.RECEIVED && request.getSenderActorType() == Actors.CBP_CRYPTO_CUSTOMER) {

                    switch (request.getRequestAction()) {

                        case DISCONNECT:
                            this.handleDisconnect(request.getRequestId());
                            break;

                    }
                }
            }

        } catch (CantListPendingConnectionRequestsException |
                ActorConnectionNotFoundException |
                UnexpectedConnectionStateException |
                CantDisconnectFromActorException e) {

            throw new CantHandleNewsEventException(e, "", "Error handling Crypto Addresses News Event.");
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