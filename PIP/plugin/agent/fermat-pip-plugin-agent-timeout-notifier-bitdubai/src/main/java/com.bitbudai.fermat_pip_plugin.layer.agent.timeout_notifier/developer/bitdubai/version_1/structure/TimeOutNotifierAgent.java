package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao;
import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantResetTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStartTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions.CantStopTimeOutAgentException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgent</code>
 * is the agent that is started for each subscription from another plugin. When a request comes to start an agent
 * this class on a new thread monitor and notifies once the time out happens.<p/>
 * <p/>
 *
 * Created by Acosta Rodrigo - (acosta_rodrigo@hotmail.com) on 28/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TimeOutNotifierAgent implements TimeOutAgent {

    /**
     * class variables
     */
    private UUID uuid;
    private String name;
    private String description;
    private FermatActor owner;
    private long startTime;
    private long timeOutDuration;
    private long elapsedTime;
    private AgentStatus status;
    private ProtocolStatus protocolStatus;


    /**
     * default constructor
     */
    public TimeOutNotifierAgent() {

    }

    @Override
    public boolean isRunning() {
        if (this.getAgentStatus() == AgentStatus.STARTED)
            return true;
        else
            return false;
    }


    private final class Agent implements Runnable{

        @Override
        public void run() {

        }
    }

    /**
     * Getters

     */

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public long getTimeOutDuration() {
        return timeOutDuration;
    }

    @Override
    public String getAgentName() {
        return name;
    }

    @Override
    public String getAgentDescription() {
        return description;
    }

    @Override
    public FermatActor getOwner() {
        return owner;
    }

    @Override
    public AgentStatus getAgentStatus() {
        return status;
    }

    @Override
    public ProtocolStatus getNotificationProtocolStatus() {
        return protocolStatus;
    }

    @Override
    public void markEventNotificationAsRead() {

    }

    @Override
    public long getEpochStartTime() {
        return startTime;
    }

    @Override
    public long getElapsedTime() {
        return elapsedTime;
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

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setTimeOutDuration(long timeOutDuration) {
        this.timeOutDuration = timeOutDuration;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setStatus(AgentStatus status) {
        this.status = status;
    }

    public void setProtocolStatus(ProtocolStatus protocolStatus) {
        this.protocolStatus = protocolStatus;
    }

    @Override
    public String toString() {
        return "TimeOutNotifierAgent{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", startTime=" + startTime +
                ", timeOutDuration=" + timeOutDuration +
                ", elapsedTime=" + elapsedTime +
                ", status=" + status +
                ", protocolStatus=" + protocolStatus +
                '}';
    }
}
