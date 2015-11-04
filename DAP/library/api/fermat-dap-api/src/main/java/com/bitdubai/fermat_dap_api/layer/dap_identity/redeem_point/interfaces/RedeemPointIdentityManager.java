package com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantCreateNewRedeemPointException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface RedeemPointIdentityManager {

    List<RedeemPointIdentity> getRedeemPointsFromCurrentDeviceUser() throws CantCreateNewRedeemPointException;

    RedeemPointIdentity createNewRedeemPoint(String alias) throws CantCreateNewRedeemPointException;


}
