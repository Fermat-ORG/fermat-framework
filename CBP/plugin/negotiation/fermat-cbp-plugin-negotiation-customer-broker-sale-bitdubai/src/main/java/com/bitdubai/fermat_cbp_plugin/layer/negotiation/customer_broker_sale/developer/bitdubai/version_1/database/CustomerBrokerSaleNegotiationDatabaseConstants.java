package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Jorge Gonzalez - (jorgeejgonzalez@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerSaleNegotiationDatabaseConstants {

    /**
     * Negotiations database table definition.
     */
    static final String NEGOTIATIONS_TABLE_NAME = "negotiations";

    static final String NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "crypto_customer_public_key";
    static final String NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME = "crypto_broker_public_key";
    static final String NEGOTIATIONS_START_DATETIME_COLUMN_NAME = "start_datetime";
    static final String NEGOTIATIONS_STATUS_COLUMN_NAME = "status";

    static final String NEGOTIATIONS_FIRST_KEY_COLUMN = "negotiation_id";

    /**
     * Clauses database table definition.
     */
    static final String CLAUSES_TABLE_NAME = "clauses";

    static final String CLAUSES_CLAUSE_ID_COLUMN_NAME = "clause_id";
    static final String CLAUSES_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String CLAUSES_TYPE_COLUMN_NAME = "type";
    static final String CLAUSES_VALUE_COLUMN_NAME = "value";
    static final String CLAUSES_STATUS_COLUMN_NAME = "status";
    static final String CLAUSES_PROPOSED_BY_COLUMN_NAME = "proposed_by";
    static final String CLAUSES_INDEX_ORDER_COLUMN_NAME = "index_order";

    static final String CLAUSES_FIRST_KEY_COLUMN = "clause_id";

    /**
     * Clause Status Log database table definition.
     */
    static final String CLAUSE_STATUS_LOG_TABLE_NAME = "clause_status_log";

    static final String CLAUSE_STATUS_LOG_CHANGE_ID_COLUMN_NAME = "change_id";
    static final String CLAUSE_STATUS_LOG_CLAUSE_ID_COLUMN_NAME = "clause_id";
    static final String CLAUSE_STATUS_LOG_STATUS_COLUMN_NAME = "status";
    static final String CLAUSE_STATUS_LOG_CHANGE_DATETIME_COLUMN_NAME = "change_datetime";

    static final String CLAUSE_STATUS_LOG_FIRST_KEY_COLUMN = "change_id";

}