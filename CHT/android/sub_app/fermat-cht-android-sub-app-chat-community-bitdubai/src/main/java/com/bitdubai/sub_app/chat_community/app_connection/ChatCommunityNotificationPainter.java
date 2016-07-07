package com.bitdubai.sub_app.chat_community.app_connection;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

/**
 * ChatCommunityNotificationPainter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class ChatCommunityNotificationPainter extends NotificationPainter {

    private String title;
    private String textBody;
    private String image;
    private RemoteViews remoteViews;

    //constructor

    public ChatCommunityNotificationPainter(String title, String textBody, String image, String viewCode)
    {
        this.title    = title;
        this.textBody = textBody;
        this.image    = image;
        remoteViews = null;
    }

    @Override
    public RemoteViews getNotificationView(String code) {
        return this.remoteViews;
    }

    @Override
    public String getNotificationTitle() {
        return this.title;
    }

    @Override
    public String getNotificationImageText() {
        return this.image;
    }

    @Override
    public String getNotificationTextBody() {
        return this.textBody;
    }

    @Override
    public int getIcon() {
        return 0;
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
