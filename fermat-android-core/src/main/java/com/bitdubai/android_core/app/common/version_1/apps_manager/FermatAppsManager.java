package com.bitdubai.android_core.app.common.version_1.apps_manager;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.common.version_1.recents.RecentApp;
import com.bitdubai.android_core.app.common.version_1.recents.RecentAppComparator;
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
import com.bitdubai.fermat_api.layer.dmp_module.AppManager;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2016.02.26..
 */
//TODO: esta clase es la cual se encargará de manejar la creación de una aplicación fermat, manejo de sesiones, ubicacion en la stack (para el back button o para ver la lista de apps activas),
    // obtener conexiones, etc

public class FermatAppsManager implements com.bitdubai.fermat_android_api.engine.FermatAppsManager {

    private Map<String,RecentApp> recentsAppsStack;
    private FermatSessionManager fermatSessionManager;


    public FermatAppsManager() {
        this.recentsAppsStack = new HashMap<>();
        this.fermatSessionManager = new FermatSessionManager();
    }

    public FermatStructure lastAppStructure() {
        return selectRuntimeManager(findLastElement().getFermatApp().getAppType()).getLastApp();
    }

    @Override
    public FermatSession lastAppSession() {
        return fermatSessionManager.getAppsSession(findLastElement().getPublicKey());
    }

    private RecentApp findLastElement(){
        return (RecentApp) CollectionUtils.find(recentsAppsStack.values(), new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((RecentApp) o).getTaskStackPosition() == recentsAppsStack.size();
            }
        });
    }


    public List<RecentApp> getRecentsAppsStack() {
        //falta ordenar esto por posicion
        ArrayList list = new ArrayList(recentsAppsStack.values());
        Collections.sort(list,new RecentAppComparator());
        return list;
    }


    @Override
    public boolean isAppOpen(String appPublicKey) {
        return recentsAppsStack.containsKey(appPublicKey);
    }

    @Override
    public FermatSession getAppsSession(String appPublicKey) {
        if(fermatSessionManager.isSessionOpen(appPublicKey)){
            return fermatSessionManager.getAppsSession(appPublicKey);
        }else{
            //todo: tengo que armar un archivo que contenga las aplicaciones que estan instaladas y su tipo para poder ir a buscarlas a donde corresponda
            //openApp();
            return null;
        }

    }

    @Override
    public FermatSession openApp(FermatApp fermatApp, AppConnections fermatAppConnection) {
        if(recentsAppsStack.containsKey(fermatApp.getAppPublicKey())){
            recentsAppsStack.get(fermatApp.getAppPublicKey()).setTaskStackPosition(recentsAppsStack.size());
        }else{
            recentsAppsStack.put(fermatApp.getAppPublicKey(), new RecentApp(fermatApp.getAppPublicKey(),fermatApp,recentsAppsStack.size()));
        }

        if(fermatSessionManager.isSessionOpen(fermatApp.getAppPublicKey())){
            return fermatSessionManager.getAppsSession(fermatApp.getAppPublicKey());
        }else {
            return fermatSessionManager.openAppSession(fermatApp, FermatSystemUtils.getErrorManager(), getModuleManager(fermatAppConnection.getPluginVersionReference()), fermatAppConnection);
        }
    }

    /**
     * aca no solo la obtengo si no que la tengo que poner arriba del stack de apps
     * @param publicKey
     * @return
     */
    @Override
    public FermatApp getApp(String publicKey,FermatAppType fermatAppType) throws Exception {
        FermatApp fermatApp = null;
        if(recentsAppsStack.containsKey(publicKey)){
            fermatApp = recentsAppsStack.get(publicKey).getFermatApp();
        }else{
            fermatApp = selectAppManager(fermatAppType).getApp(publicKey);
        }
        return fermatApp;
    }

    @Override
    public FermatStructure getAppStructure(String appPublicKey, FermatAppType appType) {
        return selectRuntimeManager(appType).getAppByPublicKey(appPublicKey);
    }


    /**
     * Search app in every app manager in fermat (module)
     *
     * @param fermatAppType
     * @return
     */
    public AppManager selectAppManager(FermatAppType fermatAppType){
        AppManager appManager = null;
        switch (fermatAppType) {
            case WALLET:
                appManager = FermatSystemUtils.getWalletManager();
                break;
            case SUB_APP:
                appManager = FermatSystemUtils.getSubAppManager();
                break;
            case DESKTOP:
                appManager = FermatSystemUtils.getDesktopManager();
                break;
        }
        return appManager;
    }

    /**
     *
     * Search runtime manager in fermat
     *
     * @param fermatAppType
     * @return
     */
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
