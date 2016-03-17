package com.bitdubai.android_core.app.common.version_1.communication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_core.FermatSystem;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by MAtias Furszyfer on 2016.03.09..
 */
//TODO: le tengo que poner un timeout para desconectar los clientes y no abusen de Fermat, seguramente se pueda controlar esto por un token que me envia
    //TODO:   haciendo que paguen una cierta cantidad de satoshis por utilizar Fermat como servicio en background

public class CommunicationService extends Service{

    private static final String TAG = "CommService";

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
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "Received from service: " + msg.arg1);
            try {
                switch (msg.what) {
                    case CommunicationMessages.MSG_REGISTER_CLIENT:
                        registerClient(msg.getData().getString(CommunicationDataKeys.DATA_PUBLIC_KEY), msg.replyTo);
                        break;
                    case CommunicationMessages.MSG_UNREGISTER_CLIENT:
                        unRegisterClient(msg.getData().getString(CommunicationDataKeys.DATA_PUBLIC_KEY));
                        break;
                    case CommunicationMessages.MSG_REQUEST_DATA_MESSAGE:
                        send(msg.getData().getString(CommunicationDataKeys.DATA_PUBLIC_KEY),
                                moduleDataRequest(
                                        (PluginVersionReference) msg.getData().getSerializable(CommunicationDataKeys.DATA_PLUGIN_VERSION_REFERENCE),
                                        msg.getData().getString(CommunicationDataKeys.DATA_METHOD_TO_EXECUTE),
                                        (Serializable[]) msg.getData().getSerializable(CommunicationDataKeys.DATA_PARAMS_TO_EXECUTE_METHOD))
                        );
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }catch (RemoteException e){
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void registerClient(String key, Messenger replyTo){
        clients.put(key,replyTo);
    }

    private void unRegisterClient(String key){
        clients.remove(key);
    }

    private String moduleDataRequest(final PluginVersionReference pluginVersionReference,final String method, Serializable[] parameters){
        Log.i(TAG,"Invoque method called");
        Callable<Object> callable = new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                FermatManager fermatManager = fermatSystem.getPlugin(pluginVersionReference);
                Class clazz = fermatManager.getClass();

                Method m = null;
                Object s = null;
                try {
                    m = clazz.getMethod(method,null);
                    Log.i(TAG,"Method: "+ m.getName());
                    Log.i(TAG,"Method return generic type: "+ m.getGenericReturnType());
                    Log.i(TAG,"Method return type: "+ m.getReturnType());
                    s =  m.invoke(fermatManager, null);
                    Log.i(TAG,"Method return: "+ s.toString());
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

        Log.i("APP", s != null ? s.toString() : null);
        Gson gson = new Gson();
        String json = gson.toJson(s);
        Log.i("APP", s != null ? s.toString() : null);

        return json;
    }


    private void send(String key,Serializable serializable) throws RemoteException {
        Message msg = Message.obtain(null, CommunicationMessages.MSG_REQUEST_DATA_MESSAGE);
        msg.getData().putSerializable(CommunicationDataKeys.DATA_KEY_TO_RESPONSE,serializable);
        clients.get(key).send(msg);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fermatSystem = FermatSystem.getInstance();
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    boolean mBound;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "entering onBind");
        return mMessenger.getBinder();
    }
}
