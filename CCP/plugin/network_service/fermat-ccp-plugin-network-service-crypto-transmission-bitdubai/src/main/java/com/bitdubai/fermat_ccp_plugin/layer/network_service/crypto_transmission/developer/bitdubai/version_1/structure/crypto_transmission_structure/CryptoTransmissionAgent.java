package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.CryptoTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.CryptoTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao.CryptoTransmissionConnectionsDAO;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao.CryptoTransmissionMetadataDAO_V2;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantInitializeCryptoTransmissionNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.structure.CommunicationNetworkServiceConnectionManager_V2;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingCryptoMetadataEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Matias Furszyfer on 2015.10.05..
 */
public class CryptoTransmissionAgent {

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
    private AtomicBoolean running;

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
    CommunicationNetworkServiceConnectionManager_V2 communicationNetworkServiceConnectionManager;

    /**
     *
     */
    WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * plugin root
     */
    CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot;

    /**
     * DAO CryptoTransmission
     */
    CryptoTransmissionMetadataDAO_V2 incomingCryptoTransmissionMetadataDAO;
    CryptoTransmissionMetadataDAO_V2 outgoingCryptoTransmissionMetadataDAO;

    CryptoTransmissionConnectionsDAO cryptoTransmissionConnectionsDAO;

    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;



    /**
     * Represent the send cycle tread of this NetworkService
     */
    private Runnable toSend;

    /**
     * Represent the send messages tread of this TemplateNetworkServiceRemoteAgent
     */
    private Runnable toReceive;

    private List<Future<?>> futures= new ArrayList<>();
    private final ExecutorService threadPoolExecutor;

    /**
     * Cache de metadata con conexions leidas anteriormente
     * ActorPublicKey, metadata de respuesta
     */
    Map<String, CryptoTransmissionProtocolState> cacheResponseMetadataFromRemotes;

    /**
     * Mapa con el publicKey del componentProfile al cual me quiero conectar
     *  y las veces que intenté conectarme para saltearlo si no está conectado
     */
    Map<String,Timer> connectionsTimer;

    /**
     * Cola de espera, a la cual van pasando las conecciones que no se pudieron conectar para que se hagan con más tiempo y no saturen el server
     */
    Map<String, CryptoTransmissionPlatformComponentProfilePlusWaitTime> waitingPlatformComponentProfile;

    /**
     * Pool connections requested waiting for peer or server response
     *
     * publicKey  and transaccion metadata waiting to be a response
     */
    Map<String,CryptoTransmissionMetadata> poolConnectionsWaitingForResponse;


    Map<String, FermatMessage> receiveMessage;
    private boolean flag=true;


    private  EventManager eventManager;
    /**
     *  Constructor
     *  @param
     * @param cryptoTransmissionNetworkServicePluginRoot
     * @param cryptoTransmissionConnectionsDAO
     * @param communicationNetworkServiceConnectionManager
     * @param errorManager
     */

    public CryptoTransmissionAgent(
            CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot,
            CryptoTransmissionConnectionsDAO cryptoTransmissionConnectionsDAO,
            CryptoTransmissionMetadataDAO_V2 incomingCryptoTransmissionMetadataDAO,
            CryptoTransmissionMetadataDAO_V2 outgoingCryptoTransmissionMetadataDAO,
            CommunicationNetworkServiceConnectionManager_V2 communicationNetworkServiceConnectionManager,
            WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,
            ErrorManager errorManager,
            List<PlatformComponentProfile> remoteNetworkServicesRegisteredList,
            ECCKeyPair identity,
            EventManager eventManager) {

        this.cryptoTransmissionNetworkServicePluginRoot = cryptoTransmissionNetworkServicePluginRoot;
        this.cryptoTransmissionConnectionsDAO = cryptoTransmissionConnectionsDAO;
        this.incomingCryptoTransmissionMetadataDAO = incomingCryptoTransmissionMetadataDAO;
        this.outgoingCryptoTransmissionMetadataDAO = outgoingCryptoTransmissionMetadataDAO;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.wsCommunicationsCloudClientManager =wsCommunicationsCloudClientManager;
        this.errorManager = errorManager;
        this.remoteNetworkServicesRegisteredList = remoteNetworkServicesRegisteredList;
        this.identity = identity;
        this.eventManager = eventManager;

        cacheResponseMetadataFromRemotes = new HashMap<String, CryptoTransmissionProtocolState>();
        waitingPlatformComponentProfile = new HashMap<>();

        threadPoolExecutor = Executors.newFixedThreadPool(2);


        poolConnectionsWaitingForResponse = new HashMap<> ();

        //Create a thread to send the messages
        this.toSend = new Runnable() {
            @Override
            public void run() {
                while (running.get())
                    sendCycle();
            }
        };

        //Create a thread to receive the messages
        this.toReceive = new Runnable() {
            @Override
            public void run() {
                while (running.get())
                    receiveCycle();
            }
        };

    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        //Set to running
        this.running  = new AtomicBoolean(true);

        try {
            outgoingCryptoTransmissionMetadataDAO.initialize();
            incomingCryptoTransmissionMetadataDAO.initialize();
        } catch (CantInitializeCryptoTransmissionNetworkServiceDatabaseException e) {
            e.printStackTrace();
        }

        //Start the Thread
        futures.add(threadPoolExecutor.submit(toSend));
        futures.add(threadPoolExecutor.submit(toReceive));

        System.out.println("CryptoTransmissionAgent - started ");


    }

