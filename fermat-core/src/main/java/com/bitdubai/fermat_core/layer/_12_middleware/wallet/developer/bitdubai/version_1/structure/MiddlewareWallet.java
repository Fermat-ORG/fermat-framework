package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.CryptoAccount;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.FiatAccount;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.Wallet;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */

/**
 * The Middleware Wallet manages the information about the funds the user have in a wallet. It has a multi-layer design.
 * 
 * The top one is the Account Layer, that is what users sees, their accounts.
 * 
 * The middle layer is the Crypto Value Layer, that is what the user has in crypto currencies.
 * 
 * The bottom layer is the Value Chunk Layer, that represents the chunks of crypto the user have, maintaining the
 * relationship with the price it was bought.
 * 
 * The wallet also manages inter account transactions.
 * 
 * * * * * *
 * * * 
 */

public class MiddlewareWallet implements DealsWithPluginDatabaseSystem, Wallet  {


    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    
    /**
     * Wallet Interface member variables.
     */
    UUID walletId;
    UUID ownerId;
    Database database;
    
    
    
    List<FiatAccount> fiatAccounts;
    List<CryptoAccount> cryptoAccounts;
    
    public MiddlewareWallet (UUID ownerId){

        /**
         * The only one who can set the ownerId is the Plugin Root.
         */
        this.ownerId = ownerId;
        
        /**
         * I will get a wallet id.
         */
        this.walletId = UUID.randomUUID();
    }
    
    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
    
    /**
     * Wallet Interface implementation.
     */
    @Override
    public UUID getWalletId() {
        return this.walletId;
    }

    @Override
    public void initialize() throws CantInitializeWalletException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.ownerId, this.walletId.toString());
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            
            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            this.database = this.pluginDatabaseSystem.createDatabase(this.ownerId, this.walletId.toString());
            
            /**
             * Next, I will add a few tables.
             */
           // DatabaseTable table = this.database.newTable("accounts");
           // DatabaseTableColumn column= table.newColumn();

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

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public FiatAccount createFiatAccount (FiatCurrency fiatCurrency){
        
        FiatAccount fiatAccount = new MiddlewareFiatAccount(fiatCurrency);
        
        this.fiatAccounts.add (fiatAccount);
        
        return fiatAccount;
    }

    public CryptoAccount createCryptoAccount (CryptoCurrency cryptoCurrency){
        
        CryptoAccount cryptoAccount = new MiddlewareCryptoAccount(cryptoCurrency);
        
        this.cryptoAccounts.add (cryptoAccount);
        
        return cryptoAccount;
    }

    public void deleteFiatAccount(int index){
        this.fiatAccounts.remove(index);
    }

    public void deleteCryptoAccount(int index){
        this.cryptoAccounts.remove(index);
    }
    
    public FiatAccount getFiatAccount (int index){
        return fiatAccounts.get(index);
    }

    public CryptoAccount getCryptoAccount (int index){
        return cryptoAccounts.get(index);
    }

    public void sizeOfFiatAccounts (){
        fiatAccounts.size();
    }

    public void sizeOfCryptoAccounts (){
        cryptoAccounts.size();
    }
    
    public void transferFromFiatToFiat (FiatAccount fiatAccountFrom, FiatAccount fiatAccountTo, Double amountFrom, Double amountTo){
                
    }
    
    public void transferFromCryptoToCrypto (CryptoAccount cryptoAccountFrom, CryptoAccount cryptoAccountTo, Double amountFrom, Double amountTo){

    }

    public void transferFromFiatToCrypto (FiatAccount fiatAccountFrom, CryptoAccount cryptoAccountTo, Double amountFrom, Double amountTo){

    }

    public void transferFromCryptoToFiat (CryptoAccount cryptoAccountFrom, FiatAccount fiatAccountTo, Double amountFrom, Double amountTo){

    }
    
    public void debitFiatAccount (FiatAccount fiatAccount,Double amount){
        
    }

    public void creditFiatAccount (FiatAccount fiatAccount,Double amount){

    }

    public void debitCryptoAccount (CryptoAccount cryptoAccount,Double amount){

    }

    public void creditCryptoAccount (CryptoAccount cryptoAccount,Double amount){

    }



}
