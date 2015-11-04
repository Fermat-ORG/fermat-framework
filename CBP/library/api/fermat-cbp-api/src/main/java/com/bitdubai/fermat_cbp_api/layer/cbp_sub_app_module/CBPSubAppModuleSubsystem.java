package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartSubsystemException;

public interface CBPSubAppModuleSubsystem {
    void start() throws CantStartSubsystemException;
    Plugin getPlugin();
}
