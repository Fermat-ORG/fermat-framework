package com.bitdubai.android_core.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mati on 2016.04.22..
 */
public class FermatSystemRunningReceiver extends BroadcastReceiver{



    public FermatSystemRunningReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ApplicationSession.getInstance().setFermatRunning(true);
    }
}