    /**
     * Pause the internal threads
     */
    public void pause(){
        running.set(false);
        Iterator<Future<?>> it = futures.iterator();

        while (it.hasNext()){
            it.next().cancel(true);
        }
    }

    /**
     * Resume the internal threads
     */
    public void resume(){
        if(running.get()==false){
            futures.add(threadPoolExecutor.submit(toSend));
            futures.add(threadPoolExecutor.submit(toReceive));
            this.running.set(true);
        }
    }

    /**
     * Stop the internal threads
     */
    public void stop(){
        running.set(false);
        //Stop the Thread
        Iterator<Future<?>> it = futures.iterator();

        while (it.hasNext()){
            it.next().cancel(true);
        }

    }




    /**
     *
     * Lifeclycle of the networkService Cryptotransmission.
     * This thread controls the send circuit saving each states and metadata in database
     *
     */
    public void sendCycle(){

        try {

            if(running.get()) {
                if (cryptoTransmissionNetworkServicePluginRoot.isRegister()) {
                    // deberia dividirlo en funciones mas pequeñas despues

                    // function to process and send metadata to remote
                    processMetadata();

                    // this function read the active conections waiting for response that the events not catch
                    //readResponseFromRemote();


                    //Discount from the the waiting list
                    discountWaitTime();
                }
            }
//            if (toSend.isInterrupted() == Boolean.FALSE) {
                //Sleep for a time
                Thread.sleep(CryptoTransmissionAgent.SLEEP_TIME);
//            }

        } catch (InterruptedException e) {
            running.set(false);
//            toSend.interrupt();
            System.out.println("CryptoTransmissionAgent - Thread Interrupted stopped ...  ");
            resume();
            System.out.println("CryptoTransmissionAgent - Thread Interrupted stopped, restarting threads ...  ");
        }

    }

    private void processMetadata() {

        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.PRE_PROCESSING_SEND.getCode());
            //filters.put(ComunicationLayerNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, remoteNetworkServicePublicKey);

         /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = outgoingCryptoTransmissionMetadataDAO.findAll(filters);


