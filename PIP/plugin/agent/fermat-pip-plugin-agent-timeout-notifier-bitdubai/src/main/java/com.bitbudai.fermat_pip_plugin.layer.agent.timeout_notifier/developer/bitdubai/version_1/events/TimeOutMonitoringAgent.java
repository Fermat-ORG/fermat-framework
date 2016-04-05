package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.events;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseConstants;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgent;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgentPool;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by rodrigo on 3/30/16.
 */
public class TimeOutMonitoringAgent implements Agent {

    /**
     * class variables
     */
    private final TimeOutNotifierAgentDatabaseDao dao;
    private AgentStatus agentStatus;
    private MonitoringAgent monitoringAgent;
    private Thread monitorThread;

    /**
     * Platform variables
     */
    private final ErrorManager errorManager;
    private final EventManager eventManager;

    /**
     * constructor
     * @param errorManager
     * @param dao
     * @param eventManager
     */
    public TimeOutMonitoringAgent(TimeOutNotifierAgentDatabaseDao dao, ErrorManager errorManager, EventManager eventManager) {
        this.dao = dao;
        this.errorManager = errorManager;
        this.eventManager = eventManager;

        this.agentStatus = AgentStatus.CREATED;
    }

    @Override
    public void start() throws CantStartAgentException {
        if (monitoringAgent != null)
            monitoringAgent = new MonitoringAgent(this.dao);

        monitorThread = new Thread(monitoringAgent, "TimeOutNotifier");
        monitorThread.start();
        this.agentStatus = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        monitoringAgent.setExecutionFlag(false);
        while (monitoringAgent.getAgentStatus() != AgentStatus.STOPPED){
            //wait until is stopped.
        }
        monitorThread.interrupt();
        this.agentStatus = AgentStatus.STOPPED;
    }

    public AgentStatus getAgentStatus(){
        return this.agentStatus;
    }


    private class MonitoringAgent implements Runnable{
        /**
         * private class variables
         */
        private final TimeOutNotifierAgentDatabaseDao dao;
        private final int ITERATION_TIME = 1000 * 5; //5 seconds.
        private AtomicBoolean executionFlag;
        private AgentStatus agentStatus;

        public MonitoringAgent(TimeOutNotifierAgentDatabaseDao dao) {
            this.dao = dao;
        }

        public boolean getExecutionFlag() {
            return executionFlag.get();
        }

        public void setExecutionFlag(boolean executionFlag) {
            this.executionFlag.set(executionFlag);
        }

        public AgentStatus getAgentStatus() {
            return agentStatus;
        }

        @Override
        public void run() {
            this.agentStatus = AgentStatus.STARTED;

            while (executionFlag.get()){
                doTheMainTask();

                try {
                    Thread.sleep(ITERATION_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // if I reach here, then a stop request is in progress
            this.agentStatus = AgentStatus.STOPPED;

        }

        private void doTheMainTask() {
            try {
                List<TimeOutNotifierAgent> timeOutManagerList =  dao.getTimeOutNotifierAgent(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME, AgentStatus.STARTED.code, DatabaseFilterType.EQUAL);
                for (TimeOutNotifierAgent timeOutNotifierAgent : timeOutManagerList){
                    if (timeOutNotifierAgent.getEpochEndTime() > System.currentTimeMillis()){
                        //raise event
                        dao.updateMonitorEventData(timeOutNotifierAgent.getUUID());
                    }
                }
            } catch (CantExecuteQueryException e) {
                e.printStackTrace();
            }
        }
    }
}
