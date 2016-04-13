package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.notifications;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.app_connection.ChatNotificationPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.NotificationDescriptor;

/**
 * Created by Eleazar (eorono@protonmail.com) on 13/04/16.
 */
public class ChatNotificationPainterBuilder {


    public static NotificationPainter getNotification(String code) {

        NotificationPainter notification =null;
        try {
            NotificationDescriptor notificationType = NotificationDescriptor.getByCode(code);

            switch(notificationType){
                case RECEIVED:
                    notification = new ChatNotificationPainter("Fermat Chat","You Received a new message", "", "", android.R.drawable.ic_notification_overlay);
                    break;
            }
        }


        catch (InvalidParameterException e) {
            e.printStackTrace();
        }


        return notification;
    }
}
