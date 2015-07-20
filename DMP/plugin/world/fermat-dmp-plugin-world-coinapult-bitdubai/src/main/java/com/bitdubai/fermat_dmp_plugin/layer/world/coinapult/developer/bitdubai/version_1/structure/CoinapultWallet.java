/*
 * @#CoinapultWallet.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantGetAddressesException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantGetWalletBalanceException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantInitializeDbWalletException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.exceptions.CantInitializeFileWalletException;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.wallet.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.wallet.CryptoWalletAddress;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.wallet.CryptoWalletBalance;
import com.bitdubai.fermat_api.layer.dmp_world.coinapult.wallet.CryptoWalletTransaction;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.enums.States;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client.AccountInfo;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client.Address;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client.CoinapultClient;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client.ECC;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.coinapult_http_client.Transaction;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_world.coinapult.developer.bitdubai.version_1.structure.CoinapultWallet</code> represents
 * a coinapult wallet
 *
 * Created by Roberto Requena - (rrequena) on 30/04/15.
 * @version 1.0
 */
public class CoinapultWallet implements CryptoWallet {

    /*
     * Represents the Database
     */
    private Database database;

    /**
     * DealsWithError Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
     * UsesFileSystem Interface member variable
     */
    private PluginFileSystem pluginFileSystem;

    /*
     * Represents the file
     */
    private PluginTextFile walletPkFile;

   /**
    * Represents the addresses
    */
    private List<CryptoWalletAddress> addresses;

    /**
     * Represents the balances
     */
    private List<CryptoWalletBalance> balances;

    /**
     * Represents the balances
     */
    private List<CryptoWalletTransaction> transactions;

    /**
     * Represents the coinapultClient
     */
    private CoinapultClient coinapultClient;

     /**
      * Represents the pluginId
      */
    private UUID pluginId;

    /**
     * Represents the walletId
     */
    private UUID walletId;

   /*
    * The coinapult api work with Elliptic curve cryptography (ECC), to created new wallets.
    * It is a variant of the asymmetric or public key cryptography based on the mathematics
    * of elliptic curves. Asymmetric systems or public key cryptography uses two different keys,
    * one of which can be public, the other private.
    *
    * http://en.wikipedia.org/wiki/Elliptic_curve_cryptography
    */
    /**
     * Represents the keyPair
     */
    private KeyPair keyPair;

    /**
     * Constructor
     *
     * @param walletId      The walletId for this wallet
     * @param pluginId      The walletId for this plugin
     * @param errorManager  The error manager
     * @param eventManager  The event manager
     */
    public CoinapultWallet(UUID walletId, UUID pluginId, ErrorManager errorManager, EventManager eventManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem) {

        this.walletId = walletId;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.addresses = new ArrayList<>();
        this.balances = new ArrayList<>();
        this.database = null;
        this.keyPair = null;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
    }



    /**
     * This method automatically initialize the coinapult wallet componet
     *
     * @throws CantInitializeDbWalletException, CantInitializeFileWalletException
     */
    public void initialize() throws CantInitializeDbWalletException, CantInitializeFileWalletException {

        /*
         * If the database is not initialized
         */
        if (database == null && pluginDatabaseSystem != null){
            this.initializeDb();
        }

        /*
         * If the file is not initialized
         */
        if (walletPkFile == null && pluginFileSystem != null){
            this.initializeFile();
        }

    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeDbWalletException
     */
    private void initializeDb() throws CantInitializeDbWalletException {


        try {

            /*
             * Open new database connection
             */
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.walletId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeDbWalletException();

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CoinapultDatabaseFactory coinapultDatabaseFactory = new CoinapultDatabaseFactory();
            coinapultDatabaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.database = coinapultDatabaseFactory.createDatabase(this.pluginId, this.walletId);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeDbWalletException();

            }
        }

    }

    /**
     * This method initialize the file
     *
     * @throws CantInitializeFileWalletException
     */
    private void initializeFile() throws CantInitializeFileWalletException {

        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);

