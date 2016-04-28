/*
* @#AvailableNodes.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.AvailableNodes</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 26/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/availablenodes")
public class AvailableNodes implements RestFulServices {



    @GET
    @Path("/listavailablenodesprofile")
    @Produces(MediaType.APPLICATION_JSON)
    public String listAvailableNodesProfile(){

        List<NodeProfile> listnode = new ArrayList<>();

        NodeProfile nodeProfile1 = new NodeProfile();
        nodeProfile1.setName("nodeInexistent");
        nodeProfile1.setIdentityPublicKey("147258369");
        nodeProfile1.setIp("192.168.1.24");
        nodeProfile1.setDefaultPort(9090);

        listnode.add(nodeProfile1);

        NodeProfile nodeProfile2 = new NodeProfile();
        nodeProfile2.setName("nodetest");
        nodeProfile2.setIdentityPublicKey("123456789");
        nodeProfile2.setIp("192.168.1.4");
        nodeProfile2.setDefaultPort(9090);

        listnode.add(nodeProfile2);

        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();

        jsonObject.addProperty("success", Boolean.TRUE);
        jsonObject.addProperty("data", gson.toJson(listnode));

        return gson.toJson(jsonObject);
    }

}
