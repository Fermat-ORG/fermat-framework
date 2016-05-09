package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAnswerQuotesRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantConfirmException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingQuotesRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.QuotesRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionInformation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionRequest;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerQuote;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmQuotesRequestException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceManager</code>
 * is the manager of the plug-in crypto broker actor network service of the cbp platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoBrokerActorNetworkServiceManager implements CryptoBrokerManager {

    private final CommunicationsClientConnection            communicationsClientConnection    ;
    private final CryptoBrokerActorNetworkServiceDao        cryptoBrokerActorNetworkServiceDao;
    private final CryptoBrokerActorNetworkServicePluginRoot pluginRoot                        ;
    private final ErrorManager                              errorManager                      ;
    private final PluginVersionReference                    pluginVersionReference            ;

    /**
     * Executor
     */
    ExecutorService executorService;

    private PlatformComponentProfile platformComponentProfile;

    public CryptoBrokerActorNetworkServiceManager(final CommunicationsClientConnection            communicationsClientConnection    ,
                                                  final CryptoBrokerActorNetworkServiceDao        cryptoBrokerActorNetworkServiceDao,
                                                  final CryptoBrokerActorNetworkServicePluginRoot pluginRoot                        ,
                                                  final ErrorManager                              errorManager                      ,
                                                  final PluginVersionReference                    pluginVersionReference            ) {

        this.communicationsClientConnection     = communicationsClientConnection    ;
        this.cryptoBrokerActorNetworkServiceDao = cryptoBrokerActorNetworkServiceDao;
        this.pluginRoot                         = pluginRoot                        ;
        this.errorManager                       = errorManager                      ;
        this.pluginVersionReference             = pluginVersionReference            ;
        this.executorService                    = Executors.newFixedThreadPool(3)   ;
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
                        (cryptoBroker.getAlias()),
                        (cryptoBroker.getAlias().toLowerCase() + "_" + platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
                        imageString
                );

                communicationsClientConnection.registerComponentForCommunication(platformComponentProfile.getNetworkServiceType(), actorPlatformComponentProfile);

                if (cryptoBrokersToExpose != null && cryptoBrokersToExpose.containsKey(cryptoBroker.getPublicKey()))
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

    @Override
    public void updateIdentity(CryptoBrokerExposingData actor) throws CantExposeIdentityException {
        try {
            if (isRegistered()) {

                final String imageString = Base64.encodeToString(actor.getImage(), Base64.DEFAULT);


                final PlatformComponentProfile platformComponentProfile = communicationsClientConnection.constructPlatformComponentProfileFactory(
                        actor.getPublicKey(),
                        (actor.getAlias()),
                        (actor.getAlias().toLowerCase() + "_" + this.platformComponentProfile.getName().replace(" ", "_")),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_CRYPTO_BROKER,
                        imageString);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            communicationsClientConnection.updateRegisterActorProfile(platformComponentProfile.getNetworkServiceType(), platformComponentProfile);
                        } catch (CantRegisterComponentException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        }catch (Exception e){

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

            final ProtocolState           state  = ProtocolState          .PROCESSING_SEND;
            final RequestType             type   = RequestType            .SENT           ;
            final ConnectionRequestAction action = ConnectionRequestAction.REQUEST        ;

            cryptoBrokerActorNetworkServiceDao.createConnectionRequest(
                    brokerInformation,
                    state            ,
                    type             ,
                    action
            );

            sendMessage(
                    buildJsonRequestMessage(brokerInformation),
                    brokerInformation.getSenderPublicKey(),
                    brokerInformation.getSenderActorType(),
                    brokerInformation.getDestinationPublicKey(),
                    Actors.CBP_CRYPTO_BROKER
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
     */
    @Override
    public final void disconnect(final UUID requestId) throws CantDisconnectException            ,
                                                              ConnectionRequestNotFoundException {

        try {

            final ProtocolState state = ProtocolState.PROCESSING_SEND;

            cryptoBrokerActorNetworkServiceDao.disconnectConnection(
                    requestId,
                    state
            );

            CryptoBrokerConnectionRequest cbcr = cryptoBrokerActorNetworkServiceDao.getConnectionRequest(requestId);

            if (cbcr.getRequestType() == RequestType.RECEIVED) {
                sendMessage(
                        buildJsonInformationMessage(cbcr),
                        cbcr.getDestinationPublicKey(),
                        Actors.CBP_CRYPTO_BROKER,
                        cbcr.getSenderPublicKey(),
                        cbcr.getSenderActorType()
                );
            } else {
                sendMessage(
                        buildJsonInformationMessage(cbcr),
                        cbcr.getSenderPublicKey(),
                        cbcr.getSenderActorType(),
                        cbcr.getDestinationPublicKey(),
                        Actors.CBP_CRYPTO_BROKER
                );
            }

        } catch (final CantDisconnectException | ConnectionRequestNotFoundException e){

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

            cryptoBrokerActorNetworkServiceDao.denyConnection(
                    requestId,
                    protocolState
            );

            CryptoBrokerConnectionRequest cbcr = cryptoBrokerActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(cbcr),
                    cbcr.getDestinationPublicKey(),
                    Actors.CBP_CRYPTO_BROKER,
                    cbcr.getSenderPublicKey(),
                    cbcr.getSenderActorType()
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
            ConnectionRequestNotFoundException  {

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

            cryptoBrokerActorNetworkServiceDao.acceptConnection(
                    requestId,
                    protocolState
            );

            CryptoBrokerConnectionRequest cbcr = cryptoBrokerActorNetworkServiceDao.getConnectionRequest(requestId);

            sendMessage(
                    buildJsonInformationMessage(cbcr),
                    cbcr.getDestinationPublicKey(),
                    Actors.CBP_CRYPTO_BROKER,
                    cbcr.getSenderPublicKey(),
                    cbcr.getSenderActorType()
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
    public final List<CryptoBrokerConnectionRequest> listPendingConnectionNews(Actors actorType) throws CantListPendingConnectionRequestsException {

        try {

            return cryptoBrokerActorNetworkServiceDao.listPendingConnectionNews(actorType);

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

            return cryptoBrokerActorNetworkServiceDao.listPendingConnectionUpdates();

        } catch (final CantListPendingConnectionRequestsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingConnectionRequestsException(e, null, "Unhandled Exception.");
        }
    }


    @Override
    public CryptoBrokerExtraData<CryptoBrokerQuote> requestQuotes(final String requesterPublicKey   ,
                                                                  final Actors requesterActorType   ,
                                                                  final String cryptoBrokerPublicKey) throws CantRequestQuotesException {

        try {

            final UUID newId = UUID.randomUUID();

            final ProtocolState           state  = ProtocolState          .PROCESSING_SEND;
            final RequestType             type   = RequestType            .SENT           ;

            CryptoBrokerActorNetworkServiceQuotesRequest quotesRequest = cryptoBrokerActorNetworkServiceDao.createQuotesRequest(
                    newId,
                    requesterPublicKey,
                    requesterActorType,
                    cryptoBrokerPublicKey,
                    state,
                    type
            );

            sendMessage(
                    quotesRequest.toJson(),
                    quotesRequest.getRequesterPublicKey(),
                    quotesRequest.getRequesterActorType(),
                    quotesRequest.getCryptoBrokerPublicKey(),
                    Actors.CBP_CRYPTO_BROKER
            );

            return quotesRequest;

        } catch (final CantRequestQuotesException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestQuotesException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<CryptoBrokerExtraData<CryptoBrokerQuote>> listPendingQuotesRequests(final RequestType requestType) throws CantListPendingQuotesRequestsException {

        try {

            return cryptoBrokerActorNetworkServiceDao.listPendingQuotesRequests(ProtocolState.PENDING_LOCAL_ACTION, requestType);

        } catch (final CantListPendingQuotesRequestsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListPendingQuotesRequestsException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void answerQuotesRequest(final UUID                    requestId ,
                                    final long                    updateTime,
                                    final List<CryptoBrokerQuote> quotes    ) throws CantAnswerQuotesRequestException,
                                                                                     QuotesRequestNotFoundException  {

        try {

            cryptoBrokerActorNetworkServiceDao.answerQuotesRequest(
                    requestId,
                    updateTime,
                    quotes,
                    ProtocolState.PROCESSING_SEND
            );

            CryptoBrokerActorNetworkServiceQuotesRequest quotesRequest = cryptoBrokerActorNetworkServiceDao.getQuotesRequest(requestId);

            sendMessage(
                    quotesRequest.toJson(),
                    quotesRequest.getCryptoBrokerPublicKey(),
                    Actors.CBP_CRYPTO_BROKER,
                    quotesRequest.getRequesterPublicKey(),
                    quotesRequest.getRequesterActorType()
            );

        } catch (final QuotesRequestNotFoundException | CantAnswerQuotesRequestException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAnswerQuotesRequestException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void confirmQuotesRequest(UUID requestId) throws CantConfirmException, QuotesRequestNotFoundException {

        try {

            cryptoBrokerActorNetworkServiceDao.confirmQuotesRequest(requestId);

        } catch (final QuotesRequestNotFoundException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantConfirmQuotesRequestException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, "", "Error in DAO, trying to confirm the quotes request.");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void confirm(final UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException {

        try {

            cryptoBrokerActorNetworkServiceDao.confirmActorConnectionRequest(requestId);

        } catch (final ConnectionRequestNotFoundException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final CantConfirmConnectionRequestException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, "", "Error in DAO, trying to confirm the request.");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantConfirmException(e, null, "Unhandled Exception.");
        }
    }

    private void sendMessage(final String jsonMessage      ,
                             final String identityPublicKey,
                             final Actors identityType     ,
                             final String actorPublicKey   ,
                             final Actors actorType        ) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    pluginRoot.sendNewMessage(
                            pluginRoot.getProfileSenderToRequestConnection(
                                    identityPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(identityType)
                            ),
                            pluginRoot.getProfileDestinationToRequestConnection(
                                    actorPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(actorType)
                            ),
                            jsonMessage
                    );
                } catch (CantSendMessageException | InvalidParameterException e) {
                    errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(final Actors type) throws InvalidParameterException {

        switch (type) {

            case CBP_CRYPTO_BROKER    : return PlatformComponentType.ACTOR_CRYPTO_BROKER  ;
            case CBP_CRYPTO_CUSTOMER  : return PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;

            default: throw new InvalidParameterException(
                    " actor type: "+type.name()+"  type-code: "+type.getCode(),
                    " type of actor not expected."
            );
        }
    }

    private String buildJsonInformationMessage(final CryptoBrokerConnectionRequest aer) {

        return new InformationMessage(
                aer.getRequestId(),
                aer.getRequestAction()
        ).toJson();
    }

    private String buildJsonRequestMessage(final CryptoBrokerConnectionInformation aer) {

        return new RequestMessage(
                aer.getConnectionId(),
                aer.getSenderPublicKey(),
                aer.getSenderActorType(),
                aer.getSenderAlias(),
                aer.getSenderImage(),
                aer.getDestinationPublicKey(),
                ConnectionRequestAction.REQUEST,
                aer.getSendingTime()
        ).toJson();
    }

}
