package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkCallChannel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure.NetworkServiceRemoteAgent</code>
 * is the service toRead that maintaining the communication channel, read and wait for new message.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public final class NetworkServiceRemoteAgent extends Observable {

    private static final long SLEEP_TIME   = 2000;
    private static final int  SEND_TASK    =    0;
    private static final int  RECEIVE_TASK =    1;

    private final NetworkCallChannel              networkCallChannel             ;
    private final ErrorManager                    errorManager                   ;
    private final NetworkServiceConnectionManager networkServiceConnectionManager;
    private       ExecutorService                 executorService                ;
    private final Future<?>[]                     futures                        = new Future[2];

    private Boolean running;

    private Runnable toReceive = new Runnable() {
        @Override
        public void run() {
            while (running) processMessageReceived();
        }
    };

    private Runnable toSend = new Runnable() {
        @Override
        public void run() {
            while (running) processMessageToSend();
        }
    };

    public NetworkServiceRemoteAgent(final NetworkServiceConnectionManager networkServiceConnectionManager,
                                     final NetworkCallChannel              networkCallChannel             ,
                                     final ErrorManager                    errorManager                   ) {

        this.errorManager                    = errorManager                   ;
        this.networkServiceConnectionManager = networkServiceConnectionManager;
        this.networkCallChannel              = networkCallChannel             ;

        this.running                         = Boolean.FALSE                  ;
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
        if(networkCallChannel.isActive())
            networkCallChannel.closeChannel();

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
            if (networkCallChannel.isActive()){

                //System.out.println("CommunicationNetworkServiceRemoteAgent - networkCallChannel.getUnreadMessagesCount() = "+networkCallChannel.getUnreadMessagesCount());

                /**
                 * process all pending messages
                 */
                for (int i = 0; i < networkCallChannel.getUnreadMessagesCount(); i++) {

                    /*
                     * Read the next message in the queue
                     */
                    NetworkServiceMessage message = networkCallChannel.getNextUnreadMessage();

                    /*
                     * Validate the message signature
                     */
                    AsymmetricCryptography.verifyMessageSignature(message.getSignature(), message.getContent(), networkCallChannel.getRemoteParticipantNetworkService().getIdentityPublicKey());

                    /*
                     * Decrypt the message content
                     */
                    message.setContent(AsymmetricCryptography.decryptMessagePrivateKey(message.getContent(), networkServiceConnectionManager.getNetworkServiceRoot().getIdentity().getPrivateKey()));

                    /*
                     * Change to the new status
                     */
                    message.setFermatMessagesStatus(FermatMessagesStatus.NEW_RECEIVED);

                    /*
                     * Save to the data base table
                     */
                    networkServiceConnectionManager.getIncomingMessagesDao().create(message);

                    /*
                     * Remove the message from the queue
                     */
                    networkCallChannel.markMessageAsRead(message);

                    /*
                     * Notify all observer of this agent that Received a new message
                     */
                    setChanged();
                    notifyObservers(message);

                }

            }else{
                networkServiceConnectionManager.closeConnection(networkCallChannel.getRemoteParticipant().getIdentityPublicKey());
            }

            if(Thread.currentThread().isInterrupted() == Boolean.FALSE) {
                //Sleep for a time
                Thread.sleep(NetworkServiceRemoteAgent.SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            running = false;
            Thread.currentThread().interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(networkServiceConnectionManager.getNetworkServiceRoot().getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: " + e.getMessage()));
            e.printStackTrace();
        } catch(Exception e){
            errorManager.reportUnexpectedPluginException(networkServiceConnectionManager.getNetworkServiceRoot().getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not process message received. Error reason: " + e.getMessage()));
        }

    }

    /**
     * This method read for new messages pending to send on the data base in
     * the table <code>outbox_messages</code> and encrypt the message content,
     * sing the message and send it
     */
    public void processMessageToSend(){

        try {

            if (networkCallChannel.isActive()){
                try {

                    Map<String, Object> filters = new HashMap<>();
                    filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
                    filters.put(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, networkCallChannel.getRemoteParticipant().getIdentityPublicKey());

                    synchronized (this) {
                    /*
                     * Read all pending message from database
                     */
                        List<NetworkServiceMessage> messages = networkServiceConnectionManager.getOutgoingMessagesDao().findAll(filters);

                    /*
                     * For each message
                     */
                        for (NetworkServiceMessage message : messages) {


                            if (networkCallChannel.isActive() && (message.getFermatMessagesStatus() == FermatMessagesStatus.PENDING_TO_SEND)) {

                            /*
                             * Encrypt the content of the message whit the remote network service public key
                             */
                                message.setContent(AsymmetricCryptography.encryptMessagePublicKey(message.getContent(), networkCallChannel.getRemoteParticipantNetworkService().getIdentityPublicKey()));

                            /*
                             * Sing the message
                             */
                            String signature = AsymmetricCryptography.createMessageSignature(message.getContent(), networkServiceConnectionManager.getNetworkServiceRoot().getIdentity().getPrivateKey());
                            message.setSignature(signature);



                                /*
                                 * Send the message
                                 */
                                networkCallChannel.sendMessage(message);

                                /*
                                 * Change the message and update in the data base
                                 */
                                message.setFermatMessagesStatus(FermatMessagesStatus.SENT);
                                networkServiceConnectionManager.getOutgoingMessagesDao().update(message);

                                /*
                                 * Notify a new message send
                                 */
                                networkServiceConnectionManager.getNetworkServiceRoot().onSentMessage(message);


                            } else {
                                System.out.println("CommunicationNetworkServiceRemoteAgent - Call Channel is active = " + networkCallChannel.isActive());
                            }

                        }
                    }


                }catch (Exception e){
                    System.out.println("CommunicationNetworkServiceRemoteAgent - Error sending message: " + e.getMessage());
                    e.printStackTrace();
                }


            }else{
                networkServiceConnectionManager.closeConnection(networkCallChannel.getRemoteParticipant().getIdentityPublicKey());
            }


            if(Thread.currentThread().isInterrupted() == Boolean.FALSE){
                //Sleep for a time
                Thread.sleep(NetworkServiceRemoteAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            running = false;
            Thread.currentThread().interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
        }
    }
}
