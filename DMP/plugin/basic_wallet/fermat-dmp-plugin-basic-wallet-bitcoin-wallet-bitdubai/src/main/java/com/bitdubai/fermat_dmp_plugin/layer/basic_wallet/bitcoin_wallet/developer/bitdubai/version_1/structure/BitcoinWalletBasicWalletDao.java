package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;


import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;

import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantExecuteBitconTransactionException;
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

    /*
     * getBookBalance must get actual Book Balance of wallet, select record from balances table
     */
    public long getBookBalance() throws CantCalculateBalanceException {
        try{
            return getCurrentBookBalance();
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }
    }

    /*
     * getBookBalance must get actual Book Balance of wallet, select record from balances table
     */

    public long getAvailableBalance() throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalance();
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }
    }
    /*
     * Add a new debit transaction.
     */
    public void addDebit(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterDebitDebitException {

        try{
            if(isTransactionInTable(transactionRecord.getIdTransaction(), transactionRecord.getType(), balanceType))
                throw new CantRegisterDebitDebitException(CantRegisterDebitDebitException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;

            long availableRunningBalance = calculateAvailableRunningBalance(-availableAmount);
            long bookRunningBalance = calculateBookRunningBalance(-bookAmount);

            executeTransaction(transactionRecord, balanceType, availableRunningBalance, bookRunningBalance);

        } catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteBitconTransactionException exception){
            throw new CantRegisterDebitDebitException(CantRegisterDebitDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }
    }

    /*
     * Add a new Credit transaction.
     */
    public void addCredit(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterCreditException{
        try{
            if(isTransactionInTable(transactionRecord.getIdTransaction(), transactionRecord.getType(), balanceType))
                throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;

            long availableRunningBalance = calculateAvailableRunningBalance(availableAmount);
            long bookRunningBalance = calculateBookRunningBalance(bookAmount);

            executeTransaction(transactionRecord, balanceType, availableRunningBalance, bookRunningBalance);

        } catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteBitconTransactionException exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
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

    /**
     * We use this method to check if the given transaction is already in the table, we do a query to the table with the specifics of the record
     * and then check if it's not empty
     * @param transactionId
     * @param transactionType
     * @param balanceType
     * @return
     * @throws CantLoadTableToMemoryException
     */
    private boolean isTransactionInTable(final UUID transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable bitCoinWlletTable = getBitcoinWalletTable();
        bitCoinWlletTable.setUUIDFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        bitCoinWlletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWlletTable.setStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWlletTable.loadToMemory();
        return !bitCoinWlletTable.getRecords().isEmpty();
    }

    private DatabaseTable getBitcoinWalletTable(){
        return database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);
    }

    private long calculateAvailableRunningBalance(final long transactionAmount) throws CantGetBalanceRecordException{
        return  getCurrentAvailableBalance() + transactionAmount;
    }

    private long calculateBookRunningBalance(final long transactionAmount) throws CantGetBalanceRecordException{
        return  getCurrentBookBalance() + transactionAmount;
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK);
    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE);
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetBalanceRecordException {
        if (balanceType == BalanceType.AVAILABLE)
            return getBalancesRecord().getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        else
            return getBalancesRecord().getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
    }

    private DatabaseTableRecord getBalancesRecord() throws CantGetBalanceRecordException{
        try {
            DatabaseTable bitcoinwalletTable = getBalancesTable();
            bitcoinwalletTable.loadToMemory();
            return bitcoinwalletTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetBalanceRecordException("Error to get balances record",cantLoadTableToMemory,"Can't load balance table" , "");
        }
    }

    private DatabaseTable getBalancesTable(){
        return database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME);
    }

    private void executeTransaction(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) throws CantExecuteBitconTransactionException {
        try{
            DatabaseTableRecord bitcoinWalletRecord = constructBitcoinWalletRecord(transactionRecord, balanceType, availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance);

            BitcoinWalletBasicWalletDaoTransaction bitcoinWalletBasicWalletDaoTransaction = new BitcoinWalletBasicWalletDaoTransaction(database);

            bitcoinWalletBasicWalletDaoTransaction.executeTransaction(getBitcoinWalletTable(), bitcoinWalletRecord, getBalancesTable(), balanceRecord);
        } catch(CantGetBalanceRecordException | CantLoadTableToMemoryException exception){
            throw new CantExecuteBitconTransactionException(CantExecuteBitconTransactionException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }
    }

    /**
     * This method constructs a Table Record using the data from the transactionRecord, the balance type and the runningBalances that have already been calculated
     * @param transactionRecord
     * @param balanceType
     * @param availableRunningBalance
     * @param bookRunningBalance
     * @return
     * @throws CantLoadTableToMemoryException
     */
    private DatabaseTableRecord constructBitcoinWalletRecord(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) throws CantLoadTableToMemoryException{
        DatabaseTableRecord record = getBitcoinWalletEmptyRecord();
        record.setUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, UUID.randomUUID());
        record.setUUIDValue(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionRecord.getIdTransaction());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionRecord.getType().getCode());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME, transactionRecord.getAmount());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME, transactionRecord.getMemo());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, transactionRecord.getTimestamp());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, transactionRecord.getTramsactionHash());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME, transactionRecord.getAddressFrom().getAddress());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME, transactionRecord.getAddressTo().getAddress());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        return record;
    }

    private DatabaseTableRecord getBitcoinWalletEmptyRecord() throws CantLoadTableToMemoryException{
        return getBitcoinWalletTable().getEmptyRecord();
    }

    private DatabaseTableRecord constructBalanceRecord(final long availableRunningBalance, final long bookRunningBalance) throws CantGetBalanceRecordException{
        DatabaseTableRecord record = getBalancesRecord();
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        return record;
    }

}