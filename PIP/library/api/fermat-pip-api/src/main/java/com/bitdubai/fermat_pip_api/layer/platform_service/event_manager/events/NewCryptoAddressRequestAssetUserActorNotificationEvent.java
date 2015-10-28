package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by Nerio on 28/10/15.
 */
public class NewCryptoAddressRequestAssetUserActorNotificationEvent extends AbstractFermatEvent {

    public NewCryptoAddressRequestAssetUserActorNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
