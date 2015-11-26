package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.exceptions.CantCheckAppropriationStatsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.exceptions.CantStartAppropriationStatsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.exceptions.CantLoadAppropriationStatsEventListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 18/11/15.
 */
public class AppropriationStatsDAO implements AutoCloseable {


    //VARIABLE DECLARATION

    private Database database;

    //CONSTRUCTORS

    public AppropriationStatsDAO(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantOpenDatabaseException, DatabaseNotFoundException {
        this.database = pluginDatabaseSystem.openDatabase(pluginId, AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_DATABASE);
    }


    //PUBLIC METHODS


    /*
    * Event Recorded Table's Actions.
    *
    */
    public void saveNewEvent(FermatEvent event) throws CantSaveEventException {
        String eventType = event.getEventType().getCode();
        String eventSource = event.getSource().getCode();

        String context = "Event Type : " + eventType + " - Event Source: " + eventSource;
        try {
            DatabaseTable databaseTable = this.database.getTable(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());

            databaseTable.insertRecord(eventRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Appropriation Stats Event Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public void assetAppropriated(DigitalAsset asset, ActorAssetUser user) throws CantStartAppropriationStatsException {
        String context = "Asset: " + asset + " \n"
                + " User: " + user;
        try {
            DatabaseTable databaseTable = this.database.getTable(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_TABLE_NAME);
            DatabaseTableRecord appropriatedRecord = databaseTable.getEmptyRecord();
            UUID recordId = UUID.randomUUID();

            appropriatedRecord.setUUIDValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_ID_COLUMN_NAME, recordId);
            appropriatedRecord.setStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_ASSET_COLUMN_NAME, XMLParser.parseObject(asset));
            appropriatedRecord.setStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_USER_COLUMN_NAME, XMLParser.parseObject(user));
            appropriatedRecord.setLongValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_TIME_COLUMN_NAME, System.currentTimeMillis());

            databaseTable.insertRecord(appropriatedRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantStartAppropriationStatsException(exception, context, "Cannot insert a record in Asset Appropriation Stats Appropriated Table");
        } catch (Exception exception) {
            throw new CantStartAppropriationStatsException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public List<ActorAssetUser> usersThatAppropriatedAsset(DigitalAsset assetAppropriated) throws CantCheckAppropriationStatsException {
        String context = "Asset: " + assetAppropriated;
        try {
            DatabaseTable appropriatedTable = database.getTable(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_TABLE_NAME);
            appropriatedTable.setStringFilter(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_ASSET_COLUMN_NAME, XMLParser.parseObject(assetAppropriated), DatabaseFilterType.EQUAL);
            appropriatedTable.loadToMemory();
            List<ActorAssetUser> listToReturn = new ArrayList<>();
            if (appropriatedTable.getRecords().isEmpty()) return Collections.EMPTY_LIST;

            for (DatabaseTableRecord record : appropriatedTable.getRecords()) {
                listToReturn.add((AssetUserActorRecord) XMLParser.parseXML(record.getStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_USER_COLUMN_NAME), new AssetUserActorRecord()));
            }
            return listToReturn;
        } catch (Exception e) {
            throw new CantCheckAppropriationStatsException(FermatException.wrapException(e), context, "Unknown");
        }
    }

    public List<DigitalAsset> assetsAppropriatedByUser(ActorAssetUser userThatAppropriated) throws CantCheckAppropriationStatsException {
        String context = "ActorAssetUser: " + userThatAppropriated;
        try {
            DatabaseTable appropriatedTable = database.getTable(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_TABLE_NAME);
            appropriatedTable.setStringFilter(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_USER_COLUMN_NAME, XMLParser.parseObject(userThatAppropriated), DatabaseFilterType.EQUAL);
            appropriatedTable.loadToMemory();
            List<DigitalAsset> listToReturn = new ArrayList<>();
            if (appropriatedTable.getRecords().isEmpty()) return Collections.EMPTY_LIST;

            for (DatabaseTableRecord record : appropriatedTable.getRecords()) {
                listToReturn.add((DigitalAsset) XMLParser.parseXML(record.getStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_ASSET_COLUMN_NAME), new DigitalAsset()));
            }
            return listToReturn;
        } catch (Exception e) {
            throw new CantCheckAppropriationStatsException(FermatException.wrapException(e), context, "Unknown");
        }
    }

    public Map<ActorAssetUser, DigitalAsset> allAppropriationStats() throws CantCheckAppropriationStatsException {
        try {
            DatabaseTable appropriatedTable = database.getTable(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_TABLE_NAME);
            appropriatedTable.loadToMemory();

            Map<ActorAssetUser, DigitalAsset> mapToReturn = new HashMap<>();
            if (appropriatedTable.getRecords().isEmpty()) return Collections.EMPTY_MAP;

            for (DatabaseTableRecord record : appropriatedTable.getRecords()) {
                DigitalAsset asset = (DigitalAsset) XMLParser.parseXML(record.getStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_ASSET_COLUMN_NAME), new DigitalAsset());
                ActorAssetUser user = (ActorAssetUser) XMLParser.parseXML(record.getStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_APPROPRIATED_USER_COLUMN_NAME), new AssetUserActorRecord());
                mapToReturn.put(user, asset);
            }
            return mapToReturn;
        } catch (Exception e) {
            throw new CantCheckAppropriationStatsException(FermatException.wrapException(e), "SELECT ALL.", "Unknown");
        }
    }

    public void notifyEvent(String eventId) throws CantLoadAppropriationStatsEventListException, RecordsNotFoundException {
        updateStringFieldByEventId(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.NOTIFIED.getCode(), eventId);
    }


    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     * <p/>
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     * <p/>
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p/>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p/>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     * <p/>
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p/>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        database.closeDatabase();
    }
    //PRIVATE METHODS

    private void updateStringFieldByEventId(String columnName, String value, String id) throws CantLoadAppropriationStatsEventListException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Id: " + id;
        try {
            DatabaseTable eventRecordedTable;
            eventRecordedTable = database.getTable(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_NAME);
            eventRecordedTable.setStringFilter(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            eventRecordedTable.loadToMemory();

            if (eventRecordedTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            for (DatabaseTableRecord record : eventRecordedTable.getRecords()) {
                record.setStringValue(columnName, value);
                eventRecordedTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAppropriationStatsEventListException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantLoadAppropriationStatsEventListException(exception, context, "Cannot update record.");
        }
    }

    private String getStringFieldByEventId(String columnName, String id) throws CantLoadAppropriationStatsEventListException, RecordsNotFoundException {
        try {
            String context = "Column Name: " + columnName + " - Id: " + id;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.setFilterOrder(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                return record.getStringValue(columnName);
            }
            throw new RecordsNotFoundException(null, context, "");
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAppropriationStatsEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private List<String> getPendingEventsBySource(EventSource eventSource) throws CantLoadAppropriationStatsEventListException {
        try {
            DatabaseTable eventsRecordedTable;
            eventsRecordedTable = database.getTable(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_NAME);

            DatabaseTableFilter statusFilter = eventsRecordedTable.getEmptyTableFilter();
            statusFilter.setColumn(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_STATUS_COLUMN_NAME);
            statusFilter.setValue(EventStatus.PENDING.getCode());
            statusFilter.setType(DatabaseFilterType.EQUAL);

            DatabaseTableFilter sourceFilter = eventsRecordedTable.getEmptyTableFilter();
            sourceFilter.setColumn(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
            sourceFilter.setValue(eventSource.getCode());
            sourceFilter.setType(DatabaseFilterType.EQUAL);

            List<DatabaseTableFilter> filters = new ArrayList<>();
            filters.add(statusFilter);
            filters.add(sourceFilter);

            eventsRecordedTable.setFilterGroup(
                    eventsRecordedTable.getNewFilterGroup(filters,
                            new ArrayList<DatabaseTableFilterGroup>(),
                            DatabaseFilterOperator.AND));

            eventsRecordedTable.setFilterOrder(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            eventsRecordedTable.loadToMemory();
            List<String> eventIdList = new ArrayList<>();
            for (DatabaseTableRecord record : eventsRecordedTable.getRecords()) {
                eventIdList.add(record.getStringValue(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_ID_COLUMN_NAME));
            }
            return eventIdList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAppropriationStatsEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadAppropriationStatsEventListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

    //GETTER AND SETTERS

    public List<String> getPendingIssuerNetworkServiceEvents() throws CantLoadAppropriationStatsEventListException {
        return getPendingEventsBySource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER);
    }


    public EventType getEventTypeById(String id) throws CantLoadAppropriationStatsEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventType.getByCode(getStringFieldByEventId(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }
    //INNER CLASSES
}
