package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_crypto_money_purchase.developer.bitdubai.version_1.structure;

/**
 * Created by angel on 24/9/15.
 */

public class CustomerBrokerCryptoMoneyPurchaseContractDatabaseConstants {

    /**
     *  CustomerBrokerCryptoMoneyPurchaseContract database name.
     */

        public static final String CRYPTO_MONEY_PURCHASE_DATABASE_NAME = "customer_broker_crypto_money_purchase_contract";

    /**
     *  CustomerBrokerCryptoMoneyPurchaseContract database table definition.
     */

        static final String CRYPTO_MONEY_PURCHASE_TABLE_NAME = "customer_broker_crypto_money_purchase";

        public static final String CRYPTO_MONEY_PURCHASE_TABLE_ID_COLUMN_NAME = "contract_id";
        public static final String CRYPTO_MONEY_PURCHASE_PUBLIC_KEY_BROKER_COLUMN_NAME = "broker_publicKey";
        public static final String CRYPTO_MONEY_PURCHASE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME = "customer_publicKey";
        public static final String CRYPTO_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME = "payment_currency";
        public static final String CRYPTO_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME = "merchandise_currency";
        public static final String CRYPTO_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME = "reference_price";
        public static final String CRYPTO_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME = "reference_currency";
        public static final String CRYPTO_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME = "merchandise_amount";
        public static final String CRYPTO_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME = "payment_amount";
        public static final String CRYPTO_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME = "payment_expiration_date";
        public static final String CRYPTO_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME = "merchandise_delivery_expiration_date";
        public static final String CRYPTO_MONEY_PURCHASE_STATUS_COLUMN_NAME = "status";

        public static final String CRYPTO_MONEY_PURCHASE_TABLE_FIRST_KEY_COLUMN = "contract_id";

    /**
     *  CustomerBrokerCryptoMoneyPurchaseEventsRecorded database table definition.
     */

        public static final String CRYPTO_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_NAME = "customer_broker_crypto_money_purchase_events_recorded";

        public static final String CRYPTO_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_ID_COLUMN = "id";
        public static final String CRYPTO_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_EVENT_COLUMN = "event";
        public static final String CRYPTO_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_SOURCE_COLUMN = "source";
        public static final String CRYPTO_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_STATUS_COLUMN = "status";
        public static final String CRYPTO_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN = "timestamp";

        public static final String CRYPTO_MONEY_PURCHASE_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "id";

}
