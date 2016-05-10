package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;



import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;


import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
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
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionSummary;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantExecuteLossProtectedBitcoinTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantGetLossProtectedBalanceRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantGetTransactionsRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantInsertSpendingException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.util.BitcoinLossProtecdWalletTransactionWrapper;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletLossProtectedWalletDao {

    private Database database;

    private CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter;

    private UUID exchangeProviderId;


    CurrencyPair wantedCurrencyPair = new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR);
    CurrencyExchangeRateProviderManager rateProviderManager;

    public BitcoinWalletLossProtectedWalletDao(Database database){
        this.database = database;
    }

    /*
     * getBookBalance must get actual Book Balance of wallet, select record from balances table
     */
    public long getBookBalance() throws CantCalculateBalanceException {
        try{
            return getCurrentBookBalance();
        } catch (CantGetLossProtectedBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public long getBookBalance(BlockchainNetworkType blockchainNetworkType) throws CantCalculateBalanceException {
        try{
            return getCurrentBookBalance(blockchainNetworkType);
        } catch (CantGetLossProtectedBalanceRecordException exception){
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
        } catch (CantGetLossProtectedBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public long getAvailableBalance(BlockchainNetworkType blockchainNetworkType) throws CantCalculateBalanceException {
        try{
            return getCurrentAvailableBalance(blockchainNetworkType);
        } catch (CantGetLossProtectedBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public long getAvailableBalance(BlockchainNetworkType blockchainNetworkType,String exchangeRate) throws CantListTransactionsException {
        try {

            long availableBalance = 0;
            long spending = 0;
            long total =0;

            //busco los registros de ingreso menores a la cotizacion actual, me ijo cuanto gasto de cada bloque y saco el saldo


            for(DatabaseTableRecord record :  getRecordsLessThanRate(blockchainNetworkType,exchangeRate))
            {
                try {

                    spending+= getTotalTransactionsSpending(record.getUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME));

                }catch (NullPointerException e){
                    System.out.println("THERE IS A NULL POINTER HERE !! TESTING ! ");
                }

            }

            for(DatabaseTableRecord record :  getRecordsLessThanRate(blockchainNetworkType,exchangeRate))
            {
                try {

                    total+= record.getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_AMOUNT_COLUMN_NAME);

                }catch (NullPointerException e){
                    System.out.println("THERE IS A NULL POINTER HERE !! TESTING ! ");
                }

            }

            availableBalance = total - spending;

            //balance
            return availableBalance;

        } catch (CantGetTransactionsRecordException e) {
            throw new CantListTransactionsException("Get List of Transactions By Exchange Rate", e, "Error load transactions records table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<BitcoinLossProtectedWalletTransaction> listTransactions(BalanceType balanceType,TransactionType transactionType,
                                                           int max,
                                                           int offset) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

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

    public List<BitcoinLossProtectedWalletTransaction> listTransactionsByExchangeRate(BalanceType balanceType,TransactionType transactionType,
                                                                                      BlockchainNetworkType blockchainNetworkType,
                                                                                      long exchangeRate) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME, String.valueOf(exchangeRate), DatabaseFilterType.LESS_OR_EQUAL_THAN);
            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);

            bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);


            bitcoinWalletTable.loadToMemory();

            return createTransactionList(bitcoinWalletTable.getRecords());
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantListTransactionsException("Get List of Transactions By Exchange Rate", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<BitcoinLossProtectedWalletSpend> listTransactionsSpending(UUID transactionId) throws CantListTransactionsException {
        try {

            DatabaseTable bitcoinSpentTable = database.getTable(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_NAME);

            bitcoinSpentTable.addUUIDFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            bitcoinSpentTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinSpentTable.loadToMemory();

            List<BitcoinLossProtectedWalletSpend> spendings = new ArrayList<>();

            for(DatabaseTableRecord record : bitcoinSpentTable.getRecords())
            {

                UUID spendId               = record.getUUIDValue(  BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_ID_COLUMN_NAME);
                long amount                = record.getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_BTC_SPENT_COLUMN_NAME);
                long timeStamp             = record.getLongValue(  BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_TIME_STAMP_COLUMN_NAME);
                double exchangeRate        = Double.parseDouble(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_EXCHANGE_RATE_COLUMN_NAME));
                BlockchainNetworkType blockchainNetworkType  = BlockchainNetworkType.getByCode(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_RUNNING_NETWORK_TYPE));

                spendings.add(new BitcoinWalletLossProtectedWalletSpend(spendId,transactionId,amount,timeStamp,exchangeRate,blockchainNetworkType));

            }

            return spendings;

       } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantListTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public List<BitcoinLossProtectedWalletSpend> listAllWalletSpending(BlockchainNetworkType blockchainNetworkType) throws CantListTransactionsException {
        try {

            DatabaseTable bitcoinSpentTable = database.getTable(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_NAME);

            bitcoinSpentTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
            bitcoinSpentTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);

            bitcoinSpentTable.loadToMemory();

            List<BitcoinLossProtectedWalletSpend> spendings = new ArrayList<>();

            for(DatabaseTableRecord record : bitcoinSpentTable.getRecords())
            {

                UUID spendId               = record.getUUIDValue(  BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_ID_COLUMN_NAME);
                UUID transactionId         = record.getUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TRANSACTION_ID_COLUMN_NAME);
                long amount                = record.getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_BTC_SPENT_COLUMN_NAME);
                long timeStamp             = record.getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_TIME_STAMP_COLUMN_NAME);
                double exchangeRate        = Double.parseDouble(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_EXCHANGE_RATE_COLUMN_NAME));

                spendings.add(new BitcoinWalletLossProtectedWalletSpend(spendId,transactionId,amount,timeStamp,exchangeRate,blockchainNetworkType));

            }

            return spendings;

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantListTransactionsException("Get List of Transactions", cantLoadTableToMemory, "Error load wallet table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public long getTotalTransactionsSpending(UUID transactionId) throws CantListTransactionsException {
        try {

            long spending = 0;

            DatabaseTable bitcoinSpentTable = database.getTable(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_NAME);

            bitcoinSpentTable.addUUIDFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            bitcoinSpentTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinSpentTable.loadToMemory();

            for(DatabaseTableRecord record : bitcoinSpentTable.getRecords())
            {
                spending  += record.getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_BTC_SPENT_COLUMN_NAME);
            }


            return spending;

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantListTransactionsException("Get List of Transactions spending", cantLoadTableToMemory, "Error load spent table ", "");
        } catch (Exception exception){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }


    public List<BitcoinLossProtectedWalletTransaction> listTransactionsByActor(final String actorPublicKey,
                                                                  final BalanceType balanceType,
                                                                  final int max,
                                                                  final int offset) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop(String.valueOf(max));
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

            // filter by actor from and actor to (debits and credits related with this actor).
            List<DatabaseTableFilter> tableFilters = new ArrayList<>();
            tableFilters.add(bitcoinWalletTable.getNewFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, DatabaseFilterType.EQUAL, actorPublicKey));
            tableFilters.add(bitcoinWalletTable.getNewFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, DatabaseFilterType.EQUAL, actorPublicKey));

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

    public List<BitcoinLossProtectedWalletTransaction> listTransactionsByActorAndType(final String actorPublicKey,
                                                                  final BalanceType balanceType,
                                                                  final TransactionType transactionType,
                                                                  final int max,
                                                                  final int offset) throws CantListTransactionsException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop(String.valueOf(max));
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));

            bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

            //not reversed
            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME, TransactionState.REVERSED.getCode(), DatabaseFilterType.NOT_EQUALS);

            // filter by actor from or to

            if (transactionType == TransactionType.CREDIT)
                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
            else
                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);


            bitcoinWalletTable.loadToMemory();

            if (createTransactionList(bitcoinWalletTable.getRecords()).size()== 0 && transactionType == TransactionType.CREDIT){
                bitcoinWalletTable.clearAllFilters();
                bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
                bitcoinWalletTable.loadToMemory();
                return createTransactionList(bitcoinWalletTable.getRecords());
            }
            if (createTransactionList(bitcoinWalletTable.getRecords()).size()== 0 && transactionType == TransactionType.DEBIT){
                bitcoinWalletTable.clearAllFilters();
                bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);
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

    public List<BitcoinLossProtectedWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType     balanceType,
                                                                                     TransactionType transactionType,
                                                                                     int             max,
                                                                                     int             offset,
                                                                                     BlockchainNetworkType blockchainNetworkType) throws CantListTransactionsException {
        try {

            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            bitcoinWalletTable.setFilterTop(String.valueOf(max));
            bitcoinWalletTable.setFilterOffSet(String.valueOf(offset));



            if ( transactionType == TransactionType.CREDIT){
                //not reversed
                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME, TransactionState.REVERSED.getCode(), DatabaseFilterType.NOT_EQUALS);

                bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.loadToMemory();
                return createTransactionList(bitcoinWalletTable.getRecords());
            }
            if ( transactionType == TransactionType.DEBIT){
                bitcoinWalletTable.clearAllFilters();
                //not reversed
                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME, TransactionState.REVERSED.getCode(), DatabaseFilterType.NOT_EQUALS);

                bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

                bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);


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
    public void addDebit(final BitcoinLossProtectedWalletTransactionRecord transactionRecord, final BalanceType balanceType, final String exchangeRate,
                         CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter,
                         UUID exchangeProviderId,
                         CurrencyExchangeRateProviderManager rateProviderManager
                         ) throws CantRegisterDebitException {
        try {

            this.rateProviderManager = rateProviderManager;
            this.exchangeProviderId = exchangeProviderId;
            this.exchangeProviderFilterManagerproviderFilter = exchangeProviderFilterManagerproviderFilter;

            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalance(-availableAmount, transactionRecord.getBlockchainNetworkType());
            long bookRunningBalance = calculateBookRunningBalance(-bookAmount, transactionRecord.getBlockchainNetworkType());

            //todo update if the record exists. The record might exists if many send request are executed so add an else to this If

            if (!isTransactionInTable(transactionRecord.getTransactionId(), TransactionType.DEBIT, balanceType))
                executeTransaction(transactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance, exchangeRate);



        } catch (CantGetLossProtectedBalanceRecordException | CantExecuteLossProtectedBitcoinTransactionException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, e, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public void addDebit(final BitcoinLossProtectedWalletTransactionRecord transactionRecord, final BalanceType balanceType, final String exchangeRate
                         ) throws CantRegisterDebitException {
        try {


            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalance(-availableAmount, transactionRecord.getBlockchainNetworkType());
            long bookRunningBalance = calculateBookRunningBalance(-bookAmount, transactionRecord.getBlockchainNetworkType());

            //todo update if the record exists. The record might exists if many send request are executed so add an else to this If

            if (!isTransactionInTable(transactionRecord.getTransactionId(), TransactionType.DEBIT, balanceType))
                executeTransaction(transactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance, exchangeRate);


        } catch (CantGetLossProtectedBalanceRecordException | CantExecuteLossProtectedBitcoinTransactionException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, e, null, "Check the cause");
        } catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    public void revertCredit(final BitcoinLossProtectedWalletTransactionRecord transactionRecord, final BalanceType balanceType) throws CantRegisterDebitException {
        try {
            long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
            long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
            long availableRunningBalance = calculateAvailableRunningBalance(availableAmount, transactionRecord.getBlockchainNetworkType());
            long bookRunningBalance = calculateBookRunningBalance(bookAmount, transactionRecord.getBlockchainNetworkType());

            DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance, transactionRecord.getBlockchainNetworkType());

           //Balance table - add filter by network type,
            DatabaseTable balanceTable = getBalancesTable();
            balanceTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE, transactionRecord.getBlockchainNetworkType().getCode(), DatabaseFilterType.EQUAL);

            DatabaseTransaction dbTransaction = database.newTransaction();
            dbTransaction.addRecordToUpdate(balanceTable, balanceRecord);

            database.executeTransaction(dbTransaction);

            //delete spendings records
            this.deleteSpendings(transactionRecord.getTransactionId());

        } catch (CantGetLossProtectedBalanceRecordException exception) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        }    catch (Exception exception){
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*
     * Add a new Credit transaction.
     */
    public void addCredit(final BitcoinLossProtectedWalletTransactionRecord transactionRecord, final BalanceType balanceType, final String exchangeRate) throws CantRegisterCreditException {
        try{

                if(isTransactionInTable(transactionRecord.getTransactionId(), TransactionType.CREDIT, balanceType))
                    throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

                long availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? transactionRecord.getAmount() : 0L;
                long bookAmount = balanceType.equals(BalanceType.BOOK) ? transactionRecord.getAmount() : 0L;
                long availableRunningBalance = calculateAvailableRunningBalance(availableAmount, transactionRecord.getBlockchainNetworkType());
                long bookRunningBalance = calculateBookRunningBalance(bookAmount, transactionRecord.getBlockchainNetworkType());
                executeTransaction(transactionRecord, TransactionType.CREDIT, balanceType, availableRunningBalance, bookRunningBalance, exchangeRate);

        } catch(CantGetLossProtectedBalanceRecordException | CantLoadTableToMemoryException | CantExecuteLossProtectedBitcoinTransactionException exception){
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
            bitcoinwalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME,transactionState.getCode());
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

    public void updateTransactionRate(UUID transactionID, double rate) throws CantStoreMemoException, CantFindTransactionException {
        try {
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME, String.valueOf(rate));
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

    private void setSpendingRate(final UUID spendingId)
    {
        final ExchangeRate[] rate = new ExchangeRate[1];
        try {

            //get setting exchange provider manager
            //update transaction rate

            final UUID rateProviderManagerId = exchangeProviderId;

            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {

                        CurrencyExchangeRateProviderManager rateProviderManager = exchangeProviderFilterManagerproviderFilter.getProviderReference(rateProviderManagerId);
                        //your exchange rate.
                        rate[0] = rateProviderManager.getCurrentExchangeRate(wantedCurrencyPair);

                        //update spending record

                        updateSpendingRate(spendingId, rate[0].getPurchasePrice());

                    } catch (CantGetExchangeRateException e) {
                        e.printStackTrace();

                    } catch (UnsupportedCurrencyPairException e) {
                        e.printStackTrace();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private final void updateSpendingRate(UUID spentID, double rate) throws CantStoreMemoException, CantFindTransactionException {
        try {
            // create the database objects
            DatabaseTable bitcoinSpentTable = database.getTable(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_NAME);

            bitcoinSpentTable.addUUIDFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_ID_COLUMN_NAME, spentID, DatabaseFilterType.EQUAL);

            bitcoinSpentTable.loadToMemory();

            for(DatabaseTableRecord record : bitcoinSpentTable.getRecords())
            {
                record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_EXCHANGE_RATE_COLUMN_NAME, String.valueOf(rate));

                bitcoinSpentTable.updateRecord(record);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Transaction table" + spentID.toString(), "");

        } catch (CantUpdateRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error",cantUpdateRecord,"Error update memo of Transaction " + spentID.toString(), "");
        } catch(Exception exception){
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    private final void deleteSpendings(UUID transactionId) throws CantStoreMemoException, CantFindTransactionException {
        try {
            // create the database objects
            DatabaseTable bitcoinSpentTable = database.getTable(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_NAME);

            bitcoinSpentTable.addUUIDFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);

            bitcoinSpentTable.loadToMemory();


            for(DatabaseTableRecord record : bitcoinSpentTable.getRecords())
            {
                bitcoinSpentTable.deleteRecord(record);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantFindTransactionException("Transaction Memo Update Error",cantLoadTableToMemory,"Error load Spending table", "");

        } catch (CantDeleteRecordException cantUpdateRecord) {
            throw new CantStoreMemoException("Transaction Memo Update Error",cantUpdateRecord,"Error deleting Spendings ", "");
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
            bitcoinwalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

            bitcoinwalletTable.loadToMemory();

            // Read record data and create transactions list
            for(DatabaseTableRecord record : bitcoinwalletTable.getRecords()){
                record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_MEMO_COLUMN_NAME,memo);
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

    public BitcoinLossProtectedWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                                      BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            String query = "SELECT COUNT(*), " +
                    BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME +
                    ", SUM(" +
                    BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_AMOUNT_COLUMN_NAME +
                    ") FROM " +
                    BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_NAME +
                    " WHERE " +
                    BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME +
                    " = '" +
                    balanceType.getCode() +
                    "' AND (" +
                    BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME +
                    " = '" +
                    actorPublicKey +
                    "' OR " +
                    BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_COLUMN_NAME +
                    " = '" +
                    actorPublicKey +
                    "') GROUP BY " +
                    BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME;

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

            return new BitcoinWalletLossProtectedWalletTransactionSummary(sentTransactionsNumber, receivedTransactionsNumber, sentAmount, receivedAmount);

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
        bitCoinWalletTable.addUUIDFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        bitCoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        bitCoinWalletTable.loadToMemory();
        return !bitCoinWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getBitcoinWalletTable(){
        return database.getTable(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_NAME);
    }

    private long calculateAvailableRunningBalance(final long transactionAmount,BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceRecordException{
        return  getCurrentAvailableBalance(blockchainNetworkType) + transactionAmount;
    }

    private long calculateBookRunningBalance(final long transactionAmount,BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceRecordException{
        return  getCurrentBookBalance(blockchainNetworkType) + transactionAmount;
    }

    private long getCurrentBookBalance() throws CantGetLossProtectedBalanceRecordException {
        return getCurrentBalance(BalanceType.BOOK);
    }
    private long getCurrentBookBalance(BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceRecordException{
        return getCurrentBalance(BalanceType.BOOK, blockchainNetworkType);
    }

    private long getCurrentAvailableBalance() throws CantGetLossProtectedBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE);
    }
    private long getCurrentAvailableBalance(BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceRecordException{
        return getCurrentBalance(BalanceType.AVAILABLE ,blockchainNetworkType);
    }

    private long getCurrentBalance(final BalanceType balanceType) throws CantGetLossProtectedBalanceRecordException {
        if (balanceType == BalanceType.AVAILABLE)
            return getBalancesRecord().getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        else
            return getBalancesRecord().getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
    }

    private long getCurrentBalance(final BalanceType balanceType, BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceRecordException {
        if (balanceType == BalanceType.AVAILABLE)
            return getBalancesRecord(blockchainNetworkType).getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        else
            return getBalancesRecord(blockchainNetworkType).getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
    }


    private DatabaseTableRecord getBalancesRecord() throws CantGetLossProtectedBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetLossProtectedBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTableRecord getBalancesRecord(BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();

            balancesTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE,blockchainNetworkType.getCode(),DatabaseFilterType.EQUAL);

            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetLossProtectedBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTable getBalancesTable(){
        return database.getTable(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_NAME);
    }

    private void executeTransaction(final BitcoinLossProtectedWalletTransactionRecord transactionRecord, final TransactionType transactionType, final BalanceType balanceType, final long availableRunningBalance, final long bookRunningBalance, final String exchangeRate) throws CantExecuteLossProtectedBitcoinTransactionException {
        try{
            DatabaseTableRecord bitcoinWalletRecord = constructBitcoinWalletRecord(transactionRecord, balanceType,transactionType ,availableRunningBalance, bookRunningBalance, exchangeRate);
            DatabaseTableRecord balanceRecord = constructBalanceRecord(availableRunningBalance, bookRunningBalance, transactionRecord.getBlockchainNetworkType());

            BitcoinWalletLossProtectedWalletDaoTransaction bitcoinWalletBasicWalletDaoTransaction = new BitcoinWalletLossProtectedWalletDaoTransaction(database);

            //Balance table - add filter by network type,
            DatabaseTable balanceTable = getBalancesTable();
            balanceTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE,transactionRecord.getBlockchainNetworkType().getCode(),DatabaseFilterType.EQUAL);
            bitcoinWalletBasicWalletDaoTransaction.executeTransaction(getBitcoinWalletTable(), bitcoinWalletRecord, balanceTable, balanceRecord);
        } catch(CantGetLossProtectedBalanceRecordException | CantLoadTableToMemoryException exception){
            throw new CantExecuteLossProtectedBitcoinTransactionException(CantExecuteLossProtectedBitcoinTransactionException.DEFAULT_MESSAGE, exception, null, "Check the cause");
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
    private DatabaseTableRecord constructBitcoinWalletRecord(final BitcoinLossProtectedWalletTransactionRecord transactionRecord,
                                                             final BalanceType balanceType,
                                                             final TransactionType transactionType,
                                                             final long availableRunningBalance,
                                                             final long bookRunningBalance,
                                                             final String exchangeRate) throws CantLoadTableToMemoryException {

        DatabaseTableRecord record = getBitcoinWalletEmptyRecord();

        record.setUUIDValue  (BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ID_COLUMN_NAME                       , UUID.randomUUID());
        record.setUUIDValue  (BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME         , transactionRecord.getTransactionId());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_AMOUNT_COLUMN_NAME, transactionRecord.getAmount());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_MEMO_COLUMN_NAME, transactionRecord.getMemo());
        record.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME, transactionRecord.getTimestamp());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME, transactionRecord.getTransactionHash());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME             , transactionRecord.getAddressFrom().getAddress());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME               , transactionRecord.getAddressTo().getAddress());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME             , balanceType.getCode());
        record.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue  (BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME     , bookRunningBalance);
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME, transactionRecord.getActorFromPublicKey());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_COLUMN_NAME                 , transactionRecord.getActorToPublicKey());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME          , transactionRecord.getActorFromType().getCode());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME            , transactionRecord.getActorToType().getCode());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_NETWORK_TYPE                 , transactionRecord.getBlockchainNetworkType().getCode());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME        , TransactionState.COMPLETE.getCode());
        record.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME, exchangeRate);

        return record;
    }

    private DatabaseTableRecord getBitcoinWalletEmptyRecord() throws CantLoadTableToMemoryException{
        return getBitcoinWalletTable().getEmptyRecord();
    }

    private DatabaseTableRecord constructBalanceRecord(final long availableRunningBalance, final long bookRunningBalance, BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceRecordException{
        DatabaseTableRecord record = getBalancesRecord(blockchainNetworkType);
        record.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        return record;
    }

    // Read record data and create transactions list
    private List<BitcoinLossProtectedWalletTransaction> createTransactionList(final Collection<DatabaseTableRecord> records){

        List<BitcoinLossProtectedWalletTransaction> transactions = new ArrayList<>();

        for(DatabaseTableRecord record : records)
            transactions.add(constructBitcoinWalletTransactionFromRecord(record));

        return transactions;
    }

    private BitcoinLossProtectedWalletTransaction constructBitcoinWalletTransactionFromRecord(final DatabaseTableRecord record){

        UUID transactionId              = record.getUUIDValue(  BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME);
        String transactionHash          = record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME);
        TransactionType transactionType = TransactionType.getByCode(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME));
        CryptoAddress addressFrom       = new CryptoAddress();
        addressFrom.setAddress(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME));
        CryptoAddress addressTo         = new CryptoAddress();
        addressTo.setAddress(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME));
        String actorFromPublicKey       = record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME);
        String actorToPublicKey         = record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_COLUMN_NAME);
        Actors actorFromType            = Actors.getByCode(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME));
        Actors actorToType              = Actors.getByCode(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME));
        BalanceType balanceType         = BalanceType.getByCode(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME));
        long amount                     = record.getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_AMOUNT_COLUMN_NAME);
        long runningBookBalance         = record.getLongValue(  BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance    = record.getLongValue(  BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp                  = record.getLongValue(  BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME);
        String memo                     = record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_MEMO_COLUMN_NAME);
        BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getByCode(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_NETWORK_TYPE));

        double exchangeRate = Double.parseDouble(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME));

        TransactionState transactionState = null;
        try {
            transactionState = TransactionState.getByCode(record.getStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        return new BitcoinLossProtecdWalletTransactionWrapper(transactionId, transactionHash, transactionType, addressFrom, addressTo,
                actorFromPublicKey, actorToPublicKey, actorFromType, actorToType, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo, blockchainNetworkType,transactionState,exchangeRate);
    }

    public void deleteTransaction(UUID transactionID)throws CantFindTransactionException{

        try {
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

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

    public BitcoinLossProtectedWalletTransaction selectTransaction(UUID transactionID)throws CantFindTransactionException{

        try {

            BitcoinLossProtectedWalletTransaction bitcoinWalletTransaction = null;
            // create the database objects
            DatabaseTable bitcoinwalletTable = getBitcoinWalletTable();
            /**
             *  I will load the information of table into a memory structure, filter for transaction id
             */
            bitcoinwalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME, transactionID.toString(), DatabaseFilterType.EQUAL);

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




    private  List<DatabaseTableRecord> getRecordsLessThanRate(BlockchainNetworkType blockchainNetworkType,String exchangeRate) throws CantGetTransactionsRecordException
    {
        try {
        DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

        //I look for lower income records to the current share price

        bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, BalanceType.AVAILABLE.getCode(), DatabaseFilterType.EQUAL);
        bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, TransactionType.CREDIT.getCode(), DatabaseFilterType.EQUAL);
        bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME, String.valueOf(exchangeRate), DatabaseFilterType.LESS_OR_EQUAL_THAN);
        bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);

        bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.loadToMemory();

        return bitcoinWalletTable.getRecords();

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetTransactionsRecordException("Get Records Less ThanRate",e,"CantLoadTableToMemoryException" , "");

        } catch (Exception e) {
            throw new CantGetTransactionsRecordException("Get Records Less ThanRate",FermatException.wrapException(e),"unknown Error", "");

        }
    }


    public void insertSpending(BitcoinLossProtectedWalletTransactionRecord transactionRecord,String exchangeRate) throws CantInsertSpendingException {

        try {

            long timestamp = System.currentTimeMillis();
            List<DatabaseTableRecord> transactionsRecords =

            //busco transacciones para gastar sin perdidas, sino tengo voy a buscar todas
             transactionsRecords = getRecordsLessThanRate(transactionRecord.getBlockchainNetworkType(),exchangeRate);

            if(transactionsRecords.size() == 0)
              transactionsRecords = getCreditTransactionsRecords(transactionRecord.getBlockchainNetworkType());

            //gasto primero de las transacciones con mayor valor de rate primero
            //tengo que traer lo que llevo gastado de cada transaccion para saber si uso eso o paso a otra

            //tomo todas las transacciones e inserto registros de gastos como perdida

            DatabaseTable spendingTable = database.getTable(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_NAME);
            long recordAmount = 0;

            recordAmount = transactionRecord.getAmount();

            if(transactionsRecords.size() > 0)
            {
                long rest = 0;
                for(DatabaseTableRecord record : transactionsRecords){


                    long spent = getTotalTransactionsSpending(record.getUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME));
                    long spendingAmount =  record.getLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_AMOUNT_COLUMN_NAME) - spent;

                    if(spendingAmount  != 0)
                        if(spendingAmount >= recordAmount)
                        {
                            DatabaseTableRecord recordToInsert = spendingTable.getEmptyRecord();

                            UUID spentId = UUID.randomUUID();
                            recordToInsert.setUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TRANSACTION_ID_COLUMN_NAME, (record.getUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME)));
                            recordToInsert.setUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_ID_COLUMN_NAME, spentId);
                            recordToInsert.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_TIME_STAMP_COLUMN_NAME, timestamp);
                            recordToInsert.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_BTC_SPENT_COLUMN_NAME, recordAmount);
                            recordToInsert.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_EXCHANGE_RATE_COLUMN_NAME, exchangeRate);
                            recordToInsert.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_RUNNING_NETWORK_TYPE, transactionRecord.getBlockchainNetworkType().getCode());

                            spendingTable.insertRecord(recordToInsert);

                            //update exchange rate
                            setSpendingRate(spentId);

                            recordAmount = 0;

                            return;
                        }
                        else
                        {

                            UUID spentId = UUID.randomUUID();

                            DatabaseTableRecord recordToInsert = spendingTable.getEmptyRecord();

                            recordToInsert.setUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TRANSACTION_ID_COLUMN_NAME, (record.getUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME)));
                            recordToInsert.setUUIDValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_ID_COLUMN_NAME, spentId);
                            recordToInsert.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_TIME_STAMP_COLUMN_NAME, timestamp);
                            recordToInsert.setLongValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_BTC_SPENT_COLUMN_NAME, spendingAmount);
                            recordToInsert.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_EXCHANGE_RATE_COLUMN_NAME, exchangeRate);
                            recordToInsert.setStringValue(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_SPENT_TABLE_RUNNING_NETWORK_TYPE, transactionRecord.getBlockchainNetworkType().getCode());

                            spendingTable.insertRecord(recordToInsert);

                            //me sobran btc tengo que gastarlos de otra transaccion
                            recordAmount = recordAmount - spendingAmount;

                            //update exchange rate
                            setSpendingRate(spentId);

                        }

                }

            }


        } catch (CantInsertRecordException e) {
            throw new CantInsertSpendingException("Cant Insert Spending Exception",e,"CantInsertRecordException", "");

        } catch (CantGetTransactionsRecordException e) {
            throw new CantInsertSpendingException("Cant Insert Spending Exception",e,"CantGetTransactionsRecordException", "");
        } catch (Exception e) {
            throw new CantInsertSpendingException("Cant Insert Spending Exception",e,"unknown error", "");
        }
    }




    private  List<DatabaseTableRecord> getCreditTransactionsRecords(BlockchainNetworkType blockchainNetworkType) throws CantGetTransactionsRecordException
    {
        try {
            DatabaseTable bitcoinWalletTable = getBitcoinWalletTable();

            //I look for lower income records to the current share price

            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME, BalanceType.AVAILABLE.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME, TransactionType.CREDIT.getCode(), DatabaseFilterType.EQUAL);
            bitcoinWalletTable.addStringFilter(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_RUNNING_NETWORK_TYPE, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);

            bitcoinWalletTable.addFilterOrder(BitcoinLossProtectedWalletDatabaseConstants.LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

            bitcoinWalletTable.loadToMemory();

            return bitcoinWalletTable.getRecords();

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetTransactionsRecordException("Get Records Less ThanRate",e,"CantLoadTableToMemoryException" , "");

        } catch (Exception e) {
            throw new CantGetTransactionsRecordException("Get Records Less ThanRate",FermatException.wrapException(e),"unknown Error", "");

        }
    }


}