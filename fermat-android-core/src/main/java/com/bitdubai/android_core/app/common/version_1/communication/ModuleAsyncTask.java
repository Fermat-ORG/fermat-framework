package com.bitdubai.android_core.app.common.version_1.communication;

import android.os.AsyncTask;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_core.FermatSystem;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by mati on 2016.04.08..
 */
public class ModuleAsyncTask extends AsyncTask<Object,Integer,Object> {

    private static final String TAG = "AsyncTask";

    private final PluginVersionReference pluginVersionReference;
    private final String method;
    private final Serializable parameters;
    private final FermatSystem fermatSystem;
    private WeakReference<CommunicationService> communicationServiceWeakReference;
    private String requestId;
    private String requestKey;
    private final Messenger replyTo;


    public ModuleAsyncTask(CommunicationService communicationService, FermatSystem fermatSystem,Messenger replyTo, String requestId, String requestKey, PluginVersionReference pluginVersionReference, String method, Serializable parameters) {
        this.pluginVersionReference = pluginVersionReference;
        this.method = method;
        this.parameters = parameters;
        this.fermatSystem = fermatSystem;
        communicationServiceWeakReference = new WeakReference<CommunicationService>(communicationService);
        this.requestId = requestId;
        this.requestKey = requestKey;
        this.replyTo = replyTo;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(TAG, "Started");
        Log.i(TAG,"Method to execute: "+ method);
        Log.i(TAG, "PluginVersionReference: " + pluginVersionReference.toString());
        Log.i(TAG,"Parameters: "+parameters);
    }

    @Override
    protected Object doInBackground(Object... objs) {
        try {
            Log.i(TAG, "Invoque method called");
            FermatManager fermatManager = fermatSystem.getPlugin(pluginVersionReference);
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
            return s;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (VersionNotFoundException e) {
            e.printStackTrace();
        } catch (CantGetModuleManagerException e) {
            e.printStackTrace();
        } catch (CantStartPluginException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try {
            communicationServiceWeakReference.get().send(replyTo,requestId,requestKey,o);
            communicationServiceWeakReference.get().processingQueue--;
            Log.i(TAG, "Processiong request queue:" + communicationServiceWeakReference.get().processingQueue);
            clear();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clear(){
        communicationServiceWeakReference.clear();
    }


}
