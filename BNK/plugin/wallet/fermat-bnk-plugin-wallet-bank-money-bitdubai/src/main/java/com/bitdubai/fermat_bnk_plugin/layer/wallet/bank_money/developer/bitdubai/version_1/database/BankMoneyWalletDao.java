package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.*;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure.BankAccountNumberImpl;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure.BankMoneyTransactionRecordImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

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
    UUID pluginId;
    ErrorManager errorManager;
    String publicKey;

    public BankMoneyWalletDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    public BankMoneyWalletDao(Database database) {
        this.database = database;
    }

    public BankMoneyWalletDao(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, ErrorManager errorManager, String publicKey) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.publicKey = publicKey;
    }


    public void initialize() throws CantInitializeBankMoneyWalletDatabaseException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            BankMoneyWalletDatabaseFactory factory = new BankMoneyWalletDatabaseFactory(pluginDatabaseSystem);
            try {
                this.database = factory.createDatabase(this.pluginId, this.pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, f);
                throw new CantInitializeBankMoneyWalletDatabaseException("Database could not be opened", f, "Database Name: " + pluginId.toString(), "");
            }
        } catch (CantOpenDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeBankMoneyWalletDatabaseException("Database could not be opened", e, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeBankMoneyWalletDatabaseException("Database could not be opened", e, "Database Name: " + pluginId.toString(), "");
        }
    }

    public void addBankMoneyTransaction(BankMoneyTransactionRecord bankMoneyTransactionRecord,
                                        BalanceType balanceType,
                                        long runingBookBalance,
                                        long runingAvalibleBalance
    ) throws CantAddBankMoneyException {
        try {
            DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTIONS_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME, bankMoneyTransactionRecord.getBankTransactionId());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME, bankMoneyTransactionRecord.getTransactionType().getCode());
            record.setDoubleValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME, bankMoneyTransactionRecord.getAmount());
            record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME, bankMoneyTransactionRecord.getCurrencyType().getCode());
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantAddBankMoneyException(CantAddBankMoneyException.DEFAULT_MESSAGE, e, "Cant Add Bank Money Exception", "Cant Insert Record Exception");
        }
    }

    public void updateBalance(String account, float amount, BalanceType balanceType) {
        try {
            float bookBalance = 0;
            float availableBalance = 0;
            DatabaseTableRecord record;
            DatabaseTable table;
            List<DatabaseTableRecord> records;
            table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_TABLE_NAME);
            table.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, account, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            records = table.getRecords();
            record = records.get(0);
            bookBalance = record.getFloatValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BOOK_BALANCE_COLUMN_NAME);
            availableBalance = record.getFloatValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_AVAILABLE_BALANCE_COLUMN_NAME);
            if (BalanceType.BOOK == balanceType) {
                bookBalance = bookBalance + amount;
            }
            if (BalanceType.AVAILABLE == balanceType) {
                availableBalance = availableBalance + amount;
            }
            record.setFloatValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BOOK_BALANCE_COLUMN_NAME, bookBalance);
            record.setFloatValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_AVAILABLE_BALANCE_COLUMN_NAME, availableBalance);
            table.updateRecord(record);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
                runningBookBalance = getBookBalance(bankMoneyTransactionRecord.getBankAccountNumber());
                runningAvailableBalance = (long) (getAvailableBalance(bankMoneyTransactionRecord.getBankAccountNumber()) + (-availableAmount));
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);
                updateBalance(bankMoneyTransactionRecord.getBankAccountNumber(), bankMoneyTransactionRecord.getAmount() * (-1), BalanceType.AVAILABLE);
            }
            if (balanceType == BalanceType.BOOK) {
                bookAmount = bankMoneyTransactionRecord.getAmount();
                runningAvailableBalance = getAvailableBalance(bankMoneyTransactionRecord.getBankAccountNumber());
                runningBookBalance = (long) (getBookBalance(bankMoneyTransactionRecord.getBankAccountNumber()) + (-bookAmount));
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);
                updateBalance(bankMoneyTransactionRecord.getBankAccountNumber(), bankMoneyTransactionRecord.getAmount() * (-1), BalanceType.BOOK);
            }
        } catch (CantAddBankMoneyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
                runningBookBalance = getBookBalance(bankMoneyTransactionRecord.getBankAccountNumber());
                runningAvailableBalance = (long) (getAvailableBalance(bankMoneyTransactionRecord.getBankAccountNumber()) + availableAmount);
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);
                updateBalance(bankMoneyTransactionRecord.getBankAccountNumber(), bankMoneyTransactionRecord.getAmount(), BalanceType.AVAILABLE);
            }
            if (balanceType == BalanceType.BOOK) {
                bookAmount = bankMoneyTransactionRecord.getAmount();
                runningAvailableBalance = getAvailableBalance(bankMoneyTransactionRecord.getBankAccountNumber());
                runningBookBalance = (long) (getBookBalance(bankMoneyTransactionRecord.getBankAccountNumber()) + bookAmount);
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);
                updateBalance(bankMoneyTransactionRecord.getBankAccountNumber(), bankMoneyTransactionRecord.getAmount(), BalanceType.BOOK);
            }
        } catch (CantAddBankMoneyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE, e, "Cant Add Credit Exception", "Cant Add Bank Money Exception");
        }
    }

    public void makeHold(BankMoneyTransactionRecord bankMoneyTransactionRecord, BalanceType balanceType) throws CantMakeHoldException {
        try {
            double availableAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;

            if (balanceType == BalanceType.AVAILABLE) {
                availableAmount = bankMoneyTransactionRecord.getAmount();
                runningAvailableBalance = (long) (getAvailableBalance(bankMoneyTransactionRecord.getBankAccountNumber()) + (-availableAmount));
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);

            }
            if (balanceType == BalanceType.BOOK) {
                throw new CantMakeHoldException(CantMakeHoldException.DEFAULT_MESSAGE, new Exception(), "Can't make hold exception", null);
            }
        } catch (CantAddBankMoneyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantMakeHoldException(CantMakeHoldException.DEFAULT_MESSAGE, e, "Can't make hold exception", null);
        }
    }

    public void makeUnhold(BankMoneyTransactionRecord bankMoneyTransactionRecord, BalanceType balanceType) throws CantMakeUnholdException {
        try {
            double unholdAmount;
            double bookAmount;
            long runningAvailableBalance = 0;
            long runningBookBalance = 0;

            if (balanceType == BalanceType.AVAILABLE) {
                unholdAmount = bankMoneyTransactionRecord.getAmount();
                runningAvailableBalance = (long) (getHeldFunds(bankMoneyTransactionRecord.getBankAccountNumber()) + (-unholdAmount));
                addBankMoneyTransaction(bankMoneyTransactionRecord, balanceType, runningBookBalance, runningAvailableBalance);

            }
            if (balanceType == BalanceType.BOOK) {
                throw new CantMakeUnholdException(CantMakeUnholdException.DEFAULT_MESSAGE, new Exception(), "Can't make hold exception", null);
            }
        } catch (CantAddBankMoneyException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantMakeUnholdException(CantMakeUnholdException.DEFAULT_MESSAGE, e, "Can't make unhold exception", null);
        }
    }

    public long getAvailableBalance(String accountNumber) {
        long availableBalance = 0;
        /*for (DatabaseTableRecord record : getBankMoneyTransactionList(accountNumber,BalanceType.AVAILABLE)) {
            availableBalance = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
            availableBalance += availableBalance;
        }*/
        List<DatabaseTableRecord> records;
        DatabaseTableRecord record;
        records = getBankMoneyTransactionList(accountNumber, BalanceType.AVAILABLE);
        record = records.get(0);
        availableBalance = (long) record.getFloatValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_AVAILABLE_BALANCE_COLUMN_NAME);
        return availableBalance;
    }

    public long getBookBalance(String accountNumber) {
        long bookBalance = 0;
        /*for (DatabaseTableRecord record : getBankMoneyTransactionList(accountNumber,BalanceType.BOOK)) {
            bookBalance = record.getLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME);
            bookBalance += bookBalance;
        }*/
        List<DatabaseTableRecord> records;
        DatabaseTableRecord record;
        records = getBankMoneyTransactionList(accountNumber, BalanceType.AVAILABLE);
        record = records.get(0);
        bookBalance = (long) record.getFloatValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BOOK_BALANCE_COLUMN_NAME);
        return bookBalance;
    }

    public long getHeldFunds(String account) {
        long heldFunds = 0;
        try {
            for (BankMoneyTransactionRecord record : getTransactions(TransactionType.HOLD, account)) {
                heldFunds = record.getRunningAvailableBalance();
                heldFunds += heldFunds;
            }
        } catch (CantGetTransactionsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        return heldFunds;
    }

    private List<DatabaseTableRecord> getBankMoneyTransactionList(String accountNumber, BalanceType balanceType) {
        DatabaseTable totalBalancesTable = null;
        try {

            totalBalancesTable = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_TABLE_NAME);
            totalBalancesTable.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, accountNumber, DatabaseFilterType.EQUAL);
            //totalBalancesTable.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME,balanceType.getCode(),DatabaseFilterType.EQUAL);
            totalBalancesTable.loadToMemory();

        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
        if (balanceType == BalanceType.AVAILABLE)
            return Double.valueOf(getBankMoneyTotalBalance().getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_AVAILABLE_BALANCE_COLUMN_NAME));
        else
            return Double.valueOf(getBankMoneyTotalBalance().getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BOOK_BALANCE_COLUMN_NAME));
    }

    public void addNewAccount(BankAccountNumber bankAccountNumber) throws CantInsertRecordException {
        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_TABLE_NAME);
        DatabaseTableRecord record = table.getEmptyRecord();
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_PUBLIC_KEY_COLUMN_NAME, this.publicKey);
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, bankAccountNumber.getAccount());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BANK_CURRENCY_TYPE_COLUMN_NAME, bankAccountNumber.getCurrencyType().getCode());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_ALIAS_COLUMN_NAME, bankAccountNumber.getAlias());
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_ACCOUNT_TYPE_COLUMN_NAME, bankAccountNumber.getAccountType().getCode());
        record.setLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_AVAILABLE_BALANCE_COLUMN_NAME, 0);
        record.setLongValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BOOK_BALANCE_COLUMN_NAME, 0);
        table.insertRecord(record);
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetBankMoneyTotalBalanceException(CantGetBankMoneyTotalBalanceException.DEFAULT_MESSAGE, e, "Cant Get Bank Money Total Balance Exception ", "Cant Load Table To Memory Exception");
        }
    }

    public List<BankAccountNumber> getAccounts() throws CantGetAccountsException {
        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_TABLE_NAME);
        table.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
        System.out.println("BNK-account");
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetAccountsException(CantGetAccountsException.DEFAULT_MESSAGE, e, "Cant Get Transactions Exception", "Cant Load Table To Memory Exception");
        }
        return constructBankAccountNumberList(table.getRecords());
    }

    public List<BankMoneyTransactionRecord> getTransactions(TransactionType transactionType, int max, int offset, String account) throws CantGetTransactionsException {

        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTIONS_TABLE_NAME);
        table.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, account, DatabaseFilterType.EQUAL);
        table.setFilterTop(String.valueOf(max));
        table.setFilterOffSet(String.valueOf(offset));

        try {
            table.loadToMemory();
            return createTransactionList(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, e, "Cant Get Transactions Exception", "Cant Load Table To Memory Exception");
        }
    }

    public List<BankMoneyTransactionRecord> getTransactions(TransactionType transactionType, String account) throws CantGetTransactionsException {
        int max = 100;
        int offset = 0;
        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTIONS_TABLE_NAME);

        table.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, account, DatabaseFilterType.EQUAL);
        table.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME, BalanceType.AVAILABLE.getCode(), DatabaseFilterType.EQUAL);
        table.setFilterTop(String.valueOf(max));
        table.setFilterOffSet(String.valueOf(offset));

        try {
            table.loadToMemory();
            return createTransactionList(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, e, "Cant Get Transactions Exception", "Cant Load Table To Memory Exception");
        }
    }

    private List<BankMoneyTransactionRecord> createTransactionList(Collection<DatabaseTableRecord> records) {
        List<BankMoneyTransactionRecord> list = new ArrayList<>();

        for (DatabaseTableRecord record : records)
            list.add(constructTransactionBankMoney(record));

        return list;
    }


    private BankMoneyTransactionRecord constructTransactionBankMoney(DatabaseTableRecord record) {

        UUID bankTransactionId = record.getUUIDValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME);
        String balanceType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME);
        String transactionType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME);
        float amount = record.getFloatValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME);
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


        return new BankMoneyTransactionRecordImpl(
                bankTransactionId,
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


    private List<BankAccountNumber> constructBankAccountNumberList(List<DatabaseTableRecord> records) {
        List<BankAccountNumber> list = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            list.add(constructBankAccountNumber(record));
        }
        return list;
    }

    private BankAccountNumber constructBankAccountNumber(DatabaseTableRecord record) {
        String alias = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_ALIAS_COLUMN_NAME);
        String account = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME);
        System.out.println(" BNK-ACCOUNT   alias = " + alias + " account =" + account);
        BankAccountNumberImpl bankAccountNumber = null;
        try {
            String accountType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_ACCOUNT_TYPE_COLUMN_NAME);
            System.out.println("account type = " + accountType);
            String currencyType = record.getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BANK_CURRENCY_TYPE_COLUMN_NAME);
            System.out.println("currency type = " + currencyType);
            BankAccountType bankAccountType = BankAccountType.getByCode(accountType);
            FiatCurrency currency = FiatCurrency.getByCode(currencyType);
            System.out.println("BNK-ACCOUNT = [" + account + "] alias = [" + alias + "] account type = [" + bankAccountType.getCode() + "]");
            bankAccountNumber = new BankAccountNumberImpl(alias, account, currency, bankAccountType,getBankName());

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            System.out.println("error seteando fiatcurrency " + e.getMessage());
        }
        return bankAccountNumber;
    }

    public void createBankName(String bankName) throws CantInsertRecordException {
        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_TABLE);
        DatabaseTableRecord record = table.getEmptyRecord();
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_PUBLIC_KEY_COLUMN, this.publicKey);
        record.setStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN, bankName);
        table.insertRecord(record);
    }

    public String getBankName() throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_TABLE);
        table.addStringFilter(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_PUBLIC_KEY_COLUMN, publicKey, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        try {
            return table.getRecords().get(0).getStringValue(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return null;
        }
    }

}
