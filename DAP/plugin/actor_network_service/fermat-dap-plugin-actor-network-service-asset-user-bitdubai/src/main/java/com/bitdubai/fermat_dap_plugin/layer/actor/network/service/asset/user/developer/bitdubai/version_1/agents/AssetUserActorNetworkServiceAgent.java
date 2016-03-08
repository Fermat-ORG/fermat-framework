/*
* @#AssetUserActorNetworkServiceAgent.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.agents;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetNetworkServicePendingNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.enums.ActorAssetProtocolState;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantListActorAssetUsersException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.AssetUserActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.structure.AssetUserNetworkServiceRecord;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.agents.AssetUserActorNetworkServiceAgent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 15/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetUserActorNetworkServiceAgent extends FermatAgent {

    /*
    * Represent the sleep time for the  send (15000 milliseconds)
    */
    private static final long SEND_SLEEP_TIME = 15000;
    private static final long RECEIVE_SLEEP_TIME = 15000;
    private static final int SEND_TASK = 0;
    private static final int RECEIVE_TASK = 1;
    private final ExecutorService threadPoolExecutor;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represent is the tread is running
     */
//    private Boolean running;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Communication Service, Class to send the message
     */

    /**
     * Communication manager, Class to obtain the connections
     */
    CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     *
     */
    WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;


    /**
     * PlatformComponentProfile platformComponentProfile
     */
    PlatformComponentProfile platformComponentProfile;


    /**
     * Represent the send cycle tread of this NetworkService
     */
    private Runnable toSend;
    private Runnable toReceive;
    private Runnable toProcessMessage;

    private Future<?>[] futures = new Future[3];

    private boolean flag = true;

    private OutgoingMessageDao outgoingMessageDao;

    private Database dataBase;

    private List<FermatMessage> listRecorMessageToSend;

    /**
     * Pool connections requested waiting for peer or server response
     * <p/>
     * publicKey  and transaccion metadata waiting to be a response
     */
    Map<String, AssetUserNetworkServiceRecord> poolConnectionsWaitingForResponse;

    private AssetUserActorNetworkServicePluginRoot assetUserActorNetworkServicePluginRoot;

    //    public AssetUserActorNetworkServiceAgent(AssetUserActorNetworkServicePluginRoot assetUserActorNetworkServicePluginRoot,
