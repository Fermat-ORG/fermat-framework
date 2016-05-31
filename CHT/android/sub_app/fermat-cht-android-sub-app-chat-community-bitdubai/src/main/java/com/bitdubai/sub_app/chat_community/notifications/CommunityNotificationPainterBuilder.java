package com.bitdubai.sub_app.chat_community.notifications;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_cht_api.layer.actor_connection.enums.ActorConnectionNotificationType;
import com.bitdubai.sub_app.chat_community.R;

/**
 * This class contains all the necessary logic to build the notifications of the crypto broker community.
 *
 * Created by Franklin Marcano) on 29/02/2016.
 *
 * @author franklinmarcano1970
 * @version 1.0.0
 */
public class CommunityNotificationPainterBuilder {

    public static NotificationPainter getNotification(final String code) {

        NotificationPainter notification = null;
        try {

            ActorConnectionNotificationType notificationType = ActorConnectionNotificationType.getByCode(code);

            switch (notificationType) {
                case ACTOR_CONNECTED:
                    notification = new CommunityNotificationPainter("CHT Community", "A Chat accepted your connection request.", "", "", R.drawable.cht_ic_nav_connections);
                    break;
                case CONNECTION_REQUEST_RECEIVED:
                    notification = new CommunityNotificationPainter("CHT Community", "A Chat wants to connect with you.", "", "", R.drawable.cht_ic_nav_connections);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }

}
