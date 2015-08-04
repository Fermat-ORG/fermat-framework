package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.exceptions.CantRecordClosedWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_runtime.exceptions.CantRecordOpenedWalletException;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.WalletClosedEvent;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public class WalletClosedEventHandler implements EventHandler {
    WalletRuntimeManager walletRuntimeManager;

    public void setWalletRuntimeManager(WalletRuntimeManager walletRuntimeManager) {
        this.walletRuntimeManager = walletRuntimeManager;
    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        UUID walletId = ((WalletClosedEvent)platformEvent).getWalletId();


        if (((Service) this.walletRuntimeManager).getStatus() == ServiceStatus.STARTED) {

            try
            {
                this.walletRuntimeManager.recordClosedWallet(walletId);
            }
            catch (CantRecordClosedWalletException cantRecordClosedWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */

                String defaultMessage = cantRecordClosedWalletException.DEFAULT_MESSAGE;
                FermatException exception = FermatException.wrapException(cantRecordClosedWalletException);
                String contex = "Wallet Closed Event Handler - handleEvent method: " + cantRecordClosedWalletException.getContext();
                String possibleReason = "the exception is thrown when calling 'this.walletRuntimeManager.recordClosedWallet (walletId)': " + cantRecordClosedWalletException.getPossibleReason();
                //cantRecordClosedWalletException = new CantRecordClosedWalletException(defaultMessage,exception,"","");

                throw new CantRecordClosedWalletException(defaultMessage,exception,contex,possibleReason);
            }
            catch (Exception exception){

                throw new CantRecordOpenedWalletException(CantRecordOpenedWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception),null,null);
            }
        }
    }
}
