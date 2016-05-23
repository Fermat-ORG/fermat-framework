package org.fermat.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

import java.util.Map;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/10/15.
 */
public interface UserRedemptionManager extends FermatManager {
    void redeemAssetToRedeemPoint(Map<DigitalAssetMetadata, ActorAssetRedeemPoint> toRedeem, String walletPublicKey) throws org.fermat.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
}
