package com.bitdubai.fermat_api.layer.pip_user.extra_user;

import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantGetExtraUserRegistry;

import java.util.UUID;

/**
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.identity.extra_user.UserRegistry</code> is a interface
 *     that define the methods for management the Users operations.
 *
 * Created by ciencias on 3/18/15.
 */
public interface UserRegistry {

    public User createUser(String userName) throws CantCreateExtraUserRegistry;

    public User getUser(UUID userId) throws CantGetExtraUserRegistry;


}
