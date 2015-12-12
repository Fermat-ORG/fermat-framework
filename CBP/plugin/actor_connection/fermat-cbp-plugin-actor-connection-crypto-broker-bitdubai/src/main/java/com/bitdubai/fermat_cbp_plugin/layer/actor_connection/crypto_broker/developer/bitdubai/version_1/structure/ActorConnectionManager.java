package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantChangeActorConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorConnectionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure.ActorConnectionManager</code>
 * bla bla bla.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/11/2015.
 */
public class ActorConnectionManager implements CryptoBrokerActorConnectionManager {

    private final CryptoBrokerManager            cryptoBrokerNetworkService;
    private final CryptoBrokerActorConnectionDao dao                       ;
    private final ErrorManager                   errorManager              ;
    private final PluginVersionReference         pluginVersionReference    ;

    public ActorConnectionManager(final CryptoBrokerManager            cryptoBrokerNetworkService,
                                  final CryptoBrokerActorConnectionDao dao                       ,
                                  final ErrorManager                   errorManager              ,
                                  final PluginVersionReference         pluginVersionReference    ) {

        this.cryptoBrokerNetworkService = cryptoBrokerNetworkService;
        this.dao                        = dao                       ;
        this.errorManager               = errorManager              ;
        this.pluginVersionReference     = pluginVersionReference    ;
    }

    @Override
    public CryptoBrokerActorConnectionSearch getSearch(CryptoBrokerActorIdentity actorIdentitySearching) {

        return new ActorConnectionSearch(actorIdentitySearching, dao);
    }

    @Override
    public void requestConnection(final CryptoBrokerActorConnection actorConnection) throws CantRequestActorConnectionException,
                                                                                            UnsupportedActorTypeException      ,
                                                                                            ConnectionAlreadyRequestedException {

    }

    @Override
    public void disconnect(final UUID connectionId) throws CantDisconnectFromActorException   ,
                                                           ActorConnectionNotFoundException   ,
                                                           UnexpectedConnectionStateException {

    }

    @Override
    public void denyConnection(final UUID connectionId) throws CantDenyActorConnectionRequestException,
                                                               ActorConnectionNotFoundException       ,
                                                               UnexpectedConnectionStateException     {

        try {

            ConnectionState currentConnectionState = dao.getConnectionState(connectionId);

            switch (currentConnectionState) {

                case DENIED_LOCALLY:
                    // no action needed
                    break;

                case PENDING_LOCALLY_ACCEPTANCE:

                    // deny connection through network service and after that mark as DENIED LOCALLY
                    cryptoBrokerNetworkService.denyConnection(connectionId);

                    dao.changeConnectionState(
                            connectionId,
                            ConnectionState.DENIED_LOCALLY
                    );

                    break;

                default:
                    throw new UnexpectedConnectionStateException(
                            "connectionId: "+connectionId + " - currentConnectionState: "+currentConnectionState,
                            "Unexpected contact state for denying."
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
                    cantGetConnectionStateException
            );
            throw new CantDenyActorConnectionRequestException(
                    cantGetConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to get the connection state."
            );
        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException networkServiceException ) {

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    networkServiceException
            );
            throw new CantDenyActorConnectionRequestException(
                    networkServiceException,
                    "connectionId: "+connectionId,
                    "Error trying to deny the connection through the network service."
            );
        } catch (final CantChangeActorConnectionStateException cantChangeActorConnectionStateException ) {

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantChangeActorConnectionStateException
            );
            throw new CantDenyActorConnectionRequestException(
                    cantChangeActorConnectionStateException,
                    "connectionId: "+connectionId,
                    "Error trying to change the actor connection state."
            );
        } catch (final Exception exception) {

            errorManager.reportUnexpectedPluginException(
                    pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception
            );
            throw new CantDenyActorConnectionRequestException(
                    exception,
                    "connectionId: "+connectionId,
                    "Unhandled error."
            );
        }

    }

    @Override
    public void cancelConnection(final UUID connectionId) throws CantCancelActorConnectionRequestException,
                                                                 ActorConnectionNotFoundException         ,
                                                                 UnexpectedConnectionStateException       {

    }

    @Override
    public void acceptConnection(final UUID connectionId) throws CantAcceptActorConnectionRequestException,
                                                                 ActorConnectionNotFoundException         ,
            UnexpectedConnectionStateException {

    }

}
