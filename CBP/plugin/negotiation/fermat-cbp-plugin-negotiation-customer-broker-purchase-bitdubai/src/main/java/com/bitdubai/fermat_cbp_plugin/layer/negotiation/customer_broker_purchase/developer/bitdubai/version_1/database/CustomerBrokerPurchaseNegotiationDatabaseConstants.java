package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerPurchaseNegotiationDatabaseConstants {

    public static final String DATABASE_NAME = "negotiation_customer_broker_purchase";

    /**
     * Negotiations Purchase database table definition.
     */
    static final String NEGOTIATIONS_PURCHASE_TABLE_NAME = "negotiations_purchase";

    static final String NEGOTIATIONS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String NEGOTIATIONS_PURCHASE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "crypto_customer_public_key";
    static final String NEGOTIATIONS_PURCHASE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME = "crypto_broker_public_key";
    static final String NEGOTIATIONS_PURCHASE_START_DATE_TIME_COLUMN_NAME = "start_date_time";
    static final String NEGOTIATIONS_PURCHASE_EXPIRATION_DATE_TIME_COLUMN_NAME = "expiration_date_time";
    static final String NEGOTIATIONS_PURCHASE_STATUS_COLUMN_NAME = "status";
    static final String NEGOTIATIONS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME = "near_expiration_date_time";
    static final String NEGOTIATIONS_PURCHASE_MEMO_COLUMN_NAME = "memo";
    static final String NEGOTIATIONS_PURCHASE_CANCEL_REASON_COLUMN_NAME = "cancel_reason";
    static final String NEGOTIATIONS_PURCHASE_LAST_NEGOTIATION_UPDATE_DATE_COLUMN_NAME = "last_negotiation_update_date";

    static final String NEGOTIATIONS_PURCHASE_FIRST_KEY_COLUMN = "negotiation_id";

    /**
     * Clauses Purchase database table definition.
     */
    static final String CLAUSES_PURCHASE_TABLE_NAME = "clauses_purchase";

    static final String CLAUSES_PURCHASE_CLAUSE_ID_COLUMN_NAME = "clause_id";
    static final String CLAUSES_PURCHASE_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String CLAUSES_PURCHASE_TYPE_COLUMN_NAME = "type";
    static final String CLAUSES_PURCHASE_VALUE_COLUMN_NAME = "value";
    static final String CLAUSES_PURCHASE_STATUS_COLUMN_NAME = "status";
    static final String CLAUSES_PURCHASE_PROPOSED_BY_COLUMN_NAME = "proposed_by";
    static final String CLAUSES_PURCHASE_INDEX_ORDER_COLUMN_NAME = "index_order";

    static final String CLAUSES_PURCHASE_FIRST_KEY_COLUMN = "clause_id";

    /**
     * Changes Purchase database table definition.
     */
    static final String CHANGES_PURCHASE_TABLE_NAME = "changes_purchase";

    static final String CHANGES_PURCHASE_CHANGE_ID_COLUMN_NAME = "change_id";
    static final String CHANGES_PURCHASE_CLAUSE_ID_COLUMN_NAME = "clause_id";
    static final String CHANGES_PURCHASE_STATUS_COLUMN_NAME = "status";
    static final String CHANGES_PURCHASE_START_DATE_TIME_COLUMN_NAME = "start_date_time";

    static final String CHANGES_PURCHASE_FIRST_KEY_COLUMN = "change_id";

    /**
     * Locations Customer database table definition.
     */
    static final String LOCATIONS_CUSTOMER_TABLE_NAME = "locations_customer";

    static final String LOCATIONS_CUSTOMER_LOCATION_ID_COLUMN_NAME = "location_id";
    static final String LOCATIONS_CUSTOMER_LOCATION_COLUMN_NAME = "location";
    static final String LOCATIONS_CUSTOMER_URI_COLUMN_NAME = "uri";

    static final String LOCATIONS_CUSTOMER_FIRST_KEY_COLUMN = "location_id";

    /**
     * Bank Accounts Customer database table definition.
     */
    static final String BANK_ACCOUNTS_CUSTOMER_TABLE_NAME = "bank_accounts_customer";

    static final String BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_ID_COLUMN_NAME = "bank_accounts_id";
    static final String BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_COLUMN_NAME = "bank_accounts";
    static final String BANK_ACCOUNTS_CUSTOMER_BANK_ACCOUNTS_TYPE_COLUMN_NAME = "bank_accounts_type";

    static final String BANK_ACCOUNTS_CUSTOMER_FIRST_KEY_COLUMN = "bank_accounts_id";

}