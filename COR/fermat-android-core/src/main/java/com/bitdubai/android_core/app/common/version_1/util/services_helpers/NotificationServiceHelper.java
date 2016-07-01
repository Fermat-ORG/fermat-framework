package com.bitdubai.android_core.app.common.version_1.util.services_helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2016.04.04..
 */
public class NotificationServiceHelper {

    private static final String TAG = "NotificationService";
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
            Log.i(TAG, "Connected");
            try {
                notificationService = ((NotificationService.LocalBinder) service).getService();
                mNotificationServiceConnected = true;
            }catch (Exception e){
//                e.printStackTrace();
                Log.e(TAG,"Exception in onServiceConnected from NotificationServiceHelper");
            }
        }
    };

    public void bindNotificationService() {
        if(!mNotificationServiceConnected) {
            Log.i(TAG,"binding service");
            Intent intent = new Intent(contextWeakReference.get(), NotificationService.class);
//            contextWeakReference.get().startService(intent);
            contextWeakReference.get().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }

    }

    public void unbindNotificationService(){
        if(mNotificationServiceConnected) {
            Log.d(TAG ,"Unbinding AppManagerService");
            // Detach our existing connection.
            contextWeakReference.get().unbindService(mServiceConnection);
            mNotificationServiceConnected = false;

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
