/*
 * @#CommunicationsNetworkNodeP2PDeveloperDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database;


import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInitializeCommunicationsNetworkNodeP2PDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CommunicationsNetworkNodeP2PDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public CommunicationsNetworkNodeP2PDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CommunicationsNetworkNodeP2PDatabaseFactory communicationsNetworkNodeP2PDatabaseFactory = new CommunicationsNetworkNodeP2PDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = communicationsNetworkNodeP2PDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Communications Network Node", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table actor catalog columns.
         */
        List<String> actorcatalogColumns = new ArrayList<String>();

        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NAME_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ALIAS_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ACTOR_TYPE_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_PHOTO_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LATITUDE_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LONGITUDE_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_EXTRA_DATA_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_HOSTED_TIMESTAMP_COLUMN_NAME);
        actorcatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        /**
         * Table actor catalog addition.
         */
        DeveloperDatabaseTable actorcatalogTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TABLE_NAME, actorcatalogColumns);
        tables.add(actorcatalogTable);

        /**
         * Table actor catalog transaction columns.
         */
        List<String> actorcatalogtransactionColumns = new ArrayList<String>();

        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_NAME_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_ALIAS_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_PHOTO_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_EXTRA_DATA_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_HOSTED_TIMESTAMP_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorcatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME);
        /**
         * Table actor catalog transaction addition.
         */
        DeveloperDatabaseTable actorcatalogtransactionTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_TABLE_NAME, actorcatalogtransactionColumns);
        tables.add(actorcatalogtransactionTable);

        /**
         * Table actors catalog transactions pending for propagation columns.
         */
        List<String> actorscatalogtransactionspendingforpropagationColumns = new ArrayList<String>();

        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NAME_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ALIAS_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ACTOR_TYPE_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_PHOTO_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LATITUDE_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LONGITUDE_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_EXTRA_DATA_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HOSTED_TIMESTAMP_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TRANSACTION_TYPE_COLUMN_NAME);
        /**
         * Table actors catalog transactions pending for propagation addition.
         */
        DeveloperDatabaseTable actorscatalogtransactionspendingforpropagationTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME, actorscatalogtransactionspendingforpropagationColumns);
        tables.add(actorscatalogtransactionspendingforpropagationTable);

        /**
         * Table calls log columns.
         */
        List<String> callslogColumns = new ArrayList<String>();

        callslogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_CALL_ID_COLUMN_NAME);
        callslogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_CALL_TIMESTAMP_COLUMN_NAME);
        callslogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_FINISH_TIMESTAMP_COLUMN_NAME);
        callslogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_START_TIMESTAMP_COLUMN_NAME);
        callslogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_STEP_COLUMN_NAME);
        /**
         * Table calls log addition.
         */
        DeveloperDatabaseTable callslogTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_TABLE_NAME, callslogColumns);
        tables.add(callslogTable);

        /**
         * Table checked in actor columns.
         */
        List<String> checkedinactorColumns = new ArrayList<String>();

        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_UUID_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NAME_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_PHOTO_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LATITUDE_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LONGITUDE_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CHECKED_IN_TIMESTAMP_COLUMN_NAME);
        checkedinactorColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NS_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        /**
         * Table checked in actor addition.
         */
        DeveloperDatabaseTable checkedinactorTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_TABLE_NAME, checkedinactorColumns);
        tables.add(checkedinactorTable);

        /**
         * Table checked in clients columns.
         */
        List<String> checkedinclientsColumns = new ArrayList<String>();

        checkedinclientsColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_UUID_COLUMN_NAME);
        checkedinclientsColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinclientsColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_LATITUDE_COLUMN_NAME);
        checkedinclientsColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_LONGITUDE_COLUMN_NAME);
        checkedinclientsColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_DEVICE_TYPE_COLUMN_NAME);
        checkedinclientsColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_CHECKED_IN_TIMESTAMP_COLUMN_NAME);
        /**
         * Table checked in clients addition.
         */
        DeveloperDatabaseTable checkedinclientsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_TABLE_NAME, checkedinclientsColumns);
        tables.add(checkedinclientsTable);

        /**
         * Table checked in network service columns.
         */
        List<String> checkedinnetworkserviceColumns = new ArrayList<String>();

        checkedinnetworkserviceColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_UUID_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_LATITUDE_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_LONGITUDE_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_NETWORK_SERVICE_TYPE_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_CHECKED_IN_TIMESTAMP_COLUMN_NAME);
        /**
         * Table checked in network service addition.
         */
        DeveloperDatabaseTable checkedinnetworkserviceTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_TABLE_NAME, checkedinnetworkserviceColumns);
        tables.add(checkedinnetworkserviceTable);

        /**
         * Table checked actors history columns.
         */
        List<String> checkedactorshistoryColumns = new ArrayList<String>();

        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_UUID_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_NAME_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_ALIAS_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_ACTOR_TYPE_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_PHOTO_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CLIENT_IDENTITY_PUBLICKEY_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_EXTRA_DATA_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME);
        checkedactorshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CHECK_TYPE_COLUMN_NAME);
        /**
         * Table checked actors history addition.
         */
        DeveloperDatabaseTable checkedactorshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_TABLE_NAME, checkedactorshistoryColumns);
        tables.add(checkedactorshistoryTable);

        /**
         * Table checked clients history columns.
         */
        List<String> checkedclientshistoryColumns = new ArrayList<String>();

        checkedclientshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_UUID_COLUMN_NAME);
        checkedclientshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedclientshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        checkedclientshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        checkedclientshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_DEVICE_TYPE_COLUMN_NAME);
        checkedclientshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME);
        checkedclientshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_CHECK_TYPE_COLUMN_NAME);
        /**
         * Table checked clients history addition.
         */
        DeveloperDatabaseTable checkedclientshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_TABLE_NAME, checkedclientshistoryColumns);
        tables.add(checkedclientshistoryTable);

        /**
         * Table checked network service history columns.
         */
        List<String> checkednetworkservicehistoryColumns = new ArrayList<String>();

        checkednetworkservicehistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_UUID_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_CHECK_TYPE_COLUMN_NAME);
        /**
         * Table checked network service history addition.
         */
        DeveloperDatabaseTable checkednetworkservicehistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_TABLE_NAME, checkednetworkservicehistoryColumns);
        tables.add(checkednetworkservicehistoryTable);

        /**
         * Table clients connections history columns.
         */
        List<String> clientsconnectionshistoryColumns = new ArrayList<String>();

        clientsconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_UUID_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_DEVICE_TYPE_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME);
        /**
         * Table clients connections history addition.
         */
        DeveloperDatabaseTable clientsconnectionshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_TABLE_NAME, clientsconnectionshistoryColumns);
        tables.add(clientsconnectionshistoryTable);

        /**
         * Table nodes connections history columns.
         */
        List<String> nodesconnectionshistoryColumns = new ArrayList<String>();

        nodesconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_UUID_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_IP_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_DEFAULT_PORT_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME);
        /**
         * Table nodes connections history addition.
         */
        DeveloperDatabaseTable nodesconnectionshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_TABLE_NAME, nodesconnectionshistoryColumns);
        tables.add(nodesconnectionshistoryTable);

        /**
         * Table method calls history columns.
         */
        List<String> methodcallshistoryColumns = new ArrayList<String>();

        methodcallshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_UUID_COLUMN_NAME);
        methodcallshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_METHOD_NAME_COLUMN_NAME);
        methodcallshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_PARAMETERS_COLUMN_NAME);
        methodcallshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        methodcallshistoryColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_CREATE_TIMESTAMP_COLUMN_NAME);
        /**
         * Table method calls history addition.
         */
        DeveloperDatabaseTable methodcallshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_TABLE_NAME, methodcallshistoryColumns);
        tables.add(methodcallshistoryTable);

        /**
         * Table nodes catalog columns.
         */
        List<String> nodescatalogColumns = new ArrayList<String>();

        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_NAME_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_IP_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_DEFAULT_PORT_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_LAST_LATITUDE_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_LAST_LONGITUDE_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_OFFLINE_COUNTER_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME);
        nodescatalogColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME);
        /**
         * Table nodes catalog addition.
         */
        DeveloperDatabaseTable nodescatalogTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TABLE_NAME, nodescatalogColumns);
        tables.add(nodescatalogTable);

        /**
         * Table nodes catalog transaction columns.
         */
        List<String> nodescatalogtransactionColumns = new ArrayList<String>();

        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_NAME_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_IP_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_DEFAULT_PORT_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_REGISTERED_TIMESTAMP_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME);
        nodescatalogtransactionColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME);
        /**
         * Table nodes catalog transaction addition.
         */
        DeveloperDatabaseTable nodescatalogtransactionTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_TABLE_NAME, nodescatalogtransactionColumns);
        tables.add(nodescatalogtransactionTable);

        /**
         * Table nodes catalog transactions pending for propagation columns.
         */
        List<String> nodescatalogtransactionspendingforpropagationColumns = new ArrayList<String>();

        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NAME_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IP_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_DEFAULT_PORT_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LATITUDE_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LONGITUDE_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_REGISTERED_TIMESTAMP_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TRANSACTION_TYPE_COLUMN_NAME);
        /**
         * Table nodes catalog transactions pending for propagation addition.
         */
        DeveloperDatabaseTable nodescatalogtransactionspendingforpropagationTable = developerObjectFactory.getNewDeveloperDatabaseTable(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME, nodescatalogtransactionspendingforpropagationColumns);
        tables.add(nodescatalogtransactionspendingforpropagationTable);



        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();


        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        List<String> developerRow = new ArrayList<String>();
        for (DatabaseTableRecord row : records) {
            /**
             * for each row in the table list
             */
            for (DatabaseRecord field : row.getValues()) {
                /**
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue().toString());
            }
            /**
             * I create the Developer Database record
             */
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }


        /**
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}