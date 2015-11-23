package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerActorNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                                   final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCryptoBrokerActorNetworkServiceDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCryptoBrokerActorNetworkServiceDatabaseException {

        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CryptoBrokerActorNetworkServiceDatabaseConstants.CRYPTO_BROKER_ACTOR_NETWORK_SERVICE_DATABASE_NAME);

        } catch (final CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCryptoBrokerActorNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (final DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            final CryptoBrokerActorNetworkServiceDatabaseFactory cryptoBrokerActorNetworkServiceDatabaseFactory = new CryptoBrokerActorNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = cryptoBrokerActorNetworkServiceDatabaseFactory.createDatabase(pluginId, CryptoBrokerActorNetworkServiceDatabaseConstants.CRYPTO_BROKER_ACTOR_NETWORK_SERVICE_DATABASE_NAME);
            } catch (final CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCryptoBrokerActorNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(final DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Crypto Broker", CryptoBrokerActorNetworkServiceDatabaseConstants.CRYPTO_BROKER_ACTOR_NETWORK_SERVICE_DATABASE_NAME));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Connection News columns.
         */
        List<String> connectionNewsColumns = new ArrayList<String>();

        connectionNewsColumns.add(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME            );
        connectionNewsColumns.add(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME     );
        connectionNewsColumns.add(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME     );
        connectionNewsColumns.add(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME          );
        connectionNewsColumns.add(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
        connectionNewsColumns.add(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME          );
        connectionNewsColumns.add(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME        );
        connectionNewsColumns.add(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME             );
        /**
         * Table Connection News addition.
         */
        DeveloperDatabaseTable connectionNewsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME, connectionNewsColumns);
        tables.add(connectionNewsTable);



        return tables;
    }

    public final List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                            final DeveloperDatabaseTable developerDatabaseTable) {

        try {

            initializeDatabase();

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
