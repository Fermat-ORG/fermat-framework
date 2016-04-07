package org.fermat.fermat_dap_api.layer.all_definition.events;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;

/**
 * Created by Nerio on 27/10/15.
 */
public class ActorAssetUserCompleteRegistrationNotificationEvent extends AbstractDAPEvent {

    private org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser actorAssetUser;

    public org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser getActorAssetUser() {
        return actorAssetUser;
    }

    public void setActorAssetUser(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }

    public ActorAssetUserCompleteRegistrationNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
