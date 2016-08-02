package com.bitdubai.android_core.app.common.version_1.apps_manager;

import android.content.Context;

import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Matias Furszyfer on 2016.03.05..
 */
public class AppsConfiguration {

    private final WeakReference<FermatAppsManagerService> fermatAppsManager;

    private final String APPS_CONFIGURATION_FILE = "installed_app_configuration.txt";

    public AppsConfiguration(FermatAppsManagerService fermatAppsManager) {
        this.fermatAppsManager = new WeakReference<FermatAppsManagerService>(fermatAppsManager) ;
    }

    public HashMap<String, FermatAppType> readAppsCoreInstalled(){
        try{
            Context context = FermatApplication.getInstance().getApplicationContext();
            FileInputStream fIn = context.openFileInput(APPS_CONFIGURATION_FILE);
            ObjectInputStream isr = new ObjectInputStream(fIn);
            return (HashMap<String, FermatAppType>) isr.readObject();
        } catch (FileNotFoundException fileNotFoundException){
            updateAppsCoreInstalled();
        } catch (IOException ioe) {
            updateAppsCoreInstalled();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            updateAppsCoreInstalled();
        }
        return new HashMap<>();
    }

    public HashMap<String,FermatAppType>  updateAppsCoreInstalled(){
        HashMap<String,FermatAppType> appsInstalledInDevice = new HashMap<>();
        // Aplicaciones instaladas en el dispoanae sitivo separadas por tipo
        for (FermatAppType fermatAppType : FermatAppType.values()) {
            RuntimeManager runtimeManager = fermatAppsManager.get().selectRuntimeManager(fermatAppType);
            if(runtimeManager != null)
                for (String key : fermatAppsManager.get().selectRuntimeManager(fermatAppType).getListOfAppsPublicKey()) {
                    appsInstalledInDevice.put(key,fermatAppType);
                }
        }
        Context context = FermatApplication.getInstance().getApplicationContext();
        try {
            FileOutputStream fOut = context.openFileOutput(APPS_CONFIGURATION_FILE,
                    Context.MODE_PRIVATE);
            ObjectOutputStream osw = new ObjectOutputStream(fOut);
            osw.writeObject(appsInstalledInDevice);
            osw.flush();
            osw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return appsInstalledInDevice;
    }




}
