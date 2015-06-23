/*
 * @#IntraUserNetworkServiceRemoteAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ConnectionStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.Message;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.ServiceToServiceOnlineConnection;

import java.security.KeyPair;
import java.util.Observable;
import java.util.UUID;

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
     * Represent the sleep time for the toRead (2000 milliseconds)
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
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Represent is the tread is active
     */
    private Boolean active;

    /**
     * Represent is the keyPair
     */
    private KeyPair keyPair;

    /**
     * Represent the read messages tread of this IntraUserNetworkServiceRemoteAgent
     */
    private Thread toReceive;

    /**
     * Represent the send messages tread of this IntraUserNetworkServiceRemoteAgent
     */
    private Thread toSend;

    /**
     * Constructor with parameters
     *
     * @param serviceToServiceOnlineConnection  the serviceToServiceOnlineConnection instance
     * @param pluginDatabaseSystem the pluginDatabaseSystem instance
     * @param errorManager  the errorManager instance
     */
    public IntraUserNetworkServiceRemoteAgent(ServiceToServiceOnlineConnection serviceToServiceOnlineConnection, PluginDatabaseSystem pluginDatabaseSystem, ErrorManager errorManager) {

        super();
        this.serviceToServiceOnlineConnection = serviceToServiceOnlineConnection;
        this.pluginDatabaseSystem             = pluginDatabaseSystem;
        this.errorManager                     = errorManager;

        //Create a thread to receive the messages
        this.toReceive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (active)
                    processMessageReceived();
            }
        });

        //Create a thread to send the messages
        this.toSend = new Thread(new Runnable() {
            @Override
            public void run() {
                while (active)
                    processMessageToSend();
            }
        });

    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        //Start the Threads
        toReceive.start();
        toSend.start();

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
     * This method decrypt the content of the message received and save on the
     * data base in the table <code>inbox_messages</code> and notify all observers
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

                    Message message = serviceToServiceOnlineConnection.readNextMessage();

                    //Cast the message to IntraUserNetworkServiceMessage
                    IntraUserNetworkServiceMessage intraUserNetworkServiceMessage = (IntraUserNetworkServiceMessage) message;

                    //Save to the data base table

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

                //Estos valores deben tomarse de la base de datos
                UUID remoteNetworkService = UUID.randomUUID();
                String msjTextContent = "Prueba";

                try {

                    //Read all message from database
                    //Falta implementar la lectura de la base de datos


                    //Create a new message to send
                    IntraUserNetworkServiceMessage message = new IntraUserNetworkServiceMessage();

                    //Encrypt the message text content
                    message.setTextContent(AsymmectricCryptography.encryptMessagePublicKey(msjTextContent, keyPair.getPublic().toString()));

                    //Sing the encrypt message
                    //message.setSignature(AsymmectricCryptography.createMessageSignature(message.getTextContent(), keyPair.getPrivate().toString()));

                    //Send the message
                    serviceToServiceOnlineConnection.sendMessage(message);

                } catch (CantSendMessageException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send message to remote network service " + remoteNetworkService));
                }

            }

            //Sleep for a time
            toSend.sleep(IntraUserNetworkServiceRemoteAgent.SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        }

    }

}
