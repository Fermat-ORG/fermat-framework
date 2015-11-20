/*
 * @#TemplateNetworkServiceDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.database.communications;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Roberto Requena - (rart3001@gn¡mail.com) on 15/10/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME  = "communication_network_service_data_base";

    /**
     * incoming messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME = "incoming_messages";

    public static final String INCOMING_MESSAGES_ID_COLUMN_NAME = "incoming_id";
    public static final String INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String INCOMING_MESSAGES_TYPE_COLUMN_NAME = "type";
    public static final String INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String INCOMING_MESSAGES_STATUS_COLUMN_NAME = "status";

    public static final String INCOMING_MESSAGES_FIRST_KEY_COLUMN = INCOMING_MESSAGES_ID_COLUMN_NAME;

    /**
     * outgoing messages database table definition.
     */
    public static final String OUTGOING_MESSAGES_TABLE_NAME = "outgoing_messages";

    public static final String OUTGOING_MESSAGES_ID_COLUMN_NAME = "outgoing_id";
    public static final String OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String OUTGOING_MESSAGES_TYPE_COLUMN_NAME = "type";
    public static final String OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String OUTGOING_MESSAGES_STATUS_COLUMN_NAME = "status";

    public static final String OUTGOING_MESSAGES_FIRST_KEY_COLUMN = OUTGOING_MESSAGES_ID_COLUMN_NAME;


    /**
     * digital_asset_metadata_transaction database table definition.
     */
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_TABLE_NAME = "digital_asset_metadata_transaction";

    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_GENESIS_TRANSACTION_COLUMN_NAME = "genesis_transaction";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_SENDER_TYPE_COLUMN_NAME = "sender_type";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_RECEIVER_TYPE_COLUMN_NAME = "receiver_type";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_META_DATA_XML_COLUMN_NAME = "meta_data_xml";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_TYPE_COLUMN_NAME = "type";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_DISTRIBUTION_STATUS_COLUMN_NAME = "distribution_status";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_TIMESTAMP_COLUMN_NAME = "timestamp";
    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_PROCESSED_COLUMN_NAME = "processed";

    public static final String DIGITAL_ASSET_METADATA_TRANSACTION_FIRST_KEY_COLUMN = "transaction_id";

}
