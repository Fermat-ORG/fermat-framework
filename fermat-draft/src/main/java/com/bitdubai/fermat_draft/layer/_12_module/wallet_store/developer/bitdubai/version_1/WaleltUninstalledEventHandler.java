package com.bitdubai.fermat_plugin.layer._14_module.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._14_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.layer._14_module.wallet_store.CantRecordUninstalledWalletException;
import com.bitdubai.fermat_api.layer._14_module.wallet_store.WalletStore;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WaleltUninstalledEventHandler implements EventHandler {
    WalletStoreManM walletStore;
    
    public void setWalletStore (WalletStore walletStore){
        this.walletStore = walletStore;
    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) throws Exception {

        UUID walletId = ((WalletUninstalledEvent)platformEvent).getWalletId();


        if(((Service) this.walletStore).getStatus() == ServiceStatus.STARTED){

            try
            {
                this.walletStore.recordUninstalledwallet(walletId);
            }
            catch (CantRecordUninstalledWalletException cantRecordUninstalledWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantRecordInstalledWalletException: " + cantRecordUninstalledWalletException.getMessage());
                cantRecordUninstalledWalletException.printStackTrace();

                throw cantRecordUninstalledWalletException;

            }

        }
        else
        {
            throw new ModuleNotRunningException();
        }
    }
}
