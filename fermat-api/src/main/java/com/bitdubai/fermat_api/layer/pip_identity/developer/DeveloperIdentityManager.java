package com.bitdubai.fermat_api.layer.pip_identity.developer;

import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetDeveloperException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_api.layer.pip_identity.developer.exceptions.DeveloperLoginFailedException;

import java.util.List;

/**
 * Created by eze on 2015.07.09..
 */
public interface DeveloperIdentityManager {
    public List<DeveloperIdentity> getDevelopersFromActualUser() throws CantGetUserDeveloperIdentitiesException;
    public void createNewDeveloper(String alias) throws CantCreateNewDeveloperException;
    public void login(String alias) throws DeveloperLoginFailedException;
    public DeveloperIdentity getActualDeveloper() throws CantGetDeveloperException;
}
