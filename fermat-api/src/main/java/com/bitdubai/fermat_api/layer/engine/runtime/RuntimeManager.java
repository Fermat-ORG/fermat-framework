package com.bitdubai.fermat_api.layer.engine.runtime;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;

import java.util.Set;

/**
 * The class <code>com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public interface RuntimeManager extends FermatManager {
    void recordNAvigationStructure(FermatStructure fermatStructure);

    FermatStructure getLastApp();

    FermatStructure getAppByPublicKey(String appPublicKey);

    /**
     * This method will be removed when each app could be installed from the app store
     *
     * @return
     */
    Set<String> getListOfAppsPublicKey();
}
