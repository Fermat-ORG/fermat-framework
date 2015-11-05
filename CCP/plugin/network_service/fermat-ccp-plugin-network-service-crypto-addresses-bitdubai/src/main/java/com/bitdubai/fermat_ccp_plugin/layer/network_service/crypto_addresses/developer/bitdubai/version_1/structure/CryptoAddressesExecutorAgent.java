package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.ProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.CryptoAddressesNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.AcceptMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.DenyMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesExecutorAgent</code>
 * haves all the necessary business logic to execute all required actions.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public final class CryptoAddressesExecutorAgent extends FermatAgent {

    // Represent the sleep time for the cycles of receive and send in this agent, with both cycles send and receive 15000 millis.
    private static final long SLEEP_TIME = 7500;

    // Represent the receive and send cycles for this agent.
    private Thread agentThread;

    // network services registered
    private Map<String, String> poolConnectionsWaitingForResponse;

    private final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot;
    private final ErrorManager                            errorManager                           ;
    private final EventManager                            eventManager                           ;
    private final CryptoAddressesNetworkServiceDao        dao                                    ;
    private final WsCommunicationsCloudClientManager      wsCommunicationsCloudClientManager     ;

    public CryptoAddressesExecutorAgent(final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot,
                                        final ErrorManager                            errorManager                           ,
                                        final EventManager                            eventManager                           ,
                                        final CryptoAddressesNetworkServiceDao        dao                                    ,
                                        final WsCommunicationsCloudClientManager      wsCommunicationsCloudClientManager     ) {

        this.cryptoAddressesNetworkServicePluginRoot      = cryptoAddressesNetworkServicePluginRoot;
        this.errorManager                                 = errorManager                           ;
        this.eventManager                                 = eventManager                           ;
        this.dao                                          = dao                                    ;
        this.wsCommunicationsCloudClientManager           = wsCommunicationsCloudClientManager     ;

        this.status                                       = AgentStatus.CREATED                    ;

        this.poolConnectionsWaitingForResponse = new HashMap<>();

        //Create a thread to send the messages
        this.agentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning()) {
                    sendCycle();
                    receiveCycle();
                }
            }
        });
    }

    public final void start() throws CantStartAgentException {

        try {

            agentThread.start();

            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    @Override
    public void pause() {
        agentThread.interrupt();
        super.pause();
    }

    @Override
    public void resume() {
        agentThread.start();
        super.resume();
    }

    @Override
    public void stop() {
        agentThread.interrupt();
        super.stop();
    }

    private void sendCycle() {

        try {

            if(cryptoAddressesNetworkServicePluginRoot.isRegister()) {

                // function to process and send the right message to the counterparts.
                processSend();
            }

            //Sleep for a time
            Thread.sleep(SLEEP_TIME);

        } catch (InterruptedException e) {

            reportUnexpectedError(e);
        } catch(Exception e) {

            reportUnexpectedError(FermatException.wrapException(e));
        }

    }

    private void processSend() {
        try {

            List<CryptoAddressRequest> cryptoAddressRequestList = dao.listPendingRequestsByProtocolState(
                    ProtocolState.PROCESSING_SEND
            );

            for(CryptoAddressRequest aer : cryptoAddressRequestList) {

                switch (aer.getAction()) {

                    case ACCEPT:

                        System.out.println("********* Crypto Addresses: Executor Agent -> Sending ACCEPTANCE. "+aer);

                        if (sendMessageToActor(
                                buildJsonAcceptMessage(aer),
                                aer.getIdentityPublicKeyResponding(),
                                aer.getIdentityTypeResponding(),
                                aer.getIdentityPublicKeyRequesting(),
                                aer.getIdentityTypeRequesting()
                        )) {
                            confirmRequest(aer.getRequestId());
                        }

                        break;

                    case DENY:

                        System.out.println("********* Crypto Addresses: Executor Agent -> Sending DENIAL. "+aer);

                        if (sendMessageToActor(
                                buildJsonDenyMessage(aer),
                                aer.getIdentityPublicKeyResponding(),
                                aer.getIdentityTypeResponding(),
                                aer.getIdentityPublicKeyRequesting(),
                                aer.getIdentityTypeRequesting()
                        )) {
                            confirmRequest(aer.getRequestId());
                        }

                        break;

                    case REQUEST:

                        System.out.println("********* Crypto Addresses: Executor Agent -> Sending REQUEST. "+aer);

                        if (sendMessageToActor(
                                buildJsonRequestMessage(aer),
                                aer.getIdentityPublicKeyRequesting(),
                                aer.getIdentityTypeRequesting(),
                                aer.getIdentityPublicKeyResponding(),
                                aer.getIdentityTypeResponding()
                        )) {
                            toWaitingResponse(aer.getRequestId());
                        }

                        break;
                }
            }

        } catch(CantListPendingCryptoAddressRequestsException |
                CantChangeProtocolStateException              |
                PendingRequestNotFoundException               |
                CantConfirmAddressExchangeRequestException    e) {

            reportUnexpectedError(e);
        }
    }

    private void receiveCycle() {

        try {

            if(cryptoAddressesNetworkServicePluginRoot.isRegister()) {

                // function to process and send the right message to the counterparts.
                processReceive();
            }

            //Sleep for a while
            Thread.sleep(SLEEP_TIME);

        } catch (InterruptedException e) {

            reportUnexpectedError(FermatException.wrapException(e));
        } catch(Exception e) {

            reportUnexpectedError(e);
        }

    }

    public void processReceive(){

        try {

            // if there is pending actions i raise a crypto address news event.
            if(dao.isPendingRequestByProtocolState(ProtocolState.PENDING_ACTION)) {
                System.out.println("************* Crypto Address -> Pending Action detected!");
                FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_ADDRESSES_NEWS);
                eventToRaise.setSource(cryptoAddressesNetworkServicePluginRoot.getEventSource());
                eventManager.raiseEvent(eventToRaise);
            }

        } catch(CantListPendingCryptoAddressRequestsException e) {

            reportUnexpectedError(e);
        }
    }

    private boolean sendMessageToActor(final String jsonMessage      ,
                                       final String identityPublicKey,
                                       final Actors identityType     ,
                                       final String actorPublicKey   ,
                                       final Actors actorType        ) {

        try {

            if (!poolConnectionsWaitingForResponse.containsKey(actorPublicKey)) {

                if (cryptoAddressesNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorPublicKey) == null) {

                    if (wsCommunicationsCloudClientManager != null) {

                        if (cryptoAddressesNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot() != null) {

                            PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
                                    .constructBasicPlatformComponentProfileFactory(
                                            identityPublicKey,
                                            NetworkServiceType.UNDEFINED,
                                            platformComponentTypeSelectorByActorType(identityType));
                            PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
                                    .constructBasicPlatformComponentProfileFactory(
                                            actorPublicKey,
                                            NetworkServiceType.UNDEFINED,
                                            platformComponentTypeSelectorByActorType(actorType));

                            cryptoAddressesNetworkServicePluginRoot.getNetworkServiceConnectionManager().connectTo(
                                    applicantParticipant,
                                    cryptoAddressesNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot(),
                                    remoteParticipant
                            );

                            // i put the actor in the pool of connections waiting for response-
                            poolConnectionsWaitingForResponse.put(actorPublicKey, actorPublicKey);
                        }

                    }

                    return false;

                } else {

                    return sendMessage(identityPublicKey, actorPublicKey, jsonMessage);

                }
            } else {

                return sendMessage(identityPublicKey, actorPublicKey, jsonMessage);
            }


        } catch (Exception z) {

            reportUnexpectedError(FermatException.wrapException(z));
            return false;
        }
    }

    private boolean sendMessage(final String identityPublicKey,
                                final String actorPublicKey   ,
                                final String jsonMessage      ) {

        NetworkServiceLocal communicationNetworkServiceLocal = cryptoAddressesNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorPublicKey);

        if (communicationNetworkServiceLocal != null) {

            communicationNetworkServiceLocal.sendMessage(
                    identityPublicKey,
                    actorPublicKey,
                    jsonMessage
            );

            poolConnectionsWaitingForResponse.remove(actorPublicKey);

            return true;
        }

        return false;
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(final Actors type) throws InvalidParameterException {

        switch (type) {

            case INTRA_USER  : return PlatformComponentType.ACTOR_INTRA_USER  ;
            case CCM_INTRA_WALLET_USER: return PlatformComponentType.ACTOR_INTRA_USER  ;
            case CCP_INTRA_WALLET_USER  : return PlatformComponentType.ACTOR_INTRA_USER  ;
            case DAP_ASSET_ISSUER: return PlatformComponentType.ACTOR_ASSET_ISSUER;
            case DAP_ASSET_USER  : return PlatformComponentType.ACTOR_ASSET_USER  ;

            default: throw new InvalidParameterException(
                  " actor type: "+type.name()+"  type-code: "+type.getCode(),
                  " type of actor not expected."
            );
        }
    }

    private String buildJsonAcceptMessage(final CryptoAddressRequest aer) {

        return new AcceptMessage(
                aer.getRequestId(),
                aer.getCryptoAddress()
        ).toJson();
    }

    private String buildJsonDenyMessage(final CryptoAddressRequest aer) {

        return new DenyMessage(
                aer.getRequestId(),
                "Denied by Incompatibility"
        ).toJson();
    }

    private String buildJsonRequestMessage(final CryptoAddressRequest aer) {

        return new RequestMessage(
                aer.getRequestId(),
                aer.getCryptoCurrency(),
                aer.getIdentityTypeRequesting(),
                aer.getIdentityTypeResponding(),
                aer.getIdentityPublicKeyRequesting(),
                aer.getIdentityPublicKeyResponding(),
                aer.getCryptoAddressDealer(),
                aer.getBlockchainNetworkType()
        ).toJson();
    }

    private void toWaitingResponse(final UUID requestId) throws CantChangeProtocolStateException,
                                                                PendingRequestNotFoundException {

        dao.changeProtocolState(requestId, ProtocolState.WAITING_RESPONSE);
    }

    private void confirmRequest(final UUID requestId) throws CantConfirmAddressExchangeRequestException,
                                                             PendingRequestNotFoundException           {

        dao.confirmAddressExchangeRequest(requestId);
    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(cryptoAddressesNetworkServicePluginRoot.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    public void connectionFailure(final String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }

}
