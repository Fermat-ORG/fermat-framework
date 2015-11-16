package com.bitdubai.android_core.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.CantReportCriticalStartingProblemException;
import com.bitdubai.fermat_api.CantStartPlatformException;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_core.CorePlatformContext;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_core.Platform;
import com.bitdubai.fermat_osa_android_core.OSAPlatform;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.exceptions.CantSetPlatformInformationException;

import java.util.ArrayList;


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
    public static final String ACTIVE_PLATFORMS = "active";

    // Indicate if the app was loaded, for not load again the start activity.
    private static boolean WAS_START_ACTIVITY_LOADED = false;

    ArrayList<Platforms> activePlatforms;

    private ProgressDialog mDialog;

    private ImageView imageView_fermat;


    Animation animation1;
    Animation animation2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            // Indicate if the app was loaded, for not load again the start activity.
            if (WAS_START_ACTIVITY_LOADED) {
                this.fermatInit ();
            }

            try {
                setContentView(R.layout.splash_screen);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) getWindow().setStatusBarColor(Color.TRANSPARENT);

                imageView_fermat = (ImageView) findViewById(R.id.imageView_fermat);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Oooops! recovering from system error",
                        Toast.LENGTH_LONG).show();
            }

            int applicationState = ((ApplicationSession)getApplication()).getApplicationState();

            if(applicationState==ApplicationSession.STATE_NOT_CREATED) {
//                mDialog = new ProgressDialog(this);
//                mDialog.setMessage("Please wait...");
//                mDialog.setCancelable(false);
//////                    mDialog.show();


                animation1 = new AlphaAnimation(0.0f, 1.0f);
                animation1.setDuration(1000);
                //animation1.setStartOffset(5000);

                //animation1 AnimationListener
                animation1.setAnimationListener(new Animation.AnimationListener(){

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        // start animation2 when animation1 ends (continue)
                        imageView_fermat.startAnimation(animation2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationStart(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                });

                animation2 = new AlphaAnimation(1.0f, 0.0f);
                animation2.setDuration(1000);
                //animation2.setStartOffset(5000);

                //animation2 AnimationListener
                animation2.setAnimationListener(new Animation.AnimationListener(){

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        // start animation1 when animation2 ends (repeat)
                        imageView_fermat.startAnimation(animation1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationStart(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                });

                imageView_fermat.startAnimation(animation1);



                GetTask getTask = new GetTask(this,this);
                getTask.setCallBack(this);
                getTask.execute();
            }else if (applicationState == ApplicationSession.STATE_STARTED ){
                fermatInit();
            }


    }

    public void handleTouch(View view) {
        fermatInit();
    }

    private boolean fermatInit() {
        Intent intent = new Intent(this, SubAppActivity.class);
        intent.putExtra(ACTIVE_PLATFORMS,activePlatforms);
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
        //mDialog.dismiss();

        imageView_fermat.clearAnimation();

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

            final FermatSystem fermatSystem =((ApplicationSession) getApplication()).getFermatSystem();

            try {
                Context context = getApplicationContext();
                fermatSystem.start(context, new OSAPlatform());

            } catch (FermatException e) {
                System.err.println(e.toString());
                System.out.println(e.getPossibleReason());
                System.out.println(e.getFormattedContext());
                System.out.println(e.getFormattedTrace());
            }

            try {
                fermatSystem.startAllRegisteredPlatforms();

            } catch (FermatException e) {
                System.err.println(e.toString());
                System.out.println(e.getPossibleReason());
                System.out.println(e.getFormattedContext());
                System.out.println(e.getFormattedTrace());
            }

            return true;
        }
    }

    private void setPlatformDeviceInfo(PlatformInfoManager platformInfoManager){
        try {
            PlatformInfo platformInfo = platformInfoManager.getPlatformInfo();
            activePlatforms = loadActivePlatforms(platformInfo);
            platformInfo.setScreenSize(getScreenSize());
            platformInfoManager.setPlatformInfo(platformInfo);
        } catch(CantLoadPlatformInformationException |
                CantSetPlatformInformationException  e) {
            e.printStackTrace();
        }
    }

    private ScreenSize getScreenSize(){

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return DeviceInfoUtils.toScreenSize(dpHeight,dpWidth);

    }

    private ArrayList<Platforms> loadActivePlatforms(PlatformInfo platformInfo){
        ArrayList<Platforms> list;
        //if(platformInfo.getActivePlatforms().size()==0){
           list = platformInfo.addActivePlatform(Platforms.CRYPTO_CURRENCY_PLATFORM);
        //}else{
         //  list = platformInfo.getActivePlatforms();
        //}
        return list;
    }
}
