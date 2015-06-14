package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;

/**
 * Created by ciencias on 30.12.14.
 */
public interface Service {

    public void start() throws CantStartPluginException;

    public void pause();
    
    public void resume();

    public void stop();

    public ServiceStatus getStatus();

}
