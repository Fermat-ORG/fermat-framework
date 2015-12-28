package com.bitdubai.android_core.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.widget.Toast;

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
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantSetPlatformInformationException;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

public class LoaderService extends Service {


    public static final String START_ACTIVITY_INIT = "Init";

    // Indicate if the app was loaded, for not load again the start activity.
    private static boolean WAS_START_ACTIVITY_LOADED = false;

    //private ProgressDialog mDialog;

    @Override
    public void onCreate(){

        Toast.makeText(getApplicationContext(), "Fermat start on create", Toast.LENGTH_LONG).show();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //startBackgroundTask(intent, startId);
        Toast.makeText(getApplicationContext(), "Fermat start on start", Toast.LENGTH_LONG).show();
        new GetTask(this).execute();

        return Service.START_STICKY;
    }

    class GetTask extends AsyncTask<Object, Void, Boolean> {
        Context context;

        GetTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //mDialog = new ProgressDialog(context);
            //mDialog.setMessage("Please wait...");
            //mDialog.show();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            //init runtime app

            try {

                final FermatSystem fermatSystem = ((ApplicationSession)getApplication()).getFermatSystem();

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


            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            //mDialog.dismiss();

            // Indicate that app was loaded.
            Toast.makeText(context, "Fermat start", Toast.LENGTH_LONG).show();
            WAS_START_ACTIVITY_LOADED = true;
            ((ApplicationSession)getApplication()).changeApplicationState(ApplicationSession.STATE_STARTED);
            fermatInit();

            //fermatInit();
        }
    }


    private boolean fermatInit() {
        Intent intent = new Intent(this, SubAppActivity.class);
        intent.putExtra(START_ACTIVITY_INIT, "init");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return true;
    }

    private void setPlatformDeviceInfo(PlatformInfoManager platformInfoManager){
        try {
            PlatformInfo platformInfo = platformInfoManager.getPlatformInfo();
            platformInfo.setScreenSize(getScreenSize());
            platformInfoManager.setPlatformInfo(platformInfo);
        } catch(CantSetPlatformInformationException  |
                CantLoadPlatformInformationException e) {
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
