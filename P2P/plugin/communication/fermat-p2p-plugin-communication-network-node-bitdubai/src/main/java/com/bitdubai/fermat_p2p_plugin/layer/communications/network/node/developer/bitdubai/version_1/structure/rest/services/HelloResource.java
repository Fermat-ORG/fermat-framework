/*
 * @#HelloResource.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.RestFulServices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.HelloResource</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/hello")
public class HelloResource implements RestFulServices {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return "Hello World, esta funcionando : )";
    }
}