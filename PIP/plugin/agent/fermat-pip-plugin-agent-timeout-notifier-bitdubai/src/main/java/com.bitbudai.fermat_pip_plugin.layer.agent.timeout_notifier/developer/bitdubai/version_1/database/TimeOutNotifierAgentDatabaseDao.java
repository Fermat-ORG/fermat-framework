package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions.CantInitializeTimeOutNotifierAgentDatabaseException;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions.InconsistentResultObtainedInDatabaseQueryException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
     * Platform variables
     */
    final PluginDatabaseSystem pluginDatabaseSystem;
    final UUID pluginId;

    /**
     * Constructor
     * @param pluginDatabaseSystem
     */
    public TimeOutNotifierAgentDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        try {
            initialize();
        } catch (CantInitializeTimeOutNotifierAgentDatabaseException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws CantInitializeTimeOutNotifierAgentDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, "TimeOut Notifier");

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeTimeOutNotifierAgentDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            TimeOutNotifierAgentDatabaseFactory timeOutNotifierAgentDatabaseFactory = new TimeOutNotifierAgentDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = timeOutNotifierAgentDatabaseFactory.createDatabase(pluginId, "TimeOut Notifier");
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeTimeOutNotifierAgentDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
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

    private DatabaseTableRecord getRecordFromTimeOutNotifierAgent(TimeOutAgent timeOutNotifierAgent){
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, timeOutNotifierAgent.getUUID());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_NAME_COLUMN_NAME, timeOutNotifierAgent.getAgentName());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DESCRIPTION_COLUMN_NAME, timeOutNotifierAgent.getAgentDescription());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME, timeOutNotifierAgent.getOwner().getPublicKey());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_START_TIME_COLUMN_NAME, timeOutNotifierAgent.getEpochStartTime());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DURATION_COLUMN_NAME, timeOutNotifierAgent.getTimeOutDuration());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_ELAPSED_COLUMN_NAME, timeOutNotifierAgent.getElapsedTime());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME, timeOutNotifierAgent.getAgentStatus().getCode());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_PROTOCOL_STATUS_COLUMN_NAME, timeOutNotifierAgent.getNotificationProtocolStatus().getCode());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_LAST_UPDATE_COLUMN_NAME, getCurrentTime());

        return record;
    }

    private long getCurrentTime() {
        return  System.currentTimeMillis();
    }

    private DatabaseTableRecord getOwnerRecordFromTimeOutNotifierAgent(FermatActor fermatActor){
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME, fermatActor.getPublicKey());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_NAME_COLUMN_NAME, fermatActor.getName());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TYPE_COLUMN_NAME, fermatActor.getType().getCode());

        return record;
    }

    private boolean isNewTimeOutNotifierAgent(TimeOutAgent timeOutNotifierAgent) throws CantExecuteQueryException{
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addUUIDFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, timeOutNotifierAgent.getUUID(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        return databaseTable.getRecords().isEmpty();
    }


    private boolean isNewOwner(FermatActor owner) throws CantExecuteQueryException{
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TABLE_NAME);
        databaseTable.addStringFilter(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_OWNER_PUBLICKEY_COLUMN_NAME, owner.getPublicKey(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        return databaseTable.getRecords().isEmpty();
    }

    private void thrownCantExecuteQueryException(Exception e, String tableName) throws CantExecuteQueryException {
        CantExecuteQueryException cantExecuteQueryException = new CantExecuteQueryException("There was an error executing a query in the " + tableName + " table.", e, tableName, "Database Error");
        throw cantExecuteQueryException;
    }

    /**
     * inserts into a database a new Agent and its owner
     * @param timeOutNotifierAgent
     * @throws CantExecuteQueryException
     * @throws InconsistentResultObtainedInDatabaseQueryException
     */
    public void addTimeOutNotifierAgent(TimeOutAgent timeOutNotifierAgent) throws CantExecuteQueryException, InconsistentResultObtainedInDatabaseQueryException {
        if (!isNewTimeOutNotifierAgent(timeOutNotifierAgent)){
            throw new InconsistentResultObtainedInDatabaseQueryException(null, "The TimeOutNotifierAgent already exists in database.", "duplicated data");
        }

        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        DatabaseTableRecord record = getRecordFromTimeOutNotifierAgent(timeOutNotifierAgent);

        // I insert the agent
        try {
            databaseTable.insertRecord(record);
        } catch (CantInsertRecordException e) {
            throw new CantExecuteQueryException(e, "Cant insert agent: " + timeOutNotifierAgent.toString(), "database issue");
        }

        //if the owner is new I will insert it
        FermatActor owner = timeOutNotifierAgent.getOwner();

        if (isNewOwner(owner)){
            DatabaseTable ownerTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TABLE_NAME);
            DatabaseTableRecord ownerRecord = getOwnerRecordFromTimeOutNotifierAgent(owner);
            try {
                ownerTable.insertRecord(ownerRecord);
            } catch (CantInsertRecordException e) {
                throw new CantExecuteQueryException(e, "Cant insert owner: " + owner.toString(), "database issue");
            }
        }
    }

    private FermatActor getOwner(String publicKey) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_TABLE_NAME);
        databaseTable.addStringFilter(TimeOutNotifierAgentDatabaseConstants.AGENT_OWNER_OWNER_PUBLICKEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        if (!databaseTable.getRecords().isEmpty()){
            DatabaseTableRecord record = databaseTable.getRecords().get(0);
            return getFermatActorFromRecord(record);
        } else
            return null;
    }


    public List<TimeOutNotifierAgent> getTimeOutNotifierAgent(String columnName, String stringValue) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addStringFilter(columnName, stringValue, DatabaseFilterType.EQUAL);

        return loadTimeOutNotifierAgentsFromDatabase(databaseTable);
    }

    public List<TimeOutNotifierAgent> getTimeOutNotifierAgent(String columnName, String stringValue, DatabaseFilterType filterType) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addStringFilter(columnName, stringValue, filterType);

        return loadTimeOutNotifierAgentsFromDatabase(databaseTable);
    }

    public List<TimeOutNotifierAgent> getTimeOutNotifierAgent(String columnName, UUID uuIDValue) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addUUIDFilter(columnName, uuIDValue, DatabaseFilterType.EQUAL);

        return loadTimeOutNotifierAgentsFromDatabase(databaseTable);
    }

    public List<TimeOutNotifierAgent> getTimeOutNotifierAgent(FermatActor owner) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addStringFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME, owner.getPublicKey(), DatabaseFilterType.EQUAL);

        return loadTimeOutNotifierAgentsFromDatabase(databaseTable);
    }

    private List<TimeOutNotifierAgent> loadTimeOutNotifierAgentsFromDatabase(DatabaseTable filteredTable) throws CantExecuteQueryException {
        try {
            filteredTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, filteredTable.getTableName());
        }

        List<TimeOutNotifierAgent> timeOutNotifierAgentList = new ArrayList<>();
        for (DatabaseTableRecord record : filteredTable.getRecords()){
            TimeOutNotifierAgent agent = getTimeOutNotifierAgentFromRecord(record);

            // I will get the owner record
            FermatActor owner = getOwner(record.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME));
            agent.setOwner(owner);

            timeOutNotifierAgentList.add(agent);
        }

        return timeOutNotifierAgentList;
    }

    public void removeTimeOutNotifierAgent (TimeOutAgent timeOutNotifierAgent) throws InconsistentResultObtainedInDatabaseQueryException, CantExecuteQueryException {
        if (isNewTimeOutNotifierAgent(timeOutNotifierAgent))
            throw new InconsistentResultObtainedInDatabaseQueryException(null, "Trying to delete an un existing agent." + timeOutNotifierAgent.toString(), "inconsistent data");

        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        DatabaseTableRecord record = getRecordFromTimeOutNotifierAgent(timeOutNotifierAgent);
        try {
            databaseTable.deleteRecord(record);
        } catch (CantDeleteRecordException e) {
            throw new CantExecuteQueryException(e, "Can't delete existing record." + record.toString(), "Database issue");
        }
    }

}
