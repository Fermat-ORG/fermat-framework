package com.bitdubai.fermat_api.layer._5_user.extra_user;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */
public interface UserRegistry {
    
public User createUser();

public User getUser(UUID userId);
    
    
}
