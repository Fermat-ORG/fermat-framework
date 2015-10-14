package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public interface AssetDistributionManager {
    void distributeAssets(HashMap<DigitalAssetMetadata,ActorAssetUser> digitalAssetsToDistribute, String walletPublicKey) throws CantDistributeDigitalAssetsException;
}
