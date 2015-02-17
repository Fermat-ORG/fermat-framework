package com.bitdubai.wallet_platform_core.layer._9_network_service.wallet_resources.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._9_network_service.CantCheckResourcesException;
import com.bitdubai.wallet_platform_api.layer._9_network_service.NetworkService;


/**
 * Created by loui on 17/02/15.
 */
public class BegunWalletInstallationEventHandler implements EventHandler {
    NetworkService networkService;
    
    public void setNetworkService(NetworkService networkService){
        this.networkService = networkService;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        /*
        if (((Service) this.networkService).getStatus() == ServiceStatus.STARTED) {
            
            try
            {
                this.networkService.checkResources();                
            }
            catch (CantCheckResourcesException cantCheckResourcesException)
            {
                System.err.println("CantCheckResourcesException: " + cantCheckResourcesException.getMessage());
                cantCheckResourcesException.printStackTrace();

                throw cantCheckResourcesException;
            }
        }
        else
        {
            throw new CantCheckResourcesException();
        }
    */
    }
}
