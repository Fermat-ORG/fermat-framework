package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.structure.AIDLObject;
import com.bitdubai.android_core.app.common.version_1.communication.structure.test.MyModuleObject;

/**
 * Created by mati on 2016.04.18..
 */
public class CommunicationServerService extends Service {

    private static String TAG = "CommunicationServerService";


    private final IServerBrokerService.Stub mBinder = new IServerBrokerService.Stub() {


        @Override
        public AIDLObject invoqueModuleMethod(String platformCode, String layerCode, String pluginsCode, String developerCode, String version, String method, AIDLObject[] parameters) throws RemoteException {
            Log.i(TAG,"invoqueModuleMethod");
            Log.i(TAG,platformCode);
            Log.i(TAG,layerCode);
            Log.i(TAG,pluginsCode);
            Log.i(TAG,version);
            Log.i(TAG,method);
            Log.i(TAG,"Parameters");
            for (AIDLObject parameter : parameters) {
                Log.i(TAG, parameter.toString());
            }

            MyModuleObject myModuleObject = new MyModuleObject("1","mati",22);

            return myModuleObject;
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"OnCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
