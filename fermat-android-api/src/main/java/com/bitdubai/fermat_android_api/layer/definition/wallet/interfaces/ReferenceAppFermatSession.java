package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public interface ReferenceAppFermatSession<A extends FermatApp,M extends ModuleManager> extends FermatSession{

    /**
     * Devuelve el tipo de la app
     *
     * @return
     */
    A getFermatApp();


    /**
     * Devuelve el module manager
     */
    M getModuleManager();

    /**
     *  Devuelve la identidad activa
     */
    //I getIdentity();

}
