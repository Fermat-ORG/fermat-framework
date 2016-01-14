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
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.VpnShareMemoryCache</code>
 * is responsible the holds a cache for the vpns<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class VpnShareMemoryCacheOld {

    /**
     * Represent the singleton instance
     */
    private static VpnShareMemoryCacheOld instance = new VpnShareMemoryCacheOld();

    /**
     * Holds all properties
     */
    private  Map<NetworkServiceType, Map<String, VpnClientConnection>>   connectionMap;

    /**
     * Constructor
     */
    private VpnShareMemoryCacheOld(){
        super();
        this.connectionMap = Collections.synchronizedMap(new HashMap<NetworkServiceType, Map<String, VpnClientConnection>>());
    }

    /**
     * Get the session client
     *
     * @param networkServiceType
     * @param profileIdentityPublicKey to identify
     * @return the VpnClientConnection instance
     */
    public static VpnClientConnection get(NetworkServiceType networkServiceType, String profileIdentityPublicKey){

        if(instance.connectionMap.containsKey(networkServiceType)){
            return instance.connectionMap.get(networkServiceType).get(profileIdentityPublicKey);
        }

        return null;
    }

    /**
     * Add a new session to the memory cache
     *
     * @param networkServiceType
     * @param profileIdentityPublicKey the identify
     * @param connection to storage
     */
    public static void add(NetworkServiceType networkServiceType, String profileIdentityPublicKey, VpnClientConnection connection){

        if(instance.connectionMap.containsKey(networkServiceType)){
            instance.connectionMap.get(networkServiceType).put(profileIdentityPublicKey, connection);
        }else{
            Map<String, VpnClientConnection> connections = Collections.synchronizedMap(new HashMap<String, VpnClientConnection>());
            connections.put(profileIdentityPublicKey, connection);
            instance.connectionMap.put(networkServiceType, connections);
        }

    }

    /**
     * Remove the session client
     *
     * @param networkServiceType
     * @param profileIdentityPublicKey that identify
     * @return the VpnClientConnection remove
     */
    public static VpnClientConnection remove(NetworkServiceType networkServiceType, String profileIdentityPublicKey){

        if(instance.connectionMap.containsKey(networkServiceType)){
            return instance.connectionMap.get(networkServiceType).remove(profileIdentityPublicKey);
        }

        return null;
    }

    /**
     * Verify is exist a session for a client
     *
     * @param profileIdentityPublicKey that identify
     * @return (TRUE or FALSE)
     */
    public static boolean isConnected(NetworkServiceType networkServiceType, String profileIdentityPublicKey){

        if(instance.connectionMap.containsKey(networkServiceType)){
            return instance.connectionMap.get(networkServiceType).containsKey(profileIdentityPublicKey);
        }

        return Boolean.FALSE;
    }
}
