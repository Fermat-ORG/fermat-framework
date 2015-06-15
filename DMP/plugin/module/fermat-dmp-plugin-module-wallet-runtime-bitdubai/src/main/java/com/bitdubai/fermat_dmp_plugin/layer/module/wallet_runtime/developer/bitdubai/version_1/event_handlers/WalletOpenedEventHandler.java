package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._16_module.wallet_runtime.exceptions.CantRecordOpenedWalletException;
import com.bitdubai.fermat_api.layer._16_module.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.WalletOpenedEvent;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletOpenedEventHandler implements EventHandler {
    WalletRuntimeManager walletRuntimeManager;
    
    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        UUID walletId = ((WalletOpenedEvent)platformEvent).getWalletId();
        
        
        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {
            
            try
            {
                this.walletRuntimeManager.recordOpenedWallet(walletId);
            }
            catch (CantRecordOpenedWalletException cantRecordOpenedWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantRecordOpenedWalletException: " + cantRecordOpenedWalletException.getMessage());
                cantRecordOpenedWalletException.printStackTrace();

                throw cantRecordOpenedWalletException;

            }
            
        } 
    }
}
