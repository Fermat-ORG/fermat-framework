/*
 * @#MessageTrasmitPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;

import org.java_websocket.WebSocket;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.processors.MessageTransmitPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.MESSAGE_TRANSMIT</code> is receive by the server.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Deprecated
public class MessageTransmitPacketProcessor extends FermatPacketProcessor {

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(WebSocket, FermatPacket, ECCKeyPair)
     */
    @Override
    public void processingPackage(WebSocket clientConnection, FermatPacket receiveFermatPacket, ECCKeyPair serverIdentity) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("MessageTransmitPacketProcessor - processingPackage");

         /*
         * Get the FermatMessage from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmetricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), serverIdentity.getPrivateKey());

        /*
         * Construct the fermat message object
         */
        FermatMessageCommunication fermatMessage = (FermatMessageCommunication) new FermatMessageCommunication().fromJson(messageContentJsonStringRepresentation);

       /*
        * Construct a new fermat packet whit the same message and different destination
        */
        FermatPacket fermatPacketRespond = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(fermatMessage.getCommunicationCloudClientIdentity(), //Destination
                                                                                                                    serverIdentity.getPublicKey(),                       //Sender
                                                                                                                    fermatMessage.toJson(),                              //Message Content
                                                                                                                    FermatPacketType.MESSAGE_TRANSMIT,                   //Packet type
                                                                                                                    serverIdentity.getPrivateKey());                     //Sender private key

        /*
         * Get the connection of the destination
         */
        WebSocket clientConnectionDestination = getWsCommunicationCloudServer().getRegisteredClientConnectionsCache().get(fermatPacketRespond.getDestination());


        System.out.println("MessageTransmitPacketProcessor - clientConnectionDestination "+clientConnectionDestination);

        /*
         * If the connection to client destination available
         */
        if (clientConnectionDestination != null && clientConnectionDestination.isOpen()){

            System.out.println("MessageTransmitPacketProcessor - sending to destination "+fermatPacketRespond.getDestination());

           /*
            * Send the encode packet to the destination
            */
            clientConnectionDestination.send(FermatPacketEncoder.encode(fermatPacketRespond));

        }

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.MESSAGE_TRANSMIT;
    }
}
