package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.DealsWithCryptoIndex;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.interfaces.DiscountWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;


import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.exceptions.CantStartWalletServiceException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.interfaces.WalletService;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.exceptions.CantInitializeWalletException;

import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */

/**
 * This Wallet provides fiat Accounts over crypto.
 *
 * * * * * *
 * * * 
 */

public class BasicWalletDiscountWallet implements DealsWithCryptoIndex, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DiscountWallet, WalletService {

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    private CryptoIndexManager cryptoIndexManager;
    
    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    
    /**
     * Wallet Interface member variables.
     */
    private UUID walletId;
    private UUID ownerId;

    private FiatCurrency fiatCurrency;
    private CryptoCurrency cryptoCurrency;

    private Database database;

  //  private Map<UUID, Account> fiatAccounts = new HashMap<>();

    /**
     * Constructor.
     */
    //public BasicWalletDiscountWallet(UUID ownerId, FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency){
    public BasicWalletDiscountWallet(UUID ownerId){

        /**
         * The only one who can set the ownerId is the Plugin Root.
         */
        this.ownerId = ownerId;

        /**
         * I will set the wallet fiat and crypto currency
         */
        this.fiatCurrency = fiatCurrency;
        this.cryptoCurrency = cryptoCurrency;
        /**
         * I will get a wallet id.
         * TODO: ESTO NO DEBERIA SER UN VALOR FIJO?
         */
        this.walletId = UUID.randomUUID();
    }


    /**
     * BasicWalletDiscountWallet methods.
     */
    
    public void initialize() throws CantInitializeWalletException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.ownerId, this.walletId.toString());
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            
            BasicWalletDatabaseFactory databaseFactory = new BasicWalletDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {

                this.database =  databaseFactory.createDatabase(this.ownerId, this.walletId);
                
            }
            catch (CantCreateDatabaseException cantCreateDatabaseException){
    
                /**
                 * The database cannot be created. I can not handle this situation.
                 */
                System.err.println("CantCreateDatabaseException: " + cantCreateDatabaseException.getMessage());
                cantCreateDatabaseException.printStackTrace();
                throw new CantInitializeWalletException();
            }
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            System.err.println("CantOpenDatabaseException: " + cantOpenDatabaseException.getMessage());
            cantOpenDatabaseException.printStackTrace();
            throw new CantInitializeWalletException();
        }

    }

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    @Override
    public void setCryptoIndexManager(CryptoIndexManager cryptoIndexManager) {
        this.cryptoIndexManager = cryptoIndexManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager (ErrorManager errorManager){ this.errorManager = errorManager; };

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
    
    /**
     * DiscountWallet Interface implementation.
     */
    @Override
    public UUID getWalletId() {
        return this.walletId;
    }

    @Override
    public CryptoCurrency getCryptoCurrency(){return this.cryptoCurrency;}

    @Override
    public FiatCurrency getFiatCurrency(){return this.fiatCurrency;}


    /*
     * The wallet balance is calculated by the BasicWalletBalance class
     * so we just call it to do its job.
     */
    @Override
    public long getBalance() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantCalculateBalanceException {

        BasicWalletBalance basicWalletBalance = new BasicWalletBalance();

        basicWalletBalance.setDatabase(this.database);

        return basicWalletBalance.getBalance();

    }

    /*
     * To get the available amount of money that can be spent we call the
     * available method of the BasicWalletAvailable module who answer the
     * question for us.
     */
    @Override
    public long getAvailableAmount() throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantCalculateAvailableAmountException {

        BasicWalletAvailable basicWalletAvailable = new BasicWalletAvailable(this.fiatCurrency, this.cryptoCurrency);
        basicWalletAvailable.setCryptoIndexManager(this.cryptoIndexManager);
        basicWalletAvailable.setErrorManager(this.errorManager);
        basicWalletAvailable.setDatabase(this.database);

        return basicWalletAvailable.getAvailableAmount();

    }

    /**
     * A debit transaction represents an amount of crypto currency going out of the wallet. What we actually register is the crypto
     * currency together with the fiat currency that represents at the moment the transaction was done.
     * * *
     */
    @Override
    public long debit(long fiatAmount,long cryptoAmount) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.DebitFailedException {

        BasicWalletDebitTransaction debitTransaction = new BasicWalletDebitTransaction(this.fiatCurrency,this.cryptoCurrency);

        debitTransaction.setDatabase(this.database);

        return debitTransaction.debit(fiatAmount, cryptoAmount);

    }

    /**
     * A credit represents an amount of fiat currency received. What we actually register is the crypto
     * currency together with the fiat currency that represents at the moment the credit was done.
     * * *
     */
    @Override
    public void credit(long fiatAmount, long cryptoAmount) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CreditFailedException {

        BasicWalletCreditTransaction creditTransaction = new BasicWalletCreditTransaction();

        creditTransaction.setDatabase(this.database);

        creditTransaction.credit(fiatAmount, cryptoAmount);

    }

    @Override
    public long calculateDiscount(long fiatAmount, long cryptoAmount) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CalculateDiscountFailedException {
        BasicWalletDiscount basicWalletDiscount = new BasicWalletDiscount(this.fiatCurrency,this.cryptoCurrency);
        basicWalletDiscount.setDatabase(this.database);
        basicWalletDiscount.setCryptoIndexManager(this.cryptoIndexManager);
        basicWalletDiscount.setErrorManager(this.errorManager);

        return basicWalletDiscount.calculateDiscount(fiatAmount,cryptoAmount);
    }


        /**
         * Wallet Service Interface implementation.
         */
    
    /**
     * The Start method loads the information of the wallet from the Database, and create an in memory structure.
     */
    @Override
    public void start() throws CantStartWalletServiceException {

    }

    @Override
    public void stop() {

    }
}
