package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface AssetUserCommunitySubAppModuleManager extends ModuleManager {

    List<AssetUserActorRecord> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;

    void connectToActorAssetUser(ActorAssetIssuer requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToActorAssetUserException;

//    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;
}
