package com.bitdubai.android_core.app.common.version_1.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_BROADCAST_TYPE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

/**
 * Created by Matias Furszyfer on 2016.03.01..
 */
public class NotificationService extends Service {

    public static String LOG_TAG = "NotificationService";
    private final IBinder mBinder = new LocalBinder();
    // map from AppPublicKey to notificationId
    private Map<String, Integer> lstNotifications;

    private Map<Integer, NotificationCompat.Builder> mapNotifications;
    private int notificationIdCount;
    //for progress notifications
    private NotificationManager mNotifyManager;

    int number = 0;


    public class LocalBinder extends Binder {
        public NotificationService getService() {
            return NotificationService.this;
        }
    }


    public NotificationService() {
        this.lstNotifications = new HashMap<>();
        this.mapNotifications = new HashMap<>();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Log.v(LOG_TAG, "in onBind: " + intent.getExtras().get(LOG_TAG));
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals("cancel")) {
            int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
            cancelNotification(notificationId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

//    public void cancelNotification(String appPublicKey) {
//        NotificationManager notificationManager = (NotificationManager)
//                getSystemService(NOTIFICATION_SERVICE);
//        if(lstNotifications.containsKey(appPublicKey)) {
//            int id = lstNotifications.get(appPublicKey);
//            notificationManager.cancel(id);
//        }else {
//            Log.i(LOG_TAG, "Cancel notificación arrive with no public key");
//        }
//    }

    public void cancelNotification(FermatBundle fermatBundle) {
        String sourcePlugin = fermatBundle.getString(SOURCE_PLUGIN);
        int notificationId = fermatBundle.getInt(NOTIFICATION_ID);

        if (!dataIsValid(sourcePlugin, notificationId)) return;

        char[] letters = sourcePlugin.toCharArray();
        int leeterCount = 0;
        for (char letter : letters) {
            leeterCount += letter;
        }
        notificationId = notificationId + leeterCount;

        cancelNotification(notificationId);
    }

    public void cancelNotification(int notificationId) {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    public void pushNotification(String appPublicKey, Notification notification) {
        if (notification == null) throw new IllegalArgumentException("Notification null");
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        int id;
        if (!lstNotifications.containsKey(appPublicKey)) {
            Random random = new Random();
            id = random.nextInt(50);
            lstNotifications.put(appPublicKey, id);
        } else {
            id = lstNotifications.get(appPublicKey);
        }
//        notification.deleteIntent =
        notificationManager.notify(id, notification);
    }

    public void notificate(String publicKey, String code) {
        Notification.Builder builder = null;
        int notificationId = 0;
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (publicKey != null) {
            if (lstNotifications.containsKey(publicKey)) {
                notificationId = lstNotifications.get(publicKey);
            } else {
                synchronized (this) {
                    notificationId = lstNotifications.size() + 1;
                    lstNotifications.put(publicKey, notificationId);
                }
            }
            // notificationIdCount++;
            // lstNotifications.put(fermatStructure.getPublicKey(),notificationIdCount);
            AppConnections fermatAppConnection = null;
            try {
                fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(publicKey, this, FermatApplication.getInstance().getAppManager().getAppsSession(publicKey));
            } catch (Exception e) {
                Log.e(LOG_TAG, new StringBuilder().append("Exception params: ").append(publicKey).toString());
                e.printStackTrace();
            }
            NotificationPainter notificationPainter = null;
            if (fermatAppConnection != null) {
                try {
                    notificationPainter = fermatAppConnection.getNotificationPainter(code);
                } catch (Exception e) {
                }
            }

            if (notificationPainter != null) {
                if (notificationPainter.showNotification()) {  //get if notification settings enabled view
                    RemoteViews remoteViews = notificationPainter.getNotificationView(code);
                    Intent intent = new Intent();
                    intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, publicKey);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setAction("org.fermat.APP_LAUNCHER");
                    intent.putExtra(ApplicationConstants.ACTIVITY_CODE_TO_OPEN, notificationPainter.getActivityCodeResult());
                    //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pi = PendingIntent
                            .getBroadcast(this, 0, intent, 0);
                    if (remoteViews != null) {
                        builder = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setTicker("ticker")
                                .setPriority(Notification.PRIORITY_LOW).setAutoCancel(true)
                                .setAutoCancel(true)
                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                .setLights(Color.YELLOW, 3000, 3000)
                                .setContentIntent(pi)
                                .setContent(remoteViews)
                                .setWhen(System.currentTimeMillis());
                    } else {
                        builder = new Notification.Builder(this)
                                .setTicker(notificationPainter.getNotificationTitle())
                                .setSmallIcon((notificationPainter.getIcon() <= 0) ? R.mipmap.ic_launcher : notificationPainter.getIcon())
                                .setContentTitle(notificationPainter.getNotificationTitle())
                                .setContentText(notificationPainter.getNotificationTextBody())
                                .setContentIntent(pi)
                                .setAutoCancel(true)
                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                .setLights(Color.YELLOW, 3000, 3000);
                    }
                    Log.i(LOG_TAG, new StringBuilder().append("Launcher: ").append(publicKey).toString());
                }
            } else {
                Intent intent = new Intent();
                intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, publicKey);
                intent.setAction("org.fermat.APP_LAUNCHER");
                //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pi = PendingIntent
                        .getBroadcast(this, 0, intent, 0);
                builder = new Notification.Builder(this)
                        .setTicker("Something arrive")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Fermat: new notification")
                        .setAutoCancel(true)
                        .setContentIntent(pi)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setLights(Color.YELLOW, 3000, 3000);
                Log.i(LOG_TAG, new StringBuilder().append("Launcher: ").append(publicKey).toString());
            }

        } else {
            builder = new Notification.Builder(this)
                    .setTicker("Something arrive")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Fermat: new notification")
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setLights(Color.YELLOW, 3000, 3000);
        }

        if (builder != null) {
            Notification notification = builder.build();
//            mapNotifications.put()
            notificationManager.notify(/*(fermatStructure!=null)?notificationId:*/notificationId, notification);
        }
    }


    public void notificate(FermatBundle fermatBundle) {

        try {
            String appToOpenPublicKey = fermatBundle.getString(APP_TO_OPEN_PUBLIC_KEY);
            Owner owner = (Owner) fermatBundle.getSerializable(APP_NOTIFICATION_PAINTER_FROM);
            String appActivityToOpen = fermatBundle.getString(APP_ACTIVITY_TO_OPEN_CODE);
            String notificationType = fermatBundle.getString(NOTIFICATION_BROADCAST_TYPE);
            String sourcePlugin = fermatBundle.getString(SOURCE_PLUGIN);
            int notificationId = fermatBundle.getInt(NOTIFICATION_ID);

            //todo: sumar notificationId con sourcePlugin.

            if (!dataIsValid(owner, sourcePlugin, notificationId)) return;

            char[] letters = sourcePlugin.toCharArray();
            int leeterCount = 0;
            for (char letter : letters) {
                leeterCount += letter;
            }
            notificationId = notificationId + leeterCount;


            Notification.Builder builder = null;
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
//            if(publicKey==null) throw new IllegalArgumentException("App public key null");

//            if(lstNotifications.containsKey(notificationId)){
//                notificationId = lstNotifications.get(publicKey);
//            }else{
//                synchronized (this) {
//                    number++;
//                    notificationId = number;
//                    lstNotifications.put(publicKey,notificationId);
//                }
//            }

            AppConnections fermatAppConnection = FermatAppConnectionManager.getFermatAppConnection(owner.getOwnerAppPublicKey(), this, FermatApplication.getInstance().getAppManager().getAppsSession(owner.getOwnerAppPublicKey()));
            NotificationPainter notificationPainter = null;
            try {
                notificationPainter = fermatAppConnection.getNotificationPainter(fermatBundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent cancelIntent = new Intent("cancel");
            cancelIntent.putExtra(NOTIFICATION_ID, notificationId);
            PendingIntent cancelPendingIntent = PendingIntent.getService(this, 0, cancelIntent, 0);
            if (notificationPainter != null) {
                if (notificationPainter.showNotification()) {
                    RemoteViews remoteViews = notificationPainter.getNotificationView(fermatBundle);
                    Intent intent = new Intent();
                    intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, appToOpenPublicKey);
                    intent.setAction("org.fermat.APP_LAUNCHER");
                    intent.putExtra(ApplicationConstants.ACTIVITY_CODE_TO_OPEN, notificationPainter.getActivityCodeResult());
                    PendingIntent pi = PendingIntent
                            .getBroadcast(this, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);
                    if (remoteViews != null) {
                        builder = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setTicker("ticker")
                                .setPriority(Notification.PRIORITY_LOW).setAutoCancel(true)
                                .setAutoCancel(true)
                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                .setLights(Color.YELLOW, 3000, 3000)
                                .setContentIntent(pi)
                                .setContent(remoteViews)
                                .setDeleteIntent(cancelPendingIntent)
                                .setWhen(System.currentTimeMillis());
                    } else {
                        builder = new Notification.Builder(this)
                                .setTicker(notificationPainter.getNotificationTitle())
                                .setSmallIcon((notificationPainter.getIcon() <= 0) ? R.mipmap.ic_launcher : notificationPainter.getIcon())
                                .setContentTitle(notificationPainter.getNotificationTitle())
                                .setContentText(notificationPainter.getNotificationTextBody())
                                .setContentIntent(pi)
                                .setAutoCancel(true)
                                .setDeleteIntent(cancelPendingIntent)
                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                .setLights(Color.YELLOW, 3000, 3000);
                    }
                }
            } else {
                Intent intent = new Intent();
                intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, appToOpenPublicKey);
                intent.setAction("org.fermat.APP_LAUNCHER");
                PendingIntent pi = PendingIntent
                        .getBroadcast(this, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);
                builder = new Notification.Builder(this)
                        .setTicker("Something arrive")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Fermat: new notification")
                        .setAutoCancel(true)
                        .setContentIntent(pi)
                        .setDeleteIntent(cancelPendingIntent)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setLights(Color.YELLOW, 3000, 3000);

            }

            if (builder != null) {
                Notification notification = builder.build();
                notificationManager.notify(notificationId, notification);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    private boolean dataIsValid(Owner owner, String sourcePlugin, int notificationId) {
        boolean flag = true;
        if (owner == null) {
            Log.e(LOG_TAG, "Notification Owner null");
            flag = false;
        }
        if (notificationId == 0) {
            Log.e(LOG_TAG, "Notification notificationId 0");
            flag = false;
        }
        if (sourcePlugin == null) {
            Log.e(LOG_TAG, "Notification source plugin null ");
            flag = false;
        }
        return flag;
    }

    private boolean dataIsValid(String sourcePlugin, int notificationId) {
        boolean flag = true;
        if (notificationId == 0) {
            Log.e(LOG_TAG, "Notification notificationId 0");
            flag = false;
        }
        if (sourcePlugin == null) {
            Log.e(LOG_TAG, "Notification source plugin null ");
            flag = false;
        }
        return flag;
    }


    public int notificateProgress(FermatBundle bundle) {
        NotificationCompat.Builder mBuilder;
        try {
            int progress = (int) bundle.getSerializable(Broadcaster.PROGRESS_BAR);
            int publishId = (bundle.contains(Broadcaster.PUBLISH_ID)) ? bundle.getInt(Broadcaster.PUBLISH_ID) : 0;
            String progressText = (bundle.contains(Broadcaster.PROGRESS_BAR_TEXT)) ? bundle.getString(Broadcaster.PROGRESS_BAR_TEXT) : null;

            mNotifyManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            if (progress < 0 || progress > 100) {
                mNotifyManager.cancel(publishId);
            } else {

// Displays the progress bar for the first time.

                if (publishId == 0) {
                    mBuilder = new NotificationCompat.Builder(this);
                    mBuilder.setContentTitle((progressText != null) ? progressText : "Downloading something...")
                            .setContentText("Download in progress")
                            .setSmallIcon(R.drawable.fermat_logo_310_x_310);
                    Random random = new Random();
                    publishId = random.nextInt();
                    if (publishId < 0) {
                        publishId = publishId * (-1);
                    }
                    mapNotifications.put(publishId, mBuilder);
                } else {
                    if (mapNotifications.containsKey(publishId))
                        mBuilder = mapNotifications.get(publishId);
                    else {
                        Log.i(LOG_TAG, "Error, Notification id not found");
                        return 0;
                    }
                }
                mBuilder.setProgress(100, progress, false);

                mNotifyManager.notify(publishId, mBuilder.build());
                return publishId;
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void notificateProgress(final String code, int progress) {
        if (!lstNotifications.containsKey(code)) {
            notificationIdCount++;
            lstNotifications.put(code, notificationIdCount);
        }
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Downloading blockchain blocks")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.fermat_logo_310_x_310);
// Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
// Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr += 5) {
// Sets the progress indicator to a max value, the
// current completion percentage, and "determinate"
// state
                            mBuilder.setProgress(100, incr, false);
// Displays the progress bar for the first time.
                            mNotifyManager.notify(lstNotifications.get(code), mBuilder.build());
// Sleeps the thread, simulating an operation
// that takes time
                            try {
// Sleep for 5 seconds
                                Thread.sleep(5 * 1000);
                            } catch (InterruptedException e) {
                                Log.d(LOG_TAG, "sleep failure");
                            }
                        }
// When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
// Removes the progress bar
                                .setProgress(0, 0, false);
                        mNotifyManager.notify(lstNotifications.get(code), mBuilder.build());
                    }
                }
// Starts the thread by calling the run() method in its Runnable
        ).start();
    }
}