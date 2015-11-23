package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoney;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoneyTotalBalanceException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetBalancesRecord;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyBalance;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyListException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyTotalBalanceRecordList;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCurrentBalanceException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyWalletDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure.CashMoneyConstructor;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure.CashMoneyManagerImp;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure.TransactionCashMoney;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by yordin on 01/10/15.
 */
public class CashMoneyWalletDao {
    /**
     *  Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Database connection
     */
    Database database;

    public CashMoneyWalletDao (PluginDatabaseSystem pluginDatabaseSystem){
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     *
     * @param ownerId
     * @throws CantInitializeCashMoneyWalletDatabaseException
     */
    public void initializeDatabase(UUID ownerId) throws CantInitializeCashMoneyWalletDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, CashMoneyWalletDatabaseConstants.DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCashMoneyWalletDatabaseException(
                    CantInitializeCashMoneyWalletDatabaseException.DEFAULT_MESSAGE,
                    cantOpenDatabaseException,
                    "initializeDatabase",
                    "Cant Initialize CashMoneyConstructor WalletDatabase Exception - Cant Open Database Exception");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CashMoneyWalletDatabaseFactory cashMoneyWalletDatabaseFactory = new CashMoneyWalletDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = cashMoneyWalletDatabaseFactory.createDatabase(ownerId, CashMoneyWalletDatabaseConstants.DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCashMoneyWalletDatabaseException(
                        CantInitializeCashMoneyWalletDatabaseException.DEFAULT_MESSAGE,
                        cantCreateDatabaseException,
                        "initializeDatabase",
                        "Cant Initialize CashMoneyConstructor WalletDatabase Exception - Cant Create Database Exception");
            }
        }
    }
    /*public void addCashMoney(
                             CashMoneyBalanceRecord cashMoneyBalanceRecord,
                             BalanceType balanceType,
                             long runingBookBalance,
                             long runingAvalibleBalance
    ) throws CantAddCashMoney{
        try {
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TABLE_NAME);
        DatabaseTableRecord record =  table.getEmptyRecord();

        record.setUUIDValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME, cashMoneyBalanceRecord.getCashTransactionId());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, cashMoneyBalanceRecord.getPublicKeyActorFrom());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME, cashMoneyBalanceRecord.getPublicKeyActorTo());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME, cashMoneyBalanceRecord.getTransactionType().getCode());
        record.setDoubleValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME, cashMoneyBalanceRecord.getAmount());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME, cashMoneyBalanceRecord.getCashCurrencyType().getCode());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_REFERENCE_COLUMN_NAME, cashMoneyBalanceRecord.getCashReference());
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME, runingBookBalance);
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME,runingAvalibleBalance);
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TIMESTAMP_COLUMN_NAME, cashMoneyBalanceRecord.getTimestamp());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_MEMO_COLUMN_NAME, cashMoneyBalanceRecord.getMemo());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_STATUS_COLUMN_NAME,cashMoneyBalanceRecord.getStatus().getCode());

            table.insertRecord(record);
            database.closeDatabase();
        } catch (CantInsertRecordException cantInsertRecordException) {
            throw new CantAddCashMoney(CantAddCashMoney.DEFAULT_MESSAGE,cantInsertRecordException,"Cant Add Cash Money","Cant Insert Record Exception");
        }
    }*/
    public CashMoneyWallet registerCashMoney(
            String cashTransactionId,
            String publicKeyActorFrom,
            String publicKeyActorTo,
            String status,
            String balanceType,
            String transactionType,
            double amount,
            String cashCurrencyType,
            String cashReference,
            long runningBookBalance,
            long runningAvailableBalance,
            long timestamp,
            String memo) throws CantCreateCashMoneyException {
        try {
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TABLE_NAME);
        DatabaseTableRecord record =  table.getEmptyRecord();

            CashMoneyWallet cashMoney = cashMoney(record,
                cashTransactionId,
                publicKeyActorFrom,
                publicKeyActorTo,
                status,
                balanceType,
                transactionType,
                amount,
                cashCurrencyType,
                cashReference,
                runningBookBalance,
                runningAvailableBalance,
                timestamp,
                memo);

            table.insertRecord(record);

            database.closeDatabase();
            return  cashMoney;
        } catch (CantInsertRecordException e) {
            throw new CantCreateCashMoneyException(CantCreateCashMoneyException.DEFAULT_MESSAGE,e,"Cant Create CashMoneyWallet Exception","Cant Insert Record Exception");
        }
    }
    /**
     *
     * @param cashMoneyTransaction
     * @param name
     * @param description
     * @throws CantAddCashMoneyTotalBalanceException
     */
    public void addCashMoneyTotalBalance(CashMoneyWalletTransaction cashMoneyTransaction,
                                         String name,
                                         String description) throws CantAddCashMoneyTotalBalanceException
    {
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_TABLE_NAME);
        DatabaseTableRecord record =  table.getEmptyRecord();

        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_WALLET_KEY_BROKER_COLUMN_NAME, cashMoneyTransaction.getPublicKeyActorFrom());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_PUBLIC_KEY_BROKER_COLUMN_NAME, cashMoneyTransaction.getPublicKeyActorTo());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_CASH_CURRENCY_TYPE_COLUMN_NAME, cashMoneyTransaction.getCashCurrencyType().getCode());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_NAME_COLUMN_NAME,name);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_DESCRIPTION_COLUMN_NAME, description);
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME, cashMoneyTransaction.getRunningAvailableBalance());
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME, cashMoneyTransaction.getRunningBookBalance());

        try {
            table.insertRecord(record);
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
           throw new CantAddCashMoneyTotalBalanceException(CantAddCashMoneyTotalBalanceException.DEFAULT_MESSAGE,e,"Cant Add CashMoneyConstructor Total Balance","Cant Insert Record Exception");
        }
    }
    /**
     *
     * @param cashMoneyBalanceRecord
     * @param balanceType
     * @throws CantAddDebitException
     */
    /*public void addDebit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantAddDebitException {
        try {
        double availableAmount;
        double bookAmount;
        long runningAvailableBalance = 0;
        long runningBookBalance = 0;
        if (balanceType == BalanceType.AVAILABLE){
            availableAmount  = cashMoneyBalanceRecord.getAmount();
            runningAvailableBalance = (long) (getCurrentBalance(BalanceType.AVAILABLE) + (-availableAmount));
            addCashMoney(cashMoneyBalanceRecord, balanceType, runningBookBalance,runningAvailableBalance);

        }
        if (balanceType == BalanceType.BOOK){
            bookAmount  = cashMoneyBalanceRecord.getAmount();
            runningBookBalance = (long) (getCurrentBalance(BalanceType.BOOK) + (-bookAmount));
            addCashMoney(cashMoneyBalanceRecord, balanceType, runningBookBalance, runningAvailableBalance);
        }

        } catch (CantGetBalancesRecord cantGetBalancesRecord) {
            throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE,cantGetBalancesRecord,"Cant Add Debit Exception","Cant Get Balances Record");
        } catch (CantGetCurrentBalanceException e) {
            throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE,e,"Cant Add Debit Exception","Cant Get Current Balance Exception");
        } catch (CantAddCashMoney cantAddCashMoney) {
           throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE,cantAddCashMoney,"Cant Add Debit Exception","Cant Add CashMoneyConstructor");
        }
    }*/
    /**
     *
     * @param cashMoneyBalanceRecord
     * @param balanceType
     * @throws CantAddCreditException
     */
    /*public void addCredit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantAddCreditException {
        try {
            double availableAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;
            if (balanceType == BalanceType.AVAILABLE){
                availableAmount  = cashMoneyBalanceRecord.getAmount();
                runningAvailableBalance = (long) (getCurrentBalance(BalanceType.AVAILABLE) + (-availableAmount));
                addCashMoney(cashMoneyBalanceRecord, balanceType, runningBookBalance, runningAvailableBalance);

            }
            if (balanceType == BalanceType.BOOK){
                bookAmount  = cashMoneyBalanceRecord.getAmount();
                runningBookBalance = (long) (getCurrentBalance(BalanceType.BOOK) + (-bookAmount));
                addCashMoney(cashMoneyBalanceRecord, balanceType, runningBookBalance, runningAvailableBalance);
            }

        }catch (CantGetBalancesRecord cantGetBalancesRecord) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE,cantGetBalancesRecord,"Cant Add Debit Exception","Cant Get Balances Record");
        } catch (CantGetCurrentBalanceException e) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE,e,"Cant Add Credit Exception","Cant Get Current Balance Exception");
        } catch (CantAddCashMoney cantAddCashMoney) {
           throw  new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE,cantAddCashMoney,"Cant Add Credit Exception","Cant Add Cash Money");
        }
    }*/
     /**
     *
     * @param balanceType
     * @param max
     * @param offset
     * @return
     * @throws CantTransactionCashMoneyException
     */
    public List<CashMoneyWalletTransaction> getTransactions(BalanceType balanceType, int max, int offset )throws CantTransactionCashMoneyException {

        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_TABLE_NAME);

        table.setStringFilter(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        table.setFilterTop(String.valueOf(max));
        table.setFilterOffSet(String.valueOf(offset));

        try {
            table.loadToMemory();
            return createTransactionList(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
           throw new CantTransactionCashMoneyException(
                   CantTransactionCashMoneyException.DEFAULT_MESSAGE,
                   e,
                   "Cant Transaction CashMoneyConstructor Exception",
                   "Cant Load Table To Memory Exception"
           );
        }
    }
    public List<CashMoneyWallet> getTransactionsCashMoney() throws CantCreateCashMoneyException {
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_TABLE_NAME);

        try {
            table.loadToMemory();
            return createTransactionCashMoneyList(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
           throw new CantCreateCashMoneyException(
                   CantCreateCashMoneyException.DEFAULT_MESSAGE,
                   e,
                   "Cant Create Cash Money Exception",
                   "Cant Load Table To Memory Exception");
        }

    }
    /**
     *
     * @return
     * @throws CantGetCashMoneyBalance
     */
    public double getAmauntCashMoneyTotalBalance(BalanceType balanceType) throws CantGetCashMoneyTotalBalanceRecordList, CantGetCashMoneyListException {
        double balanceAmount = 0;
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getCashMoneyTotalBalancesRecordList()){
                String stringAmununt = record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME);
                balanceAmount += Double.valueOf(stringAmununt);
            }
        } else {
            for (DatabaseTableRecord record : getCashMoneyTotalBalancesRecordList()){
                String stringAmununt = record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME);
                balanceAmount += Double.valueOf(stringAmununt);
            }
        }
        return balanceAmount;
    }
    public double getAmaunt() throws CantGetCashMoneyListException {
            double balanceAmount = 0;
            for (DatabaseTableRecord record : getCashMoneyList()){
                balanceAmount= record.getDoubleValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME);
                balanceAmount +=balanceAmount;
            }
        return balanceAmount;
    }
    /**
     *
     * @param balanceType
     * @return
     * @throws CantGetBalancesRecord
     */
    public double getCurrentBalance(BalanceType balanceType) throws CantGetCurrentBalanceException, CantGetBalancesRecord {
        if (balanceType == balanceType.AVAILABLE)
            return Double.valueOf(getCashMoneyTotalBalance().getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME));
        else
            return Double.valueOf(getCashMoneyTotalBalance().getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME));
        }

    /**
     *
     * @return
     * @throws CantGetBalancesRecord
     */
         private DatabaseTableRecord getCashMoneyTotalBalance() throws CantGetBalancesRecord {
        try {
            DatabaseTable balancesTable = database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_TABLE_NAME);
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalancesRecord(CantGetBalancesRecord.DEFAULT_MESSAGE,exception,"Cant Get Balances Record","Cant Load Table To Memory Exception");
        }
    }
    /**
     *
     * @return
     */
    private List<DatabaseTableRecord> getCashMoneyTotalBalancesRecordList() throws CantGetCashMoneyTotalBalanceRecordList, CantGetCashMoneyListException {
        try {
            DatabaseTable totalBalancesTable;
            totalBalancesTable = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_TABLE_NAME);
            totalBalancesTable.loadToMemory();
            return totalBalancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetCashMoneyTotalBalanceRecordList(CantGetCashMoneyTotalBalanceRecordList.DEFAULT_MESSAGE, exception, "Cant Get CashMoneyConstructor Total Balance Record List", "Cant Load Table T oMemory Exception");
        }
    }
        /**
         *
         */
        private List<DatabaseTableRecord> getCashMoneyList() throws CantGetCashMoneyListException{
            try {
                DatabaseTable totalBalancesTable;
                totalBalancesTable = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TABLE_NAME);
                totalBalancesTable.loadToMemory();
                return totalBalancesTable.getRecords();
            } catch (CantLoadTableToMemoryException e) {
              throw new CantGetCashMoneyListException(CantGetCashMoneyListException.DEFAULT_MESSAGE,e,"Cant Get CashMoneyConstructor List Exception","Cant Load Table To Memory Exception");
            }
        }

    private List<CashMoneyWalletTransaction> createTransactionList(Collection<DatabaseTableRecord> records){
        List<CashMoneyWalletTransaction> cashMoneyTransactionsList = new ArrayList<>();

        for(DatabaseTableRecord record : records)
            cashMoneyTransactionsList.add(constructCashMoneyTransactionFromRecord(record));

        return cashMoneyTransactionsList;
    }
    private List<CashMoneyWallet> createTransactionCashMoneyList(Collection<DatabaseTableRecord> records){
        List<CashMoneyWallet> cashMoneyConstructorTransactionsList = new ArrayList<>();

        for(DatabaseTableRecord record : records)
            cashMoneyConstructorTransactionsList.add(constructTransactionCashMoney(record));

        return cashMoneyConstructorTransactionsList;
    }

    /**
     *
     * @param record
     * @return
     */
    private CashMoneyWalletTransaction constructCashMoneyTransactionFromRecord(DatabaseTableRecord record){

        UUID cashTransactionId=record.getUUIDValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME);
        String walletKeyBroker=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_WALLET_KEY_BROKER_COLUMN_NAME);
        String publicKeyCustomer=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        String publicKeyBroker=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String balanceType=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME);
        String transactionType=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME);
        double amount=record.getDoubleValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME);
        String cashCurrencyType=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME);
        String cashReference=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_REFERENCE_COLUMN_NAME);
        long runningBookBalance=record.getLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance=record.getLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp=record.getLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TIMESTAMP_COLUMN_NAME);
        String memo=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_MEMO_COLUMN_NAME);
        String status=record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_STATUS_COLUMN_NAME);

        return new TransactionCashMoney(
                cashTransactionId,
                walletKeyBroker,
                publicKeyCustomer,
                publicKeyBroker,
                balanceType,
                transactionType,
                amount,
                cashCurrencyType,
                cashReference,
                runningBookBalance,
                runningAvailableBalance,
                timeStamp,
                memo,
                status);
    }
    private CashMoneyWallet constructTransactionCashMoney(DatabaseTableRecord record){

        UUID cashTransactionId          =record.getUUIDValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME);
        String walletKeyBroker          =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_WALLET_KEY_BROKER_COLUMN_NAME);
        String publicKeyCustomer        =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        String publicKeyBroker          =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String balanceType              =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME);
        String transactionType          =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME);
        double amount                   =record.getDoubleValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME);
        String cashCurrencyType         =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME);
        String cashReference            =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_REFERENCE_COLUMN_NAME);
        long runningBookBalance         =record.getLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance    =record.getLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp                  =record.getLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TIMESTAMP_COLUMN_NAME);
        String memo                     =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_MEMO_COLUMN_NAME);
        String status                   =record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_STATUS_COLUMN_NAME);

        return (CashMoneyWallet) new CashMoneyConstructor(
                cashTransactionId,
                walletKeyBroker,
                publicKeyCustomer,
                publicKeyBroker,
                balanceType,
                transactionType,
                amount,
                cashCurrencyType,
                cashReference,
                runningBookBalance,
                runningAvailableBalance,
                timeStamp,
                memo,
                status);
    }
    private CashMoneyWallet cashMoney(DatabaseTableRecord record,
                                String cashTransactionId,
                                String publicKeyActorFrom,
                                String publicKeyActorTo,
                                String status,
                                String balanceType,
                                String transactionType,
                                double amount,
                                String cashCurrencyType,
                                String cashReference,
                                long runningBookBalance,
                                long runningAvailableBalance,
                                long timestamp,
                                String memo){

        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME, cashTransactionId);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, publicKeyActorFrom);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyActorTo);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME, balanceType);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME, transactionType);
        record.setDoubleValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME, amount);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME, cashCurrencyType);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_REFERENCE_COLUMN_NAME, cashReference);
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME, runningBookBalance);
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME,runningAvailableBalance);
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TIMESTAMP_COLUMN_NAME, timestamp);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_MEMO_COLUMN_NAME, memo);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_STATUS_COLUMN_NAME,status);

        return (CashMoneyWallet) new CashMoneyManagerImp(
                 cashTransactionId,
                 publicKeyActorFrom,
                 publicKeyActorTo,
                 status,
                 balanceType,
                 transactionType,
                 amount,
                 cashCurrencyType,
                 cashReference,
                 runningBookBalance,
                 runningAvailableBalance,
                 timestamp,
                 memo);
    }

}
