package com.bitdubai.fermat_android_api.engine;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

/**
 * Created by mati on 2016.02.26..
 */
public interface FermatAppsManager {

    FermatStructure lastAppStructure();
    FermatSession lastAppSession();
    RuntimeManager selectRuntimeManager(FermatAppType fermatAppType);

    boolean isAppOpen(String appPublicKey);

    FermatSession getAppsSession(String appPublicKey);

    FermatSession openApp(FermatApp fermatApp,AppConnections fermatAppConnection);

    FermatApp getApp(String publicKey,FermatAppType fermatAppType) throws Exception;

    FermatStructure getAppStructure(String appPublicKey, FermatAppType appType);
}
