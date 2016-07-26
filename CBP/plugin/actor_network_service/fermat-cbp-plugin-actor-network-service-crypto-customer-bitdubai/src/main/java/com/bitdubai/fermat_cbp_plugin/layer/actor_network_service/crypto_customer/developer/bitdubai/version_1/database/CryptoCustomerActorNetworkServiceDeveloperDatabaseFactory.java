package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.factories.NetworkServiceDatabaseFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public final class CryptoCustomerActorNetworkServiceDeveloperDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public CryptoCustomerActorNetworkServiceDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                                     final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeDatabaseException
     */
    public void initializeDatabase(final String tableId) throws CantInitializeDatabaseException {

        switch (tableId) {

            case NetworkServiceDatabaseConstants.DATABASE_NAME:
                try {

                    this.database = this.pluginDatabaseSystem.openDatabase(pluginId, NetworkServiceDatabaseConstants.DATABASE_NAME);

                } catch (CantOpenDatabaseException e) {

                    throw new CantInitializeDatabaseException(e, new StringBuilder().append("tableId: ").append(tableId).toString(), "Error trying to open the database.");

                } catch (DatabaseNotFoundException e) {

                    NetworkServiceDatabaseFactory networkServiceDatabaseFactory = new NetworkServiceDatabaseFactory(pluginDatabaseSystem);

                    try {

                        this.database = networkServiceDatabaseFactory.createDatabase(pluginId, NetworkServiceDatabaseConstants.DATABASE_NAME);

                    } catch (CantCreateDatabaseException z) {

                        throw new CantInitializeDatabaseException(z, new StringBuilder().append("tableId: ").append(tableId).toString(), "Error trying to create the database.");
                    }
                }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(final DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabase> databases = new ArrayList<>();

       /* databases.add(developerObjectFactory.getNewDeveloperDatabase(
                "Actor Network Service",
                CryptoBrokerActorNetworkServiceDatabaseConstants.CRYPTO_BROKER_ACTOR_NETWORK_SERVICE_DATABASE_NAME
        ));*/

        databases.add(developerObjectFactory.getNewDeveloperDatabase(
                "Network Service Template",
                NetworkServiceDatabaseConstants.DATABASE_NAME
        ));

        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(final DeveloperObjectFactory developerObjectFactory,
                                                             final DeveloperDatabase developerDatabase) {

        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        switch (developerDatabase.getId()) {

            case NetworkServiceDatabaseConstants.DATABASE_NAME:

                /**
                 * Table incoming messages columns.
                 */
                List<String> incomingMessagesColumns = new ArrayList<>();

                incomingMessagesColumns.add(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME);
                incomingMessagesColumns.add(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME);
                incomingMessagesColumns.add(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
                incomingMessagesColumns.add(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
                incomingMessagesColumns.add(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
                incomingMessagesColumns.add(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_CONTENT_TYPE_COLUMN_NAME);
                incomingMessagesColumns.add(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME);
                incomingMessagesColumns.add(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_CONTENT_COLUMN_NAME);
                /**
                 * Table incoming messages addition.
                 */
                DeveloperDatabaseTable incomingMessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME, incomingMessagesColumns);
                tables.add(incomingMessagesTable);

                /**
                 * Table outgoing messages columns.
                 */
                List<String> outgoingMessagesColumns = new ArrayList<>();

                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME);
                outgoingMessagesColumns.add(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_CONTENT_COLUMN_NAME);
                /**
                 * Table outgoing messages addition.
                 */
                DeveloperDatabaseTable outgoingMessagesTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME, outgoingMessagesColumns);
                tables.add(outgoingMessagesTable);

                /**
                 * Table outgoing messages columns.
                 */
                List<String> queriesColumns = new ArrayList<>();

                queriesColumns.add(NetworkServiceDatabaseConstants.QUERIES_ID_COLUMN_NAME);
                queriesColumns.add(NetworkServiceDatabaseConstants.QUERIES_BROADCAST_CODE_COLUMN_NAME);
                queriesColumns.add(NetworkServiceDatabaseConstants.QUERIES_DISCOVERY_QUERY_PARAMS_COLUMN_NAME);
                queriesColumns.add(NetworkServiceDatabaseConstants.QUERIES_EXECUTION_TIME_COLUMN_NAME);
                queriesColumns.add(NetworkServiceDatabaseConstants.QUERIES_TYPE_COLUMN_NAME);
                queriesColumns.add(NetworkServiceDatabaseConstants.QUERIES_STATUS_COLUMN_NAME);

                /**
                 * Table queries messages addition.
                 */
                DeveloperDatabaseTable queriesTable = developerObjectFactory.getNewDeveloperDatabaseTable(NetworkServiceDatabaseConstants.QUERIES_TABLE_NAME, queriesColumns);
                tables.add(queriesTable);
                break;
        }

        return tables;
    }

    public final List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                            final DeveloperDatabase developerDatabase,
                                                                            final DeveloperDatabaseTable developerDatabaseTable) {

        try {

            initializeDatabase(developerDatabase.getId());

            final List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

            final DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

            try {

                selectedTable.loadToMemory();
            } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

                return returnedRecords;
            }

            final List<DatabaseTableRecord> records = selectedTable.getRecords();

            List<String> developerRow;

            for (DatabaseTableRecord row : records) {

                developerRow = new ArrayList<>();

                for (DatabaseRecord field : row.getValues())
                    developerRow.add(field.getValue());

                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            return returnedRecords;

        } catch (Exception e) {

            System.err.println(e);
            return new ArrayList<>();
        }
    }

}
