package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.CantStartAgentException;

/**
 * Created by ciencias on 3/19/15.
 */
public interface Agent {
    
    void start() throws CantStartAgentException;
    
    void stop();

}
