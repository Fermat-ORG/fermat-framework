package com.bitdubai.fermat_core.layer._11_middleware.wallet.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_middleware.MiddlewareNotStartedException;
import com.bitdubai.fermat_api.layer._11_middleware.WalletManager;
import com.bitdubai.fermat_api.layer._11_middleware.wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.events.WalletCreatedEvent;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public class WalletCreatedEventHandler implements EventHandler {

    WalletManager walletManager;

    public void setWalletmanager ( WalletManager walletManager){
        this.walletManager = walletManager;
    }

    @Override
    public  void handleEvent(PlatformEvent platformEvent) throws Exception {
        UUID walletId = ((WalletCreatedEvent) platformEvent).getWalletId();


        if (((Service) this.walletManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                this.walletManager.createWallet(walletId);
            }
            catch (CantCreateWalletException cantCreateWalletException)
            {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */
                System.err.println("CantCreateCryptoWalletException: "+ cantCreateWalletException.getMessage());
                cantCreateWalletException.printStackTrace();

                throw  cantCreateWalletException;
            }
        }
        else
        {
            throw new MiddlewareNotStartedException();
        }
    }
}
