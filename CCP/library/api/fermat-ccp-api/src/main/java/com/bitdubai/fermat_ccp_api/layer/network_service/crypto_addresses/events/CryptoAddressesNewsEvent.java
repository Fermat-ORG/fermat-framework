package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

/**
 * The event <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.CryptoAddressesNewsEvent</code>
 * is used to indicate that there is news in crypto addresses network service.
 *
 * Created by  by Leon Acosta (laion.cj91@gmail.com) on 31/10/2015.
 */
public class CryptoAddressesNewsEvent extends AbstractCCPEvent {

    public CryptoAddressesNewsEvent(FermatEventEnum eventType) {
        super(eventType);
    }

}
