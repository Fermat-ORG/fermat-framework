package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.factories.NetworkServiceMessageFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
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

    private final NetworkClientConnection         networkClientConnection        ;
    private final Profile                         remoteParticipant              ;
    private final ErrorManager                    errorManager                   ;
    private final NetworkServiceConnectionManager networkServiceConnectionManager;
    private       ExecutorService                 executorService                ;
    private final Future<?>[]                     futures                        = new Future[2];

    private Boolean running;

    private Runnable toSend = new Runnable() {
        @Override
        public void run() {
            while (running) processMessageToSend();
        }
    };

    public NetworkServiceRemoteAgent(final NetworkServiceConnectionManager networkServiceConnectionManager,
                                     final NetworkClientConnection         networkClientConnection        ,
                                     final Profile                         remoteParticipant              ,
                                     final ErrorManager                    errorManager                   ) {

        this.errorManager                    = errorManager                   ;
        this.networkServiceConnectionManager = networkServiceConnectionManager;
        this.networkClientConnection         = networkClientConnection        ;
        this.remoteParticipant               = remoteParticipant              ;

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

        this.executorService = Executors.newFixedThreadPool(1);

        //Start the Threads
        futures[SEND_TASK] = executorService.submit(toSend);

        System.out.println("NetworkServiceRemoteAgent - started ");

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

        executorService.shutdownNow();
        //Disconnect from the service
        /*if(networkClientConnection.isConnected())
            networkClientConnection.closeChannel();*/

        System.out.println("NetworkServiceRemoteAgent - stopped ");
    }

    /**
     * This method read for new messages pending to send on the data base in
     * the table <code>outbox_messages</code> and encrypt the message content,
     * sing the message and send it
     */
    public void processMessageToSend(){

        try {

            if (networkClientConnection.isConnected()){
                try {

                    Map<String, Object> filters = new HashMap<>();
                    filters.put(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
                    filters.put(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME, remoteParticipant.getIdentityPublicKey());

                    synchronized (this) {
                    /*
                     * Read all pending message from database
                     */
                        List<NetworkServiceMessage> messages = networkServiceConnectionManager.getOutgoingMessagesDao().findAll(filters);

                    /*
                     * For each message
                     */
                        for (NetworkServiceMessage message : messages) {


                            if (networkClientConnection.isConnected() && (message.getFermatMessagesStatus() == FermatMessagesStatus.PENDING_TO_SEND)) {

                                /*
                                 * Encrypt the content of the message whit the remote network service public key
                                 */
                                if (message.isBetweenActors())
                                    message.setContent(AsymmetricCryptography.encryptMessagePublicKey(message.getContent(), message.getReceiverNsPublicKey()));
                                else
                                    message.setContent(AsymmetricCryptography.encryptMessagePublicKey(message.getContent(), message.getReceiverPublicKey()));

                                /*
                                 * Sing the message
                                 */
                                String signature = AsymmetricCryptography.createMessageSignature(message.getContent(), networkServiceConnectionManager.getNetworkServiceRoot().getIdentity().getPrivateKey());
                                message.setSignature(signature);

                                networkClientConnection.sendPackageMessage(message, networkServiceConnectionManager.getNetworkServiceRoot().getProfile().getNetworkServiceType(), message.getReceiverPublicKey(), message.getReceiverClientPublicKey());

                                /*
                                 * Change the message and update in the data base
                                 */
                                message.setFermatMessagesStatus(FermatMessagesStatus.SENT);
                                networkServiceConnectionManager.getOutgoingMessagesDao().update(message);

                                /*
                                 * Notify a message sent
                                 */
                                networkServiceConnectionManager.getNetworkServiceRoot().onSentMessage(message);

                            } else {
                                System.out.println("NetworkServiceRemoteAgent - Connection is connected = " + networkClientConnection.isConnected());
                            }

                        }
                    }


                }catch (Exception e){
                    System.out.println("NetworkServiceRemoteAgent - Error sending message: " + e.getMessage());
                    e.printStackTrace();
                }


            } else {
                networkServiceConnectionManager.closeConnection(remoteParticipant.getIdentityPublicKey());
            }


            if(Thread.currentThread().isInterrupted() == Boolean.FALSE){
                //Sleep for a time
                Thread.sleep(NetworkServiceRemoteAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            running = false;
            Thread.currentThread().interrupt();
            System.out.println("NetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
        }
    }
}
