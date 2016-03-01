package com.bitdubai.sub_app.crypto_customer_community.notifications;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.enums.CryptoCustomerActorConnectionNotificationType;

/**
 * This class contains all the necessary logic to build the notifications of the crypto customer community.
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

            CryptoCustomerActorConnectionNotificationType notificationType = CryptoCustomerActorConnectionNotificationType.getByCode(code);

            switch (notificationType) {
                case CONNECTION_REQUEST_RECEIVED:
                    notification = new CommunityNotificationPainter("Crypto Customer Community", "A customer wants to connect with you.", "", "");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }

}
