package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.CryptoTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.CryptoTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
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


    private static final int SEND_TASK = 0;
    private static final int RECEIVE_TASK = 1;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represent is the tread is running
     */
    private AtomicBoolean running;

    /**
     * plugin root
     */
    CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot;

    /**
     * Represent the send cycle tread of this NetworkService
     */
    private Runnable toSend;
    /**
     * Represent the send messages tread of this TemplateNetworkServiceRemoteAgent
     */
    private Runnable toReceive;

    private Future<?>[] futures= new Future[2];
    private final ExecutorService threadPoolExecutor;

    /**
     * Pool connections requested waiting for peer or server response
     *
     * publicKey  and transaccion metadata waiting to be a response
     */
    Map<String,CryptoTransmissionMetadata> poolConnectionsWaitingForResponse;

    private  EventManager eventManager;
    /**
     *  Constructor
     *  @param
     * @param cryptoTransmissionNetworkServicePluginRoot
     * @param errorManager
     */

    public CryptoTransmissionAgent(
            CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot,
            ErrorManager errorManager,
            EventManager eventManager) {

        this.cryptoTransmissionNetworkServicePluginRoot = cryptoTransmissionNetworkServicePluginRoot;
        this.errorManager = errorManager;
        this.eventManager = eventManager;

        threadPoolExecutor = Executors.newFixedThreadPool(2);


        poolConnectionsWaitingForResponse = new HashMap<> ();

        //Create a thread to send the messages
        this.toSend = new Runnable() {
            @Override
            public void run() {
                while (true)
                    sendCycle();
            }
        };

        //Create a thread to receive the messages
        this.toReceive = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    receiveCycle();
                }
            }
        };

    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        try {
            //Set to running
            this.running = new AtomicBoolean(true);

            if (futures != null) {
                if (futures[SEND_TASK] != null) futures[SEND_TASK].cancel(true);
                if (futures[RECEIVE_TASK] != null) futures[RECEIVE_TASK].cancel(true);
            }
            //Start the Thread
            assert futures != null;
            futures[SEND_TASK] = threadPoolExecutor.submit(toSend);
            futures[RECEIVE_TASK] = threadPoolExecutor.submit(toReceive);

            System.out.println("CryptoTransmissionAgent - started ");


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Pause the internal threads
     */
    public void pause(){
        running.set(false);

        futures[SEND_TASK].cancel(true);
        futures[RECEIVE_TASK].cancel(true);
    }

    /**
     * Resume the internal threads
     */
    public void resume(){
        try {
            if (!running.get()) {

                if (futures != null) {
                    if (futures[SEND_TASK] != null) futures[SEND_TASK].cancel(true);
                    if (futures[RECEIVE_TASK] != null) futures[RECEIVE_TASK].cancel(true);
                }
                assert futures != null;
                futures[SEND_TASK] = threadPoolExecutor.submit(toSend);
                futures[RECEIVE_TASK] = threadPoolExecutor.submit(toReceive);
                this.running.set(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Stop the internal threads
     */
    public void stop(){
        try {
            running.set(false);
            //Stop the Thread
            futures[SEND_TASK].cancel(true);
            futures[RECEIVE_TASK].cancel(true);

        }catch (Exception e){
            e.printStackTrace();
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

            if (!cryptoTransmissionNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().isConnected()){
                //System.out.println("ActorNetworkServiceRecordedAgent - sendCycle() no connection available ... ");
                return;
            } else  {

                // function to process and send metadata to remote
                processMetadata();

                Thread.sleep(CryptoTransmissionAgent.SLEEP_TIME);
            }

        } catch (InterruptedException e) {
            running.set(false);
            System.out.println("CryptoTransmissionAgent - Thread Interrupted stopped ...  ");
            System.out.println("CryptoTransmissionAgent - Thread Interrupted stopped, restarting threads ...  ");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void processMetadata() {

        try {
            Map<String, Object> filters = new HashMap<>();
            filters.put(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.PRE_PROCESSING_SEND.getCode());

         /*
         * Read all pending CryptoTransmissionMetadata from database
         */
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = cryptoTransmissionNetworkServicePluginRoot.getOutgoingCryptoTransmissionMetadataDAO().findAll(filters);


            for (CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata) {


                if(!poolConnectionsWaitingForResponse.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())) {

                    if (cryptoTransmissionNetworkServicePluginRoot.getCommunicationNetworkServiceConnectionManager().getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getDestinationPublicKey()) == null) {
                        if (cryptoTransmissionNetworkServicePluginRoot.getWsCommunicationsCloudClientManager() != null && cryptoTransmissionNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot() != null) {
                            PlatformComponentProfile applicantParticipant = cryptoTransmissionNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(cryptoTransmissionMetadata.getSenderPublicKey(), "sender", "sender", NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_INTRA_USER, "");
                            PlatformComponentProfile remoteParticipant = cryptoTransmissionNetworkServicePluginRoot.getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().constructPlatformComponentProfileFactory(cryptoTransmissionMetadata.getDestinationPublicKey(),"destination","destination", NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_INTRA_USER,"");
                            cryptoTransmissionNetworkServicePluginRoot.getCommunicationNetworkServiceConnectionManager().connectTo(applicantParticipant, cryptoTransmissionNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot(), remoteParticipant);
                            // pass the metada to a pool wainting for the response of the other peer or server failure
                            poolConnectionsWaitingForResponse.put(cryptoTransmissionMetadata.getDestinationPublicKey(), cryptoTransmissionMetadata);

                        }
                    }
                }else{

                    NetworkServiceLocal communicationNetworkServiceLocal = cryptoTransmissionNetworkServicePluginRoot.getCommunicationNetworkServiceConnectionManager().getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getDestinationPublicKey());

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
                            cryptoTransmissionNetworkServicePluginRoot.getOutgoingCryptoTransmissionMetadataDAO().changeCryptoTransmissionProtocolState(
                                    cryptoTransmissionMetadata.getTransactionId(),
                                    CryptoTransmissionProtocolState.SENT_TO_COMMUNICATION_TEMPLATE);

                            System.out.print("-----------------------\n" +
                                    "CRYPTO METADATA SENT_TO_COMMUNICATION_TEMPLATE !!!!! -----------------------\n" +
                                    "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());

                        //} catch (CantUpdateRecordDataBaseException e) {
                          //  e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        }
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
            List<CryptoTransmissionMetadata> lstCryptoTransmissionMetadata = cryptoTransmissionNetworkServicePluginRoot.getIncomingCryptoTransmissionMetadataDAO().findAll(filters);


            for(CryptoTransmissionMetadata cryptoTransmissionMetadata : lstCryptoTransmissionMetadata) {

                //   CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getSenderPublicKey());

                if (cryptoTransmissionMetadata.getCryptoTransmissionProtocolState() != CryptoTransmissionProtocolState.DONE) {

                    System.out.print("-----------------------\n" +
                            "RECIBIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());

                    // si no contiene la metadata, la tengo que guardar en la bd y notificar que llegó, tambien debería cargar ese caché cuando se lanza el evento de que llega la metadata de respuesta
                    // if( ! cacheResponseMetadataFromRemotes.containsKey(cryptoTransmissionMetadata.getDestinationPublicKey())){
                    try {
                        // lo cambio directo porque la metadata viene con un mensaje de estado distinto, actualizado
                        switch (cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates()) {

                            case SEEN_BY_DESTINATION_NETWORK_SERVICE:
                                //guardo estado
                                // deberia ver si tengo que lanzar un evento acá
                                cryptoTransmissionNetworkServicePluginRoot.getIncomingCryptoTransmissionMetadataDAO().changeCryptoTransmissionProtocolState(
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
                                cryptoTransmissionNetworkServicePluginRoot.getOutgoingCryptoTransmissionMetadataDAO().update(cryptoTransmissionMetadata);

                                System.out.print("-----------------------\n" +
                                        "RECIVIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());

                                lauchNotification();
                                break;

                            case CREDITED_IN_DESTINATION_WALLET:
                                // Guardo estado
                                System.out.print("-----------------------\n" +
                                        "RECIBIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates());

                                //update message in DONE and Close connection with another device - End message
                                cryptoTransmissionNetworkServicePluginRoot.getOutgoingCryptoTransmissionMetadataDAO().doneTransaction(cryptoTransmissionMetadata.getTransactionId());


                                System.out.print("CryptoTransmission Close Connection - End Message");
                                this.poolConnectionsWaitingForResponse.remove(cryptoTransmissionMetadata.getDestinationPublicKey());
                                break;
                            // si el mensaje viene con un estado de SENT es porque es la primera vez que llega, por lo que tengo que guardarlo en la bd y responder
                            case SEEN_BY_OWN_NETWORK_SERVICE_WAITING_FOR_RESPONSE:

//                                incomingCryptoTransmissionMetadataDAO.changeTransactionStateAndProtocolState(
//                                        cryptoTransmissionMetadata.getTransactionId(),
//                                        CryptoTransmissionMetadataState.SEEN_BY_DESTINATION_NETWORK_SERVICE,
//                                        CryptoTransmissionProtocolState.RECEIVED);

                                System.out.print("-----------------------\n" +
                                        "RECIBIENDO CRYPTO METADATA!!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());

                                lauchNotification();

                                // El destination soy yo porque me lo estan enviando
                                // El sender es el otro y es a quien le voy a responder
                                NetworkServiceLocal communicationNetworkServiceLocal = cryptoTransmissionNetworkServicePluginRoot.getCommunicationNetworkServiceConnectionManager().getNetworkServiceLocalInstance(cryptoTransmissionMetadata.getSenderPublicKey());
                                if (communicationNetworkServiceLocal != null) {
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
                                } else {
                                    System.out.print("-----------------------\n" +
                                            "INTENTO DE RESPUESTA DENEGADO, NO HAY CONEXION CON EL OTRO TRANSMISSION -----------------------\n" +
                                            "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());
                                }
                                break;
                            case CREDITED_IN_OWN_WALLET:
                                System.out.print("-----------------------\n" +
                                        "CREDITED IN WALLET, TENES QUE VER QUE SE HACE ACÁ !!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());
                                break;
                            default:
                                System.out.print("-----------------------\n" +
                                        "TE ESTAS YENDO POR EL DEFAULT !!!!! -----------------------\n" +
                                        "-----------------------\n STATE: " + cryptoTransmissionMetadata.getCryptoTransmissionProtocolState());
                                break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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