package com.bitdubai.wallet_platform_core.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._11_module.ModuleNotRunningException;
import com.bitdubai.wallet_platform_api.PlatformService;
import com.bitdubai.wallet_platform_api.layer._11_module.wallet_manager.CantCreateDefaultWalletsException;
import com.bitdubai.wallet_platform_api.layer._11_module.wallet_manager.WalletManager;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventHandler;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.UserCreatedEvent;

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
    public void raiseEvent(PlatformEvent platformEvent) throws Exception{

        UUID userId = ((UserCreatedEvent) platformEvent).getUserId();


        if (((PlatformService) this.walletManager).getStatus() == ServiceStatus.STARTED) {

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
