package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_redemption.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 03/11/15.
 */
public interface DealsWithRedeemPointRedemption {
    void setRedeemPointRedemptionManager(RedeemPointRedemptionManager redeemPointRedemptionManager) throws CantSetObjectException;
}
