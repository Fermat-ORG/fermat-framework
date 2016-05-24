package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AppropriationStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventStatus;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantLoadAssetAppropriationTransactionListException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteAppropriationTransactionException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyStartedException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AppropriationTransactionRecord;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.exceptions.CantLoadIssuerAppropriationEventListException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.functional.IssuerAppropriationVault;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class IssuerAppropriationDAO {

    //VARIABLE DECLARATION
    private Database database;
    private IssuerAppropriationVault vault;

    //CONSTRUCTORS
    public IssuerAppropriationDAO(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, IssuerAppropriationVault vault) throws CantOpenDatabaseException, DatabaseNotFoundException {
        database = pluginDatabaseSystem.openDatabase(pluginId, IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_DATABASE);
        this.vault = vault;
    }

    //PUBLIC METHODS

    /*
    * Event Recorded Table's Actions.
    *
    */
    public void saveNewEvent(FermatEvent event) throws CantSaveEventException {
        String eventType = event.getEventType().getCode();
        String eventSource = event.getSource().getCode();

        String context = "Event Type : " + eventType + " - Event Source: " + eventSource;
        try {
            DatabaseTable databaseTable = this.database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());

            databaseTable.insertRecord(eventRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Appropriation Event Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public void updateEventStatus(EventStatus status, String eventId) throws CantLoadIssuerAppropriationEventListException, RecordsNotFoundException {
        updateStringFieldByEventId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, status.getCode(), eventId);
    }

    /*
    * Transaction Metadata Table's Actions.
    *
    */

    public String startAppropriation(DigitalAssetMetadata assetMetadata, String userWalletPublicKey, String bitcoinWalletPublicKey, BlockchainNetworkType networkType) throws CantExecuteAppropriationTransactionException, TransactionAlreadyStartedException {
        String context = "Asset : " + assetMetadata.getDigitalAsset().getPublicKey() + " - Btc Wallet: " + bitcoinWalletPublicKey
                + " - User Wallet: " + userWalletPublicKey + " - Network: " + networkType;
        try {
            if (transactionExists(assetMetadata.getGenesisTransaction(), userWalletPublicKey, bitcoinWalletPublicKey)) {
                throw new TransactionAlreadyStartedException(null, context, "You already started the transaction for this asset.");
            }


            DatabaseTable databaseTable = this.database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            DatabaseTableRecord transactionRecord = databaseTable.getEmptyRecord();

            String transactionId = UUID.randomUUID().toString(); //The id of the record to be created.
            vault.persistDigitalAssetMetadataInLocalStorage(assetMetadata, transactionId);

            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, transactionId);
            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_NETWORK_TYPE, networkType.getCode());
            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, AppropriationStatus.APPROPRIATION_STARTED.getCode());
            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_DA_PUBLIC_KEY_COLUMN_NAME, assetMetadata.getDigitalAsset().getPublicKey());
            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME, userWalletPublicKey);
            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME, bitcoinWalletPublicKey);
            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME, "-");
            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME, "-");
            transactionRecord.setLongValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, System.currentTimeMillis());
            transactionRecord.setLongValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME, Validate.MAX_DATE); //Since I can't store null on a primitive I'll set it as the max possible then update it.
            transactionRecord.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, assetMetadata.getGenesisTransaction());

            databaseTable.insertRecord(transactionRecord);
            return transactionId;
        } catch (CantInsertRecordException exception) {
            throw new CantExecuteAppropriationTransactionException(exception, context, "Cannot insert a record in Asset Appropriation Transaction Metadata table.");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantExecuteAppropriationTransactionException(exception, context, "Cannot save digital asset on file system..");
        } catch (CantLoadAssetAppropriationTransactionListException exception) {
            throw new CantExecuteAppropriationTransactionException(exception, context, "Cannot load transaction metadata list.");
        }
    }

    public void updateGenesisTransaction(String genesisTransaction, String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, genesisTransaction, transactionId);
    }

    public void updateCryptoAddress(CryptoAddress cryptoAddress, String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME, cryptoAddress.getAddress(), transactionId);
        updateStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode(), transactionId);
    }

    public void updateTransactionStatusAssetDebited(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStatus(AppropriationStatus.ASSET_DEBITED, transactionId);
    }

    public void updateTransactionStatusAppropriationStarted(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStatus(AppropriationStatus.APPROPRIATION_STARTED, transactionId);
    }

    public void updateTransactionStatusCryptoAddressObtained(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStatus(AppropriationStatus.CRYPTOADDRESS_OBTAINED, transactionId);
    }

    public void updateTransactionStatusCryptoAddressRegistered(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStatus(AppropriationStatus.CRYPTOADDRESS_REGISTERED, transactionId);
    }

    public void updateTransactionStatusBitcoinsSent(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStatus(AppropriationStatus.BITCOINS_SENT, transactionId);
    }

    public void completeAppropriationReversedOnBlockChain(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        completeTransaction(AppropriationStatus.REVERTED_ON_BLOCKCHAIN, transactionId);
    }

    public void completeAppropriationReversedOnCryptoNetwork(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        completeTransaction(AppropriationStatus.REVERTED_ON_CRYPTO_NETWORK, transactionId);
    }


    public void updateStatusSendingMessage(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStatus(AppropriationStatus.SENDING_MESSAGE, transactionId);
    }

    public void completeAppropriationSuccessful(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        completeTransaction(AppropriationStatus.APPROPRIATION_SUCCESSFUL, transactionId);
    }

    //PRIVATE METHODS
    private boolean isPendingEventsBySource(EventSource eventSource) throws CantLoadIssuerAppropriationEventListException {
        return !getPendingEventsBySource(eventSource).isEmpty();
    }

    private void updateStringFieldByEventId(String columnName, String value, String id) throws CantLoadIssuerAppropriationEventListException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Id: " + id;
        try {
            DatabaseTable eventRecordedTable;
            eventRecordedTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            eventRecordedTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            eventRecordedTable.loadToMemory();
            if (eventRecordedTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }
            for (DatabaseTableRecord record : eventRecordedTable.getRecords()) {
                record.setStringValue(columnName, value);
                eventRecordedTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadIssuerAppropriationEventListException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantLoadIssuerAppropriationEventListException(exception, context, "Cannot update record.");
        }
    }


    private String getStringFieldByEventId(String columnName, String id) throws CantLoadIssuerAppropriationEventListException, RecordsNotFoundException {
        try {
            String context = "Column Name: " + columnName + " - Id: " + id;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                return record.getStringValue(columnName);
            }
            throw new RecordsNotFoundException(null, context, "");
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadIssuerAppropriationEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private List<String> getPendingEventsBySource(EventSource eventSource) throws CantLoadIssuerAppropriationEventListException {
        try {
            DatabaseTable eventsRecordedTable;
            eventsRecordedTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            eventsRecordedTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);
            eventsRecordedTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            eventsRecordedTable.addFilterOrder(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            eventsRecordedTable.loadToMemory();
            List<String> eventIdList = new ArrayList<>();
            for (DatabaseTableRecord record : eventsRecordedTable.getRecords()) {
                eventIdList.add(record.getStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME));
            }
            return eventIdList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadIssuerAppropriationEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadIssuerAppropriationEventListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
        }
    }

    private boolean transactionExists(String txHash, String userWalletPublicKey, String bitcoinWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException {
        try {
            getTransactionId(txHash, userWalletPublicKey, bitcoinWalletPublicKey);
            return true;
        } catch (RecordsNotFoundException e) {
            return false;
        }

    }

    private String getTransactionId(String txHash, String userWalletPublicKey, String bitcoinWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        String context = "Asset Public Key: " + txHash + " - User Wallet: " + userWalletPublicKey
                + " - BTC Wallet: " + bitcoinWalletPublicKey;
        try {
            DatabaseTable transactionMetadataTable;
            transactionMetadataTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);

            DatabaseTableFilter addressFilter = transactionMetadataTable.getEmptyTableFilter();
            addressFilter.setColumn(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME);
            addressFilter.setValue(bitcoinWalletPublicKey);
            addressFilter.setType(DatabaseFilterType.EQUAL);

            DatabaseTableFilter assetPublicKeyFilter = transactionMetadataTable.getEmptyTableFilter();
            assetPublicKeyFilter.setColumn(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME);
            assetPublicKeyFilter.setValue(txHash);
            assetPublicKeyFilter.setType(DatabaseFilterType.EQUAL);

            DatabaseTableFilter userWallerFilter = transactionMetadataTable.getEmptyTableFilter();
            userWallerFilter.setColumn(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME);
            userWallerFilter.setValue(userWalletPublicKey);
            userWallerFilter.setType(DatabaseFilterType.EQUAL);

            List<DatabaseTableFilter> filters = new ArrayList<>();
            filters.add(addressFilter);
            filters.add(assetPublicKeyFilter);
            filters.add(userWallerFilter);

            transactionMetadataTable.setFilterGroup(
                    transactionMetadataTable.getNewFilterGroup(filters,
                            new ArrayList<DatabaseTableFilterGroup>(),
                            DatabaseFilterOperator.AND));

            transactionMetadataTable.addFilterOrder(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            transactionMetadataTable.loadToMemory();

            if (transactionMetadataTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            return transactionMetadataTable.getRecords().get(0).getStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, context, "Cannot load table to memory.");
        }
    }

    private String getTransactionIdByGenesisTransaction(String genesisTransaction) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        try {
            String context = "Genesis Transaction: " + genesisTransaction;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            return databaseTable.getRecords().get(0).getStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private List<String> getTransactionIdsForUserWallet(String userWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        try {
            String context = "User Wallet: " + userWalletPublicKey;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME, userWalletPublicKey, DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            List<String> idList = new ArrayList<>(databaseTable.getRecords().size());

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                idList.add(record.getStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME));
            }
            return idList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private List<String> getTransactionIdsForBitcoinWallet(String bitcoinWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        try {
            String context = "Bitcoin Wallet: " + bitcoinWalletPublicKey;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME, bitcoinWalletPublicKey, DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            List<String> idList = new ArrayList<>(databaseTable.getRecords().size());

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                idList.add(record.getStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME));
            }
            return idList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private List<String> getTransactionIdsForStatus(AppropriationStatus status) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        try {
            String context = "Status: " + status;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            List<String> idList = new ArrayList<>(databaseTable.getRecords().size());

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                idList.add(record.getStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME));
            }
            return idList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private String getStringFieldByTransactionId(String columnName, String id) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        try {
            String context = "Column Name: " + columnName + " - Id: " + id;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            return databaseTable.getRecords().get(0).getStringValue(columnName);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }


    private long getLongFieldByTransactionId(String columnName, String id) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        try {
            String context = "Column Name: " + columnName + " - Id: " + id;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            return databaseTable.getRecords().get(0).getLongValue(columnName);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private void updateStringFieldByTransactionId(String columnName, String value, String id) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Id: " + id;
        try {
            DatabaseTable transactionTable;
            transactionTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            transactionTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            transactionTable.loadToMemory();

            if (transactionTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            for (DatabaseTableRecord record : transactionTable.getRecords()) {
                record.setStringValue(columnName, value);
                transactionTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, context, "Cannot update record.");
        }
    }

    private void completeTransaction(AppropriationStatus status, String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        String context = "Status: " + status.getCode()
                + " ID: " + transactionId;
        try {
            DatabaseTable transactionTable;
            transactionTable = database.getTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            transactionTable.addStringFilter(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            transactionTable.loadToMemory();

            if (transactionTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            for (DatabaseTableRecord record : transactionTable.getRecords()) {
                record.setStringValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, status.getCode());
                record.setLongValue(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME, System.currentTimeMillis());
                transactionTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, context, "Cannot update record.");
        }
    }

    private void updateStatus(AppropriationStatus status, String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, status.getCode(), transactionId);
    }

    private org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.functional.IssuerAppropriationTransactionRecordImpl constructRecordFromId(String transactionId) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        String context = "TransactionId : " + transactionId;
        try {
            BlockchainNetworkType networkType = BlockchainNetworkType.getByCode(getStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_NETWORK_TYPE, transactionId));
            String bitcoinWalletPublicKey = getStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME, transactionId);
            String userWalletPublicKey = getStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME, transactionId);
            String address = getStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME, transactionId);
            String code = getStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME, transactionId);
            CryptoAddress cryptoAddress = null;
            if (!(code.equals("-")) && !(address.equals("-"))) {
                CryptoCurrency currency = CryptoCurrency.getByCode(code);
                cryptoAddress = new CryptoAddress(address, currency);
            }
            AppropriationStatus status = AppropriationStatus.getByCode(getStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, transactionId));
            DigitalAssetMetadata assetMetadata = vault.getDigitalAssetMetadataFromLocalStorage(transactionId);
            long startTime = getLongFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, transactionId);
            long endTime = getLongFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME, transactionId);
            String genesisTransaction = getStringFieldByTransactionId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, transactionId);

            return new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.functional.IssuerAppropriationTransactionRecordImpl(transactionId, networkType, status, assetMetadata, bitcoinWalletPublicKey, userWalletPublicKey, cryptoAddress, startTime, endTime, genesisTransaction);
        } catch (InvalidParameterException | CantGetDigitalAssetFromLocalStorageException e) {
            throw new CantLoadAssetAppropriationTransactionListException(e, context, "There was an exception while constructing the record.");
        }
    }


    //GETTER AND SETTERS

    /*
     * Events table.
     */
    public List<String> getPendingActorAssetUserEvents() throws CantLoadIssuerAppropriationEventListException {
        return getPendingEventsBySource(EventSource.ACTOR_ASSET_USER);
    }

    public List<String> getPendingCryptoRouterEvents() throws CantLoadIssuerAppropriationEventListException {
        return getPendingEventsBySource(EventSource.CRYPTO_ROUTER);
    }

    public EventType getEventTypeById(String id) throws CantLoadIssuerAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventType.getByCode(getStringFieldByEventId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }

    public org.fermat.fermat_dap_api.layer.all_definition.enums.EventType getDAPEventTypeById(String id) throws CantLoadIssuerAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return org.fermat.fermat_dap_api.layer.all_definition.enums.EventType.getByCode(getStringFieldByEventId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }

    public EventSource getEventSourceById(String id) throws CantLoadIssuerAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventSource.getByCode(getStringFieldByEventId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, id));
    }

    public EventStatus getEventStatusById(String id) throws CantLoadIssuerAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventStatus.getByCode(getStringFieldByEventId(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, id));
    }

    public boolean isPendingActorAssetUserEvents() throws CantLoadIssuerAppropriationEventListException {
        return isPendingEventsBySource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
    }

    public List<String> getPendingIssuerNetworkServiceEvents() throws CantLoadIssuerAppropriationEventListException {
        return getPendingEventsBySource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER);
    }


    /*
     * Transaction metadata table.
     */
    public AppropriationTransactionRecord getTransaction(DigitalAsset digitalAsset, String assetUserWalletPublicKey, String bitcoinWalletPublicKey) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        return constructRecordFromId(getTransactionId(digitalAsset.getPublicKey(), assetUserWalletPublicKey, bitcoinWalletPublicKey));
    }

    public AppropriationTransactionRecord getTransaction(String genesisTransaction) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        return constructRecordFromId(getTransactionIdByGenesisTransaction(genesisTransaction));
    }

    public List<AppropriationTransactionRecord> getTransactionsForUserWallet(String assetUserWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException {
        try {
            List<String> transactionIds = getTransactionIdsForUserWallet(assetUserWalletPublicKey);
            List<AppropriationTransactionRecord> appropriationTransactionRecords = new ArrayList<>(transactionIds.size());
            for (String id : transactionIds) {
                appropriationTransactionRecords.add(constructRecordFromId(id));
            }
            return appropriationTransactionRecords;
        } catch (RecordsNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<AppropriationTransactionRecord> getTransactionsForBitcoinWallet(String bitcoinWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException {
        try {
            List<String> transactionIds = getTransactionIdsForBitcoinWallet(bitcoinWalletPublicKey);
            List<AppropriationTransactionRecord> appropriationTransactionRecords = new ArrayList<>(transactionIds.size());
            for (String id : transactionIds) {
                appropriationTransactionRecords.add(constructRecordFromId(id));
            }
            return appropriationTransactionRecords;
        } catch (RecordsNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<AppropriationTransactionRecord> getUnsendedTransactions() throws CantLoadAssetAppropriationTransactionListException {
        List<AppropriationTransactionRecord> uncompleted = new ArrayList<>();
        uncompleted.addAll(getTransactionsForStatus(AppropriationStatus.APPROPRIATION_STARTED));
        uncompleted.addAll(getTransactionsForStatus(AppropriationStatus.CRYPTOADDRESS_OBTAINED));
        uncompleted.addAll(getTransactionsForStatus(AppropriationStatus.CRYPTOADDRESS_REGISTERED));
        uncompleted.addAll(getTransactionsForStatus(AppropriationStatus.SENDING_MESSAGE));
        uncompleted.addAll(getTransactionsForStatus(AppropriationStatus.BITCOINS_SENT));
        uncompleted.addAll(getTransactionsForStatus(AppropriationStatus.ASSET_DEBITED));
        return uncompleted;
    }

    public List<AppropriationTransactionRecord> getTransactionsForStatus(AppropriationStatus status) throws CantLoadAssetAppropriationTransactionListException {
        try {
            List<String> transactionIds = getTransactionIdsForStatus(status);
            List<AppropriationTransactionRecord> appropriationTransactionRecords = new ArrayList<>(transactionIds.size());
            for (String id : transactionIds) {
                appropriationTransactionRecords.add(constructRecordFromId(id));
            }
            return appropriationTransactionRecords;
        } catch (RecordsNotFoundException e) {
            return new ArrayList<>();
        }
    }
    //INNER CLASSES
}
