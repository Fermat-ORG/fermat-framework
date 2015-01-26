package com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._11_module.ModuleNotRunningException;
import com.bitdubai.smartwallet.platform.layer._11_module.ModuleService;
import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.CantCreateWalletException;
import com.bitdubai.smartwallet.platform.layer._11_module.wallet_manager.WalletManager;
import com.bitdubai.smartwallet.platform.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.smartwallet.platform.layer._1_definition.event.PlatformEvent;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventHandler;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.developer.UserCreatedEvent;

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


        if (((ModuleService) this.walletManager).getStatus() == ServiceStatus.RUNNING) {

            try
            {
                this.walletManager.createDefaultWallets(userId);
            }
            catch (CantCreateWalletException cantCreateWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateWalletException: " + cantCreateWalletException.getMessage());
                cantCreateWalletException.printStackTrace();

                throw cantCreateWalletException;
            }
        }
        else
        {
            throw new ModuleNotRunningException();
        }

    }
}
