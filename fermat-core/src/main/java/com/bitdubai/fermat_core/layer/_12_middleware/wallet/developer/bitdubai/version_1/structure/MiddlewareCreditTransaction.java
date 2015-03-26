package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.*;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CreditFailedException;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.TransferFailedException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
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
    private Map<UUID, Account> fiatAccounts;

    void setDatabase(Database database) {
        this.database = database;
    }

    void setFiatAccounts(Map<UUID, Account> fiatAccounts) {
        this.fiatAccounts = fiatAccounts;
    }

    void credit(Account account, long fiatAmount, CryptoCurrency cryptoCurrency, long cryptoAmount) throws CreditFailedException {

        long unixTime = System.currentTimeMillis() / 1000L;


        /**
         * Both amounts must be grater than zero.
         */
        
        if (fiatAmount <= 0) {
            throw new CreditFailedException(CreditFailedReasons.AMOUNT_MUST_BE_OVER_ZERO);
        }

        if (cryptoAmount <= 0) {
            throw new CreditFailedException(CreditFailedReasons.CRYPTO_AMOUNT_MUST_BE_OVER_ZERO);
        }

        /**
         * I will check the account received belongs to this wallet.
         */
        Account inMemoryAccount;
        inMemoryAccount = fiatAccounts.get(((MiddlewareAccount) account).getId());

        if ( inMemoryAccount == null ) {
            throw new CreditFailedException(CreditFailedReasons.ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET);
        }

        /**
         * Then I will check if the account is not locked.
         */
        if (((MiddlewareAccount) inMemoryAccount).getLockStatus() == AccountLockStatus.LOCKED) {
            throw new CreditFailedException(CreditFailedReasons.ACCOUNT_ALREADY_LOCKED);
        }

        /**
         * Then I will check if the account is not open.
         */
        if (inMemoryAccount.getStatus() != AccountStatus.OPEN) {
            throw new CreditFailedException(CreditFailedReasons.ACCOUNT_NOT_OPEN);
        }

         /**
         * I create the value chunk record.
         */
        DatabaseTable valueChunksTable = database.getTable(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
        DatabaseTableRecord valueChunkRecord = valueChunksTable.getEmptyRecord();

        UUID valueChunkRecordId = UUID.randomUUID();

        valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, valueChunkRecordId);
        valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode());
        valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, new UUID(0L, 0L));
        valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, account.getFiatCurrency().getCode());
        valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);
        valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, cryptoCurrency.getCode());
        valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME , ((MiddlewareAccount) inMemoryAccount).getId());
        valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);
        
        /**
         * Here I create the credit record for historical purposes.
         */
        DatabaseTable creditsTable = database.getTable(MiddlewareDatabaseConstants.DEBITS_TABLE_NAME);
        DatabaseTableRecord creditRecord = creditsTable.getEmptyRecord();

        creditRecord.setUUIDValue(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_COLUMN_NAME , UUID.randomUUID());
        creditRecord.setUUIDValue(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_ACCOUNT_COLUMN_NAME, ((MiddlewareAccount) inMemoryAccount).getId());
        creditRecord.setlongValue(MiddlewareDatabaseConstants.CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);
        creditRecord.setlongValue(MiddlewareDatabaseConstants.CREDITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        creditRecord.setUUIDValue(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_VALUE_CHUNK_COLUMN_NAME, valueChunkRecordId);
        creditRecord.setlongValue(MiddlewareDatabaseConstants.CREDITS_TABLE_TIME_STAMP_COLUMN_NAME,  unixTime);

        /**
         * Then I execute the database transaction.
         */
        DatabaseTransaction databaseTransaction = database.newTransaction();
        
        databaseTransaction.addRecordToInsert(valueChunksTable, valueChunkRecord);
        databaseTransaction.addRecordToInsert(creditsTable, creditRecord);

        try {
            database.executeTransaction(databaseTransaction);
        }
        catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

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

        ((MiddlewareAccount) inMemoryAccount).updateBalance();

    }
    
}
