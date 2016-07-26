package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerNewNegotiationTransactionDatabaseConstants {

    public static final String DATABASE_NAME = "customer_broker_new_database";
    /**
     * Customer Broker New database table definition.
     */
    public static final String CUSTOMER_BROKER_NEW_TABLE_NAME = "customer_broker_new";

    public static final String CUSTOMER_BROKER_NEW_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String CUSTOMER_BROKER_NEW_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    public static final String CUSTOMER_BROKER_NEW_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    public static final String CUSTOMER_BROKER_NEW_PUBLIC_KEY_CUSTOMER_COLUMN_NAME = "public_key_customer";
    public static final String CUSTOMER_BROKER_NEW_STATUS_TRANSACTION_COLUMN_NAME = "status";
    public static final String CUSTOMER_BROKER_NEW_STATUS_NEGOTIATION_COLUMN_NAME = "status_negotiation";
    public static final String CUSTOMER_BROKER_NEW_STATE_TRANSMISSION_COLUMN_NAME = "state_transmission";
    public static final String CUSTOMER_BROKER_NEW_NEGOTIATION_TYPE_COLUMN_NAME = "negotiation_type";
    public static final String CUSTOMER_BROKER_NEW_NEGOTIATION_XML_COLUMN_NAME = "negotiation_xml";
    public static final String CUSTOMER_BROKER_NEW_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String CUSTOMER_BROKER_NEW_SEND_TRANSACTION_COLUMN_NAME = "sendTransaction";
    public static final String CUSTOMER_BROKER_NEW_CONFIRM_TRANSACTION_COLUMN_NAME = "confirmTransaction";

    static final String CUSTOMER_BROKER_NEW_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Events recorded database table definition.
     */
    public static final String CUSTOMER_BROKER_NEW_EVENT_TABLE_NAME = "customer_broker_new_event";

    public static final String CUSTOMER_BROKER_NEW_EVENT_ID_COLUMN_NAME = "event_id";
    public static final String CUSTOMER_BROKER_NEW_EVENT_TYPE_COLUMN_NAME = "event";
    public static final String CUSTOMER_BROKER_NEW_EVENT_SOURCE_COLUMN_NAME = "source";
    public static final String CUSTOMER_BROKER_NEW_EVENT_STATUS_COLUMN_NAME = "status";
    public static final String CUSTOMER_BROKER_NEW_EVENT_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String CUSTOMER_BROKER_NEW_EVENT_FIRST_KEY_COLUMN = "event_id";

}