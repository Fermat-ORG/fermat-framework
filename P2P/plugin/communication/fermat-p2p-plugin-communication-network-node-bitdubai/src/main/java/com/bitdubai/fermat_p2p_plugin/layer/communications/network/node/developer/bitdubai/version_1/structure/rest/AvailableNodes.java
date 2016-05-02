/*
* @#AvailableNodes.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

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
@Path("/available/nodes")
public class AvailableNodes implements RestFulServices {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(AvailableNodes.class));

    /**
     * Represent the daoFactory
     */
    private DaoFactory daoFactory;

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Constructor
     */
    public AvailableNodes(){
        daoFactory = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        gson = new Gson();
    }

    @GET
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

        jsonObject.addProperty("success", Boolean.TRUE);
        jsonObject.addProperty("data", gson.toJson(listnode));

        return gson.toJson(jsonObject);
    }

}
