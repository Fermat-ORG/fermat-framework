package com.bitdubai.fermat_android_api.engine;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;

/**
 * Created by mati on 2016.02.26..
 */
public interface FermatAppsManager {

    FermatStructure lastAppStructure();
    FermatSession lastAppSession();
    RuntimeManager selectRuntimeManager(FermatAppType fermatAppType);
}
