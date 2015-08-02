package com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRecordInstalledWalletNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRecordOpenedWalletException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletInstalledEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletOpenedEvent;

import java.util.UUID;

/**
 * Created by Matias Furszyfer
 */
public class WalletInstalledEventHandler implements EventHandler {
    WalletRuntimeManager walletRuntimeManager;
    
    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        String publicKey = ((WalletInstalledEvent)platformEvent).getPublicKey();
        
        
        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {
            
            try {
                this.walletRuntimeManager.recordNavigationStructure(publicKey);
            }
            catch (CantRecordInstalledWalletNavigationStructureException cantRecordInstalledWalletNavigationStructureException){
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantRecordInstalledWalletNavigationStructureException: " + cantRecordInstalledWalletNavigationStructureException.getMessage());
                cantRecordInstalledWalletNavigationStructureException.printStackTrace();

                throw cantRecordInstalledWalletNavigationStructureException;

            }
            
        } 
    }
}
