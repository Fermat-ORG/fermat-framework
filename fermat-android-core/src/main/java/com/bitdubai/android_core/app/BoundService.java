package com.bitdubai.android_core.app;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.util.AndroidCoreUtils;
import com.bitdubai.android_core.app.common.version_1.util.task.GetTask;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetAddonException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.DeviceInfoUtils;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_osa_android_core.OSAPlatform;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantSetPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;

import java.util.concurrent.ExecutorService;

/**
 * Created by Matias Furszyfer on 2016.02.03..
 */
//TODO: tengo que cambiar el nombre de este servicio para que sea el responsable de ser llamado en el bootup del sistema y active el servicio de Fermat y todos los procesos asociados que se requieran
public class BoundService extends Service implements FermatWorkerCallBack {

    public static String LOG_TAG = "BoundService";
    private static final int NTHREDS = 3;
    private ExecutorService executorService;
    // Indicate if the app was loaded, for not load again the start activity.
    private static boolean WAS_START_ACTIVITY_LOADED = false;
    private final IBinder mBinder = new LocalBinder();



    public class LocalBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {

            final FermatSystem fermatSystem = ApplicationSession.getInstance().getFermatSystem();
            AndroidCoreUtils androidCoreUtils = AndroidCoreUtils.getInstance();
            //androidCoreUtils.setContextAndResume(this);
            fermatSystem.start(this.getApplicationContext(), new OSAPlatform(androidCoreUtils));
            // Indicate if the app was loaded, for not load again the start activity.
            if (WAS_START_ACTIVITY_LOADED) {
                this.fermatInit();
            }

            GetTask getTask = new GetTask(this, this);
            getTask.setCallBack(this);
            getTask.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind: " + intent.getExtras().get(LOG_TAG));
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
    }

    private boolean fermatInit() {
        //TODO: aca mando el broadcast

        return true;
    }


    /**
     * implement this function to handle the result object through dynamic array
     *
     * @param result array of native object (handle result field with result[0], result[1],... result[n]
     */
    @Override
    public void onPostExecute(Object... result) {

        try {

            final FermatSystem fermatSystem = ApplicationSession.getInstance().getFermatSystem();

            PlatformInfoManager platformInfoManager = (PlatformInfoManager) fermatSystem.startAndGetAddon(
                    new AddonVersionReference(
                            Platforms.PLUG_INS_PLATFORM,
                            Layers.PLATFORM_SERVICE,
                            Addons.PLATFORM_INFO,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );

            setPlatformDeviceInfo(platformInfoManager);
        } catch (CantGetAddonException | VersionNotFoundException e) {

            System.out.println(e.toString());
        }

        // Indicate that app was loaded.
        WAS_START_ACTIVITY_LOADED = true;
        fermatInit();
    }

    /**
     * Implement this function to handle errors during the execution of any fermat worker instance
     *
     * @param ex Throwable object
     */
    @Override
    public void onErrorOccurred(Exception ex) {
        //mDialog.dismiss();
        ex.printStackTrace();

        Toast.makeText(getApplicationContext(), "Application crash, re open the app please",
                Toast.LENGTH_LONG).show();
    }

    private void setPlatformDeviceInfo(PlatformInfoManager platformInfoManager){
        try {
            PlatformInfo platformInfo = platformInfoManager.getPlatformInfo();
            platformInfo.setScreenSize(getScreenSize());
            platformInfoManager.setPlatformInfo(platformInfo);
        } catch(
                CantSetPlatformInformationException | com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantLoadPlatformInformationException e) {
            e.printStackTrace();
        }
    }

    private ScreenSize getScreenSize(){

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return DeviceInfoUtils.toScreenSize(dpHeight, dpWidth);

    }





}
