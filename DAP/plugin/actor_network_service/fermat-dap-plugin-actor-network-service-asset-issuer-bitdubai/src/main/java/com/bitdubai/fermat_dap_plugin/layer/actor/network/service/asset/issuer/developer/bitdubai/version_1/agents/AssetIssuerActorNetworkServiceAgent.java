/*
* @#AssetUserActorNetworkServiceAgent.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.agents;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.AssetIssuerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.database.communications.AssetIssuerNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.agents.AssetUserActorNetworkServiceAgent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 15/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetIssuerActorNetworkServiceAgent extends FermatAgent {

    /*
    * Represent the sleep time for the  send (15000 milliseconds)
    */
    private static final long SEND_SLEEP_TIME = 15000;
    private static final long RECEIVE_SLEEP_TIME = 15000;
    private final ExecutorService threadPoolExecutor;

    private AssetIssuerActorNetworkServicePluginRoot assetIssuerActorNetworkServicePluginRoot;

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

    private List<Future<?>> futures = new ArrayList<>();

    private boolean flag = true;

    private OutgoingMessageDao outgoingMessageDao;

    private Database dataBase;

    private List<FermatMessage> listRecorMessageToSend;

    /**
     * Pool connections requested waiting for peer or server response
     * <p/>
     * publicKey  and transaccion metadata waiting to be a response
     */
    Map<String, FermatMessage> poolConnectionsWaitingForResponse;


    public AssetIssuerActorNetworkServiceAgent(AssetIssuerActorNetworkServicePluginRoot assetIssuerActorNetworkServicePluginRoot,
                                               WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,
                                               CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
                                               PlatformComponentProfile platformComponentProfile,
                                               ErrorManager errorManager,
                                               ECCKeyPair identity,
                                               Database dataBase) {


        this.assetIssuerActorNetworkServicePluginRoot = assetIssuerActorNetworkServicePluginRoot;
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.platformComponentProfile = platformComponentProfile;
        this.errorManager = errorManager;
        this.identity = identity;
        this.dataBase = dataBase;
        this.status = AgentStatus.CREATED;

        outgoingMessageDao = new OutgoingMessageDao(this.dataBase);

        poolConnectionsWaitingForResponse = new HashMap<>();

        threadPoolExecutor = Executors.newFixedThreadPool(1);
        //Create a thread to send the messages
        this.toSend = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning()) {
                    sendCycle();
                }
            }
        });

        //Create a thread to receive the messages
//        this.toReceive = new Runnable() {
//            @Override
//            public void run() {
//                while (isRunning())
//                    receiveCycle();
//            }
//        };

    }

    /**
     * Start the internal threads to make the job
     */
    public void start() throws CantStartAgentException {
        try {
            futures.add(threadPoolExecutor.submit(toSend));
//            futures.add(threadPoolExecutor.submit(toReceive));

            System.out.println("START READ IN THE TABLE TO SEND MESSAGE WITH STATE PENDING_TO_SEND ");

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
            futures.add(threadPoolExecutor.submit(toSend));
//            futures.add(threadPoolExecutor.submit(toReceive));

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
            Iterator<Future<?>> it = futures.iterator();

            while (it.hasNext()) {
                it.next().cancel(true);
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
            Iterator<Future<?>> it = futures.iterator();

            while (it.hasNext()) {
                it.next().cancel(true);
            }

            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {
            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }


    /**
     * Lifeclycle of the actornetworkService
     */
    public void sendCycle() {
        try {
            if (this.assetIssuerActorNetworkServicePluginRoot.isRegister()) {
                processMetadata();
            }

            Thread.sleep(SEND_SLEEP_TIME);
        } catch (InterruptedException e) {
            status = AgentStatus.STOPPED;
            System.out.println(this.getClass().getSimpleName() + " - Thread Interrupted stopped ...  ");
            reportUnexpectedError(FermatException.wrapException(e));
        }
    }

//    public void receiveCycle() {
//
//        try {
//
//            if (assetIssuerActorNetworkServicePluginRoot.isRegister()) {
//
//                // function to process and send the right message to the counterparts.
//                processMetadata();
//            }
//
//            //Sleep for a time
//            Thread.sleep(RECEIVE_SLEEP_TIME);
//
//        } catch (InterruptedException e) {
//            status = AgentStatus.STOPPED;
//            reportUnexpectedError(FermatException.wrapException(e));
//        } /*catch(Exception e) {
//
//            reportUnexpectedError(FermatException.wrapException(e));
//        }*/
//
//    }

    private void processMetadata() {

        try {

            listRecorMessageToSend = outgoingMessageDao.findAll(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, FermatMessagesStatus.PENDING_TO_SEND.getCode());

            if (listRecorMessageToSend != null && !listRecorMessageToSend.isEmpty()) {

                for (FermatMessage fm : listRecorMessageToSend) {

                    if (!poolConnectionsWaitingForResponse.containsKey(fm.getReceiver())) {
                            /*
                            * Create the sender basic profile
                            */
                        PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
                                constructBasicPlatformComponentProfileFactory(
                                        fm.getSender(),
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_ASSET_REDEEM_POINT);

                            /*
                             * Create the receiver basic profile
                             */
                        PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
                                constructBasicPlatformComponentProfileFactory(
                                        fm.getReceiver(),
                                        NetworkServiceType.UNDEFINED,
                                        PlatformComponentType.ACTOR_ASSET_ISSUER);


                        try {
                            communicationNetworkServiceConnectionManager.connectTo(sender, platformComponentProfile, receiver);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // pass the metadata to a pool waiting for the response of the other peer or server failure
                        poolConnectionsWaitingForResponse.put(fm.getReceiver(), fm);
                    }
                }
            }
        } catch (CantReadRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send Message PENDING_TO_SEND"));
        }
    }

    public void connectionFailure(String identityPublicKey) {
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }

    public Map<String, FermatMessage> getPoolConnectionsWaitingForResponse() {
        return poolConnectionsWaitingForResponse;
    }

    private void reportUnexpectedError(FermatException e) {
        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }
}
