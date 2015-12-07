package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface AssetIssuerCommunitySubAppModuleManager extends ModuleManager {

    List<AssetIssuerActorRecord> getAllActorAssetIssuerRegistered() throws CantGetAssetIssuerActorsException;

    void connectToActorAssetIssuer(ActorAssetRedeemPoint requester, List<ActorAssetIssuer> actorAssetIssuers) throws CantConnectToActorAssetRedeemPointException;

//    List<ActorAssetUser> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;
//
//    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;

}
