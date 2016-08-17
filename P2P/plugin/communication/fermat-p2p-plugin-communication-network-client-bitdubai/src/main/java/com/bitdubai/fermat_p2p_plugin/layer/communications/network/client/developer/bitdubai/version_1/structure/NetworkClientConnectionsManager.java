package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.ConnectivityManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.P2PLayerManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantRequestConnectionToExternalNodeException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientConnectionsManager</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 05/05/16.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 20/05/2016.
 *
 * @author
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientConnectionsManager {

    private ECCKeyPair                           identity       ;
    private NetworkClientCommunicationPluginRoot pluginRoot     ;
    private EventManager                         eventManager   ;
    private LocationManager                      locationManager;

    private Map<String, NetworkClientCommunicationConnection> activeConnectionsToExternalNodes;
    private ConnectivityManager connectivityManager;
    private P2PLayerManager p2PLayerManager;


    public NetworkClientConnectionsManager(final ECCKeyPair                           identity       ,
                                           final EventManager                         eventManager   ,
                                           final LocationManager                      locationManager,
                                           final NetworkClientCommunicationPluginRoot pluginRoot,
                                           ConnectivityManager connectivityManager                    ,
                                           final P2PLayerManager p2PLayerManager) {

        this.activeConnectionsToExternalNodes = new HashMap<>();
        this.pluginRoot      = pluginRoot     ;
        this.identity        = identity       ;
        this.eventManager    = eventManager   ;
        this.locationManager = locationManager;
        this.connectivityManager = connectivityManager;
        this.p2PLayerManager = p2PLayerManager;

    }

    public Map<String, NetworkClientCommunicationConnection> getActiveConnectionsToExternalNodes() {
        return activeConnectionsToExternalNodes;
    }

    public synchronized void requestConnectionToExternalNode(final String             uriToNode         ,
                                                             final NetworkServiceType networkServiceType,
                                                             final ActorProfile       actorProfile      ) throws CantRequestConnectionToExternalNodeException {

        try {

            final NetworkClientCommunicationConnection networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    uriToNode,
                    eventManager,
                    locationManager,
                    identity,
                    pluginRoot,
                    -1,
                    Boolean.TRUE,
                    null, // it should stablished to null to be save when is connected in ConneciontHistory,
                    connectivityManager,
                    p2PLayerManager
            );

            activeConnectionsToExternalNodes.put(uriToNode, networkClientCommunicationConnection);

            new Thread(){
                @Override
                public void run(){
                    networkClientCommunicationConnection.initializeAndConnectToExternalNode(
                            networkServiceType,
                            actorProfile
                    );
                }
            }.start();


        } catch (Exception exception) {

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception
            );
            throw new CantRequestConnectionToExternalNodeException(
                    exception,
                    "",
                    "Unhandled Error."
            );
        }
    }
}
