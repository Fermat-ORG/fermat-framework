package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public interface FermatSession<A extends FermatApp,M extends ModuleManager>{

    /**
     *
     * @param key
     * @param object
     */
    void setData(String key, Object object);

    /**
     *
     * @param key
     * @return
     */
    Object getData(String key);

    /**
     *
     */
    void removeData(String key);

    /**
     *
     * @return
     */
    ErrorManager getErrorManager();


    String getAppPublicKey();

    List<FermatApp> getPosibleConnections();

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
