package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;
import com.bitdubai.android_core.app.common.version_1.util.AndroidCoreUtils;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.module_object_creator.FermatModuleObjectInterface;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_osa_android_core.OSAPlatform;

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
 * Created by mati on 2016.04.18..
 */
public class CommunicationServerService extends Service {

    private static String TAG = "CommunicationServerService";

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

    private final IServerBrokerService.Stub mBinder = new IServerBrokerService.Stub() {


        @Override
        public FermatModuleObjectWrapper invoqueModuleMethod(String platformCode, String layerCode, String pluginsCode, String developerCode, String version, String method, FermatModuleObjectWrapper[] parameters) throws RemoteException {
            Log.i(TAG,"invoqueModuleMethod");
            Log.i(TAG,platformCode);
            Log.i(TAG,layerCode);
            Log.i(TAG,pluginsCode);
            Log.i(TAG,version);
            Log.i(TAG,method);
            Log.i(TAG,"Parameters");
            for (FermatModuleObjectWrapper parameter : parameters) {
                Log.i(TAG, parameter.toString());
            }
            Serializable aidlObject = null;
            try {
                PluginVersionReference pluginVersionReference = new PluginVersionReference(
                        Platforms.getByCode(platformCode),
                        Layers.getByCode(layerCode),
                        Plugins.getByCode(pluginsCode),
                        Developers.BITDUBAI,
                        new Version());
                aidlObject = moduleDataRequest(pluginVersionReference,method,parameters);
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }


            return new FermatModuleObjectWrapper(aidlObject);
        }

        @Override
        public FermatModuleObjectWrapper invoqueModuleMethod2(String platformCode, String layerCode, String pluginsCode, String developerCode, String version, String method, FermatModuleObjectWrapper[] parameters) throws RemoteException {
            Log.i(TAG,"invoqueModuleMethod");
            Log.i(TAG,platformCode);
            Log.i(TAG,layerCode);
            Log.i(TAG,pluginsCode);
            Log.i(TAG,version);
            Log.i(TAG,method);
            Log.i(TAG,"Parameters");
            for (FermatModuleObjectWrapper parameter : parameters) {
                Log.i(TAG, parameter.toString());
            }
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
        fermatSystem = FermatSystem.getInstance();
        try {
            AndroidCoreUtils androidCoreUtils = AndroidCoreUtils.getInstance();
//            AndroidCoreUtils.getInstance().setContextAndResume(this);
            fermatSystem.start(this.getApplicationContext(), new OSAPlatform(androidCoreUtils));
        } catch (FermatException e) {

            System.err.println(e.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService = Executors.newFixedThreadPool(10);
        clients = new HashMap<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        executorService.shutdownNow();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }


    private Serializable moduleDataRequest(final PluginVersionReference pluginVersionReference,final String method, final FermatModuleObjectWrapper[]  parameters){
        Log.i(TAG, "Invoque method called");
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
                Object[] params = null;
                if(parameters!=null){
                    params = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        params[i] = parameters[i].getObject();
                    }
                }
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
                        try{
                            m = clazz.getMethod(method,classes);
                        }catch (NoSuchMethodException e){
                            for(Class interfaces : clazz.getInterfaces()){
                                try {
                                    m = interfaces.getMethod(method, classes);
                                }catch (NoSuchMethodException e1){
                                    Log.e(TAG,"metodo no encontrado");
                                }
                            }
                        }
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
                    Log.e(TAG,"NoSuchMethodException:"+method+" on class"+clazz.getName());
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
        } catch (Exception e){
            e.printStackTrace();
        }

        if(s!=null) {
            Log.i(TAG, "Data to send: "+ s.toString());
        }else{
            Log.i(TAG, "Data to send: null, check this");
        }
        final Serializable finalS = (Serializable) s;
        if(s!=null){
            Log.i(TAG,"Method return: "+ s.toString());
        }else{
            Log.i(TAG,"Method return: null, check this");
        }
        return finalS;
    }


}
