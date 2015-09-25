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
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/15.
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

        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME                    );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME             );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_REQUESTING_COLUMN_NAME      );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_ACCEPTING_COLUMN_NAME       );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_REQUESTING_COLUMN_NAME);
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_ACCEPTING_COLUMN_NAME );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_FROM_REQUEST_COLUMN_NAME   );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_FROM_RESPONSE_COLUMN_NAME  );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME               );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME       );
        cryptoAddressRequestColumns.add(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME                         );

        /**
         * Table Crypto Address Request addition.
         */
        DeveloperDatabaseTable cryptoAddressRequestTable = developerObjectFactory.getNewDeveloperDatabaseTable(
            CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TABLE_NAME,
            cryptoAddressRequestColumns
        );

        tables.add(cryptoAddressRequestTable);

        return tables;
    }

    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {

        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

        try {

            selectedTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();

        List<String> developerRow = new ArrayList<>();

        for (DatabaseTableRecord row : records) {

            for (DatabaseRecord field : row.getValues())
                developerRow.add(field.getValue());

            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }
        return returnedRecords;
    }
}
