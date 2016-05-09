package com.bitdubai.android_core.app.common.version_1.apps_manager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.android_core.app.common.version_1.recents.RecentApp;
import com.bitdubai.android_core.app.common.version_1.recents.RecentAppComparator;
import com.bitdubai.android_core.app.common.version_1.sessions.FermatSessionManager;
import com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils;
import com.bitdubai.fermat_android_api.engine.FermatRecentApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_module.AppManager;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getSubAppRuntimeMiddleware;
import static com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils.getWalletRuntimeManager;

/**
 * Created by Matias Furszyfer on 2016.02.26..
 */
//TODO: esta clase es la cual se encargará de manejar la creación de una aplicación fermat, manejo de sesiones, ubicacion en la stack (para el back button o para ver la lista de apps activas),
    // obtener conexiones, etc
//TODO: falta agregar el tema de cargar el AppsConfig cuando se incia la app por primera vez

public class FermatAppsManagerService extends Service implements com.bitdubai.fermat_android_api.engine.FermatAppsManager {

    private static final String TAG = "AppsManagerService";

    private Map<String,RecentApp> recentsAppsStack;
    private FermatSessionManager fermatSessionManager;
    private HashMap<String,FermatAppType> appsInstalledInDevice = new HashMap<>();
    // Binder given to clients
    private final IBinder localBinder = new AppManagerLocalBinder();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class AppManagerLocalBinder extends Binder {
        public FermatAppsManagerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return FermatAppsManagerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.recentsAppsStack = new HashMap<>();
        this.fermatSessionManager = new FermatSessionManager();
        init();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String key = intent.getStringExtra(AppManagerKeys.AUTENTIFICATION_CLIENT_KEY);
        return localBinder;
    }

    public void init(){
        AppsConfiguration appsConfiguration = new AppsConfiguration(this);
        appsInstalledInDevice = appsConfiguration.readAppsCoreInstalled();
        //if(appsInstalledInDevice.isEmpty()){
            appsInstalledInDevice = appsConfiguration.updateAppsCoreInstalled();
        //}
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
                int pos = ((RecentApp) o).getTaskStackPosition()+1;
                return ((RecentApp) o).getTaskStackPosition()+1 == recentsAppsStack.size();
            }
        });
    }


    @Override
    public List<FermatRecentApp> getRecentsAppsStack() {
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
        try {
            if (fermatSessionManager.isSessionOpen(appPublicKey)) {
                orderStackWithThisPkLast(appPublicKey);
                return fermatSessionManager.getAppsSession(appPublicKey);
            } else {
                return openApp(
                        selectAppManager(
                                appsInstalledInDevice.get(appPublicKey)).getApp(appPublicKey),
                        FermatAppConnectionManager.getFermatAppConnection(
                                appPublicKey,ApplicationSession.getInstance().getApplicationContext()
                        )
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private void orderStackWithThisPkLast(String publicKey){
        RecentApp recentApp = recentsAppsStack.get(publicKey);
        recentsAppsStack.remove(publicKey);
        for(RecentApp r : recentsAppsStack.values()){
            r.setTaskStackPosition(r.getTaskStackPosition()-1);
        }
        recentApp.setTaskStackPosition(recentsAppsStack.size());
        recentsAppsStack.put(publicKey,recentApp);
    }

    @Override
    public FermatSession openApp(FermatApp fermatApp, AppConnections fermatAppConnection) {
        if(fermatApp!=null) {
            if (recentsAppsStack.containsKey(fermatApp.getAppPublicKey())) {
//            recentsAppsStack.get(fermatApp.getAppPublicKey()).setTaskStackPosition(recentsAppsStack.size());
                orderStackWithThisPkLast(fermatApp.getAppPublicKey());
            } else {
                recentsAppsStack.put(fermatApp.getAppPublicKey(), new RecentApp(fermatApp.getAppPublicKey(), fermatApp, recentsAppsStack.size()));
            }
            return openSession(fermatApp, fermatAppConnection);
        }
        return null;
    }

    private FermatSession openSession(FermatApp fermatApp,AppConnections fermatAppConnection){
        FermatSession fermatSession = null;
        if(fermatSessionManager.isSessionOpen(fermatApp.getAppPublicKey())){
            fermatSession = fermatSessionManager.getAppsSession(fermatApp.getAppPublicKey());
        }else {
//            ModuleManager moduleManager = null;
//            try {
//                moduleManager = ApplicationSession.getInstance().getServicesHelpers().getClientSideBrokerServiceAIDL().getModuleManager(fermatAppConnection.getPluginVersionReference());
//            } catch (CantCreateProxyException e) {
//                e.printStackTrace();
//            }
//            fermatSession = fermatSessionManager.openAppSession(fermatApp, FermatSystemUtils.getErrorManager(), moduleManager, fermatAppConnection);
            fermatSession = fermatSessionManager.openAppSession(fermatApp, FermatSystemUtils.getErrorManager(), FermatSystemUtils.getModuleManager(fermatAppConnection.getPluginVersionReference()), fermatAppConnection);
        }
        fermatAppConnection.setFullyLoadedSession(fermatSession);
        return fermatSession;
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
            openApp(fermatApp,FermatAppConnectionManager.getFermatAppConnection(fermatApp.getAppPublicKey(),ApplicationSession.getInstance().getApplicationContext()));
        }

        orderStackWithThisPkLast(publicKey);
        return fermatApp;
    }

    @Override
    public FermatApp getApp(String appPublicKey) throws Exception {
        FermatApp fermatApp = null;
        if(recentsAppsStack.containsKey(appPublicKey)){
            fermatApp = recentsAppsStack.get(appPublicKey).getFermatApp();
        }else{
            fermatApp = selectAppManager(appsInstalledInDevice.get(appPublicKey)).getApp(appPublicKey);
        }
        return fermatApp;
    }

    @Override
    public FermatStructure getAppStructure(String appPublicKey, FermatAppType appType) {
        return selectRuntimeManager(appType).getAppByPublicKey(appPublicKey);
    }

    @Override
    public FermatStructure getAppStructure(String appPublicKey) {
        FermatAppType fermatAppType =appsInstalledInDevice.get(appPublicKey);
        if(fermatAppType!=null) {
            return selectRuntimeManager(fermatAppType).getAppByPublicKey(appPublicKey);
        }else{
            Log.e(TAG,"App instaled in device null: "+appPublicKey);
            Log.e(TAG,"If the public key of the app is fine, try removing data and restart app. filesystem problem..");
            return null;
        }
    }

    @Override
    public FermatStructure getLastAppStructure() {
        RecentApp recentApp = findLastElement();
        return selectRuntimeManager(recentApp.getFermatApp().getAppType()).getLastApp();
    }


    @Override
    public void clearRuntime() {
        try {
            if (getWalletRuntimeManager().getLastWallet() != null)
                getWalletRuntimeManager().getLastWallet().clear();

            if (getSubAppRuntimeMiddleware().getLastApp() != null)
                getSubAppRuntimeMiddleware().getLastApp().clear();

        }catch (Exception e){
            e.printStackTrace();
        }
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
        //Este swith debe ser cambiado por una petición al core pasandole el FermatAppType
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
            case P2P_APP:
                runtimeManager = FermatSystemUtils.getP2PApssRuntimeManager();
                break;
        }
        return runtimeManager;
    }


}
