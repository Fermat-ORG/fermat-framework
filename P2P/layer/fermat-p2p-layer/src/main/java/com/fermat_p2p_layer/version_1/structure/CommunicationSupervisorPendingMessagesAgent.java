///*
// * @#CommunicationSupervisorPendingMessagesAgent  - 2016
// * Copyright bitDubai.com., All rights reserved.
// * You may not modify, use, reproduce or distribute this software.
// * BITDUBAI/CONFIDENTIAL
// */
//package com.fermat_p2p_layer.version_1.structure;
//
//import com.bitdubai.fermat_api.CantStartAgentException;
//import com.bitdubai.fermat_api.CantStopAgentException;
//import com.bitdubai.fermat_api.FermatAgent;
//import com.bitdubai.fermat_api.FermatException;
//import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
//import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
//import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
//import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
//import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
//import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantReadRecordDataBaseException;
//import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
//import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
//import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
//import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
//import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
//import com.fermat_p2p_layer.version_1.P2PLayerPluginRoot;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.TimeUnit;
//
///**
// * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents.CommunicationSupervisorPendingMessagesAgent</code> is
// * responsible to validate is exist pending message to process (incoming or outgoing )
// * <p/>
// * <p/>
// * Created by Roberto Requena - (rart3001@gmail.com) on 05/02/16.
// *
// * @version 1.0
// * @since Java JDK 1.7
// */
//public class CommunicationSupervisorPendingMessagesAgent extends FermatAgent {
//
//    /**
//     * Represent the networkServiceRoot
//     */
//    private P2PLayerPluginRoot networkServiceRoot;
//
//    /**
//     * Represent the scheduledThreadPool
//     */
//    private ScheduledExecutorService scheduledThreadPool;
//
//    /**
//     * Represent the scheduledFutures
//     */
//    private List<ScheduledFuture> scheduledFutures;
//
//    /**
//     * Represent the poolConnectionsWaitingForResponse
//     */
//    private Map<String, PlatformComponentProfile> poolConnectionsWaitingForResponse;
//
//    /**
//     * Constructor with parameter
//     *
//     * @param networkServiceRoot
//     */
//    public CommunicationSupervisorPendingMessagesAgent(P2PLayerPluginRoot networkServiceRoot){
//        super();
//        this.networkServiceRoot                = networkServiceRoot;
//        this.status                            = AgentStatus.CREATED;
//        this.poolConnectionsWaitingForResponse = new HashMap<>();
//        this.scheduledThreadPool               = Executors.newScheduledThreadPool(4);
//        this.scheduledFutures                  = new ArrayList<>();
//    }
//
//    /**
//     * Method that process the pending incoming messages
//     */
//    private void processPendingIncomingMessage() {
//
////        try {
////
////            /*
////             * Read all pending message from database
////             */
////            Map<String, Object> filters = new HashMap<>();
////            filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.NEW_RECEIVED.getCode());
////            List<NetworkServiceMessage> messages = getFermatMessages();//networkServiceRoot.getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().findAll(filters);
////
////            /*
////             * For all destination in the message request a new connection
////             */
////            for (FermatMessage fermatMessage: messages) {
////
////                networkServiceRoot.onNewMessagesReceive(fermatMessage);
////
////                ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
////                networkServiceRoot.getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().update(fermatMessage);
////
////            }
////
////        }catch (Exception e){
////            System.out.println("CommunicationSupervisorPendingMessagesAgent ("+networkServiceRoot.getNetworkServiceProfile().getName()+") - processPendingIncomingMessage detect a error: "+e.getMessage());
////            e.printStackTrace();
////        }
//    }
//
//    private List<NetworkServiceMessage> getFermatMessages(){
//        Map<String, Object> filters = new HashMap<>();
//        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.NEW_RECEIVED.getCode());
//        Iterator<AbstractNetworkService> it = networkServiceRoot.getNetworkServices().iterator();
//        List<NetworkServiceMessage> list  = new ArrayList<>();
//        while(it.hasNext()){
//            try {
//                list.addAll(it.next().getNetworkServiceConnectionManager().getIncomingMessagesDao().findAll(filters));
//            } catch (CantReadRecordDataBaseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return list;
//    }
//
//    private List<NetworkServiceMessage> getOutgoingFermatMessages(int countFail,int countFailMax){
//        Iterator<AbstractNetworkService> it = networkServiceRoot.getNetworkServices().iterator();
//        List<NetworkServiceMessage> list  = new ArrayList<>();
//        while(it.hasNext()){
//            try {
//                AbstractNetworkService abstractNetworkService = it.next();
//                for(FermatMessage fermatMessage:abstractNetworkService.getNetworkServiceConnectionManager().getOutgoingMessagesDao().findByFailCount(countFail, countFailMax)){
//                    if (!poolConnectionsWaitingForResponse.containsKey(fermatMessage.getReceiver())) {
//                        if (abstractNetworkService.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(fermatMessage.getReceiver()) == null) {
//
//                            PlatformComponentProfile applicantParticipant = networkServiceRoot.getProfileSenderToRequestConnection(
//                                    fermatMessage.getSender(),
//                                    fermatMessage.getSenderNsType(),
//                                    fermatMessage.getSenderType()
//                            );
//
//                            PlatformComponentProfile remoteParticipant = networkServiceRoot.getProfileDestinationToRequestConnection(
//                                    fermatMessage.getReceiver(),
//                                    fermatMessage.getReceiverNsType(),
//                                    fermatMessage.getReceiverType()
//                            );
//
//                            // System.out.println("CommunicationSupervisorPendingMessagesAgent ("+networkServiceRoot.getNetworkServiceProfile().getName()+") - Requesting new connection with "+remoteParticipant.getIdentityPublicKey());
//                            networkServiceRoot.getCommunicationNetworkServiceConnectionManager().connectTo(applicantParticipant, networkServiceRoot.getNetworkServiceProfile(), remoteParticipant);
//
//                            poolConnectionsWaitingForResponse.put(fermatMessage.getReceiver(), remoteParticipant);
//
//                        }
//                    }
//                }
//
//            } catch (CantReadRecordDataBaseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return list;
//    }
//
//    /**
//     * Method that process the pending outgoing messages, in this method
//     * validate is pending message to send, and request new connection for
//     * the remote agent send the message
//     */
//    private void processPendingOutgoingMessage(Integer countFail, Integer countFailMax) {
//
//        try {
//
//            /*
//             * Read all pending message from database
//             */
//            List<NetworkServiceMessage> messages = getOutgoingFermatMessages(countFail,countFailMax); //networkServiceRoot.getCommunicationNetworkServiceConnectionManager().getOutgoingMessageDao().findByFailCount(countFail, countFailMax);
//
//           // System.out.println("CommunicationSupervisorPendingMessagesAgent ("+networkServiceRoot.getNetworkServiceProfile().getName()+") - processPendingOutgoingMessage messages.size() = "+ (messages != null ? messages.size() : 0));
//
//            /*
//             * For all destination in the message request a new connection
//             */
//            for (FermatMessage fermatMessage: messages) {
//
//                if (!poolConnectionsWaitingForResponse.containsKey(fermatMessage.getReceiver())) {
//                    if (networkServiceRoot.getCommunicationNetworkServiceConnectionManager().getNetworkServiceLocalInstance(fermatMessage.getReceiver()) == null) {
//
//                        PlatformComponentProfile applicantParticipant = networkServiceRoot.getProfileSenderToRequestConnection(
//                                fermatMessage.getSender(),
//                                fermatMessage.getSenderNsType(),
//                                fermatMessage.getSenderType()
//                        );
//
//                        PlatformComponentProfile remoteParticipant = networkServiceRoot.getProfileDestinationToRequestConnection(
//                                fermatMessage.getReceiver(),
//                                fermatMessage.getReceiverNsType(),
//                                fermatMessage.getReceiverType()
//                        );
//
//                       // System.out.println("CommunicationSupervisorPendingMessagesAgent ("+networkServiceRoot.getNetworkServiceProfile().getName()+") - Requesting new connection with "+remoteParticipant.getIdentityPublicKey());
//                        networkServiceRoot.getCommunicationNetworkServiceConnectionManager().connectTo(applicantParticipant, networkServiceRoot.getNetworkServiceProfile(), remoteParticipant);
//
//                        poolConnectionsWaitingForResponse.put(fermatMessage.getReceiver(), remoteParticipant);
//
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("CommunicationSupervisorPendingMessagesAgent ("+networkServiceRoot.getNetworkServiceProfile().getName()+") - processPendingOutgoingMessage detect a error: "+e.getMessage());
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * (non-javadoc)
//     * @see FermatAgent#start()
//     */
//    @Override
//    public void start() throws CantStartAgentException {
//
//        try {
//
//            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingIncomingMessageProcessorTask(),       30,  30, TimeUnit.SECONDS));
//            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(1, 4),     1,  1, TimeUnit.MINUTES));
//            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(5, 9),    10, 10, TimeUnit.MINUTES));
//            scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(10, null), 1,  1, TimeUnit.HOURS));
//
//            this.status = AgentStatus.STARTED;
//
//        } catch (Exception exception) {
//            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
//        }
//    }
//
//    /**
//     * (non-javadoc)
//     * @see FermatAgent#resume()
//     */
//    public void resume() throws CantStartAgentException {
//        try {
//            try {
//
//                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingIncomingMessageProcessorTask(),        30, 30, TimeUnit.SECONDS));
//                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(1, 4),     1,  1, TimeUnit.MINUTES));
//                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(5, 9),    10, 10, TimeUnit.MINUTES));
//                scheduledFutures.add(scheduledThreadPool.scheduleAtFixedRate(new PendingOutgoingMessageProcessorTask(10, null), 1,  1, TimeUnit.HOURS));
//
//                this.status = AgentStatus.STARTED;
//
//            } catch (Exception exception) {
//                throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
//            }
//
//        } catch (Exception exception) {
//
//            throw new CantStartAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
//        }
//    }
//
//    /**
//     * (non-javadoc)
//     * @see FermatAgent#pause()
//     */
//    public void pause() throws CantStopAgentException {
//        try {
//
//            for (ScheduledFuture future: scheduledFutures) {
//                future.cancel(Boolean.TRUE);
//            }
//
//            this.status = AgentStatus.PAUSED;
//
//        } catch (Exception exception) {
//
//            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
//        }
//    }
//
//    /**
//     * (non-javadoc)
//     * @see FermatAgent#stop()
//     */
//    public void stop() throws CantStopAgentException {
//        try {
//
//            scheduledThreadPool.shutdown();
//            this.status = AgentStatus.PAUSED;
//
//        } catch (Exception exception) {
//
//            throw new CantStopAgentException(FermatException.wrapException(exception), null, "You should inspect the cause.");
//        }
//    }
//
//    /**
//     * Notify to the agent that remove a specific connection
//     *
//     * @param identityPublicKey
//     */
//    public void removeConnectionWaitingForResponse(String identityPublicKey){
//        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
//    }
//
//    /**
//     * Notify to the agent that remove all connection
//     */
//    public void removeAllConnectionWaitingForResponse(){
//        this.poolConnectionsWaitingForResponse.clear();
//    }
//
//
//    private class PendingIncomingMessageProcessorTask implements Runnable {
//
//        /**
//         * (non-javadoc)
//         * @see Runnable#run()
//         */
//        @Override
//        public void run() {
//            processPendingIncomingMessage();
//        }
//    }
//
//    private class PendingOutgoingMessageProcessorTask implements Runnable {
//
//        /**
//         * Represent the count fail
//         */
//        private Integer countFailMin;
//
//        /**
//         * Represent the count fail
//         */
//        private Integer countFailMax;
//
//        /**
//         * Constructor with parameters
//         * @param countFailMin
//         * @param countFailMax
//         */
//        public PendingOutgoingMessageProcessorTask(Integer countFailMin, Integer countFailMax){
//            super();
//            this.countFailMin = countFailMin;
//            this.countFailMax = countFailMax;
//        }
//
//        /**
//         * (non-javadoc)
//         * @see Runnable#run()
//         */
//        @Override
//        public void run() {
//            processPendingOutgoingMessage(countFailMin, countFailMax);
//        }
//    }
//
//}
