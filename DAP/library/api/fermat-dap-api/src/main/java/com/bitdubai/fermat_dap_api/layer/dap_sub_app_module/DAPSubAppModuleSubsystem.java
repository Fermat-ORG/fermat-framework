package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Nerio on 13/10/15.
 */
public interface DAPSubAppModuleSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();
}
