/*
* @#CompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.all_definition.dap_actor_network_service.asset_user.enums.events;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.all_definition.dap_actor_network_service.asset_user.enums.DapEvenType;

import java.util.List;

/**
 * The Class <code>CompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent</code>
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

    public CompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent(DapEvenType eventType) {
        super(eventType);
    }







}
