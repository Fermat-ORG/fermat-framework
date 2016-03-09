package com.bitdubai.fermat_android_api.engine;

import android.widget.RemoteViews;

/**
 * Created by mati on 2016.02.05..
 */
public interface NotificationPainter {

    RemoteViews getNotificationView(String code);

    String getNotificationTitle();

    String getNotificationImageText();

    String getNotificationTextBody();

    int getIcon();

    String getActivityCodeResult();
//set enabled notification property
    boolean showNotification();

}
