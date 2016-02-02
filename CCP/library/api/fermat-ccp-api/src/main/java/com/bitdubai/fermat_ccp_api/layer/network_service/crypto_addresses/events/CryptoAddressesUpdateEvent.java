package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

/**
 * Created by natalia on 25/01/16.
 */
public class CryptoAddressesUpdateEvent extends AbstractCCPEvent {

    public CryptoAddressesUpdateEvent(FermatEventEnum eventType) {
        super(eventType);
    }

}
