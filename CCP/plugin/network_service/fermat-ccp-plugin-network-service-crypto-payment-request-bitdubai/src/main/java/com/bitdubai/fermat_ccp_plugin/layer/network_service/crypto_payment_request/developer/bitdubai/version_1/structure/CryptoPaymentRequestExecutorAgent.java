package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestExecutorAgent</code>
 * haves all the necessary business logic to execute all required actions.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 */

//DEPRECADO
public class CryptoPaymentRequestExecutorAgent extends FermatAgent {

    // Represent the sleep time for the cycles of receive and send in this agent.
  /*  private static final long SLEEP_TIME = 7500;

    // Represent the receive and send cycles for this agent.
    private final Runnable agentTask= new Runnable() {
        @Override
        public void run() {
            while (isRunning()) {
                sendCycle();
                receiveCycle();
            }

        }
    };

    private ExecutorService executorService;
    private Future<?> future;

    // network services registered
    private Map<String, String> poolConnectionsWaitingForResponse;

    private final CryptoPaymentRequestNetworkServicePluginRoot cryptoPaymentRequestNetworkServicePluginRoot;
    private final CryptoPaymentRequestNetworkServiceDao        cryptoPaymentRequestNetworkServiceDao       ;
    private final PluginVersionReference                       pluginVersionReference                      ;

    public CryptoPaymentRequestExecutorAgent(final CryptoPaymentRequestNetworkServicePluginRoot cryptoPaymentRequestNetworkServicePluginRoot,
                                             final CryptoPaymentRequestNetworkServiceDao        cryptoPaymentRequestNetworkServiceDao       ,
                                             final PluginVersionReference                       pluginVersionReference                      ) {

        this.cryptoPaymentRequestNetworkServicePluginRoot = cryptoPaymentRequestNetworkServicePluginRoot;
        this.cryptoPaymentRequestNetworkServiceDao        = cryptoPaymentRequestNetworkServiceDao       ;
        this.pluginVersionReference                       = pluginVersionReference                      ;

        this.status                                       = AgentStatus.CREATED                         ;

        poolConnectionsWaitingForResponse = new HashMap<>();

        executorService = Executors.newSingleThreadExecutor();

    }

    public void start() throws CantStartAgentException {

        try {
            future = executorService.submit(agentTask);
            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    @Override
    public void pause() {
        future.cancel(true);
        status = AgentStatus.PAUSED;
    }

    @Override
    public void resume() {
        future = executorService.submit(agentTask);
        status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        future.cancel(true);
        this.status = AgentStatus.STOPPED;

    }

    public void stopExecutor(){
        executorService.shutdownNow();
    }

    public void sendCycle() {

        try {

            if (cryptoPaymentRequestNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null) {

                if (!cryptoPaymentRequestNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()) {
                    //System.out.println("CryptoPaymentRequestExecutorAgent - sendCycle() no connection available ... ");
                    return;
                } else {

                    // function to process and send the rigth message to the counterparts.
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

            List<CryptoPaymentRequest> cryptoPaymentRequestList = cryptoPaymentRequestNetworkServiceDao.listRequestsByProtocolState(
                    RequestProtocolState.PROCESSING_SEND
            );

            for(CryptoPaymentRequest cpr : cryptoPaymentRequestList) {
                switch (cpr.getAction()) {

                    case INFORM_APPROVAL:
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Approval. PROCESSING_SEND -> CONFIRM REQUEST.");
                        if (sendMessageToActor(
                                buildJsonInformationMessage(cpr),
                                cpr.getIdentityPublicKey(),
                                cpr.getIdentityType(),
                                cpr.getActorPublicKey(),
                                cpr.getActorType()
                        )) {
                            System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Approval. PROCESSING_SEND -> CONFIRM REQUEST -> OK.");
                        }
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Approval. PROCESSING_SEND -> CONFIRM REQUEST -> WAIT MORE.");
                        break;

                    case INFORM_DENIAL:
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Denial. PROCESSING_SEND -> CONFIRM REQUEST.");
                        if (sendMessageToActor(
                                buildJsonInformationMessage(cpr),
                                cpr.getIdentityPublicKey(),
                                cpr.getIdentityType(),
                                cpr.getActorPublicKey(),
                                cpr.getActorType()
                        )) {
                            System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Denial. PROCESSING_SEND -> CONFIRM REQUEST -> OK.");
                        }
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Denial. PROCESSING_SEND -> CONFIRM REQUEST -> WAIT MORE.");
                        break;

                    case INFORM_RECEPTION:
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Reception Inform. PROCESSING_SEND -> CONFIRM REQUEST.");
                        if (sendMessageToActor(
                                buildJsonInformationMessage(cpr),
                                cpr.getIdentityPublicKey(),
                                cpr.getIdentityType(),
                                cpr.getActorPublicKey(),
                                cpr.getActorType()
                        )) {

                            System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Reception Inform. PROCESSING_SEND -> CONFIRM REQUEST -> OK.");
                        }
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Reception Inform. PROCESSING_SEND -> CONFIRM REQUEST -> WAIT MORE.");
                        break;

                    case INFORM_REFUSAL:
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Refusal. PROCESSING_SEND -> CONFIRM REQUEST.");
                        if (sendMessageToActor(
                                buildJsonInformationMessage(cpr),
                                cpr.getIdentityPublicKey(),
                                cpr.getIdentityType(),
                                cpr.getActorPublicKey(),
                                cpr.getActorType()
                        )) {
                            System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Refusal. PROCESSING_SEND -> CONFIRM REQUEST -> OK.");
                        }
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Refusal. PROCESSING_SEND -> CONFIRM REQUEST -> WAIT MORE.");
                        break;

                    case REQUEST:

                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Request. PROCESSING_SEND -> WAITING_RESPONSE.");

                        if (sendMessageToActor(
                                buildJsonRequestMessage(cpr),
                                cpr.getIdentityPublicKey(),
                                cpr.getIdentityType(),
                                cpr.getActorPublicKey(),
                                cpr.getActorType()
                        )) {
                            toWaitingResponse(cpr.getRequestId());
                            System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Request. PROCESSING_SEND -> WAITING_RESPONSE -> OK.");
                        }
                        System.out.println("********** Crypto Payment Request NS -> Executor Agent -> Sending Request. PROCESSING_SEND -> WAITING_RESPONSE -> WAIT MORE.");

                        break;
                }
            }

        } catch(CantListRequestsException               |
                CantChangeRequestProtocolStateException |
                RequestNotFoundException                e) {

            reportUnexpectedError(e);
        }
    }

    public void receiveCycle() {

        try {

            if (cryptoPaymentRequestNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null) {

                if (!cryptoPaymentRequestNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()) {
                    //System.out.println("CryptoPaymentRequestExecutorAgent - sendCycle() no connection available ... ");
                    return;
                } else {

                    // function to process and send the rigth message to the counterparts.
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

            // if there is pending actions i raise a crypto address news event.
            if(cryptoPaymentRequestNetworkServiceDao.isPendingRequestByProtocolState(RequestProtocolState.PENDING_ACTION)) {
                System.out.println("************* Crypto Payment Request -> Pending Action detected!");
                FermatEvent eventToRaise = cryptoPaymentRequestNetworkServicePluginRoot.getEventManager().getNewEvent(EventType.CRYPTO_PAYMENT_REQUEST_NEWS);
                eventToRaise.setSource(cryptoPaymentRequestNetworkServicePluginRoot.getEventSource());
                cryptoPaymentRequestNetworkServicePluginRoot.getEventManager().raiseEvent(eventToRaise);
            }

        } catch(CantListRequestsException e) {

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

                if (cryptoPaymentRequestNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorPublicKey) == null) {


                        if (cryptoPaymentRequestNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot() != null) {

                            PlatformComponentProfile applicantParticipant = cryptoPaymentRequestNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                                    .constructBasicPlatformComponentProfileFactory(
                                            identityPublicKey,
                                            NetworkServiceType.UNDEFINED,
                                            platformComponentTypeSelectorByActorType(identityType));
                            PlatformComponentProfile remoteParticipant = cryptoPaymentRequestNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                                    .constructBasicPlatformComponentProfileFactory(
                                            actorPublicKey,
                                            NetworkServiceType.UNDEFINED,
                                            platformComponentTypeSelectorByActorType(actorType));

                            cryptoPaymentRequestNetworkServicePluginRoot.getNetworkServiceConnectionManager().connectTo(
                                    applicantParticipant,
                                    cryptoPaymentRequestNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot(),
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

        NetworkServiceLocal communicationNetworkServiceLocal = cryptoPaymentRequestNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorPublicKey);

        if (communicationNetworkServiceLocal != null) {

            communicationNetworkServiceLocal.sendMessage(
                    identityPublicKey,
                    actorPublicKey,
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

    private String buildJsonInformationMessage(CryptoPaymentRequest cpr) {

        return new InformationMessage(
                cpr.getRequestId(),
                cpr.getAction(),
                cpr.getIdentityPublicKey(),
                cpr.getActorPublicKey()
        ).toJson();
    }

    private String buildJsonRequestMessage(CryptoPaymentRequest cpr) {

        return new RequestMessage(
                cpr.getRequestId()        ,
                cpr.getIdentityPublicKey(),
                cpr.getIdentityType()     ,
                cpr.getActorPublicKey()   ,
                cpr.getActorType()        ,
                cpr.getDescription()      ,
                cpr.getCryptoAddress()    ,
                cpr.getAmount()           ,
                cpr.getStartTimeStamp()   ,
                cpr.getAction()           ,
                cpr.getNetworkType(),
                cpr.getReferenceWallet(),
                cpr.getIdentityPublicKey(),
                cpr.getActorPublicKey()

        ).toJson();
    }

    private void toWaitingResponse(final UUID requestId) throws CantChangeRequestProtocolStateException,
            RequestNotFoundException               {

        cryptoPaymentRequestNetworkServiceDao.changeProtocolState(
                requestId,
                RequestProtocolState.WAITING_RESPONSE
        );
    }




    private void reportUnexpectedError(Exception e) {
        cryptoPaymentRequestNetworkServicePluginRoot.getErrorManager().reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    public void connectionFailure(String remotePublicKey){
        this.poolConnectionsWaitingForResponse.remove(remotePublicKey);
    }

    public boolean isConnectionOpen(String remotePublicKey) {
        return poolConnectionsWaitingForResponse.containsKey(remotePublicKey);   }



    public Map<String, String> getPoolConnectionsWaitingForResponse() {
        return poolConnectionsWaitingForResponse;
    }
    */
}