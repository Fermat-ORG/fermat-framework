package com.bitdubai.android_core.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;
import com.bitdubai.android_core.app.common.version_1.helpers.ApplicationsHelper;
import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;
import com.bitdubai.android_core.app.common.version_1.receivers.NotificationReceiver;
import com.bitdubai.android_core.app.common.version_1.util.mail.YourOwnSender;
import com.bitdubai.android_core.app.common.version_1.util.services_helpers.ServicesHelpers;
import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_core.FermatSystem;
import com.github.anrwatchdog.ANRWatchDog;
import com.mati.fermat_osa_addon_android_loader.LoaderManager;
import com.mati.fermat_osa_addon_android_loader.Smith;

import org.acra.ACRA;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.06.29..
 */
public class FermatFramework implements FermatApplicationSession<FermatSystem>,FermatContext {

    private static final String TAG = "FermatFramework";

    /**
     * Application states
     */
    public static final int STATE_NOT_CREATED=0;
    public static final int STATE_STARTED=1;
    public static final int STATE_STARTED_DESKTOP=2;


    /**
     * ApplicationClass
     */
    private Application application;

    /**
     *  Fermat platform
     */

    private FermatSystem fermatSystem;

    /**
     *  Application state
     */
    public static int applicationState=STATE_NOT_CREATED;
    private boolean fermatRunning;
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
     * Base loader
     */
    private ClassLoader mBaseClassLoader;

    private LoaderManager loaderManager;

    public static FermatFramework init(Application application){
        return new FermatFramework(application);
    }


    /**
     *  Application session constructor
     */

    private FermatFramework(Application application) {
        super();
        this.application = application;
        fermatSystem = FermatSystem.getInstance();
        fermatSystem.setFermatContext(this);
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

    @Override
    public FermatApplicationCaller getApplicationManager() {
        return new ApplicationsHelper(application);
    }

    @Override
    public Context getApplicationContext() {
        return application.getApplicationContext();
    }

    /**
     *  Method to change the application state from services or activities
     *
     * @param applicationState  is an application state constant from FermatFramework class
     */

    public void changeApplicationState(int applicationState){
        FermatFramework.applicationState =applicationState;
    }

    /**
     * Method to get the application state from services or activities
     *
     * @return application state constant from FermatFramework class
     */

    public int getApplicationState(){
        return applicationState;
    }

    /**
     * this method have to be called in the onCreate of the application
     */

    public void onCreate() {
        ACRA.init(application);
        YourOwnSender yourSender = new YourOwnSender(application);
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

        try {
            Log.d(TAG, "Starting app");
            //loading the main class loader
            Context mBase = new Smith<Context>(application, "mBase").get();
            Object mPackageInfo = new Smith<Object>(mBase, "mPackageInfo")
                    .get();
            Smith<ClassLoader> sClassLoader = new Smith<ClassLoader>(
                    mPackageInfo, "mClassLoader");
            mBaseClassLoader = sClassLoader.get();
        }catch (Exception e){
            e.printStackTrace();
        }

        loaderManager = new LoaderManager<>(this);

//        loadProcessInfo();

        boolean isThisProcessFermatFrontApp = isThisProcessFermatFrontApp();
        runServiceHelpers(isThisProcessFermatFrontApp);
        if(!isThisProcessFermatFrontApp){
            notificationReceiver = new NotificationReceiver(this);
            IntentFilter intentFilter = new IntentFilter(NotificationReceiver.INTENT_NAME);
            application.getApplicationContext().registerReceiver(notificationReceiver, intentFilter);
        }

//        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("org.fermat.SYSTEM_RUNNING");
//        bManager.registerReceiver(new FermatSystemRunningReceiver(this), intentFilter);

        new ANRWatchDog().start();

    }


    void handleUncaughtException(Thread thread, Throwable e) {
        Toast.makeText(application.getApplicationContext(), "Sorry, The app is not working", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(application.getApplicationContext(),StartActivity.class);
        application.startActivity(intent);
    }

    public FermatAppsManagerService getAppManager(){
        return getServicesHelpers().getAppManager();
    }

    public NotificationService getNotificationService(){
        return getServicesHelpers().getNotificationService();
    }

    public ServicesHelpers getServicesHelpers() {
        return servicesHelpers;
    }

    /**
     * Get the list of currently running process.
     */
    private void loadProcessInfo() {
        int processId = android.os.Process.myPid();

        String myProcessName = application.getPackageName();
        Log.i(TAG,"context:"+myProcessName);

    }

    public boolean isThisProcessFermatFrontApp() {
        int pId = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) application
                .getSystemService(application.ACTIVITY_SERVICE);
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
                    servicesHelpers = new ServicesHelpers(application.getApplicationContext(), isFermatOpen);
                    servicesHelpers.bindServices();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public synchronized void setFermatRunning(boolean fermatRunning) {
        Log.i(TAG,"Fermat running: "+fermatRunning);
        this.fermatRunning = fermatRunning;
    }

    public boolean isFermatRunning() {
        if(!fermatRunning){
            try {
                if(getServicesHelpers().getClientSideBrokerServiceAIDL()!=null)
                    fermatRunning = getServicesHelpers().getClientSideBrokerServiceAIDL().isFermatBackgroundServiceRunning();
            }catch (Exception e){
                Log.e(TAG,"isFermatRunning launch exception");
            }
        }
        return fermatRunning;
    }

    @Override
    public ClassLoader getBaseClassLoader() {
        return mBaseClassLoader;
    }

    @Override
    public Object loadObject(String pluginName) {
        return loaderManager.load(pluginName);
    }

    @Override
    public Object objectToProxyfactory(Object base, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface) {
        return loaderManager.objectToProxyFactory(base, interfaceLoader, interfaces, returnInterface);
    }

//    public ProxyBuilder

    @Override
    public Object loadProxyObject(String moduleName,ClassLoader interfaceLoader,Class[] interfaces,Object returnInterface,Object... args) {
        return loaderManager.objectProxyFactory(moduleName,interfaceLoader,interfaces,returnInterface,args);
    }

    public ClassLoader getExternalLoader(String name){
        return loaderManager.getExternalLoader(name);
    }

    public Application getApplication() {
        return application;
    }

    public void onTerminate() {
        servicesHelpers.unbindServices();
        application.getApplicationContext().unregisterReceiver(notificationReceiver);
    }

    public LoaderManager getLoaderManager() {
        return loaderManager;
    }
}