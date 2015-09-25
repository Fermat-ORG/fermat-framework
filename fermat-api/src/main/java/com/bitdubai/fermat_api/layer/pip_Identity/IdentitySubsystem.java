package com.bitdubai.fermat_api.layer.pip_Identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.ccp_identity.CantStartSubsystemException;


/**
 * Created by natalia on 11/08/15.
 */
public interface IdentitySubsystem {

    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
