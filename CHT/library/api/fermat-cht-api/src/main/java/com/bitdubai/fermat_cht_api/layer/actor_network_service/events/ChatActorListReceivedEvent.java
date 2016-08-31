package com.bitdubai.fermat_cht_api.layer.actor_network_service.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;

import java.util.List;

/**
 * Created by Leon Acosta (laion.cj91@gmail.com) on 26/08/2016.
 *
 * @author lnacosta
 */
public class ChatActorListReceivedEvent extends AbstractEvent {

    private List<ChatExposingData> actorProfileList;

    public ChatActorListReceivedEvent(FermatEventEnum eventType) {
        super(eventType);
    }

    public List<ChatExposingData> getActorProfileList() {
        return actorProfileList;
    }

    public void setActorProfileList(List<ChatExposingData> actorProfileList) {
        this.actorProfileList = actorProfileList;
    }
}
