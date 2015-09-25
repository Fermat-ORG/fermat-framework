/*
 * @#WsCommunicationVPNClientManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.vpn;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.AttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.google.gson.JsonObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.vpn.WsCommunicationVPNClientManagerAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationVPNClientManagerAgent extends Thread{

    /**
     * Represent the SLEEP_TIME
     */
    private static long SLEEP_TIME = 60000;

    /**
     * Represent the requestedVpnConnections
     */
    private Map<NetworkServiceType, Map<String, PlatformComponentProfile>> requestedVpnConnections;

    /**
     * Represent the vpnClientActiveCache;
     */
    private Map<NetworkServiceType, Map<String, WsCommunicationVPNClient>> vpnClientActiveCache;

    /**
     * Represent the isRunning
     */
    private boolean isRunning;

    /**
     * Constructor
     */
    public WsCommunicationVPNClientManagerAgent(){
       this.requestedVpnConnections = new ConcurrentHashMap<>();
       this.vpnClientActiveCache = new ConcurrentHashMap<>();
       this.isRunning = Boolean.FALSE;
    }

    /**
     * Create a new WsCommunicationVPNClient
     *
     * @param serverURI
     * @param networkServiceType
     * @param vpnServerIdentity
     * @param remotePlatformComponentProfileIdentity
     */
    public void createNewWsCommunicationVPNServer(URI serverURI, NetworkServiceType networkServiceType, String vpnServerIdentity, String remotePlatformComponentProfileIdentity) {

        /*
         * Create the identity
         */
        ECCKeyPair vpnClientIdentity = new ECCKeyPair();

        /*
         * Create a new headers
         */
        Map<String, String> headers = new HashMap<>();

        /*
         * Get json representation
         */
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AttNamesConstants.JSON_ATT_NAME_REGISTER_PARTICIPANT_IDENTITY_VPN, requestedVpnConnections.get(networkServiceType).get(remotePlatformComponentProfileIdentity).getIdentityPublicKey());
        jsonObject.addProperty(AttNamesConstants.JSON_ATT_NAME_CLIENT_IDENTITY_VPN, vpnClientIdentity.getPublicKey());

        /*
         * Add the att to the header
         */
        headers.put(AttNamesConstants.HEADER_ATT_NAME_TI,  AsymmectricCryptography.encryptMessagePublicKey(jsonObject.toString(), vpnServerIdentity));

        /*
         * Construct the vpn client
         */
        WsCommunicationVPNClient vpnClient = new WsCommunicationVPNClient(vpnClientIdentity, serverURI, requestedVpnConnections.get(networkServiceType).get(remotePlatformComponentProfileIdentity), vpnServerIdentity, headers);

        /*
         * Add to the vpn client active
         */
        if (vpnClientActiveCache.containsKey(networkServiceType)){

            vpnClientActiveCache.get(networkServiceType).put(remotePlatformComponentProfileIdentity, vpnClient);

        }else {

            Map<String, WsCommunicationVPNClient> newMap = new HashMap<>();
            newMap.put(remotePlatformComponentProfileIdentity, vpnClient);
            vpnClientActiveCache.put(networkServiceType, newMap);
        }

        /*
         * Construct the connect
         */
        vpnClient.connect();
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        try {

            //While is running
            while (isRunning){

                System.out.println(" WsCommunicationVPNClientManagerAgent - vpnClientActiveCache.size() "+vpnClientActiveCache.size());

                //If empty
                if (vpnClientActiveCache.isEmpty()){
                    //Auto stop
                    isRunning = Boolean.FALSE;
                }

                for (NetworkServiceType networkServiceType : vpnClientActiveCache.keySet()) {

                    for (String remote : vpnClientActiveCache.get(networkServiceType).keySet()) {

                        System.out.println(" WsCommunicationVPNClientManagerAgent - networkServiceType.size() "+vpnClientActiveCache.get(networkServiceType).size());

                       /* WsCommunicationVPNClient wsCommunicationVPNServer = vpnClientActiveCache.get(networkServiceType).get(remote);

                        //Verified is this vpn is active
                        if (!wsCommunicationVPNServer.isActive()){

                            wsCommunicationVPNServer.getConnection().close();
                            vpnClientActiveCache.remove(wsCommunicationVPNServer);

                        } */

                    }

                }

                sleep(WsCommunicationVPNClientManagerAgent.SLEEP_TIME);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * (non-javadoc)
     * @see Thread#start()
     */
    @Override
    public synchronized void start() {
        isRunning = Boolean.TRUE;
        super.start();
    }

    /**
     * Add a new applicant to vpn
     * @param applicant
     */
    public void addRequestedVpnConnections(PlatformComponentProfile applicant, PlatformComponentProfile remote){


        if (requestedVpnConnections.containsKey(applicant.getNetworkServiceType())){

            requestedVpnConnections.get(applicant.getNetworkServiceType()).put(remote.getIdentityPublicKey(), applicant);

        }else {

            Map<String, PlatformComponentProfile> newMap = new HashMap<>();
            newMap.put(remote.getIdentityPublicKey(), applicant);
            requestedVpnConnections.put(applicant.getNetworkServiceType(), newMap);
        }

    }

    /**
     * Return the active connection
     *
     * @param applicant
     * @return WsCommunicationVPNClient
     */
    public WsCommunicationVPNClient getActiveVpnConnection(PlatformComponentProfile applicant, String remotePlatformComponentProfile){

        if (vpnClientActiveCache.containsKey(applicant.getNetworkServiceType())){

            if (vpnClientActiveCache.get(applicant.getNetworkServiceType()).containsKey(remotePlatformComponentProfile)){
                return vpnClientActiveCache.get(applicant.getNetworkServiceType()).get(remotePlatformComponentProfile);
            }else {
                throw new IllegalArgumentException("The remotePlatformComponentProfile is no valid, do not exist a vpn connection for this ");
            }

        }else {
            throw new IllegalArgumentException("The applicant is no valid, do not exist a vpn connection for this ");
        }
    }

    /**
     * Get the isRunning
     * @return boolean
     */
    public boolean isRunning() {
        return isRunning;
    }
}
