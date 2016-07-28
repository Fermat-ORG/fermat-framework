package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionSummary;


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
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.exceptions.CantExecuteBitconTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.exceptions.CantGetBalanceRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.util.CryptoWalletTransactionWrapper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * Created by eze on 2015.06.23..
 */
public class CryptoWalletBasicWalletDao {

    private Database database;

    public CryptoWalletBasicWalletDao(Database database){
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

    public List<CryptoWalletTransaction> listTransactions(BalanceType balanceType,TransactionType transactionType,
                                                           int max,
                                                           int offset) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addFilterOrder(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

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

    public List<CryptoWalletTransaction> listTransactionsByActor(final String actorPublicKey,
                                                                  final BalanceType balanceType,
                                                                  final int max,
                                                                  final int offset) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop(String.valueOf(max));
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            bitcoinWalletTable.addFilterOrder(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

            // filter by actor from and actor to (debits and credits related with this actor).
            List<DatabaseTableFilter> tableFilters = new ArrayList<>();
            tableFilters.add(bitcoinWalletTable.getNewFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, DatabaseFilterType.EQUAL, actorPublicKey));
            tableFilters.add(bitcoinWalletTable.getNewFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, DatabaseFilterType.EQUAL, actorPublicKey));

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

    public List<CryptoWalletTransaction> listTransactionsByActorAndType(final String actorPublicKey,
                                                                  final BalanceType balanceType,
                                                                  final TransactionType  transactionType,
                                                                  final int max,
                                                                  final int offset,
                                                                        BlockchainNetworkType blockchainNetworkType) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop   (String.valueOf(max)   );
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            bitcoinWalletTable.addFilterOrder(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME, TransactionState.REVERSED.getCode(), DatabaseFilterType.NOT_EQUALS);

            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);


            // filter by actor from or to

