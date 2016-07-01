package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.events;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgent;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.events.MaxTimeOutNotificationReachedEvent;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.events.TimeOutReachedEvent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by rodrigo on 3/30/16.
 */
public class TimeOutMonitoringAgent implements Agent, FermatEventMonitor {

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
     *
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
        if (monitoringAgent == null)
            monitoringAgent = new MonitoringAgent(this.dao);

        monitoringAgent.setExecutionFlag(true);

        if (monitorThread == null)
            monitorThread = new Thread(monitoringAgent, "TimeOutNotifierThread");

        monitorThread.start();
        this.agentStatus = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        monitoringAgent.setExecutionFlag(false);
        while (monitoringAgent.getAgentStatus() != AgentStatus.STOPPED) {
            //wait until is stopped.
        }
        monitorThread.interrupt();
        this.agentStatus = AgentStatus.STOPPED;
    }

    public AgentStatus getAgentStatus() {
        return this.agentStatus;
    }


    private class MonitoringAgent implements Runnable {
        /**
         * private class variables
         */
        private final TimeOutNotifierAgentDatabaseDao dao;
        private final int ITERATION_TIME = 1000 * 5; //5 seconds.
        private final int MAX_TIME_OUT_NOTIFICATIONS = 5;
        private AtomicBoolean executionFlag;
        private AgentStatus agentStatus;

        public MonitoringAgent(TimeOutNotifierAgentDatabaseDao dao) {
            this.dao = dao;
            executionFlag = new AtomicBoolean(true);
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

            while (getExecutionFlag()) {
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
                List<TimeOutNotifierAgent> timeOutManagerList = dao.getPendingNotification();
                for (TimeOutNotifierAgent timeOutNotifierAgent : timeOutManagerList) {
                    if (timeOutNotifierAgent.getEpochEndTime() > System.currentTimeMillis()) {
                        int numNotifications = dao.updateMonitorEventData(timeOutNotifierAgent.getUUID());

                        raiseEvent(EventType.TIMEOUT_REACHED, timeOutNotifierAgent, numNotifications);
                        System.out.println("***TimeOutNotifier*** Event Raised for agent " + timeOutNotifierAgent.toString());


                        if (numNotifications > MAX_TIME_OUT_NOTIFICATIONS) {
                            raiseEvent(EventType.MAX_TIMEOUT_NOTIFICATION_REACHED, timeOutNotifierAgent, numNotifications);
                            System.out.println("***TimeOutNotifier*** Max Event Raised Notification reached");
                        }

                    }
                }
            } catch (CantExecuteQueryException e) {
                e.printStackTrace();
            }
        }

        private void raiseEvent(EventType eventType, TimeOutNotifierAgent timeOutNotifierAgent, int amountRaises) {
            switch (eventType) {
                case TIMEOUT_REACHED:
                    TimeOutReachedEvent event = new TimeOutReachedEvent();
                    event.setTimeOutAgent(timeOutNotifierAgent);
                    event.setSource(EventSource.TIMEOUT_NOTIFIER);
                    event.setAmountRaises(amountRaises);

                    eventManager.raiseEvent(event);
                    break;
                case MAX_TIMEOUT_NOTIFICATION_REACHED:
                    MaxTimeOutNotificationReachedEvent event1 = new MaxTimeOutNotificationReachedEvent();
                    event1.setTimeOutAgent(timeOutNotifierAgent);
                    event1.setSource(EventSource.TIMEOUT_NOTIFIER);
                    eventManager.raiseEvent(event1);
                    break;
            }
        }
    }

    @Override
    public void handleEventException(Exception exception, FermatEvent fermatEvent) {
        errorManager.reportUnexpectedPluginException(Plugins.TIMEOUT_NOTIFIER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
    }
}
