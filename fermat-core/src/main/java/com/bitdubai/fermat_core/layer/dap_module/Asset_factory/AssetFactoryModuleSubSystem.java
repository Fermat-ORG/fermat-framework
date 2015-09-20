package com.bitdubai.fermat_core.layer.dap_module.Asset_factory;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_module.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_module.DAPModuleSubsystem;
import com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by franklin on 20/09/15.
 */
public class AssetFactoryModuleSubSystem implements DAPModuleSubsystem{
    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception exception)
        {
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"DAPModuleSubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
