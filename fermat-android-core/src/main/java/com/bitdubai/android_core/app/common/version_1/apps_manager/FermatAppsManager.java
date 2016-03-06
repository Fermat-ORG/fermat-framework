package com.bitdubai.android_core.app.common.version_1.apps_manager;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.common.version_1.recents.RecentApp;
import com.bitdubai.android_core.app.common.version_1.sessions.FermatSessionManager;
import com.bitdubai.android_core.app.common.version_1.util.FermatSystemUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ModuleManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import java.util.Stack;

/**
 * Created by Matias Furszyfer on 2016.02.26..
 */
//TODO: esta clase es la cual se encargará de manejar la creación de una aplicación fermat, manejo de sesiones, ubicacion en la stack (para el back button o para ver la lista de apps activas),
    // obtener conexiones, etc

public class FermatAppsManager implements com.bitdubai.fermat_android_api.engine.FermatAppsManager {

    private Stack<RecentApp> recentsAppsStack;
    private FermatSessionManager fermatSessionManager;


    public FermatAppsManager() {
        this.recentsAppsStack = new Stack<>();
        this.fermatSessionManager = new FermatSessionManager();
    }

    public void addApp(String appPublickKey){
        recentsAppsStack.push(new RecentApp(appPublickKey));
    }

    public FermatStructure lastAppStructure(){
        String appPublicKey = recentsAppsStack.peek().getPublicKey();
        return selectRuntimeManager(fermatSessionManager.getAppsSession(appPublicKey).getFermatApp().getAppType()).getLastApp();
    }

    @Override
    public FermatSession lastAppSession() {
        String appPublicKey = recentsAppsStack.peek().getPublicKey();
        return fermatSessionManager.getAppsSession(appPublicKey);
    }

    public Stack<RecentApp> getRecentsAppsStack() {
        return recentsAppsStack;
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

    @Override
    public boolean isAppOpen(String appPublicKey) {
        return recentsAppsStack.contains(new RecentApp(appPublicKey));
    }

    @Override
    public FermatSession getAppsSession(String appPublicKey) {
        return fermatSessionManager.getAppsSession(appPublicKey);
    }

    @Override
    public FermatSession openApp(FermatApp fermatApp,AppConnections fermatAppConnection) {
        recentsAppsStack.push(
                new RecentApp(
                        fermatApp.getAppPublicKey(),
                        fermatApp.getAppName(),
                        fermatApp.getAppStatus(),
                        fermatApp.getAppType()));
        return fermatSessionManager.openAppSession(fermatApp,FermatSystemUtils.getErrorManager(),getModuleManager(fermatAppConnection.getPluginVersionReference()),fermatAppConnection);
    }



    /**
     *  Return an instance of module manager
     * @param pluginVersionReference
     * @return
     */
    public ModuleManager getModuleManager(PluginVersionReference pluginVersionReference){
        try {
            return ApplicationSession.getInstance().getFermatSystem().getModuleManager(pluginVersionReference);
        } catch (ModuleManagerNotFoundException | CantGetModuleManagerException e) {
            System.err.println(e.getMessage());
            System.err.println(e.toString());
            return null;
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }

}
