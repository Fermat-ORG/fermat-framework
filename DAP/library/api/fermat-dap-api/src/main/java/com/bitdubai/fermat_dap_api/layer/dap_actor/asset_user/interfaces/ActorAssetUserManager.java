package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;


/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetUserManager {

    void registerActorAssetUser(ActorAssetUser actorAssetUserToRegister);

    void requestListActorAssetUserRegistered();

    void connectTo(PlatformComponentProfile actorAssetUser);

    void sendMessage(PlatformComponentProfile actorAssetUser, String msjContent);
}
