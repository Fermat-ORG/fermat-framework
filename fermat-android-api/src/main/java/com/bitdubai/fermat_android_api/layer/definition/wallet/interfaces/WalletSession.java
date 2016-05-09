package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSession extends FermatSession{


    /**
     *
     */
    WalletSettings getWalletSettings();

    /**
     *
     */
    ErrorManager getErrorManager();


    String getIdentityConnection();



}
