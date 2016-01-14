/*
 * @#VpnShareMemoryCache.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.VpnClientConnection;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

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
public class VpnShareMemoryCache {

    /**
     * Represent the logger instance
     */
    private static Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(VpnShareMemoryCache.class));

    /**
     * Represent the singleton instance
     */
    private static VpnShareMemoryCache instance = new VpnShareMemoryCache();

    /**
     * Holds all applicant connection
     */
    private  Map<NetworkServiceType, Map<String, Map<String, VpnClientConnection>>> mapByNetworkServicesMapForApplicant;
    
    /**
     * Holds all remotes connection
     */
    private  Map<NetworkServiceType, Map<String, Map<String, VpnClientConnection>>> mapByNetworkServicesMapFoRemotes;

    /**
     * Constructor
     */
    private VpnShareMemoryCache(){
        super();
        this.mapByNetworkServicesMapForApplicant = Collections.synchronizedMap(new HashMap<NetworkServiceType, Map<String, Map<String, VpnClientConnection>>>());
        this.mapByNetworkServicesMapFoRemotes = Collections.synchronizedMap(new HashMap<NetworkServiceType, Map<String, Map<String, VpnClientConnection>>>());
    }

    /**
     * Get the session client
     *
     * @param networkServiceType
     * @param applicantIdentityPublicKey to identify
     * @return the VpnClientConnection instance
     */
    private static VpnClientConnection getApplicant(NetworkServiceType networkServiceType, String applicantIdentityPublicKey, String remoteIdentityPublicKey){

        LOG.info("calling getApplicant");

        if(instance.mapByNetworkServicesMapForApplicant.containsKey(networkServiceType)){
            if (instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).containsKey(applicantIdentityPublicKey)){
                return instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).get(applicantIdentityPublicKey).get(remoteIdentityPublicKey);
            }
        }

        return null;
    }

    /**
     * Get the session client
     *
     * @param networkServiceType
     * @param applicantIdentityPublicKey to identify
     * @return the VpnClientConnection instance
     */
    private static VpnClientConnection getRemote(NetworkServiceType networkServiceType, String remoteIdentityPublicKey, String applicantIdentityPublicKey){

        LOG.info("calling getRemote");

        if(instance.mapByNetworkServicesMapFoRemotes.containsKey(networkServiceType)){
            if (instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).containsKey(remoteIdentityPublicKey)){
                return instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).get(remoteIdentityPublicKey).get(applicantIdentityPublicKey);
            }
        }

        return null;
    }

    /**
     * Add a new vpn client connection
     * @param connection
     */
    public static void add(VpnClientConnection connection){
        if (connection.isApplicant()){
            addApplicant(connection.getNetworkServiceType(), connection.getParticipant().getIdentityPublicKey(), connection.getRemoteParticipantIdentity(), connection);
        }else {
            addRemote(connection.getNetworkServiceType(), connection.getParticipant().getIdentityPublicKey(), connection.getRemoteParticipantIdentity(), connection);
        }
    }

    /**
     * Add a new session to the applicant memory cache
     *
     * @param networkServiceType
     * @param applicantIdentityPublicKey the identify
     * @param connection to storage
     */
    private static void addApplicant(NetworkServiceType networkServiceType, String applicantIdentityPublicKey, String remoteIdentityPublicKey, VpnClientConnection connection){

        LOG.info("calling addApplicant");
        
        if(instance.mapByNetworkServicesMapForApplicant.containsKey(networkServiceType)){

            if (instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).containsKey(applicantIdentityPublicKey)){

                instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).get(applicantIdentityPublicKey).put(remoteIdentityPublicKey, connection);

            }else {

                Map<String, VpnClientConnection> mapConnectionsByProfile = Collections.synchronizedMap(new HashMap<String, VpnClientConnection>());
                mapConnectionsByProfile.put(remoteIdentityPublicKey, connection);
                instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).put(applicantIdentityPublicKey, mapConnectionsByProfile);
            }

        }else{

            Map<String, VpnClientConnection> mapConnectionsByProfile = Collections.synchronizedMap(new HashMap<String, VpnClientConnection>());
            mapConnectionsByProfile.put(remoteIdentityPublicKey, connection);

            Map<String, Map<String, VpnClientConnection>> mapByNetworkService = Collections.synchronizedMap(new HashMap<String, Map<String, VpnClientConnection>>());
            mapByNetworkService.put(applicantIdentityPublicKey, mapConnectionsByProfile);

            instance.mapByNetworkServicesMapForApplicant.put(networkServiceType, mapByNetworkService);
        }

    }


    /**
     * Add a new session to the remote memory cache
     *
     * @param networkServiceType
     * @param remoteIdentityPublicKey the identify
     * @param connection to storage
     */
    private static void addRemote(NetworkServiceType networkServiceType, String remoteIdentityPublicKey, String applicantIdentityPublicKey, VpnClientConnection connection){

        LOG.info("calling addRemote");

        if(instance.mapByNetworkServicesMapFoRemotes.containsKey(networkServiceType)){

            if (instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).containsKey(remoteIdentityPublicKey)){

                instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).get(remoteIdentityPublicKey).put(applicantIdentityPublicKey, connection);

            }else {

                Map<String, VpnClientConnection> mapConnectionsByProfile = Collections.synchronizedMap(new HashMap<String, VpnClientConnection>());
                mapConnectionsByProfile.put(applicantIdentityPublicKey, connection);
                instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).put(remoteIdentityPublicKey, mapConnectionsByProfile);
            }

        }else{

            Map<String, VpnClientConnection> mapConnectionsByProfile = Collections.synchronizedMap(new HashMap<String, VpnClientConnection>());
            mapConnectionsByProfile.put(applicantIdentityPublicKey, connection);

            Map<String, Map<String, VpnClientConnection>> mapByNetworkService = Collections.synchronizedMap(new HashMap<String, Map<String, VpnClientConnection>>());
            mapByNetworkService.put(remoteIdentityPublicKey, mapConnectionsByProfile);

            instance.mapByNetworkServicesMapFoRemotes.put(networkServiceType, mapByNetworkService);
        }

    }

    /**
     * Remove the vpn client connection
     *
     * @param connection
     * @return the VpnClientConnection remove
     */
    public static VpnClientConnection remove(VpnClientConnection connection){

        if(connection.isApplicant()){
           return removeApplicant(connection.getNetworkServiceType(), connection.getParticipant().getIdentityPublicKey(), connection.getRemoteParticipantIdentity());
        }else {
            return removeRemote(connection.getNetworkServiceType(), connection.getParticipant().getIdentityPublicKey(), connection.getRemoteParticipantIdentity());
        }

    }

    /**
     * Remove the applicant vpn client connection
     *
     * @param networkServiceType
     * @param applicantIdentityPublicKey
     * @param remoteIdentityPublicKey
     * @return VpnClientConnection
     */
    private static VpnClientConnection removeApplicant(NetworkServiceType networkServiceType, String applicantIdentityPublicKey, String remoteIdentityPublicKey){

        LOG.info("calling removeRemote");

        if(instance.mapByNetworkServicesMapFoRemotes.containsKey(networkServiceType)){
            if (instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).containsKey(applicantIdentityPublicKey)){
                return instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).get(applicantIdentityPublicKey).remove(remoteIdentityPublicKey);
            }
        }

        return null;
    }

    /**
     * Remove the remote vpn client connection
     *
     * @param networkServiceType
     * @param remoteIdentityPublicKey
     * @param applicantIdentityPublicKey
     * @return VpnClientConnection
     */
    private static VpnClientConnection removeRemote(NetworkServiceType networkServiceType, String remoteIdentityPublicKey, String applicantIdentityPublicKey){

        LOG.info("calling removeApplicant");

        if(instance.mapByNetworkServicesMapForApplicant.containsKey(networkServiceType)){
            if (instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).containsKey(remoteIdentityPublicKey)){
               return instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).get(remoteIdentityPublicKey).remove(applicantIdentityPublicKey);
            }
        }

        return null;
    }




    /**
     * Verify is exist a vpn client connection
     *
     * @param connection that identify
     * @return (TRUE or FALSE)
     */
    public static boolean isConnected(VpnClientConnection connection){

        if(connection.isApplicant()){
            return isConnectedApplicant(connection.getNetworkServiceType(), connection.getParticipant().getIdentityPublicKey(), connection.getRemoteParticipantIdentity());
        }else {
            return isConnectedRemote(connection.getNetworkServiceType(), connection.getParticipant().getIdentityPublicKey(), connection.getRemoteParticipantIdentity());
        }

    }

    /**
     * Verify is exist a applicant vpn client connection
     *
     * @param networkServiceType
     * @param applicantIdentityPublicKey
     * @param remoteIdentityPublicKey
     * @return boolean
     */
    private static boolean isConnectedApplicant(NetworkServiceType networkServiceType, String applicantIdentityPublicKey, String remoteIdentityPublicKey){

        LOG.info("calling isConnectedApplicant");

        if(instance.mapByNetworkServicesMapForApplicant.containsKey(networkServiceType)){
            if (instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).containsKey(applicantIdentityPublicKey)){
                return instance.mapByNetworkServicesMapForApplicant.get(networkServiceType).get(applicantIdentityPublicKey).containsKey(remoteIdentityPublicKey);
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Verify is exist  a remote vpn client connection
     *
     * @param networkServiceType
     * @param remoteIdentityPublicKey
     * @param applicantIdentityPublicKey
     * @return boolean
     */
    private static boolean isConnectedRemote(NetworkServiceType networkServiceType, String remoteIdentityPublicKey, String applicantIdentityPublicKey){

        LOG.info("calling isConnectedRemote");

        if(instance.mapByNetworkServicesMapFoRemotes.containsKey(networkServiceType)){
            if (instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).containsKey(remoteIdentityPublicKey)){
                return instance.mapByNetworkServicesMapFoRemotes.get(networkServiceType).get(remoteIdentityPublicKey).containsKey(applicantIdentityPublicKey);
            }
        }
        return Boolean.FALSE;
    }


    /**
     * Return the remote connection for a connection of the vpn
     * @param vpnClientConnection
     * @return VpnClientConnection
     */
    public static VpnClientConnection getMyRemote(VpnClientConnection vpnClientConnection){

        if (vpnClientConnection.isApplicant()){
           return getRemote(vpnClientConnection.getNetworkServiceType(), vpnClientConnection.getRemoteParticipantIdentity(), vpnClientConnection.getParticipant().getIdentityPublicKey());
        }else {
           return getApplicant(vpnClientConnection.getNetworkServiceType(), vpnClientConnection.getRemoteParticipantIdentity(), vpnClientConnection.getParticipant().getIdentityPublicKey());
        }

    }

    /**
     * Validate is the remote connection for a connection of the vpn is connected
     * @param vpnClientConnection
     * @return boolean
     */
    public static boolean myRemoteIsConnected(VpnClientConnection vpnClientConnection){

        if(vpnClientConnection.isApplicant()){
            return isConnectedRemote(vpnClientConnection.getNetworkServiceType(), vpnClientConnection.getRemoteParticipantIdentity(), vpnClientConnection.getParticipant().getIdentityPublicKey());
        } else {
            return isConnectedApplicant(vpnClientConnection.getNetworkServiceType(), vpnClientConnection.getRemoteParticipantIdentity(), vpnClientConnection.getParticipant().getIdentityPublicKey());
        }
    }
}
