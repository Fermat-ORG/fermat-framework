package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.exceptions.CantLoadAppropriationStatsEventListException;

import java.io.Closeable;
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
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Appropriation Event Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
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

    //GETTER AND SETTERS

    //INNER CLASSES
}
