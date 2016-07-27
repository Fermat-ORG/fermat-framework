package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 17/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerUpdateNegotiationTransactionDatabaseConstants {

    public static final String DATABASE_NAME = "customer_broker_update_database";
    /**
     * Customer Broker Update database table definition.
     */
    static final String CUSTOMER_BROKER_UPDATE_TABLE_NAME = "customer_broker_update";

    static final String CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    static final String CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME = "public_key_customer";
    static final String CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME = "status";
    static final String CUSTOMER_BROKER_UPDATE_STATUS_NEGOTIATION_COLUMN_NAME = "status_negotiation";
    static final String CUSTOMER_BROKER_UPDATE_STATE_TRANSMISSION_COLUMN_NAME = "state_transaction";
    static final String CUSTOMER_BROKER_UPDATE_NEGOTIATION_TYPE_COLUMN_NAME = "negotiation_type";
    static final String CUSTOMER_BROKER_UPDATE_NEGOTIATION_XML_COLUMN_NAME = "negotiation_xml";
    static final String CUSTOMER_BROKER_UPDATE_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String CUSTOMER_BROKER_UPDATE_SEND_TRANSACTION_COLUMN_NAME = "sendTransaction";
    static final String CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME = "confirmTransaction";

    static final String CUSTOMER_BROKER_UPDATE_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Customer Broker New Event database table definition.
     */
//    static final String CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME = "customer_broker_new_event";
    static final String CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME = "customer_broker_update_event";

    static final String CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME = "event_id";
    static final String CUSTOMER_BROKER_UPDATE_EVENT_TYPE_COLUMN_NAME = "event_type";
    static final String CUSTOMER_BROKER_UPDATE_EVENT_SOURCE_COLUMN_NAME = "source";
    static final String CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME = "status";
    static final String CUSTOMER_BROKER_UPDATE_EVENT_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String CUSTOMER_BROKER_UPDATE_EVENT_FIRST_KEY_COLUMN = "event_id";

}