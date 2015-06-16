package com.bitdubai.fermat_api.layer.pip_user.intra_user;


import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.intra_user.exceptions.CantCreateIntraUserException;

import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */
public interface IntraUserManager {
    
    public void crateUser(UUID userId) throws CantCreateIntraUserException;

    public User getUser(UUID id);
}
