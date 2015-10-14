package com.bitdubai.fermat_ccp_api.layer.middleware;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface MiddlewareSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();

}
