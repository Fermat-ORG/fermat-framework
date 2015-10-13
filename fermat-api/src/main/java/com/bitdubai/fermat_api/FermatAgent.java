package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

/**
 * The interface <code>com.bitdubai.fermat_api.FermatAgent</code>
 * provides the basic functionality of a fermat agent.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/10/2015.
 */
public interface FermatAgent {

    void start() throws CantStartAgentException;

    void pause();

    void resume();

    void stop();

    ServiceStatus getStatus();

}
