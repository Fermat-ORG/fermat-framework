package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerId;

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
     */
    Database createDatabase (UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        DatabaseTableFactory table;
        HashMap<String, List<IncomingExtraUSerDataBaseConstants.ColumnDefinition>> tablesDefinitions = new HashMap<>();

        /**
         * IncomingExtraUserRegistry table
         */
        tablesDefinitions.put(IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME, Arrays.asList(
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ID_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_TO_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_FROM_COLUMN,
                //  IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_SPECIALIST_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ACTION_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TIMESTAMP_COLUMN
        ));

        /**
         * IncomingCryptoEventsRecorded table
         */
        tablesDefinitions.put(IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_NAME, Arrays.asList(
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_ID_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_EVENT_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_SOURCE_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_STATUS_COLUMN,
                IncomingExtraUSerDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN
        ));

        try {
            for(Map.Entry<String, List<IncomingExtraUSerDataBaseConstants.ColumnDefinition>> tableDefinition: tablesDefinitions.entrySet()){
                table = ((DatabaseFactory) database).newTableFactory(ownerId, tableDefinition.getKey());
                System.err.println("INCOMING EXTRA USER REGISTRY: " + tableDefinition.getKey() + " TABLE CREATED");
                for(IncomingExtraUSerDataBaseConstants.ColumnDefinition columnDefinition: tableDefinition.getValue()){
                    table.addColumn(columnDefinition.columnName, columnDefinition.columnDataType, columnDefinition.columnDataTypeSize, columnDefinition.columnIsPrimaryKey);
                    System.err.println("INCOMING EXTRA USER REGISTRY: " + tableDefinition.getKey() + " - " + columnDefinition.columnName + " COLUMN ADDED");
                }
                ((DatabaseFactory) database).createTable(table);
            }

        } catch (InvalidOwnerId invalidOwnerId) {
            System.out.println("InvalidOwnerId: " + invalidOwnerId.getMessage());
            invalidOwnerId.printStackTrace();
            throw new CantCreateDatabaseException();
        } catch (CantCreateTableException e) {
            System.out.println("InvalidOwnerId: CantCreateTableException " + e.getMessage());
            throw new CantCreateDatabaseException();
        }
        return database;
    }
}
