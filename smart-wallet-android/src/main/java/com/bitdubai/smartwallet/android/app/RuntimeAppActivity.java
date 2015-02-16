package com.bitdubai.smartwallet.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;

import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.AndroidOsAddonRoot;
import android.content.Context;

import com.bitdubai.wallet_platform_api.CantStartPlatformException;
import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.*;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.AppRuntime;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Plugins;
import com.bitdubai.wallet_platform_core.Platform;

import com.bitdubai.smartwallet.R;


import com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.AppRuntimePluginRoot;

import com.bitdubai.wallet_platform_core.CorePlatformContext;
/**
 * Created by toshiba on 16/02/2015.
 */
public class RuntimeAppActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);

        try {
            //init runtime app
            Platform platform = MyApplication.getPlatform();
            Context mContext = this.getApplicationContext();

            AndroidOsAddonRoot Os = new AndroidOsAddonRoot();

            Os.setContext(this);
            platform.setOs(Os);
            platform.start();

            CorePlatformContext platformContext = platform.getCorePlatformContext();

            AppRuntime appRuntimeMiddleware =  (AppRuntime)platformContext.getPlugin(Plugins.APP_RUNTIME_MIDDLEWARE);

           Apps apps = appRuntimeMiddleware.getLastApp();


        }
        catch (CantStartPlatformException e) {
            System.err.println("CantStartPlatformException: " + e.getMessage());

        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
