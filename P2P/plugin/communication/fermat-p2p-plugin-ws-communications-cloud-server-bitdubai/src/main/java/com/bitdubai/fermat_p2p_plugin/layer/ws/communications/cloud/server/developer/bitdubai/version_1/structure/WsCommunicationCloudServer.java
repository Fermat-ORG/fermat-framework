/*
 * @#CommunicationServer.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.server.CommunicationCloudServer;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.FermatPacketProcessor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer</code> is
 * the web socket server to manage the communication
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationCloudServer extends WebSocketServer implements CommunicationCloudServer {

    /**
     * Represent the default port
     */
    public static final int DEFAULT_PORT = 9090;

    /**
     * Holds the pending register clients connections cache
     */
    private Map<String, WebSocket> pendingRegisterClientConnectionsCache;

    /**
     * Holds the registered clients connections cache
     */
    private Map<String, WebSocket> registeredClientConnectionsCache;

    /**
     * Holds the identity of the server by client
     */
    private Map<Integer, ECCKeyPair> serverIdentityByClientCache;

    /**
     * Holds the packet processors objects
     */
    private Map<FermatPacketType, List<FermatPacketProcessor>> packetProcessorsRegister;

    /**
     * Constructor with parameter
     * @param address
     */
    public WsCommunicationCloudServer(InetSocketAddress address) {
        super(address);
        this.pendingRegisterClientConnectionsCache = new ConcurrentHashMap<>();
        this.registeredClientConnectionsCache      = new ConcurrentHashMap<>();
        this.serverIdentityByClientCache           = new ConcurrentHashMap<>();
        this.packetProcessorsRegister = new ConcurrentHashMap<>();
    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onOpen(WebSocket, ClientHandshake)
     */
    @Override
    public void onOpen(WebSocket clientConnection, ClientHandshake handshake) {

        System.out.println(" WsCommunicationCloudServer - Starting method onOpen");
        System.out.println(" WsCommunicationCloudServer - New Client: "+clientConnection.getRemoteSocketAddress().getAddress().getHostAddress() + " is connected!");
        System.out.println(" WsCommunicationCloudServer - Handshake Resource Descriptor = " + handshake.getResourceDescriptor());
        System.out.println(" WsCommunicationCloudServer - temp-i = " + handshake.getFieldValue(AttNamesConstants.HEADER_ATT_NAME_TI));

        /*
         * Validate is a handshake valid
         */
        if (handshake.getFieldValue(AttNamesConstants.HEADER_ATT_NAME_TI)     != null &&
                handshake.getFieldValue(AttNamesConstants.HEADER_ATT_NAME_TI) != ""){

            /*
             * Get the temporal identity of the CommunicationsCloudClient componet
             */
            JsonParser parser = new JsonParser();
            JsonObject temporalIdentity = parser.parse(handshake.getFieldValue(AttNamesConstants.HEADER_ATT_NAME_TI)).getAsJsonObject();
            String temporalClientIdentity = temporalIdentity.get(AttNamesConstants.JSON_ATT_NAME_IDENTITY).getAsString();

            /*
             * Create a new server identity to talk with this client
             */
            ECCKeyPair serverIdentity = new ECCKeyPair();

            /*
             * Get json representation for the serverIdentity
             */
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(AttNamesConstants.JSON_ATT_NAME_SERVER_IDENTITY, serverIdentity.getPublicKey());

            /*
             * Construct a fermat packet whit the server identity
             */
            FermatPacket fermatPacket = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(temporalClientIdentity, serverIdentity.getPublicKey(), FermatPacketType.SERVER_HANDSHAKE_RESPOND, jsonObject.toString(), NetworkServiceType.UNDEFINED, serverIdentity.getPrivateKey());

            /*
             * Send the encode packet to the client
             */
            clientConnection.send(FermatPacketEncoder.encode(fermatPacket));

            /*
             * Add to the pending register client connection
             */
            pendingRegisterClientConnectionsCache.put(temporalClientIdentity, clientConnection);

            /*
             * Add to the cache the server identity for this client connection
             */
            serverIdentityByClientCache.put(clientConnection.hashCode(), serverIdentity);

        }else {

            clientConnection.closeConnection(404, "DENIED, NOT VALID HANDSHAKE");
        }
    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onClose(WebSocket, int, String, boolean)
     */
    @Override
    public void onClose(WebSocket clientConnection, int code, String reason, boolean remote) {

        System.out.println(" WsCommunicationCloudServer - Starting method onClose");
        System.out.println( clientConnection + " is disconnect!" );
        System.out.println( " code   = "+code );
        System.out.println( " reason = "+reason );
        System.out.println( " remote = "+remote );

    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onMessage(WebSocket, String)
     */
    @Override
    public void onMessage(WebSocket clientConnection, String message) {

        System.out.println(" WsCommunicationCloudServer - Starting method onMessage");

        FermatPacket fermatPacket = FermatPacketDecoder.decode(message,serverIdentityByClientCache.get(clientConnection.hashCode()).getPrivateKey());

        /*
         * Call the processors for this packet
         */
        for (FermatPacketProcessor fermatPacketProcessor :packetProcessorsRegister.get(fermatPacket.getFermatPacketType())) {

            /*
             * Processor make his job
             */
            fermatPacketProcessor.processingPackage(clientConnection, fermatPacket);
        }

    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onError(WebSocket, Exception)
     */
    @Override
    public void onError(WebSocket clientConnection, Exception ex) {

        System.out.println(" WsCommunicationCloudServer - Starting method onError");
        ex.printStackTrace();
        clientConnection.closeConnection(505, "- ERROR -");
    }


    /**
     * This method register a FermatPacketProcessor object with this
     * server
     */
    public void registerFermatPacketProcessorServerSideObject(FermatPacketProcessor fermatPacketProcessor) {

        /*
         * Set server reference
         */
        fermatPacketProcessor.setWsCommunicationCloudServer(this);

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



}
