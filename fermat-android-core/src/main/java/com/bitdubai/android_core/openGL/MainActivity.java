package com.bitdubai.android_core.openGL;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ciencias on 6/26/15.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Check if the device supports OpenGL
         */

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = activityManager.getDeviceConfigurationInfo();

        boolean supportES2 = (info.reqGlEsVersion >= 0x20000);
        if (supportES2) {

            MainRenderer mainRenderer = new MainRenderer();

            MainSurfaceView mainSurfaceView = new MainSurfaceView(this);
            mainSurfaceView.setEGLContextClientVersion(2);
            mainSurfaceView.setRenderer(mainRenderer);
        }
        else {
            Log.e("Openg GL not Supported", "Your device does not support the required version of Open GL");
        }

    }

}
