package com.bitdubai.android_core.app.common.version_1.util.services_helpers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer  on 2016.04.04..
 */
public class AppManagerServiceHelper {

    private static final String TAG = "AppManagerServiceHelper";
    private WeakReference<Context> contextWeakReference;

    public AppManagerServiceHelper(Context contextWeakReference) {
        this.contextWeakReference = new WeakReference<Context>(contextWeakReference);
    }

    /**
     * AppsManagerService
     */
    private boolean appsManagerBoundService;
    private FermatAppsManagerService fermatAppsManagerService;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection appsManagerServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            try {
                FermatAppsManagerService.AppManagerLocalBinder binder = (FermatAppsManagerService.AppManagerLocalBinder) service;
                fermatAppsManagerService = binder.getService();
                appsManagerBoundService = true;
            }catch (Exception e){
//                e.printStackTrace();
                Log.e(TAG,"Exception in onServiceConnected");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            appsManagerBoundService = false;
        }
    };

    public void bindAppManagerService() {
        if (!appsManagerBoundService) {
            Intent intent = new Intent(contextWeakReference.get(), FermatAppsManagerService.class);
            //startService(intent);
            contextWeakReference.get().bindService(intent, appsManagerServiceConnection, Context.BIND_AUTO_CREATE);
        }

    }

    public void unbindAppManagerService(){
        if(appsManagerBoundService) {
            // Detach our existing connection.
            contextWeakReference.get().unbindService(appsManagerServiceConnection);
            appsManagerBoundService = false;
            Log.d(contextWeakReference.get().getPackageName(), "Unbinding AppManagerService");
        }
    }

    public FermatAppsManagerService getFermatAppsManagerService() {
        return fermatAppsManagerService;
    }

    public void clear() {
        if(appsManagerBoundService){
            unbindAppManagerService();
        }
        contextWeakReference.clear();
    }
}
