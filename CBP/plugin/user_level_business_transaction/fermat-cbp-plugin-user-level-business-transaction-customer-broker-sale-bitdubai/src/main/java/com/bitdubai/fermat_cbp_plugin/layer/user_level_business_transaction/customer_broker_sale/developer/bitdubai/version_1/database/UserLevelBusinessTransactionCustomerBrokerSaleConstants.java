package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.database;

/**
 * The Class <code>UserLevelBusinessTransactionCustomerBrokerSaleConstants
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Franklin Marcano - (franklinmarcano970@gmail.com) on 15.12.15
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UserLevelBusinessTransactionCustomerBrokerSaleConstants {
    /**
     * Customer Broker Purchase database table definition.
     */
    public static final String CUSTOMER_BROKER_SALE_DATABASE_NAME = "customer_broker_sale_DB";

    /**
     * Customer Broker Purchase database table definition.
     */
    public static final String CUSTOMER_BROKER_SALE_TABLE_NAME = "customer_broker_sale";

    public static final String CUSTOMER_BROKER_SALE_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String CUSTOMER_BROKER_SALE_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String CUSTOMER_BROKER_SALE_CONTRACT_TRANSACTION_ID_COLUMN_NAME = "contract_transaction_id";
    public static final String CUSTOMER_BROKER_SALE_PURCHASE_STATUS_COLUMN_NAME = "purchase_status";
    public static final String CUSTOMER_BROKER_SALE_CONTRACT_STATUS_COLUMN_NAME = "contract_status";
    public static final String CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME = "transaction_status";
    public static final String CUSTOMER_BROKER_SALE_CURRENCY_TYPE_COLUMN_NAME = "currency_type";
    public static final String CUSTOMER_BROKER_SALE_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    public static final String CUSTOMER_BROKER_SALE_MEMO_COLUMN_NAME = "memo";

    public static final String CUSTOMER_BROKER_SALE_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Events recorded database table definition.
     */
    public static final String CUSTOMER_BROKER_SALE_EVENTS_RECORDED_TABLE_NAME = "events_recorded";

    public static final String CUSTOMER_BROKER_SALE_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String CUSTOMER_BROKER_SALE_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String CUSTOMER_BROKER_SALE_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String CUSTOMER_BROKER_SALE_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String CUSTOMER_BROKER_SALE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String CUSTOMER_BROKER_SALE_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";


}
