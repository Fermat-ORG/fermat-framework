package com.bitdubai.android_core.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.android_core.app.common.version_1.ApplicationConstants;
import com.bitdubai.android_core.app.common.version_1.util.AndroidCoreUtils;
import com.bitdubai.android_core.app.common.version_1.util.task.GetTaskV2;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
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
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_osa_android_core.OSAPlatform;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantSetPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;


/**
 * Created by Matias Furszyfer
 */

/**
 * This Activity is used as a loader, to show something while the Fermat Platform is being initialized.
 * <p>
 * -- Luis.
 */

public class StartActivity extends AppCompatActivity implements  FermatWorkerCallBack /**,ServiceCallback */{



    // Indicate if the app was loaded, for not load again the start activity.
    private static boolean WAS_START_ACTIVITY_LOADED = false;

    private ImageView imageView_fermat;

    Animation animation1;
    Animation animation2;

    /**
     * Service
     */
    private BoundService mBoundService;
    boolean mServiceConnected = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FermatSystem fermatSystem =ApplicationSession.getInstance().getFermatSystem();

        try {
            AndroidCoreUtils androidCoreUtils = AndroidCoreUtils.getInstance();
//            AndroidCoreUtils.getInstance().setContextAndResume(this);
            fermatSystem.start(this.getApplicationContext(), new OSAPlatform(androidCoreUtils));
        } catch (FermatException e) {

            System.err.println(e.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

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

            int applicationState = ApplicationSession.getInstance().getApplicationState();

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





                GetTaskV2 getTask = new GetTaskV2(this,this);
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
        //Intent intent = new Intent(this, SubAppActivity.class);
        WAS_START_ACTIVITY_LOADED = true;
        Intent intent = new Intent(this, DesktopActivity.class);
        intent.putExtra(ApplicationConstants.START_ACTIVITY_INIT, "init");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
        finish();
        return true;
    }




    /**
     * Dispatch onStop() to all fragments.  Ensure all loaders are stopped.
     */


    @Override
    protected void onStart() {
        super.onStart();
//        Intent intent = new Intent(this, BoundService.class);
//        intent.putExtra(BoundService.LOG_TAG,"Activity 1");
//        startService(intent);
//        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

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

    @Override
    public void onErrorOccurred(Exception ex) {
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


    /**
     * Service
     */
//    private ServiceConnection mServiceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mServiceConnected = false;
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBoundService = ((BoundService.LocalBinder)service).getService();
//            mBoundService.registerCallback(registerCallback());
//            mServiceConnected = true;
//            Log.i("APP","service connected");
//        }
//    };
//
//    private ServiceCallback registerCallback(){
//        return this;
//    }
//
//
//    @Override
//    public void callback(int option) {
//        if (option==1){
//            fermatInit();
//        }else if(option==2){
//            Toast.makeText(this,"Ooooops, an error occur, please re open the app",Toast.LENGTH_SHORT).show();
//        }
//    }
}
