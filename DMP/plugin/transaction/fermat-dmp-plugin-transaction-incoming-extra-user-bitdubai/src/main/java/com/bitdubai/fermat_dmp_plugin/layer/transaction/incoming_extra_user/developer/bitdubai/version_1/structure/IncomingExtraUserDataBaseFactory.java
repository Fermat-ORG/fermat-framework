package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.*;

/**
 * Created by Arturo Vallone on 25/04/15
 */
class IncomingExtraUserDataBaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;


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
    Database createDatabase (UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
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
            for(Map.Entry<String, List<IncomingExtraUserDataBaseConstants.ColumnDefinition>> tableDefinition: tablesDefinitions.entrySet()){
                table = ((DatabaseFactory) database).newTableFactory(ownerId, tableDefinition.getKey());
                System.err.println("INCOMING EXTRA USER REGISTRY: " + tableDefinition.getKey() + " TABLE CREATED");
                for(IncomingExtraUserDataBaseConstants.ColumnDefinition columnDefinition: tableDefinition.getValue()){
                    table.addColumn(columnDefinition.columnName, columnDefinition.columnDataType, columnDefinition.columnDataTypeSize, columnDefinition.columnIsPrimaryKey);
                    System.err.println("INCOMING EXTRA USER REGISTRY: " + tableDefinition.getKey() + " - " + columnDefinition.columnName + " COLUMN ADDED");
                }
                //TODO FIX THIS LOGIC! THIS IS SO WRONG!!!!!!
                ((DatabaseFactory) database).createTable(table);
            }

        } catch (InvalidOwnerIdException exception) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, "We are passing the wrong OwnerId to the new TableFactory object");
        } catch (CantCreateTableException exception) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, "The table must have been improperly setup, check the context of the cause for the specification of the table");
        }
        return database;
    }
}
