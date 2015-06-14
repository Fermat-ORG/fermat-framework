package com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._16_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.WalletCreatedEvent;

import java.util.UUID;

/**
 * Created by loui on 19/02/15.
 */
public class WalletCreatedEventHandler implements EventHandler {

    WalletManager walletManager;

    public void setWalletManager ( WalletManager walletManager){
        this.walletManager = walletManager;
    }

    @Override
    public  void handleEvent(PlatformEvent platformEvent) throws Exception {
        UUID walletId = ((WalletCreatedEvent) platformEvent).getWalletId();
        EventSource eventSource = platformEvent.getSource();
        if (eventSource == EventSource.MIDDLEWARE_WALLET_PLUGIN) {


            if (((Service) this.walletManager).getStatus() == ServiceStatus.STARTED) {

                try {
                    this.walletManager.loadUserWallets(walletId);
                } catch (CantLoadWalletsException cantLoadWalletsException) {
                    /**
                     * The main module could not handle this exception. Me neither. Will throw it again.
                     */
                    System.err.println("CantCreateCryptoWalletException: " + cantLoadWalletsException.getMessage());
                    cantLoadWalletsException.printStackTrace();

                    throw cantLoadWalletsException;
                }
            } else {
                throw new ModuleNotRunningException();
            }
        }
    }
}
