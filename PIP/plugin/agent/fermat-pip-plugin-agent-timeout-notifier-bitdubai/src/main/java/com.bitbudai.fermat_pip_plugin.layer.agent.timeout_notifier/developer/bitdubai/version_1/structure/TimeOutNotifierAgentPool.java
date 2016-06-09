package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseConstants;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.events.TimeOutMonitoringAgent;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions.InconsistentResultObtainedInDatabaseQueryException;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantAddNewTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantRemoveExistingTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStartTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStopTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 3/28/16.
 */
public class TimeOutNotifierAgentPool {
    /**
     * class variables
     */
    private List<TimeOutAgent> runningAgents;
    final private TimeOutNotifierAgentDatabaseDao dao;
    final TimeOutMonitoringAgent timeOutMonitoringAgent;


    /**
     * platform variables
     */
    final ErrorManager errorManager;

    /**
     * default constructor
     */
    public TimeOutNotifierAgentPool(TimeOutNotifierAgentDatabaseDao timeOutNotifierAgentDatabaseDao, ErrorManager errorManager, TimeOutMonitoringAgent timeOutMonitoringAgent) {
        this.dao = timeOutNotifierAgentDatabaseDao;
        this.errorManager = errorManager;
        this.timeOutMonitoringAgent = timeOutMonitoringAgent;

        initialize();
    }

    /**
     * Loads and starts all the agents that are supposed to be running.
     */
    public void initialize() {
        runningAgents = new ArrayList<>();
        runningAgents.addAll(loadRunningAgents());

        //If I have things to do, I will start the monitoring agent.
        if (!runningAgents.isEmpty())
            try {
                timeOutMonitoringAgent.start();
            } catch (CantStartAgentException e) {
                e.printStackTrace();
            }

    }

    private List<TimeOutAgent> loadRunningAgents() {
        List<TimeOutAgent> timeOutAgentList = new ArrayList<>();
        try {
            timeOutAgentList.addAll(dao.getTimeOutNotifierAgent(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME, AgentStatus.STARTED.getCode(), DatabaseFilterType.EQUAL));
        } catch (CantExecuteQueryException e) {
            return timeOutAgentList;
        }

        return timeOutAgentList;
    }

    /**
     * adds a new agent to the pool.
     *
     * @param timeOutNotifierAgent
     * @throws CantAddNewTimeOutAgentException
     */
    public void addRunningAgent(TimeOutAgent timeOutNotifierAgent) throws CantAddNewTimeOutAgentException {
        runningAgents.add(timeOutNotifierAgent);
        try {
            dao.addTimeOutNotifierAgent(timeOutNotifierAgent);
        } catch (Exception e) {
            //remove it from memory.
            try {
                removeRunningAgent(timeOutNotifierAgent);
            } catch (CantRemoveExistingTimeOutAgentException e1) {
                //not important
            }

            CantAddNewTimeOutAgentException cantAddNewTimeOutAgentException = new CantAddNewTimeOutAgentException(e,
                    "Database Error adding new Agent to the pool",
                    timeOutNotifierAgent.toString());

            errorManager.reportUnexpectedPluginException(Plugins.TIMEOUT_NOTIFIER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantAddNewTimeOutAgentException);
            throw cantAddNewTimeOutAgentException;
        }
    }

    public void removeRunningAgent(TimeOutAgent timeOutNotifierAgent) throws CantRemoveExistingTimeOutAgentException {
        if (runningAgents.contains(timeOutNotifierAgent))
            runningAgents.remove(timeOutNotifierAgent);

        try {
            dao.removeTimeOutNotifierAgent(timeOutNotifierAgent);

        } catch (Exception e) {
            CantRemoveExistingTimeOutAgentException exception = new CantRemoveExistingTimeOutAgentException(e,
                    "Error trying to remove an Agent from the pool.",
                    "Database issue");

            errorManager.reportUnexpectedPluginException(Plugins.TIMEOUT_NOTIFIER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }

    /**
     * Gets the running agents of the pool
     *
     * @return
     */
    public List<TimeOutAgent> getRunningAgents() {
        return runningAgents;
    }


    public void stopTimeOutAgent(TimeOutAgent timeOutAgent) throws CantStopTimeOutAgentException {
        TimeOutNotifierAgent agent = (TimeOutNotifierAgent) timeOutAgent;
        try {
            agent.setStatus(AgentStatus.STOPPED);
            dao.updateTimeOutNotifierAgent(agent);

            boolean running = false;
            for (TimeOutAgent myRunningAgents : runningAgents) {
                if (myRunningAgents.getStatus() == AgentStatus.STARTED)
                    running = true;
            }

            // stop the monitoring agent if this is the last one
            if (!running && timeOutMonitoringAgent.getAgentStatus() == AgentStatus.STARTED)
                timeOutMonitoringAgent.stop();

        } catch (CantExecuteQueryException e) {
            CantStopTimeOutAgentException exception = new CantStopTimeOutAgentException(e, "Database error updating status to stopped. " + agent.toString(), "Database error");
            errorManager.reportUnexpectedPluginException(Plugins.TIMEOUT_NOTIFIER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }


    public void startTimeOutAgent(TimeOutAgent timeOutAgent) throws CantStartTimeOutAgentException {
        TimeOutNotifierAgent agent = (TimeOutNotifierAgent) timeOutAgent;
        agent.setEpochStartTime(System.currentTimeMillis());
        agent.setStatus(AgentStatus.STARTED);
        agent.setEpochEndTime(agent.getEpochStartTime() + agent.getDuration());
        try {
            dao.updateTimeOutNotifierAgent(agent);
            if (timeOutMonitoringAgent.getAgentStatus() != AgentStatus.STARTED)
                timeOutMonitoringAgent.start();

        } catch (CantExecuteQueryException e) {
            CantStartTimeOutAgentException exception = new CantStartTimeOutAgentException(e, "Database error updating status to run. " + agent.toString(), "Database error");
            errorManager.reportUnexpectedPluginException(Plugins.TIMEOUT_NOTIFIER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        } catch (CantStartAgentException e) {
            CantStartTimeOutAgentException exception = new CantStartTimeOutAgentException(e, "Error starting monitoring agent", "agent error");
            errorManager.reportUnexpectedPluginException(Plugins.TIMEOUT_NOTIFIER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }

        runningAgents.add(timeOutAgent);
    }

    public void markAsRead(TimeOutAgent timeOutAgent) throws CantExecuteQueryException, InconsistentResultObtainedInDatabaseQueryException {
        dao.markAsRead(timeOutAgent.getUUID());
    }
}
