package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisAddressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/08/15.
 */
public class AssetIssuingTransactionDao {

    UUID pluginId;
    Database database;

    PluginDatabaseSystem pluginDatabaseSystem;
    private final int INITIAL_DIGITAL_ASSET_GENERATED_AMOUNT = 0;

    public AssetIssuingTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        database = openDatabase();
        database.closeDatabase();
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        DatabaseTable assetIssuingDatabaseTable = database.getTable(tableName);
        return assetIssuingDatabaseTable;
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Issuing Transaction Database", "Error in database plugin.");
        }
    }

    public void updateTransactionProtocolStatus(String transactionID, ProtocolStatus protocolStatus) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME, transactionID, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + transactionID + " Protocol Status:" + protocolStatus.getCode());
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME, protocolStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        }
    }

    public void updateTransactionProtocolStatusByTransactionId(String transactionID, ProtocolStatus protocolStatus) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, transactionID, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + transactionID + " Protocol Status:" + protocolStatus.getCode());
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME, protocolStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        }
    }

    public void updateDigitalAssetTransactionStatus(String transactionID, TransactionStatus transactionStatus) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, transactionID, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + transactionID + "Transaction Status:" + transactionStatus.getCode());
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, transactionStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        }
    }

    public void updateEventStatus(String eventId) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_ID_COLUMN, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Event ID:" + eventId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_STATUS_COLUMN, EventStatus.NOTIFIED.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME, "Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME, "Check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME, "Check the cause");
        }
    }

    public void updateDigitalAssetTransactionStatusByGenesisTransaction(String genesisTransaction, TransactionStatus transactionStatus) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + genesisTransaction + "Transaction Status:" + transactionStatus.getCode());
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, transactionStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Trying to update " + AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME, "Check the cause");
        }
    }

    public void confirmReception(String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        updateTransactionProtocolStatus(genesisTransaction, ProtocolStatus.RECEPTION_NOTIFIED);
        updateDigitalAssetTransactionStatusByGenesisTransaction(genesisTransaction, TransactionStatus.DELIVERED);
    }

    public void persistDigitalAssetTransactionId(String digitalAssetPublicKey, String transactionId) throws CantPersistDigitalAssetException {

        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME, digitalAssetPublicKey);
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, transactionId);
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.FORMING_GENESIS.getCode());
            databaseTable.insertRecord(record);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception, "Opening the Asset Issuing plugin database", "Cannot open the Asset Issuing database");
        } catch (CantInsertRecordException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception, "Persisting a forming genesis digital asset", "Cannot insert a record in the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(FermatException.wrapException(exception), "Persisting a forming genesis digital asset", "Unexpected exception");
        }

    }

    public void persistDigitalAsset(String digitalAssetPublicKey,
                                    String digitalAssetLocalStoragePath,
                                    int assetsAmount, BlockchainNetworkType blockchainNetworkType,
                                    String walletPublickey) throws CantPersistDigitalAssetException {

        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, digitalAssetPublicKey);
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_LOCAL_STORAGE_PATH_COLUMN_NAME, digitalAssetLocalStoragePath);
            record.setIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE_COLUMN_NAME, assetsAmount);
            record.setIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME, INITIAL_DIGITAL_ASSET_GENERATED_AMOUNT);
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, blockchainNetworkType.getCode());
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublickey);
            record.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ISSUING_STATUS_COLUMN_NAME, IssuingStatus.ISSUING.getCode());
            databaseTable.insertRecord(record);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception, "Opening the Asset Issuing plugin database", "Cannot open the Asset Issuing database");
        } catch (CantInsertRecordException exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(exception, "Persisting a forming genesis digital asset", "Cannot insert a record in the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantPersistDigitalAssetException(FermatException.wrapException(exception), "Persisting a forming genesis digital asset", "Unexpected exception");
        }

    }

    public String getDigitalAssetPublicKeyById(String transactionId) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {

        return getStringFieldFromAssetIssuingTableById(transactionId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME);
    }

    public String getDigitalAssetGenesisAddressById(String transactionId) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringFieldFromAssetIssuingTableById(transactionId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_ADDRESS_COLUMN_NAME);
    }

    public String getDigitalAssetGenesisTransactionById(String transactionId) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringFieldFromAssetIssuingTableById(transactionId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public String getDigitalAssetHashById(String transactionId) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringFieldFromAssetIssuingTableById(transactionId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME);
    }

    public String getPublicKeyByTransactionHash(String transactionHash) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromAssetIssuingTableByFieldCode(transactionHash, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME);
    }

    public String getPublicKeyByGenesisTransaction(String genesisTransaction) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromAssetIssuingTableByFieldCode(genesisTransaction, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public String getTransactionIdByGenesisTransaction(String genesisTransaction) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromAssetIssuingTableByFieldCode(genesisTransaction, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public String getTransactionIdByTransactionhash(String genesisTransaction) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromAssetIssuingTableByFieldCode(genesisTransaction, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME);
    }

    private String getStringFieldFromAssetIssuingTableById(String transactionId, String fieldCode) throws UnexpectedResultReturnedFromDatabaseException, CantCheckAssetIssuingProgressException {
        return getStringValueFromAssetIssuingTableByFieldCode(transactionId, fieldCode, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME);
    }

    private String getWalletPublicKeyByDigitalAssetPublicKey(String digitalAssetPublicKey) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromAssetIssuingTableByFieldCode(digitalAssetPublicKey, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_WALLET_PUBLIC_KEY_COLUMN_NAME, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME);
    }

    private String getStringValueFromAssetIssuingTableByFieldCode(String value, String fieldCode, String indexColumn) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(indexColumn, value, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", indexColumn + ":" + value);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            this.database.closeDatabase();
            String stringToReturn = databaseTableRecord.getStringValue(fieldCode);
            return stringToReturn;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get " + fieldCode, "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get " + fieldCode, "Cannot load the database into memory");
        }
    }

    public String getDigitalAssetGenesisTransactionByHash(String transactionHash) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        return getStringValueFromAssetIssuingTableByFieldCode(transactionHash, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME);
    }

    public TransactionStatus getDigitalAssetTransactionStatus(String transactionId) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {

        try {
            String transactionStatusCode = getStringFieldFromAssetIssuingTableById(transactionId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME);
            return TransactionStatus.getByCode(transactionStatusCode);
        } catch (InvalidParameterException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Checking Transaction Status", "Unexpected exception");
        }
    }

    public List<String> getPendingDigitalAssetPublicKeys() throws CantCheckAssetIssuingProgressException {
        String publicKey;
        int assetsToGenerate;
        int assetsGenerated;
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            List<String> pendingDigitalAssetPublicKeyList = new ArrayList<>();
            for (DatabaseTableRecord databaseTableRecord : databaseTableRecords) {
                publicKey = databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME);
                assetsToGenerate = databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE_COLUMN_NAME);
                assetsGenerated = databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME);
                if (assetsToGenerate - assetsGenerated > 0) {
                    pendingDigitalAssetPublicKeyList.add(publicKey);
                }
            }
            this.database.closeDatabase();
            return pendingDigitalAssetPublicKeyList;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Checking pending Digital Assets PublicKeys", "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Checking pending Digital Assets PublicKeys", "Cannot load the database into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Checking pending Digital Assets PublicKeys", "Unexpected exception");
        }
    }

    public List<String> getPendingDigitalAssetsTransactionIdByPublicKey(String publicKey) throws CantCheckAssetIssuingProgressException {

        String digitalAssetTransactionStatus;
        String digitalAssetTransactionId;
        try {
            this.database = openDatabase();
            List<String> pendingDigitalAssetsTransactionId = new ArrayList<>();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            //Para ejecutar este segundo filtro necesitaría modificar el objeto DatabaseFilterType para que acepte NOT_EQUAL, por ahora, lo hago "MANUALMENTE"
            //databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, publicKey, DatabaseFilterType.);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            for (DatabaseTableRecord databaseTableRecord : databaseTableRecords) {
                //Este procedimiento puede cambiar si logro añadir el filtro que necesito.
                digitalAssetTransactionStatus = databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME);
                if (!digitalAssetTransactionStatus.equals(TransactionStatus.ISSUED)) {
                    digitalAssetTransactionId = databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME);
                    pendingDigitalAssetsTransactionId.add(digitalAssetTransactionId);
                }
            }
            this.database.closeDatabase();
            return pendingDigitalAssetsTransactionId;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting pending digital assets transactions to issue", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting pending digital assets transactions to issue", "Cannot load to memory the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Getting pending digital assets transactions to issue", "Unexpected exception");
        }

    }

    public String getIssuingStatusByPublicKey(String publicKey) throws CantCheckAssetIssuingProgressException {
        try {
            String context = "Asset Public Key: " + publicKey;

            this.database.openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Public key:" + publicKey);
            }
            if (databaseTableRecords.isEmpty()) {
                this.database.closeDatabase();
                throw new CantCheckAssetIssuingProgressException(null, context, "The given asset public key is not registered in the database.");
            }

            databaseTableRecord = databaseTableRecords.get(0);

            String issuingStatus = databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ISSUING_STATUS_COLUMN_NAME);
            this.database.closeDatabase();
            return issuingStatus;

        } catch (DatabaseNotFoundException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting the number of Digital Assets generated", "Cannot found the Asset Issuing database");
        } catch (CantOpenDatabaseException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting the number of Digital Assets generated", "Cannot open the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting the number of Digital Assets generated", "Cannot load the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Getting the number of Digital Assets generated", "Unexpected exception");
        }
    }

    public int getNumberOfIssuedAssets(String publicKey) throws CantCheckAssetIssuingProgressException {
        try {
            this.database.openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Public key:" + publicKey);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            int assetsGenerated = databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME);
            this.database.closeDatabase();
            return assetsGenerated;

        } catch (DatabaseNotFoundException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting the number of Digital Assets generated", "Cannot found the Asset Issuing database");
        } catch (CantOpenDatabaseException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting the number of Digital Assets generated", "Cannot open the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting the number of Digital Assets generated", "Cannot load the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Getting the number of Digital Assets generated", "Unexpected exception");
        }
    }

    public boolean isAnyPendingAsset() throws CantCheckAssetIssuingProgressException {
        List<String> pendingAssetsPublicKey = getPendingDigitalAssetPublicKeys();
        return !pendingAssetsPublicKey.isEmpty();
    }

    public int getNumberOfPendingAssets(String publicKey) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {

        try {
            this.database.openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Public key:" + publicKey);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            int assetsToGenerate = databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE_COLUMN_NAME);
            int assetsGenerated = databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME);
            this.database.closeDatabase();
            return assetsToGenerate - assetsGenerated;

        } catch (DatabaseNotFoundException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Opening the Asset Issuing plugin database", "Cannot found the Asset Issuing database");
        } catch (CantOpenDatabaseException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Opening the Asset Issuing plugin database", "Cannot open the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Loading Asset Issuing plugin database to memory", "Cannot load the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Checking pending assets to issue", "Unexpected exception");
        }
    }

    public void persistOutgoingIntraActorUUID(String transactionID, UUID outgoingId) throws CantPersistsTransactionUUIDException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, transactionID, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + transactionID + " OutgoingId:" + outgoingId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_OUTGOING_ID_COLUMN_NAME, outgoingId.toString());
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.GENESIS_OBTAINED.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistsTransactionUUIDException(exception, "Persisting OutgoingId in database", "Cannot open or find the database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantPersistsTransactionUUIDException(FermatException.wrapException(exception), "Persisting OutgoingId in database", "Unexpected exception");
        }
    }

    public void persistGenesisAddress(String transactionID, String genesisAddress) throws CantPersistsGenesisAddressException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, transactionID, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + transactionID + " Genesis Address:" + genesisAddress);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_ADDRESS_COLUMN_NAME, genesisAddress);
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.GENESIS_OBTAINED.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistsGenesisAddressException(exception, "Persisting Genesis Address in database", "Cannot open or find the database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantPersistsGenesisAddressException(FermatException.wrapException(exception), "Getting pending digital assets transactions to issue", "Unexpected exception");
        }
    }

    public void persistGenesisTransaction(String outgoingTransactionID, String genesisTransaction) throws CantPersistsGenesisTransactionException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_OUTGOING_ID_COLUMN_NAME, outgoingTransactionID, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + outgoingTransactionID + " Genesis Transaction:" + genesisTransaction);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction);
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.CRYPTO_SENT.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistsGenesisTransactionException(exception, "Persisting Genesis Transaction in database", "Cannot open or find the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantPersistsGenesisTransactionException(exception, "Persisting Genesis Transaction in database", "Cannot load the database into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantPersistsGenesisTransactionException(FermatException.wrapException(exception), "Persisting Genesis Transaction in database", "Unexpected exception");
        }
    }

    public void persistDigitalAssetHash(String transactionID, String digitalAssetHash) throws CantPersistsGenesisTransactionException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, transactionID, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            DatabaseTableRecord databaseTableRecord;
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + transactionID + " Digital Asset Hash:" + digitalAssetHash);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME, digitalAssetHash);
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.HASH_SETTLED.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantPersistsGenesisTransactionException(exception, "Persisting Digital Asset Hash in database", "Cannot open or find the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantPersistsGenesisTransactionException(exception, "Persisting Digital Asset Hash in database", "Cannot load the database into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantPersistsGenesisTransactionException(FermatException.wrapException(exception), "Persisting Digital Asset Hash in database", "Unexpected exception");
        }
    }

    public boolean isPublicKeyUsed(String publicKey) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            int recordsInTable = databaseTableRecords.size();
            this.database.closeDatabase();
            switch (recordsInTable) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "PublicKey:" + publicKey);
            }
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Opening the Asset Issuing plugin database", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Loading Asset Issuing plugin database to memory", "Cannot load the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Checking pending assets to issue", "Unexpected exception");
        }
    }

    public boolean isTransactionIdUsed(UUID transactionId) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setUUIDFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_FIRST_KEY_COLUMN, transactionId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            int recordsInTable = databaseTableRecords.size();
            this.database.closeDatabase();
            switch (recordsInTable) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction ID:" + transactionId);
            }
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Opening the Asset Issuing plugin database", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Loading Asset Issuing plugin database to memory", "Cannot load the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Checking pending assets to issue", "Unexpected exception");
        }
    }

    public HashMap<String, String> getPendingTransactionsHeaders() throws CantCheckAssetIssuingProgressException {
        try {
            DatabaseTable databaseTable;
            HashMap<String, String> transactionsIds = new HashMap<String, String>();
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                transactionsIds.put(record.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_ADDRESS_COLUMN_NAME), record.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME));
            }
            return transactionsIds;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to check pending transaction headers", "Cannot load the table into memory.");
        } catch (Exception exception) {
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Trying to check pending transaction headers", "Unexpected exception.");
        }
    }

    public boolean isPendingTransactions(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode(), DatabaseFilterType.EQUAL);
            //databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.ISSUING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            this.database.closeDatabase();
            Logger LOG = Logger.getGlobal();
            LOG.info("ISSUING DAO - Records pending " + databaseTable.getRecords().size());
            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Getting pending transactions.", "Cannot load table to memory.");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Getting pending transactions.", "Unexpected exception");
        }
    }

    public boolean isPendingEvents() throws CantExecuteQueryException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_STATUS_COLUMN, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            this.database.closeDatabase();
            Logger LOG = Logger.getGlobal();
            LOG.info("ISSUING DAO - Events pending " + databaseTable.getRecords().size());
            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

    public List<String> getPendingEvents() throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {

        try {
            this.database = openDatabase();
            List<String> eventIdList = new ArrayList<>();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_STATUS_COLUMN, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.setFilterOrder(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TIMESTAMP_COLUMN, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            for (DatabaseTableRecord databaseTableRecord : databaseTableRecords) {
                String eventId = databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_ID_COLUMN);
                eventIdList.add(eventId);
            }
            this.database.closeDatabase();
            return eventIdList;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get pending events", "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get pending events", "Cannot load the database into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Trying to get pending events.", "Unexpected exception");
        }
    }

    public String getEventTypeById(String eventId) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {

        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_ID_COLUMN, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Event Id" + eventId);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            this.database.closeDatabase();
            return databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_EVENT_COLUMN);
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get pending events", "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get pending events", "Cannot load the database into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Trying to get pending events.", "Unexpected exception");
        }
    }

    public boolean isReceivedDigitalAssets() throws CantExecuteQueryException {
        return isFieldValueInAssetIssuingTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.RECEIVED.getCode());
    }

    public boolean isDeliveredDigitalAssets() throws CantExecuteQueryException {
        return isFieldValueInAssetIssuingTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.DELIVERED.getCode());
    }

    private boolean isFieldValueInAssetIssuingTable(String fieldCode, String value) throws CantExecuteQueryException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(fieldCode, value, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            this.database.closeDatabase();
            return !databaseTable.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException("Error executing query in DB.", exception, "Getting received digital assets.", "Cannot load table to memory.");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantExecuteQueryException(CantExecuteQueryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Getting received digital assets.", "Unexpected exception");
        }
    }

    public List<String> getGenesisTransactionsFromDigitalAssetsReceived() throws CantCheckAssetIssuingProgressException {
        try {
            this.database = openDatabase();
            List<String> genesisTransactionsFromDigitalAssetReceived = new ArrayList<>();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, TransactionStatus.RECEIVED.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            for (DatabaseTableRecord databaseTableRecord : databaseTableRecords) {
                String genesisTransaction = databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME);
                genesisTransactionsFromDigitalAssetReceived.add(genesisTransaction);
                databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME, TransactionStatus.DELIVERED.getCode());
                databaseTable.updateRecord(databaseTableRecord);
            }
            this.database.closeDatabase();
            return genesisTransactionsFromDigitalAssetReceived;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get received digital assets", "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get received digital assets", "Cannot load the database into memory");
        } catch (CantUpdateRecordException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get received digital assets", "Cannot update the transaction status to DELIVERED");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Trying to get received digital assets.", "Unexpected exception");
        }
    }

    public List<String> getGenesisTransactionByDeliveredStatus() throws CantCheckAssetIssuingProgressException {
        return getValueListFromAssetIssuingTableByFieldCode(
                AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME,
                TransactionStatus.DELIVERED.getCode(),
                AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME);
    }

    public List<String> getTransactionHashByDeliveredStatus() throws CantCheckAssetIssuingProgressException {
        return getValueListFromAssetIssuingTableByFieldCode(
                AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME,
                TransactionStatus.DELIVERED.getCode(),
                AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME);
    }

    public List<String> getOutgoingTransactionIdByIssuingStatus() throws CantCheckAssetIssuingProgressException {
        return getValueListFromAssetIssuingTableByFieldCode(
                AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME,
                TransactionStatus.ISSUING.getCode(),
                AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_OUTGOING_ID_COLUMN_NAME);
    }

    /**
     * This method returns a <code>List<String></code> from the Asset issuing table.
     *
     * @param fieldCode      Column name used as index
     * @param valueAsked     the "needle" seeked
     * @param columnToReturn column that cointains the required value
     * @return
     * @throws CantCheckAssetIssuingProgressException
     */
    private List<String> getValueListFromAssetIssuingTableByFieldCode(String fieldCode, String valueAsked, String columnToReturn) throws CantCheckAssetIssuingProgressException {
        try {
            this.database = openDatabase();
            List<String> resultList = new ArrayList<>();
            DatabaseTable databaseTable = getDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(fieldCode, valueAsked, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            for (DatabaseTableRecord databaseTableRecord : databaseTableRecords) {
                String valueReturned = databaseTableRecord.getStringValue(columnToReturn);
                resultList.add(valueReturned);
            }
            this.database.closeDatabase();
            return resultList;
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get " + fieldCode, "Cannot find or open the database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Trying to get " + fieldCode, "Cannot load the database into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Trying to get " + fieldCode, "Unexpected exception");
        }
    }

    public List<String> getGenesisTransactionsByCryptoStatus(CryptoStatus cryptoStatus) throws CantCheckAssetIssuingProgressException {

        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            List<String> transactionsHashList = new ArrayList<>();
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                transactionsHashList.add(record.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME));
            }
            this.database.closeDatabase();
            return transactionsHashList;
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting genesis transaction.", "Cannot load table to memory");
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting genesis transaction.", "Cannot open or find the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Getting transactions hash.", "Unexpected exception");
        }

    }

    public List<String> getTransactionsHashByCryptoStatus(CryptoStatus cryptoStatus) throws CantCheckAssetIssuingProgressException {

        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            List<String> transactionsHashList = new ArrayList<>();
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME, ProtocolStatus.TO_BE_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                transactionsHashList.add(record.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME));
            }
            this.database.closeDatabase();
            return transactionsHashList;
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting transactions hash.", "Cannot load table to memory");
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Getting transactions hash.", "Cannot open or find the Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Getting transactions hash.", "Unexpected exception");
        }

    }

    public void updateDigitalAssetCryptoStatusByTransactionHash(String transactionHash, CryptoStatus cryptoStatus) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME, transactionHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction hash:" + transactionHash);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Crypto Status.", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Crypto Status ", "Cannot load the table into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Updating Crypto Status.", "Unexpected exception - Transaction hash:" + transactionHash);
        }
    }

    public void updateDigitalAssetCryptoStatusByGenesisTransaction(String genesisTransaction, CryptoStatus cryptoStatus) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Genesis Transaction:" + genesisTransaction);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Crypto Status.", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Crypto Status ", "Cannot load the table into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Updating Crypto Status.", "Unexpected exception - Transaction hash:" + genesisTransaction);
        }
    }

    public void updateDigitalAssetTransactionStatusByTransactionHash(String transactionHash, TransactionStatus transactionStatus) throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME, transactionHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Transaction hash:" + transactionHash);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME, transactionStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Transaction Status.", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Transaction Status ", "Cannot load the table into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Updating Transaction Status.", "Unexpected exception - Transaction hash:" + transactionHash);
        }
    }

    public void updateAssetsGeneratedCounter(String assetPublicKey) throws CantCheckAssetIssuingProgressException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Digital Asset public key:" + assetPublicKey);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            int assetsGenerated = databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME);
            int assetsToGenerate = databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE_COLUMN_NAME);
            assetsGenerated = assetsGenerated + 1;
            if (assetsToGenerate == assetsGenerated) {
                databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ISSUING_STATUS_COLUMN_NAME, IssuingStatus.ISSUED.getCode());
            }
            databaseTableRecord.setIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME, assetsGenerated);
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Digital Asset Counter.", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Digital Asset Counter", "Cannot load the table into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Updating Digital Asset Counter.", "Unexpected exception - Digital Asset public key:" + assetPublicKey);
        }
    }

    public void updateDigitalAssetIssuingStatus(String assetPublicKey, IssuingStatus issuingStatus) throws CantCheckAssetIssuingProgressException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);
            databaseTable.setStringFilter(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, assetPublicKey, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
            DatabaseTableRecord databaseTableRecord;
            if (databaseTableRecords.size() > 1) {
                this.database.closeDatabase();
                throw new UnexpectedResultReturnedFromDatabaseException("Unexpected result. More than value returned.", "Digital Asset public key:" + assetPublicKey);
            } else {
                databaseTableRecord = databaseTableRecords.get(0);
            }
            databaseTableRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ISSUING_STATUS_COLUMN_NAME, issuingStatus.getCode());
            databaseTable.updateRecord(databaseTableRecord);
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Issuing Status.", "Cannot open or find the Asset Issuing database");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(exception, "Updating Issuing Status ", "Cannot load the table into memory");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCheckAssetIssuingProgressException(FermatException.wrapException(exception), "Updating Issuing Status.", "Unexpected exception - Digital Asset public key:" + assetPublicKey);
        }
    }

    public int updateTransactionProtocolStatus(boolean occurrence) throws CantExecuteQueryException {
        //TODO: implement this method
        return 0;
    }

    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            this.database = openDatabase();
            DatabaseTable databaseTable = this.database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            Logger LOG = Logger.getGlobal();
            LOG.info("ASSET DAO:\nUUID:" + eventRecordID + "\n" + unixTime);
            eventRecord.setUUIDValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_ID_COLUMN, eventRecordID);
            eventRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_EVENT_COLUMN, eventType);
            eventRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_SOURCE_COLUMN, eventSource);
            eventRecord.setStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_STATUS_COLUMN, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TIMESTAMP_COLUMN, unixTime);
            databaseTable.insertRecord(eventRecord);
            LOG.info("record:" + eventRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_ID_COLUMN));
            this.database.closeDatabase();
        } catch (CantExecuteDatabaseOperationException exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot open or find the Asset Issuing database");
        } catch (CantInsertRecordException exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Issuing database");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

}
