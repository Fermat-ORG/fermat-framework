/*
 * @#WebServicesApplication.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.webservices;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.webservices.WebServicesApplication</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WebServicesApplication extends Application {

    /**
     * Represent the PLUGIN_ROOT_ATT_NAME
     */
    public static final String PLUGIN_ROOT_ATT_NAME = "wsCommunicationCloudServerAttRef";

    /**
     * Represent the wsCommunicationCloudServer
     */
    private WsCommunicationCloudServer wsCommunicationCloudServer;

    /**
     * Constructor whit parameter
     * @param wsCommunicationCloudServer
     */
    public WebServicesApplication(WsCommunicationCloudServer wsCommunicationCloudServer){
        super();
        this.wsCommunicationCloudServer = wsCommunicationCloudServer;
    }

    /**
     * (non-javadoc)
     * @see Application#createInboundRoot()
     */
    @Override
    public synchronized Restlet createInboundRoot() {

        /*
         * Put the reference into the context
         */
        getContext().getAttributes().put(WebServicesApplication.PLUGIN_ROOT_ATT_NAME, wsCommunicationCloudServer);
        Router router = new Router(getContext());
        router.attach("/components/registered/", ComponentRegisteredListWebService.class);
        return router;
    }
}
