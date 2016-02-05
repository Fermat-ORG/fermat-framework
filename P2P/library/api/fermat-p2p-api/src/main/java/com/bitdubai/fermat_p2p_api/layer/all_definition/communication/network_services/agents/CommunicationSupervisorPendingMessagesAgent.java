/*
 * @#CommunicationSupervisorPendingMessagesAgent  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents.CommunicationSupervisorPendingMessagesAgent</code> is
 * responsible to validate is exist pending message to process (incoming or outgoing )
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 05/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationSupervisorPendingMessagesAgent extends FermatAgent {

    /**
     * Represent the sleep time between process (15000 milliseconds)
     */
    private static final long SLEEP_TIME  = 15000;

    /**
     * Represent the OUT_TASK_INDEX (0)
     */
    private static final int OUT_TASK_INDEX = 0;

    /**
     * Represent the IN_TASK_INDEX (1)
     */
    private static final int IN_TASK_INDEX = 1;

    /**
     * Represent the networkServiceRoot
     */
    private AbstractNetworkServiceBase networkServiceRoot;

    /**
     * Represent the threadPoolExecutor
     */
    private final ExecutorService threadPoolExecutor;

    /**
     * Represent the pendingOutgoingMessageProcessorTask
     */
    private Runnable pendingOutgoingMessageProcessorTask;

    /**
     * Represent the pendingIncomingMessageProcessorTask
     */
    private Runnable pendingIncomingMessageProcessorTask;

    /**
     * Represent the poolConnectionsWaitingForResponse
     */
    private Map<String, PlatformComponentProfile> poolConnectionsWaitingForResponse;

    /**
     * Represent the futures task array
     */
    private Future<?>[] futures;

    /**
     * Constructor with parameter
     *
     * @param networkServiceRoot
     */
    public CommunicationSupervisorPendingMessagesAgent(AbstractNetworkServiceBase networkServiceRoot){
        super();
        this.networkServiceRoot                = networkServiceRoot;
        this.futures                           = new Future[2];
        this.threadPoolExecutor                = Executors.newFixedThreadPool(2);
        this.status                            = AgentStatus.CREATED;
        this.poolConnectionsWaitingForResponse = new HashMap<>();

        //Create a thread to process the messages in the outgoing table
        this.pendingOutgoingMessageProcessorTask = new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    processPendingOutgoingMessage();
            }
        };

        //Create a thread to process the messages in the ingoing table
        this.pendingIncomingMessageProcessorTask = new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    processPendingIncomingMessage();
            }
        };
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
            filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.NEW_RECEIVED.getCode());
            List<FermatMessage> messages = networkServiceRoot.getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().findAll(filters);

            /*
             * For all destination in the message request a new connection
             */
            for (FermatMessage fermatMessage: messages) {

                networkServiceRoot.onNewMessagesReceive(fermatMessage);
            }

        }catch (Exception e){
            System.out.println("CommunicationSupervisorPendingMessagesAgent - processPendingIncomingMessage detect a error: "+e.getMessage());
        }
    }

    /**
     * Method that process the pending outgoing messages, in this method
     * validate is pending message to send, and request new connection for
     * the remote agent send the message
     */
    private void processPendingOutgoingMessage() {

        try {

            /*
             * Read all pending message from database
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
            List<FermatMessage> messages = networkServiceRoot.getCommunicationNetworkServiceConnectionManager().getOutgoingMessageDao().findAll(filters);

            /*
             * For all destination in the message request a new connection
             */
            for (FermatMessage fermatMessage: messages) {

                if (!poolConnectionsWaitingForResponse.containsKey(fermatMessage.getReceiver())) {
                    if (networkServiceRoot.getCommunicationNetworkServiceConnectionManager().getNetworkServiceLocalInstance(fermatMessage.getReceiver()) == null) {

                        PlatformComponentProfile applicantParticipant = networkServiceRoot.getProfileToRequestConnectionSender(fermatMessage.getSender());

                        PlatformComponentProfile remoteParticipant = networkServiceRoot.getProfileToRequestConnectionDestination(fermatMessage.getReceiver());

                        networkServiceRoot.getCommunicationNetworkServiceConnectionManager().connectTo(applicantParticipant, networkServiceRoot.getNetworkServiceProfile(), remoteParticipant);

                        poolConnectionsWaitingForResponse.put(fermatMessage.getReceiver(), remoteParticipant);

                    }
                }

            }

        } catch (Exception e) {
            System.out.println("CommunicationSupervisorPendingMessagesAgent - processPendingOutgoingMessage detect a error: "+e.getMessage());
        }

    }

    /**
     * (non-javadoc)
     * @see FermatAgent#start()
     */
    @Override
    public void start() throws CantStartAgentException {

        try {

            if(futures!=null){
                if(futures[OUT_TASK_INDEX]!=null) futures[OUT_TASK_INDEX].cancel(true);
                if(futures[IN_TASK_INDEX]!=null) futures[IN_TASK_INDEX].cancel(true);

                futures[OUT_TASK_INDEX] = threadPoolExecutor.submit(pendingOutgoingMessageProcessorTask);
                futures[IN_TASK_INDEX] = threadPoolExecutor.submit(pendingIncomingMessageProcessorTask);

            }

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
            if(futures!=null){
                if(futures[OUT_TASK_INDEX]!=null) futures[OUT_TASK_INDEX].cancel(true);
                if(futures[IN_TASK_INDEX]!=null) futures[IN_TASK_INDEX].cancel(true);

                futures[OUT_TASK_INDEX] = threadPoolExecutor.submit(pendingOutgoingMessageProcessorTask);
                futures[IN_TASK_INDEX] = threadPoolExecutor.submit(pendingIncomingMessageProcessorTask);

            }

            this.status = AgentStatus.STARTED;

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

            if(futures!=null){
                if(futures[OUT_TASK_INDEX]!=null) futures[OUT_TASK_INDEX].cancel(true);
                if(futures[IN_TASK_INDEX]!=null) futures[IN_TASK_INDEX].cancel(true);
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

            if(futures!=null){
                if(futures[OUT_TASK_INDEX]!=null) futures[OUT_TASK_INDEX].cancel(true);
                if(futures[IN_TASK_INDEX]!=null) futures[IN_TASK_INDEX].cancel(true);
            }

            this.status = AgentStatus.PAUSED;

        } catch (Exception exception) {

            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
        }
    }

    /**
     * Notify to the agent that a request connection fail
     *
     * @param identityPublicKey
     */
    public void connectionFailure(String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }

    /**
     * Get the poolConnectionsWaitingForResponse instance
     *
     * @return Map<String, PlatformComponentProfile>
     */
    public Map<String, PlatformComponentProfile> getPoolConnectionsWaitingForResponse() {
        return poolConnectionsWaitingForResponse;
    }
}
