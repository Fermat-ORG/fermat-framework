package com.bitdubai.sub_app.chat_community.notifications;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

/**
 * This class contains the basic functionality of the crypto broker community notification painter.
 * <p/>
 * Created by Franklin Marcano on 29/02/2016.
 *
 * @author franklinmarcano1970
 * @version 1.0.0
 */
public class CommunityNotificationPainter extends NotificationPainter {

    private String title;
    private String textBody;
    private String image;
    private RemoteViews remoteViews;
    private int icon;

    public CommunityNotificationPainter(final String title,
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
