package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.ActorProtocolState;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDao;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

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
    private Map<String, String> poolConnectionsWaitingForResponse;

    // counter and wait time
    private Map<String, ActorNetworkServiceConnectionIncubation> waitingPlatformComponentProfile;

    private IntraUserNetworkServiceDao actorNetworkServiceDao;

    private final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;
    private final IntraActorNetworkServicePluginRoot ActorNetworkServicePluginRoot;
    private final ErrorManager errorManager                                ;
    private final EventManager eventManager                                ;
    private final PluginDatabaseSystem pluginDatabaseSystem                        ;
    private final UUID pluginId                                    ;
    private final WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager          ;
    private PluginFileSystem pluginFileSystem;

    public ActorNetworkServiceRecordedAgent(final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
                                             final IntraActorNetworkServicePluginRoot ActorNetworkServicePluginRoot,
                                             final ErrorManager                                 errorManager                                ,
                                             final EventManager                                 eventManager                                ,
                                             final PluginDatabaseSystem                         pluginDatabaseSystem                        ,
                                             final UUID                                         pluginId                                    ,
                                             final PluginFileSystem                             pluginFileSystem                            ,
                                             final WsCommunicationsCloudClientManager           wsCommunicationsCloudClientManager) {

        this.ActorNetworkServicePluginRoot = ActorNetworkServicePluginRoot;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.errorManager                                 = errorManager                                ;
        this.eventManager                                 = eventManager                                ;
        this.pluginDatabaseSystem                         = pluginDatabaseSystem                        ;
        this.pluginId                                     = pluginId                                    ;
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

            this.actorNetworkServiceDao = new IntraUserNetworkServiceDao(
                    pluginDatabaseSystem,
                    pluginFileSystem,
                    pluginId,
                    ActorNetworkServicePluginRoot.getDataBase()
            );

            //actorNetworkServiceDao.initialize();

            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    // TODO MANAGE PAUSE, STOP AND RESUME METHODS.

    public void sendCycle() {

        try {

            if(ActorNetworkServicePluginRoot.isRegister()) {

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

            List<ActorNetworkServiceRecord> lstActorRecord = actorNetworkServiceDao.getAllRequestCacheRecord(
                    ActorProtocolState.PROCESSING_SEND
            );

            for (ActorNetworkServiceRecord cpr : lstActorRecord) {
                switch (cpr.getNotificationDescriptor()) {

                    case ASKFORACCEPTANCE:
                    case ACCEPTED:
                    case DISCONNECTED:
                    case RECEIVED:

                    sendMessageToActor(
                            cpr.toJson(),
                            cpr.getActorDestinationPublicKey(),
                            cpr.getActorDestinationType(),
                            cpr.getActorSenderPublicKey(),
                            cpr.getActorSenderType()
                    );

                        toWaitingResponse(cpr.getId());
                        break;

                }

            }
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
        }
    }


    public void receiveCycle() {

        try {

            if(ActorNetworkServicePluginRoot.isRegister()) {

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
            List<ActorNetworkServiceRecord> lstActorRecord = actorNetworkServiceDao.getAllRequestCacheRecord(
                    ActorProtocolState.PROCESSING_RECEIVE
            );

            for(ActorNetworkServiceRecord cpr : lstActorRecord) {
                switch (cpr.getNotificationDescriptor()) {

                    case ASKFORACCEPTANCE:

                        break;
                    case ACCEPTED:
                    case DISCONNECTED:
                    case RECEIVED:

                        sendMessageToActor(
                                cpr.toJson(),
                                cpr.getActorDestinationPublicKey(),
                                cpr.getActorDestinationType(),
                                cpr.getActorSenderPublicKey(),
                                cpr.getActorSenderType()
                        );

                        toWaitingResponse(cpr.getId());
                        break;

                }
            }


        } catch (CantExecuteDatabaseOperationException e) {
            reportUnexpectedError(e);
        }
    }

    private void sendMessageToActor(final String jsonMessage      ,
                                    final String actorPublicKey   ,
                                    final Actors actorType        ,
                                    final String identityPublicKey,
                                    final Actors identityType     ) {

        try {
            if (!poolConnectionsWaitingForResponse.containsKey(actorPublicKey)) {

                if (communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(actorPublicKey) == null) {


                    if (wsCommunicationsCloudClientManager != null) {

                        if (ActorNetworkServicePluginRoot.getPlatformComponentProfile() != null) {

                            PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                                    identityPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(identityType)
                            );
                            PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                                    actorPublicKey,
                                    NetworkServiceType.UNDEFINED,
                                    platformComponentTypeSelectorByActorType(actorType)
                            );

                            communicationNetworkServiceConnectionManager.connectTo(
                                    applicantParticipant,
                                    ActorNetworkServicePluginRoot.getPlatformComponentProfile(),
                                    remoteParticipant
                            );

                            // i put the actor in the pool of connections waiting for response-
                            poolConnectionsWaitingForResponse.put(actorPublicKey, actorPublicKey);
                        }

                    }
                }
            } else {

                NetworkServiceLocal communicationNetworkServiceLocal = ActorNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(actorPublicKey);

                if (communicationNetworkServiceLocal != null) {

                    try {

                        communicationNetworkServiceLocal.sendMessage(
                                ActorNetworkServicePluginRoot.getIdentityPublicKey(),
                                actorPublicKey,
                                jsonMessage
                        );

                        poolConnectionsWaitingForResponse.remove(actorPublicKey);

                        communicationNetworkServiceConnectionManager.closeConnection(actorPublicKey); // close connection once i send message ?

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
            case ASSET_ISSUER: return PlatformComponentType.ACTOR_ASSET_ISSUER;
            case ASSET_USER  : return PlatformComponentType.ACTOR_ASSET_USER  ;

            default: throw new InvalidParameterException(
                    " actor type: "+type.name()+"  type-code: "+type.getCode(),
                    " type of actor not expected."
            );
        }
    }

//    private String buildJsonInformationMessage(ActorNetworkServiceRecord cpr) {
//
//        return new InformationMessage(
//                cpr.getRequestId(),
//                cpr.getAction()
//        ).toJson();
//    }
//
//    private String buildJsonRequestMessage(CryptoPaymentRequest cpr) {
//
//        return new RequestMessage(
//                cpr.getRequestId()        ,
//                cpr.getIdentityPublicKey(),
//                cpr.getIdentityType()     ,
//                cpr.getActorPublicKey()   ,
//                cpr.getActorType()        ,
//                cpr.getDescription()      ,
//                cpr.getCryptoAddress()    ,
//                cpr.getAmount()           ,
//                cpr.getStartTimeStamp()   ,
//                cpr.getAction()           ,
//                cpr.getNetworkType()
//        ).toJson();
//    }

    private void toPendingAction(UUID requestId){ //throws CantChangeRequestProtocolStateException,
           // RequestNotFoundException {

        //actorNetworkServiceDao.changeProtocolState(requestId, ActorProtocolState.PENDING_ACTION);
    }

    private void toWaitingResponse(UUID requestId) {//throws CantChangeRequestProtocolStateException,
            //RequestNotFoundException                            {

        //actorNetworkServiceDao.changeProtocolState(requestId, ActorProtocolState.WAITING_RESPONSE);
    }

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
}
