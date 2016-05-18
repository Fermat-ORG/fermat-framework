package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.notifications;


import android.widget.RemoteViews;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.app_connection.ChatNotificationPainter;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.NotificationDescriptor;

/**
 * Created by Eleazar (eorono@protonmail.com) on 13/04/16.
 */
public class ChatNotificationPainterBuilder implements NotificationPainter {

    private RemoteViews remoteViews;
    private String title;
    private String image;
    private String textBody;
    private int    icon;


    public ChatNotificationPainterBuilder( String title,  String textBody, String image, int icon) {
        this.title = title;
        this.image = image;
        this.textBody = textBody;
        this.icon = icon;
    }

    public ChatNotificationPainterBuilder( String title,  String textBody, String image) {
        this(title, textBody, image, 0);
    }
    @Override
    public RemoteViews getNotificationView(String code) {
        return remoteViews;
    }

    @Override
    public String getNotificationTitle() {
        return title;
    }

    @Override
    public String getNotificationImageText() {
        return image;
    }

    @Override
    public String getNotificationTextBody() {
        return textBody;
    }

    @Override
    public int getIcon() {
        return icon;
    }

    @Override
    public String getActivityCodeResult() {
        return null;
    }

    @Override
    public boolean showNotification() {
        return true;
    }
}



