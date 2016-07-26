package com.bitdubai.android_core.app.common.version_1.communication.client_system_broker;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.LocalSocket;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.FermatPlatformServiceNotConnectedException;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.InvalidMethodExecutionException;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.LargeDataRequestException;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.LargeWorkOnMainThreadException;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.MethodTimeOutException;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.structure.LocalClientSocketSession;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.CommunicationDataKeys;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.CommunicationMessages;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.IntentServerServiceAction;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.aidl.IPlatformService;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.aidl.PlatformService;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.structure.FermatModuleObjectWrapper;
import com.bitdubai.android_core.app.common.version_1.communication.platform_service.structure.ModuleObjectParameterWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.core.MethodDetail;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

/**
 * Created by Matias Furszyfer on 2016.03.31..
 */
public class ClientSystemBrokerServiceAIDL extends Service implements ClientBrokerService {

    private static final String TAG = "ClientBrokerServiceAIDL";
    //    private static final String KEY = "s";
    private static final int THREADS_NUM = 5;

    private final IBinder localBinder = new LocalBinder();

    private ThreadPoolExecutor poolExecutor;
    private ProxyFactory proxyFactory;
    private BufferChannelAIDL bufferChannelAIDL;


    private String serverIdentificationKey;

    /**
     * Socket Implementation to receive messages from server
     */
    private LocalClientSocketSession mReceiverSocketSession;


    public ClientSystemBrokerServiceAIDL() {
        this.proxyFactory = new ProxyFactory();
    }

