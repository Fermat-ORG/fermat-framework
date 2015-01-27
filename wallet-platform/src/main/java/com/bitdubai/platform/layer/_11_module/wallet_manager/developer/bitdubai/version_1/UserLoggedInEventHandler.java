package com.bitdubai.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.platform.layer._11_module.ModuleService;
import com.bitdubai.platform.layer._11_module.ModuleNotRunningException;
import com.bitdubai.platform.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.platform.layer._11_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.platform.layer._11_module.wallet_manager.WalletManager;
import com.bitdubai.platform.layer._2_event.manager.EventHandler;
import com.bitdubai.platform.layer._1_definition.event.PlatformEvent;
import com.bitdubai.platform.layer._2_event.manager.UserLoggedInEvent;

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


        if (((ModuleService) this.walletManager).getStatus() == ServiceStatus.RUNNING) {

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
