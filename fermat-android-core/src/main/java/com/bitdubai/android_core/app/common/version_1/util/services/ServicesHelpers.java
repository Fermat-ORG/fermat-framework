package com.bitdubai.android_core.app.common.version_1.util.services;

import android.content.Context;

import com.bitdubai.android_core.app.NotificationService;
import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;

/**
 * Created by mati on 2016.04.04..
 */
public class ServicesHelpers {

    private AppManagerServiceHelper appManagerServiceHelper;
    private NotificationServiceHelper notificationServiceHelper;

    public ServicesHelpers(Context contextWeakReference){
        appManagerServiceHelper = new AppManagerServiceHelper(contextWeakReference);
        notificationServiceHelper = new NotificationServiceHelper(contextWeakReference);
    }

    public void bindServices(){
        appManagerServiceHelper.bindAppManagerService();
    }

    public void unbindServices(){
        appManagerServiceHelper.unbindAppManagerService();
        notificationServiceHelper.unbindNotificationService();
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
