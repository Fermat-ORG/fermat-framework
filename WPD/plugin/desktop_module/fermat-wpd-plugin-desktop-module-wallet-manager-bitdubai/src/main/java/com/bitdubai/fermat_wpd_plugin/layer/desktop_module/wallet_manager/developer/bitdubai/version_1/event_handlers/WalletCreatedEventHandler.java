package com.bitdubai.fermat_wpd_plugin.layer.desktop_module.wallet_manager.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_module.ModuleNotRunningException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.WalletCreatedEvent;

import java.util.UUID;

/**
 * Created by loui on 19/02/15.
 */
public class WalletCreatedEventHandler implements FermatEventHandler {

    WalletManager walletManager;

    public void setWalletManager ( WalletManager walletManager){
        this.walletManager = walletManager;
    }

    @Override
    public  void handleEvent(FermatEvent fermatEvent) throws FermatException {

        // TODO USABA LA WALLETID DE LA REQUESTED CREADA PARA CARGAR LAS BILLETERAS DEL USUARIO?
        UUID walletId = ((WalletCreatedEvent) fermatEvent).getWalletId();
        EventSource eventSource = fermatEvent.getSource();
        if (eventSource == EventSource.MIDDLEWARE_WALLET_PLUGIN) {


            if (((Service) this.walletManager).getStatus() == ServiceStatus.STARTED) {

                try {
                    // TODO USABA LA WALLETID DE LA REQUESTED CREADA PARA CARGAR LAS BILLETERAS DEL USUARIO?
                    this.walletManager.loadUserWallets("");
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
