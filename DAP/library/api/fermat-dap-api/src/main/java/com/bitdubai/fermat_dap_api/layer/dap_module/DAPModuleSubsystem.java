package com.bitdubai.fermat_dap_api.layer.dap_module;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by franklin on 11/09/15.
 */
public interface DAPModuleSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
