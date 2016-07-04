package com.bitdubai.sub_app.intra_user_community.app_connection;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

/**
 * Created by natalia on 11/02/16.
 */
public class UserCommunityNotificationPainter extends NotificationPainter{

    private String title;
    private String textBody;
    private String image;
    private RemoteViews remoteViews;
    private String codeReturn;


    //constructor

    public UserCommunityNotificationPainter(String title, String textBody, String image, String viewCode,String codeReturn)
    {
        this.title    = title;
        this.textBody = textBody;
        this.image    = image;
        remoteViews = null;
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
        return  this.codeReturn ;
    }

    @Override
    public boolean showNotification() {
        return true;
    }
}
