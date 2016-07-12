package com.bitdubai.sub_app_artist_community.notifications;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/04/16.
 */
public class CommunityNotificationPainter extends NotificationPainter {

    private String title;
    private String textBody;
    private String image;
    private RemoteViews remoteViews;
    private int icon;

    public CommunityNotificationPainter(
            final String title,
            final String textBody,
            final String image,
            final String viewCode,
            final int icon) {
        this.title = title;
        this.textBody = textBody;
        this.image = image;
        this.icon = icon;
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