        try {

            /*
             * get the file with the private key of this wallet
             */
            walletPkFile = pluginFileSystem.getTextFile(pluginId, walletId.toString(), walletId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

        } catch (FileNotFoundException e) {

            /*
             * The file no exist may be the first time the wallet is created on this device,
             * We need to create the file
             */
            try {

                /*
                 * create the file
                 */
                walletPkFile = pluginFileSystem.createTextFile(pluginId, pluginId.toString(), walletId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);


                /*
                 * Generate new Key par for this wallet
                 */
                this.keyPair = ECC.generateKeypairAndroid();

                /*
                 * Export the private key to PEM format and save as the content of the file
                 *
                 * PEM: file format used to store digital certificates, Encoded certificate in Base64,
                 * enclosed "-----BEGIN CERTIFICATE-----" and "-----END CERTIFICATE-----"
                 *
                 */
                walletPkFile.setContent(ECC.exportToPEM(this.keyPair.getPrivate()));

                /*
                 * Persist the file
                 */
                walletPkFile.persistToMedia();


            } catch (Exception exception) {

                /**
                 *  - The file can not be created
                 *  - The key pair can't generate
                 *  - Can't export the private key to PEM
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeFileWalletException();

            }


        } catch (CantCreateFileException cantCreateFileException) {

            /**
             * The file exist but can't get it
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
            throw new CantInitializeFileWalletException();
        }


        /*
         * If file is got it, load it
         */
        if (walletPkFile != null){

            try {

                /*
                 * If the key pair is null load from file content
                 */
                if (this.keyPair == null){

                    walletPkFile.loadFromMedia();
                    String walletPkPem = walletPkFile.getContent();
                    this.keyPair =  ECC.importFromPEM(walletPkPem);
                    this.coinapultClient = new CoinapultClient(walletPkPem); //Probar si la clave funciona cargada directamente desde el archivo

                }else {

                    this.coinapultClient = new CoinapultClient(ECC.exportToPEM(this.keyPair.getPrivate()));
                }


            } catch (Exception exception) {
                /**
                 * - The file exist but can't load it
                 * - Can't convert the pem string to keypair
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeFileWalletException();

            }
        }

    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#convert(Number, String, Number, String)
     */
    @Override
    public CryptoWalletTransaction convert(Number amount, String currency, Number outAmount, String outCurrency) {

        try {

            /*
             * Create the convert transaction with coinapult api
             */
            Transaction.Json convert = coinapultClient.convert(amount, currency, outAmount, outCurrency,null);
            CoinapultTransaction coinapultTransaction = new CoinapultTransaction(convert);


            //Save the new transaction to database


            return coinapultTransaction;

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#getAddresses()
     */
    @Override
    public List<CryptoWalletAddress> getAddresses() throws CantGetAddressesException{

        if (addresses == null ||
                addresses.isEmpty()){

            try {

                /**
                 * Get addresses from database
                 */


            } catch (Exception exception) {

                /**
                 * Can't convert the pem string to keypair
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantGetAddressesException();

            }


        }


        return addresses;
    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#getBalances()
     */
    @Override
    public List<CryptoWalletBalance> getBalances() throws CantGetWalletBalanceException{


        if (balances == null ||
                balances.isEmpty()){

            try {

                /*
                 * Get balance from coinapult
                 */
                AccountInfo.Json accountInfo = coinapultClient.accountInfo();

                for (AccountInfo.Balance balance : accountInfo.balances){
                    this.balances.add(new CoinapultBalance(balance.currency,
                                                                                                                                                           balance.amount.multiply(new BigDecimal(1000)).longValue(), //Convert the amount to Satoshis multiply for 1000
                                                                                                                                                           Boolean.TRUE));
                }

                /*
                 * Then save the balances to balances history database
                 */

            } catch (Exception exception) {

                /*
                 * Can't connect to coinapult api, and if can not get the balances update from coinapult
                 * load from database history
                 */
                throw new CantGetWalletBalanceException();

            }
        }

        return balances;
    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#generateNewBitCoinAddress()
     */
    @Override
    public CryptoWalletAddress generateNewBitCoinAddress() {


        try {

            /*
             * Generate new address from coinapult api
             */
            Address.Json newAddress = coinapultClient.getBitcoinAddress();

            CoinapultAddress coinapultAddress = new CoinapultAddress();
            coinapultAddress.setAddress(newAddress.address);
            coinapultAddress.setCryptoCurrency(CryptoCurrency.BITCOIN);
            coinapultAddress.setStatus(States.COINAPULT_ADDRESS_STATE_VALID.getValue());

            //Save the new address to database


            //Add to wallet's addresses
            this.getAddresses().add(coinapultAddress);

            return  coinapultAddress;

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#lock(Number, Number, String)
     */
    @Override
    public CryptoWalletTransaction lock(Number amount, Number outAmount, String outCurrency) {

        try {

            /*
             * Create the lock transaction with coinapult api
             */
            Transaction.Json lock = coinapultClient.lock(amount, outAmount, outCurrency, null);
            CoinapultTransaction coinapultTransaction = new CoinapultTransaction(lock);


            //Save the new transaction to database


            return coinapultTransaction;

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#unlock(Number, String, Number, String)
     */
    @Override
    public CryptoWalletTransaction unlock(Number amount, String inCurrency, Number outAmount, String address) {

        try {

            /*
             * Create the unlock transaction with coinapult api
             */
            Transaction.Json unlock = coinapultClient.unlock(amount, inCurrency, outAmount, address, null, Boolean.TRUE);
            CoinapultTransaction coinapultTransaction = new CoinapultTransaction(unlock);


            //Save the new transaction to database


            return coinapultTransaction;

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#send(Number, String, String, Number)
     */
    @Override
    public CryptoWalletTransaction send(Number amount, String currency, String address, Number outAmount) {

        try {

            /*
             * Create the send transaction with coinapult api
             */
            Transaction.Json payment = coinapultClient.send(amount, currency, address, outAmount, null, null, null);
            CoinapultTransaction coinapultTransaction = new CoinapultTransaction(payment);


            //Save the new transaction to database


            return coinapultTransaction;

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#receive(Number)
     */
    @Override
    public CryptoWalletTransaction receive(Number amount) {

        try {

            /*
             * Create the receive transaction with coinapult api
             */
            Transaction.Json invoice = coinapultClient.receive(amount, CryptoCurrency.BITCOIN.toString(), null);
            CoinapultTransaction coinapultTransaction = new CoinapultTransaction(invoice);


            //Save the new transaction to database


            return coinapultTransaction;

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
        }

        return null;
    }

    /**
     * (non-Javadoc)
     * @see CryptoWallet#getTransactions()
     */
    @Override
    public List<CryptoWalletTransaction> getTransactions() throws CantGetTransactionsException{

        if (transactions == null
                || transactions.isEmpty()){

            try {

                /**
                 * Get transactions from database
                 */


            } catch (Exception exception) {

                /**
                 * Can't load from database
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_COINAPULT_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantGetTransactionsException();

            }

        }

        return transactions;
    }

    /**
     * Return the CoinapultClient instance for this wallet
     * @return CoinapultClient
     */
    public CoinapultClient getCoinapultClient() {
        return coinapultClient;
    }
}
