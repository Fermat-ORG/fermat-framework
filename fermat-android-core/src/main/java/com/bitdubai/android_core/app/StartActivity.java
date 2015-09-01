package com.bitdubai.android_core.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsDataBaseSystem;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsFileSystem;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.AndroidOsLocationSystem;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.CantReportCriticalStartingProblemException;
import com.bitdubai.fermat_api.CantStartPlatformException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.DeviceInfoUtils;
import com.bitdubai.fermat_api.layer.osa_android.LoggerSystemOs;
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.LoggerAddonRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;

import java.util.concurrent.Executors;


/**
 * Created by Matias Furszyfer
 */

/**
 * This Activity is used as a loader, to show something while the Fermat Platform is being initialized.
 * <p>
 * -- Luis.
 */

public class StartActivity extends FragmentActivity implements FermatWorkerCallBack{


    public static final String START_ACTIVITY_INIT = "Init";

    // Indicate if the app was loaded, for not load again the start activity.
    private static boolean WAS_START_ACTIVITY_LOADED = false;


    private AndroidOsFileSystem fileSystemOs;
    private CorePlatformContext platformContext;
    private AndroidOsDataBaseSystem databaseSystemOs;
    private AndroidOsLocationSystem locationSystemOs;
    private LoggerSystemOs loggerSystemOs;

    private Platform platform;


    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            // Indicate if the app was loaded, for not load again the start activity.
            if (WAS_START_ACTIVITY_LOADED) {
                this.fermatInit ();
            }

            try {
                setContentView(R.layout.splash_screen);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                        Toast.LENGTH_LONG).show();
            }

            int applicationState = ((ApplicationSession)getApplication()).getApplicationState();

            if(applicationState==ApplicationSession.STATE_NOT_CREATED) {
                mDialog = new ProgressDialog(this);
                mDialog.setMessage("Please wait...");
                mDialog.show();
                GetTask getTask = new GetTask(this,this);
                getTask.setCallBack(this);
                Executors.newSingleThreadExecutor().execute(getTask);
            }else if (applicationState == ApplicationSession.STATE_STARTED ){
                fermatInit();
            }


    }

    public void handleTouch(View view) {
        fermatInit();
    }

    private boolean fermatInit() {
        Intent intent = new Intent(this, SubAppActivity.class);
        intent.putExtra(START_ACTIVITY_INIT, "init");
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }

    /**
     * implement this function to handle the result object through dynamic array
     *
     * @param result array of native object (handle result field with result[0], result[1],... result[n]
     */
    @Override
    public void onPostExecute(Object... result) {
        mDialog.dismiss();

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
        ex.printStackTrace();
        Toast.makeText(getApplicationContext(), "Application crash, re open the app please",
                Toast.LENGTH_LONG).show();
    }

    class GetTask extends FermatWorker{


        public GetTask(Activity activity,FermatWorkerCallBack fermatWorkerCallBack){
            super(activity,fermatWorkerCallBack);
        }


        /**
         * This function is used for the run method of the fermat background worker
         *
         * @throws Exception any type of exception
         */
        @Override
        protected Object doInBackground() throws Exception {


                Context context = getApplicationContext();

                platform = ((ApplicationSession)getApplication()).getFermatPlatform();


                //set Os Addons in platform
                fileSystemOs = new AndroidOsFileSystem();
                fileSystemOs.setContext(context);
                platform.setFileSystemOs(fileSystemOs);

                databaseSystemOs = new AndroidOsDataBaseSystem();
                databaseSystemOs.setContext(context);
                platform.setDataBaseSystemOs(databaseSystemOs);

                //    locationSystemOs = new AndroidOsLocationSystem();
                //    locationSystemOs.setContext(context);
                //    platform.setLocationSystemOs(locationSystemOs);

                loggerSystemOs = new LoggerAddonRoot();
                try {
                    ((Service) loggerSystemOs).start();
                    platform.setLoggerSystemOs(loggerSystemOs);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
                }

                //execute start platform
                try {

                    platform.start();

                } catch (CantStartPlatformException | CantReportCriticalStartingProblemException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
                }


                /**
                 * get platform object
                 */

                platformContext = platform.getCorePlatformContext();


                PlatformInfoManager platformInfoManager = (PlatformInfoManager) platform.getCorePlatformContext().getAddon(Addons.PLATFORM_INFO);
                setPlatformDeviceInfo(platformInfoManager);

                return true;
        }
    }

    private void setPlatformDeviceInfo(PlatformInfoManager platformInfoManager){
        try {
            PlatformInfo platformInfo = platformInfoManager.getPlatformInfo();
            platformInfo.setScreenSize(getScreenSize());
            platformInfoManager.setPlatformInfo(platformInfo);
        } catch (CantLoadPlatformInformationException e) {
            e.printStackTrace();
        }
    }

    private ScreenSize getScreenSize(){

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return DeviceInfoUtils.toScreenSize(dpHeight,dpWidth);

    }
}
