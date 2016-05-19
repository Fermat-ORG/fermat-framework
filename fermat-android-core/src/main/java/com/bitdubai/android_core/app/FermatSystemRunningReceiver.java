package com.bitdubai.android_core.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mati on 2016.04.22..
 */
public class FermatSystemRunningReceiver extends BroadcastReceiver{


    private static final String TAG = "FermatSystemRunningReceiver";

    public FermatSystemRunningReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Receiving fermat running");
        ApplicationSession.getInstance().setFermatRunning(true);
    }
}
