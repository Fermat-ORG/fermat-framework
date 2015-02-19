package com.bitdubai.wallet_platform_core.layer._10_network_service.wallet_resources.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._11_network_service.wallet_resources.WalletResourcesManager;


/**
 * Created by loui on 17/02/15.
 */
public class BegunWalletInstallationEventHandler implements EventHandler {
    WalletResourcesManager walletResourcesManager;
    
    public void setNetworkService(WalletResourcesManager walletResourcesManager){
        this.walletResourcesManager = walletResourcesManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {

    }
}
