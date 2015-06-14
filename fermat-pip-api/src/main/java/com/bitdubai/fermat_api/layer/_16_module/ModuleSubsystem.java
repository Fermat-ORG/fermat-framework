package com.bitdubai.fermat_api.layer._16_module;

import com.bitdubai.fermat_api.Plugin;


/**
 * Created by ciencias on 21.01.15.
 */
public interface ModuleSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
