package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia on 29/08/16.
 */
public class ActorNetworkListReceiveEvent  extends AbstractCCPEvent {

     List<IntraUserInformation> actorProfileList = new ArrayList<>();

    public ActorNetworkListReceiveEvent(FermatEventEnum eventType) {
        super(eventType);
    }

    public List<IntraUserInformation> getActorProfileList() {
        return actorProfileList;
    }

    public void setActorProfileList(List<IntraUserInformation> actorProfileList) {
        this.actorProfileList = actorProfileList;
    }
}
