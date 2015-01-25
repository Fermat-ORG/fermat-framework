package com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._11_module.ModuleNotRunningException;
import com.bitdubai.smartwallet.platform.layer._11_module.ModuleStatus;
import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.CantLoadUserWalletsException;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventHandler;
import com.bitdubai.smartwallet.platform.layer._1_definition.event.PlatformEvent;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.UserLoggedInEvent;

import java.util.UUID;

/**
 * Created by ciencias on 24.01.15.
 */
public class UserLoggedInEventHandler implements EventHandler {

    WalletManagerModule walletManager;

    public void setWalletManager (WalletManagerModule walletManager){
        this.walletManager = walletManager;
    }

    @Override
    public void raiseEvent(PlatformEvent platformEvent) throws Exception{

        UUID userId = ((UserLoggedInEvent) platformEvent).getUserId();


        if (this.walletManager.getStatus() == ModuleStatus.RUNNING) {

            try
            {
                this.walletManager.loadUserWallets(userId);
            }
            catch (CantLoadUserWalletsException cantLoadUserWalletsException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantLoadUserWalletsException: " + cantLoadUserWalletsException.getMessage());
                cantLoadUserWalletsException.printStackTrace();

                throw cantLoadUserWalletsException;
            }
        }
        else
        {
            throw new ModuleNotRunningException();
        }

    }
}
