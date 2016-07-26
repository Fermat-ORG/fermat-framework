package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkestpriest@gmail.com) on 29/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OpenContractBusinessTransactionDatabaseConstants {

    public static final String DATABASE_NAME = "open_contract_database";
    /**
     * Open Contract database table definition.
     */
    public static final String OPEN_CONTRACT_TABLE_NAME = "open_contract";

    public static final String OPEN_CONTRACT_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String OPEN_CONTRACT_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    public static final String OPEN_CONTRACT_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String OPEN_CONTRACT_CONTRACT_STATUS_COLUMN_NAME = "contract_status";
    public static final String OPEN_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME = "transaction_status";
    public static final String OPEN_CONTRACT_TRANSMISSION_STATUS_COLUMN_NAME = "transmission_status";
    public static final String OPEN_CONTRACT_CONTRACT_TYPE_COLUMN_NAME = "contract_type";
    public static final String OPEN_CONTRACT_CONTRACT_XML_COLUMN_NAME = "contract_xml";

    public static final String OPEN_CONTRACT_FIRST_KEY_COLUMN = "transaction_id";

    /**
     * Events recorded database table definition.
     */
    public static final String OPEN_CONTRACT_EVENTS_RECORDED_TABLE_NAME = "distribution_events_recorded";

    public static final String OPEN_CONTRACT_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String OPEN_CONTRACT_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String OPEN_CONTRACT_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String OPEN_CONTRACT_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String OPEN_CONTRACT_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String OPEN_CONTRACT_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = "event_id";

}
