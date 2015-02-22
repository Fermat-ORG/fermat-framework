package com.bitdubai.fermat_core.layer._10_network_service.wallet_community.developer.bitdubai.version_1.EventHandlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._10_network_service.wallet_community.WalletCommunityManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventSource;

/**
 * Created by loui on 19/02/15.
 */
public class FinishedWalletInstallationEventHandler implements EventHandler {
    WalletCommunityManager walletCommunityManager;

    public void setWalletCommunityManager(WalletCommunityManager walletCommunityManager){
        this.walletCommunityManager = walletCommunityManager;
    }
    
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        EventSource eventSource = platformEvent.getSource();
        if (eventSource == EventSource.MIDDLEWARE_WALLET_PLUGIN) {


            if (((Service) this.walletCommunityManager).getStatus() == ServiceStatus.STARTED) {
         
         
         
            }
        }
    }
}
