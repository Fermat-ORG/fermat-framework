package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by Matias Furszyfer on 2016.06.01..
 */
public abstract class AbstractAgent {

    protected AgentStatus status = AgentStatus.CREATED;

    private long sleepTime;
    private TimeUnit timeUnit;
    private long initDelayTime = 0;
    private String threadName;
    private boolean isDaemon;
    private ScheduledExecutorService scheduledExecutorService;
    private int threadPriority;

    public AbstractAgent(long sleepTime, TimeUnit timeUnit) {
        this.sleepTime = sleepTime;
        this.timeUnit = timeUnit;
    }

    public AbstractAgent(long sleepTime, TimeUnit timeUnit, long initDelayTime) {
        this(sleepTime, timeUnit);
        this.initDelayTime = initDelayTime;
    }

    public AbstractAgent(final String threadName,long sleepTime, TimeUnit timeUnit, long initDelayTime) {
        this(sleepTime,timeUnit,initDelayTime);
        this.threadName = threadName;
    }


    public void start() throws CantStartAgentException {
        if (isRunning())
            throw new CantStartAgentException("Agent is already running, first use stop() method to reset");
        else initExecutor();
        this.scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    agentJob();
                }catch (Exception e){
                    onErrorOccur(e);
                }
            }
        }, initDelayTime, sleepTime, timeUnit);
        this.status = AgentStatus.STARTED;
    }

    public void stop() throws CantStopAgentException {
        if (isStop()) throw new CantStopAgentException("Agent is not running");
        this.scheduledExecutorService.shutdownNow();
        this.status = AgentStatus.STOPPED;
    }

    public AgentStatus getStatus() {
        return status;
    }

    public boolean isRunning() {
        return status == AgentStatus.STARTED;
    }

    public boolean isStop() {
        return status == AgentStatus.STOPPED;
    }

    public void setIsDaemon(boolean isDaemon) {
        this.isDaemon = isDaemon;
    }

    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }

    private void initExecutor(){
        if(scheduledExecutorService==null) {
            ThreadFactory threadFactory = new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    if(threadName!=null) thread.setName(threadName);
                    if (isDaemon)thread.setDaemon(true);
                    if (threadPriority!=0)thread.setPriority(threadPriority);
                    return thread;
                }
            };
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
        }
    }

    /**
     * In this method developer will have to implement the runnable used in the agent
     *
     * @return
     */
    protected abstract void agentJob();

    protected abstract void onErrorOccur(Exception e);


}
