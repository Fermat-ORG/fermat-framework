package com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces;

import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;

import java.util.List;

/**
 * The Interface <code>DeveloperIdentityManager</code>
 * indicates the functionality of a DeveloperIdentityManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DeveloperIdentityManager {

    /**
     * This method will give us a list of all the developers associated to the actual Device User logged in
     *
     * @return the list of Developers associated to the current logged in Device User.
     * @throws CantGetUserDeveloperIdentitiesException
     */
    List<DeveloperIdentity> getDevelopersFromCurrentDeviceUser() throws CantGetUserDeveloperIdentitiesException;

    /**
     * This method creates a new Developer Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias the alias that the user choose as developer identity
     * @return the new developer just created
     * @throws CantCreateNewDeveloperException
     */
    DeveloperIdentity createNewDeveloper(String alias) throws CantCreateNewDeveloperException;

}