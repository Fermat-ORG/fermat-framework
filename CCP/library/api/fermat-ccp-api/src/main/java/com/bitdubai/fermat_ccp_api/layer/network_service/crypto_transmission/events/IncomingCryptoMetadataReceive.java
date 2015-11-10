package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

/**
 * Created by natalia on 10/11/15.
 */
public class IncomingCryptoMetadataReceive extends AbstractCCPEvent {

    public IncomingCryptoMetadataReceive(FermatEventEnum eventType) {
        super(eventType);
    }
}
