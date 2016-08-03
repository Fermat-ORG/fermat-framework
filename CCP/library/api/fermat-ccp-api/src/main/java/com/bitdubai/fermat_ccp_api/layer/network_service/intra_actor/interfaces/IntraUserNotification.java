package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums.NotificationDescriptor;

import java.util.UUID;

/**
 * The interface <code>IntraUserNotification</code>
 * provides the methods to analyze a notification coming from another intra user
 */
public interface IntraUserNotification {

    /**
     * The method <code>getPublicKeyOfTheSender</code> tells us the public key
     * of the intra user sending the notification
     *
     * @return the public key
     */

    String getActorSenderAlias();

    byte[] getActorSenderProfileImage();

    //new

    String getActorSenderPhrase();

    UUID getId();

    Actors getActorDestinationType();

    String getActorDestinationPublicKey();

    String getActorSenderPublicKey();

    Actors getActorSenderType();

    void setFlagReadead(boolean flagReadead);

    /**
     * The method <code>getNotificationDescriptor</code> tells us the nature of the notification
     *
     * @return the descriptor of the notification
     */
    NotificationDescriptor getNotificationDescriptor();

    long getSentDate();

    /**
     * The method <code>getCity</code> returns the City of the represented intra user
     *
     * @return the name of the intra user
     */
    public String getCity();
    /**
     * The method <code>getCountry</code> returns the Country of the represented intra user
     *
     * @return the name of the intra user
     */
    public String getCountry();

}
