package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.DealsWithCryptoIndex;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.CryptoValueChunkStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.DebitFailedReasons;
//import com.bitdubai.fermat_api.layer.basic_wallet.discount_wallet.exceptions.AvailableFailedException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.CantCalculateAvailableAmountException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions.DebitFailedException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.util.Converter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 3/25/15.
 */

/*
 * This may be the most important class in the plug-in's business logic
 * The user wants to spend some money, so we need to debit it from the money
 * chunks we have registered. The question is, which chunks should we use? (as
 * different available chunks have been bought with different exchange rates).
 * The exchange rate information can be calculate with the information we have, so
 * we will use the chunks bought cheaper to pay the user, in this way after the user
 * spend the money, if he ask for a new balance he will notice that he got a discount.
 * You can find more information in the plug-in documentation found in the README.md file of
 * this module.
 */

class BasicWalletDebitTransaction implements DealsWithCryptoIndex, DealsWithErrors {


    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    private CryptoIndexManager cryptoIndexManager;


    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /*
     * Class variables implementation
     */
    private Database database;
    private CryptoCurrency cryptoCurrency;
    private FiatCurrency fiatCurrency;

    /*
     * Class constructor
     */
    BasicWalletDebitTransaction(FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency){
        this.fiatCurrency = fiatCurrency;
        this.cryptoCurrency = cryptoCurrency;
    }

    /*
     * Class methods implementation
     */
    void setDatabase(Database database) {
        this.database = database;
    }

    /*
     * We need to modify the ValueChunks table and the Debits table.
     */
    public long debit(long fiatAmount, long cryptoAmount) throws DebitFailedException {

        // We will modify this two tables
        DatabaseTable valueChunksTable = database.getTable(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
        DatabaseTable debitsTable = database.getTable(BasicWalletDatabaseConstants.DEBITS_TABLE_NAME);


        // and to modify the tables we will need records
        DatabaseTableRecord debitRecord;
        DatabaseTableRecord valueChunkRecord;

        // We will deal with transactions, so we must create one
        DatabaseTransaction databaseTransaction = database.newTransaction();

        // Lets define some constants and values we will need for the new records
        long unixTime = System.currentTimeMillis() / 1000L;

        final UUID emptyUUID = new UUID(0L, 0L);

        /**
         * Both amounts must be grater than zero.
         */
        if (fiatAmount <= 0) {
            throw new DebitFailedException(DebitFailedReasons.AMOUNT_MUST_BE_OVER_ZERO);
        }

        if (cryptoAmount <= 0) {
            throw new DebitFailedException(DebitFailedReasons.CRYPTO_AMOUNT_MUST_BE_OVER_ZERO);
        }

        /*
         * We need to check that the amount of monet can be paid before changing our tables
         * So we will first see how much available money we have and see if it is enough.
         */
        BasicWalletAvailable basicWalletAvailable = new BasicWalletAvailable(this.fiatCurrency, this.cryptoCurrency);
        basicWalletAvailable.setCryptoIndexManager(this.cryptoIndexManager);
        basicWalletAvailable.setErrorManager(this.errorManager);
        basicWalletAvailable.setDatabase(this.database);
        long available;

        try {
            available = basicWalletAvailable.getAvailableAmount();
        } catch (CantCalculateAvailableAmountException e) {
            System.err.println("AvailableFailedException" + e.getMessage());
            e.printStackTrace();
            throw new DebitFailedException(DebitFailedReasons.CANT_CALCULATE_AVAILABLE_AMOUNT_OF_FIAT_MONEY);
        }
        // now if we don't have enough money we should throw an exception
        if(available < fiatAmount) {
            throw new DebitFailedException(DebitFailedReasons.NOT_ENOUGH_FIAT_AVAILABLE);
        }

        // Now we complete the debit new record
        debitRecord = debitsTable.getEmptyRecord();

        UUID debitRecordId = UUID.randomUUID();

        debitRecord.setUUIDValue(BasicWalletDatabaseConstants.DEBITS_TABLE_ID_COLUMN_NAME , debitRecordId);
        debitRecord.setLongValue(BasicWalletDatabaseConstants.DEBITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatAmount);
        debitRecord.setLongValue(BasicWalletDatabaseConstants.DEBITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        debitRecord.setLongValue(BasicWalletDatabaseConstants.DEBITS_TABLE_TIME_STAMP_COLUMN_NAME,  unixTime);

        // Now that we have the record set, we can add to the transaction the record to be inserted.
        databaseTransaction.addRecordToInsert(debitsTable, debitRecord);

        /*
         * Now we will deal with the valueChunkTable
         */

        // First, let's filter the records that we can spend

        // We set the filter to get the UNSPENT chunks
        valueChunksTable.setStringFilter(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode(), DatabaseFilterType.EQUAL);

        // now we apply the filter
        try {
            valueChunksTable.loadToMemory();
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemoryException: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new DebitFailedException(DebitFailedReasons.VALUE_CHUNKS_TABLE_FAIL_TO_LOAD_TO_MEMORY);
        }

        List<DatabaseTableRecord> recordList = valueChunksTable.getRecords();

        // We need to sort the records according to their exchange rate.
        Collections.sort(recordList, new ChunksComparator());

        // Here we save the fiat money spent according the chunks used to pay
        long spent = 0;

        for(DatabaseTableRecord record : recordList){

            // if we have nothing else to debit then we have finished the work
            // of this loop
            if(cryptoAmount == 0)
                break;


            long chunkCryptoAmount = record.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);
            long chunkFiatAmount = record.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);

            if(cryptoAmount >= chunkCryptoAmount){

                // We update the value chunk record
                record.setStringValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME,CryptoValueChunkStatus.SPENT.getCode());
                record.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME,debitRecordId);
                record.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);

