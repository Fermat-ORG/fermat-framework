package com.bitdubai.fermat_api.layer._5_user;

import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */
public interface User {
    
    public void setName(String name);

    public void setId(UUID id);
    
    public String getName(String name);
    
    public UUID getId();
    
    public UserTypes getType();
}
