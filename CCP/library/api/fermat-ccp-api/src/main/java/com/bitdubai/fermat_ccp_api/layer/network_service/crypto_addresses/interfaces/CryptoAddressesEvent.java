package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoAddressesEvent</code>
 * haves all the common functionality of crypto addresses events.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/10/2015.
 */
public class CryptoAddressesEvent extends AbstractCCPEvent {

    private Actors actorType;

    private UUID requestId;

    public CryptoAddressesEvent(FermatEventEnum eventType) {
        super(eventType);
    }

    public UUID getRequestId() {
        return requestId;
    }

    public Actors getActorType() {
        return actorType;
    }

    public void setActorType(final Actors actorType) {
        this.actorType = actorType;
    }

    public void setRequestId(final UUID requestId) {
        this.requestId = requestId;
    }

}
