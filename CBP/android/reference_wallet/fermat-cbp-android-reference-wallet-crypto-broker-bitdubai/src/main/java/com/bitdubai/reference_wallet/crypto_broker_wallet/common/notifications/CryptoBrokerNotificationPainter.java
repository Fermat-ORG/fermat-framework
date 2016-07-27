package com.bitdubai.reference_wallet.crypto_broker_wallet.common.notifications;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

/**
 * Created by guillermo on 04/03/16.
 */
public class CryptoBrokerNotificationPainter extends NotificationPainter {

    private RemoteViews remoteViews;
    private String title;
    private String image;
    private String textBody;
    private int icon;


    public CryptoBrokerNotificationPainter(String title, String textBody, String image, int icon) {
        this.title = title;
        this.image = image;
        this.textBody = textBody;
        this.icon = icon;
    }

    public CryptoBrokerNotificationPainter(String title, String textBody, String image) {
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
