package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.factories;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.MalformedFMPPacketException;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.factories.NetworkServiceMessageFactory</code>
 * is a factory class that builds the Network Service Messages.
 * <p/>
 *
 * Created  by Leon Acosta - (laion.cj91@gmail.com) on 13/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkServiceMessageFactory {

    /**
     * Construct a FermatMessage encrypted with the destination identity public key and signed
     * whit the private key passed as an argument
     *
     * @param senderIdentity
     * @param receiverIdentityPublicKey
     * @param content
     * @param fermatMessageContentType
     * @return FermatMessage
     * @throws FMPException
     */
    public static NetworkServiceMessage constructNetworkServiceMessageEncryptedAndSigned(final ECCKeyPair senderIdentity, final String receiverIdentityPublicKey, final String content, final FermatMessageContentType fermatMessageContentType) throws FMPException{

        String messageHash = AsymmetricCryptography.encryptMessagePublicKey(content, receiverIdentityPublicKey);
        String signature   = AsymmetricCryptography.createMessageSignature(messageHash, senderIdentity.getPrivateKey());

        return new NetworkServiceMessage(messageHash, null, fermatMessageContentType, FermatMessagesStatus.PENDING_TO_SEND, receiverIdentityPublicKey, senderIdentity.getPublicKey(), new Timestamp(System.currentTimeMillis()), signature);
    }


    /**
     * Construct a FermatMessage with parameters
     *
     * @param senderIdentityPublicKey
     * @param receiverIdentityPublicKey
     * @param content
     * @param fermatMessageContentType
     * @return FermatMessage
     * @throws FMPException
     */
    public static NetworkServiceMessage constructNetworkServiceMessage(final String senderIdentityPublicKey, final String receiverIdentityPublicKey, final String content, final FermatMessageContentType fermatMessageContentType) throws FMPException{

        return new NetworkServiceMessage(content, null, fermatMessageContentType, FermatMessagesStatus.PENDING_TO_SEND, receiverIdentityPublicKey, senderIdentityPublicKey, new Timestamp(System.currentTimeMillis()), null);
    }

    public static NetworkServiceMessage constructNetworkServiceMessage(final String                   senderIdentityPublicKey  ,
                                                       final PlatformComponentType    senderType               ,
                                                       final NetworkServiceType       senderNsType             ,
                                                       final String                   receiverIdentityPublicKey,
                                                       final PlatformComponentType    receiverType             ,
                                                       final NetworkServiceType       receiverNsType           ,
                                                       final String                   content                  ,
                                                       final FermatMessageContentType fermatMessageContentType ) throws FMPException{

        NetworkServiceMessage message = new NetworkServiceMessage(
                content,
                null,
                fermatMessageContentType,
                FermatMessagesStatus.PENDING_TO_SEND,
                receiverIdentityPublicKey,
                senderIdentityPublicKey,
                new Timestamp(System.currentTimeMillis()),
                null
        );

        message.setSenderType(senderType);
        message.setReceiverType(receiverType);

        message.setSenderNsType(senderNsType);
        message.setReceiverNsType(receiverNsType);

        return message;
    }


    /**
     * Construct a FermatMessageCommunication from a json string
     *
     * @param jsonMessageData
     * @return FermatPacketCommunication
     * @throws FMPException
     */
	public static NetworkServiceMessage constructFermatMessageFromJsonString(String jsonMessageData) throws FMPException {

        try {

            /*
             * Validate the data
             */
            validatePacketDataString(jsonMessageData);

            /**
             * Create a temporal object
             */
            NetworkServiceMessage temp = new NetworkServiceMessage();

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