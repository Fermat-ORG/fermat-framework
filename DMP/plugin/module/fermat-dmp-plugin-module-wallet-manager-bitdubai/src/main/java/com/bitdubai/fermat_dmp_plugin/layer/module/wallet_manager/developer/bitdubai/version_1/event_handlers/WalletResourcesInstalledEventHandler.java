package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer._16_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEventHandler implements EventHandler {
    WalletManager walletManager;

    public void setWalletManager (WalletManager walletManager){
        this.walletManager = walletManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {

    }
}
