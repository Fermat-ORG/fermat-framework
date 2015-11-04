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
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudClientPingAgent extends Thread {

    /*
     * Represent the sleep time for the read or send (60000 milliseconds)
     */
    private static final long SLEEP_TIME = 60000;

    /**
     * Represent the wsCommunicationsCloudClientChannel
     */
    private WsCommunicationsCloudClientChannel wsCommunicationsCloudClientChannel;

    /**
     * Represent the isConnected
     */
    private boolean isConnected;

    /**
     * Constructor with parameters
     * @param wsCommunicationsCloudClientChannel
     */
    public WsCommunicationsCloudClientPingAgent(WsCommunicationsCloudClientChannel wsCommunicationsCloudClientChannel){
        this.wsCommunicationsCloudClientChannel = wsCommunicationsCloudClientChannel;
        this.isConnected = Boolean.FALSE;
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

                    System.out.println(" WsCommunicationsCloudClientPingAgent - sending ping");

                    try {

                        FramedataImpl1 frame = new FramedataImpl1(Framedata.Opcode.PING);
                        frame.setFin(true);
                        wsCommunicationsCloudClientChannel.getConnection().sendFrame(frame);

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }

                sleep(30000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
