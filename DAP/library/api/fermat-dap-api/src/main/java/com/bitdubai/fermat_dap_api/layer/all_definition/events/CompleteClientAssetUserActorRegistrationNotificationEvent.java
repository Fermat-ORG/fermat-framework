/*
* @#CompleteClientAssetUserActorRegistrationNotificationEvent.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.all_definition.events;


import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DapEvenType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
/**
 * The Class <code>CompleteClientAssetUserActorRegistrationNotificationEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteClientAssetUserActorRegistrationNotificationEvent extends AbstractDapAssetUserActorNetworkServiceFermatEvent {

    private ActorAssetUser actorAssetUser;

    public ActorAssetUser getActorAssetUser() {
        return actorAssetUser;
    }

    public void setActorAssetUser(ActorAssetUser actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }

    public CompleteClientAssetUserActorRegistrationNotificationEvent(DapEvenType eventType) {
        super(eventType);
    }
}
