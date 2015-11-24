package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.UnexpectedProtocolStateException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionInformation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionRequest;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.ConnectionNewsDao;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceManager</code>
 * is the manager of the plug-in crypto broker actor network service of the cbp platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoBrokerActorNetworkServiceManager implements CryptoBrokerManager {

    private final CommunicationsClientConnection communicationsClientConnection;
    private final ConnectionNewsDao              connectionNewsDao             ;
    private final ErrorManager                   errorManager                  ;
    private final PluginVersionReference         pluginVersionReference        ;


    private PlatformComponentProfile platformComponentProfile;

    public CryptoBrokerActorNetworkServiceManager(final CommunicationsClientConnection communicationsClientConnection,
                                                  final ConnectionNewsDao              connectionNewsDao             ,
                                                  final ErrorManager                   errorManager                  ,
                                                  final PluginVersionReference         pluginVersionReference        ) {

        this.communicationsClientConnection = communicationsClientConnection;
        this.connectionNewsDao              = connectionNewsDao             ;
        this.errorManager                   = errorManager                  ;
        this.pluginVersionReference         = pluginVersionReference        ;
    }

    private ConcurrentHashMap<String, CryptoBrokerExposingData> cryptoBrokersToExpose;

    @Override
    public final void exposeIdentity(final CryptoBrokerExposingData cryptoBroker) throws CantExposeIdentityException {

        try {

            if (!isRegistered()) {

                addCryptoBrokerToExpose(cryptoBroker);

            } else {

                final String imageString = Base64.encodeToString(cryptoBroker.getImage(), Base64.DEFAULT);

                final PlatformComponentProfile actorPlatformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        cryptoBroker.getPublicKey(),
                        (cryptoBroker.getAlias().toLowerCase()),
                        (cryptoBroker.getAlias().toLowerCase() + "_" + platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
                        imageString
                );

                communicationsClientConnection.registerComponentForCommunication(platformComponentProfile.getNetworkServiceType(), actorPlatformComponentProfile);

                cryptoBrokersToExpose.remove(cryptoBroker.getPublicKey());
            }

        } catch (final CantRegisterComponentException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Problem trying to register an identity component.");

        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentityException(e, null, "Unhandled Exception.");
        }
    }

    private void addCryptoBrokerToExpose(final CryptoBrokerExposingData cryptoBrokerExposingData) {

        if (cryptoBrokersToExpose == null)
            cryptoBrokersToExpose = new ConcurrentHashMap<>();

        cryptoBrokersToExpose.putIfAbsent(cryptoBrokerExposingData.getPublicKey(), cryptoBrokerExposingData);
    }

    @Override
    public final void exposeIdentities(final Collection<CryptoBrokerExposingData> cryptoBrokerExposingDataList) throws CantExposeIdentitiesException {

        try {

            for (final CryptoBrokerExposingData cryptoBroker : cryptoBrokerExposingDataList)
                this.exposeIdentity(cryptoBroker);

        } catch (final CantExposeIdentityException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Problem trying to expose an identity.");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException(e, null, "Unhandled Exception.");
        }
    }

    private boolean isRegistered() {
        return platformComponentProfile != null;
    }

    public final void setPlatformComponentProfile(final PlatformComponentProfile platformComponentProfile) {

        this.platformComponentProfile = platformComponentProfile;

        if (platformComponentProfile != null && cryptoBrokersToExpose != null && !cryptoBrokersToExpose.isEmpty()) {

            try {

                this.exposeIdentities(cryptoBrokersToExpose.values());

            } catch (final CantExposeIdentitiesException e){

                errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }
    }

    @Override
    public final CryptoBrokerSearch getSearch() {
        return new CryptoBrokerActorNetworkServiceSearch(communicationsClientConnection, errorManager, pluginVersionReference);
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : REQUEST.
     * - Type          : SENT.
     */
    @Override
    public final void requestConnection(final CryptoBrokerConnectionInformation brokerInformation) throws CantRequestConnectionException {

        try {

            final UUID newId = UUID.randomUUID();

            final ProtocolState           state  = ProtocolState          .PROCESSING_SEND;
            final RequestType             type   = RequestType            .SENT           ;
            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST        ;

            connectionNewsDao.createConnectionRequest(
                    newId            ,
                    brokerInformation,
                    state            ,
                    type             ,
                    action
            );

        } catch (final CantRequestConnectionException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestConnectionException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_SEND.
     * - Action        : DISCONNECT.
     * - Type          : SENT.
     */
    @Override
    public final void disconnect(final String identityPublicKey,
                                 final Actors identityActorType,
                                 final String brokerPublicKey  ) throws CantDisconnectException {

        try {

            final UUID newId    = UUID.randomUUID();
            final long sentTime = System.currentTimeMillis();

            final ProtocolState           state  = ProtocolState          .PROCESSING_SEND;
            final RequestType             type   = RequestType            .SENT           ;
            final ConnectionRequestAction action = ConnectionRequestAction.DISCONNECT     ;

            connectionNewsDao.createDisconnectionRequest(
                    newId,
                    identityPublicKey,
                    identityActorType,
                    brokerPublicKey,
                    sentTime,
                    state,
                    type,
                    action
            );

        } catch (final CantDisconnectException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDisconnectException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : DENY.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public final void denyConnection(final UUID requestId) throws CantDenyConnectionRequestException ,
                                                                  ConnectionRequestNotFoundException {

        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            connectionNewsDao.denyConnection(
                    requestId,
                    protocolState
            );

        } catch (final CantDenyConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDenyConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : CANCEL.
     * - Protocol State: PROCESSING_SEND.
     *
     * We must to validate if the record is in PENDING_REMOTE_ACTION.
     */
    @Override
    public final void cancelConnection(final UUID requestId) throws CantCancelConnectionRequestException,
                                                                    ConnectionRequestNotFoundException  ,
                                                                    UnexpectedProtocolStateException    {

    }

    /**
     * I update the record with the new address an then, i indicate the ns agent the action that it must take:
     * - Action        : ACCEPT.
     * - Protocol State: PROCESSING_SEND.
     */
    @Override
    public final void acceptConnection(final UUID requestId) throws CantAcceptConnectionRequestException,
                                                                    ConnectionRequestNotFoundException  {

        try {

            final ProtocolState protocolState = ProtocolState.PROCESSING_SEND;

            connectionNewsDao.acceptConnection(
                    requestId,
                    protocolState
            );

        } catch (final CantAcceptConnectionRequestException | ConnectionRequestNotFoundException e){
            // ConnectionRequestNotFoundException - THIS SHOULD NOT HAPPEN.
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAcceptConnectionRequestException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return all the request news with a pending local action.
     * State : PENDING_LOCAL_ACTION.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    @Override
    public final List<CryptoBrokerConnectionRequest> listPendingConnectionNews() throws CantListPendingConnectionRequestsException {

        try {

            List<ConnectionRequestAction> actions = new ArrayList<>();

            actions.add(ConnectionRequestAction.CANCEL    );
            actions.add(ConnectionRequestAction.DISCONNECT);
            actions.add(ConnectionRequestAction.REQUEST   );

            return connectionNewsDao.listAllPendingRequests(actions);

        } catch (final CantListPendingConnectionRequestsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }

    /**
     * we'll return all the request updates with a pending local action.
     * State : PENDING_LOCAL_ACTION.
     *
     * @throws CantListPendingConnectionRequestsException  if something goes wrong.
     */
    @Override
    public final List<CryptoBrokerConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {

        try {

            List<ConnectionRequestAction> actions = new ArrayList<>();

            actions.add(ConnectionRequestAction.ACCEPT);
            actions.add(ConnectionRequestAction.DENY  );

            return connectionNewsDao.listAllPendingRequests(actions);

        } catch (final CantListPendingConnectionRequestsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }
}
