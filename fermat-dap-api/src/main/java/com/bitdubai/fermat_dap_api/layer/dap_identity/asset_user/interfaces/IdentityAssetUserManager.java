package com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.exceptions.CantCreateNewIdentityAssetUserException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface IdentityAssetUserManager {

    List<IdentityAssetUser> getIdentityAssetUsersFromCurrentDeviceUser() throws CantCreateNewIdentityAssetUserException;

    IdentityAssetUser createNewIdentityAssetUser(String alias) throws CantCreateNewIdentityAssetUserException;

}
