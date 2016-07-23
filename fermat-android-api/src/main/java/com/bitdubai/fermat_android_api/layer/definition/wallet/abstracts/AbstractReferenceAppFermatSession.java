package com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public class AbstractReferenceAppFermatSession<A extends FermatApp, M extends ModuleManager, R extends ResourceProviderManager> extends BaseFermatSession<A, R> implements ReferenceAppFermatSession<M> {

    private M moduleManager;

    public AbstractReferenceAppFermatSession(String publicKey, A fermatApp, ErrorManager errorManager, M moduleManager, R resourceProviderManager) {
        super(publicKey, fermatApp, resourceProviderManager, errorManager);
        this.moduleManager = moduleManager;
    }

    public AbstractReferenceAppFermatSession() {
    }

    public M getModuleManager() {
        return moduleManager;
    }

    public void setModuleManager(M moduleManager) {
        this.moduleManager = moduleManager;
    }

}
