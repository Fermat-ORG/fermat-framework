package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantAddNewTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 3/28/16.
 */
public class TimeOutNotifierAgentPool {
    /**
     * class variables
     */
    private List<TimeOutAgent> runningAgents;
    private TimeOutNotifierAgentDatabaseDao timeOutNotifierAgentDatabaseDao;

    /**
     * platform variables
     */
    final PluginDatabaseSystem pluginDatabaseSystem;
    final UUID pluginId;
    final ErrorManager errorManager;

    /**
     * default constructor
     */
    public TimeOutNotifierAgentPool(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, ErrorManager errorManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;

        initialize();
    }

    /**
     * Loads and starts all the agents that are supposed to be running.
     */
    public void initialize(){
        runningAgents = new ArrayList<>();
    }

    private TimeOutNotifierAgentDatabaseDao getDao(){
        if (timeOutNotifierAgentDatabaseDao == null)
            timeOutNotifierAgentDatabaseDao = new TimeOutNotifierAgentDatabaseDao(pluginDatabaseSystem, pluginId);

        return timeOutNotifierAgentDatabaseDao;

    }

    /**
     * adds a new agent to the pool.
     * @param timeOutNotifierAgent
     * @throws CantAddNewTimeOutAgentException
     */
    public void addRunningAgent(TimeOutNotifierAgent timeOutNotifierAgent) throws CantAddNewTimeOutAgentException {
        runningAgents.add(timeOutNotifierAgent);
        try {
            getDao().addTimeOutNotifierAgent(timeOutNotifierAgent);
        } catch (Exception e) {
            //remove it from memory.
            removeRunningAgent(timeOutNotifierAgent);

            CantAddNewTimeOutAgentException cantAddNewTimeOutAgentException = new CantAddNewTimeOutAgentException(e,
                    "Database Error adding new Agent to the pool",
                    timeOutNotifierAgent.toString());

            errorManager.reportUnexpectedPluginException(Plugins.TIMEOUT_NOTIFIER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantAddNewTimeOutAgentException);
            throw cantAddNewTimeOutAgentException;
        }
    }

    public void removeRunningAgent(TimeOutNotifierAgent timeOutNotifierAgent){
        runningAgents.remove(timeOutNotifierAgent);
    }

    /**
     * Gets the running agents of the pool
     * @return
     */
    public List<TimeOutAgent> getRunningAgents() {
        return runningAgents;
    }


}
