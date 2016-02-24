package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.ChatNetworkServicePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.ChatMetaDataDao;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.CommunicationChatNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) 2/02/2016.
 */
public class ChatNetworkServiceAgent {

    /*
    * Represent the sleep time for the read or send (2000 milliseconds)
    */
    private static final long SLEEP_TIME = 15000;
    private static final long RECEIVE_SLEEP_TIME = 15000;


    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represent is the tread is running
     */
    private Boolean running;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Communication Service, Class to send the message
     */
    //CryptoTransmissionNetworkServiceLocal communicationNetworkServiceLocal;

    /**
     * Communication manager, Class to obtain the connections
     */
    CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     *
     */
    WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * plugin root
     */
    ChatNetworkServicePluginRoot chatNetworkServicePluginRoot;

    /**
     * DAO TransactionTransmission
     */
    ChatMetaDataDao chatMetaDataDao;

    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * PlatformComponentProfile platformComponentProfile
     */
    PlatformComponentProfile platformComponentProfile;


    /**
     * Represent the send cycle tread of this NetworkService
     */
    private Thread toSend;

    /**
     * Represent the send messages tread of this TemplateNetworkServiceRemoteAgent
     */
    //private Thread toReceive;

    /**
     * Cache de metadata con conexions leidas anteriormente
     * ActorPublicKey, metadata de respuesta
     */
    Map<String, DistributionStatus> cacheResponseMetadataFromRemotes;

    /**
     * Map contains publicKey from componentProfile to connect
     *  and the number of connections intents.
     */
    Map<String,Integer> connectionsCounters;

    /**
     * Cola de espera, a la cual van pasando las conecciones que no se pudieron conectar para que se hagan con más tiempo y no saturen el server
     */
    Map<String, ChatNetworkServicePlatformComponentProfilePlusWaitTime> waitingPlatformComponentProfile;

    /**
     * Pool connections requested waiting for peer or server response
     *
     * publicKey  and transaccion metadata waiting to be a response
     */
    Map<String,ChatMetadata> poolConnectionsWaitingForResponse;


    Map<String, FermatMessage> receiveMessage;
    private boolean flag=true;


    private EventManager eventManager;

    /**
     * Constructor
     * @param chatNetworkServicePluginRoot
     * @param chatMetaDataDao
     * @param communicationNetworkServiceConnectionManager
     * @param wsCommunicationsCloudClientManager
     * @param platformComponentProfile
     * @param errorManager
     * @param remoteNetworkServicesRegisteredList
     * @param identity
     * @param eventManager
     */
    public ChatNetworkServiceAgent(
            ChatNetworkServicePluginRoot chatNetworkServicePluginRoot,
            ChatMetaDataDao chatMetaDataDao,
            CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
            WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,
            PlatformComponentProfile platformComponentProfile,
            ErrorManager errorManager,
            List<PlatformComponentProfile> remoteNetworkServicesRegisteredList,
            ECCKeyPair identity,
            EventManager eventManager) {
        try{

            this.chatNetworkServicePluginRoot = chatNetworkServicePluginRoot;
            this.chatMetaDataDao = chatMetaDataDao;
            this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
            this.wsCommunicationsCloudClientManager =wsCommunicationsCloudClientManager;
            this.errorManager = errorManager;
            this.remoteNetworkServicesRegisteredList = remoteNetworkServicesRegisteredList;
            this.identity = identity;
            this.platformComponentProfile = platformComponentProfile;
            this.eventManager = eventManager;


            cacheResponseMetadataFromRemotes = new HashMap<String, DistributionStatus>();
            waitingPlatformComponentProfile = new HashMap<>();


            poolConnectionsWaitingForResponse = new HashMap<> ();

            //Create a thread to send the messages
            this.toSend = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (running)
                        sendCycle();
                }
            });

            //Create a thread to receive the messages
