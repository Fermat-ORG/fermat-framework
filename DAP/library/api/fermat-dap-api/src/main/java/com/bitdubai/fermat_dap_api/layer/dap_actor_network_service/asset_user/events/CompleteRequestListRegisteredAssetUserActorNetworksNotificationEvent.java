/*
* @#CompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.events;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.enums.EventType;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.events.CompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 11/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent extends AbstractDapAssetUserActorNetworkServiceFermatEvent {

    private List<ActorAssetUser> actorAssetUserList;




    public List<ActorAssetUser> getActorAssetUserList() {
        return actorAssetUserList;
    }

    public void setActorAssetUserList(List<ActorAssetUser> actorAssetUserList) {
        this.actorAssetUserList = actorAssetUserList;
    }

    public CompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent(EventType eventType) {
        super(eventType);
    }







}
