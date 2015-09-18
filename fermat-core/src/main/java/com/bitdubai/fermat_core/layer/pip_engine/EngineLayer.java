package com.bitdubai.fermat_core.layer.pip_engine;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.pip_engine.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.pip_engine.EngineSubsystem;
import com.bitdubai.fermat_core.layer.pip_engine.desktop_runtime.DesktopRuntimeSubsystem;

/**
 * Created by Matias Furszyfer on 21/05/15.
 */
public class EngineLayer implements PlatformLayer {


    private Plugin mDesktopRuntimePlugin;

    @Override
    public void start() throws CantStartLayerException {

        mDesktopRuntimePlugin = getPlugin(new DesktopRuntimeSubsystem());

    }

    private Plugin getPlugin(EngineSubsystem engineSubsystem) throws CantStartLayerException {
        try {
            engineSubsystem.start();
            return engineSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getmDesktopRuntimePlugin() {
        return mDesktopRuntimePlugin;
    }
}
