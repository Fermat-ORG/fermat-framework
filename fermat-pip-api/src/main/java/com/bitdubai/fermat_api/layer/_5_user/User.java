package com.bitdubai.fermat_api.layer._5_user;

import java.util.UUID;

/**
 * *  <p>The abstract class <code>com.bitdubai.fermat_api.layer._5_user.User</code> is a interface
 *     that define the methods for management the Users settings.
 *
 * Created by loui on 22/02/15.
 *
 */
public interface User {
    
    public void setName(String name);

    public void setId(UUID id);
    
    public String getName(String name);
    
    public UUID getId();
    
    public UserTypes getType();
}
