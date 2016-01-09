/*
 * @#WebSocketCloudServerChannelPingAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty;


import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;

import java.util.Iterator;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationsCloudServerPingAgent</code>
 * is responsible to verify the connections status, its validate if is this connection are alive using a ping message<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WebSocketCloudServerChannelPingAgent extends Thread {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(WebSocketCloudServerChannelPingAgent.class));

    /*
     * Represent the sleep time for send new ping (10000 milliseconds)
     */
    private static final long SLEEP_TIME = 10000;

    /**
     * Represent the webSocketCloudServerChannel
     */
    private WebSocketCloudServerChannel webSocketCloudServerChannel;

    /**
     * Constructor with parameters
     * @param webSocketCloudServerChannel
     */
    public WebSocketCloudServerChannelPingAgent(WebSocketCloudServerChannel webSocketCloudServerChannel){
        this.webSocketCloudServerChannel = webSocketCloudServerChannel;
    }

    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        /*
         * While is no connect
         */
        while (true){

            try {

                Iterator<Session> iterator = webSocketCloudServerChannel.getActiveClientConnection().getSession().getOpenSessions().iterator();

                while (iterator.hasNext()){

                    ClientConnection connection = (ClientConnection) iterator.next();

                    LOG.debug("Running");

                    if (connection.getSession().isOpen()){

                        try {

                            //Validate if pending pong message
                            if (connection.getPendingPongMsg()){
                                throw new RuntimeException("Connection maybe not active");
                            }

                            /*
                             * Send the ping message
                             */
                            webSocketCloudServerChannel.sendPingMessage(connection);

                        }catch (RuntimeException ex){

                            LOG.error("Error occurred sending ping to the client " + connection.getSession().getId() + ", or pending pong message not received");
                            LOG.error("Pending pong message = " + connection.getPendingPongMsg());

                            //wsCommunicationCloudServer.onClose(connection, 1000, " - Connection no alive", true);

                            iterator.remove();
                        }

                    }

                }


                if (!this.isInterrupted()){
                    sleep(SLEEP_TIME);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}