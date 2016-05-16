package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl;

import android.app.Service;
import android.content.Intent;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.common.version_1.classes.BroadcastInterface;
import com.bitdubai.android_core.app.common.version_1.classes.BroadcastManager;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.CommunicationDataKeys;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.CommunicationMessages;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.IntentServerServiceAction;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.ModuleObjectParameterWrapper;
import com.bitdubai.android_core.app.common.version_1.util.AndroidCoreUtils;
import com.bitdubai.android_core.app.common.version_1.util.task.GetTask;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetAddonException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.DeviceInfoUtils;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.module_object_creator.FermatModuleObjectInterface;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_osa_android_core.OSAPlatform;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantSetPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by MAtias Furszyfer on 2016.04.18..
 */
public class CommunicationServerService extends Service implements FermatWorkerCallBack, BroadcastInterface {

    public static final String SERVER_NAME = "server_fermat";


    private static String TAG = "CommunicationServerService";
    private static int BLOCK_SYZE = 1024 * 250;

    public int processingQueue = 0;
    /**
     * Clients connected
     */
    private Map<String, Messenger> clients;

    /**
     * Server socket
     */
    private LocalServerSocket localServerSocket;
    private Thread serverThread;
    /**
     * Clients connected
     */
    private Map<String, LocalServerSocketSession> socketsClients;

    private boolean isFermatSystemRunning = false;

    /**
     * Fermat background service
     * <p/>
     * Esto tiene que estar corriendo en un servicio a parte y con este conectarme al otro, usarlo de router y haciendo los bloqueos acá.
     */
    private FermatSystem fermatSystem;

