package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingQuotesRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.QuotesRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionRequest;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmQuotesRequestException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesExecutorAgent</code>
 * contains all the necessary business logic to execute all required actions.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public final class CryptoBrokerExecutorAgent extends FermatAgent {

    // Represent the sleep time for the cycles of receive and send in this agent, with both cycles send and receive 15000 millis.
    private static final long SLEEP_TIME = 7500;

    // Represent the receive and send cycles for this agent.
    private Thread agentThread;

    // network services registered
    private Map<String, String> poolConnectionsWaitingForResponse;

    private final CryptoBrokerActorNetworkServicePluginRoot cryptoBrokerActorNetworkServicePluginRoot;
    private final ErrorManager                              errorManager                             ;
    private final EventManager                              eventManager                             ;
    private final CryptoBrokerActorNetworkServiceDao        dao                                      ;

    public CryptoBrokerExecutorAgent(final CryptoBrokerActorNetworkServicePluginRoot cryptoBrokerActorNetworkServicePluginRoot,
                                     final ErrorManager                              errorManager                             ,
                                     final EventManager                              eventManager                             ,
                                     final CryptoBrokerActorNetworkServiceDao dao                                             ) {

        this.cryptoBrokerActorNetworkServicePluginRoot = cryptoBrokerActorNetworkServicePluginRoot;
        this.errorManager                              = errorManager                             ;
        this.eventManager                              = eventManager                             ;
        this.dao                                       = dao                                      ;

        this.status                                    = AgentStatus.CREATED                      ;

        this.poolConnectionsWaitingForResponse = new HashMap<>();

//        Create a thread to send the messages
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

        if (!isRunning()) {
            try {

                this.status = AgentStatus.STARTED;

                agentThread.start();

            } catch (Exception exception) {

                throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
            }
        }
    }

    @Override
    public void pause() {
        agentThread.interrupt();
        status = AgentStatus.PAUSED;
    }

    @Override
    public void resume() {
        agentThread.start();
        status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        agentThread.interrupt();
        //TODO: fijense esto
        status = AgentStatus.STOPPED;

    }

    private void sendCycle() {

        try {

            if (cryptoBrokerActorNetworkServicePluginRoot.getCommunicationsClientConnection() != null) {

                if (cryptoBrokerActorNetworkServicePluginRoot.getCommunicationsClientConnection().isConnected()) {

                    processSend();

                    //Sleep for a time
                    Thread.sleep(SLEEP_TIME);
                }
            }

        } catch (InterruptedException e) {

            reportUnexpectedError(FermatException.wrapException(e));
        } catch(Exception e) {
            status = AgentStatus.STOPPED;
            reportUnexpectedError(e);
        }

    }

    private void processSend() {

        try {

            List<CryptoBrokerConnectionRequest> cryptoAddressRequestList = dao.listAllRequestByProtocolState(
                    ProtocolState.PROCESSING_SEND
            );

            for(CryptoBrokerConnectionRequest cbcr : cryptoAddressRequestList) {

                switch (cbcr.getRequestAction()) {

                   case ACCEPT:

                        System.out.println("********* Crypto Broker: Executor Agent -> Sending ACCEPTANCE. "+cbcr);

                        if (sendMessageToActor(
                                buildJsonInformationMessage(cbcr),
                                cbcr.getDestinationPublicKey(),
                                Actors.CBP_CRYPTO_BROKER,
                                cbcr.getSenderPublicKey(),
                                cbcr.getSenderActorType()
                        )) {
                            confirmRequest(cbcr.getRequestId());
                        }

                        break;

                     case DENY:

                        System.out.println("********* Crypto Broker: Executor Agent -> Sending DENIAL. "+cbcr);

                        if (sendMessageToActor(
                                buildJsonInformationMessage(cbcr),
                                cbcr.getDestinationPublicKey(),
                                Actors.CBP_CRYPTO_BROKER,
                                cbcr.getSenderPublicKey(),
                                cbcr.getSenderActorType()
                        )) {
                             confirmRequest(cbcr.getRequestId());
                        }

                        break;

                    case DISCONNECT:

                        System.out.println("********* Crypto Broker: Executor Agent -> Sending DISCONNECTION. "+cbcr);

                        if (cbcr.getRequestType() == RequestType.RECEIVED) {
                            if (sendMessageToActor(
                                    buildJsonInformationMessage(cbcr),
                                    cbcr.getDestinationPublicKey(),
                                    Actors.CBP_CRYPTO_BROKER,
                                    cbcr.getSenderPublicKey(),
                                    cbcr.getSenderActorType()
                            )) {
                                confirmRequest(cbcr.getRequestId());
                            }
                        } else {
                            if (sendMessageToActor(
                                    buildJsonInformationMessage(cbcr),
                                    cbcr.getSenderPublicKey(),
                                    cbcr.getSenderActorType(),
                                    cbcr.getDestinationPublicKey(),
                                    Actors.CBP_CRYPTO_BROKER
                            )) {
                                confirmRequest(cbcr.getRequestId());
                            }
                        }

                        break;

                    case REQUEST:

                        System.out.println("********* Crypto Broker: Executor Agent -> Sending REQUEST. "+cbcr);

                        if (sendMessageToActor(
                                buildJsonRequestMessage(cbcr),
                                cbcr.getSenderPublicKey(),
                                cbcr.getSenderActorType(),
                                cbcr.getDestinationPublicKey(),
                                Actors.CBP_CRYPTO_BROKER
                        )) {
                            confirmRequest(cbcr.getRequestId());
                        }

                        break;
                }
            }

        } catch(CantListPendingConnectionRequestsException |
                CantConfirmConnectionRequestException |
                ConnectionRequestNotFoundException           e) {

            reportUnexpectedError(e);
        }

        try {

            List<CryptoBrokerActorNetworkServiceQuotesRequest>  quotesRequestList = dao.listPendingQuotesRequestsByProtocolState(ProtocolState.PROCESSING_SEND);

            for(CryptoBrokerActorNetworkServiceQuotesRequest cbqr : quotesRequestList) {

                switch (cbqr.getType()) {

                    case SENT:

                        System.out.println("********* Crypto Broker: Executor Agent -> Sending QUOTES REQUEST. "+cbqr);

                        if (sendMessageToActor(
                                cbqr.toJson(),
                                cbqr.getRequesterPublicKey(),
                                cbqr.getRequesterActorType(),
                                cbqr.getCryptoBrokerPublicKey(),
                                Actors.CBP_CRYPTO_BROKER
                        )) {
                            dao.confirmQuotesRequest(cbqr.getRequestId());
                        }

                        break;

                    case RECEIVED:

                        System.out.println("********* Crypto Broker: Executor Agent -> Sending QUOTES UPDATES. "+cbqr);

                        if (sendMessageToActor(
                                cbqr.toJson(),
                                cbqr.getCryptoBrokerPublicKey(),
                                Actors.CBP_CRYPTO_BROKER,
                                cbqr.getRequesterPublicKey(),
                                cbqr.getRequesterActorType()
                        )) {
                            dao.confirmQuotesRequest(cbqr.getRequestId());
                        }

                        break;
                }
            }

        } catch(CantListPendingQuotesRequestsException |
                CantConfirmQuotesRequestException      |
                QuotesRequestNotFoundException         e) {

            reportUnexpectedError(e);
        }
    }

    private void receiveCycle() {

        try {

            if (cryptoBrokerActorNetworkServicePluginRoot.getCommunicationsClientConnection() != null) {

                if (cryptoBrokerActorNetworkServicePluginRoot.getCommunicationsClientConnection().isConnected()) {

                    processReceive();

                    //Sleep for a time
                    Thread.sleep(SLEEP_TIME);
                }
            }

        } catch (InterruptedException e) {
            status = AgentStatus.STOPPED;
            reportUnexpectedError(FermatException.wrapException(e));
        } catch(Exception e) {

            reportUnexpectedError(e);
        }

    }

    public void processReceive(){

        try {

            if(dao.isPendingQuotesRequestsNews()) {
                FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_QUOTES_REQUEST_NEWS);
                eventToRaise.setSource(cryptoBrokerActorNetworkServicePluginRoot.eventSource);
                eventManager.raiseEvent(eventToRaise);
                System.out.println("CRYPTO BROKER QUOTES NEWS");
            }

            if(dao.isPendingQuotesRequestUpdates()) {
                FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_QUOTES_REQUEST_UPDATES);
                eventToRaise.setSource(cryptoBrokerActorNetworkServicePluginRoot.eventSource);
                eventManager.raiseEvent(eventToRaise);
                System.out.println("CRYPTO BROKER QUOTES UPDATES");
            }

            // if there is pending actions i raise a crypto address news event.
            if(dao.isPendingConnectionRequests()) {
                FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_CONNECTION_REQUEST_NEWS);
                eventToRaise.setSource(cryptoBrokerActorNetworkServicePluginRoot.eventSource);
                eventManager.raiseEvent(eventToRaise);
                System.out.println("CRYPTO BROKER CONNECTION NEWS");
            }

            if(dao.isPendingConnectionUpdates()) {
                FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES);
                eventToRaise.setSource(cryptoBrokerActorNetworkServicePluginRoot.eventSource);
                eventManager.raiseEvent(eventToRaise);
                System.out.println("CRYPTO BROKER CONNECTION UPDATES");
            }

        } catch(Exception e) {

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

                if (cryptoBrokerActorNetworkServicePluginRoot.getCommunicationNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorPublicKey) == null) {


                    if (cryptoBrokerActorNetworkServicePluginRoot.getNetworkServiceProfile() != null) {

                        PlatformComponentProfile applicantParticipant = cryptoBrokerActorNetworkServicePluginRoot.getCommunicationsClientConnection()
                                .constructBasicPlatformComponentProfileFactory(
                                        identityPublicKey,
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(identityType));
                        PlatformComponentProfile remoteParticipant = cryptoBrokerActorNetworkServicePluginRoot.getCommunicationsClientConnection()
                                .constructBasicPlatformComponentProfileFactory(
                                        actorPublicKey,
                                        NetworkServiceType.UNDEFINED,
                                        platformComponentTypeSelectorByActorType(actorType));

                        cryptoBrokerActorNetworkServicePluginRoot.getCommunicationNetworkServiceConnectionManager().connectTo(
                                applicantParticipant,
                                cryptoBrokerActorNetworkServicePluginRoot.getNetworkServiceProfile(),
                                remoteParticipant
                        );

                        // i put the actor in the pool of connections waiting for response-
                        poolConnectionsWaitingForResponse.put(actorPublicKey, actorPublicKey);
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

        CommunicationNetworkServiceLocal communicationNetworkServiceLocal = cryptoBrokerActorNetworkServicePluginRoot.getCommunicationNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorPublicKey);

        if (communicationNetworkServiceLocal != null) {

            communicationNetworkServiceLocal.sendMessage(
                    identityPublicKey,
                    jsonMessage
            );

            poolConnectionsWaitingForResponse.remove(actorPublicKey);

            return true;
        }
        poolConnectionsWaitingForResponse.remove(actorPublicKey);
        return false;
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(final Actors type) throws InvalidParameterException {

        switch (type) {

            case CBP_CRYPTO_BROKER    : return PlatformComponentType.ACTOR_CRYPTO_BROKER  ;
            case CBP_CRYPTO_CUSTOMER  : return PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;
            case INTRA_USER           : return PlatformComponentType.ACTOR_INTRA_USER     ;
            case CCM_INTRA_WALLET_USER: return PlatformComponentType.ACTOR_INTRA_USER     ;
            case CCP_INTRA_WALLET_USER: return PlatformComponentType.ACTOR_INTRA_USER     ;
            case DAP_ASSET_ISSUER     : return PlatformComponentType.ACTOR_ASSET_ISSUER   ;
            case DAP_ASSET_USER       : return PlatformComponentType.ACTOR_ASSET_USER     ;

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

    private String buildJsonRequestMessage(final CryptoBrokerConnectionRequest aer) {

        return new RequestMessage(
                aer.getRequestId(),
                aer.getSenderPublicKey(),
                aer.getSenderActorType(),
                aer.getSenderAlias(),
                aer.getSenderImage(),
                aer.getDestinationPublicKey(),
                aer.getRequestAction(),
                aer.getSentTime()
        ).toJson();
    }

    private void confirmRequest(final UUID requestId) throws CantConfirmConnectionRequestException,
                                                             ConnectionRequestNotFoundException   {

        dao.confirmActorConnectionRequest(requestId);
    }

    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(cryptoBrokerActorNetworkServicePluginRoot.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    public void connectionFailure(final String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }

}