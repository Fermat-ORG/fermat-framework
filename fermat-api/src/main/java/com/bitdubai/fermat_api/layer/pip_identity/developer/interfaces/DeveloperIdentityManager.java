package com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces;

import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.DeveloperLoginFailedException;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DeveloperIdentityManager</code>
 * indicates the functionality of a DeveloperIdentityManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DeveloperIdentityManager {
    public List<DeveloperIdentity> getDevelopersFromActualUser() throws CantGetUserDeveloperIdentitiesException;
    public void createNewDeveloper(String alias) throws CantCreateNewDeveloperException;
    public void login(String alias) throws DeveloperLoginFailedException;
    public DeveloperIdentity getActualDeveloper() throws CantGetDeveloperException;
}