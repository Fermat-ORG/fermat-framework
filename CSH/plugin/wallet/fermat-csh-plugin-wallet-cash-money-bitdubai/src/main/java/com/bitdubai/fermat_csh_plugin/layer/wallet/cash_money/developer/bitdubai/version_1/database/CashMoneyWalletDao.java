package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyWalletTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CashMoneyWalletDoesNotExistException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.WalletCashMoneyPluginRoot;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantInitializeCashMoneyWalletDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantRegisterCashMoneyWalletTransactionException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CashMoneyWalletInconsistentTableStateException;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure.CashMoneyWalletTransactionImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CashMoneyWalletDao {

    private final WalletCashMoneyPluginRoot pluginRoot;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public CashMoneyWalletDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final WalletCashMoneyPluginRoot pluginRoot) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.pluginRoot = pluginRoot;
    }

    public void initialize() throws CantInitializeCashMoneyWalletDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            CashMoneyWalletDatabaseFactory databaseFactory = new CashMoneyWalletDatabaseFactory(pluginDatabaseSystem);
            try {
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeCashMoneyWalletDatabaseException("Database could not be opened", cantCreateDatabaseException, "Database Name: " + pluginId.toString(), "");
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeCashMoneyWalletDatabaseException("Database could not be opened", cantOpenDatabaseException, "Database Name: " + pluginId.toString(), "");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeCashMoneyWalletDatabaseException("Database could not be opened", FermatException.wrapException(e), "Database Name: " + pluginId.toString(), "");
        }
    }


    public void createCashMoneyWallet(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyWalletException {

        if(walletExists(walletPublicKey))
            throw new CantCreateCashMoneyWalletException(CantCreateCashMoneyWalletException.DEFAULT_MESSAGE, null, "Cant create Cash Money Wallet", "Cash Wallet already exists! publicKey:" + walletPublicKey);

        try {
            DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.WALLETS_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey);
            record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME, "0");
            record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME, "0");
            record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_CURRENCY_COLUMN_NAME, fiatCurrency.getCode());
            record.setLongValue(CashMoneyWalletDatabaseConstants.WALLETS_TIMESTAMP_WALLET_CREATION_COLUMN_NAME, (new Date().getTime() / 1000));

            table.insertRecord(record);
        } catch (CantInsertRecordException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCashMoneyWalletException(CantCreateCashMoneyWalletException.DEFAULT_MESSAGE, e, "Cant create Cash Money Wallet", "Cant insert record into database");
        }
    }

    public void registerTransaction(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterCashMoneyWalletTransactionException {
        try {
            DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            constructRecordFromCashMoneyWalletTransaction(record, cashMoneyWalletTransaction);

            table.insertRecord(record);
        } catch (CantInsertRecordException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterCashMoneyWalletTransactionException(CantRegisterCashMoneyWalletTransactionException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        }
    }


    public void credit(String walletPublicKey, BalanceType balanceType, BigDecimal creditAmount) throws CantRegisterCreditException {
        DatabaseTable table;
        DatabaseTableRecord record;
        try {
            table = this.database.getTable(CashMoneyWalletDatabaseConstants.WALLETS_TABLE_NAME);
            record = this.getWalletRecordByPublicKey(walletPublicKey);
            if (balanceType == BalanceType.AVAILABLE) {
                BigDecimal available = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME));
                available = available.add(creditAmount);

                record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME, available.toPlainString());
            } else if (balanceType == BalanceType.BOOK) {
                BigDecimal book = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME));
                book = book.add(creditAmount);

                record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME, book.toPlainString());
            } else if (balanceType == BalanceType.ALL) {
                BigDecimal available = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME));
                BigDecimal book = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME));

                available = available.add(creditAmount);
                book = book.add(creditAmount);

                record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME, available.toPlainString());
                record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME, book.toPlainString());
            }

            table.updateRecord(record);

        } catch (CashMoneyWalletInconsistentTableStateException e) {
            throw new CantRegisterCreditException(e.getMessage(), e, "Credit in Cash wallet", "Cant credit balance. Inconsistent Table State");
        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCreditException(e.getMessage(), e, "Credit in Cash wallet", "Cant credit balance. Cant update record");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterCreditException(e.getMessage(), e, "Credit in Cash wallet", "Cant credit balance. Cant load table to memory");
        } catch (CashMoneyWalletDoesNotExistException e) {
        throw new CantRegisterCreditException(e.getMessage(), e, "Credit in Cash wallet", "Cant credit balance. Wallet does not exist");
        }
    }

    public void debit(String walletPublicKey, BalanceType balanceType, BigDecimal debitAmount) throws CantRegisterDebitException, CashMoneyWalletInsufficientFundsException {
        DatabaseTable table;
        DatabaseTableRecord record;
        try {
            table = this.database.getTable(CashMoneyWalletDatabaseConstants.WALLETS_TABLE_NAME);
            record = this.getWalletRecordByPublicKey(walletPublicKey);
            if (balanceType == BalanceType.AVAILABLE) {
                BigDecimal available = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME));
                available = available.subtract(debitAmount);

                if (available.compareTo(new BigDecimal(0)) < 0)
                    throw new CashMoneyWalletInsufficientFundsException(CashMoneyWalletInsufficientFundsException.DEFAULT_MESSAGE, null, "Debit in Cash wallet", "Cant debit available balance by this amount, insufficient funds.");

                record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME, available.toPlainString());

            } else if (balanceType == BalanceType.BOOK) {
                BigDecimal book = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME));
                book = book.subtract(debitAmount);

                if (book.compareTo(new BigDecimal(0)) < 0)
                    throw new CashMoneyWalletInsufficientFundsException(CantRegisterDebitException.DEFAULT_MESSAGE, null, "Debit in Cash wallet", "Cant debit book balance by this amount, insufficient funds.");

                record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME, book.toPlainString());

            } else if (balanceType == BalanceType.ALL) {
                BigDecimal available = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME));
                BigDecimal book = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME));

                available = available.subtract(debitAmount);
                book = book.subtract(debitAmount);

                if (available.compareTo(new BigDecimal(0)) < 0)
                    throw new CashMoneyWalletInsufficientFundsException(CantRegisterDebitException.DEFAULT_MESSAGE, null, "Debit in Cash wallet", "Cant debit available balance by this amount, insufficient funds.");

                if (book.compareTo(new BigDecimal(0)) < 0)
                    throw new CashMoneyWalletInsufficientFundsException(CantRegisterDebitException.DEFAULT_MESSAGE, null, "Debit in Cash wallet", "Cant debit book balance by this amount, insufficient funds.");

                record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME, available.toPlainString());
                record.setStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME, book.toPlainString());
            }


            table.updateRecord(record);

        } catch (CashMoneyWalletInconsistentTableStateException e) {
            throw new CantRegisterDebitException(e.getMessage(), e, "Debit in Cash wallet", "Cant debit balance. Inconsistent Table State");
        } catch (CantUpdateRecordException e) {
            throw new CantRegisterDebitException(e.getMessage(), e, "Debit in Cash wallet", "Cant debit balance. Cant update record");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterDebitException(e.getMessage(), e, "Debit in Cash wallet", "Cant debit balance. Cant load table to memory");
        } catch (CashMoneyWalletDoesNotExistException e) {
            throw new CantRegisterDebitException(e.getMessage(), e, "Debit in Cash wallet", "Cant debit balance. Wallet does not exist");
        }
    }

    public FiatCurrency getWalletCurrency(String walletPublicKey) throws CantGetCashMoneyWalletCurrencyException {
        FiatCurrency currency;
        try {
            DatabaseTableRecord record = this.getWalletRecordByPublicKey(walletPublicKey);
            currency = FiatCurrency.getByCode(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_CURRENCY_COLUMN_NAME));
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletCurrencyException(CantGetCashMoneyWalletCurrencyException.DEFAULT_MESSAGE, e, "Cant get wallet currency", null);
        }

        return currency;
    }

    public BigDecimal getWalletBalance(String walletPublicKey, BalanceType balanceType) throws CantGetCashMoneyWalletBalanceException {
        BigDecimal balance;
        try {
            DatabaseTableRecord record = this.getWalletRecordByPublicKey(walletPublicKey);
            if (balanceType == BalanceType.AVAILABLE)
                balance = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME));
            else if (balanceType == BalanceType.BOOK)
                balance = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME));
            else throw new InvalidParameterException();
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletBalanceException(CantGetCashMoneyWalletBalanceException.DEFAULT_MESSAGE, e, "Cant get wallet balance", null);
        }

        return balance;
    }

    public boolean walletExists(String walletPublicKey) {
        DatabaseTableRecord record = null;
        try {
            record = this.getWalletRecordByPublicKey(walletPublicKey);
        } catch (CashMoneyWalletDoesNotExistException e) {
            return false;
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            return false;
        }

        return record.getStringValue(CashMoneyWalletDatabaseConstants.WALLETS_WALLET_PUBLIC_KEY_COLUMN_NAME).equals(walletPublicKey);
    }


    public CashMoneyWalletTransaction getTransaction(UUID transactionId) throws CantGetCashMoneyWalletTransactionsException {
        CashMoneyWalletTransaction transaction;

        List<DatabaseTableRecord> records;
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME);
        table.addUUIDFilter(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        table.addStringFilter(CashMoneyWalletDatabaseConstants.TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, BalanceType.BOOK.getCode(), DatabaseFilterType.EQUAL);

        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, e, "getTransaction", "Cant load table into memory");
        }

        records = table.getRecords();

        if (records.size() == 0)
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, null, "getTransaction", "No record found");
        if (records.size() != 1)
            throw new CantGetCashMoneyWalletTransactionsException("Inconsistent (" + records.size() + ") number of fetched records, should be between 0 and 1.", null, "The id is: " + transactionId, "");

        try{
            transaction = constructCashMoneyWalletTransactionFromRecord(records.get(0));
        }catch(CantCreateCashMoneyWalletTransactionException e){
            throw new CantGetCashMoneyWalletTransactionsException(CantCreateCashMoneyWalletTransactionException.DEFAULT_MESSAGE, null, "getTransaction", "Error creating transaction from record");

        }

        return transaction;
    }

    public List<CashMoneyWalletTransaction> getTransactions(String walletPublicKey, List<TransactionType> transactionTypes, List<BalanceType> balanceTypes, int max, int offset) throws CantGetCashMoneyWalletTransactionsException {
        List<CashMoneyWalletTransaction> transactions = new ArrayList<>();

        List<String> transactionTypesString = new ArrayList<>();
        for(TransactionType t : transactionTypes)
            transactionTypesString.add(t.getCode());

        List<String> balanceTypesString = new ArrayList<>();
        for(BalanceType b : balanceTypes)
            balanceTypesString.add(b.getCode());

        String query = "SELECT * FROM " +
                CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME +
                " WHERE ( " +
                CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME +
                " = '" +
                StringUtils.join(transactionTypesString, "' OR " + CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME + " = '") +
                "') AND (" +
                CashMoneyWalletDatabaseConstants.TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME +
                " = '" +
                StringUtils.join(balanceTypesString, "' OR " + CashMoneyWalletDatabaseConstants.TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME + " = '") +
                "') AND " +
                CashMoneyWalletDatabaseConstants.TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME +
                " = '" +
                walletPublicKey +
                "' ORDER BY " +
                CashMoneyWalletDatabaseConstants.TRANSACTIONS_TIMESTAMP_COLUMN_NAME +
                " DESC " +
                " LIMIT " + max +
                " OFFSET " + offset;


        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME);

        try {
            Collection<DatabaseTableRecord> records = table.customQuery(query, false);

            for (DatabaseTableRecord record : records) {
                CashMoneyWalletTransaction transaction = constructCashMoneyWalletTransactionFromRecord(record);
                transactions.add(transaction);
            }
        } catch (CantCreateCashMoneyWalletTransactionException e) {
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, e, "Failed to get Cash Money Wallet Transactions.", "CantCreateCashMoneyWalletTransactionException");
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetCashMoneyWalletTransactionsException(CantGetCashMoneyWalletTransactionsException.DEFAULT_MESSAGE, e, "Failed to get Cash Money Wallet Transactions.", "CantLoadTableToMemoryException");
        }
        return transactions;
    }


    public BigDecimal getHeldFunds(String walletPublicKey, String actorPublicKey) throws CantGetHeldFundsException {
        List<DatabaseTableRecord> records;
        BigDecimal heldFunds = new BigDecimal(0);
        BigDecimal unheldFunds = new BigDecimal(0);

        //List<DatabaseTableFilter> filtersTable = new ArrayList<>();
        //DatabaseTableFilter walletFilter, actorFilter;
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME);

        /*walletFilter = getEmptyTransactionsTableFilter();
        walletFilter.setColumn(CashMoneyWalletDatabaseConstants.TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        walletFilter.setValue(walletPublicKey);
        walletFilter.setType(DatabaseFilterType.EQUAL);
        filtersTable.add(walletFilter);

        actorFilter = getEmptyTransactionsTableFilter();
        actorFilter.setColumn(CashMoneyWalletDatabaseConstants.TRANSACTIONS_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        actorFilter.setValue(actorPublicKey);
        actorFilter.setType(DatabaseFilterType.EQUAL);
        filtersTable.add(actorFilter);

        table.setFilterGroup(filtersTable, null, DatabaseFilterOperator.AND);*/
        table.addStringFilter(
                CashMoneyWalletDatabaseConstants.TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME,
                walletPublicKey,
                DatabaseFilterType.EQUAL);

        String transactionTypeString;
        TransactionType transactionType;
        String amountString;
        BigDecimal recordAmount;
        try {
            table.loadToMemory();
            records = table.getRecords();
        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetHeldFundsException(CantGetHeldFundsException.DEFAULT_MESSAGE, e, "getHeldFunds", "Cant load table into memory");
        }

        for (DatabaseTableRecord record : records) {
            /*if (record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME).equals(TransactionType.HOLD.getCode()))
                heldFunds.add(new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_AMOUNT_COLUMN_NAME)));
            else if (record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME).equals(TransactionType.UNHOLD.getCode()))
                unheldFunds.add(new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_AMOUNT_COLUMN_NAME)));*/
            if(record
                    .getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_ACTOR_PUBLIC_KEY_COLUMN_NAME)
                    .equals(actorPublicKey)){
                transactionTypeString = record.getStringValue(
                        CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME);
                try {
                    transactionType = TransactionType.getByCode(transactionTypeString);
                    amountString = record.getStringValue(
                            CashMoneyWalletDatabaseConstants
                                    .TRANSACTIONS_AMOUNT_COLUMN_NAME);
                    recordAmount = new BigDecimal(amountString);
                    switch (transactionType){
                        case HOLD:
                            heldFunds = heldFunds.add(recordAmount);
                            break;
                        case UNHOLD:
                            unheldFunds = unheldFunds.add(recordAmount);
                            break;
                        default:
                            continue;
                    }
                } catch (InvalidParameterException e) {
                    //Invalid parameter in this record, we'll continue.
                    pluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,e);
                    continue;
                }
            }
        }
        heldFunds = heldFunds.subtract(unheldFunds);

        if (heldFunds.compareTo(new BigDecimal(0)) < 0)
            throw new CantGetHeldFundsException(CantGetHeldFundsException.DEFAULT_MESSAGE, null, "Held funds calculates to a negative value", "Unheld funds are higher than held funds, invalid table state");

        return heldFunds;
    }


    /* INTERNAL HELPER FUNCTIONS */
    private DatabaseTableFilter getEmptyTransactionsTableFilter() {
        return this.database.getTable(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME).getEmptyTableFilter();
    }

    private DatabaseTableRecord getWalletRecordByPublicKey(String walletPublicKey) throws CantLoadTableToMemoryException, CashMoneyWalletInconsistentTableStateException, CashMoneyWalletDoesNotExistException {
        List<DatabaseTableRecord> records;
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.WALLETS_TABLE_NAME);
        table.addStringFilter(CashMoneyWalletDatabaseConstants.WALLETS_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
        try {
            table.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "getWalletRecordByPublicKey", "Cant load table into memory");
        }

        records = table.getRecords();

        if (records.size() == 0)
            throw new CashMoneyWalletDoesNotExistException(CashMoneyWalletDoesNotExistException.DEFAULT_MESSAGE);
        if (records.size() != 1)
            throw new CashMoneyWalletInconsistentTableStateException("Inconsistent (" + records.size() + ") number of fetched records, should be between 0 and 1.", null, "The id is: " + walletPublicKey, "");

        return records.get(0);
    }


    private List<DatabaseTableRecord> getTransactionRecordsByFilter(DatabaseTableFilter filter, int max, int offset) throws CantLoadTableToMemoryException {
        DatabaseTable table = this.database.getTable(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        if (offset >= 0)
            table.setFilterOffSet(String.valueOf(offset));

        if (max >= 0)
            table.setFilterTop(String.valueOf(max));

        table.loadToMemory();
        return table.getRecords();
    }


    private void constructRecordFromCashMoneyWalletTransaction(DatabaseTableRecord newRecord, CashMoneyWalletTransaction cashMoneyWalletTransaction) {

        newRecord.setUUIDValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, cashMoneyWalletTransaction.getTransactionId());
        newRecord.setStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME, cashMoneyWalletTransaction.getPublicKeyWallet());
        newRecord.setStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_ACTOR_PUBLIC_KEY_COLUMN_NAME, cashMoneyWalletTransaction.getPublicKeyActor());
        newRecord.setStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_PLUGIN_PUBLIC_KEY_COLUMN_NAME, cashMoneyWalletTransaction.getPublicKeyPlugin());
        newRecord.setStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, cashMoneyWalletTransaction.getTransactionType().getCode());
        newRecord.setStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, cashMoneyWalletTransaction.getBalanceType().getCode());
        newRecord.setStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_AMOUNT_COLUMN_NAME, cashMoneyWalletTransaction.getAmount().toPlainString());
        newRecord.setStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_MEMO_COLUMN_NAME, cashMoneyWalletTransaction.getMemo());
        newRecord.setLongValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TIMESTAMP_COLUMN_NAME, cashMoneyWalletTransaction.getTimestamp());
    }

    private CashMoneyWalletTransaction constructCashMoneyWalletTransactionFromRecord(DatabaseTableRecord record) throws CantCreateCashMoneyWalletTransactionException {

        UUID transactionId = record.getUUIDValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME);
        String publicKeyWallet = record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyActor = record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String publicKeyPlugin = record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_PLUGIN_PUBLIC_KEY_COLUMN_NAME);
        BigDecimal amount = new BigDecimal(record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_AMOUNT_COLUMN_NAME));
        String memo = record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_MEMO_COLUMN_NAME);
        long timestamp = record.getLongValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TIMESTAMP_COLUMN_NAME);

        TransactionType transactionType;
        try {
            transactionType = TransactionType.getByCode(record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateCashMoneyWalletTransactionException(e.getMessage(), e, "Cash Money Wallet", "Invalid TransactionType value stored in table"
                    + CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME + " for id " + transactionId);
        }

        BalanceType balanceType;
        try {
            balanceType = BalanceType.getByCode(record.getStringValue(CashMoneyWalletDatabaseConstants.TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            throw new CantCreateCashMoneyWalletTransactionException(e.getMessage(), e, "Cash Money Wallet", "Invalid BalanceType value stored in table"
                    + CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME + " for id " + transactionId);
        }

        return new CashMoneyWalletTransactionImpl(transactionId, publicKeyWallet, publicKeyActor, publicKeyPlugin, transactionType, balanceType, amount, memo, timestamp, false);
    }

}
