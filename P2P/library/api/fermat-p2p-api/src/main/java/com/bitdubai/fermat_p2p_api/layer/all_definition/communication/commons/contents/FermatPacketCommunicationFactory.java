/*
 * @#FermatPacketCommunicationFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatPacketType;
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
     * @param privateKeyToSing
     * @return FermatPacket
     */
    public static FermatPacket constructFermatPacketEncryptedAndSinged(final String destination, final String sender, final String messageContentJsonString, final FermatPacketType fermatPacketType, final String privateKeyToSing) {

        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(messageContentJsonString, destination);
        String signature = AsymmetricCryptography.createMessageSignature(privateKeyToSing, messageHash);

        return new FermatPacketCommunication(destination, sender, fermatPacketType, messageHash, signature);
    }


    /**
     * Construct a FermatPacket encrypted with the specify identity public key and signed
     * whit the private key passed as an argument, this method is use to create a FermatPacket
     * the type FermatPacketType.MESSAGE_TRANSMIT, because the destination if different to the
     * server.
     *
     * In this case the Communication Cloud Server is a intermediary, is used as a bridge
     * to pass messages between components. For this reason the package has
     * to be encrypted with your public key.
     *
     * @param destination
     * @param sender
     * @param messageContentJsonString
     * @param fermatPacketType
     * @param clientPrivateKeyToSing
     * @param publicKeyToEncrypt
     * @return
     */
    public static FermatPacket constructFermatPacketEncryptedAndSingedForMsjTransmit(final String destination, final String sender, final String messageContentJsonString, final FermatPacketType fermatPacketType, final String clientPrivateKeyToSing, final String publicKeyToEncrypt) {

        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(messageContentJsonString, publicKeyToEncrypt);
        String signature = AsymmetricCryptography.createMessageSignature(messageHash, clientPrivateKeyToSing);

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