package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.*;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure.BankMoneyBalanceList;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure.TransactionBankMoney;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by yordin on 01/10/15.
 */
public class BankMoneyWalletDao {

    /**
     * Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Database connection
     */
    Database database;

    /**
     *
     */


    public BankMoneyWalletDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    public BankMoneyWalletDao(Database database) {
        this.database = database;
    }

    /**
     * @param ownerId
     * @throws CantInitializeBankMoneyWalletDatabaseException
     */
    public void initializeDatabase(UUID ownerId) throws CantInitializeBankMoneyWalletDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, BankMoneyWalletDatabaseConstants.DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeBankMoneyWalletDatabaseException(
                    CantInitializeBankMoneyWalletDatabaseException.DEFAULT_MESSAGE,
                    cantOpenDatabaseException,
                    "initializeDatabase",
                    "Cant Initialize CashMoneyConstructor WalletDatabase Exception - Cant Open Database Exception");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            BankMoneyWalletDatabaseFactory cashMoneyWalletDatabaseFactory = new BankMoneyWalletDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = cashMoneyWalletDatabaseFactory.createDatabase(ownerId, BankMoneyWalletDatabaseConstants.DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeBankMoneyWalletDatabaseException(
                        CantInitializeBankMoneyWalletDatabaseException.DEFAULT_MESSAGE,
                        cantCreateDatabaseException,
                        "initializeDatabase",
                        "Cant Initialize BankMoneyWallet WalletDatabase Exception - Cant Create Database Exception");
            }
        }
    }

    public void addBankMoneyTransaction(BankMoneyTransactionRecord bankMoneyTransactionRecord,
                                        BalanceType balanceType,
                                        long runingBookBalance,
                                        long runingAvalibleBalance
    ) throws CantAddBankMoneyException {
        try {
            DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME, bankMoneyTransactionRecord.getBankTransactionId());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, bankMoneyTransactionRecord.getPublicKeyActorFrom());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME, bankMoneyTransactionRecord.getPublicKeyActorTo());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME, bankMoneyTransactionRecord.getTransactionType().getCode());
            record.setDoubleValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME, bankMoneyTransactionRecord.getAmount());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME, bankMoneyTransactionRecord.getBankCurrencyType().getCode());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_OPERATION_TYPE_COLUMN_NAME, bankMoneyTransactionRecord.getBankOperationType().getCode());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_DOCUMENT_REFERENCE_COLUMN_NAME, bankMoneyTransactionRecord.getBankDocumentReference());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN_NAME, bankMoneyTransactionRecord.getBankName());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, bankMoneyTransactionRecord.getBankAccountNumber());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_TYPE_COLUMN_NAME, bankMoneyTransactionRecord.getBankAccountType().getCode());
            record.setLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME, runingBookBalance);
            record.setLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, runingAvalibleBalance);
            record.setLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TIMESTAMP_COLUMN_NAME, bankMoneyTransactionRecord.getTimestamp());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_MEMO_COLUMN_NAME, bankMoneyTransactionRecord.getMemo());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_STATUS_COLUMN_NAME, bankMoneyTransactionRecord.getStatus().getCode());

            table.insertRecord(record);
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddBankMoneyException(CantAddBankMoneyException.DEFAULT_MESSAGE, e, "Cant Add Bank Money Exception", "Cant Insert Record Exception");
        }
    }

    public void addDebit(BankMoneyTransactionRecord bankMoneyTransactionRecord, BalanceType balanceType) throws CantAddDebitException {
        try {
            double availableAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;

            if (balanceType == BalanceType.AVAILABLE) {
                availableAmount = bankMoneyTransactionRecord.getAmount();
                runningAvailableBalance = (long) (getAvailableBalance() + (-availableAmount));
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);

            }
            if (balanceType == BalanceType.BOOK) {
                bookAmount = bankMoneyTransactionRecord.getAmount();
                runningBookBalance = (long) (getBookBalance() + (-bookAmount));
                ;
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);
            }
        } catch (CantAddBankMoneyException e) {
            throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE, e, "Cant Add Debit Exception", "Cant Add Bank Money Exception");
        }
    }

    public void addCredit(BankMoneyTransactionRecord bankMoneyTransactionRecord, BalanceType balanceType) throws CantAddCreditException {
        try {
            double availableAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;

            if (balanceType == BalanceType.AVAILABLE) {
                availableAmount = bankMoneyTransactionRecord.getAmount();
                runningAvailableBalance = (long) (getAvailableBalance() + availableAmount);
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);

            }
            if (balanceType == BalanceType.BOOK) {
                bookAmount = bankMoneyTransactionRecord.getAmount();
                runningBookBalance = (long) (getBookBalance() + bookAmount);
                ;
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);
            }
        } catch (CantAddBankMoneyException e) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE, e, "Cant Add Credit Exception", "Cant Add Bank Money Exception");
        }
    }

    public void makeHold(BankMoneyTransactionRecord bankMoneyTransactionRecord, BalanceType balanceType)throws CantMakeHoldException {
        try {
            double availableAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;

            if (balanceType == BalanceType.AVAILABLE) {
                availableAmount = bankMoneyTransactionRecord.getAmount();
                runningAvailableBalance = (long) (getAvailableBalance() + (-availableAmount));
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);

            }
            if (balanceType == BalanceType.BOOK) {
                throw new CantMakeHoldException(CantMakeHoldException.DEFAULT_MESSAGE, new Exception(), "Can't make hold exception", null);
            }
        } catch (CantAddBankMoneyException e) {
            throw new CantMakeHoldException(CantMakeHoldException.DEFAULT_MESSAGE, e, "Can't make hold exception", null);
        }
    }

    public void makeUnhold(BankMoneyTransactionRecord bankMoneyTransactionRecord,BalanceType balanceType)throws CantMakeUnholdException{
        try {
            double unholdAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;

            if (balanceType == BalanceType.AVAILABLE) {
                unholdAmount = bankMoneyTransactionRecord.getAmount();
                runningAvailableBalance = (long) (getHeldFunds() + (-unholdAmount));
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);

            }
            if (balanceType == BalanceType.BOOK) {
                throw new CantMakeUnholdException(CantMakeUnholdException.DEFAULT_MESSAGE, new Exception(), "Can't make hold exception", null);
            }
        } catch (CantAddBankMoneyException e) {
            throw new CantMakeUnholdException(CantMakeUnholdException.DEFAULT_MESSAGE, e, "Can't make unhold exception", null);
        }
    }

    public double getAmaunt() {
        double balanceAmount = 0;
        for (DatabaseTableRecord record : getBankMoneyTransactionList()) {
            balanceAmount = record.getDoubleValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME);
            balanceAmount += balanceAmount;
        }
        return balanceAmount;
    }

    public long getAvailableBalance() {
        long availableBalance = 0;
        for (DatabaseTableRecord record : getBankMoneyTransactionList()) {
            availableBalance = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
            availableBalance += availableBalance;
        }
        return availableBalance;
    }

    public long getBookBalance() {
        long bookBalance = 0;
        for (DatabaseTableRecord record : getBankMoneyTransactionList()) {
            bookBalance = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME);
            bookBalance += bookBalance;
        }
        return bookBalance;
    }

    public long getHeldFunds(){
        long heldFunds = 0;
        try {
            for (DatabaseTableRecord record : getTransactions(TransactionType.HOLD)) {
                heldFunds = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
                heldFunds += heldFunds;
            }
        }catch (CantGetTransactionsException e){

        }
        return heldFunds;
    }

    private List<DatabaseTableRecord> getBankMoneyTransactionList() {
        DatabaseTable totalBalancesTable = null;
        try {

            totalBalancesTable = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TABLE_NAME);
            totalBalancesTable.loadToMemory();

        } catch (CantLoadTableToMemoryException e) {

        }
        return totalBalancesTable.getRecords();
    }

    /**
     * @param balanceType
     * @return
     * @throws CantGetCurrentBalanceException
     * @throws CantGetBankMoneyTotalBalanceException
     */
    public double getCurrentBalance(BalanceType balanceType) throws CantGetCurrentBalanceException, CantGetBankMoneyTotalBalanceException {
        if (balanceType == balanceType.AVAILABLE)
            return Double.valueOf(getBankMoneyTotalBalance().getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME));
        else
            return Double.valueOf(getBankMoneyTotalBalance().getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME));
    }

    public List<BankMoneyWalletBalance> getBalanceType(BalanceType balanceType) throws CantCalculateBalanceException {
        try {
            DatabaseTable table = database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_TABLE_NAME);
            table.setStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return createBankMoneyBalanceList(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, e, "", "");
        }

           /* if (balanceType == balanceType.AVAILABLE) {
                return bankMoney.getBookBalance(BalanceType.getByCode(BankMoneyWalletDatabaseConstants.BANK_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME));
                //return getBankMoneyTotalBalance().getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME);
            } else {
                return bankMoney.getBookBalance(BalanceType.getByCode(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME));
            }*/

    }

    /**
     * @return
     * @throws CantGetBankMoneyTotalBalanceException
     */
    private DatabaseTableRecord getBankMoneyTotalBalance() throws CantGetBankMoneyTotalBalanceException {
        try {
            DatabaseTable balancesTable = database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_TABLE_NAME);
            balancesTable.loadToMemory();
            return balancesTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetBankMoneyTotalBalanceException(CantGetBankMoneyTotalBalanceException.DEFAULT_MESSAGE, e, "Cant Get Bank Money Total Balance Exception ", "Cant Load Table To Memory Exception");
        }
    }

    public List<BankMoneyTransactionRecord> getTransactions(TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {

        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TABLE_NAME);

        table.setStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        table.setFilterTop(String.valueOf(max));
        table.setFilterOffSet(String.valueOf(offset));

        try {
            table.loadToMemory();
            return createTransactionList(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, e, "Cant Get Transactions Exception", "Cant Load Table To Memory Exception");
        }
    }

    public List<DatabaseTableRecord> getTransactions(TransactionType transactionType) throws CantGetTransactionsException {

        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TABLE_NAME);

        table.setStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);

        try {
            table.loadToMemory();
            return table.getRecords();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, e, "Cant Get Transactions Exception", "Cant Load Table To Memory Exception");
        }
    }

    private List<BankMoneyTransactionRecord> createTransactionList(Collection<DatabaseTableRecord> records) {
        List<BankMoneyTransactionRecord> list = new ArrayList<>();

        for (DatabaseTableRecord record : records)
            list.add(constructTransactionBankMoney(record));

        return list;
    }

    private List<BankMoneyWalletBalance> createBankMoneyBalanceList(Collection<DatabaseTableRecord> records) {
        List<BankMoneyWalletBalance> list = new ArrayList<>();

        for (DatabaseTableRecord record : records)
            list.add(constructBankMoneyBalanceList(record));

        return list;
    }

    private BankMoneyTransactionRecord constructTransactionBankMoney(DatabaseTableRecord record) {

        UUID bankTransactionId = record.getUUIDValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME);
        String publicKeyBroker = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        String publicKeyCustomer = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String balanceType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME);
        String transactionType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME);
        double amount = record.getDoubleValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME);
        String cashCurrencyType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME);
        String bankOperationType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_OPERATION_TYPE_COLUMN_NAME);
        String bankDocumentReference = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_DOCUMENT_REFERENCE_COLUMN_NAME);
        String bankName = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN_NAME);
        String bankNumber = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME);
        String bankAccountType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_TYPE_COLUMN_NAME);
        long runningBookBalance = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timeStamp = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TIMESTAMP_COLUMN_NAME);
        String memo = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_MEMO_COLUMN_NAME);
        String status = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_STATUS_COLUMN_NAME);

        return new TransactionBankMoney(
                bankTransactionId,
                publicKeyBroker,
                publicKeyCustomer,
                balanceType,
                transactionType,
                amount,
                cashCurrencyType,
                bankOperationType,
                bankDocumentReference,
                bankName,
                bankNumber,
                bankAccountType,
                runningBookBalance,
                runningAvailableBalance,
                timeStamp,
                memo,
                status);
    }

    private BankMoneyWalletBalance constructBankMoneyBalanceList(DatabaseTableRecord record) {

        UUID bankTransactionId = record.getUUIDValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME);
        String publicKeyActorFrom = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        String publicKeyActorTo = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String balanceType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME);
        String transactionType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME);
        double amount = record.getDoubleValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME);
        String bankCurrencyType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME);
        String bankOperationType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_OPERATION_TYPE_COLUMN_NAME);
        String bankDocumentReference = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_DOCUMENT_REFERENCE_COLUMN_NAME);
        String bankName = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN_NAME);
        String bankAccountNumber = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME);
        String bankAccountType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_TYPE_COLUMN_NAME);
        long runningBookBalance = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        long runningAvailableBalance = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long timestamp = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TIMESTAMP_COLUMN_NAME);
        String getMemo = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_MEMO_COLUMN_NAME);


        return new BankMoneyBalanceList(
                bankTransactionId,
                publicKeyActorFrom,
                publicKeyActorTo,
                balanceType,
                transactionType,
                amount,
                bankCurrencyType,
                bankOperationType,
                bankDocumentReference,
                bankName,
                bankAccountNumber,
                bankAccountType,
                runningBookBalance,
                runningAvailableBalance,
                timestamp,
                getMemo);
    }
}
