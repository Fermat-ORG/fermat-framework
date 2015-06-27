package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;

/**
 * Created by Arturo Vallone 25/04/2015
 * Edited by Ezequiel Postan
 */
class IncomingCryptoDataBaseConstants {

    // IncomingCrypto database name
    static final String INCOMING_CRYPTO_DATABASE = "IncomingCryptoDatabase";

    // IncomingCrypto table name
    static final String INCOMING_CRYPTO_REGISTRY_TABLE_NAME = "IncomingCryptoRegistry";

    /* This is the transaction Id
     * We use an Id different from the transaction hash because we distinguish between fermat transactions
     * and crypto transactions-
     */
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN = new ColumnDefinition("Id", DatabaseDataType.STRING, 36,true);

    // Cryoto Transaction associated to the fermat transaction
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN = new ColumnDefinition("TransactionHash", DatabaseDataType.STRING, 64,false);
    // Crypto Address where the currency was sent
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_TO_COLUMN = new ColumnDefinition("AddressTo", DatabaseDataType.STRING, 34,false);
    // Crypto currency of the transaction (Bitcoin, Litecoin, etc)
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN = new ColumnDefinition("CryptoCurrency", DatabaseDataType.STRING, 3,false);
    // Crypto amount associated to this transaction
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN = new ColumnDefinition("CryptoAmount", DatabaseDataType.MONEY, 100,false);
    // Sender crypto Address
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_FROM_COLUMN = new ColumnDefinition("AddressFrom", DatabaseDataType.STRING, 34,false);
    // A field that stores who is responsible of this transaction
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN = new ColumnDefinition("Specialist", DatabaseDataType.STRING, 10,false);
    // A crypto transaction has one status, the possible status are described in the CryptoStatus enum
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN = new ColumnDefinition("CryptoStatus", DatabaseDataType.STRING, 10,false);
    // A fermat transaction is defined as the application or reversion of a crypto transaction
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN = new ColumnDefinition("Action", DatabaseDataType.STRING, 10,false);
    // The protocol status can be found in the ProtocolStatus enum and list states in the TransactionTransferenceProtocol
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN = new ColumnDefinition("ProtocolStatus", DatabaseDataType.STRING, 10,false);
    // The transaction status is defined by the TransactionStatus enum located in the TransactionTransferenceProtocol package (transaction_transference_protocol)
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN = new ColumnDefinition("TransactionStatus", DatabaseDataType.STRING, 10,false);
    static final ColumnDefinition INCOMING_CRYPTO_REGISTRY_TABLE_TIMESTAMP_COLUMN = new ColumnDefinition("Timestamp", DatabaseDataType.LONG_INTEGER, 100,false);

    static final String INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME = "IncomingCryptoEventsRecorded";
    static final ColumnDefinition INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_ID_COLUMN = new ColumnDefinition("Id", DatabaseDataType.STRING, 36,true);
    static final ColumnDefinition INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_EVENT_COLUMN = new ColumnDefinition("Event", DatabaseDataType.STRING, 10,false);
    static final ColumnDefinition INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_SOURCE_COLUMN = new ColumnDefinition("Source", DatabaseDataType.STRING, 10,false);
    static final ColumnDefinition INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN = new ColumnDefinition("Status", DatabaseDataType.STRING, 10,false);
    static final ColumnDefinition INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN = new ColumnDefinition("Timestamp", DatabaseDataType.LONG_INTEGER, 100,false);

    static class ColumnDefinition {
        final String columnName;
        final DatabaseDataType columnDataType;
        final int columnDataTypeSize;
        final boolean columnIsPrimaryKey;

        public ColumnDefinition(String columnName, DatabaseDataType columnDataType, int columnDataTypeSize, boolean columnIsPrimaryKey) {
            this.columnName = columnName;
            this.columnDataType = columnDataType;
            this.columnDataTypeSize = columnDataTypeSize;
            this.columnIsPrimaryKey = columnIsPrimaryKey;
        }
    }
}
