package com.bitdubai.fermat_dap_api.layer.dap_identity.user.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.issuer.exceptions.CantCreateNewIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.user.exceptions.CantCreateNewUserException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface UserIdentityManager {

    List<UserIdentity> getUsersFromCurrentDeviceUser() throws CantCreateNewUserException;

    UserIdentity createNewUser(String alias) throws CantCreateNewUserException;

}
