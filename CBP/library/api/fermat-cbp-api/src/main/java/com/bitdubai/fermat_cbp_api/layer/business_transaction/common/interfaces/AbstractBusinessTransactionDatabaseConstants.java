package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/07/16.
 */
public class AbstractBusinessTransactionDatabaseConstants {

    public static final String BUSINESS_TRANSACTION_MESSAGE_STATUS_TABLE = "message_status";


    public static final String BUSINESS_TRANSACTION_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String BUSINESS_TRANSACTION_SEND_TIMESTAMP_COLUMN_NAME = "send_timestamp";
    public static final String BUSINESS_TRANSACTION_LAST_TIMESTAMP_COLUMN_NAME = "last_timestamp";
    public static final String BUSINESS_TRANSACTION_COMPLETION_TIMESTAMP_COLUMN_NAME = "completion_timestamp";
    public static final String BUSINESS_TRANSACTION_FAILED_EVENT_TIMESTAMP_COLUMN_NAME = "failed_timestamp";
    public static final String BUSINESS_TRANSACTION_COUNTER_COLUMN_NAME = "counter";
    public static final String BUSINESS_TRANSACTION_RESEND_COUNTER_COLUMN_NAME = "resend_counter";
    public static final String BUSINESS_TRANSACTION_SUCCESSFUL_FLAG_TIMESTAMP_COLUMN_NAME = "successful_flag";

    public static final String BUSINESS_TRANSACTION_FIRST_KEY_COLUMN = "contract_hash";

}
