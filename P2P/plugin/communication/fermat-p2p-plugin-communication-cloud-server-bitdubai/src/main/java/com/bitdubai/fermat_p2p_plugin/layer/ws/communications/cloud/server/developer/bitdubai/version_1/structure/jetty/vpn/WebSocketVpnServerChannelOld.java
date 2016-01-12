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
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.VpnShareMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.WebSocketVpnIdentity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;


/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.WebSocketVpnServerChannel</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WebSocketVpnServerChannelOld extends WebSocketAdapter {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(WebSocketVpnServerChannelOld.class));

    /**
     *  Represent the vpnServerIdentity
     */
    private ECCKeyPair vpnServerIdentity;

    /**
     *  Represent the vpnId
     */
    private String vpnId;

    /**
     *  Represent the vpnClientConnection
     */
    private VpnClientConnection vpnClientConnection;

    /**
     *  Represent the participantPendingToReconnect
     */
    private PlatformComponentProfile participantPendingToReconnect;

    /**
     *  Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Constructor
     */
    public WebSocketVpnServerChannelOld(){
        super();
        this.vpnId = UUID.randomUUID().toString();
        this.vpnServerIdentity = WebSocketVpnIdentity.getInstance().getIdentity();
    }

    @Override
    public void onWebSocketConnect(Session session) {

        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Starting method onWebSocketConnect");
        LOG.info("vpnId= " + vpnId);
        LOG.info("New Client: " + session.getRemoteAddress().toString() + " is connected!");
        String temp_i = session.getUpgradeRequest().getHeader(JsonAttNamesConstants.HEADER_ATT_NAME_TI);
        LOG.info("temp-i = " + temp_i);

        /*
         * Validate is a handshake valid
         */
        if (temp_i != null && temp_i != ""){

            String messageContentJsonStringRepresentation =  AsymmetricCryptography.decryptMessagePrivateKey(temp_i, vpnServerIdentity.getPrivateKey());

            LOG.info("messageContentJsonStringRepresentation = " + messageContentJsonStringRepresentation);

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject contentJsonObject = parser.parse(messageContentJsonStringRepresentation).getAsJsonObject();

            /*
             * Get the identity send by the participant
             */
            networkServiceType                   = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.NETWORK_SERVICE_TYPE), NetworkServiceType.class);
            String vpnClientIdentity             = contentJsonObject.get(JsonAttNamesConstants.CLIENT_IDENTITY_VPN).getAsString();
            PlatformComponentProfile participant = gson.fromJson(contentJsonObject.get(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN).getAsString(), PlatformComponentProfileCommunication.class);
            String remoteParticipantIdentity     = contentJsonObject.get(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN).getAsString();

            /*
             * Get the client identity and the participant profile
             */
            //vpnClientConnection = new VpnClientConnection(vpnClientIdentity, participant, remoteParticipantIdentity, session);
            getParticipantsConnections().put(vpnClientConnection.getParticipant().getIdentityPublicKey()+"_"+networkServiceType.toString(), vpnClientConnection);

            if (participantPendingToReconnect == null) {

                LOG.info("All participant are connected = " + getParticipantsConnections().containsKey(remoteParticipantIdentity+"_"+networkServiceType.toString()));

                //Validate if all participantsConnections register are connect
                if(getParticipantsConnections().containsKey(remoteParticipantIdentity+"_"+networkServiceType.toString())){

                    List<VpnClientConnection> participants = new ArrayList<>(getParticipantsConnections().values());

                    PlatformComponentProfile peer1 = participants.get(0).getParticipant();
                    PlatformComponentProfile peer2 = participants.get((participants.size()-1)).getParticipant();

                    sendNotificationPacketConnectionComplete(peer1, peer2);
                    sendNotificationPacketConnectionComplete(peer2, peer1);

                }

            }else {

                /*
                 * Validate if the client
                 */
                if (participantPendingToReconnect.getIdentityPublicKey().equals(participant.getIdentityPublicKey())){

                    //Notify to the remote participant already connecting again
                    sendNotificationPacketReconnected(vpnClientConnection);
                    participantPendingToReconnect = null;
                }

            }

        }else {

            if (session.isOpen()) {
                session.close(CloseReason.CloseCodes.PROTOCOL_ERROR.getCode(), "DENIED, NOT VALID HANDSHAKE");
            }
        }

    }

    @Override
    public void onWebSocketText(String fermatPacketEncode){

        LOG.info("-----------------------------------------------------------");
        LOG.info("Received TEXT message: " + fermatPacketEncode);

        /*
         * Decode the fermatPacketEncode into a fermatPacket
         */
        FermatPacket receiveFermatPacket = FermatPacketDecoder.decode(fermatPacketEncode, vpnServerIdentity.getPrivateKey());


        if (receiveFermatPacket.getFermatPacketType() == FermatPacketType.MESSAGE_TRANSMIT){

            /*
             * Get the FermatMessage from the message content and decrypt
             */
            String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), vpnServerIdentity.getPrivateKey());

            /*
             * Construct the fermat message object
             */
            FermatMessageCommunication fermatMessage = (FermatMessageCommunication) new FermatMessageCommunication().fromJson(messageContentJsonStringRepresentation);


            LOG.info("fermatMessage = " + fermatMessage);

            /*
            * Construct a new fermat packet whit the same message and different destination
            */
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(vpnClientConnection.getVpnClientIdentity(), //Destination
                    vpnServerIdentity.getPublicKey(),           //Sender
                    fermatMessage.toJson(),                     //Message Content
                    FermatPacketType.MESSAGE_TRANSMIT,          //Packet type
                    vpnServerIdentity.getPrivateKey());         //Sender private key
            /*
             * Get the connection of the destination
             */
            VpnClientConnection clientConnectionDestination = getParticipantsConnections().get(fermatMessage.getReceiver()+"_"+networkServiceType.toString());

            //LOG.info("clientConnectionDestination = " + clientConnectionDestination.getSession().getRemoteAddress());

            /*
             * If the connection to client destination available
             */
            if (clientConnectionDestination != null && clientConnectionDestination.getSession().isOpen()){

                LOG.info("Sending msg to: " + clientConnectionDestination.getParticipant().getAlias());

               /*
                * Send the encode packet to the destination

                try {

                    //clientConnectionDestination.getSession().getRemote().sendString(FermatPacketEncoder.encode(fermatPacketRespond));

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }

        }else {
            LOG.warn("Packet type " + receiveFermatPacket.getFermatPacketType() + "is not supported");
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason){
        System.out.println("Socket Closed: ["+statusCode+"] " + reason);
    }

    @Override
    public void onWebSocketError(Throwable cause){
        cause.printStackTrace(System.err);
    }

    /**
     * Construct a packet whit the information that a vpn is ready
     *
     * @param destinationPlatformComponentProfile
     * @param remotePlatformComponentProfile
     */
    private void sendNotificationPacketConnectionComplete(PlatformComponentProfile destinationPlatformComponentProfile, PlatformComponentProfile remotePlatformComponentProfile){

        LOG.info("sendNotificationPacketConnectionComplete = " + destinationPlatformComponentProfile.getName() + " (" + destinationPlatformComponentProfile.getIdentityPublicKey() + ")");

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

        Iterator<VpnClientConnection> iterator = getParticipantsConnections().values().iterator();
        while (iterator.hasNext()){

            VpnClientConnection connection = iterator.next();

            if (!connection.getSession().equals(vpnClientConnection.getSession())){

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
                FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(connection.getVpnClientIdentity(), //Destination
                                                                                                                            vpnServerIdentity.getPublicKey(),                      //Sender
                                                                                                                            gson.toJson(packetContent),                            //Message Content
                                                                                                                            FermatPacketType.MESSAGE_TRANSMIT,                     //Packet type
                                                                                                                            vpnServerIdentity.getPrivateKey());                    //Sender private key

                /*
                 * Send notification

                try {

                    //connection.getSession().getRemote().sendString(FermatPacketEncoder.encode(fermatPacketRespond));

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                break;
            }
        }

    }


    /**
     * Get the participantsConnections value
     *
     * @return participantsConnections current value
     */
    private Map<String, VpnClientConnection> getParticipantsConnections() {

        if (VpnShareMemoryCache.isConnected(networkServiceType, "CONNECTION_MAP")){
            return (Map<String, VpnClientConnection>) VpnShareMemoryCache.get(networkServiceType, "CONNECTION_MAP");
        }else {
            Map<String, VpnClientConnection> connectionMap = new ConcurrentHashMap<>();
            //VpnShareMemoryCache.getInstance().add("CONNECTION_MAP", connectionMap);
            return connectionMap;
        }
    }
}
