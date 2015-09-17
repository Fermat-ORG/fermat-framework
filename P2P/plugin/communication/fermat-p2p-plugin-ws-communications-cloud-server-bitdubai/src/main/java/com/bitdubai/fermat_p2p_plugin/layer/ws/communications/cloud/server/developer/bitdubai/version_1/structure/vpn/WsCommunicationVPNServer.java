/*
 * @#WsCommunicationVPNServer.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.vpn;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
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
     * Represent the WS_PROTOCOL
     */
    private static final String WS_PROTOCOL = "ws://";

    /**
     * Represent the vpnServerIdentity
     */
    private ECCKeyPair vpnServerIdentity;

    /**
     * Represent the registered participants to this vpn
     */
    private List<PlatformComponentProfile> registeredParticipants;

    /**
     * Holds all the participants connections
     */
    private Map<String, WebSocket> participants;

    /**
     * Holds all the participants connections
     */
    private Map<String, String> vpnClientIdentityByParticipants;

    /**
     * Constructor with parameters
     *
     * @param address
     * @param registeredParticipants
     */
    public WsCommunicationVPNServer(InetSocketAddress address, List<PlatformComponentProfile> registeredParticipants) {
        super(address);
        this.vpnServerIdentity               = new ECCKeyPair();
        this.registeredParticipants          = registeredParticipants;
        this.participants                    = new ConcurrentHashMap<>();
        this.vpnClientIdentityByParticipants = new ConcurrentHashMap<>();
    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onOpen(WebSocket, ClientHandshake)
     */
    @Override
    public void onOpen(WebSocket clientConnection, ClientHandshake handshake) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationVPNServer - Starting method onOpen");
        System.out.println(" WsCommunicationVPNServer - New Participant Client: "+clientConnection.getRemoteSocketAddress().getAddress().getHostAddress() + " is connected!");
        System.out.println(" WsCommunicationVPNServer - reg-part-i = " + handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN));
        System.out.println(" WsCommunicationVPNServer - vpn-cl-i = " + handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_CLIENT_IDENTITY_VPN));

        /*
         * Validate is a handshake valid
         */
        if (handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN)     != null &&
                handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN) != ""   &&
                    handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_CLIENT_IDENTITY_VPN)           != null &&
                        handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_CLIENT_IDENTITY_VPN)       != ""){

            boolean isRegistered = Boolean.FALSE;

            /*
             * Get the identity send by the participant
             */
            String participantIdentity =  AsymmectricCryptography.decryptMessagePrivateKey(handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN), vpnServerIdentity.getPrivateKey());
            String vpnClientIdentity   =  AsymmectricCryptography.decryptMessagePrivateKey(handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_CLIENT_IDENTITY_VPN), vpnServerIdentity.getPrivateKey());


            for (PlatformComponentProfile registeredParticipant : registeredParticipants) {

                //Validate if registered
                if (registeredParticipant.getIdentityPublicKey() == participantIdentity) {
                    isRegistered = Boolean.FALSE;
                }

            }

            //If not registered close the connection
            if (!isRegistered){
                clientConnection.closeConnection(404, "NOT A PARTICIPANT REGISTER FOR THIS VPN");
            }else {
                participants.put(participantIdentity, clientConnection);
                vpnClientIdentityByParticipants.put(participantIdentity, vpnClientIdentity);
            }


            //Validate if all participants register are connect
            if(registeredParticipants.size() == participants.size()){

                PlatformComponentProfile peer1 =  registeredParticipants.get(0);
                PlatformComponentProfile peer2 = registeredParticipants.get((registeredParticipants.size()-1));

                sendNotificationPacketConnectionComplete(peer1, peer2.getIdentityPublicKey());
                sendNotificationPacketConnectionComplete(peer2, peer1.getIdentityPublicKey());

            }

        }

    }

    /**
     *
     * @param destinationPlatformComponentProfile
     * @param remotePlatformComponentProfileIdentity
     */
    private void sendNotificationPacketConnectionComplete(PlatformComponentProfile destinationPlatformComponentProfile, String remotePlatformComponentProfileIdentity){

         /*
         * Construct the content of the msj
         */
        Gson gson = new Gson();
        JsonObject packetContent = new JsonObject();
        packetContent.addProperty(AttNamesConstants.JSON_ATT_NAME_NETWORK_SERVICE_TYPE,  destinationPlatformComponentProfile.getNetworkServiceType().toString());
        packetContent.addProperty(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN,  remotePlatformComponentProfileIdentity);

        /*
        * Construct a new fermat packet whit the same message and different destination
        */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(vpnClientIdentityByParticipants.get(destinationPlatformComponentProfile.getIdentityPublicKey()), //Destination
                                                                                                                    vpnServerIdentity.getPublicKey(),                                  //Sender
                                                                                                                    gson.toJson(packetContent),                                        //Message Content
                                                                                                                    FermatPacketType.COMPLETE_COMPONENT_CONNECTION_REQUEST,            //Packet type
                                                                                                                    vpnServerIdentity.getPrivateKey());                                //Sender private key
        /*
         * Get the connection of the destination
         */
        WebSocket clientConnectionDestination = participants.get(destinationPlatformComponentProfile.getIdentityPublicKey());

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

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationVPNServer - Starting method onClose");
        System.out.println(" WsCommunicationVPNServer - " + clientConnection.getRemoteSocketAddress() + " is disconnect! code = " + code + " reason = " + reason + " remote = " + remote);

        if (participants.size() <= 1){

            /*
             * Close all the connection
             */
            for (WebSocket conn: connections()) {
                conn.closeConnection(505, " All participants close her connections ");
            }
        }

    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onMessage(WebSocket, String)
     */
    @Override
    public void onMessage(WebSocket clientConnection, String fermatPacketEncode) {

        /*
         * Decode the fermatPacketEncode into a fermatPacket
         */
        FermatPacket receiveFermatPacket = FermatPacketDecoder.decode(fermatPacketEncode, vpnServerIdentity.getPrivateKey());

        /*
         * Get the FermatMessage from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), vpnServerIdentity.getPrivateKey());

        /*
         * Construct the fermat message object
         */
        FermatMessageCommunication fermatMessage = (FermatMessageCommunication) new FermatMessageCommunication().fromJson(messageContentJsonStringRepresentation);

        /*
         * Send the message to the other participants
         */
        for (String participantIdentity : participants.keySet()) {

            //If participantIdentity is different from the sender, send the message
            if (participantIdentity != receiveFermatPacket.getSender()){

                /*
                * Construct a new fermat packet whit the same message and different destination
                */
                FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(vpnClientIdentityByParticipants.get(participantIdentity), //Destination
                                                                                                                            vpnServerIdentity.getPublicKey(),                         //Sender
                                                                                                                            fermatMessage.toJson(),                                   //Message Content
                                                                                                                            FermatPacketType.MESSAGE_TRANSMIT,                        //Packet type
                                                                                                                            vpnServerIdentity.getPrivateKey());                       //Sender private key

                /*
                 * Get the connection of the destination
                 */
                WebSocket clientConnectionDestination = participants.get(participantIdentity);

                System.out.println("WsCommunicationVPNServer - clientConnectionDestination "+clientConnectionDestination);

                /*
                 * If the connection to client destination available
                 */
                if (clientConnectionDestination != null && clientConnectionDestination.isOpen()){

                    System.out.println("WsCommunicationVPNServer - sending to destination "+fermatPacketRespond.getDestination());

                   /*
                    * Send the encode packet to the destination
                    */
                    clientConnectionDestination.send(FermatPacketEncoder.encode(fermatPacketRespond));

                }

            }

        }

    }

    /**
     * (non-javadoc)
     * @see WebSocketServer#onError(WebSocket, Exception)
     */
    @Override
    public void onError(WebSocket clientConnection, Exception ex) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationVPNServer - Starting method onError");
        ex.printStackTrace();

        /*
         * Close all the connection
         */
        for (WebSocket conn: connections()) {
            conn.closeConnection(505, "- ERROR :" + ex.getLocalizedMessage());
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

}
