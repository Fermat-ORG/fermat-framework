/*
 * @#ExampleResourceImpl.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.ExampleResourceImpl</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 10/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("hello")
public class ExampleResourceImpl {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World";
    }
}
