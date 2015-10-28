/*
 * @#RestletCommunicationCloudServer.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.webservices;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer;

import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.webservices.RestletCommunicationCloudServer</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RestletCommunicationCloudServer {

    /**
     * Represent the restletWebServer
     */
    private Component restletWebServer;

    /**
     * Represent the wsCommunicationCloudServer
     */
    private WsCommunicationCloudServer wsCommunicationCloudServer;


    public RestletCommunicationCloudServer(WsCommunicationCloudServer wsCommunicationCloudServer) {
       super();
       restletWebServer = new Component();

       restletWebServer.setName("Restlet Web Server component");
       restletWebServer.setDescription("Add RESTFUL capability to the Ws Communication Cloud Server");
       restletWebServer.setOwner("bitDubai.com");
       restletWebServer.setAuthor("Roberto Requena - (rart3001@gmail.com)");

       Server server = new Server(new Context(), Protocol.HTTP, 8080);
       server.getContext().getParameters().set("tracing", "false");
       restletWebServer.getServers().add(server);

       restletWebServer.getDefaultHost().attach("/fermat/cloud-server/v1", new WebServicesApplication(wsCommunicationCloudServer));
    }

    public void start() throws Exception {
        System.out.println("RestletCommunicationCloudServer - starting");
        restletWebServer.start();
    }
}
