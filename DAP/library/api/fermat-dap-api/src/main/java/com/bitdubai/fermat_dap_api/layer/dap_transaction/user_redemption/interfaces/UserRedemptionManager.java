package com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/10/15.
 */
public interface UserRedemptionManager extends FermatManager {
    void redeemAssetToRedeemPoint(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint, String walletPublicKey) throws CantRedeemDigitalAssetException;
}
