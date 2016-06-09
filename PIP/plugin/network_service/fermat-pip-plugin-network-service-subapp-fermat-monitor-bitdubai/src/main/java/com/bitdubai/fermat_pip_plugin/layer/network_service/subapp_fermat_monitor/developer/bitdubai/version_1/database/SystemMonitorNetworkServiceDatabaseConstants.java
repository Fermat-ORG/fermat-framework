package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_pip_plugin.layer.network_service.system_monitor.developer.bitdubai.version_1.database.SystemMonitorNetworkServiceDeveloperDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Miguel Celedon - (miguelceledon@outlook.com) on 14/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SystemMonitorNetworkServiceDatabaseConstants {

    public static final String DATABASE_NAME = "System Monitor Database";

    /**
     * System Data database table definition.
     */
    public static final String SYSTEM_DATA_TABLE_NAME = "system_data";

    public static final String SYSTEM_DATA_SYSTEM_ID_COLUMN_NAME = "system_id";
    public static final String SYSTEM_DATA_NODE_TYPE_COLUMN_NAME = "node_type";
    public static final String SYSTEM_DATA_HARDWARE_COLUMN_NAME = "hardware";
    public static final String SYSTEM_DATA_OS_COLUMN_NAME = "os";

    public static final String SYSTEM_DATA_FIRST_KEY_COLUMN = "system_id";

    /**
     * Connections database table definition.
     */
    public static final String CONNECTIONS_TABLE_NAME = "connections";

    public static final String CONNECTIONS_CONNID_COLUMN_NAME = "connid";
    public static final String CONNECTIONS_PEERID_COLUMN_NAME = "peerid";
    public static final String CONNECTIONS_PEER_IPV4_COLUMN_NAME = "peer_ipv4";
    public static final String CONNECTIONS_PEER_IPV6_COLUMN_NAME = "peer_ipv6";
    public static final String CONNECTIONS_NETWORK_SERVICE_NAME_COLUMN_NAME = "network_service_name";

    public static final String CONNECTIONS_FIRST_KEY_COLUMN = "connid";

    /**
     * Services database table definition.
     */
    public static final String SERVICES_TABLE_NAME = "services";

    public static final String SERVICES_ID_COLUMN_NAME = "id";
    public static final String SERVICES_NAME_COLUMN_NAME = "name";
    public static final String SERVICES_TYPE_COLUMN_NAME = "type";
    public static final String SERVICES_SUBTYPE_COLUMN_NAME = "subtype";

    public static final String SERVICES_FIRST_KEY_COLUMN = "id";


    /**
     * Platform components database table definition.
     */
    public static final String PLATFORM_COMPONENTS_TABLE_NAME = "platform_component";

    public static final String COMPONENT_ID_COLUMN_NAME = "id";
    public static final String COMPONENT_NAME_COLUMN_NAME = "name";
    public static final String COMPONENT_TYPE_COLUMN_NAME = "type";

    public static final String COMPONENT_FIRST_KEY_COLUMN = "id";


}