package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionStates;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.events.IncomingCryptoMetadataReceive;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events.ActorNetworkServicePendingsNotificationEvent;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.CryptoTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.CryptoTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao.CryptoTransmissionConnectionsDAO;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao.CryptoTransmissionMetadataDAO;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantInitializeCryptoTransmissionNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantSaveCryptoTransmissionMetadatatException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingCryptoMetadataEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot;

    /**
     * DAO CryptoTransmission
     */
    CryptoTransmissionMetadataDAO cryptoTransmissionMetadataDAO;
    CryptoTransmissionConnectionsDAO cryptoTransmissionConnectionsDAO;

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
    private Thread toReceive;

    /**
     * Cache de metadata con conexions leidas anteriormente
     * ActorPublicKey, metadata de respuesta
     */
    Map<String, CryptoTransmissionStates> cacheResponseMetadataFromRemotes;

    /**
     * Mapa con el publicKey del componentProfile al cual me quiero conectar
     *  y las veces que intenté conectarme para saltearlo si no está conectado
     */
    Map<String,Integer> connectionsCounters;

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
     * @param cryptoTransmissionMetadataDAO
     * @param communicationNetworkServiceConnectionManager
     * @param errorManager
     */

    public CryptoTransmissionAgent(
            //CryptoTransmissionNetworkServiceLocal communicationNetworkServiceLocal,
            CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot,
            CryptoTransmissionConnectionsDAO cryptoTransmissionConnectionsDAO,
            CryptoTransmissionMetadataDAO cryptoTransmissionMetadataDAO,
            CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
            WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,
            PlatformComponentProfile platformComponentProfile,
            ErrorManager errorManager,
            List<PlatformComponentProfile> remoteNetworkServicesRegisteredList,
            ECCKeyPair identity,
            EventManager eventManager) {

        //this.communicationNetworkServiceLocal = communicationNetworkServiceLocal;
        this.cryptoTransmissionNetworkServicePluginRoot = cryptoTransmissionNetworkServicePluginRoot;
        this.cryptoTransmissionConnectionsDAO = cryptoTransmissionConnectionsDAO;
        this.cryptoTransmissionMetadataDAO = cryptoTransmissionMetadataDAO;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.wsCommunicationsCloudClientManager =wsCommunicationsCloudClientManager;
        this.errorManager = errorManager;
        this.remoteNetworkServicesRegisteredList = remoteNetworkServicesRegisteredList;
        this.identity = identity;
        this.platformComponentProfile = platformComponentProfile;
        this.eventManager = eventManager;


        cacheResponseMetadataFromRemotes = new HashMap<String, CryptoTransmissionStates>();
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
        this.toReceive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running)
                    receiveCycle();
            }
        });

    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        //Set to running
        this.running  = Boolean.TRUE;

        try {
            cryptoTransmissionMetadataDAO.initialize();
        } catch (CantInitializeCryptoTransmissionNetworkServiceDatabaseException e) {
            e.printStackTrace();
        }

        //Start the Thread
        toSend.start();
        toReceive.start();

        System.out.println("CryptoTransmissionAgent - started ");

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
        toReceive.interrupt();
        //Disconnect from the service
        //serviceToServiceOnlineConnection.disconnect();
    }




    /**
     *
     * Lifeclycle of the networkService Cryptotransmission.
     * This thread controls the send circuit saving each states and metadata in database
     *
     */
    public void sendCycle(){

        try {

            if(cryptoTransmissionNetworkServicePluginRoot.isRegister()) {
                // deberia dividirlo en funciones mas pequeñas despues

                // function to process and send metadata to remote
                processMetadata();

                // this function read the active conections waiting for response that the events not catch
                //readResponseFromRemote();


                //Discount from the the waiting list
                discountWaitTime();
            }

            if(toReceive.isInterrupted() == Boolean.FALSE){
                //Sleep for a time
                toSend.sleep(CryptoTransmissionAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            toSend.interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
            return;        }

    }

    private void processMetadata() {

        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionStates.PRE_PROCESSING_SEND.getCode());
            //filters.put(ComunicationLayerNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, remoteNetworkServicePublicKey);

         /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = cryptoTransmissionMetadataDAO.findAll(filters);


                for (CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata) {


                    if(!poolConnectionsWaitingForResponse.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())) {

                        //TODO: hacer un filtro por aquellas que se encuentran conectadas

//                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory()
//                PlatformComponentProfile platformComponentProfile = new Pl

                        if (communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getDestinationPublicKey()) == null) {


                            if (wsCommunicationsCloudClientManager != null) {

                                if (platformComponentProfile != null) {

                                    PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(cryptoTransmissionMetadata.getSenderPublicKey(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_INTRA_USER);
                                    PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(cryptoTransmissionMetadata.getDestinationPublicKey(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_INTRA_USER);
                                    communicationNetworkServiceConnectionManager.connectTo(applicantParticipant, platformComponentProfile, remoteParticipant);

                                    // pass the metada to a pool wainting for the response of the other peer or server failure
                                    poolConnectionsWaitingForResponse.put(cryptoTransmissionMetadata.getDestinationPublicKey(), cryptoTransmissionMetadata);
                                }

                            }
                        }
                    }else{

                        NetworkServiceLocal communicationNetworkServiceLocal = cryptoTransmissionNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getDestinationPublicKey());

                        if (communicationNetworkServiceLocal != null) {

                                try {


                                    //Cambio estado de base de datos a PROCESSING_SEND_COMMUNICATION_DATABASE
                                    cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SENT);

                                    System.out.print("-----------------------\n" +
                                            "ENVIANDO CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n A: " + cryptoTransmissionMetadata.getDestinationPublicKey());

                                    // Si se encuentra conectado paso la metadata al dao de la capa de comunicacion para que lo envie
                                    Gson gson = new Gson();
                                    String jsonMetadata = gson.toJson(cryptoTransmissionMetadata);

                                    // Envio el mensaje a la capa de comunicacion

                                    communicationNetworkServiceLocal.sendMessage(identity.getPublicKey(),cryptoTransmissionMetadata.getDestinationPublicKey(),jsonMetadata);

                                    //cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.PROCESSING_SEND_COMMUNICATION_TEMPLATE);

                                    cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);

                                    System.out.print("-----------------------\n" +
                                            "CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());

                                } catch (CantUpdateRecordDataBaseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                        }
                    }
                }





            //wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParameters);

            /**
             *  De los componentes registrados me fijo cuales trae el discovery y cuales tengo que chequear si se encuentran conectados o los tengo que conectar
             */

            /**
             * Primero me fijo y las conecto
             //             */
//            for (PlatformComponentProfile remoteComponentProfile:remoteNetworkServicesRegisteredList){
//
//                boolean flag=false;
//                int counter = 0;
//                while(!flag && counter<lstCryptoTransmissionMetadata.size()){
//
//                    CryptoTransmissionMetadata cryptoTransmissionMetadata = lstCryptoTransmissionMetadata.get(counter);
//
//                    //TODO: chequear si el componente proviene de ese actor, si proviene y no está conectado tengo que hacer la conexión, si está conectado tengo que hacer el send
//                    //TODO: estoy tomando el getIdentityPublicKey como si fuera la key del actor, cuando Robert lo tenga lo cambió
//
//                    //TODO: LISTO
//                    if(remoteComponentProfile.getIdentityPublicKey().equals(cryptoTransmissionNetworkServicePluginRoot.isRegisteredConnectionForActorPK(cryptoTransmissionMetadata.getDestinationPublicKey()))){
//                        /*
//                        * Get the local representation of the remote network service
//                        *
//                        *  Te devuelve nullsi no existe la oonexion, si cryptoTransmissionNetworkServiceLocal te devuelve algo diferente quiere decir que está conectado
//                        */
//                        CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteComponentProfile.getIdentityPublicKey());
//
//                        if(communicationNetworkServiceLocal==null){
//
//                            if(!connectionsCounters.containsKey(remoteComponentProfile.getIdentityPublicKey())){
//                                connectionsCounters.put(remoteComponentProfile.getIdentityPublicKey(),0);
//                            }else{
//
//                                int connectionCounter = connectionsCounters.get(remoteComponentProfile.getIdentityPublicKey());
//
//                                if(connectionCounter>5 && connectionCounter<10){
//                                    connectionCounter++;
//                                    continue;
//                                }else if(connectionCounter>10){
//                                    connectionCounter++;
//                                    // lo paso a los de espera
//                                    CryptoTransmissionPlatformComponentProfilePlusWaitTime cryptoTransmissionPlatformComponentProfilePlusWaitTime = new CryptoTransmissionPlatformComponentProfilePlusWaitTime(
//                                            remoteComponentProfile,
//                                            CryptoTransmissionPlatformComponentProfilePlusWaitTime.WAIT_TIME_MIN);
//
//                                    waitingPlatformComponentProfile.put(cryptoTransmissionMetadata.getDestinationPublicKey(),cryptoTransmissionPlatformComponentProfilePlusWaitTime);
//                                    remoteNetworkServicesRegisteredList.remove(remoteComponentProfile);
//
//                                }
//                            }
//                            // Contador de intentos por componentes profile para subir el tiempo de espera del hilo
//
//
//                            // Establezco la conexion
//                            communicationNetworkServiceConnectionManager.connectTo(remoteComponentProfile);
//
//
//
//
//
//                        }else{
//
//                            try {
//                            // Si se encuentra conectado paso la metadata al dao de la capa de comunicacion para que lo envie
//                            Gson gson = new Gson();
//                            String jsonMetadata = gson.toJson(cryptoTransmissionMetadata);
//
//                            // Envio el mensaje a la capa de comunicacion
//
//                                communicationNetworkServiceLocal.sendMessage(jsonMetadata, identity);
//
//                            //Cambio estado de base de datos a PROCESSING_SEND_COMMUNICATION_DATABASE
//                            cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.PROCESSING_SEND_COMMUNICATION_TEMPLATE);
//
//                                System.out.print("-----------------------\n" +
//                                        "ENVIANDO CRYPTO METADATA!!!!! -----------------------\n" +
//                                        "-----------------------\n A: " + cryptoTransmissionMetadata.getDestinationPublicKey());
//
//                                cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.PROCESSING_SEND_COMMUNICATION_TEMPLATE);
//
//                                cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);
//
//                                System.out.print("-----------------------\n" +
//                                        "CRYPTO METADATA!!!!! -----------------------\n" +
//                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
//
//                            } catch (CantUpdateRecordDataBaseException e) {
//                                e.printStackTrace();
//                            } catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//                    counter++;
//                }

            //}


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
            // function to process metadata received
            processReceive();

            if(toSend.isInterrupted() == Boolean.FALSE){
                toReceive.sleep(CryptoTransmissionAgent.RECEIVE_SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            toReceive.interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
            return;        }

    }

    private void processReceive() {

        try {
        //communicationNetworkServiceConnectionManager.

        Map<String, Object> filters = new HashMap<>();
        filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME, "false");

         /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = cryptoTransmissionMetadataDAO.findAll(filters);


            for(CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata){

                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getSenderPublicKey());

                if(communicationNetworkServiceLocal!=null){

                    System.out.print("-----------------------\n" +
                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());

                    // si no contiene la metadata, la tengo que guardar en la bd y notificar que llegó, tambien debería cargar ese caché cuando se lanza el evento de que llega la metadata de respuesta
                   // if( ! cacheResponseMetadataFromRemotes.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())){

                        try {
                            // lo cambio directo porque la metadata viene con un mensaje de estado distinto, actualizado


                            switch (cryptoTransmissionMetadata.getCryptoTransmissionStates()) {

                                case SEEN_BY_DESTINATION_NETWORK_SERVICE:
                                    //guardo estado
                                    // deberia ver si tengo que lanzar un evento acá


                                    System.out.print("-----------------------\n" +
                                            "ACA DEBERIA LANZAR EVENTO NO CREO  -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    System.out.print("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");
                                    break;

                                case SEEN_BY_DESTINATION_VAULT:
                                    // deberia ver si tengo que lanzar un evento acá
                                    System.out.print("-----------------------\n" +
                                            "ACA DEBERIA LANZAR EVENTO NO CREO -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    System.out.print("CryptoTransmission SEEN_BY_DESTINATION_VAULT event");

                                    cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_DESTINATION_VAULT);
                                    cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);
                                    cryptoTransmissionMetadataDAO.update(cryptoTransmissionMetadata);

                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());

                                    lauchNotification();
                                    break;

                                case CREDITED_IN_DESTINATION_WALLET:
                                    // Guardo estado
                                    System.out.print("-----------------------\n" +
                                            "ACA DEBERIA LANZAR EVENTO NO CREO -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    // deberia ver si tengo que lanzar un evento acá
                                    //para el outgoing intra user

                                  //  cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.CREDITED_IN_DESTINATION_WALLET);
                                  //  cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);
                                  //  cryptoTransmissionMetadataDAO.update(cryptoTransmissionMetadata);

                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());


                                    System.out.print("CryptoTransmission CREDITED_IN_DESTINATION_WALLET event");
                                    this.poolConnectionsWaitingForResponse.remove(cryptoTransmissionMetadata.getDestinationPublicKey());
                                    break;
                                // si el mensaje viene con un estado de SENT es porque es la primera vez que llega, por lo que tengo que guardarlo en la bd y responder
                                case SENT:

                                    cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_OWN_NETWORK_SERVICE);
                                    cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);
                                    cryptoTransmissionMetadataDAO.update(cryptoTransmissionMetadata);

                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());

                                    lauchNotification();

                                    // Notifico recepcion de metadata
                                    CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage = new CryptoTransmissionResponseMessage(
                                            cryptoTransmissionMetadata.getTransactionId(),
                                            CryptoTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE,
                                            CryptoTransmissionMetadataType.METADATA_SEND);
                                    Gson gson = new Gson();

                                    String message = gson.toJson(cryptoTransmissionResponseMessage);

                                    // El destination soy yo porque me lo estan enviando
                                    // El sender es el otro y es a quien le voy a responder

                                    communicationNetworkServiceLocal.sendMessage(cryptoTransmissionMetadata.getDestinationPublicKey(),cryptoTransmissionMetadata.getSenderPublicKey(), message);


                                    System.out.print("-----------------------\n" +
                                            "ENVIANDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    break;
                                default:
                                    System.out.print("-----------------------\n" +
                                            "TE ESTAS YENDO POR EL DEFAULT MATI T!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    break;
                            }
                          //  cacheResponseMetadataFromRemotes.put(cryptoTransmissionMetadata.getDestinationPublicKey(), cryptoTransmissionMetadata.getCryptoTransmissionStates());

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }


            }


        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

    }

    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile){
        this.platformComponentProfile = platformComponentProfile;
    }



    public void connectionFailure(String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }


    public boolean isConnection(String publicKey){
        return this.poolConnectionsWaitingForResponse.containsKey(publicKey);
    }

    public boolean isRunning(){
        return running;
    }

    private void lauchNotification(){
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_METADATA);
        IncomingCryptoMetadataEvent incomingCryptoMetadataReceive = (IncomingCryptoMetadataEvent) fermatEvent;
        incomingCryptoMetadataReceive.setSource(EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION);
        eventManager.raiseEvent(incomingCryptoMetadataReceive);
    }

}
