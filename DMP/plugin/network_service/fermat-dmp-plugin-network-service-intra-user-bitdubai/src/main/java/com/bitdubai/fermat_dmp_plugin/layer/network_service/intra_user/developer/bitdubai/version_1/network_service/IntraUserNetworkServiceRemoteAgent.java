/*
 * @#IntraUserNetworkServiceRemoteAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.network_service;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;

import java.util.List;
import java.util.Observable;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceRemoteAgent</code>
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
public class IntraUserNetworkServiceRemoteAgent extends Observable {

    /*
     * Represent the sleep time for the read or send (2000 milliseconds)
     */
    private static final long SLEEP_TIME = 2000;

    /**
     * Represent the serviceToServiceOnlineConnection
     */
    private ServiceToServiceOnlineConnection serviceToServiceOnlineConnection;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represent the incomingMessageDataAccessObject
     */
    private IncomingMessageDataAccessObject incomingMessageDataAccessObject;

    /**
     * Represent the outgoingMessageDataAccessObject
     */
    private OutgoingMessageDataAccessObject outgoingMessageDataAccessObject;

    /**
     * Represent is the tread is running
     */
    private Boolean running;

    /**
     * Represent the read messages tread of this IntraUserNetworkServiceRemoteAgent
     */
    private Thread toReceive;

    /**
     * Represent the send messages tread of this IntraUserNetworkServiceRemoteAgent
     */
    private Thread toSend;

    /**
     * Represent the eccKeyPair
     */
    private ECCKeyPair eccKeyPair;

    /**
     * Represent the public key of the remote network service
     */
    private String remoteNetworkServicePublicKey;

    /**
     * Constructor with parameters
     *
     * @param eccKeyPair from the plugin root
     * @param remoteNetworkServicePublicKey the public key
     * @param serviceToServiceOnlineConnection  the serviceToServiceOnlineConnection instance
     * @param errorManager  instance
     * @param incomingMessageDataAccessObject instance
     * @param outgoingMessageDataAccessObject instance
     */
    public IntraUserNetworkServiceRemoteAgent(ECCKeyPair eccKeyPair, String remoteNetworkServicePublicKey, ServiceToServiceOnlineConnection serviceToServiceOnlineConnection, ErrorManager errorManager, IncomingMessageDataAccessObject incomingMessageDataAccessObject, OutgoingMessageDataAccessObject outgoingMessageDataAccessObject) {

        super();
        this.eccKeyPair                       = eccKeyPair;
        this.remoteNetworkServicePublicKey    = remoteNetworkServicePublicKey;
        this.serviceToServiceOnlineConnection = serviceToServiceOnlineConnection;
        this.errorManager                     = errorManager;
        this.running                          = Boolean.FALSE;
        this.incomingMessageDataAccessObject  = incomingMessageDataAccessObject;
        this.outgoingMessageDataAccessObject  = outgoingMessageDataAccessObject;


        //Create a thread to receive the messages
        this.toReceive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running)
                    processMessageReceived();
            }
        });

        //Create a thread to send the messages
        this.toSend = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running)
                    processMessageToSend();
            }
        });

    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        //Set to running
        this.running  = Boolean.TRUE;

        //Start the Threads
        toReceive.start();
        toSend.start();

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
        toReceive.interrupt();
        toSend.interrupt();

        //Disconnect from the service
        serviceToServiceOnlineConnection.disconnect();
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
            if (serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED){

                /**
                 * process all pending messages
                 */
                for (int i = 0; i < serviceToServiceOnlineConnection.getUnreadMessagesCount(); i++) {

                    /*
                     * Read the next message in the queue
                     */
                    Message message = serviceToServiceOnlineConnection.readNextMessage();

                    /*
                     *  Cast the message to IncomingIntraUserNetworkServiceMessage
                     */
                    IncomingIntraUserNetworkServiceMessage incomingIntraUserNetworkServiceMessage = (IncomingIntraUserNetworkServiceMessage) message;

                    /*
                     * Validate the message signature
                     */
                    AsymmectricCryptography.verifyMessageSignature(incomingIntraUserNetworkServiceMessage.getSignature(), incomingIntraUserNetworkServiceMessage.getTextContent(), remoteNetworkServicePublicKey);

                    /*
                     * Decrypt the message content
                     */
                    incomingIntraUserNetworkServiceMessage.setTextContent(AsymmectricCryptography.decryptMessagePrivateKey(incomingIntraUserNetworkServiceMessage.getTextContent(), eccKeyPair.getPrivateKey()));

                    /*
                     * Change to the new status
                     */
                    incomingIntraUserNetworkServiceMessage.setStatus(MessagesStatus.NEW_RECEIVED);

                    /*
                     * Save to the data base table
                     */
                    incomingMessageDataAccessObject.create(incomingIntraUserNetworkServiceMessage);

                    /*
                     * Remove the message from the queue
                     */
                    serviceToServiceOnlineConnection.clearMessage(message);

                    /**
                     * Notify all observer of this agent that Received a new message
                     */
                    notifyObservers(message);

                }

            }

            //Sleep for a time
            toReceive.sleep(IntraUserNetworkServiceRemoteAgent.SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: "+e.getMessage()));
        }

    }

    /**
     * This method read for new messages pending to send on the data base in
     * the table <code>outbox_messages</code> and encrypt the message content,
     * sing the message and send it
     */
    public void processMessageToSend(){

        try {

            if (serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

                try {

                    /*
                     * Read all pending message from database
                     */
                    List<OutgoingIntraUserNetworkServiceMessage> messages = outgoingMessageDataAccessObject.findAll(com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_STATUS_COLUMN_NAME,
                                                                                                                  MessagesStatus.PENDING_TO_SEND.getCode());
                    /*
                     * For each message
                     */
                    for (OutgoingIntraUserNetworkServiceMessage message: messages){

                        /*
                         * Encrypt the content of the message whit the remote public key
                         */
                        message.setTextContent(AsymmectricCryptography.encryptMessagePublicKey(message.getTextContent(), remoteNetworkServicePublicKey));

                        /*
                         * Sing the message
                         */
                        AsymmectricCryptography.createMessageSignature(message.getTextContent(), eccKeyPair.getPrivateKey());

                        /*
                         * Send the message
                         */
                        serviceToServiceOnlineConnection.sendMessage(message);

                        /*
                         * Change the message and update in the data base
                         */
                        message.setStatus(MessagesStatus.SENT);
                        outgoingMessageDataAccessObject.update(message);
                    }


                } catch (CantSendMessageException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message to remote network service "));
                } catch (CantUpdateRecordDataBaseException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process messages to send. Error reason: "+e.getMessage()));
                } catch (CantReadRecordDataBaseException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process messages to send. Error reason: " + e.getMessage()));
                }

            }

            //Sleep for a time
            toSend.sleep(IntraUserNetworkServiceRemoteAgent.SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        }

    }

}
