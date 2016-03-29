package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantAddNewTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantRemoveExistingTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutManager;

import java.util.List;
import java.util.UUID;


/**
 * Created by rodrigo on 3/27/16.
 */
public class TimeOutNotifierAgentPluginRoot extends AbstractPlugin implements TimeOutManager {

    /**
     * constructor
     */
    public TimeOutNotifierAgentPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        System.out.println("***New Timeout Notifier started...***");
    }


    @Override
    public void stop() {

    }

    @Override
    public TimeOutAgent addNew(long epochTime, long timeout, String name) throws CantAddNewTimeOutAgentException {
        return null;
    }

    @Override
    public void remove(TimeOutAgent timeOutAgent) throws CantRemoveExistingTimeOutAgentException {

    }

    @Override
    public TimeOutAgent getTimeOutAgent(UUID uuid) {
        return null;
    }

    @Override
    public List<TimeOutAgent> getTimeOutAgents() {
        return null;
    }
}
