/*
 * @#CompleteRegistrationComponentPacketProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;

import org.java_websocket.WebSocket;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.processors.CompleteRegistrationComponentPacketProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 07/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteRegistrationComponentPacketProcessor extends FermatPacketProcessor {

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#processingPackage(FermatPacket)
     */
    @Override
    public void processingPackage(FermatPacket receiveFermatPacket) {

        System.out.println("CompleteRegistrationComponentPacketProcessor - processingPackage");

        /*
         * Get the platformComponentProfile from the message content and decrypt
         */
        String messageContentJsonStringRepresentation = AsymmectricCryptography.decryptMessagePrivateKey(receiveFermatPacket.getMessageContent(), getWsCommunicationsCloudClientChannel().getTemporalIdentity().getPrivateKey());

         /*
         * Convert in platformComponentProfile
         */
        PlatformComponentProfile platformComponentProfile = new PlatformComponentProfileCommunication().fromJson(messageContentJsonStringRepresentation);

        if (platformComponentProfile.getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT_COMPONENT){

            /* ---------------------------------------------------------------------------------------------------
             * IMPORTANT: This Message Content of this packet come encrypted with the temporal identity public key
             * at this moment the communication cloud client is noT register
             * ---------------------------------------------------------------------------------------------------
             * Mark as register
             */
            getWsCommunicationsCloudClientChannel().setIsRegister(Boolean.TRUE);

            System.out.println("CompleteRegistrationComponentPacketProcessor - getWsCommunicationsCloudClientChannel().isRegister() = "+ getWsCommunicationsCloudClientChannel().isRegister());
        }

        //TODO: Fire a event

    }

    /**
     * (no-javadoc)
     * @see FermatPacketProcessor#getFermatPacketType()
     */
    @Override
    public FermatPacketType getFermatPacketType() {
        return FermatPacketType.COMPLETE_REGISTRATION_COMPONENT;
    }
}
