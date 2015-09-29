package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public interface DealsWithAssetIssuing {
    void setAssetIssuingManager(AssetIssuingManager assetIssuingManager) throws CantSetObjectException;
}
