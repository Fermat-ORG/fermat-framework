/*
 * @#CommunicationServerMainRunner.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1.WsCommunicationsServerCloudMainRunner</code> initialize
 * the plugin root that represent the cloud server and start it
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsServerCloudMainRunner {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String [ ] args){

        WsCommunicationsServerCloudPluginRoot wsCommunicationsServerCloudPluginRoot = null;

        try {

            /*
             * Create the plugin root
             */
            wsCommunicationsServerCloudPluginRoot = new WsCommunicationsServerCloudPluginRoot();

            /*
             * By default the server is configure to be disable (NOT START),
             * then set to enable to start
             */
            wsCommunicationsServerCloudPluginRoot.setDisableServerFlag(WsCommunicationsServerCloudPluginRoot.ENABLE_SERVER);

            /*
             * Start the process
             */
            wsCommunicationsServerCloudPluginRoot.start();

        } catch (Exception e) {

            /**
             * Print the error
             */
            e.printStackTrace();

            /**
             * Stop the execution
             */
            wsCommunicationsServerCloudPluginRoot.stop();


        }
    }

}
