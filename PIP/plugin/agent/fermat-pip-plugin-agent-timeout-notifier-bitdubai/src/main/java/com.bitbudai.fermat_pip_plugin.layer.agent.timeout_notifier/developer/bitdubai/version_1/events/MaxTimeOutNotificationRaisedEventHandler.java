package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.events;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgentPool;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.all_definition.event_manager.events.MaxTimeOutNotificationReachedEvent;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;

/**
 * Created by rodrigo on 4/9/16.
 */
public class MaxTimeOutNotificationRaisedEventHandler implements FermatEventHandler {
    /**
     * Class variables
     */
    TimeOutNotifierAgentPool timeOutNotifierAgentPool;
    TimeOutAgent timeOutAgent;

    public MaxTimeOutNotificationRaisedEventHandler(TimeOutNotifierAgentPool timeOutNotifierAgentPool) {
        this.timeOutNotifierAgentPool = timeOutNotifierAgentPool;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (fermatEvent == null)
            throw new FermatException("Event is null", null, "Handling event", null);

        if (fermatEvent instanceof MaxTimeOutNotificationReachedEvent) {
            MaxTimeOutNotificationReachedEvent maxTimeOutNotificationReachedEvent = (MaxTimeOutNotificationReachedEvent) fermatEvent;
            timeOutAgent = maxTimeOutNotificationReachedEvent.getTimeOutAgent();

            if (timeOutAgent == null)
                throw new FermatException("TimeOutAgent is null", null, "Handling event", null);

            // I will stop and remove the agent
            timeOutNotifierAgentPool.stopTimeOutAgent(timeOutAgent);
            timeOutNotifierAgentPool.removeRunningAgent(timeOutAgent);
        }
    }
}
