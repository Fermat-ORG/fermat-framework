package org.fermat.fermat_dap_api.layer.all_definition.events;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewReceiveMessageActorNotificationEvent extends AbstractDAPEvent {

    private org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage message;

    public NewReceiveMessageActorNotificationEvent(EventType eventType) {
        super(eventType);
    }

    public org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage getMessage() {
        return message;
    }

    public void setNewReceiveMessage(org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage message) {
        this.message = message;
    }
}
