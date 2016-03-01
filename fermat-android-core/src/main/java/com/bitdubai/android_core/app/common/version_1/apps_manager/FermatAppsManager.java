package com.bitdubai.android_core.app.common.version_1.apps_manager;

import android.content.Context;

import com.bitdubai.android_core.app.common.version_1.sessions.FermatSessionManager;
import com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Created by Matias Furszyfer on 2016.02.26..
 */
//TODO: esta clase es la cual se encargará de manejar la creación de una aplicación fermat, manejo de sesiones, ubicacion en la stack (para el back button o para ver la lista de apps activas),
    // obtener conexiones, etc

public class FermatAppsManager implements com.bitdubai.fermat_android_api.engine.FermatAppsManager {

    private WeakReference<Context> applicationContext;
    private Stack<String> openAppsStack;
    private FermatSessionManager fermatSessionManager;


    public FermatAppsManager(Context applicationContext) {
        this.applicationContext = new WeakReference<>(applicationContext);
        this.openAppsStack = new Stack<>();
        this.fermatSessionManager = new FermatSessionManager();
    }

    public void addApp(String appPublickKey){
        openAppsStack.push(appPublickKey);
    }

    public FermatStructure lastAppStructure(){
        String appPublicKey = openAppsStack.peek();
        return selectRuntimeManager(fermatSessionManager.getAppsSession(appPublicKey).getFermatApp().getAppType()).getLastApp();
    }

    public FermatSession lastAppSession(){
        String appPublicKey = openAppsStack.peek();
        return fermatSessionManager.getAppsSession(appPublicKey);
    }


    public RuntimeManager selectRuntimeManager(FermatAppType fermatAppType){
        RuntimeManager runtimeManager = null;
        switch (fermatAppType) {
            case WALLET:
                runtimeManager = FermatSystemUtils.getWalletRuntimeManager();
                break;
            case SUB_APP:
                runtimeManager = FermatSystemUtils.getSubAppRuntimeMiddleware();
                break;
            case DESKTOP:
                runtimeManager = FermatSystemUtils.getDesktopRuntimeManager();
                break;
        }
        return runtimeManager;
    }

    @Deprecated
    public FermatSessionManager getFermatSessionManager() {
        return fermatSessionManager;
    }
}
