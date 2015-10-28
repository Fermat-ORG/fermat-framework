package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkServicePendingsNotificationEvent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mati on 2015.10.15..
 */
public class ActorNetworkServiceRecordedAgent extends FermatAgent{


    private static final long SEND_SLEEP_TIME    = 15000;
    private static final long RECEIVE_SLEEP_TIME = 15000;

    private Thread toSend   ;
    private Thread toReceive;

    // network services registered
    private Map<String, ActorNetworkServiceRecord> poolConnectionsWaitingForResponse;

    // counter and wait time
    private Map<String, ActorNetworkServiceConnectionIncubation> waitingPlatformComponentProfile;


    private final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;
    private final com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot actorNetworkServicePluginRoot;
    private final ErrorManager errorManager                                ;
    private final EventManager eventManager                                ;
    private final WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager          ;
    private PluginFileSystem pluginFileSystem;

    public ActorNetworkServiceRecordedAgent(final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
                                             final com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot ActorNetworkServicePluginRoot,
                                             final ErrorManager                                 errorManager                                ,
                                             final EventManager                                 eventManager                                ,
                                             final PluginFileSystem                             pluginFileSystem                            ,
                                             final WsCommunicationsCloudClientManager           wsCommunicationsCloudClientManager) {

        this.actorNetworkServicePluginRoot = ActorNetworkServicePluginRoot;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.errorManager                                 = errorManager                                ;
        this.eventManager                                 = eventManager                                ;
        this.wsCommunicationsCloudClientManager           = wsCommunicationsCloudClientManager          ;
        this.pluginFileSystem                             = pluginFileSystem                            ;

        this.status                                       = AgentStatus.CREATED                         ;

        waitingPlatformComponentProfile   = new HashMap<>();
        poolConnectionsWaitingForResponse = new HashMap<>();

        //Create a thread to send the messages
        this.toSend = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    sendCycle();
            }
        });

        //Create a thread to receive the messages
        this.toReceive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    receiveCycle();
            }
        });
    }

    public void start() throws CantStartAgentException {

        try {

            toSend.start();

            toReceive.start();

            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    // TODO MANAGE PAUSE, STOP AND RESUME METHODS.

    public void sendCycle() {

        try {

            if(actorNetworkServicePluginRoot.isRegister()) {

                // function to process and send the rigth message to the counterparts.
                processSend();
            }

            //Sleep for a time
            toSend.sleep(SEND_SLEEP_TIME);

        } catch (InterruptedException e) {

            reportUnexpectedError(FermatException.wrapException(e));
        } /*catch(Exception e) {

            reportUnexpectedError(FermatException.wrapException(e));
        }*/

    }

    private void processSend() {
        try {

            List<ActorNetworkServiceRecord> lstActorRecord = actorNetworkServicePluginRoot.getOutgoingNotificationDao().listRequestsByProtocolStateAndType(
                    ActorProtocolState.PROCESSING_SEND
            );


            for (ActorNetworkServiceRecord cpr : lstActorRecord) {
                switch (cpr.getNotificationDescriptor()) {

                    case ASKFORACCEPTANCE:
                    case ACCEPTED:
                    case DISCONNECTED:
                    case RECEIVED:

                        sendMessageToActor(
                                cpr
                        );

                        System.out.print("-----------------------\n" +
                                "ENVIANDO MENSAJE A OTRO INTRA USER!!!!! -----------------------\n" +
                                "-----------------------\n DESDE: " + cpr.getActorSenderAlias());


                        //toWaitingResponse(cpr.getId(),actorNetworkServicePluginRoot.getOutgoingNotificationDao());
                        break;

                }

            }
//        } catch (CantExecuteDatabaseOperationException e) {
//            e.printStackTrace();
//        }
        } catch (CantListIntraWalletUsersException e) {
            e.printStackTrace();
        }
    }


    public void receiveCycle() {

        try {

            if(actorNetworkServicePluginRoot.isRegister()) {

                // function to process and send the rigth message to the counterparts.
                processReceive();
            }

            //Sleep for a time
            toReceive.sleep(RECEIVE_SLEEP_TIME);

        } catch (InterruptedException e) {

            reportUnexpectedError(FermatException.wrapException(e));
        } /*catch(Exception e) {

            reportUnexpectedError(FermatException.wrapException(e));
        }*/

    }

    public void processReceive(){


       try {

            // process notifications
            List<ActorNetworkServiceRecord> lstActorRecord = actorNetworkServicePluginRoot.getIncomingNotificationsDao().listRequestsByProtocolStateAndType(
                    ActorProtocolState.PROCESSING_RECEIVE
            );


            for(ActorNetworkServiceRecord cpr : lstActorRecord) {
                switch (cpr.getNotificationDescriptor()) {

                    case ASKFORACCEPTANCE:

                        System.out.println("----------------------------\n" +
                                "MENSAJE PROCESANDOSE:" + cpr
                                + "\n-------------------------------------------------");

                        lauchNotification();

                        try {

                            actorNetworkServicePluginRoot.getIncomingNotificationsDao().changeProtocolState(cpr.getId(),ActorProtocolState.PENDING_ACTION);

                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
                        } catch (RequestNotFoundException e) {
                            e.printStackTrace();
                        }


//                        Gson gson = new Gson();
//
//                        cpr.changeDescriptor(NotificationDescriptor.ACCEPTED);
//                        String message = gson.toJson(cpr);
//
//                        // El destination soy yo porque me lo estan enviando
//                        // El sender es el otro y es a quien le voy a responder
//                        NetworkServiceLocal communicationNetworkServiceLocal = actorNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(cpr.getActorSenderPublicKey());
//
//                        // los cambio porque el sender es el destino y el destination soy yo al estar recibiendo
//                        communicationNetworkServiceLocal.sendMessage(cpr.getActorSenderPublicKey(),cpr.getActorDestinationPublicKey(), message);
//
//
//                        System.out.print("-----------------------\n" +
//                                "ENVIANDO RESPUESTA !!!!! -----------------------\n" +
//                                "-----------------------\n NOTIFICATION: " + cpr);


                        break;
                    case ACCEPTED:

                        System.out.print("-----------------------\n" +
                                "ACEPTARON EL REQUEST!!!!!-----------------------\n" +
                                "-----------------------\n NOTIFICAION: " + cpr);

                        lauchNotification();

                        try {

                            actorNetworkServicePluginRoot.getIncomingNotificationsDao().changeProtocolState(cpr.getId(),ActorProtocolState.PENDING_ACTION);

                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
                        } catch (RequestNotFoundException e) {
                            e.printStackTrace();
                        }

                        break;


                    case DISCONNECTED:
                    case RECEIVED:

                        sendMessageToActor(cpr);


                            //toWaitingResponse(cpr.getId(),actorNetworkServicePluginRoot.getIncomingNotificationsDao());

                        break;

                }
            }


       } catch (CantListIntraWalletUsersException e) {
           e.printStackTrace();
       }
    }

    private void sendMessageToActor(ActorNetworkServiceRecord actorNetworkServiceRecord) {

        try {
            if (!poolConnectionsWaitingForResponse.containsKey(actorNetworkServiceRecord.getActorDestinationPublicKey())) {

                if (communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(actorNetworkServiceRecord.getActorDestinationPublicKey()) == null) {


                    if (wsCommunicationsCloudClientManager != null) {

                        if (actorNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot() != null) {


                            PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
                                    .constructBasicPlatformComponentProfileFactory(
                                            actorNetworkServiceRecord.getActorSenderPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER);
                            PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
                                    .constructBasicPlatformComponentProfileFactory(
                                            actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                            NetworkServiceType.UNDEFINED,
                                            PlatformComponentType.ACTOR_INTRA_USER);

                            communicationNetworkServiceConnectionManager.connectTo(
                                    applicantParticipant,
                                    actorNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot(),
                                    remoteParticipant
                            );

                            // i put the actor in the pool of connections waiting for response-
                            poolConnectionsWaitingForResponse.put(actorNetworkServiceRecord.getActorDestinationPublicKey(), actorNetworkServiceRecord);
                        }

                    }
                }else{
                    NetworkServiceLocal communicationNetworkServiceLocal = actorNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorNetworkServiceRecord.getActorDestinationPublicKey());

                    System.out.println("----------------------------\n" +
                            "ENVIANDO MENSAJE:" + actorNetworkServiceRecord
                            + "\n-------------------------------------------------");

                    Gson gson = new Gson();

                    communicationNetworkServiceLocal.sendMessage(
                            actorNetworkServiceRecord.getActorSenderPublicKey(),
                            actorNetworkServiceRecord.getActorDestinationPublicKey(),
                            gson.toJson(actorNetworkServiceRecord)
                    );

                    actorNetworkServicePluginRoot.getOutgoingNotificationDao().changeProtocolState(actorNetworkServiceRecord.getId(), ActorProtocolState.SENT);

                    poolConnectionsWaitingForResponse.put(actorNetworkServiceRecord.getActorDestinationPublicKey(), actorNetworkServiceRecord);
                }
            } else {

                NetworkServiceLocal communicationNetworkServiceLocal = actorNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorNetworkServiceRecord.getActorDestinationPublicKey());

                if (communicationNetworkServiceLocal != null) {

                    try {

                        System.out.println("----------------------------\n" +
                                "ENVIANDO MENSAJE:" + actorNetworkServiceRecord
                                + "\n-------------------------------------------------");

                        Gson gson = new Gson();

                        communicationNetworkServiceLocal.sendMessage(
                                actorNetworkServiceRecord.getActorSenderPublicKey(),
                                actorNetworkServiceRecord.getActorDestinationPublicKey(),
                                gson.toJson(actorNetworkServiceRecord)
                        );

                        actorNetworkServicePluginRoot.getOutgoingNotificationDao().changeProtocolState(actorNetworkServiceRecord.getId(), ActorProtocolState.SENT);


                    } catch (Exception e) {

                        reportUnexpectedError(FermatException.wrapException(e));
                    }
                }
            }


        } catch (Exception z) {

            reportUnexpectedError(FermatException.wrapException(z));
        }
    }

    private PlatformComponentType platformComponentTypeSelectorByActorType(Actors type) throws InvalidParameterException {

        switch (type) {

            case INTRA_USER  : return PlatformComponentType.ACTOR_INTRA_USER  ;
            case DAP_ASSET_ISSUER: return PlatformComponentType.ACTOR_ASSET_ISSUER;
            case DAP_ASSET_USER  : return PlatformComponentType.ACTOR_ASSET_USER  ;

            default: throw new InvalidParameterException(
                    " actor type: "+type.name()+"  type-code: "+type.getCode(),
                    " type of actor not expected."
            );
        }
    }


    private void toPendingAction(UUID requestId){ //throws CantChangeRequestProtocolStateException,
           // RequestNotFoundException {

        //actorNetworkServiceDao.changeProtocolState(requestId, ActorProtocolState.PENDING_ACTION);
    }

//    private void toWaitingResponse(UUID notificationId,DAO dao) throws CantUpdateRecordDataBaseException, RequestNotFoundException, CantUpdateRecordException {
//        //dao.changeProtocolState(notificationId, ActorProtocolState.WAITING_RESPONSE);
//    }

    private void raiseEvent(final EventType eventType,
                            final UUID      requestId) {

        FermatEvent eventToRaise = eventManager.getNewEvent(eventType);
        //((CryptoPaymentRequestEvent) eventToRaise).setRequestId(requestId);
        //eventToRaise.setSource(CryptoPaymentRequestNetworkServicePluginRoot.EVENT_SOURCE);
        eventManager.raiseEvent(eventToRaise);
    }

    private void reportUnexpectedError(FermatException e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    public void connectionFailure(String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }


    public Map<String, ActorNetworkServiceRecord> getPoolConnectionsWaitingForResponse() {
        return poolConnectionsWaitingForResponse;
    }

    private void lauchNotification(){
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS);
        ActorNetworkServicePendingsNotificationEvent intraUserActorRequestConnectionEvent = (ActorNetworkServicePendingsNotificationEvent) fermatEvent;
        eventManager.raiseEvent(intraUserActorRequestConnectionEvent);
    }

}
