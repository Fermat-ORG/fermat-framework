package com.bitdubai.fermat_api.layer._14_middleware;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by ciencias on 20.01.15.
 */
public interface MiddlewareSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
