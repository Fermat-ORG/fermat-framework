package com.bitdubai.android_core.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat.R;

/**
 * Created by mati on 2016.03.01..
 */
public class NotificationService extends Service {

    public static String LOG_TAG = "NotificationService";
    private final IBinder mBinder = new LocalBinder();


    public class LocalBinder extends Binder {
        NotificationService getService() {
            return NotificationService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind: " + intent.getExtras().get(LOG_TAG));
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
    }

    private void notificate(String code,FermatStructure fermatStructure){
        if (fermatStructure != null) {
            //FermatStructure fermatStructure = getAppInUse(appPublicKey);
            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(fermatStructure.getPublicKey(), this);
            NotificationPainter notificationPainter = fermatAppConnection.getNotificationPainter(code);
            if (notificationPainter != null) {
                RemoteViews remoteViews = notificationPainter.getNotificationView(code);
                Intent intent = new Intent(this, (fermatStructure.getFermatAppType() == FermatAppType.WALLET) ? WalletActivity.class : SubAppActivity.class);
                intent.putExtra((fermatStructure.getFermatAppType() == FermatAppType.WALLET) ? WalletActivity.WALLET_PUBLIC_KEY : SubAppActivity.SUB_APP_PUBLIC_KEY, fermatStructure.getPublicKey());
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pi = PendingIntent
                        .getActivity(this, 0, intent, 0);

                Notification.Builder builder = null;
                if (remoteViews != null) {
                    builder = new Notification.Builder(this).setSmallIcon(R.drawable.fermat_logo_310_x_310).setTicker("ticker")
                            .setPriority(Notification.PRIORITY_LOW).setAutoCancel(true)
                            .setAutoCancel(true)
                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                            .setLights(Color.YELLOW, 3000, 3000)
                            .setContent(remoteViews)
                            .setWhen(System.currentTimeMillis());
                } else {
                    builder = new Notification.Builder(this)
                            .setTicker(notificationPainter.getNotificationTitle())
                            .setSmallIcon((notificationPainter.getIcon() <= 0) ? R.drawable.fermat_logo_310_x_310 : notificationPainter.getIcon())
                            .setContentTitle(notificationPainter.getNotificationTitle())
                            .setContentText(notificationPainter.getNotificationTextBody())
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                            .setLights(Color.YELLOW, 3000, 3000);
                }

                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder.build());
            }
        } else {
            Notification.Builder builder = new Notification.Builder(this)
                    .setTicker("Something arrive")
                    .setSmallIcon(R.drawable.fermat_logo_310_x_310)
                    .setContentTitle("Fermat: new notification")
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setLights(Color.YELLOW, 3000, 3000);

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());
        }
    }

}
