package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantAddNewTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantRemoveExistingTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 3/27/16.
 */
public class TimeOutNotifierManager  implements TimeOutManager{

    /**
     * platform variables
     */
    final PluginDatabaseSystem pluginDatabaseSystem;
    final UUID pluginId;
    final ErrorManager errorManager;
    final TimeOutNotifierAgentPool timeOutNotifierAgentPool;


    /**
     * constructor
     * @param pluginDatabaseSystem
     * @param pluginId
     * @param errorManager
     */
    public TimeOutNotifierManager(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ErrorManager errorManager, TimeOutNotifierAgentPool timeOutNotifierAgentPool) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.timeOutNotifierAgentPool = timeOutNotifierAgentPool;
    }

    @Override
    public TimeOutAgent addNew(long epochTime, long timeout, String name, FermatActor owner) throws CantAddNewTimeOutAgentException {
        TimeOutNotifierAgent timeOutNotifierAgent = new TimeOutNotifierAgent();
        timeOutNotifierAgent.setUuid(UUID.randomUUID());
        timeOutNotifierAgent.setName(name);
        timeOutNotifierAgent.setStartTime(epochTime);
        timeOutNotifierAgent.setTimeOutDuration(timeout);
        timeOutNotifierAgent.setOwner(owner);
        timeOutNotifierAgent.setStatus(AgentStatus.CREATED);
        timeOutNotifierAgent.setProtocolStatus(ProtocolStatus.NO_ACTION_REQUIRED);

        timeOutNotifierAgentPool.addRunningAgent(timeOutNotifierAgent);

        return timeOutNotifierAgent;
    }

    @Override
    public void remove(TimeOutAgent timeOutAgent) throws CantRemoveExistingTimeOutAgentException {
        timeOutNotifierAgentPool.removeRunningAgent(timeOutAgent);
    }

    @Override
    public TimeOutAgent getTimeOutAgent(UUID uuid) {
        for (TimeOutAgent timeOutAgent : timeOutNotifierAgentPool.getRunningAgents()){
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
        for (TimeOutAgent timeOutAgent : timeOutNotifierAgentPool.getRunningAgents()){
            if (timeOutAgent.getOwner() == owner)
                timeOutAgentList.add(timeOutAgent);
        }
        return timeOutAgentList;
    }

    @Override
    public List<TimeOutAgent> getTimeOutAgents(AgentStatus status) {
        List<TimeOutAgent> timeOutAgentList = new ArrayList<>();
        for (TimeOutAgent timeOutAgent : timeOutNotifierAgentPool.getRunningAgents()){
            if (timeOutAgent.getAgentStatus() == status)
                timeOutAgentList.add(timeOutAgent);
        }
        return timeOutAgentList;
    }
}
