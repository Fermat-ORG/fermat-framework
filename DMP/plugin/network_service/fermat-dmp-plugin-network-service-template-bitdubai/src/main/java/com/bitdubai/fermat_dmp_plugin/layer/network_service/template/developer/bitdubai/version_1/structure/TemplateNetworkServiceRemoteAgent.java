/*
 * @#TemplateNetworkServiceRemoteAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.communication.IncomingMessageDao;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.communication.OutgoingMessageDao;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.TemplateNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceRemoteAgent</code>
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
public class TemplateNetworkServiceRemoteAgent extends Observable {

    /*
     * Represent the sleep time for the read or send (2000 milliseconds)
     */
    private static final long SLEEP_TIME = 60000;

    /**
     * Represent the communicationsVPNConnection
     */
    CommunicationsVPNConnection communicationsVPNConnection;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

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
     * Represent the read messages tread of this TemplateNetworkServiceRemoteAgent
     */
    private Thread toReceive;

    /**
     * Represent the send messages tread of this TemplateNetworkServiceRemoteAgent
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
     * @param errorManager  instance
     * @param incomingMessageDao instance
     * @param outgoingMessageDao instance
     */
    public TemplateNetworkServiceRemoteAgent(ECCKeyPair eccKeyPair, CommunicationsVPNConnection communicationsVPNConnection, String remoteNetworkServicePublicKey, ErrorManager errorManager, IncomingMessageDao incomingMessageDao, OutgoingMessageDao outgoingMessageDao) {

        super();
        this.eccKeyPair                          = eccKeyPair;
        this.remoteNetworkServicePublicKey       = remoteNetworkServicePublicKey;
        this.errorManager                        = errorManager;
        this.running                             = Boolean.FALSE;
        this.incomingMessageDao                  = incomingMessageDao;
        this.outgoingMessageDao                  = outgoingMessageDao;
        this.communicationsVPNConnection         = communicationsVPNConnection;


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
        //serviceToServiceOnlineConnection.disconnect();
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
            if (communicationsVPNConnection.isActive()){

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
                    AsymmectricCryptography.verifyMessageSignature(message.getSignature(), message.getContent(), remoteNetworkServicePublicKey);

                    /*
                     * Decrypt the message content
                     */
                    ((FermatMessageCommunication) message).setContent(AsymmectricCryptography.decryptMessagePrivateKey(message.getContent(), eccKeyPair.getPrivateKey()));

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

                    /**
                     * Notify all observer of this agent that Received a new message
                     */
                    notifyObservers(message);

                }

            }

            //Sleep for a time
            toReceive.sleep(TemplateNetworkServiceRemoteAgent.SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: "+e.getMessage()));
        }

    }

    /**
     * This method read for new messages pending to send on the data base in
     * the table <code>outbox_messages</code> and encrypt the message content,
     * sing the message and send it
     */
    public void processMessageToSend(){

        try {

                try {

                    Map<String, Object> filters = new HashMap<>();
                    filters.put(TemplateNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
                    filters.put(TemplateNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, remoteNetworkServicePublicKey);

                    /*
                     * Read all pending message from database
                     */
                    List<FermatMessage> messages = outgoingMessageDao.findAll();
                    /*
                     * For each message
                     */
                    for (FermatMessage message: messages){


                        if (communicationsVPNConnection.isActive()) {

                            /*
                             * Encrypt the content of the message whit the remote public key
                             */
                            ((FermatMessageCommunication) message).setContent(AsymmectricCryptography.encryptMessagePublicKey(message.getContent(), remoteNetworkServicePublicKey));

                            /*
                             * Sing the message
                             */
                            AsymmectricCryptography.createMessageSignature(message.getContent(), eccKeyPair.getPrivateKey());

                            /*
                             * Send the message
                             */
                            communicationsVPNConnection.sendMessage(message);

                            /*
                             * Change the message and update in the data base
                             */
                            ((FermatMessageCommunication) message).setFermatMessagesStatus(FermatMessagesStatus.SENT);
                            outgoingMessageDao.update(message);
                        }
                    }

                } catch (CantUpdateRecordDataBaseException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process messages to send. Error reason: "+e.getMessage()));
                } catch (CantReadRecordDataBaseException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process messages to send. Error reason: " + e.getMessage()));
                }

            //Sleep for a time
            toSend.sleep(TemplateNetworkServiceRemoteAgent.SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        }

    }

}
