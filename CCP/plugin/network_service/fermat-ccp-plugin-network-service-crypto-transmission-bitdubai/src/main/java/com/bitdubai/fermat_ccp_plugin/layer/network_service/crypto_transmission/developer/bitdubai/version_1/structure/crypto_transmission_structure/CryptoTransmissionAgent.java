package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.enums.CryptoTransmissionStates;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;
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
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.05..
 */
public class CryptoTransmissionAgent {

    /*
    * Represent the sleep time for the read or send (2000 milliseconds)
    */
    private static final long SLEEP_TIME = 6000;

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
            ECCKeyPair identity) {

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

            //Sleep for a time
            toSend.sleep(CryptoTransmissionAgent.SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        }

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






            /*
             * Por cada mensaje en la base de datos que se encuetra en estado de procesado
             * y que no se encuentra conectado (me tengo que fijar en el cryptoTransmissionNetworkServiceConnectionManager que la conexion para enviar la metadata no esté activa)
             * hago un discovery por cada metadata a enviar y cargo el remoteNetworkServicesRegisteredList con la lista de conexiones entrantes
             */


                for (CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata) {


                    if(!poolConnectionsWaitingForResponse.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())) {

                        //TODO: hacer un filtro por aquellas que se encuentran conectadas

//                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory()
//                PlatformComponentProfile platformComponentProfile = new Pl

                        if (communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getDestinationPublicKey()) == null) {


                            if (wsCommunicationsCloudClientManager != null) {

                                if (platformComponentProfile != null) {


                                    DiscoveryQueryParameters discoveryQueryParameters = wsCommunicationsCloudClientManager.
                                            getCommunicationsCloudClientConnection().
                                            constructDiscoveryQueryParamsFactory(
                                                    PlatformComponentType.NETWORK_SERVICE,//applicant = who made the request
                                                    NetworkServiceType.CRYPTO_TRANSMISSION,
                                                    null,                     // alias
                                                    "actor_prueba_robert_public_key", // identityPublicKey
                                                    null,                     // location
                                                    null,                     // distance
                                                    null,                     // name
                                                    null,                     // extraData
                                                    null,                     // offset
                                                    null,                     // max
                                                    PlatformComponentType.ACTOR,        // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                                                    NetworkServiceType.UNDEFINED); // fromOtherNetworkServiceType,    when use this filter apply the identityPublicKey

                                    //TODO: poner tipo de actor, PlatformComponenType.ActorTtype



                                    communicationNetworkServiceConnectionManager.connectTo(cryptoTransmissionMetadata.getSenderPublicKey(), platformComponentProfile, discoveryQueryParameters);


                                    // pass the metada to a pool wainting for the response of the other peer or server failure
                                    poolConnectionsWaitingForResponse.put(cryptoTransmissionMetadata.getDestinationPublicKey(), cryptoTransmissionMetadata);
                                }

                            }
                        }
                    }
                }



            NetworkServiceLocal communicationNetworkServiceLocal = cryptoTransmissionNetworkServicePluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance("actor_prueba_robert_public_key");

            if (communicationNetworkServiceLocal != null) {

                for (CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata) {

                    try {
                        // Si se encuentra conectado paso la metadata al dao de la capa de comunicacion para que lo envie
                        Gson gson = new Gson();
                        String jsonMetadata = gson.toJson(cryptoTransmissionMetadata);

                        // Envio el mensaje a la capa de comunicacion

                        communicationNetworkServiceLocal.sendMessage(identity.getPublicKey(), jsonMetadata);


                        //Cambio estado de base de datos a PROCESSING_SEND_COMMUNICATION_DATABASE
                        cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.PROCESSING_SEND_COMMUNICATION_TEMPLATE);

                        System.out.print("-----------------------\n" +
                                "ENVIANDO CRYPTO METADATA!!!!! -----------------------\n" +
                                "-----------------------\n A: " + cryptoTransmissionMetadata.getDestinationPublicKey());

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


//    private List<CryptoTransmissionMetadata> checkIpkConnected(List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata){
//        List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadataNotConnected = new ArrayList<CryptoTransmissionMetadata>();
//
//        for (CryptoTransmissionMetadata cryptoTransmissionMetadata: lstCryptoTransmissionMetadata){
//            cryptoTransmissionNetworkServiceConnectionManager.
//        }
//
//    }

    // Este agente se usa por las dudas que no se haya escuchado el evento del receive que guarda las cosas en la db de la metadata recibida
    private void receiveCycle(){

        try {


            // function to process metadata received
            processReceive();


            //Sleep for a time
            toSend.sleep(CryptoTransmissionAgent.SLEEP_TIME);

        } catch (InterruptedException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        }

    }


    private void processReceive() {

        try {
        //communicationNetworkServiceConnectionManager.

        Map<String, Object> filters = new HashMap<>();
        filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionStates.PROCESSING_RECEIVE.getCode());

            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionMetadataType.METADATA_RECEIVE.getCode());
        //filters.put(ComunicationLayerNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME, remoteNetworkServicePublicKey);

         /*
         * Read all pending CryptoTransmissionMetadata from database
         */


            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = cryptoTransmissionMetadataDAO.findAll(filters);


            for(CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata){

                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getSenderPublicKey());

                if(communicationNetworkServiceLocal!=null){
                    //FermatMessage fermatMessage =  communicationNetworkServiceLocal.getLastMessageReceived();
                    //String messageContent = fermatMessage.getContent();
                    //Gson gson = new Gson();
                    //CryptoTransmissionMetadata cryptoTransmissionMetadata = gson.fromJson(messageContent, CryptoTransmissionMetadata.class);


                    System.out.print("-----------------------\n" +
                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());

                    // si no contiene la metadata, la tengo que guardar en la bd y notificar que llegó, tambien debería cargar ese caché cuando se lanza el evento de que llega la metadata de respuesta
                   // if( ! cacheResponseMetadataFromRemotes.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())){

                        try {
                            // lo cambio directo porque la metadata viene con un mensaje de estado distinto, actualizado

                            switch (cryptoTransmissionMetadata.getCryptoTransmissionStates()) {
                                case PROCESSING_RECEIVE:
                                    cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);
                                    // deberia ver si tengo que lanzar un evento acá
                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    System.out.print("CryptoTransmission PROCESSING_RECEIVE event");
                                    break;

                                case SEEN_BY_DESTINATION_NETWORK_SERVICE:
                                    cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE);
                                    cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);
                                    // deberia ver si tengo que lanzar un evento acá
                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    System.out.print("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");
                                    break;
                                case SEEN_BY_DESTINATION_VAULT:
                                    // deberia ver si tengo que lanzar un evento acá
                                    cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_DESTINATION_VAULT);
                                    cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);
                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    System.out.print("CryptoTransmission SEEN_BY_DESTINATION_VAULT event");
                                    break;
                                case CREDITED_IN_DESTINATION_WALLET:
                                    cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.CREDITED_IN_DESTINATION_WALLET);
                                    cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);
                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    // deberia ver si tengo que lanzar un evento acá
                                    System.out.print("CryptoTransmission CREDITED_IN_DESTINATION_WALLET event");
                                    break;
                                // si el mensaje viene con un estado de SENT es porque es la primera vez que llega, por lo que tengo que guardarlo en la bd y responder
                                case SENT:
                                    cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE);
                                    cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);
                                    cryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
                                    System.out.print("-----------------------\n" +
                                            "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());

                                    // Notifico recepcion de metadata
                                    cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_SEND);
                                    Gson gson = new Gson();
                                    String message = gson.toJson(cryptoTransmissionMetadata);
                                    communicationNetworkServiceLocal.sendMessage(cryptoTransmissionMetadata.getSenderPublicKey(),message);
                                    System.out.print("-----------------------\n" +
                                            "ENVIANDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
                                    break;
                            }
                          //  cacheResponseMetadataFromRemotes.put(cryptoTransmissionMetadata.getDestinationPublicKey(), cryptoTransmissionMetadata.getCryptoTransmissionStates());

                        } catch (CantUpdateRecordDataBaseException e) {
                            e.printStackTrace();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }


            }


       // for (PlatformComponentProfile remoteComponentProfile:remoteNetworkServicesRegisteredList){
            //Me fijo cuales estan conectados
            //TODO: preguntar si se pueden separar las dos listas o conviene trabajar todo desde acá con las otras respuestas tambien
