package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerSaleNegotiationDatabaseConstants {

    /**
     * Negotiations Sale database table definition.
     */
    static final String NEGOTIATIONS_SALE_TABLE_NAME = "negotiations_sale";

    static final String NEGOTIATIONS_SALE_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String NEGOTIATIONS_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "crypto_customer_public_key";
    static final String NEGOTIATIONS_SALE_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME = "crypto_broker_public_key";
    static final String NEGOTIATIONS_SALE_START_DATE_TIME_COLUMN_NAME = "start_date_time";
    static final String NEGOTIATIONS_SALE_STATUS_COLUMN_NAME = "status";

    static final String NEGOTIATIONS_SALE_FIRST_KEY_COLUMN = "negotiation_id";

    /**
     * Clauses Sale database table definition.
     */
    static final String CLAUSES_SALE_TABLE_NAME = "clauses_sale";

    static final String CLAUSES_SALE_CLAUSE_ID_COLUMN_NAME = "clause_id";
    static final String CLAUSES_SALE_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String CLAUSES_SALE_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "crypto_customer_public_key";
    static final String CLAUSES_SALE_TYPE_COLUMN_NAME = "type";
    static final String CLAUSES_SALE_VALUE_COLUMN_NAME = "value";
    static final String CLAUSES_SALE_STATUS_COLUMN_NAME = "status";
    static final String CLAUSES_SALE_PROPOSED_BY_COLUMN_NAME = "proposed_by";
    static final String CLAUSES_SALE_INDEX_ORDER_COLUMN_NAME = "index_order";

    static final String CLAUSES_SALE_FIRST_KEY_COLUMN = "clause_id";

    /**
     * Changes Sale database table definition.
     */
    static final String CHANGES_SALE_TABLE_NAME = "changes_sale";

    static final String CHANGES_SALE_CHANGE_ID_COLUMN_NAME = "change_id";
    static final String CHANGES_SALE_CLAUSE_ID_COLUMN_NAME = "clause_id";
    static final String CHANGES_SALE_STATUS_COLUMN_NAME = "status";
    static final String CHANGES_SALE_START_DATE_TIME_COLUMN_NAME = "start_date_time";

    static final String CHANGES_SALE_FIRST_KEY_COLUMN = "clause_id";

}