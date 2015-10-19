package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface RedeemPointCommunitySubAppModuleManager {

//    List<ActorAssetIssuer> getAllActorAssetIssuerRegistered() throws CantGetAssetIssuerActorsException;
//    List<ActorAssetUser> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;

    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;
}
