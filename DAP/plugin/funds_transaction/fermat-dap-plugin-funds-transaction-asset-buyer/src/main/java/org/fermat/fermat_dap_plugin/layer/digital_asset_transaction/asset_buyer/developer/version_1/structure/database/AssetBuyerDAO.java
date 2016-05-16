package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Base64;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.EventStatus;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetSellContentMessage;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetBuyerDAO {

    //VARIABLE DECLARATION

    //VARIABLE DECLARATION
    private final UUID pluginId;
    private final Database database;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final ActorAssetUserManager actorAssetUserManager;
    private final AssetUserWalletManager assetUserWalletManager;
    private final org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.AssetBuyingVault assetBuyingVault;

    //CONSTRUCTORS
    public AssetBuyerDAO(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, ActorAssetUserManager actorAssetUserManager, AssetUserWalletManager assetUserWalletManager, org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.AssetBuyingVault assetBuyingVault) throws CantExecuteDatabaseOperationException {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.actorAssetUserManager = actorAssetUserManager;
        this.assetUserWalletManager = assetUserWalletManager;
        this.assetBuyingVault = assetBuyingVault;
        database = openDatabase();
    }

    //PUBLIC METHODS

    //INSERTS
    public void saveNewEvent(FermatEvent event) throws CantSaveEventException {
        String eventType = event.getEventType().getCode();
        String eventSource = event.getSource().getCode();
        String context = "Event Type : " + eventType + " - Event Source: " + eventSource;
        try {
            DatabaseTable databaseTable = this.database.getTable(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();

            eventRecord.setUUIDValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
            databaseTable.insertRecord(eventRecord);
        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, context, "Cannot insert a record in Asset Appropriation Event Table");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), context, "Unexpected exception");
        }
    }

    public void saveNewBuying(AssetSellContentMessage assetSellContentMessage, String senderPublicKey) throws CantInsertRecordException, CantCreateDigitalAssetFileException {
        assetBuyingVault.persistDigitalAssetMetadataInLocalStorage(assetSellContentMessage.getAssetMetadata(), assetSellContentMessage.getSellingId().toString());
        DatabaseTable databaseTable = getBuyerTable();
        DatabaseTableRecord buyingRecord = databaseTable.getEmptyRecord();
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_ENTRY_ID_COLUMN_NAME, assetSellContentMessage.getSellingId().toString());
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NETWORK_TYPE_COLUMN_NAME, assetSellContentMessage.getAssetMetadata().getNetworkType().getCode());
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_REFERENCE_COLUMN_NAME, assetSellContentMessage.getNegotiationId().toString());
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_CRYPTO_ADDRESS_COLUMN_NAME, assetSellContentMessage.getSellerCryptoAddress().getAddress());
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_CRYPTO_ADDRESS_COLUMN_NAME, assetSellContentMessage.getBuyerCryptoAddress().getAddress());
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_CRYPTO_CURRENCY_COLUMN_NAME, assetSellContentMessage.getSellerCryptoAddress().getCryptoCurrency().getCode());
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME, assetSellContentMessage.getSellStatus().getCode());
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_PUBLICKEY_COLUMN_NAME, senderPublicKey);
        buyingRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_TRANSACTION_COLUMN_NAME, Base64.encodeToString(assetSellContentMessage.getSerializedTransaction(), Base64.DEFAULT));
        buyingRecord.setLongValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_VALUE_COLUMN_NAME, assetSellContentMessage.getTransactionValue());
        databaseTable.insertRecord(buyingRecord);
    }

    public void saveAssetNegotiation(AssetNegotiation negotiation, String sellerPublicKey) throws CantInsertRecordException {
        DatabaseTable databaseTable = getNegotiationTable();
        DatabaseTableRecord negotiationRecord = databaseTable.getEmptyRecord();
        negotiationRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId().toString());
        negotiationRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_OBJECT_XML_COLUMN_NAME, XMLParser.parseObject(negotiation));
        negotiationRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_SELLER_PUBLICKEY_COLUMN_NAME, sellerPublicKey);
        negotiationRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_ASSET_PUBLICKEY_COLUMN_NAME, negotiation.getAssetToOffer().getPublicKey());
        negotiationRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME, AssetSellStatus.WAITING_CONFIRMATION.getCode());
        negotiationRecord.setLongValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FOR_PROCESS_COLUMN_NAME, negotiation.getQuantityToBuy());
        negotiationRecord.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_NETWORK_TYPE_COLUMN_NAME, negotiation.getNetworkType().getCode());
        negotiationRecord.setLongValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_TIMESTAMP_COLUMN_NAME, System.currentTimeMillis());
        databaseTable.insertRecord(negotiationRecord);
    }

    //UPDATES
    public void updateAssetNegotiation(AssetNegotiation negotiation) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordForTableByKey(getNegotiationTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_OBJECT_XML_COLUMN_NAME, XMLParser.parseObject(negotiation), AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FIRST_KEY_COLUMN, negotiation.getNegotiationId().toString());
    }

    public void updateNegotiationStatus(UUID transactionId, AssetSellStatus status) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordForTableByKey(getNegotiationTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME, status.getCode(), AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FIRST_KEY_COLUMN, transactionId.toString());
    }

    public void updateSellingStatus(UUID transactionId, AssetSellStatus status) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordForTableByKey(getBuyerTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME, status.getCode(), AssetBuyerDatabaseConstants.ASSET_BUYER_FIRST_KEY_COLUMN, transactionId.toString());
    }

    public void acceptNegotiation(UUID negotiationId, String btcWalletPk) throws DAPException, CantLoadTableToMemoryException, CantUpdateRecordException {
        processNegotiation(negotiationId);
        updateRecordForTableByKey(getNegotiationTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_BTC_WALLET_PK_COLUMN_NAME, btcWalletPk, AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FIRST_KEY_COLUMN, negotiationId.toString());
    }

    public void rejectNegotiation(UUID negotiationId) throws DAPException, CantLoadTableToMemoryException, CantUpdateRecordException {
        processNegotiation(negotiationId);
    }

    public void processNegotiation(UUID negotiationId) throws DAPException, CantUpdateRecordException, CantLoadTableToMemoryException {
        org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord record = getNegotiationRecord(negotiationId);
        long forProcess = record.getForProcess() - 1;
        updateRecordForTableByKey(getNegotiationTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME, forProcess, AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FIRST_KEY_COLUMN, negotiationId.toString());
        if (forProcess == 0) {
            updateRecordForTableByKey(getNegotiationTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME, AssetSellStatus.NEGOTIATION_FINISHED.getCode(), AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FIRST_KEY_COLUMN, negotiationId.toString());
        }
    }

    public void updateOutgoingId(UUID transactionId, UUID outgoingId) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordForTableByKey(getBuyerTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_OUTGOING_ID_COLUMN_NAME, outgoingId.toString(), AssetBuyerDatabaseConstants.ASSET_BUYER_FIRST_KEY_COLUMN, transactionId.toString());
    }

    public void updateSellerTransaction(UUID transactionId, byte[] serializedTransaction) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordForTableByKey(getBuyerTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_TRANSACTION_COLUMN_NAME, Base64.encodeToString(serializedTransaction, Base64.DEFAULT), AssetBuyerDatabaseConstants.ASSET_BUYER_FIRST_KEY_COLUMN, transactionId.toString());
    }

    public void updateBuyerTransaction(UUID transactionId, DraftTransaction draftTransaction) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordForTableByKey(getBuyerTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_TRANSACTION_COLUMN_NAME, Base64.encodeToString(draftTransaction.serialize(), Base64.DEFAULT), AssetBuyerDatabaseConstants.ASSET_BUYER_FIRST_KEY_COLUMN, transactionId.toString());
        updateRecordForTableByKey(getBuyerTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_VALUE_COLUMN_NAME, draftTransaction.getValue(), AssetBuyerDatabaseConstants.ASSET_BUYER_FIRST_KEY_COLUMN, transactionId.toString());
    }

    public void updateTransactionHash(UUID transactionId, String transactionHash) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateRecordForTableByKey(getBuyerTable(), AssetBuyerDatabaseConstants.ASSET_BUYER_TX_HASH_COLUMN_NAME, transactionHash, AssetBuyerDatabaseConstants.ASSET_BUYER_FIRST_KEY_COLUMN, transactionId.toString());
    }

    public void notifyEvent(String eventId) throws CantUpdateRecordException, CantLoadTableToMemoryException, RecordsNotFoundException {
        DatabaseTable table = getEventsTable();
        table.addStringFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = table.getRecords().get(0);
        record.setStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.NOTIFIED.getCode());
        table.updateRecord(record);
    }

    //DELETE
    public void deleteBuyingRecord(UUID recordId) throws CantDeleteRecordException, CantLoadTableToMemoryException, RecordsNotFoundException {
        DatabaseTable table = getBuyerTable();
        table.addStringFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_ENTRY_ID_COLUMN_NAME, recordId.toString(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = table.getRecords().get(0);
        table.deleteRecord(record);
    }
    //PRIVATE METHODS

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, AssetBuyerDatabaseConstants.ASSET_BUYER_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Opening the Asset Reception Transaction Database", "Error in database plugin.");
        }
    }

    private void updateRecordForTableByKey(DatabaseTable table, String recordName, long recordValue, String filterColumn, String filterValue) throws CantUpdateRecordException, CantLoadTableToMemoryException, RecordsNotFoundException {
        table.addStringFilter(filterColumn, filterValue, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = table.getRecords().get(0);
        record.setLongValue(recordName, recordValue);
        table.updateRecord(record);
    }

    private void updateRecordForTableByKey(DatabaseTable table, String recordName, String recordValue, String filterColumn, String filterValue) throws CantUpdateRecordException, CantLoadTableToMemoryException, RecordsNotFoundException {
        table.addStringFilter(filterColumn, filterValue, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = table.getRecords().get(0);
        record.setStringValue(recordName, recordValue);
        table.updateRecord(record);
    }

    private List<DatabaseTableRecord> getRecordsByFilterBuyerTable(DatabaseTableFilter... filters) throws CantLoadTableToMemoryException {
        DatabaseTable table = getBuyerTable();
        for (DatabaseTableFilter filter : filters) {
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        }
        table.addFilterOrder(AssetBuyerDatabaseConstants.ASSET_BUYER_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
        table.loadToMemory();
        return table.getRecords();
    }

    private List<DatabaseTableRecord> getRecordsByFilterNegotiationTable(DatabaseTableFilter... filters) throws CantLoadTableToMemoryException {
        DatabaseTable table = getNegotiationTable();
        for (DatabaseTableFilter filter : filters) {
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        }
        table.addFilterOrder(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_TIMESTAMP_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
        table.loadToMemory();
        return table.getRecords();
    }

    private org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord constructBuyingByDatabaseRecord(DatabaseTableRecord record) throws InvalidParameterException, CantAssetUserActorNotFoundException, CantGetAssetUserActorsException, CantGetDigitalAssetFromLocalStorageException, CantLoadWalletException {
        AssetSellStatus status = AssetSellStatus.getByCode(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME));
        UUID entryId = UUID.fromString(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_ENTRY_ID_COLUMN_NAME));
        DigitalAssetMetadata metadata = assetBuyingVault.getDigitalAssetMetadataFromLocalStorage(entryId.toString());
        ActorAssetUser user = actorAssetUserManager.getActorByPublicKey(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_PUBLICKEY_COLUMN_NAME), metadata.getNetworkType());
        String encodeUnsignedTransaction = record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_TRANSACTION_COLUMN_NAME);
        String encodeSignedTransaction = record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_TRANSACTION_COLUMN_NAME);
        DraftTransaction buyerTransaction = !Validate.isValidString(encodeSignedTransaction) ? null : DraftTransaction.deserialize(metadata.getNetworkType(), Base64.decode(encodeSignedTransaction, Base64.DEFAULT));
        if (!Validate.isObjectNull(buyerTransaction))
            buyerTransaction.addValue(record.getLongValue(AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_VALUE_COLUMN_NAME));
        DraftTransaction sellerTransaction = !Validate.isValidString(encodeUnsignedTransaction) ? null : DraftTransaction.deserialize(metadata.getNetworkType(), Base64.decode(encodeUnsignedTransaction, Base64.DEFAULT));
        if (!Validate.isObjectNull(sellerTransaction))
            sellerTransaction.addValue(record.getLongValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_VALUE_COLUMN_NAME));
