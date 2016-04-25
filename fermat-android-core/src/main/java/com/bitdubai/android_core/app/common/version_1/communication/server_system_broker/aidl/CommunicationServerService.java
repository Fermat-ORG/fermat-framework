package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure.FermatModuleObjectWrapper;
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
import com.bitdubai.fermat_api.layer.all_definition.util.DeviceInfoUtils;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.module_object_creator.FermatModuleObjectInterface;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_osa_android_core.OSAPlatform;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantSetPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;

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
public class CommunicationServerService extends Service implements FermatWorkerCallBack {

    private static String TAG = "CommunicationServerService";

    public int processingQueue = 0;
    /**
     * Clients connected
     */
    private Map<String, Messenger> clients;

    private boolean isFermatSystemRunning = false;

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

        @Override
        public boolean isFermatSystemRunning() throws RemoteException {
            return isFermatSystemRunning;
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

        GetTask getTask = new GetTask(this,this);
        getTask.setCallBack(this);
        getTask.execute();

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
                        m = clazz.getDeclaredMethod(method, null);
                        Log.i(TAG,"Method: "+ m.getName());
                        Log.i(TAG,"Method return generic type: "+ m.getGenericReturnType());
                        Log.i(TAG,"Method return type: "+ m.getReturnType());
                        s =  m.invoke(moduleManager, null);
                    } else{
                        try{
                            for(Class c : classes){
                                Log.i(TAG,"Class to use for parameter: "+ c.getName());
                            }
                            m = clazz.getDeclaredMethod(method, classes);
                        }catch (NoSuchMethodException e){
                            Log.e(TAG,"Metodo buscando: "+method);
                            int pos = 0;
                            for (Method method1 : clazz.getMethods()) {
                                //Log.e(TAG,pos+": Metodo: "+method1.getName( ));
                                if(method1.getName().equals(method)){
                                    for (Class<?> aClass : method1.getParameterTypes()) {
                                        Log.e(TAG,pos+": Metodo parameters class type: "+aClass.getName());
                                    }
                                }
                            }
                            for(Method methodInterface : clazz.getDeclaredMethods()){
                                if(methodInterface.getName().equals(method)){
                                    m = methodInterface;
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
                } catch (Exception e){
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

    private void setPlatformDeviceInfo(PlatformInfoManager platformInfoManager){
        try {
            PlatformInfo platformInfo = platformInfoManager.getPlatformInfo();
            platformInfo.setScreenSize(getScreenSize());
            platformInfoManager.setPlatformInfo(platformInfo);
        } catch(
                CantSetPlatformInformationException | com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantLoadPlatformInformationException e) {
            e.printStackTrace();
        }
    }

    private ScreenSize getScreenSize(){

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return DeviceInfoUtils.toScreenSize(dpHeight, dpWidth);

    }
}
