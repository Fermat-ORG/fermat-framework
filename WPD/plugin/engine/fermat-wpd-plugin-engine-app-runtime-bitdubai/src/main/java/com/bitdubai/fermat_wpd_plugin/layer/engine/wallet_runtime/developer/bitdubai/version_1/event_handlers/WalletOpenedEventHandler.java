package com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_wpd_api.all_definition.events.WalletOpenedEvent;

/**
 * Created by Matias Furszyfer
 */
public class WalletOpenedEventHandler implements FermatEventHandler {
    WalletRuntimeManager walletRuntimeManager;

    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        String walletId = ((WalletOpenedEvent) fermatEvent).getWalletPublicKey();


        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {

//            try
//            {
//                this.walletRuntimeManager.recordNavigationStructure(walletId);
//            }
//            catch (CantRecordInstalledWalletNavigationStructureException cantRecordOpenedWalletException) {
//                /**
//                 * The main module could not handle this exception. Me neither. Will throw it again.
//                 */
//                throw cantRecordOpenedWalletException;
//
//            }

        }
    }
}
