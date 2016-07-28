package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
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
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyRestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure.CryptoMoneyRestockTransactionImpl;

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
public class StockTransactionsCryptoMoneyRestockDatabaseDao {

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
    public StockTransactionsCryptoMoneyRestockDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            StockTransactionsCryptoMoneyRestockDatabaseFactory stockTransactionsCryptoMoneyRestockDatabaseFactory = new StockTransactionsCryptoMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);
            database = stockTransactionsCryptoMoneyRestockDatabaseFactory.createDatabase(this.pluginId, StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getBankMoneyRestockRecord(CryptoMoneyTransaction cryptoMoneyTransaction
    ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME, cryptoMoneyTransaction.getTransactionId());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, cryptoMoneyTransaction.getActorPublicKey());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CRYPTO_CURRENCY_COLUMN_NAME, cryptoMoneyTransaction.getCryptoCurrency().getCode());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, cryptoMoneyTransaction.getCbpWalletPublicKey());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CRY_WALLET_PUBLIC_KEY_COLUMN_NAME, cryptoMoneyTransaction.getCryWalletPublicKey());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CONCEPT_COLUMN_NAME, cryptoMoneyTransaction.getConcept());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME, cryptoMoneyTransaction.getTimeStamp().toString());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_MEMO_COLUMN_NAME, cryptoMoneyTransaction.getMemo());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_AMOUNT_COLUMN_NAME, cryptoMoneyTransaction.getAmount().toPlainString());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME, cryptoMoneyTransaction.getTransactionStatus().getCode());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_PRICE_REFERENCE_COLUMN_NAME, cryptoMoneyTransaction.getPriceReference().toPlainString());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME, cryptoMoneyTransaction.getOriginTransaction().getCode());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME, cryptoMoneyTransaction.getOriginTransactionId());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME, cryptoMoneyTransaction.getBlockchainNetworkType().getCode());
        //Fee values
        record.setLongValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_FEE_COLUMN_NAME, cryptoMoneyTransaction.getFee());
        record.setStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_FEE_ORIGIN_COLUMN_NAME, cryptoMoneyTransaction.getFeeOrigin().getCode());

        return record;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private List<DatabaseTableRecord> getCryptoMoneyRestockData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private CryptoMoneyTransaction getCryptoMoneyRestockTransaction(final DatabaseTableRecord cryptoMoneyRestockTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        CryptoMoneyRestockTransactionImpl cryptoMoneyRestockTransaction = new CryptoMoneyRestockTransactionImpl();

        cryptoMoneyRestockTransaction.setTransactionId(cryptoMoneyRestockTransactionRecord.getUUIDValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME));
        cryptoMoneyRestockTransaction.setActorPublicKey(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME));
        cryptoMoneyRestockTransaction.setCryptoCurrency(CryptoCurrency.getByCode(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CRYPTO_CURRENCY_COLUMN_NAME)));
        cryptoMoneyRestockTransaction.setCbpWalletPublicKey(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cryptoMoneyRestockTransaction.setCryWalletPublicKey(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CRY_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cryptoMoneyRestockTransaction.setConcept(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CONCEPT_COLUMN_NAME));
        cryptoMoneyRestockTransaction.setAmount(new BigDecimal(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_AMOUNT_COLUMN_NAME)));
        cryptoMoneyRestockTransaction.setTimeStamp(Timestamp.valueOf(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME)));
        cryptoMoneyRestockTransaction.setMemo(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_MEMO_COLUMN_NAME));
        cryptoMoneyRestockTransaction.setTransactionStatus(TransactionStatusRestockDestock.getByCode(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME)));
        cryptoMoneyRestockTransaction.setPriceReference(new BigDecimal(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_PRICE_REFERENCE_COLUMN_NAME)));
        cryptoMoneyRestockTransaction.setOriginTransaction(OriginTransaction.getByCode(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME)));
        cryptoMoneyRestockTransaction.setOriginTransactionId(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME));
        cryptoMoneyRestockTransaction.setBlockchainNetworkType(BlockchainNetworkType.getByCode(cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME)));
        //Fee values
        long fee = cryptoMoneyRestockTransactionRecord.getLongValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_FEE_COLUMN_NAME);
        long minimalFee = BitcoinFee.SLOW.getFee();
        if (fee < minimalFee) {
            fee = minimalFee;
        }
        String feeOriginString = cryptoMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_FEE_ORIGIN_COLUMN_NAME);
        FeeOrigin feeOrigin;
        if (feeOriginString == null || feeOriginString.isEmpty()) {
            feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
        } else {
            try {
                feeOrigin = FeeOrigin.getByCode(feeOriginString);
            } catch (InvalidParameterException ex) {
                feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
            }
        }
        cryptoMoneyRestockTransaction.setFee(fee);
        cryptoMoneyRestockTransaction.setFeeOrigin(feeOrigin);

        return cryptoMoneyRestockTransaction;
    }

    public void saveCryptoMoneyRestockTransactionData(CryptoMoneyTransaction cryptoMoneyTransaction) throws DatabaseOperationException, MissingCryptoMoneyRestockDataException {

        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            //TODO: Solo para prueba ya que priceReference viene null desde android revisar con Nelson
            cryptoMoneyTransaction.setPriceReference(new BigDecimal(0));
            DatabaseTable table = getDatabaseTable(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TABLE_NAME);
            DatabaseTableRecord bankMoneyRestockRecord = getBankMoneyRestockRecord(cryptoMoneyTransaction);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cryptoMoneyTransaction.getTransactionId().toString());
            filter.setColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME);

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

    public List<CryptoMoneyTransaction> getCryptoMoneyTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException {
        Database database = null;
        try {
            database = openDatabase();
            List<CryptoMoneyTransaction> cryptoMoneyTransactions = new ArrayList<>();
            // I will add the Asset Factory information from the database
            for (DatabaseTableRecord cryptoMoneyRestockRecord : getCryptoMoneyRestockData(filter)) {
                final CryptoMoneyTransaction cryptoMoneyTransaction = getCryptoMoneyRestockTransaction(cryptoMoneyRestockRecord);

                if (!cryptoMoneyTransaction.getTransactionStatus().equals(TransactionStatusRestockDestock.COMPLETED))
                    cryptoMoneyTransactions.add(cryptoMoneyTransaction);
            }

            database.closeDatabase();

            return cryptoMoneyTransactions;
        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, new StringBuilder().append("error trying to get Bank Money Restock Transaction from the database with filter: ").append(filter.toString()).toString(), null);
        }
    }
}
