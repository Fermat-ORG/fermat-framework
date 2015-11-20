package com.bitdubai.fermat_wpd_plugin.layer.desktop_module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantCreateDefaultWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.DeviceUserCreatedEvent;

/**
 * Created by ciencias on 26.01.15.
 */
public class UserCreatedEventHandler  implements FermatEventHandler {

    WalletManager walletManager;

    public void setWalletManager (WalletManager walletManager){
        this.walletManager = walletManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException{

        String deviceUserPublicKey = ((DeviceUserCreatedEvent) fermatEvent).getPublicKey();


        if (((Service) this.walletManager).getStatus() == ServiceStatus.STARTED) {

            try
            {
                this.walletManager.createDefaultWallets(deviceUserPublicKey);
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
