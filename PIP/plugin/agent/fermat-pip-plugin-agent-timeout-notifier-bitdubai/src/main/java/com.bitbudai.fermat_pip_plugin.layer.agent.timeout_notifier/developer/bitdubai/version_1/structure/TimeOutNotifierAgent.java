package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgent</code>
 * is the agent that is started for each subscription from another plugin. When a request comes to start an agent
 * this class on a new thread monitor and notifies once the time out happens.<p/>
 * <p/>
 * <p/>
 * Created by Acosta Rodrigo - (acosta_rodrigo@hotmail.com) on 28/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TimeOutNotifierAgent implements TimeOutAgent, Runnable {

    /**
     * class variables
     */
    private UUID uuid;
    private String name;
    private String description;
    private FermatActor owner;
    private long epochStartTime;
    private long duration;
    private long epochEndTime;
    private AgentStatus status;
    private boolean isRead;


    /**
     * default constructor
     */
    public TimeOutNotifierAgent() {

    }

    @Override
    public boolean isRunning() {
        if (this.getStatus() == AgentStatus.STARTED)
            return true;
        else
            return false;
    }

    @Override
    public void run() {

    }

    /**
     * Getters
     */

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public FermatActor getOwner() {
        return owner;
    }

    @Override
    public AgentStatus getStatus() {
        return status;
    }

    @Override
    public boolean isRead() {
        return this.isRead;
    }


    @Override
    public long getEpochStartTime() {
        return epochStartTime;
    }

    @Override
    public long getEpochEndTime() {
        return epochEndTime;
    }

    /**
     * Setters
     */

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(FermatActor owner) {
        this.owner = owner;
    }

    public void setEpochStartTime(long epochStartTime) {
        this.epochStartTime = epochStartTime;
    }

    public void setEpochEndTime(long epochEndTime) {
        this.epochEndTime = epochEndTime;
    }

    public void setDuration(long timeOutDuration) {
        this.duration = timeOutDuration;
    }

    public void setStatus(AgentStatus status) {
        this.status = status;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "TimeOutNotifierAgent{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", startTime=" + epochStartTime +
                ", endTime=" + epochEndTime +
                ", timeOutDuration=" + duration +
                ", status=" + status +
                '}';
    }
}
