package com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantCreateNewRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantListAssetRedeemPointException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface RedeemPointIdentityManager {

    /**
     * The method <code>getAllIntraWalletUsersFromCurrentDeviceUser</code> will give us a list of all the intra wallet users associated to the actual Device User logged in
     *
     * @return the list of Asset Issuer users associated to the current logged in Device User.
     *
     * @throws CantListAssetRedeemPointException if something goes wrong.
     */
    List<RedeemPointIdentity> getRedeemPointsFromCurrentDeviceUser() throws CantListAssetRedeemPointException;


    /**
     * The method <code>createNewIntraWalletUser</code> creates a new intra wallet user Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias        the alias that the user choose as intra user identity
     * @param profileImage the profile image to identify this identity
     *
     * @return the intra user created
     *
     * @throws CantCreateNewRedeemPointException if something goes wrong.
     */
    RedeemPointIdentity createNewRedeemPoint(String alias       ,
                                                   byte[] profileImage) throws CantCreateNewRedeemPointException;

    /**
     * The method <code>hasAssetUserIdentity</code> returns if has a intra user identity created
     *
     * @return
     * @throws CantListAssetRedeemPointException
     */
    boolean  hasAssetUserIdentity() throws CantListAssetRedeemPointException ;


}
