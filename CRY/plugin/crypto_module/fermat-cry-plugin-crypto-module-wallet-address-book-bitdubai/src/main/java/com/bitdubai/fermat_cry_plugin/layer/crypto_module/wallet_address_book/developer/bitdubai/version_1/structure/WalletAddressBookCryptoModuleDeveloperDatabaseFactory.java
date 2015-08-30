package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletAddressBookCryptoModuleException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDeveloperDatabaseFactory</code> have
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletAddressBookCryptoModuleDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    private Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public WalletAddressBookCryptoModuleDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeWalletAddressBookCryptoModuleException
     */
    public void initializeDatabase() throws CantInitializeWalletAddressBookCryptoModuleException {
        try {
            database = pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            database.closeDatabase();
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            createDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException){
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantOpenDatabaseException);
        } catch(Exception exception){
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Unchecked Exception, check the cause");
        }
    }

    private void createDatabase() throws CantInitializeWalletAddressBookCryptoModuleException{
        try {
            WalletAddressBookCryptoModuleDatabaseFactory databaseFactory = new WalletAddressBookCryptoModuleDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
            database =  databaseFactory.createDatabase(this.pluginId);
        } catch (CantCreateDatabaseException cantCreateDatabaseException){
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantCreateDatabaseException);
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        try{
            List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
            databases.add(developerObjectFactory.getNewDeveloperDatabase("Wallet Address Book", this.pluginId.toString()));
            return databases;
        } catch (Exception exception){
            /*
             * If any error occurs we return an empty list
             */
            return new ArrayList<>();
        }
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * walletAddressBookTable columns
         */
        List<String> walletAddressBookTableColumns = new ArrayList<String>();
        walletAddressBookTableColumns.add(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID);
        walletAddressBookTableColumns.add(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY);
        walletAddressBookTableColumns.add(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE);
        walletAddressBookTableColumns.add(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS);
        walletAddressBookTableColumns.add(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY);
        walletAddressBookTableColumns.add(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_TIME_STAMP);

        /**
         * walletAddressBook table
         */
        DeveloperDatabaseTable walletAddressBookTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME, walletAddressBookTableColumns);
        tables.add(walletAddressBookTable);

        return tables;
    }

    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
            selectedTable.loadToMemory();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            database.closeDatabase();

            List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
            for (DatabaseTableRecord row : records) {
                List<String> developerRow = new ArrayList<String>();
                for (DatabaseRecord field : row.getValues()) {
                    developerRow.add(field.getValue());
                }
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            return returnedRecords;
        } catch (Exception exception) {
            /*
             * If any error occurs we return an empty list
             */
            return new ArrayList<>();
        }
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