//            this.toReceive = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (running)
//                        receiveCycle();
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
            FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE,FermatException.wrapException(e), "EXCEPTION THAT THE PLUGIN CAN NOT HANDLE BY ITSELF","Check the cause");
            reportUnexpectedException(ex);
        }

    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        //Set to running
        this.running  = Boolean.TRUE;
        try {
            try {
                chatMetaDataDao.initialize();
            } catch (CantInitializeChatNetworkServiceDatabaseException e) {
                reportUnexpectedException(e);
            }
            //Start the Thread
            toSend.start();
        //    toReceive.start();
            System.out.println("TransactionTransmissionAgent - started ");
        } catch (Exception exception){
            FermatException ex = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE,FermatException.wrapException(exception), "EXCEPTION THAT THE PLUGIN CAN NOT HANDLE BY ITSELF","Check the cause");
            reportUnexpectedException(ex);
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

        //Stop the Thread
        toSend.interrupt();
      //  toReceive.interrupt();
        this.running = Boolean.FALSE;
        //Disconnect from the service
    }

    /**
     *
     * Lifeclycle of the networkService Cryptotransmission.
     * This thread controls the send circuit saving each states and metadata in database
     *
     */
    public void sendCycle(){

        try {

            if(chatNetworkServicePluginRoot.isRegister()) {

                // function to process and send metadata to remote
                processMetadata();

                //Discount from the the waiting list
                discountWaitTime();
            }

            //Sleep for a time
            Thread.sleep(SLEEP_TIME);

        } catch (InterruptedException e) {
            reportUnexpectedException(FermatException.wrapException(e));
        } catch (Exception e) {
            FermatException ex = new FermatException(FermatException.DEFAULT_MESSAGE,FermatException.wrapException(e), "EXCEPTION THAT THE PLUGIN CAN NOT HANDLE BY ITSELF","Check the cause");
            reportUnexpectedException(ex);

        }

    }

    private void processMetadata() {

        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put(CommunicationChatNetworkServiceDatabaseConstants.CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME, DistributionStatus.DELIVERING.getCode());

         /*
         * Read all pending BusinessTransactionMetadata from database
         */

            List<ChatMetadataRecord> chatMetadataRecordList = chatMetaDataDao.findAllToSend();


            for (ChatMetadataRecord chatMetadataRecord : chatMetadataRecordList) {

                String receiverPublicKey= chatMetadataRecord.getRemoteActorPublicKey();

                if(!poolConnectionsWaitingForResponse.containsKey(receiverPublicKey)) {

                    //TODO: hacer un filtro por aquellas que se encuentran conectadas

                    if (communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(receiverPublicKey) == null) {

                        if (wsCommunicationsCloudClientManager != null) {

                            if (platformComponentProfile != null) {

                                PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(chatMetadataRecord.getLocalActorPublicKey(), NetworkServiceType.CHAT, PlatformComponentType.NETWORK_SERVICE);
                                PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(chatMetadataRecord.getRemoteActorPublicKey(), NetworkServiceType.CHAT, PlatformComponentType.NETWORK_SERVICE);
                                communicationNetworkServiceConnectionManager.connectTo(applicantParticipant, platformComponentProfile, remoteParticipant);

                                // pass the chatMetadataRecord to a pool waiting for the response of the other peer or server failure
                                poolConnectionsWaitingForResponse.put(receiverPublicKey, chatMetadataRecord);
                            }

                        }
                    }
                }else{

                    NetworkServiceLocal communicationNetworkServiceLocal = chatNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(receiverPublicKey);

                    if (communicationNetworkServiceLocal != null) {

                        try {
                            chatMetadataRecord.setDistributionStatus(DistributionStatus.SENT);

                            System.out.print("-----------------------\n" +
                                    "CHAT METADATA RECORD -----------------------\n" +
                                    "-----------------------\n To: " + receiverPublicKey);

                            // Si se encuentra conectado paso la metadata al dao de la capa de comunicacion para que lo envie

                            String msjContent = EncodeMsjContent.encodeMSjContentChatMetadataTransmit(chatMetadataRecord, chatMetadataRecord.getLocalActorType(), chatMetadataRecord.getRemoteActorType());

                            // Envio el mensaje a la capa de comunicacion

                            communicationNetworkServiceLocal.sendMessage(identity.getPublicKey(),receiverPublicKey,msjContent);


                            chatMetaDataDao.update(chatMetadataRecord);

                            System.out.print("-----------------------\n" +
                                    "CHAT METADATA -----------------------\n" +
                                    "-----------------------\n STATE: " + chatMetadataRecord.getDistributionStatus());

                        } catch (CantUpdateRecordDataBaseException e) {
                            reportUnexpectedException(e);
                        } catch (Exception e) {
                            reportUnexpectedException(FermatException.wrapException(e));
                        }
                    }
                }
            }

        } catch (CantReadRecordDataBaseException | CantEstablishConnectionException | CantLoadTableToMemoryException e) {
            reportUnexpectedException(e);
        }
    }

    private void discountWaitTime(){
        if(!waitingPlatformComponentProfile.isEmpty()) {
            for (ChatNetworkServicePlatformComponentProfilePlusWaitTime chatNetworkServicePlatformComponentProfilePlusWaitTime : waitingPlatformComponentProfile.values()) {
                chatNetworkServicePlatformComponentProfilePlusWaitTime.WaitTimeDown();
                if (chatNetworkServicePlatformComponentProfilePlusWaitTime.getWaitTime() <= 0) {
                    remoteNetworkServicesRegisteredList.add(chatNetworkServicePlatformComponentProfilePlusWaitTime.getPlatformComponentProfile());
                    waitingPlatformComponentProfile.remove(chatNetworkServicePlatformComponentProfilePlusWaitTime);
                }
            }
        }
    }

    public void addRemoteNetworkServicesRegisteredList(List<PlatformComponentProfile> list){
        remoteNetworkServicesRegisteredList = list;
    }

    private void receiveCycle(){
        try {
            // function to process metadata received
           // processReceive();
            //Sleep for a time
            Thread.sleep(RECEIVE_SLEEP_TIME);

        } catch (InterruptedException e) {
            reportUnexpectedException(FermatException.wrapException(e));
        }

    }
