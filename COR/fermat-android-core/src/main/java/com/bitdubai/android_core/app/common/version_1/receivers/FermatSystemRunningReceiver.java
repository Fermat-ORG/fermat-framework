package com.bitdubai.android_core.app.common.version_1.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bitdubai.android_core.app.FermatApplication;

/**
 * Created by Matias Furszyfer on 2016.04.22..
 */
public class FermatSystemRunningReceiver extends BroadcastReceiver {


    private static final String TAG = "FermatSystemReceiver";

    public FermatSystemRunningReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Receiving fermat running");
        FermatApplication.getInstance().setFermatRunning(true);
    }
}
