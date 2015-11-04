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
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalanceRecord;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyTransaction;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoney;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoneyBalance;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoneyBalanceRecordException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoneyTotalBalanceException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetBalancesRecord;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyBalance;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyListException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyTotalBalanceRecordList;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCurrentBalanceException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyWalletDatabaseException;
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
     * @param databaseName
     * @throws CantInitializeCashMoneyWalletDatabaseException
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeCashMoneyWalletDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, databaseName);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCashMoneyWalletDatabaseException(
                    CantInitializeCashMoneyWalletDatabaseException.DEFAULT_MESSAGE,
                    cantOpenDatabaseException,
                    "initializeDatabase",
                    "Cant Initialize CashMoney WalletDatabase Exception - Cant Open Database Exception");
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
                database = cashMoneyWalletDatabaseFactory.createDatabase(ownerId, databaseName);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCashMoneyWalletDatabaseException(
                        CantInitializeCashMoneyWalletDatabaseException.DEFAULT_MESSAGE,
                        cantCreateDatabaseException,
                        "initializeDatabase",
                        "Cant Initialize CashMoney WalletDatabase Exception - Cant Create Database Exception");
            }
        }
    }
    public void addCashMoney(
                             CashMoneyTransaction cashMoneyTransaction,
                             BalanceType balanceType,
                             TransactionType transactionType,
                             CashCurrencyType cashCurrencyType
    ) throws CantAddCashMoney{
        try {
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TABLE_NAME);
        DatabaseTableRecord record =  table.getEmptyRecord();

        record.setUUIDValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME, cashMoneyTransaction.getCashTransactionId());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, cashMoneyTransaction.getPublicKeyActorFrom());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME, cashMoneyTransaction.getPublicKeyActorTo());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode());
        record.setDoubleValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME, cashMoneyTransaction.getAmount());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME, cashCurrencyType.getCode());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_REFERENCE_COLUMN_NAME, cashMoneyTransaction.getCashReference());
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME, cashMoneyTransaction.getRunningBookBalance());
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, cashMoneyTransaction.getRunningAvailableBalance());
        record.setLongValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TIMESTAMP_COLUMN_NAME, cashMoneyTransaction.getTimestamp());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_STATUS_COLUMN_NAME,cashMoneyTransaction.getStatus().getCode());

            table.insertRecord(record);
            database.closeDatabase();
        } catch (CantInsertRecordException cantInsertRecordException) {
            throw new CantAddCashMoney(CantAddCashMoney.DEFAULT_MESSAGE,cantInsertRecordException,"Cant Add Cash Money","Cant Insert Record Exception");
        }
    }
    public void addCashMoneyBalance( String cash_transaction_id, String cashMoneyDebit, String cashMoneyCredit, String cashMoneyBalance, String time) throws CantAddCashMoneyBalance{
    try{
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TABLE_NAME);
        DatabaseTableRecord record =  table.getEmptyRecord();

        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_BALANCE_ID_COLUMN_NAME, cash_transaction_id);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_BALANCE_DEBIT_COLUMN_NAME, cashMoneyDebit);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_BALANCE_CREDIT_COLUMN_NAME, cashMoneyCredit);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_BALANCE_BALANCE_COLUMN_NAME, cashMoneyBalance);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_BALANCE_TIMESTAMP_COLUMN_NAME,time);

        table.insertRecord(record);
        database.closeDatabase();
    } catch (CantInsertRecordException cantInsertRecordException) {
        throw new CantAddCashMoneyBalance(CantAddCashMoneyBalance.DEFAULT_MESSAGE,cantInsertRecordException,"Cant Add Cash Money Balance","Cant Insert Record Exception");
    }
    }
    /**
     *
     * @param cashMoneyTransaction
     * @param name
     * @param description
     * @throws CantAddCashMoneyTotalBalanceException
     */
    public void addCashMoneyTotalBalance(CashMoneyTransaction cashMoneyTransaction,
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
           throw new CantAddCashMoneyTotalBalanceException(CantAddCashMoneyTotalBalanceException.DEFAULT_MESSAGE,e,"Cant Add CashMoney Total Balance","Cant Insert Record Exception");
        }
    }
    /**
     *
     * @param cashMoneyBalanceRecord
     * @param balanceType
     * @throws CantAddDebitException
     */
    public void addDebit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantAddDebitException {
        try {
        double availableAmount;
        double availableRunningBalance;
        double bookAmount;
        double bookRunningBalance;
        if (balanceType == BalanceType.AVAILABLE){
            availableAmount  = cashMoneyBalanceRecord.getAmount();
            availableRunningBalance = getCurrentBalance(BalanceType.AVAILABLE) + (-availableAmount);
            addCashMoneyBalanceRecord(cashMoneyBalanceRecord,balanceType,TransactionType.DEBIT,availableRunningBalance);
        }
        if (balanceType == BalanceType.BOOK){
            bookAmount  = cashMoneyBalanceRecord.getAmount();
            bookRunningBalance = getCurrentBalance(BalanceType.BOOK) + (-bookAmount);
            addCashMoneyBalanceRecord(cashMoneyBalanceRecord,balanceType,TransactionType.DEBIT,bookRunningBalance);
        }

        } catch (CantAddCashMoneyBalanceRecordException e) {
           throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE,e,"Cant Add Debit Exception","Cant Add CashMoney Balance Record Exception");
        } catch (CantGetBalancesRecord cantGetBalancesRecord) {
            throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE,cantGetBalancesRecord,"Cant Add Debit Exception","Cant Get Balances Record");
        } catch (CantGetCurrentBalanceException e) {
            throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE,e,"Cant Add Debit Exception","Cant Get Current Balance Exception");
        }
    }
    /**
     *
     * @param cashMoneyBalanceRecord
     * @param balanceType
     * @throws CantAddCreditException
     */
    public void addCredit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantAddCreditException {
        try {
            double availableAmount;
            double availableRunningBalance;
            double bookAmount;
            double bookRunningBalance;
            if (balanceType == BalanceType.AVAILABLE){
                availableAmount  = cashMoneyBalanceRecord.getAmount();
                availableRunningBalance = getCurrentBalance(BalanceType.AVAILABLE) + (-availableAmount);
                addCashMoneyBalanceRecord(cashMoneyBalanceRecord,balanceType,TransactionType.CREDIT,availableRunningBalance);
            }
            if (balanceType == BalanceType.BOOK){
                bookAmount  = cashMoneyBalanceRecord.getAmount();
                bookRunningBalance = getCurrentBalance(BalanceType.BOOK) + (-bookAmount);
                addCashMoneyBalanceRecord(cashMoneyBalanceRecord,balanceType,TransactionType.CREDIT,bookRunningBalance);
            }

        } catch (CantAddCashMoneyBalanceRecordException e) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE,e,"Cant Add Debit Exception","Cant Add CashMoney Balance Record Exception");
        } catch (CantGetBalancesRecord cantGetBalancesRecord) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE,cantGetBalancesRecord,"Cant Add Debit Exception","Cant Get Balances Record");
        } catch (CantGetCurrentBalanceException e) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE,e,"Cant Add Credit Exception","Cant Get Current Balance Exception");
        }
    }
    /**
     *
     * @param cashMoneyBalanceRecord
     * @param balanceType
     * @param transactionType
     */
    public void addCashMoneyBalanceRecord(
                                            CashMoneyBalanceRecord cashMoneyBalanceRecord,
                                            BalanceType balanceType,
                                            TransactionType transactionType,
                                            double amount
    ) throws CantAddCashMoneyBalanceRecordException
    {
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_TABLE_NAME);
        DatabaseTableRecord record =  table.getEmptyRecord();

        record.setUUIDValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_CASH_TRANSACTION_ID_COLUMN_NAME, cashMoneyBalanceRecord.getCashTransactionId());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_PUBLIC_KEY_ACTOR_FROM, cashMoneyBalanceRecord.getPublicKeyActorFrom());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_PUBLIC_KEY_ACTOR_TO, cashMoneyBalanceRecord.getPublicKeyActorTo());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_STATUS, cashMoneyBalanceRecord.getStatus().getCode());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_BALANCE_TYPE, balanceType.getCode());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_TRANSACTION_TYPE, transactionType.getCode());
        record.setDoubleValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_AMAUNT, amount);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_CASH_CURRENCY_TYPE, cashMoneyBalanceRecord.getCashCurrencyType().getCode());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_CASH_REFERENCE, cashMoneyBalanceRecord.getCashReference());
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_MEMO, cashMoneyBalanceRecord.getMemo());

        try {
            table.insertRecord(record);
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddCashMoneyBalanceRecordException(CantAddCashMoneyBalanceRecordException.DEFAULT_MESSAGE,e,"Cant Add CashMoney Balance Record Exception","Cant Insert Record Exception");
        }
    }

    /**
     *
     * @param balanceType
     * @param max
     * @param offset
     * @return
     * @throws CantTransactionCashMoneyException
     */
    public List<CashMoneyTransaction> getTransactions(BalanceType balanceType, int max, int offset )throws CantTransactionCashMoneyException {

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
                   "Cant Transaction CashMoney Exception",
                   "Cant Load Table To Memory Exception"
           );
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
            throw new CantGetCashMoneyTotalBalanceRecordList(CantGetCashMoneyTotalBalanceRecordList.DEFAULT_MESSAGE, exception, "Cant Get CashMoney Total Balance Record List", "Cant Load Table T oMemory Exception");
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
              throw new CantGetCashMoneyListException(CantGetCashMoneyListException.DEFAULT_MESSAGE,e,"Cant Get CashMoney List Exception","Cant Load Table To Memory Exception");
            }
        }

    private List<CashMoneyTransaction> createTransactionList(Collection<DatabaseTableRecord> records){
        List<CashMoneyTransaction> cashMoneyTransactionsList = new ArrayList<>();

        for(DatabaseTableRecord record : records)
            cashMoneyTransactionsList.add(constructCashMoneyTransactionFromRecord(record));

        return cashMoneyTransactionsList;
    }

    private CashMoneyTransaction constructCashMoneyTransactionFromRecord(DatabaseTableRecord record){
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

}
