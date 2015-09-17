package com.bitdubai.fermat_api.layer.pip_engine;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Matias Furszyfer on 21/05/15.
 */
public interface EngineSubsystem {
    public void start() throws CantStartSubsystemException;
    public Plugin getPlugin();
}
