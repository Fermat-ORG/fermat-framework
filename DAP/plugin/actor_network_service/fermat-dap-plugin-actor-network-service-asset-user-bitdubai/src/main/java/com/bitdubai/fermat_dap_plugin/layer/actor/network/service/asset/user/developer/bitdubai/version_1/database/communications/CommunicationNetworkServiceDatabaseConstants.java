/*
 * @#TemplateNetworkServiceDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME  = "ActorNetworkServiceAssetUser";

    /**
     * incoming messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME = "incoming_messages";

    public static final String INCOMING_MESSAGES_ID_COLUMN_NAME = "id";
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

    public static final String OUTGOING_MESSAGES_ID_COLUMN_NAME = "id";
    public static final String OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String OUTGOING_MESSAGES_TYPE_COLUMN_NAME = "type";
    public static final String OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String OUTGOING_MESSAGES_STATUS_COLUMN_NAME = "status";

    public static final String OUTGOING_MESSAGES_FIRST_KEY_COLUMN = OUTGOING_MESSAGES_ID_COLUMN_NAME;

}
