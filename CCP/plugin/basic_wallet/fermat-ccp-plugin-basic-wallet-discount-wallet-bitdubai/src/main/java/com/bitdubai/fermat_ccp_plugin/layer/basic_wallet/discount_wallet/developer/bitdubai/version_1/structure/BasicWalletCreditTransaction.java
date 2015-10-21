package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.CreditFailedReasons;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.CryptoValueChunkStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CreditFailedException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;

import java.util.UUID;

/**
 * Created by ciencias on 3/24/15.
 */
class BasicWalletCreditTransaction {

    private Database database;

    void setDatabase(Database database) {
        this.database = database;
    }

    /*
     * The credit method will take as parameters the fiat and corresponding crypto amount of
     * money we want to deposit in the account.
     * We just need to add to the database the information about this action.
     * We won't register the type of fiat and crypto currency because this is
     * unequivocally established by the wallet information.
     */
    void credit(long fiatAmount, long cryptoAmount) throws CreditFailedException {

        long unixTime = System.currentTimeMillis() / 1000L;

        /**
         * Some needed constants.
         */
        final UUID emptyUUID = new UUID(0L, 0L);

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
         * Here I create the credit record for historical purposes.
         */
        DatabaseTable creditsTable = database.getTable(BasicWalletDatabaseConstants.CREDITS_TABLE_NAME);
        DatabaseTableRecord creditRecord = creditsTable.getEmptyRecord();

        UUID creditRecordId = UUID.randomUUID();
        
        creditRecord.setUUIDValue(BasicWalletDatabaseConstants.CREDITS_TABLE_ID_COLUMN_NAME , creditRecordId);
        creditRecord.setLongValue(BasicWalletDatabaseConstants.CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);
        creditRecord.setLongValue(BasicWalletDatabaseConstants.CREDITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        creditRecord.setLongValue(BasicWalletDatabaseConstants.CREDITS_TABLE_TIME_STAMP_COLUMN_NAME,  unixTime);

        /**
         * I create the value chunk record.
         */
        DatabaseTable valueChunksTable = database.getTable(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
        DatabaseTableRecord valueChunkRecord = valueChunksTable.getEmptyRecord();

        UUID valueChunkRecordId = UUID.randomUUID();

        valueChunkRecord.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, valueChunkRecordId);
        valueChunkRecord.setStringValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode());
        valueChunkRecord.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, emptyUUID);

        valueChunkRecord.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);

        valueChunkRecord.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);

        valueChunkRecord.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);
        
        valueChunkRecord.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME, creditRecordId);
        valueChunkRecord.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME, emptyUUID);

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
/*
        /**
         * Then I update the accounts in memory.

        ((BasicWalletAccount) inMemoryAccount).setDatabase(this.database);
        ((BasicWalletAccount) inMemoryAccount).updateBalance();
*/
    }
    
}
