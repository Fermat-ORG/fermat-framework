package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

/**
 * Created by natalia on 16/06/15.
 * asdasda
 */

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantRegisterActorAddressBookException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */

public class ActorAddressBookCryptoModuleDao implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public ActorAddressBookCryptoModuleDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
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

    public void initialize() throws CantInitializeActorAddressBookCryptoModuleException {

        if (errorManager == null)
            throw new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, null, "Error Manager: null", "You have to set the ErrorManager before initializing");
        if (pluginDatabaseSystem == null)
            throw new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, null, "Plugin Database System: null", "You have to set the PluginDatabaseSystem before initializing");
        if (pluginId == null)
            throw new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, null, "Plugin Id: null", "You have to set the PluginId before initializing");

        /**
         * I will try to open the actor address book's database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            ActorAddressBookCryptoModuleDatabaseFactory databaseFactory = new ActorAddressBookCryptoModuleDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {
                this.database = databaseFactory.createDatabase(this.pluginId, this.pluginId);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeActorAddressBookCryptoModuleException(CantInitializeActorAddressBookCryptoModuleException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
    }

    public void registerActorAddressBook(UUID actorId, Actors actorType, CryptoAddress cryptoAddress) throws CantRegisterActorAddressBookException {

        if (actorId == null)
            throw new CantRegisterActorAddressBookException(CantRegisterActorAddressBookException.DEFAULT_MESSAGE, null, "ActorId: null", "You have to set the ActorId before initializing");
        if (actorType == null)
            throw new CantRegisterActorAddressBookException(CantRegisterActorAddressBookException.DEFAULT_MESSAGE, null, "ActorType: null", "You have to set the ActorType before initializing");
        if (cryptoAddress == null)
            throw new CantRegisterActorAddressBookException(CantRegisterActorAddressBookException.DEFAULT_MESSAGE, null, "CryptoAddress: null", "You have to set the CryptoAddress before initializing");

        /**
         * Here I create the Address book record for new Actor.
         */
        long unixTime = System.currentTimeMillis() / 1000L;

        DatabaseTable addressBookTable = database.getTable(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        DatabaseTableRecord addressBookRecord = addressBookTable.getEmptyRecord();

        UUID creditRecordId = UUID.randomUUID();

        addressBookRecord.setUUIDValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID, creditRecordId);
        addressBookRecord.setUUIDValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_ID, actorId);
        addressBookRecord.setStringValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_TYPE, actorType.getCode());
        addressBookRecord.setStringValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());
        addressBookRecord.setStringValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode());
        addressBookRecord.setLongValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_TIME_STAMP, unixTime);

        try {
            addressBookTable.insertRecord(addressBookRecord);
        } catch (CantInsertRecordException cantInsertRecord) {
            throw new CantRegisterActorAddressBookException(CantRegisterActorAddressBookException.DEFAULT_MESSAGE, cantInsertRecord, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }

    public ActorAddressBookRecord getActorAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetActorAddressBookException, ActorAddressBookNotFoundException {

        if (cryptoAddress == null)
            throw new CantGetActorAddressBookException(CantGetActorAddressBookException.DEFAULT_MESSAGE, null, "cryptoAddress: null", "The parameter cryptoAddress cannot be null");


        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        table.setStringFilter(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
        table.setStringFilter(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode(), DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetActorAddressBookException(CantGetActorAddressBookException.DEFAULT_MESSAGE, cantLoadTableToMemory, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }


        /**
         * Will go through the records getting each Actor address.
         */

        List<DatabaseTableRecord> records = table.getRecords();
        DatabaseTableRecord record;

        UUID actorId;
        Actors actorType;

        if (records.size() > 0) {
            record = records.get(0);
            actorId = record.getUUIDValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_ID);
            actorType = Actors.getByCode(record.getStringValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_TYPE));
            return new ActorAddressBookCryptoModuleRecord(actorId, actorType, cryptoAddress);
        } else {
            throw new ActorAddressBookNotFoundException(ActorAddressBookNotFoundException.DEFAULT_MESSAGE, null, "", "The crypto_address is not registered.");
        }
    }

    public List<ActorAddressBookRecord> getAllActorAddressBookByActorId(UUID actorId) throws CantGetActorAddressBookException, ActorAddressBookNotFoundException {

        if (actorId == null)
            throw new CantGetActorAddressBookException(CantGetActorAddressBookException.DEFAULT_MESSAGE, null, "actorId: null", "The parameter actorId cannot be null");

        DatabaseTable table;

        List<ActorAddressBookRecord> actorsAddressBooks = new ArrayList<>();

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        table.setUUIDFilter(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_ID, actorId, DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetActorAddressBookException(CantGetActorAddressBookException.DEFAULT_MESSAGE, cantLoadTableToMemory, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }

        /**
         * Will go through the records getting each Actor address.
         */

        Actors actorType;
        String address;
        CryptoCurrency cryptoCurrency;
        CryptoAddress cryptoAddress;

        List<DatabaseTableRecord> databaseTableRecordList = table.getRecords();

        if (databaseTableRecordList.size() > 0) {
            for (DatabaseTableRecord record : table.getRecords()) {
                actorType = Actors.getByCode(record.getStringValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_TYPE));
                address = record.getStringValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS);
                cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(ActorAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY));
                cryptoAddress = new CryptoAddress(address, cryptoCurrency);
                ActorAddressBookRecord addressBook = new ActorAddressBookCryptoModuleRecord(actorId, actorType, cryptoAddress);
                actorsAddressBooks.add(addressBook);
            }
            return actorsAddressBooks;
        } else {
            throw new ActorAddressBookNotFoundException(ActorAddressBookNotFoundException.DEFAULT_MESSAGE, null, "", "There is not a actor address book registered with that actor_id.");
        }
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