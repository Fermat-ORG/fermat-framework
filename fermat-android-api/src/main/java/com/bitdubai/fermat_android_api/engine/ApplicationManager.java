package com.bitdubai.fermat_android_api.engine;

import android.content.Context;
import android.content.Intent;

import com.bitdubai.fermat_android_api.constants.ApplicationConstants;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2016.05.19..
 */
public class ApplicationManager {


    private WeakReference<Context> context;

    public ApplicationManager(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    public void goHome(){
        try {
            Intent intent;
            intent = new Intent();
            intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, "main_desktop");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setAction("org.fermat.APP_LAUNCHER");
            context.get().sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeApp(String appPublicKey) throws Exception {
        try{
            Intent intent;
            intent = new Intent();
            intent.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY, appPublicKey);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            intent.setAction("org.fermat.APP_LAUNCHER");
            context.get().sendBroadcast(intent);
        }catch (Exception e){
            throw new Exception("App public key not exist");
        }
    }

}
