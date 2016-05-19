package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantRequestConnectionToExternalNodeException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.util.HardcodeConstants;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientConnectionsManager</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 05/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientConnectionsManager {

    /*
     * Represent the listActorConnectIntoNode
     */
    private Map<String, String> listActorConnectIntoNode;

    /*
     * Represent the activeConnectionsToExternalNodes
     */
    private Map<String, NetworkClientCommunicationConnection> activeConnectionsToExternalNodes;

    /*
     * Represent the node identity
     */
    private ECCKeyPair identity;

    private NetworkClientCommunicationPluginRoot pluginRoot;

    private EventManager eventManager;

    private LocationManager locationManager;

    public NetworkClientConnectionsManager(ECCKeyPair identity, EventManager eventManager, LocationManager locationManager, NetworkClientCommunicationPluginRoot pluginRoot){
        this.listActorConnectIntoNode = new HashMap<>();
        this.activeConnectionsToExternalNodes = new HashMap<>();
        this.pluginRoot = pluginRoot;
        this.identity = identity;
        this.eventManager = eventManager;
        this.locationManager = locationManager;
    }

    public Map<String, String> getListActorConnectIntoNode() {
        return listActorConnectIntoNode;
    }

    public void setListActorConnectIntoNode(Map<String, String> listActorConnectIntoNode) {
        this.listActorConnectIntoNode = listActorConnectIntoNode;
    }

    public Map<String, NetworkClientCommunicationConnection> getActiveConnectionsToExternalNodes() {
        return activeConnectionsToExternalNodes;
    }

    public void setActiveConnectionsToExternalNodes(Map<String, NetworkClientCommunicationConnection> activeConnectionsToExternalNodes) {
        this.activeConnectionsToExternalNodes = activeConnectionsToExternalNodes;
    }

    public synchronized void requestConnectionToExternalNode(String identityPublicKey, String hostPath){

        try {

            URI uri = new URI(HardcodeConstants.WS_PROTOCOL +  hostPath + "/fermat/ws/client-channel");

            final NetworkClientCommunicationConnection networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    uri,
                    eventManager,
                    locationManager,
                    identity,
                    pluginRoot,
                    -1,
                    Boolean.TRUE
                    );

            listActorConnectIntoNode.put(identityPublicKey, hostPath);
            activeConnectionsToExternalNodes.put(hostPath, networkClientCommunicationConnection);

            new Thread(){
                @Override
                public void run(){
                    networkClientCommunicationConnection.initializeAndConnect();
                }
            }.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void requestConnectionToExternalNode(final String uriToNode) throws CantRequestConnectionToExternalNodeException {

        try {

            URI uri = new URI(HardcodeConstants.WS_PROTOCOL + uriToNode + "/fermat/ws/client-channel");

            final NetworkClientCommunicationConnection networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    uri,
                    eventManager,
                    locationManager,
                    identity,
                    pluginRoot,
                    -1,
                    Boolean.TRUE
            );

            activeConnectionsToExternalNodes.put(uriToNode, networkClientCommunicationConnection);

            new Thread(){
                @Override
                public void run(){
                    networkClientCommunicationConnection.initializeAndConnect();
                }
            }.start();


        } catch (URISyntaxException URISyntaxException) {

            pluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    URISyntaxException
            );
            throw new CantRequestConnectionToExternalNodeException(
                    URISyntaxException,
                    "",
                    ""
            );
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
