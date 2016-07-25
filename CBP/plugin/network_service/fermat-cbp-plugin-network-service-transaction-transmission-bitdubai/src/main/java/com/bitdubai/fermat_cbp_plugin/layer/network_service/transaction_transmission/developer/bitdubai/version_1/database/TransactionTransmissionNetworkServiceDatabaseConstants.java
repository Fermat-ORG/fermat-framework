package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.TransactionTransmissionNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * Created by ghost on 02/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class TransactionTransmissionNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME = "transaction_transmission_database";

    /**
     * Transaction Transmission Has database table definition.
     */
    public static final String TRANSACTION_TRANSMISSION_HASH_TABLE_NAME = "transaction_transmission_hash";

    public static final String TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME = "transmission_id";
    public static final String TRANSACTION_TRANSMISSION_HASH_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME = "contract_status";
    public static final String TRANSACTION_TRANSMISSION_HASH_SENDER_PUBLIC_KEY_COLUMN_NAME = "sender_public_key";
    public static final String TRANSACTION_TRANSMISSION_HASH_SENDER_TYPE_COLUMN_NAME = "sender_type";
    public static final String TRANSACTION_TRANSMISSION_HASH_RECEIVER_PUBLIC_KEY_COLUMN_NAME = "receiver_public_key";
    public static final String TRANSACTION_TRANSMISSION_HASH_RECEIVER_TYPE_COLUMN_NAME = "receiver_type";
    public static final String TRANSACTION_TRANSMISSION_HASH_CONTRACT_ID_COLUMN_NAME = "contract_id";
    public static final String TRANSACTION_TRANSMISSION_HASH_NEGOTIATION_ID_COLUMN_NAME = "negotiation_id";
    public static final String TRANSACTION_TRANSMISSION_HASH_TRANSACTION_TYPE_COLUMN_NAME = "transaction_type";
    public static final String TRANSACTION_TRANSMISSION_HASH_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String TRANSACTION_TRANSMISSION_HASH_STATE_COLUMN_NAME = "state";
    public static final String TRANSACTION_TRANSMISSION_HASH_PENDING_FLAG_COLUMN_NAME = "pending_flag";
    public static final String TRANSACTION_TRANSMISSION_HASH_REMOTE_BUSINESS_TRANSACTION = "remote_business_tx";

    public static final String TRANSACTION_TRANSMISSION_HASH_FIRST_KEY_COLUMN = "transmission_id";

    /**
     * COMPONENT VERSIONS DETAILS database table definition.
     */
    public static final String COMPONENT_VERSIONS_DETAILS_TABLE_NAME = "component_versions_details";

    public static final String COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME = "id";
    public static final String COMPONENT_VERSIONS_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    public static final String COMPONENT_VERSIONS_DETAILS_IPK_COLUMN_NAME = "ipk";
    public static final String COMPONENT_VERSIONS_DETAILS_LAST_CONNECTION_COLUMN_NAME = "last_connection";

    public static final String COMPONENT_VERSIONS_DETAILS_FIRST_KEY_COLUMN = "id";

}
