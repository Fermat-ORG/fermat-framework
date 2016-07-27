package com.bitdubai.fermat_android_api.engine;

import android.widget.RemoteViews;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.02.05..
 */
public class NotificationPainter implements Serializable {

    public RemoteViews getNotificationView(String code) {
        return null;
    }

    public RemoteViews getNotificationView(FermatBundle fermatBundle) {
        return null;
    }

    public String getNotificationTitle() {
        return null;
    }

    public String getNotificationImageText() {
        return null;
    }

    public String getNotificationTextBody() {
        return null;
    }

    public int getIcon() {
        return 0;
    }

    /**
     * Set Activity code to open when click notification, code from Activities enum
     *
     * @return string code activity
     */
    public String getActivityCodeResult() {
        return null;
    }

    /**
     * set enabled notification property, to show or not show
     *
     * @return
     */
    public boolean showNotification() {
        return false;
    }

}
