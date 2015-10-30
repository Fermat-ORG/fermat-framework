package com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/10/15.
 */
public interface DealsWithUserRedemption {
    void setUserRedemptionManager(UserRedemptionManager userRedemptionManager)  throws CantSetObjectException;
}
