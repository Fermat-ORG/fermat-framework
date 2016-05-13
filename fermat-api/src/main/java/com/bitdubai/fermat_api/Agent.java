package com.bitdubai.fermat_api;

/**
 * Created by ciencias on 3/19/15.
 */
public interface Agent {
    
    void start() throws CantStartAgentException;
    
    void stop();

}
