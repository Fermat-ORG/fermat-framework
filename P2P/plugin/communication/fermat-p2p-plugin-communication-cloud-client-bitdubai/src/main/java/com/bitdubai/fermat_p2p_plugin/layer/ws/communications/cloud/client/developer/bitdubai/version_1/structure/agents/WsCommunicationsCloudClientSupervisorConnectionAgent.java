/*
 * @#WsCommunicationsCloudClientSupervisorConnectionAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.WsCommunicationsCloudClientPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientChannel;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientConnection;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents.WsCommunicationsCloudClientSupervisorConnectionAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudClientSupervisorConnectionAgent extends Thread {

    /**
     * Represent the wsCommunicationsCloudClientPluginRoot
     */
    private WsCommunicationsCloudClientPluginRoot wsCommunicationsCloudClientPluginRoot;

    /**
     * Constructor with parameters
     * @param wsCommunicationsCloudClientPluginRoot
     */
    public WsCommunicationsCloudClientSupervisorConnectionAgent(WsCommunicationsCloudClientPluginRoot wsCommunicationsCloudClientPluginRoot){
        this.wsCommunicationsCloudClientPluginRoot = wsCommunicationsCloudClientPluginRoot;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        if(wsCommunicationsCloudClientPluginRoot.getStatus() == ServiceStatus.STARTED &&
                wsCommunicationsCloudClientPluginRoot.getListOfWSCommunicationsTyrusCloudClientConnection() != null){

            for(NetworkServiceType networkServiceType : wsCommunicationsCloudClientPluginRoot.getListOfWSCommunicationsTyrusCloudClientConnection().keySet()) {


                Session session = getConnection(networkServiceType);

                if (session != null) {

                   // System.out.println(" WsCommunicationsCloudClientSupervisorConnectionAgent - Connection is Open = " + session.isOpen());

                    try {

                        if (session.isOpen()) {
                            getWsCommunicationsTyrusCloudClientChannel(networkServiceType).sendPing();
                        }

                    } catch (Exception ex) {
                        System.out.println(" WsCommunicationsCloudClientSupervisorConnectionAgent - Error occurred sending ping to the node, closing the connection to remote node");
                        System.out.println(" ERROR: "+ex.getMessage());
                        System.out.println(" WsCommunicationsCloudClientSupervisorConnectionAgent -  closeConnection()");

                        getWsCommunicationsTyrusCloudClientChannel(networkServiceType).closeConnection();
                        ((WsCommunicationsTyrusCloudClientConnection) wsCommunicationsCloudClientPluginRoot.getCommunicationsCloudClientConnection(networkServiceType)).getWsCommunicationsTyrusCloudClientChannel().setIsRegister(Boolean.FALSE);
                        ((WsCommunicationsTyrusCloudClientConnection) wsCommunicationsCloudClientPluginRoot.getCommunicationsCloudClientConnection(networkServiceType)).getWsCommunicationsTyrusCloudClientChannel().raiseClientConnectionLooseNotificationEvent();
                    }

                }

            }
        }
    }

    /**
     * Get the connection
     * @return Session
     */
    private Session getConnection(NetworkServiceType networkServiceType){
        return ((WsCommunicationsTyrusCloudClientConnection)wsCommunicationsCloudClientPluginRoot.getCommunicationsCloudClientConnection(networkServiceType)).getWsCommunicationsTyrusCloudClientChannel().getClientConnection();
    }

    /**
     * Get the WsCommunicationsTyrusCloudClientChannel
     * @return WsCommunicationsTyrusCloudClientChannel
     */
    private WsCommunicationsTyrusCloudClientChannel getWsCommunicationsTyrusCloudClientChannel(NetworkServiceType networkServiceType){
        return ((WsCommunicationsTyrusCloudClientConnection)wsCommunicationsCloudClientPluginRoot.getCommunicationsCloudClientConnection(networkServiceType)).getWsCommunicationsTyrusCloudClientChannel();
    }

}
