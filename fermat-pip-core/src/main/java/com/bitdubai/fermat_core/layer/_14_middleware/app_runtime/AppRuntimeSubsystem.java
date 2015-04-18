package com.bitdubai.fermat_core.layer._14_middleware.app_runtime;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._14_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._14_middleware.MiddlewareSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer._14_middleware.app_runtime.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 14/02/15.
 */
public class AppRuntimeSubsystem implements MiddlewareSubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
