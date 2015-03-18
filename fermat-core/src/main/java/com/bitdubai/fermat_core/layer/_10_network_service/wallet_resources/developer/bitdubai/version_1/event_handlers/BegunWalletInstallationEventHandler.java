package com.bitdubai.fermat_core.layer._10_network_service.wallet_resources.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._10_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._10_network_service.wallet_resources.WalletResourcesManager;


/**
 * Created by loui on 17/02/15.
 */
public class BegunWalletInstallationEventHandler implements EventHandler {
    WalletResourcesManager walletResourcesManager;
    
    public void setWalletResourcesManager(WalletResourcesManager walletResourcesManager){
        this.walletResourcesManager = walletResourcesManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {




        if (((Service) this.walletResourcesManager).getStatus() == ServiceStatus.STARTED) {

            try
            {
                this.walletResourcesManager.checkResources();
            }
            catch (CantCheckResourcesException cantCheckResourcesException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateDefaultWalletsException: " + cantCheckResourcesException.getMessage());
                cantCheckResourcesException.printStackTrace();

                throw cantCheckResourcesException;
            }
        }
        else
        {
            throw new CantCheckResourcesException();
        }

    }
}
