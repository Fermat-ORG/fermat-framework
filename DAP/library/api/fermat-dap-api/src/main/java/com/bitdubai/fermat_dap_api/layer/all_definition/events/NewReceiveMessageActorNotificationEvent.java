package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewReceiveMessageActorNotificationEvent extends AbstractDAPEvent {

    private DAPActor actorAssetSender;
    private DAPActor actorAssetDestination;
    private DAPMessage message;

    public NewReceiveMessageActorNotificationEvent(EventType eventType) {
        super(eventType);
    }

    public DAPActor getActorAssetSender() {
        return actorAssetSender;
    }

    public DAPActor getActorAssetDestination() {
        return actorAssetDestination;
    }

    public DAPMessage getMessage() {
        return message;
    }

    public void setNewReceiveMessage(DAPActor actorAssetSender, DAPActor actorAssetDestination, DAPMessage message) {
        this.actorAssetSender = actorAssetSender;
        this.actorAssetDestination = actorAssetDestination;
        this.message = message;
    }
}
