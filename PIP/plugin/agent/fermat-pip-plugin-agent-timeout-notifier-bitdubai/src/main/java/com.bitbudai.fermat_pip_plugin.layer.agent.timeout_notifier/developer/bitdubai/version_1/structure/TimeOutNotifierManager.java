package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantAddNewTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantRemoveExistingTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 3/27/16.
 */
public class TimeOutNotifierManager  implements TimeOutManager{


    @Override
    public TimeOutAgent addNew(long epochTime, long timeout, String name, FermatActor owner) throws CantAddNewTimeOutAgentException {
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
