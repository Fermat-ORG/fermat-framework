package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.*;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CreditFailedException;
import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseTransactionFailedException;

import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 3/24/15.
 */
class MiddlewareCreditTransaction {

    private Database database;
    private Map<UUID, FiatAccount> fiatAccounts;
    private Map<UUID,CryptoAccount> cryptoAccounts;

    void setDatabase(Database database) {
        this.database = database;
    }

    void setFiatAccounts(Map<UUID, FiatAccount> fiatAccounts) {
        this.fiatAccounts = fiatAccounts;
    }

    public void setCryptoAccounts(Map<UUID, CryptoAccount> cryptoAccounts) {
        this.cryptoAccounts = cryptoAccounts;
    }


    void credit(FiatAccount fiatAccount, long fiatAmount, CryptoAccount cryptoAccount, long cryptoAmount) throws CreditFailedException {

        long unixTime = System.currentTimeMillis() / 1000L;
        
        
        /**
         * First I will check the accounts received belongs to this wallet.
         */
        FiatAccount inMemoryFiatAccount;
        inMemoryFiatAccount = fiatAccounts.get(((MiddlewareFiatAccount) fiatAccount).getId());

        if ( inMemoryFiatAccount == null ) {
            throw new CreditFailedException(CreditFailedReasons.FIAT_ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET);
        }

        CryptoAccount inMemoryCryptoAccount;
        inMemoryCryptoAccount = cryptoAccounts.get(((MiddlewareCryptoAccount) cryptoAccount).getId());

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
        DatabaseTableRecord cryptoAccountRecord = ((MiddlewareCryptoAccount) inMemoryCryptoAccount).getRecord();

        fiatAccountRecord.setlongValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME,fiatAccountNewBalance);
        cryptoAccountRecord.setlongValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME,cryptoAccountNewBalance);

        /**
         * I create the value chunk record.
         */
        DatabaseTable valueChunksTable = database.getTable(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
        DatabaseTableRecord valueChunkRecord = valueChunksTable.getEmptyRecord();

        UUID valueChunkRecordId = UUID.randomUUID();

        valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME , valueChunkRecordId);
        valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode());
        valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, UUID.fromString(""));
        valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, fiatAccount.getFiatCurrency().getCode());
        valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);
        valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAccount.getCryptoCurrency().getCode());
        valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_FIAT_ACCOUNT_COLUMN_NAME , ((MiddlewareFiatAccount) inMemoryFiatAccount).getId());
        valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CRYPTO_ACCOUNT_COLUMN_NAME , ((MiddlewareCryptoAccount) inMemoryCryptoAccount).getId());
        valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);
        
        /**
         * Here I create the credit record for historical purposes.
         */
        DatabaseTable creditsTable = database.getTable(MiddlewareDatabaseConstants.DEBITS_TABLE_NAME);
        DatabaseTableRecord creditRecord = creditsTable.getEmptyRecord();

        creditRecord.setUUIDValue(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_COLUMN_NAME , UUID.randomUUID());
        creditRecord.setUUIDValue(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_FIAT_ACCOUNT_COLUMN_NAME, ((MiddlewareFiatAccount) inMemoryFiatAccount).getId());
        creditRecord.setlongValue(MiddlewareDatabaseConstants.CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);
        creditRecord.setUUIDValue(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_CRYPTO_ACCOUNT_COLUMN_NAME, ((MiddlewareFiatAccount) inMemoryCryptoAccount).getId());
        creditRecord.setlongValue(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_CRYPTO_ACCOUNT_COLUMN_NAME, cryptoAmount);
        creditRecord.setUUIDValue(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_VALUE_CHUNK_COLUMN_NAME, valueChunkRecordId);
        creditRecord.setlongValue(MiddlewareDatabaseConstants.CREDITS_TABLE_TIME_STAMP_COLUMN_NAME,  unixTime);

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
            ((MiddlewareCryptoAccount) inMemoryCryptoAccount).setStatus(currentCryptoAccountStatus);

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
        ((MiddlewareCryptoAccount) inMemoryCryptoAccount).setBalance(cryptoAccountNewBalance);

        /**
         * Finally I release the lock.
         */
        ((MiddlewareFiatAccount) inMemoryFiatAccount).setStatus(currentFiatAccountStatus);
        ((MiddlewareCryptoAccount) inMemoryCryptoAccount).setStatus(currentCryptoAccountStatus);

    }
    
}
