package org.fermat.fermat_dap_api.layer.all_definition.events;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewRequestActorNotificationEvent extends AbstractDAPEvent {

    private org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification actorNotification;

    public NewRequestActorNotificationEvent(EventType eventType) {
        super(eventType);
    }

    public org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification getActorNotification() {
        return actorNotification;
    }

    public void setActorNotification(org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification actorNotification) {
        this.actorNotification = actorNotification;
    }
}
