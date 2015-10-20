package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/10/15.
 */
public interface DealsWithAssetAppropriation {
    void setAssetAppropriationManager(AssetAppropriationManager assetAppropriationManager) throws CantSetObjectException;
}
