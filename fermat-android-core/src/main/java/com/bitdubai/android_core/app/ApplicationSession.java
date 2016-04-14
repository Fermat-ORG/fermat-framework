package com.bitdubai.android_core.app;


import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.ClientSystemBrokerService;
import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;
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

/**
 * Matias Furszyfer
 */


@ReportsCrashes(//formUri = "http://yourserver.com/yourscript",
        mailTo = "matiasfurszyfer@gmail.com",
        customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)

public class ApplicationSession extends MultiDexApplication implements Serializable,FermatApplicationSession {


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


    public static ApplicationSession getInstance(){
        return instance;
    }

    private Thread.UncaughtExceptionHandler defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();


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
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                servicesHelpers = new ServicesHelpers(getInstance().getApplicationContext());
                servicesHelpers.bindServices();


            }
        }).start();

//        new ANRWatchDog().start();

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

    public ClientSystemBrokerService getClientSideBrokerService(){
        return getServicesHelpers().getClientSideBrokerService();
    }

    public ServicesHelpers getServicesHelpers() {
        return servicesHelpers;
    }
}