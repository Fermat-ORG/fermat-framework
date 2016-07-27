package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.MissingCryptoMoneyDestockDataException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.CryptoMoneyDestockTransactionImpl;

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

    private DatabaseTableRecord getBankMoneyDestockRecord(CryptoMoneyTransaction cryptoMoneyTransaction
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
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_AMOUNT_COLUMN_NAME, cryptoMoneyTransaction.getAmount().toPlainString());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_STATUS_COLUMN_NAME, cryptoMoneyTransaction.getTransactionStatus().getCode());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_PRICE_REFERENCE_COLUMN_NAME, cryptoMoneyTransaction.getPriceReference().toPlainString());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME, cryptoMoneyTransaction.getOriginTransaction().getCode());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME, cryptoMoneyTransaction.getOriginTransactionId());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME, cryptoMoneyTransaction.getBlockchainNetworkType().getCode());
        //Fee values
        record.setLongValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_FEE_COLUMN_NAME, cryptoMoneyTransaction.getFee());
        record.setStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_ORIGIN_FEE_COLUMN_NAME, cryptoMoneyTransaction.getFeeOrigin().getCode());

        return record;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private List<DatabaseTableRecord> getCryptoMoneyDestockData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private CryptoMoneyTransaction getCryptoMoneyRestockTransaction(final DatabaseTableRecord bankMoneyRestockTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        CryptoMoneyDestockTransactionImpl cryptoMoneyDestockTransaction = new CryptoMoneyDestockTransactionImpl();

        cryptoMoneyDestockTransaction.setTransactionId(bankMoneyRestockTransactionRecord.getUUIDValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setActorPublicKey(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setCryptoCurrency(CryptoCurrency.getByCode(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CRYPTO_CURRENCY_COLUMN_NAME)));
        cryptoMoneyDestockTransaction.setCbpWalletPublicKey(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setCryWalletPublicKey(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CRY_WALLET_PUBLIC_KEY_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setConcept(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_CONCEPT_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setAmount(new BigDecimal(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_AMOUNT_COLUMN_NAME)));
        cryptoMoneyDestockTransaction.setTimeStamp(Timestamp.valueOf(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TIMESTAMP_COLUMN_NAME)));
        cryptoMoneyDestockTransaction.setMemo(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_MEMO_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setTransactionStatus(TransactionStatusRestockDestock.getByCode(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_STATUS_COLUMN_NAME)));
        cryptoMoneyDestockTransaction.setPriceReference(new BigDecimal(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_PRICE_REFERENCE_COLUMN_NAME)));
        cryptoMoneyDestockTransaction.setOriginTransaction(OriginTransaction.getByCode(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME)));
        cryptoMoneyDestockTransaction.setOriginTransactionId(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME));
        cryptoMoneyDestockTransaction.setBlockchainNetworkType(BlockchainNetworkType.getByCode(bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME)));

        //Fee values
        long fee = bankMoneyRestockTransactionRecord.getLongValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_FEE_COLUMN_NAME);
        long minimalFee = BitcoinFee.SLOW.getFee();
        if (fee < minimalFee) {
            fee = minimalFee;
        }
        String feeOriginString = bankMoneyRestockTransactionRecord.getStringValue(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_ORIGIN_FEE_COLUMN_NAME);
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
        cryptoMoneyDestockTransaction.setFee(fee);
        cryptoMoneyDestockTransaction.setFeeOrigin(feeOrigin);

        return cryptoMoneyDestockTransaction;
    }

    public void saveCryptoMoneyDestockTransactionData(CryptoMoneyTransaction cryptoMoneyTransaction) throws DatabaseOperationException, MissingCryptoMoneyDestockDataException {

        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            //TODO: Solo para prueba ya que priceReference viene null desde android revisar con Nelson
            cryptoMoneyTransaction.setPriceReference(new BigDecimal(0));

            DatabaseTable table = getDatabaseTable(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TABLE_NAME);
            DatabaseTableRecord bankMoneyRestockRecord = getBankMoneyDestockRecord(cryptoMoneyTransaction);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cryptoMoneyTransaction.getTransactionId().toString());
            filter.setColumn(StockTransactionsCrpytoMoneyDestockDatabaseConstants.CRYPTO_MONEY_DESTOCK_TRANSACTION_ID_COLUMN_NAME);

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
            for (DatabaseTableRecord cryptoMoneyRestockRecord : getCryptoMoneyDestockData(filter)) {
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
