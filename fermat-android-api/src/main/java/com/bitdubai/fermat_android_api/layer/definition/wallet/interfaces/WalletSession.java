package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSession{


    /**
     *
     */
    public WalletSettings getWalletSettings();

    /**
     *
     */
    ErrorManager getErrorManager();

    String getAppPublicKey();

    void setData(String key,Object value);

    String getIdentityConnection();

}
