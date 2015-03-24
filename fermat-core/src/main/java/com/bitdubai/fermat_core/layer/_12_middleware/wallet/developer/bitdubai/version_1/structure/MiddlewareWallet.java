package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.blockchain_info.exceptions.CantStartBlockchainInfoWallet;
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

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
           try{

            this.database = this.pluginDatabaseSystem.createDatabase(this.ownerId, this.walletId.toString());

            /**
             * Next, I will add the needed tables.
             */
            try {

                DatabaseTableFactory table;

                /**
                 * First the fiat accounts table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.ownerId, DatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME);
                table.addColumn(DatabaseConstants.FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(DatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.FIAT_ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
                table.addColumn(DatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 3);

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
                table = ((DatabaseFactory) this.database).newTableFactory(this.ownerId, DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME);
                table.addColumn(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
                table.addColumn(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 3);

                try {
                    ((DatabaseFactory) this.database).createTable(this.ownerId, table);
                }
                catch (CantCreateTableException cantCreateTableException) {
                    System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                    cantCreateTableException.printStackTrace();
                    throw new CantInitializeWalletException();
                }

                /**
                 * Then the transfers table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.ownerId, DatabaseConstants.TRANSFERS_TABLE_NAME);
                table.addColumn(DatabaseConstants.TRANSFERS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.TRANSFERS_TABLE_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 100);
                table.addColumn(DatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_FROM_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.TRANSFERS_TABLE_AMOUNT_FROM_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_TO_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.TRANSFERS_TABLE_AMOUNT_TO_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.TRANSFERS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

                try {
                    ((DatabaseFactory) this.database).createTable(this.ownerId, table);
                }
                catch (CantCreateTableException cantCreateTableException) {
                    System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                    cantCreateTableException.printStackTrace();
                    throw new CantInitializeWalletException();
                }

                /**
                 * Then the value chunks table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.ownerId, DatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
                table.addColumn(DatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
                table.addColumn(DatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
                table.addColumn(DatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

                try {
                    ((DatabaseFactory) this.database).createTable(this.ownerId, table);
                }
                catch (CantCreateTableException cantCreateTableException) {
                    System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                    cantCreateTableException.printStackTrace();
                    throw new CantInitializeWalletException();
                }
                
                /**
                 * Then the debits table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.ownerId, DatabaseConstants.DEBITS_TABLE_NAME);
                table.addColumn(DatabaseConstants.DEBITS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.DEBITS_TABLE_ID_FIAT_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.DEBITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.DEBITS_TABLE_ID_CRYPTO_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.DEBITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.DEBITS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

                try {
                    ((DatabaseFactory) this.database).createTable(this.ownerId, table);
                }
                catch (CantCreateTableException cantCreateTableException) {
                    System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                    cantCreateTableException.printStackTrace();
                    throw new CantInitializeWalletException();
                }
                
                /**
                 * Then the credits table.
                 */
                table = ((DatabaseFactory) this.database).newTableFactory(this.ownerId, DatabaseConstants.CREDITS_TABLE_NAME);
                table.addColumn(DatabaseConstants.CREDITS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.CREDITS_TABLE_ID_FIAT_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.CREDITS_TABLE_ID_CRYPTO_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.CREDITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
                table.addColumn(DatabaseConstants.CREDITS_TABLE_ID_VALUE_CHUNK_COLUMN_NAME, DatabaseDataType.STRING, 36);
                table.addColumn(DatabaseConstants.CREDITS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

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
        table = this.database.getTable(DatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME);
        table.loadToMemory();

        /**
         * Will go through the records getting each fiat account.
         */
        for (DatabaseTableRecord record : table.getRecords()) {
            
            UUID accountId;
            accountId = record.getUUIDValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME);
            
            FiatAccount fiatAccount;
            fiatAccount = new MiddlewareFiatAccount(accountId);

            fiatAccount.setLabel(record.getStringValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME));
            fiatAccount.setName(record.getStringValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME));
            
            ((MiddlewareFiatAccount) fiatAccount).setFiatCurrency(FiatCurrency.getByCode(record.getStringValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME)));
            ((MiddlewareFiatAccount) fiatAccount).setBalance(record.getlongValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME));
            ((MiddlewareFiatAccount) fiatAccount).setStatus(AccountStatus.getByCode(record.getStringValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME)));
            
            ((MiddlewareFiatAccount) fiatAccount).setTable(table);
            ((MiddlewareFiatAccount) fiatAccount).setRecord(record);
            
            fiatAccounts.put(accountId, fiatAccount);
        }

        /**
         * Secondly the crypto accounts.
         */
        table = this.database.getTable(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME);
        table.loadToMemory();

        /**
         * Will go through the records getting each crypto account.
         */
        for (DatabaseTableRecord record : table.getRecords()) {

            UUID accountId;
            accountId = record.getUUIDValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME);

            CryptoAccount cryptoAccount;
            cryptoAccount = new MiddlewareCryptoAccount(accountId);

            cryptoAccount.setLabel(record.getStringValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_NAME));
            cryptoAccount.setName(record.getStringValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_NAME));

            ((MiddlewareCryptoAccount) cryptoAccount).setCryptoCurrency(CryptoCurrency.getByCode(record.getStringValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME)));
            ((MiddlewareCryptoAccount) cryptoAccount).setBalance(record.getlongValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME));
            ((MiddlewareCryptoAccount) cryptoAccount).setStatus(AccountStatus.getByCode(record.getStringValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_STATUS_COLUMN_NAME)));

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
    
    public FiatAccount createFiatAccount (FiatCurrency fiatCurrency){

        /**
         * First I get a new id for the new account.
         */
        UUID id = UUID.randomUUID();

        /**
         * Then I add the new record to the database.
         */
        DatabaseTable table;
        table = this.database.getTable(DatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME);

        DatabaseTableRecord newRecord;
        newRecord = table.getEmptyRecord();
        
        newRecord.setUUIDValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_ID_COLUMN_NAME, id);
        newRecord.setStringValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, DatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_NAME, DatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_DEFAULT_VALUE);
        newRecord.setlongValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME, fiatCurrency.getCode());
        newRecord.setStringValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, DatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_DEFAULT_VALUE);

        table.insertRecord(newRecord);
        
        /**
         * Finally I update the information in memory.
         */
        FiatAccount fiatAccount = new MiddlewareFiatAccount(id);
        
        ((MiddlewareFiatAccount) fiatAccount).setFiatCurrency(fiatCurrency);
        ((MiddlewareFiatAccount) fiatAccount).setBalance(DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_DEFAULT_VALUE);
        ((MiddlewareFiatAccount) fiatAccount).setStatus(AccountStatus.getByCode(DatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_DEFAULT_VALUE));

        fiatAccount.setLabel(DatabaseConstants.FIAT_ACCOUNTS_TABLE_LABEL_COLUMN_DEFAULT_VALUE);
        fiatAccount.setName(DatabaseConstants.FIAT_ACCOUNTS_TABLE_NAME_COLUMN_DEFAULT_VALUE);


        ((MiddlewareFiatAccount) fiatAccount).setTable(table);
        ((MiddlewareFiatAccount) fiatAccount).setRecord(newRecord);

        this.fiatAccounts.put (id, fiatAccount);

        return fiatAccount;
    }

    public CryptoAccount createCryptoAccount (CryptoCurrency cryptoCurrency){
  
        /**
         * First I get a new id for the new account.
         */
        UUID id = UUID.randomUUID();

        /**
         * Then I add the new record to the database.
         */
        DatabaseTable table;
        table = this.database.getTable(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME);

        DatabaseTableRecord newRecord;
        newRecord = table.getEmptyRecord();

        newRecord.setUUIDValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_ID_COLUMN_NAME, id);
        newRecord.setStringValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_NAME, DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_NAME, DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_DEFAULT_VALUE);
        newRecord.setlongValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME, DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, cryptoCurrency.getCode());
        newRecord.setStringValue(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_STATUS_COLUMN_NAME, DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_STATUS_COLUMN_DEFAULT_VALUE);

        table.insertRecord(newRecord);

        /**
         * Finally I update the information in memory.
         */
        CryptoAccount cryptoAccount = new MiddlewareCryptoAccount(id);
        
        ((MiddlewareCryptoAccount) cryptoAccount).setCryptoCurrency(cryptoCurrency);
        ((MiddlewareCryptoAccount) cryptoAccount).setBalance(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_BALANCE_COLUMN_DEFAULT_VALUE);
        ((MiddlewareCryptoAccount) cryptoAccount).setStatus(AccountStatus.getByCode(DatabaseConstants.FIAT_ACCOUNTS_TABLE_STATUS_COLUMN_DEFAULT_VALUE));

        cryptoAccount.setLabel(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_LABEL_COLUMN_DEFAULT_VALUE);
        cryptoAccount.setName(DatabaseConstants.CRYPTO_ACCOUNTS_TABLE_NAME_COLUMN_DEFAULT_VALUE);

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

        /**
         * First I will check the accounts received belongs to this wallet.
         */
        FiatAccount inMemoryAccountFrom;
        inMemoryAccountFrom = fiatAccounts.get(((MiddlewareFiatAccount) fiatAccountFrom).getId());

        if ( inMemoryAccountFrom == null ) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_FROM_DOES_NOT_BELONG_TO_THIS_WALLET);
        }

        FiatAccount inMemoryAccountTo;
        inMemoryAccountTo = fiatAccounts.get(((MiddlewareFiatAccount) fiatAccountTo).getId());

        if ( inMemoryAccountTo == null ) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_TO_DOES_NOT_BELONG_TO_THIS_WALLET);
        }
        
        /**
         * Then I will check if the accounts are not already locked.
         */
        if (inMemoryAccountFrom.getStatus() == AccountStatus.LOCKED) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_FROM_ALREADY_LOCKED);
        }

        if (inMemoryAccountTo.getStatus() == AccountStatus.LOCKED) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_TO_ALREADY_LOCKED);
        }

        /**
         * Then I will check if the accounts are not open.
         */
        if (inMemoryAccountFrom.getStatus() != AccountStatus.OPEN) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_FROM_NOT_OPEN);
        }

        if (inMemoryAccountTo.getStatus() != AccountStatus.OPEN) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_TO_NOT_OPEN);
        }

        /**
         * Then I will lock the accounts involved in the transaction.
         */

        AccountStatus currentAccountFromStatus = inMemoryAccountFrom.getStatus();
        AccountStatus currentAccountToStatus = inMemoryAccountTo.getStatus();

        ((MiddlewareFiatAccount) inMemoryAccountFrom).setStatus(AccountStatus.LOCKED);
        ((MiddlewareFiatAccount) inMemoryAccountTo).setStatus(AccountStatus.LOCKED);

        /**
         * Then I validate that there are enough funds on the account to be debited.
         */

        if ( inMemoryAccountFrom.getBalance() < amountFrom) {

            ((MiddlewareFiatAccount) inMemoryAccountFrom).setStatus(currentAccountFromStatus);
            ((MiddlewareFiatAccount) inMemoryAccountTo).setStatus(currentAccountToStatus);
            
            throw new TransferFailedException(TransferFailedReasons.NOT_ENOUGH_FUNDS);
        }
        
        /**
         * Prepare everything for the execution of the database transaction.
         */
        long accountFromNewBalance = inMemoryAccountFrom.getBalance() - amountFrom;
        long accountToNewBalance = inMemoryAccountTo.getBalance() + amountTo;
          
        DatabaseTable accountFromTable = ((MiddlewareFiatAccount) inMemoryAccountFrom).getTable();
        DatabaseTable accountToTable = ((MiddlewareFiatAccount) inMemoryAccountTo).getTable();
        
        DatabaseTableRecord accountFromRecord = ((MiddlewareFiatAccount) inMemoryAccountFrom).getRecord();
        DatabaseTableRecord accountToRecord = ((MiddlewareFiatAccount) inMemoryAccountTo).getRecord();

        accountFromRecord.setlongValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME,accountFromNewBalance);
        accountToRecord.setlongValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME,accountToNewBalance);

        /**
         * Here I create the transfer record for historical purposes.
         */
        DatabaseTable transfersTable = database.getTable(DatabaseConstants.TRANSFERS_TABLE_NAME);
        DatabaseTableRecord transferRecord = transfersTable.getEmptyRecord();
        
        transferRecord.setUUIDValue(DatabaseConstants.TRANSFERS_TABLE_ID_COLUMN_NAME , UUID.randomUUID());
        transferRecord.setStringValue(DatabaseConstants.TRANSFERS_TABLE_MEMO_COLUMN_NAME, memo);
        transferRecord.setUUIDValue(DatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_FROM_COLUMN_NAME, ((MiddlewareFiatAccount) inMemoryAccountFrom).getId());
        transferRecord.setlongValue(DatabaseConstants.TRANSFERS_TABLE_AMOUNT_FROM_COLUMN_NAME, amountFrom);
        transferRecord.setUUIDValue(DatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_TO_COLUMN_NAME, ((MiddlewareFiatAccount) inMemoryAccountTo).getId());
        transferRecord.setlongValue(DatabaseConstants.TRANSFERS_TABLE_AMOUNT_TO_COLUMN_NAME, amountTo);
        transferRecord.setlongValue(DatabaseConstants.TRANSFERS_TABLE_TIME_STAMP_COLUMN_NAME, System.currentTimeMillis() / 1000L);
        
        /**
         * Then I execute the database transaction.
         */
        DatabaseTransaction databaseTransaction = database.newTransaction();
        
        databaseTransaction.addRecordToUpdate(accountFromTable, accountFromRecord);
        databaseTransaction.addRecordToUpdate(accountToTable, accountToRecord);
        databaseTransaction.addRecordToInsert(transfersTable, transferRecord);
        
        try {
            database.executeTransaction(databaseTransaction);
        }
        catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            ((MiddlewareFiatAccount) inMemoryAccountFrom).setStatus(currentAccountFromStatus);
            ((MiddlewareFiatAccount) inMemoryAccountTo).setStatus(currentAccountToStatus);
            
            /**
             * I can not solve this situation.
             */
            System.err.println("DatabaseTransactionFailedException: " + databaseTransactionFailedException.getMessage());
            databaseTransactionFailedException.printStackTrace();
            throw new TransferFailedException(TransferFailedReasons.CANT_SAVE_TRANSACTION);
        }
        
        /**
         * Then I update the accounts in memory.
         */
        ((MiddlewareFiatAccount) inMemoryAccountFrom).setBalance(accountFromNewBalance);
        ((MiddlewareFiatAccount) inMemoryAccountTo).setBalance(accountToNewBalance);

        /**
         * Finally I release the lock.
         */
        ((MiddlewareFiatAccount) inMemoryAccountFrom).setStatus(currentAccountFromStatus);
        ((MiddlewareFiatAccount) inMemoryAccountTo).setStatus(currentAccountToStatus);
    }

    @Override
    public void debit(FiatAccount fiatAccount, long fiatAmount, CryptoAccount cryptoAccount, long cryptoAmount) throws CreditFailedException {

    }

    /**
     * A credit transaction represents an amount of crypto currency received. What we actually register is the crypto 
     * currency together with the fiat currency that represents at the moment of reception.
     * * *
     */
    @Override
    public void credit(FiatAccount fiatAccount, long fiatAmount, CryptoAccount cryptoAccount, long cryptoAmount) throws CreditFailedException {

        /**
         * First I will check the accounts received belongs to this wallet.
         */
        FiatAccount inMemoryFiatAccount;
        inMemoryFiatAccount = fiatAccounts.get(((MiddlewareFiatAccount) fiatAccount).getId());

        if ( inMemoryFiatAccount == null ) {
            throw new CreditFailedException(CreditFailedReasons.FIAT_ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET);
        }

        FiatAccount inMemoryCryptoAccount;
        inMemoryCryptoAccount = fiatAccounts.get(((MiddlewareFiatAccount) cryptoAccount).getId());

        if ( inMemoryCryptoAccount == null ) {
            throw new CreditFailedException(CreditFailedReasons.CRYPTO_ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET);
        }

        /**
         * Then I will check if the accounts are not already locked.
         */
        if (inMemoryFiatAccount.getStatus() == AccountStatus.LOCKED) {
            throw new CreditFailedException(CreditFailedReasons.FIAT_ACCOUNT_ALREADY_LOCKED);
        }

        if (inMemoryCryptoAccount.getStatus() == AccountStatus.LOCKED) {
            throw new CreditFailedException(CreditFailedReasons.CRYPTO_ACCOUNT_ALREADY_LOCKED);
        }

        /**
         * Then I will check if the accounts are not open.
         */
        if (inMemoryFiatAccount.getStatus() != AccountStatus.OPEN) {
            throw new CreditFailedException(CreditFailedReasons.FIAT_ACCOUNT_NOT_OPEN);
        }

        if (inMemoryCryptoAccount.getStatus() != AccountStatus.OPEN) {
            throw new CreditFailedException(CreditFailedReasons.CRYPTO_ACCOUNT_NOT_OPEN);
        }

        /**
         * Then I will lock the accounts involved in the transaction.
         */

        AccountStatus currentFiatAccountStatus = inMemoryFiatAccount.getStatus();
        AccountStatus currentCryptoAccountStatus = inMemoryCryptoAccount.getStatus();

        ((MiddlewareFiatAccount) inMemoryFiatAccount).setStatus(AccountStatus.LOCKED);
        ((MiddlewareCryptoAccount) inMemoryCryptoAccount).setStatus(AccountStatus.LOCKED);
        
        /**
         * Prepare everything for the execution of the database transaction.
         */
        long fiatAccountNewBalance = inMemoryFiatAccount.getBalance() + fiatAmount;
        long cryptoAccountNewBalance = inMemoryCryptoAccount.getBalance() + cryptoAmount;

        DatabaseTable fiatAccountTable = ((MiddlewareFiatAccount) inMemoryFiatAccount).getTable();
        DatabaseTable cryptoAccountTable = ((MiddlewareFiatAccount) inMemoryCryptoAccount).getTable();

        DatabaseTableRecord fiatAccountRecord = ((MiddlewareFiatAccount) inMemoryFiatAccount).getRecord();
        DatabaseTableRecord cryptoAccountRecord = ((MiddlewareFiatAccount) inMemoryCryptoAccount).getRecord();

        fiatAccountRecord.setlongValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME,fiatAccountNewBalance);
        cryptoAccountRecord.setlongValue(DatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME,cryptoAccountNewBalance);

        /**
         * I create the value chunk record.
         */
        DatabaseTable valueChunksTable = database.getTable(DatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
        DatabaseTableRecord valueChunkRecord = valueChunksTable.getEmptyRecord();

        UUID valueChunkRecordId = UUID.randomUUID();

        valueChunkRecord.setUUIDValue(DatabaseConstants.VALUE_CHUNKS_TABLE_NAME , valueChunkRecordId);
        valueChunkRecord.setStringValue(DatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, fiatAccount.getFiatCurrency().getCode());
        valueChunkRecord.setlongValue(DatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);
        valueChunkRecord.setStringValue(DatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAccount.getCryptoCurrency().getCode());
        valueChunkRecord.setlongValue(DatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);

        /**
         * Here I create the credit record for historical purposes.
         */
        DatabaseTable creditsTable = database.getTable(DatabaseConstants.DEBITS_TABLE_NAME);
        DatabaseTableRecord creditRecord = creditsTable.getEmptyRecord();

        creditRecord.setUUIDValue(DatabaseConstants.CREDITS_TABLE_ID_COLUMN_NAME , UUID.randomUUID());
        creditRecord.setUUIDValue(DatabaseConstants.CREDITS_TABLE_ID_FIAT_ACCOUNT_COLUMN_NAME, ((MiddlewareFiatAccount) inMemoryFiatAccount).getId());
        creditRecord.setlongValue(DatabaseConstants.CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);
        creditRecord.setUUIDValue(DatabaseConstants.CREDITS_TABLE_ID_CRYPTO_ACCOUNT_COLUMN_NAME, ((MiddlewareFiatAccount) inMemoryCryptoAccount).getId());
        creditRecord.setlongValue(DatabaseConstants.CREDITS_TABLE_ID_CRYPTO_ACCOUNT_COLUMN_NAME, cryptoAmount);
        creditRecord.setUUIDValue(DatabaseConstants.CREDITS_TABLE_ID_VALUE_CHUNK_COLUMN_NAME, valueChunkRecordId);
        creditRecord.setlongValue(DatabaseConstants.CREDITS_TABLE_TIME_STAMP_COLUMN_NAME,  System.currentTimeMillis() / 1000L);
        
        /**
         * Then I execute the database transaction.
         */
        DatabaseTransaction databaseTransaction = database.newTransaction();

        databaseTransaction.addRecordToUpdate(fiatAccountTable, fiatAccountRecord);
        databaseTransaction.addRecordToUpdate(cryptoAccountTable, cryptoAccountRecord);
        databaseTransaction.addRecordToInsert(valueChunksTable, valueChunkRecord);
        databaseTransaction.addRecordToInsert(creditsTable, creditRecord);

        try {
            database.executeTransaction(databaseTransaction);
        }
        catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            ((MiddlewareFiatAccount) inMemoryFiatAccount).setStatus(currentFiatAccountStatus);
            ((MiddlewareFiatAccount) inMemoryCryptoAccount).setStatus(currentCryptoAccountStatus);

            /**
             * I can not solve this situation.
             */
            System.err.println("DatabaseTransactionFailedException: " + databaseTransactionFailedException.getMessage());
            databaseTransactionFailedException.printStackTrace();
            throw new CreditFailedException(CreditFailedReasons.CANT_SAVE_TRANSACTION);
        }

        /**
         * Then I update the accounts in memory.
         */
        ((MiddlewareFiatAccount) inMemoryFiatAccount).setBalance(fiatAccountNewBalance);
        ((MiddlewareFiatAccount) inMemoryCryptoAccount).setBalance(cryptoAccountNewBalance);

        /**
         * Finally I release the lock.
         */
        ((MiddlewareFiatAccount) inMemoryFiatAccount).setStatus(currentFiatAccountStatus);
        ((MiddlewareFiatAccount) inMemoryCryptoAccount).setStatus(currentCryptoAccountStatus);

    }
    

}
