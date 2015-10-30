/*
 * @#WsCommunicationsCloudServerPingAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure;


import org.java_websocket.WebSocket;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationsCloudServerPingAgent</code>
 * is responsible to verify the connections status, its validate if is this connection are alive using a ping message<p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 28/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudServerPingAgent extends Thread {

    /*
     * Represent the sleep time for send new ping (30000 milliseconds)
     */
    private static final long SLEEP_TIME = 30000;

    /**
     * Represent the wsCommunicationCloudServer
     */
    private WsCommunicationCloudServer wsCommunicationCloudServer;

    /**
     * Constructor with parameters
     * @param wsCommunicationCloudServer
     */
    public WsCommunicationsCloudServerPingAgent(WsCommunicationCloudServer wsCommunicationCloudServer){
        this.wsCommunicationCloudServer = wsCommunicationCloudServer;
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


                for (WebSocket connection :wsCommunicationCloudServer.connections()) {

                    System.out.println(" WsCommunicationsCloudServerPingAgent - running");

                    try {

                        /*
                         * Send the ping message
                         */
                        wsCommunicationCloudServer.sendPingMessage(connection);

                    }catch (Exception ex){

                        System.out.println(" WsCommunicationsCloudServerPingAgent - Error occurred sending ping to the node");
                        wsCommunicationCloudServer.onClose(connection, 1000, " - Connection no alive", true);
                    }

                }

                if (!this.isInterrupted()){
                    sleep(SLEEP_TIME);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
