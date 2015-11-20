/*
 * @#FermatEmbeddedNodeServer.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketClientChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketNodeChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.JaxRsActivator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.servlets.HomeServlet;

import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.xnio.BufferAllocator;
import org.xnio.ByteBufferSlicePool;

import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
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
     * Represent the serverBuilder instance
     */
    private final Undertow.Builder serverBuilder;

    /**
     * Represent the servletContainer instance
     */
    private final ServletContainer servletContainer;

    /**
     * Represent the pathHandler instance
     */
    private PathHandler pathHandler;

    /**
     * Represent the UndertowJaxrsServer instance
     */
    private final UndertowJaxrsServer server;

    /**
     * Constructor
     */
    public FermatEmbeddedNodeServer(){
       super();
       this.server = new UndertowJaxrsServer();
       this.serverBuilder = Undertow.builder().addHttpListener(DEFAULT_PORT, DEFAULT_IP);
       this.servletContainer = Servlets.defaultContainer();
    }

    private DeploymentInfo createDeploymentInfo(){

        /*
         * Create the App WebSocketDeploymentInfo and configure
         */
        WebSocketDeploymentInfo appWebSocketDeploymentInfo = new WebSocketDeploymentInfo();
        appWebSocketDeploymentInfo.setBuffers(new ByteBufferSlicePool(BufferAllocator.BYTE_BUFFER_ALLOCATOR, 17000, 17000 * 16));
        appWebSocketDeploymentInfo.addEndpoint(WebSocketNodeChannelServerEndpoint.class);
        appWebSocketDeploymentInfo.addEndpoint(WebSocketClientChannelServerEndpoint.class);

        /*
         * Create the App ResteasyDeployment and configure
         */
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(JaxRsActivator.class.getName());
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

        /*
         * Create the App DeploymentInfo and configure
         */
        DeploymentInfo appDeploymentInfo  = server.undertowDeployment(deployment, APP_NAME);
        appDeploymentInfo.setClassLoader(FermatEmbeddedNodeServer.class.getClassLoader())
                        .setContextPath(APP_NAME)
                        .setDeploymentName(WAR_APP_NAME)
                        .addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, appWebSocketDeploymentInfo)
                        .addServlets(Servlets.servlet("HomeServlet", HomeServlet.class).addMapping("/home"))
                        .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        /*
         * Return the appDeploymentInfo
         */
        return appDeploymentInfo;

    }

    /**
     * Method tha initialize and start the
     * Embedded server
     */
    public void start() throws ServletException {

        DeploymentInfo appDeploymentInfo = createDeploymentInfo();
        server.deploy(appDeploymentInfo);
        server.start(serverBuilder);
    }
}