//        String transactionHash = record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_TX_HASH_COLUMN_NAME);
        UUID negotiationId = UUID.fromString(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_REFERENCE_COLUMN_NAME));
        CryptoCurrency currency = CryptoCurrency.getByCode(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_CRYPTO_CURRENCY_COLUMN_NAME));
        CryptoAddress sellerCryptoAddress = new CryptoAddress(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_CRYPTO_ADDRESS_COLUMN_NAME), currency);
        CryptoAddress buyerCryptoAddress = new CryptoAddress(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_CRYPTO_ADDRESS_COLUMN_NAME), currency);
        String outgoingId = record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_OUTGOING_ID_COLUMN_NAME);
        return new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord(entryId, metadata, user, status, buyerTransaction, sellerTransaction, negotiationId, sellerCryptoAddress, buyerCryptoAddress, outgoingId);
    }

    private org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord constructNegotiationByDatabaseRecord(DatabaseTableRecord record) throws InvalidParameterException, CantAssetUserActorNotFoundException, CantGetAssetUserActorsException {
        AssetNegotiation negotiation = (AssetNegotiation) XMLParser.parseXML(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_OBJECT_XML_COLUMN_NAME), new AssetNegotiation());
        AssetSellStatus status = AssetSellStatus.getByCode(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME));
        Date timeStamp = new Date(record.getLongValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_TIMESTAMP_COLUMN_NAME));
        ActorAssetUser actorAssetUser = actorAssetUserManager.getActorByPublicKey(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_SELLER_PUBLICKEY_COLUMN_NAME));
        String btcWalletPublicKey = record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_BTC_WALLET_PK_COLUMN_NAME);
        long forProcess = record.getLongValue(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FOR_PROCESS_COLUMN_NAME);
        return new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord(negotiation, status, actorAssetUser, timeStamp, btcWalletPublicKey, forProcess);
    }

    private DatabaseTable getNegotiationTable() {
        return getDatabaseTable(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_TABLE_NAME);
    }

    private DatabaseTable getBuyerTable() {
        return getDatabaseTable(AssetBuyerDatabaseConstants.ASSET_BUYER_TABLE_NAME);
    }

    private DatabaseTableFilter constructEqualFilter(String column, String value) {
        return constructFilter(column, value, DatabaseFilterType.EQUAL);
    }

    private DatabaseTableFilter constructFilter(final String column, final String value, final DatabaseFilterType type) {
        return new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return column;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public DatabaseFilterType getType() {
                return type;
            }
        };
    }

    private List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord> constructBuyingRecordList(List<DatabaseTableRecord> records) throws CantAssetUserActorNotFoundException, CantLoadWalletException, InvalidParameterException, CantGetAssetUserActorsException, CantGetDigitalAssetFromLocalStorageException {
        List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord> toReturn = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            toReturn.add(constructBuyingByDatabaseRecord(record));
        }
        return toReturn;
    }

    private List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord> constructNegotiationList(List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
        List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord> toReturn = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            toReturn.add(constructNegotiationByDatabaseRecord(record));
        }
        return toReturn;
    }

    private DatabaseTable getEventsTable() {
        return getDatabaseTable(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TABLE_NAME);
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        return database.getTable(tableName);
    }

    //GETTER AND SETTERS
    public List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord> getActionRequiredBuying() throws DAPException {
        DatabaseTableFilter filter = constructFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME, AssetSellStatus.NO_ACTION_REQUIRED.getCode(), DatabaseFilterType.NOT_EQUALS);
        try {
            return constructBuyingRecordList(getRecordsByFilterBuyerTable(filter));
        } catch (CantLoadWalletException | CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    public List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord> getAddingInputsBuying() throws DAPException {
        DatabaseTableFilter filter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME, AssetSellStatus.ADDING_INPUTS.getCode());
        try {
            return constructBuyingRecordList(getRecordsByFilterBuyerTable(filter));
        } catch (CantLoadWalletException | CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    public List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord> getWaitingCompleteSignature() throws DAPException {
        DatabaseTableFilter filter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME, AssetSellStatus.WAITING_COMPLETE_SIGNATURE.getCode());
        try {
            return constructBuyingRecordList(getRecordsByFilterBuyerTable(filter));
        } catch (CantLoadWalletException | CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    public List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord> getCompletedSignedBuying() throws DAPException {
        DatabaseTableFilter filter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME, AssetSellStatus.COMPLETE_SIGNATURE.getCode());
        try {
            return constructBuyingRecordList(getRecordsByFilterBuyerTable(filter));
        } catch (CantLoadWalletException | CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    public List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord> getNewNegotiations(BlockchainNetworkType networkType) throws DAPException {
        DatabaseTableFilter statusFilter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME, AssetSellStatus.WAITING_CONFIRMATION.getCode());
        DatabaseTableFilter networkFilter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_NETWORK_TYPE_COLUMN_NAME, networkType.getCode());
        try {
            return constructNegotiationList(getRecordsByFilterNegotiationTable(statusFilter, networkFilter));
        } catch (CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    public List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord> getNegotiationAnswer() throws DAPException {
        DatabaseTableFilter confirmedFilter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME, AssetSellStatus.NEGOTIATION_CONFIRMED.getCode());
        DatabaseTableFilter rejectedFilter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME, AssetSellStatus.NEGOTIATION_REJECTED.getCode());
        try {
            return constructNegotiationList(getRecordsByFilterNegotiationTable(confirmedFilter, rejectedFilter));
        } catch (CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    public List<String> getPendingOutgoingDraftEvents() throws CantLoadTableToMemoryException {
        DatabaseTable table = getEventsTable();
        table.addStringFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
        table.addStringFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_EVENT_COLUMN_NAME, com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.OUTGOING_DRAFT_TRANSACTION_FINISHED.getCode(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        List<String> toReturn = new ArrayList<>();
        for (DatabaseTableRecord record : table.getRecords()) {
            toReturn.add(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME));
        }
        return toReturn;
    }

    public List<String> getPendingEvents() throws CantLoadTableToMemoryException {
        DatabaseTable table = getEventsTable();
        table.addStringFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
        table.loadToMemory();
        List<String> toReturn = new ArrayList<>();
        for (DatabaseTableRecord record : table.getRecords()) {
            toReturn.add(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME));
        }
        return toReturn;
    }

    public EventType getEventType(String eventId) throws InvalidParameterException, RecordsNotFoundException, CantLoadTableToMemoryException {
        DatabaseTable table = getEventsTable();
        table.addStringFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
        table.loadToMemory();
        if (table.getRecords().isEmpty()) throw new RecordsNotFoundException();
        DatabaseTableRecord record = table.getRecords().get(0);
        return EventType.getByCode(record.getStringValue(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_EVENT_COLUMN_NAME));
    }


    public org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord getNegotiationRecord(UUID recordId) throws DAPException {
        DatabaseTableFilter filter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_ID_COLUMN_NAME, recordId.toString());
        try {
            List<DatabaseTableRecord> recordList = getRecordsByFilterNegotiationTable(filter);
            if (recordList.isEmpty()) {
                throw new RecordsNotFoundException();
            }
            return constructNegotiationByDatabaseRecord(recordList.get(0));
        } catch (CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }

    public List<org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord> getBuyingRecordsForNegotiation(UUID negotiationId) throws DAPException {
        DatabaseTableFilter filter = constructEqualFilter(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_REFERENCE_COLUMN_NAME, negotiationId.toString());
        try {
            return constructBuyingRecordList(getRecordsByFilterBuyerTable(filter));
        } catch (CantLoadWalletException | CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new DAPException(e);
        }
    }
    //INNER CLASSES
}
