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
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.diagrams.wallets.ExternalBitcoinWallet;
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
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
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

public class WalletAddressBookCryptoModuleDao implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

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
    public WalletAddressBookCryptoModuleDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        /**
         * The only one who can set the pluginId is the Plugin Root.
         */
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
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
            database.closeDatabase();
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            createDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch(Exception exception){
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, exception);
        }
    }



    public void registerWalletAddressBookModule(CryptoAddress cryptoAddress, ReferenceWallet referenceWallet, String walletPublicKey) throws CantRegisterWalletAddressBookException {
        try {
            database.openDatabase();
            DatabaseTable walletAddressBookTable = getCryptoWalletAddressBookTable();
            DatabaseTableRecord walletAddressBookModuleRecord = walletAddressBookTable.getEmptyRecord();

            UUID recordId = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();

            walletAddressBookModuleRecord.setUUIDValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID, recordId);
            walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY, walletPublicKey);
            walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE, referenceWallet.getCode());
            walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());
            walletAddressBookModuleRecord.setStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode());
            walletAddressBookModuleRecord.setLongValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_TIME_STAMP, unixTime);

            walletAddressBookTable.insertRecord(walletAddressBookModuleRecord);
            database.closeDatabase();

        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (CantInsertRecordException cantInsertRecord) {
            database.closeDatabase();
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, cantInsertRecord, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch(Exception exception){
            if(database!=null)
                database.closeDatabase();
            throw new CantRegisterWalletAddressBookException(CantRegisterWalletAddressBookException.DEFAULT_MESSAGE, exception);
        }
    }

    public WalletAddressBookRecord getWalletAddressBookModuleByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {
        try {
            List<DatabaseTableRecord> records = getCryptoWalletAddressBookRecords(cryptoAddress);

            if(records.isEmpty())
                throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, null, "", "The crypto_address is not registered.");

            DatabaseTableRecord record = records.get(0);
            String walletPublicKey = record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY);
            ReferenceWallet referenceWallet = ReferenceWallet.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE));

            return new WalletAddressBookCryptoModuleRecord(cryptoAddress, referenceWallet, walletPublicKey);
        } catch(CantGetWalletAddressBookException | WalletAddressBookNotFoundException exception){
            throw exception;
        } catch (InvalidParameterException e) {
            throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, e,"", "Unknown Reference Wallet. Someone probably added a wallet type and didn't completed the getByCode method");
        } catch(Exception exception){
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
    }


    public List<WalletAddressBookRecord> getAllWalletAddressBookModuleByWalletPublicKey(String walletPublicKey) throws CantGetWalletAddressBookException, WalletAddressBookNotFoundException {
        try{
            List<DatabaseTableRecord> records = getCryptoWalletAddressBookRecords(walletPublicKey);
            if (records.isEmpty())
                throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, null, "", "There is not a wallet address book registered with that wallet_id.");

            List<WalletAddressBookRecord> walletAddressBookRecords = new ArrayList<>();
            for (DatabaseTableRecord record : records)
                walletAddressBookRecords.add(constructWalletAddressBookRecord(record));
            return walletAddressBookRecords;
        } catch(CantGetWalletAddressBookException | WalletAddressBookNotFoundException exception){
            throw exception;
        } catch (InvalidParameterException e) {
            throw new WalletAddressBookNotFoundException(WalletAddressBookNotFoundException.DEFAULT_MESSAGE, e,"", "Unknown Reference Wallet. Someone probably added a wallet type and didn't completed the getByCode method");
        }catch(Exception exception){
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }

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


    private void createDatabase() throws CantInitializeWalletAddressBookCryptoModuleException{
        try {
            WalletAddressBookCryptoModuleDatabaseFactory databaseFactory = new WalletAddressBookCryptoModuleDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(pluginId);
            database.closeDatabase();
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantInitializeWalletAddressBookCryptoModuleException(CantInitializeWalletAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
        }
    }

    private DatabaseTable getCryptoWalletAddressBookTable(){
        return database.getTable(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
    }

    private List<DatabaseTableRecord> getCryptoWalletAddressBookRecords(final CryptoAddress cryptoAddress) throws CantGetWalletAddressBookException {
        try{
            database.openDatabase();
            DatabaseTable walletAddressBookTable = getCryptoWalletAddressBookTable();
            walletAddressBookTable.setStringFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
            walletAddressBookTable.setStringFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode(), DatabaseFilterType.EQUAL);
            walletAddressBookTable.loadToMemory();
            List<DatabaseTableRecord> records = walletAddressBookTable.getRecords();
            database.closeDatabase();
            return records;
        } catch(FermatException exception){
            database.closeDatabase();
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }
    }

    private List<DatabaseTableRecord> getCryptoWalletAddressBookRecords(final String walletPublicKey) throws CantGetWalletAddressBookException {
        try{
            database.openDatabase();
            DatabaseTable walletAddressBookTable = getCryptoWalletAddressBookTable();
            walletAddressBookTable.setStringFilter(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY, walletPublicKey, DatabaseFilterType.EQUAL);
            walletAddressBookTable.loadToMemory();
            List<DatabaseTableRecord> records = walletAddressBookTable.getRecords();
            database.closeDatabase();
            return records;
        } catch(FermatException exception){
            database.closeDatabase();
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }
    }

    private WalletAddressBookRecord constructWalletAddressBookRecord(final DatabaseTableRecord record) throws InvalidParameterException {
        String walletPublicKey = record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY);
        ReferenceWallet referenceWallet = ReferenceWallet.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE));
        String address = record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS);
        CryptoCurrency cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY));
        CryptoAddress cryptoAddress = new CryptoAddress(address, cryptoCurrency);
        return new WalletAddressBookCryptoModuleRecord(cryptoAddress, referenceWallet, walletPublicKey);
    }

}