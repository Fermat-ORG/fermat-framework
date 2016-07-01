package com.bitdubai.android_core.app.common.version_1.helpers;

import android.content.Context;
import android.content.Intent;

import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.android_core.app.common.version_1.recents.RecentsActivity;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2016.05.19..
 */
public class ApplicationsHelper implements FermatApplicationCaller {

    public static final int TASK_MANAGER_STACK = 100;

    private WeakReference<Context> context;

    public ApplicationsHelper(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    public void openFermatHome(){
        try {
            Intent intent;
            intent = new Intent();
            intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, "main_desktop");
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setAction("org.fermat.APP_LAUNCHER");
            context.get().sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFermatApp(String appPublicKey) throws Exception {
        try{
            Intent intent;
            intent = new Intent();
            intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, appPublicKey);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            intent.setAction("org.fermat.APP_LAUNCHER");
            context.get().sendBroadcast(intent);
        }catch (Exception e){
            throw new Exception("App public key not exist");
        }
    }

    public void openRecentsScreen(){
        Intent resultIntent = new Intent(context.get(),RecentsActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.putExtra(ApplicationConstants.RECENT_APPS, FermatApplication.getInstance().getAppManager().getRecentsAppsStack().toArray());
        context.get().startActivity(resultIntent);
    }

}
