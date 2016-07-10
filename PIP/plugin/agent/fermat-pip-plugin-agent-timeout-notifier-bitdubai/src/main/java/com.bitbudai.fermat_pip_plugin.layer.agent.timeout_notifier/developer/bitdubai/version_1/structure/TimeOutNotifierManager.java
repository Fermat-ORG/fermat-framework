package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions.InconsistentResultObtainedInDatabaseQueryException;
import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantAddNewTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantMarkAgentAsReadException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantRemoveExistingTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStartTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStopTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 3/27/16.
 */
public class TimeOutNotifierManager implements TimeOutManager {

    /**
     * Class Variables
     */
    final TimeOutNotifierAgentDatabaseDao dao;
    final TimeOutNotifierAgentPool timeOutNotifierAgentPool;

    /**
     * platform variables
     */
    final ErrorManager errorManager;

    /**
     * constructor
     *
     * @param errorManager
     */
    public TimeOutNotifierManager(TimeOutNotifierAgentDatabaseDao timeOutNotifierAgentDatabaseDao, ErrorManager errorManager, TimeOutNotifierAgentPool timeOutNotifierAgentPool) {
        this.dao = timeOutNotifierAgentDatabaseDao;
        this.errorManager = errorManager;
        this.timeOutNotifierAgentPool = timeOutNotifierAgentPool;
    }

    @Override
    public TimeOutAgent addNew(long duration, String name, FermatActor owner) throws CantAddNewTimeOutAgentException {
        TimeOutNotifierAgent timeOutNotifierAgent = new TimeOutNotifierAgent();
        timeOutNotifierAgent.setUuid(UUID.randomUUID());
        timeOutNotifierAgent.setName(name);
        timeOutNotifierAgent.setDuration(duration);
        timeOutNotifierAgent.setOwner(owner);
        timeOutNotifierAgent.setStatus(AgentStatus.CREATED);


        timeOutNotifierAgentPool.addRunningAgent(timeOutNotifierAgent);

        return timeOutNotifierAgent;
    }

    @Override
    public TimeOutAgent addNewAndStart(long duration, String agentName, FermatActor owner) throws CantAddNewTimeOutAgentException, CantStartTimeOutAgentException {
        TimeOutAgent timeOutAgent = this.addNew(duration, agentName, owner);
        startTimeOutAgent(timeOutAgent);
        return timeOutAgent;
    }

    @Override
    public void remove(TimeOutAgent timeOutAgent) throws CantRemoveExistingTimeOutAgentException {
        timeOutNotifierAgentPool.removeRunningAgent(timeOutAgent);
    }

    @Override
    public void stopTimeOutAgent(TimeOutAgent timeOutAgent) throws CantStopTimeOutAgentException {
        timeOutNotifierAgentPool.stopTimeOutAgent(timeOutAgent);
    }

    @Override
    public void startTimeOutAgent(TimeOutAgent timeOutAgent) throws CantStartTimeOutAgentException {
        timeOutNotifierAgentPool.startTimeOutAgent(timeOutAgent);
    }


    @Override
    public TimeOutAgent getTimeOutAgent(UUID uuid) {
        for (TimeOutAgent timeOutAgent : timeOutNotifierAgentPool.getRunningAgents()) {
            if (timeOutAgent.getUUID() == uuid)
                return timeOutAgent;
        }
        //if no match
        return null;
    }

    @Override
    public List<TimeOutAgent> getTimeOutAgents() {
        return timeOutNotifierAgentPool.getRunningAgents();
    }

    @Override
    public List<TimeOutAgent> getTimeOutAgents(FermatActor owner) {
        List<TimeOutAgent> timeOutAgentList = new ArrayList<>();
        for (TimeOutAgent timeOutAgent : timeOutNotifierAgentPool.getRunningAgents()) {
            if (timeOutAgent.getOwner() == owner)
                timeOutAgentList.add(timeOutAgent);
        }
        return timeOutAgentList;
    }

    @Override
    public List<TimeOutAgent> getTimeOutAgents(AgentStatus status) {
        List<TimeOutAgent> timeOutAgentList = new ArrayList<>();
        for (TimeOutAgent timeOutAgent : timeOutNotifierAgentPool.getRunningAgents()) {
            if (timeOutAgent.getStatus() == status)
                timeOutAgentList.add(timeOutAgent);
        }
        return timeOutAgentList;
    }

    @Override
    public void markAsRead(TimeOutAgent timeOutAgent) throws CantMarkAgentAsReadException {
        try {
            timeOutNotifierAgentPool.markAsRead(timeOutAgent);
        } catch (CantExecuteQueryException | InconsistentResultObtainedInDatabaseQueryException e) {
            CantMarkAgentAsReadException exception = new CantMarkAgentAsReadException(e, "Error marking agent as Read. " + timeOutAgent.toString(), "Database Issue");
            errorManager.reportUnexpectedPluginException(Plugins.TIMEOUT_NOTIFIER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }
}
