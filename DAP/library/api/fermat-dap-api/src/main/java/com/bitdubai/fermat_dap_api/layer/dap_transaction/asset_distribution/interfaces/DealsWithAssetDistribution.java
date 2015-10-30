package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public interface DealsWithAssetDistribution {
    void setAssetDistributionManager(AssetDistributionManager assetDistributionManager)throws CantSetObjectException;
}
