/*
* @#NewCryptoAddressRequestAssetUserActorNotificationEvent.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * The Class <code>com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.NewCryptoAddressRequestAssetUserActorNotificationEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 28/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewCryptoAddressRequestAssetUserActorNotificationEvent extends AbstractFermatEvent{


    private ActorAssetIssuer actorAssetIssuerSender;
    private ActorAssetUser actorAssetUserDestination;

    public ActorAssetIssuer getActorAssetIssuerSender() {
        return actorAssetIssuerSender;
    }

    public ActorAssetUser getActorAssetUserDestination() {
        return actorAssetUserDestination;
    }

    public void setNewCryptoAddressRequest(ActorAssetIssuer actorAssetIssuerSender, ActorAssetUser actorAssetUserDestination){
        this.actorAssetIssuerSender=actorAssetIssuerSender;
        this.actorAssetUserDestination=actorAssetUserDestination;
    }

    public NewCryptoAddressRequestAssetUserActorNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
