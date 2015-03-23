package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantStartBlockchainInfoWallet;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.CryptoAccount;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.FiatAccount;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.Wallet;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantStartWalletException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
    
    final String FIAT_ACCOUNTS_TABLE_NAME = "fiat accounts";
    final String FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME = "id";
    final String FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME = "label";
    final String FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME = "name";
    final String FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME = "balance";
    final String FIAT_ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME = "fiat currency";
    

    final String CRYPTO_ACCOUNTS_TABLE_NAME = "crypto accounts";
    final String CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME = "id";
    final String CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_NAME = "alias";
    final String CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_NAME = "name";
    final String CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME = "balance";
    final String CRYPTO_ACCOUNTS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME = "crypto currency";
    
    
    Map<UUID, FiatAccount> fiatAccounts = new HashMap<>();
    Map<UUID,CryptoAccount> cryptoAccounts = new HashMap<>();
    
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
     * MiddlewareWallet Interface implementation.
     */
    
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
           try{

            this.database = this.pluginDatabaseSystem.createDatabase(this.ownerId, this.walletId.toString());

            /**
             * Next, I will add a few tables.
             */
            try {

                DatabaseTableFactory table;

                /**
                 * First the fiat accounts table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.ownerId, FIAT_ACCOUNTS_TABLE_NAME);
                table.addColumn(FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(FIAT_ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);

                try {
                    ((DatabaseFactory) this.database).createTable(this.ownerId, table);
                }
                catch (CantCreateTableException cantCreateTableException) {
                    System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                    cantCreateTableException.printStackTrace();
                    throw new CantInitializeWalletException();
                }

                /**
                 * Then the crypto accounts table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.ownerId, CRYPTO_ACCOUNTS_TABLE_NAME);
                table.addColumn(CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

                try {
                    ((DatabaseFactory) this.database).createTable(this.ownerId, table);
                }
                catch (CantCreateTableException cantCreateTableException) {
                    System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                    cantCreateTableException.printStackTrace();
                    throw new CantInitializeWalletException();
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
                throw new CantInitializeWalletException();
            }

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
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
     * The Start method.
     */
    public void start() throws CantStartWalletException {
        
        /**
         * I will load the information of the wallet from the Database, and create an in memory structure.
         */

        /**
         * I will try to open the wallets' database.
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.ownerId, this.walletId.toString());
        }
        catch (DatabaseNotFoundException  | CantOpenDatabaseException exception ) {
            /**
             * I can not solve this situation.
             */
            System.err.println("DatabaseNotFoundException or CantOpenDatabaseException: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantStartWalletException();
        }

        DatabaseTable table;
        
        /**
         * Now I will load the information into a memory structure. Firstly the fiat accounts.
         */
        table = this.database.getTable(FIAT_ACCOUNTS_TABLE_NAME);
        table.loadToMemory();

        /**
         * Will go through the records getting each fiat account.
         */
        for (DatabaseTableRecord record : table.getRecords()) {
            
            UUID accountId;
            accountId = record.getValue(FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME);
            
            FiatAccount fiatAccount;
            fiatAccount = new MiddlewareFiatAccount(accountId);

            fiatAccount.setLabel(record.getValue(FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME));
            fiatAccount.setName(record.getValue(FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME));
            
            ((MiddlewareFiatAccount) fiatAccount).setFiatCurrency(FiatCurrency.getByCode(record.getValue(FIAT_ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME)));
            ((MiddlewareFiatAccount) fiatAccount).setBalance(record.getValue(FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME));

            fiatAccounts.put(accountId, fiatAccount);
        }

        /**
         * Secondly the crypto accounts.
         */
        table = this.database.getTable(CRYPTO_ACCOUNTS_TABLE_NAME);
        table.loadToMemory();

        /**
         * Will go through the records getting each crypto account.
         */
        for (DatabaseTableRecord record : table.getRecords()) {

            UUID accountId;
            accountId = record.getValue(CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME);

            CryptoAccount cryptoAccount;
            cryptoAccount = new MiddlewareCryptoAccount(accountId);

            cryptoAccount.setLabel(record.getValue(CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_NAME));
            cryptoAccount.setName(record.getValue(CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_NAME));

            ((MiddlewareCryptoAccount) cryptoAccount).setCryptoCurrency(CryptoCurrency.getByCode(record.getValue(CRYPTO_ACCOUNTS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME)));
            ((MiddlewareCryptoAccount) cryptoAccount).setBalance(record.getValue(CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME));

            cryptoAccounts.put(accountId, cryptoAccount);
        }
        
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
    public FiatAccount[] getFiatAccounts() {
        
        FiatAccount[] fiatAccountsArray = {};

        Iterator iterator = fiatAccounts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            fiatAccountsArray[fiatAccountsArray.length] = (FiatAccount) pair.getValue();
            iterator.remove();
        }
        
        return fiatAccountsArray;
    }

    @Override
    public CryptoAccount[] getCryptoAccounts() {

        CryptoAccount[] cryptoAccountsArray = {};

        Iterator iterator = fiatAccounts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            cryptoAccountsArray[cryptoAccountsArray.length] = (CryptoAccount) pair.getValue();
            iterator.remove();
        }

        return cryptoAccountsArray;
    }
    
    public FiatAccount createFiatAccount (FiatCurrency fiatCurrency){
        
        UUID id = UUID.randomUUID();
        
        FiatAccount fiatAccount = new MiddlewareFiatAccount(id);
        ((MiddlewareFiatAccount) fiatAccount).setFiatCurrency(fiatCurrency);

        ((MiddlewareFiatAccount) fiatAccount).persistToMedia();
        this.fiatAccounts.put (id, fiatAccount);
        
        return fiatAccount;
    }

    public CryptoAccount createCryptoAccount (CryptoCurrency cryptoCurrency){

        UUID id = UUID.randomUUID();

        CryptoAccount cryptoAccount = new MiddlewareCryptoAccount(id);
        ((MiddlewareCryptoAccount) cryptoAccount).setCryptoCurrency(cryptoCurrency);

        ((MiddlewareCryptoAccount) cryptoAccount).persistToMedia();
        this.cryptoAccounts.put (id, cryptoAccount);
        
        return cryptoAccount;
    }

    
    
    
    
    
    
    
    public void deleteFiatAccount(int index){
        this.fiatAccounts.remove(index);
    }

    public void deleteCryptoAccount(int index){
        this.cryptoAccounts.remove(index);
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
