package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/11/15.
 */
public class UserRedemptionDao {
    UUID pluginId;
    Database database;
    PluginDatabaseSystem pluginDatabaseSystem;

    public UserRedemptionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        database = openDatabase();
        database.closeDatabase();

    }

    private DatabaseTable getDatabaseTable(String tableName){
        DatabaseTable assetDistributionDatabaseTable = database.getTable(tableName);
        return assetDistributionDatabaseTable;
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, UserRedemptionDatabaseConstants.USER_REDEMPTION_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the User Redemption Transaction Database", "Error in database plugin.");
        }
    }

    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            this.database=openDatabase();
            DatabaseTable databaseTable = this.database.getTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            Logger LOG = Logger.getGlobal();
            LOG.info("Distribution DAO:\nUUID:"+eventRecordID+"\n"+unixTime);
            eventRecord.setUUIDValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.toString());
            eventRecord.setLongValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);
            LOG.info("record:" + eventRecord.getStringValue(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME));
            this.database.closeDatabase();
        }  catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot open or find the User Redemption database");
        } catch (CantInsertRecordException exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in User Redemption database");
        } catch(Exception exception){
            this.database.closeDatabase();
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }
}
