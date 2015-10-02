package com.bitdubai.fermat_ccp_api.layer.request;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface RequestSubsystem {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();

}
