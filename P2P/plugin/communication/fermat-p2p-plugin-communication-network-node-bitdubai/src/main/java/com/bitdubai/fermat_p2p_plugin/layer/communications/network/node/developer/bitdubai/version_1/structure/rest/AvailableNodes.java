/*
* @#AvailableNodes.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.wrappers.NetworkNodeCommunicationDeviceLocation;
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

        JsonObject jsonObject = new JsonObject();


        try {

            List<NodesCatalog> listNodesCatalog = daoFactory.getNodesCatalogDao().findAll();

            if(listNodesCatalog != null) {

                List<NodeProfile> listNodeProfile = new ArrayList<>();

                for (NodesCatalog nodesCatalog : listNodesCatalog) {

                    NodeProfile nodeProfile = new NodeProfile();
                    nodeProfile.setName((nodesCatalog.getName() != null ? nodesCatalog.getName() : null));
                    nodeProfile.setIp(nodesCatalog.getIp());
                    nodeProfile.setDefaultPort(nodesCatalog.getDefaultPort());
                    nodeProfile.setIdentityPublicKey(nodesCatalog.getIdentityPublicKey());

                    if(nodesCatalog.getLastLatitude() != null && nodesCatalog.getLastLongitude() != null &&
                            nodesCatalog.getLastLatitude() != 0 && nodesCatalog.getLastLongitude() != 0){

                        Location location = new NetworkNodeCommunicationDeviceLocation(
                                nodesCatalog.getLastLatitude() ,
                                nodesCatalog.getLastLongitude(),
                                null     ,
                                null     ,
                                null     ,
                                System.currentTimeMillis(),
                                LocationSource.UNKNOWN
                        );

                        nodeProfile.setLocation(location);

                    }

                    listNodeProfile.add(nodeProfile);

                }

                jsonObject.addProperty("success", Boolean.TRUE);
                jsonObject.addProperty("data", gson.toJson(listNodeProfile));

            }else{

                jsonObject.addProperty("success", Boolean.FALSE);
                jsonObject.addProperty("message", "There are content in the Table");

            }

        } catch (CantReadRecordDataBaseException e) {
            jsonObject.addProperty("success", Boolean.FALSE);
            jsonObject.addProperty("message", gson.toJson(e));
        }

       return gson.toJson(jsonObject);
    }

}
