package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewReceiveMessageActorNotificationEvent extends AbstractDAPEvent {

    private DAPMessage message;

    public NewReceiveMessageActorNotificationEvent(EventType eventType) {
        super(eventType);
    }

    public DAPMessage getMessage() {
        return message;
    }

    public void setNewReceiveMessage(DAPMessage message) {
        this.message = message;
    }
}
