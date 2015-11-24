/*
 * @#NetworkNodeMainRunner.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;


import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.FermatEmbeddedNodeServer;

import org.jboss.logging.Logger;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodeMainRunner</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 11/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkNodeMainRunner {

    /**
     * Represent the LOG
     */
    private static final Logger LOG = Logger.getLogger(NetworkNodeMainRunner.class.getName());

    public static void main(String [ ] args) throws IOException, ServletException {

        try {

            System.out.println("***********************************************************************");
            System.out.println("*FERMAT - NETWORK NODE STARTING                                       *");
            System.out.println("***********************************************************************");
            FermatEmbeddedNodeServer fermatEmbeddedNodeServer = new FermatEmbeddedNodeServer();
            fermatEmbeddedNodeServer.start();

            System.out.println("Network node started...");

        }catch (Exception e){

            System.out.println("***********************************************************************");
            System.out.println("*FERMAT - ERROR NETWORK NODE *");
            System.out.println("***********************************************************************");
            LOG.error(e);
        }

    }

}
