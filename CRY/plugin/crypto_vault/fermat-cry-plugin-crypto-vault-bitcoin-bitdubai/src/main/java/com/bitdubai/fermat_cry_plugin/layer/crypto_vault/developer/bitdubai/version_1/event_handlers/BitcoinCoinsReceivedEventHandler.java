package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

/**
 * Created by rodrigo on 2015.06.18..
 */
public class BitcoinCoinsReceivedEventHandler implements EventHandler{

    CryptoVaultManager cryptoVaultManager;

    /**
     * PlatformEvent interface member variable
     */
    PlatformEvent platformEvent;

    /**
     * EventHandler interface implementation
     * @param platformEvent
     * @throws Exception
     */
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        if (((Service) this.cryptoVaultManager).getStatus() == ServiceStatus.STARTED){

        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }

    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager){
        this.cryptoVaultManager = cryptoVaultManager;
    }
}
