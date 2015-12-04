/*
 * @#NetworkNodeMainRunner.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;


import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

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

    /**
     * The main method to start the app
     * @param args
     * @throws IOException
     * @throws ServletException
     */
    public static void main(String [ ] args) throws IOException, ServletException {

        try {
                        
            System.out.println("***********************************************************************");
            System.out.println("* FERMAT - Plugin Network Node - Version 1.0 (2015)                   *");
            System.out.println("* www.fermat.org                                                      *");
            System.out.println("* www.bitDubai.com - 2015                                             *");
            System.out.println("* NETWORK NODE STARTING                                               *");
            System.out.println("***********************************************************************");

            /*
             * Create the plugin root
             */
            NetworkNodePluginRoot networkNodePluginRoot = new NetworkNodePluginRoot();

            /*
             * start the plugin root
             */
            networkNodePluginRoot.start();

            /*
             * Try to automatic open
             */
            openUri();

        }catch (Exception e){

            System.out.println("***********************************************************************");
            System.out.println("*FERMAT - ERROR NETWORK NODE                                          *");
            System.out.println("***********************************************************************");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Network node started satisfactory...");
    }

    /**
     * Open the node uri on the browser
     * @throws IOException
     */
    private static void openUri() throws IOException, URISyntaxException {

        if( !java.awt.Desktop.isDesktopSupported() ) {
            System.out.println("Desktop is not supported.");
            System.out.println("Go To --> http://localhost:8080/");
            return;
        }

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        if( !desktop.isSupported(java.awt.Desktop.Action.BROWSE) ) {
            System.out.println("Desktop doesn't support the browse action.");
            System.out.println("Go To --> http://localhost:8080/");
            return;
        }

        System.out.println("Opening in the browser --> http://localhost:8080/");

        java.net.URI uri = new java.net.URI("http://localhost:8080/");
        desktop.browse( uri );

    }
}
