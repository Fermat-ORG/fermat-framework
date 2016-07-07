package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInitializeCommunicationsNetworkNodeP2PDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.*;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public final class CommunicationsNetworkNodeP2PDeveloperDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem database system plug-in reference
     * @param pluginId             plug-in id reference
     */
    public CommunicationsNetworkNodeP2PDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                                final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
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
            database = this.pluginDatabaseSystem.openDatabase(pluginId, DATA_BASE_NAME);

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
                database = communicationsNetworkNodeP2PDatabaseFactory.createDatabase(pluginId, DATA_BASE_NAME);
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
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Communications Network Node", DATA_BASE_NAME));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(final DeveloperObjectFactory developerObjectFactory,
                                                             final DeveloperDatabase      developerDatabase     ) {

        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table actor catalog columns.
         */
        List<String> actorcatalogColumns = new ArrayList<>();

        actorcatalogColumns.add(ACTOR_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_NAME_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_ALIAS_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_ACTOR_TYPE_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_PHOTO_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_THUMBNAIL_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_LAST_LATITUDE_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_LAST_LONGITUDE_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_EXTRA_DATA_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_HOSTED_TIMESTAMP_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_LAST_UPDATE_TIME_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_LAST_CONNECTION_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorcatalogColumns.add(ACTOR_CATALOG_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        /**
         * Table actor catalog addition.
         */
        DeveloperDatabaseTable actorcatalogTable = developerObjectFactory.getNewDeveloperDatabaseTable(ACTOR_CATALOG_TABLE_NAME, actorcatalogColumns);
        tables.add(actorcatalogTable);

        /**
         * Table actor catalog transaction columns.
         */
        List<String> actorcatalogtransactionColumns = new ArrayList<>();

        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_NAME_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_ALIAS_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_PHOTO_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_THUMBNAIL_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_EXTRA_DATA_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_HOSTED_TIMESTAMP_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_LAST_CONNECTION_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME);
        actorcatalogtransactionColumns.add(ACTOR_CATALOG_TRANSACTION_GENERATION_TIME_COLUMN_NAME);
        /**
         * Table actor catalog transaction addition.
         */
        DeveloperDatabaseTable actorcatalogtransactionTable = developerObjectFactory.getNewDeveloperDatabaseTable(ACTOR_CATALOG_TRANSACTION_TABLE_NAME, actorcatalogtransactionColumns);
        tables.add(actorcatalogtransactionTable);

        /**
         * Table actors catalog transactions pending for propagation columns.
         */
        List<String> actorscatalogtransactionspendingforpropagationColumns = new ArrayList<>();

        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_NAME_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_ALIAS_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_PHOTO_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_THUMBNAIL_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_EXTRA_DATA_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_HOSTED_TIMESTAMP_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_LAST_CONNECTION_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME);
        actorscatalogtransactionspendingforpropagationColumns.add(ACTOR_CATALOG_TRANSACTION_GENERATION_TIME_COLUMN_NAME);
        /**
         * Table actors catalog transactions pending for propagation addition.
         */
        DeveloperDatabaseTable actorscatalogtransactionspendingforpropagationTable = developerObjectFactory.getNewDeveloperDatabaseTable(ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME, actorscatalogtransactionspendingforpropagationColumns);
        tables.add(actorscatalogtransactionspendingforpropagationTable);

        /**
         * Table calls log columns.
         */
        List<String> callslogColumns = new ArrayList<>();

        callslogColumns.add(CALLS_LOG_CALL_ID_COLUMN_NAME);
        callslogColumns.add(CALLS_LOG_CALL_TIMESTAMP_COLUMN_NAME);
        callslogColumns.add(CALLS_LOG_FINISH_TIMESTAMP_COLUMN_NAME);
        callslogColumns.add(CALLS_LOG_START_TIMESTAMP_COLUMN_NAME);
        callslogColumns.add(CALLS_LOG_STEP_COLUMN_NAME);
        /**
         * Table calls log addition.
         */
        DeveloperDatabaseTable callslogTable = developerObjectFactory.getNewDeveloperDatabaseTable(CALLS_LOG_TABLE_NAME, callslogColumns);
        tables.add(callslogTable);

        /**
         * Table checked in actor columns.
         */
        List<String> checkedinactorColumns = new ArrayList<>();

        checkedinactorColumns.add(CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_NAME_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_PHOTO_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_LATITUDE_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_LONGITUDE_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_CHECKED_IN_TIMESTAMP_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_NS_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinactorColumns.add(CHECKED_IN_ACTOR_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);

        /**
         * Table checked in actor addition.
         */
        DeveloperDatabaseTable checkedinactorTable = developerObjectFactory.getNewDeveloperDatabaseTable(CHECKED_IN_ACTOR_TABLE_NAME, checkedinactorColumns);
        tables.add(checkedinactorTable);

        /**
         * Table checked in clients columns.
         */
        List<String> checkedinclientsColumns = new ArrayList<>();

        checkedinclientsColumns.add(CHECKED_IN_CLIENTS_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinclientsColumns.add(CHECKED_IN_CLIENTS_LATITUDE_COLUMN_NAME);
        checkedinclientsColumns.add(CHECKED_IN_CLIENTS_LONGITUDE_COLUMN_NAME);
        checkedinclientsColumns.add(CHECKED_IN_CLIENTS_DEVICE_TYPE_COLUMN_NAME);
        checkedinclientsColumns.add(CHECKED_IN_CLIENTS_CHECKED_IN_TIMESTAMP_COLUMN_NAME);
        /**
         * Table checked in clients addition.
         */
        DeveloperDatabaseTable checkedinclientsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CHECKED_IN_CLIENTS_TABLE_NAME, checkedinclientsColumns);
        tables.add(checkedinclientsTable);

        /**
         * Table checked in network service columns.
         */
        List<String> checkedinnetworkserviceColumns = new ArrayList<>();

        checkedinnetworkserviceColumns.add(CHECKED_IN_NETWORK_SERVICE_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CHECKED_IN_NETWORK_SERVICE_LATITUDE_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CHECKED_IN_NETWORK_SERVICE_LONGITUDE_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CHECKED_IN_NETWORK_SERVICE_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CHECKED_IN_NETWORK_SERVICE_NETWORK_SERVICE_TYPE_COLUMN_NAME);
        checkedinnetworkserviceColumns.add(CHECKED_IN_NETWORK_SERVICE_CHECKED_IN_TIMESTAMP_COLUMN_NAME);
        /**
         * Table checked in network service addition.
         */
        DeveloperDatabaseTable checkedinnetworkserviceTable = developerObjectFactory.getNewDeveloperDatabaseTable(CHECKED_IN_NETWORK_SERVICE_TABLE_NAME, checkedinnetworkserviceColumns);
        tables.add(checkedinnetworkserviceTable);

        /**
         * Table checked actors history columns.
         */
        List<String> checkedactorshistoryColumns = new ArrayList<>();

        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_UUID_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_NAME_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_ALIAS_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_ACTOR_TYPE_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_PHOTO_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_CLIENT_IDENTITY_PUBLICKEY_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_EXTRA_DATA_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME);
        checkedactorshistoryColumns.add(CHECKED_ACTORS_HISTORY_CHECK_TYPE_COLUMN_NAME);
        /**
         * Table checked actors history addition.
         */
        DeveloperDatabaseTable checkedactorshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CHECKED_ACTORS_HISTORY_TABLE_NAME, checkedactorshistoryColumns);
        tables.add(checkedactorshistoryTable);

        /**
         * Table Clients Registration History table addition.
         */
        List<String> clientsRegistrationHistoryColumns = new ArrayList<>();

        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_ID_COLUMN_NAME                 );
        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_LAST_LATITUDE_COLUMN_NAME      );
        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_LAST_LONGITUDE_COLUMN_NAME     );
        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_DEVICE_TYPE_COLUMN_NAME        );
        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME  );
        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_TYPE_COLUMN_NAME               );
        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_RESULT_COLUMN_NAME             );
        clientsRegistrationHistoryColumns.add(CLIENTS_REGISTRATION_HISTORY_DETAIL_COLUMN_NAME             );

        DeveloperDatabaseTable clientsRegistrationHistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CLIENTS_REGISTRATION_HISTORY_TABLE_NAME, clientsRegistrationHistoryColumns);
        tables.add(clientsRegistrationHistoryTable);

        /**
         * Table checked network service history columns.
         */
        List<String> checkednetworkservicehistoryColumns = new ArrayList<>();

        checkednetworkservicehistoryColumns.add(CHECKED_NETWORK_SERVICE_HISTORY_UUID_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CHECKED_NETWORK_SERVICE_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CHECKED_NETWORK_SERVICE_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CHECKED_NETWORK_SERVICE_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CHECKED_NETWORK_SERVICE_HISTORY_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CHECKED_NETWORK_SERVICE_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CHECKED_NETWORK_SERVICE_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME);
        checkednetworkservicehistoryColumns.add(CHECKED_NETWORK_SERVICE_HISTORY_CHECK_TYPE_COLUMN_NAME);
        /**
         * Table checked network service history addition.
         */
        DeveloperDatabaseTable checkednetworkservicehistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CHECKED_NETWORK_SERVICE_HISTORY_TABLE_NAME, checkednetworkservicehistoryColumns);
        tables.add(checkednetworkservicehistoryTable);

        /**
         * Table clients connections history columns.
         */
        List<String> clientsconnectionshistoryColumns = new ArrayList<>();

        clientsconnectionshistoryColumns.add(CLIENTS_CONNECTIONS_HISTORY_UUID_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CLIENTS_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CLIENTS_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CLIENTS_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CLIENTS_CONNECTIONS_HISTORY_DEVICE_TYPE_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CLIENTS_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME);
        clientsconnectionshistoryColumns.add(CLIENTS_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME);
        /**
         * Table clients connections history addition.
         */
        DeveloperDatabaseTable clientsconnectionshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(CLIENTS_CONNECTIONS_HISTORY_TABLE_NAME, clientsconnectionshistoryColumns);
        tables.add(clientsconnectionshistoryTable);

        /**
         * Table nodes connections history columns.
         */
        List<String> nodesconnectionshistoryColumns = new ArrayList<>();

        nodesconnectionshistoryColumns.add(NODES_CONNECTIONS_HISTORY_UUID_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(NODES_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(NODES_CONNECTIONS_HISTORY_IP_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(NODES_CONNECTIONS_HISTORY_DEFAULT_PORT_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(NODES_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(NODES_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(NODES_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME);
        nodesconnectionshistoryColumns.add(NODES_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME);
        /**
         * Table nodes connections history addition.
         */
        DeveloperDatabaseTable nodesconnectionshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(NODES_CONNECTIONS_HISTORY_TABLE_NAME, nodesconnectionshistoryColumns);
        tables.add(nodesconnectionshistoryTable);

        /**
         * Table method calls history columns.
         */
        List<String> methodcallshistoryColumns = new ArrayList<>();

        methodcallshistoryColumns.add(METHOD_CALLS_HISTORY_UUID_COLUMN_NAME);
        methodcallshistoryColumns.add(METHOD_CALLS_HISTORY_METHOD_NAME_COLUMN_NAME);
        methodcallshistoryColumns.add(METHOD_CALLS_HISTORY_PARAMETERS_COLUMN_NAME);
        methodcallshistoryColumns.add(METHOD_CALLS_HISTORY_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        methodcallshistoryColumns.add(METHOD_CALLS_HISTORY_CREATE_TIMESTAMP_COLUMN_NAME);
        /**
         * Table method calls history addition.
         */
        DeveloperDatabaseTable methodcallshistoryTable = developerObjectFactory.getNewDeveloperDatabaseTable(METHOD_CALLS_HISTORY_TABLE_NAME, methodcallshistoryColumns);
        tables.add(methodcallshistoryTable);

        /**
         * Table nodes catalog columns.
         */
        List<String> nodescatalogColumns = new ArrayList<>();

        nodescatalogColumns.add(NODES_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_NAME_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_IP_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_DEFAULT_PORT_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_LAST_LATITUDE_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_LAST_LONGITUDE_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_OFFLINE_COUNTER_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME);
        nodescatalogColumns.add(NODES_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME);
        /**
         * Table nodes catalog addition.
         */
        DeveloperDatabaseTable nodescatalogTable = developerObjectFactory.getNewDeveloperDatabaseTable(NODES_CATALOG_TABLE_NAME, nodescatalogColumns);
        tables.add(nodescatalogTable);

        /**
         * Table nodes catalog transaction columns.
         */
        List<String> nodescatalogtransactionColumns = new ArrayList<>();

        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_NAME_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_IP_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_DEFAULT_PORT_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_REGISTERED_TIMESTAMP_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME);
        nodescatalogtransactionColumns.add(NODES_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME);
        /**
         * Table nodes catalog transaction addition.
         */
        DeveloperDatabaseTable nodescatalogtransactionTable = developerObjectFactory.getNewDeveloperDatabaseTable(NODES_CATALOG_TRANSACTION_TABLE_NAME, nodescatalogtransactionColumns);
        tables.add(nodescatalogtransactionTable);

        /**
         * Table nodes catalog transactions pending for propagation columns.
         */
        List<String> nodescatalogtransactionspendingforpropagationColumns = new ArrayList<>();

        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NAME_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IP_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_DEFAULT_PORT_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LATITUDE_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LONGITUDE_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_REGISTERED_TIMESTAMP_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME);
        nodescatalogtransactionspendingforpropagationColumns.add(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TRANSACTION_TYPE_COLUMN_NAME);
        /**
         * Table nodes catalog transactions pending for propagation addition.
         */
        DeveloperDatabaseTable nodescatalogtransactionspendingforpropagationTable = developerObjectFactory.getNewDeveloperDatabaseTable(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME, nodescatalogtransactionspendingforpropagationColumns);
        tables.add(nodescatalogtransactionspendingforpropagationTable);

        return tables;
    }

    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                      final DeveloperDatabase      developerDatabase     ,
                                                                      final DeveloperDatabaseTable developerDatabaseTable) {

        try {

            this.initializeDatabase();

            List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

            final DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

            selectedTable.loadToMemory();

            final List<DatabaseTableRecord> records = selectedTable.getRecords();

            List<String> developerRow;

            for (final DatabaseTableRecord row: records){

                developerRow = new ArrayList<>();

                for (final DatabaseRecord field : row.getValues())
                    developerRow.add(field.getValue());

                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }

            return returnedRecords;

        } catch (final CantLoadTableToMemoryException                 |
                       CantInitializeCommunicationsNetworkNodeP2PDatabaseException e) {

            System.err.println(e);

            return new ArrayList<>();

        } catch (final Exception e){

            return new ArrayList<>();
        }
    }

}