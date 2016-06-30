package com.bitdubai.android_core.app.common.version_1.runtime_estructure_manager;

import android.widget.Toast;

import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2016.02.13..
 */
public class RuntimeStructureManager implements FermatRuntime {

    private WeakReference<FermatActivity> fermatActivity;

    public RuntimeStructureManager(FermatActivity fermatActivity) {
        this.fermatActivity = new WeakReference(fermatActivity);
    }

    @Override
    public void changeActivityBack(String appBackPublicKey, String activityCode) {
        try {
            FermatApplication.getInstance().getAppManager().getLastAppStructure().getLastActivity().changeBackActivity(appBackPublicKey,activityCode);
        }catch (InvalidParameterException e) {
            e.printStackTrace();
            Toast.makeText(fermatActivity.get().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void changeStartActivity(String activityCode){
        try {
            FermatStructure fermatStructure = FermatApplication.getInstance().getAppManager().getLastAppStructure();
            fermatStructure.changeActualStartActivity(activityCode);
            FermatApplication.getInstance().getAppManager().selectRuntimeManager(FermatApplication.getInstance().getAppManager().getApp(fermatStructure.getPublicKey()).getAppType()).recordNAvigationStructure(fermatStructure);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(fermatActivity.get().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }



    public void clear(){
        fermatActivity.clear();
    }


}
