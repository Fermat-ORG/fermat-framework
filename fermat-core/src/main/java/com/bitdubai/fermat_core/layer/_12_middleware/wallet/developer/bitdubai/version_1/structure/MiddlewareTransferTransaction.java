package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.AccountStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.FiatAccount;
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
    private Map<UUID, FiatAccount> fiatAccounts;

    void setDatabase(Database database) {
        this.database = database;
    }

    void setFiatAccounts(Map<UUID, FiatAccount> fiatAccounts) {
        this.fiatAccounts = fiatAccounts;
    }

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

        accountFromRecord.setlongValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME,accountFromNewBalance);
        accountToRecord.setlongValue(MiddlewareDatabaseConstants.FIAT_ACCOUNTS_TABLE_BALANCE_COLUMN_NAME,accountToNewBalance);

        /**
         * Here I create the transfer record for historical purposes.
         */
        DatabaseTable transfersTable = database.getTable(MiddlewareDatabaseConstants.TRANSFERS_TABLE_NAME);
        DatabaseTableRecord transferRecord = transfersTable.getEmptyRecord();

        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_COLUMN_NAME , UUID.randomUUID());
        transferRecord.setStringValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_MEMO_COLUMN_NAME, memo);
        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_FROM_COLUMN_NAME, ((MiddlewareFiatAccount) inMemoryAccountFrom).getId());
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_AMOUNT_FROM_COLUMN_NAME, amountFrom);
        transferRecord.setUUIDValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_TO_COLUMN_NAME, ((MiddlewareFiatAccount) inMemoryAccountTo).getId());
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_AMOUNT_TO_COLUMN_NAME, amountTo);
        transferRecord.setlongValue(MiddlewareDatabaseConstants.TRANSFERS_TABLE_TIME_STAMP_COLUMN_NAME, System.currentTimeMillis() / 1000L);

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


}
