package com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer._11_network_service.crypto_addressees.CryptoAddressesManager;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 20/02/15.
 */
public class IntraUserCreatedEventHandler implements EventHandler {
    CryptoAddressesManager cryptoAddressesManager;
    
    public void setCryptoAddressesManager(CryptoAddressesManager cryptoAddressesManager){
        this.cryptoAddressesManager = cryptoAddressesManager;
        
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        
    }
}
