/*
 * @#RequestListComponentRegisterPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.RequestListComponentRegisterPacketProcessor</code> implement
 * the logic to process the packet when a packet type <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED</code> is receive by the server.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RequestListComponentRegisterPacketProcessor extends FermatPacketProcessor {

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

        System.out.println(" --------------------------------------------------------------------- ");
        System.out.println("RequestListComponentRegisterPacketProcessor - Starting processingPackage");

        /*
         * Get the platformComponentProfile from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey());

        System.out.println("RequestListComponentRegisterPacketProcessor - messageContentJsonStringRepresentation = "+messageContentJsonStringRepresentation);

        /*
         * Construct the json object
         */
        Gson gson = new Gson();

         /*
         * Get the list
         */
        List<PlatformComponentProfile> list = gson.fromJson(messageContentJsonStringRepresentation, new TypeToken<List<PlatformComponentProfileCommunication>>(){}.getType());


        System.out.println("WsCommunicationsCloudClientPluginRoot - list = "+list);


        /*
         * ONLY FOR TEST SEND MESSAGE TO ALL COMPONENT REGISTER IN THE SERVER


            String messageContent = "***********************************************************************************************\n " +
                                    "* HELLO THUNDER COINS TEAM...  This message was sent from the device of ROBERTO REQUENA... :) *\n" +
                                    "*********************************************************************************************** ";

            for (PlatformComponentProfile platformComponentProfileDestination:list) {

                FermatMessage fermatMessage = null;
                try {

                    fermatMessage = FermatMessageCommunicationFactory.constructFermatMessageEncryptedAndSinged(getWsCommunicationsCloudClientChannel().getPlatformComponentProfile(), //Sender
                                                                                                                platformComponentProfileDestination,                                  //Receiver
                                                                                                                messageContent,                                                      //Message Content
                                                                                                                FermatMessageContentType.TEXT,                                       //Type
                                                                                                                getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey()); //Sender private key


                    FermatPacket fermatPacketRequest = FermatPacketCommunicationFactory.constructFermatPacketEncryptedAndSinged(getWsCommunicationsCloudClientChannel().getServerIdentity(),                  //Destination
                                                                                                                                getWsCommunicationsCloudClientChannel().getClientIdentity().getPublicKey(),   //Sender
                                                                                                                                fermatMessage.toJson(),                                                  //packet Content
                                                                                                                                FermatPacketType.MESSAGE_TRANSMIT,                                       //Packet type
                                                                                                                                getWsCommunicationsCloudClientChannel().getClientIdentity().getPrivateKey()); //Sender private key

                    getWsCommunicationsCloudClientChannel().send(FermatPacketEncoder.encode(fermatPacketRequest));



                } catch (FMPException e) {
                    e.printStackTrace();
                }

            }
           */


         /*
         * Create a raise a new event whit the list
         */
        FermatEvent event =  EventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION.getNewEvent();
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);
        getWsCommunicationsCloudClientChannel().getEventManager().raiseEvent(event);

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.REQUEST_LIST_COMPONENT_REGISTERED;
    }
}
