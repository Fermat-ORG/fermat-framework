package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;

/**
 * The abstract class <code>com.bitdubai.fermat_api.FermatAgent</code>
 * provides the basic functionality of a fermat agent.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/10/2015.
 */
public abstract class FermatAgent {

    protected AgentStatus status;

    public void start() throws CantStartAgentException {
        this.status = AgentStatus.STARTED;
    }

    public void pause() throws CantStopAgentException {
        this.status = AgentStatus.PAUSED;
    }

    public void resume() throws CantStartAgentException {
        this.status = AgentStatus.STARTED;
    }

    public void stop() throws CantStopAgentException {
        this.status = AgentStatus.STOPPED;
    }

    public AgentStatus getStatus() {
        return status;
    }

    public boolean isRunning() {
        return status == AgentStatus.STARTED;
    }

}