//                                             WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,
//                                             CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
//                                             PlatformComponentProfile platformComponentProfile,
//                                             ErrorManager errorManager,
//                                             ECCKeyPair identity,
//                                             Database dataBase) {
    public AssetUserActorNetworkServiceAgent(AssetUserActorNetworkServicePluginRoot assetUserActorNetworkServicePluginRoot,
                                             Database dataBase) {

        this.assetUserActorNetworkServicePluginRoot = assetUserActorNetworkServicePluginRoot;
//        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
//        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
//        this.platformComponentProfile = platformComponentProfile;
//        this.errorManager = errorManager;
//        this.identity = identity;
        this.dataBase = dataBase;
        this.status = AgentStatus.CREATED;

        outgoingMessageDao = new OutgoingMessageDao(this.dataBase);

        poolConnectionsWaitingForResponse = new HashMap<>();

        threadPoolExecutor = Executors.newFixedThreadPool(3);

        //Create a thread to send the messages
        this.toProcessMessage = new Runnable() {
            @Override
            public void run() {
                while (isRunning()) {
                    sendMessage();
                }
            }
        };

        //Create a thread to receive the messages
        this.toSend = new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    sendCycle();
            }
        };

        //Create a thread to receive the messages
        this.toReceive = new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    receiveCycle();
            }
        };
    }

    /**
     * Start the internal threads to make the job
     */
    public void start() throws CantStartAgentException {
        try {
            if (futures != null) {
                if (futures[SEND_TASK] != null) futures[SEND_TASK].cancel(true);
                if (futures[RECEIVE_TASK] != null) futures[RECEIVE_TASK].cancel(true);

                futures[SEND_TASK] = threadPoolExecutor.submit(toSend);
                futures[RECEIVE_TASK] = threadPoolExecutor.submit(toReceive);

            }

            this.status = AgentStatus.STARTED;
        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * Resume the internal threads
     */
    public void resume() throws CantStartAgentException {
        try {
            if (futures != null) {
                if (futures[SEND_TASK] != null) futures[SEND_TASK].cancel(true);
                if (futures[RECEIVE_TASK] != null) futures[RECEIVE_TASK].cancel(true);

                futures[SEND_TASK] = threadPoolExecutor.submit(toSend);
                futures[RECEIVE_TASK] = threadPoolExecutor.submit(toReceive);

            }

            this.status = AgentStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * Pause the internal threads
     */
    public void pause() throws CantStopAgentException {
        try {
            if (futures != null) {
                if (futures[SEND_TASK] != null) futures[SEND_TASK].cancel(true);
                if (futures[RECEIVE_TASK] != null) futures[RECEIVE_TASK].cancel(true);
            }

            this.status = AgentStatus.PAUSED;
        } catch (Exception exception) {
            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * Stop the internal threads
     */
    public void stop() throws CantStopAgentException {
        try {
            if (futures != null) {
                if (futures[SEND_TASK] != null) futures[SEND_TASK].cancel(true);
                if (futures[RECEIVE_TASK] != null) futures[RECEIVE_TASK].cancel(true);
            }

            this.status = AgentStatus.PAUSED;
            ;

        } catch (Exception exception) {
            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    public void sendCycle() {
        try {
//            if (this.assetUserActorNetworkServicePluginRoot.isRegister()) {
//                processMetadata();
//            }
            if (assetUserActorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null) {

                if (!assetUserActorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()) {
                    //System.out.println("ActorNetworkServiceRecordedAgent - sendCycle() no connection available ... ");
                    return;
                } else {
                    // function to process and send the rigth message to the counterparts.
                    processSend();

                    //Sleep for a time
                    TimeUnit.SECONDS.sleep(2);
                }
            }
//            Thread.sleep(SEND_SLEEP_TIME);
        } catch (InterruptedException e) {
            status = AgentStatus.STOPPED;
            System.out.println(this.getClass().getSimpleName() + " - Thread Interrupted stopped ...  ");
            reportUnexpectedError(FermatException.wrapException(e));
        }
    }

    private void processSend() {
        try {

            List<AssetUserNetworkServiceRecord> lstActorRecord = assetUserActorNetworkServicePluginRoot
                    .getOutgoingNotificationDao().listRequestsByProtocolStateAndNotDone(
                            ActorAssetProtocolState.PROCESSING_SEND);


            for (AssetUserNetworkServiceRecord cpr : lstActorRecord) {
                switch (cpr.getAssetNotificationDescriptor()) {

                    case ASKFORCONNECTION:
                    case ACCEPTED:
                    case DISCONNECTED:
                    case DENIED:
                    case RECEIVED:
                        sendMessageToActor(cpr);

                        System.out.print("SEND MENSAJE A OTRO ACTOR ASSET DESDE: " + cpr.getActorSenderAlias());

                        //toWaitingResponse(cpr.getId(),actorNetworkServicePluginRoot.getOutgoingNotificationDao());
                        break;
                }
            }
//        } catch (CantExecuteDatabaseOperationException e) {
//            e.printStackTrace();
//        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveCycle() {

        try {

            if (assetUserActorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null) {

                if (!assetUserActorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()) {
                    //System.out.println("ActorNetworkServiceRecordedAgent - receiveCycle() no connection available ... ");
                    return;
                } else {

                    // function to process and send the right message to the counterparts.
                    processReceive();

                    //Sleep for a time
                    Thread.sleep(RECEIVE_SLEEP_TIME);
                }
            }

        } catch (InterruptedException e) {
            status = AgentStatus.STOPPED;
            System.out.println("ActorNetworkServiceRecordedAgent - receiveCycle() Thread Interrupted stopped ... ");
        } catch (Exception e) {

            reportUnexpectedError(FermatException.wrapException(e));
        }

    }

    public void processReceive() {
        try {
            List<AssetUserNetworkServiceRecord> lstActorRecord = assetUserActorNetworkServicePluginRoot.
                    getIncomingNotificationsDao().listRequestsByProtocolStateAndType(
                    ActorAssetProtocolState.PROCESSING_RECEIVE);

            for (AssetUserNetworkServiceRecord cpr : lstActorRecord) {
                switch (cpr.getAssetNotificationDescriptor()) {
                    case ASKFORCONNECTION:
                        System.out.println("ACTOR ASSET MENSAJE PROCESANDOSE:" + cpr.getActorDestinationPublicKey());
                        launchNotification();
                        try {
                            assetUserActorNetworkServicePluginRoot.getIncomingNotificationsDao().
                                    changeProtocolState(cpr.getId(), ActorAssetProtocolState.PENDING_ACTION);
                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
//                        } catch (RequestNotFoundException e) {
//                            e.printStackTrace();
                        }
                        break;

                    case ACCEPTED:
                        System.out.print("ACTOR ASSET REQUEST ACEPTADO: " + cpr.getActorDestinationPublicKey());
                        launchNotification();
                        try {
                            assetUserActorNetworkServicePluginRoot.getIncomingNotificationsDao().
                                    changeProtocolState(cpr.getId(), ActorAssetProtocolState.PENDING_ACTION);
                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
//                        } catch (RequestNotFoundException e) {
//                            e.printStackTrace();
                        }
                        break;

                    case DISCONNECTED:
                        System.out.print("ACTOR ASSET REQUEST DESCONEXION: " + cpr.getActorDestinationPublicKey());
                        launchNotification();
                        try {
                            assetUserActorNetworkServicePluginRoot.getIncomingNotificationsDao().
                                    changeProtocolState(cpr.getId(), ActorAssetProtocolState.PENDING_ACTION);
                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
//                        } catch (RequestNotFoundException e) {
//                            e.printStackTrace();
                        }
                        break;

                    case RECEIVED:
                        sendMessageToActor(cpr);
                        //toWaitingResponse(cpr.getId(),actorNetworkServicePluginRoot.getIncomingNotificationsDao());
                        break;

                    case DENIED:
                        System.out.print("ACTOR ASSET REQUEST DENEGADO: " + cpr.getActorDestinationPublicKey());
                        launchNotification();
                        try {
                            assetUserActorNetworkServicePluginRoot.getIncomingNotificationsDao().
                                    changeProtocolState(cpr.getId(), ActorAssetProtocolState.PENDING_ACTION);
                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (CantUpdateRecordException e) {
                            e.printStackTrace();
//                        } catch (RequestNotFoundException e) {
//                            e.printStackTrace();
                        }
                        break;
                }
            }
        } catch (CantGetActorAssetNotificationException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToActor(AssetUserNetworkServiceRecord assetUserNetworkServiceRecord) {
        try {
            if (!poolConnectionsWaitingForResponse.containsKey(assetUserNetworkServiceRecord.getActorDestinationPublicKey())) {
                if (assetUserActorNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(assetUserNetworkServiceRecord.getActorDestinationPublicKey()) == null) {
                    if (assetUserActorNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot() != null) {

                        PlatformComponentProfile applicantParticipant = assetUserActorNetworkServicePluginRoot.
                                getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                                .constructPlatformComponentProfileFactory(
                                        assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                                        "A",
                                        "A",
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_ASSET_USER, "");

                        PlatformComponentProfile remoteParticipant = assetUserActorNetworkServicePluginRoot.
                                getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                                .constructPlatformComponentProfileFactory(
                                        assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                                        "B",
                                        "B",
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_ASSET_USER, "");

                        assetUserActorNetworkServicePluginRoot.getNetworkServiceConnectionManager().connectTo(
                                applicantParticipant,
                                assetUserActorNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot(),
                                remoteParticipant);

                        // I put the actor in the pool of connections waiting for response-
                        poolConnectionsWaitingForResponse.put(assetUserNetworkServiceRecord.getActorDestinationPublicKey(), assetUserNetworkServiceRecord);
                    }
                } else {
                    NetworkServiceLocal communicationNetworkServiceLocal = assetUserActorNetworkServicePluginRoot.
                            getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(assetUserNetworkServiceRecord.getActorDestinationPublicKey());

                    System.out.println("ACTOR ASSET ENVIANDO MENSAJE: " + assetUserNetworkServiceRecord.getActorDestinationPublicKey());

                    communicationNetworkServiceLocal.sendMessage(
                            assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                            assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                            assetUserNetworkServiceRecord.toJson());

                    assetUserActorNetworkServicePluginRoot.getOutgoingNotificationDao().
                            changeProtocolState(assetUserNetworkServiceRecord.getId(), ActorAssetProtocolState.SENT);

                    poolConnectionsWaitingForResponse.put(assetUserNetworkServiceRecord.getActorDestinationPublicKey(), assetUserNetworkServiceRecord);
                }
            } else {
                NetworkServiceLocal communicationNetworkServiceLocal = assetUserActorNetworkServicePluginRoot.getNetworkServiceConnectionManager().
                        getNetworkServiceLocalInstance(assetUserNetworkServiceRecord.getActorDestinationPublicKey());
                if (communicationNetworkServiceLocal != null) {
                    try {
                        System.out.println("ACTOR ASSET ENVIANDO MENSAJE: " + assetUserNetworkServiceRecord.getActorDestinationPublicKey());
                        communicationNetworkServiceLocal.sendMessage(
                                assetUserNetworkServiceRecord.getActorSenderPublicKey(),
                                assetUserNetworkServiceRecord.getActorDestinationPublicKey(),
                                assetUserNetworkServiceRecord.toJson());

                        assetUserActorNetworkServicePluginRoot.getOutgoingNotificationDao().
                                changeProtocolState(assetUserNetworkServiceRecord.getId(), ActorAssetProtocolState.SENT);
                    } catch (Exception e) {
                        reportUnexpectedError(FermatException.wrapException(e));
                    }
                }
            }
        } catch (Exception z) {
            reportUnexpectedError(FermatException.wrapException(z));
        }
    }

    /**
     * Lifeclycle of the actornetworkService
     */
    public void sendMessage() {
        try {
//            if (this.assetUserActorNetworkServicePluginRoot.isRegister()) {
//                processMetadata();
//            }
            if (assetUserActorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection() != null) {

                if (!assetUserActorNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()) {
                    //System.out.println("ActorNetworkServiceRecordedAgent - sendCycle() no connection available ... ");
                    return;
                } else {
                    // function to process and send the rigth message to the counterparts.
                    processMetadata();

                    //Sleep for a time
                    TimeUnit.SECONDS.sleep(2);
                }
            }
//            Thread.sleep(SEND_SLEEP_TIME);
        } catch (InterruptedException e) {
            status = AgentStatus.STOPPED;
            System.out.println(this.getClass().getSimpleName() + " - Thread Interrupted stopped ...  ");
            reportUnexpectedError(FermatException.wrapException(e));
        }
    }

    private void processMetadata() {
        System.out.println("ACTOR ASSET - PASO POR processMetadata...");
//        try {
//            listRecorMessageToSend = outgoingMessageDao.findAll(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, FermatMessagesStatus.PENDING_TO_SEND.getCode());
//            if (listRecorMessageToSend != null && !listRecorMessageToSend.isEmpty()) {
//                for (FermatMessage fm : listRecorMessageToSend) {
//                    if (!poolConnectionsWaitingForResponse.containsKey(fm.getReceiver())) {
//                            /*
//                            * Create the sender basic profile
//                            */
//                        PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
//                                constructBasicPlatformComponentProfileFactory(
//                                        fm.getSender(),
//                                        NetworkServiceType.UNDEFINED,
//                                        PlatformComponentType.ACTOR_ASSET_USER);
//
//                            /*
//                             * Create the receiver basic profile
//                             */
//                        PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
//                                constructBasicPlatformComponentProfileFactory(
//                                        fm.getReceiver(),
//                                        NetworkServiceType.UNDEFINED,
//                                        PlatformComponentType.ACTOR_ASSET_USER);
//
//                        try {
//                            communicationNetworkServiceConnectionManager.connectTo(sender, platformComponentProfile, receiver);
//                        } catch (CantEstablishConnectionException e) {
//                            e.printStackTrace();
//                        }
//                        // pass the metada to a pool wainting for the response of the other peer or server failure
//                        poolConnectionsWaitingForResponse.put(fm.getReceiver(), fm);
//                    }
//                }
//            }
//        } catch (CantReadRecordDataBaseException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send Message PENDING_TO_SEND"));
//        }
    }

    public void connectionFailure(String identityPublicKey) {
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }

    public Map<String, AssetUserNetworkServiceRecord> getPoolConnectionsWaitingForResponse() {
        return poolConnectionsWaitingForResponse;
    }

    private void reportUnexpectedError(FermatException e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    private void launchNotification() {
        FermatEvent fermatEvent = assetUserActorNetworkServicePluginRoot.getEventManager().getNewEvent(EventType.ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS);
        ActorAssetNetworkServicePendingNotificationEvent actorAssetRequestConnectionEvent = (ActorAssetNetworkServicePendingNotificationEvent) fermatEvent;
        assetUserActorNetworkServicePluginRoot.getEventManager().raiseEvent(actorAssetRequestConnectionEvent);
    }
}
