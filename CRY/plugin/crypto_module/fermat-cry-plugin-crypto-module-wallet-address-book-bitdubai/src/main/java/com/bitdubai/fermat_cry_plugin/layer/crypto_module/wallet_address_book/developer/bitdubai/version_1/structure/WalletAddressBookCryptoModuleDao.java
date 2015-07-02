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
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
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
        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            WalletAddressBookCryptoModuleDatabaseFactory databaseFactory = new WalletAddressBookCryptoModuleDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {
                this.database = databaseFactory.createDatabase(this.pluginId);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
    }

    public void registerWalletAddressBookModule(CryptoAddress cryptoAddress, PlatformWalletType platformWalletType, UUID walletId) throws CantRegisterWalletAddressBookException {

        /**
         * Here I create the Address book record for the Wallet.
         */
        long unixTime = System.currentTimeMillis() / 1000L;

        DatabaseTable walletAddressBookModuleTable = database.getTable(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        DatabaseTableRecord walletAddressBookModuleRecord = walletAddressBookModuleTable.getEmptyRecord();

        UUID recordId = UUID.randomUUID();

        walletAddressBookModuleRecord.setUUIDValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID, recordId);
        walletAddressBookModuleRecord.setUUIDValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID, walletId);
        walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE, platformWalletType.getCode());
        walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());
        walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode());
        walletAddressBookModuleRecord.setLongValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_TIME_STAMP, unixTime);

        try {
            walletAddressBookModuleTable.insertRecord(walletAddressBookModuleRecord);
        } catch (CantInsertRecordException cantInsertRecord) {
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, cantInsertRecord, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    public WalletAddressBookRecord getWalletAddressBookModuleByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {

        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        table.setStringFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
        table.setStringFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode(), DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, cantLoadTableToMemory, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }

        /**
         * Will go through the records getting each Actor address.
         */

        List<DatabaseTableRecord> records = table.getRecords();
        DatabaseTableRecord record;

        UUID walletId;
        PlatformWalletType platformWalletType;

        if (records.size() > 0) {
            record = records.get(0);
            walletId = record.getUUIDValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID);
            platformWalletType = PlatformWalletType.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE));
        } else {
            throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, null, "", "The crypto_address is not registered.");
        }

        return new WalletAddressBookCryptoModuleRecord(cryptoAddress, platformWalletType, walletId);
    }

    public List<WalletAddressBookRecord> getAllWalletAddressBookModuleByWalletId(UUID walletId) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {

        List<WalletAddressBookRecord> walletAddressBookRecords = new ArrayList<>();

        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        table.setUUIDFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID, walletId, DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, cantLoadTableToMemory, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }

        /**
         * Will go through the records getting each Wallet address.
         */

        PlatformWalletType platformWalletType;
        String address;
        CryptoCurrency cryptoCurrency;
        CryptoAddress cryptoAddress;

        List<DatabaseTableRecord> databaseTableRecordList = table.getRecords();

        if (databaseTableRecordList.size() < 0) {
            for (DatabaseTableRecord record : databaseTableRecordList) {
                platformWalletType = PlatformWalletType.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE));
                address = record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS);
                cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY));
                cryptoAddress = new CryptoAddress(address, cryptoCurrency);
                WalletAddressBookRecord addressBook = new WalletAddressBookCryptoModuleRecord(cryptoAddress, platformWalletType, walletId);
                walletAddressBookRecords.add(addressBook);
            }
        } else {
            throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, null, "", "There is not a wallet address book registered with that wallet_id.");
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