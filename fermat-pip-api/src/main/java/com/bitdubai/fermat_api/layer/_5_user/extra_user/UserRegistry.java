package com.bitdubai.fermat_api.layer._5_user.extra_user;

import com.bitdubai.fermat_api.layer._5_user.User;
import com.bitdubai.fermat_api.layer._5_user.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_api.layer._5_user.extra_user.exceptions.CantGetExtraUserRegistry;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */
public interface UserRegistry {
    
public User createUser() throws CantCreateExtraUserRegistry;

public User getUser(UUID userId) throws CantGetExtraUserRegistry;
    
    
}
