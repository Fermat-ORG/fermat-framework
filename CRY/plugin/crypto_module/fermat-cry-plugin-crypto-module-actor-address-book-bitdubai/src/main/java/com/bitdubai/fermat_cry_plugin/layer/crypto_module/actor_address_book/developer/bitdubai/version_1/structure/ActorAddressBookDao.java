package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

/**
 * Created by natalia on 16/06/15.
 * asdasda
 */

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.ActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantRegisterActorAddressBook;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */

public class ActorAddressBookDao implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public ActorAddressBookDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId){
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

    public void initialize() throws CantInitializeActorAddressBookException {

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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeActorAddressBookException();
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            ActorAddressBookDatabaseFactory databaseFactory = new ActorAddressBookDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {

                this.database =  databaseFactory.createDatabase(this.pluginId, this.pluginId);

            }
            catch (CantCreateDatabaseException cantCreateDatabaseException){

                /**
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeActorAddressBookException();
            }
        }
    }

    public void registerActorAddressBook(UUID actorId, Actors actorType, CryptoAddress cryptoAddress) throws CantRegisterActorAddressBook {

        /**
         * Here I create the Address book record for new Actor.
         */
        long unixTime = System.currentTimeMillis() / 1000L;

        DatabaseTable addressBookTable = database.getTable(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        DatabaseTableRecord addressBookRecord = addressBookTable.getEmptyRecord();

        UUID creditRecordId = UUID.randomUUID();

        addressBookRecord.setUUIDValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID , creditRecordId);
        addressBookRecord.setUUIDValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_ID, actorId);
        addressBookRecord.setStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_TYPE, actorType.getCode());
        addressBookRecord.setStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());
        addressBookRecord.setStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, cryptoAddress.getCryptoCurrency().getCode());
        addressBookRecord.setLongValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_TIME_STAMP, unixTime);

        try{
            addressBookTable.insertRecord(addressBookRecord);
        } catch(CantInsertRecord cantInsertRecord) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInsertRecord);
            throw new CantRegisterActorAddressBook();
        }
    }

    public ActorAddressBook getActorAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetActorAddressBook {

        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        table.setStringFilter(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS,cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
        table.setStringFilter(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY,cryptoAddress.getCryptoCurrency().getCode(), DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetActorAddressBook();
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
            actorId = record.getUUIDValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_ID);
            actorType = Actors.getByCode(record.getStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_TYPE));
        } else {
            return null;
        }

        return new ActorAddressBookRegistry(actorId, actorType, cryptoAddress);
    }

    public List<ActorAddressBook> getAllActorAddressBookByActorId(UUID actorId) throws CantGetActorAddressBook {

        DatabaseTable table;

        List<ActorAddressBook> actorsAddressBooks = new ArrayList<>();

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        table.setUUIDFilter(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_ID, actorId, DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetActorAddressBook();
        }


        /**
         * Will go through the records getting each Actor address.
         */

        Actors actorType;
        String address;
        CryptoCurrency cryptoCurrency;
        CryptoAddress cryptoAddress;

        for (DatabaseTableRecord record : table.getRecords()) {
            actorType = Actors.getByCode(record.getStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ACTOR_TYPE));
            address = record.getStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS);
            cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY));
            cryptoAddress = new CryptoAddress(address, cryptoCurrency);
            ActorAddressBook addressBook = new ActorAddressBookRegistry(actorId, actorType, cryptoAddress);
            actorsAddressBooks.add(addressBook);

        }

        return actorsAddressBooks;
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