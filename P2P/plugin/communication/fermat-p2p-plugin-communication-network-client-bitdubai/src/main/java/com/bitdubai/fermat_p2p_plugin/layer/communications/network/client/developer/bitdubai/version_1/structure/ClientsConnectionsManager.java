package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.util.HardcodeConstants;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.ClientsConnectionsManager</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 05/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientsConnectionsManager {

    /*
     * Represent the listActorConnectIntoNode
     */
    private Map<String, String> listActorConnectIntoNode;

    /*
     * Represent the listConnectionActiveToNode
     */
    private Map<String, NetworkClientCommunicationConnection> listConnectionActiveToNode;

    /*
     * Represent the node identity
     */
    private ECCKeyPair identity;

    private NetworkClientCommunicationPluginRoot pluginRoot;

    private ErrorManager errorManager;

    private EventManager eventManager;

    private LocationManager locationManager;

    public ClientsConnectionsManager(ECCKeyPair identity, ErrorManager errorManager, EventManager eventManager, LocationManager locationManager, NetworkClientCommunicationPluginRoot pluginRoot){
        this.listActorConnectIntoNode = new HashMap<>();
        this.listConnectionActiveToNode = new HashMap<>();
        this.pluginRoot = pluginRoot;
        this.identity = identity;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.locationManager = locationManager;
    }

    public Map<String, String> getListActorConnectIntoNode() {
        return listActorConnectIntoNode;
    }

    public void setListActorConnectIntoNode(Map<String, String> listActorConnectIntoNode) {
        this.listActorConnectIntoNode = listActorConnectIntoNode;
    }

    public Map<String, NetworkClientCommunicationConnection> getListConnectionActiveToNode() {
        return listConnectionActiveToNode;
    }

    public void setListConnectionActiveToNode(Map<String, NetworkClientCommunicationConnection> listConnectionActiveToNode) {
        this.listConnectionActiveToNode = listConnectionActiveToNode;
    }

    public synchronized void requestConnectionToExternalNode(String identityPublicKey, String hostPath){

        try {

            URI uri = new URI(HardcodeConstants.WS_PROTOCOL +  hostPath + "/fermat/ws/client-channel");

            final NetworkClientCommunicationConnection networkClientCommunicationConnection = new NetworkClientCommunicationConnection(
                    uri,
                    errorManager,
                    eventManager,
                    locationManager,
                    identity,
                    pluginRoot.getPluginVersionReference(),
                    pluginRoot,
                    -1,
                    Boolean.TRUE
                    );

            listActorConnectIntoNode.put(identityPublicKey, hostPath);
            listConnectionActiveToNode.put(hostPath, networkClientCommunicationConnection);

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

}
