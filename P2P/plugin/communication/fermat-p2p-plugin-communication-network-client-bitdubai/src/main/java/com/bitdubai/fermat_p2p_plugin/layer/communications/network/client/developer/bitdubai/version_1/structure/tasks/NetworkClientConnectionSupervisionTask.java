package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.tasks;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot;

/**
 * The Class <code>NetworkClientConnectionSupervisionTask</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientConnectionSupervisionTask extends Thread {

    /**
     * Represent the networkClientCommunicationPluginRoot
     */
    private NetworkClientCommunicationPluginRoot networkClientCommunicationPluginRoot;

    /**
     * Constructor with parameters
     * @param networkClientCommunicationPluginRoot
     */
    public NetworkClientConnectionSupervisionTask(NetworkClientCommunicationPluginRoot networkClientCommunicationPluginRoot){
        this.networkClientCommunicationPluginRoot = networkClientCommunicationPluginRoot;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        if(networkClientCommunicationPluginRoot.getStatus() == ServiceStatus.STARTED) {

            // check if the connection is open and send a ping message to the server, this way we could keep the connection with the server active indicating him that we're active too.
        }
    }

}
