/*
 * @#ConfigurationWebService.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.ConfigurationManager;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.ShareMemoryCacheForVpnClientsConnections;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.VpnClientConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.ConfigurationWebService</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 25/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/api/admin/configuration")
public class ConfigurationWebService {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ConfigurationWebService.class));

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Constructor
     */
    public ConfigurationWebService() {
        super();
        this.gson = new Gson();
    }

    @GET
    public String isActive() {
        return "The Configuration WebService is running ...";
    }


    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfiguration() {

        LOG.info("Executing getConfiguration()");

        Configuration configuration = new Configuration();
        configuration.setPort(Integer.valueOf(ConfigurationManager.getValue(ConfigurationManager.PORT)));
        configuration.setUser(ConfigurationManager.getValue(ConfigurationManager.USER));
        configuration.setPassword(ConfigurationManager.getValue(ConfigurationManager.PASSWORD));

        return Response.status(200).entity(gson.toJson("")).build();

    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveConfiguration(Configuration configuration) {

        LOG.info("Executing saveConfiguration()");

        try {

            if(!configuration.getPort().toString().equals(ConfigurationManager.getValue(ConfigurationManager.PORT))){
                ConfigurationManager.updateValue(ConfigurationManager.PORT, configuration.getPort().toString());
            }

            if(!configuration.getUser().equals(ConfigurationManager.getValue(ConfigurationManager.USER))){
                ConfigurationManager.updateValue(ConfigurationManager.USER, configuration.getUser());
            }

            if(!configuration.getPassword().equals(ConfigurationManager.getValue(ConfigurationManager.PASSWORD))){
                ConfigurationManager.updateValue(ConfigurationManager.PASSWORD, configuration.getPassword());
            }

        } catch (ConfigurationException e) {
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.status(200).entity(gson.toJson("")).build();

    }

}
