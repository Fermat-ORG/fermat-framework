package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseContractDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 23/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerPurchaseContractDatabaseConstants {

    public static final String DATABASE_NAME = "contract_customer_broker_purchase";
    /**
     * Contracts Purchase database table definition.
     */
    static final String CONTRACTS_PURCHASE_TABLE_NAME = "contracts_purchase";

    static final String CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME = "contract_id";
    static final String CONTRACTS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    static final String CONTRACTS_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "customer_public_key";
    static final String CONTRACTS_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME = "date_time";
    static final String CONTRACTS_PURCHASE_STATUS_COLUMN_NAME = "status";
    static final String CONTRACTS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME = "near_expiration_date_time";
    static final String CONTRACTS_PURCHASE_CANCEL_REASON_COLUMN_NAME = "cancel_reason";

    static final String CONTRACTS_PURCHASE_FIRST_KEY_COLUMN = "contract_id";

    /**
     * Clause Contract database table definition.
     */
    static final String CLAUSE_CONTRACT_TABLE_NAME = "clause_contract";

    static final String CLAUSE_CONTRACT_CLAUSE_ID_COLUMN_NAME = "clause_id";
    static final String CLAUSE_CONTRACT_CONTRACT_ID_COLUMN_NAME = "contract_id";
    static final String CLAUSE_CONTRACT_TYPE_COLUMN_NAME = "type";
    static final String CLAUSE_CONTRACT_EXECUTION_ORDER_COLUMN_NAME = "execution_order";
    static final String CLAUSE_CONTRACT_CURRENT_STATUS_COLUMN_NAME = "current_status";

    static final String CLAUSE_CONTRACT_FIRST_KEY_COLUMN = "clause_id";

    /**
     * Clause Status Log database table definition.
     */
    static final String CLAUSE_STATUS_LOG_TABLE_NAME = "clause_status_log";

    static final String CLAUSE_STATUS_LOG_LOG_ID_COLUMN_NAME = "log_id";
    static final String CLAUSE_STATUS_LOG_CLAUSE_ID_COLUMN_NAME = "clause_id";
    static final String CLAUSE_STATUS_LOG_STATUS_COLUMN_NAME = "status";
    static final String CLAUSE_STATUS_LOG_DATE_TIME_COLUMN_NAME = "date_time";
    static final String CLAUSE_STATUS_LOG_CHANGE_BY_COLUMN_NAME = "change_by";

    static final String CLAUSE_STATUS_LOG_FIRST_KEY_COLUMN = "log_id";

}