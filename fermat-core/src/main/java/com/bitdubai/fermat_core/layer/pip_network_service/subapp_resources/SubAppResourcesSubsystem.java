package com.bitdubai.fermat_core.layer.pip_network_service.subapp_resources;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.CantStartSubsystemException;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.NetworkSubsystem;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.DeveloperBitDubai;


/**
 * Created by natalia on 28/07/15.
 */
public class SubAppResourcesSubsystem implements NetworkSubsystem {

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