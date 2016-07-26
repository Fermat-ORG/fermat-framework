package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions.MissingCashMoneyDestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.CashMoneyDestockTransactionImpl;

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
public class StockTransactionsCashMoneyDestockDatabaseDao {

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
    public StockTransactionsCashMoneyDestockDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            StockTransactionsCashMoneyDestockDatabaseFactory stockTransactionsCashMoneyDestockDatabaseFactory = new StockTransactionsCashMoneyDestockDatabaseFactory(this.pluginDatabaseSystem);
            database = stockTransactionsCashMoneyDestockDatabaseFactory.createDatabase(this.pluginId, StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getCashMoneyDestockRecord(CashMoneyTransaction cashMoneyTransaction
    ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME, cashMoneyTransaction.getTransactionId());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, cashMoneyTransaction.getActorPublicKey());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_FIAT_CURRENCY_COLUMN_NAME, cashMoneyTransaction.getFiatCurrency().getCode());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, cashMoneyTransaction.getCbpWalletPublicKey());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_CSH_WALLET_PUBLIC_KEY_COLUMN_NAME, cashMoneyTransaction.getCashWalletPublicKey());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_CASH_REFERENCE_COLUMN_NAME, cashMoneyTransaction.getCashReference());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_CONCEPT_COLUMN_NAME, cashMoneyTransaction.getConcept());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TIMESTAMP_COLUMN_NAME, cashMoneyTransaction.getTimeStamp().toString());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_MEMO_COLUMN_NAME, cashMoneyTransaction.getMemo());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_AMOUNT_COLUMN_NAME, cashMoneyTransaction.getAmount().toPlainString());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TRANSACTION_STATUS_COLUMN_NAME, cashMoneyTransaction.getTransactionStatus().getCode());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_PRICE_REFERENCE_COLUMN_NAME, cashMoneyTransaction.getPriceReference().toPlainString());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME, cashMoneyTransaction.getOriginTransaction().getCode());
        record.setStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME, cashMoneyTransaction.getOriginTransactionId());

        return record;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private List<DatabaseTableRecord> getCashMoneyDestockData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private CashMoneyTransaction getCashMoneyDestockTransaction(final DatabaseTableRecord cashMoneyRestockTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        CashMoneyDestockTransactionImpl cashMoneyDestockTransaction = new CashMoneyDestockTransactionImpl();

        cashMoneyDestockTransaction.setTransactionId(cashMoneyRestockTransactionRecord.getUUIDValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME));
        cashMoneyDestockTransaction.setActorPublicKey(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME));
        cashMoneyDestockTransaction.setFiatCurrency(FiatCurrency.getByCode(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_FIAT_CURRENCY_COLUMN_NAME)));
        cashMoneyDestockTransaction.setCbpWalletPublicKey(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cashMoneyDestockTransaction.setCashWalletPublicKey(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_CSH_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cashMoneyDestockTransaction.setCashReference(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_CASH_REFERENCE_COLUMN_NAME));
        cashMoneyDestockTransaction.setConcept(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_CONCEPT_COLUMN_NAME));
        cashMoneyDestockTransaction.setAmount(new BigDecimal(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_AMOUNT_COLUMN_NAME)));
        cashMoneyDestockTransaction.setTimeStamp(Timestamp.valueOf(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TIMESTAMP_COLUMN_NAME)));
        cashMoneyDestockTransaction.setMemo(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_MEMO_COLUMN_NAME));
        cashMoneyDestockTransaction.setTransactionStatus(TransactionStatusRestockDestock.getByCode(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TRANSACTION_STATUS_COLUMN_NAME)));
        cashMoneyDestockTransaction.setPriceReference(new BigDecimal(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_PRICE_REFERENCE_COLUMN_NAME)));
        cashMoneyDestockTransaction.setOriginTransaction(OriginTransaction.getByCode(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME)));
        cashMoneyDestockTransaction.setOriginTransactionId(cashMoneyRestockTransactionRecord.getStringValue(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME));

        return cashMoneyDestockTransaction;
    }

    public void saveCashMoneyDestockTransactionData(CashMoneyTransaction cashMoneyTransaction) throws DatabaseOperationException, MissingCashMoneyDestockDataException {

        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            //TODO: Solo para prueba ya que priceReference viene null desde android revisar con Nelson
            cashMoneyTransaction.setPriceReference(new BigDecimal(0));

            DatabaseTable table = getDatabaseTable(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TABLE_NAME);
            DatabaseTableRecord bankMoneyRestockRecord = getCashMoneyDestockRecord(cashMoneyTransaction);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cashMoneyTransaction.getTransactionId().toString());
            filter.setColumn(StockTransactionsCashMoneyDestockDatabaseConstants.CASH_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME);

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
        try {
            openDatabase();
            List<CashMoneyTransaction> cashMoneyTransactions = new ArrayList<>();

            final List<DatabaseTableRecord> cashMoneyDestockData = getCashMoneyDestockData(filter);
            for (DatabaseTableRecord cashMoneyRestockRecord : cashMoneyDestockData) {
                final CashMoneyTransaction cashMoneyTransaction = getCashMoneyDestockTransaction(cashMoneyRestockRecord);

                if (!cashMoneyTransaction.getTransactionStatus().equals(TransactionStatusRestockDestock.COMPLETED))
                    cashMoneyTransactions.add(cashMoneyTransaction);
            }

            return cashMoneyTransactions;
        } catch (Exception e) {
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e,
                    new StringBuilder().append("error trying to get Bank Money Restock Transaction from the database with filter: ").append(filter.toString()).toString(), null);
        }
    }
}
