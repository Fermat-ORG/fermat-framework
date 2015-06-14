package com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._16_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.*;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.DeviceUserLoggedInEvent;

import java.util.UUID;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedInEventHandler implements EventHandler {

    WalletManager walletManager;

    public void setWalletManager (WalletManager walletManager){
        this.walletManager = walletManager;
    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception{

        UUID userId = ((DeviceUserLoggedInEvent) platformEvent).getUserId();


        if (((Service) this.walletManager).getStatus() == ServiceStatus.STARTED) {

            try
            {
                this.walletManager.loadUserWallets(userId);
            }
            catch (CantLoadWalletsException cantLoadWalletsException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantLoadUserWalletsException: " + cantLoadWalletsException.getMessage());
                cantLoadWalletsException.printStackTrace();

                throw cantLoadWalletsException;
            }
        }
        else
        {
            throw new ModuleNotRunningException();
        }

    }
}
