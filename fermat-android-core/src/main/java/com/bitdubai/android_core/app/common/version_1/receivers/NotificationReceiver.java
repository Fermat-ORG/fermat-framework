package com.bitdubai.android_core.app.common.version_1.receivers;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.05.08..
 */
//todo: pasar todo a que se use el FermatBundle y no el code
public class NotificationReceiver extends BroadcastReceiver {

    public static final String INTENT_NAME = "notification_receiver";
    private static final String TAG = "NotificationReceiver";
    private final WeakReference<ApplicationSession> weakSession;


    public NotificationReceiver(ApplicationSession applicationSession) {
        this.weakSession = new WeakReference<ApplicationSession>(applicationSession);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String appPublicKey = intent.getStringExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY);
        String code = intent.getStringExtra(ApplicationConstants.INTENT_EXTRA_DATA);
        FermatBundle data = (FermatBundle) intent.getSerializableExtra(ApplicationConstants.INTENT_EXTRA_DATA_BUNDLE);
        try {
            weakSession.get().getNotificationService().notificate(appPublicKey, code);
        }catch (Exception e){
            Log.e(TAG,"Process name: "+getCurrentProcess().processName);
            e.printStackTrace();
        }
    }


    public ActivityManager.RunningAppProcessInfo getCurrentProcess() {
        int pId = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) weakSession.get()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager
                .getRunningAppProcesses();
        for (int idx = 0; idx < procInfos.size(); idx++) {
            ActivityManager.RunningAppProcessInfo process = procInfos.get(idx);
            String processName = process.processName;
            if(pId == process.pid) {
                return process;
            }
        }
        return null;
    }
}
