package com.bitdubai.android_core.app.common.version_1.communication;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_core.FermatSystem;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Matias Furszyfer on 2016.03.09..
 */
//TODO: le tengo que poner un timeout para desconectar los clientes y no abusen de Fermat, seguramente se pueda controlar esto por un token que me envia
    //TODO:   haciendo que paguen una cierta cantidad de satoshis por utilizar Fermat como servicio en background

public class CommunicationService extends Service{

    private static final String TAG = "CommService";

    public int processingQueue = 0;
    /**
     * Clients connected
     */
    private Map<String, Messenger> clients;

    /**
     * Fermat background service
     *
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
                        registerClient(msg.getData().getString(CommunicationDataKeys.DATA_PUBLIC_KEY), msg.replyTo);
                        break;
                    case CommunicationMessages.MSG_UNREGISTER_CLIENT:
                        unRegisterClient(msg.getData().getString(CommunicationDataKeys.DATA_PUBLIC_KEY));
                        break;
                    case CommunicationMessages.MSG_REQUEST_DATA_MESSAGE:
                        processingQueue++;
                        Log.i(TAG,"Processiong request queue:"+processingQueue);
                            executorService.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        moduleDataRequest2(
                                                msg.replyTo,
                                                data.getString(CommunicationDataKeys.DATA_REQUEST_ID),
                                                data.getString(CommunicationDataKeys.DATA_PUBLIC_KEY),
                                                (PluginVersionReference) data.getSerializable(CommunicationDataKeys.DATA_PLUGIN_VERSION_REFERENCE),
                                                data.getString(CommunicationDataKeys.DATA_METHOD_TO_EXECUTE),
                                                data.getSerializable(CommunicationDataKeys.DATA_PARAMS_TO_EXECUTE_METHOD));

                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });

                        break;
                    default:
                        super.handleMessage(msg);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void registerClient(String key, Messenger replyTo){
        if(key!=null) {
            clients.put(key, replyTo);
        }
    }

    private void unRegisterClient(String key){
        clients.remove(key);
    }

    private void moduleDataRequest2(Messenger replyTo, final String requestId, final String requestKey, final PluginVersionReference pluginVersionReference, final String method, final Serializable parameters){
        new ModuleAsyncTask(this,fermatSystem,replyTo,requestId,requestKey,pluginVersionReference,method,parameters).execute();
    }


    private Object moduleDataRequest(final PluginVersionReference pluginVersionReference,final String method, final Serializable parameters){
        Log.i(TAG,"Invoque method called");
        Callable<Object> callable = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Log.i(TAG,"Method to execute: "+ method);
                Log.i(TAG,"PluginVersionReference: "+ pluginVersionReference.toString());
                Log.i(TAG,"Parameters: "+parameters);
                FermatManager fermatManager = fermatSystem.startAndGetPluginVersion(pluginVersionReference);
                ModuleManager moduleManager = null;
                Class clazz = null;
                if(fermatManager instanceof AbstractModule){
                    moduleManager = ((AbstractModule) fermatManager).getModuleManager();
                    clazz = moduleManager.getClass();
                }else{
                    clazz = fermatManager.getClass();
                }
                Method m = null;
                Object s = null;
                Class[] classes = null;
                Object[] params = (Object[]) parameters;
                if(parameters!=null) {
                    classes = new Class[params.length];
                    for (int pos = 0; pos < params.length; pos++) {
                        classes[pos] = params[pos].getClass();
                        Log.i(TAG, "Parametro: " + params[pos].getClass().getCanonicalName());
                    }
                }
                //TODO: ver porque puse el moduleManager en el invoque, si daberia id ahí o d
                try {
                    if(classes==null){
                        m = clazz.getMethod(method,null);
                        Log.i(TAG,"Method: "+ m.getName());
                        Log.i(TAG,"Method return generic type: "+ m.getGenericReturnType());
                        Log.i(TAG,"Method return type: "+ m.getReturnType());
                        s =  m.invoke(moduleManager, null);
                    } else{
                        m = clazz.getMethod(method,classes);
                        Log.i(TAG,"Method: "+ m.getName());
                        Log.i(TAG,"Method return generic type: "+ m.getGenericReturnType());
                        Log.i(TAG,"Method return type: "+ m.getReturnType());
                        s =  m.invoke(moduleManager,params);
                    }
                    if(s!=null){
                        Log.i(TAG,"Method return: "+ s.toString());
                    }else{
                        Log.i(TAG,"Method return: null, check this");
                    }

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return s;
            }
        };

        Future<Object> future = executorService.submit(callable);

        Object s = null;
        try {
            s = future.get();
            Log.i(TAG,"Invoque method return: "+ s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(s!=null) {
            Log.i(TAG, "Data to send: "+ s.toString());
        }else{
            Log.i(TAG, "Data to send: null, check this");
        }
        final Object finalS = s;
//        Future<String> gsonFuture = executorService.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                Gson gson = new Gson();
//                String json = gson.toJson(finalS);
//                return json;
//            }
//        });
//        String json = null;
//        try {
//            json = gsonFuture.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        if(s!=null){
            Log.i(TAG,"Method return: "+ s.toString());
        }else{
            Log.i(TAG,"Method return: null, check this");
        }

//        return json;
        return finalS;
    }


    public void send(Messenger replyTo,String id, String key, Object object) throws RemoteException {
        Log.i(TAG,"Sending data to:"+ clients.get(key));
        Log.i(TAG,"Sending data: "+ object);
//        if(object!=null) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (Method method : object.getClass().getDeclaredMethods()) {
//                stringBuilder.append(method.getName()).append("\n");
//            }
//
//            Log.i(TAG, "Data methods: " + stringBuilder.toString());
//
//            Log.i(TAG, "Data name: " + object.getClass().getCanonicalName());
//        }
        Message msg = Message.obtain(null, CommunicationMessages.MSG_REQUEST_DATA_MESSAGE);
        if(object instanceof Serializable){
            msg.getData().putSerializable(CommunicationDataKeys.DATA_KEY_TO_RESPONSE, (Serializable) object);
            msg.getData().putString(CommunicationDataKeys.DATA_REQUEST_ID, id);
        }else{
            msg.getData().putSerializable(CommunicationDataKeys.DATA_KEY_TO_RESPONSE, null);
            msg.getData().putString(CommunicationDataKeys.DATA_REQUEST_ID, id);
            Log.i(TAG, "Data is not serializable");
        }


        if(replyTo!=null){
            replyTo.send(msg);
        }else{
            Log.i(TAG, "ReplyTo is null, CHECK THIS.");
            Messenger messenger = clients.get(key);
            if(messenger!=null){
                clients.get(key).send(msg);
            }else{
                Log.i(TAG, "Client is not in map");
            }

        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        fermatSystem = FermatSystem.getInstance();
        executorService = Executors.newFixedThreadPool(10);
        clients = new HashMap<>();

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "entering onBind");
        return mMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        executorService.shutdownNow();
    }
}
