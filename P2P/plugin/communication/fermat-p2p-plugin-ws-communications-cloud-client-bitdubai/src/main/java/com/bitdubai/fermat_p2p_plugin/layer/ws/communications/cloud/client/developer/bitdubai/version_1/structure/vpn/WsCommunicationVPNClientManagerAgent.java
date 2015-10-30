/*
 * @#WsCommunicationVPNClientManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.vpn;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
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
     * @param vpnServerIdentity
     * @param remotePlatformComponentProfile
     */
    public void createNewWsCommunicationVPNClient(URI serverURI, String vpnServerIdentity, String participantIdentity, PlatformComponentProfile remotePlatformComponentProfile, PlatformComponentProfile remoteParticipantNetworkService) {

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
        jsonObject.addProperty(JsonAttNamesConstants.REGISTER_PARTICIPANT_IDENTITY_VPN, participantIdentity);
        jsonObject.addProperty(JsonAttNamesConstants.CLIENT_IDENTITY_VPN, vpnClientIdentity.getPublicKey());

        /*
         * Add the att to the header
         */
        headers.put(JsonAttNamesConstants.HEADER_ATT_NAME_TI,  AsymmetricCryptography.encryptMessagePublicKey(jsonObject.toString(), vpnServerIdentity));

        /*
         * Construct the vpn client
         */
        WsCommunicationVPNClient vpnClient = new WsCommunicationVPNClient(vpnClientIdentity, serverURI, remotePlatformComponentProfile, remoteParticipantNetworkService, vpnServerIdentity, headers);


        System.out.println("GUARDANDO LA CONEXION EN CACHE CON NS = " + remoteParticipantNetworkService.getNetworkServiceType());
        System.out.println("GUARDANDO LA CONEXION EN CACHE CON PK = " + remotePlatformComponentProfile.getIdentityPublicKey());

        /*
         * Add to the vpn client active
         */
        if (vpnClientActiveCache.containsKey(remoteParticipantNetworkService.getNetworkServiceType())){

            vpnClientActiveCache.get(remoteParticipantNetworkService.getNetworkServiceType()).put(remotePlatformComponentProfile.getIdentityPublicKey(), vpnClient);

        }else {

            Map<String, WsCommunicationVPNClient> newMap = new HashMap<>();
            newMap.put(remotePlatformComponentProfile.getIdentityPublicKey(), vpnClient);
            vpnClientActiveCache.put(remoteParticipantNetworkService.getNetworkServiceType(), newMap);
        }

        /*
         * call the connect
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
                    System.out.println(" WsCommunicationVPNClientManagerAgent - Auto stop ");
                    //Auto stop
                    isRunning = Boolean.FALSE;
                }

                for (NetworkServiceType networkServiceType : vpnClientActiveCache.keySet()) {

                    for (String remote : vpnClientActiveCache.get(networkServiceType).keySet()) {

                       System.out.println(" WsCommunicationVPNClientManagerAgent - vpnClientActiveCache.get("+networkServiceType+").size() = "+vpnClientActiveCache.get(networkServiceType).size());

                       WsCommunicationVPNClient wsCommunicationVPNClient = vpnClientActiveCache.get(networkServiceType).get(remote);

                        //Verified if this vpn connection is open
                        if (!wsCommunicationVPNClient.getConnection().isOpen()){

                            try {

                                /*
                                 * Verified if a pong message respond pending
                                 */
                                if (wsCommunicationVPNClient.isPongMessagePending()){
                                    throw new RuntimeException("Connection maybe not active");
                                }

                                wsCommunicationVPNClient.sendPingMessage();

                            }catch (Exception e){
                                System.out.println(" WsCommunicationVPNClientManagerAgent - Error occurred sending ping to the vpn node, closing the connection to remote node");
                                wsCommunicationVPNClient.close();
                                vpnClientActiveCache.get(networkServiceType).remove(wsCommunicationVPNClient);
                                if (vpnClientActiveCache.get(networkServiceType).isEmpty()){
                                    vpnClientActiveCache.remove(networkServiceType);
                                }
                            }

                        }

                    }

                }

                if (!this.isInterrupted()){
                    sleep(WsCommunicationVPNClientManagerAgent.SLEEP_TIME);
                }

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
     * Return the active connection
     *
     * @param applicantNetworkServiceType
     * @return WsCommunicationVPNClient
     */
    public WsCommunicationVPNClient getActiveVpnConnection(NetworkServiceType applicantNetworkServiceType, PlatformComponentProfile remotePlatformComponentProfile){

        if (vpnClientActiveCache.containsKey(applicantNetworkServiceType)){

            if (vpnClientActiveCache.get(applicantNetworkServiceType).containsKey(remotePlatformComponentProfile.getIdentityPublicKey())){
                return vpnClientActiveCache.get(applicantNetworkServiceType).get(remotePlatformComponentProfile.getIdentityPublicKey());
            }else {
                throw new IllegalArgumentException("The applicantNetworkServiceType is no valid, do not exist a vpn connection for this ");
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
