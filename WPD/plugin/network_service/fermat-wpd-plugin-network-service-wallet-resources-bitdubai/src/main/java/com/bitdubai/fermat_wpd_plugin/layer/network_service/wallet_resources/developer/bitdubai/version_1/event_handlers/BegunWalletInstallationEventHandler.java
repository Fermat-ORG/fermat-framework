package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesInstalationManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;


/**
 * Created by loui on 17/02/15.
 */
public class BegunWalletInstallationEventHandler implements FermatEventHandler {
    WalletResourcesInstalationManager walletResourcesInstalationManager;
    
    public void setWalletResourcesInstalationManager(WalletResourcesInstalationManager walletResourcesInstalationManager){
        this.walletResourcesInstalationManager = walletResourcesInstalationManager;
    }
    
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {




        if (((Service) this.walletResourcesInstalationManager).getStatus() == ServiceStatus.STARTED) {

            //TODO: por ahora comentado, testeando mati
//            try
//            {
//                this.walletResourcesInstalationManager.checkResources();
//            }
//            catch (CantCheckResourcesException cantCheckResourcesException)
//            {
//                /**
//                 * The main module could not handle this exception. Me neither. Will throw it again.
//                 */
//
//                throw cantCheckResourcesException;
//            }
        }
        else
        {
            throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES:",null,"Error intalled wallet resources fields" , "");
        }

    }
}
