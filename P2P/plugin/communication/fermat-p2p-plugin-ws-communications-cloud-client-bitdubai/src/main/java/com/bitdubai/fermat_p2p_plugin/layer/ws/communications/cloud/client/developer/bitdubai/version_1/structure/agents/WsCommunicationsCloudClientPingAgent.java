/*
 * @#WsCommunicationsCloudClientAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClientChannel;

import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents.WsCommunicationsCloudClientAgent</code>
 * is responsible to verify the connection status with the remote node, its validate if is this connection are alive using a ping message<p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudClientPingAgent extends Thread {

    /*
     * Represent the sleep time for send new ping (30000 milliseconds)
     */
    private static final long SLEEP_TIME = 30000;

    /**
     * Represent the wsCommunicationsCloudClientChannel
     */
    private WsCommunicationsCloudClientChannel wsCommunicationsCloudClientChannel;

    /**
     * Constructor with parameters
     * @param wsCommunicationsCloudClientChannel
     */
    public WsCommunicationsCloudClientPingAgent(WsCommunicationsCloudClientChannel wsCommunicationsCloudClientChannel){
        this.wsCommunicationsCloudClientChannel = wsCommunicationsCloudClientChannel;
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

                if (wsCommunicationsCloudClientChannel.getConnection().isOpen()){

                    System.out.println(" WsCommunicationsCloudClientPingAgent - running");

                    try {

                        /*
                         * If a pong message respond pending
                         */
                        if (wsCommunicationsCloudClientChannel.isPongMessagePending()){
                            throw new RuntimeException("Connection maybe not active");
                        }

                        /*
                         * Send the ping message
                         */
                        wsCommunicationsCloudClientChannel.sendPingMessage();

                    } catch (Exception ex) {
                        System.out.println(" WsCommunicationsCloudClientPingAgent - Error occurred sending ping to the node, closing the connection to remote node");
                        wsCommunicationsCloudClientChannel.getConnection().close();
                        this.interrupt();

                        //TODO: START CONNECTION AGENT AND RISE EVENT NOTIFY THE CONNECTION
                        break;
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
