package com.bitdubai.fermat_api.layer._11_world;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._16_module.CantStartSubsystemException;

/**
 * Created by ciencias on 2/6/15.
 */
public interface WorldSubsystem {
    public void start() throws CantStartSubsystemException;
    public Plugin getPlugin();
}
