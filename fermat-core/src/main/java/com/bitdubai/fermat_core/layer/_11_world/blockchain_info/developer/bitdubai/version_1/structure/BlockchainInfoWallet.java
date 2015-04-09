package com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer._11_world.CryptoWallet;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.DealsWithBlockchainInfoApi;
import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantStartBlockchainInfoWallet;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;


import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;

import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer._2_os.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Address;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.PaymentResponse;
import com.bitdubai.fermat_core.layer._11_world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import java.io.IOException;
import java.util.UUID;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;

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


    /**
     * Constructor.
     */

    public BlockchainInfoWallet( UUID pluginId,UUID walletId){

        this.pluginId = pluginId;
        this.walletId = walletId;

    }

    public void initialize() throws CantStartBlockchainInfoWallet {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.walletId.toString());
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            BlockchainInfoDatabaseFactory databaseFactory = new BlockchainInfoDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {

                this.database =  databaseFactory.createDatabase(this.pluginId, this.walletId);

            }
            catch (CantCreateDatabaseException cantCreateDatabaseException){

                /**
                 * The database cannot be created. I can not handle this situation.
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
         * Will try to open the wallets' database.
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.walletId.toString());
        }
        catch (DatabaseNotFoundException  | CantOpenDatabaseException exception ) {
            /**
             * I can not solve this situation.
             */
            System.err.println("DatabaseNotFoundException or CantOpenDatabaseException: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantStartBlockchainInfoWallet();
        }

        DatabaseTable table;

        /**
         * Now I will load the information into a memory structure. Firstly the incoming crypto.
         */
        table = this.database.getTable(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);

        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantStartBlockchainInfoWallet();
        }


        /**
         * Now I will load the information into a memory structure. Firstly the incoming crypto.
         */
        table = this.database.getTable(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);

        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantStartBlockchainInfoWallet();
        }


        /**
         * Then the incoming crypto.
         */
        table = this.database.getTable(BlockchainInfoDatabaseConstants.OUTGOING_CRYPTO_TABLE_NAME);

        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantStartBlockchainInfoWallet();
        }


        /**
         * Then the crypto address.
         */
        table = this.database.getTable(BlockchainInfoDatabaseConstants.CRYPTO_ADDRESSES_TABLE_NAME);

        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
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
        }  catch (CantCreateFileException cantCreateFileException) {
            /**
             * This really should never happen. But if it does...
             */
            System.err.println("CantCreateFileException: " + cantCreateFileException.getMessage());
            cantCreateFileException.printStackTrace();

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
          catch (CantCreateFileException cantCreateFileException) {
        /**
         * This really should never happen. But if it does...
         */
        System.err.println("CantCreateFileException: " + cantCreateFileException.getMessage());
        cantCreateFileException.printStackTrace();

        }
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
            e.printStackTrace(); }
        catch (CantCreateFileException cantCreateFileException) {
            /**
             * This really should never happen. But if it does...
             */
            System.err.println("CantCreateFileException: " + cantCreateFileException.getMessage());
            cantCreateFileException.printStackTrace();

        }


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
