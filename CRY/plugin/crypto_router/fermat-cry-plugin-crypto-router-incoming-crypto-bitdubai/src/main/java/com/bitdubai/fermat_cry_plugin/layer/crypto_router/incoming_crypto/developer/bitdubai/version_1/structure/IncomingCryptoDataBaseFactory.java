package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.InvalidOwnerId;

import java.util.*;

/**
 * Created by Arturo Vallone on 25/04/15
 */
class IncomingCryptoDataBaseFactory implements DealsWithPluginDatabaseSystem {

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
     * IncomingCryptoDataBaseFactory methods.
     */
    Database createDatabase (UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        DatabaseTableFactory table;
        HashMap<String, List<IncomingCryptoDataBaseConstants.ColumnDefinition>> tablesDefinitions = new HashMap<>();

        /**
         * IncomingCryptoRegistry table
         */
        tablesDefinitions.put(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME, Arrays.asList(
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_TO_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_FROM_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TIMESTAMP_COLUMN
        ));

        /**
         * IncomingCryptoEventsRecorded table
         */
        tablesDefinitions.put(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME, Arrays.asList(
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_ID_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_EVENT_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_SOURCE_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN,
                IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN
        ));

        try {
            for(Map.Entry<String, List<IncomingCryptoDataBaseConstants.ColumnDefinition>> tableDefinition: tablesDefinitions.entrySet()){
                table = ((DatabaseFactory) database).newTableFactory(ownerId, tableDefinition.getKey());
                for(IncomingCryptoDataBaseConstants.ColumnDefinition columnDefinition: tableDefinition.getValue()){
                    table.addColumn(columnDefinition.columnName, columnDefinition.columnDataType, columnDefinition.columnDataTypeSize, columnDefinition.columnIsPrimaryKey);
                }
            }

        } catch (InvalidOwnerId invalidOwnerId) {
            System.out.println("InvalidOwnerId: " + invalidOwnerId.getMessage());
            invalidOwnerId.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        return database;
    }
}
