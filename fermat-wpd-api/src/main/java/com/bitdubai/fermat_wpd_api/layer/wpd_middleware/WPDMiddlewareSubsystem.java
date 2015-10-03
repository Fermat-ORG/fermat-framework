package com.bitdubai.fermat_wpd_api.layer.wpd_middleware;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Nerio on 29/09/15.
 */
public interface WPDMiddlewareSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();
}
