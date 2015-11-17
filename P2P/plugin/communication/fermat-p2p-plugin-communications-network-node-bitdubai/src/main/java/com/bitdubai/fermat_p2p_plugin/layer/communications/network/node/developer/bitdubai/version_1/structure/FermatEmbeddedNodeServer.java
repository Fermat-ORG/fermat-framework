/*
 * @#FermatEmbeddedNodeServer.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketClientChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketNodeChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.servlets.HomeServlet;

import org.jboss.logging.Logger;
import org.xnio.BufferAllocator;
import org.xnio.ByteBufferSlicePool;

import java.io.File;

import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.FermatEmbeddedNodeServer</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatEmbeddedNodeServer {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(FermatEmbeddedNodeServer.class.getName());

    /**
     * Represent the APP_NAME
     */
    public static final String APP_NAME = "/fermat";

    /**
     * Represent the WAR_APP_NAME
     */
    public static final String WAR_APP_NAME = "FermatNetworkNode.war";

    /**
     * Represent the DEFAULT_PORT number
     */
    public static final int DEFAULT_PORT = 8080;

    /**
     * Represent the DEFAULT_IP number
     */
    public static final String DEFAULT_IP = "0.0.0.0";

    /**
     * Represent the deploymentManager instance
     */
    private DeploymentManager deploymentManager;

    /**
     * Represent the pathHandler instance
     */
    private PathHandler pathHandler;

    /**
     * Represent the undertowServer instance
     */
    private Undertow undertowServer;

    /**
     * Constructor
     */
    public FermatEmbeddedNodeServer(){
       super();
    }


    private void init() throws ServletException {

        /*
         * Create the  appWebSocketDeploymentInfo and configure
         */
        WebSocketDeploymentInfo appWebSocketDeploymentInfo = new WebSocketDeploymentInfo();
        appWebSocketDeploymentInfo.setBuffers(new ByteBufferSlicePool(BufferAllocator.BYTE_BUFFER_ALLOCATOR, 17000, 17000 * 16));
        appWebSocketDeploymentInfo.addEndpoint(WebSocketNodeChannelServerEndpoint.class);
        appWebSocketDeploymentInfo.addEndpoint(WebSocketClientChannelServerEndpoint.class);

        /*
         * Create the  appDeploymentInfo and configure
         */
        DeploymentInfo appDeploymentInfo = Servlets.deployment()
                                        .setClassLoader(FermatEmbeddedNodeServer.class.getClassLoader())
                                        .setContextPath(APP_NAME)
                                        .setDeploymentName(WAR_APP_NAME)
                                        .setResourceManager(new FileResourceManager(new File("src/main/webapp"), 1024))
                                        .addServlets(Servlets.servlet("HomeServlet", HomeServlet.class).addMapping("/home"))
                                        .setResourceManager(new ClassPathResourceManager(FermatEmbeddedNodeServer.class.getClassLoader(), FermatEmbeddedNodeServer.class.getPackage()))
                                        .addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, appWebSocketDeploymentInfo);

        /*
         * Create the deploymentManager
         */
        deploymentManager = Servlets.defaultContainer().addDeployment(appDeploymentInfo);

        /*
         * Deploy the app
         */
        deploymentManager.deploy();

        /*
         * Create the path handle
         */
        pathHandler = Handlers.path(Handlers.redirect("/fermat/home")).addPrefixPath(APP_NAME, deploymentManager.start());

        /*
         * Create the server
         */
        undertowServer = Undertow.builder().addHttpListener(DEFAULT_PORT, DEFAULT_IP).setHandler(pathHandler).build();

    }

    /**
     * Method tha initialize and start the
     * Embedded server
     */
    public void start() throws ServletException {

        init();
        undertowServer.start();
    }
}
