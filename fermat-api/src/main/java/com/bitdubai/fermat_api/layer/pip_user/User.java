package com.bitdubai.fermat_api.layer.pip_user;

import java.util.UUID;

/**
 * *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.identity.User</code> is a interface
 *     that define the methods for management the Users settings.
 *
 * Created by loui on 22/02/15.
 *
 */
public interface User {
    
    public void setName(String name);

    public void setId(UUID id);
    
    public String getName();
    
    public UUID getId();
    
    public UserTypes getType();
}
