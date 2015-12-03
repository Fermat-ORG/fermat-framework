/*
 * @#Test.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.FermatEmbeddedNodeServer;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.JaxRsActivator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.servlets.HomeServlet;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.util.Headers;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.Test</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Test {

    public static void main(String [ ] args) throws IOException, ServletException {

        try {

            System.out.println("***********************************************************************");
            System.out.println("* FERMAT - Plugin Network Node - Version 1.0 (2015)                   *");
            System.out.println("* www.fermat.org                                                      *");
            System.out.println("* www.bitDubai.com                                                    *");
            System.out.println("* NETWORK NODE STARTING                                               *");
            System.out.println("***********************************************************************");

            FermatEmbeddedNodeServer fermatEmbeddedNodeServer = new FermatEmbeddedNodeServer();
            fermatEmbeddedNodeServer.start();

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

