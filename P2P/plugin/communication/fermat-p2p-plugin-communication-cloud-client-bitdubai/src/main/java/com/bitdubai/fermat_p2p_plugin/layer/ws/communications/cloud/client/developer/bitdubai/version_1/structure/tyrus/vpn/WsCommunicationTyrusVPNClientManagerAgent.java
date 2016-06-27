/*
 * @#WsCommunicationVPNClientManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.vpn;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.conf.CloudClientVpnConfigurator;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.glassfish.tyrus.client.ClientManager;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.vpn.WsCommunicationVPNClientManagerAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationTyrusVPNClientManagerAgent{

    /**
     *  Represent the instance
     */
    private static WsCommunicationTyrusVPNClientManagerAgent instance = new WsCommunicationTyrusVPNClientManagerAgent();

    /**
     * Represent the instance.vpnClientActiveCache;
     */
    private Map<NetworkServiceType, Map<String, WsCommunicationTyrusVPNClient>> vpnClientActiveCache;

    /**
     * Represent the eventManager
     */
    private EventManager eventManager;

    /**
     * Constructor
     */
    private WsCommunicationTyrusVPNClientManagerAgent(){
       this.vpnClientActiveCache = new ConcurrentHashMap<>();
    }

    /**
     * Create a new WsCommunicationVPNClient
     *
     * @param serverURI
     * @param vpnServerIdentity
     * @param remotePlatformComponentProfile
     */
    public void createNewWsCommunicationVPNClient(URI serverURI, String vpnServerIdentity, PlatformComponentProfile participant, PlatformComponentProfile remotePlatformComponentProfile, PlatformComponentProfile remoteParticipantNetworkService, EventManager eventManager) throws IOException, DeploymentException {

        /*
         * Create the identity
         */
        ECCKeyPair vpnClientIdentity = new ECCKeyPair();


        /*
         * Clean the extra data to reduce size
         */
        PlatformComponentProfileCommunication registerParticipant = (PlatformComponentProfileCommunication) participant;
        registerParticipant.setExtraData(null);

        /*
         * Construct the vpn client
         */
        WsCommunicationTyrusVPNClient newPpnClient = new WsCommunicationTyrusVPNClient(this, vpnClientIdentity, remotePlatformComponentProfile, remoteParticipantNetworkService, vpnServerIdentity);

        /*
         * Configure the event manager
         */
        this.eventManager = eventManager;

        System.out.println("GUARDANDO LA CONEXION EN CACHE CON NS = " + remoteParticipantNetworkService.getNetworkServiceType());
        System.out.println("GUARDANDO LA CONEXION EN CACHE CON PK = " + remotePlatformComponentProfile.getIdentityPublicKey());

        /*
         * Add to the vpn client active
         */
        if (instance.vpnClientActiveCache.containsKey(remoteParticipantNetworkService.getNetworkServiceType())){

            instance.vpnClientActiveCache.get(remoteParticipantNetworkService.getNetworkServiceType()).put(remotePlatformComponentProfile.getIdentityPublicKey(), newPpnClient);

        }else {

            Map<String, WsCommunicationTyrusVPNClient> newMap = new ConcurrentHashMap<>();
            newMap.put(remotePlatformComponentProfile.getIdentityPublicKey(), newPpnClient);
            instance.vpnClientActiveCache.put(remoteParticipantNetworkService.getNetworkServiceType(), newMap);
        }

        CloudClientVpnConfigurator cloudClientVpnConfigurator = new CloudClientVpnConfigurator(vpnClientIdentity, remoteParticipantNetworkService, participant, remotePlatformComponentProfile);

        ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create()
                                                                        .configurator(cloudClientVpnConfigurator)
                                                                        .build();

        ClientManager clientManager = ClientManager.createClient();

        /*
        ClientManager.ReconnectHandler reconnectHandler = new ClientManager.ReconnectHandler() {

            @Override
            public boolean onDisconnect(CloseReason closeReason) {
                System.out.println("### Reconnecting... ");
                return true;
            }

            @Override
            public boolean onConnectFailure(Exception exception) {
                // Thread.sleep(...) to avoid potential DDoS when you don't limit number of reconnects.
                return true;
            }

        };

        /*
         *  Add Property RECONNECT_HANDLER to reconnect automatically
         */
       /* clientManager.getProperties().put(ClientProperties.RECONNECT_HANDLER, reconnectHandler);
        */

        clientManager.connectToServer(newPpnClient, clientConfig, serverURI);

    }

    /**
     * Notify when a vpn connection close
     */
    public void riseVpnConnectionCloseNotificationEvent(NetworkServiceType networkServiceApplicant, PlatformComponentProfile remoteParticipant,boolean isCloseNormal) {

        System.out.println("WsCommunicationVPNClientManagerAgent - riseVpnConnectionCloseNotificationEvent");
        FermatEvent platformEvent = eventManager.getNewEvent(P2pEventType.VPN_CONNECTION_CLOSE);
        VPNConnectionCloseNotificationEvent event =  (VPNConnectionCloseNotificationEvent) platformEvent;
        event.setSource(EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN);
        event.setNetworkServiceApplicant(networkServiceApplicant);
        event.setRemoteParticipant(remoteParticipant);
        event.setIsCloseNormal(isCloseNormal);
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
     * Return the active connection
     *
     * @param applicantNetworkServiceType
     * @return WsCommunicationVPNClient
     */
    public synchronized WsCommunicationTyrusVPNClient getActiveVpnConnection(NetworkServiceType applicantNetworkServiceType, PlatformComponentProfile remotePlatformComponentProfile){

        System.out.println("WsCommunicationVPNClientManagerAgent getActiveVpnConnection - remotePlatformComponentProfile = "+remotePlatformComponentProfile.getAlias());
        System.out.println("WsCommunicationVPNClientManagerAgent getActiveVpnConnection - applicantNetworkServiceType = "+applicantNetworkServiceType);

        for (NetworkServiceType networkServiceType: instance.vpnClientActiveCache.keySet()) {
            System.out.println("WsCommunicationVPNClientManagerAgent networkServiceType available= "+networkServiceType);
        }

        System.out.println("WsCommunicationVPNClientManagerAgent instance.vpnClientActiveCache.containsKey(applicantNetworkServiceType) = "+instance.vpnClientActiveCache.containsKey(applicantNetworkServiceType));

        if (instance.vpnClientActiveCache.containsKey(applicantNetworkServiceType)){

            System.out.println("WsCommunicationVPNClientManagerAgent - instance.vpnClientActiveCache.get(applicantNetworkServiceType).size() = "+instance.vpnClientActiveCache.get(applicantNetworkServiceType).size());

            System.out.println("---------------------CLAVE DISPONIBLES:"+instance.vpnClientActiveCache.keySet().toString()+"------------------------------------");
            System.out.println("---------------------CLAVES BUSCADA:"+remotePlatformComponentProfile.getIdentityPublicKey()+"------------------------------------");

            if (instance.vpnClientActiveCache.get(applicantNetworkServiceType).containsKey(remotePlatformComponentProfile.getIdentityPublicKey())){
                return instance.vpnClientActiveCache.get(applicantNetworkServiceType).get(remotePlatformComponentProfile.getIdentityPublicKey());
            }else {

                System.out.println("WsCommunicationVPNClientManagerAgent getActiveVpnConnection - pk = "+remotePlatformComponentProfile.getIdentityPublicKey());
                throw new IllegalArgumentException("The remote pk is no valid, do not exist a vpn connection for this pk = "+remotePlatformComponentProfile.getIdentityPublicKey());
            }

        }else {
            throw new IllegalArgumentException("The applicantNetworkServiceType is no valid, do not exist a vpn connection for this ");
        }
    }

    /*
     * Close all Vpn Connections when close the App
     */
    public void closeAllVpnConnections(){

        try {

            System.out.println("WsCommunicationTyrusVPNClientManagerAgent - closeAllVpnConnections() -- Calling wsCommunicationVPNClient.close().");

            if (!instance.vpnClientActiveCache.isEmpty()) {

                for (NetworkServiceType networkServiceType : instance.vpnClientActiveCache.keySet()) {

                    for (String remote : instance.vpnClientActiveCache.get(networkServiceType).keySet()) {
                        WsCommunicationTyrusVPNClient wsCommunicationVPNClient = instance.vpnClientActiveCache.get(networkServiceType).get(remote);
                        wsCommunicationVPNClient.close();
                        instance.vpnClientActiveCache.get(networkServiceType).remove(remote);
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
    public static WsCommunicationTyrusVPNClientManagerAgent getInstance() {
        return instance;
    }
}
