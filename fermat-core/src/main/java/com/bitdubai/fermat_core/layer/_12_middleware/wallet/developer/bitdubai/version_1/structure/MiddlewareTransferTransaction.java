package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.AccountLockStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.AccountStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.Account;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.TransferFailedReasons;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.TransferFailedException;
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
         * Here I create the transfer record for historical purposes.
         */
        DatabaseTable transfersTable = database.getTable(MiddlewareDatabaseConstants.TRANSFERS_TABLE_NAME);
        DatabaseTableRecord transferRecord = transfersTable.getEmptyRecord();

        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_COLUMN_NAME , UUID.randomUUID());
        transferRecord.setStringValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_MEMO_COLUMN_NAME, memo);
        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_FROM_COLUMN_NAME, ((MiddlewareAccount) inMemoryAccountFrom).getId());
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_AMOUNT_FROM_COLUMN_NAME, amountFrom);
        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_TO_COLUMN_NAME, ((MiddlewareAccount) inMemoryAccountTo).getId());
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_AMOUNT_TO_COLUMN_NAME, amountTo);
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_TIME_STAMP_COLUMN_NAME, System.currentTimeMillis() / 1000L);

        /**
         * Then I execute the database transaction.
         */
        DatabaseTransaction databaseTransaction = database.newTransaction();

        databaseTransaction.addRecordToInsert(transfersTable, transferRecord);

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

    }


}
