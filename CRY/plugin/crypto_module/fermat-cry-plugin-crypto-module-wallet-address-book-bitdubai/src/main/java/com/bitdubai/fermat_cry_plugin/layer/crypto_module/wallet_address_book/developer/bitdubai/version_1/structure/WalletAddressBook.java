package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
//TODO faltan las clases de exceptions en fermat-api
//import CantGetWalletCryptoAddress;
//import CantRegisterWalletCryptoAddress;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletAddressBookException;

import java.util.UUID;

/**
 * Created by armando on 28/05/15.
 */
public class WalletAddressBook implements DealsWithPluginDatabaseSystem {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private UUID walletId;
    private Database database;

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

    public WalletAddressBook(UUID walletId, ErrorManager errorManager){

        /**
         * The only one who can set the ownerId is the Plugin Root.
         */
        this.walletId = walletId;
        this.errorManager = errorManager;

    }


    /**
     * CryptoAddressBook Interface implementation.
     */

    public void initialize() throws CantInitializeWalletAddressBookException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.walletId,WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            WalletAddressBookDataBaseFactory databaseFactory = new WalletAddressBookDataBaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            /**
             * I will create the database where I am going to store the
             information of this wallet.
             */
            try {

                this.database =  databaseFactory.createDatabase(walletId);

            }
            catch (CantCreateDatabaseException cantCreateDatabaseException){

                /**
                 * The database cannot be created. I can not handle this
                 situation.
                 */

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,cantCreateDatabaseException);
                throw new CantInitializeWalletAddressBookException();
            }
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this
             situation.
             */

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeWalletAddressBookException();
        }

    }

  /*  public UUID getActorByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetWalletCryptoAddress {
        DatabaseTable table;

        /**
         *  I will load the information of table into a memory structure, filter
         by crypto address .
         */
      //  table = this.database.getTable(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);

      //  table.setStringFilter(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS,cryptoAddress.getAddress(),DatabaseFilterType.EQUAL);
       // try {
       //     table.loadToMemory();
       // }
       // catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */

          //  errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadTableToMemory);
          //  throw new CantGetWalletCryptoAddress();
       // }


        /**
         * Will go through the records getting each user address.
         */

       // UUID wallet_id ;
       // for (DatabaseTableRecord record : table.getRecords()) {

         //   wallet_id = record.getUUIDValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID_WALLET);

           // return wallet_id;
       // }

       // return null;
   // }

    //public void registerActorCryptoAddress (UUID walletId,CryptoAddress cryptoAddress)throws CantRegisterWalletCryptoAddress {

        /**
         * Here I create the Address book record for new user.
         */
      /*  long unixTime = System.currentTimeMillis() / 1000L;

        DatabaseTable addressbookTable = database.getTable(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        DatabaseTableRecord addressbookRecord = addressbookTable.getEmptyRecord();

        UUID creditRecordId = UUID.randomUUID();


        addressbookRecord.setUUIDValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID , creditRecordId);

        addressbookRecord.setUUIDValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID_WALLET, walletId);

        addressbookRecord.setStringValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, cryptoAddress.getAddress());

        addressbookRecord.setLongValue(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_TIME_STAMP, unixTime);

        try{
            addressbookTable.insertRecord(addressbookRecord);

        }catch(CantInsertRecord CantInsertRecord)
        {
            /**
             * I can not solve this situation.
             */

          //  errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, CantInsertRecord);
         //   throw new CantRegisterWalletCryptoAddress();
       // }


   // }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem
                                                pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


}