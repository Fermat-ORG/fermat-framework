package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropiation.exceptions.CantExecuteAppropiationTransactionException;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/10/15.
 */
public interface AssetAppropriationManager {
    void appropiateAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToAppropiate, String walletPublicKey) throws CantExecuteAppropiationTransactionException;
}
