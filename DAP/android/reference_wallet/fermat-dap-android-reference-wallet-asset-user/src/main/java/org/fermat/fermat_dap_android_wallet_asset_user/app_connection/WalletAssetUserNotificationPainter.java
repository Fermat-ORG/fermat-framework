package org.fermat.fermat_dap_android_wallet_asset_user.app_connection;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

import java.io.Serializable;

/**
 * Created by Nerio on 12/03/16.
 */
public class WalletAssetUserNotificationPainter extends NotificationPainter implements Serializable {

    private String title;
    private String textBody;
    private String image;
    private RemoteViews remoteViews;

    //constructor

    public WalletAssetUserNotificationPainter(String title, String textBody, String image, String viewCode) {
        this.title = title;
        this.textBody = textBody;
        this.image = image;
    }

    public RemoteViews getNotificationView(String code) {
        return this.remoteViews;
    }

    public String getNotificationTitle() {
        return this.title;
    }

    public String getNotificationImageText() {
        return this.image;
    }


    public String getNotificationTextBody() {
        return this.textBody;
    }

    public int getIcon() {
        return 0;
    }

    public String getActivityCodeResult() {
        return null;
    }

    public boolean showNotification() {
        return true;
    }
}