            for (CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata) {


                if(!poolConnectionsWaitingForResponse.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())) {
                    if (communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getDestinationPublicKey()) == null) {
                        if (wsCommunicationsCloudClientManager != null && cryptoTransmissionNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot() != null) {
                            PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(cryptoTransmissionMetadata.getSenderPublicKey(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_INTRA_USER);
                            PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(cryptoTransmissionMetadata.getDestinationPublicKey(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_INTRA_USER);
                            communicationNetworkServiceConnectionManager.connectTo(applicantParticipant, cryptoTransmissionNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot(), remoteParticipant);
                            // pass the metada to a pool wainting for the response of the other peer or server failure
                            poolConnectionsWaitingForResponse.put(cryptoTransmissionMetadata.getDestinationPublicKey(), cryptoTransmissionMetadata);

                        }
                    }
                }else{

                    NetworkServiceLocal communicationNetworkServiceLocal = cryptoTransmissionNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getDestinationPublicKey());

                    if (communicationNetworkServiceLocal != null) {

                        try {

                            Gson gson = new Gson();
                            String jsonMetadata = null;
                            CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage = null;
                            switch (cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates()){
                                case SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE:
                                    jsonMetadata = gson.toJson(cryptoTransmissionMetadata);
                                    System.out.print("-----------------------\n" +
                                            "ENVIANDO CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n A: " + cryptoTransmissionMetadata.getDestinationPublicKey());

                                    communicationNetworkServiceLocal.sendMessage(cryptoTransmissionMetadata.getSenderPublicKey(),cryptoTransmissionMetadata.getDestinationPublicKey(),jsonMetadata);

                                    break;
                                case SEEN_BY_DESTINATION_VAULT:
                                    // Notifico recepcion de metadata
                                    cryptoTransmissionResponseMessage = new CryptoTransmissionResponseMessage(
                                            cryptoTransmissionMetadata.getTransactionId(),
                                            CryptoTransmissionProtocolState.SENT,
                                            CryptoTransmissionMetadataType.METADATA_SEND,
                                            CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_VAULT);
                                    jsonMetadata = gson.toJson(cryptoTransmissionResponseMessage);
                                    System.out.print("-----------------------\n" +
                                            "ENVIANDO RESPUESTA CRYPTO METADATA SEEN_BY_DESTINATION_VAULT -----------------------\n" +
                                            "-----------------------\n A: " + cryptoTransmissionMetadata.getDestinationPublicKey());

                                    communicationNetworkServiceLocal.sendMessage(cryptoTransmissionMetadata.getDestinationPublicKey(),cryptoTransmissionMetadata.getSenderPublicKey(),jsonMetadata);

                                    break;
                                case CREDITED_IN_DESTINATION_WALLET:
                                    // Notifico recepcion de metadata
                                    cryptoTransmissionResponseMessage = new CryptoTransmissionResponseMessage(
                                            cryptoTransmissionMetadata.getTransactionId(),
                                            CryptoTransmissionProtocolState.SENT,
                                            CryptoTransmissionMetadataType.METADATA_SEND,
                                            CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
                                    jsonMetadata = gson.toJson(cryptoTransmissionResponseMessage);

                                    System.out.print("-----------------------\n" +
                                            "ENVIANDO RESPUESTA CRYPTO METADATA CREDITED_IN_DESTINATION_WALLET -----------------------\n" +
                                            "-----------------------\n A: " + cryptoTransmissionMetadata.getDestinationPublicKey());

                                    communicationNetworkServiceLocal.sendMessage(cryptoTransmissionMetadata.getDestinationPublicKey(),cryptoTransmissionMetadata.getSenderPublicKey(),jsonMetadata);

                                    break;

                            }
                            //Cambio estado de base de datos a PROCESSING_SEND_COMMUNICATION_DATABASE
                            outgoingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolState(
                                    cryptoTransmissionMetadata.getTransactionId(),
                                    CryptoTransmissionProtocolState.SENT_TO_COMMUNICATION_TEMPLATE);

                            System.out.print("-----------------------\n" +
                                    "CRYPTO METADATA!!!!! -----------------------\n" +
                                    "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());

                        //} catch (CantUpdateRecordDataBaseException e) {
                          //  e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        } catch (CantEstablishConnectionException e) {
            e.printStackTrace();
        }
    }

    private void discountWaitTime(){
        if(!waitingPlatformComponentProfile.isEmpty()) {
            for (CryptoTransmissionPlatformComponentProfilePlusWaitTime cryptoTransmissionPlatformComponentProfilePlusWaitTime : waitingPlatformComponentProfile.values()) {
                cryptoTransmissionPlatformComponentProfilePlusWaitTime.WaitTimeDown();
                if (cryptoTransmissionPlatformComponentProfilePlusWaitTime.getWaitTime() <= 0) {
                    remoteNetworkServicesRegisteredList.add(cryptoTransmissionPlatformComponentProfilePlusWaitTime.getPlatformComponentProfile());
                    waitingPlatformComponentProfile.remove(cryptoTransmissionPlatformComponentProfilePlusWaitTime);

                }
            }
        }
    }



    public void addRemoteNetworkServicesRegisteredList(List<PlatformComponentProfile> list){
        remoteNetworkServicesRegisteredList = list;
    }

    // Este agente se usa por las dudas que no se haya escuchado el evento del receive que guarda las cosas en la db de la metadata recibida
    private void receiveCycle(){
        try {
            if(running.get()) {
                // function to process metadata received
                processReceive();
            }

//            if(toReceive.isInterrupted() == Boolean.FALSE){
            Thread.sleep(CryptoTransmissionAgent.SLEEP_TIME);
//            }

        } catch (InterruptedException e) {
            running.set(false);
            resume();
            System.out.println("CryptoTransmissionAgent - Thread Interrupted stopped ...  ");
        }

    }

    private void processReceive() {

        try {
            //communicationNetworkServiceConnectionManager.

            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME, "false");

             /*
             * Read all pending CryptoTransmissionMetadata from database
             */
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = incomingCryptoTransmissionMetadataDAO.findAll(filters);


            for(CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata){

             //   CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getSenderPublicKey());

              //  if(communicationNetworkServiceLocal!=null){

                    System.out.print("-----------------------\n" +
                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());

                    // si no contiene la metadata, la tengo que guardar en la bd y notificar que llegó, tambien debería cargar ese caché cuando se lanza el evento de que llega la metadata de respuesta
                    // if( ! cacheResponseMetadataFromRemotes.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())){
                    try {
                        // lo cambio directo porque la metadata viene con un mensaje de estado distinto, actualizado
                        switch (cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates()) {

                            case SEEN_BY_DESTINATION_NETWORK_SERVICE:
                                //guardo estado
                                // deberia ver si tengo que lanzar un evento acá
                                incomingCryptoTransmissionMetadataDAO.changeCryptoTransmissionProtocolState(
                                        cryptoTransmissionMetadata.getTransactionId(),
                                        CryptoTransmissionProtocolState.RECEIVED);
                                //TODO: for test reason
                                lauchNotification();
                                System.out.print("-----------------------\n" +
                                        "ACA DEBERIA LANZAR EVENTO NO CREO  -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());
                                System.out.print("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");
                                break;

                            case SEEN_BY_DESTINATION_VAULT:
                                // deberia ver si tengo que lanzar un evento acá
                                System.out.print("-----------------------\n" +
                                        "ACA DEBERIA LANZAR EVENTO NO CREO -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());
                                System.out.print("CryptoTransmission SEEN_BY_DESTINATION_VAULT event");

                                cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState.RECEIVED);
                                cryptoTransmissionMetadata.changeMetadataState(CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_VAULT);
                                cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);
                                outgoingCryptoTransmissionMetadataDAO.update(cryptoTransmissionMetadata);

                                System.out.print("-----------------------\n" +
                                        "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());

                                lauchNotification();
                                break;

                            case CREDITED_IN_DESTINATION_WALLET:
                                // Guardo estado
                                System.out.print("-----------------------\n" +
                                        "ACA DEBERIA LANZAR EVENTO NO CREO -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());
                                // deberia ver si tengo que lanzar un evento acá
                                //para el outgoing intra user

                                outgoingCryptoTransmissionMetadataDAO.doneTransaction(cryptoTransmissionMetadata.getTransactionId());

                                System.out.print("-----------------------\n" +
                                        "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());


                                System.out.print("CryptoTransmission CREDITED_IN_DESTINATION_WALLET event");
                                this.poolConnectionsWaitingForResponse.remove(cryptoTransmissionMetadata.getDestinationPublicKey());
                                break;
                            // si el mensaje viene con un estado de SENT es porque es la primera vez que llega, por lo que tengo que guardarlo en la bd y responder
                            case SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE:

                                incomingCryptoTransmissionMetadataDAO.changeTransactionStateAndProtocolState(
                                        cryptoTransmissionMetadata.getTransactionId(),
                                        CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_NETWORK_SERVICE,
                                        CryptoTransmissionProtocolState.RECEIVED);

                                System.out.print("-----------------------\n" +
                                        "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());

                                lauchNotification();

                                // El destination soy yo porque me lo estan enviando
                                // El sender es el otro y es a quien le voy a responder
                                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getSenderPublicKey());
                                if(communicationNetworkServiceLocal!=null) {
                                    // Notifico recepcion de metadata
                                    CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage = new CryptoTransmissionResponseMessage(
                                            cryptoTransmissionMetadata.getTransactionId(),
                                            CryptoTransmissionProtocolState.SENT,
                                            CryptoTransmissionMetadataType.METADATA_SEND,
                                            CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_NETWORK_SERVICE);

                                    Gson gson = new Gson();
                                    String message = gson.toJson(cryptoTransmissionResponseMessage);
                                    communicationNetworkServiceLocal.sendMessage(cryptoTransmissionMetadata.getDestinationPublicKey(), cryptoTransmissionMetadata.getSenderPublicKey(), message);
                                    System.out.print("-----------------------\n" +
                                            "ENVIANDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());
                                }else{
                                    System.out.print("-----------------------\n" +
                                            "INTENTO DE RESPUESTA DENEGADO, NO HAY CONEXION CON EL OTRO TRANSMISSION -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());
                                }
                                break;
                            default:
                                System.out.print("-----------------------\n" +
                                        "TE ESTAS YENDO POR EL DEFAULT MATI T!!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());
                                break;
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }





        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

    }

    public void connectionFailure(String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }

    public boolean isConnection(String publicKey){
        return this.poolConnectionsWaitingForResponse.containsKey(publicKey);
    }

    private void lauchNotification(){
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_METADATA);
        IncomingCryptoMetadataEvent incomingCryptoMetadataReceive = (IncomingCryptoMetadataEvent) fermatEvent;
        incomingCryptoMetadataReceive.setSource(EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION);
        eventManager.raiseEvent(incomingCryptoMetadataReceive);
    }



}