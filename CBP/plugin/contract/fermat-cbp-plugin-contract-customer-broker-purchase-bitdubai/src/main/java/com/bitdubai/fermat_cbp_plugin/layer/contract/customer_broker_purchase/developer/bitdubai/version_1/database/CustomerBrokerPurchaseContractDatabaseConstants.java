package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseContractDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 02/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerPurchaseContractDatabaseConstants {

    /**
     * Contract Purchase database table definition.
     */
    static final String CONTRACT_PURCHASE_TABLE_NAME = "contract_purchase";

    static final String CONTRACT_PURCHASE_CONTRACT_ID_COLUMN_NAME = "contract_id";
    static final String CONTRACT_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "customer_public_key";
    static final String CONTRACT_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String CONTRACT_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME = "payment_currency";
    static final String CONTRACT_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME = "merchandise_currency";
    static final String CONTRACT_PURCHASE_REFERENCE_PRICE_COLUMN_NAME = "reference_price";
    static final String CONTRACT_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME = "reference_currency";
    static final String CONTRACT_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME = "payment_amount";
    static final String CONTRACT_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME = "merchandise_amount";
    static final String CONTRACT_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME = "payment_expiration_date";
    static final String CONTRACT_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME = "merchandise_delivery_expiration_date";
    static final String CONTRACT_PURCHASE_STATUS_COLUMN_NAME = "status";

    static final String CONTRACT_PURCHASE_FIRST_KEY_COLUMN = "contract_id";

}