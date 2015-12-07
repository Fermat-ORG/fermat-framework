/*
 * @#WsCommunicationVPNServer.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.vpn;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationVPNServer</code> is
 * a communication vpn server
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationVPNServer extends WebSocketServer{

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(WsCommunicationVPNServer.class));

    /**
     * Represent the WS_PROTOCOL
     */
    private static final String WS_PROTOCOL = "ws://";

    /**
     * Represent the vpnServerIdentity
     */
    private ECCKeyPair vpnServerIdentity;

    /**
     * Represent the registered participantsConnections to this vpn
     */
    private List<PlatformComponentProfile> registeredParticipants;

    /**
     * Holds all the participants connections
     */
    private Map<String, WebSocket> participantsConnections;

    /**
     * Holds all the vpnClientIdentity By Participants
     */
    private Map<String, String> vpnClientIdentityByParticipants;

    /**
     * Represent the wsCommunicationCloudServer
     */
    private WsCommunicationCloudServer wsCommunicationCloudServer;

    /**
     * Represent the networkServiceTypeApplicant
     */
    private NetworkServiceType networkServiceTypeApplicant;

    /**
     * Holds all pong message by connection
     */
    private Map<Integer, Boolean> pendingPongMessageByConnection;

    /**
     * Constructor with parameters
     *
     * @param address
     * @param registeredParticipants
     */
    public WsCommunicationVPNServer(InetSocketAddress address, List<PlatformComponentProfile> registeredParticipants, WsCommunicationCloudServer wsCommunicationCloudServer, NetworkServiceType networkServiceTypeApplicant) {
        super(address);
        this.vpnServerIdentity               = new ECCKeyPair();
        this.registeredParticipants          = registeredParticipants;
        this.participantsConnections         = new ConcurrentHashMap<>();
        this.vpnClientIdentityByParticipants = new ConcurrentHashMap<>();
        this.wsCommunicationCloudServer      = wsCommunicationCloudServer;
        this.networkServiceTypeApplicant     = networkServiceTypeApplicant;
        this.pendingPongMessageByConnection  = new ConcurrentHashMap<>();

        participantsConnections.clear();
        vpnClientIdentityByParticipants.clear();
        vpnClientIdentityByParticipants.clear();
    }

    /**
     * Send ping message to the remote node, to verify is connection
     * alive
     */
    public void sendPingMessage(){

        FramedataImpl1 frame = new FramedataImpl1(Framedata.Opcode.PING);
        frame.setFin(true);

        for (WebSocket conn:connections()) {
            LOG.debug("WsCommunicationVPNClient - Sending ping message to remote node (" + conn.getRemoteSocketAddress() + ")");
            conn.sendFrame(frame);
            pendingPongMessageByConnection.put(conn.hashCode(), Boolean.TRUE);
        }


    }

    /**
     * Receive pong message from the remote node, to verify is connection
     * alive
     *
     * @param conn
     * @param f
     */
    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        LOG.debug("Pong message receiveRemote from node (" + conn.getRemoteSocketAddress() + ") connection is alive");
        if (f.getOpcode() == Framedata.Opcode.PONG){
            LOG.debug("Pong message receiveRemote from node (" + conn.getRemoteSocketAddress() + ") connection is alive");
            pendingPongMessageByConnection.remove(conn.hashCode());
        }
    }




    /**
     * (non-javadoc)
     * @see WebSocketServer#onOpen(WebSocket, ClientHandshake)
     */
    @Override
    public void onOpen(WebSocket clientConnection, ClientHandshake handshake) {

        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("WsCommunicationVPNServer - Starting method onOpen");
        LOG.info("WsCommunicationVPNServer - New Participant Client: " + clientConnection.getRemoteSocketAddress().getAddress().getHostAddress() + " is connected!");
        LOG.info("WsCommunicationVPNServer - tmp-i = " + handshake.getFieldValue(JsonAttNamesConstants.HEADER_ATT_NAME_TI));

        /*
         * Validate is a handshake valid
         */
        if (handshake.getFieldValue(JsonAttNamesConstants.HEADER_ATT_NAME_TI)     != null &&
                handshake.getFieldValue(JsonAttNamesConstants.HEADER_ATT_NAME_TI) != ""     ){

            boolean isRegistered = Boolean.FALSE;

            String messageContentJsonStringRepresentation =  AsymmetricCryptography.decryptMessagePrivateKey(handshake.getFieldValue(JsonAttNamesConstants.HEADER_ATT_NAME_TI), vpnServerIdentity.getPrivateKey());

            LOG.info("messageContentJsonStringRepresentation = " + messageContentJsonStringRepresentation);

            JsonParser parser = new JsonParser();
            JsonObject respond = parser.parse(messageContentJsonStringRepresentation).getAsJsonObject();

            /*
             * Get the identity send by the participant
             */
            String participantIdentity =  respond.get(JsonAttNamesConstants.REGISTER_PARTICIPANT_IDENTITY_VPN).getAsString();
            String vpnClientIdentity   =  respond.get(JsonAttNamesConstants.CLIENT_IDENTITY_VPN).getAsString();

            for (PlatformComponentProfile registeredParticipant : registeredParticipants) {

                //Validate if registered
                if (registeredParticipant.getIdentityPublicKey().equals(participantIdentity)) {
                    isRegistered = Boolean.TRUE;
                }

            }

            //If not registered close the connection
            if (!isRegistered){
                clientConnection.closeConnection(404, "NOT A PARTICIPANT REGISTER FOR THIS VPN");
            }else {
                participantsConnections.put(participantIdentity, clientConnection);
                vpnClientIdentityByParticipants.put(participantIdentity, vpnClientIdentity);
            }

            LOG.info("registeredParticipants.size() = " + registeredParticipants.size());
            LOG.info("participantsConnections.size() = " + participantsConnections.size());
            LOG.info("Integer.compare(registeredParticipants.size(), participantsConnections.size()) == 0 = " + (Integer.compare(registeredParticipants.size(), participantsConnections.size()) == 0));

            //Validate if all participantsConnections register are connect
            if(Integer.compare(registeredParticipants.size(), participantsConnections.size()) == 0){

                PlatformComponentProfile peer1 = registeredParticipants.get(0);
                PlatformComponentProfile peer2 = registeredParticipants.get((registeredParticipants.size()-1));

                sendNotificationPacketConnectionComplete(peer1, peer2);
                sendNotificationPacketConnectionComplete(peer2, peer1);

            }

        }else {
            clientConnection.closeConnection(404, "DENIED, NOT VALID HANDSHAKE");
        }

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
        packetContent.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, networkServiceTypeApplicant.toString());

        /*
         * Get the connection client of the destination
         * IMPORTANT: No send by vpn connection, no support this type of packet
         */
        WebSocket clientConnectionDestination = wsCommunicationCloudServer.getRegisteredClientConnectionsCache().get(destinationPlatformComponentProfile.getCommunicationCloudClientIdentity());

        /*
        * Construct a new fermat packet whit the same message and different destination
        */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(destinationPlatformComponentProfile.getCommunicationCloudClientIdentity(), //Destination
                                                                                                                    wsCommunicationCloudServer.getServerIdentityByClientCache().get(clientConnectionDestination.hashCode()).getPublicKey(),                                  //Sender
                                                                                                                    gson.toJson(packetContent),                                        //Message Content
                                                                                                                    FermatPacketType.COMPLETE_COMPONENT_CONNECTION_REQUEST,            //Packet type
                                                                                                                    wsCommunicationCloudServer.getServerIdentityByClientCache().get(clientConnectionDestination.hashCode()).getPrivateKey());                                //Sender private key
        /*
        * Send the encode packet to the destination
        */
        clientConnectionDestination.send(FermatPacketEncoder.encode(fermatPacketRespond));

    }



    /**
     * (non-javadoc)
     * @see WebSocketServer#onClose(WebSocket, int, String, boolean)
     */
    @Override
    public void onClose(WebSocket clientConnection, int code, String reason, boolean remote) {

        LOG.info("--------------------------------------------------------------------- ");
        LOG.info("Starting method onClose");
        LOG.info(clientConnection.getRemoteSocketAddress() + " is disconnect! code = " + code + " reason = " + reason + " remote = " + remote);

        Iterator<WebSocket> iterator = connections().iterator();
        while (iterator.hasNext()){
            WebSocket conn = iterator.next();
            conn.closeConnection(505, " All participantsConnections close her connections ");
        }

        participantsConnections.clear();
        vpnClientIdentityByParticipants.clear();
        registeredParticipants.clear();

    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onMessage(WebSocket, String)
     */
    @Override
    public void onMessage(WebSocket clientConnection, String fermatPacketEncode) {

        LOG.info("onMessage ");

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
            FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(vpnClientIdentityByParticipants.get(fermatMessage.getReceiver()), //Destination
                                                                                                                        vpnServerIdentity.getPublicKey(),                         //Sender
                                                                                                                        fermatMessage.toJson(),                                   //Message Content
                                                                                                                        FermatPacketType.MESSAGE_TRANSMIT,                        //Packet type
                                                                                                                        vpnServerIdentity.getPrivateKey());                       //Sender private key
            /*
             * Get the connection of the destination
             */
            WebSocket clientConnectionDestination = participantsConnections.get(fermatMessage.getReceiver());



            /*
             * If the connection to client destination available
             */
            if (clientConnectionDestination != null && clientConnectionDestination.isOpen()){

                LOG.info("sending to destination " + fermatPacketRespond.getDestination());

               /*
                * Send the encode packet to the destination
                */
                clientConnectionDestination.send(FermatPacketEncoder.encode(fermatPacketRespond));

            }


        }else {
            LOG.warn("Packet type " + receiveFermatPacket.getFermatPacketType() + "is not supported");
        }

    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onError(WebSocket, Exception)
     */
    @Override
    public void onError(WebSocket clientConnection, Exception ex) {

        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("WsCommunicationVPNServer - Starting method onError");
        ex.printStackTrace();

        closeAllConnections(ex);

    }

    public void closeAllConnections(){

        LOG.info("Starting method closeAllConnections");
        /*
         * Close all the connection
         */
        Iterator<WebSocket> iterator = connections().iterator();
        while (iterator.hasNext()){
            WebSocket conn = iterator.next();
            conn.closeConnection(404, " - VPN participants are disconnect");
        }
    }

    public void closeAllConnections(Exception ex){

        LOG.info("Starting method closeAllConnections(Exception)");
        /*
         * Close all the connection
         */
        Iterator<WebSocket> iterator = connections().iterator();

        while (iterator.hasNext()){
            WebSocket conn = iterator.next();
            conn.closeConnection(505, " - VPN ERROR :" + ex.getLocalizedMessage());
        }

    }



    /**
     * Indicate is active this vpn
     *
     * @return boolean
     */
    public boolean isActive(){
        return (registeredParticipants.size() == connections().size());
    }

    /**
     * Get the VpnServerIdentityPublicKey
     * @return String
     */
    public String getVpnServerIdentityPublicKey(){
       return vpnServerIdentity.getPublicKey();
    }

    /**
     * Get the UriConnection
     * @return URI
     * @throws URISyntaxException
     */
    public URI getUriConnection() {

        try {
            return new URI(WsCommunicationVPNServer.WS_PROTOCOL + getAddress().getHostString()  + ":" + getPort());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the PendingPongMessageByConnection
     * @return Map<Integer, Boolean>
     */
    public Map<Integer, Boolean> getPendingPongMessageByConnection() {
        return pendingPongMessageByConnection;
    }

}
