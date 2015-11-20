package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
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
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyDestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.CryptoMoneyDestockTransactionImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>BusinessTransactionBankMoneyRestockDatabaseDao</code>
 * contains the logic for handling database the plugin
 * Created by franklin on 17/11/15.
 */
public class StockTransactionsCryptoMoneyDestockDatabaseDao {

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
    public StockTransactionsCryptoMoneyDestockDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            StockTransactionsCryptoMoneyDestockDatabaseFactory stockTransactionsCryptoMoneyDestockDatabaseFactory = new StockTransactionsCryptoMoneyDestockDatabaseFactory(this.pluginDatabaseSystem);
            database = stockTransactionsCryptoMoneyDestockDatabaseFactory.createDatabase(this.pluginId, StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getBankMoneyRestockRecord(CryptoMoneyTransaction cryptoMoneyTransaction
                                                          ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME, cryptoMoneyTransaction.getTransactionId());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, cryptoMoneyTransaction.getActorPublicKey());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CRYPTO_CURRENCY_COLUMN_NAME, cryptoMoneyTransaction.getCryptoCurrency().getCode());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, cryptoMoneyTransaction.getCbpWalletPublicKey());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CRY_WALLET_PUBLIC_KEY_COLUMN_NAME, cryptoMoneyTransaction.getCryWalletPublicKey());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CONCEPT_COLUMN_NAME, cryptoMoneyTransaction.getConcept());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TIMESTAMP_COLUMN_NAME, cryptoMoneyTransaction.getTimeStamp().toString());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_MEMO_COLUMN_NAME, cryptoMoneyTransaction.getMemo());
        record.setFloatValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_MEMO_COLUMN_NAME, cryptoMoneyTransaction.getAmount());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_STATUS_COLUMN_NAME, cryptoMoneyTransaction.getTransactionStatus().getCode());

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

    private List<DatabaseTableRecord> getCryptoMoneyRestockData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TABLE_NAME);

        if (filter != null)
            table.setStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private CryptoMoneyTransaction getCryptoMoneyRestockTransaction(final DatabaseTableRecord bankMoneyRestockTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException, com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException {

        CryptoMoneyDestockTransactionImpl cryptoMoneyDestockTransaction = new CryptoMoneyDestockTransactionImpl();

        cryptoMoneyDestockTransaction.setTransactionId(bankMoneyRestockTransactionRecord.getUUIDValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setActorPublicKey(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setCryptoCurrency(CryptoCurrency.getByCode(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CRYPTO_CURRENCY_COLUMN_NAME)));
        cryptoMoneyDestockTransaction.setCbpWalletPublicKey(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setCryWalletPublicKey(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CRY_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setConcept(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CONCEPT_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setAmount(bankMoneyRestockTransactionRecord.getFloatValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_AMOUNT_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setTimeStamp(Timestamp.valueOf(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TIMESTAMP_COLUMN_NAME)));
        cryptoMoneyDestockTransaction.setMemo(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_MEMO_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setTransactionStatus(TransactionStatusRestockDestock.getByCode(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_STATUS_COLUMN_NAME)));

        return cryptoMoneyDestockTransaction;
    }

    public void saveCryptoMoneyRestockTransactionData(CryptoMoneyTransaction cryptoMoneyTransaction) throws DatabaseOperationException, MissingCryptoMoneyDestockDataException {

        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TABLE_NAME);
            DatabaseTableRecord bankMoneyRestockRecord = getBankMoneyRestockRecord(cryptoMoneyTransaction);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cryptoMoneyTransaction.getTransactionId().toString());
            filter.setColumn(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME);

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

    public List<CryptoMoneyTransaction> getCryptoMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<CryptoMoneyTransaction> cryptoMoneyTransactions = new ArrayList<>();
            // I will add the Asset Factory information from the database
            for (DatabaseTableRecord cryptoMoneyRestockRecord : getCryptoMoneyRestockData(filter)) {
                final CryptoMoneyTransaction cryptoMoneyTransaction = getCryptoMoneyRestockTransaction(cryptoMoneyRestockRecord);

                cryptoMoneyTransactions.add(cryptoMoneyTransaction);
            }

            database.closeDatabase();

            return cryptoMoneyTransactions;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Bank Money Restock Transaction from the database with filter: " + filter.toString(), null);
        }
    }
}
