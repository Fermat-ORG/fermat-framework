package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_reception.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/10/15.
 */
public interface DealsWithAssetReception {
    void setAssetReceptionManager(AssetReceptionManager assetReceptionManager)throws CantSetObjectException;
}