//
//    private void processReceive() {
//
//        try {
//            //communicationNetworkServiceConnectionManager.
//
//            Map<String, Object> filters = new HashMap<>();
//            filters.put(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_PENDING_FLAG_COLUMN_NAME, "false");
//
//         /*
//         * Read all pending CryptoTransmissionMetadata from database
//         */
//            List<BusinessTransactionMetadata> businessTransactionMetadataList = chatMetaDataDao.findAllToReceive(filters);
//
//
//            for(BusinessTransactionMetadata businessTransactionMetadata : businessTransactionMetadataList){
//
//                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(businessTransactionMetadata.getSenderId());
//
//                if(communicationNetworkServiceLocal!=null){
//
//                    System.out.print("-----------------------\n" +
//                            "RECEIVING BUSINESS TRANSACTION-----------------------\n" +
//                            "-----------------------\n STATE: " + businessTransactionMetadata.getState());
//
//                    // si no contiene la metadata, la tengo que guardar en la bd y notificar que llegó, tambien debería cargar ese caché cuando se lanza el evento de que llega la metadata de respuesta
//                    // if( ! cacheResponseMetadataFromRemotes.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())){
//
//                    try {
//
//                        switch (businessTransactionMetadata.getState()) {
//
//                            case SEEN_BY_DESTINATION_NETWORK_SERVICE:
//                                //TODO: revisar que se puede hacer acá
//                                System.out.println("Transaction Transmission SEEN_BY_DESTINATION_NETWORK_SERVICE---to implement");
//                                break;
//
//                            case CONFIRM_CONTRACT:
//                                System.out.print(businessTransactionMetadata.getSenderId()+" Transaction Transmission CONFIRM_CONTRACT");
//
//                                //this.poolConnectionsWaitingForResponse.remove(businessTransactionMetadata.getReceiverId());
//                                launchNotification();
//                                this.poolConnectionsWaitingForResponse.remove(businessTransactionMetadata.getReceiverId());
//                                break;
//
//                            case CONFIRM_RESPONSE:
//                                System.out.print(businessTransactionMetadata.getSenderId()+" Transaction Transmission CONFIRM_RESPONSE");
//                                launchNotification();
//                                this.poolConnectionsWaitingForResponse.remove(businessTransactionMetadata.getReceiverId());
//                                break;
//                            // si el mensaje viene con un estado de SENT es porque es la primera vez que llega, por lo que tengo que guardarlo en la bd y responder
//                            case SENT:
//
//                                businessTransactionMetadata.setState(TransactionTransmissionStates.SEEN_BY_OWN_NETWORK_SERVICE);
//                                businessTransactionMetadata.setBusinessTransactionTransactionType(businessTransactionMetadata.getType());
//                                chatMetaDataDao.update(businessTransactionMetadata);
//
//                                System.out.print("-----------------------\n" +
//                                        "RECEIVING BUSINESS TRANSACTION -----------------------\n" +
//                                        "-----------------------\n STATE: " + businessTransactionMetadata.getState());
//
//                                launchNotification();
//
//                                TransactionTransmissionResponseMessage cryptoTransmissionResponseMessage = new TransactionTransmissionResponseMessage(
//                                        businessTransactionMetadata.getTransactionId(),
//                                        TransactionTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE,
//                                        businessTransactionMetadata.getType());
//
//                                Gson gson = new Gson();
//
//                                String message = gson.toJson(cryptoTransmissionResponseMessage);
//
//                                // El destination soy yo porque me lo estan enviando
//                                // El sender es el otro y es a quien le voy a responder
//
//                                communicationNetworkServiceLocal.sendMessage(businessTransactionMetadata.getReceiverId(), businessTransactionMetadata.getSenderId(), message);
//
//
//                                System.out.print("-----------------------\n" +
//                                        "SENDING ANSWER -----------------------\n" +
//                                        "-----------------------\n STATE: " + businessTransactionMetadata.getState());
//                                break;
//                            default:
//                                //TODO: handle with an exception
//                                break;
//                        }
//
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//        } catch (CantReadRecordDataBaseException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile){
        try{
            this.platformComponentProfile = platformComponentProfile;
        }catch (Exception ex){
           // throw new ObjectNotSetException(ex, "CAN NOT SET THE OBJECT","");
            ex.printStackTrace();
            FermatException e = new ObjectNotSetException(FermatException.wrapException(ex), "","Check the cause");
            reportUnexpectedException(e);
        }

    }



    public void connectionFailure(String identityPublicKey){
        if(identityPublicKey != null && identityPublicKey.length()>0){
            this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
        }
    }


    public boolean isConnection(String publicKey){
        return this.poolConnectionsWaitingForResponse.containsKey(publicKey);
    }

    public boolean isRunning(){
        return running;
    }
//
//    private void launchNotification(){
//        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE);
//        IncomingNewContractStatusUpdate incomingNewContractStatusUpdate = (IncomingNewContractStatusUpdate) fermatEvent;
//        incomingNewContractStatusUpdate.setSource(EventSource.NETWORK_SERVICE_TRANSACTION_TRANSMISSION);
//        eventManager.raiseEvent(incomingNewContractStatusUpdate);
//    }
private void reportUnexpectedException(FermatException e){
    errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
}


}
