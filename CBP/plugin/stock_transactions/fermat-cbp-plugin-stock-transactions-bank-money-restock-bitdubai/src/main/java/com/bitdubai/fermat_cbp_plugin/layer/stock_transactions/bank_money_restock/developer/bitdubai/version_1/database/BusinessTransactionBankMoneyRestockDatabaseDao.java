package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.MissingBankMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.BankMoneyRestockTransactionImpl;

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
public class BusinessTransactionBankMoneyRestockDatabaseDao {

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
    public BusinessTransactionBankMoneyRestockDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            BusinessTransactionBankMoneyRestockDatabaseFactory businessTransactionBankMoneyRestockDatabaseFactory = new BusinessTransactionBankMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);
            database = businessTransactionBankMoneyRestockDatabaseFactory.createDatabase(this.pluginId, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getBankMoneyRestockRecord(BankMoneyTransaction bankMoneyTransaction
    ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME, bankMoneyTransaction.getTransactionId());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, bankMoneyTransaction.getActorPublicKey());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_FIAT_CURRENCY_COLUMN_NAME, bankMoneyTransaction.getFiatCurrency().getCode());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_BNK_WALLET_PUBLIC_KEY_COLUMN_NAME, bankMoneyTransaction.getBnkWalletPublicKey());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, bankMoneyTransaction.getCbpWalletPublicKey());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_BANK_ACCOUNT_COLUMN_NAME, bankMoneyTransaction.getBankAccount());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_CONCEPT_COLUMN_NAME, bankMoneyTransaction.getConcept());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME, bankMoneyTransaction.getTimeStamp().toString());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_MEMO_COLUMN_NAME, bankMoneyTransaction.getMemo());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_AMOUNT_COLUMN_NAME, bankMoneyTransaction.getAmount().toPlainString());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME, bankMoneyTransaction.getTransactionStatus().getCode());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_PRICE_REFERENCE_COLUMN_NAME, bankMoneyTransaction.getPriceReference().toPlainString());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME, bankMoneyTransaction.getOriginTransaction().getCode());
        record.setStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME, bankMoneyTransaction.getOriginTransactionId());

        return record;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private List<DatabaseTableRecord> getBankMoneyRestockData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private BankMoneyTransaction getBankMoneyRestockTransaction(final DatabaseTableRecord bankMoneyRestockTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        BankMoneyRestockTransactionImpl bankMoneyRestockTransaction = new BankMoneyRestockTransactionImpl();

        bankMoneyRestockTransaction.setTransactionId(bankMoneyRestockTransactionRecord.getUUIDValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME));
        bankMoneyRestockTransaction.setActorPublicKey(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME));
        bankMoneyRestockTransaction.setFiatCurrency(FiatCurrency.getByCode(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_FIAT_CURRENCY_COLUMN_NAME)));
        bankMoneyRestockTransaction.setCbpWalletPublicKey(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME));
        bankMoneyRestockTransaction.setBnkWalletPublicKey(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_BNK_WALLET_PUBLIC_KEY_COLUMN_NAME));
        bankMoneyRestockTransaction.setBankAccount(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_BANK_ACCOUNT_COLUMN_NAME));
        bankMoneyRestockTransaction.setConcept(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_CONCEPT_COLUMN_NAME));
        bankMoneyRestockTransaction.setAmount(new BigDecimal(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_AMOUNT_COLUMN_NAME)));
        bankMoneyRestockTransaction.setTimeStamp(Timestamp.valueOf(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME)));
        bankMoneyRestockTransaction.setMemo(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_MEMO_COLUMN_NAME));
        bankMoneyRestockTransaction.setTransactionStatus(TransactionStatusRestockDestock.getByCode(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME)));
        bankMoneyRestockTransaction.setPriceReference(new BigDecimal(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_PRICE_REFERENCE_COLUMN_NAME)));
        bankMoneyRestockTransaction.setOriginTransaction(OriginTransaction.getByCode(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME)));
        bankMoneyRestockTransaction.setOriginTransactionId(bankMoneyRestockTransactionRecord.getStringValue(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME));

        return bankMoneyRestockTransaction;
    }

    public void saveBankMoneyRestockTransactionData(BankMoneyTransaction bankMoneyTransaction) throws DatabaseOperationException, MissingBankMoneyRestockDataException {

        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            //TODO: Solo para prueba ya que priceReference viene null desde android revisar con Nelson
            bankMoneyTransaction.setPriceReference(new BigDecimal(0));

            /*//TODO:Revisar con guillermo que el accountNumber viene null
            bankMoneyTransaction.setBankAccount("123456");
            */
            DatabaseTable table = getDatabaseTable(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_TABLE_NAME);
            DatabaseTableRecord bankMoneyRestockRecord = getBankMoneyRestockRecord(bankMoneyTransaction);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(bankMoneyTransaction.getTransactionId().toString());
            filter.setColumn(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME);

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

    public List<BankMoneyTransaction> getBankMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException {
        Database database = null;
        try {
            database = openDatabase();
            List<BankMoneyTransaction> bankMoneyTransactions = new ArrayList<>();
            // I will add the Asset Factory information from the database
            for (DatabaseTableRecord bankMoneyRestockRecord : getBankMoneyRestockData(filter)) {
                final BankMoneyTransaction bankMoneyTransaction = getBankMoneyRestockTransaction(bankMoneyRestockRecord);

                if (!bankMoneyTransaction.getTransactionStatus().equals(TransactionStatusRestockDestock.COMPLETED))
                    bankMoneyTransactions.add(bankMoneyTransaction);
            }

            database.closeDatabase();

            return bankMoneyTransactions;
        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, new StringBuilder().append("error trying to get Bank Money Restock Transaction from the database with filter: ").append(filter.toString()).toString(), null);
        }
    }
}
