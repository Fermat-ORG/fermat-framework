package com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces;

import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantGetUserIntraUserIdentitiesException;

import java.util.List;

/**
 * The interface <code>IntraUserIdentityManager</code>
 * provides the methods to create and obtain intra users associated to a Device User.
 */
public interface IntraUserIdentityManager {

    /**
     * The method <code>getAllIntraUsersFromCurrentDeviceUser</code> will give us a list of all the intra users associated to the actual Device User logged in
     *
     * @return the list of intra users associated to the current logged in Device User.
     * @throws CantGetUserIntraUserIdentitiesException
     */
    List<IntraUserIdentity> getAllIntraUsersFromCurrentDeviceUser() throws CantGetUserIntraUserIdentitiesException;

    /**
     * The method <code>createNewIntraUser</code> creates a new Developer Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias        the alias that the user choose as intra user identity
     * @param profileImage the profile image to identify this identity
     * @return the intra user created
     * @throws CantCreateNewIntraUserException
     */
    IntraUserIdentity createNewIntraUser(String alias, byte[] profileImage) throws CantCreateNewIntraUserException;
    
}
