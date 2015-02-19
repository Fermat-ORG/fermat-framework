package com.bitdubai.wallet_platform_core.layer._10_network_service.wallet_community.developer.bitdubai.version_1.EventHandlers;

import com.bitdubai.wallet_platform_api.layer._10_network_service.wallet_community.WalletCommunityManager;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 19/02/15.
 */
public class FinishedWalletinstallationEventHandler implements EventHandler {
    WalletCommunityManager walletCommunityManager;
    
    public void setWalletCommunityManager(WalletCommunityManager walletCommunityManager){
        this.walletCommunityManager = walletCommunityManager;
    }
    
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        
    }
}
