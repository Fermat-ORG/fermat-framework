package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.*;

/**
 * Created by Arturo Vallone on 25/04/15
 */
public class IncomingExtraUserDataBaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    public IncomingExtraUserDataBaseFactory(final PluginDatabaseSystem pluginDatabaseSystem){
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * IncomingExtraUserDataBaseFactory methods.
     * The whole login of this module should be redesigned
     */
    public Database createDatabase (UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        try {
            Database database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
            DatabaseFactory databaseFactory = database.getDatabaseFactory();
            DatabaseTableFactory table;
            HashMap<String, List<IncomingExtraUserDataBaseConstants.ColumnDefinition>> tablesDefinitions = new HashMap<>();

            /**
             * IncomingExtraUserRegistry table
             */
            tablesDefinitions.put(IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME, Arrays.asList(
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ID_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_TO_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_FROM_COLUMN,
                    //  IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_SPECIALIST_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ACTION_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TIMESTAMP_COLUMN
            ));

            /**
             * IncomingCryptoEventsRecorded table
             */
            tablesDefinitions.put(IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_NAME, Arrays.asList(
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_ID_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_EVENT_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_SOURCE_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_STATUS_COLUMN,
                    IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN
            ));

            try {
                for (Map.Entry<String, List<IncomingExtraUserDataBaseConstants.ColumnDefinition>> tableDefinition : tablesDefinitions.entrySet()) {
                    table = databaseFactory.newTableFactory(ownerId, tableDefinition.getKey());
                    for (IncomingExtraUserDataBaseConstants.ColumnDefinition columnDefinition : tableDefinition.getValue()) {
                        table.addColumn(columnDefinition.columnName, columnDefinition.columnDataType, columnDefinition.columnDataTypeSize, columnDefinition.columnIsPrimaryKey);
                    }
                    databaseFactory.createTable(table);
                }

            } catch (InvalidOwnerIdException exception) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, "We are passing the wrong OwnerId to the new TableFactory object");
            } catch (CantCreateTableException exception) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, "The table must have been improperly setup, check the context of the cause for the specification of the table");
            } catch (Exception exception) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause of this error");
            }
            return database;
        } catch (CantCreateDatabaseException e) {
            throw e;
        } catch (Exception e) {
            throw new CantCreateDatabaseException("An Unexpected Exception Happened", FermatException.wrapException(e),"","");
        }
    }
}
