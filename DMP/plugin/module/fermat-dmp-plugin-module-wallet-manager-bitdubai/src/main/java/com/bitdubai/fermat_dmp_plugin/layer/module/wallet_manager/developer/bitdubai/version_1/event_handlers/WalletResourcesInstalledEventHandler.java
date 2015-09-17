package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEventHandler implements EventHandler {
    WalletManager walletManager;

    public void setWalletManager (WalletManager walletManager){
        this.walletManager = walletManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {

    }
}
