package com.bitdubai.sub_app.wallet_manager.notification_painter;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

/**
 * Created by natalia on 12/02/16.
 */
public class DesktopNotificationPainter extends NotificationPainter {

    private String title;
    private String textBody;
    private String image;
    private RemoteViews remoteViews;

    //constructor

    public DesktopNotificationPainter(String title, String textBody, String image, String viewCode){
        this.title    = title;
        this.textBody = textBody;
        this.image    = image;

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
