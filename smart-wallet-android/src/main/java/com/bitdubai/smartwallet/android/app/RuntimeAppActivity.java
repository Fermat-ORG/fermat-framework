package com.bitdubai.smartwallet.android.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;

import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.AndroidOsAddonRoot;
import android.content.Context;

import com.bitdubai.wallet_platform_api.CantStartPlatformException;
import com.bitdubai.wallet_platform_api.layer._11_middleware.app_runtime.*;
import com.bitdubai.wallet_platform_api.layer._11_middleware.app_runtime.AppRuntimeManager;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Plugins;
import com.bitdubai.wallet_platform_core.Platform;

import com.bitdubai.smartwallet.R;
import com.bitdubai.wallet_platform_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.structure.*;


import com.bitdubai.wallet_platform_core.CorePlatformContext;
import com.bitdubai.wallet_platform_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.structure.RuntimeSubApp;

import java.util.Map;
import java.util.Iterator;
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

            AppRuntimeManager appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.APP_RUNTIME_MIDDLEWARE);

           App app = appRuntimeMiddleware.getLastApp();

            Map<SubApps, SubApp> subapps = app.getSubApps();

            Iterator<Map.Entry<SubApps, SubApp>>  entries = subapps.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<SubApps, SubApp> thisEntry =  entries.next();
                RuntimeSubApp subApp = (RuntimeSubApp)thisEntry.getValue();
                SubApps type = subApp.getType();
                Map<Activities, com.bitdubai.wallet_platform_api.layer._11_middleware.app_runtime.Activity> activities = subApp.getActivities();
                Map<Wallets, Wallet> wallets = subApp.getWallets();
                Iterator<Map.Entry<Activities, com.bitdubai.wallet_platform_api.layer._11_middleware.app_runtime.Activity>>  eactivities = activities.entrySet().iterator();

                while (eactivities.hasNext()) {
                    Map.Entry<Activities, com.bitdubai.wallet_platform_api.layer._11_middleware.app_runtime.Activity> activityEntry =  eactivities.next();
                    try {
                        RuntimeActivity activity = (RuntimeActivity)activityEntry.getValue();
                    }
                    catch (Exception e){

                    }


                }

            }



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
