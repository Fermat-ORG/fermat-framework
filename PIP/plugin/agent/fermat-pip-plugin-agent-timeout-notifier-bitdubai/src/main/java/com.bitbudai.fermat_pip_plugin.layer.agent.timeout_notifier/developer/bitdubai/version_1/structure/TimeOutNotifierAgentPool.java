package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import java.util.List;

/**
 * Created by rodrigo on 3/28/16.
 */
public class TimeOutNotifierAgentPool {
    private List<TimeOutNotifierAgent> runningAgents;

    /**
     * default constructor
     */
    public TimeOutNotifierAgentPool() {
        initialize();
    }

    /**
     * Loads and starts all the agents that are supposed to be running.
     */
    public void initialize(){

    }

    public void addRunningAgent(TimeOutNotifierAgent timeOutNotifierAgent){
        runningAgents.add(timeOutNotifierAgent);
    }

    public void removeRunningAgent(TimeOutNotifierAgent timeOutNotifierAgent){
        runningAgents.remove(timeOutNotifierAgent);
    }

    /**
     * Gets the running agents of the pool
     * @return
     */
    public List<TimeOutNotifierAgent> getRunningAgents() {
        return runningAgents;
    }


}
