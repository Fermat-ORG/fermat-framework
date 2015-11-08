package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.exceptions.CantLoadAssetAppropriationEventListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationDAO implements AutoCloseable {

    //VARIABLE DECLARATION
    private Database database;

    //CONSTRUCTORS
    public AssetAppropriationDAO(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantOpenDatabaseException, DatabaseNotFoundException {
        database = pluginDatabaseSystem.openDatabase(pluginId, AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_DATABASE);
    }

    //PUBLIC METHODS
    public void saveNewEvent(FermatEvent event) throws CantSaveEventException {
        String eventType = event.getEventType().getCode();
        String eventSource = event.getSource().getCode();

        String context = "Event Type : " + eventType + " - Event Source: " + eventSource;
        try {
            DatabaseTable databaseTable = this.database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());

            databaseTable.insertRecord(eventRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Reception database");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public List<String> getPendingActorAssetUserEvents() throws CantLoadAssetAppropriationEventListException {
        return getPendingEventsBySource(EventSource.ACTOR_ASSET_USER);
    }

    public EventType getEventTypeById(String id) throws CantLoadAssetAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventType.getByCode(getStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }

    public EventSource getEventSourceById(String id) throws CantLoadAssetAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventSource.getByCode(getStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, id));
    }

    public EventStatus getEventStatusById(String id) throws CantLoadAssetAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventStatus.getByCode(getStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, id));
    }

    public void updateEventStatus(EventStatus status, String eventId) throws CantLoadAssetAppropriationEventListException, RecordsNotFoundException {
        updateStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, status.getCode(), eventId);
    }

    public boolean isPendingActorAssetUserEvents() throws CantLoadAssetAppropriationEventListException {
        return isPendingEventsBySource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
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
    private boolean isPendingEventsBySource(EventSource eventSource) throws CantLoadAssetAppropriationEventListException {
        return !getPendingEventsBySource(eventSource).isEmpty();
    }

    private void updateStringFieldByEventId(String columnName, String value, String id) throws CantLoadAssetAppropriationEventListException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Id: " + id;
        try {
            DatabaseTable eventRecordedTable;
            eventRecordedTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            eventRecordedTable.setStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            eventRecordedTable.loadToMemory();
            if (eventRecordedTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }
            for (DatabaseTableRecord record : eventRecordedTable.getRecords()) {
                record.setStringValue(columnName, value);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationEventListException(exception, context, "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadAssetAppropriationEventListException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }


    private String getStringFieldByEventId(String columnName, String id) throws CantLoadAssetAppropriationEventListException, RecordsNotFoundException {
        try {
            String context = "Column Name: " + columnName + " - Id: " + id;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                return record.getStringValue(columnName);
            }
            throw new RecordsNotFoundException(null, context, "");
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadAssetAppropriationEventListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

    private List<String> getPendingEventsBySource(EventSource eventSource) throws CantLoadAssetAppropriationEventListException {
        try {
            DatabaseTable eventsRecordedTable;
            eventsRecordedTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);

            DatabaseTableFilter statusFilter = eventsRecordedTable.getEmptyTableFilter();
            statusFilter.setColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME);
            statusFilter.setValue(EventStatus.PENDING.getCode());
            statusFilter.setType(DatabaseFilterType.EQUAL);

            DatabaseTableFilter sourceFilter = eventsRecordedTable.getEmptyTableFilter();
            sourceFilter.setColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
            sourceFilter.setValue(eventSource.getCode());
            sourceFilter.setType(DatabaseFilterType.EQUAL);

            List<DatabaseTableFilter> filters = new ArrayList<>();
            filters.add(statusFilter);
            filters.add(sourceFilter);

            eventsRecordedTable.setFilterGroup(
                    eventsRecordedTable.getNewFilterGroup(filters,
                            new ArrayList<DatabaseTableFilterGroup>(),
                            DatabaseFilterOperator.AND));

            eventsRecordedTable.setFilterOrder(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            eventsRecordedTable.loadToMemory();
            List<String> eventIdList = new ArrayList<>();
            for (DatabaseTableRecord record : eventsRecordedTable.getRecords()) {
                eventIdList.add(record.getStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME));
            }
            return eventIdList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadAssetAppropriationEventListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

    //GETTER AND SETTERS

    //INNER CLASSES
}
