package com.bitdubai.fermat_core.layer.pip_engine.desktop_runtime;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.pip_engine.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.pip_engine.EngineSubsystem;
import com.bitdubai.fermat_pip_plugin.layer.engine.desktop_runtime.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Matias Furszyfer on 2015.09.17..
 */
public class DesktopRuntimeSubsystem implements EngineSubsystem {

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
