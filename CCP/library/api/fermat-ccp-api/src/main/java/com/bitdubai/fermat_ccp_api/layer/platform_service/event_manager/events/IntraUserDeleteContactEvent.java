package com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

import java.util.UUID;

/**
 * Created by Gian Barboza on 29/08/16.
 */
public class IntraUserDeleteContactEvent extends AbstractCCPEvent {

    private String actorPublicKey;

    public IntraUserDeleteContactEvent(FermatEventEnum eventType) {
        super(eventType);
    }


    public String getActorPublicKey(){
        return actorPublicKey;
    }

    public void setActorPublicKey(String actorPublicKey){
        this.actorPublicKey = actorPublicKey;
    }
}
