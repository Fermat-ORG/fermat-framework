package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.database;


import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
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
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.exceptions.MissingCashMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.CashMoneyRestockTransactionImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockDatabaseDao</code>
 * contains the logic for handling database the plugin
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCashMoneyRestockDatabaseDao {

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
    public StockTransactionsCashMoneyRestockDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            StockTransactionsCashMoneyRestockDatabaseFactory stockTransactionsCashMoneyRestockDatabaseFactory = new StockTransactionsCashMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);
            database = stockTransactionsCashMoneyRestockDatabaseFactory.createDatabase(this.pluginId, StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getCashMoneyRestockRecord(CashMoneyTransaction cashMoneyTransaction
    ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME, cashMoneyTransaction.getTransactionId());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, cashMoneyTransaction.getActorPublicKey());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_FIAT_CURRENCY_COLUMN_NAME, cashMoneyTransaction.getFiatCurrency().getCode());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, cashMoneyTransaction.getCbpWalletPublicKey());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CSH_WALLET_PUBLIC_KEY_COLUMN_NAME, cashMoneyTransaction.getCashWalletPublicKey());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CASH_REFERENCE_COLUMN_NAME, cashMoneyTransaction.getCashReference());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CONCEPT_COLUMN_NAME, cashMoneyTransaction.getConcept());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME, cashMoneyTransaction.getTimeStamp().toString());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_MEMO_COLUMN_NAME, cashMoneyTransaction.getMemo());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_AMOUNT_COLUMN_NAME, cashMoneyTransaction.getAmount().toPlainString());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME, cashMoneyTransaction.getTransactionStatus().getCode());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_PRICE_REFERENCE_COLUMN_NAME, cashMoneyTransaction.getPriceReference().toPlainString());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME, cashMoneyTransaction.getOriginTransaction().getCode());
        record.setStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME, cashMoneyTransaction.getOriginTransactionId());

        return record;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private List<DatabaseTableRecord> getCashMoneyRestockData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private CashMoneyTransaction getCashMoneyRestockTransaction(final DatabaseTableRecord cashMoneyRestockTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        CashMoneyRestockTransactionImpl cashMoneyRestockTransaction = new CashMoneyRestockTransactionImpl();

        cashMoneyRestockTransaction.setTransactionId(cashMoneyRestockTransactionRecord.getUUIDValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME));
        cashMoneyRestockTransaction.setActorPublicKey(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME));
        cashMoneyRestockTransaction.setFiatCurrency(FiatCurrency.getByCode(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_FIAT_CURRENCY_COLUMN_NAME)));
        cashMoneyRestockTransaction.setCbpWalletPublicKey(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cashMoneyRestockTransaction.setCashWalletPublicKey(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CSH_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cashMoneyRestockTransaction.setCashReference(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CASH_REFERENCE_COLUMN_NAME));
        cashMoneyRestockTransaction.setConcept(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_CONCEPT_COLUMN_NAME));
        cashMoneyRestockTransaction.setAmount(new BigDecimal(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_AMOUNT_COLUMN_NAME)));
        cashMoneyRestockTransaction.setTimeStamp(Timestamp.valueOf(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME)));
        cashMoneyRestockTransaction.setMemo(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_MEMO_COLUMN_NAME));
        cashMoneyRestockTransaction.setTransactionStatus(TransactionStatusRestockDestock.getByCode(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME)));
        cashMoneyRestockTransaction.setPriceReference(new BigDecimal(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_PRICE_REFERENCE_COLUMN_NAME)));
        cashMoneyRestockTransaction.setOriginTransaction(OriginTransaction.getByCode(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME)));
        cashMoneyRestockTransaction.setOriginTransactionId(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME));

        return cashMoneyRestockTransaction;
    }

    public void saveCashMoneyRestockTransactionData(CashMoneyTransaction cashMoneyTransaction) throws DatabaseOperationException, MissingCashMoneyRestockDataException {

        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TABLE_NAME);
            DatabaseTableRecord bankMoneyRestockRecord = getCashMoneyRestockRecord(cashMoneyTransaction);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cashMoneyTransaction.getTransactionId().toString());
            filter.setColumn(StockTransactionsCashMoneyRestockDatabaseConstants.CASH_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, bankMoneyRestockRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, bankMoneyRestockRecord);
            }

            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);
            database.closeDatabase();

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Bank Money Restock Transaction in the database.", null);
        }
    }

    public List<CashMoneyTransaction> getCashMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException {
        Database database = null;
        try {
            database = openDatabase();
            List<CashMoneyTransaction> cashMoneyTransactions = new ArrayList<>();
            // I will add the Asset Factory information from the database
            for (DatabaseTableRecord cashMoneyRestockRecord : getCashMoneyRestockData(filter)) {
                final CashMoneyTransaction cashMoneyTransaction = getCashMoneyRestockTransaction(cashMoneyRestockRecord);

                if (!cashMoneyTransaction.getTransactionStatus().equals(TransactionStatusRestockDestock.COMPLETED))
                    cashMoneyTransactions.add(cashMoneyTransaction);
            }

            database.closeDatabase();

            return cashMoneyTransactions;
        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, new StringBuilder().append("error trying to get Bank Money Restock Transaction from the database with filter: ").append(filter.toString()).toString(), null);
        }
    }
}
