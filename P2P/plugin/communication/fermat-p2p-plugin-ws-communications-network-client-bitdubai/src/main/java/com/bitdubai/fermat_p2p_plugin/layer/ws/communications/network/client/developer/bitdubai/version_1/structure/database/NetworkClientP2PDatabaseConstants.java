/*
* @#NetworkClientP2PDatabaseConstants.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.database;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.p2p.network_client.developer.bitdubai.version_1.structure.NetworkClientP2PDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientP2PDatabaseConstants {

    public static final String DATA_BASE_NAME  = "network_client_data_base";

    /**
     * Node Connection history database table definition.
     */
    public static final String NODE_CONNECTION_HISTORY_TABLE_NAME = "node_connection_history";

    public  static final String NODE_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "identity_public_key";
    public  static final String NODE_CONNECTION_HISTORY_IP_COLUMN_NAME = "ip";
    public static final String NODE_CONNECTION_HISTORY_DEFAULT_PORT_COLUMN_NAME = "default_port";
    public static final String NODE_CONNECTION_HISTORY_LATITUDE_COLUMN_NAME = "latitude";
    public static final String NODE_CONNECTION_HISTORY_LONGITUDE_COLUMN_NAME = "longitude";
    public static final String NODE_CONNECTION_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME = "last_connection_timestamp";

    public static final String NODE_CONNECTION_HISTORY_FIRST_KEY_COLUMN = "identity_public_key";

    /**
     * Client Connection history database table definition.
     */
    public static final String CLIENT_CONNECTION_HISTORY_TABLE_NAME = "client_connection_history";

    public static final String CLIENT_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "identity_public_key";
    public static final String CLIENT_CONNECTION_HISTORY_NAME_COLUMN_NAME = "name";
    public static final String CLIENT_CONNECTION_HISTORY_ALIAS_COLUMN_NAME = "alias";
    public static final String CLIENT_CONNECTION_HISTORY_COMPONENT_TYPE_COLUMN_NAME = "component_type";
    public static final String CLIENT_CONNECTION_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME = "network_service_type";
    public static final String CLIENT_CONNECTION_HISTORY_LAST_LATITUDE_COLUMN_NAME = "last_latitude";
    public static final String CLIENT_CONNECTION_HISTORY_LAST_LONGITUDE_COLUMN_NAME = "last_longitude";
    public static final String CLIENT_CONNECTION_HISTORY_EXTRA_DATA_COLUMN_NAME = "extra_data";
    public static final String CLIENT_CONNECTION_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME = "last_connection_timestamp";

    public static final String CLIENT_CONNECTION_HISTORY_FIRST_KEY_COLUMN = "identity_public_key";

}
