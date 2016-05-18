/*
 * @#WebSocketVpnServerChannel.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.ClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.conf.WebSocketConfigurator;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.ShareMemoryCacheForVpnClientsConnections;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.WebSocketVpnIdentity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.WebSocketVpnServerChannel</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@ServerEndpoint(value="/vpn/", configurator = WebSocketConfigurator.class)
public class WebSocketVpnServerChannel {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(WebSocketVpnServerChannel.class));

    /**
     *  Represent the vpnServerIdentity
     */
    private ECCKeyPair vpnServerIdentity;

    /**
     *  Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     *  Represent the vpnClientConnection
     */
    private VpnClientConnection vpnClientConnection;

    /*
     * We use messageComplete to handle the packets that are sent in parts
     * we set the messageComplete to empty because
     * if we do with null the messageComplete after will concat like a word and show exception in its handle
     */
     private String messageComplete = "";

    /**
     * Constructor
     */
    public WebSocketVpnServerChannel(){
        super();
        this.vpnServerIdentity = WebSocketVpnIdentity.getInstance().getIdentity();
    }

    /**
     * When a new websocket client connect with this server
     *
     * @param session of the client
     * @throws IOException
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

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject contentJsonObject = parser.parse(temp_i).getAsJsonObject();

             /*
             * Get the identity send by the participant
             */
            networkServiceType                   = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE), NetworkServiceType.class);
            String vpnClientIdentity             = contentJsonObject.get(JsonAttNamesConstants.CLIENT_IDENTITY_VPN).getAsString();
            PlatformComponentProfile participant = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN).getAsString(), PlatformComponentProfileCommunication.class);
            String remoteParticipantIdentity     = contentJsonObject.get(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN).getAsString();

            LOG.info("participant.getAlias = " + participant.getAlias());

            /*
             * Get the client identity and the participant profile
             */
            vpnClientConnection = new VpnClientConnection(vpnClientIdentity, participant, remoteParticipantIdentity, session, networkServiceType);
            ShareMemoryCacheForVpnClientsConnections.add(vpnClientConnection);

            Boolean allConnected = ShareMemoryCacheForVpnClientsConnections.isConnected(networkServiceType, vpnClientConnection.getKeyForMyRemote());
            LOG.info("All participant are connected = " + allConnected);

            //Validate if all participantsConnections register are connect
            if(allConnected){

                PlatformComponentProfile peer1 = participant;
                PlatformComponentProfile peer2 = ShareMemoryCacheForVpnClientsConnections.getMyRemote(vpnClientConnection).getParticipant();

                LOG.info("peer1 is = " + peer1.getAlias());
                LOG.info("peer2 is = " + peer2.getAlias());

                sendNotificationVpnConnectionComplete(peer1, peer2);
                sendNotificationVpnConnectionComplete(peer2, peer1);

            }

        }else {

            if (session.isOpen()) {
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "DENIED, NOT VALID HANDSHAKE"));
            }
        }

    }

    /**
     * When a new fermatPacketEncode is receive
     *
     * @param fermatPacketEncode value
     */
    @OnMessage
    public void fermatPacketReceive(String fermatPacketEncode, boolean lastPacket, Session session){

        /*
         *  if fermatPacketEncode is the las Packet then
         *  the messageComplete concats the string and handle packet correctly
         */
        if(lastPacket) {

            messageComplete = messageComplete + fermatPacketEncode;

            LOG.info("-----------------------------------------------------------");
            LOG.debug("Received TEXT message: " + messageComplete);

        /*
         * Decode the fermatPacketEncode into a fermatPacket
         */
            FermatPacket receiveFermatPacket = FermatPacketDecoder.decode(messageComplete, vpnServerIdentity.getPrivateKey());


            if (receiveFermatPacket.getFermatPacketType() == FermatPacketType.MESSAGE_TRANSMIT) {

                /*
                 * Get the FermatMessage from the message content and decrypt
                 */
                String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), vpnServerIdentity.getPrivateKey());

                /*
                 * Construct the fermat message object
                 */
                FermatMessageCommunication fermatMessage = (FermatMessageCommunication) new FermatMessageCommunication().fromJson(messageContentJsonStringRepresentation);


                LOG.debug("fermatMessage = " + fermatMessage);

                /*
                * Construct a new fermat packet whit the same message and different destination
                */
                FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(vpnClientConnection.getVpnClientIdentity(), //Destination
                        vpnServerIdentity.getPublicKey(),           //Sender
                        fermatMessage.toJson(),                     //Message Content
                        FermatPacketType.MESSAGE_TRANSMIT,          //Packet type
                        vpnServerIdentity.getPrivateKey());         //Sender private key

                String key = fermatMessage.getReceiver() + fermatMessage.getSender();

                /*
                 * Get the connection of the destination
                 */
                VpnClientConnection clientConnectionDestination = ShareMemoryCacheForVpnClientsConnections.getMyRemote(vpnClientConnection.getNetworkServiceType(), key);

                /*
                 * If the connection to client destination available
                 */
                if (clientConnectionDestination != null && clientConnectionDestination.getSession().isOpen()) {

                    LOG.info("Sending msg to: " + clientConnectionDestination.getParticipant().getAlias());

                   /*
                    * Send the encode packet to the destination
                    */
                    clientConnectionDestination.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

                }

            } else {
                LOG.warn("Packet type " + receiveFermatPacket.getFermatPacketType() + "is not supported");
            }

            /*
             * we set the messageComplete to empty because if we do with null the messageComplete after will concat like a word
             * and show exception in its handle
             */
            messageComplete = "";

        }else{

            /*
             * the messageComplete concats the string
             */
            messageComplete = messageComplete + fermatPacketEncode;

        }

    }

    /**
     * Whe a web socket client close the connection
     *
     * @param reason of the closure
     */
    @OnClose
    public void onWebSocketClose(CloseReason reason) {

        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Starting method onWebSocketClose");
        LOG.info("Socket " + vpnClientConnection.getSession().getId() + "(" + vpnClientConnection.getParticipant().getAlias() + ") is disconnect! code = " + reason.getCloseCode() + "[" + reason.getCloseCode().getCode() + "] reason = " + reason.getReasonPhrase());

        VpnClientConnection vpnClientConnectionRemote = ShareMemoryCacheForVpnClientsConnections.getMyRemote(vpnClientConnection);

        try {

            if (reason.getCloseCode().equals(CloseReason.CloseCodes.NORMAL_CLOSURE)) {
                if (vpnClientConnectionRemote != null)
                    vpnClientConnectionRemote.getSession().close(new CloseReason(reason.getCloseCode(), "The remote participant close the connection. Details: "+reason.getReasonPhrase()));

            }else {
                if (vpnClientConnectionRemote != null)
                    vpnClientConnectionRemote.getSession().close(new CloseReason(reason.getCloseCode(), "Details: "+reason.getReasonPhrase()));
            }

            ShareMemoryCacheForVpnClientsConnections.remove(networkServiceType, vpnClientConnection.getMyKey());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When occur a error
     *
     * @param cause of the error
     */
    @OnError
    public void onWebSocketError(Throwable cause){

        try {

            LOG.error(cause.getMessage());
            vpnClientConnection.getSession().close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "Server detect a error, close connection. Details: "+cause.getMessage()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Construct a packet whit the information that a vpn is ready
     *
     * @param destinationPlatformComponentProfile
     * @param remotePlatformComponentProfile
     */
    private void sendNotificationVpnConnectionComplete(PlatformComponentProfile destinationPlatformComponentProfile, PlatformComponentProfile remotePlatformComponentProfile){

        LOG.info("sendNotificationVpnConnectionComplete = " + destinationPlatformComponentProfile.getName() + " (" + destinationPlatformComponentProfile.getIdentityPublicKey() + ")");

         /*
         * Construct the content of the msj
         */
        Gson gson = new Gson();
        JsonObject packetContent = new JsonObject();
        packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN,  remotePlatformComponentProfile.toJson());
        packetContent.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN,  destinationPlatformComponentProfile.toJson());
        packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceType.toString());

        /*
         * Get the connection client of the destination
         * IMPORTANT: No send by vpn connection, no support this type of packet
         */
        ClientConnection clientConnectionDestination = MemoryCache.getInstance().getRegisteredClientConnectionsCache().get(destinationPlatformComponentProfile.getCommunicationCloudClientIdentity());

        /*
        * Construct a new fermat packet whit the same message and different destination
        */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(destinationPlatformComponentProfile.getCommunicationCloudClientIdentity(), //Destination
                                                                                                                    clientConnectionDestination.getServerIdentity().getPublicKey(), //Sender
                                                                                                                    gson.toJson(packetContent),                                        //Message Content
                                                                                                                    FermatPacketType.COMPLETE_COMPONENT_CONNECTION_REQUEST,            //Packet type
                                                                                                                    clientConnectionDestination.getServerIdentity().getPrivateKey()); //Sender private key
        /*
        * Send the encode packet to the destination
        */
        clientConnectionDestination.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

    }

    /**
     * Construct a packet whit the information that a participant of a vpn is already reconnect
     */
    private void sendNotificationPacketReconnected(VpnClientConnection vpnClientConnection){

        LOG.info("sendNotificationPacketReconnected");

           VpnClientConnection connectionRemote = ShareMemoryCacheForVpnClientsConnections.getMyRemote(vpnClientConnection);

            /*
             * Construct the content of the msj
             */
            Gson gson = new Gson();
            JsonObject packetContent = new JsonObject();
            packetContent.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, vpnClientConnection.getParticipant().getIdentityPublicKey());
            packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceType.toString());
            packetContent.addProperty(JsonAttNamesConstants.RECONNECTED, Boolean.TRUE);

            /*
            * Construct a notification
            */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(connectionRemote.getVpnClientIdentity(), //Destination
                                                                                                                        vpnServerIdentity.getPublicKey(),                      //Sender
                                                                                                                        gson.toJson(packetContent),                            //Message Content
                                                                                                                        FermatPacketType.MESSAGE_TRANSMIT,                     //Packet type
                                                                                                                        vpnServerIdentity.getPrivateKey());                    //Sender private key
            /*
             * Send notification
             */
        connectionRemote.getSession().getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRespond));

    }

}
