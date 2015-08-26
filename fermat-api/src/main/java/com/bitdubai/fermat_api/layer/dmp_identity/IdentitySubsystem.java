package com.bitdubai.fermat_api.layer.dmp_identity;

import com.bitdubai.fermat_api.Plugin;


/**
 * Created by natalia on 11/08/15.
 */
public interface IdentitySubsystem {

    void start () throws CantStartSubsystemException;
    Plugin getPlugin();
}
