/*
 * @#CommunicationServerMainRunner.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.server.developer.bitdubai.version_1.CommunicationServerMainRunner</code> initialize
 * the plugin root that represent the cloud server and start it
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationServerMainRunner {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String [ ] args){

        CommunicationServerPluginRoot communicationServerPluginRoot = null;

        try {

            /*
             * Create the plugin root
             */
            communicationServerPluginRoot = new CommunicationServerPluginRoot();

            /*
             * By default the server is configure to be disable (NOT START),
             * then set to enable to start
             */
            communicationServerPluginRoot.setDisableServerFlag(CommunicationServerPluginRoot.ENABLE_SERVER);

            /*
             * Start the process
             */
            communicationServerPluginRoot.start();

        } catch (Exception e) {

            /**
             * Print the error
             */
            e.printStackTrace();

            /**
             * Stop the execution
             */
            communicationServerPluginRoot.stop();


        }
    }

}
