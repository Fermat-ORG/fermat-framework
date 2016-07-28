package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public interface ReferenceAppFermatSession<M extends ModuleManager> extends FermatSession {

    /**
     * Devuelve el module manager
     */
    M getModuleManager();

}
