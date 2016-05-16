package com.bitdubai.android_core.app;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.ClientBrokerService;
import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;
import com.bitdubai.android_core.app.common.version_1.receivers.NotificationReceiver;
import com.bitdubai.android_core.app.common.version_1.util.mail.YourOwnSender;
import com.bitdubai.android_core.app.common.version_1.util.services_helpers.ServicesHelpers;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_core.FermatSystem;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.io.Serializable;
import java.util.List;

/**
 * Matias Furszyfer
 */


@ReportsCrashes(//formUri = "http://yourserver.com/yourscript",
        mailTo = "matiasfurszyfer@gmail.com",
        customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)

public class ApplicationSession extends MultiDexApplication implements Serializable,FermatApplicationSession {

    private final String TAG = "ApplicationSession";

    private static ApplicationSession instance;
    /**
     * Application states
     */
    public static final int STATE_NOT_CREATED=0;
    public static final int STATE_STARTED=1;
    public static final int STATE_STARTED_DESKTOP=2;

    /**
     *  Fermat platform
     */

    private FermatSystem fermatSystem;

    /**
     *  Application state
     */
    public static int applicationState=STATE_NOT_CREATED;
    private boolean fermatRunning;


    public static ApplicationSession getInstance(){
        return instance;
    }

    private Thread.UncaughtExceptionHandler defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();


    /**
     *  NotificationReceiver
     */
    private NotificationReceiver notificationReceiver;

    /**
     * Services helpers
     */
    private ServicesHelpers servicesHelpers;

    /**
     *  Application session constructor
     */

    public ApplicationSession() {
        super();
        instance = this;
        fermatSystem = FermatSystem.getInstance();


    }


    /**
     *  Method to get the fermat system
     * @return FermatSystem
     */
    public FermatSystem getFermatSystem() {
        if(fermatSystem==null){
            fermatSystem = FermatSystem.getInstance();
        }
        return fermatSystem;
    }

    /**
     *  Method to change the application state from services or activities
     *
     * @param applicationState  is an application state constant from ApplicationSession class
     */

    public void changeApplicationState(int applicationState){
        ApplicationSession.applicationState =applicationState;
    }

    /**
     * Method to get the application state from services or activities
     *
     * @return application state constant from ApplicationSession class
     */

    public int getApplicationState(){
        return applicationState;
    }


    @Override
    public void onTerminate(){
        servicesHelpers.unbindServices();
        unregisterReceiver(notificationReceiver);
        super.onTerminate();
    }

    @Override
    public void onCreate() {
        ACRA.init(this);
        YourOwnSender yourSender = new YourOwnSender(getApplicationContext());
        ACRA.getErrorReporter().setReportSender(yourSender);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                e.printStackTrace();
                handleUncaughtException(thread, e);
//                ACRA.getErrorReporter().handleSilentException(e);
                ACRA.getErrorReporter().handleException(e);
                ACRA.getErrorReporter().uncaughtException(thread,e);
            }
        });

//        loadProcessInfo();

        boolean isThisProcessFermatFrontApp = isThisProcessFermatFrontApp();
        runServiceHelpers(isThisProcessFermatFrontApp);
        if(!isThisProcessFermatFrontApp){
            notificationReceiver = new NotificationReceiver(this);
            IntentFilter intentFilter = new IntentFilter(NotificationReceiver.INTENT_NAME);
            registerReceiver(notificationReceiver, intentFilter);
        }

//        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("org.fermat.SYSTEM_RUNNING");
//        bManager.registerReceiver(new FermatSystemRunningReceiver(this), intentFilter);

        //new ANRWatchDog().start();

        super.onCreate();
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
       // MultiDex.install(this);
    }


    private void handleUncaughtException (Thread thread, Throwable e) {
        Toast.makeText(this,"Sorry, The app is not working",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
    }

    public FermatAppsManagerService getAppManager(){
        return getServicesHelpers().getAppManager();
    }

    public NotificationService getNotificationService(){
        return getServicesHelpers().getNotificationService();
    }

    public ClientBrokerService getClientSideBrokerService(){
        return getServicesHelpers().getClientSideBrokerService();
    }

    public ServicesHelpers getServicesHelpers() {
        return servicesHelpers;
    }

    /**
     * Get the list of currently running process.
     */
    private void loadProcessInfo() {
        int processId = android.os.Process.myPid();

        String myProcessName =getApplicationContext().getPackageName();
        Log.i(TAG,"context:"+myProcessName);





    }

    public boolean isThisProcessFermatFrontApp() {
        int pId = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager
                .getRunningAppProcesses();
        for (int idx = 0; idx < procInfos.size(); idx++) {
            ActivityManager.RunningAppProcessInfo process = procInfos.get(idx);
            String processName = process.processName;
            if(pId != process.pid) {
                if (processName.equals("org.fermat")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void runServiceHelpers(final boolean isFermatOpen){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    servicesHelpers = new ServicesHelpers(getInstance().getApplicationContext(), isFermatOpen);
                    servicesHelpers.bindServices();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void setFermatRunning(boolean fermatRunning) {
        Log.i(TAG,"Fermat running");
        this.fermatRunning = fermatRunning;
    }

    public boolean isFermatRunning() {
        return fermatRunning;
    }
}