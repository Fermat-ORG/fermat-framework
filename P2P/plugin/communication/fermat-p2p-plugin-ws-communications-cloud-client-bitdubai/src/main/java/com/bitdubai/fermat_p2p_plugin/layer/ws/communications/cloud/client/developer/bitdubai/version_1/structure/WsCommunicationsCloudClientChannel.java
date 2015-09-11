/*
 * @#WsCommunicationsCloudClient.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.FermatPacketProcessor;
import com.google.gson.JsonObject;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClientChannel</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudClientChannel extends WebSocketClient {

    /**
     * Represent the value of DEFAULT_CONNECTION_TIMEOUT
     */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 10000;

    /*
     * Represent the wsCommunicationsCloudClientAgent
     */
    private WsCommunicationsCloudClientAgent wsCommunicationsCloudClientAgent;

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
    private Map<FermatPacketType, List<FermatPacketProcessor>> packetProcessorsRegister;

    /**
     * Represent is the client is register with the server
     */
    private boolean isRegister;


    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        System.out.println(" WsCommunicationsCloudClientChannel - onWebsocketPong");
        System.out.println(" WsCommunicationsCloudClientChannel - conn = "+conn);
        System.out.println(" WsCommunicationsCloudClientChannel - f = "+f);
    }

    /**
     * Constructor with parameters
     *
     * @param serverUri
     * @param draft
     * @param headers
     * @param connectTimeout
     */
    private WsCommunicationsCloudClientChannel(URI serverUri, Draft draft, Map<String, String> headers, int connectTimeout, ECCKeyPair temporalIdentity) {
        super(serverUri, draft, headers, connectTimeout);
        this.clientIdentity = new ECCKeyPair();
        this.temporalIdentity = temporalIdentity;
        this.packetProcessorsRegister = new ConcurrentHashMap<>();
        isRegister = Boolean.FALSE;
    }

    /**
     * Factory method to create new instance of WsCommunicationsCloudClientChannel
     *
     * @param serverUri
     * @param draft
     * @return WsCommunicationsCloudClientChannel instance
     */
    public static WsCommunicationsCloudClientChannel constructWsCommunicationsCloudClientFactory(URI serverUri, Draft draft){

        /*
         * Create a new temporal identity
         */
        ECCKeyPair tempIdentity = new ECCKeyPair();

        /*
         * Create a new headers
         */
        Map<String, String> headers = new HashMap<>();

        /*
         * Get json representation
         */
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AttNamesConstants.JSON_ATT_NAME_IDENTITY, tempIdentity.getPublicKey());

        /*
         * Add the att to the header
         */
        headers.put(AttNamesConstants.HEADER_ATT_NAME_TI, jsonObject.toString());

        /*
         * Construct the instance with the required parameters
         */
        return new WsCommunicationsCloudClientChannel(serverUri, draft, headers, WsCommunicationsCloudClientChannel.DEFAULT_CONNECTION_TIMEOUT, tempIdentity);
    }

    /**
     * (non-javadoc)
     * @see WebSocketClient#onOpen(ServerHandshake)
     */
    @Override
    public void onOpen(ServerHandshake handShakeData) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationsCloudClientChannel - Starting method onOpen");
        System.out.println("Server hand Shake Data = " + handShakeData);
        System.out.println("Server getReadyState() = " + getReadyState());
    }

    /**
     * (non-javadoc)
     * @see WebSocketClient#onMessage(String)
     */
    @Override
    public void onMessage(String fermatPacketEncode) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationsCloudClientChannel - Starting method onMessage(String)");
        System.out.println(" WsCommunicationsCloudClientChannel - encode fermatPacket " + fermatPacketEncode);

        FermatPacket fermatPacketReceive = null;

        /*
         * If the client is no register
         */
        if (!isRegister){

            /**
             * Decode the message with the temporal identity
             */
            fermatPacketReceive = FermatPacketDecoder.decode(fermatPacketEncode, temporalIdentity.getPrivateKey());
            System.out.println(" WsCommunicationsCloudClientChannel - decode fermatPacket " + FermatPacketDecoder.decode(fermatPacketEncode, temporalIdentity.getPrivateKey()));

        }else {

            /**
             * Decode the message with the client identity
             */
            fermatPacketReceive = FermatPacketDecoder.decode(fermatPacketEncode, clientIdentity.getPrivateKey());
            System.out.println(" WsCommunicationsCloudClientChannel - decode fermatPacket " + FermatPacketDecoder.decode(fermatPacketEncode, clientIdentity.getPrivateKey()));

            /*
             * Validate the signature
             */
            validateFermatPacketSignature(fermatPacketReceive);
        }



        /*
         * Call the processors for this packet
         */
        for (FermatPacketProcessor fermatPacketProcessor :packetProcessorsRegister.get(fermatPacketReceive.getFermatPacketType())) {

        /*
         * Processor make his job
         */
            fermatPacketProcessor.processingPackage(fermatPacketReceive);
        }

    }

    /**
     * (non-javadoc)
     * @see WebSocketClient#onClose(int, String, boolean)
     */
    @Override
    public void onClose(int code, String reason, boolean remote) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationsCloudClientChannel - Starting method onClose");
        System.out.println(" WsCommunicationsCloudClientChannel -  code   = " + code + " reason = " + reason + " remote = " + remote);
        System.out.println(" WsCommunicationsCloudClientChannel -  getReadyState() = " + getReadyState());
        System.out.println(" WsCommunicationsCloudClientChannel -  getConnection().isFlushAndClose() = " + getConnection().isFlushAndClose());

        /*
         * Start the agent to try the reconnect
         */
        wsCommunicationsCloudClientAgent.setIsConnected(Boolean.FALSE);
        wsCommunicationsCloudClientAgent.run();
    }

    /**
     * (non-javadoc)
     * @see WebSocketClient#onError(Exception)
     */
    @Override
    public void onError(Exception ex) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationsCloudClientChannel - Starting method onError");
        ex.printStackTrace();
        getConnection().closeConnection(1000, ex.getLocalizedMessage());

    }

    /**
     * Validate the signature of the packet
     * @param fermatPacketReceive
     */
    private void validateFermatPacketSignature(FermatPacket fermatPacketReceive){

        System.out.println(" WsCommunicationsCloudClientChannel - validateFermatPacketSignature");

         /*
         * Validate the signature
         */
        boolean isValid =AsymmectricCryptography.verifyMessageSignature(fermatPacketReceive.getSignature(), fermatPacketReceive.getMessageContent(), getServerIdentity());

        System.out.println(" WsCommunicationsCloudClientChannel - isValid = "+isValid);

        /*
         * if not valid signature
         */
        if (!isValid){
            throw new RuntimeException("Fermat Packet received has not a valid signature, go to close this connection maybe is compromise");
        }

    }

    /**
     * This method register a FermatPacketProcessor object with this
     * server
     */
    public void registerFermatPacketProcessorServerSideObject(FermatPacketProcessor fermatPacketProcessor) {

        /*
         * Set server reference
         */
        fermatPacketProcessor.setWsCommunicationsCloudClientChannel(this);

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
            List<FermatPacketProcessor> fermatPacketProcessorList = new ArrayList<>();
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
     * Get the isRegister value
     * @return boolean
     */
    public boolean isRegister() {
        return isRegister;
    }

    /**
     * Set the isRegister
     * @param isRegister
     */
    public void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    /**
     * Set the wsCommunicationsCloudClientAgent
     * @param wsCommunicationsCloudClientAgent
     */
    public void setWsCommunicationsCloudClientAgent(WsCommunicationsCloudClientAgent wsCommunicationsCloudClientAgent) {
        this.wsCommunicationsCloudClientAgent = wsCommunicationsCloudClientAgent;
    }
}
