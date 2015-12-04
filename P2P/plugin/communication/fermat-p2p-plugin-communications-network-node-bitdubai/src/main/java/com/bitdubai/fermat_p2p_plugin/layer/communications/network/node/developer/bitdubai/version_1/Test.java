/*
 * @#Test.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.JaxRsActivator;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.servlets.HomeServlet;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

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

    public static void main(String[] args) throws Exception {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(Handlers.path()
                                .addPrefixPath("/webapp", createStaticResourceHandler())
                                .addPrefixPath("/api", new InternalRestHandler())
                                .addPrefixPath("/serv", createServletHandler())
                                .addPrefixPath("/fermat", createRestApiHandler())
                ).build();
        server.start();
    }

    private static HttpHandler createStaticResourceHandler() {
        final ResourceManager staticResources = new ClassPathResourceManager(Test.class.getClassLoader(),"webapp");
        final ResourceHandler resourceHandler = new ResourceHandler(staticResources);
        resourceHandler.setWelcomeFiles("index.html");
        return resourceHandler;
    }

    private static class InternalRestHandler implements HttpHandler {
        @Override
        public void handleRequest(final HttpServerExchange exchange) throws Exception {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("Hello World U");
        }
    }

    private static HttpHandler createServletHandler() throws Exception {
        DeploymentInfo di = Servlets.deployment()
                .setClassLoader(Test.class.getClassLoader())
                .setContextPath("/serv")
                .setDeploymentName("My Servlets")
                .addServlets(
                        Servlets.servlet("helloServlet", HomeServlet.class).addMapping("/home")
                );
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(di);
        manager.deploy();
        HttpHandler servletHandler = manager.start();
        return servletHandler;
    }

    private static HttpHandler createRestApiHandler() throws Exception {
        final UndertowJaxrsServer server = new UndertowJaxrsServer();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(JaxRsActivator.class.getName() );
        DeploymentInfo di = server.undertowDeployment(deployment, "/rest/api/v1")
                .setClassLoader(Test.class.getClassLoader())
                .setContextPath("/fermat")
                .setDeploymentName("My API");
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(di);
        manager.deploy();
        HttpHandler servletHandler = manager.start();
        return servletHandler;
    }
}

