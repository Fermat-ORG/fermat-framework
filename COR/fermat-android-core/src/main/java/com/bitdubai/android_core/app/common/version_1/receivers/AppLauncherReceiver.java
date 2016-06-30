package com.bitdubai.android_core.app.common.version_1.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bitdubai.android_core.app.AppActivity;
import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.android_core.app.DesktopActivity;
import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;

/**
 * Created by Matias Furszyfer on 2016.03.05..
 *
 * This class acts like a router to open every app in fermat and put it in the front of the task stack
 */
public class AppLauncherReceiver extends BroadcastReceiver {


    private static final String TAG = "AppLauncherReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"onReceive");
        String appPublicKey = intent.getStringExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY);
        FermatApp fermatApp = null;
        try {
             fermatApp = FermatApplication.getInstance().getAppManager().getApp(appPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fermatApp==null) throw new RuntimeException("Error, app is not available. Please remove data in your app if your are testing or you are a developer. Report this issue to furszy");
        FermatAppType fermatAppType = fermatApp.getAppType(); //(FermatAppType) intent.getSerializableExtra(ApplicationConstants.INTENT_APP_TYPE);
        Class<?> clazz = null;
        switch (fermatAppType){
            case WALLET:
            case SUB_APP:
                clazz = AppActivity.class;
                break;
            case DESKTOP:
                clazz = DesktopActivity.class;
        }
        Intent data = new Intent(context,clazz);
        data.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, appPublicKey);
        data.putExtra(ApplicationConstants.INTENT_APP_TYPE, fermatAppType);
        data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(data);
        if(context instanceof FermatActivity){
            ((FermatActivity) context).finish();
        }
    }
}
