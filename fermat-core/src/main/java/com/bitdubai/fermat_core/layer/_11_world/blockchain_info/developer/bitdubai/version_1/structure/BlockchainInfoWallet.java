package com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.CryptoWallet;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.DealsWithBlockchainInfoApi;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantStartBlockchainInfoWallet;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Address;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.PaymentResponse;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import java.io.IOException;
import java.util.UUID;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;
import com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.exceptions.CantStartWalletException;

/**
 * Created by ciencias on 3/19/15.
 */
public class BlockchainInfoWallet implements CryptoWallet ,DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem, DealsWithBlockchainInfoApi {

    /**
     * UsesFileSystem Interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * CryptoWallet Interface member variables.
     */
    private String password = "";
    private String apiKey = "";
    private String walletAddress ="";
    private String walletGuid ="";
    private UUID walletId;
    private UUID pluginId;
    Database database;
    
    
    // TODO NATALIA : Fijate que en  DISCOUNT WALLET hay una clase con las constantes de base de datos y otra clase que es la DatabaseFactory. Segui ese patron de diseño.

    final String INCOMING_CRYPTO_TABLE_NAME = "incoming_crypto";
    final String INCOMING_CRYPTO_TABLE_ID_COLUMN_NAME = "id";
    final String INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME = "trx_hash";
    final String INCOMING_CRYPTO_TABLE_AMOUNT_COLUMN_NAME = "amount";
    final String INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_TO_COLUMN_NAME = "crypto_address_to";
    final String INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME = "status";
    final String INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME = "current_confirmations";
    final String INCOMING_CRYPTO_TABLE_PREVIOUS_CONFIRMATIONS_COLUMN_NAME = "previous_confirmations";

    final String CRYPTO_ACCOUNTS_TABLE_NAME = "crypto accounts";
    final String CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME = "id";
    final String CRYPTO_ACCOUNTS_TABLE_ALIAS_COLUMN_NAME = "alias";
    final String CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME = "balance";

    public BlockchainInfoWallet( UUID pluginId,UUID walletId){

        this.pluginId = pluginId;
        this.walletId = walletId;

    }


    /**
     * UsesFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void start() throws  CantStartBlockchainInfoWallet{
        /**
         * I will try to open the wallets' database..
         */
        try {

            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, "36e5c6d4-4faa-4f47-aad7-7769b393773a");
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
        try{
            this.database = this.pluginDatabaseSystem.createDatabase(this.pluginId, this.walletId.toString());

            /**
             * Next, I will add a few tables.
             */
            try {

                DatabaseTableFactory table;

                /**
                 * First the INCOMING_CRYPTO table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.pluginId, INCOMING_CRYPTO_TABLE_NAME);

                table.addColumn(INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(INCOMING_CRYPTO_TABLE_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.INTEGER, 0);
                table.addColumn(INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME, DatabaseDataType.INTEGER, 0);
                table.addColumn(INCOMING_CRYPTO_TABLE_PREVIOUS_CONFIRMATIONS_COLUMN_NAME, DatabaseDataType.INTEGER, 0);

                try {
                    ((DatabaseFactory) this.database).createTable(this.pluginId, table);
                }
                catch (CantCreateTableException cantCreateTableException) {
                    System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                    cantCreateTableException.printStackTrace();
                    throw new CantStartBlockchainInfoWallet();
                }

                /**
                 * Then the OUTGOING_CRYPTO accounts table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.pluginId, CRYPTO_ACCOUNTS_TABLE_NAME);
                table.addColumn(CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(CRYPTO_ACCOUNTS_TABLE_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

                try {
                    ((DatabaseFactory) this.database).createTable(this.pluginId, table);
                }
                catch (CantCreateTableException cantCreateTableException) {
                    System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                    cantCreateTableException.printStackTrace();
                    throw new CantStartBlockchainInfoWallet();
                }

            }
            catch (InvalidOwnerId invalidOwnerId) {
                /**
                 * This shouldn't happen here because I was the one who gave the owner id to the database file system,
                 * but anyway, if this happens, I can not continue.
                 * * *
                 */
                System.err.println("InvalidOwnerId: " + invalidOwnerId.getMessage());
                invalidOwnerId.printStackTrace();
                throw new CantStartBlockchainInfoWallet();
            }
        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            System.err.println("CantCreateDatabaseException: " + cantCreateDatabaseException.getMessage());
            cantCreateDatabaseException.printStackTrace();
            throw new CantStartBlockchainInfoWallet();
        }

        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            System.err.println("CantOpenDatabaseException: " + cantOpenDatabaseException.getMessage());
            cantOpenDatabaseException.printStackTrace();
            throw new CantStartBlockchainInfoWallet();
        }

    }

    @Override
    public void stop() {

    }



    /**
     * CryptoWallet Interface implementation.
     */

    @Override
    public long getWalletBalance(CryptoCurrency cryptoCurrency){
        long balance = 0;
        try{
            //get wallet property files, address and guid, and get actual balance
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, pluginId.toString(), this.walletId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            String[] walletData = layoutFile.getContent().split(";");

            this.walletGuid = walletData[1].toString();
            this.password = walletData[4].toString();

            Wallet wallet = new Wallet(this.walletGuid, this.password);
            wallet.setApiCode(this.apiKey);
            balance = wallet.getBalance();


        } catch (IOException|APIException e) {
            System.err.println("CantConnectToApi: " + e.getMessage());
            e.printStackTrace();
           // throw new CantStartWalletException();
           }
        catch (FileNotFoundException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            System.err.println("CantNotGetWalletBalance: " + e.getMessage());
            e.printStackTrace();
           // throw new CantLoadFileException("wallet file");
            }
        return balance;
    }

    @Override
    public long getAddressBalance(CryptoAddress cryptoAddress){
        long addressBalance = 0;
        try{

            this.walletAddress = cryptoAddress.getAddress();
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, pluginId.toString(), this.walletId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String[] walletData = layoutFile.getContent().split(";");

            this.password = walletData[4].toString();
            Wallet wallet = new Wallet(this.walletGuid, this.password);
            wallet.setApiCode(this.apiKey);
            // get an address from your wallet and include only transactions with up to 3
            // confirmations in the balance
            Address addr = wallet.getAddress(this.walletAddress, 3);
            addressBalance =  addr.getBalance();

        } catch (IOException|APIException e) {
            e.printStackTrace();}
        catch (FileNotFoundException e) {
            e.printStackTrace();}

        return addressBalance;
    }

    @Override
    public void sendCrypto (CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddress){
        // send 0.2 bitcoins with a custom fee of 0.01 BTC and a note
        // public notes require a minimum transaction size of 0.005 BTC
        try{
            PluginTextFile layoutFile;
            layoutFile = pluginFileSystem.getTextFile(pluginId, pluginId.toString(), walletId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            String[] walletData = layoutFile.getContent().split(";");
            walletAddress = walletData[0].toString();
            walletGuid = walletData[1].toString();

            Wallet wallet = new Wallet(walletGuid, password);
            PaymentResponse payment = wallet.send(cryptoAddress.getAddress(), amount, null,null, "");

        } catch (IOException|APIException e) {
            e.printStackTrace();}
        catch (FileNotFoundException e) {
            e.printStackTrace();}


    }

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
