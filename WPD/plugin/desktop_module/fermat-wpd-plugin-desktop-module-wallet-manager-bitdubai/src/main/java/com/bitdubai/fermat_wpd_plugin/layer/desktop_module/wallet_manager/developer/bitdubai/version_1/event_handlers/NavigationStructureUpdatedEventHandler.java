package com.bitdubai.fermat_wpd_plugin.layer.desktop_module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantEnableWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;

/**
 * Created by loui on 19/02/15.
 */
public class NavigationStructureUpdatedEventHandler implements FermatEventHandler {
    
    WalletManager walletManager;

    public void setWalletManager (WalletManager walletManager){
        this.walletManager = walletManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (((Service) this.walletManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                this.walletManager.enableWallet();
            }
            catch (CantEnableWalletException cantEnableWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateCryptoWalletException: "+ cantEnableWalletException.getMessage());
                cantEnableWalletException.printStackTrace();

                throw  cantEnableWalletException;
            }
        }
        else
        {
            throw new ModuleNotRunningException();
        }
    }
}
