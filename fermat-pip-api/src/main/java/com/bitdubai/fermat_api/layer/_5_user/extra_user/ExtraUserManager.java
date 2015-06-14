package com.bitdubai.fermat_api.layer._5_user.extra_user;

import com.bitdubai.fermat_api.layer._5_user.User;
import java.util.UUID;

/**
 *    <p>The abstract class <code>com.bitdubai.fermat_api.layer._5_user.extra_user.ExtraUserManager</code> is a interface
 *     that define the methods for management the Users operations.
 *
 * Created by loui on 22/02/15.
 */
public interface ExtraUserManager {

    public User getUser(UUID id);

    public User createUser(String userName);
}
