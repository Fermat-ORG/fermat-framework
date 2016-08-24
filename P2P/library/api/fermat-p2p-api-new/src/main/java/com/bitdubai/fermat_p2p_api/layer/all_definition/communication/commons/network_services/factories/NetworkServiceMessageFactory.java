package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.factories;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageStatus;

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
     * Through this method we can build a Network service Message between network services.
     *
     * @param senderProfile
     * @param receiverProfile
     * @param content
     * @param messageContentType
     *
     * @return a NetworkServiceMessage instance
     */
    public static NetworkServiceMessage buildNetworkServiceMessage(final NetworkServiceProfile senderProfile     ,
                                                                   final NetworkServiceProfile receiverProfile   ,
                                                                   final String                content           ,
                                                                   final MessageContentType    messageContentType) {

        NetworkServiceMessage message = new NetworkServiceMessage();

        message.setContent(content);
        message.setNetworkServiceType(senderProfile.getNetworkServiceType());
        message.setSenderPublicKey(senderProfile.getIdentityPublicKey());
        message.setReceiverPublicKey(receiverProfile.getIdentityPublicKey());
        message.setShippingTimestamp(new Timestamp(System.currentTimeMillis()));
        message.setIsBetweenActors(Boolean.FALSE);
        message.setMessageStatus(MessageStatus.PENDING_TO_SEND);
        message.setMessageContentType(messageContentType);

        return message;
    }

    /**
     * Through this method we can build a Network service Message between actors.
     *
     * @param senderActorProfile
     * @param receiverActorProfile
     * @param networkServiceProfile
     * @param content
     * @param messageContentType
     *
     * @return a NetworkServiceMessage instance
     */
    public static NetworkServiceMessage buildNetworkServiceMessage(final ActorProfile          senderActorProfile   ,
                                                                   final ActorProfile          receiverActorProfile ,
                                                                   final NetworkServiceProfile networkServiceProfile,
                                                                   final String                content              ,
                                                                   final MessageContentType    messageContentType   ) {

        NetworkServiceMessage message = new NetworkServiceMessage();

        message.setContent(content);
        message.setNetworkServiceType(networkServiceProfile.getNetworkServiceType());
        message.setSenderPublicKey(senderActorProfile.getIdentityPublicKey());
        message.setReceiverPublicKey(receiverActorProfile.getIdentityPublicKey());
        message.setShippingTimestamp(new Timestamp(System.currentTimeMillis()));
        message.setIsBetweenActors(Boolean.TRUE);
        message.setMessageStatus(MessageStatus.PENDING_TO_SEND);
        message.setMessageContentType(messageContentType);

        return message;
    }

}