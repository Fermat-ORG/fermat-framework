package com.bitdubai.fermat_api.layer.dmp_world;

import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;

/**
 * Created by ciencias on 3/19/15.
 */
public interface Agent {
    
    public void start() throws CantStartAgentException;
    
    public void stop ();

}
