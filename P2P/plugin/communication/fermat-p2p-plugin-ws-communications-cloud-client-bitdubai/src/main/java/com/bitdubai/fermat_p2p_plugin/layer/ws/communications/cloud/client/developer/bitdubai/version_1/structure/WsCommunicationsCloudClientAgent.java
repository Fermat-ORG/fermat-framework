/*
 * @#WsCommunicationsCloudClientAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.WsCommunicationsCloudClientAgent</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudClientAgent extends Thread {

    /*
     * Represent the sleep time for the read or send (2000 milliseconds)
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
        while (!isConnected){

            /*
             * If the connection is not open and not connecting
             */
            if (!wsCommunicationsCloudClientChannel.getConnection().isOpen()
                    && !wsCommunicationsCloudClientChannel.getConnection().isConnecting()){

                try {

                    /*
                     * Try to connect
                     */
                    wsCommunicationsCloudClientChannel.connect();
                    isConnected = wsCommunicationsCloudClientChannel.getConnection().isOpen();

                    /*
                     * If is not connected sleep for a while
                     */
                    if (!isConnected){

                        /**
                         * Sleep for the next try connection
                         */
                        sleep(WsCommunicationsCloudClientAgent.SLEEP_TIME);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    this.interrupt();
                }

            }

        }

    }
}
