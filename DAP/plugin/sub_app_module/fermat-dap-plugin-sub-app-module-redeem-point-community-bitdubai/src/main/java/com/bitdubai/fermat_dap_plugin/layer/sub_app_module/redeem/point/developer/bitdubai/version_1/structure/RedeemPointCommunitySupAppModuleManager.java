package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public class RedeemPointCommunitySupAppModuleManager {

    ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    public RedeemPointCommunitySupAppModuleManager(ActorAssetRedeemPointManager actorAssetRedeemPointManager) {
        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
    }

    public List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException, CantAssetRedeemPointActorNotFoundException {
        return actorAssetRedeemPointManager.getAllAssetRedeemPointActorInTableRegistered();
    }
}
