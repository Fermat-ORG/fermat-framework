/*
 * @#CommunicationServer.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1.structure.CommunicationCloudServer</code> is
 * the web socket server to manage the communication
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 01/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationCloudServer extends WebSocketServer {

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
    private Map<String, ECCKeyPair> serverIdentityByClientCache;

    /**
     * Constructor with parameter
     * @param address
     */
    public CommunicationCloudServer(InetSocketAddress address) {
        super(address);
        this.pendingRegisterClientConnectionsCache = new ConcurrentHashMap<>();
        this.registeredClientConnectionsCache      = new ConcurrentHashMap<>();
        this.serverIdentityByClientCache           = new ConcurrentHashMap<>();
    }

    /**
     *
     * @param clientConnection
     * @param handshake
     */
    @Override
    public void onOpen(WebSocket clientConnection, ClientHandshake handshake) {

        System.out.println(" New Client: "+clientConnection.getRemoteSocketAddress().getAddress().getHostAddress() + " is connected!");
        System.out.println(" Handshake Resource Descriptor = " + handshake.getResourceDescriptor());

        if (handshake.getFieldValue("temp-idem") != null){

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject temporalIdentity = parser.parse(handshake.getFieldValue("temp-idem")).getAsJsonObject();

            String temporalClientIdentity = temporalIdentity.get("identity").toString();

            pendingRegisterClientConnectionsCache.put(temporalClientIdentity, clientConnection);

            ECCKeyPair serverIdentity = new ECCKeyPair();

            serverIdentityByClientCache.put(temporalClientIdentity, serverIdentity);

            JsonObject messageRespond = new JsonObject();
            messageRespond.addProperty("serverIdentity", serverIdentity.getPublicKey());

            String jsonMessageRespond = gson.toJson(messageRespond);
            String encryptedJsonMsjRespond = AsymmectricCryptography.encryptMessagePublicKey(jsonMessageRespond, temporalClientIdentity);

            clientConnection.send(encryptedJsonMsjRespond);

        }else {

            clientConnection.closeConnection(404, "DENIED, NOT VALID HANDSHAKE");
        }

    }

    @Override
    public void onClose(WebSocket clientConnection, int code, String reason, boolean remote) {

        System.out.println( clientConnection + " is disconnect!" );
        System.out.println( " code = "+code );
        System.out.println( " reason = "+reason );
        System.out.println( " remote = "+remote );


    }

    @Override
    public void onMessage(WebSocket conn, String message) {

        System.out.println( conn + ": " + message );

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }
}
