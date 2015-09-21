package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database.OutgoingIntraUserTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 21/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingIntraUserTransactionDatabaseConstants {


    /**
     * Outgoing Intra User database name.
     */
    static final String OUTGOING_INTRA_USER_DATABASE_NAME = "outgoing_intra_user_database";

    /**
     * Outgoing Intra User database table definition.
     */
    static final String OUTGOING_INTRA_USER_TABLE_NAME = "outgoing_intra_user";

    static final String OUTGOING_INTRA_USER_TRANSACTION_ID_COLUMN_NAME          = "transaction_id";
    static final String OUTGOING_INTRA_USER_WALLET_ID_TO_DEBIT_FROM_COLUMN_NAME = "wallet_id_to_debit_from";
    static final String OUTGOING_INTRA_USER_TRANSACTION_HASH_COLUMN_NAME        = "transaction_hash";
    static final String OUTGOING_INTRA_USER_ADDRESS_FROM_COLUMN_NAME            = "address_from";
    static final String OUTGOING_INTRA_USER_ADDRESS_TO_COLUMN_NAME              = "address_to";
    static final String OUTGOING_INTRA_USER_CRYPTO_CURRENCY_COLUMN_NAME         = "crypto_currency";
    static final String OUTGOING_INTRA_USER_CRYPTO_AMOUNT_COLUMN_NAME           = "crypto_amount";
    static final String OUTGOING_INTRA_USER_TRANSACTION_STATUS_COLUMN_NAME      = "transaction_status";
    static final String OUTGOING_INTRA_USER_DESCRIPTION_COLUMN_NAME             = "description";
    static final String OUTGOING_INTRA_USER_TIMESTAMP_COLUMN_NAME               = "timestamp";
    static final String OUTGOING_INTRA_USER_CRYPTO_STATUS_COLUMN_NAME           = "crypto_status";
    static final String OUTGOING_INTRA_USER_ACTOR_FROM_PUBLIC_KEY_COLUMN_NAME   = "actor_from_public_key";
    static final String OUTGOING_INTRA_USER_ACTOR_FROM_TYPE_COLUMN_NAME         = "actor_from_type";
    static final String OUTGOING_INTRA_USER_ACTOR_TO_PUBLIC_KEY_COLUMN_NAME     = "actor_to_public_key";
    static final String OUTGOING_INTRA_USER_ACTOR_TO_TYPE_COLUMN_NAME           = "actor_to_type";

    static final String OUTGOING_INTRA_USER_FIRST_KEY_COLUMN = "transaction_id";
}
