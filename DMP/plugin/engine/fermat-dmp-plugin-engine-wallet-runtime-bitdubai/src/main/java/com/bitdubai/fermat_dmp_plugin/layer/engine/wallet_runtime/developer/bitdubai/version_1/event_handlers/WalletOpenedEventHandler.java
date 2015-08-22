package com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletOpenedEvent;

/**
 * Created by Matias Furszyfer
 */
public class WalletOpenedEventHandler implements EventHandler {
    WalletRuntimeManager walletRuntimeManager;
    
    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        String walletId = ((WalletOpenedEvent)platformEvent).getWalletPublicKey();
        
        
        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {
            
//            try
//            {
//                this.walletRuntimeManager.recordNavigationStructure(walletId);
//            }
//            catch (CantRecordInstalledWalletNavigationStructureException cantRecordOpenedWalletException) {
//                /**
//                 * The main module could not handle this exception. Me neither. Will throw it again.
//                 */
//                throw cantRecordOpenedWalletException;
//
//            }
            
        } 
    }
}
