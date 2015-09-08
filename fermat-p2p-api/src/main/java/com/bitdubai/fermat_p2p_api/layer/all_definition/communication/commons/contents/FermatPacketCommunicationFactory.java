/*
 * @#FermatPacketCommunicationFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.MalformedFMPPacketException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketCommunicationFactory</code> is a
 * factory class that construct the FermatPacket
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 */
public class FermatPacketCommunicationFactory {

    /**
     * Construct a FermatPacket encrypted with the destination identity public key and signed
     * whit the private key passed as an argument
     *
     * @param destination
     * @param sender
     * @param fermatPacketType
     * @param messageContentJsonString
     * @param privateKey
     * @return FermatPacket
     */
    public static FermatPacket constructFermatPacketEncryptedAndSinged(final String destination, final String sender, final String messageContentJsonString, final FermatPacketType fermatPacketType, final String privateKey) {

        String messageHash = AsymmectricCryptography.encryptMessagePublicKey(messageContentJsonString, destination);
        String signature = AsymmectricCryptography.createMessageSignature(messageHash, privateKey);

        return new FermatPacketCommunication(destination, sender, fermatPacketType, messageHash, signature);
    }

    /**
     * Construct a FermatPacket
     *
     * @param destination
     * @param sender
     * @param fermatPacketType
     * @param messageContentJsonString
     *
     * @return FermatPacket
     */
    public static FermatPacket constructFermatPacket(final String destination, final String sender, final String messageContentJsonString,  final FermatPacketType fermatPacketType) {
        return new FermatPacketCommunication(destination, sender, fermatPacketType, messageContentJsonString, null);
    }


    /**
     * Construct a FermatPacketCommunication from a json string
     *
     * @param jsonPacketData
     * @return FermatPacketCommunication
     * @throws RuntimeException
     */
	public static FermatPacket constructFermatPacketFromJsonString(String jsonPacketData) {

        try {

            /*
             * Validate the data
             */
            validatePacketDataString(jsonPacketData);

            /**
             * Create a temporal object
             */
            FermatPacketCommunication temp = new FermatPacketCommunication();

            /*
             * Convert to the object
             */
            return temp.fromJson(jsonPacketData);

        }catch (Exception exception){

            throw new RuntimeException ("The packet data is not properly assembled", exception);
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