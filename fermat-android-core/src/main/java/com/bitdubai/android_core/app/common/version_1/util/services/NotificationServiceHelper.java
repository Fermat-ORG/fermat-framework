package com.bitdubai.android_core.app.common.version_1.util.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.bitdubai.android_core.app.NotificationService;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2016.04.04..
 */
public class NotificationServiceHelper {

    private final WeakReference<Context> contextWeakReference;
    private NotificationService notificationService;
    private boolean mNotificationServiceConnected;

    public NotificationServiceHelper(Context contextWeakReference) {
        this.contextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mNotificationServiceConnected = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            notificationService = ((NotificationService.LocalBinder)service).getService();
            mNotificationServiceConnected = true;
        }
    };

    public void bindNotificationService() {
        if(!mNotificationServiceConnected) {
            Intent intent = new Intent(contextWeakReference.get(), NotificationService.class);
            contextWeakReference.get().startService(intent);
            contextWeakReference.get().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }

    }

    public void unbindNotificationService(){
        if(mNotificationServiceConnected) {
            // Detach our existing connection.
            contextWeakReference.get().unbindService(mServiceConnection);
            mNotificationServiceConnected = false;
            Log.d(contextWeakReference.get().getPackageName(), "Unbinding AppManagerService");
        }
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }


    public void clear() {
        if(mNotificationServiceConnected){
            unbindNotificationService();
        }
        contextWeakReference.clear();
    }

}
