package com.bitdubai.fermat_api.layer.dmp_identity.intra_user;

import com.bitdubai.fermat_api.Plugin;


/**
 * Created by natalia on 11/08/15.
 */
public interface IdentitySubsystem {

    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
