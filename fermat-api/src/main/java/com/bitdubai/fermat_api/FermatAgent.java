package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

/**
 * The abstract class <code>com.bitdubai.fermat_api.FermatAgent</code>
 * provides the basic functionality of a fermat agent.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/10/2015.
 */
public abstract class FermatAgent {

    protected ServiceStatus serviceStatus;

    public void start() throws CantStartAgentException {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    public void pause(){
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    public void resume(){
        this.serviceStatus = ServiceStatus.STARTED;
    }

    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    public ServiceStatus getStatus(){
        return serviceStatus;
    }

}
