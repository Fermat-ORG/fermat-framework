package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSession extends FermatSession{

    /**
     *
     * @return
     */
    public InstalledWallet getWalletSessionType();

    /**
     *
     * @return
     */
    public WalletResourcesProviderManager getWalletResourcesProviderManager();

    /**
     *
     */
    public WalletSettings getWalletSettings();


}
