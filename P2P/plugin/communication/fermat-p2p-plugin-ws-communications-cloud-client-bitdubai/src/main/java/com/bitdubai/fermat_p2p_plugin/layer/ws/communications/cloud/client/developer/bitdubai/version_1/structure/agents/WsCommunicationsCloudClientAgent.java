/*
 * @#WsCommunicationsCloudClientAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClientChannel;

import org.java_websocket.WebSocket;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.agents.WsCommunicationsCloudClientAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudClientAgent extends Thread {

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
    public WsCommunicationsCloudClientAgent(WsCommunicationsCloudClientChannel wsCommunicationsCloudClientChannel){
        this.wsCommunicationsCloudClientChannel = wsCommunicationsCloudClientChannel;
        this.wsCommunicationsCloudClientChannel.setWsCommunicationsCloudClientAgent(this);
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
      //  while (!isConnected){

           // System.out.println(" WsCommunicationsCloudClientAgent - !wsCommunicationsCloudClientChannel.getConnection().isOpen() = "+!wsCommunicationsCloudClientChannel.getConnection().isOpen());
           // System.out.println(" WsCommunicationsCloudClientAgent - !wsCommunicationsCloudClientChannel.getConnection().isConnecting() = "+!wsCommunicationsCloudClientChannel.getConnection().isConnecting());
           // System.out.println(" WsCommunicationsCloudClientAgent - !wsCommunicationsCloudClientChannel.getConnection().isClosing() = "+!wsCommunicationsCloudClientChannel.getConnection().isClosing());
           // System.out.println(" WsCommunicationsCloudClientAgent -  wsCommunicationsCloudClientChannel.getConnection().getReadyState() = "+wsCommunicationsCloudClientChannel.getConnection().getReadyState());
           // System.out.println(" WsCommunicationsCloudClientAgent -  wsCommunicationsCloudClientChannel.getConnection().isFlushAndClose() = " + wsCommunicationsCloudClientChannel.getConnection().isFlushAndClose());
            /*
             * If the connection is not open and not connecting and not closing and is closed
             */
            if (!wsCommunicationsCloudClientChannel.getConnection().isOpen()               &&
                    !wsCommunicationsCloudClientChannel.getConnection().isConnecting()     &&
                          !wsCommunicationsCloudClientChannel.getConnection().isClosing()  &&
                                wsCommunicationsCloudClientChannel.getConnection().getReadyState() == WebSocket.READYSTATE.NOT_YET_CONNECTED){

                try {

                    /*
                     * Try to connect
                     */
                    wsCommunicationsCloudClientChannel.connect();
                    isConnected = wsCommunicationsCloudClientChannel.getConnection().isOpen();

                    System.out.println(" WsCommunicationsCloudClientAgent - isConnected = "+isConnected);

                    /*
                     * If is not connected sleep for a while
                     */
                    if (!isConnected){

                       System.out.println(" WsCommunicationsCloudClientAgent - sleep for = "+WsCommunicationsCloudClientAgent.SLEEP_TIME);

                        /**
                         * Sleep for the next try connection
                         */
                       // sleep(WsCommunicationsCloudClientAgent.SLEEP_TIME);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    wsCommunicationsCloudClientChannel.getConnection().close();
                    interrupt();
                }

            }else {

                System.out.println(" WsCommunicationsCloudClientAgent - sleep for = 10000");

                /**
                 * Sleep for the next try connection
                 */
                try {
                    sleep(new Long(10000).longValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

     //   }

    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
