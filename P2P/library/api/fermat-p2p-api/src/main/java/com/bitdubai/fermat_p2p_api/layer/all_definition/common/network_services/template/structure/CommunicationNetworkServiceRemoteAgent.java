/*
 * @#TemplateNetworkServiceRemoteAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.communications.IntraCommunicationNetworkServiceRemoteAgent</code>
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
public class CommunicationNetworkServiceRemoteAgent extends Observable {

    /*
     * Represent the sleep time for the read or send (2000 milliseconds)
     */
    private static final long SLEEP_TIME = 2000;

    private static final int SEND_TASK = 0;
    private static final int RECEIVE_TASK = 1;

    private final NetworkService networkServicePluginRoot;

    /**
     * Represent the communicationsVPNConnection
     */
    private CommunicationsVPNConnection communicationsVPNConnection;

    /**
     * Manager
     */
    private CommunicationNetworkServiceConnectionManager_V2 communicationNetworkServiceConnectionManager;
    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Represent the incomingMessageDao
     */
    private IncomingMessageDao incomingMessageDao;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDao outgoingMessageDao;

    /**
     * Represent is the tread is running
     */
    private Boolean running;

    /**
     * Represent the read messages tread of this IntraCommunicationNetworkServiceRemoteAgent
     */
    private Runnable toReceive = new Runnable() {
        @Override
        public void run() {
            while (running)
                processMessageReceived();
        }
    };

    /**
     * Represent the send messages tread of this IntraCommunicationNetworkServiceRemoteAgent
     */
    private Runnable toSend = new Runnable() {
        @Override
        public void run() {
            while (running)
                processMessageToSend();
        }
    };
    /**
     *
     */
    private ExecutorService executorService;
    private Future<?>[] futures = new Future[2];
    /**
     * Represent the eccKeyPair
     */
    private ECCKeyPair eccKeyPair;

    /**
     * Constructor with parameters
     *  @param eccKeyPair from the plugin root
     * @param errorManager  instance
     * @param incomingMessageDao instance
     * @param outgoingMessageDao instance
     * @param networkServicePluginRoot
     */
    public CommunicationNetworkServiceRemoteAgent(CommunicationNetworkServiceConnectionManager_V2 communicationNetworkServiceConnectionManager,ECCKeyPair eccKeyPair, CommunicationsVPNConnection communicationsVPNConnection, ErrorManager errorManager, EventManager eventManager, IncomingMessageDao incomingMessageDao, OutgoingMessageDao outgoingMessageDao, NetworkService networkServicePluginRoot) {

        super();
        this.eccKeyPair                          = eccKeyPair;
        this.errorManager                        = errorManager;
        this.eventManager                        = eventManager;
        this.running                             = Boolean.FALSE;
        this.incomingMessageDao                  = incomingMessageDao;
        this.outgoingMessageDao                  = outgoingMessageDao;
        this.communicationsVPNConnection         = communicationsVPNConnection;
        this.networkServicePluginRoot            = networkServicePluginRoot;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        executorService = Executors.newFixedThreadPool(2);

    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        try{
            //Set to running
            this.running  = Boolean.TRUE;

            if(futures!=null){
                if(futures[SEND_TASK]!=null)futures[SEND_TASK].cancel(true);
                if(futures[RECEIVE_TASK]!=null)futures[RECEIVE_TASK].cancel(true);
            }
            //Start the Threads
            assert futures != null;
            futures[SEND_TASK] = executorService.submit(toSend);
            futures[RECEIVE_TASK] = executorService.submit(toReceive);

            System.out.println("CommunicationNetworkServiceRemoteAgent - started ");

        }catch (Exception e){
            e.printStackTrace();
        }

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
        try {

            System.out.println("CommunicationNetworkServiceRemoteAgent - stopped ");


            //Stop the Threads
            futures[SEND_TASK].cancel(true);
            futures[RECEIVE_TASK].cancel(true);

            executorService.shutdownNow();

            System.out.println("Old Template CommunicationNetworkServiceRemoteAgent - stop() - calling  communicationsVPNConnection.close()");


            //Disconnect from the service
            if (communicationsVPNConnection.isConnected())
                communicationsVPNConnection.close();

            System.out.println("CommunicationNetworkServiceRemoteAgent - stopped ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method process the message received and save on the
     * data base in the table <code>incoming_messages</code> and notify all observers
     * to the new messages received
     */
    private void processMessageReceived(){

        try {

           System.out.println("CommunicationNetworkServiceRemoteAgent - processMessageReceived "+communicationsVPNConnection.isActive()+"---" +
                   "Thread: "+ Thread.currentThread().getId()+"----------------");

            /**
             * Verified the status of the connection
             */
            if (communicationsVPNConnection.isConnected()){

                System.out.println("CommunicationNetworkServiceRemoteAgent - communicationsVPNConnection.getUnreadMessagesCount() = "+communicationsVPNConnection.getUnreadMessagesCount());

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
                    ((FermatMessageCommunication) message).setContent(AsymmetricCryptography.decryptMessagePrivateKey(message.getContent(), eccKeyPair.getPrivateKey()));

                    /*
                     * Change to the new status
                     */
                    ((FermatMessageCommunication) message).setFermatMessagesStatus(FermatMessagesStatus.NEW_RECEIVED);

                    /*
                     * Save to the data base table
                     */
                    incomingMessageDao.create(message);

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

            }else{
                communicationNetworkServiceConnectionManager.closeConnection(communicationsVPNConnection.getRemoteParticipant().getIdentityPublicKey());
            }

            if(Thread.currentThread().isInterrupted() == Boolean.FALSE) {
                //Sleep for a time
//                Thread.sleep(CommunicationNetworkServiceRemoteAgent.SLEEP_TIME);
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (InterruptedException e) {
            running = false;
            Thread.currentThread().interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: " + e.getMessage()));
        } catch (Exception e){
            e.printStackTrace();
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

                    System.out.println("CommunicationNetworkServiceRemoteAgent - processMessageToSend ");


                    Map<String, Object> filters = new HashMap<>();
                    filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
                    filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, communicationsVPNConnection.getRemoteParticipant().getIdentityPublicKey());

                    /*
                     * Read all pending message from database
                     */
                    List<FermatMessage> messages = outgoingMessageDao.findAll(filters);

                    System.out.println("CommunicationNetworkServiceRemoteAgent - messages.size() "+messages.size());

                    /*
                     * For each message
                     */
                    for (FermatMessage message: messages){


                        if (communicationsVPNConnection.isConnected() && (message.getFermatMessagesStatus() == FermatMessagesStatus.PENDING_TO_SEND)){

                            /*
                             * Encrypt the content of the message whit the remote network service public key
                             */
                            ((FermatMessageCommunication) message).setContent(AsymmetricCryptography.encryptMessagePublicKey(message.getContent(), communicationsVPNConnection.getRemoteParticipantNetworkService().getIdentityPublicKey()));

                            /*
                             * Sing the message
                             */
                            String signature = AsymmetricCryptography.createMessageSignature(message.getContent(), eccKeyPair.getPrivateKey());
                            ((FermatMessageCommunication) message).setSignature(signature);

                            System.out.println("CommunicationNetworkServiceRemoteAgent - communicationsVPNConnection = "+communicationsVPNConnection);

                            /*
                             * Send the message
                             */
                            communicationsVPNConnection.sendMessage(message);

                            /*
                             * Change the message and update in the data base
                             */
                            ((FermatMessageCommunication) message).setFermatMessagesStatus(FermatMessagesStatus.SENT);
                            outgoingMessageDao.update(message);


                            /*
                             * Put the message on a event and fire new event
                             */
//                            FermatEvent fermatEvent = eventManager.getNewEvent(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
//                            fermatEvent.setSource();
//                            ((NewNetworkServiceMessageSentNotificationEvent) fermatEvent).setData(message);
//                            eventManager.raiseEvent(fermatEvent);
                            networkServicePluginRoot.handleNewSentMessageNotificationEvent(message);


                        }else{
                            System.out.println("CommunicationNetworkServiceRemoteAgent - VPN connection is connected = "+communicationsVPNConnection.isConnected());
                        }

                    }


                }catch (Exception e){
                    System.out.println("IntraCommunicationNetworkServiceRemoteAgent - Error sending message: " + e.getMessage());
                    e.printStackTrace();
                }


            }else{
                communicationNetworkServiceConnectionManager.closeConnection(communicationsVPNConnection.getRemoteParticipant().getIdentityPublicKey());
            }


            if(Thread.currentThread().isInterrupted() == Boolean.FALSE){
                //Sleep for a time
//                Thread.sleep(CommunicationNetworkServiceRemoteAgent.SLEEP_TIME);
                TimeUnit.SECONDS.sleep(2);
            }

        } catch (InterruptedException e) {
            running = false;
            Thread.currentThread().interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
