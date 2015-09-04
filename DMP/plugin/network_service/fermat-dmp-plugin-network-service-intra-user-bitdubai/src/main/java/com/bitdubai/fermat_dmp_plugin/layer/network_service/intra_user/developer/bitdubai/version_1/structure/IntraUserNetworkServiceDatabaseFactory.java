/*
 * @#IntraUserNetworkServiceDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * Created by Roberto Requena - (rrequena) on 22/05/15.
 *
 * @version 1.0
 */
public class IntraUserNetworkServiceDatabaseFactory {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameter
     *
     * @param pluginDatabaseSystem the instance
     */
    public IntraUserNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the data base
     *
     * @param ownerId the owner id
     * @return Database object reference
     * @throws CantCreateDatabaseException error
     */
    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where going to store all the information of this network service.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, IntraUserNetworkServiceDatabaseConstants.DATA_BASE_NAME);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException();
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            /**
             * Configure the Incoming messages table.
             */
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(ownerId, IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME);

            table.addColumn(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.TRUE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            try {
                //Create the incoming messages table.
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException();
            }

            /**
             * Configure the Outgoing messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME);

            table.addColumn(IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_ID_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.TRUE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            try {
                //Create the outgoing messages table.
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException();
            }

            /**
             * Configure the Known Network Intra Users Cache table.
             */
            table = databaseFactory.newTableFactory(ownerId, IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_NAME);

            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_USER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_PROFILE_PICTURE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_LOCATION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_CREATED_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_UPDATE_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.FALSE);

            try {
                //Create the Known Network Intra Users Cache table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException();
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException();
        }
        return database;
    }
}
