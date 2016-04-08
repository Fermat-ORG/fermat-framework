package com.bitdubai.android_core.app.common.version_1.util.services_helpers;

import android.content.Context;
import android.util.Log;

import com.bitdubai.android_core.app.NotificationService;
import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;

/**
 * Created by mati on 2016.04.04..
 */
public class ServicesHelpers {

    private static final String TAG = "ServicesHelpers";

    private AppManagerServiceHelper appManagerServiceHelper;
    private NotificationServiceHelper notificationServiceHelper;
    private CommunicationServiceHelper communicationServiceHelper;

    public ServicesHelpers(Context contextWeakReference){
        appManagerServiceHelper = new AppManagerServiceHelper(contextWeakReference);
        notificationServiceHelper = new NotificationServiceHelper(contextWeakReference);
        communicationServiceHelper = new CommunicationServiceHelper(contextWeakReference);
    }

    public void bindServices(){
        Log.d(TAG,"binding every service");
        appManagerServiceHelper.bindAppManagerService();
        notificationServiceHelper.bindNotificationService();
        //communicationServiceHelper.bindCommService();
    }

    public void unbindServices(){
        Log.d(TAG,"unbind every service");
        appManagerServiceHelper.unbindAppManagerService();
        notificationServiceHelper.unbindNotificationService();
        //communicationServiceHelper.unbindCommService();
    }

    public FermatAppsManagerService getAppManager(){
        return appManagerServiceHelper.getFermatAppsManagerService();
    }

    public NotificationService getNotificationService() {
        return notificationServiceHelper.getNotificationService();
    }

    public void clear(){
        appManagerServiceHelper.clear();
        notificationServiceHelper.clear();
    }
}
