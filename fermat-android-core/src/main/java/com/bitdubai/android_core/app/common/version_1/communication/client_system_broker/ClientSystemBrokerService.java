package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.CommunicationDataKeys;
import com.bitdubai.android_core.app.common.version_1.communication.CommunicationMessages;
import com.bitdubai.android_core.app.common.version_1.communication.CommunicationService;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.FermatServiceNotConnectedException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Matias Furszyfer on 2016.03.31..
 */
public class ClientSystemBrokerService extends Service {

    private static final String TAG = "ClientBrokerService";
    private static final String KEY = "s";
    private static final int THREADS_NUM = 3;

    private final IBinder localBinder = new LocalBinder();

    private ExecutorService poolExecutor;

    private ProxyFactory proxyFactory;
    private BufferChannel bufferChannel;

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());




    public ClientSystemBrokerService() {
        this.proxyFactory = new ProxyFactory();
    }

    public BufferChannel getBufferChannel() {
        return bufferChannel;
    }

    public class LocalBinder extends Binder {

        public ClientSystemBrokerService getService(){
            return ClientSystemBrokerService.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        proxyFactory = new ProxyFactory();
        bufferChannel = new BufferChannel();
        poolExecutor = Executors.newFixedThreadPool(THREADS_NUM);
        PackageManager packageManager = getPackageManager();
        Intent serviceIntent = new Intent("org.fermat.COMM_SERVICE");
        List<ResolveInfo> services = packageManager.queryIntentServices(serviceIntent, 0);
        if (services.size() > 0) {
            ResolveInfo service = services.get(0);
            Intent intent = new Intent();
            intent.setClassName(service.serviceInfo.packageName, service.serviceInfo.name);
            intent.setAction("org.fermat.COMM_SERVICE");
            //ComponentName cn = startService(intent);
//            startService(intent);
//            doBindService(intent);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }


    protected Object sendMessage(String keyToResponse,Object module,Method method,Object params)  throws FermatServiceNotConnectedException {
        try {
            Log.i(TAG,"Trying to send request");
            if(mServiceMcu!=null || mIsBound) {
                Message msg = Message.obtain(null,
                        CommunicationMessages.MSG_REQUEST_DATA_MESSAGE);
                msg.replyTo = mMessenger;
                Bundle bundle = new Bundle();
                bundle.putString(CommunicationDataKeys.DATA_PUBLIC_KEY, KEY);
                UUID requestId = UUID.randomUUID();
                bundle.putString(CommunicationDataKeys.DATA_REQUEST_ID, requestId.toString());
                bundle.putSerializable(CommunicationDataKeys.DATA_PLUGIN_VERSION_REFERENCE, ((ProxyInvocationHandler) Proxy.getInvocationHandler(module)).getPluginVersionReference());
                bundle.putString(CommunicationDataKeys.DATA_METHOD_TO_EXECUTE, method.getName());
                bundle.putSerializable(CommunicationDataKeys.DATA_PARAMS_TO_EXECUTE_METHOD, (Serializable) params);
                bundle.putString(CommunicationDataKeys.DATA_KEY_TO_RESPONSE, keyToResponse);
                msg.setData(bundle);
                Log.i(TAG, "Sending request");
                mServiceMcu.send(msg);

                if( method.getReturnType().equals(Void.TYPE)){
                    return null;
                }

                return bufferChannel.getObject(requestId);

            }else{
                Log.e(TAG, "FermatService is not running");
                Intent intent = new Intent(this, CommunicationService.class);
                startService(intent);
                doBindService(intent);
                //throw new FermatServiceNotConnectedException();
                synchronized (this){
                    wait();
                }
                sendMessage(keyToResponse,module,method,params);
            }

        } catch (RemoteException e) {
            // In this case the service has crashed before we could even
            // do anything with it; we can count on soon being
            // disconnected (and then reconnected if it can be restarted)
            // so there is no need to do anything here.
            Log.e(TAG, "FermatService is not running");
            e.printStackTrace();
        } catch (InterruptedException e) {
            //todo: lanzar la excepcion para arriba para que se avise al module que no tiene datos
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onDestroy() {
        if(mIsBound){
            doUnbindService();
        }
        super.onDestroy();
    }

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
            bufferChannel.notificateObject(id,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };



    private Messenger mServiceMcu = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mServiceMcu = new Messenger(service);
            Log.d(TAG, "Attached.");

            mIsBound = true;

            // We want to monitor the service for as long as we are
            // connected to it.
            try {
                Message msg = Message.obtain(null,
                        CommunicationMessages.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                Bundle bundle = new Bundle();
                bundle.putString(CommunicationDataKeys.DATA_PUBLIC_KEY, KEY);
                msg.setData(bundle);

                Log.i(TAG, "service connected: " + mIsBound);
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
            mIsBound = false;
            Log.e(TAG, "CommService disconnected");


        }
    };

    void doBindService(Intent intent) {
        // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
        //bindService(new Intent(this, MessengerService.class), mConnection, Context.BIND_AUTO_CREATE);
        try {
            Log.d(TAG, "Before init intent.componentName");
            Log.d(TAG, "Before bindService");
            if (bindService(intent, mConnection, BIND_AUTO_CREATE)){
                Log.d(TAG, "Binding to Modem Watcher returned true");
            } else {
                Log.d(TAG, "Binding to Modem Watcher returned false");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "can't bind to ModemWatcherService, check permission in Manifest");
        } catch (Exception e){
            e.printStackTrace();
        }
        //mIsBound = true;
        Log.d(TAG, "Binding.");
    }

    void doUnbindService() {
        if (mIsBound) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mServiceMcu != null) {
                try {
                    Message msg = Message.obtain(null, CommunicationMessages.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mServiceMcu.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
            }

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
        ProxyInvocationHandler mInvocationHandler = new ProxyInvocationHandler(this,"key",pluginVersionReference);
        return proxyFactory.createModuleManagerProxy(pluginVersionReference,mInvocationHandler);
    }


}
