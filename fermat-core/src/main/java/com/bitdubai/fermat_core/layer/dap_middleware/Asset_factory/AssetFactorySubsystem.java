package com.bitdubai.fermat_core.layer.dap_middleware.Asset_factory;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.DAPMiddlewareSubsystem;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.DeveloperBitDubai;


/**
 * Created by franklin on 10/09/15.
 */
public class AssetFactorySubsystem implements DAPMiddlewareSubsystem{
    Plugin plugin;

    @Override
    public void start() throws CantStartSubsystemException {
        try
        {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception exception)
        {
            //System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(FermatException.wrapException(exception),"AssetFactorySubsystem","Unexpected Exception");
        }
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
}
