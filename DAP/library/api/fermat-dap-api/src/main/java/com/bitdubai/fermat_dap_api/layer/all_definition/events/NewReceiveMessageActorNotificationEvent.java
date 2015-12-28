package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewReceiveMessageActorNotificationEvent extends AbstractDAPEvent {

    private DAPMessage message;

    public NewReceiveMessageActorNotificationEvent(EventType eventType) {
        super(eventType);
    }

    public DAPActor getActorAssetSender() {
        return message.getActorSender();
    }

    public DAPActor getActorAssetDestination() {
        return message.getActorReceiver();
    }

    public DAPMessage getMessage() {
        return message;
    }

    public void setNewReceiveMessage(DAPMessage message) {
        this.message = message;
    }
}
