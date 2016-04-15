package com.bitdubai.android_core.app.common.version_1.runtime_estructure_manager;

import android.widget.Toast;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

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
            selectRuntimeManager().getLastApp().getLastActivity().changeBackActivity(appBackPublicKey,activityCode);
        }catch (InvalidParameterException e) {
            e.printStackTrace();
            Toast.makeText(fermatActivity.get().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void changeStartActivity(String activityCode){
        try {
            FermatStructure fermatStructure = ApplicationSession.getInstance().getAppManager().getLastAppStructure();
            fermatStructure.changeActualStartActivity(activityCode);
            selectRuntimeManager().recordNAvigationStructure(fermatStructure);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(fermatActivity.get().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_LONG).show();
        }
    }

    public RuntimeManager selectRuntimeManager(){
        RuntimeManager runtimeManager = null;
        switch (fermatActivity.get().getType()) {
            case ACTIVITY_TYPE_WALLET:
                runtimeManager = FermatSystemUtils.getWalletRuntimeManager();
                break;
            case ACTIVITY_TYPE_SUB_APP:
                runtimeManager = FermatSystemUtils.getSubAppRuntimeMiddleware();
                break;
            case ACTIVITY_TYPE_DESKTOP:
                break;
        }
        return runtimeManager;
    }


    public void clear(){
        fermatActivity.clear();
    }


}
