package com.bitdubai.android_core.app.common.version_1.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/**
 * Created by Matias Furszyfer
 */

public class BootupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "App started", Toast.LENGTH_LONG).show();
//---start the main activity of our app---
//        Intent i = new Intent(context, StartActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

        // Explicitly start My Service
//        Intent i = new Intent(context, BoundService.class);
        // TODO Add extras if required.
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startService(intent);
    }
}