            if (transactionType == TransactionType.CREDIT)
                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            else
                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);


            bitcoinWalletTable.loadToMemory();

            if (createTransactionList(bitcoinWalletTable.getRecords()).size()== 0 && transactionType == TransactionType.CREDIT){
                bitcoinWalletTable.clearAllFilters();
                bitcoinWalletTable.addFilterOrder(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
                bitcoinWalletTable.loadToMemory();
                return createTransactionList(bitcoinWalletTable.getRecords());
            }
            if (createTransactionList(bitcoinWalletTable.getRecords()).size()== 0 && transactionType == TransactionType.DEBIT){
                bitcoinWalletTable.clearAllFilters();
                bitcoinWalletTable.addFilterOrder(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
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

    public List<CryptoWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType     balanceType,
                                                                                     TransactionType transactionType,
                                                                                     int             max,
                                                                                     int             offset,
                                                                                    BlockchainNetworkType blockchainNetworkType) throws CantListTransactionsException {
        try {

//            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();
//
//            String query = "SELECT * FROM " +
//                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_NAME +
//                    " WHERE " +
//                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME +
//                    " = '" +
//                    balanceType.getCode() +
//                    "' AND " +
//                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME +
//                    " = '" +
//                    transactionType.getCode() +
//                    "' GROUP BY ";
//
//            if (transactionType == TransactionType.CREDIT)
//                query += CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME;
//            else if (transactionType == TransactionType.DEBIT)
//                query += CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME;
//
//            query += " HAVING MAX(" +
//                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME +
//                    ") = " +
//                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME +
//                    " LIMIT " + max +
//                    " OFFSET " + offset;
//


            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop   (String.valueOf(max)   );
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            //not reversed
            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME, TransactionState.REVERSED.getCode(), DatabaseFilterType.NOT_EQUALS);

            bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);

            if ( transactionType == TransactionType.CREDIT){
               // bitcoinWalletTable.clearAllFilters();

                bitcoinWalletTable.addFilterOrder(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);


                bitcoinWalletTable.loadToMemory();
                return createTransactionList(bitcoinWalletTable.getRecords());
            }
            if ( transactionType == TransactionType.DEBIT){
               // bitcoinWalletTable.clearAllFilters();
                bitcoinWalletTable.addFilterOrder(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);


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
    public void addDebit(final CryptoWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            long total = 0;
             if (transactionRecord.getFeeOrigin().equals(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS ))
                 total = transactionRecord.getTotal();
            else
                total = transactionRecord.getAmount();

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? total : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? total : 0L;
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

    public void revertCredit(final CryptoWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            //verify fee origin and add or deducted to balance
            long total = 0;
            if (transactionRecord.getFeeOrigin().equals(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS ))
                total = transactionRecord.getTotal();
            else
                total = transactionRecord.getAmount();

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? total : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? total : 0L;
            long availableRunningBalance = calculateAvailableRunningBalance(availableAmount, transactionRecord.getBlockchainNetworkType());
            long bookRunningBalance = calculateBookRunningBalance(bookAmount, transactionRecord.getBlockchainNetworkType());

            DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance, transactionRecord.getBlockchainNetworkType());

           //Balance table - add filter by network type,
            DatabaseTable balanceTable = getBalancesTable();
            balanceTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE, transactionRecord.getBlockchainNetworkType().getCode(), DatabaseFilterType.EQUAL);

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
    public void addCredit(final CryptoWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterCreditException {
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
            bitcoinwalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME,transactionState.getCode());
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
            bitcoinwalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_MEMO_COLUMN_NAME,memo);
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

    public CryptoWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                                      BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            String query = "SELECT COUNT(*), " +
                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME +
                    ", SUM(" +
                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_AMOUNT_COLUMN_NAME +
                    ") FROM " +
                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_NAME +
                    " WHERE " +
                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME +
                    " = '" +
                    balanceType.getCode() +
                    "' AND (" +
                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME +
                    " = '" +
                    actorPublicKey +
                    "' OR " +
                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME +
                    " = '" +
                    actorPublicKey +
                    "') GROUP BY " +
                    CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME;

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

            return new CryptoWalletBasicWalletTransactionSummary(sentTransactionsNumber, receivedTransactionsNumber, sentAmount, receivedAmount);

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
        bitCoinWalletTable.addUUIDFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        bitCoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWalletTable.loadToMemory();
        return !bitCoinWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getBitcoinWalletTable(){
        return database.getTable(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_NAME);
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
            return getBalancesRecord().getLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        else
            return getBalancesRecord().getLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
    }

    private long getCurrentBalance(final BalanceType balanceType, BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException {
        if (balanceType == BalanceType.AVAILABLE)
            return getBalancesRecord(blockchainNetworkType).getLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        else
            return getBalancesRecord(blockchainNetworkType).getLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
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

            balancesTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE,blockchainNetworkType.getCode(),DatabaseFilterType.EQUAL);

            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTable getBalancesTable(){
        return database.getTable(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_NAME);
    }

    private void executeTransaction(final CryptoWalletTransactionRecord transactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance) throws CantExecuteBitconTransactionException {
        try{
            DatabaseTableRecord bitcoinWalletRecord = constructBitcoinWalletRecord(transactionRecord, balanceType,transactionType ,availableRunningBalance, bookRunningBalance);
            DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance, transactionRecord.getBlockchainNetworkType());

            CryptoWalletBasicWalletDaoTransaction cryptoWalletBasicWalletDaoTransaction = new CryptoWalletBasicWalletDaoTransaction(database);

            //Balance table - add filter by network type,
            DatabaseTable balanceTable = getBalancesTable();
            balanceTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE,transactionRecord.getBlockchainNetworkType().getCode(),DatabaseFilterType.EQUAL);
            cryptoWalletBasicWalletDaoTransaction.executeTransaction(getBitcoinWalletTable(), bitcoinWalletRecord, balanceTable, balanceRecord);
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
    private DatabaseTableRecord constructBitcoinWalletRecord(final CryptoWalletTransactionRecord transactionRecord,
                                                             final BalanceType balanceType,
                                                             final TransactionType transactionType,
                                                             final long availableRunningBalance,
                                                             final long bookRunningBalance) throws CantLoadTableToMemoryException {

        DatabaseTableRecord record = getBitcoinWalletEmptyRecord();

        record.setUUIDValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ID_COLUMN_NAME, UUID.randomUUID());
        record.setUUIDValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionRecord.getTransactionId());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_AMOUNT_COLUMN_NAME, transactionRecord.getAmount());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_MEMO_COLUMN_NAME, transactionRecord.getMemo());
        record.setLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, transactionRecord.getTimestamp());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, transactionRecord.getTransactionHash());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME             , transactionRecord.getAddressFrom().getAddress());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME, transactionRecord.getAddressTo().getAddress());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, transactionRecord.getActorFromPublicKey());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME                 , transactionRecord.getActorToPublicKey());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME          , transactionRecord.getActorFromType().getCode());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME            , transactionRecord.getActorToType().getCode());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_NETWORK_TYPE                 , transactionRecord.getBlockchainNetworkType().getCode());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME        , TransactionState.COMPLETE.getCode());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_CRYPTO_CURRENCY_COLUMN_NAME        , transactionRecord.getCryptoCurrency().getCode());
        record.setLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_FEE_COLUMN_NAME, transactionRecord.getFee());
        record.setStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_FEE_ORIGIN_COLUMN_NAME, transactionRecord.getFeeOrigin().getCode());
        record.setLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_TOTAL_COLUMN_NAME, transactionRecord.getTotal());

        return record;
    }

    private DatabaseTableRecord getBitcoinWalletEmptyRecord() throws CantLoadTableToMemoryException{
        return getBitcoinWalletTable().getEmptyRecord();
    }

    private DatabaseTableRecord constructBalanceRecord(final long availableRunningBalance, final long bookRunningBalance, BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceRecordException{
        DatabaseTableRecord record = getBalancesRecord(blockchainNetworkType);
        record.setLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        return record;
    }

    // Read record data and create transactions list
    private List<CryptoWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records){

        List<CryptoWalletTransaction> transactions = new ArrayList<>();

        for(DatabaseTableRecord record : records)
        {
            CryptoWalletTransaction transaction = constructBitcoinWalletTransactionFromRecord(record);
            transactions.add(constructBitcoinWalletTransactionFromRecord(record));

          /*  if(transaction.getTransactionHash().equals("fab23065-5b3a-4ec1-8a51-6c9bae62bb36"))
            {
                try {
                    long availableAmount =  transaction.getAmount();
                    long bookAmount = 0 ;
                    long availableRunningBalance = calculateAvailableRunningBalance(availableAmount, transaction.getBlockchainNetworkType());
                    long bookRunningBalance = calculateBookRunningBalance(bookAmount, transaction.getBlockchainNetworkType());

                    DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance, transaction.getBlockchainNetworkType());

                    //Balance table - add filter by network type,
                    DatabaseTable balanceTable = getBalancesTable();
                    balanceTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE, transaction.getBlockchainNetworkType().getCode(), DatabaseFilterType.EQUAL);

                    DatabaseTransaction dbTransaction = database.newTransaction();
                    dbTransaction.addRecordToUpdate(balanceTable, balanceRecord);


                    database.executeTransaction(dbTransaction);
                } catch (DatabaseTransactionFailedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/



        }


        return transactions;
    }

    private CryptoWalletTransaction constructBitcoinWalletTransactionFromRecord(final DatabaseTableRecord record){

        UUID transactionId              = record.getUUIDValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ID_COLUMN_NAME);
        String transactionHash          = record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ID_COLUMN_NAME);
        TransactionType transactionType = TransactionType.getByCode(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom       = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo         = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME));
        String actorFromPublicKey       = record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME);
        String actorToPublicKey         = record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME);
        Actors actorFromType            = Actors.getByCode(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType              = Actors.getByCode(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME));
        BalanceType balanceType         = BalanceType.getByCode(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME));
        long amount                     = record.getLongValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_AMOUNT_COLUMN_NAME);
        long runningBookBalance         = record.getLongValue(  CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance    = record.getLongValue(  CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp                  = record.getLongValue(  CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME);
        String memo                     = record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_MEMO_COLUMN_NAME);
        BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_RUNNING_NETWORK_TYPE));
        FeeOrigin feeOrigin             = null;
        long total                       = 0;
        long fee                         = record.getLongValue(  CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_FEE_COLUMN_NAME);
        try {
            feeOrigin = FeeOrigin.getByCode(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_FEE_ORIGIN_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
        }

        if(feeOrigin.equals(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS))
            total = amount + fee;
        else
            total = amount - fee;

        TransactionState transactionState = null;
        CryptoCurrency cryptoCurrency = null;
        try {
         cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_CRYPTO_CURRENCY_COLUMN_NAME));
                  transactionState = TransactionState.getByCode(record.getStringValue(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME));

        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        return new CryptoWalletTransactionWrapper(transactionId, transactionHash, transactionType, addressFrom, addressTo,
                actorFromPublicKey, actorToPublicKey, actorFromType, actorToType, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo, blockchainNetworkType,transactionState,
                cryptoCurrency, feeOrigin,fee,total);
    }

    public void deleteTransaction(UUID transactionID)throws CantFindTransactionException{

        try {
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

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

    public CryptoWalletTransaction selectTransaction(UUID transactionID)throws CantFindTransactionException{

        try {

            CryptoWalletTransaction cryptoWalletTransaction = null;
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(CryptoWalletDatabaseConstants.CRYPTO_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                cryptoWalletTransaction = constructBitcoinWalletTransactionFromRecord(record);
            }

            return cryptoWalletTransaction;
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Select Transaction Data Error",cantLoadTableToMemory,"Error load Transaction table" + transactionID.toString(), "");
        } catch (Exception e) {
            throw new CantFindTransactionException("Select Transaction Data Error",e,"unknown Error" + transactionID.toString(), "");

        }
    }
}