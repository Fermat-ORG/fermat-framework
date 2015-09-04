/*
 * @#NetworkIntraUserDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserNetworkServiceDatabaseConstants {

    /**
     * Represent the name of the data base
     */
    public static final String DATA_BASE_NAME  = "intra_user_network_service_data_base";

    /**
     * Incoming messages database table definition.
     */
    public static final String INCOMING_MESSAGES_TABLE_NAME = "incoming_messages";
    public static final String INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME = "id";
    public static final String INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME = "type";
    public static final String INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME = "status";

    /**
     * Outgoing messages database table definition.
     */
    public static final String OUTGOING_MESSAGES_TABLE_NAME = "outgoing_messages";
    public  static final String OUTGOING_MESSAGES_TABLE_ID_COLUMN_NAME = "id";
    public static final String OUTGOING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME = "sender_id";
    public static final String OUTGOING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME = "receiver_id";
    public static final String OUTGOING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME = "text_content";
    public static final String OUTGOING_MESSAGES_TABLE_TYPE_COLUMN_NAME = "type";
    public static final String OUTGOING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME = "shipping_timestamp";
    public static final String OUTGOING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME = "delivery_timestamp";
    public static final String OUTGOING_MESSAGES_TABLE_STATUS_COLUMN_NAME = "status";


    /**
     *  Intra User NetWork Services Cache database table definition.
     */
    public static final String INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_NAME = "intra_user_network_service_cache";
    public static final String INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_ID_COLUMN_NAME = "id";
    public static final String INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_USER_NAME_COLUMN_NAME = "intra_user_alias";
    //public static final String INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_PROFILE_PICTURE_COLUMN_NAME = "profile_picture";
    public static final String INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_PUBLIC_KEY_COLUMN_NAME = "intra_user_public_key";
    public static final String INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_INTRA_USER_LOGGED_IN_PUBLIC_KEY_COLUMN_NAME = "intra_user_logged_in_public_key";
    public static final String INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_CREATED_TIME_COLUMN_NAME = "created_time";
    public static final String INTRA_USER_NETWORK_SERVICE_CACHE_TABLE_DESCRIPTOR_COLUMN_NAME = "request_descriptor";


}
