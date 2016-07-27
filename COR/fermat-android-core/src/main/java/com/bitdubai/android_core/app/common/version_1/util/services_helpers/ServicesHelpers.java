package com.bitdubai.android_core.app.common.version_1.util.services_helpers;

import android.content.Context;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.ClientBrokerService;
import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;

/**
 * Created by Matias Furszyfer on 2016.04.04..
 */
public class ServicesHelpers {

    private static final String TAG = "ServicesHelpers";

    private AppManagerServiceHelper appManagerServiceHelper;
    private NotificationServiceHelper notificationServiceHelper;
    private ClientSideBrokerServiceHelperAidle clientSystemBrokerServiceAIDL;

    private boolean isInBackground;


    public ServicesHelpers(Context contextWeakReference, boolean isInBackground) {
        this.isInBackground = isInBackground;
        if (!isInBackground) {
            appManagerServiceHelper = new AppManagerServiceHelper(contextWeakReference);
            notificationServiceHelper = new NotificationServiceHelper(contextWeakReference);
            clientSystemBrokerServiceAIDL = new ClientSideBrokerServiceHelperAidle(contextWeakReference);
        }
    }

    public void bindServices() {
        Log.d(TAG, "binding every service");
        bindBackgroundServices();
        if (!isInBackground) {
            appManagerServiceHelper.bindAppManagerService();
            notificationServiceHelper.bindNotificationService();
            clientSystemBrokerServiceAIDL.clientSideBrokerBoundService();
        }
    }

    public void unbindServices() {
        Log.d(TAG, "unbind every service");
        unbindBackgroundServices();
        if (!isInBackground) {
            appManagerServiceHelper.unbindAppManagerService();
            notificationServiceHelper.unbindNotificationService();
            clientSystemBrokerServiceAIDL.unbindClientSideBrokerService();
        }
    }

    /**
     * Future background services
     */
    public void bindBackgroundServices() {

    }

    public void unbindBackgroundServices() {

    }


    public FermatAppsManagerService getAppManager() {
        return appManagerServiceHelper.getFermatAppsManagerService();
    }

    public NotificationService getNotificationService() {
        Log.i(TAG, new StringBuilder().append("getNotificationService, service state: ").append(notificationServiceHelper.getNotificationService()).toString());
        return notificationServiceHelper.getNotificationService();
    }

    public void clear() {
        appManagerServiceHelper.clear();
        notificationServiceHelper.clear();
    }


    public ClientBrokerService getClientSideBrokerServiceAIDL() {
        return clientSystemBrokerServiceAIDL.getClientSystemBrokerService();
    }


}