//            CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteComponentProfile.getIdentityPublicKey());
//            if(communicationNetworkServiceLocal!=null){
//                FermatMessage fermatMessage =  communicationNetworkServiceLocal.getLastMessageReceived();
//                String messageContent = fermatMessage.getContent();
//                Gson gson = new Gson();
//                CryptoTransmissionMetadata cryptoTransmissionMetadata = gson.fromJson(messageContent, CryptoTransmissionMetadata.class);
//
//
//                System.out.print("-----------------------\n" +
//                        "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
//                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
//
//                // si no contiene la metadata, la tengo que guardar en la bd y notificar que llegó, tambien debería cargar ese caché cuando se lanza el evento de que llega la metadata de respuesta
//                if( ! cacheResponseMetadataFromRemotes.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())){
//
//                    try {
//                        // lo cambio directo porque la metadata viene con un mensaje de estado distinto, actualizado
//
//                        switch (cryptoTransmissionMetadata.getCryptoTransmissionStates()) {
//                            case SEEN_BY_DESTINATION_NETWORK_SERVICE:
//                                cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE);
//                                cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);
//                                // deberia ver si tengo que lanzar un evento acá
//                                System.out.print("-----------------------\n" +
//                                        "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
//                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
//                                System.out.print("CryptoTransmission SEEN_BY_DESTINATION_NETWORK_SERVICE event");
//                                break;
//                            case SEEN_BY_DESTINATION_VAULT:
//                                // deberia ver si tengo que lanzar un evento acá
//                                cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_DESTINATION_VAULT);
//                                cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);
//                                System.out.print("-----------------------\n" +
//                                        "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
//                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
//                                System.out.print("CryptoTransmission SEEN_BY_DESTINATION_VAULT event");
//                                break;
//                            case CREDITED_IN_DESTINATION_WALLET:
//                                cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.CREDITED_IN_DESTINATION_WALLET);
//                                cryptoTransmissionMetadataDAO.changeState(cryptoTransmissionMetadata);
//                                System.out.print("-----------------------\n" +
//                                        "RECIVIENDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
//                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
//                                // deberia ver si tengo que lanzar un evento acá
//                                System.out.print("CryptoTransmission CREDITED_IN_DESTINATION_WALLET event");
//                                break;
//                            // si el mensaje viene con un estado de SENT es porque es la primera vez que llega, por lo que tengo que guardarlo en la bd y responder
//                            case SENT:
//                                cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE);
//                                cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);
//                                cryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);
//                                System.out.print("-----------------------\n" +
//                                        "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
//                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
//
//                                // Notifico recepcion de metadata
//                                cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_SEND);
//                                gson = new Gson();
//                                String message = gson.toJson(cryptoTransmissionMetadata);
//                                communicationNetworkServiceLocal.sendMessage(message,identity);
//                                System.out.print("-----------------------\n" +
//                                        "ENVIANDO RESPUESTA CRYPTO METADATA!!!!! -----------------------\n" +
//                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());
//                                break;
//                        }
//                        cacheResponseMetadataFromRemotes.put(cryptoTransmissionMetadata.getDestinationPublicKey(), cryptoTransmissionMetadata.getCryptoTransmissionStates());
//
//                    } catch (CantUpdateRecordDataBaseException e) {
//                        e.printStackTrace();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
        //    }
        //}

        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
        }

    }

    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile){
        this.platformComponentProfile = platformComponentProfile;
    }

    public void handleNewMessages(FermatMessage fermatMessage){

        Gson gson = new Gson();

        try {

            CryptoTransmissionMetadata cryptoTransmissionMetadata = gson.fromJson(fermatMessage.getContent(), CryptoTransmissionMetadataRecord.class);

            cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.PROCESSING_RECEIVE);

            cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_RECEIVE);

            cryptoTransmissionMetadataDAO.saveCryptoTransmissionMetadata(cryptoTransmissionMetadata);

            System.out.print("-----------------------\n" +
                    "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                    "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());

            CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getSenderPublicKey());


            cryptoTransmissionMetadata.changeState(CryptoTransmissionStates.SEEN_BY_DESTINATION_NETWORK_SERVICE);

            cryptoTransmissionMetadata.setTypeMetadata(CryptoTransmissionMetadataType.METADATA_SEND);

            communicationNetworkServiceLocal.sendMessage(cryptoTransmissionMetadata.getSenderPublicKey(),gson.toJson(cryptoTransmissionMetadata));

            System.out.print("-----------------------\n" +
                    "RESPONDIENDO ENVIO DE CRYPTO METADATA!!!!! -----------------------\n" +
                    "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionStates());



        } catch (CantSaveCryptoTransmissionMetadatatException e) {
            e.printStackTrace();
        }
    }

    public void connectionFailure(String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }


    public boolean isConnection(String publicKey){
        return this.poolConnectionsWaitingForResponse.containsKey(publicKey);
    }

}
