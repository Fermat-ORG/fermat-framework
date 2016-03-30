package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.events;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgentPool;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * Created by rodrigo on 3/30/16.
 */
public class TimeOutMonitoringAgent implements Agent {

    /**
     * class variables
     */
    private final TimeOutNotifierAgentDatabaseDao dao;
    private final TimeOutNotifierAgentPool pool;

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
    public TimeOutMonitoringAgent(TimeOutNotifierAgentDatabaseDao dao, TimeOutNotifierAgentPool pool, ErrorManager errorManager, EventManager eventManager) {
        this.dao = dao;
        this.pool = pool;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
    }

    @Override
    public void start() throws CantStartAgentException {

    }

    @Override
    public void stop() {

    }
}
