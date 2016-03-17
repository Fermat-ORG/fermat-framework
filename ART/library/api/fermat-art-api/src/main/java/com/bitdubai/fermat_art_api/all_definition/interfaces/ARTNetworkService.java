package com.bitdubai.fermat_art_api.all_definition.interfaces;

import com.bitdubai.fermat_art_api.all_definition.exceptions.CantConfirmActorNotificationException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantGetActortNotificationException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorNotification;

import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 14/03/16.
 */
public interface ARTNetworkService {

    /**
     * The method <coda>getPendingNotifications</coda> returns all pending notifications
     * of responses to requests for connection
     *
     * @return List of IntraUserNotification
     */
    List<ActorNotification> getPendingNotifications() throws CantGetActortNotificationException;

    /**
     * The method <coda>confirmActorNotification</coda> confirm the pending notification
     */
    void confirmActorNotification(UUID notificationID) throws CantConfirmActorNotificationException;

}
