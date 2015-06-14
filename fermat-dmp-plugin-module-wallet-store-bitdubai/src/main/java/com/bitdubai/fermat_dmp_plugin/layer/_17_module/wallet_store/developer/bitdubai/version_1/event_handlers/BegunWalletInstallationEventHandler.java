package com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_store.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._16_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.layer._16_module.wallet_store.exceptions.CantRecordInstalledWalletException;
import com.bitdubai.fermat_api.layer._16_module.wallet_store.WalletStoreManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.BegunWalletInstallationEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class BegunWalletInstallationEventHandler implements EventHandler {
    WalletStoreManager walletStoreManager;

    public void setWalletStoreManager(WalletStoreManager walletStoreManager){
        this.walletStoreManager = walletStoreManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        
        UUID walletId = ((BegunWalletInstallationEvent)platformEvent).getWalletId();


        if(((Service) this.walletStoreManager).getStatus() == ServiceStatus.STARTED) {
            
            try
            {
                this.walletStoreManager.recordInstalledWallet(walletId);
            }
            catch (CantRecordInstalledWalletException cantRecordInstalledWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantRecordInstalledWalletException: " + cantRecordInstalledWalletException.getMessage());
                cantRecordInstalledWalletException.printStackTrace();

                throw cantRecordInstalledWalletException;
                
            }
            
        }
        else
        {
            throw new ModuleNotRunningException();
        }
    }
}
