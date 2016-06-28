package com.bitdubai.sub_app.crypto_broker_community.common.notifications;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.enums.CryptoBrokerActorConnectionNotificationType;

import com.bitdubai.sub_app.crypto_broker_community.R;

/**
 * This class contains all the necessary logic to build the notifications of the crypto broker community.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/02/2016.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class CommunityNotificationPainterBuilder {

    public static NotificationPainter getNotification(final String code) {

        NotificationPainter notification = null;
        try {

            CryptoBrokerActorConnectionNotificationType notificationType = CryptoBrokerActorConnectionNotificationType.getByCode(code);

            switch (notificationType) {
                case ACTOR_CONNECTED:
                    notification = new CommunityNotificationPainter("Crypto Broker Community", "A Broker accepted your connection request.", "", "", R.drawable.cbc_ic_nav_connections);
                    break;
                case CONNECTION_REQUEST_RECEIVED:
                    notification = new CommunityNotificationPainter("Crypto Broker Community", "A Broker wants to connect with you.", "", "", R.drawable.cbc_ic_nav_connections);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }

}
