package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.database;

/**
 * Created by Joaquin Carrasquero on 17/03/16.
 */
public class OutgoinDeviceUserTransactionDatabaseConstants {

    /**
     * Outgoing Intra User database name.
     */
    public static final String OUTGOING_DEVICE_DATABASE_NAME= "outgoing_device_database";

    /**
     * Outgoing Intra User database table definition.
     */
    public static final String OUTGOING_DEVICE_TABLE_NAME = "outgoing_draft";

    public static final String OUTGOING_DEVICE_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String OUTGOING_DEVICE_TRANSACTION_HASH_COLUMN_NAME = "transaction_hash";
    public static final String OUTGOING_DEVICE_CRYPTO_AMOUNT_COLUMN_NAME = "crypto_amount";
    public static final String OUTGOING_DEVICE_TRANSACTION_STATUS_COLUMN_NAME = "transaction_status";
    public static final String OUTGOING_DEVICE_DESCRIPTION_COLUMN_NAME = "description";
    public static final String OUTGOING_DEVICE_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String OUTGOING_DEVICE_ACTOR_TYPE_COLUMN_NAME = "actor_type";
    public static final String OUTGOING_DEVICE_WALLET_REFERENCE_TYPE_SENDING_COLUMN_NAME = "reference_wallet_sending";
    public static final String OUTGOING_DEVICE_WALLET_REFERENCE_TYPE_RECEIVING_COLUMN_NAME = "reference_wallet_receiving";
    public static final String OUTGOING_DEVICE_WALLET_PUBLIC_KEY_SENDING_COLUMN_NAME = "wallet_public_key_sending";
    public static final String OUTGOING_DEVICE_WALLET_PUBLIC_KEY_RECEIVING_COLUMN_NAME = "wallet_public_key_receiving";
    public static final String OUTGOING_DEVICE_RUNNING_NETWORK_TYPE = "runningNetworkType";
    public static final String OUTGOING_DEVICE_FIRST_KEY_COLUMN = "transaction_id";

}
