package com.bitdubai.reference_niche_wallet.loss_protected_wallet.app_connection;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

/**
 * Created by natalia on 12/02/16.
 */
public class LossProtectedWalletNotificationPainter extends NotificationPainter {

    private String title;
    private String textBody;
    private String image;
    private RemoteViews remoteViews;
    private String activityCodeToOpen;
    private boolean showNotification;

    //constructor

    public LossProtectedWalletNotificationPainter(String title, String textBody, String image, String viewCode, boolean showNotification,String activityCodeToOpen){
        this.title    = title;
        this.textBody = textBody;
        this.image    = image;
        this.showNotification = showNotification;
        this.activityCodeToOpen = activityCodeToOpen;

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
        return this.activityCodeToOpen;
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }


}
