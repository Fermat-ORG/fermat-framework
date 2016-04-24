package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl.CommunicationServerService;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl.IServerBrokerService;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Matias Furszyfer on 2016.03.31..
 */
public class ClientSystemBrokerServiceAIDL extends Service implements ClientBrokerService {

    private static final String TAG = "ClientBrokerService";
    private static final String KEY = "s";
    private static final int THREADS_NUM = 3;

    private final IBinder localBinder = new LocalBinder();

    private ExecutorService poolExecutor;

    private ProxyFactory proxyFactory;

    public ClientSystemBrokerServiceAIDL() {
        this.proxyFactory = new ProxyFactory();
    }

    public Object sendMessage(PluginVersionReference pluginVersionReference,String responseStr, Object proxy, Method method, Object[] args) {
        FermatModuleObjectWrapper[] parameters = null;
        if(args!=null) {
            parameters = new FermatModuleObjectWrapper[args.length];

            for (int i = 0; i < args.length; i++) {
                try {
                    FermatModuleObjectWrapper fermatModuleObjectWrapper = new FermatModuleObjectWrapper((Serializable) args[i]);
                    parameters[i] = fermatModuleObjectWrapper;
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Objeto no implementa el FermatModuleObject");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            parameters = new FermatModuleObjectWrapper[0];
        }

        try {
            FermatModuleObjectWrapper fermatModuleObjectWrapper = iServerBrokerService.invoqueModuleMethod(
                    pluginVersionReference.getPlatform().getCode(),
                    pluginVersionReference.getLayers().getCode(),
                    pluginVersionReference.getPlugins().getCode(),
                    pluginVersionReference.getDeveloper().getCode(),
                    pluginVersionReference.getVersion().toString(),
                    method.getName(),
                    parameters);

            if(fermatModuleObjectWrapper!=null) {
                Log.i(TAG, "Object retuned: " + fermatModuleObjectWrapper.toString());
            }else{
                Log.i(TAG, "Object retuned: null");
            }
            return (fermatModuleObjectWrapper!=null)?fermatModuleObjectWrapper.getObject():null;

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isFermatBackgroundServiceRunning() {
        try {
            return iServerBrokerService.isFermatSystemRunning();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public class LocalBinder extends Binder {

        public ClientSystemBrokerServiceAIDL getService(){
            return ClientSystemBrokerServiceAIDL.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        proxyFactory = new ProxyFactory();
        poolExecutor = Executors.newFixedThreadPool(THREADS_NUM);
        Intent serviceIntent = new Intent(this, CommunicationServerService.class);
        doBindService(serviceIntent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onDestroy() {
        if(mIsBound){
            doUnbindService();
        }
        super.onDestroy();
    }

    private IServerBrokerService iServerBrokerService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            iServerBrokerService = IServerBrokerService.Stub.asInterface(service);
            Log.d(TAG, "Attached.");
            mIsBound = true;
        }


        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            iServerBrokerService = null;
            mIsBound = false;
            Log.e(TAG, "ISERVERBROKERSERVICE disconnected");


        }
    };

    void doBindService(Intent intent) {
        try {
            Log.d(TAG, "Before init intent.componentName");
            Log.d(TAG, "Before bindService");
            if (bindService(intent, mConnection, BIND_AUTO_CREATE)){
                Log.d(TAG, "Binding to ISERVERBROKERSERVICE returned true");
            } else {
                Log.d(TAG, "Binding to ISERVERBROKERSERVICE returned false");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "can't bind to ISERVERBROKERSERVICE, check permission in Manifest");
        } catch (Exception e){
            e.printStackTrace();
        }
        //mIsBound = true;
        Log.d(TAG, "Binding.");
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
            Log.d(TAG, "Unbinding.");
        }
    }

    /**
     *  Proxy methods
     */

    public ModuleManager getModuleManager(PluginVersionReference pluginVersionReference) throws CantCreateProxyException {
        Log.i(TAG,"creating proxy");
        ProxyInvocationHandlerAIDL mInvocationHandler = new ProxyInvocationHandlerAIDL(this,"key",pluginVersionReference);
        return proxyFactory.createModuleManagerProxy(pluginVersionReference,mInvocationHandler);
    }


}
