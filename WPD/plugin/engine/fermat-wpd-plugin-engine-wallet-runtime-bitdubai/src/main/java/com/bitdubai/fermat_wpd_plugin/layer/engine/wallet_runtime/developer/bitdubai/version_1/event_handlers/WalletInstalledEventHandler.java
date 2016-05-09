package com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletInstalledEvent;

/**
 * Created by Matias Furszyfer
 */
public class WalletInstalledEventHandler implements FermatEventHandler {
    WalletRuntimeManager walletRuntimeManager;

    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        String publicKey = ((WalletInstalledEvent) fermatEvent).getPublicKey();


        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {

            //TODO: Hablar con luis que más quiere que haga cuando se instala la wallet

//            try {
//                this.walletRuntimeManager.recordNavigationStructure(publicKey);
//            }
//            catch (CantRecordInstalledWalletNavigationStructureException cantRecordInstalledWalletNavigationStructureException){
//                /**
//                 * The main module could not handle this exception. Me neither. Will throw it again.
//                 */
//                System.err.println("CantRecordInstalledWalletNavigationStructureException: " + cantRecordInstalledWalletNavigationStructureException.getMessage());
//                cantRecordInstalledWalletNavigationStructureException.printStackTrace();
//
//                throw cantRecordInstalledWalletNavigationStructureException;
//
//            }

        }
    }
}
