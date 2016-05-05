package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.call_channels;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.conf.NetworkCallChannelConfigurator;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.glassfish.tyrus.client.ClientManager;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;

/**
 * The Class <code>NetworkClientCommunicationCallChannelManagerAgent</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientCommunicationCallChannelManagerAgent {

    /**
     *  Represent the instance
     */
    private static final NetworkClientCommunicationCallChannelManagerAgent instance = new NetworkClientCommunicationCallChannelManagerAgent();

    /**
     * Represent the instance.vpnClientActiveCache;
     */
    private Map<NetworkServiceType, Map<String, NetworkClientCommunicationCallChannel>> vpnClientActiveCache;

    /**
     * Represent the eventManager
     */
    private EventManager eventManager;

    /**
     * Constructor
     */
    private NetworkClientCommunicationCallChannelManagerAgent() {

       this.vpnClientActiveCache = new ConcurrentHashMap<>();
    }

    /**
     * Create a new WsCommunicationVPNClient
     *
     * @param serverURI
     * @param vpnServerIdentity
     * @param remotePlatformComponentProfile
     */
    public void createNewCallChannel(final URI                   serverURI                      ,
                                     final String                vpnServerIdentity              ,
                                     final Profile               participant                    ,
                                     final Profile               remotePlatformComponentProfile ,
                                     final NetworkServiceProfile remoteParticipantNetworkService,
                                     final EventManager          eventManager                   ) throws IOException, DeploymentException {

        /*
         * Create the identity
         */
        ECCKeyPair vpnClientIdentity = new ECCKeyPair();

        /*
         * Clean the extra data to reduce size
         */
        if (participant instanceof ActorProfile) {
            ((ActorProfile) participant).setPhoto(null);
            ((ActorProfile) participant).setExtraData(null);
        }

        /*
         * Construct the vpn client
         */
        NetworkClientCommunicationCallChannel newCallChannel = new NetworkClientCommunicationCallChannel(this, vpnClientIdentity, remotePlatformComponentProfile, remoteParticipantNetworkService, vpnServerIdentity);

        /*
         * Configure the event manager
         */
        this.eventManager = eventManager;

        System.out.println("CACHING CONNECTION WITH NS = " + remoteParticipantNetworkService.getNetworkServiceType());
        System.out.println("CACHING CONNECTION WITH PK = " + remotePlatformComponentProfile.getIdentityPublicKey());

        /*
         * Add to the vpn client active
         */
        if (instance.vpnClientActiveCache.containsKey(remoteParticipantNetworkService.getNetworkServiceType())){

            instance.vpnClientActiveCache.get(remoteParticipantNetworkService.getNetworkServiceType()).put(remotePlatformComponentProfile.getIdentityPublicKey(), newCallChannel);

        } else {

            Map<String, NetworkClientCommunicationCallChannel> newMap = new ConcurrentHashMap<>();
            newMap.put(remotePlatformComponentProfile.getIdentityPublicKey(), newCallChannel);
            instance.vpnClientActiveCache.put(remoteParticipantNetworkService.getNetworkServiceType(), newMap);
        }

        NetworkCallChannelConfigurator cloudClientVpnConfigurator = new NetworkCallChannelConfigurator(
                vpnClientIdentity              ,
                remoteParticipantNetworkService,
                participant                    ,
                remotePlatformComponentProfile
        );

        ClientEndpointConfig clientConfig = ClientEndpointConfig.Builder.create()
                                                                        .configurator(cloudClientVpnConfigurator)
                                                                        .build();

        ClientManager clientManager = ClientManager.createClient();

        clientManager.connectToServer(newCallChannel, clientConfig, serverURI);

    }

    /**
     * Notify when a vpn connection close
     */
    public void raiseNetworkChannelCallConnectionCloseNotificationEvent(final NetworkServiceType networkServiceApplicant,
                                                                        final Profile            remoteParticipant      ,
                                                                        final boolean            isCloseNormal          ) {

        System.out.println("NetworkClientCommunicationCallChannelManagerAgent - raiseNetworkChannelCallConnectionCloseNotificationEvent");

        // TODO IMPLEMENT
    }

    /**
     * Notify when a vpn connection loose
     */
    public void raiseNetworkChannelCallConnectionLooseNotificationEvent(final NetworkServiceType networkServiceApplicant,
                                                                        final Profile            remoteParticipant      ) {

        System.out.println("NetworkClientCommunicationCallChannelManagerAgent - raiseNetworkChannelCallConnectionLooseNotificationEvent");

        // TODO IMPLEMENT
    }

    /**
     * Return the active connection
     *
     * @param applicantNetworkServiceType
     *
     * @return WsCommunicationVPNClient
     */
    public synchronized NetworkClientCommunicationCallChannel getActiveVpnConnection(NetworkServiceType applicantNetworkServiceType, Profile remotePlatformComponentProfile){

        System.out.println("NetworkClientCommunicationCallChannelManagerAgent getActiveVpnConnection - remotePlatformComponentProfile = "+remotePlatformComponentProfile.getIdentityPublicKey());
        System.out.println("NetworkClientCommunicationCallChannelManagerAgent getActiveVpnConnection - applicantNetworkServiceType = "+applicantNetworkServiceType);

        for (NetworkServiceType networkServiceType: instance.vpnClientActiveCache.keySet()) {
            System.out.println("NetworkClientCommunicationCallChannelManagerAgent networkServiceType available= "+networkServiceType);
        }

        System.out.println("NetworkClientCommunicationCallChannelManagerAgent instance.vpnClientActiveCache.containsKey(applicantNetworkServiceType) = "+instance.vpnClientActiveCache.containsKey(applicantNetworkServiceType));

        if (instance.vpnClientActiveCache.containsKey(applicantNetworkServiceType)){

            System.out.println("NetworkClientCommunicationCallChannelManagerAgent - instance.vpnClientActiveCache.get(applicantNetworkServiceType).size() = "+instance.vpnClientActiveCache.get(applicantNetworkServiceType).size());

            System.out.println("---------------------CLAVE DISPONIBLES:"+instance.vpnClientActiveCache.keySet().toString()+"------------------------------------");
            System.out.println("---------------------CLAVES BUSCADA:"+remotePlatformComponentProfile.getIdentityPublicKey()+"------------------------------------");

            if (instance.vpnClientActiveCache.get(applicantNetworkServiceType).containsKey(remotePlatformComponentProfile.getIdentityPublicKey())){
                return instance.vpnClientActiveCache.get(applicantNetworkServiceType).get(remotePlatformComponentProfile.getIdentityPublicKey());
            }else {

                System.out.println("NetworkClientCommunicationCallChannelManagerAgent getActiveVpnConnection - pk = "+remotePlatformComponentProfile.getIdentityPublicKey());
                throw new IllegalArgumentException("The remote pk is no valid, do not exist a vpn connection for this pk = "+remotePlatformComponentProfile.getIdentityPublicKey());
            }

        }else {
            throw new IllegalArgumentException("The applicantNetworkServiceType is no valid, do not exist a vpn connection for this ");
        }
    }

    /*
     * Close all active calls when close the app.
     */
    public void closeAllActiveCalls(){

        try {

            System.out.println("NetworkClientCommunicationCallChannelManagerAgent - closeAllActiveCalls()");

            if (!instance.vpnClientActiveCache.isEmpty()) {

                for (NetworkServiceType networkServiceType : instance.vpnClientActiveCache.keySet()) {

                    for (String remote : instance.vpnClientActiveCache.get(networkServiceType).keySet()) {
                        NetworkClientCommunicationCallChannel networkClientCommunicationCallChannel = instance.vpnClientActiveCache.get(networkServiceType).get(remote);
                        networkClientCommunicationCallChannel.closeChannel();
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
    public static NetworkClientCommunicationCallChannelManagerAgent getInstance() {
        return instance;
    }
}
