package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgent;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.utils.FermatActorImpl;
import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao</code>
 * holds the access and modification methods to the database.
 * <p/>
 *
 * Created by Acosta Rodrigo - (acosta_rodrigo@hotmail.com) on 28/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TimeOutNotifierAgentDatabaseDao {

    /**
     * class variables
     */
    Database database;

    /**
     * Constructor
     * @param database
     */
    public TimeOutNotifierAgentDatabaseDao(Database database) {
        this.database= database;
    }

    private TimeOutNotifierAgent getTimeOutNotifierAgentFromRecord(DatabaseTableRecord agentRecord){
        TimeOutNotifierAgent timeOutNotifierAgent = new TimeOutNotifierAgent();

        timeOutNotifierAgent.setUuid(agentRecord.getUUIDValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME));
        timeOutNotifierAgent.setName(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_NAME_COLUMN_NAME));
        timeOutNotifierAgent.setDescription(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DESCRIPTION_COLUMN_NAME));
        timeOutNotifierAgent.setStartTime(agentRecord.getLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_START_TIME_COLUMN_NAME));
        timeOutNotifierAgent.setTimeOutDuration(agentRecord.getLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DURATION_COLUMN_NAME));
        timeOutNotifierAgent.setElapsedTime(agentRecord.getLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_ELAPSED_COLUMN_NAME));
        try {
            timeOutNotifierAgent.setStatus(AgentStatus.getByCode(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME)));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        try {
            timeOutNotifierAgent.setProtocolStatus(ProtocolStatus.getByCode(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_PROTOCOL_STATUS_COLUMN_NAME)));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        return timeOutNotifierAgent;
    }

    private FermatActor getFermatActorFromRecord(DatabaseTableRecord ownerRecord){
        FermatActorImpl owner = new FermatActorImpl();
        owner.setName(ownerRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_NAME_COLUMN_NAME));
        owner.setPublicKey(ownerRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME));
        owner.setType(Actors.getByCode(ownerRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TYPE_COLUMN_NAME)));

        return owner;
    }

    private DatabaseTableRecord getRecordFromTimeOutNotifierAgent(TimeOutNotifierAgent timeOutNotifierAgent){
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, timeOutNotifierAgent.getUUID());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_NAME_COLUMN_NAME, timeOutNotifierAgent.getAgentName());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DESCRIPTION_COLUMN_NAME, timeOutNotifierAgent.getAgentDescription());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_START_TIME_COLUMN_NAME, timeOutNotifierAgent.getEpochStartTime());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DURATION_COLUMN_NAME, timeOutNotifierAgent.getTimeOutDuration());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_ELAPSED_COLUMN_NAME, timeOutNotifierAgent.getElapsedTime());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME, timeOutNotifierAgent.getAgentStatus().getCode());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_PROTOCOL_STATUS_COLUMN_NAME, timeOutNotifierAgent.getNotificationProtocolStatus().getCode());

        return record;
    }

    private DatabaseTableRecord getOwnerRecordFromTimeOutNotifierAgent(FermatActorImpl fermatActor){
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME, fermatActor.getPublicKey());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_NAME_COLUMN_NAME, fermatActor.getName());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TYPE_COLUMN_NAME, fermatActor.getType().getCode());

        return record;
    }

    private boolean isNewTimeOutNotifierAgent(TimeOutNotifierAgent timeOutNotifierAgent) throws CantExecuteQueryException{
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addUUIDFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, timeOutNotifierAgent.getUUID(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        return databaseTable.getRecords().isEmpty();
    }

    private boolean isNewOwner(FermatActorImpl owner) throws CantExecuteQueryException{
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addStringFilter(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TABLE_NAME, owner.getPublicKey(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        return databaseTable.getRecords().isEmpty();
    }

    private CantExecuteQueryException thrownCantExecuteQueryException(Exception e, String tableName){
        CantExecuteQueryException cantExecuteQueryException = new CantExecuteQueryException("There was an error executing a query in the " + tableName + " table.", e, tableName, "Database Error");
        return cantExecuteQueryException;
    }
}
