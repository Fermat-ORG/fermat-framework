package com.bitdubai.fermat_android_api.engine;

import android.widget.RemoteViews;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.02.05..
 */
public interface NotificationPainter extends Serializable{

    RemoteViews getNotificationView(String code);

    String getNotificationTitle();

    String getNotificationImageText();

    String getNotificationTextBody();

    int getIcon();

    /**
     * Set Activity code to open when click notification, code from Activities enum
     * @return string code activity
     */
    String getActivityCodeResult();

    /**
     * set enabled notification property, to show or not show
     * @return
     */
    boolean showNotification();

}
