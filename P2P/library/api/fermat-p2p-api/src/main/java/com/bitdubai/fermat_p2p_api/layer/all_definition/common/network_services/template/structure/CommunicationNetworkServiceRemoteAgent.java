package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.IncomingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationNetworkServiceRemoteAgent</code>
 * is the service toRead that maintaining the communication channel, read and wait for new message.
 *
 * This class extend of the <code>java.util.Observable</code> class,  its used on the software design pattern called: The observer pattern,
 * for more info see @link https://en.wikipedia.org/wiki/Observer_pattern
 *
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/06/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 02/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CommunicationNetworkServiceRemoteAgent extends Observable {

    // Represent the sleep time for the read or send (2000 milliseconds)
    private static final long SLEEP_TIME = 2000;

    private final ECCKeyPair eccKeyPair;
    private final CommunicationsVPNConnection communicationsVPNConnection;
    private final ErrorManager                errorManager               ;
    private final EventManager                eventManager               ;
    private final IncomingMessageDao          incomingMessageDao         ;
    private final OutgoingMessageDao          outgoingMessageDao         ;
    private final EventSource                 eventSource                ;
    private final PluginVersionReference      pluginVersionReference     ;

    /**
     * Represent is the tread is running
     */
    private Boolean running;

    //Represent the read messages tread of this CommunicationNetworkServiceRemoteAgent
    private final Thread toReceive;
    //Represent the send messages tread of this CommunicationNetworkServiceRemoteAgent
    private final Thread toSend;

    /**
     * Represent the eccKeyPair
     */


    /**
     * Constructor with parameters.
     */
    public CommunicationNetworkServiceRemoteAgent(final ECCKeyPair eccKeyPair,
                                                  final CommunicationsVPNConnection communicationsVPNConnection,
                                                  final ErrorManager errorManager,
                                                  final EventManager eventManager,
                                                  final IncomingMessageDao incomingMessageDao,
                                                  final OutgoingMessageDao outgoingMessageDao,
                                                  final EventSource eventSource,
                                                  final PluginVersionReference pluginVersionReference) {

        super();
        this.eccKeyPair                  = eccKeyPair                 ;
        this.errorManager                = errorManager               ;
        this.eventManager                = eventManager               ;
        this.running                     = Boolean.FALSE              ;
        this.incomingMessageDao          = incomingMessageDao         ;
        this.outgoingMessageDao          = outgoingMessageDao         ;
        this.communicationsVPNConnection = communicationsVPNConnection;
        this.eventSource                 = eventSource                ;
        this.pluginVersionReference      = pluginVersionReference     ;


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

//        ExecutorService executorService =

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
        communicationsVPNConnection.close();
    }

    /**
     * This method process the message received and save on the
     * data base in the table <code>incoming_messages</code> and notify all observers
     * to the new messages received
     */
    private void processMessageReceived(){

        try {

           // System.out.println("CommunicationNetworkServiceRemoteAgent - "+communicationsVPNConnection.isActive());

            /**
             * Verified the status of the connection
             */
            if (communicationsVPNConnection.isActive()){

             //   System.out.println("CommunicationNetworkServiceRemoteAgent - "+communicationsVPNConnection.getUnreadMessagesCount());

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

            }

            if(!toReceive.isInterrupted()){
                //Sleep for a time
                Thread.sleep(CommunicationNetworkServiceRemoteAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            toReceive.interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
            return;        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: "+e.getMessage()));
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
                    filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
                    filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, communicationsVPNConnection.getRemoteParticipant().getIdentityPublicKey());

                    /*
                     * Read all pending message from database
                     */
                    List<FermatMessage> messages = outgoingMessageDao.findAll(filters);
                    /*
                     * For each message
                     */
                    for (FermatMessage message: messages){


                        if (communicationsVPNConnection.isActive() && (message.getFermatMessagesStatus() != FermatMessagesStatus.SENT)) {

                            /*
                             * Encrypt the content of the message whit the remote network service public key
                             */
                            ((FermatMessageCommunication) message).setContent(AsymmetricCryptography.encryptMessagePublicKey(message.getContent(), communicationsVPNConnection.getRemoteParticipantNetworkService().getIdentityPublicKey()));

                            /*
                             * Sing the message
                             */
                            String signature = AsymmetricCryptography.createMessageSignature(message.getContent(), eccKeyPair.getPrivateKey());
                            ((FermatMessageCommunication) message).setSignature(signature);

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
                            FermatEvent fermatEvent = eventManager.getNewEvent(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
                            fermatEvent.setSource(eventSource);
                            ((NewNetworkServiceMessageSentNotificationEvent) fermatEvent).setData(message);
                            eventManager.raiseEvent(fermatEvent);
                        }
                    }

                } catch(CantUpdateRecordDataBaseException |
                        CantReadRecordDataBaseException   |
                        RecordNotFoundException           e) {

                    errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }


            if(!toSend.isInterrupted()){
                //Sleep for a time
                Thread.sleep(CommunicationNetworkServiceRemoteAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            toSend.interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
            return;
        }

    }

}
