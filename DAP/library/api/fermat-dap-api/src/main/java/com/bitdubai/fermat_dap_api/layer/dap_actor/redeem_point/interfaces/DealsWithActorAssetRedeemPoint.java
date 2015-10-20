package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Nerio on 07/09/15.
 */
public interface DealsWithActorAssetRedeemPoint {

    void setActorAssetRedeemPointManager(ActorAssetRedeemPointManager actorAssetRedeemPointManager) throws CantSetObjectException;

}
