package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceLocal;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.NetworkServiceNegotiationTransmissionPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceConnectionsDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.NegotiationTransmissionNetworkServiceDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantRegisterSendNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.messages.NegotiationMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//<<<<<<< HEAD
//=======
//>>>>>>> 78d02bfd88d1c23d48396cb851829d86d9fbee61

/**
 * Created by Yordin Alayn on 30.11.15.
 */
public class NegotiationTransmissionAgent {

    //Represent the sleep time for the read or send (2000 milliseconds)
    private static final long SLEEP_TIME = 15000;
    private static final long RECEIVE_SLEEP_TIME = 15000;

    //DealsWithErrors Interface member variables.
    private ErrorManager errorManager;

    //Represent is the tread is running
    private Boolean running;

    //Represent the identity
    private ECCKeyPair identity;

    //Communication Service, Class to send the message
    //CryptoTransmissionNetworkServiceLocal communicationNetworkServiceLocal;

    //Communication manager, Class to obtain the connections
    CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    //Communication Cloud Client manager
    WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    //plugin root
    NetworkServiceNegotiationTransmissionPluginRoot networkServiceNegotiationTransmissionPluginRoot;

    //DAO NegotiationTransmission
    NegotiationTransmissionNetworkServiceConnectionsDatabaseDao databaseConnectionsDao;
    NegotiationTransmissionNetworkServiceDatabaseDao databaseDao;

    //Represent the remoteNetworkServicesRegisteredList
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    //PlatformComponentProfile platformComponentProfile
    PlatformComponentProfile platformComponentProfile;

    //Represent the send cycle tread of this NetworkService
    private Thread toSend;

    //Represent the send messages tread of this TemplateNetworkServiceRemoteAgent
    private Thread toReceive;

    //Cache de metadata con conexions leidas anteriormente. ActorPublicKey, metadata de respuesta
    Map<String, NegotiationTransmissionState> cacheResponseMetadataFromRemotes;

    //Map contains publicKey from componentProfile to connect and the number of connections intents.
    Map<String,Integer> connectionsCounters;

    //Cola de espera, a la cual van pasando las conecciones que no se pudieron conectar para que se hagan con más tiempo y no saturen el server
    Map<String, NegotiationTransmissionPlatformComponentProfilePlusWaitTime> waitingPlatformComponentProfile;

    //Pool connections requested waiting for peer or server response. PublicKey  and transaccion metadata waiting to be a response
    Map<String,NegotiationTransmission> poolConnectionsWaitingForResponse;

    //
    Map<String, FermatMessage> receiveMessage;
    private boolean flag=true;


    private EventManager eventManager;

