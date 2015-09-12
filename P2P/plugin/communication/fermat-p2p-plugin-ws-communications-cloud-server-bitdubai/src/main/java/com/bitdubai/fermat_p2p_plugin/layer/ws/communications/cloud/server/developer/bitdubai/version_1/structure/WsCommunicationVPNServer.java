/*
 * @#WsCommunicationVPNServer.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
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
     * Represent the vpnIdentity
     */
    private ECCKeyPair vpnIdentity;

    /**
     * Represent the registered participants to this vpn
     */
    private List<String> registeredParticipantsIdentity;

    /**
     * Holds all the participants connections
     */
    private Map<String, WebSocket> participants;

    /**
     * Constructor with parameters
     * @param address
     */
    public WsCommunicationVPNServer(InetSocketAddress address, List<String> participantsIdentity) {
        super(address);
        this.vpnIdentity = new ECCKeyPair();
        this.registeredParticipantsIdentity = participantsIdentity;
        this.participants = new ConcurrentHashMap<>();
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

        /*
         * Validate is a handshake valid
         */
        if (handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN)     != null &&
                handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN) != ""){

            boolean isRegistered = Boolean.FALSE;

            /*
             * Get the identity send by the participant
             */
            String participantIdentity =  AsymmectricCryptography.decryptMessagePrivateKey(handshake.getFieldValue(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN), vpnIdentity.getPrivateKey());

            for (String registeredParticipantIdentity : registeredParticipantsIdentity) {

                //Validate if registered
                if (registeredParticipantIdentity == participantIdentity) {
                    isRegistered = Boolean.FALSE;
                }

            }

            //If not registered close the connection
            if (!isRegistered){
                clientConnection.closeConnection(404, "NOT A PARTICIPANT REGISTER FOR THIS VPN");
            }else {
                participants.put(participantIdentity, clientConnection);
            }


            //Validate if all participants register are connect
            if(registeredParticipantsIdentity.size() == participants.size()){

                /*
                 * Send the confirmation message
                 */
                for (String participantIdentityConnected : participants.keySet()) {

                    /*
                    * Construct a new fermat packet whit the same message and different destination
                    */
                    FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(participantIdentityConnected,      //Destination
                                                                                                                                vpnIdentity.getPublicKey(),        //Sender
                                                                                                                                "VPN Connection stablished",       //Message Content
                                                                                                                                FermatPacketType.MESSAGE_TRANSMIT, //Packet type
                                                                                                                                vpnIdentity.getPrivateKey());      //Sender private key
                    /*
                     * Get the connection of the destination
                     */
                    WebSocket clientConnectionDestination = participants.get(participantIdentity);

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
     * @see WebSocketServer#onClose(WebSocket, int, String, boolean)
     */
    @Override
    public void onClose(WebSocket clientConnection, int code, String reason, boolean remote) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationVPNServer - Starting method onClose");
        System.out.println(" WsCommunicationVPNServer - " + clientConnection.getRemoteSocketAddress() + " is disconnect! code = " + code + " reason = " + reason + " remote = " + remote);

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
        FermatPacket receiveFermatPacket = FermatPacketDecoder.decode(fermatPacketEncode, vpnIdentity.getPrivateKey());

        /*
         * Get the FermatMessage from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), vpnIdentity.getPrivateKey());

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
                FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(fermatMessage.getCommunicationCloudClientIdentity(), //Destination
                                                                                                                            vpnIdentity.getPublicKey(),                         //Sender
                                                                                                                            fermatMessage.toJson(),                             //Message Content
                                                                                                                            FermatPacketType.MESSAGE_TRANSMIT,                  //Packet type
                                                                                                                            vpnIdentity.getPrivateKey());                       //Sender private key

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
        return (registeredParticipantsIdentity.size() == connections().size());
    }

}
