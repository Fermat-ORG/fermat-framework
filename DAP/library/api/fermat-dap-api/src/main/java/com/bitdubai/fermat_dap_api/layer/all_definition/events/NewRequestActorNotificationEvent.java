package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewRequestActorNotificationEvent extends AbstractDAPEvent {

    private ActorNotification actorNotification;

    public NewRequestActorNotificationEvent(EventType eventType) {
        super(eventType);
    }

    public ActorNotification getActorNotification() {
        return actorNotification;
    }

    public void setActorNotification(ActorNotification actorNotification) {
        this.actorNotification = actorNotification;
    }
}
