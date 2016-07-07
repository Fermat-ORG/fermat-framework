package com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

import java.io.Serializable;

/**
 * Created by natalia on 12/02/16.
 */
public class BitcoinWalletNotificationPainter extends NotificationPainter implements Serializable {

    private String title;
    private String textBody;
    private String image;
    private RemoteViews remoteViews;
    private String activityCodeToOpen;
    private boolean showNotification;
    private String codeReturn;

    //constructor

    public BitcoinWalletNotificationPainter(String title, String textBody, String image, String viewCode, boolean showNotification,String codeReturn){
        this.title    = title;
        this.textBody = textBody;
        this.image    = image;
        this.showNotification = showNotification;
        this.codeReturn = codeReturn;

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
        return this.codeReturn;
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }


}
