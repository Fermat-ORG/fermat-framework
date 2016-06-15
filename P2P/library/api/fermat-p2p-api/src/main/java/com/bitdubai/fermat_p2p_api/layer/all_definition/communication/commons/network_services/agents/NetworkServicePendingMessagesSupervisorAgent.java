package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents.NetworkServicePendingMessagesSupervisorAgent</code> is
 * responsible to validate is exist pending message to process (incoming or outgoing)
 * <p/>
 * Created by Leon Acosta - (rart3001@gmail.com) on 16/05/2016.
 * Based on Roberto Requena network service Template.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServicePendingMessagesSupervisorAgent extends FermatAgent {

    private AbstractNetworkService networkServiceRoot;
    private ScheduledExecutorService scheduledThreadPool;
    private List<ScheduledFuture> scheduledFutures;
    private Map<String, Profile> poolConnectionsWaitingForResponse;

    /**
     * Constructor with parameter
     *
     * @param networkServiceRoot
     */
    public NetworkServicePendingMessagesSupervisorAgent(final AbstractNetworkService networkServiceRoot){

        super();
        this.networkServiceRoot                = networkServiceRoot;
        this.status                            = AgentStatus.CREATED;
        this.poolConnectionsWaitingForResponse = new HashMap<>();
        this.scheduledThreadPool               = Executors.newScheduledThreadPool(4);
        this.scheduledFutures                  = new ArrayList<>();
    }

    /**
     * Method that process the pending incoming messages
     */
    private void processPendingIncomingMessage() {

        try {

            /*
             * Read all pending message from database
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.NEW_RECEIVED.getCode());
            List<NetworkServiceMessage> messages = networkServiceRoot.getNetworkServiceConnectionManager().getIncomingMessagesDao().findAll(filters);

            if(messages != null) {

                /*
                 * For all destination in the message request a new connection
                 */
                for (NetworkServiceMessage fermatMessage : messages) {

                    networkServiceRoot.onNewMessageReceived(fermatMessage);

                    networkServiceRoot.getNetworkServiceConnectionManager().getIncomingMessagesDao().markAsRead(fermatMessage);

                }
            }

        }catch (Exception e){
            System.out.println("CommunicationSupervisorPendingMessagesAgent ("+networkServiceRoot.getProfile().getNetworkServiceType()+") - processPendingIncomingMessage detect a error: "+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method that process the pending outgoing messages, in this method
     * validate is pending message to send, and request new connection for
     * the remote agent send the message
     */
    private void processPendingOutgoingMessage(Integer countFail, Integer countFailMax) {

        try {

            /*
             * Read all pending message from database
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
            List<NetworkServiceMessage> messages = networkServiceRoot.getNetworkServiceConnectionManager().getOutgoingMessagesDao().findAll(filters);

            System.out.println("CommunicationSupervisorPendingMessagesAgent ("+networkServiceRoot.getProfile().getNetworkServiceType()+") - processPendingOutgoingMessage messages.size() = "+ (messages != null ? messages.size() : 0));

            if(messages != null) {

                /*
                 * For all destination in the message request a new connection
                 */
                for (NetworkServiceMessage fermatMessage: messages) {

                    if (!poolConnectionsWaitingForResponse.containsKey(fermatMessage.getReceiverPublicKey())) {

                        ActorProfile remoteParticipant = new ActorProfile();
                        remoteParticipant.setIdentityPublicKey(fermatMessage.getReceiverPublicKey());

                        networkServiceRoot.getConnection().callActor(networkServiceRoot.getProfile(), remoteParticipant);

                        poolConnectionsWaitingForResponse.put(fermatMessage.getReceiverPublicKey(), remoteParticipant);

                    }
                }

            }

        } catch (Exception e) {
            System.out.println("CommunicationSupervisorPendingMessagesAgent ("+networkServiceRoot.getProfile().getNetworkServiceType()+") - processPendingOutgoingMessage detect a error: "+e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * (non-javadoc)
     * @see FermatAgent#start()
     */
    @Override
    public void start() throws CantStartAgentException {

        try {

            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingIncomingMessageProcessorTask(),       30,  30, TimeUnit.SECONDS));
            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(1, 4),     1,  1, TimeUnit.MINUTES));
            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(5, 9),    10, 10, TimeUnit.MINUTES));
            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(10, null), 1,  1, TimeUnit.HOURS));

            this.status = AgentStatus.STARTED;

        } catch (Exception exception) {
            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#resume()
     */
    public void resume() throws CantStartAgentException {
        try {
            try {

                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingIncomingMessageProcessorTask(),        30, 30, TimeUnit.SECONDS));
                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(1, 4),     1,  1, TimeUnit.MINUTES));
                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(5, 9),    10, 10, TimeUnit.MINUTES));
                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(10, null), 1,  1, TimeUnit.HOURS));

                this.status = AgentStatus.STARTED;

            } catch (Exception exception) {
                throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
            }

        } catch (Exception exception) {

            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#pause()
     */
    public void pause() throws CantStopAgentException {
        try {

            for (ScheduledFuture future: scheduledFutures) {
                future.cancel(Boolean.TRUE);
            }

            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * (non-javadoc)
     * @see FermatAgent#stop()
     */
    public void stop() throws CantStopAgentException {
        try {

            scheduledThreadPool.shutdown();
            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * Notify to the agent that remove a specific connection
     *
     * @param identityPublicKey
     */
    public void removeConnectionWaitingForResponse(String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }

    /**
     * Notify to the agent that remove all connection
     */
    public void removeAllConnectionWaitingForResponse(){
        this.poolConnectionsWaitingForResponse.clear();
    }


    private class PendingIncomingMessageProcessorTask implements Runnable {

        /**
         * (non-javadoc)
         * @see Runnable#run()
         */
        @Override
        public void run() {
            processPendingIncomingMessage();
        }
    }

    private class PendingOutgoingMessageProcessorTask implements Runnable {

        /**
         * Represent the count fail
         */
        private Integer countFailMin;

        /**
         * Represent the count fail
         */
        private Integer countFailMax;

        /**
         * Constructor with parameters
         * @param countFailMin
         * @param countFailMax
         */
        public PendingOutgoingMessageProcessorTask(Integer countFailMin, Integer countFailMax){
            super();
            this.countFailMin = countFailMin;
            this.countFailMax = countFailMax;
        }

        /**
         * (non-javadoc)
         * @see Runnable#run()
         */
        @Override
        public void run() {
            processPendingOutgoingMessage(countFailMin, countFailMax);
        }
    }

}
