/*
 * @#WebSocketCloudServerChannel.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.conf.WebSocketConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.processors.FermatJettyPacketProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.WebSocketCloudServerChannel</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ServerEndpoint(value="/ws/", configurator = WebSocketConfigurator.class)
public class WebSocketCloudServerChannel {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(WebSocketCloudServerChannel.class));

    /**
     * Represent the active client Connection
     */
    private ClientConnection activeClientConnection;

    /**
     * Represent the messageComplete
     */
    private String messageComplete;

    /**
     * Constructor
     */
    public WebSocketCloudServerChannel(){
        super();
        messageComplete = "";
    }

    /**
     * Method called on open a new web socket connection
     */
    @OnOpen
    public void onWebSocketConnect(Session session) throws IOException {

        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Starting method onOpen");
        LOG.info("New Client: " + session.getId() + " is connected!");

        String temp_i = (String) session.getUserProperties().get(JsonAttNamesConstants.HEADER_ATT_NAME_TI);
        LOG.info("temp-i = " + temp_i);

        /*
         * Validate is a handshake valid
         */
        if (temp_i != null && temp_i != ""){

            activeClientConnection = new ClientConnection(session);

            /*
             * Get the temporal identity of the CommunicationsClientConnection component
             */
            JsonParser parser = new JsonParser();
            JsonObject temporalIdentity = parser.parse(temp_i).getAsJsonObject();
            String temporalClientIdentity = temporalIdentity.get(JsonAttNamesConstants.NAME_IDENTITY).getAsString();

            /*
             * Create a new server identity to talk with this client
             */
            ECCKeyPair serverIdentity = new ECCKeyPair();

            /*
             * Get json representation for the serverIdentity
             */
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(JsonAttNamesConstants.SERVER_IDENTITY, serverIdentity.getPublicKey());

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
            session.getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacket));

            /*
             * Add to the pending register client connection
             */
            MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().put(temporalClientIdentity, activeClientConnection);

            /*
             * Add the server identity for this client connection
             */
            activeClientConnection.setServerIdentity(serverIdentity);

            /*
             * Add client identity for his client connection
             */
            activeClientConnection.setClientIdentity(temporalClientIdentity);

        }else {

            if (session.isOpen()) {
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "DENIED, NOT VALID HANDSHAKE"));
            }
        }

        LOG.info("session.getOpenSessions().size() = " + session.getOpenSessions().size());

    }

    @OnMessage
    public void onWebSocketText(String fermatPacketEncode, boolean lastPacket, Session session) {

        /*
         *  if fermatPacketEncode is the las Packet then
         *  the messageComplete concat the string and handle packet correctly
         */
        if(lastPacket) {

            /*
             * We use messageComplete to handle the packets that are sent in parts
             * we set the messageComplete to empty because
             * if we do with null the messageComplete after will concat like a word and show exception in its handle
             */
            messageComplete = messageComplete + fermatPacketEncode;

            LOG.info(" --------------------------------------------------------------------- ");
            LOG.info("Starting method onWebSocketText");

            if(!isJSONVALID(messageComplete)) {
                LOG.info(" --------------------------------MESSAGE BAD ASSEMBLY------------------------------------- ");
            }else {
            /*
             * Get the server identity for this client connection
             */
                ECCKeyPair serverIdentity = activeClientConnection.getServerIdentity();

            /*
             * Decode the fermatPacketEncode into a fermatPacket
             */
                FermatPacket fermatPacketReceive = FermatPacketDecoder.decode(messageComplete, serverIdentity.getPrivateKey());


                LOG.info("fermatPacket.getFermatPacketType() = " + fermatPacketReceive.getFermatPacketType());


                //verify is packet supported
                if (MemoryCache.getInstance().getPacketProcessorsRegister().containsKey(fermatPacketReceive.getFermatPacketType())) {


            /*
             * Call the processors for this packet
             */
                    for (FermatJettyPacketProcessor fermatPacketProcessor : MemoryCache.getInstance().getPacketProcessorsRegister().get(fermatPacketReceive.getFermatPacketType())) {

                /*
                 * Processor make his job
                 */
                        fermatPacketProcessor.processingPackage(activeClientConnection, fermatPacketReceive);
                    }


                } else {
                    LOG.info("Packet type " + fermatPacketReceive.getFermatPacketType() + "is not supported");
                }
            }
            /*
             * we set the messageComplete to empty because if we do with null the messageComplete after will concat like a word
             * and show exception in its handle
             */
            messageComplete = "";

        }else{

            /*
             * the messageComplete concat the string
             */
            messageComplete = messageComplete + fermatPacketEncode;

        }

    }

    @OnMessage
    public void onPongMessage(PongMessage message) {
        LOG.debug("Pong message receive from server = " + message);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Starting method onWebSocketClose");
        LOG.info("Socket " + activeClientConnection.getSession().getId() + " is disconnect! code = " + reason.getCloseCode() + "[" + reason.getCloseCode().getCode() + "] reason = " + reason.getReasonPhrase());

        if (reason.getCloseCode().equals(CloseReason.CloseCodes.CLOSED_ABNORMALLY)) {
            LOG.info("Waiting for client reconnect");

            final String clientIdentity = activeClientConnection.getClientIdentity();
            final String id = activeClientConnection.getSession().getId();

            if (clientIdentity != null && clientIdentity != "") {

                MemoryCache.getInstance().putReferencesToStandBy(activeClientConnection);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       LOG.info("Client (" + id + ") not reconnect, proceed to clean references on stand by");
                                       MemoryCache.getInstance().getStandByProfileByClientIdentity().remove(clientIdentity);
                                       LOG.info("Number of list of profiles into standby cache = " + MemoryCache.getInstance().getStandByProfileByClientIdentity().size());
                                   }
                               },
                        60000
                );

                MemoryCache.getInstance().getTimersByClientIdentity().put(clientIdentity, timer);
            }

        } else {
            MemoryCache.getInstance().cleanReferences(activeClientConnection);
        }
    }

    @OnError
    public void onWebSocketError(Throwable cause) throws IOException {

        LOG.info("--------------------------------------------------------------------- ");
        LOG.info("Starting method onWebSocketError");
        cause.printStackTrace();
        MemoryCache.getInstance().cleanReferences(activeClientConnection);

        if (activeClientConnection.getSession().isOpen()) {
            activeClientConnection.getSession().close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, cause.getMessage()));
        }

    }

    private boolean isJSONVALID(String JSON_STRING){

        ECCKeyPair serverIdentity = activeClientConnection.getServerIdentity();

        try{

            FermatPacket fermatPacketReceive = FermatPacketDecoder.decode(JSON_STRING, serverIdentity.getPrivateKey());

            return Boolean.TRUE;

        }catch (Exception e){

//            LOG.info(AsymmetricCryptography.decryptMessagePrivateKey(JSON_STRING, serverIdentity.getPrivateKey()));

            return Boolean.FALSE;
        }
    }

}