    /**
     * Executor service
     */
    private ExecutorService executorService;

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());


    /**
     * Manager
     */
    private BroadcastManager broadcastManager;


    private void chunkAndSendData(String dataId, String clientKey, Serializable data) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.reset();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(data);
            byte[] yourBytes = byteArrayOutputStream.toByteArray();


            int i = 0;
            int blockSize = 512;
            while (i < yourBytes.length) {
                if (i + 512 > yourBytes.length) {
                    blockSize = yourBytes.length - i;
                }

                Messenger messenger = clients.get(clientKey);
                Message msg = Message.obtain(null, CommunicationMessages.MSG_SEND_CHUNKED_DATA);
                byte[] chunkedDate = Arrays.copyOfRange(yourBytes, i, (i + 1) * blockSize);
                msg.getData().putByteArray(CommunicationDataKeys.DATA_CHUNKED_DATA, chunkedDate);
                msg.getData().putString(CommunicationDataKeys.DATA_REQUEST_ID, dataId);

                i = blockSize * i;
                i++;

                msg.getData().putBoolean(CommunicationDataKeys.DATA_IS_CHUNKED_DATA_FINISH, i >= yourBytes.length);

                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

    }

    private boolean isDataForChunk(Serializable data) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.reset();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(data);
            byte[] yourBytes = byteArrayOutputStream.toByteArray();

            return (yourBytes.length > BLOCK_SYZE);
        } catch (NotSerializableException e) {
            if (data instanceof SettingsManager) {
                Log.e(TAG, "ERROR: NO USAR getSettingsManager DEL MODULE");
            } else {
                Log.e(TAG, "ERROR: Class is not implementing Serializable, class name: " + data.getClass().getName());
                if (data instanceof ArrayList || data instanceof List) {
                    Log.e(TAG, String.valueOf(data.getClass().getComponentType()));
                }
            }
            throw new Exception("ERROR: Class is not implementing Serializable, class name: " + data.getClass().getName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return false;
    }

    private void sendFullData(String dataId, String clientKey, Serializable data) throws NotSerializableException {


        //test
        Log.i(TAG, "DATA CLass name: " + data.getClass().getName());
        Log.i(TAG, "DATA: " + data);
        sendLargeData(dataId, clientKey, data);


        Messenger messenger = clients.get(clientKey);
        Message msg = Message.obtain(null, CommunicationMessages.MSG_REQUEST_DATA_MESSAGE);
        msg.getData().putSerializable(CommunicationDataKeys.DATA_KEY_TO_RESPONSE, data);
        msg.getData().putString(CommunicationDataKeys.DATA_REQUEST_ID, dataId);
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendLargeData(String dataId, String clientKey, Serializable data) {
        try {
            Log.i(TAG,"Socket sending response to client");
            LocalServerSocketSession localServerSocketSession = socketsClients.get(clientKey);
            localServerSocketSession.sendMessage(dataId, data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private final IServerBrokerService.Stub mBinder = new IServerBrokerService.Stub() {


        public String register() {
            final String clientKey = UUID.randomUUID().toString();
            if (serverThread == null) {
                serverThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LocalSocket localSocket = localServerSocket.accept();
                            localSocket.setSendBufferSize(500000);
                            LocalServerSocketSession localServerSocketSession = new LocalServerSocketSession(clientKey, localSocket);
                            socketsClients.put(clientKey, localServerSocketSession);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
            serverThread.start();

            return clientKey;
        }

        @Override
        public FermatModuleObjectWrapper invoqueModuleMethod(String clientKey, String dataId, String platformCode, String layerCode, String pluginsCode, String developerCode, String version, String method, ModuleObjectParameterWrapper[] parameters) throws RemoteException {
//            Log.i(TAG,"invoqueModuleMethod");
//            Log.i(TAG,platformCode);
//            Log.i(TAG,layerCode);
//            Log.i(TAG,pluginsCode);
//            Log.i(TAG,version);
//            Log.i(TAG,method);
//            Log.i(TAG,"Parameters");
//            for (ModuleObjectParameterWrapper parameter : parameters) {
//                Log.i(TAG, parameter.toString());
//            }
            Object returnModuleObject = null;
            PluginVersionReference pluginVersionReference = null;
            try {
                pluginVersionReference = new PluginVersionReference(
                        Platforms.getByCode(platformCode),
                        Layers.getByCode(layerCode),
                        Plugins.getByCode(pluginsCode),
                        Developers.BITDUBAI,
                        new Version());
                returnModuleObject = moduleDataRequest(pluginVersionReference, method, parameters);
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }


            /**
             * Acá se va a hacer el chunk y el envio al cliente
             */
            //chunkAndSendData(dataId,clientKey,aidlObject);
            try {

                if (returnModuleObject instanceof Exception) {
                    return new FermatModuleObjectWrapper(dataId, null, true, (Exception) returnModuleObject);
                } else {
                    if (!(returnModuleObject instanceof Serializable)) {
                        if (returnModuleObject != null) {
                            NotSerializableException e = new NotSerializableException("Object returned: " + returnModuleObject.getClass().getName() + " from method " + method + " is not implementing serializable");
                            return new FermatModuleObjectWrapper(dataId, null, true, e);

                        } else {
                            //throw new NotSerializableException("Object returned: <null> from method: "+method +" is not implementing serializable");
//                            Log.e(TAG, "object returned null in method: "+method+" from plugin: "+pluginVersionReference.toString3());
                        }
                    }
                    Serializable aidlObject = (Serializable) returnModuleObject;
                    if (isDataForChunk(aidlObject)) {
                        try {
                            sendLargeData(dataId, clientKey, aidlObject);
                            return new FermatModuleObjectWrapper(aidlObject, true, dataId);
                        } catch (Exception e) {
                            return new FermatModuleObjectWrapper(dataId, aidlObject, true, e);
                        }
                    } else {
                        return new FermatModuleObjectWrapper(aidlObject, false, dataId);
                    }
                }
            } catch (Exception e) {
//                Exception e1 = new Exception("Error in Method: "+method+" object returned: "+returnModuleObject,e);
                return new FermatModuleObjectWrapper(dataId, null, true, e);
            }

        }

        @Override
        public FermatModuleObjectWrapper invoqueModuleLargeDataMethod(String clientKey, String dataId, String platformCode, String layerCode, String pluginsCode, String developerCode, String version, String method, ModuleObjectParameterWrapper[] parameters) throws RemoteException {
//            Log.i(TAG,"invoqueModuleMethod");
//            Log.i(TAG,platformCode);
//            Log.i(TAG,layerCode);
//            Log.i(TAG,pluginsCode);
//            Log.i(TAG,version);
//            Log.i(TAG,method);
//            Log.i(TAG,"Parameters");
//            for (ModuleObjectParameterWrapper parameter : parameters) {
//                Log.i(TAG, parameter.toString());
//            }
            Object returnModuleObject = null;
            PluginVersionReference pluginVersionReference =  null;
            try {
                pluginVersionReference= new PluginVersionReference(
                        Platforms.getByCode(platformCode),
                        Layers.getByCode(layerCode),
                        Plugins.getByCode(pluginsCode),
                        Developers.BITDUBAI,
                        new Version());
                returnModuleObject = moduleDataRequest(pluginVersionReference, method, parameters);
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
            /**
             * Acá se va a hacer el chunk y el envio al cliente
             */
//            chunkAndSendData(dataId, clientKey, aidlObject);
//            return new FermatModuleObjectWrapper(aidlObject, true, dataId);



            try {

                if (returnModuleObject instanceof Exception) {
                    return new FermatModuleObjectWrapper(dataId, null, true, (Exception) returnModuleObject);
                } else {
                    if (!(returnModuleObject instanceof Serializable)) {
                        if (returnModuleObject != null) {
                            NotSerializableException e = new NotSerializableException("Object returned: " + returnModuleObject.getClass().getName() + " from method " + method + " is not implementing serializable");
                            // return the exception
                            sendLargeData(dataId, clientKey, new FermatModuleObjectWrapper(dataId, null, true, e));
                        }
                    }else {
                        Serializable aidlObject = (Serializable) returnModuleObject;
                        try {
                            sendLargeData(dataId, clientKey, aidlObject);
                            return new FermatModuleObjectWrapper(aidlObject, true, dataId);
                        } catch (Exception e) {
                            sendLargeData(dataId, clientKey, new FermatModuleObjectWrapper(dataId, null, true, e));
                        }
                    }

                }
            } catch (Exception e) {
//                Exception e1 = new Exception("Error in Method: "+method+" object returned: "+returnModuleObject,e);
                try {
                    sendLargeData(dataId, clientKey, new FermatModuleObjectWrapper(dataId, null, true, e));
                } catch (Exception e1) {
                    Log.e(TAG,"Error, please contact furszy and show him the stacktrace below");
                    e1.printStackTrace();
                }
//                new FermatModuleObjectWrapper(dataId, null, true, e);
            }

            return new FermatModuleObjectWrapper(null, true, dataId);

        }

        @Override
        public FermatModuleObjectWrapper invoqueModuleMethod2(String platformCode, String layerCode, String pluginsCode, String developerCode, String version, String method, FermatModuleObjectWrapper[] parameters) throws RemoteException {
//            Log.i(TAG,"invoqueModuleMethod");
//            Log.i(TAG,platformCode);
//            Log.i(TAG,layerCode);
//            Log.i(TAG,pluginsCode);
//            Log.i(TAG,version);
//            Log.i(TAG,method);
//            Log.i(TAG,"Parameters");
//            for (FermatModuleObjectWrapper parameter : parameters) {
//                Log.i(TAG, parameter.toString());
//            }
            FermatModuleObjectWrapper wrapper = null;
//            try {
//                PluginVersionReference pluginVersionReference = new PluginVersionReference(
//                        Platforms.getByCode(platformCode),
//                        Layers.getByCode(layerCode),
//                        Plugins.getByCode(pluginsCode),
//                        Developers.BITDUBAI,
//                        new Version());
//                wrapper = moduleDataRequest2(pluginVersionReference,method,parameters);
//            } catch (InvalidParameterException e) {
//                e.printStackTrace();
//            }

            wrapper = new FermatModuleObjectWrapper((FermatModuleObjectInterface) parameters[0].getObject());

            return wrapper;
        }

        @Override
        public boolean isFermatSystemRunning() throws RemoteException {
            return isFermatSystemRunning;
        }


    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind:" + intent.getAction());
        IBinder iBinder = null;
        try {
            switch (intent.getAction()) {
                case IntentServerServiceAction.ACTION_BIND_AIDL:
                    iBinder = mBinder;
                    break;
                case IntentServerServiceAction.ACTION_BIND_MESSENGER:
                    iBinder = mMessenger.getBinder();
                    break;
                default:
                    Log.i(TAG, "onBind defautl");
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "OnCreate");
        fermatSystem = FermatSystem.getInstance();
        try {
            AndroidCoreUtils androidCoreUtils = AndroidCoreUtils.getInstance();
            broadcastManager = new BroadcastManager();
            AndroidCoreUtils.getInstance().setContextAndResume(broadcastManager);
            if (!AndroidCoreUtils.getInstance().isStarted())
                AndroidCoreUtils.getInstance().setStarted(true);
            fermatSystem.start(this.getApplicationContext(), new OSAPlatform(androidCoreUtils));
        } catch (FermatException e) {
            System.err.println(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        GetTask getTask = new GetTask(this, this);
        getTask.setCallBack(this);
        getTask.execute();

        executorService = Executors.newFixedThreadPool(10);
        clients = new HashMap<>();
        socketsClients = new HashMap<>();


        try {
            localServerSocket = new LocalServerSocket(SERVER_NAME);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        executorService.shutdownNow();
        try {
            localServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (LocalServerSocketSession localServerSocketSession : socketsClients.values()) {
            try {
                localServerSocketSession.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }


    private Object moduleDataRequest(final PluginVersionReference pluginVersionReference, final String method, final ModuleObjectParameterWrapper[] parameters) {
//        Log.i(TAG, "Invoque method called");
        Callable<Object> callable = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
//                Log.i(TAG,"Method to execute: "+ method);
//                Log.i(TAG,"PluginVersionReference: "+ pluginVersionReference.toString());
//                Log.i(TAG,"Parameters: "+parameters);
                FermatManager fermatManager = fermatSystem.startAndGetPluginVersion(pluginVersionReference);
                ModuleManager moduleManager = null;
                Class clazz = null;
                if (fermatManager instanceof AbstractModule) {
                    moduleManager = ((AbstractModule) fermatManager).getModuleManager();
                    clazz = moduleManager.getClass();
                } else {
                    clazz = fermatManager.getClass();
                }
                Method m = null;
                Object returnedObject = null;
                Object[] params = null;
                Class<?>[] paramsTypes = null;
                if (parameters != null) {
                    params = new Object[parameters.length];
                    paramsTypes = new Class[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        params[i] = parameters[i].getObject();
                        paramsTypes[i] = parameters[i].getParameterType();
                    }
                }
//                if(parameters!=null) {
//                    classes = new Class[params.length];
//                    for (int pos = 0; pos < params.length; pos++) {
////                        if(params[pos]!=null) classes[pos] = params[pos].getClass();
////                        else Log.e(TAG,"Null parameter on method: "+method);
////                        Log.i(TAG, "Parametro: " + params[pos].getClass().getCanonicalName());
//                    }
//                }
                try {
                    if (paramsTypes == null) {
                        m = clazz.getDeclaredMethod(method, null);
//                        Log.i(TAG,"Method: "+ m.getName());
//                        Log.i(TAG,"Method return generic type: "+ m.getGenericReturnType());
//                        Log.i(TAG,"Method return type: "+ m.getReturnType());
                        returnedObject = m.invoke(moduleManager, null);
                    } else {
                        try {
//                            for(Class c : classes){
//                                Log.i(TAG,"Class to use for parameter: "+ c.getName());
//                            }
                            m = clazz.getDeclaredMethod(method, paramsTypes);
                        } catch (NoSuchMethodException e) {
                            //Log.e(TAG,"Metodo buscando: "+method);
                            for (Method methodInterface : clazz.getDeclaredMethods()) {
                                if (methodInterface.getName().equals(method)) {
                                    m = methodInterface;
                                }

                            }
                        }
                        if (moduleManager != null) {
                            if (m != null) {
                                returnedObject = m.invoke(moduleManager, params);
                            } else {
                                for (Method method1 : moduleManager.getClass().getSuperclass().getDeclaredMethods()) {
                                    if (method1.getName().equals(method)) {
                                        try {
                                            returnedObject = method1.invoke(moduleManager, params);
                                        } catch (Exception e) {
                                            returnedObject = e;
                                        }
                                        break;
                                    }
                                }

                            }
                        } else {
                            Log.e(TAG, "NOT FOUND ModuleManger for this pluginVersionRefence:" + pluginVersionReference.toString());
                        }
                    }

                    if (m != null) {
                        if(!m.getReturnType().equals(Void.TYPE) && returnedObject==null){
                            Log.i(TAG, "Object returned null in method: "+method+" from plugin: "+pluginVersionReference.toString3()+" please check the module if this is not what you expected");
                        }
                    }

                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "NoSuchMethodException:" + method + " on class" + clazz.getName());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    return e.getTargetException();
//                    return e;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    return e;
                }
                return returnedObject;
            }
        };

        Future<Object> future = executorService.submit(callable);

        Object s = null;
        try {
            s = future.get();

//            Log.i(TAG,"Invoque method return: "+ s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if(s!=null) {
//            Log.i(TAG, "Data to send: "+ s.toString());
//        }else{
//            Log.i(TAG, "Data to send: null, check this");
//        }

        return s;
    }


    @Override
    public void onPostExecute(Object... result) {
        try {

            final FermatSystem fermatSystem = ApplicationSession.getInstance().getFermatSystem();

            PlatformInfoManager platformInfoManager = (PlatformInfoManager) fermatSystem.startAndGetAddon(
                    new AddonVersionReference(
                            Platforms.PLUG_INS_PLATFORM,
                            Layers.PLATFORM_SERVICE,
                            Addons.PLATFORM_INFO,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );

            setPlatformDeviceInfo(platformInfoManager);
        } catch (CantGetAddonException | VersionNotFoundException e) {

            System.out.println(e.toString());
        }

        // Indicate that app was loaded.
        isFermatSystemRunning = true;
        Intent intent = new Intent();
        intent.setAction("org.fermat.SYSTEM_RUNNING");
        sendBroadcast(intent);
    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }

    private void setPlatformDeviceInfo(PlatformInfoManager platformInfoManager) {
        try {
            PlatformInfo platformInfo = platformInfoManager.getPlatformInfo();
            platformInfo.setScreenSize(getScreenSize());
            platformInfoManager.setPlatformInfo(platformInfo);
        } catch (
                CantSetPlatformInformationException | com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantLoadPlatformInformationException e) {
            e.printStackTrace();
        }
    }

    private ScreenSize getScreenSize() {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return DeviceInfoUtils.toScreenSize(dpHeight, dpWidth);

    }

    /**
     * Messenger
     */

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            Log.d(TAG, "Received from service: " + msg.arg1);
            final Bundle data = msg.getData();
            try {
                switch (msg.what) {
                    case CommunicationMessages.MSG_REGISTER_CLIENT:
                        final String clientKey = msg.getData().getString(CommunicationDataKeys.DATA_PUBLIC_KEY);
                        registerClient(clientKey, msg.replyTo);
                        if (msg.getData().containsKey(CommunicationDataKeys.DATA_SOCKET_STARTED)) {
                            serverThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        LocalSocket localSocket = localServerSocket.accept();
                                        localSocket.setSendBufferSize(500000);
                                        LocalServerSocketSession localServerSocketSession = new LocalServerSocketSession(clientKey, localSocket);
                                        socketsClients.put(clientKey, localServerSocketSession);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            serverThread.start();
                        }
                        break;
                    case CommunicationMessages.MSG_UNREGISTER_CLIENT:
                        unRegisterClient(msg.getData().getString(CommunicationDataKeys.DATA_PUBLIC_KEY));
                        break;
                    case CommunicationMessages.MSG_REQUEST_DATA_MESSAGE:
                        break;
                    default:
                        Log.i(TAG, "Incoming handler default");
                        super.handleMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void registerClient(String key, Messenger replyTo) {
        if (key != null) {
            clients.put(key, replyTo);
        }
    }

    private void unRegisterClient(String key) {
        clients.remove(key);
    }


    /**
     * Methods to delete
     */

    @Override
    public void notificateBroadcast(String appCode, String code) {

    }

    @Override
    public void notificateBroadcast(String appCode, FermatBundle bundle) {

    }

    @Override
    public int notificateProgressBroadcast(FermatBundle bundle) {
        return 0;
    }

}
