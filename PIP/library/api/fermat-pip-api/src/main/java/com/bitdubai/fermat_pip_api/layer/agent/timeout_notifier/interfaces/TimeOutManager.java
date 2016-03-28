package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces;


import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantAddNewTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantRemoveExistingTimeOutAgentException;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 3/26/16.
 * An agent that accepts subscriptions to start monitoring elapsed time and notify after a time out is detected.
 */
public interface TimeOutManager {

    /**
     * Adds a new Time Out Manager to start monitoring timeouts.
     * @param epochTime the Start time configured for this Agent.
     * @param timeout the elapsed time to monitor for a timeout
     * @param name the Name of the agent to be added.
     * @param owner a FermatActor that is the owner of the agent.
     * @return the newly created TimeOut Agent
     * @throws CantAddNewTimeOutAgentException
     */
    TimeOutAgent addNew(long epochTime, long timeout, String name, FermatActor owner) throws CantAddNewTimeOutAgentException;

    /**
     * Removes a configured Agent from the Manager. It is stopped if running.
     * @param timeOutAgent the Agent to be removed.
     * @throws CantRemoveExistingTimeOutAgentException
     */
    void remove(TimeOutAgent timeOutAgent) throws CantRemoveExistingTimeOutAgentException;

    /**
     * Gets the TimeoutAgent from the specified Id.
     * @param uuid the internal UUID of the Agent
     * @return an existing TimeOutAgent. Null if doesn't exists.
     */
    TimeOutAgent getTimeOutAgent(UUID uuid);

    /**
     * The List of all configured TimeOut Agents
     * @return
     */
    List<TimeOutAgent> getTimeOutAgents();

}
