package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEventHandler implements FermatEventHandler {
    WalletManager walletManager;

    public void setWalletManager (WalletManager walletManager){
        this.walletManager = walletManager;
    }
    
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

    }
}
