package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

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



}
