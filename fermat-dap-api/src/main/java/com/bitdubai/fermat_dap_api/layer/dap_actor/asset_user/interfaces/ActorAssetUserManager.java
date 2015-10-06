package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantRegisterActorAssetUserException;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnecToException;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantSendMessageException;


/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetUserManager {

    public void registerActorAssetUser(ActorAssetUser actorAssetUserToRegister) throws CantRegisterActorAssetUserException;

    public void requestListActorAssetUserRegistered()  throws CantRequestListActorAssetUserRegisteredException;

    public void connectTo(PlatformComponentProfile actorAssetUser)  throws CantConnecToException;

    public void sendMessage(PlatformComponentProfile actorAssetUser, String msjContent)  throws CantSendMessageException;




}
