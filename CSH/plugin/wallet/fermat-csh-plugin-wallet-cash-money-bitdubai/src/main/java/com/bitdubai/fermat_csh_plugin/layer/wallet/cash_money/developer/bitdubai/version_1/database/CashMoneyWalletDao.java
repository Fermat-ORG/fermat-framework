package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
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
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoneyBalanceRecord;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoney;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoneyBalance;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCashMoneyBalanceRecordException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetBalancesRecord;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantGetCashMoneyBalance;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyWalletDatabaseException;

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
    public void addCashMoney(          String cash_transaction_id,
                                       String public_key_customer,
                                       String public_key_broker,
                                       String balance_type,
                                       String transaction_type,
                                       String amount,
                                       String cash_currency_type,
                                       String cash_reference,
                                       String running_book_balance,
                                       String running_available_balance,
                                       String status) throws CantAddCashMoney{
        try {
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TABLE_NAME);
        DatabaseTableRecord record =  table.getEmptyRecord();

        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME, cash_transaction_id);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME,public_key_customer);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME, public_key_broker);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME, balance_type);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME, transaction_type);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME,amount);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME,cash_currency_type);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_REFERENCE_COLUMN_NAME,cash_reference);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME,running_book_balance);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME,running_available_balance);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_STATUS_COLUMN_NAME,status);


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
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_BALANCE_CREDIT_COLUMN_NAME,cashMoneyCredit);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_BALANCE_BALANCE_COLUMN_NAME,cashMoneyBalance);
        record.setStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_BALANCE_TIMESTAMP_COLUMN_NAME,time);


        table.insertRecord(record);
        database.closeDatabase();
    } catch (CantInsertRecordException cantInsertRecordException) {
        throw new CantAddCashMoneyBalance(CantAddCashMoneyBalance.DEFAULT_MESSAGE,cantInsertRecordException,"Cant Add Cash Money Balance","Cant Insert Record Exception");
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
     * @return
     * @throws CantGetCashMoneyBalance
     */
    public double getCashMoneyBalance(BalanceType balanceType) throws CantGetCashMoneyBalance {
        double balanceAmount = 0;
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecordList()){
                String stringAmununt = record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME);
                balanceAmount += Double.valueOf(stringAmununt);
            }
        } else {
            for (DatabaseTableRecord record : getBalancesRecordList()){
                String stringAmununt = record.getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME);
                balanceAmount += Double.valueOf(stringAmununt);
            }
        }
        return balanceAmount;
    }

    /**
     *
     * @param balanceType
     * @return
     * @throws CantGetBalancesRecord
     */
    private Double getCurrentBalance(BalanceType balanceType) throws CantGetBalancesRecord {
        if (balanceType == balanceType.AVAILABLE)
            return Double.valueOf(getBalancesRecord().getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME));
        else
            return Double.valueOf(getBalancesRecord().getStringValue(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME));
        }

    /**
     *
     * @return
     * @throws CantGetBalancesRecord
     */
         private DatabaseTableRecord getBalancesRecord() throws CantGetBalancesRecord {
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
    //TODO AGREGAR EXCEPCIONES.
    private List<DatabaseTableRecord> getBalancesRecordList(){
        DatabaseTable totalBalancesTable = null;
        try {
            totalBalancesTable = this.database.getTable(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_TABLE_NAME);
            totalBalancesTable.loadToMemory();

        } catch (CantLoadTableToMemoryException exception) {
            //TODO AGREGAR EXCEPCIONES.
        }
        return totalBalancesTable.getRecords();
    }

}
