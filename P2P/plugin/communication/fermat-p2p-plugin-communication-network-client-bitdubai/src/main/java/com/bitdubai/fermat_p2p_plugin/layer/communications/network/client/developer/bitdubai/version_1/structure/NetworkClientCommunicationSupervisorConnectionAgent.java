/*
* @#NetworkClientCommunicationSupervisorConnectionAgent.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.NetworkClientCommunicationPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.channels.endpoints.NetworkClientCommunicationChannel;

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

    private NetworkClientCommunicationPluginRoot networkClientCommunicationPluginRoot;

    /**
     * Constructor with parameters
     * @param networkClientCommunicationPluginRoot
     */
    public NetworkClientCommunicationSupervisorConnectionAgent(NetworkClientCommunicationPluginRoot networkClientCommunicationPluginRoot){
        this.networkClientCommunicationPluginRoot = networkClientCommunicationPluginRoot;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        if(networkClientCommunicationPluginRoot.isStarted() &&
                networkClientCommunicationPluginRoot.getNetworkClientCommunicationConnection() != null &&
                getCommunicationsNetworkClientChannel() != null){

            Session session = getConnection();

            if (session != null) {

                try {

                    System.out.println("SENDING PING TO NODE");

                    if (session.isOpen())
//                        getCommunicationsNetworkClientChannel().sendPing();
                        getCommunicationsNetworkClientChannel().sendPong();

                }catch (Exception ex) {
                    System.out.println(ex.getCause());
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
