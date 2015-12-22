package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class BrokerSubmitOfflineMerchandiseBusinessTransactionDao {
    
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public BrokerSubmitOfflineMerchandiseBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database             = database            ;
    }

    public void initialize() throws CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(
                    CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Generic Exception.");
        }
    }

    /**
     * Returns the Database
     *
     * @return Database
     */
    private Database getDataBase() {
        return database;
    }

    /**
     * Returns the Ack Online Payment DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseSubmitTable() {
        return getDataBase().getTable(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_TABLE_NAME);
    }

    /**
     * Returns the Submit Online Merchandise Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * This method save an incoming new event in database.
     * @param eventType
     * @param eventSource
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            UUID eventRecordID = UUID.randomUUID();
            saveNewEvent(eventType, eventSource, eventRecordID.toString());

        } catch(Exception exception){
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method save an incoming new event in database. You can set the event Id with this method
     * @param eventType
     * @param eventSource
     * @param eventId
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource, String eventId) throws CantSaveEventException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            long unixTime = System.currentTimeMillis();
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(BrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_OFFLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(
                    exception,
                    "Saving new event.",
                    "Cannot insert a record in Submit Online Merchandise database");
        } catch(Exception exception){
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

}
