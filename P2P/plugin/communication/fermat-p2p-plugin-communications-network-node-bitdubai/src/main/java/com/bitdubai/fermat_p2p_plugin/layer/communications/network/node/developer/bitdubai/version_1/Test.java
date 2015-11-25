/*
 * @#Test.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketClientChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketNodeChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.JaxRsActivator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.servlets.HomeServlet;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.weld.environment.util.Reflections;
import org.xnio.BufferAllocator;
import org.xnio.ByteBufferSlicePool;


import java.io.IOException;
import java.nio.file.Path;
import java.security.Provider;
import java.util.Set;

import javax.servlet.ServletException;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletInfo;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.Test</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Test {

    private static Undertow server;
    public static void main( String[] args ) throws ServletException, IllegalArgumentException, IOException {

        ServletInfo restEasyServlet = Servlets.servlet("ResteasyServlet", HttpServlet30Dispatcher.class)
                .setAsyncSupported(true)
                .setLoadOnStartup(1)
                .addMapping("/fermat/rest/api/v1/hello");

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
        DeploymentInfo appDeploymentInfo  = new DeploymentInfo();
        appDeploymentInfo.setClassLoader(Test.class.getClassLoader())
                .setContextPath("fermat")
                .setDeploymentName("test.war")
                .addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, appWebSocketDeploymentInfo)
                .addServlets(Servlets.servlet("HomeServlet", HomeServlet.class).addMapping("/home"))
                .addServlet(restEasyServlet).setDeploymentName("ResteasyUndertow")
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(appDeploymentInfo);
        deploymentManager.deploy();
        Undertow server = null;
        try {
            server = Undertow.builder()
                    .addHttpListener(8080, "0.0.0.0")
                    .setHandler(deploymentManager.start())
                    .build();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        server.start();

    }
}
