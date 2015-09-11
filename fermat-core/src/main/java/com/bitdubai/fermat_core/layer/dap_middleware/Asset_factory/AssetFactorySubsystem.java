package com.bitdubai.fermat_core.layer.dap_middleware.Asset_factory;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.DAPMiddlewareSubsystem;


/**
 * Created by franklin on 10/09/15.
 */
class AssetFactorySubsystem implements DAPMiddlewareSubsystem{
    Plugin plugin;

    @Override
    public void star() throws CantStartSubsystemException {

    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }
}
