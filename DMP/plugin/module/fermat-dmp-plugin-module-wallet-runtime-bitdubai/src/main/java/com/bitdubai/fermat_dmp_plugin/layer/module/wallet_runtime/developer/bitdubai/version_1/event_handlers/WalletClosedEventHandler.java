package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._16_module.wallet_runtime.exceptions.CantRecordClosedWalletException;
import com.bitdubai.fermat_api.layer._16_module.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.WalletClosedEvent;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletClosedEventHandler implements EventHandler{
    WalletRuntimeManager walletRuntimeManager;

    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        UUID walletId = ((WalletClosedEvent)platformEvent).getWalletId();


        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {

            try
            {
                this.walletRuntimeManager.recordClosedWallet(walletId);
            }
            catch (CantRecordClosedWalletException cantRecordClosedWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantRecordClosedWalletException: " + cantRecordClosedWalletException.getMessage());
                cantRecordClosedWalletException.printStackTrace();

                throw cantRecordClosedWalletException;

            }

        }
    }
}