    public Object sendMessage(final PluginVersionReference pluginVersionReference, final Object proxy, final Method method, final MethodDetail methodDetail, Object[] args) throws Exception {
        //Log.i(TAG,"SendMessage start");
        ModuleObjectParameterWrapper[] parameters = null;
        Class<?>[] parametersTypes = method.getParameterTypes();
        if (args != null) {
            parameters = new ModuleObjectParameterWrapper[args.length];

            for (int i = 0; i < args.length; i++) {
                try {
                    ModuleObjectParameterWrapper fermatModuleObjectWrapper = new ModuleObjectParameterWrapper((Serializable) args[i], parametersTypes[i]);
                    parameters[i] = fermatModuleObjectWrapper;
                } catch (ClassCastException e) {
                    //e.printStackTrace();
                    Log.e(TAG, new StringBuilder().append("ERROR: Objeto ").append(args[i].getClass().getName()).append(" no implementa interface Serializable").toString());
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            parameters = new ModuleObjectParameterWrapper[0];
        }
        /**
         * Data id
         */
        final String dataId = UUID.randomUUID().toString();
//        Log.e(TAG,"data id: "+dataId+" from method "+method);
        boolean isDataChuncked = false;
        FermatModuleObjectWrapper objectArrived = null;
        /**
         * Method detail if the developer want something specific
         */
        try {
            final ModuleObjectParameterWrapper[] parametersTemp = parameters;
            if (methodDetail != null) {
                long methdTimeout = methodDetail.timeout();
                if (methdTimeout != -1) {
                    final Callable<FermatModuleObjectWrapper> callable = new Callable<FermatModuleObjectWrapper>() {
                        @Override
                        public FermatModuleObjectWrapper call() throws Exception {
                            try {
                                return requestModuleObjetc(dataId, proxy, method, parametersTemp, pluginVersionReference, methodDetail.looType());
                            } catch (FermatPlatformServiceNotConnectedException e) {
                                e.printStackTrace();
                                tryReconnect();
                            }
                            return null;
                        }
                    };
                    Future<FermatModuleObjectWrapper> objectFuture = poolExecutor.submit(callable);
                    try {
                        Log.i(TAG, "Timeout method");
                        objectArrived = objectFuture.get(methdTimeout, methodDetail.timeoutUnit());
                        Log.i(TAG, "Timeout method return");
                    } catch (TimeoutException e) {
                        //Method canceled and return an exception
                        objectFuture.cancel(true);
                        poolExecutor.purge();
                        Log.i(TAG, new StringBuilder().append("Timeout launched wainting for method: ").append(method.getName()).append("in module: ").append(pluginVersionReference.toString3()).append(" ,this will return null").toString());
                        return new MethodTimeOutException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    /**
                     * Normal data request
                     * todo: capaz tengo que dividir esto, dividir el background del main, que el segundo canal sea bidireccional...
                     */
                    MethodDetail.LoopType loopType = (Looper.myLooper() == Looper.getMainLooper()) ? MethodDetail.LoopType.MAIN : MethodDetail.LoopType.BACKGROUND;
                    objectArrived = requestModuleObjetc(dataId, proxy, method, parametersTemp, pluginVersionReference, loopType);
                }
            } else {
                /**
                 * Normal data request
                 * todo: capaz tengo que dividir esto, dividir el background del main, que el segundo canal sea bidireccional...
                 */
                //MethodDetail.LoopType loopType = (Looper.myLooper() == Looper.getMainLooper())? MethodDetail.LoopType.MAIN: MethodDetail.LoopType.BACKGROUND;
                objectArrived = requestModuleObjetc(dataId, proxy, method, parametersTemp, pluginVersionReference, null);
            }
        } catch (FermatPlatformServiceNotConnectedException e) {
            e.printStackTrace();
            tryReconnect();
        }
        Log.i(TAG, "SendMessage return from server");
        if (objectArrived != null) {
            Log.i(TAG, new StringBuilder().append("Object: ").append(objectArrived.getObject()).toString());
            if (objectArrived.getE() != null) return objectArrived.getE();
            isDataChuncked = objectArrived.isLargeData();
        } else {
            if (!method.getReturnType().equals(Void.TYPE))
                Log.i(TAG, new StringBuilder().append("Object arrived null in method: ").append(method.getName()).append(", this happen when an error occur in the module or if you activate the timeout, please check your module and contact furszy if the error persist.").toString());
            return null;
        }
        Object o = null;
        //Log.i(TAG,"SendMessage almost end");
        if (isDataChuncked) {
            // Check if the data is on main thread or in background.
            if (Looper.myLooper() == Looper.getMainLooper())
                return new LargeWorkOnMainThreadException(proxy, method);
            //test reason
            mReceiverSocketSession.addWaitingMessage(dataId);

            o = bufferChannelAIDL.getBufferObject(dataId);
            return (o instanceof EmptyObject) ? null : o;
        } else {
            Object o1 = objectArrived.getObject();
            ;
            return o1;
        }
    }


    private FermatModuleObjectWrapper requestModuleObjetc(String dataId, Object proxy, Method method, ModuleObjectParameterWrapper[] parameters, PluginVersionReference pluginVersionReference, MethodDetail.LoopType loopType) {
        FermatModuleObjectWrapper objectArrived = null;
        if (iServerBrokerService == null) {
            Log.e(TAG, "FermatPlatformService is not connected");
            throw new FermatPlatformServiceNotConnectedException();
        }
        if (loopType != null) {
            if (loopType == MethodDetail.LoopType.BACKGROUND) {
                if (Looper.myLooper() == Looper.getMainLooper())
                    return new FermatModuleObjectWrapper(dataId, null, true, new InvalidMethodExecutionException(proxy, method, "The MethodDetail annotation have background thread value and this method is invoqued in the main thread."));
                Log.i(TAG, "Sending background request");
                try {
                    objectArrived = iServerBrokerService.invoqueModuleLargeDataMethod(
                            serverIdentificationKey,
                            dataId,
                            pluginVersionReference.getPlatform().getCode(),
                            pluginVersionReference.getLayers().getCode(),
                            pluginVersionReference.getPlugins().getCode(),
                            pluginVersionReference.getDeveloper().getCode(),
                            pluginVersionReference.getVersion().toString(),
                            method.getName(),
                            parameters);
                } catch (TransactionTooLargeException t1) {
                    Log.e(TAG, new StringBuilder().append("Method send too much data, remove large data from method's parameters, minimize data returned by the module or check the android framework documentation for make a large data background request, method=").append(method.getName()).append(" at pluginVersionReference=").append(pluginVersionReference.toString3()).toString());
                    objectArrived = new FermatModuleObjectWrapper(new LargeDataRequestException(proxy, method, t1));
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    Log.e(TAG, new StringBuilder().append("ERROR: Some of the parameters not implement Serializable interface in class ").append(proxy.getClass().getInterfaces()[0]).append(" in method:").append(method.getName()).toString());
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "Sending background return");
            } else {
                objectArrived = fastModuleObjectRequest(dataId, proxy, method, parameters, pluginVersionReference);
            }
        } else {
            objectArrived = fastModuleObjectRequest(dataId, proxy, method, parameters, pluginVersionReference);
        }
        return objectArrived;
    }

    private FermatModuleObjectWrapper fastModuleObjectRequest(String dataId, Object proxy, Method method, ModuleObjectParameterWrapper[] parameters, PluginVersionReference pluginVersionReference) {
        FermatModuleObjectWrapper fermatModuleObjectWrapper = null;
        try {
            fermatModuleObjectWrapper = iServerBrokerService.invoqueModuleMethod(
                    serverIdentificationKey,
                    dataId,
                    pluginVersionReference.getPlatform().getCode(),
                    pluginVersionReference.getLayers().getCode(),
                    pluginVersionReference.getPlugins().getCode(),
                    pluginVersionReference.getDeveloper().getCode(),
                    pluginVersionReference.getVersion().toString(),
                    method.getName(),
                    parameters);
        } catch (TransactionTooLargeException t) {
            try {
                fermatModuleObjectWrapper = iServerBrokerService.invoqueModuleLargeDataMethod(
                        serverIdentificationKey,
                        dataId,
                        pluginVersionReference.getPlatform().getCode(),
                        pluginVersionReference.getLayers().getCode(),
                        pluginVersionReference.getPlugins().getCode(),
                        pluginVersionReference.getDeveloper().getCode(),
                        pluginVersionReference.getVersion().toString(),
                        method.getName(),
                        parameters);
            } catch (TransactionTooLargeException t1) {
                Log.e(TAG, new StringBuilder().append("Method request too much data on the main thread, method=").append(method.getName()).append(" at pluginVersionReference=").append(pluginVersionReference.toString3()).toString());
                fermatModuleObjectWrapper = new FermatModuleObjectWrapper(new LargeWorkOnMainThreadException(proxy, method, t1));
            } catch (RemoteException e) {
                Log.e(TAG,"Explota acá");
                e.printStackTrace();
            } catch (RuntimeException e) {
                Log.e(TAG, new StringBuilder().append("ERROR: Some of the parameters not implement Serializable interface in interface ").append(proxy.getClass().getInterfaces()[0]).append(" in method:").append(method.getName()).toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fermatModuleObjectWrapper;
    }

    @Override
    public boolean isFermatBackgroundServiceRunning() throws FermatPlatformServiceNotConnectedException {
        try {
            if (iServerBrokerService != null)
                return iServerBrokerService.isFermatSystemRunning();
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new FermatPlatformServiceNotConnectedException("PlatformService not connected yet", e);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void disconnect() {
        try {
            if (mPlatformServiceIsBound) {
                doUnbindService();
            }
            if (mReceiverSocketSession.isConnected()) {
                mReceiverSocketSession.stop();
                try {
                    mReceiverSocketSession.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {
        try {
            if (!mPlatformServiceIsBound) {
                Intent serviceIntent = new Intent(this, PlatformService.class);
                serviceIntent.setAction(IntentServerServiceAction.ACTION_BIND_AIDL);
                doBindService(serviceIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {

        public ClientSystemBrokerServiceAIDL getService() {
            return ClientSystemBrokerServiceAIDL.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            proxyFactory = new ProxyFactory();
            poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS_NUM);
            bufferChannelAIDL = new BufferChannelAIDL();

//            Intent serviceIntent = new Intent(this, PlatformService.class);
//            serviceIntent.setAction(IntentServerServiceAction.ACTION_BIND_AIDL);
//            doBindService(serviceIntent);

            connect();

//        Intent serviceIntent2 = new Intent(this, PlatformService.class);
//        serviceIntent2.setAction(IntentServerServiceAction.ACTION_BIND_MESSENGER);
//        doBindMessengerService(serviceIntent2);
        } catch (Exception e) {
            Log.e(TAG, "Error creating client, please contact to Furszy");
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onDestroy() {
        if (mPlatformServiceIsBound) {
            doUnbindService();
        }

//        if(mMessengerIsBound){
//            doUnbindMessengerService();
//        }
        super.onDestroy();
    }

    private IPlatformService iServerBrokerService = null;
    /**
     * Flag indicating whether we have called bind on the service.
     */
    boolean mPlatformServiceIsBound;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mPlatformServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            iServerBrokerService = IPlatformService.Stub.asInterface(service);
            Log.d(TAG, "Attached.");
            mPlatformServiceIsBound = true;
            Log.i(TAG, "Registering client");
            try {
                serverIdentificationKey = iServerBrokerService.register();
//                try {
//                    iServerBrokerService.asBinder().unlinkToDeath(new IBinder.DeathRecipient() {
//                        @Override
//                        public void binderDied() {
//                            Log.e(TAG,"Binder unlinked");
//                        }
//                    }, 0);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                //running socket receiver
                Log.i(TAG, "Starting socket receiver");
                LocalSocket localSocket = new LocalSocket();
                mReceiverSocketSession = new LocalClientSocketSession(serverIdentificationKey, localSocket, bufferChannelAIDL);
                mReceiverSocketSession.connect();
                mReceiverSocketSession.startReceiving();

            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "Cant run socket, register to server fail");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                FermatApplication.getInstance().setFermatRunning(iServerBrokerService.isFermatSystemRunning());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            iServerBrokerService = null;
            mPlatformServiceIsBound = false;
            Log.e(TAG, "ISERVERBROKERSERVICE disconnected");


        }
    };

    void doBindService(Intent intent) {
        try {
            //Log.d(TAG, "Before init intent.componentName");
            //Log.d(TAG, "Before bindService");
            if (bindService(intent, mPlatformServiceConnection, BIND_AUTO_CREATE)) {
                Log.d(TAG, "Binding to ISERVERBROKERSERVICE returned true");
            } else {
                Log.d(TAG, "Binding to ISERVERBROKERSERVICE returned false");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "can't bind to ISERVERBROKERSERVICE, check permission in Manifest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //mPlatformServiceIsBound = true;
        Log.d(TAG, "Binding.");
    }

    void doUnbindService() {
        if (mPlatformServiceIsBound) {
            // Detach our existing connection.
            unbindService(mPlatformServiceConnection);
            mPlatformServiceIsBound = false;
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
    /**
     * Service server messenger
     */
    private Messenger mServiceMcu = null;
    /**
     * Flag indicating whether we have called bind on the service.
     */
    boolean mMessengerIsBound;


    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, new StringBuilder().append("Received from service: ").append(msg.arg1).toString());
            Bundle bundle = msg.getData();
            String id = bundle.getString(CommunicationDataKeys.DATA_REQUEST_ID);
            switch (msg.what) {
                case CommunicationMessages.MSG_REQUEST_DATA_MESSAGE:
                    //Log.d(TAG, "Received from service: " + msg.arg1);
                    //String keyResponse = msg.getData().getString(DATA_KEY_TO_RESPONSE);
                    //TODO: el DATA_KEY_TO_RESPONSE quizás deberia ser el id
//                    onMessageRecieve(UUID.fromString(id),msg.getData().getSerializable(CommunicationDataKeys.DATA_KEY_TO_RESPONSE));

                    //todo: con esto solo estaba el metodo viejo:
//                    onFullDateRecieve(id,bundle.getSerializable(CommunicationDataKeys.DATA_KEY_TO_RESPONSE));


                    break;
                case CommunicationMessages.MSG_SEND_CHUNKED_DATA:
                    bundle = msg.getData();
//                    onChuckedDateRecieve(
//                            id,
//                            bundle.getByteArray(CommunicationDataKeys.DATA_CHUNKED_DATA),
//                            bundle.getBoolean(CommunicationDataKeys.DATA_IS_CHUNKED_DATA_FINISH));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void onChuckedDateRecieve(String id, byte[] chunkedData, boolean isFinishData) {
        try {
            //bufferChannel.notificateObject(id,data);
            //   bufferChannelAIDL.addChunkedData(id,chunkedData,isFinishData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onFullDateRecieve(String id, Serializable data) {
        try {
            //bufferChannel.notificateObject(id,data);
            bufferChannelAIDL.addFullDataAndNotificateArrive(id, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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

                //testear esto
                //si el cliente está registrado:
//                mReceiverSocketSession = new LocalClientSocketSession(KEY,new LocalSocket(),bufferChannelAIDL);
//                mReceiverSocketSession.connect();
//                mReceiverSocketSession.start();
                //


//                Message msg = Message.obtain(null,
//                        CommunicationMessages.MSG_REGISTER_CLIENT);
//                msg.replyTo = mMessenger;
//                Bundle bundle = new Bundle();
//                bundle.putString(CommunicationDataKeys.DATA_PUBLIC_KEY, KEY);
//                bundle.putBoolean(CommunicationDataKeys.DATA_SOCKET_STARTED,true);
//                msg.setData(bundle);
//
//                Log.i(TAG, "service connected: " + mMessengerIsBound);
//                Log.i(TAG, "service is not null: " + mServiceMcu);
//                mServiceMcu.send(msg);
//
//                synchronized (this){
//                    notifyAll();
//                }


            } catch (Exception e) {
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

    void doBindMessengerService(Intent intent) {
        try {
            // Log.d(TAG, "Before init intent.componentName");
            //Log.d(TAG, "Before bindService");
            if (bindService(intent, mMessengerConnection, BIND_AUTO_CREATE)) {
                Log.d(TAG, "Binding to ISERVERBROKERSERVICE MESSENGER returned true");
            } else {
                Log.d(TAG, "Binding to ISERVERBROKERSERVICE MESSENGER returned false");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "can't bind to ISERVERBROKERSERVICE MESSENGER, check permission in Manifest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //mPlatformServiceIsBound = true;
        Log.d(TAG, "Binding.");
    }

    void doUnbindMessengerService() {
        if (mMessengerIsBound) {
            // Detach our existing connection.
            unbindService(mMessengerConnection);
            mMessengerIsBound = false;
            Log.d(TAG, "Unbinding MESSENGER.");
        }
    }


    private void tryReconnect() {
        if (!mPlatformServiceIsBound) {
            Intent serviceIntent = new Intent(this, PlatformService.class);
            serviceIntent.setAction(IntentServerServiceAction.ACTION_BIND_AIDL);
            doBindService(serviceIntent);
        } else {
            Log.e(TAG, "Trying to reconnect when the PlatformService is connected, contact to furszy");
        }
    }


    /**
     * Proxy methods
     */

    public ModuleManager getModuleManager(PluginVersionReference pluginVersionReference) throws CantCreateProxyException {
        //Log.i(TAG,"creating proxy");
        ProxyInvocationHandlerAIDL mInvocationHandler = new ProxyInvocationHandlerAIDL(this, pluginVersionReference);
        return proxyFactory.createModuleManagerProxy(pluginVersionReference, mInvocationHandler);
    }

    @Override
    public ModuleManager[] getModuleManager(PluginVersionReference[] pluginVersionReference) throws CantCreateProxyException {
        ModuleManager[] moduleManagers = new ModuleManager[pluginVersionReference.length];
        for (int i = 0; i < pluginVersionReference.length; i++) {
            ProxyInvocationHandlerAIDL mInvocationHandler = new ProxyInvocationHandlerAIDL(this, pluginVersionReference[i]);
            moduleManagers[i] = proxyFactory.createModuleManagerProxy(pluginVersionReference[i], mInvocationHandler);
        }
        return moduleManagers;
    }

}
