/*
 * @#WsCommunicationVPNClient.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.vpn;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsVPNConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.vpn.WsCommunicationVPNClient</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationTyrusVPNClient extends Endpoint implements CommunicationsVPNConnection{

    /**
     * Represent the vpnClientIdentity
     */
    private ECCKeyPair vpnClientIdentity;

    /**
     * Represent the remoteParticipant of the vpn
     */
    private PlatformComponentProfile remoteParticipant;

    /**
     * Represent the remoteParticipantNetworkService of the vpn
     */
    private PlatformComponentProfile remoteParticipantNetworkService;

    /**
     * Represent the vpnServerIdentity
     */
    private String vpnServerIdentity;

    /**
     * Represent the pending incoming messages cache
     */
    private List<FermatMessage> pendingIncomingMessages;

    /**
     * Represent the wsCommunicationVPNClientManagerAgent
     */
    private WsCommunicationTyrusVPNClientManagerAgent wsCommunicationTyrusVPNClientManagerAgent;

    /**
     * Represent the clientConnection
     */
    private Session vpnClientConnection;

    /**
     * Constructor with parameters
     *  @param wsCommunicationTyrusVPNClientManagerAgent
     * @param vpnClientIdentity
     * @param remoteParticipant
     * @param remoteParticipantNetworkService
     * @param vpnServerIdentity
     */
    public WsCommunicationTyrusVPNClient(WsCommunicationTyrusVPNClientManagerAgent wsCommunicationTyrusVPNClientManagerAgent, ECCKeyPair vpnClientIdentity, PlatformComponentProfile remoteParticipant, PlatformComponentProfile remoteParticipantNetworkService, String vpnServerIdentity) {
        this.wsCommunicationTyrusVPNClientManagerAgent = wsCommunicationTyrusVPNClientManagerAgent;
        this.vpnClientIdentity = vpnClientIdentity;
        this.remoteParticipant = remoteParticipant;
        this.remoteParticipantNetworkService = remoteParticipantNetworkService;
        this.vpnServerIdentity = vpnServerIdentity;
        this.pendingIncomingMessages = new ArrayList<>();
    }

    /**
     *  (non-javadoc)
     *  @see Endpoint#onOpen(Session, EndpointConfig)
     */
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationVPNClient - Starting method onOpen");
        System.out.println(" WsCommunicationVPNClient - Session id = " + session.getId());
        this.vpnClientConnection = session;

        /*
         * Configure message handler
         */
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String fermatPacketEncode) {
                processMessage(fermatPacketEncode);
            }
        });


    }

    /**
     * Method that process the messages received
     *
     * @param fermatPacketEncode
     */
    public void processMessage(String fermatPacketEncode) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationVPNClient - Starting method onMessage(String)");
        System.out.println(" WsCommunicationVPNClient - encode fermatPacket " + fermatPacketEncode);

        /**
         * Decode the message with the client identity
         */
        FermatPacket fermatPacketReceive = FermatPacketDecoder.decode(fermatPacketEncode, vpnClientIdentity.getPrivateKey());
        System.out.println(" WsCommunicationsCloudClientChannel - decode fermatPacket " + FermatPacketDecoder.decode(fermatPacketEncode, vpnClientIdentity.getPrivateKey()));

        /*
         * Validate the signature
         */
        validateFermatPacketSignature(fermatPacketReceive);

        if (fermatPacketReceive.getFermatPacketType() == FermatPacketType.MESSAGE_TRANSMIT){

            /*
             * Get the platformComponentProfile from the message content and decrypt
             */
            String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(fermatPacketReceive.getMessageContent(), vpnClientIdentity.getPrivateKey());

            System.out.println("WsCommunicationVPNClient - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

            /*
             * Get the message object
             */
            FermatMessage fermatMessage = new FermatMessageCommunication().fromJson(messageContentJsonStringRepresentation);

            System.out.println("WsCommunicationVPNClient - fermatMessage = "+fermatMessage);

            /*
             * Add to the list
             */
            pendingIncomingMessages.add(fermatMessage);

            System.out.println("WsCommunicationVPNClient - pendingIncomingMessages.size() = " + pendingIncomingMessages.size());

        }else {
            System.out.println("WsCommunicationVPNClient - Packet type " + fermatPacketReceive.getFermatPacketType() + "is not supported");
        }

    }

    /**
     *  (non-javadoc)
     *  @see Endpoint#onClose(Session, CloseReason)
     */
    @Override
    public void onClose(final Session session, final CloseReason reason)  {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println(" WsCommunicationVPNClient - Starting method onClose");
        System.out.println("Socket " + session.getId() + " is disconnect! code = " + reason.getCloseCode() + "[" + reason.getCloseCode().getCode() + "] reason = " + reason.getReasonPhrase());

        switch (reason.getCloseCode().getCode()) {

            case 1000:
                try {
                    wsCommunicationTyrusVPNClientManagerAgent.riseVpnConnectionCloseNotificationEvent(remoteParticipantNetworkService.getNetworkServiceType(), remoteParticipant,true);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 1002:
            case 1006:
                    try {
                        wsCommunicationTyrusVPNClientManagerAgent.riseVpnConnectionCloseNotificationEvent(remoteParticipantNetworkService.getNetworkServiceType(), remoteParticipant,false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                break;
            default:
                    try {
                        //TODO: ver codigo por codigo haber si esto cumple con el true
                        wsCommunicationTyrusVPNClientManagerAgent.riseVpnConnectionCloseNotificationEvent(remoteParticipantNetworkService.getNetworkServiceType(), remoteParticipant,true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                break;
        }

    }

    /**
     *  (non-javadoc)
     *  @see Endpoint#onError(Session, Throwable)
     */
    @Override
    public void onError(Session session, Throwable t) {

        try {

            System.out.println(" --------------------------------------------------------------------- ");
            System.out.println(" WsCommunicationVPNClient - Starting method onError");
            t.printStackTrace();
            vpnClientConnection.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, t.getMessage()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {

            System.out.println(" WsCommunicationVPNClient - close connection");
            if(vpnClientConnection.isOpen()) {
                vpnClientConnection.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Close the vpn connection to "+remoteParticipant.getAlias()+" intentionally."));
            }
            wsCommunicationTyrusVPNClientManagerAgent.riseVpnConnectionCloseNotificationEvent(remoteParticipantNetworkService.getNetworkServiceType(), remoteParticipant, true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Validate the signature of the packet
     * @param fermatPacketReceive
     */
    private void validateFermatPacketSignature(FermatPacket fermatPacketReceive){

        System.out.println(" WsCommunicationVPNClient - validateFermatPacketSignature");

         /*
         * Validate the signature
         */
        boolean isValid = AsymmetricCryptography.verifyMessageSignature(fermatPacketReceive.getSignature(), fermatPacketReceive.getMessageContent(), vpnServerIdentity);

        System.out.println(" WsCommunicationVPNClient - isValid = " + isValid);

        /*
         * if not valid signature
         */
        if (!isValid){
            throw new RuntimeException("Fermat Packet received has not a valid signature, go to close this connection maybe is compromise");
        }

    }

    /**
     * (non-javadoc)
     * @see CommunicationsVPNConnection#sendMessage(FermatMessage)
     */
    @Override
    public synchronized void sendMessage(FermatMessage fermatMessage){

        System.out.println("WsCommunicationVPNClient - sendMessage");

        /*
         * Validate parameter
         */
        if (fermatMessage == null){
            throw new IllegalArgumentException("The fermatMessage is required, can not be null");
        }

         /*
         * Construct a fermat packet whit the message to transmit
         */
        FermatPacket fermatPacketRequest = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(vpnServerIdentity,                  //Destination
                                                                                                                    vpnClientIdentity.getPublicKey(),   //Sender
                                                                                                                    fermatMessage.toJson(),             //Message Content
                                                                                                                    FermatPacketType.MESSAGE_TRANSMIT,  //Packet type
                                                                                                                    vpnClientIdentity.getPrivateKey()); //Sender private key


        if (vpnClientConnection.isOpen()){

            /**
             * if Packet is bigger than 1000 Send the message through of sendDividedChain
             */
            if((FermatPacketEncoder.encode(fermatPacketRequest)).length() > 1000){

                try {
                    sendDividedChain(FermatPacketEncoder.encode(fermatPacketRequest));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else {

                 /*
                 * Send the encode packet to the server
                 */
                 vpnClientConnection.getAsyncRemote().sendText(FermatPacketEncoder.encode(fermatPacketRequest));

            }

        }else{
            throw new RuntimeException("Client Connection is Close");
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see CommunicationsVPNConnection#getUnreadMessagesCount()
     */
    public int getUnreadMessagesCount() {

        /*
         * Validate if not null
         */
        if (pendingIncomingMessages != null){

            /*
             * Return the size of the cache
             */
            return pendingIncomingMessages.size();

        }

        /*
         * Empty cache return 0
         */
        return 0;
    }

    /**
     * (non-Javadoc)
     *
     * @see CommunicationsVPNConnection#readNextMessage()
     */
    public FermatMessage readNextMessage() {

        /*
         * Validate no empty
         */
        if(!pendingIncomingMessages.isEmpty()) {

            /*
             * Return the next message
             */
            return pendingIncomingMessages.iterator().next();

        }else {

            //TODO: CREATE A APPROPRIATE EXCEPTION
            throw new RuntimeException();
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see CommunicationsVPNConnection#removeMessageRead(FermatMessage)
     */
    @Override
    public void removeMessageRead(FermatMessage fermatMessage) {

        /*
         * Remove the message
         */
        pendingIncomingMessages.remove(fermatMessage);
    }

    /**
     * (non-Javadoc)
     *
     * @see CommunicationsVPNConnection#isActive()
     */
    @Override
    public boolean isActive() {
        return vpnClientConnection.isOpen();
    }

    /**
     * (non-Javadoc)
     *
     * @see CommunicationsVPNConnection#getRemoteParticipant()
     */
    public PlatformComponentProfile getRemoteParticipant() {
        return remoteParticipant;
    }

    /**
     * (non-Javadoc)
     *
     * @see CommunicationsVPNConnection#getRemoteParticipantNetworkService()
     */
    public PlatformComponentProfile getRemoteParticipantNetworkService() {
        return remoteParticipantNetworkService;
    }

    @Override
    public boolean isConnected() {
        return vpnClientConnection.isOpen();
    }

    @Override
    public String toString() {
        return "WsCommunicationVPNClient{" +
                "vpnClientIdentity=" + vpnClientIdentity +
                ", remoteParticipant=" + remoteParticipant +
                ", remoteParticipantNetworkService=" + remoteParticipantNetworkService +
                ", vpnServerIdentity='" + vpnServerIdentity + '\'' +
                '}';
    }

    /**
     * Send the message divide in packets of Size 1000
     */
    private void sendDividedChain(final String message) throws IOException {

        /*
         * Number of packets to send of Size 1000
         */
        int ref = message.length() / 1000;

        /*
         * Used to validate if the packet is send complete or in two parts
         */
        int residue = message.length() % 1000;

        /*
         * Index to handle the send of packet subString
         */
        int beginIndex = 0;
        int endIndex = 1000;

        for(int i = 0; i < ref-1; i++){

            vpnClientConnection.getBasicRemote().sendText(message.substring(beginIndex, endIndex), Boolean.FALSE);
            beginIndex = endIndex;
            endIndex = endIndex + 1000;

        }

        /*
         * we get the last Chain to send
         */
        String lastChain = message.substring(beginIndex, message.length());

        /*
         * if residue is equals 0 then send the lastChain Complete
         * else then the lastChain is divided in two parts to send
         */
        if(residue == 0){

            /*
             * the lastChain is send Complete
             */
            vpnClientConnection.getBasicRemote().sendText(lastChain, Boolean.TRUE);

        }else{

            /*
             * the lastChain is divided in two parts to send
             */
            int middleIndexlastChain = (lastChain.length() % 2 == 0) ? (lastChain.length() / 2)  : ((lastChain.length() + 1) / 2) - 1;
            vpnClientConnection.getBasicRemote().sendText(lastChain.substring(0, middleIndexlastChain), Boolean.FALSE);
            vpnClientConnection.getBasicRemote().sendText(lastChain.substring(middleIndexlastChain, lastChain.length()), Boolean.TRUE);

        }

    }

}
