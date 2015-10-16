package com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums.IntraUserNotificationDescriptor;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserNotification</code>
 * provides the methods to analyze a notification coming from another intra user
 */
public interface IntraUserNotification {

    /**
     * The method <code>getPublicKeyOfTheIntraUserSendingUsANotification</code> tells us the public key
     * of the intra user sending the notification
     *
     * @return the public key
     */
    String getPublicKeyOfTheIntraUserSendingUsANotification();

    String getPublicKeyOfTheIntraUserToConnect();

    String getIntraUserToConnectAlias();

    byte[] getIntraUserToConnectProfileImage();

    //new

    UUID getId();

    Actors getActorDestinationType();

    String getActorDestinationPublicKey();

    String getActorSenderPublicKey();

    Actors getActorSenderType();

    /**
     * The method <code>getNotificationDescriptor</code> tells us the nature of the notification
     *
     * @return the descriptor of the notification
     */
    IntraUserNotificationDescriptor getNotificationDescriptor();
}
