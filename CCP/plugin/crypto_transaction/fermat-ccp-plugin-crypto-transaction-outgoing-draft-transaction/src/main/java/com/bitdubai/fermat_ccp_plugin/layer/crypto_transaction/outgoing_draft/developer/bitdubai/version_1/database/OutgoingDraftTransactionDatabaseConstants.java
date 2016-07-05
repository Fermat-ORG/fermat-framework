package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.database;

/**
 * The Class <code>OutgoingIntraActorTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 21/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingDraftTransactionDatabaseConstants {


    /**
     * Outgoing Intra User database name.
     */
    public static final String OUTGOING_DRAFT_DATABASE_NAME = "outgoing_draft_database";

    /**
     * Outgoing Intra User database table definition.
     */
    public static final String OUTGOING_DRAFT_TABLE_NAME = "outgoing_draft";

    public static final String OUTGOING_DRAFT_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String OUTGOING_DRAFT_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME = "wallet_id_to_debit_from";
    public static final String OUTGOING_DRAFT_TRANSACTION_HASH_COLUMN_NAME = "transaction_hash";
    public static final String OUTGOING_DRAFT_ADDRESS_FROM_COLUMN_NAME = "address_from";
    public static final String OUTGOING_DRAFT_ADDRESS_TO_COLUMN_NAME = "address_to";
    public static final String OUTGOING_DRAFT_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency";
    public static final String OUTGOING_DRAFT_CRYPTO_AMOUNT_COLUMN_NAME = "crypto_amount";
    public static final String OUTGOING_DRAFT_OP_RETURN_COLUMN_NAME = "op_return";
    public static final String OUTGOING_DRAFT_TRANSACTION_STATUS_COLUMN_NAME = "transaction_status";
    public static final String OUTGOING_DRAFT_DESCRIPTION_COLUMN_NAME = "description";
    public static final String OUTGOING_DRAFT_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String OUTGOING_DRAFT_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    public static final String OUTGOING_DRAFT_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME = "actor_from_public_key";
    public static final String OUTGOING_DRAFT_ACTOR_FROM_TYPE_COLUMN_NAME = "actor_from_type";
    public static final String OUTGOING_DRAFT_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME = "actor_to_public_key";
    public static final String OUTGOING_DRAFT_ACTOR_TO_TYPE_COLUMN_NAME = "actor_to_type";
    public static final String OUTGOING_DRAFT_WALLET_REFERENCE_TYPE_COLUMN_NAME = "reference_wallet";
    public static final String OUTGOING_DRAFT_SAME_DEVICE_COLUMN_NAME = "same_device";
    public static final String OUTGOING_DRAFT_RUNNING_NETWORK_TYPE = "runningNetworkType";
    public static final String OUTGOING_DRAFT_TRANSACTION_MARK_COLUMN_NAME = "read_mark";
    public static final String OUTGOING_DRAFT_TRANSACTION_FEE_COLUMN = "transaction_fee";
    public static final String OUTGOING_DRAFT_TRANSACTION_FEE_ORIGIN_COLUMN = "transaction_feeOrigin";
    public static final String OUTGOING_DRAFT_FIRST_KEY_COLUMN = "transaction_id";


}
