package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public interface FermatSession{

    /**
     *
     * @param key
     * @param object
     */
    public void setData (String key,Object object);

    /**
     *
     * @param key
     * @return
     */
    public Object getData (String key);

    /**
     *
     * @return
     */
    public ErrorManager getErrorManager();


    String getAppPublicKey();
}
