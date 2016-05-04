package com.bitdubai.sub_app_artist_community.notifications;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.enums.ArtistActorConnectionNotificationType;
import com.bitdubai.sub_app.artist_community.R;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class CommunityNotificationPainterBuilder {
    public static NotificationPainter getNotification(final String code) {
        NotificationPainter notification = null;
        try {
            ArtistActorConnectionNotificationType notificationType = ArtistActorConnectionNotificationType.getByCode(code);

            switch (notificationType) {
                case CONNECTION_REQUEST_RECEIVED:
                    notification = new CommunityNotificationPainter(
                            "Artist Community",
                            "A Fermat Artist wants to connect with you.",
                            "",
                            "", R.drawable.aac_ic_nav_connections);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notification;
    }
}
