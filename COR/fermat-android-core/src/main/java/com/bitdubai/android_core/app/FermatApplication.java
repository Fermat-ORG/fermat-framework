package com.bitdubai.android_core.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.apps_manager.FermatAppsManagerService;
import com.bitdubai.android_core.app.common.version_1.notifications.NotificationService;
import com.bitdubai.android_core.app.common.version_1.util.services_helpers.ServicesHelpers;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_api.FermatBroadcastReceiver;
import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_api.FermatIntentFilter;
import com.bitdubai.fermat_core.FermatSystem;

import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Matias Furszyfer on 2016.06.29..
 */
@ReportsCrashes(//formUri = "http://yourserver.com/yourscript",
        mailTo = "matiasfurszyfer@gmail.com",
        customReportContent = {ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)

public class FermatApplication extends MultiDexApplication implements FermatApplicationSession<FermatSystem>, FermatContext {

    private final String TAG = "ApplicationSession";

    private static FermatApplication instance;

    /**
     * FermatFramework
     */
    private FermatFramework fermatFramework;

    public static FermatApplication getInstance() {
        return instance;
    }

    private Thread.UncaughtExceptionHandler defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();


    /**
     * Application session constructor
     */

    public FermatApplication() {
        super();
        instance = this;
    }


    /**
     * Method to get the fermat system
     *
     * @return FermatSystem
     */
    public FermatSystem getFermatSystem() {
        return fermatFramework.getFermatSystem();
    }

    @Override
    public FermatApplicationCaller getApplicationManager() {
        return fermatFramework.getApplicationManager();
    }

    /**
     * Method to change the application state from services or activities
     *
     * @param applicationState is an application state constant from ApplicationSession class
     */

    public void changeApplicationState(int applicationState) {
        fermatFramework.changeApplicationState(applicationState);
    }

    /**
     * Method to get the application state from services or activities
     *
     * @return application state constant from ApplicationSession class
     */

    public int getApplicationState() {
        return fermatFramework.getApplicationState();
    }


    @Override
    public void onTerminate() {
        Log.i(TAG, "onTerminate");
        fermatFramework.onTerminate();
        super.onTerminate();
    }


    @Override
    public void onCreate() {
        fermatFramework.onCreate();
        super.onCreate();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        fermatFramework = FermatFramework.init(this);
        // MultiDex.install(this);
    }


    private void handleUncaughtException(Thread thread, Throwable e) {
        fermatFramework.handleUncaughtException(thread, e);
    }

    public FermatAppsManagerService getAppManager() {
        return getServicesHelpers().getAppManager();
    }

    public NotificationService getNotificationService() {
        return getServicesHelpers().getNotificationService();
    }

    public ServicesHelpers getServicesHelpers() {
        return fermatFramework.getServicesHelpers();
    }

    /**
     * Get the list of currently running process.
     */
    private void loadProcessInfo() {
        int processId = android.os.Process.myPid();

        String myProcessName = getApplicationContext().getPackageName();
        Log.i(TAG, new StringBuilder().append("context:").append(myProcessName).toString());

    }


    public boolean isFermatRunning() {
        if (!fermatFramework.isFermatRunning()) {
            try {
                if (getServicesHelpers().getClientSideBrokerServiceAIDL() != null)
                    fermatFramework.setFermatRunning(getServicesHelpers().getClientSideBrokerServiceAIDL().isFermatBackgroundServiceRunning());
            } catch (Exception e) {
                Log.e(TAG, "isFermatRunning launch exception");
            }
        }
        return fermatFramework.isFermatRunning();
    }

    @Override
    public ClassLoader getBaseClassLoader() {
        return fermatFramework.getBaseClassLoader();
    }

    @Override
    public Object loadObject(String pluginName) {
        return fermatFramework.loadObject(pluginName);
    }

    @Override
    public Object objectToProxyfactory(Object base, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface) {
        return fermatFramework.getLoaderManager().objectToProxyFactory(base, interfaceLoader, interfaces, returnInterface);
    }

//    public ProxyBuilder

    @Override
    public Object loadProxyObject(String moduleName, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface, Object... args) {
        return fermatFramework.getLoaderManager().objectProxyFactory(moduleName, interfaceLoader, interfaces, returnInterface, args);
    }

    @Override
    public void registerReceiver(FermatIntentFilter filter, FermatBroadcastReceiver fermatBroadcastReceiver, String appPublicKey) {
        fermatFramework.registerReceiver(filter, fermatBroadcastReceiver, appPublicKey);
    }

    @Override
    public void unregisterReceiver(FermatBroadcastReceiver fermatBroadcastReceiver, String appPublicKey) {
        fermatFramework.unregisterReceiver(fermatBroadcastReceiver, appPublicKey);
    }


    public ClassLoader getExternalLoader(String name) {
        return fermatFramework.getLoaderManager().getExternalLoader(name);
    }

    public void setFermatRunning(boolean fermatRunning) {
        this.fermatFramework.setFermatRunning(fermatRunning);
    }

    public FermatFramework getFermatFramework() {
        return fermatFramework;
    }
//    @Override
//    public Object loadProxyObject(FermatContext fermatContext, String moduleName, ClassLoader interfaceLoader, Class[] interfaces, Object returnInterface, Object... args) {
//        return loaderManager.objectProxyFactory(moduleName,interfaceLoader,interfaces,returnInterface,args);
//    }
//    public <I> I loadProxyObject(String moduleName,ClassLoader interfaceLoader,Class[] interfaces,I returnInterface) {
//        return (I) loaderManager.objectProxyFactory(moduleName,interfaceLoader,interfaces,returnInterface);
//    }
}
