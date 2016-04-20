package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by mati on 2016.04.18..
 */
public class CommunicationServerService extends Service {

    private static String TAG = "CommunicationServerService";




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
