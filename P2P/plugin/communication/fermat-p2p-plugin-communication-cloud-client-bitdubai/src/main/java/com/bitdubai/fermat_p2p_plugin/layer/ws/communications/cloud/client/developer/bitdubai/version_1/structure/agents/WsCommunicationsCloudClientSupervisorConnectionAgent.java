/*
 * @#WsCommunicationsCloudClientSupervisorConnectionAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.WsCommunicationsCloudClientPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClientConnection;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientChannel;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.tyrus.WsCommunicationsTyrusCloudClientConnection;

import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import javax.websocket.DeploymentException;
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

        try {

            if(wsCommunicationsCloudClientPluginRoot.getStatus() == ServiceStatus.STARTED){

                System.out.println(" WsCommunicationsCloudClientSupervisorConnectionAgent - Connection is Open = "+getConnection().isOpen());

                if (!getConnection().isOpen()) {
                    System.out.println(" WsCommunicationsCloudClientSupervisorConnectionAgent - Trying to reconnect whit cloud server ");
                    //wsCommunicationsCloudClientPluginRoot.connectClient();
                }else {

                    try {

                        if (getConnection().isOpen()){
                            getWsCommunicationsTyrusCloudClientChannel().sendPing();
                        }

                    } catch (Exception ex) {
                        System.out.println(" WsCommunicationsCloudClientSupervisorConnectionAgent - Error occurred sending ping to the node, closing the connection to remote node");
                        getWsCommunicationsTyrusCloudClientChannel().closeConnection();
                        ((WsCommunicationsCloudClientConnection)wsCommunicationsCloudClientPluginRoot.getCommunicationsCloudClientConnection()).getWsCommunicationsCloudClientChannel().setIsRegister(Boolean.FALSE);
                        ((WsCommunicationsCloudClientConnection)wsCommunicationsCloudClientPluginRoot.getCommunicationsCloudClientConnection()).getWsCommunicationsCloudClientChannel().raiseClientConnectionLooseNotificationEvent();
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        } catch (DeploymentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Get the connection
     * @return Session
     */
    private Session getConnection(){
        return ((WsCommunicationsTyrusCloudClientConnection)wsCommunicationsCloudClientPluginRoot.getCommunicationsCloudClientConnection()).getWsCommunicationsTyrusCloudClientChannel().getClientConnection();
    }

    /**
     * Get the WsCommunicationsTyrusCloudClientChannel
     * @return WsCommunicationsTyrusCloudClientChannel
     */
    private WsCommunicationsTyrusCloudClientChannel getWsCommunicationsTyrusCloudClientChannel(){
        return ((WsCommunicationsTyrusCloudClientConnection)wsCommunicationsCloudClientPluginRoot.getCommunicationsCloudClientConnection()).getWsCommunicationsTyrusCloudClientChannel();
    }

}
