package com.bitdubai.fermat_core.layer._8_crypto.address_book.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import com.bitdubai.fermat_api.layer._5_user.UserTypes;
import com.bitdubai.fermat_api.layer._5_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer._5_user.device_user.DeviceUserManager;

import com.bitdubai.fermat_api.layer._5_user.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer._5_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_api.layer._5_user.intra_user.DealsWithIntraUsers;
import com.bitdubai.fermat_api.layer._5_user.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.CryptoAddressBook;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.exceptions.CantGetUserCryptoAddress;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.exceptions.CantRegisterUserCryptoAddress;
import com.bitdubai.fermat_core.layer._8_crypto.address_book.developer.bitdubai.version_1.exceptions.*;
import com.bitdubai.fermat_api.layer._5_user.User;
import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */

public class AddressBook implements CryptoAddressBook ,DealsWithDeviceUsers,DealsWithExtraUsers,DealsWithIntraUsers, DealsWithPluginDatabaseSystem {


    /**
     * CryptoAddressBook Interface member variables.
     */


    private UUID ownerId;
    private Database database;

    /**
     * DealsWithDeviceUsers Interface member variables.
     */

    DeviceUserManager deviceUserManager;

    /**
     * DealsWithExtraUsers Interface member variables.
     */

    ExtraUserManager extraUserManager;

    /**
     * DealsWithIntraUsers Interface member variables.
     */


    IntraUserManager intraUserManager;


    /**
     * UsesDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;





    /**
     * Constructor.
     */
    public AddressBook(UUID ownerId){

        /**
         * The only one who can set the ownerId is the Plugin Root.
         */
        this.ownerId = ownerId;

    }





    /**
     * CryptoAddressBook Interface implementation.
     */

    public void initialize() throws CantInitializeAddresBookException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.ownerId, this.ownerId.toString());
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            AddressBookDatabaseFactory databaseFactory = new AddressBookDatabaseFactory();
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
                System.err.println("CantCreateDatabaseException: " + cantCreateDatabaseException.getMessage());
                cantCreateDatabaseException.printStackTrace();
                throw new CantInitializeAddresBookException();
            }
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            System.err.println("CantOpenDatabaseException: " + cantOpenDatabaseException.getMessage());
            cantOpenDatabaseException.printStackTrace();
            throw new CantInitializeAddresBookException();
        }

    }


    @Override
    public User getUserByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetUserCryptoAddress {

        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter by crypto address .
         */
        table = this.database.getTable(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        table.setStringFilter(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS,cryptoAddress.getAddress(), DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantGetUserCryptoAddress();
        }


        /**
         * Will go through the records getting each user address.
         */
        UserTypes userTypes;
        UUID user_id ;

        for (DatabaseTableRecord record : table.getRecords()) {
            userTypes = UserTypes.getByCode(record.getStringValue(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_USER_TYPE));
            user_id = record.getUUIDValue(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID_USER);

                switch (userTypes) {
                    case DEVICE_USER:
                        return this.deviceUserManager.getUser(user_id);

                    case EXTRA_USER:
                        return this.extraUserManager.getUser(user_id);

                    case INTRA_USER:
                        return this.intraUserManager.getUser(user_id);

                }


        }

        return null;
    }

    @Override
    public void registerUserCryptoAddress (UserTypes userType, UUID userId,CryptoAddress cryptoAddress)throws CantRegisterUserCryptoAddress {

        /**
         * Here I create the Address book record for new user.
         */
        long unixTime = System.currentTimeMillis() / 1000L;

        DatabaseTable addressbookTable = database.getTable(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
        DatabaseTableRecord addressbookRecord = addressbookTable.getEmptyRecord();

        UUID creditRecordId = UUID.randomUUID();

        addressbookRecord.setUUIDValue(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID , creditRecordId);
        addressbookRecord.setUUIDValue(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID_USER, userId);
        addressbookRecord.setStringValue(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_USER_TYPE, userType.getCode());
        addressbookRecord.setStringValue(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());
        addressbookRecord.setlongValue(AddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_TIME_STAMP,  unixTime);

        try{
            addressbookTable.insertRecord(addressbookRecord);
        }catch(CantInsertRecord e)
        {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantInsertRecord: " + e.getMessage());
            e.printStackTrace();
            throw new CantRegisterUserCryptoAddress();
        }


    }

    /**
     * DealsWithDeviceUsers interface implementation.
     */

    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager){
       this.deviceUserManager = deviceUserManager;
    }

    /**
     * DealsWithExtraUsers interface implementation.
     */

    @Override
    public void setExtraUserManager(ExtraUserManager extraUserManager){
         this.extraUserManager = extraUserManager;
    }

    /**
     * DealsWithIntraUsers interface implementation.
     */

    @Override
    public void setIntraUserManager(IntraUserManager intraUserManager){
        this.intraUserManager = intraUserManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

}
