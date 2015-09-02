/*
 * @#CommunicationServerMainRunner.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1.CommunicationCloudServerMainRunner</code> initialize
 * the plugin root that represent the cloud server and start it
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationCloudServerMainRunner {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String [ ] args){

        CommunicationCloudServerPluginRoot communicationCloudServerPluginRoot = null;

        try {

            /*
             * Create the plugin root
             */
            communicationCloudServerPluginRoot = new CommunicationCloudServerPluginRoot();

            /*
             * By default the server is configure to be disable (NOT START),
             * then set to enable to start
             */
            communicationCloudServerPluginRoot.setDisableServerFlag(CommunicationCloudServerPluginRoot.ENABLE_SERVER);

            /*
             * Start the process
             */
            communicationCloudServerPluginRoot.start();

        } catch (Exception e) {

            /**
             * Print the error
             */
            e.printStackTrace();

            /**
             * Stop the execution
             */
            communicationCloudServerPluginRoot.stop();


        }
    }

}
