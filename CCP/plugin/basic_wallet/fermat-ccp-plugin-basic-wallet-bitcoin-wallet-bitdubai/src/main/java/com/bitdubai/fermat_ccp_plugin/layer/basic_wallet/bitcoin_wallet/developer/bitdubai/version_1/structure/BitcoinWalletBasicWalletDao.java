package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionSummary;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;


import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantExecuteBitconTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantGetBalanceRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.util.BitcoinWalletTransactionWrapper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWalletDao {

    private Database database;

    public BitcoinWalletBasicWalletDao(Database database){
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
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public long getBookBalance(BlockchainNetworkType blockchainNetworkType) throws CantCalculateBalanceException {
        try{
            return getCurrentBookBalance(blockchainNetworkType);
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
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
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public long getAvailableBalance(BlockchainNetworkType blockchainNetworkType) throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalance(blockchainNetworkType);
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<BitcoinWalletTransaction> listTransactions(BalanceType balanceType,TransactionType transactionType,
                                                           int max,
                                                           int offset) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addFilterOrder(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.setFilterTop(String.valueOf(max));
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            bitcoinWalletTable.loadToMemory();

            return createTransactionList(bitcoinWalletTable.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantListTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<BitcoinWalletTransaction> listTransactionsByActor(final String actorPublicKey,
                                                                  final BalanceType balanceType,
                                                                  final int max,
                                                                  final int offset) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop(String.valueOf(max));
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            bitcoinWalletTable.addFilterOrder(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

            // filter by actor from and actor to (debits and credits related with this actor).
            List<DatabaseTableFilter> tableFilters = new ArrayList<>();
            tableFilters.add(bitcoinWalletTable.getNewFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, DatabaseFilterType.EQUAL, actorPublicKey));
            tableFilters.add(bitcoinWalletTable.getNewFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, DatabaseFilterType.EQUAL, actorPublicKey));

            DatabaseTableFilterGroup filterGroup = bitcoinWalletTable.getNewFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            bitcoinWalletTable.setFilterGroup(filterGroup);

            bitcoinWalletTable.loadToMemory();
            return createTransactionList(bitcoinWalletTable.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, cantLoadTableToMemory, "Error loading wallet table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Unhandled exception.");
        }
    }

    public List<BitcoinWalletTransaction> listTransactionsByActorAndType(final String actorPublicKey,
                                                                  final BalanceType balanceType,
                                                                  final TransactionType  transactionType,
                                                                  final int max,
                                                                  final int offset) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop   (String.valueOf(max)   );
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            bitcoinWalletTable.addFilterOrder(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

            bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME, TransactionState.REVERSED.getCode(), DatabaseFilterType.NOT_EQUALS);

            // filter by actor from or to

            if (transactionType == TransactionType.CREDIT)
                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            else
                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);


            bitcoinWalletTable.loadToMemory();

            if (createTransactionList(bitcoinWalletTable.getRecords()).size()== 0 && transactionType == TransactionType.CREDIT){
                bitcoinWalletTable.clearAllFilters();
                bitcoinWalletTable.addFilterOrder(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
                bitcoinWalletTable.loadToMemory();
                return createTransactionList(bitcoinWalletTable.getRecords());
            }
            if (createTransactionList(bitcoinWalletTable.getRecords()).size()== 0 && transactionType == TransactionType.DEBIT){
                bitcoinWalletTable.clearAllFilters();
                bitcoinWalletTable.addFilterOrder(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
                bitcoinWalletTable.loadToMemory();
                return createTransactionList(bitcoinWalletTable.getRecords());
            }

            return createTransactionList(bitcoinWalletTable.getRecords());

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, cantLoadTableToMemory, "Error loading wallet table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Unhandled exception.");
        }
    }

    public List<BitcoinWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType     balanceType,
                                                                                     TransactionType transactionType,
                                                                                     int             max,
                                                                                     int             offset) throws CantListTransactionsException {
        try {

//            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();
//
//            String query = "SELECT * FROM " +
//                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME +
//                    " WHERE " +
//                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME +
//                    " = '" +
//                    balanceType.getCode() +
//                    "' AND " +
//                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME +
//                    " = '" +
//                    transactionType.getCode() +
//                    "' GROUP BY ";
//
//            if (transactionType == TransactionType.CREDIT)
//                query += BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME;
//            else if (transactionType == TransactionType.DEBIT)
//                query += BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_COLUMN_NAME;
//
//            query += " HAVING MAX(" +
//                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME +
//                    ") = " +
//                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME +
//                    " LIMIT " + max +
//                    " OFFSET " + offset;
//


            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop   (String.valueOf(max)   );
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            //not reversed
            bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME, TransactionState.REVERSED.getCode(), DatabaseFilterType.NOT_EQUALS);


            if ( transactionType == TransactionType.CREDIT){
               // bitcoinWalletTable.clearAllFilters();

                bitcoinWalletTable.addFilterOrder(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);


                bitcoinWalletTable.loadToMemory();
                return createTransactionList(bitcoinWalletTable.getRecords());
            }
            if ( transactionType == TransactionType.DEBIT){
               // bitcoinWalletTable.clearAllFilters();
                bitcoinWalletTable.addFilterOrder(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);


                bitcoinWalletTable.loadToMemory();
                return createTransactionList(bitcoinWalletTable.getRecords());
            }
            return createTransactionList(bitcoinWalletTable.getRecords());

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, cantLoadTableToMemory, "Error loading wallet table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Unhandled exception.");
        }
    }

    /*
     * Add a new debit transaction.
     */
    public void addDebit(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalance(-availableAmount, transactionRecord.getBlockchainNetworkType());
            long bookRunningBalance = calculateBookRunningBalance(-bookAmount,transactionRecord.getBlockchainNetworkType());

            //todo update if the record exists. The record might exists if many send request are executed so add an else to this If

            if (!isTransactionInTable(transactionRecord.getTransactionId(), TransactionType.DEBIT, balanceType))
                executeTransaction(transactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance);

        } catch (CantGetBalanceRecordException | CantExecuteBitconTransactionException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, e, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public void revertCredit(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalance(availableAmount, transactionRecord.getBlockchainNetworkType());
            long bookRunningBalance = calculateBookRunningBalance(bookAmount, transactionRecord.getBlockchainNetworkType());

            DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance, transactionRecord.getBlockchainNetworkType());

           //Balance table - add filter by network type,
            DatabaseTable balanceTable = getBalancesTable();
            balanceTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE, transactionRecord.getBlockchainNetworkType().getCode(), DatabaseFilterType.EQUAL);

            DatabaseTransaction dbTransaction = database.newTransaction();
            dbTransaction.addRecordToUpdate(balanceTable, balanceRecord);

            database.executeTransaction(dbTransaction);


        } catch (CantGetBalanceRecordException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }    catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
     * Add a new Credit transaction.
     */
    public void addCredit(final BitcoinWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterCreditException {
        try{
//            if(!isTransactionInTable(transactionRecord.getTransactionId(), TransactionType.CREDIT, balanceType)) {

                if(isTransactionInTable(transactionRecord.getTransactionId(), TransactionType.CREDIT, balanceType))
                    throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");


                long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
                long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
                long availableRunningBalance = calculateAvailableRunningBalance(availableAmount, transactionRecord.getBlockchainNetworkType());
                long bookRunningBalance = calculateBookRunningBalance(bookAmount,transactionRecord.getBlockchainNetworkType());
                executeTransaction(transactionRecord, TransactionType.CREDIT, balanceType, availableRunningBalance, bookRunningBalance);
//            }
        } catch(CantGetBalanceRecordException | CantLoadTableToMemoryException | CantExecuteBitconTransactionException exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public void updateTransactionState(UUID transactionID, TransactionState transactionState) throws CantStoreMemoException, CantFindTransactionException {
        try {
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME,transactionState.getCode());
                bitcoinwalletTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error",cantUpdateRecord,"Error update memo of Transaction " + transactionID.toString(), "");
        } catch(Exception exception){
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void updateMemoFiled(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException {
        try {
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME,memo);
                bitcoinwalletTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error",cantUpdateRecord,"Error update memo of Transaction " + transactionID.toString(), "");
        } catch(Exception exception){
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public BitcoinWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                                      BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            String query = "SELECT COUNT(*), " +
                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME +
                    ", SUM(" +
                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME +
                    ") FROM " +
                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME +
                    " WHERE " +
                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME +
                    " = '" +
                    balanceType.getCode() +
                    "' AND (" +
                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME +
                    " = '" +
                    actorPublicKey +
                    "' OR " +
                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_COLUMN_NAME +
                    " = '" +
                    actorPublicKey +
                    "') GROUP BY " +
                    BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME;

            List<DatabaseTableRecord> records = bitcoinWalletTable.customQuery(query, true);

            int sentTransactionsNumber = 0;

            int receivedTransactionsNumber = 0;

            long sentAmount = 0;

            long receivedAmount = 0;

            for (DatabaseTableRecord record : records) {
                TransactionType transactionType = TransactionType.getByCode(record.getStringValue("Column1"));

                switch (transactionType) {
                    case CREDIT:
                        receivedTransactionsNumber = record.getIntegerValue("Column0");
                        receivedAmount = record.getLongValue("Column2");
                        break;
                    case DEBIT:
                        sentTransactionsNumber = record.getIntegerValue("Column0");
                        sentAmount = record.getLongValue("Column2");

                        break;
                }
            }

            return new BitcoinWalletBasicWalletTransactionSummary(sentTransactionsNumber, receivedTransactionsNumber, sentAmount, receivedAmount);

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetActorTransactionSummaryException(CantGetActorTransactionSummaryException.DEFAULT_MESSAGE, e,"Error loading Transaction table", "");
        }
    }

    /**
     * We use this method to check if the given transaction is already in the table, we do a query to the table with the specifics of the record
     * and then check if it's not empty
     * @param transactionId
     * @param transactionType
     * @param balanceType
     * @return
     * @throws CantLoadTableToMemoryException if something goes wrong.
     */
    private boolean isTransactionInTable(final UUID transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable bitCoinWalletTable = getBitcoinWalletTable();
        bitCoinWalletTable.addUUIDFilter(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        bitCoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWalletTable.loadToMemory();
        return !bitCoinWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getBitcoinWalletTable(){
        return database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME);
    }

    private long calculateAvailableRunningBalance(final long transactionAmount,BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException{
        return  getCurrentAvailableBalance(blockchainNetworkType) + transactionAmount;
    }

    private long calculateBookRunningBalance(final long transactionAmount,BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException{
        return  getCurrentBookBalance(blockchainNetworkType) + transactionAmount;
    }

    private long getCurrentBookBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK);
    }
    private long getCurrentBookBalance(BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK, blockchainNetworkType);
    }

    private long getCurrentAvailableBalance() throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE);
    }
    private long getCurrentAvailableBalance(BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE ,blockchainNetworkType);
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetBalanceRecordException {
        if (balanceType == BalanceType.AVAILABLE)
            return getBalancesRecord().getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        else
            return getBalancesRecord().getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
    }

    private long getCurrentBalance(final BalanceType balanceType, BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException {
        if (balanceType == BalanceType.AVAILABLE)
            return getBalancesRecord(blockchainNetworkType).getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        else
            return getBalancesRecord(blockchainNetworkType).getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
    }


    private DatabaseTableRecord getBalancesRecord() throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTableRecord getBalancesRecord(BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();

            balancesTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE,blockchainNetworkType.getCode(),DatabaseFilterType.EQUAL);

            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTable getBalancesTable(){
        return database.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME);
    }

    private void executeTransaction(final BitcoinWalletTransactionRecord transactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) throws CantExecuteBitconTransactionException {
        try{
            DatabaseTableRecord bitcoinWalletRecord = constructBitcoinWalletRecord(transactionRecord, balanceType,transactionType ,availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance, transactionRecord.getBlockchainNetworkType());

            BitcoinWalletBasicWalletDaoTransaction bitcoinWalletBasicWalletDaoTransaction = new BitcoinWalletBasicWalletDaoTransaction(database);

            //Balance table - add filter by network type,
            DatabaseTable balanceTable = getBalancesTable();
            balanceTable.addStringFilter(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE,transactionRecord.getBlockchainNetworkType().getCode(),DatabaseFilterType.EQUAL);
            bitcoinWalletBasicWalletDaoTransaction.executeTransaction(getBitcoinWalletTable(), bitcoinWalletRecord, balanceTable, balanceRecord);
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
    private DatabaseTableRecord constructBitcoinWalletRecord(final BitcoinWalletTransactionRecord transactionRecord,
                                                             final BalanceType balanceType,
                                                             final TransactionType transactionType,
                                                             final long availableRunningBalance,
                                                             final long bookRunningBalance) throws CantLoadTableToMemoryException {

        DatabaseTableRecord record = getBitcoinWalletEmptyRecord();

        record.setUUIDValue  (BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME                       , UUID.randomUUID());
        record.setUUIDValue  (BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME         , transactionRecord.getTransactionId());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME, transactionRecord.getAmount());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME, transactionRecord.getMemo());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, transactionRecord.getTimestamp());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, transactionRecord.getTransactionHash());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME             , transactionRecord.getAddressFrom().getAddress());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME               , transactionRecord.getAddressTo().getAddress());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME             , balanceType.getCode());
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue  (BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME     , bookRunningBalance);
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, transactionRecord.getActorFromPublicKey());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_COLUMN_NAME                 , transactionRecord.getActorToPublicKey());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME          , transactionRecord.getActorFromType().getCode());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME            , transactionRecord.getActorToType().getCode());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_NETWORK_TYPE                 , transactionRecord.getBlockchainNetworkType().getCode());
        record.setStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME        , TransactionState.COMPLETE.getCode());

        return record;
    }

    private DatabaseTableRecord getBitcoinWalletEmptyRecord() throws CantLoadTableToMemoryException{
        return getBitcoinWalletTable().getEmptyRecord();
    }

    private DatabaseTableRecord constructBalanceRecord(final long availableRunningBalance, final long bookRunningBalance, BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException{
        DatabaseTableRecord record = getBalancesRecord(blockchainNetworkType);
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        return record;
    }

    // Read record data and create transactions list
    private List<BitcoinWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records){

        List<BitcoinWalletTransaction> transactions = new ArrayList<>();

        for(DatabaseTableRecord record : records)
            transactions.add(constructBitcoinWalletTransactionFromRecord(record));

        return transactions;
    }

    private BitcoinWalletTransaction constructBitcoinWalletTransactionFromRecord(final DatabaseTableRecord record){

        UUID transactionId              = record.getUUIDValue(  BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
        String transactionHash          = record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
        TransactionType transactionType = TransactionType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom       = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo         = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME));
        String actorFromPublicKey       = record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME);
        String actorToPublicKey         = record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_COLUMN_NAME);
        Actors actorFromType            = Actors.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType              = Actors.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME));
        BalanceType balanceType         = BalanceType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME));
        long amount                     = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME);
        long runningBookBalance         = record.getLongValue(  BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance    = record.getLongValue(  BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp                  = record.getLongValue(  BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME);
        String memo                     = record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME);
        BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_NETWORK_TYPE));
        TransactionState transactionState = null;
        try {
            transactionState = TransactionState.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        return new BitcoinWalletTransactionWrapper(transactionId, transactionHash, transactionType, addressFrom, addressTo,
                actorFromPublicKey, actorToPublicKey, actorFromType, actorToType, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo, blockchainNetworkType,transactionState);
    }

    public void deleteTransaction(UUID transactionID)throws CantFindTransactionException{

        try {
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                bitcoinwalletTable.deleteRecord(record);
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");
        } catch (CantDeleteRecordException e) {
            e.printStackTrace();
        }
    }

    public BitcoinWalletTransaction selectTransaction(UUID transactionID)throws CantFindTransactionException{

        try {

            BitcoinWalletTransaction bitcoinWalletTransaction = null;
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(BitcoinWalletDatabaseConstants.BBITCOIN_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                bitcoinWalletTransaction = constructBitcoinWalletTransactionFromRecord(record);
            }

            return bitcoinWalletTransaction;
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Select Transaction Data Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");
        } catch (Exception e) {
            throw new CantFindTransactionException("Select Transaction Data Error",e,"unknown Error" + transactionID.toString(), "");

        }
    }
}