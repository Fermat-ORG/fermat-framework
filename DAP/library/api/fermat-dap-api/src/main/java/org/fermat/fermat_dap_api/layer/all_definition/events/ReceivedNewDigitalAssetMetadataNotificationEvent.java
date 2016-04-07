package org.fermat.fermat_dap_api.layer.all_definition.events;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;

/**
 * Created by Nerio on 15/10/15.
 */
public class ReceivedNewDigitalAssetMetadataNotificationEvent extends AbstractDAPEvent {

    private org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser actorAssetUser;

    public org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser getActorAssetUser() {
        return actorAssetUser;
    }

    public void setActorAssetUser(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }

    public ReceivedNewDigitalAssetMetadataNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
