package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetContractListException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.BrokerSubmitOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions.CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.CRYPTO_MERCHANDISE_SUBMITTED;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_ONLINE_DE_STOCK;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE_CONFIRMATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.PENDING_SUBMIT_ONLINE_MERCHANDISE_NOTIFICATION;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus.getByCode;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.DATABASE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TABLE_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME;
import static com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/2015.
 * Updated by Nelson Ramirez (nelsonalfo@gmail.com) on 25/05/2016
 */
public class BrokerSubmitOnlineMerchandiseBusinessTransactionDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private BrokerSubmitOnlineMerchandisePluginRoot pluginRoot;
    private Database database;

    public BrokerSubmitOnlineMerchandiseBusinessTransactionDao(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId,
            final Database database,
            final BrokerSubmitOnlineMerchandisePluginRoot pluginRoot) {

        this.pluginRoot = pluginRoot;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
    }

    public void initialize() throws CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    DATABASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory databaseFactory =
                        new BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        DATABASE_NAME
                );

            } catch (CantCreateDatabaseException f) {
                pluginRoot.reportError(
                        DISABLES_THIS_PLUGIN,
                        f);
                throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantCreateDatabaseException.DEFAULT_MESSAGE,
                        f,
                        "",
                        "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                pluginRoot.reportError(
                        DISABLES_THIS_PLUGIN,
                        z);
                throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                        CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                        z,
                        "",
                        "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {
            pluginRoot.reportError(
                    DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantOpenDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            pluginRoot.reportError(
                    DISABLES_THIS_PLUGIN,
                    e);
            throw new CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(
                    CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException.DEFAULT_MESSAGE,
                    e,
                    "",
                    "Generic Exception.");
        }
    }

    /**
     * This method save an incoming new event in database.
     *
     * @param eventType   event type
     * @param eventSource event source
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            UUID eventRecordID = UUID.randomUUID();
            saveNewEvent(eventType, eventSource, eventRecordID.toString());

        } catch (Exception exception) {
            pluginRoot.reportError(
                    DISABLES_THIS_PLUGIN,
                    exception);
            throw new CantSaveEventException(
                    FermatException.wrapException(exception),
                    "Saving new event.",
                    "Unexpected exception");
        }
    }

    /**
     * This method save an incoming new event in database. You can set the event Id with this method
     *
     * @param eventType   event type
     * @param eventSource event source
     * @param eventId     event ID
     * @throws CantSaveEventException
     */
    public void saveNewEvent(String eventType, String eventSource, String eventId) throws CantSaveEventException {
        try {
            if (isContractHashInDatabase(eventId)) {
                return;
            }
            DatabaseTable databaseTable = getDatabaseEventsTable();
            DatabaseTableRecord eventRecord = databaseTable.getEmptyRecord();
            long unixTime = System.currentTimeMillis();
            eventRecord.setStringValue(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventType);
            eventRecord.setStringValue(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
            databaseTable.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Submit Online Merchandise database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

    /**
     * This method returns the actual contract transaction status
     *
     * @param contractHash the contract Hash/ID
     * @return the contract transaction status
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            String stringContractTransactionStatus = getValue(
                    contractHash,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME);

            return getByCode(stringContractTransactionStatus);

        } catch (InvalidParameterException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the contract transaction status",
                    "Invalid code in ContractTransactionStatus enum");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Getting the contract transaction status", "Unexpected error");
        }
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractPurchase in crypto broker side, only for backup
     *
     * @param purchaseContract the object with the purchase contract information to persist
     * @param currencyCode     the code of the sent crypto currency
     * @param cryptoAmount     the amount of crypto currency
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractPurchase purchaseContract,
                                          String currencyCode,
                                          long cryptoAmount) throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(purchaseContract.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(purchaseContract).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();

            databaseTableRecord = buildDatabaseTableRecord(databaseTableRecord, purchaseContract, currencyCode, cryptoAmount);

            databaseTable.insertRecord(databaseTableRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Error in persistContractInDatabase", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method persists a sale contract record in database to be used to send the merchandise to the customer
     *
     * @param saleContract          a object with the given sale contract information: contract Hash/ID, broker and customer public keys
     * @param customerCryptoAddress the customer crypto address
     * @param cryptoWalletPublicKey the crypto wallet (bitcoins, fermats, litecoins, etc) public key
     * @param cryptoAmount          the amount of crypto currency
     * @param cbpWalletPublicKey    the CBP wallet (customer, broker) public key
     * @param referencePrice        the reference price
     * @param merchandiseCurrency   the merchandise crypto currency to be send
     * @param blockchainNetworkType the blockchain network type
     * @param intraActorPublicKey   the intra actor public key
     * @throws CantInsertRecordException
     */
    public void persistContractInDatabase(CustomerBrokerContractSale saleContract,
                                          String customerCryptoAddress,
                                          String cryptoWalletPublicKey,
                                          long cryptoAmount,
                                          String cbpWalletPublicKey,
                                          BigDecimal referencePrice,
                                          CryptoCurrency merchandiseCurrency,
                                          BlockchainNetworkType blockchainNetworkType,
                                          String intraActorPublicKey,
                                          FeeOrigin feeOrigin,
                                          long fee) throws CantInsertRecordException {
        try {
            if (isContractHashInDatabase(saleContract.getContractId())) {
                System.out.println(new StringBuilder().append("The contract ").append(saleContract).append(" exists in database").toString());
                return;
            }
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();

            databaseTableRecord = buildDatabaseTableRecord(
                    databaseTableRecord,
                    saleContract,
                    customerCryptoAddress,
                    cryptoWalletPublicKey,
                    cryptoAmount,
                    cbpWalletPublicKey,
                    referencePrice,
                    merchandiseCurrency,
                    blockchainNetworkType,
                    intraActorPublicKey,
                    feeOrigin,
                    fee);

            databaseTable.insertRecord(databaseTableRecord);

        } catch (CantInsertRecordException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Error in persistContractInDatabase", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord list from the pending Crypto De Stock transactions
     *
     * @return the list of business transaction
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingDeStockTransactionList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getBrokerOnlinePaymentRecordList(
                    PENDING_ONLINE_DE_STOCK.getCode(),
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantGetContractListException.DEFAULT_MESSAGE, exception, "Error in persistContractInDatabase", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns a BusinessTransactionRecord list from the pending Crypto transactions
     *
     * @return the list of business transaction
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingCryptoTransactionList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {
            return getBrokerOnlinePaymentRecordList(
                    CRYPTO_MERCHANDISE_SUBMITTED.getCode(),
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from getPendingCryptoTransactionList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the BusinessTransactionRecord from a given contractHashId.
     * The BusinessTransactionRecord contains all the Submit Online Merchandise table record.
     *
     * @param contractHash the contract Hash/ID
     * @return the business transaction record associated with the contract Hash
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public BusinessTransactionRecord getBrokerBusinessTransactionRecord(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            double referencePriceFromDatabase;

            BusinessTransactionRecord businessTransactionRecord = new BusinessTransactionRecord();
            databaseTable.addStringFilter(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);

            businessTransactionRecord.setBrokerPublicKey(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setContractHash(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME));
            ContractTransactionStatus status = getByCode(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME));
            businessTransactionRecord.setContractTransactionStatus(status);
            businessTransactionRecord.setCustomerPublicKey(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setTransactionHash(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME));
            businessTransactionRecord.setTransactionId(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME));
            businessTransactionRecord.setActorPublicKey(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setExternalWalletPublicKey(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME));
            businessTransactionRecord.setCryptoAmount(record.getLongValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME));
            businessTransactionRecord.setCBPWalletPublicKey(record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME));

            referencePriceFromDatabase = record.getDoubleValue(SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME);
            businessTransactionRecord.setPriceReference(BigDecimal.valueOf(referencePriceFromDatabase));

            final String cryptoCurrencyCode = record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME);
            if (cryptoCurrencyCode != null) {
                businessTransactionRecord.setCryptoCurrency(CryptoCurrency.getByCode(cryptoCurrencyCode));

                String cryptoAddressString = record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME);
                CryptoAddress customerCryptoAddress = new CryptoAddress(cryptoAddressString, businessTransactionRecord.getCryptoCurrency());
                businessTransactionRecord.setCryptoAddress(customerCryptoAddress);
            }

            String blockchainNetworkTypeString = record.getStringValue(SUBMIT_ONLINE_MERCHANDISE_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME);
            BlockchainNetworkType blockchainNetworkType = (blockchainNetworkTypeString == null || blockchainNetworkTypeString.isEmpty()) ?
                    BlockchainNetworkType.getDefaultBlockchainNetworkType() :
                    BlockchainNetworkType.getByCode(blockchainNetworkTypeString);

            businessTransactionRecord.setBlockchainNetworkType(blockchainNetworkType);

            businessTransactionRecord.setFee(record.getLongValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_FEE_COLUMN_NAME));
            String feeOriginString = record.getStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_ORIGIN_FEE_COLUMN_NAME);
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
            businessTransactionRecord.setFeeOrigin(feeOrigin);

            return businessTransactionRecord;

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        } catch (InvalidParameterException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Invalid parameter in ContractTransactionStatus");
        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Unexpected Result");
        }

    }

    /**
     * Update a Business Transaction Record in database
     *
     * @param businessTransactionRecord object with the updated information
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateBusinessTransactionRecord(BusinessTransactionRecord businessTransactionRecord)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            String contractHash = businessTransactionRecord.getContractHash();

            DatabaseTable databaseTable = getDatabaseSubmitTable();
            databaseTable.addStringFilter(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();

            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record = buildDatabaseTableRecord(record, businessTransactionRecord);

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected results in database");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Updating databaseTableRecord from a BusinessTransactionRecord",
                    "Unexpected error");
        }
    }

    /**
     * This method returns the pending to submit crypto transactions
     *
     * @return list of business transaction ID
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingToSubmitCryptoList() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {
            return getStringList(PENDING_SUBMIT_ONLINE_MERCHANDISE.getCode(),
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingToSubmitCryptoList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the pending to submit notifications transactions
     *
     * @return the list of business transaction records
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitNotificationList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getBrokerOnlinePaymentRecordList(
                    PENDING_SUBMIT_ONLINE_MERCHANDISE_NOTIFICATION.getCode(),
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingTosSubmitNotificationList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * @return list of Business Transaction records pending to Submit Confirmation
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitConfirmationList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getCustomerOnlinePaymentRecordList(
                    PENDING_SUBMIT_ONLINE_MERCHANDISE_CONFIRMATION.getCode(),
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from PendingTosSubmitNotificationList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * @return list of Business Transaction records with Crypto Status PENDING_SUBMIT
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getPendingToSubmitCryptoStatusList() throws
            UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getBrokerOnlinePaymentRecordList(
                    CryptoStatus.PENDING_SUBMIT.getCode(),
                    SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception,
                    "Getting value from getPendingToSubmitCryptoStatusList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * @return list of Business Transaction records with Crypto Status ON_CRYPTO_NETWORK
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getOnCryptoNetworkCryptoStatusList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getBrokerOnlinePaymentRecordList(
                    CryptoStatus.ON_CRYPTO_NETWORK.getCode(),
                    SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException("Error", exception, "Getting value from getOnCryptoNetworkCryptoStatusList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * @return list of Business Transaction records with Crypto Status ON_BLOCKCHAIN
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<BusinessTransactionRecord> getOnBlockchainCryptoStatusList()
            throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {

            return getBrokerOnlinePaymentRecordList(
                    CryptoStatus.ON_BLOCKCHAIN.getCode(),
                    SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

        } catch (CantGetContractListException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new CantGetContractListException("Error", exception, "Getting value from getOnBlockchainCryptoStatusList", "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the event type recorded in database by event id
     *
     * @param eventId the event ID
     * @return the String with the event type code
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public String getEventType(String eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            return records.get(0).getStringValue(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_EVENT_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Getting value from database", "Unexpected error");
        }

    }

    /**
     * @return a list of pending events
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantGetContractListException
     */
    public List<String> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            List<String> eventTypeList = new ArrayList<>();
            databaseTable.addStringFilter(
                    SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return eventTypeList; //There is no records in database, I'll return an empty list.

            for (DatabaseTableRecord databaseTableRecord : records) {
                String eventId = databaseTableRecord.getStringValue(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }

            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantGetContractListException(e, "Getting events in EventStatus.PENDING", "Cannot load the table into memory");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Getting events in EventStatus.PENDING", "Unexpected error");
        }
    }

    /**
     * This method checks if the given contract hash exists in database.
     *
     * @param contractHash the contract Hash/ID to check
     * @return <code>true</code> if the given contract hash exists in database. <code>false</code> otherwise
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public boolean isContractHashInDatabase(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            String contractHashFromDatabase = getValue(
                    contractHash,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME,
                    SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME);

            return contractHashFromDatabase != null;

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method updates the status of an event.
     *
     * @param eventId     the event ID
     * @param eventStatus the new event status
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void updateEventStatus(String eventId, EventStatus eventStatus)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseEventsTable();
            databaseTable.addStringFilter(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            checkDatabaseRecords(records);

            DatabaseTableRecord record = records.get(0);
            record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME, eventStatus.getCode());

            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, new StringBuilder().append("Updating parameter ").append(SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_STATUS_COLUMN_NAME).toString(), "");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected error", "Check the cause");
        }
    }

    /**
     * This method returns the completion date from database.
     *
     * @param contractHash the contract Hash/ID
     * @return the completion date in millis
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    public long getCompletionDateByContractHash(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            databaseTable.addStringFilter(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return 0;

            checkDatabaseRecords(records);

            return records.get(0).getLongValue(SUBMIT_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting completion date from database", "Cannot load the database table");
        }
    }

    /**
     * * This method sets the completion date of a contract in the database.
     *
     * @param contractHash   the contract Hash/ID
     * @param completionDate the new completion date
     * @throws UnexpectedResultReturnedFromDatabaseException
     * @throws CantUpdateRecordException
     */
    public void setCompletionDateByContractHash(String contractHash, long completionDate)
            throws UnexpectedResultReturnedFromDatabaseException, CantUpdateRecordException {
        try {
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            databaseTable.addStringFilter(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, contractHash, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty())
                return;

            checkDatabaseRecords(records);
            DatabaseTableRecord record = records.get(0);
            record.setLongValue(SUBMIT_ONLINE_MERCHANDISE_COMPLETION_DATE_COLUMN_NAME, completionDate);
            databaseTable.updateRecord(record);

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Setting completion date from database", "Cannot load the database table");
        }
    }

    /**
     * Returns the Database
     *
     * @return Database
     */
    private Database getDataBase() {
        return database;
    }

    /**
     * Returns the Ack Online Payment DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseSubmitTable() {
        return getDataBase().getTable(
                SUBMIT_ONLINE_MERCHANDISE_TABLE_NAME);
    }

    /**
     * Returns the Submit Online Merchandise Events DatabaseTable
     *
     * @return DatabaseTable
     */
    private DatabaseTable getDatabaseEventsTable() {
        return getDataBase().getTable(
                SUBMIT_ONLINE_MERCHANDISE_EVENTS_RECORDED_TABLE_NAME);
    }

    /**
     * This method returns a complete database table record from a BusinessTransactionRecord
     *
     * @param record                    the data base record
     * @param businessTransactionRecord the business transaction record
     * @return the filled data base record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record, BusinessTransactionRecord businessTransactionRecord) {
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME, businessTransactionRecord.getTransactionId());
        //For the business transaction this value represents the contract hash.
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, businessTransactionRecord.getContractHash());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getCustomerPublicKey());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getBrokerPublicKey());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getCBPWalletPublicKey());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, businessTransactionRecord.getContractTransactionStatus().getCode());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME, businessTransactionRecord.getCryptoAddress().getAddress());
        record.setLongValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME, businessTransactionRecord.getCryptoAmount());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME, businessTransactionRecord.getCryptoCurrency().getCode());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME, businessTransactionRecord.getExternalWalletPublicKey());
        record.setDoubleValue(SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME, businessTransactionRecord.getPriceReference().doubleValue());
        record.setLongValue(SUBMIT_ONLINE_MERCHANDISE_TIMESTAMP_COLUMN_NAME, businessTransactionRecord.getTimestamp());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_HASH_COLUMN_NAME, businessTransactionRecord.getTransactionHash());
        //new fields
        //Origin Fee
        record.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_ORIGIN_FEE_COLUMN_NAME, businessTransactionRecord.getFeeOrigin().getCode());
        //Fee
        record.setLongValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_FEE_COLUMN_NAME, businessTransactionRecord.getFee());

        //I need to check if crypto status is null
        CryptoStatus cryptoStatus = businessTransactionRecord.getCryptoStatus();
        if (cryptoStatus != null)
            record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_STATUS_COLUMN_NAME, cryptoStatus.getCode());

        return record;
    }

    /**
     * This method creates a database table record in crypto broker side, only for backup
     *
     * @param record           the database record to fill
     * @param purchaseContract the purchase contract object
     * @param currencyCode     the code of the sent crypto currency
     * @param cryptoAmount     the amount of crypto currency
     * @return the filled database record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record,
                                                         CustomerBrokerContractPurchase purchaseContract,
                                                         String currencyCode,
                                                         long cryptoAmount) {
        UUID transactionId = UUID.randomUUID();

        record.setUUIDValue(SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, purchaseContract.getContractId());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, purchaseContract.getPublicKeyCustomer());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME, purchaseContract.getPublicKeyBroker());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, PENDING_SUBMIT_ONLINE_MERCHANDISE_CONFIRMATION.getCode());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME, currencyCode);
        record.setLongValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);

        return record;
    }

    /**
     * This method creates a database table record from a CustomerBrokerContractPurchase object.
     * This record is not complete, is missing the transaction hash,  and the crypto status,
     * this values will after sending the crypto amount, also the timestamp is set at this moment.
     *
     * @param record                the database record to fill
     * @param saleContract          a object with the given sale contract information: contract Hash/ID, broker and customer public keys
     * @param customerCryptoAddress the customer crypto address
     * @param cryptoWalletPublicKey the crypto wallet (bitcoins, fermats, litecoins, etc) public key
     * @param cryptoAmount          the amount of crypto currency
     * @param cbpWalletPublicKey    the CBP wallet (customer, broker) public key
     * @param referencePrice        the reference price
     * @param merchandiseCurrency   the merchandise crypto currency to be send
     * @param blockchainNetworkType the blockchain network type
     * @param intraActorPk          the intra actor public key
     * @return the filled data base record
     */
    private DatabaseTableRecord buildDatabaseTableRecord(DatabaseTableRecord record,
                                                         CustomerBrokerContractSale saleContract,
                                                         String customerCryptoAddress,
                                                         String cryptoWalletPublicKey,
                                                         long cryptoAmount,
                                                         String cbpWalletPublicKey,
                                                         BigDecimal referencePrice,
                                                         CryptoCurrency merchandiseCurrency,
                                                         BlockchainNetworkType blockchainNetworkType,
                                                         String intraActorPk,
                                                         FeeOrigin feeOrigin,
                                                         long fee) {

        UUID transactionId = UUID.randomUUID();
        record.setUUIDValue(SUBMIT_ONLINE_MERCHANDISE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        //For the business transaction this value represents the contract hash.
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_HASH_COLUMN_NAME, saleContract.getContractId());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, saleContract.getPublicKeyCustomer());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_BROKER_PUBLIC_KEY_COLUMN_NAME, saleContract.getPublicKeyBroker());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, cbpWalletPublicKey);
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME, PENDING_ONLINE_DE_STOCK.getCode());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_ADDRESS_COLUMN_NAME, customerCryptoAddress);
        record.setLongValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_AMOUNT_COLUMN_NAME, cryptoAmount);
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_CRYPTO_WALLET_PUBLIC_KEY_COLUMN_NAME, cryptoWalletPublicKey);
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_INTRA_ACTOR_PUBLIC_KEY_COLUMN_NAME, intraActorPk);
        record.setDoubleValue(SUBMIT_ONLINE_MERCHANDISE_REFERENCE_PRICE_COLUMN_NAME, referencePrice.doubleValue());
        //Origin Fee
        record.setStringValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_ORIGIN_FEE_COLUMN_NAME, feeOrigin.getCode());
        //Fee
        record.setLongValue(BrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseConstants.SUBMIT_ONLINE_MERCHANDISE_FEE_COLUMN_NAME, fee);


        if (blockchainNetworkType == null)
            blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        record.setStringValue(SUBMIT_ONLINE_MERCHANDISE_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, blockchainNetworkType.getCode());

        return record;
    }

    /**
     * This method returns a list of {@link BusinessTransactionRecord} according to the given arguments.
     *
     * @param key         the search key.
     * @param keyColumn   the column to search.
     * @param valueColumn the column that contain the value.
     * @return List<BusinessTransactionRecord>
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<BusinessTransactionRecord> getBrokerOnlinePaymentRecordList(String key, String keyColumn, String valueColumn)
            throws CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {

        List<String> pendingContractHash = getStringList(key, keyColumn, valueColumn);
        List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();

        for (String contractHash : pendingContractHash) {
            BusinessTransactionRecord businessTransactionRecord = getBrokerBusinessTransactionRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }

        return businessTransactionRecordList;
    }

    /**
     * This method returns a BusinessTransactionRecord List according the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return List<BusinessTransactionRecord>
     * @throws CantGetContractListException
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private List<BusinessTransactionRecord> getCustomerOnlinePaymentRecordList(String key, String keyColumn, String valueColumn) throws
            CantGetContractListException, UnexpectedResultReturnedFromDatabaseException {

        List<String> pendingContractHash = getStringList(key, keyColumn, valueColumn);
        List<BusinessTransactionRecord> businessTransactionRecordList = new ArrayList<>();
        BusinessTransactionRecord businessTransactionRecord;

        for (String contractHash : pendingContractHash) {
            businessTransactionRecord = getBrokerBusinessTransactionRecord(contractHash);
            businessTransactionRecordList.add(businessTransactionRecord);
        }

        return businessTransactionRecordList;
    }

    /**
     * This method returns a <code>List</code> of String with the parameter in the arguments.
     *
     * @param key         String with the search key.
     * @param keyColumn   String with the key column name.
     * @param valueColumn String with the value searched column name.
     * @return List of String values
     */
    private List<String> getStringList(String key, String keyColumn, String valueColumn) throws CantGetContractListException {
        try {
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            List<String> contractHashList = new ArrayList<>();
            databaseTable.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty())
                return contractHashList; //There is no records in database, I'll return an empty list.

            for (DatabaseTableRecord databaseTableRecord : records) {
                String value = databaseTableRecord.getStringValue(valueColumn);
                contractHashList.add(value);
            }

            return contractHashList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetContractListException(e, new StringBuilder().append("Getting ").append(valueColumn).append(" based on ").append(key).toString(), "Cannot load the table into memory");
        }
    }

    /**
     * This method returns a String value from parameters in database.
     *
     * @param key         the key to search
     * @param keyColumn   the column where is the key
     * @param valueColumn the column with the value
     * @return the value for the given parameters
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private String getValue(String key, String keyColumn, String valueColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            DatabaseTable databaseTable = getDatabaseSubmitTable();
            databaseTable.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if (records.isEmpty()) return null;

            checkDatabaseRecords(records);

            return records.get(0).getStringValue(valueColumn);

        } catch (CantLoadTableToMemoryException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        }
    }

    /**
     * This method check the database record result.
     *
     * @param records the records to check
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws UnexpectedResultReturnedFromDatabaseException {
        // Represents the maximum number of records in <code>records</code>
        //I'm gonna set this number in 1 for now, because I want to check the records object has one only result.
        int VALID_RESULTS_NUMBER = 1;
        int recordsSize;
        if (records.isEmpty())
            return;

        recordsSize = records.size();

        if (recordsSize > VALID_RESULTS_NUMBER)
            throw new UnexpectedResultReturnedFromDatabaseException(new StringBuilder().append("I excepted ").append(VALID_RESULTS_NUMBER).append(", but I got ").append(recordsSize).toString());
    }
}
