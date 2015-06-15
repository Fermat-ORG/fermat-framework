package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer._16_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantCreateDefaultWalletsException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.DeviceUserCreatedEvent;

import java.util.UUID;

/**
 * Created by ciencias on 26.01.15.
 */
public class UserCreatedEventHandler  implements EventHandler {

    WalletManager walletManager;

    public void setWalletManager (WalletManager walletManager){
        this.walletManager = walletManager;
    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception{

        UUID userId = ((DeviceUserCreatedEvent) platformEvent).getUserId();


        if (((Service) this.walletManager).getStatus() == ServiceStatus.STARTED) {

            try
            {
                this.walletManager.createDefaultWallets(userId);
            }
            catch (CantCreateDefaultWalletsException cantCreateDefaultWalletsException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateDefaultWalletsException: " + cantCreateDefaultWalletsException.getMessage());
                cantCreateDefaultWalletsException.printStackTrace();

                throw cantCreateDefaultWalletsException;
            }
        }
        else
        {
            throw new ModuleNotRunningException();
        }

    }
}
