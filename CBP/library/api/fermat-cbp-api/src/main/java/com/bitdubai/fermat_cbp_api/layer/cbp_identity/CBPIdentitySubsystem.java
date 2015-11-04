package com.bitdubai.fermat_cbp_api.layer.cbp_identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartSubsystemException;

public interface CBPIdentitySubsystem {
    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
