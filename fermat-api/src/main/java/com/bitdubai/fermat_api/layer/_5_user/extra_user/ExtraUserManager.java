package com.bitdubai.fermat_api.layer._5_user.extra_user;

import com.bitdubai.fermat_api.layer._5_user.User;
import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */
public interface ExtraUserManager {

    public User getUser(UUID id);
}
