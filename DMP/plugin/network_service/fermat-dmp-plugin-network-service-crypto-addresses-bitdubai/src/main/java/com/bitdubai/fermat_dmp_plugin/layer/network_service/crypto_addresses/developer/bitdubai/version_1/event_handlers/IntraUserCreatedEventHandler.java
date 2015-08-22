package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;

/**
 * Created by loui on 20/02/15.
 */
public class IntraUserCreatedEventHandler implements EventHandler {
    CryptoAddressesManager cryptoAddressesManager;
    
    public void setCryptoAddressesManager(CryptoAddressesManager cryptoAddressesManager){
        this.cryptoAddressesManager = cryptoAddressesManager;
        
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException{
        
    }
}
