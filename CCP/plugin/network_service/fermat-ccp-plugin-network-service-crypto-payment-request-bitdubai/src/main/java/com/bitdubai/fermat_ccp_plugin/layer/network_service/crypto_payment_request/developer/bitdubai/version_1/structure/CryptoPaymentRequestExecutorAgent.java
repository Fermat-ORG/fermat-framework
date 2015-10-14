package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestAction;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequest;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.CryptoPaymentRequestNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database.CryptoPaymentRequestNetworkServiceDao;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantChangeCryptoPaymentRequestProtocolStateException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantInitializeExecutorAgentException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.exceptions.CantListRequestsException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.structure.CryptoPaymentRequestExecutorAgent</code>
 * haves all the necessary business logic to execute all required actions.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 */
public class CryptoPaymentRequestExecutorAgent implements FermatAgent {

    private Thread        agentThread  ;
    private ExecutorAgent executorAgent;

    private ServiceStatus serviceStatus;

    private final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;
    private final ErrorManager                                 errorManager                                ;
    private final EventManager                                 eventManager                                ;
    private final PluginDatabaseSystem                         pluginDatabaseSystem                        ;
    private final UUID                                         pluginId                                    ;

    public CryptoPaymentRequestExecutorAgent(final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
                                             final ErrorManager                                 errorManager                                ,
                                             final EventManager                                 eventManager                                ,
                                             final PluginDatabaseSystem                         pluginDatabaseSystem                        ,
                                             final UUID                                         pluginId                                    ) {

        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.errorManager                                 = errorManager                                ;
        this.eventManager                                 = eventManager                                ;
        this.pluginDatabaseSystem                         = pluginDatabaseSystem                        ;
        this.pluginId                                     = pluginId                                    ;
        this.serviceStatus                                = ServiceStatus.CREATED                       ;
    }

