/*
 * @#TemplateNetworkServiceDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications;

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
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_2.database.communications.CommunicationNetworkServiceDatabaseFactory</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15 - Jose Briceño - (joebricenor@gmail.com) on 18/02/16.
 *
 * @version 2.0
 * @since Java JDK 1.7
 */

public class CommunicationNetworkServiceDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with parameters to instantiate class
     *
     * @param pluginDatabaseSystem
     */
    public CommunicationNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * Create the database
     *
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase() throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();
            /**
             * Create DAP_Message table.
             */
            table = databaseFactory.newTableFactory(pluginId, CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TABLE_NAME);

            table.addColumn(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_DATA_COLUMN_NAME, DatabaseDataType.STRING, 1000, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_SUBJECT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);


            table.addIndex(CommunicationNetworkServiceDatabaseConstants.DAP_MESSAGE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(pluginId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }
}
