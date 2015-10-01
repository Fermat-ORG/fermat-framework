package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_community.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_community.interfaces.WalletCommunityManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;


/**
 * Created by loui on 19/02/15.
 */
public class FinishedWalletInstallationEventHandler implements FermatEventHandler {
    WalletCommunityManager walletCommunityManager;

    public void setWalletCommunityManager(WalletCommunityManager walletCommunityManager){
        this.walletCommunityManager = walletCommunityManager;
    }
    
    
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        EventSource eventSource = fermatEvent.getSource();
        if (eventSource == EventSource.MIDDLEWARE_WALLET_PLUGIN) {


            if (((Service) this.walletCommunityManager).getStatus() == ServiceStatus.STARTED) {
         
         
         
            }
        }
    }
}
