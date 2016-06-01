package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Matias Furszyfer on 2016.06.01..
 */
public abstract class AbstractAgent {

    protected AgentStatus status;

    private long sleepTime;
    private TimeUnit timeUnit;
    private long initDelayTime = 0;
    private ScheduledExecutorService scheduledExecutorService;

    public AbstractAgent(long sleepTime,TimeUnit timeUnit) {
        this.sleepTime = sleepTime;
        this.timeUnit = timeUnit;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public AbstractAgent(long sleepTime,TimeUnit timeUnit,long initDelayTime) {
        this.sleepTime = sleepTime;
        this.timeUnit = timeUnit;
        this.initDelayTime = initDelayTime;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }


    public void start() throws CantStartAgentException {
        this.scheduledExecutorService.scheduleWithFixedDelay(agentJob(),initDelayTime,sleepTime,timeUnit);
        this.status = AgentStatus.STARTED;
    }

    public void stop() throws CantStopAgentException {
        this.scheduledExecutorService.shutdownNow();
        this.status = AgentStatus.STOPPED;
    }

    public AgentStatus getStatus(){
        return status;
    }

    public boolean isRunning(){
        return status == AgentStatus.STARTED;
    }

    public boolean isStop(){
        return status == AgentStatus.STOPPED;
    }

    /**
     * In this method developer will have to implement the runnable used in the agent
     *
     * @return
     */
    protected abstract Runnable agentJob();
}
