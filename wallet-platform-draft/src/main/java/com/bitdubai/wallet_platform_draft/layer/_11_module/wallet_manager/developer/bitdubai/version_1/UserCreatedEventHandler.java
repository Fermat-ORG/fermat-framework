package com.bitdubai.wallet_platform_draft.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._11_module.ModuleNotRunningException;
import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._11_module.wallet_manager.CantCreateDefaultWalletsException;
import com.bitdubai.wallet_platform_api.layer._11_module.wallet_manager.WalletManager;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.*;

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
