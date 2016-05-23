package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.database;

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
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.exceptions.CantLoadAssetAppropriationEventListException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.functional.AssetAppropriationTransactionRecordImpl;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationDAO implements AutoCloseable {

    //VARIABLE DECLARATION
    private Database database;
    private org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.functional.AssetAppropriationVault vault;

    //CONSTRUCTORS
    public AssetAppropriationDAO(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId, org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.functional.AssetAppropriationVault vault) throws CantOpenDatabaseException, DatabaseNotFoundException {
        database = pluginDatabaseSystem.openDatabase(pluginId, AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_DATABASE);
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
            DatabaseTable databaseTable = this.database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());

            databaseTable.insertRecord(eventRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Appropriation Event Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public void updateEventStatus(EventStatus status, String eventId) throws CantLoadAssetAppropriationEventListException, RecordsNotFoundException {
        updateStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, status.getCode(), eventId);
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


            DatabaseTable databaseTable = this.database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            DatabaseTableRecord transactionRecord = databaseTable.getEmptyRecord();

            String transactionId = UUID.randomUUID().toString(); //The id of the record to be created.
            vault.persistDigitalAssetMetadataInLocalStorage(assetMetadata, transactionId);

            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, transactionId);
            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_NETWORK_TYPE, networkType.getCode());
            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, AppropriationStatus.APPROPRIATION_STARTED.getCode());
            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_DA_PUBLIC_KEY_COLUMN_NAME, assetMetadata.getDigitalAsset().getPublicKey());
            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME, userWalletPublicKey);
            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME, bitcoinWalletPublicKey);
            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME, "-");
            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME, "-");
            transactionRecord.setLongValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, System.currentTimeMillis());
            transactionRecord.setLongValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME, Validate.MAX_DATE); //Since I can't store null on a primitive I'll set it as the max possible then update it.
            transactionRecord.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, assetMetadata.getGenesisTransaction());

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
        updateStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, genesisTransaction, transactionId);
    }

    public void updateCryptoAddress(CryptoAddress cryptoAddress, String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME, cryptoAddress.getAddress(), transactionId);
        updateStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode(), transactionId);
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

    public void updateStatusCreatingMovement(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStatus(AppropriationStatus.CREATING_MOVEMENT, transactionId);
    }

    public void completeAppropriationSuccessful(String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        completeTransaction(AppropriationStatus.APPROPRIATION_SUCCESSFUL, transactionId);
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     * <p/>
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     * <p/>
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     * <p/>
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     * <p/>
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     * <p/>
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     * <p/>
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        database.closeDatabase();
    }

    //PRIVATE METHODS
    private boolean isPendingEventsBySource(EventSource eventSource) throws CantLoadAssetAppropriationEventListException {
        return !getPendingEventsBySource(eventSource).isEmpty();
    }

    private void updateStringFieldByEventId(String columnName, String value, String id) throws CantLoadAssetAppropriationEventListException, RecordsNotFoundException {
        String context = "Column Name: " + columnName + " - Id: " + id;
        try {
            DatabaseTable eventRecordedTable;
            eventRecordedTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            eventRecordedTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            eventRecordedTable.loadToMemory();
            if (eventRecordedTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }
            for (DatabaseTableRecord record : eventRecordedTable.getRecords()) {
                record.setStringValue(columnName, value);
                eventRecordedTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationEventListException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantLoadAssetAppropriationEventListException(exception, context, "Cannot update record.");
        }
    }


    private String getStringFieldByEventId(String columnName, String id) throws CantLoadAssetAppropriationEventListException, RecordsNotFoundException {
        try {
            String context = "Column Name: " + columnName + " - Id: " + id;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            databaseTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                return record.getStringValue(columnName);
            }
            throw new RecordsNotFoundException(null, context, "");
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private List<String> getPendingEventsBySource(EventSource eventSource) throws CantLoadAssetAppropriationEventListException {
        try {
            DatabaseTable eventsRecordedTable;
            eventsRecordedTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);
            eventsRecordedTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);
            eventsRecordedTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            eventsRecordedTable.addFilterOrder(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            eventsRecordedTable.loadToMemory();
            List<String> eventIdList = new ArrayList<>();
            for (DatabaseTableRecord record : eventsRecordedTable.getRecords()) {
                eventIdList.add(record.getStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME));
            }
            return eventIdList;
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationEventListException(exception, "Getting pending events.", "Cannot load table to memory.");
        } catch (Exception exception) {
            throw new CantLoadAssetAppropriationEventListException(FermatException.wrapException(exception), "Getting pending events.", "Unexpected exception");
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
            transactionMetadataTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);

            DatabaseTableFilter addressFilter = transactionMetadataTable.getEmptyTableFilter();
            addressFilter.setColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME);
            addressFilter.setValue(bitcoinWalletPublicKey);
            addressFilter.setType(DatabaseFilterType.EQUAL);

            DatabaseTableFilter assetPublicKeyFilter = transactionMetadataTable.getEmptyTableFilter();
            assetPublicKeyFilter.setColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME);
            assetPublicKeyFilter.setValue(txHash);
            assetPublicKeyFilter.setType(DatabaseFilterType.EQUAL);

            DatabaseTableFilter userWallerFilter = transactionMetadataTable.getEmptyTableFilter();
            userWallerFilter.setColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME);
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

            transactionMetadataTable.addFilterOrder(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            transactionMetadataTable.loadToMemory();

            if (transactionMetadataTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            return transactionMetadataTable.getRecords().get(0).getStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, context, "Cannot load table to memory.");
        }
    }

    private String getTransactionIdByGenesisTransaction(String genesisTransaction) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        try {
            String context = "Genesis Transaction: " + genesisTransaction;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, genesisTransaction, DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            return databaseTable.getRecords().get(0).getStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME);
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, "Getting pending events.", "Cannot load table to memory.");
        }
    }

    private List<String> getTransactionIdsForUserWallet(String userWalletPublicKey) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        try {
            String context = "User Wallet: " + userWalletPublicKey;
            DatabaseTable databaseTable;
            databaseTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME, userWalletPublicKey, DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            List<String> idList = new ArrayList<>(databaseTable.getRecords().size());

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                idList.add(record.getStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME));
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
            databaseTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME, bitcoinWalletPublicKey, DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);

            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            List<String> idList = new ArrayList<>(databaseTable.getRecords().size());

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                idList.add(record.getStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME));
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
            databaseTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
            databaseTable.addFilterOrder(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            databaseTable.loadToMemory();

            if (databaseTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            List<String> idList = new ArrayList<>(databaseTable.getRecords().size());

            for (DatabaseTableRecord record : databaseTable.getRecords()) {
                idList.add(record.getStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME));
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
            databaseTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
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
            databaseTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            databaseTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
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
            transactionTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            transactionTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
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
            transactionTable = database.getTable(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);
            transactionTable.addStringFilter(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            transactionTable.loadToMemory();

            if (transactionTable.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "");
            }

            for (DatabaseTableRecord record : transactionTable.getRecords()) {
                record.setStringValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, status.getCode());
                record.setLongValue(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME, System.currentTimeMillis());
                transactionTable.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, context, "Cannot load table to memory.");
        } catch (CantUpdateRecordException exception) {
            throw new CantLoadAssetAppropriationTransactionListException(exception, context, "Cannot update record.");
        }
    }

    private void updateStatus(AppropriationStatus status, String transactionId) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        updateStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, status.getCode(), transactionId);
    }

    private AssetAppropriationTransactionRecordImpl constructRecordFromId(String transactionId) throws CantLoadAssetAppropriationTransactionListException, RecordsNotFoundException {
        String context = "TransactionId : " + transactionId;
        try {
            String bitcoinWalletPublicKey = getStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME, transactionId);
            String userWalletPublicKey = getStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME, transactionId);
            String address = getStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME, transactionId);
            String code = getStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME, transactionId);
            CryptoAddress cryptoAddress = null;
            if (!(code.equals("-")) && !(address.equals("-"))) {
                CryptoCurrency currency = CryptoCurrency.getByCode(code);
                cryptoAddress = new CryptoAddress(address, currency);
            }
            BlockchainNetworkType networkType = BlockchainNetworkType.getByCode(getStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_NETWORK_TYPE, transactionId));
            AppropriationStatus status = AppropriationStatus.getByCode(getStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, transactionId));
            DigitalAssetMetadata assetMetadata = vault.getDigitalAssetMetadataFromLocalStorage(transactionId);
            long startTime = getLongFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, transactionId);
            long endTime = getLongFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME, transactionId);
            String genesisTransaction = getStringFieldByTransactionId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, transactionId);

            return new AssetAppropriationTransactionRecordImpl(transactionId, networkType, status, assetMetadata, bitcoinWalletPublicKey, userWalletPublicKey, cryptoAddress, startTime, endTime, genesisTransaction);
        } catch (InvalidParameterException | CantGetDigitalAssetFromLocalStorageException e) {
            throw new CantLoadAssetAppropriationTransactionListException(e, context, "There was an exception while constructing the record.");
        }
    }


    //GETTER AND SETTERS

    /*
     * Events table.
     */
    public List<String> getPendingActorAssetUserEvents() throws CantLoadAssetAppropriationEventListException {
        return getPendingEventsBySource(EventSource.ACTOR_ASSET_USER);
    }

    public List<String> getPendingCryptoRouterEvents() throws CantLoadAssetAppropriationEventListException {
        return getPendingEventsBySource(EventSource.CRYPTO_ROUTER);
    }

    public EventType getEventTypeById(String id) throws CantLoadAssetAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventType.getByCode(getStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }

    public org.fermat.fermat_dap_api.layer.all_definition.enums.EventType getDAPEventTypeById(String id) throws CantLoadAssetAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return org.fermat.fermat_dap_api.layer.all_definition.enums.EventType.getByCode(getStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, id));
    }

    public EventSource getEventSourceById(String id) throws CantLoadAssetAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventSource.getByCode(getStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, id));
    }

    public EventStatus getEventStatusById(String id) throws CantLoadAssetAppropriationEventListException, InvalidParameterException, RecordsNotFoundException {
        return EventStatus.getByCode(getStringFieldByEventId(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, id));
    }

    public boolean isPendingActorAssetUserEvents() throws CantLoadAssetAppropriationEventListException {
        return isPendingEventsBySource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
    }

    public List<String> getPendingIssuerNetworkServiceEvents() throws CantLoadAssetAppropriationEventListException {
        return getPendingEventsBySource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_ISSUER);
    }


    /*
     * Transaction metadata table.
     */
    public AppropriationTransactionRecord getTransaction(DigitalAssetMetadata digitalAsset, String assetUserWalletPublicKey, String bitcoinWalletPublicKey) throws RecordsNotFoundException, CantLoadAssetAppropriationTransactionListException {
        return constructRecordFromId(getTransactionId(digitalAsset.getGenesisTransaction(), assetUserWalletPublicKey, bitcoinWalletPublicKey));
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
