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

import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.structure.LocalClientSocketSession;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.CommunicationDataKeys;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.CommunicationMessages;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.IntentServerServiceAction;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl.CommunicationServerService;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl.IServerBrokerService;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.ModuleObjectParameterWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.core.MethodDetail;
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

    private static final String TAG = "ClientBrokerServiceAIDL";
//    private static final String KEY = "s";
    private static final int THREADS_NUM = 3;

    private final IBinder localBinder = new LocalBinder();

    private ExecutorService poolExecutor;
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

    public Object sendMessage(PluginVersionReference pluginVersionReference,String responseStr, Object proxy, Method method, Object[] args) throws Exception {
        //Log.i(TAG,"SendMessage start");
        ModuleObjectParameterWrapper[] parameters = null;
        Class<?>[] parametersTypes = method.getParameterTypes();
        if(args!=null) {
            parameters = new ModuleObjectParameterWrapper[args.length];

            for (int i = 0; i < args.length; i++) {
                try {
                    ModuleObjectParameterWrapper fermatModuleObjectWrapper = new ModuleObjectParameterWrapper((Serializable) args[i],parametersTypes[i]);
                    parameters[i] = fermatModuleObjectWrapper;
                } catch (ClassCastException e) {
                    //e.printStackTrace();
                    Log.e(TAG, "ERROR: Objeto "+args[i].getClass().getName()+" no implementa interface Serializable");
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            parameters = new ModuleObjectParameterWrapper[0];
        }


        String dataId = UUID.randomUUID().toString();
        boolean isDataChuncked = false;
        FermatModuleObjectWrapper objectArrived = null;
        MethodDetail methodDetail = method.getAnnotation(MethodDetail.class);
        if(methodDetail!=null){
            if(methodDetail.looType() == MethodDetail.LoopType.BACKGROUND){
                Log.i(TAG,"Sending background request");
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
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    Log.e(TAG, "ERROR: Some of the parameters not implement Serializable interface in interface " + proxy.getClass().getInterfaces()[0] + " in method:" + method.getName());
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG,"Sending background return");
            }
        }else {
            try {
                objectArrived = iServerBrokerService.invoqueModuleMethod(
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
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    Log.e(TAG, "ERROR: Some of the parameters not implement Serializable interface in interface " + proxy.getClass().getInterfaces()[0] + " in method:" + method.getName());
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG,"SendMessage return from server");


        if(objectArrived!=null){
            if(objectArrived.getE()!=null) return objectArrived.getE();
            isDataChuncked = objectArrived.isLargeData();
        }else{
            if (!method.getReturnType().equals(Void.TYPE))
                Log.e(TAG,"Object arrived null in method: "+method.getName()+", this happen when an error occur in the module, please check your module and contact furszy if the error persist,");
            return null;
        }
        Object o = null;
        //Log.i(TAG,"SendMessage almost end");
        if(isDataChuncked){
            if(Looper.myLooper() == Looper.getMainLooper()) return new LargeWorkOnMainThreadException(proxy,method);
            //test reason
            mReceiverSocketSession.addWaitingMessage(dataId);

            o = bufferChannelAIDL.getBufferObject(dataId);
            //Log.i(TAG, o != null ? o.toString() : "");
            return (o instanceof EmptyObject)?null:o;
        }else{
            Object o1 = objectArrived.getObject();;
            //Log.i(TAG, o1 != null ? o1.toString() : "");
            return o1;
        }
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
        bufferChannelAIDL = new BufferChannelAIDL();

        Intent serviceIntent = new Intent(this, CommunicationServerService.class);
        serviceIntent.setAction(IntentServerServiceAction.ACTION_BIND_AIDL);
        doBindService(serviceIntent);

//        Intent serviceIntent2 = new Intent(this, CommunicationServerService.class);
//        serviceIntent2.setAction(IntentServerServiceAction.ACTION_BIND_MESSENGER);
//        doBindMessengerService(serviceIntent2);
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

//        if(mMessengerIsBound){
//            doUnbindMessengerService();
//        }
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

            Log.i(TAG,"Registering client");
            try {
                serverIdentificationKey = iServerBrokerService.register();


                //running socket receiver
                Log.i(TAG,"Starting socket receiver");
                mReceiverSocketSession = new LocalClientSocketSession(serverIdentificationKey,new LocalSocket(),bufferChannelAIDL);
                mReceiverSocketSession.connect();
                mReceiverSocketSession.start();

            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG,"Cant run socket, register to server fail");
            }




//            Message msg = Message.obtain(null,
//                    CommunicationMessages.MSG_REGISTER_CLIENT);
//            msg.replyTo = mMessenger;
//            Bundle bundle = new Bundle();
//            bundle.putString(CommunicationDataKeys.DATA_PUBLIC_KEY, KEY);
//            bundle.putBoolean(CommunicationDataKeys.DATA_SOCKET_STARTED, true);
//            msg.setData(bundle);
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
            //Log.d(TAG, "Before init intent.componentName");
            //Log.d(TAG, "Before bindService");
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
    /**
     * Service server messenger
     */
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

    private void onChuckedDateRecieve(String id, byte[] chunkedData,boolean isFinishData) {
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

    void doBindMessengerService(Intent intent) {
        try {
           // Log.d(TAG, "Before init intent.componentName");
            //Log.d(TAG, "Before bindService");
            if (bindService(intent, mMessengerConnection, BIND_AUTO_CREATE)){
                Log.d(TAG, "Binding to ISERVERBROKERSERVICE MESSENGER returned true");
            } else {
                Log.d(TAG, "Binding to ISERVERBROKERSERVICE MESSENGER returned false");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "can't bind to ISERVERBROKERSERVICE MESSENGER, check permission in Manifest");
        } catch (Exception e){
            e.printStackTrace();
        }
        //mIsBound = true;
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




    /**
     *  Proxy methods
     */

    public ModuleManager getModuleManager(PluginVersionReference pluginVersionReference) throws CantCreateProxyException {
        //Log.i(TAG,"creating proxy");
        ProxyInvocationHandlerAIDL mInvocationHandler = new ProxyInvocationHandlerAIDL(this,"key",pluginVersionReference);
        return proxyFactory.createModuleManagerProxy(pluginVersionReference,mInvocationHandler);
    }

}
