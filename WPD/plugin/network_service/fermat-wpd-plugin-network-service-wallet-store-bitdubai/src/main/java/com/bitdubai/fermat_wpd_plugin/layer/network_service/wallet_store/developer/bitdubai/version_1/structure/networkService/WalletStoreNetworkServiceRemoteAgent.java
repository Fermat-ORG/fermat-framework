package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.IncomingMessageDAO;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.OutgoingMessageDAO;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database.WalletStoreNetworkServiceDatabaseConstants;

import java.util.List;
import java.util.Observable;

/**
 * Created by rodrigo on 8/11/15.
 */
public class WalletStoreNetworkServiceRemoteAgent extends Observable {

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
     * Represent the incomingMessageDao
     */
    private IncomingMessageDAO incomingMessageDao;

    /**
     * Represent the outgoingMessageDao
     */
    private OutgoingMessageDAO outgoingMessageDao;

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
     * @param eccKeyPair                       from the plugin root
     * @param remoteNetworkServicePublicKey    the public key
     * @param serviceToServiceOnlineConnection the serviceToServiceOnlineConnection instance
     * @param errorManager                     instance
     * @param incomingMessageDao               instance
     * @param outgoingMessageDao               instance
     */
    public WalletStoreNetworkServiceRemoteAgent(ECCKeyPair eccKeyPair, String remoteNetworkServicePublicKey, ServiceToServiceOnlineConnection serviceToServiceOnlineConnection, ErrorManager errorManager, IncomingMessageDAO incomingMessageDao, OutgoingMessageDAO outgoingMessageDao) {

        super();
        this.eccKeyPair = eccKeyPair;
        this.remoteNetworkServicePublicKey = remoteNetworkServicePublicKey;
        this.serviceToServiceOnlineConnection = serviceToServiceOnlineConnection;
        this.errorManager = errorManager;
        this.running = Boolean.FALSE;
        this.incomingMessageDao = incomingMessageDao;
        this.outgoingMessageDao = outgoingMessageDao;


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
    public void start() {

        //Set to running
        this.running = Boolean.TRUE;

        //Start the Threads
        toReceive.start();
        toSend.start();

    }

    /**
     * Pause the internal threads
     */
    public void pause() {
        this.running = Boolean.FALSE;
    }

    /**
     * Resume the internal threads
     */
    public void resume() {
        this.running = Boolean.TRUE;
    }

    /**
     * Stop the internal threads
     */
    public void stop() {

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
    private void processMessageReceived() {

        try {

            /**
             * Verified the status of the connection
             */
            if (serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

                /**
                 * process all pending messages
                 */
                for (int i = 0; i < serviceToServiceOnlineConnection.getUnreadMessagesCount(); i++) {

                    /*
                     * Read the next message in the queue
                     */
                    Message message = serviceToServiceOnlineConnection.readNextMessage();

                    /*
                     *  Cast the message to IncomingTemplateNetworkServiceMessage
                     */
                    WalletStoreNetworkServiceMessage incomingTemplateNetworkServiceMessage = (WalletStoreNetworkServiceMessage) message;

                    /*
                     * Validate the message signature
                     */
                    AsymmetricCryptography.verifyMessageSignature(incomingTemplateNetworkServiceMessage.getSignature(), incomingTemplateNetworkServiceMessage.getTextContent(), remoteNetworkServicePublicKey);

                    /*
                     * Decrypt the message content
                     */
                    incomingTemplateNetworkServiceMessage.setTextContent(AsymmetricCryptography.decryptMessagePrivateKey(incomingTemplateNetworkServiceMessage.getTextContent(), eccKeyPair.getPrivateKey()));

                    /*
                     * Change to the new status
                     */
                    incomingTemplateNetworkServiceMessage.setStatus(MessagesStatus.NEW_RECEIVED);

                    /*
                     * Save to the data base table
                     */
                    incomingMessageDao.create(incomingTemplateNetworkServiceMessage);

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
            if (!toReceive.isInterrupted()) {
                Thread.sleep(WalletStoreNetworkServiceRemoteAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            running = false;
            toReceive.interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: " + e.getMessage()));
        }

    }

    /**
     * This method read for new messages pending to send on the data base in
     * the table <code>outbox_messages</code> and encrypt the message content,
     * sing the message and send it
     */
    public void processMessageToSend() {

        try {

            if (serviceToServiceOnlineConnection.getStatus() == ConnectionStatus.CONNECTED) {

                try {

                    /*
                     * Read all pending message from database
                     */
                    List<WalletStoreNetworkServiceMessage> messages = outgoingMessageDao.findAll(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_STATUS_COLUMN_NAME,
                            MessagesStatus.PENDING_TO_SEND.getCode());
                    /*
                     * For each message
                     */
                    for (WalletStoreNetworkServiceMessage message : messages) {

                        /*
                         * Encrypt the content of the message whit the remote public key
                         */
                        message.setTextContent(AsymmetricCryptography.encryptMessagePublicKey(message.getTextContent(), remoteNetworkServicePublicKey));

                        /*
                         * Sing the message
                         */
                        AsymmetricCryptography.createMessageSignature(message.getTextContent(), eccKeyPair.getPrivateKey());

                        /*
                         * Send the message
                         */
                        serviceToServiceOnlineConnection.sendMessage(message);

                        /*
                         * Change the message and update in the data base
                         */
                        message.setStatus(MessagesStatus.SENT);
                        outgoingMessageDao.update(message);
                    }


                } catch (CantSendMessageException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message to remote network service "));
                } catch (CantUpdateRecordDataBaseException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process messages to send. Error reason: " + e.getMessage()));
                } catch (CantReadRecordDataBaseException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process messages to send. Error reason: " + e.getMessage()));
                }

            }

            if (!toSend.isInterrupted()) {
                //Sleep for a time
                Thread.sleep(WalletStoreNetworkServiceRemoteAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            running = false;
            toSend.interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
            return;
        }

    }
}
