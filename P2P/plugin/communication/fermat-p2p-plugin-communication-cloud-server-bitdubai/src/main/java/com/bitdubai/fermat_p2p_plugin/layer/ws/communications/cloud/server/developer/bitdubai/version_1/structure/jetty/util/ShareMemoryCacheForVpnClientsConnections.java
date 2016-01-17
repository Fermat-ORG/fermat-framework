/*
 * @#VpnShareMemoryCache.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.VpnClientConnection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.VpnShareMemoryCache_</code>
 * is responsible the holds a cache for the vpn clients connections<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ShareMemoryCacheForVpnClientsConnections {

    /**
     * Represent the singleton instance
     */
    private static ShareMemoryCacheForVpnClientsConnections instance = new ShareMemoryCacheForVpnClientsConnections();

    /**
     * Holds all properties
     */
    private  Map<NetworkServiceType, Map<String, VpnClientConnection>>   connectionMap;

    /**
     * Constructor
     */
    private ShareMemoryCacheForVpnClientsConnections(){
        super();
        this.connectionMap = Collections.synchronizedMap(new HashMap<NetworkServiceType, Map<String, VpnClientConnection>>());
    }

    /**
     * Get the vpn client connection
     *
     * @param networkServiceType
     * @param key to identify
     * @return the VpnClientConnection instance
     */
    public static VpnClientConnection get(NetworkServiceType networkServiceType, String key){

        if(instance.connectionMap.containsKey(networkServiceType)){
            return instance.connectionMap.get(networkServiceType).get(key);
        }

        return null;
    }

    /**
     *  Add a new vpn client connection to the memory cache
     * @param vpnClientConnection
     */
    public static void add(VpnClientConnection vpnClientConnection){

        if(instance.connectionMap.containsKey(vpnClientConnection.getNetworkServiceType())){
            instance.connectionMap.get(vpnClientConnection.getNetworkServiceType()).put(vpnClientConnection.getMyKey(), vpnClientConnection);
        }else{
            Map<String, VpnClientConnection> connections = Collections.synchronizedMap(new HashMap<String, VpnClientConnection>());
            connections.put(vpnClientConnection.getMyKey(), vpnClientConnection);
            instance.connectionMap.put(vpnClientConnection.getNetworkServiceType(), connections);
        }

    }

    /**
     * Remove the vpn client connection
     *
     * @param networkServiceType
     * @param key that identify
     * @return the VpnClientConnection remove
     */
    public static VpnClientConnection remove(NetworkServiceType networkServiceType, String key){

        if(instance.connectionMap.containsKey(networkServiceType)){
            return instance.connectionMap.get(networkServiceType).remove(key);
        }

        return null;
    }

    /**
     * Verify is exist a vpn client connection
     * @param networkServiceType
     * @param key
     * @return boolean
     */
    public static boolean isConnected(NetworkServiceType networkServiceType, String key){

        if(instance.connectionMap.containsKey(networkServiceType)){
            return instance.connectionMap.get(networkServiceType).containsKey(key);
        }

        return Boolean.FALSE;
    }

    /**
     * Get the vpn client connection remote
     * @param vpnClientConnection
     * @return VpnClientConnection
     */
    public static VpnClientConnection getMyRemote(VpnClientConnection vpnClientConnection){

        if(instance.connectionMap.containsKey(vpnClientConnection.getNetworkServiceType())){
            return instance.connectionMap.get(vpnClientConnection.getNetworkServiceType()).get(vpnClientConnection.getKeyForMyRemote());
        }

        return null;
    }
}
