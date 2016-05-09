package org.fermat.fermat_dap_api.layer.all_definition.events;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.List;

/**
 * Created by Nerio on 27/10/15.
 */
public class ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent extends AbstractDAPEvent {

    private List<ActorAssetUser> actorAssetUserList;

    public List<ActorAssetUser> getActorAssetUserList() {
        return actorAssetUserList;
    }

    public void setActorAssetUserList(List<ActorAssetUser> actorAssetUserList) {
        this.actorAssetUserList = actorAssetUserList;
    }

    public ActorAssetUserRequestListRegisteredNetworkServiceNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
