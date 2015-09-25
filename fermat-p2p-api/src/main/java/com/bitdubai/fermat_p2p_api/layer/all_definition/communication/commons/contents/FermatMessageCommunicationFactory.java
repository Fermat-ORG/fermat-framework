/*
 * @#FermatMessageCommunicationFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.MalformedFMPPacketException;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory</code> is a
 * factory class that construct the FermatMessage
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 */
public class FermatMessageCommunicationFactory {

    /**
     * Construct a FermatMessage encrypted with the destination identity public key and signed
     * whit the private key passed as an argument
     *
     * @param senderIdentity
     * @param receiver
     * @param content
     * @param fermatMessageContentType
     * @return FermatMessage
     * @throws FMPException
     */
    public static FermatMessage constructFermatMessageEncryptedAndSinged(final ECCKeyPair senderIdentity, final PlatformComponentProfile receiver, final String content, final FermatMessageContentType fermatMessageContentType) throws FMPException{

        String messageHash = AsymmectricCryptography.encryptMessagePublicKey(content, receiver.getIdentityPublicKey());
        String signature   = AsymmectricCryptography.createMessageSignature(messageHash, senderIdentity.getPrivateKey());

        return new FermatMessageCommunication(messageHash, null, fermatMessageContentType, FermatMessagesStatus.PENDING_TO_SEND, receiver.getIdentityPublicKey(), senderIdentity.getPublicKey(), new Timestamp(System.currentTimeMillis()), signature, receiver.getCommunicationCloudClientIdentity());
    }


    /**
     * Construct a FermatMessageCommunication from a json string
     *
     * @param jsonMessageData
     * @return FermatPacketCommunication
     * @throws FMPException
     */
	public static FermatMessage constructFermatMessageFromJsonString(String jsonMessageData) throws FMPException {

        try {

            /*
             * Validate the data
             */
            validatePacketDataString(jsonMessageData);

            /**
             * Create a temporal object
             */
            FermatMessageCommunication temp = new FermatMessageCommunication();

            /*
             * Convert to the object
             */
            return temp.fromJson(jsonMessageData);

        }catch (Exception exception){

            throw new MalformedFMPPacketException(MalformedFMPPacketException.DEFAULT_MESSAGE, exception, null, "The message data is not properly assembled");
        }

	}

    /**
     * Validate the json string data
     *
     * @param jsomPacketData
     * @throws FMPException
     */
    private static void validatePacketDataString(final String jsomPacketData) throws FMPException {

        if(jsomPacketData == null){
            throw new MalformedFMPPacketException(MalformedFMPPacketException.DEFAULT_MESSAGE, null, "", "The packet data is null");
        }

        if(jsomPacketData.isEmpty()) {
            throw new MalformedFMPPacketException(MalformedFMPPacketException.DEFAULT_MESSAGE, null, "", "The packet data is empty");
        }
    }

}