                databaseTransaction.addRecordToUpdate(valueChunksTable, record);

                // We update the remaining values
                cryptoAmount = cryptoAmount - chunkCryptoAmount;
                spent = spent + chunkFiatAmount;

            } else { // We are in the last chunk we need
                long ca1 = cryptoAmount;
                long ca2 = chunkCryptoAmount - ca1;
                long fa1 = Converter.getProportionalFiatAmountRoundedDown(chunkCryptoAmount,chunkFiatAmount,ca1);
                long fa2 = chunkFiatAmount - fa1;

                // We now update the value chunk record
                record.setStringValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME,CryptoValueChunkStatus.FATHER.getCode());
                record.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME,debitRecordId);
                record.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);

                databaseTransaction.addRecordToUpdate(valueChunksTable, record);

                /*
                 * Now we have to create two new value chunks to add to the table
                 * To see more specifications about this chunks please read the
                 * plug-in documentation
                 */

                UUID fatherID = record.getUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME);
                UUID fatherCreditID = record.getUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME);

                /*
                 * Chunk 1: The spent chunk
                 */
                DatabaseTableRecord chunk1 = valueChunksTable.getEmptyRecord();

                UUID chunk1RecordId = UUID.randomUUID();

                chunk1.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, chunk1RecordId);
                chunk1.setStringValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.SPENT.getCode());
                chunk1.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, fatherID);
                chunk1.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fa1);
                chunk1.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, ca1);
                chunk1.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);
                chunk1.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME, fatherCreditID);
                chunk1.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME, debitRecordId);

                databaseTransaction.addRecordToInsert(valueChunksTable, chunk1);

                /*
                 * Chunk 2: The unspent one
                 */
                DatabaseTableRecord chunk2 = valueChunksTable.getEmptyRecord();

                UUID chunk2RecordId = UUID.randomUUID();

                chunk2.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, chunk2RecordId);
                chunk2.setStringValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode());
                chunk2.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, fatherID);
                chunk2.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fa2);
                chunk2.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, ca2);
                chunk2.setLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);
                chunk2.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME, fatherCreditID);
                chunk2.setUUIDValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME, emptyUUID);

                databaseTransaction.addRecordToInsert(valueChunksTable, chunk2);

                // We update the remaining values
                cryptoAmount = 0;
                spent = spent + fa1;
            }
        }


        /*
         * Now we execute the database transaction to make effective all the changes we made.
         */
        try {
            database.executeTransaction(databaseTransaction);
        }
        catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            /**
             * I can not solve this situation.
             */
            System.err.println("DatabaseTransactionFailedException: " + databaseTransactionFailedException.getMessage());
            databaseTransactionFailedException.printStackTrace();
            throw new DebitFailedException(DebitFailedReasons.CANT_SAVE_TRANSACTION);
        }

        // And finally we return the discount
        return fiatAmount - spent;
    }

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    @Override
    public void setCryptoIndexManager(CryptoIndexManager cryptoIndexManager) {
        this.cryptoIndexManager = cryptoIndexManager;
    }

    /**
     * DealsWithErrors Interface member variables.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager){this.errorManager = errorManager;}


    /*
     * Private member variables
     */
    /*
     * This class is needed to sort the list of value chunks
     */
    private class ChunksComparator implements Comparator<DatabaseTableRecord> {
        @Override
        public int compare(DatabaseTableRecord databaseTableRecord1, DatabaseTableRecord databaseTableRecord2) {

            long chunkFiatAmount1 = databaseTableRecord1.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);
            long chunkCryptoAmount1 = databaseTableRecord1.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);

            long chunkFiatAmount2 = databaseTableRecord2.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);
            long chunkCryptoAmount2 = databaseTableRecord2.getLongValue(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);

            double rate1 = ((double) chunkFiatAmount1) / chunkCryptoAmount1;
            double rate2 = ((double) chunkFiatAmount2) / chunkCryptoAmount2;

            return Double.compare(rate1,rate2);
        }
    }
}
