package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingCryptoMetadataEvent extends AbstractFermatEvent {
    public IncomingCryptoMetadataEvent() {
        super(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_CRYPTO_METADATA);
    }
}
