package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesEvent;

/**
 * The event <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressRequestedEvent</code>
 *
 * Created by  by Leon Acosta (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CryptoAddressDeniedEvent extends CryptoAddressesEvent {

    public CryptoAddressDeniedEvent(FermatEventEnum eventType) {
        super(eventType);
    }
}
