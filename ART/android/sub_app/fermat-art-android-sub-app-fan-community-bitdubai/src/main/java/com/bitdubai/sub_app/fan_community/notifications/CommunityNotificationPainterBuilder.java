package com.bitdubai.sub_app.fan_community.notifications;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.enums.FanActorConnectionNotificationType;
import com.bitdubai.sub_app.fan_community.R;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class CommunityNotificationPainterBuilder {
    public static NotificationPainter getNotification(final String code) {
        NotificationPainter notification = null;
        try {
            FanActorConnectionNotificationType notificationType = FanActorConnectionNotificationType.getByCode(code);

            switch (notificationType) {
                case CONNECTION_REQUEST_RECEIVED:
                    notification = new CommunityNotificationPainter(
                            "Fan Community",
                            "A customer wants to connect with you.",
                            "",
                            "", R.drawable.afc_ic_nav_connections);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notification;
    }
}
