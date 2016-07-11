package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.interfaces.CryptoUnholdTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.MissingUnHoldCryptoDataException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.utils.UnHoldCryptoMoneyTransactionImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>UnHoldCryptoMoneyTransactionDatabaseDao</code>
 * contains the logic for handling database the plugin
 * Created by Franklin Marcano on 23/11/15.
 */
public class UnHoldCryptoMoneyTransactionDatabaseDao {
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
    public UnHoldCryptoMoneyTransactionDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            UnHoldCryptoMoneyTransactionDatabaseFactory unHoldCryptoMoneyTransactionDatabaseFactory = new UnHoldCryptoMoneyTransactionDatabaseFactory(this.pluginDatabaseSystem);
            database = unHoldCryptoMoneyTransactionDatabaseFactory.createDatabase(this.pluginId, UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_DATABASE_NAME);
        }
        return database;
    }

    private DatabaseTableRecord getUnHoldCryptoRecord(CryptoUnholdTransaction cryptoUnHoldTransaction) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setUUIDValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME, cryptoUnHoldTransaction.getTransactionId());
        record.setStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME, cryptoUnHoldTransaction.getPublicKeyActor());
        record.setStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME, cryptoUnHoldTransaction.getPublicKeyWallet());
        record.setStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME, cryptoUnHoldTransaction.getPublicKeyPlugin());
        record.setFloatValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_AMOUNT_COLUMN_NAME, cryptoUnHoldTransaction.getAmount());
        record.setStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_CURRENCY_COLUMN_NAME, cryptoUnHoldTransaction.getCurrency().getCode());
        record.setStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_MEMO_COLUMN_NAME, cryptoUnHoldTransaction.getMemo());
        record.setLongValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME, cryptoUnHoldTransaction.getTimestampAcknowledged());
        record.setLongValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME, cryptoUnHoldTransaction.getTimestampConfirmedRejected());
        record.setStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME, cryptoUnHoldTransaction.getStatus().getCode());
        record.setStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME, cryptoUnHoldTransaction.getBlockchainNetworkType().getCode());
        record.setLongValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_FEE_COLUMN_NAME, cryptoUnHoldTransaction.getFee());
        record.setStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_FEE_ORIGIN_COLUMN_NAME, cryptoUnHoldTransaction.getFeeOrigin().getCode());

        return record;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    private List<DatabaseTableRecord> getHoldCryptoData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private CryptoUnholdTransaction getCryptoUnHoldTransaction(final DatabaseTableRecord crtyptoUnHoldTransactionRecord) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException {

        UnHoldCryptoMoneyTransactionImpl unHoldCryptoMoneyTransaction = new UnHoldCryptoMoneyTransactionImpl();

        unHoldCryptoMoneyTransaction.setTransactionId(crtyptoUnHoldTransactionRecord.getUUIDValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME));
        unHoldCryptoMoneyTransaction.setPublicKeyWallet(crtyptoUnHoldTransactionRecord.getStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME));
        unHoldCryptoMoneyTransaction.setPublicKeyActor(crtyptoUnHoldTransactionRecord.getStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME));
        unHoldCryptoMoneyTransaction.setPublicKeyPlugin(crtyptoUnHoldTransactionRecord.getStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME));
        unHoldCryptoMoneyTransaction.setAmount(crtyptoUnHoldTransactionRecord.getFloatValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_AMOUNT_COLUMN_NAME));
        unHoldCryptoMoneyTransaction.setCurrency(CryptoCurrency.getByCode(crtyptoUnHoldTransactionRecord.getStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_CURRENCY_COLUMN_NAME)));
        unHoldCryptoMoneyTransaction.setMemo(crtyptoUnHoldTransactionRecord.getStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_MEMO_COLUMN_NAME));
        unHoldCryptoMoneyTransaction.setTimestampAcknowledged(crtyptoUnHoldTransactionRecord.getLongValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME));
        unHoldCryptoMoneyTransaction.setTimestampConfirmedRejected(crtyptoUnHoldTransactionRecord.getLongValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME));
        unHoldCryptoMoneyTransaction.setStatus(CryptoTransactionStatus.getByCode(crtyptoUnHoldTransactionRecord.getStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_STATUS_COLUMN_NAME)));
        unHoldCryptoMoneyTransaction.setBlockchainNetworkType(BlockchainNetworkType.getByCode(crtyptoUnHoldTransactionRecord.getStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME)));
        //Fee fields
        long longFee = crtyptoUnHoldTransactionRecord.getLongValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_FEE_COLUMN_NAME);
        long minimalFee = BitcoinFee.SLOW.getFee();
        if(longFee < minimalFee){
            longFee = minimalFee;
        }
        unHoldCryptoMoneyTransaction.setFee(longFee);
        FeeOrigin feeOrigin;
        String feeOriginString = crtyptoUnHoldTransactionRecord.getStringValue(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_FEE_ORIGIN_COLUMN_NAME);
        if(feeOriginString==null||feeOriginString.isEmpty()){
            feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
        } else {
            try{
                feeOrigin = FeeOrigin.getByCode(feeOriginString);
            } catch (InvalidParameterException ex){
                feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
            }
        }
        unHoldCryptoMoneyTransaction.setFeeOrigin(feeOrigin);

        return unHoldCryptoMoneyTransaction;
    }

    public void saveUnHoldCryptoTransactionData(CryptoUnholdTransaction cryptoUnHoldTransaction) throws DatabaseOperationException, MissingUnHoldCryptoDataException {

        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TABLE_NAME);
            DatabaseTableRecord unHoldCryptoRecord = getUnHoldCryptoRecord(cryptoUnHoldTransaction);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(cryptoUnHoldTransaction.getTransactionId().toString());
            filter.setColumn(UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, unHoldCryptoRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, unHoldCryptoRecord);
            }

            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Hold Crypto Transaction in the database.", null);
        }
    }

    public List<CryptoUnholdTransaction> getUnHoldCryptoTransactionList(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException
    {
        Database database = null;
        try {
            database = openDatabase();
            List<CryptoUnholdTransaction> cryptoUnHoldTransactions = new ArrayList<>();
            // I will add the Asset Factory information from the database
            for (DatabaseTableRecord holdCryptokRecord : getHoldCryptoData(filter)) {
                final CryptoUnholdTransaction cryptoUnHoldTransaction = getCryptoUnHoldTransaction(holdCryptokRecord);

                cryptoUnHoldTransactions.add(cryptoUnHoldTransaction);
            }

            database.closeDatabase();

            return cryptoUnHoldTransactions;
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get Hold Crypto Transaction from the database with filter: " + filter.toString(), null);
        }
    }
}
