/*
 * @#CommunicationsCloudServerP2PDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.database;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.database.CommunicationsCloudServerP2PDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationsCloudServerP2PDatabaseConstants {

    public static final String DATA_BASE_NAME  = "communication_server_data_base";


    /**
     * known servers catalog database table definition.
     */
    public static final String KNOWN_SERVERS_CATALOG_TABLE_NAME = "known_servers_catalog";

    public static final String KNOWN_SERVERS_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "identity_public_key";
    public static final String KNOWN_SERVERS_CATALOG_NAME_COLUMN_NAME = "name";
    public static final String KNOWN_SERVERS_CATALOG_IP_COLUMN_NAME = "ip";
    public static final String KNOWN_SERVERS_CATALOG_DEFAULT_PORT_COLUMN_NAME = "default_port";
    public static final String KNOWN_SERVERS_CATALOG_WEB_SERVICE_PORT_COLUMN_NAME = "web_service_port";
    public static final String KNOWN_SERVERS_CATALOG_LATITUDE_COLUMN_NAME = "latitude";
    public static final String KNOWN_SERVERS_CATALOG_LONGITUDE_COLUMN_NAME = "longitude";
    public static final String KNOWN_SERVERS_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME = "late_notification_counter";
    public static final String KNOWN_SERVERS_CATALOG_OFFLINE_COUNTER_COLUMN_NAME = "offline_counter";
    public static final String KNOWN_SERVERS_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME = "registered_timestamp";
    public static final String KNOWN_SERVERS_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME = "last_connection_timestamp";

    public static final String KNOWN_SERVERS_CATALOG_FIRST_KEY_COLUMN = "identity_public_key";

    /**
     * platform component profile registered history database table definition.
     */
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_TABLE_NAME = "platform_component_profile_registered_history";

    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "identity_public_key";
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NAME_COLUMN_NAME = "name";
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_ALIAS_COLUMN_NAME = "alias";
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_COMPONENT_TYPE_COLUMN_NAME = "component_type";
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME = "network_service_type";
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LATITUDE_COLUMN_NAME = "last_latitude";
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_LONGITUDE_COLUMN_NAME = "last_longitude";
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_EXTRA_DATA_COLUMN_NAME = "extra_data";
    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME = "last_connection_timestamp";

    public static final String PLATFORM_COMPONENT_PROFILE_REGISTERED_HISTORY_FIRST_KEY_COLUMN = "identity_public_key";

}
