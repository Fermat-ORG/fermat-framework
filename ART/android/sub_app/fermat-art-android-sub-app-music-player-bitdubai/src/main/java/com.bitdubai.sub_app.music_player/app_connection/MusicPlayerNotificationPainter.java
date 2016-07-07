package com.bitdubai.sub_app.music_player.app_connection;

import android.widget.RemoteViews;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

/**
 * Created by miguel payarez on 13/04/16.
 */
public class MusicPlayerNotificationPainter extends NotificationPainter{



        private String title;
        private String textBody;
        private String image;
        private RemoteViews remoteViews;

        //constructor

        public MusicPlayerNotificationPainter(String title, String textBody, String image, String viewCode){
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
        return false;
    }
}
