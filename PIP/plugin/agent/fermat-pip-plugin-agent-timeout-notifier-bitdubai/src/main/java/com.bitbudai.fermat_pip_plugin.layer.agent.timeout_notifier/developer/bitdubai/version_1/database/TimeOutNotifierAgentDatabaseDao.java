package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions.CantInitializeTimeOutNotifierAgentDatabaseException;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions.InconsistentResultObtainedInDatabaseQueryException;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.structure.TimeOutNotifierAgent;
import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.utils.FermatActorImpl;
import com.bitdubai.fermat_api.layer.actor.FermatActor;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.interfaces.TimeOutAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.database.TimeOutNotifierAgentDatabaseDao</code>
 * holds the access and modification methods to the database.
 * <p/>
 * <p/>
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
     *
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
            database = this.pluginDatabaseSystem.openDatabase(pluginId, TimeOutNotifierAgentDatabaseConstants.DATABASE_NAME);

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
                database = timeOutNotifierAgentDatabaseFactory.createDatabase(pluginId);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeTimeOutNotifierAgentDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    private TimeOutNotifierAgent getTimeOutNotifierAgentFromRecord(DatabaseTableRecord agentRecord) {
        TimeOutNotifierAgent timeOutNotifierAgent = new TimeOutNotifierAgent();

        timeOutNotifierAgent.setUuid(agentRecord.getUUIDValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME));
        timeOutNotifierAgent.setName(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_NAME_COLUMN_NAME));
        timeOutNotifierAgent.setDescription(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DESCRIPTION_COLUMN_NAME));
        timeOutNotifierAgent.setEpochStartTime(agentRecord.getLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_START_TIME_COLUMN_NAME));
        timeOutNotifierAgent.setEpochEndTime(agentRecord.getLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_END_TIME_COLUMN_NAME));
        timeOutNotifierAgent.setDuration(agentRecord.getLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DURATION_COLUMN_NAME));

        //agent status
        try {
            timeOutNotifierAgent.setStatus(AgentStatus.getByCode(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME)));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        // isRead Flag
        timeOutNotifierAgent.setIsRead(Boolean.valueOf(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_READ_COLUMN_NAME)));

        try {
            timeOutNotifierAgent.setOwner(getOwner(agentRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME)));
        } catch (CantExecuteQueryException e) {
            e.printStackTrace();
        }

        return timeOutNotifierAgent;
    }

    private FermatActor getFermatActorFromRecord(DatabaseTableRecord ownerRecord) {
        FermatActorImpl owner = new FermatActorImpl();
        owner.setName(ownerRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.OWNER_NAME_COLUMN_NAME));
        owner.setPublicKey(ownerRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.OWNER_PUBLICKEY_COLUMN_NAME));
        owner.setType(Actors.getByCode(ownerRecord.getStringValue(TimeOutNotifierAgentDatabaseConstants.OWNER_TYPE_COLUMN_NAME)));

        return owner;
    }

    private DatabaseTableRecord getRecordFromTimeOutNotifierAgent(TimeOutAgent timeOutNotifierAgent) {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, timeOutNotifierAgent.getUUID());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_NAME_COLUMN_NAME, timeOutNotifierAgent.getName());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DESCRIPTION_COLUMN_NAME, timeOutNotifierAgent.getDescription());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME, timeOutNotifierAgent.getOwner().getPublicKey());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_START_TIME_COLUMN_NAME, timeOutNotifierAgent.getEpochStartTime());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_END_TIME_COLUMN_NAME, timeOutNotifierAgent.getEpochEndTime());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_DURATION_COLUMN_NAME, timeOutNotifierAgent.getDuration());

        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME, timeOutNotifierAgent.getStatus().getCode());
        record.setLongValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_LAST_UPDATE_COLUMN_NAME, getCurrentTime());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_READ_COLUMN_NAME, Boolean.toString(timeOutNotifierAgent.isRead()));

        return record;
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    private DatabaseTableRecord getOwnerRecordFromTimeOutNotifierAgent(FermatActor fermatActor) {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.OWNER_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.OWNER_PUBLICKEY_COLUMN_NAME, fermatActor.getPublicKey());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.OWNER_NAME_COLUMN_NAME, fermatActor.getName());
        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.OWNER_TYPE_COLUMN_NAME, fermatActor.getType().getCode());

        return record;
    }

    private boolean isNewTimeOutNotifierAgent(TimeOutAgent timeOutNotifierAgent) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addUUIDFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, timeOutNotifierAgent.getUUID(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        return databaseTable.getRecords().isEmpty();
    }


    private boolean isNewOwner(FermatActor owner) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.OWNER_TABLE_NAME);
        databaseTable.addStringFilter(TimeOutNotifierAgentDatabaseConstants.OWNER_PUBLICKEY_COLUMN_NAME, owner.getPublicKey(), DatabaseFilterType.EQUAL);

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
     *
     * @param timeOutNotifierAgent
     * @throws CantExecuteQueryException
     * @throws InconsistentResultObtainedInDatabaseQueryException
     */
    public void addTimeOutNotifierAgent(TimeOutAgent timeOutNotifierAgent) throws CantExecuteQueryException, InconsistentResultObtainedInDatabaseQueryException {
        if (!isNewTimeOutNotifierAgent(timeOutNotifierAgent)) {
            throw new InconsistentResultObtainedInDatabaseQueryException(null, "The TimeOutNotifierAgent already exists in database.", "duplicated data");
        }

        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        DatabaseTableRecord record = getRecordFromTimeOutNotifierAgent(timeOutNotifierAgent);

        // I create a transaction to insert both agent and owner
        DatabaseTransaction transaction = database.newTransaction();
        transaction.addRecordToInsert(databaseTable, record);

        FermatActor owner = timeOutNotifierAgent.getOwner();
        if (isNewOwner(owner)) {
            DatabaseTable ownerTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.OWNER_TABLE_NAME);
            DatabaseTableRecord ownerRecord = getOwnerRecordFromTimeOutNotifierAgent(owner);

            transaction.addRecordToInsert(ownerTable, ownerRecord);
        }

        try {
            database.executeTransaction(transaction);
        } catch (DatabaseTransactionFailedException e) {
            throw new CantExecuteQueryException(e, "Error executing a database transaction. " + transaction.toString(), "database issue");
        }
    }

    private FermatActor getOwner(String publicKey) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.OWNER_TABLE_NAME);
        databaseTable.addStringFilter(TimeOutNotifierAgentDatabaseConstants.OWNER_PUBLICKEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        if (!databaseTable.getRecords().isEmpty()) {
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

    public List<TimeOutNotifierAgent> getPendingNotified() throws CantExecuteQueryException {

        return null;
    }


    private List<TimeOutNotifierAgent> loadTimeOutNotifierAgentsFromDatabase(DatabaseTable filteredTable) throws CantExecuteQueryException {
        try {
            filteredTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, filteredTable.getTableName());
        }

        List<TimeOutNotifierAgent> timeOutNotifierAgentList = new ArrayList<>();
        for (DatabaseTableRecord record : filteredTable.getRecords()) {
            TimeOutNotifierAgent agent = getTimeOutNotifierAgentFromRecord(record);

            // I will get the owner record
            FermatActor owner = getOwner(record.getStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_OWNER_PUBLICKEY_COLUMN_NAME));
            agent.setOwner(owner);

            timeOutNotifierAgentList.add(agent);
        }

        return timeOutNotifierAgentList;
    }

    public void removeTimeOutNotifierAgent(TimeOutAgent timeOutNotifierAgent) throws InconsistentResultObtainedInDatabaseQueryException, CantExecuteQueryException {
        if (timeOutNotifierAgent == null)
            thrownMissingParameterException();

        if (isNewTimeOutNotifierAgent(timeOutNotifierAgent))
            throw new InconsistentResultObtainedInDatabaseQueryException(null, "Trying to delete an un existing agent." + timeOutNotifierAgent.toString(), "inconsistent data");

        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addUUIDFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, timeOutNotifierAgent.getUUID(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }


        DatabaseTableRecord record = databaseTable.getRecords().get(0);
        try {
            databaseTable.deleteRecord(record);
        } catch (CantDeleteRecordException e) {
            throw new CantExecuteQueryException(e, "Can't delete existing record." + record.toString(), "Database issue");
        }

        // I will delete any registered event for this Agent
        removeEventMonitorRecord(timeOutNotifierAgent.getUUID());

    }

    private void removeEventMonitorRecord(UUID uuid) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_TABLE_NAME);
        databaseTable.addUUIDFilter(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AGENT_ID_COLUMN_NAME, uuid, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        if (!databaseTable.getRecords().isEmpty()) {
            DatabaseTableRecord record = databaseTable.getRecords().get(0);
            try {
                databaseTable.deleteRecord(record);
            } catch (CantDeleteRecordException e) {
                throw new CantExecuteQueryException(e, "Error deleting event record. " + uuid.toString(), "database issue");
            }
        }
    }

    private void thrownMissingParameterException() throws CantExecuteQueryException {
        throw new CantExecuteQueryException(null, "Parameter Can't be null", "Missing parameter");
    }

    public void updateTimeOutNotifierAgent(TimeOutAgent timeOutAgent) throws CantExecuteQueryException {
        if (timeOutAgent == null)
            thrownMissingParameterException();

        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addUUIDFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, timeOutAgent.getUUID(), DatabaseFilterType.EQUAL);

        DatabaseTableRecord record = getRecordFromTimeOutNotifierAgent(timeOutAgent);
        try {
            databaseTable.updateRecord(record);
        } catch (CantUpdateRecordException e) {
            throw new CantExecuteQueryException(e, "Error updating record. " + record.toString(), "Database error");
        }
    }

    public int updateMonitorEventData(UUID agentId) throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_TABLE_NAME);
        databaseTable.addUUIDFilter(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AGENT_ID_COLUMN_NAME, agentId, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        DatabaseTableRecord record;
        int numNotifications = 0;

        if (databaseTable.getRecords().size() == 0) {
            record = databaseTable.getEmptyRecord();
            record.setUUIDValue(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AGENT_ID_COLUMN_NAME, agentId);
            record.setIntegerValue(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AMOUNT_RAISE_COLUMN_NAME, 1);
            record.setLongValue(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_LAST_UPDATED_COLUMN_NAME, getCurrentTime());

            try {
                databaseTable.insertRecord(record);
            } catch (CantInsertRecordException e) {
                throw new CantExecuteQueryException(e, "Error inserting new record. " + record.toString(), "Database issue");
            }
        } else {
            record = databaseTable.getRecords().get(0);
            int current = record.getIntegerValue(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AMOUNT_RAISE_COLUMN_NAME);
            record.setIntegerValue(TimeOutNotifierAgentDatabaseConstants.EVENT_MONITOR_AMOUNT_RAISE_COLUMN_NAME, current + 1);

            numNotifications = current + 1;
            try {
                databaseTable.updateRecord(record);
            } catch (CantUpdateRecordException e) {
                throw new CantExecuteQueryException(e, "Error updating record. " + record.toString(), "Database issue");
            }
        }
        return numNotifications;
    }

    public void markAsRead(UUID uuid) throws CantExecuteQueryException, InconsistentResultObtainedInDatabaseQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addUUIDFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_ID_COLUMN_NAME, uuid, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        if (databaseTable.getRecords().size() != 1)
            throw new InconsistentResultObtainedInDatabaseQueryException(null, "Can't mark as read a non existing agent. UUID: " + uuid.toString(), "invalid data");

        DatabaseTableRecord record = databaseTable.getRecords().get(0);

        record.setStringValue(TimeOutNotifierAgentDatabaseConstants.AGENTS_READ_COLUMN_NAME, Boolean.TRUE.toString());
        try {
            databaseTable.updateRecord(record);
        } catch (CantUpdateRecordException e) {
            throw new CantExecuteQueryException(e, "unable to update read flag in record: " + record.toString(), "database error");
        }
    }

    public List<TimeOutNotifierAgent> getPendingNotification() throws CantExecuteQueryException {
        DatabaseTable databaseTable = database.getTable(TimeOutNotifierAgentDatabaseConstants.AGENTS_TABLE_NAME);
        databaseTable.addStringFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_READ_COLUMN_NAME, Boolean.FALSE.toString(), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(TimeOutNotifierAgentDatabaseConstants.AGENTS_STATE_COLUMN_NAME, AgentStatus.STARTED.getCode(), DatabaseFilterType.EQUAL);

        List<TimeOutNotifierAgent> timeOutNotifierAgentList = new ArrayList<>();

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            thrownCantExecuteQueryException(e, databaseTable.getTableName());
        }

        for (DatabaseTableRecord record : databaseTable.getRecords()) {
            timeOutNotifierAgentList.add(getTimeOutNotifierAgentFromRecord(record));
        }
        return timeOutNotifierAgentList;
    }
}
