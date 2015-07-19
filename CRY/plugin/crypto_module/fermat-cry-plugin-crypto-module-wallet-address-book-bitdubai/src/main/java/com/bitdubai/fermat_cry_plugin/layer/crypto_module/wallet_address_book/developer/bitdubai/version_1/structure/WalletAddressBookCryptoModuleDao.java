package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

/**
 * The interface <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDao</code>
 * haves all the methods that interact with the database.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 *
 * @version 1.0
 */

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.WalletAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRecord;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletAddressBookCryptoModuleException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */

public class WalletAddressBookCryptoModuleDao implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;


    /**
     * Constructor.
     */
    public WalletAddressBookCryptoModuleDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        /**
         * The only one who can set the pluginId is the Plugin Root.
         */
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    /**
     * CryptoAddressBook Interface implementation.
     */

    public void initialize() throws CantInitializeWalletAddressBookCryptoModuleException {

        if (errorManager == null)
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, null, "Error Manager: null", "You have to set the ErrorManager before initializing");
        if (pluginDatabaseSystem == null)
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, null, "Plugin Database System: null", "You have to set the PluginDatabaseSystem before initializing");
        if (pluginId == null)
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, null, "Plugin Id: null", "You have to set the PluginId before initializing");

        /**
         * I will try to open the wallets' database..
         */
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            WalletAddressBookCryptoModuleDatabaseFactory databaseFactory = new WalletAddressBookCryptoModuleDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {
                database = databaseFactory.createDatabase(this.pluginId);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
    }

    public void registerWalletAddressBookModule(CryptoAddress cryptoAddress, ReferenceWallet referenceWallet, UUID walletId) throws CantRegisterWalletAddressBookException {

        if (cryptoAddress == null)
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, null, "cryptoAddress: null", "cryptoAddress cannot be null");
        if (referenceWallet == null)
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, null, "referenceWallet: null", "referenceWallet cannot be null");
        if (walletId == null)
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, null, "walletId: null", "walletId cannot be null");
        /**
         * Here I create the Address book record for the Wallet.
         */
        long unixTime = System.currentTimeMillis() / 1000L;

        try {
            database.openDatabase();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }

        DatabaseTable walletAddressBookModuleTable = database.getTable(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        DatabaseTableRecord walletAddressBookModuleRecord = walletAddressBookModuleTable.getEmptyRecord();

        UUID recordId = UUID.randomUUID();

        walletAddressBookModuleRecord.setUUIDValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID, recordId);
        walletAddressBookModuleRecord.setUUIDValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID, walletId);
        walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE, referenceWallet.getCode());
        walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());
        walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode());
        walletAddressBookModuleRecord.setLongValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_TIME_STAMP, unixTime);

        try {
            walletAddressBookModuleTable.insertRecord(walletAddressBookModuleRecord);
            database.closeDatabase();
        } catch (CantInsertRecordException cantInsertRecord) {
            database.closeDatabase();
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, cantInsertRecord, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    public WalletAddressBookRecord getWalletAddressBookModuleByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {

        if (cryptoAddress == null)
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, null, "cryptoAddress: null", "cryptoAddress cannot be null");

        try {
            database.openDatabase();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        DatabaseTable table = database.getTable(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        table.setStringFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
        table.setStringFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode(), DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            database.closeDatabase();
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, cantLoadTableToMemory, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }

        /**
         * Will go through the records getting each Actor address.
         */

        List<DatabaseTableRecord> records = table.getRecords();
        database.closeDatabase();

        if(records.isEmpty())
            throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, null, "", "The crypto_address is not registered.");

        try {
            DatabaseTableRecord record = records.get(0);
            UUID walletId = record.getUUIDValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID);
            ReferenceWallet referenceWallet = ReferenceWallet.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE));
            return new WalletAddressBookCryptoModuleRecord(cryptoAddress, referenceWallet, walletId);
        } catch (InvalidParameterException e) {
            throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, e,"", "Unknown Reference Wallet. Someone probably added a wallet type and didn't completed the getByCode method");
        }
    }

    public List<WalletAddressBookRecord> getAllWalletAddressBookModuleByWalletId(UUID walletId) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {

        if (walletId == null)
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, null, "walletId: null", "walletId cannot be null");

        try {
            database.openDatabase();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        DatabaseTable table = this.database.getTable(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        table.setUUIDFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID, walletId, DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            database.closeDatabase();
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, cantLoadTableToMemory, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }

        /**
         * Will go through the records getting each Wallet address.
         */

        ReferenceWallet referenceWallet;
        String address;
        CryptoCurrency cryptoCurrency;
        CryptoAddress cryptoAddress;

        List<DatabaseTableRecord> records = table.getRecords();

        if (records.isEmpty())
            throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, null, "", "There is not a wallet address book registered with that wallet_id.");

        List<WalletAddressBookRecord> walletAddressBookRecords = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            try{
            referenceWallet = ReferenceWallet.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE));
            } catch (InvalidParameterException e) {
                throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, e,"", "Unknown Reference Wallet. Someone probably added a wallet type and didn't completed the getByCode method");
            }
            address = record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS);
            cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY));
            cryptoAddress = new CryptoAddress(address, cryptoCurrency);
            WalletAddressBookRecord addressBook = new WalletAddressBookCryptoModuleRecord(cryptoAddress, referenceWallet, walletId);
            walletAddressBookRecords.add(addressBook);
        }

        return walletAddressBookRecords;
    }


    /**
     * DealsWithErrors interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}