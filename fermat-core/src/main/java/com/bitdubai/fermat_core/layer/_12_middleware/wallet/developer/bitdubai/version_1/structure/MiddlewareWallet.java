package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.*;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.*;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.*;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFactory;
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
    private PluginDatabaseSystem pluginDatabaseSystem;
    
    /**
     * Wallet Interface member variables.
     */
    private UUID walletId;
    private UUID ownerId;
    private Database database;

    private Map<UUID, FiatAccount> fiatAccounts = new HashMap<>();
    private Map<UUID,CryptoAccount> cryptoAccounts = new HashMap<>();


    
    /**
     * Constructor.
     */
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
            
            MiddlewareDatabaseFactory databaseFactory = new MiddlewareDatabaseFactory();
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
     * The Start method loads the information of the wallet from the Database, and create an in memory structure.
     */
    public void loadToMemory() throws CantLoadWalletException {

        /**
         * Will try to open the wallets' database.
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
            throw new CantLoadWalletException();
        }

        DatabaseTable table;
        
        /**
         * Now I will load the information into a memory structure. Firstly the fiat accounts.
         */
        table = this.database.getTable(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME);

        try {
            table.loadToMemory();
        }
        catch (CantNotLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantInsertRecord: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantLoadWalletException();
        }
        

        /**
         * Will go through the records getting each fiat account.
         */
        for (DatabaseTableRecord record : table.getRecords()) {
            
            UUID accountId;
            accountId = record.getUUIDValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME);
            
            FiatAccount fiatAccount;
            fiatAccount = new MiddlewareFiatAccount(accountId);

            ((MiddlewareFiatAccount) fiatAccount).setTable(table);
            ((MiddlewareFiatAccount) fiatAccount).setRecord(record);
            
            fiatAccounts.put(accountId, fiatAccount);
        }

        /**
         * Secondly the crypto accounts.
         */
        table = this.database.getTable(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME);

        try {
            table.loadToMemory();
        }
        catch (CantNotLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantInsertRecord: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantLoadWalletException();
        }

        /**
         * Will go through the records getting each crypto account.
         */
        for (DatabaseTableRecord record : table.getRecords()) {

            UUID accountId;
            accountId = record.getUUIDValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME);

            CryptoAccount cryptoAccount;
            cryptoAccount = new MiddlewareCryptoAccount(accountId);

            ((MiddlewareCryptoAccount) cryptoAccount).setTable(table);
            ((MiddlewareCryptoAccount) cryptoAccount).setRecord(record);

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
    
    public FiatAccount createFiatAccount (FiatCurrency fiatCurrency) throws CantCreateAccountException {

        /**
         * First I get a new id for the new account.
         */
        UUID id = UUID.randomUUID();

        /**
         * Then I add the new record to the database.
         */
        DatabaseTable table;
        table = this.database.getTable(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME);

        DatabaseTableRecord newRecord;
        newRecord = table.getEmptyRecord();
        
        newRecord.setUUIDValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME, id);
        newRecord.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME, MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_DEFAULT_VALUE);
        newRecord.setlongValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME, fiatCurrency.getCode());
        newRecord.setStringValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_DEFAULT_VALUE);

        try {
            table.insertRecord(newRecord);
        }
        catch (CantNotInsertRecord cantInsertRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantInsertRecord: " + cantInsertRecord.getMessage());
            cantInsertRecord.printStackTrace();
            throw new CantCreateAccountException();
        }
        
        /**
         * Finally I update the information in memory.
         */
        FiatAccount fiatAccount = new MiddlewareFiatAccount(id);

        ((MiddlewareFiatAccount) fiatAccount).setTable(table);
        ((MiddlewareFiatAccount) fiatAccount).setRecord(newRecord);

        this.fiatAccounts.put (id, fiatAccount);

        return fiatAccount;
    }

    public CryptoAccount createCryptoAccount (CryptoCurrency cryptoCurrency) throws CantCreateAccountException{
  
        /**
         * First I get a new id for the new account.
         */
        UUID id = UUID.randomUUID();

        /**
         * Then I add the new record to the database.
         */
        DatabaseTable table;
        table = this.database.getTable(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME);

        DatabaseTableRecord newRecord;
        newRecord = table.getEmptyRecord();

        newRecord.setUUIDValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME, id);
        newRecord.setStringValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_NAME, MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_DEFAULT_VALUE);
        newRecord.setlongValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, cryptoCurrency.getCode());
        newRecord.setStringValue(MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, MiddlewareDatabaseConstants.CRYPTO_ACCOUNTS_TABLE_STATUS_COLUMN_DEFAULT_VALUE);

        try {
            table.insertRecord(newRecord);
        }
        catch (CantNotInsertRecord cantInsertRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantInsertRecord: " + cantInsertRecord.getMessage());
            cantInsertRecord.printStackTrace();
            throw new CantCreateAccountException();
        }

        /**
         * Finally I update the information in memory.
         */
        CryptoAccount cryptoAccount = new MiddlewareCryptoAccount(id);

        ((MiddlewareCryptoAccount) cryptoAccount).setTable(table);
        ((MiddlewareCryptoAccount) cryptoAccount).setRecord(newRecord);

        this.cryptoAccounts.put (id, cryptoAccount);

        return cryptoAccount;
    }


    /**
     * Following there comes the transactional functionality of the wallet.
     */
    
    @Override
    public void transfer(FiatAccount fiatAccountFrom, FiatAccount fiatAccountTo, long amountFrom, long amountTo, String memo) throws TransferFailedException {

        MiddlewareTransferTransaction transferTransaction = new MiddlewareTransferTransaction();

        transferTransaction.setDatabase(this.database);
        transferTransaction.setFiatAccounts(this.fiatAccounts);

        transferTransaction.transfer(fiatAccountFrom, fiatAccountTo, amountFrom, amountTo, memo);
    }


    /**
     * A debit transaction represents an amount of crypto currency going out of the wallet. What we actually register is the crypto 
     * currency together with the fiat currency that represents at the moment the transaction was done.
     * * *
     */
    @Override
    public void debit(FiatAccount fiatAccount, long fiatAmount, CryptoAccount cryptoAccount, long cryptoAmount) throws DebitFailedException {

        MiddlewareDebitTransaction debitTransaction = new MiddlewareDebitTransaction();

        debitTransaction.setDatabase(this.database);
        debitTransaction.setFiatAccounts(this.fiatAccounts);
        debitTransaction.setCryptoAccounts(this.cryptoAccounts);

        debitTransaction.debit(fiatAccount, fiatAmount, cryptoAccount, cryptoAmount);
        
    }

    /**
     * A credit transaction represents an amount of crypto currency received. What we actually register is the crypto 
     * currency together with the fiat currency that represents at the moment the transaction was done.
     * * *
     */
    @Override
    public void credit(FiatAccount fiatAccount, long fiatAmount, CryptoAccount cryptoAccount, long cryptoAmount) throws CreditFailedException {

        MiddlewareCreditTransaction creditTransaction = new MiddlewareCreditTransaction();

        creditTransaction.setDatabase(this.database);
        creditTransaction.setFiatAccounts(this.fiatAccounts);
        creditTransaction.setCryptoAccounts(this.cryptoAccounts);

        creditTransaction.credit(fiatAccount, fiatAmount, cryptoAccount, cryptoAmount);

    }
    

}
