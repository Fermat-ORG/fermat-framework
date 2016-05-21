/*
 * @#WsCommunicationsCloudClient.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.osa_android.hardware.HardwareManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteClientComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.conf.CLoudClientConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.processors.FermatTyrusPacketProcessor;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsTyrusCloudClientChannel</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ClientEndpoint(configurator = CLoudClientConfigurator.class)
public class WsCommunicationsTyrusCloudClientChannel {

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Hardaware
     */
    private HardwareManager hardwareManager;

    /**
     * Represent the wsCommunicationsCloudClientConnection
     */
    private WsCommunicationsTyrusCloudClientConnection wsCommunicationsTyrusCloudClientConnection;

    /**
     * Represent the temporalIdentity
     */
    private ECCKeyPair temporalIdentity;

    /**
     * Represent the clientIdentity
     */
    private ECCKeyPair clientIdentity;

    /**
     * Represent the serverIdentity
     */
    private String serverIdentity;

    /**
     * Represent the platformComponentProfile
     */
    private PlatformComponentProfile platformComponentProfile;

    /**
     * Holds the packet processors objects
     */
    private Map<FermatPacketType, CopyOnWriteArrayList<FermatTyrusPacketProcessor>> packetProcessorsRegister;

    /**
     * Represent is the client is register with the server
     */
    private boolean isRegister;

    /**
     * Represent the clientConnection
     */
    private Session clientConnection;

    /**
     * Constructor with parameters
     * @param eventManager
     * @param clientIdentity
     * @throws IOException
     * @throws DeploymentException
     */
    public WsCommunicationsTyrusCloudClientChannel(WsCommunicationsTyrusCloudClientConnection wsCommunicationsTyrusCloudClientConnection, EventManager eventManager, ECCKeyPair clientIdentity) throws IOException, DeploymentException {

        this.clientIdentity = clientIdentity;
        this.temporalIdentity = CLoudClientConfigurator.tempIdentity;
        this.packetProcessorsRegister = new ConcurrentHashMap<>();
        this.wsCommunicationsTyrusCloudClientConnection = wsCommunicationsTyrusCloudClientConnection;
        this.eventManager = eventManager;
        this.isRegister = Boolean.FALSE;
    }

    public synchronized void sendMessage(final String message) {

        /*
         * if Packet is bigger than 1000 Send the message through of sendDividedChain
         */
        if(message.length() > 1000){

            try {
                sendDividedChain(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{

            if(clientConnection!=null && clientConnection.isOpen())
                clientConnection.getAsyncRemote().sendText(message);
        }

    }



    @OnOpen
    public void onOpen(final Session session) {

//        System.out.println(" --------------------------------------------------------------------- ");
//        System.out.println(" WsCommunicationsTyrusCloudClientChannel - Starting method onOpen");
//        System.out.println(" WsCommunicationsTyrusCloudClientChannel - id = "+session.getId());
//        System.out.println(" WsCommunicationsTyrusCloudClientChannel - url = "+session.getRequestURI());

        this.clientConnection = session;
    }


    @OnMessage
    public void onMessage(String fermatPacketEncode) {

//        System.out.println(" --------------------------------------------------------------------- ");
//        System.out.println(" WsCommunicationsTyrusCloudClientChannel - Starting method onMessage(String)");
       // System.out.println(" WsCommunicationsTyrusCloudClientChannel - encode fermatPacket " + fermatPacketEncode);

        FermatPacket fermatPacketReceive = null;

        /*
         * If the client is no register
         */
        if (!isRegister){

//            System.out.println(" WsCommunicationsTyrusCloudClientChannel - decoding fermatPacket with temp-identity ");

            /**
             * Decode the message with the temporal identity
             */
            fermatPacketReceive = FermatPacketDecoder.decode(fermatPacketEncode, temporalIdentity.getPrivateKey());

        }else {

//            System.out.println(" WsCommunicationsTyrusCloudClientChannel - decoding fermatPacket with client-identity ");

            /**
             * Decode the message with the client identity
             */
            fermatPacketReceive = FermatPacketDecoder.decode(fermatPacketEncode, clientIdentity.getPrivateKey());

            /*
             * Validate the signature
             */
            validateFermatPacketSignature(fermatPacketReceive);
        }

       // System.out.println(" WsCommunicationsTyrusCloudClientChannel - decode fermatPacket " + fermatPacketReceive.toJson());


        //verify is packet supported
        if (packetProcessorsRegister.containsKey(fermatPacketReceive.getFermatPacketType())){

             /*
             * Call the processors for this packet
             */
            for (FermatTyrusPacketProcessor fermatPacketProcessor :packetProcessorsRegister.get(fermatPacketReceive.getFermatPacketType())) {

                /*
                 * Processor make his job
                 */
                fermatPacketProcessor.processingPackage(fermatPacketReceive);
            }

        }else {

//            System.out.println(" WsCommunicationsTyrusCloudClientChannel - Packet type " + fermatPacketReceive.getFermatPacketType() + "is not supported");

        }

    }


    @OnClose
    public void onClose(final Session session, final CloseReason reason) {

//        System.out.println(" --------------------------------------------------------------------- ");
//        System.out.println(" WsCommunicationsTyrusCloudClientChannel - Starting method onClose");


        try {
            switch (reason.getCloseCode().getCode()) {

                case 1002:
                case 1006:
                        raiseClientConnectionLooseNotificationEvent();
//                        System.out.println(" WsCommunicationsTyrusCloudClientChannel - Connection loose");
                    break;

                default:

                        if (reason.getReasonPhrase().contains("Connection failed")){
                            raiseClientConnectionLooseNotificationEvent();
                        }else{
                            raiseClientConnectionCloseNotificationEvent();
                            setIsRegister(Boolean.FALSE);
                        }

                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void closeConnection(){

        try {
            if(clientConnection.isOpen()) {

                System.out.println(" WsCommunicationsTyrusCloudClientChannel - closing the main connection");
                clientConnection.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "The cloud client close the main connection, intentionally."));
                raiseClientConnectionCloseNotificationEvent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @OnError
    public void onError(Session session, Throwable t) {
        try {
//            System.out.println(" --------------------------------------------------------------------- ");
//            System.out.println(" WsCommunicationsTyrusCloudClientChannel - Starting method onError");
            t.printStackTrace();
            clientConnection.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, t.getMessage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendPing() throws IOException {

//        System.out.println(" WsCommunicationsTyrusCloudClientChannel - Sending ping to the node...");


        String pingString = "PING";
        ByteBuffer pingData = ByteBuffer.allocate(pingString.getBytes().length);
        pingData.put(pingString.getBytes()).flip();
        getClientConnection().getBasicRemote().sendPing(pingData);

    }


    @OnMessage
    public void onPongMessage(PongMessage message) {
        //System.out.println(" WsCommunicationsTyrusCloudClientChannel - Pong message receive from server = " + message.getApplicationData().asCharBuffer().toString());
    }


    /**
     * Validate the signature of the packet
     * @param fermatPacketReceive
     */
    private void validateFermatPacketSignature(FermatPacket fermatPacketReceive){

//        System.out.println(" WsCommunicationsTyrusCloudClientChannel - validateFermatPacketSignature");

         /*
         * Validate the signature
         */
        boolean isValid = AsymmetricCryptography.verifyMessageSignature(fermatPacketReceive.getSignature(), fermatPacketReceive.getMessageContent(), getServerIdentity());

       // System.out.println(" WsCommunicationsTyrusCloudClientChannel - isValid = " + isValid);

        /*
         * if not valid signature
         */
        if (!isValid){
            throw new RuntimeException("Fermat Packet received has not a valid signature, go to close this connection maybe is compromise");
        }

    }

    /**
     * This method register a FermatTyrusPacketProcessor object with this
     * server
     */
    public void registerFermatPacketProcessor(FermatTyrusPacketProcessor fermatPacketProcessor) {


        //Validate if a previous list created
        if (packetProcessorsRegister.containsKey(fermatPacketProcessor.getFermatPacketType())){

            /*
             * Add to the existing list
             */
            packetProcessorsRegister.get(fermatPacketProcessor.getFermatPacketType()).add(fermatPacketProcessor);

        }else{

            /*
             * Create a new list and add the fermatPacketProcessor
             */
            CopyOnWriteArrayList<FermatTyrusPacketProcessor> fermatPacketProcessorList = new CopyOnWriteArrayList<>();
            fermatPacketProcessorList.add(fermatPacketProcessor);

            /*
             * Add to the packetProcessorsRegister
             */
            packetProcessorsRegister.put(fermatPacketProcessor.getFermatPacketType(), fermatPacketProcessorList);
        }

    }

    /**
     * Get the Client Identity
     * @return ECCKeyPair
     */
    public ECCKeyPair getClientIdentity() {
        return clientIdentity;
    }

    /**
     * Get Temporal Identity
     * @return ECCKeyPair
     */
    public ECCKeyPair getTemporalIdentity() {
        return temporalIdentity;
    }

    /**
     * Get Server Identity
     *
     * @return String
     */
    public String getServerIdentity() {
        return serverIdentity;
    }

    /**
     * Set Server Identity
     * @param serverIdentity
     */
    public void setServerIdentity(String serverIdentity) {
        this.serverIdentity = serverIdentity;
    }

    /**
     * Clean all packet processors registered
     */
    public void cleanPacketProcessorsRegistered(){
        packetProcessorsRegister.clear();
    }


    /**
     * Get the PlatformComponentProfile
     *
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile getPlatformComponentProfile() {
        return platformComponentProfile;
    }

    /**
     * Set the PlatformComponentProfile
     * @param platformComponentProfile
     */
    public void setPlatformComponentProfile(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    /**
     * Get the isActive value
     * @return boolean
     */
    public boolean isRegister() {
        return isRegister;
    }

    /**
     * Set the isActive
     * @param isRegister
     */
    public void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    /**
     * Get the EventManager
     * @return EventManager
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Notify when cloud client component es registered,
     * this event is raise to show the message in a popup of the UI
     */
    public void riseCompleteClientComponentRegistrationNotificationEvent() {

        FermatEvent platformEvent = eventManager.getNewEvent(P2pEventType.COMPLETE_CLIENT_COMPONENT_REGISTRATION_NOTIFICATION);
        CompleteClientComponentRegistrationNotificationEvent event =  (CompleteClientComponentRegistrationNotificationEvent) platformEvent;
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);
        event.setMessage("Cloud client communication, registered and established connection.");
        eventManager.raiseEvent(platformEvent);
    }

    /**
     * Notify when cloud client is disconnected
     */
    public void raiseClientConnectionCloseNotificationEvent() {

//        System.out.println("WsCommunicationsTyrusCloudClientChannel - raiseClientConnectionCloseNotificationEvent");
        FermatEvent platformEvent = eventManager.getNewEvent(P2pEventType.CLIENT_CONNECTION_CLOSE);
        platformEvent.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);
        eventManager.raiseEvent(platformEvent);
//        System.out.println("WsCommunicationsTyrusCloudClientChannel - Raised Event = P2pEventType.CLIENT_CONNECTION_CLOSE");
    }

    /**
     * Notify when cloud client is disconnected
     */
    public void raiseClientConnectionLooseNotificationEvent() {

        System.out.println("WsCommunicationsTyrusCloudClientChannel - raiseClientConnectionLooseNotificationEvent");
        FermatEvent platformEvent = eventManager.getNewEvent(P2pEventType.CLIENT_CONNECTION_LOOSE);
        platformEvent.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);
        eventManager.raiseEvent(platformEvent);
//        System.out.println("WsCommunicationsTyrusCloudClientChannel - Raised Event = P2pEventType.CLIENT_CONNECTION_LOOSE");
    }

    /**
     * Get the IdentityPublicKey
     * @return String
     */
    public String getIdentityPublicKey(){
        return clientIdentity.getPublicKey();
    }

    /**
     * Get the clientConnection value
     *
     * @return clientConnection current value
     */
    public Session getClientConnection() {
        return clientConnection;
    }

    /**
     * Get the wsCommunicationsTyrusCloudClientConnection value
     *
     * @return wsCommunicationsTyrusCloudClientConnection current value
     */
    public WsCommunicationsTyrusCloudClientConnection getWsCommunicationsTyrusCloudClientConnection() {
        return wsCommunicationsTyrusCloudClientConnection;
    }

    /**
     * Send the message divide in packets of Size 1000
     */
    private void sendDividedChain(final String message) throws IOException {

        /*
         * Number of packets to send of Size 1000
         */
        int ref = message.length() / 1000;

        /*
         * Used to validate if the packet is send complete or in two parts
         */
        int residue = message.length() % 1000;

        /*
         * Index to handle the send of packet subString
         */
        int beginIndex = 0;
        int endIndex = 1000;

        for(int i = 0; i < ref-1; i++){

            if(clientConnection!=null && clientConnection.isOpen()) {
                clientConnection.getBasicRemote().sendText(message.substring(beginIndex, endIndex), Boolean.FALSE);
                beginIndex = endIndex;
                endIndex = endIndex + 1000;
            }else{
                raiseClientConnectionCloseNotificationEvent();
            }
        }

        /*
         * we get the last Chain to send
         */
        String lastChain = message.substring(beginIndex, message.length());

        /*
         * if residue is equals 0 then send the lastChain Complete
         * else then the lastChain is divided in two parts to send
         */
        if(residue == 0){

            /*
             * the lastChain is send Complete
             */
            clientConnection.getBasicRemote().sendText(lastChain, Boolean.TRUE);

        }else{

            /*
             * the lastChain is divided in two parts to send
             */
            int middleIndexLastChain = (lastChain.length() % 2 == 0) ? (lastChain.length() / 2)  : ((lastChain.length() + 1) / 2) - 1;
            clientConnection.getBasicRemote().sendText(lastChain.substring(0, middleIndexLastChain), Boolean.FALSE);
            clientConnection.getBasicRemote().sendText(lastChain.substring(middleIndexLastChain, lastChain.length()), Boolean.TRUE);

        }

    }

    public HardwareManager getHardwareManager() {
        return hardwareManager;
    }
}
