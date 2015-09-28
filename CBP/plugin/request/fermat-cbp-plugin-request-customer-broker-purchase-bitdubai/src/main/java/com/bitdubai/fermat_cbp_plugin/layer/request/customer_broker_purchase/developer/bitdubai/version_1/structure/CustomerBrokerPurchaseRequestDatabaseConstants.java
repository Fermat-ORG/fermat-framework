package com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_purchase.developer.bitdubai.version_1.structure;

/**
 * Created by angel on 24/9/15.
 */

public class CustomerBrokerPurchaseRequestDatabaseConstants {

    /**
     *  CustomerBrokerPurchaseRequest database name.
     */

        public static final String CUSTOMER_BROKER_PURCHASE_DATABASE_NAME = "customer_broker_purchase_request";

    /**
     *  CustomerBrokerPurchaseRequest database table definition.
     */

        static final String CUSTOMER_BROKER_PURCHASE_TABLE_NAME = "customer_broker_purchase_request";

        public static final String CUSTOMER_BROKER_PURCHASE_TABLE_ID_COLUMN_NAME = "request_id";
        public static final String CUSTOMER_BROKER_PURCHASE_TABLE_SENDER_PUBLICKEY_COLUMN_NAME = "sender_publickey";
        public static final String CUSTOMER_BROKER_PURCHASE_TABLE_DESTINATION_PUBLICKEY_COLUMN_NAME = "destination_publickey";
        public static final String CUSTOMER_BROKER_PURCHASE_TABLE_MERCHANDISE_CURRENCY_COLUMN_NAME = "merchandise_currency";
        public static final String CUSTOMER_BROKER_PURCHASE_TABLE_MERCHANDISE_AMOUNT_COLUMN_NAME = "merchandise_amount";
        public static final String CUSTOMER_BROKER_PURCHASE_TABLE_PAYMENT_CURRENCY_COLUMN_NAME = "payment_currency";
        public static final String CUSTOMER_BROKER_PURCHASE_TABLE_REQUEST_STATUS_COLUMN_NAME = "request_status";

        public static final String CUSTOMER_BROKER_PURCHASE_TABLE_FIRST_KEY_COLUMN = "request_id";

    /**
     *  CustomerBrokerPurchaseRequestEventsRecorded database table definition.
     */

        public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_NAME = "customer_broker_purchase_events_recorded";

        public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_ID_COLUMN = "id";
        public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_EVENT_COLUMN = "event";
        public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_SOURCE_COLUMN = "source";
        public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_STATUS_COLUMN = "status";
        public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN = "timestamp";

        public static final String CUSTOMER_BROKER_PURCHASE_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "id";

}
