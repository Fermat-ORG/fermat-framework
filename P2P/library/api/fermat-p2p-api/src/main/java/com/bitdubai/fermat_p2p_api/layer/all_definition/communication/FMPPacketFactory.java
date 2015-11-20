/*
 * @#FMPPacketFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket.FMPPacketType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.MalformedFMPPacketException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FermatPacketCommunicationFactory</code> is a
 * factory class tha construct the different packet types
 * <p/>
 *
 * Created by Jorge Gonzales
 * Update by Roberto Requena - (rart3001@gmail.com) on 11/06/15.
 *
 * @version 1.0
 */
public class FMPPacketFactory {

    /**
     * Construct a FermatPacketCommunication encrypted with the destination identity public key and signed
     * whit the private key passed as an argument
     *
     * @param sender
     * @param destination
     * @param message
     * @param fmpPacketType
     * @param networkServicesType
     * @param privateKey to sing the message
     * @return FermatPacketCommunication
     * @throws FMPException
     */
    public static FMPPacket constructCloudFMPPacketEncryptedAndSinged(final String sender, final String destination, final String message, final FMPPacketType fmpPacketType, final NetworkServices networkServicesType, final String privateKey) throws FMPException{

        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(message, destination);
        String signature = AsymmetricCryptography.createMessageSignature(messageHash, privateKey);

        return new CloudFMPPacket(destination, sender, fmpPacketType, messageHash, signature, networkServicesType);
    }


    /**
     * Construct a FermatPacketCommunication from a json string
     *
     * @param jsonPacketData
     * @return FermatPacketCommunication
     * @throws FMPException
     */
	public static FMPPacket constructCloudFMPPacket(String jsonPacketData) throws FMPException {

        try {

            /*
             * Validate the data
             */
            validatePacketDataString(jsonPacketData);

            /**
             * Create a temporal object
             */
            CloudFMPPacket temp = new CloudFMPPacket();

            /*
             * Convert to the object
             */
            return temp.fromJson(jsonPacketData);

        }catch (Exception exception){

            throw new MalformedFMPPacketException(MalformedFMPPacketException.DEFAULT_MESSAGE, exception, null, "The packet data is not properly assembled");
        }

	}

    /**
     * Construct a fermat packet with the parameters
     *
     * @param destination
     * @param sender
     * @param fMPPacketType
     * @param message
     * @param signature
     * @param networkServicesType
     * @return FermatPacketCommunication
     * @throws FMPException
     */
	public static FMPPacket constructCloudFMPPacket(String destination, String sender, FMPPacketType fMPPacketType, String message, String signature, NetworkServices networkServicesType) throws FMPException{

        if((destination == null) || (sender == null) || (fMPPacketType == null) || (message == null) || (signature == null) || (networkServicesType == null)){

            throw new MalformedFMPPacketException("All argument to construct the FermatPacketCommunication are required",null, "", "Any argument is null");
        }

		return new CloudFMPPacket(destination, sender, fMPPacketType, message, signature, networkServicesType);
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