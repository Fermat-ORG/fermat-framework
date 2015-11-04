package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>WalletContactsMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletContactsMiddlewareDeveloperDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public WalletContactsMiddlewareDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                            final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    public void initializeDatabase() throws CantInitializeWalletContactsMiddlewareDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                pluginId,
                pluginId.toString()
            );

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeWalletContactsMiddlewareDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {

            WalletContactsMiddlewareDatabaseFactory walletContactsMiddlewareDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {

                database = walletContactsMiddlewareDatabaseFactory.createDatabase(
                        pluginId,
                        pluginId.toString()
                );
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                throw new CantInitializeWalletContactsMiddlewareDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new CantInitializeWalletContactsMiddlewareDatabaseException(e.getMessage());
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabase> databases = new ArrayList<>();

        databases.add(
            developerObjectFactory.getNewDeveloperDatabase(
                "Wallet Contacts",
                this.pluginId.toString()
            )
        );

        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Wallet Contacts columns.
         */
        List<String> walletContactsColumns = new ArrayList<>();

        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME       );
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_PUBLIC_KEY_COLUMN_NAME );
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_TYPE_COLUMN_NAME       );
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME      );
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME );
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME  );
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        walletContactsColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_COMPATIBILITY_COLUMN_NAME    );
        /**
         * Table Wallet Contacts addition.
         */
        DeveloperDatabaseTable walletContactsTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME, walletContactsColumns);
        tables.add(walletContactsTable);

        /**
         * Table Wallet Contact Addresses columns.
         */
        List<String> walletContactAddressesColumns = new ArrayList<>();

        walletContactAddressesColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME     );
        walletContactAddressesColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME );
        walletContactAddressesColumns.add(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME);

        /**
         * Table Wallet Contact Addresses addition.
         */
        DeveloperDatabaseTable walletContactAddressesTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TABLE_NAME, walletContactAddressesColumns);
        tables.add(walletContactAddressesTable);



        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory,
                                                                      DeveloperDatabaseTable developerDatabaseTable) {

        try {

            DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

            selectedTable.loadToMemory();

            List<DatabaseTableRecord> records = selectedTable.getRecords();

            List<String> developerRow = new ArrayList<>();

            List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

            for (DatabaseTableRecord row : records) {

                for (DatabaseRecord field : row.getValues())
                    developerRow.add(field.getValue());

                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            return returnedRecords;

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            return new ArrayList<>();
        }
    }
}