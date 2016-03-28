package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantResetTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStartTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStopTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;

import java.util.UUID;

/**
 * Created by rodrigo on 3/28/16.
 */
public class TimeOutNotifierAgent implements TimeOutAgent {

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void startTimeOutAgent() throws CantStartTimeOutAgentException {

    }

    @Override
    public void resetTimeOutAgent() throws CantResetTimeOutAgentException {

    }

    @Override
    public void stopTimeOutAgent() throws CantStopTimeOutAgentException {

    }

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public long getEpochTime() {
        return 0;
    }

    @Override
    public long getTimeOutDuration() {
        return 0;
    }

    @Override
    public String getAgentName() {
        return null;
    }

    @Override
    public String getAgentDescription() {
        return null;
    }

    @Override
    public Actors getOwnerType() {
        return null;
    }

    @Override
    public AgentStatus getAgentStatus() {
        return null;
    }

    @Override
    public ProtocolStatus getNotificationProtocolStatus() {
        return null;
    }

    @Override
    public void markEventNotificationAsRead() {

    }
}
