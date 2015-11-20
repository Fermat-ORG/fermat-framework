package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin;

/**
 * Created by eze on 2015.07.18..
 */
public interface WalletStoreSkin extends Skin{
    public InstallationStatus getInstallationStatus();
}
