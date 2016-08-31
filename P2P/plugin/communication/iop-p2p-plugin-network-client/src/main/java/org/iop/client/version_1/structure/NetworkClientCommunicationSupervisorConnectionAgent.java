/*
* @#NetworkClientCommunicationSupervisorConnectionAgent.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package org.iop.client.version_1.structure;

import org.iop.client.version_1.IoPClientPluginRoot;
import org.iop.client.version_1.channels.endpoints.NetworkClientCommunicationChannel;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.NetworkClientCommunicationSupervisorConnectionAgent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkClientCommunicationSupervisorConnectionAgent implements Runnable {

    private IoPClientPluginRoot networkClientCommunicationPluginRoot;

    /**
     * Constructor with parameters
     * @param networkClientCommunicationPluginRoot
     */
    public NetworkClientCommunicationSupervisorConnectionAgent(IoPClientPluginRoot networkClientCommunicationPluginRoot){
        this.networkClientCommunicationPluginRoot = networkClientCommunicationPluginRoot;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        if(networkClientCommunicationPluginRoot.getNetworkClientCommunicationConnection() != null &&
                getCommunicationsNetworkClientChannel() != null){

            Session session = getConnection();

            if (session != null) {

                try {

                    if (session.isOpen()) {

                        System.out.println("SENDING PING TO NODE");
                        getCommunicationsNetworkClientChannel().sendPing();

                    }else{
                        System.out.println("SENDING PING TO NODE - ERROR - SESSION IS NOT OPEN");
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

    /**
     * Get the connection
     * @return Session
     */
    private Session getConnection(){
        return networkClientCommunicationPluginRoot.getNetworkClientCommunicationConnection().getNetworkClientCommunicationChannel().getClientConnection();
    }

    /**
     * Get the NetworkClientCommunicationChannel
     * @return NetworkClientCommunicationChannel
     */
    private NetworkClientCommunicationChannel getCommunicationsNetworkClientChannel(){
        return  networkClientCommunicationPluginRoot.getNetworkClientCommunicationConnection().getNetworkClientCommunicationChannel();
    }

}
