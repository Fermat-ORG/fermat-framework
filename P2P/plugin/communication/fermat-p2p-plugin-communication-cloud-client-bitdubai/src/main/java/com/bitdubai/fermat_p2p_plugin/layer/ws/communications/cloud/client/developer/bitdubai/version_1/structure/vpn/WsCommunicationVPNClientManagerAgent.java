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
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
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
    private static long SLEEP_TIME = 160000;

    /**
     *  Represent the instance
     */
    private static WsCommunicationVPNClientManagerAgent instance = new WsCommunicationVPNClientManagerAgent();

    /**
     * Represent the vpnClientActiveCache;
     */
    private Map<NetworkServiceType, Map<String, WsCommunicationVPNClient>> vpnClientActiveCache;

    /**
     * Represent the eventManager
     */
    private EventManager eventManager;

    /**
     * Represent the isRunning
     */
    private boolean isRunning;

    /**
     * Constructor
     */
    private WsCommunicationVPNClientManagerAgent(){
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
    public void createNewWsCommunicationVPNClient(URI serverURI, String vpnServerIdentity, PlatformComponentProfile participant, PlatformComponentProfile remotePlatformComponentProfile, PlatformComponentProfile remoteParticipantNetworkService, EventManager eventManager) {

        /*
         * Create the identity
         */
        ECCKeyPair vpnClientIdentity = new ECCKeyPair();

        /*
         * Create a new headers
         */
        Map<String, String> headers = new HashMap<>();

        /*
         * Clean the extra data to reduce size
         */
        PlatformComponentProfileCommunication registerParticipant = (PlatformComponentProfileCommunication) participant;
        registerParticipant.setExtraData(null);

        /*
         * Get json representation
         */
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonAttNamesConstants.NETWORK_SERVICE_TYPE, remoteParticipantNetworkService.getNetworkServiceType().toString());
        jsonObject.addProperty(JsonAttNamesConstants.CLIENT_IDENTITY_VPN, vpnClientIdentity.getPublicKey());
        jsonObject.addProperty(JsonAttNamesConstants.APPLICANT_PARTICIPANT_VPN, registerParticipant.toJson());
        jsonObject.addProperty(JsonAttNamesConstants.REMOTE_PARTICIPANT_VPN, remotePlatformComponentProfile.getIdentityPublicKey());

        /*
         * Add the att to the header
         */
        headers.put(JsonAttNamesConstants.HEADER_ATT_NAME_TI, AsymmetricCryptography.encryptMessagePublicKey(jsonObject.toString(), vpnServerIdentity));

        /*
         * Construct the vpn client
         */
        final WsCommunicationVPNClient vpnClient = new WsCommunicationVPNClient(this, vpnClientIdentity, serverURI, remotePlatformComponentProfile, remoteParticipantNetworkService, vpnServerIdentity, headers);

        /*
         * Configure the event manager
         */
        this.eventManager = eventManager;

        System.out.println("GUARDANDO LA CONEXION EN CACHE CON NS = " + remoteParticipantNetworkService.getNetworkServiceType());
        System.out.println("GUARDANDO LA CONEXION EN CACHE CON PK = " + remotePlatformComponentProfile.getIdentityPublicKey());

        /*
         * Add to the vpn client active
         */
        if (vpnClientActiveCache.containsKey(remoteParticipantNetworkService.getNetworkServiceType())){

            vpnClientActiveCache.get(remoteParticipantNetworkService.getNetworkServiceType()).put(remotePlatformComponentProfile.getIdentityPublicKey(), vpnClient);

        }else {

            Map<String, WsCommunicationVPNClient> newMap = new ConcurrentHashMap<>();
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
    }

    /**
     * Notify when a vpn connection close
     */
    public void riseVpnConnectionCloseNotificationEvent(NetworkServiceType networkServiceApplicant, PlatformComponentProfile remoteParticipant) {

        System.out.println("WsCommunicationVPNClientManagerAgent - riseVpnConnectionCloseNotificationEvent");
        FermatEvent platformEvent = eventManager.getNewEvent(P2pEventType.VPN_CONNECTION_CLOSE);
        VPNConnectionCloseNotificationEvent event =  (VPNConnectionCloseNotificationEvent) platformEvent;
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);
        event.setNetworkServiceApplicant(networkServiceApplicant);
        event.setRemoteParticipant(remoteParticipant);
        eventManager.raiseEvent(platformEvent);
        System.out.println("WsCommunicationVPNClientManagerAgent - Raised Event = P2pEventType.VPN_CONNECTION_CLOSE");
    }

    /**
     * Notify when a vpn connection loose
     */
    public void riseVpnConnectionLooseNotificationEvent(NetworkServiceType networkServiceApplicant, PlatformComponentProfile remoteParticipant) {

        System.out.println("WsCommunicationVPNClientManagerAgent - riseVpnConnectionLooseNotificationEvent");
        FermatEvent platformEvent = eventManager.getNewEvent(P2pEventType.VPN_CONNECTION_LOOSE);
        VPNConnectionLooseNotificationEvent event =  (VPNConnectionLooseNotificationEvent) platformEvent;
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);
        event.setNetworkServiceApplicant(networkServiceApplicant);
        event.setRemoteParticipant(remoteParticipant);
        eventManager.raiseEvent(platformEvent);
        System.out.println("WsCommunicationVPNClientManagerAgent - Raised Event = P2pEventType.VPN_CONNECTION_LOOSE");
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
    public synchronized WsCommunicationVPNClient getActiveVpnConnection(NetworkServiceType applicantNetworkServiceType, PlatformComponentProfile remotePlatformComponentProfile){

        System.out.println("WsCommunicationVPNClientManagerAgent getActiveVpnConnection - remotePlatformComponentProfile = "+remotePlatformComponentProfile.getAlias());
        System.out.println("WsCommunicationVPNClientManagerAgent getActiveVpnConnection - applicantNetworkServiceType = "+applicantNetworkServiceType);

        for (NetworkServiceType networkServiceType: vpnClientActiveCache.keySet()) {
            System.out.println("WsCommunicationVPNClientManagerAgent networkServiceType available= "+networkServiceType);
        }

        System.out.println("WsCommunicationVPNClientManagerAgent vpnClientActiveCache.containsKey(applicantNetworkServiceType) = "+vpnClientActiveCache.containsKey(applicantNetworkServiceType));

        if (vpnClientActiveCache.containsKey(applicantNetworkServiceType)){

            System.out.println("WsCommunicationVPNClientManagerAgent - vpnClientActiveCache.get(applicantNetworkServiceType).size() = "+vpnClientActiveCache.get(applicantNetworkServiceType).size());

            if (vpnClientActiveCache.get(applicantNetworkServiceType).containsKey(remotePlatformComponentProfile.getIdentityPublicKey())){
                return vpnClientActiveCache.get(applicantNetworkServiceType).get(remotePlatformComponentProfile.getIdentityPublicKey());
            }else {

                System.out.println("WsCommunicationVPNClientManagerAgent getActiveVpnConnection - pk = "+remotePlatformComponentProfile.getIdentityPublicKey());
                throw new IllegalArgumentException("The remote pk is no valid, do not exist a vpn connection for this pk = "+remotePlatformComponentProfile.getIdentityPublicKey());
            }

        }else {
            throw new IllegalArgumentException("The applicantNetworkServiceType is no valid, do not exist a vpn connection for this ");
        }
    }

    /**
     * Get the isRunning
     * @return boolean
     */
    public boolean isRunning() {
        return isRunning;
    }

    /*
     * Close all Vpn Connections when close the App
     */
    public void closeAllVpnConnections(){

        try {

            System.out.println("WsCommunicationVPNClientManagerAgent - closeAllVpnConnections()");

            if (!vpnClientActiveCache.isEmpty()) {

                isRunning = Boolean.FALSE;

                for (NetworkServiceType networkServiceType : vpnClientActiveCache.keySet()) {
                    for (String remote : vpnClientActiveCache.get(networkServiceType).keySet()) {
                        WsCommunicationVPNClient wsCommunicationVPNClient = vpnClientActiveCache.get(networkServiceType).get(remote);
                        wsCommunicationVPNClient.close();
                        vpnClientActiveCache.get(networkServiceType).remove(remote);
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Get the instance value
     *
     * @return instance current value
     */
    public static WsCommunicationVPNClientManagerAgent getInstance() {
        return instance;
    }
}
