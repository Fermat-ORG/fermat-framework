/*
 * @#TemplateNetworkServiceRemoteAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.agents;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.CommunicationNetworkServiceRemoteAgent</code>
 * is the service toRead that maintaining the communication channel, read and wait for new message.
 *
 * This class extend of the <code>java.util.Observable</code> class,  its used on the software design pattern called: The observer pattern,
 * for more info see @link https://en.wikipedia.org/wiki/Observer_pattern
 *
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CommunicationNetworkServiceRemoteAgent extends Observable {

    /**
     * Represent the sleep time for the read or send (2000 milliseconds)
     */
    private static final long SLEEP_TIME = 2000;

    /**
     * Represent the SEND_TASK
     */
    private static final int SEND_TASK = 0;

    /**
     * Represent the RECEIVE_TASK
     */
    private static final int RECEIVE_TASK = 1;

    /**
     * Represent the communicationsVPNConnection
     */
    private CommunicationsVPNConnection communicationsVPNConnection;

    /**
     * Represents the errorManager
     */
    private final ErrorManager errorManager;

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent is the tread is running
     */
    private Boolean running;

    /**
     * Represent the read messages tread of this CommunicationNetworkServiceRemoteAgent
     */
    private Runnable toReceive = new Runnable() {
        @Override
        public void run() {
            while (running)
                processMessageReceived();
        }
    };

    /**
     * Represent the send messages tread of this CommunicationNetworkServiceRemoteAgent
     */
    private Runnable toSend = new Runnable() {
        @Override
        public void run() {
            while (running)
                processMessageToSend();
        }
    };


    /**
     * Represent the executorService
     */
    private ExecutorService executorService;

    /**
     * Represent the futures
     */
    private Future<?>[] futures = new Future[2];


    /**
     * Constructor with parameters
     *
     * @param communicationNetworkServiceConnectionManager
     * @param communicationsVPNConnection
     */
    public CommunicationNetworkServiceRemoteAgent(CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager, CommunicationsVPNConnection communicationsVPNConnection, ErrorManager errorManager) {

        super();
        this.running                                      = Boolean.FALSE;
        this.errorManager                                 = errorManager;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.communicationsVPNConnection = communicationsVPNConnection;
    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        //Set to running
        this.running  = Boolean.TRUE;
        if(executorService!=null){
            executorService.shutdownNow();
        }

        this.executorService = Executors.newFixedThreadPool(2);

        //Start the Threads
        futures[SEND_TASK] = executorService.submit(toSend);
        futures[RECEIVE_TASK] = executorService.submit(toReceive);

        System.out.println("CommunicationNetworkServiceRemoteAgent - started ");

    }

    /**
     * Pause the internal threads
     */
    public void pause(){
        this.running  = Boolean.FALSE;
    }

    /**
     * Resume the internal threads
     */
    public void resume(){
        this.running  = Boolean.TRUE;
    }

    /**
     * Stop the internal threads
     */
    public void stop(){

        //Stop the Threads
        futures[SEND_TASK].cancel(true);
        futures[RECEIVE_TASK].cancel(true);

        executorService.shutdownNow();
        //Disconnect from the service
        if(communicationsVPNConnection.isConnected()) {
            System.out.println("CommunicationNetworkServiceConnectionManager - stop() -- Calling communicationsVPNConnection.close()");
            communicationsVPNConnection.close();
        }

        System.out.println("CommunicationNetworkServiceRemoteAgent - stopped ");
    }

    /**
     * This method process the message received and save on the
     * data base in the table <code>incoming_messages</code> and notify all observers
     * to the new messages received
     */
    private void processMessageReceived(){

        try {

            /**
             * Verified the status of the connection
             */
            if (communicationsVPNConnection.isConnected()){

                //System.out.println("CommunicationNetworkServiceRemoteAgent - communicationsVPNConnection.getUnreadMessagesCount() = "+communicationsVPNConnection.getUnreadMessagesCount());

                /**
                 * process all pending messages
                 */
                for (int i = 0; i < communicationsVPNConnection.getUnreadMessagesCount(); i++) {

                    /*
                     * Read the next message in the queue
                     */
                    FermatMessage message = communicationsVPNConnection.readNextMessage();

                    /*
                     * Validate the message signature
                     */
                    AsymmetricCryptography.verifyMessageSignature(message.getSignature(), message.getContent(), communicationsVPNConnection.getRemoteParticipantNetworkService().getIdentityPublicKey());

                    /*
                     * Decrypt the message content
                     */
                    ((FermatMessageCommunication) message).setContent(AsymmetricCryptography.decryptMessagePrivateKey(message.getContent(), communicationNetworkServiceConnectionManager.getNetworkServiceRoot().getIdentity().getPrivateKey()));

                    /*
                     * Change to the new status
                     */
                    ((FermatMessageCommunication) message).setFermatMessagesStatus(FermatMessagesStatus.NEW_RECEIVED);

                    /*
                     * Save to the data base table
                     */
                    communicationNetworkServiceConnectionManager.getIncomingMessageDao().create(message);

                    /*
                     * Remove the message from the queue
                     */
                    communicationsVPNConnection.removeMessageRead(message);

                    /*
                     * Notify all observer of this agent that Received a new message
                     */
                    setChanged();
                    notifyObservers(message);

                }

            }

            if(Thread.currentThread().isInterrupted() == Boolean.FALSE) {
                //Sleep for a time
                Thread.sleep(CommunicationNetworkServiceRemoteAgent.SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            running = false;
            Thread.currentThread().interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(communicationNetworkServiceConnectionManager.getNetworkServiceRoot().getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: " + e.getMessage()));
            e.printStackTrace();
        } catch(Exception e){
            errorManager.reportUnexpectedPluginException(communicationNetworkServiceConnectionManager.getNetworkServiceRoot().getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: " + e.getMessage()));
        }

    }

    /**
     * This method read for new messages pending to send on the data base in
     * the table <code>outbox_messages</code> and encrypt the message content,
     * sing the message and send it
     */
    public void processMessageToSend(){

        try {

            if (communicationsVPNConnection.isConnected()){
                try {

                    Map<String, Object> filters = new HashMap<>();
                    filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
                    filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, communicationsVPNConnection.getRemoteParticipant().getIdentityPublicKey());

                    synchronized (this) {
                    /*
                     * Read all pending message from database
                     */
                        List<FermatMessage> messages = communicationNetworkServiceConnectionManager.getOutgoingMessageDao().findAll(filters);

                    /*
                     * For each message
                     */
                        for (FermatMessage message : messages) {


                            if (communicationsVPNConnection.isConnected() && (message.getFermatMessagesStatus() == FermatMessagesStatus.PENDING_TO_SEND)) {

                            /*
                             * Encrypt the content of the message whit the remote network service public key
                             */
                                ((FermatMessageCommunication) message).setContent(AsymmetricCryptography.encryptMessagePublicKey(message.getContent(), communicationsVPNConnection.getRemoteParticipantNetworkService().getIdentityPublicKey()));

                            /*
                             * Sing the message
                             */
                            String signature = AsymmetricCryptography.createMessageSignature(message.getContent(), communicationNetworkServiceConnectionManager.getNetworkServiceRoot().getIdentity().getPrivateKey());
                            ((FermatMessageCommunication) message).setSignature(signature);



                                /*
                                 * Send the message
                                 */
                                communicationsVPNConnection.sendMessage(message);

                                /*
                                 * Change the message and update in the data base
                                 */
                                ((FermatMessageCommunication) message).setFermatMessagesStatus(FermatMessagesStatus.SENT);
                                communicationNetworkServiceConnectionManager.getOutgoingMessageDao().update(message);

                                /*
                                 * Notify a new message send
                                 */
                                communicationNetworkServiceConnectionManager.getNetworkServiceRoot().onSentMessage(message);


                            } else {
                                System.out.println("CommunicationNetworkServiceRemoteAgent - VPN connection is connected = " + communicationsVPNConnection.isConnected());
                            }

                        }
                    }


                }catch (Exception e){
                    System.out.println("CommunicationNetworkServiceRemoteAgent - Error sending message: " + e.getMessage());
                    e.printStackTrace();
                }


            }

            if(Thread.currentThread().isInterrupted() == Boolean.FALSE){
                //Sleep for a time
                Thread.sleep(CommunicationNetworkServiceRemoteAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            running = false;
            Thread.currentThread().interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
        }
    }
}