    public void start() throws CantStartAgentException {

        this.executorAgent = new ExecutorAgent(
                communicationNetworkServiceConnectionManager,
                errorManager        ,
                eventManager        ,
                pluginDatabaseSystem,
                pluginId
        );

        try {

            this.executorAgent.initialize();
            this.agentThread = new Thread(this.executorAgent);
            this.agentThread.start();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    public boolean isRunning(){
        return this.executorAgent != null && this.executorAgent.isRunning();
    }

    public void stop()  {

        if(isRunning())
            this.executorAgent.stop();

        agentThread.interrupt();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public void pause() {
        // TODO MANAGE PAUSE METHOD.
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        // TODO MANAGE RESUME METHOD.
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    private static class ExecutorAgent implements Runnable  {

        private AtomicBoolean running = new AtomicBoolean(false);

        private static final int SLEEP_TIME = 5000;

        private final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;
        private final ErrorManager                                 errorManager                                ;
        private final EventManager                                 eventManager                                ;
        private final PluginDatabaseSystem                         pluginDatabaseSystem                        ;
        private final UUID                                         pluginId                                    ;

        private CryptoPaymentRequestNetworkServiceDao cryptoPaymentRequestNetworkServiceDao;

        public ExecutorAgent(final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
                             final ErrorManager                                 errorManager                                ,
                             final EventManager                                 eventManager                                ,
                             final PluginDatabaseSystem                         pluginDatabaseSystem                        ,
                             final UUID                                         pluginId                                    ) {

            this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
            this.errorManager                                 = errorManager                                ;
            this.eventManager                                 = eventManager                                ;
            this.pluginDatabaseSystem                         = pluginDatabaseSystem                        ;
            this.pluginId                                     = pluginId                                    ;
        }

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }

        private void initialize () throws CantInitializeExecutorAgentException {

            try {

                this.cryptoPaymentRequestNetworkServiceDao = new CryptoPaymentRequestNetworkServiceDao(
                        this.pluginDatabaseSystem,
                        this.pluginId
                );

                cryptoPaymentRequestNetworkServiceDao.initialize();
            } catch(CantInitializeCryptoPaymentRequestNetworkServiceDatabaseException e) {

                throw new CantInitializeExecutorAgentException(e, "", "Problem initializing Crypto Payment Request DAO from Executor Agent.");
            }

        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            running.set(true);

            /**
             * Infinite loop.
             */
            while (running.get()) {
                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    break;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        }

        private void doTheMainTask() {

            try {

                List<CryptoPaymentRequest> cryptoPaymentRequestList = cryptoPaymentRequestNetworkServiceDao.listRequestsByProtocolState(RequestProtocolState.PROCESSING);

                for(CryptoPaymentRequest cpr : cryptoPaymentRequestList) {

                    switch (cpr.getDirection()) {


                        // OUTGOING ACTIONS .-
                        case OUTGOING:
                            switch (cpr.getAction()) {

                                case INFORM_APPROVAL:

                                    sendMessageToActor(
                                            buildJsonInformationMessage(cpr),
                                            cpr.getActorPublicKey()
                                    );

                                    toWaitingResponse(cpr.getRequestId());
                                    break;

                                case INFORM_DENIAL:

                                    sendMessageToActor(
                                            buildJsonInformationMessage(cpr),
                                            cpr.getActorPublicKey()
                                    );

                                    toWaitingResponse(cpr.getRequestId());
                                    break;

                                case INFORM_RECEPTION:

                                    sendMessageToActor(
                                            buildJsonInformationMessage(cpr),
                                            cpr.getActorPublicKey()
                                    );

                                    toWaitingResponse(cpr.getRequestId());
                                    break;

                                case INFORM_REFUSAL:

                                    sendMessageToActor(
                                            buildJsonInformationMessage(cpr),
                                            cpr.getActorPublicKey()
                                    );

                                    toWaitingResponse(cpr.getRequestId());
                                    break;

                                case REQUEST:

                                    sendMessageToActor(
                                            buildJsonRequestMessage(cpr),
                                            cpr.getActorPublicKey()
                                    );

                                    toWaitingResponse(cpr.getRequestId());
                                    break;
                            }
                            break;


                        // INCOMING ACTIONS .-
                        case INCOMING:

                            switch (cpr.getAction()) {

                                case INFORM_APPROVAL:
                                    raiseEvent(EventType.CRYPTO_PAYMENT_REQUEST_APPROVED, cpr.getRequestId());
                                    toPendingAction(cpr.getRequestId());
                                    break;

                                case INFORM_DENIAL:
                                    raiseEvent(EventType.CRYPTO_PAYMENT_REQUEST_DENIED, cpr.getRequestId());
                                    toPendingAction(cpr.getRequestId());
                                    break;

                                case INFORM_RECEPTION:
                                    raiseEvent(EventType.CRYPTO_PAYMENT_REQUEST_CONFIRMED_RECEPTION, cpr.getRequestId());
                                    toPendingAction(cpr.getRequestId());
                                    break;

                                case INFORM_REFUSAL:
                                    raiseEvent(EventType.CRYPTO_PAYMENT_REQUEST_REFUSED, cpr.getRequestId());
                                    toPendingAction(cpr.getRequestId());
                                    break;

                                case REQUEST:
                                    raiseEvent(EventType.CRYPTO_PAYMENT_REQUEST_RECEIVED, cpr.getRequestId());
                                    toPendingAction(cpr.getRequestId());
                                    break;
                            }
                            break;
                    }
                }

            } catch(CantListRequestsException                            |
                    CantChangeCryptoPaymentRequestProtocolStateException |
                    RequestNotFoundException                             e) {

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch(Exception e) {

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCP_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            }
        }

        private void sendMessageToActor(String jsonMessage   ,
                                        String actorPublicKey) {

            CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(actorPublicKey);

            if(communicationNetworkServiceLocal != null)
                communicationNetworkServiceLocal.sendMessage(communicationNetworkServiceConnectionManager.getIdentity().getPublicKey(),null, jsonMessage);
        }

        private String buildJsonInformationMessage(CryptoPaymentRequest cpr) {

            return new InformationMessage(
                    cpr.getRequestId(),
                    cpr.getAction()
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
                    cpr.getNetworkType()
            ).toJson();
        }

        private void toPendingAction(UUID requestId) throws CantChangeCryptoPaymentRequestProtocolStateException,
                                                            RequestNotFoundException {

            cryptoPaymentRequestNetworkServiceDao.changeProtocolState(requestId, RequestProtocolState.PENDING_ACTION);
        }

        private void toWaitingResponse(UUID requestId) throws CantChangeCryptoPaymentRequestProtocolStateException,
                                                              RequestNotFoundException                            {

            cryptoPaymentRequestNetworkServiceDao.changeProtocolState(requestId, RequestProtocolState.WAITING_RESPONSE);
        }

        private void raiseEvent(final EventType eventType,
                                final UUID      requestId) {

            FermatEvent eventToRaise = eventManager.getNewEvent(eventType);
            ((CryptoPaymentRequestEvent) eventToRaise).setRequestId(requestId);
            eventToRaise.setSource(CryptoPaymentRequestNetworkServicePluginRoot.EVENT_SOURCE);
            eventManager.raiseEvent(eventToRaise);
        }

    }

    public void handleNewMessages(FermatMessage fermatMessage){

        try {

            Gson gson = new Gson();

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            switch (networkServiceMessage.getMessageType()) {
                case INFORMATION:
                    InformationMessage informationMessage = gson.fromJson(jsonMessage, InformationMessage.class);
                    System.out.println(informationMessage);
                    break;
                case REQUEST:
                    RequestMessage requestMessage = gson.fromJson(jsonMessage, RequestMessage.class);
                    System.out.println(requestMessage);
                    break;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void informReception(String actorPublicKey, UUID requestId) {

        CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(actorPublicKey);

        // TODO VER QUE ACTION PONER AQUÍ.
        InformationMessage informationMessage = new InformationMessage(requestId, RequestAction.INFORM_RECEPTION);

        communicationNetworkServiceLocal.sendMessage(actorPublicKey, null,informationMessage.toJson());
    }

}
