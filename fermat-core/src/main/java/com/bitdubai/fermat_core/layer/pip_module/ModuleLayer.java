package com.bitdubai.fermat_core.layer.pip_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_pip_api.layer.pip_module.CantStartSubsystemException;
import com.bitdubai.fermat_pip_api.layer.pip_module.ModuleSubsystem;
import com.bitdubai.fermat_core.layer.pip_module.developer.DeveloperModuleSubsystem;

/**
 * The interface <code>com.bitdubai.fermat_core.layer.pip_actor.ModuleLayer</code>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 */
public class ModuleLayer implements PlatformLayer {

    Plugin mDeveloperModule;

    public Plugin getmDeveloperModule() {
        return mDeveloperModule;
    }

    @Override
    public void start()  throws CantStartLayerException {
        /**
         * Let's try to start the wallet factory subsystem.
         */
        ModuleSubsystem developerModuleSubsystem = new DeveloperModuleSubsystem();

        try {
            developerModuleSubsystem.start();
            mDeveloperModule = developerModuleSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
        }
    }
}
