package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.exceptions.MissingBankMoneyDestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.BankMoneyDestockTransactionImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockDatabaseDao</code>
 * contains the logic for handling database the plugin
 * Created by franklin on 17/11/15.
 */
public class BusinessTransactionBankMoneyDestockDatabaseDao {

    Database database;
    UUID pluginId;
    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor
     */
    public BusinessTransactionBankMoneyDestockDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            BusinessTransactionBankMoneyDestockDatabaseFactory businessTransactionBankMoneyDestockDatabaseFactory = new BusinessTransactionBankMoneyDestockDatabaseFactory(this.pluginDatabaseSystem);
            database = businessTransactionBankMoneyDestockDatabaseFactory.createDatabase(this.pluginId, BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getBankMoneyRestockRecord(BankMoneyTransaction bankMoneyTransaction
                                                          ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME, bankMoneyTransaction.getTransactionId());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, bankMoneyTransaction.getActorPublicKey());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_FIAT_CURRENCY_COLUMN_NAME, bankMoneyTransaction.getFiatCurrency().getCode());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, bankMoneyTransaction.getBnkWalletPublicKey());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_BNK_WALLET_PUBLIC_KEY_COLUMN_NAME, bankMoneyTransaction.getCbpWalletPublicKey());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_BANK_ACCOUNT_COLUMN_NAME, bankMoneyTransaction.getBankAccount());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_CONCEPT_COLUMN_NAME, bankMoneyTransaction.getConcept());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TIMESTAMP_COLUMN_NAME, bankMoneyTransaction.getTimeStamp().toString());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_MEMO_COLUMN_NAME, bankMoneyTransaction.getMemo());
        record.setFloatValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_MEMO_COLUMN_NAME, bankMoneyTransaction.getAmount());
        record.setStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TRANSACTION_STATUS_COLUMN_NAME, bankMoneyTransaction.getTransactionStatus().getCode());

        return record;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.setStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        if (table.getRecords().isEmpty())
            return true;
        else
            return false;
    }

    private List<DatabaseTableRecord> getBankMoneyRestockData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TABLE_NAME);

        if (filter != null)
            table.setStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private BankMoneyTransaction getBankMoneyRestockTransaction(final DatabaseTableRecord bankMoneyRestockTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException, com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException {

        BankMoneyDestockTransactionImpl bankMoneyRestockTransaction = new BankMoneyDestockTransactionImpl();

        bankMoneyRestockTransaction.setTransactionId(bankMoneyRestockTransactionRecord.getUUIDValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME));
        bankMoneyRestockTransaction.setActorPublicKey(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME));
        bankMoneyRestockTransaction.setFiatCurrency(FiatCurrency.getFiatCurrencyTypeByCode(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_FIAT_CURRENCY_COLUMN_NAME)));
        bankMoneyRestockTransaction.setCbpWalletPublicKey(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME));
        bankMoneyRestockTransaction.setBnkWalletPublicKey(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_BNK_WALLET_PUBLIC_KEY_COLUMN_NAME));
        bankMoneyRestockTransaction.setBankAccount(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_BANK_ACCOUNT_COLUMN_NAME));
        bankMoneyRestockTransaction.setConcept(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_CONCEPT_COLUMN_NAME));
        bankMoneyRestockTransaction.setAmount(bankMoneyRestockTransactionRecord.getFloatValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_AMOUNT_COLUMN_NAME));
        bankMoneyRestockTransaction.setTimeStamp(Timestamp.valueOf(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TIMESTAMP_COLUMN_NAME)));
        bankMoneyRestockTransaction.setMemo(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_MEMO_COLUMN_NAME));
        bankMoneyRestockTransaction.setTransactionStatus(TransactionStatusRestockDestock.getByCode(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TRANSACTION_STATUS_COLUMN_NAME)));

        return bankMoneyRestockTransaction;
    }

    public void saveBankMoneyRestockTransactionData(BankMoneyTransaction bankMoneyTransaction) throws DatabaseOperationException, MissingBankMoneyDestockDataException {

        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TABLE_NAME);
            DatabaseTableRecord bankMoneyRestockRecord = getBankMoneyRestockRecord(bankMoneyTransaction);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(bankMoneyTransaction.getTransactionId().toString());
            filter.setColumn(BussinessTransactionBankMoneyDestockDatabaseConstants.BANK_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, bankMoneyRestockRecord);
            else {
                table.setStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, bankMoneyRestockRecord);
            }

            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Bank Money Restock Transaction in the database.", null);
        }
    }

    public List<BankMoneyTransaction> getBankMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<BankMoneyTransaction> bankMoneyTransactions = new ArrayList<>();
            // I will add the Asset Factory information from the database
            for (DatabaseTableRecord bankMoneyRestockRecord : getBankMoneyRestockData(filter)) {
                final BankMoneyTransaction bankMoneyTransaction = getBankMoneyRestockTransaction(bankMoneyRestockRecord);

                bankMoneyTransactions.add(bankMoneyTransaction);
            }

            database.closeDatabase();

            return bankMoneyTransactions;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Bank Money Restock Transaction from the database with filter: " + filter.toString(), null);
        }
    }
}
