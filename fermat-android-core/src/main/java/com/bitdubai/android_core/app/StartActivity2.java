package com.bitdubai.android_core.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat.R;


/**
 * Created by Matias Furszyfer
 */

/**
 * This Activity is used as a loader, to show something while the Fermat Platform is being initialized.
 * <p>
 * -- Luis.
 */

public class StartActivity2 extends AppCompatActivity  {



    // Indicate if the app was loaded, for not load again the start activity.
    private static boolean WAS_START_ACTIVITY_LOADED = false;

    private ImageView imageView_fermat;

    Animation animation1;
    Animation animation2;

    /**
     * Service
     */
//    private BackgroundService mBoundService;
    boolean mServiceConnected = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        final FermatSystem fermatSystem =ApplicationSession.getInstance().getFermatSystem();
//
//        try {
//            AndroidCoreUtils androidCoreUtils = AndroidCoreUtils.getInstance();
////            androidCoreUtils.setContextAndResume(this);
//            fermatSystem.start(this.getApplicationContext(), new OSAPlatform(androidCoreUtils));
//        } catch (FermatException e) {
//
//            System.err.println(e.toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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
                animation2.setAnimationListener(new Animation.AnimationListener() {

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
//        try {
//            if (mServiceConnected) {
//                mBoundService.unregisterCallback(this);
//                unbindService(mServiceConnection);
//                mServiceConnected = false;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        startActivity(intent);
        finish();
        return true;
    }

    /**
     * Dispatch onStop() to all fragments.  Ensure all loaders are stopped.
     */
    @Override
    protected void onStop() {
        try {
            super.onStop();

            /**
             * Service
             */
//            if (mServiceConnected) {
//                mBoundService.unregisterCallback(this);
//                unbindService(mServiceConnection);
//                mServiceConnected = false;
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("StartActivity2","starting service");
//        Intent intent = new Intent(this, BackgroundService.class);
        //intent.putExtra(BoundService.APP,"Activity 1");
//        startService(intent);
//        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }



    /**
     * Service
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceConnected = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBoundService = ((BackgroundService.LocalBinder)service).getService();
//            mBoundService.registerCallback(registerCallback());
//            mServiceConnected = true;
            Log.i("APP", "service connected");
        }
    };


}
