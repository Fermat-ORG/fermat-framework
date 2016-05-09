package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesExecutorAgent</code>
 * haves all the necessary business logic to execute all required actions.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */

//DEPRECATE
public final class CryptoAddressesExecutorAgent extends FermatAgent {

    // Represent the sleep time for the cycles of receive and send in this agent, with both cycles send and receive 15000 millis.
  /*  private static final long SLEEP_TIME = 7500;
    private final Runnable agentTask;
    private ExecutorService executorService;

    // network services registered
    private Map<String, String> poolConnectionsWaitingForResponse;

   // private final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot;
    private final CryptoAddressesNetworkServiceDao        dao                                    ;

    private Future<?> future;

    public CryptoAddressesExecutorAgent(final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot,
                                        final CryptoAddressesNetworkServiceDao        dao                                    ) {

       // this.cryptoAddressesNetworkServicePluginRoot      = cryptoAddressesNetworkServicePluginRoot;
        this.dao                                          = dao                                    ;

        this.status                                       = AgentStatus.CREATED                    ;

        this.poolConnectionsWaitingForResponse = new HashMap<>();
        executorService = Executors.newSingleThreadExecutor();

//        Create a thread to send the messages
        this.agentTask = new Runnable() {
            @Override
            public void run() {
                while (isRunning()) {
                    sendCycle();

                    receiveCycle();
                }
            }
        };
    }

    public final void start() throws CantStartAgentException {

        try {
            if(future!=null){
                future.cancel(true);
            }

            future = executorService.submit(agentTask);
            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    @Override
    public void pause() {
        this.status = AgentStatus.PAUSED;
        future.cancel(true);

    }

    @Override
    public void resume() {
        this.status = AgentStatus.STARTED;
        if(future!=null){
            future.cancel(true);
        }
        future = executorService.submit(agentTask);
    }

    @Override
    public void stop() {
        future.cancel(true);
        this.status = AgentStatus.PAUSED;

    }

    public void stopExecutor(){
        executorService.shutdownNow();
    }

    private void sendCycle() {

        try {

            if (cryptoAddressesNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null){

                if (!cryptoAddressesNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()){
                    //System.out.println("CryptoAddressesExecutorAgent - sendCycle() no connection available ... ");
                    return;
                }else {

                    // function to process and send the right message to the counterparts.
                    processSend();

                    //Sleep for a while
                    Thread.sleep(SLEEP_TIME);

                }
            }

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
                            confirmRequestAcceptance(aer.getRequestId());
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
                            confirmRequestDeny(aer.getRequestId());
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

                    case RECEIVED:

                        System.out.println("********* Crypto Addresses: Executor Agent -> Sending RECEIVED. "+aer);

                        if (sendMessageToActor(
                                buildJsonReceivedMessage(aer),
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
                CantConfirmAddressExchangeRequestException    |
                PendingRequestNotFoundException               e) {

            reportUnexpectedError(e);
        }
    }

    private void receiveCycle() {

        try {

            if (cryptoAddressesNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null){

                if (!cryptoAddressesNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()){
                    //System.out.println("CryptoAddressesExecutorAgent - receiveCycle() no connection available ... ");
                    return;
                }else {

                    // function to process and send the right message to the counterparts.
                    processReceive();

                    //Sleep for a while
                    Thread.sleep(SLEEP_TIME);

                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            reportUnexpectedError(FermatException.wrapException(e));
        } catch(Exception e) {

            reportUnexpectedError(e);
        }

    }

    public void processReceive(){

        try {

            // if there is pending actions i raise a crypto address news event.
//            if(dao.isPendingRequestByProtocolStateAndNotReadAndReceived(ProtocolState.PENDING_ACTION)){
//                FermatEvent eventToRaise = eventManager.getNewEvent(EventType.CRYPTO_ADDRESSES_NEWS);
//                eventToRaise.setSource(cryptoAddressesNetworkServicePluginRoot.getEventSource());
//                eventManager.raiseEvent(eventToRaise);
//                System.out.println("CRYPTO ADDRESS NEWS");
//            }

            //TODO:aca dispara este evento que va a tratar de actualizar el address del contacto cuando en realidad solo tiene que generarla
            //hay que separar los evento para que el wallet contact escuche otro evento de actualizar el address
            List<CryptoAddressRequest> list = dao.listPendingRequestsByProtocolState(ProtocolState.PENDING_ACTION);
            for(CryptoAddressRequest cryptoAddressRequest : list){
                if(!cryptoAddressRequest.isReadMark() ) {
                    if (cryptoAddressRequest.getMessageType().equals(AddressesConstants.INCOMING_MESSAGE)) {
                        System.out.println("CRYPTO ADDRESS NEWS - INCOMING MESSAGE");
                        FermatEvent eventToRaise = cryptoAddressesNetworkServicePluginRoot.getEventManager().getNewEvent(EventType.CRYPTO_ADDRESSES_NEWS);
                        eventToRaise.setSource(cryptoAddressesNetworkServicePluginRoot.getEventSource());
                        cryptoAddressesNetworkServicePluginRoot.getEventManager().raiseEvent(eventToRaise);
                    }

                }
            }

            List<CryptoAddressRequest> list1 = dao.listPendingRequestsByProtocolState(ProtocolState.WAITING_RESPONSE);
            for(CryptoAddressRequest cryptoAddressRequest : list1){
                if(!cryptoAddressRequest.isReadMark()){
                    if(cryptoAddressRequest.getMessageType().equals(AddressesConstants.OUTGOING_MESSAGE)){
                         FermatEvent eventToRaise = cryptoAddressesNetworkServicePluginRoot.getEventManager().getNewEvent(EventType.CRYPTO_ADDRESSES_NEWS);
                        eventToRaise.setSource(cryptoAddressesNetworkServicePluginRoot.getEventSource());
                        cryptoAddressesNetworkServicePluginRoot.getEventManager().raiseEvent(eventToRaise);
                        System.out.println("CRYPTO ADDRESS NEWS PROTOCOL DONE");

                    }
                }

            }


        } catch(CantListPendingCryptoAddressRequestsException e) {

            reportUnexpectedError(e);
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

                if (cryptoAddressesNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorPublicKey) == null) {

                    PlatformComponentProfile applicantParticipant = cryptoAddressesNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                            .constructBasicPlatformComponentProfileFactory(
                                    identityPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(identityType));
                    PlatformComponentProfile remoteParticipant = cryptoAddressesNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
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
            System.out.println("mensaje enviado");

            return true;
        }
        return false;
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(final Actors type) throws InvalidParameterException {

        switch (type) {

            case INTRA_USER            : return PlatformComponentType.ACTOR_INTRA_USER          ;
            case CCM_INTRA_WALLET_USER : return PlatformComponentType.ACTOR_INTRA_USER          ;
            case CCP_INTRA_WALLET_USER : return PlatformComponentType.ACTOR_INTRA_USER          ;
            case DAP_ASSET_ISSUER      : return PlatformComponentType.ACTOR_ASSET_ISSUER        ;
            case DAP_ASSET_USER        : return PlatformComponentType.ACTOR_ASSET_USER          ;
            case DAP_ASSET_REDEEM_POINT: return PlatformComponentType.ACTOR_ASSET_REDEEM_POINT  ;

            default: throw new InvalidParameterException(
                    " actor type: "+type.name()+"  type-code: "+type.getCode(),
                    " type of actor not expected."
            );
        }
    }

    private String buildJsonAcceptMessage(final CryptoAddressRequest aer) {

        return new AcceptMessage(
                aer.getRequestId(),
                aer.getCryptoAddress(),
                aer.getIdentityPublicKeyResponding(),
                aer.getIdentityPublicKeyRequesting()
        ).toJson();
    }

    private String buildJsonDenyMessage(final CryptoAddressRequest aer) {

        return new DenyMessage(
                aer.getRequestId(),
                "Denied by Incompatibility",
                aer.getIdentityPublicKeyResponding(),
                aer.getIdentityPublicKeyRequesting()
        ).toJson();
    }

    private String buildJsonReceivedMessage(final CryptoAddressRequest aer) {

        return new ReceivedMessage(
                aer.getRequestId(),
                aer.getIdentityPublicKeyResponding(),
                aer.getIdentityPublicKeyRequesting()
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
                aer.getBlockchainNetworkType(),
                aer.getWalletPublicKey()
        ).toJson();
    }

    private void toPendingAction(final UUID requestId) throws CantChangeProtocolStateException,
            PendingRequestNotFoundException {

        dao.changeProtocolState(requestId, ProtocolState.PENDING_ACTION);
    }

    private void toWaitingResponse(final UUID requestId) throws CantChangeProtocolStateException,
            PendingRequestNotFoundException {

        dao.changeProtocolState(requestId, ProtocolState.WAITING_RESPONSE);
    }

    private void confirmRequestAcceptance(final UUID requestId) throws CantConfirmAddressExchangeRequestException,
            PendingRequestNotFoundException, CantChangeProtocolStateException {

        dao.changeProtocolState(requestId, ProtocolState.PENDING_ACTION);
        dao.changeActionState(requestId, RequestAction.ACCEPT);
    }

    private void confirmRequestDeny(final UUID requestId) throws CantConfirmAddressExchangeRequestException,
            PendingRequestNotFoundException, CantChangeProtocolStateException {

        dao.changeProtocolState(requestId, ProtocolState.PENDING_ACTION);
        dao.changeActionState(requestId, RequestAction.DENY);
    }

    private void reportUnexpectedError(final Exception e) {
        cryptoAddressesNetworkServicePluginRoot.getErrorManager().reportUnexpectedPluginException(cryptoAddressesNetworkServicePluginRoot.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    public void connectionFailure(final String destinationPublicKey){
        this.poolConnectionsWaitingForResponse.remove(destinationPublicKey);
    }

    public boolean isConnectionOpen(String destinationPublicKey) {
        return poolConnectionsWaitingForResponse.containsKey(destinationPublicKey);
    }

    public Map<String, String> getPoolConnectionsWaitingForResponse() {
        return poolConnectionsWaitingForResponse;
    }*/
}
