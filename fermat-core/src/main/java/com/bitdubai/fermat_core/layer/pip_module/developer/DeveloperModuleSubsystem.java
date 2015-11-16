package com.bitdubai.fermat_core.layer.pip_module.developer;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_pip_api.layer.pip_module.CantStartSubsystemException;
import com.bitdubai.fermat_pip_api.layer.pip_module.ModuleSubsystem;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperModuleSubsystem implements ModuleSubsystem {

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
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }
}
