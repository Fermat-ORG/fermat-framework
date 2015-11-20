package com.bitdubai.fermat_ccm_api.layer.actor;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface ActorSubsystemCCM {

    void start() throws CantStartSubsystemException;

    Plugin getPlugin();

}