    //Constructor
    public NegotiationTransmissionAgent(
            NetworkServiceNegotiationTransmissionPluginRoot networkServiceNegotiationTransmissionPluginRoot,
            NegotiationTransmissionNetworkServiceConnectionsDatabaseDao databaseConnectionsDao,
            NegotiationTransmissionNetworkServiceDatabaseDao databaseDao,
            CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
            WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,
            PlatformComponentProfile platformComponentProfile,
            ErrorManager errorManager,
            List<PlatformComponentProfile> remoteNetworkServicesRegisteredList,
            ECCKeyPair identity,
            EventManager eventManager
    ){

        this.networkServiceNegotiationTransmissionPluginRoot = networkServiceNegotiationTransmissionPluginRoot;
        this.databaseConnectionsDao = databaseConnectionsDao;
        this.databaseDao = databaseDao;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.wsCommunicationsCloudClientManager =wsCommunicationsCloudClientManager;
        this.errorManager = errorManager;
        this.remoteNetworkServicesRegisteredList = remoteNetworkServicesRegisteredList;
        this.identity = identity;
        this.platformComponentProfile = platformComponentProfile;
        this.eventManager = eventManager;


        cacheResponseMetadataFromRemotes = new HashMap<String, NegotiationTransmissionState>();
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

    /*SERVICE*/
    public void start(){
        //Set to running
        this.running  = Boolean.TRUE;
        System.out.println("NEGOTIATION TRANSMISSION AGEN");
        try {
            databaseDao.initialize();
        } catch (CantInitializeDatabaseException e) {
            e.printStackTrace();
            System.out.println("ERROR NEGOTIATION TRANSMISSION AGEN, DATABASE DAO NOT STARTED");
        }

        //Start the Thread
        toSend.start();
        toReceive.start();

        System.out.println("NegotiationTransmissionAgent - started ");
    }

    public void pause(){
        this.running  = Boolean.FALSE;
    }

    public void resume(){
        this.running  = Boolean.TRUE;
    }

    public void stop(){
        //Stop the Thread
        toSend.interrupt();
        toReceive.interrupt();
        this.running  = Boolean.FALSE;
        //Disconnect from the service
    }
    /*END SERVICE*/

    /*PUBLIC*/
    public void sendCycle(){
        try {

            if(networkServiceNegotiationTransmissionPluginRoot.isRegister()) {
                // function to process and send metadata to remote
                processSend();
                //Discount from the the waiting list
                discountWaitTime();
            }
            //Sleep for a time
            Thread.sleep(SLEEP_TIME);

        } catch (InterruptedException e) {
            running = false;
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        }
    }

    private void processSend() {

        try {

            System.out.print("\n\n**** X) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - LIST PENDING SEND ****\n");
            List<NegotiationTransmission> negotiationTransmissionList = databaseDao.findAllByTransmissionState(NegotiationTransmissionState.PROCESSING_SEND);

            for (NegotiationTransmission negotiationTransmission : negotiationTransmissionList) {
                String receiverPublicKey= negotiationTransmission.getPublicKeyActorReceive();
                if(!poolConnectionsWaitingForResponse.containsKey(receiverPublicKey)) {
                    //TODO: hacer un filtro por aquellas que se encuentran conectadas
                    if (communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(receiverPublicKey) == null) {
                        if (wsCommunicationsCloudClientManager != null) {
                            if (platformComponentProfile != null) {

                                PlatformComponentProfile applicantParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                                        negotiationTransmission.getPublicKeyActorSend(),
                                        NetworkServiceType.UNDEFINED,
                                        negotiationTransmission.getActorSendType());
                                PlatformComponentProfile remoteParticipant = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(
                                        negotiationTransmission.getPublicKeyActorReceive(),
                                        NetworkServiceType.UNDEFINED,
                                        negotiationTransmission.getActorReceiveType());
                                communicationNetworkServiceConnectionManager.connectTo(applicantParticipant, platformComponentProfile, remoteParticipant);
                                // pass the negotiationTransmission to a pool waiting for the response of the other peer or server failure
                                poolConnectionsWaitingForResponse.put(receiverPublicKey, negotiationTransmission);

                            }
                        }
                    }
                }else{
                    NetworkServiceLocal communicationNetworkServiceLocal = networkServiceNegotiationTransmissionPluginRoot.getNetworkServiceConnectionManager().getNetworkServiceLocalInstance(receiverPublicKey);
                    if (communicationNetworkServiceLocal != null) {
                        try {

                            String jsonNegotiationTransmission = new NegotiationMessage(
                                    negotiationTransmission.getTransmissionId(),
                                    negotiationTransmission.getTransactionId(),
                                    negotiationTransmission.getNegotiationId(),
                                    negotiationTransmission.getNegotiationTransactionType(),
                                    negotiationTransmission.getPublicKeyActorSend(),
                                    negotiationTransmission.getActorSendType(),
                                    negotiationTransmission.getPublicKeyActorReceive(),
                                    negotiationTransmission.getActorReceiveType(),
                                    negotiationTransmission.getTransmissionType(),
                                    negotiationTransmission.getTransmissionState(),
                                    negotiationTransmission.getNegotiationType(),
                                    negotiationTransmission.getNegotiationXML(),
                                    negotiationTransmission.getTimestamp()
                            ).toJson();

//                            communicationNetworkServiceLocal.sendMessage(identity.getPublicKey(), receiverPublicKey, jsonNegotiationTransmission);


                            if(negotiationTransmission.getTransmissionType() == NegotiationTransmissionType.TRANSMISSION_NEGOTIATION ){
                                System.out.print("\n\n**** 10) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - SEND NEGOTIATION TO : " + receiverPublicKey + "****\n");
                                System.out.print("*** SEND DATES: " +
                                                "\n - Sender Id = " + identity.getPublicKey() +
                                                "\n - Receiver Id = " + receiverPublicKey +
                                                "\n - JsonNegotiationTransmission " + jsonNegotiationTransmission
                                );

                                negotiationTransmission.setTransmissionState(NegotiationTransmissionState.PENDING_REMOTE_ACTION);
                            }else{
                                System.out.print("\n\n**** 25) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - SEND CONFIRMATION TO : " + receiverPublicKey + "****\n");
                                negotiationTransmission.setTransmissionState(NegotiationTransmissionState.DONE);
                            }

                            databaseDao.changeState(negotiationTransmission);

                            System.out.print("-----------------------\n NEGOTIATION TRANSACTION STATE: " + negotiationTransmission.getTransmissionState() + "\n-----------------------\n");

                        } catch (CantRegisterSendNegotiationTransmissionException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException e) {
            e.printStackTrace();
        } catch (CantEstablishConnectionException e) {
            e.printStackTrace();
        }
    }

    public void addRemoteNetworkServicesRegisteredList(List<PlatformComponentProfile> list){
        remoteNetworkServicesRegisteredList = list;
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
    /*END PUBLIC*/

    /*PRIVATE*/
    private void receiveCycle(){
        try {
            // function to process metadata received
            processReceive();
            //Sleep for a time
            Thread.sleep(RECEIVE_SLEEP_TIME);

        } catch (InterruptedException e) {
            running = false;
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not sleep"));
        }
    }

    private void processReceive() {

        try {

            System.out.print("\n\n**** 14) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - LIST PENDING RECEIVE ****\n");

            List<NegotiationTransmission> negotiationTransmissionList = databaseDao.findAllByTransmissionState(NegotiationTransmissionState.PENDING_ACTION);
            for(NegotiationTransmission negotiationTransmission : negotiationTransmissionList) {
                switch (negotiationTransmission.getNegotiationTransactionType()){
                    case CUSTOMER_BROKER_NEW:

                        if(negotiationTransmission.getTransmissionType() == NegotiationTransmissionType.TRANSMISSION_NEGOTIATION){

                            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_NEW);
                            eventToRaise.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                            eventManager.raiseEvent(eventToRaise);
                            System.out.println("\n**** 15) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - RAISE EVENT NEGOTIATION TRANSMISSION TRANSACTION NEW\n");

                        }else{

                            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_NEW);
                            eventToRaise.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                            eventManager.raiseEvent(eventToRaise);
                            System.out.println("\n**** 15) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - RAISE EVENT NEGOTIATION TRANSMISSION TRANSACTION CONFIRM\n");

                        }
                        break;

                    case CUSTOMER_BROKER_UPDATE:

                        if(negotiationTransmission.getTransmissionType() == NegotiationTransmissionType.TRANSMISSION_NEGOTIATION){

                            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_UPDATE);
                            eventToRaise.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                            eventManager.raiseEvent(eventToRaise);
                            System.out.println("\n**** 15) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - RAISE EVENT NEGOTIATION TRANSMISSION TRANSACTION UPDATE\n");

                        }else{

                            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_UPDATE);
                            eventToRaise.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                            eventManager.raiseEvent(eventToRaise);
                            System.out.println("\n**** 15) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - RAISE EVENT NEGOTIATION TRANSMISSION TRANSACTION CONFIRM\n");

                        }
                        break;

                    case CUSTOMER_BROKER_CLOSE:

                        if(negotiationTransmission.getTransmissionType() == NegotiationTransmissionType.TRANSMISSION_NEGOTIATION){

                            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_TRANSACTION_CLOSE);
                            eventToRaise.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                            eventManager.raiseEvent(eventToRaise);
                            System.out.println("\n**** 15) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - RAISE EVENT NEGOTIATION TRANSMISSION TRANSACTION CLOSE\n");

                        }else{

                            FermatEvent eventToRaise = eventManager.getNewEvent(EventType.INCOMING_NEGOTIATION_TRANSMISSION_CONFIRM_CLOSE);
                            eventToRaise.setSource(EventSource.NETWORK_SERVICE_NEGOTIATION_TRANSMISSION);
                            eventManager.raiseEvent(eventToRaise);
                            System.out.println("\n**** 15) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - AGENT - RAISE EVENT NEGOTIATION TRANSMISSION TRANSACTION CONFIRM\n");

                        }
                        break;
                }
            }
        } catch (CantReadRecordDataBaseException e) {
                e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void discountWaitTime(){
        if(!waitingPlatformComponentProfile.isEmpty()) {
            for (NegotiationTransmissionPlatformComponentProfilePlusWaitTime negotiationTransmissionPlatformComponentProfilePlusWaitTime : waitingPlatformComponentProfile.values()) {
                negotiationTransmissionPlatformComponentProfilePlusWaitTime.WaitTimeDown();
                if (negotiationTransmissionPlatformComponentProfilePlusWaitTime.getWaitTime() <= 0) {
                    remoteNetworkServicesRegisteredList.add(negotiationTransmissionPlatformComponentProfilePlusWaitTime.getPlatformComponentProfile());
                    waitingPlatformComponentProfile.remove(negotiationTransmissionPlatformComponentProfilePlusWaitTime);
                }
            }
        }
    }
    /*END PRIVATE*/
}