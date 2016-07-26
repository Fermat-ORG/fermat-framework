package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.database;

/**
 * The Class <code>UserLevelBusinessTransactionCustomerBrokerPurchaseConstants
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Franklin Marcano - (franklinmarcano970@gmail.com) on 11.12.15
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UserLevelBusinessTransactionCustomerBrokerPurchaseConstants {
    /**
     * Customer Broker Purchase database table definition.
     */
    public static final String CUSTOMER_BROKER_PURCHASE_DATABASE_NAME = "customer_broker_purchase_DB";

    /**
     * Customer Broker Purchase database table definition.
     */
    public static final String CUSTOMER_BROKER_PURCHASE_TABLE_NAME = "customer_broker_purchase";

    public static final String CUSTOMER_BROKER_PURCHASE_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String CUSTOMER_BROKER_PURCHASE_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String CUSTOMER_BROKER_PURCHASE_CONTRACT_TRANSACTION_ID_COLUMN_NAME = "contract_transaction_id";
    public static final String CUSTOMER_BROKER_PURCHASE_PURCHASE_STATUS_COLUMN_NAME = "purchase_status";
    public static final String CUSTOMER_BROKER_PURCHASE_CONTRACT_STATUS_COLUMN_NAME = "contract_status";
    public static final String CUSTOMER_BROKER_PURCHASE_TRANSACTION_STATUS_COLUMN_NAME = "transaction_status";
    public static final String CUSTOMER_BROKER_PURCHASE_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    public static final String CUSTOMER_BROKER_PURCHASE_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    public static final String CUSTOMER_BROKER_PURCHASE_MEMO_COLUMN_NAME = "memo";

    public static final String CUSTOMER_BROKER_PURCHASE_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Events recorded database table definition.
     */
    public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_NAME = "events_recorded";

    public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";


}
