package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;


import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetRedeemPointManager {

    List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorRegistered() throws CantGetAssetRedeemPointActorsException, CantAssetRedeemPointActorNotFoundException;

}
