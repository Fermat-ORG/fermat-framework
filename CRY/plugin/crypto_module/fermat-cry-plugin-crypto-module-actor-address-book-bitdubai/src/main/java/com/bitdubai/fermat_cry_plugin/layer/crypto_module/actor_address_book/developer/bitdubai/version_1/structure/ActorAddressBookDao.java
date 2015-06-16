package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

/**
 * Created by natalia on 16/06/15.
 */

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.exceptions.CantGetActorCryptoAddress;
import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.exceptions.CantRegisterActorCryptoAddress;
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
import com.bitdubai.fermat_api.layer.pip_user.UserTypes;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */

public class ActorAddressBookDao implements DealsWithPluginDatabaseSystem {



    /**
     * CryptoAddressBook Interface member variables.
     */

    private UUID ownerId;
    private Database database;

    private UserTypes userTypes;
    private UUID user_id ;
    private CryptoAddress userCryptoAddress;



    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * UsesDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * Constructor.
     */
    public ActorAddressBookDao(UUID ownerId, ErrorManager errorManager){

        /**
         * The only one who can set the ownerId is the Plugin Root.
         */
        this.ownerId = ownerId;
        this.errorManager = errorManager;

    }


    /**
     * CryptoAddressBook Interface implementation.
     */

    public void initialize() throws CantInitializeActorAddressBookException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.ownerId, this.ownerId.toString());
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

                this.database =  databaseFactory.createDatabase(this.ownerId, this.ownerId);

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


    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }



    public void registerActorCryptoAddress (UserTypes userType, UUID userId,CryptoAddress cryptoAddress)throws CantRegisterActorCryptoAddress {

        /**
         * Here I create the Address book record for new Actor.
         */
        long unixTime = System.currentTimeMillis() / 1000L;

        DatabaseTable addressbookTable = database.getTable(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        DatabaseTableRecord addressbookRecord = addressbookTable.getEmptyRecord();

        UUID creditRecordId = UUID.randomUUID();

        addressbookRecord.setUUIDValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID , creditRecordId);
        addressbookRecord.setUUIDValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID_USER, userId);
        addressbookRecord.setStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_USER_TYPE, userType.getCode());
        addressbookRecord.setStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());
        addressbookRecord.setLongValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_TIME_STAMP, unixTime);

        try{
            addressbookTable.insertRecord(addressbookRecord);
        }catch(CantInsertRecord CantInsertRecord)
        {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, CantInsertRecord);
            throw new CantRegisterActorCryptoAddress();
        }


    }

    public com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.ActorAddressBook getActorAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetActorCryptoAddress {

        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        table.setStringFilter(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS,cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetActorCryptoAddress();
        }


        /**
         * Will go through the records getting each Actor address.
         */


        for (DatabaseTableRecord record : table.getRecords()) {
            userTypes = UserTypes.getByCode(record.getStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_USER_TYPE));
            user_id = record.getUUIDValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID_USER);
            userCryptoAddress.setAddress(record.getStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS));
        }

        return new com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBook(user_id, userTypes, userCryptoAddress);
    }

    public List<com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.ActorAddressBook> getAllActorAddressBookByUserId(UUID userId) throws CantGetActorCryptoAddress {

        DatabaseTable table;

        List<com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.ActorAddressBook> actorsAddressBooks = new ArrayList<com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.ActorAddressBook>();

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        table.setUUIDFilter(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID,userId, DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantGetActorCryptoAddress();
        }


        /**
         * Will go through the records getting each Actor address.
         */


        for (DatabaseTableRecord record : table.getRecords()) {
            userTypes = UserTypes.getByCode(record.getStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_USER_TYPE));
            user_id = record.getUUIDValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID_USER);
            userCryptoAddress.setAddress(record.getStringValue(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS));

            com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.ActorAddressBook addressBook = new com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBook(user_id, userTypes, userCryptoAddress);
            actorsAddressBooks.add(addressBook);

        }

        return actorsAddressBooks;
    }

}