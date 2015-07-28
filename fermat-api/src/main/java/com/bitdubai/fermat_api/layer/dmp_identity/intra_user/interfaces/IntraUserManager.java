package com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantCreateNewIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantGetUserIntraUserIdentitiesException;

import java.util.List;

/**
 * Created by eze on 2015.07.27..
 */
public interface IntraUserManager {
    /**
     * This method will give us a list of all the intra users associated to the actual Device User logged in
     *
     * @return the list of intra users associated to the current logged in Device User.
     * @throws CantGetUserIntraUserIdentitiesException
     */
    List<IntraUserIdentity> getDevelopersFromCurrentDeviceUser() throws CantGetUserIntraUserIdentitiesException;

    /**
     * This method creates a new Developer Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias the alias that the user choose as intra user identity
     * @return the intra user created
     * @throws CantCreateNewIntraUserException
     */
    IntraUserIdentity createNewDeveloper(String alias) throws CantCreateNewIntraUserException;



}
