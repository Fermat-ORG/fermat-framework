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
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.server.CommunicationCloudServer;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.FermatPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.vpn.WsCommunicationVpnServerManagerAgent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
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
     * Represent the wsCommunicationVpnServerManagerAgent
     */
    private WsCommunicationVpnServerManagerAgent wsCommunicationVpnServerManagerAgent;

    /**
     * Holds the pending register clients connections cache
     */
    private Map<String, WebSocket> pendingRegisterClientConnectionsCache;

    /**
     * Holds the registered clients connections cache
     */
    private Map<String, WebSocket> registeredClientConnectionsCache;

    /**
     * Holds the identity of the server by client the client connection hash
     */
    private Map<Integer, ECCKeyPair> serverIdentityByClientCache;

    /**
     * Holds the identity of the client by his connection hash
     */
    private Map<Integer, String> clientIdentityByClientConnectionCache;

    /**
     * Holds the packet processors objects
     */
    private Map<FermatPacketType, List<FermatPacketProcessor>> packetProcessorsRegister;

    //TODO: THE REGISTERED COMPONENT WOULD BE HOLD IN DATA BASE AND NO IN MEMORY, FOR SEARCH IN FUTURE

    /**
     * Holds all the registered Communications Cloud Server by his client connection hash
     */
    private Map<Integer, PlatformComponentProfile> registeredCommunicationsCloudServerCache;

    /**
     * Holds all the registered Communications Cloud Client by his client connection hash
     */
    private Map<Integer, PlatformComponentProfile> registeredCommunicationsCloudClientCache;

    /**
     * Holds all the Platform Component Profile register by type
     */
    private Map<PlatformComponentType, Map<NetworkServiceType, List<PlatformComponentProfile>>> registeredPlatformComponentProfileCache;

    /**
     * Constructor with parameter
     * @param address
     */
    public WsCommunicationCloudServer(InetSocketAddress address) {
        super(address);
        this.wsCommunicationVpnServerManagerAgent     = new WsCommunicationVpnServerManagerAgent(address.getHostString(), address.getPort());
        this.pendingRegisterClientConnectionsCache    = new ConcurrentHashMap<>();
        this.registeredClientConnectionsCache         = new ConcurrentHashMap<>();
        this.serverIdentityByClientCache              = new ConcurrentHashMap<>();
        this.clientIdentityByClientConnectionCache    = new ConcurrentHashMap<>();
        this.packetProcessorsRegister                 = new ConcurrentHashMap<>();
        this.registeredCommunicationsCloudServerCache = new ConcurrentHashMap<>();
        this.registeredCommunicationsCloudClientCache = new ConcurrentHashMap<>();
        this.registeredPlatformComponentProfileCache  = new ConcurrentHashMap<>();
    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onOpen(WebSocket, ClientHandshake)
     */
    @Override
    public void onOpen(WebSocket clientConnection, ClientHandshake handshake) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationCloudServer - Starting method onOpen");
        System.out.println(" WsCommunicationCloudServer - New Client: " + clientConnection.getRemoteSocketAddress().getAddress().getHostAddress() + " is connected!");
        System.out.println(" WsCommunicationCloudServer - Handshake Resource Descriptor = " + handshake.getResourceDescriptor());
        System.out.println(" WsCommunicationCloudServer - temp-i = " + handshake.getFieldValue(AttNamesConstants.HEADER_ATT_NAME_TI));

        /*
         * Validate is a handshake valid
         */
        if (handshake.getFieldValue(AttNamesConstants.HEADER_ATT_NAME_TI)     != null &&
                handshake.getFieldValue(AttNamesConstants.HEADER_ATT_NAME_TI) != ""){

            /*
             * Get the temporal identity of the CommunicationsCloudClientConnection componet
             */
            JsonParser parser = new JsonParser();
            JsonObject temporalIdentity = parser.parse(handshake.getFieldValue(AttNamesConstants.HEADER_ATT_NAME_TI)).getAsJsonObject();
            String temporalClientIdentity = temporalIdentity.get(AttNamesConstants.JSON_ATT_NAME_IDENTITY).getAsString();

            /*
             * Create a new server identity to talk with this client
             */
            ECCKeyPair serverIdentity = new ECCKeyPair();


            System.out.println(" WsCommunicationCloudServer - private key for this connection = "+serverIdentity.getPrivateKey());
            System.out.println(" WsCommunicationCloudServer - public key for this connection = "+serverIdentity.getPublicKey());


            /*
             * Get json representation for the serverIdentity
             */
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(AttNamesConstants.JSON_ATT_NAME_SERVER_IDENTITY, serverIdentity.getPublicKey());

            /*
             * Construct a fermat packet whit the server identity
             */
            FermatPacket fermatPacket = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(temporalClientIdentity,                   //Destination
                                                                                                                 serverIdentity.getPublicKey(),            //Sender
                                                                                                                 jsonObject.toString(),                    //Message Content
                                                                                                                 FermatPacketType.SERVER_HANDSHAKE_RESPOND,//Packet type
                                                                                                                 serverIdentity.getPrivateKey());          //Sender private key
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

            /**
             * Add to the cache of client identity for his client connection
             */
            clientIdentityByClientConnectionCache.put(clientConnection.hashCode(), temporalClientIdentity);

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

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationCloudServer - Starting method onClose");
        System.out.println(" WsCommunicationCloudServer - " +clientConnection.getRemoteSocketAddress() + " is disconnect! code = " + code +" reason = " + reason +" remote = " + remote);
        cleanReferences(clientConnection);
    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onMessage(WebSocket, String)
     */
    @Override
    public void onMessage(WebSocket clientConnection, String fermatPacketEncode) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationCloudServer - Starting method onMessage");
        System.out.println(" WsCommunicationCloudServer - encode fermatPacket = " + fermatPacketEncode);
        System.out.println(" WsCommunicationCloudServer - server identity for this connection = " + serverIdentityByClientCache.get(clientConnection.hashCode()).getPrivateKey());

        /*
         * Get the server identity for this client connection
         */
        ECCKeyPair serverIdentity = serverIdentityByClientCache.get(clientConnection.hashCode());

        /*
         * Decode the fermatPacketEncode into a fermatPacket
         */
        FermatPacket fermatPacketReceive = FermatPacketDecoder.decode(fermatPacketEncode, serverIdentity.getPrivateKey());

        System.out.println(" WsCommunicationCloudServer - decode fermatPacket = "+fermatPacketReceive);
        System.out.println(" WsCommunicationCloudServer - fermatPacket.getFermatPacketType() = " + fermatPacketReceive.getFermatPacketType());


        //verify is packet supported
        if (packetProcessorsRegister.containsKey(fermatPacketReceive.getFermatPacketType())){


            /*
             * Call the processors for this packet
             */
            for (FermatPacketProcessor fermatPacketProcessor :packetProcessorsRegister.get(fermatPacketReceive.getFermatPacketType())) {

                /*
                 * Processor make his job
                 */
                fermatPacketProcessor.processingPackage(clientConnection, fermatPacketReceive, serverIdentity);
            }


        }else {

            System.out.println(" WsCommunicationCloudServer - Packet type " + fermatPacketReceive.getFermatPacketType() + "is not supported");

        }

    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onError(WebSocket, Exception)
     */
    @Override
    public void onError(WebSocket clientConnection, Exception ex) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationCloudServer - Starting method onError");
        ex.printStackTrace();

        cleanReferences(clientConnection);

        /*
         * Close the connection
         */
        clientConnection.closeConnection(505, "- ERROR :" + ex.getLocalizedMessage());
    }

    @Override
    public void onWebsocketPing(WebSocket conn, Framedata f) {

        System.out.println(" WsCommunicationCloudServer - onWebsocketPing");
        System.out.println(" WsCommunicationCloudServer - Framedata = " + f.getOpcode());
        super.onWebsocketPing(conn, f);
    }

    /**
     * This method register a FermatPacketProcessor object with this
     * server
     */
    public void registerFermatPacketProcessor(FermatPacketProcessor fermatPacketProcessor) {

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

        System.out.println(" WsCommunicationCloudServer - packetProcessorsRegister = " + packetProcessorsRegister.size());

    }

    /**
     * Clean all reference from the connection
     */
    private void cleanReferences(WebSocket clientConnection){

        /*
         * Clean all the caches, remove data bind whit this connection
         */
        //TODO: REMOVE ALL COMPONENT REGISTER WITH THIS CONNECTION AND THIS IS MORE EASY IS IN DATA BASE
        removePlatformComponentRegisteredByClientIdentity(clientIdentityByClientConnectionCache.get(clientConnection.hashCode()));
        pendingRegisterClientConnectionsCache.remove(clientIdentityByClientConnectionCache.get(clientConnection.hashCode()));
        registeredClientConnectionsCache.remove(clientIdentityByClientConnectionCache.get(clientConnection.hashCode()));
        serverIdentityByClientCache.remove(clientConnection.hashCode());
        clientIdentityByClientConnectionCache.remove(clientConnection.hashCode());
        registeredCommunicationsCloudServerCache.remove(clientConnection.hashCode());
        registeredCommunicationsCloudClientCache.remove(clientConnection.hashCode());

        System.out.println(" WsCommunicationCloudServer - pendingRegisterClientConnectionsCache.size() = " + pendingRegisterClientConnectionsCache.size());
        System.out.println(" WsCommunicationCloudServer - registeredClientConnectionsCache.size() = "+registeredClientConnectionsCache.size());
        System.out.println(" WsCommunicationCloudServer - serverIdentityByClientCache.size() = "+serverIdentityByClientCache.size());
        System.out.println(" WsCommunicationCloudServer - clientIdentityByClientConnectionCache.size() = " + clientIdentityByClientConnectionCache.size());
        System.out.println(" WsCommunicationCloudServer - registeredCommunicationsCloudServerCache.size() = "+registeredCommunicationsCloudServerCache.size());
        System.out.println(" WsCommunicationCloudServer - registeredCommunicationsCloudClientCache.size() = "+registeredCommunicationsCloudClientCache.size());
    }

    /**
     * This method unregister all platform component profile
     * register
     */
    private void removePlatformComponentRegisteredByClientIdentity(String clientIdentity){

        System.out.println(" WsCommunicationCloudServer - removePlatformComponentRegisterByClientIdentity ");

        for (PlatformComponentType platformComponentType : registeredPlatformComponentProfileCache.keySet()) {

            for (NetworkServiceType networkServiceType : registeredPlatformComponentProfileCache.get(platformComponentType).keySet()) {

                for (PlatformComponentProfile platformComponentProfile : registeredPlatformComponentProfileCache.get(platformComponentType).get(networkServiceType)) {

                    if(platformComponentProfile.getCommunicationCloudClientIdentity().equals(clientIdentity)){

                        System.out.println(" WsCommunicationCloudServer - unregister = " + platformComponentProfile.getCommunicationCloudClientIdentity());
                        registeredPlatformComponentProfileCache.get(platformComponentType).get(networkServiceType).remove(platformComponentProfile);
                        break;

                    }
                }

            }

        }

    }

    /**
     * Get the wsCommunicationVpnServerManagerAgent
     * @return WsCommunicationVpnServerManagerAgent
     */
    public WsCommunicationVpnServerManagerAgent getWsCommunicationVpnServerManagerAgent() {
        return wsCommunicationVpnServerManagerAgent;
    }

    /**
     * Get the ClientIdentityByClientConnectionCache
     *
     * @return Map<Integer, String>
     */
    public Map<Integer, String> getClientIdentityByClientConnectionCache() {
        return clientIdentityByClientConnectionCache;
    }

    /**
     * Get the PendingRegisterClientConnectionsCache
     *
     * @return Map<String, WebSocket>
     */
    public Map<String, WebSocket> getPendingRegisterClientConnectionsCache() {
        return pendingRegisterClientConnectionsCache;
    }

    /**
     * Get the RegisteredClientConnectionsCache
     *
     * @return Map<String, WebSocket>
     */
    public Map<String, WebSocket> getRegisteredClientConnectionsCache() {
        return registeredClientConnectionsCache;
    }

    /**
     * Get the ServerIdentityByClientCache
     * @return Map<Integer, ECCKeyPair>
     */
    public Map<Integer, ECCKeyPair> getServerIdentityByClientCache() {
        return serverIdentityByClientCache;
    }

    /**
     * Get the RegisteredPlatformComponentProfileCache
     * @return Map<PlatformComponentType, Map<NetworkServiceType, List<PlatformComponentProfile>>>
     */
    public Map<PlatformComponentType, Map<NetworkServiceType, List<PlatformComponentProfile>>> getRegisteredPlatformComponentProfileCache() {
        return registeredPlatformComponentProfileCache;
    }

    /**
     * Get the RegisteredCommunicationsCloudServerCache
     * @return Map<Integer, PlatformComponentProfile>
     */
    public Map<Integer, PlatformComponentProfile> getRegisteredCommunicationsCloudServerCache() {
        return registeredCommunicationsCloudServerCache;
    }

    /**
     * Get the RegisteredCommunicationsCloudClientCache
     * @return Map<Integer, PlatformComponentProfile>
     */
    public Map<Integer, PlatformComponentProfile> getRegisteredCommunicationsCloudClientCache() {
        return registeredCommunicationsCloudClientCache;
    }

}
