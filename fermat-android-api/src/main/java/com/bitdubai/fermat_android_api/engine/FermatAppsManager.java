package com.bitdubai.fermat_android_api.engine;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

import java.util.List;

/**
 * Created by mati on 2016.02.26..
 */
public interface FermatAppsManager {

    /**
     * Method to initialize the fermatManager
     */
    void init();

    /**
     * Get the last app structure
     *
     * @return
     */
    FermatStructure lastAppStructure();

    /**
     * Get the last app session
     *
     * @return
     */
    FermatSession lastAppSession();


    RuntimeManager selectRuntimeManager(FermatAppType fermatAppType);

    boolean isAppOpen(String appPublicKey);

    FermatSession getAppsSession(String appPublicKey, boolean isForSubSession);

    FermatSession getAppsSession(String appPublicKey);

    FermatSession openApp(FermatApp fermatApp, AppConnections fermatAppConnection) throws Exception;

    FermatApp getApp(String publicKey, FermatAppType fermatAppType) throws Exception;

    FermatApp getApp(String appPublicKey) throws Exception;

    FermatStructure getAppStructure(String appPublicKey, FermatAppType appType);

    FermatStructure getAppStructure(String appPublicKey);

    FermatStructure getLastAppStructure();

    List<FermatRecentApp> getRecentsAppsStack();

    /**
     * Method to clear the runtime navigation structure
     */
    void clearRuntime();
}
