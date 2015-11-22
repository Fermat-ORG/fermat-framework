package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressesNetworkServiceDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDeveloperDatabaseFactory</code>
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoAddressesNetworkServiceDeveloperDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public CryptoAddressesNetworkServiceDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                                 final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public void initializeDatabase() throws CantInitializeCryptoAddressesNetworkServiceDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeCryptoAddressesNetworkServiceDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {

            CryptoAddressesNetworkServiceDatabaseFactory cryptoAddressesNetworkServiceDatabaseFactory = new CryptoAddressesNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {
                database = cryptoAddressesNetworkServiceDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeCryptoAddressesNetworkServiceDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabase> databases = new ArrayList<>();

        databases.add(developerObjectFactory.getNewDeveloperDatabase("Crypto Addresses", this.pluginId.toString()));

        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Crypto Address Request columns.
         */
        List<String> cryptoAddressRequestColumns = new ArrayList<>();

        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ID_COLUMN_NAME                            );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME             );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_TYPE_REQUESTING_COLUMN_NAME      );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_TYPE_RESPONDING_COLUMN_NAME      );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_PUBLIC_KEY_REQUESTING_COLUMN_NAME);
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_IDENTITY_PUBLIC_KEY_RESPONDING_COLUMN_NAME);
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME               );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME                );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_STATE_COLUMN_NAME                         );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TYPE_COLUMN_NAME                          );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_ACTION_COLUMN_NAME                        );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_DEALER_COLUMN_NAME                        );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME       );

        /**
         * Table Crypto Address Request addition.
         */
        DeveloperDatabaseTable cryptoAddressRequestTable = developerObjectFactory.getNewDeveloperDatabaseTable(
            CryptoAddressesNetworkServiceDatabaseConstants.ADDRESS_EXCHANGE_REQUEST_TABLE_NAME,
            cryptoAddressRequestColumns
        );

        tables.add(cryptoAddressRequestTable);

        return tables;
    }

    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
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

            List<DatabaseTableRecord> records = selectedTable.getRecords();

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
