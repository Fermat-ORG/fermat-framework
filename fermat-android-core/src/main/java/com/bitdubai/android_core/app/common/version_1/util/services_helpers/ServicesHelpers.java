package com.bitdubai.android_core.app.common.version_1.util.services_helpers;

import android.content.Context;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;
import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.ClientSystemBrokerService;

/**
 * Created by mati on 2016.04.04..
 */
public class ServicesHelpers {

    private static final String TAG = "ServicesHelpers";

    private AppManagerServiceHelper appManagerServiceHelper;
    private NotificationServiceHelper notificationServiceHelper;
    private ClientSideBrokerServiceHelper clientSideBrokerServiceHelper;

    public ServicesHelpers(Context contextWeakReference){
        appManagerServiceHelper = new AppManagerServiceHelper(contextWeakReference);
        notificationServiceHelper = new NotificationServiceHelper(contextWeakReference);
        clientSideBrokerServiceHelper = new ClientSideBrokerServiceHelper(contextWeakReference);
    }

    public void bindServices(){
        Log.d(TAG, "binding every service");
        appManagerServiceHelper.bindAppManagerService();
        notificationServiceHelper.bindNotificationService();
        clientSideBrokerServiceHelper.clientSideBrokerBoundService();
    }

    public void unbindServices(){
        Log.d(TAG, "unbind every service");
        appManagerServiceHelper.unbindAppManagerService();
        notificationServiceHelper.unbindNotificationService();
        clientSideBrokerServiceHelper.unbindClientSideBrokerService();
    }

    public FermatAppsManagerService getAppManager(){
        return appManagerServiceHelper.getFermatAppsManagerService();
    }

    public NotificationService getNotificationService() {
        return notificationServiceHelper.getNotificationService();
    }

    public ClientSystemBrokerService getClientSideBrokerService() {
        return clientSideBrokerServiceHelper.getClientSystemBrokerService();
    }

    public void clear(){
        appManagerServiceHelper.clear();
        notificationServiceHelper.clear();
        clientSideBrokerServiceHelper.clear();
    }


}
