package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

/**
 * The interface <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletCryptoAddressBookDao</code>
 * haves all the methods that interact with the database.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.WalletCryptoAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletCryptoAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletCryptoAddressBookException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletCryptoAddressBookException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */

public class WalletCryptoAddressBookDao implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public WalletCryptoAddressBookDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId){
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

    public void initialize() throws CantInitializeWalletCryptoAddressBookException{

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeWalletCryptoAddressBookException();
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            WalletAddressBookDataBaseFactory databaseFactory = new WalletAddressBookDataBaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {

                this.database =  databaseFactory.createDatabase(this.pluginId);

            }
            catch (CantCreateDatabaseException cantCreateDatabaseException){

                /**
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeWalletCryptoAddressBookException();
            }
        }
    }

    public void registerWalletCryptoAddressBook(CryptoAddress cryptoAddress, PlatformWalletType platformWalletType, UUID walletId) throws CantRegisterWalletCryptoAddressBookException {

        /**
         * Here I create the Address book record for new Actor.
         */
        long unixTime = System.currentTimeMillis() / 1000L;

        DatabaseTable walletCryptoAddressBookTable = database.getTable(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        DatabaseTableRecord walletCryptoAddressBookRecord = walletCryptoAddressBookTable.getEmptyRecord();

        UUID recordId = UUID.randomUUID();

        walletCryptoAddressBookRecord.setUUIDValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID , recordId);
        walletCryptoAddressBookRecord.setUUIDValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID, walletId);
        walletCryptoAddressBookRecord.setStringValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE, platformWalletType.getCode());
        walletCryptoAddressBookRecord.setStringValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());
        walletCryptoAddressBookRecord.setStringValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode());
        walletCryptoAddressBookRecord.setLongValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_TIME_STAMP, unixTime);

        try{
            walletCryptoAddressBookTable.insertRecord(walletCryptoAddressBookRecord);
        } catch(CantInsertRecord cantInsertRecord) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInsertRecord);
            throw new CantRegisterWalletCryptoAddressBookException();
        }
    }

    public WalletCryptoAddressBook getWalletCryptoAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetWalletCryptoAddressBookException {

        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        table.setStringFilter(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS,cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
        table.setStringFilter(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY,cryptoAddress.getCryptoCurrency().getCode(), DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetWalletCryptoAddressBookException();
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
            walletId = record.getUUIDValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID);
            platformWalletType = PlatformWalletType.getByCode(record.getStringValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE));
        } else {
            return null;
        }

        return new WalletCryptoAddressBookRegistry(cryptoAddress, platformWalletType, walletId);
    }

    public List<WalletCryptoAddressBook> getAllWalletCryptoAddressBookByActorId(UUID walletId) throws CantGetWalletCryptoAddressBookException {

        DatabaseTable table;

        List<WalletCryptoAddressBook> walletCryptoAddressBooks = new ArrayList<>();

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        table.setUUIDFilter(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_ID, walletId, DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetWalletCryptoAddressBookException();
        }


        /**
         * Will go through the records getting each Actor address.
         */

        PlatformWalletType platformWalletType;
        String address;
        CryptoCurrency cryptoCurrency;
        CryptoAddress cryptoAddress;

        for (DatabaseTableRecord record : table.getRecords()) {
            platformWalletType = PlatformWalletType.getByCode(record.getStringValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE));
            address = record.getStringValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS);
            cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY));
            cryptoAddress = new CryptoAddress(address, cryptoCurrency);
            WalletCryptoAddressBook addressBook = new WalletCryptoAddressBookRegistry(cryptoAddress, platformWalletType, walletId);
            walletCryptoAddressBooks.add(addressBook);

        }

        return walletCryptoAddressBooks;
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