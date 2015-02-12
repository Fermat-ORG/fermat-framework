package com.bitdubai.wallet_platform_draft.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._11_module.ModuleNotRunningException;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.wallet_platform_api.layer._11_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.wallet_platform_api.layer._11_module.wallet_manager.WalletManager;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.*;

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
    public void raiseEvent(PlatformEvent platformEvent) throws Exception{

        UUID userId = ((UserLoggedInEvent) platformEvent).getUserId();


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
