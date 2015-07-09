package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;


import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;

import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantExcecuteBitconTransaction;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantGetBalanceRecordException;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.util.BitcoinTransactionWrapper;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWalletDao {

    /**
     * CryptoAddressBook Interface member variables.
     */
    private Database database;

    /**
     * Constructor.
     */
    public BitcoinWalletBasicWalletDao(Database database){
        /**
         * The only one who can set the pluginId is the Plugin Root.
         */

        this.database = database;
    }

    /**
     * BitcoinWalletBasicWalletDao Interface implementation.
     */


    /*
     * getBookBalance must get actual Book Balance of wallet, select record from balances table
     */
    long getBookBalance() throws CantCalculateBalanceException {

        long balance= 0;

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME);



        try {
            bitcoinwalletTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            throw new CantCalculateBalanceException("Get Book Balance",cantLoadTableToMemory,"Error load wallet balance table ", "");


        }


        // Read record data and get book balance field

        for(DatabaseTableRecord record : bitcoinwalletTable.getRecords())
        {
            balance = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);

        }


        // Now we return balance

        return balance;

    }

/*
     * getBookBalance must get actual Book Balance of wallet, select record from balances table
     */

    long getAvailableBalance() throws CantCalculateBalanceException {

        long balance = 0;

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME);


        try {
            bitcoinwalletTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            throw new CantCalculateBalanceException("Get Book Balance",cantLoadTableToMemory,"Error load wallet balance table ", "");


        }


        // Read record data and get book balance field

        for(DatabaseTableRecord record : bitcoinwalletTable.getRecords())
        {
            balance = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVILABLE_BALANCE_COLUMN_NAME);

        }


        // Now we return balance

        return balance;

    }
    /*
     * Add a new debit transaction.
     */
    public void addDebit(BitcoinWalletTransactionRecord cryptoTransaction,BalanceType balanceType) throws CantRegisterDebitDebitException {

        long totalCredit = 0;
        long totalDebit = 0;
        long balance = 0;
        long runningBalance = 0;

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);
        DatabaseTableRecord debitRecord;


        DatabaseTable bitcoinwalletBalanceTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME);
        DatabaseTableRecord balanceRecord = null;

        //First check if the trasacction exist

        /**
         *  I will load the information of table into a memory structure, filter by transaction hash .
         */
        bitcoinwalletTable.setUUIDFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, cryptoTransaction.getIdTransaction(), DatabaseFilterType.EQUAL);
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME,TransactionType.DEBIT.getCode(),DatabaseFilterType.EQUAL);
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME,balanceType.getCode(),DatabaseFilterType.EQUAL);

        try {
            bitcoinwalletTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */

            throw new CantRegisterDebitDebitException("Error to add debit transaction to wallet",cantLoadTableToMemory,"Error load wallet table" , "");


        }

        if (bitcoinwalletTable.getRecords().size() == 0) {

            //sum all debit record and sum all credit record, for this balance type

            totalDebit = getTotalTransactions(balanceType, TransactionType.CREDIT);
            totalCredit= getTotalTransactions(balanceType, TransactionType.DEBIT);

            balance = totalCredit - totalDebit;


            // Now we complete the debit new record
            debitRecord = bitcoinwalletTable.getEmptyRecord();

            UUID debitRecordId = UUID.randomUUID();

            debitRecord.setUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, debitRecordId);
            debitRecord.setUUIDValue(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, cryptoTransaction.getIdTransaction());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, cryptoTransaction.getType().getCode());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME, cryptoTransaction.getAmount());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME, cryptoTransaction.getMemo());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, cryptoTransaction.getTimestamp());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, cryptoTransaction.getTramsactionHash());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME, cryptoTransaction.getAddressFrom().getAddress());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME, cryptoTransaction.getAddressTo().getAddress());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());

            //check balance transaction type to insert running balance field

            if(balanceType == BalanceType.AVILABLE)
            {
                //calculate running balances
                if(cryptoTransaction.getType() == TransactionType.CREDIT)
                    runningBalance = balance + cryptoTransaction.getAmount();
                else
                    runningBalance = balance- cryptoTransaction.getAmount();

                debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVILABLE_BALANCE_COLUMN_NAME, runningBalance);
                debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME, 0);

            }
            else
            {
                if(cryptoTransaction.getType() == TransactionType.CREDIT)
                    runningBalance = balance + cryptoTransaction.getAmount();
                else
                    runningBalance = balance- cryptoTransaction.getAmount();

                debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVILABLE_BALANCE_COLUMN_NAME, 0);
                debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME, runningBalance);
            }



            // Now I insert debit record.
            //Use transaction to insert wallet record, delete balance record and insert new total balances

            try {
                BitcoinWalletBasicWalletDaoTransaction bitcoinWalletBasicWalletDaoTransaction = new BitcoinWalletBasicWalletDaoTransaction(this.database);
                try
                {
                    balanceRecord = getBalancesRecord();

                    //set total balances to update
                    if(balanceType == BalanceType.AVILABLE)
                    {
                        balanceRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVILABLE_BALANCE_COLUMN_NAME, balance);

                    }
                    else
                    {
                        balanceRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, balance);

                    }
                }
                catch (CantGetBalanceRecordException e)
                {
                    throw new CantRegisterDebitDebitException("Error to add debit transaction to wallet",e,"Error to execute balance transaction insert and update" , "");

                }

                bitcoinWalletBasicWalletDaoTransaction.executeTransaction(bitcoinwalletTable, debitRecord, bitcoinwalletBalanceTable, balanceRecord);

            } catch (CantExcecuteBitconTransaction cantInsertRecordException) {

                throw new CantRegisterDebitDebitException("Error to add debit transaction to wallet",cantInsertRecordException,"Error to execute balance transaction insert and update" , "");


            }



        }

    }




    /*
     * Add a new Credit transaction.
     */
    public void addCredit(BitcoinWalletTransactionRecord cryptoTransaction,BalanceType balanceType) throws CantRegisterCreditException {

        long totalCredit = 0;
        long totalDebit = 0;
        long balance = 0;
        long runningBalance = 0;

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);
        DatabaseTableRecord debitRecord;


        DatabaseTable bitcoinwalletBalanceTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME);
        DatabaseTableRecord balanceRecord = null;

        //First check if the trasacction exist

        /**
         *  I will load the information of table into a memory structure, filter by transaction hash .
         */
        bitcoinwalletTable.setUUIDFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, cryptoTransaction.getIdTransaction(), DatabaseFilterType.EQUAL);
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME,TransactionType.CREDIT.getCode(),DatabaseFilterType.EQUAL);
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME,balanceType.getCode(),DatabaseFilterType.EQUAL);

        try {
            bitcoinwalletTable.loadToMemory();
        }
        catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */

            throw new CantRegisterCreditException("Error to add credit transaction to wallet",cantLoadTableToMemory,"Error to load wallet table" , "");


        }

        if(bitcoinwalletTable.getRecords().size() == 0)
        {
            // Now we complete the debit new record
            debitRecord = bitcoinwalletTable.getEmptyRecord();

            UUID debitRecordId = UUID.randomUUID();

            debitRecord.setUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, debitRecordId);
            debitRecord.setUUIDValue(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, cryptoTransaction.getIdTransaction());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, cryptoTransaction.getType().getCode());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME, cryptoTransaction.getAmount());
            //  debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_STATE_COLUMN_NAME, cryptoTransaction.getState().getCode());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME, cryptoTransaction.getMemo());
            debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, cryptoTransaction.getTimestamp());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, cryptoTransaction.getTramsactionHash());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME, cryptoTransaction.getAddressFrom().getAddress());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME, cryptoTransaction.getAddressTo().getAddress());
            debitRecord.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());

            //check balance transaction type to insert running balance field

            if(balanceType == BalanceType.AVILABLE)
            {
                //calculate running balances
                if(cryptoTransaction.getType() == TransactionType.CREDIT)
                    runningBalance = balance + cryptoTransaction.getAmount();
                else
                    runningBalance = balance- cryptoTransaction.getAmount();

                debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVILABLE_BALANCE_COLUMN_NAME, runningBalance);
                debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME, 0);

            } else {
                if(cryptoTransaction.getType() == TransactionType.CREDIT)
                    runningBalance = balance + cryptoTransaction.getAmount();
                else
                    runningBalance = balance- cryptoTransaction.getAmount();

                debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVILABLE_BALANCE_COLUMN_NAME, 0);
                debitRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME, runningBalance);
            }


            // Now I insert debit record.
            //Use transaction to insert wallet record, delete balance record and insert new total balances

            try {
                BitcoinWalletBasicWalletDaoTransaction bitcoinWalletBasicWalletDaoTransaction = new BitcoinWalletBasicWalletDaoTransaction(this.database);
                try{
                    balanceRecord = getBalancesRecord();

                    //set total balances to update
                    if(balanceType == BalanceType.AVILABLE){
                        balanceRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVILABLE_BALANCE_COLUMN_NAME, balance);

                    } else {
                        balanceRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, balance);

                    }
                } catch (CantGetBalanceRecordException e) {
                    throw new CantRegisterCreditException("Error to add debit transaction to wallet",e,"Error to execute balance transaction insert and update" , "");

                }

                bitcoinWalletBasicWalletDaoTransaction.executeTransaction(bitcoinwalletTable, debitRecord, bitcoinwalletBalanceTable, balanceRecord);

            } catch (CantExcecuteBitconTransaction cantInsertRecordException) {

                throw new CantRegisterCreditException("Error to add debit transaction to wallet",cantInsertRecordException,"Error to execute balance transaction insert and update" , "");


            }
        }



    }



    public List<BitcoinWalletTransactionRecord> getTransactions(int max, int offset) throws CantGetTransactionsException {
        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);

        /**
         *  I will load the information of table into a memory structure, filter for count of records and page
         */
        bitcoinwalletTable.setFilterTop(String.valueOf(max));
        bitcoinwalletTable.setFilterOffSet(String.valueOf(offset));

        try {
            bitcoinwalletTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            throw new CantGetTransactionsException("Get List of Transactions",cantLoadTableToMemory,"Error load wallet table ", "");


        }

        List<BitcoinWalletTransactionRecord> bitcoinWalletTransactionRecordList = new ArrayList<>();


        // Read record data and create transactions list

        for(DatabaseTableRecord record : bitcoinwalletTable.getRecords())
        {
            record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME);

            BitcoinWalletTransactionRecord bitcoinWalletTransactionRecord = new BitcoinTransactionWrapper();

            CryptoAddress crypoAddressTo = new CryptoAddress();


            crypoAddressTo.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME));
            crypoAddressTo.setCryptoCurrency(CryptoCurrency.BITCOIN);
            bitcoinWalletTransactionRecord.setAddressFrom(crypoAddressTo);

            CryptoAddress crypoAddressFrom = new CryptoAddress();
            crypoAddressFrom.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME));

            bitcoinWalletTransactionRecord.setAddressTo(crypoAddressFrom);
            bitcoinWalletTransactionRecord.setAmount(record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME));
            bitcoinWalletTransactionRecord.setMemo(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME));
            //    try {
            //    bitcoinWalletTransactionRecord.setState(TransactionState.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_STATE_COLUMN_NAME)));
            // } catch (InvalidParameterException e) {
            // throw new CantGetTransactionsException("Get List of Transactions",e,"Error set State ", "");


            //}
            bitcoinWalletTransactionRecord.setTimestamp(record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME));
            bitcoinWalletTransactionRecord.setTramsactionHash(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME));
            bitcoinWalletTransactionRecord.setType(TransactionType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME)));

            bitcoinWalletTransactionRecordList.add(bitcoinWalletTransactionRecord);
        }



        // Now we return BitcoinWalletTransactionRecord list
        return bitcoinWalletTransactionRecordList;
    }


    public void updateMemoFiled(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);


        /**
         *  I will load the information of table into a memory structure, filter for transaction id
         */
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);


        try {
            bitcoinwalletTable.loadToMemory();

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */

            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");

        }



        // Read record data and create transactions list

        for(DatabaseTableRecord record : bitcoinwalletTable.getRecords())
        {
            record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME,memo);

            try {

                bitcoinwalletTable.updateRecord(record);

            } catch (CantUpdateRecordException cantUpdateRecord) {

                throw new CantStoreMemoException("Transaction Memo Update Error",cantUpdateRecord,"Error update memo of Transaction " + transactionID.toString(), "");
            }

        }


    }


    private long getTotalTransactions(BalanceType balanceType,TransactionType transactionType ) throws CantRegisterDebitDebitException {


        long totalTransactiones = 0;

        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);

        //sum all debit record and sum all credit record, calculate book balance and update total balances table and runningBookBalance field

        // We set the filter to get the Send Transactions
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

        // We set the filter to get the Send Transactions for balance type
        bitcoinwalletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);


        // now we apply the filter
        try {
            bitcoinwalletTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            throw new CantRegisterDebitDebitException("Error to add debit wallet transacction", cantLoadTableToMemory, "Error load wallet table to get debit records", "");

        }

        // and finally we calculate the balance
        for (DatabaseTableRecord record : bitcoinwalletTable.getRecords()) {
            totalTransactiones += record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME);
        }

        return totalTransactiones;
    }


    private DatabaseTableRecord getBalancesRecord() throws CantGetBalanceRecordException
    {
        // create the database objects
        DatabaseTable bitcoinwalletTable = database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME);


        /**
         *  I will load the information of table into a memory structure;
         */


        try {
            bitcoinwalletTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            throw new CantGetBalanceRecordException("Error to get balances record",cantLoadTableToMemory,"Can't load balance table" , "");

        }
        return bitcoinwalletTable.getRecords().get(0);


    }
}