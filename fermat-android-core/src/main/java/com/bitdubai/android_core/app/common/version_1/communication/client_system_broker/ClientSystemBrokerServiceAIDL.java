package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.CommunicationDataKeys;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.CommunicationMessages;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl.CommunicationServerService;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl.IServerBrokerService;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.UUID;
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
     * Messeger connection implementation
     */
    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    private Messenger mServiceMcu = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mMessengerIsBound;


    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "Received from service: " + msg.arg1);
            switch (msg.what) {
                case CommunicationMessages.MSG_REQUEST_DATA_MESSAGE:
                    //Log.d(TAG, "Received from service: " + msg.arg1);
                    //String keyResponse = msg.getData().getString(DATA_KEY_TO_RESPONSE);
                    Bundle bundle = msg.getData();
                    String id = bundle.getString(CommunicationDataKeys.DATA_REQUEST_ID);
                    //TODO: el DATA_KEY_TO_RESPONSE quizás deberia ser el id
                    onMessageRecieve(UUID.fromString(id),msg.getData().getSerializable(CommunicationDataKeys.DATA_KEY_TO_RESPONSE));
                    break;
                case CommunicationMessages.MSG_SEND_CHUNKED_DATA:

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     *
     *
     * @param data
     */
    protected void onMessageRecieve(UUID id,Object data){
        try {
            //bufferChannel.notificateObject(id,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mMessengerConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mServiceMcu = new Messenger(service);
            Log.d(TAG, "Attached.");

            mMessengerIsBound = true;

            // We want to monitor the service for as long as we are
            // connected to it.
            try {
                Message msg = Message.obtain(null,
                        CommunicationMessages.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                Bundle bundle = new Bundle();
                bundle.putString(CommunicationDataKeys.DATA_PUBLIC_KEY, KEY);
                msg.setData(bundle);

                Log.i(TAG, "service connected: " + mMessengerIsBound);
                Log.i(TAG, "service is not null: " + mServiceMcu);
                mServiceMcu.send(msg);

                synchronized (this){
                    notifyAll();
                }


            } catch (RemoteException e) {
                // In this case the service has crashed before we could even
                // do anything with it; we can count on soon being
                // disconnected (and then reconnected if it can be restarted)
                // so there is no need to do anything here.
                Log.e(TAG, "CommService is not running");
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mServiceMcu = null;
            mMessengerIsBound = false;
            Log.e(TAG, "CommService disconnected");


        }
    };




    /**
     *  Proxy methods
     */

    public ModuleManager getModuleManager(PluginVersionReference pluginVersionReference) throws CantCreateProxyException {
        Log.i(TAG,"creating proxy");
        ProxyInvocationHandlerAIDL mInvocationHandler = new ProxyInvocationHandlerAIDL(this,"key",pluginVersionReference);
        return proxyFactory.createModuleManagerProxy(pluginVersionReference,mInvocationHandler);
    }


}
