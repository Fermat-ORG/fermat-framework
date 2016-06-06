package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * Created by Matias Furszyfer on 2016.06.02..
 */
public interface FermatSession {

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

}
