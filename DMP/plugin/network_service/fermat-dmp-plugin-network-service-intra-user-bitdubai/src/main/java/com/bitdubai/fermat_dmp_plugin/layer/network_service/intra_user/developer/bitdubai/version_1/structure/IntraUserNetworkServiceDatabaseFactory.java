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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerId;

import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 *
 * Created by Roberto Requena - (rrequena) on 22/05/15.
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
     * @param pluginDatabaseSystem
     */
    public IntraUserNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the data base
     *
     * @param ownerId the owner id
     * @param networkIntraUserId the intra user id
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(UUID ownerId, UUID networkIntraUserId) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(ownerId, networkIntraUserId.toString());

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException();
        }


        /**
         * Next, I will add the needed tables.
         */
        try {

            DatabaseTableFactory table;

            /**
             * Create the Known Network Intra Users Cache table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_NAME);

            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_ID_COLUMN_NAME,              DatabaseDataType.STRING,       100, Boolean.TRUE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_USER_NAME_COLUMN_NAME,       DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_PROFILE_PICTURE_COLUMN_NAME, DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_ADDRESS_COLUMN_NAME,         DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_LOCATION_COLUMN_NAME,        DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_CREATED_TIME_COLUMN_NAME,    DatabaseDataType.LONG_INTEGER,  50, Boolean.FALSE);
            table.addColumn(IntraUserNetworkServiceDatabaseConstants.KNOWN_NETWORK_INTRA_USER_CACHE_TABLE_UPDATE_TIME_COLUMN_NAME,     DatabaseDataType.LONG_INTEGER,  50, Boolean.FALSE);

            try {

                //Create the table
                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException();
            }



        }
        catch (InvalidOwnerId invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException();
        }

        return database;
    }
}
