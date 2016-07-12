package com.bitdubai.sub_app.chat_community.notifications;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
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

    public static NotificationPainter getNotification(final FermatBundle fermatBundle) {
        NotificationPainter notification = null;
        try {
            int notificationID = fermatBundle.getInt(NotificationBundleConstants.NOTIFICATION_ID);
//            ActorConnectionNotificationType notificationType = ActorConnectionNotificationType.getByCode(code);

            switch (notificationID) {// switch (notificationType) {
                case ChatBroadcasterConstants.CHAT_COMMUNITY_CONNECTION_ACCEPTED_NOTIFICATION://ACTOR_CONNECTED
                    return new CommunityNotificationPainter("Chat Community",
                            "A Chat user accepted your connection request.","",
                            "", R.drawable.cht_ic_nav_connections);
                case ChatBroadcasterConstants.CHAT_COMMUNITY_REQUEST_CONNECTION_NOTIFICATION://CONNECTION_REQUEST_RECEIVED
                    return new CommunityNotificationPainter("Chat Community",
                            "A Chat user accepted your connection request.","",
                            "", R.drawable.cht_ic_nav_connections);
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notification;
    }

}
