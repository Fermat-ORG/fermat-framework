package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces;

import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;

import java.util.UUID;

/**
 * Created by rodrigo on 3/26/16.
 */
public interface TimeOutAgent {

    /**
     * gets the internal UUID of the agent
     *
     * @return the internal UUID
     */
    UUID getUUID();

    /**
     * the agent given name for identification.
     *
     * @return the Agent given name
     */
    String getName();

    /**
     * The agent given Description.
     *
     * @return the Description of the agent
     */
    String getDescription();

    /**
     * The actor type that created this agent
     *
     * @return null if not provided.
     */
    FermatActor getOwner();

    /**
     * The current agent status.
     *
     * @return the current agent status.
     */
    AgentStatus getStatus();


    /**
     * notifies if the agent is running.
     *
     * @return true if is running, false if not.
     */
    boolean isRunning();


    /**
     * the start time of the Agent as an epoch time in milliseconds.
     *
     * @return the start time of the agent.
     */
    long getEpochStartTime();


    /**
     * the end time of the Agent as an epoch time in milliseconds.
     *
     * @return the end time of the agent.
     */
    long getEpochEndTime();

    /**
     * The duration that the Agent will be monitoring for. When this duration is reached since the start time
     * an event will be triggered.
     *
     * @return the duration of the time out
     */
    long getDuration();

    /**
     * if this agent event has already been accepted and read by the owner or not.
     *
     * @return
     */
    boolean isRead();
}
