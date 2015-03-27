package com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.*;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.TransferFailedException;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.exceptions.CantCalculateBalanceException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 3/24/15.
 */
class MiddlewareTransferTransaction {

    private Database database;
    private Map<UUID, Account> fiatAccounts;

    void setDatabase(Database database) {
        this.database = database;
    }

    void setFiatAccounts(Map<UUID, Account> fiatAccounts) {
        this.fiatAccounts = fiatAccounts;
    }

    public void transfer(Account accountFrom, Account accountTo, long amountFrom, long amountTo, String memo) throws TransferFailedException {

        long unixTime = System.currentTimeMillis() / 1000L;
        /**
         * Some needed constant.
         */

        final UUID emptyUUID = new UUID(0L, 0L);
        
        /**
         * First I will check the accounts received belongs to this wallet.
         */
        Account inMemoryAccountFrom;
        inMemoryAccountFrom = fiatAccounts.get(((MiddlewareAccount) accountFrom).getId());

        if ( inMemoryAccountFrom == null ) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_FROM_DOES_NOT_BELONG_TO_THIS_WALLET);
        }

        Account inMemoryAccountTo;
        inMemoryAccountTo = fiatAccounts.get(((MiddlewareAccount) accountTo).getId());

        if ( inMemoryAccountTo == null ) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_TO_DOES_NOT_BELONG_TO_THIS_WALLET);
        }

        /**
         * Then I will check if the accounts are of the same currency.
         */
        if ( inMemoryAccountFrom.getFiatCurrency().getCode() != inMemoryAccountTo.getFiatCurrency().getCode()) {
            throw new TransferFailedException(TransferFailedReasons.FIAT_CURRENCY_FROM_AND_FIAT_CURRENCY_TO_DONT_MATCH);
        }

        /**
         * Then I will check if the accounts are not locked.
         */
        if (((MiddlewareAccount) inMemoryAccountFrom).getLockStatus() == AccountLockStatus.LOCKED) {
            throw new TransferFailedException(TransferFailedReasons.ACCOUNT_FROM_ALREADY_LOCKED);
        }

        if (((MiddlewareAccount) inMemoryAccountTo).getLockStatus()  == AccountLockStatus.LOCKED) {
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
         * Then I validate that there are enough funds on the account to be debited.
         */

        if ( inMemoryAccountFrom.getBalance() < amountFrom) {

            throw new TransferFailedException(TransferFailedReasons.NOT_ENOUGH_FUNDS);
        }

        /**
         * I get ready a Database Transaction.
         */

        DatabaseTransaction databaseTransaction = database.newTransaction();

        /**
         * Here I create the transfer record for historical purposes.
         */
        DatabaseTable transfersTable = database.getTable(MiddlewareDatabaseConstants.TRANSFERS_TABLE_NAME);
        DatabaseTableRecord transferRecord = transfersTable.getEmptyRecord();

        UUID transferRecordId = UUID.randomUUID();
        
        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_COLUMN_NAME , transferRecordId);
        transferRecord.setStringValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_MEMO_COLUMN_NAME, memo);
        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_FROM_COLUMN_NAME, ((MiddlewareAccount) inMemoryAccountFrom).getId());
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_AMOUNT_FROM_COLUMN_NAME, amountFrom);
        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_TO_COLUMN_NAME, ((MiddlewareAccount) inMemoryAccountTo).getId());
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_AMOUNT_TO_COLUMN_NAME, amountTo);
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);

        databaseTransaction.addRecordToInsert(transfersTable, transferRecord);
        
        /**
         * I will go through all the crypto value chunks associated to the From Account.
         */

        DatabaseTable table;

        table = this.database.getTable(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);

        table.setStringFilter(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode(), DatabaseFilterType.EQUAL);
        table.setUUIDFilter(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME, ((MiddlewareAccount) inMemoryAccountFrom).getId(), DatabaseFilterType.EQUAL);
        table.setOrder(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
        
        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new TransferFailedException(TransferFailedReasons.DATABASE_UNAVAILABLE);
        }

        long balanceToTransfer = amountFrom;
        int currentRecordIndex = 0;

        UUID currentRecordId;
        long currentFiatAmount;
        long currentCryptoAmount;
        
        
        List<DatabaseTableRecord> valueChunkRecords = table.getRecords();
        DatabaseTableRecord currentRecord;

        DatabaseTable valueChunksTable = database.getTable(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
        DatabaseTableRecord valueChunkRecord;
        UUID valueChunkRecordId;

        while (balanceToTransfer > 0) {
            
            /**
             * Get the info from the current record, update its status and the added to the transaction.
             */
            currentRecord = valueChunkRecords.get(currentRecordIndex);

            currentRecordId = currentRecord.getUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME);
            currentFiatAmount = currentRecord.getlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);
            currentCryptoAmount = currentRecord.getlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);

            currentRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.FATHER.getCode());

            databaseTransaction.addRecordToUpdate(table, currentRecord);
            
            /**
             * I create the value chunk record. This will be the Son of the current record and it will be pointing to the Account To.
             */

           
            
            if (currentFiatAmount <= balanceToTransfer){
                
                /**
                 * then I create one more Son for the current value chunk with the full value of its parent.
                 */
                valueChunkRecord = valueChunksTable.getEmptyRecord();

                valueChunkRecordId = UUID.randomUUID();

                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, valueChunkRecordId);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode());
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, currentRecordId);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, currentRecord.getStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME));
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, currentFiatAmount);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, currentRecord.getStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME));
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, currentCryptoAmount);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME , ((MiddlewareAccount) accountTo).getId());
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);

                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME, emptyUUID);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME, emptyUUID);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_TRANSFER_COLUMN_NAME,transferRecordId);

                databaseTransaction.addRecordToInsert(valueChunksTable, valueChunkRecord);
                
            } else {
                
                /**
                 * In this case I create 2 records of value chunk, one pointing to the Account To and the change still 
                 * * pointing to the Account From.
                 */
                
                long fiatChange = currentFiatAmount - balanceToTransfer;
                long cryptoChange = currentCryptoAmount * balanceToTransfer / currentFiatAmount;
                long cryptoBalance = currentCryptoAmount - cryptoChange;

                /**
                 * The first record goes to the Account To with the balance.
                 */
                valueChunkRecord = valueChunksTable.getEmptyRecord();

                valueChunkRecordId = UUID.randomUUID();

                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, valueChunkRecordId);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode());
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, currentRecordId);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, currentRecord.getStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME));
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, balanceToTransfer);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, currentRecord.getStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME));
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoBalance);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME , ((MiddlewareAccount) accountTo).getId());
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);

                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME, emptyUUID);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME, emptyUUID);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_TRANSFER_COLUMN_NAME,transferRecordId);

                databaseTransaction.addRecordToInsert(valueChunksTable, valueChunkRecord);

                /**
                 * The second record goes to the Account From with the change.
                 */
                valueChunkRecord = valueChunksTable.getEmptyRecord();

                valueChunkRecordId = UUID.randomUUID();

                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, valueChunkRecordId);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode());
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, currentRecordId);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, currentRecord.getStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME));
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, fiatChange);
                valueChunkRecord.setStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, currentRecord.getStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME));
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoChange);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME , ((MiddlewareAccount) accountFrom).getId());
                valueChunkRecord.setlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, unixTime);

                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME, emptyUUID);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME, emptyUUID);
                valueChunkRecord.setUUIDValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_TRANSFER_COLUMN_NAME,transferRecordId);

                databaseTransaction.addRecordToInsert(valueChunksTable, valueChunkRecord);
                
            }
            
            /**
             * I remember how much there is still needed to transfer.
             */
            balanceToTransfer = balanceToTransfer - currentFiatAmount;
            currentRecordIndex++;
        }
        
        /**
         * Then I execute the database transaction.
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
            throw new TransferFailedException(TransferFailedReasons.CANT_SAVE_TRANSACTION);
        }

        /**
         * Finally I force an update on both account on memory balances.
         */
        
        ((MiddlewareAccount) accountFrom).updateBalance();
        ((MiddlewareAccount) accountTo).updateBalance();
        
    }


}
